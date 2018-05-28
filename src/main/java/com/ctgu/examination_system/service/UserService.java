package com.ctgu.examination_system.service;

import com.ctgu.examination_system.entity.User;

public interface UserService {
	
	User selectUserById(String userId,String password);
}
