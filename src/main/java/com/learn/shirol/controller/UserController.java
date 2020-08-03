package com.learn.shirol.controller;


import com.learn.shirol.entity.User;
import com.learn.shirol.service.UserService;
import com.learn.shirol.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("regis")
    public String  regis(User user){
        try {
            userService.regis(user);
            return "redirect:/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/regis.jsp";
        }
    }

    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }




    @RequestMapping("login")
    public String login(String username,String password,String code,HttpSession session){
        //比较验证码
        String  scode = (String) session.getAttribute("code");
        System.out.println(scode);
        System.out.println(code);
        try {
            if(code.equalsIgnoreCase(scode)){
                //获取主体对象
                Subject subject= SecurityUtils.getSubject();
                subject.login(new UsernamePasswordToken(username,password));
                return "redirect:/index.jsp";

            }else{
                throw  new RuntimeException("code error");
            }
        }  catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("username error");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("password error");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("code error");
        }

        return "redirect:/login.jsp";
    }

    /**
     * 验证码方法
     */
    @RequestMapping("getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {


        //生成验证码 图片
        Map<String, Object> map = VerifyCodeUtils.genCAP();

        //验证码放入Session
        session.setAttribute("code",map.get("code").toString());

        //禁止图像缓存
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",-1);

        response.setContentType("image/jpeg");

        //验证码存入图片
        ServletOutputStream os=response.getOutputStream();
        ImageIO.write((RenderedImage) map.get("pic"),"jpeg",os);
        os.close();


    }

}
