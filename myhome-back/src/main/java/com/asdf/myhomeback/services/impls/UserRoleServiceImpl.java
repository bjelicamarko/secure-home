package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.models.UserRole;
import com.asdf.myhomeback.repositories.UserRoleRepository;
import com.asdf.myhomeback.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole findByName(String roleName) {
        return userRoleRepository.findByName(roleName);
    }

}
