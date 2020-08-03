package com.learn.shirol.shiro.realms;

import com.learn.shirol.entity.Perm;
import com.learn.shirol.entity.Role;
import com.learn.shirol.entity.User;
import com.learn.shirol.salt.MyByteSource;
import com.learn.shirol.service.UserService;
import com.learn.shirol.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class CustomerRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取主身份信息
        String  primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("调用授权验证"+primaryPrincipal);
        //根据主身份信息获取角色和权限信息

        UserService bean = (UserService) ApplicationContextUtils.getBean("userServ");

        User user = bean.findRolesByUserName(primaryPrincipal);

        //授权角色信息
        if(!CollectionUtils.isEmpty(user.getRoles())){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach((role)->{
                simpleAuthorizationInfo.addRole(role.getName());
                List<Perm> perms = bean.findPermsByRoleId(role.getId());
                if(!CollectionUtils.isEmpty(perms)){
                    perms.forEach((perm -> {
                        simpleAuthorizationInfo.addStringPermission(perm.getName());
                    }));
                }
            });
            return simpleAuthorizationInfo;
        }

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("=====================");
        
        String  principal = (String) authenticationToken.getPrincipal();
        
        
        //在工厂中获取Service对象
        UserService bean = (UserService) ApplicationContextUtils.getBean("userServ");
        System.out.println(bean);

        User user = bean.findByUserName(principal);


        if(!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),new MyByteSource(user.getSalt()),this.getName());
        }

        return null;
    }
}
