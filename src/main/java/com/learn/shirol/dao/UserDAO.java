package com.learn.shirol.dao;

import com.learn.shirol.entity.Perm;
import com.learn.shirol.entity.Role;
import com.learn.shirol.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserDAO {
    void save(User user);
    User findByUserName(String username);
    User findRolesByUserName(String username);

    //根据角色id查询权限集合
    List<Perm> findPermsByRoleId(String id);
}
