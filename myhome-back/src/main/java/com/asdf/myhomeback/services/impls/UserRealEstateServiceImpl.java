package com.asdf.myhomeback.services.impls;

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
import com.asdf.myhomeback.services.AppUserService;
import com.asdf.myhomeback.services.RealEstateService;
import com.asdf.myhomeback.services.UserRealEstateService;
import com.asdf.myhomeback.services.UserRoleService;
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
    public UserRealEstate saveUserRealEstate(UserRealEstateDTO userRealEstateDTO) throws Exception {
        UserRoleEnum role = UserRoleEnum.valueOf(userRealEstateDTO.getRole());
        UserRealEstate userRealEstateDuplicate = userRealEstateRepository
                .findDuplicate(userRealEstateDTO.getUsername(), userRealEstateDTO.getRealEstateId());
        if (userRealEstateDuplicate != null) throw new Exception("(User, Real estate) tuple already exist.");

        AppUser user = appUserService.findByUsernameVerifiedUnlocked(userRealEstateDTO.getUsername());
        if (user == null) throw new AppUserException(String.format("User with username '%s' doesnt exist | Unverified/locked/deleted user.", user.getUsername()));

        RealEstate realEstate = realEstateService.getRealEstateById(userRealEstateDTO.getRealEstateId());
        if (realEstate == null) throw new RealEstateException(String.format("Real estate with name '%s' doesnt exist.", realEstate.getName()));

        int countOfOwners = userRealEstateRepository.countOwnersOfEstate(userRealEstateDTO.getRealEstateId());
        if(countOfOwners == 0 && !userRealEstateDTO.getRole().equals("OWNER"))
            throw new UserRealEstateException(String.format("Real estate '%s' does not have owner. In order to assign tenant to it, assign owner first.", realEstate.getName()));

        UserRealEstate userRealEstate = new UserRealEstate(user, realEstate, role);
        UserRealEstate ret = userRealEstateRepository.save(userRealEstate);

        changeUserRoles(user, role);

        return ret;
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
    public UserRealEstate changeRoleInUserRealEstate(UserRealEstateDTO userRealEstateDTO) throws AppUserException, RealEstateException, UserRealEstateException {
        UserRoleEnum newRole = UserRoleEnum.valueOf(userRealEstateDTO.getRole());

        AppUser user = appUserService.findByUsernameVerifiedUnlocked(userRealEstateDTO.getUsername());
        if (user == null) throw new AppUserException(String.format("User with username '%s' doesnt exist | Unverified/locked/deleted user.", user.getUsername()));

        RealEstate realEstate = realEstateService.getRealEstateById(userRealEstateDTO.getRealEstateId());
        if (realEstate == null) throw new RealEstateException(String.format("Real estate with name '%s' doesnt exist.", realEstate.getName()));

        UserRealEstate ure = userRealEstateRepository.findDuplicate(userRealEstateDTO.getUsername(), userRealEstateDTO.getRealEstateId());
        if(ure == null) {
            String ex = String.format("User with username '%s' does not relate with '%s' real estate.", user.getUsername(), realEstate.getName());
            throw new UserRealEstateException(ex);
        }

        int countOfOwners = userRealEstateRepository.countOwnersOfEstate(userRealEstateDTO.getRealEstateId());
        if(countOfOwners == 1 && !userRealEstateDTO.getRole().equals("OWNER"))
            throw new UserRealEstateException(user.getUsername() + " is the last owner of this real estate therefore you cannot change it to tenant.");

        UserRealEstate userRealEstate = userRealEstateRepository.findDuplicate(userRealEstateDTO.getUsername(), userRealEstateDTO.getRealEstateId());
        userRealEstate.setRole(newRole);
        UserRealEstate ret = userRealEstateRepository.save(userRealEstate);

        user = updateUserRole(user);
        appUserService.save(user);

        return ret;
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
        AppUser user = appUserService.findByUsernameVerifiedUnlocked(ureDTO.getUsername());
        if (user == null) throw new AppUserException(String.format("User with username '%s' doesnt exist | Unverified/locked/deleted user.", user.getUsername()));

        RealEstate realEstate = realEstateService.getRealEstateById(ureDTO.getRealEstateId());
        if (realEstate == null) throw new RealEstateException(String.format("Real estate with name '%s' doesnt exist.", realEstate.getName()));

        UserRealEstate ure = userRealEstateRepository.findDuplicate(ureDTO.getUsername(), ureDTO.getRealEstateId());
        if(ure == null) {
            String ex = String.format("User with username '%s' does not relate with '%s' real estate.", user.getUsername(), realEstate.getName());
            throw new UserRealEstateException(ex);
        }

        int countOfOwners = userRealEstateRepository.countOwnersOfEstate(ureDTO.getRealEstateId());
        if(countOfOwners == 1 && ure.getRole().equals(UserRoleEnum.OWNER))
            throw new UserRealEstateException(String.format("'%s' is the last owner of real estate '%s' therefore you cannot remove it.", user.getUsername(), realEstate.getName()));

        userRealEstateRepository.deleteUserRealEstate(user.getId(), ureDTO.getRealEstateId());

        user = updateUserRole(user);
        appUserService.save(user);
    }

    @Override
    public List<String> findUsersRoleInRealEstates(String username, Page<RealEstate> realEstates) {
        List<String> roles = new ArrayList<>();
        for (RealEstate realEstate: realEstates) {
            roles.add(userRealEstateRepository.findRoleInRealEstateById(username, realEstate.getId()));
        }
        return  roles;
    }

    @Override
    public List<String> getUsersFromByRealEstateName(String username, String name) {
        String role = findRoleInRealEstateByName(username, name);
        List<String> household = new ArrayList<>();

        if(role.equals("OWNER"))
            household = userRealEstateRepository.getUsersFromByRealEstateName(name);

        return household;
    }

    @Override
    public boolean isUserInRealEstate(String username, String name) {
        UserRealEstate userRealEstate = userRealEstateRepository.checkUsernameInRealEstate(username, name);
        return userRealEstate == null;
    }

    @Override
    public String findRoleInRealEstateByName(String username, String name) {
        return userRealEstateRepository.findRoleInRealEstateByName(username, name);
    }

    @Override
    public List<AppUser> getUsersFromRealEstate(String realEstateName) {
        return userRealEstateRepository.getUsersFromRealEstate(realEstateName);
    }
}
