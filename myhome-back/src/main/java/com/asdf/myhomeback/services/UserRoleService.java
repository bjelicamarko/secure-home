package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.UserRole;

public interface UserRoleService {

    UserRole findByName(String roleName);

}
