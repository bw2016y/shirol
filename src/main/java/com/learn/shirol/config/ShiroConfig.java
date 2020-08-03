package com.learn.shirol.config;


import com.learn.shirol.redis.RedisCacheManager;
import com.learn.shirol.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.omg.CORBA.CustomMarshal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //1.创建ShiroFilter，负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);


        //配置系统受限资源
        Map<String,String> map=new HashMap<>();
        map.put("/user/login","anon"); //anon 设置为公共资源
        map.put("/user/regis","anon"); //用户注册的请求路径
        map.put("/regis.jsp","anon"); //用户注册的页面
        map.put("/index.jsp","authc");  // authc代表请求这个资源需要认证和授权 除了login以外的资源都要认证和授权
        map.put("/user/getImage","anon");  //验证码

        //配置系统公共资源
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);




        return shiroFilterFactoryBean;
    }

    //2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDeafultWebSecurityManager(@Qualifier("getRealm") Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    //3.创建自定义的realm
    @Bean
    public Realm getRealm(){
        CustomerRealm customerRealm=new CustomerRealm();
        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();

        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);

        customerRealm.setCredentialsMatcher(credentialsMatcher);

        //开启缓存管理,本地缓存
        //设置缓存管理器
        //customerRealm.setCacheManager(new EhCacheManager());
        customerRealm.setCacheManager(new RedisCacheManager());
        //开启全局缓存
        customerRealm.setCachingEnabled(true);
        //开启认证缓存
        customerRealm.setAuthenticationCachingEnabled(true);
        //开启授权缓存
        customerRealm.setAuthorizationCachingEnabled(true);

        //设置缓存的名称
        customerRealm.setAuthenticationCacheName("cusAuthenticate");
        customerRealm.setAuthorizationCacheName("cusAuthorization");

        return customerRealm;
    }

}
