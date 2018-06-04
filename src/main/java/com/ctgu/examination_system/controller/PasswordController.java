package com.ctgu.examination_system.controller;

import com.ctgu.examination_system.entity.User;
import com.ctgu.examination_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * Created by 汪俊 on 2018/6/4.
 */
@Controller
public class PasswordController {
    @Autowired
    private UserService userService;
    @RequestMapping("/passwordRest")
    public String ResetPassword(HttpServletRequest request, String oldPassword, String newPassword){
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user == null);
        if (user == null){
            return "redirect:/login";
        }
        try {
            if (oldPassword.equals(user.getPassword())){
                user.setPassword(newPassword);
                userService.updateUser(user);
            } else {
                System.out.println("旧密码不正确！");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/logout";
    }
}
