package com.ctgu.examination_system.service;

import com.ctgu.examination_system.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User Login(String userId, String password, HttpServletRequest request);

    User selectUserById(String userId);

    boolean addUser(User user);

	boolean removeUser(String studentId);

	boolean updateUser(User user);
}
