package com.learn.shirol.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("order")
public class OrderController {

    @RequestMapping("save")
    //@RequiresRoles(value={"admin","user"}) //用来判断角色信息，同时具有
    //@RequiresPermissions("user:update:01") //判断权限字符串
    public String  save(){


        //代码的方式进行授权
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("admin")) {
            System.out.println("保存订单");
        }
        else{
            System.out.println("无权访问");
        }


        return "redirect:/index.jsp";
    }
}
