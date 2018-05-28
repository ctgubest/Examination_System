package com.ctgu.examination_system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctgu.examination_system.entity.User;
import com.ctgu.examination_system.entity.UserExample;
import com.ctgu.examination_system.entity.UserExample.Criteria;
import com.ctgu.examination_system.mapper.UserMapper;
import com.ctgu.examination_system.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Override
	public User selectUserById(String userId, String password) {
		if(userId != null || userId.length() > 0) {
			UserExample userExample=new UserExample();
			Criteria criteria=userExample.createCriteria();
			criteria.andUseridEqualTo(userId);
			criteria.andPasswordEqualTo(password);
			List<User> list=userMapper.selectByExample(userExample);
			if(list != null) {
				return list.get(0);
			}
		}
		return null;
	}

}
