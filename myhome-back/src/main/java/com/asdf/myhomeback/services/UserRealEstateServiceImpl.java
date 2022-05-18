package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.UserRealEstateDTO;
import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.models.RealEstate;
import com.asdf.myhomeback.models.UserRealEstate;
import com.asdf.myhomeback.models.UserRole;
import com.asdf.myhomeback.models.enums.UserRoleEnum;
import com.asdf.myhomeback.repositories.UserRealEstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
                .findDuplicate(userRealEstateDTO.getUserId(), userRealEstateDTO.getRealEstateId());
        if (userRealEstateDuplicate != null) throw new Exception("(User, Real estate) tuple already exist.");

        AppUser user = appUserService.getUser(userRealEstateDTO.getUserId());
        if (user == null) throw new Exception("User with given id doesnt exist.");

        RealEstate realEstate = realEstateService.getRealEstateById(userRealEstateDTO.getRealEstateId());
        if (realEstate == null) throw new Exception("Real estate with given id doesnt exist.");

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
    public void changeRoleInUserRealEstate(UserRealEstateDTO realEstateDTO) {
        UserRoleEnum newRole = UserRoleEnum.valueOf(realEstateDTO.getRole());
        userRealEstateRepository.updateRole(realEstateDTO.getUserId(), realEstateDTO.getRealEstateId(), newRole);
    }
}
