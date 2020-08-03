package com.learn.shirol.service;

import com.learn.shirol.dao.UserDAO;
import com.learn.shirol.entity.Perm;
import com.learn.shirol.entity.Role;
import com.learn.shirol.entity.User;
import com.learn.shirol.utils.SaltUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("userServ")
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDAO userDAO;
    @Override
    public void regis(User user) {
        //处理业务调用
        //明文密码 md5 salt hash散列

        //1. 生成随机盐
        String salt = SaltUtil.getSalt(10);
        //2. 将随机盐保存到数据库
        user.setSalt(salt);
        //3. 明文密码 md5 salt hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
        //4. 用散列代替明文
        user.setPassword(md5Hash.toHex());


        userDAO.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userDAO.findByUserName(username);
    }

    @Override
    public User findRolesByUserName(String username) {
        return userDAO.findRolesByUserName(username);
    }

    @Override
    public List<Perm> findPermsByRoleId(String id) {
        return userDAO.findPermsByRoleId(id);
    }

}
