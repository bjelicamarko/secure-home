package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.UserRealEstateDTO;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.exceptions.RealEstateException;
import com.asdf.myhomeback.exceptions.UserRealEstateException;
import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.models.RealEstate;
import com.asdf.myhomeback.models.UserRealEstate;
import com.asdf.myhomeback.models.UserRole;
import com.asdf.myhomeback.models.enums.UserRoleEnum;
import com.asdf.myhomeback.repositories.UserRealEstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRealEstateServiceImpl implements UserRealEstateService {

    @Autowired
    private UserRealEstateRepository userRealEstateRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RealEstateService realEstateService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void saveUserRealEstate(UserRealEstateDTO userRealEstateDTO) throws Exception {
        UserRoleEnum role = UserRoleEnum.valueOf(userRealEstateDTO.getRole());
        UserRealEstate userRealEstateDuplicate = userRealEstateRepository
                .findDuplicate(userRealEstateDTO.getUsername(), userRealEstateDTO.getRealEstateId());
        if (userRealEstateDuplicate != null) throw new Exception("(User, Real estate) tuple already exist.");

        AppUser user = appUserService.getUser(userRealEstateDTO.getUsername());
        if (user == null) throw new Exception("User with given id doesnt exist.");

        RealEstate realEstate = realEstateService.getRealEstateById(userRealEstateDTO.getRealEstateId());
        if (realEstate == null) throw new Exception("Real estate with given id doesnt exist.");

        int countOfOwners = userRealEstateRepository.countOwnersOfEstate(userRealEstateDTO.getRealEstateId());
        if(countOfOwners == 0 && !userRealEstateDTO.getRole().equals("OWNER"))
            throw new UserRealEstateException("This real estate does not have owner. In order to assign tenant to it, assign owner first.");

        UserRealEstate userRealEstate = new UserRealEstate(user, realEstate, role);
        userRealEstateRepository.save(userRealEstate);

        changeUserRoles(user, role);
    }

    private void changeUserRoles(AppUser user, UserRoleEnum role) {
        UserRole newRole = userRoleService.findByName("ROLE_" + role);
        if ( user.getUserType().equals("ROLE_UNASSIGNED")) {
            user.setRoles(new ArrayList<>());
            user.addRole(newRole);
            user.setUserType("ROLE_" + role);
        } else {
            // if user is getting new role
            UserRole userRole = user.getRoles()
                    .stream()
                    .filter(existingRole -> existingRole.getName().contains(role.toString()))
                    .findAny()
                    .orElse(null);
            if (userRole == null){
                user.addRole(newRole);
                if(user.getRoles().size() == 2) {
                    user.setUserType("ROLE_BOTH");
                }
            }
        }
        appUserService.save(user);
    }

    @Override
    public void changeRoleInUserRealEstate(UserRealEstateDTO userRealEstateDTO) throws Exception {
        UserRoleEnum newRole = UserRoleEnum.valueOf(userRealEstateDTO.getRole());
        AppUser user = appUserService.getUser(userRealEstateDTO.getUsername());
        if (user == null) throw new Exception("User with given id doesnt exist.");

        RealEstate realEstate = realEstateService.getRealEstateById(userRealEstateDTO.getRealEstateId());
        if (realEstate == null) throw new Exception("Real estate with given id doesnt exist.");

        UserRealEstate ure = userRealEstateRepository.findDuplicate(userRealEstateDTO.getUsername(), userRealEstateDTO.getRealEstateId());
        if(ure == null) throw new UserRealEstateException("User with given username does not relate with given real estate.");

        int countOfOwners = userRealEstateRepository.countOwnersOfEstate(userRealEstateDTO.getRealEstateId());
        if(countOfOwners == 1 && !userRealEstateDTO.getRole().equals("OWNER"))
            throw new UserRealEstateException(user.getUsername() + " is the last owner of this real estate therefore you cannot change it to tenant.");

        UserRealEstate userRealEstate = userRealEstateRepository.findDuplicate(userRealEstateDTO.getUsername(), userRealEstateDTO.getRealEstateId());
        userRealEstate.setRole(newRole);
        userRealEstateRepository.save(userRealEstate);

        user = updateUserRole(user);
        appUserService.save(user);
    }

    private AppUser updateUserRole(AppUser user) {
        user.setRoles(new ArrayList<>()); // empty roles

        UserRole owner = userRoleService.findByName("ROLE_OWNER");
        UserRole tenant = userRoleService.findByName("ROLE_TENANT");

        long countOfRoleOwner = findCountOfRole(user.getId(), UserRoleEnum.OWNER); // how many times you are owner
        if (countOfRoleOwner >= 1) {
            user.addRole(owner);
            user.setUserType("ROLE_OWNER");
        }

        long countOfRoleTenant = findCountOfRole(user.getId(), UserRoleEnum.TENANT); // how many times you are tenant
        if (countOfRoleTenant >= 1) {
            user.addRole(tenant);
            user.setUserType("ROLE_TENANT");
        }

        if (user.getRoles().size() == 2) {
            user.setUserType("ROLE_BOTH");
        }

        if (user.getRoles().size() == 0) {
            user.setUserType("ROLE_UNASSIGNED");
            UserRole unassigned = userRoleService.findByName("ROLE_UNASSIGNED");
            user.addRole(unassigned);
        }

        return user;
    }

    @Override
    public List<UserRealEstate> getUserRealEstatesFromUser(String username) {
        return userRealEstateRepository.findUserRealEstateByUsername(username);
    }

    @Override
    public int findCountOfRole(Long userId, UserRoleEnum role) {
        return userRealEstateRepository.findCountOfUserRole(userId, role);
    }

    @Override
    @Transactional
    public void deleteUserRealEstate(UserRealEstateDTO ureDTO) throws AppUserException, RealEstateException, UserRealEstateException {
        AppUser user = appUserService.getUser(ureDTO.getUsername());
        if(user == null) throw new AppUserException("User with given username does not exists!");

        RealEstate realEstate = realEstateService.getRealEstateById(ureDTO.getRealEstateId());
        if (realEstate == null) throw new RealEstateException("Real estate with given id does not exist.");

        UserRealEstate ure = userRealEstateRepository.findDuplicate(ureDTO.getUsername(), ureDTO.getRealEstateId());
        if(ure == null) throw new UserRealEstateException("User with given username does not relate with given real estate.");

        int countOfOwners = userRealEstateRepository.countOwnersOfEstate(ureDTO.getRealEstateId());
        if(countOfOwners == 1 && ure.getRole().equals(UserRoleEnum.OWNER))
            throw new UserRealEstateException(user.getUsername() + " is the last owner of this real estate therefore you cannot remove it.");

        userRealEstateRepository.deleteUserRealEstate(user.getId(), ureDTO.getRealEstateId());

        user = updateUserRole(user);
        appUserService.save(user);
    }

    @Override
    public List<String> findUsersRoleInRealEstates(String username, Page<RealEstate> realEstates) {
        List<String> roles = new ArrayList<>();
        for (RealEstate realEstate: realEstates) {
            roles.add(userRealEstateRepository.findRoleInRealEstate(username, realEstate.getId()));
        }
        return  roles;
    }

    @Override
    public List<String> getUsersFromByRealEstateName(String name) {
        return userRealEstateRepository.getUsersFromByRealEstateName(name);
    }
}
