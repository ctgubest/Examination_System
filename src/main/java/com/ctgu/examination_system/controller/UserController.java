package com.ctgu.examination_system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctgu.examination_system.entity.User;
import com.ctgu.examination_system.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private User user;
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		
		return "login";
	}
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	public String volidLogin(@RequestParam("userId") String userId,@RequestParam("password")String password) {
		user=userService.selectUserById(userId, password);
		if (user == null) { //user等于空代表没有此用户
            return "redirect:/login";
        }
		if(user.getRole()==0) {
		    System.out.println("登录没问题");
			return "redirect:/admin/showStudent";//admin登陆
		}else if(user.getRole()==1) {
			return "redirect:/teacher/showCourse";//teacher登陆
		}else {
			return "redirect:/student/showCourse";//student登陆
		}
	}
}
