package com.learn.shirol.service;

import com.learn.shirol.entity.Perm;
import com.learn.shirol.entity.Role;
import com.learn.shirol.entity.User;

import java.util.List;

public interface UserService {
    void regis(User user);
    User findByUserName(String username);
    User findRolesByUserName(String username);
    List<Perm> findPermsByRoleId(String id);

}
