package com.ctgu.examination_system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.ctgu.examination_system.entity.User;
import com.ctgu.examination_system.entity.UserExample;
import com.ctgu.examination_system.entity.UserExample.Criteria;
import com.ctgu.examination_system.mapper.UserMapper;
import com.ctgu.examination_system.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

    @Override
    public User Login(String userId, String password, HttpServletRequest request) {
        if(userId != null || userId.length() > 0) {
            User user = selectUserById(userId);
            if (user != null){
                if (password.equals(user.getPassword())){
                    request.getSession().setAttribute("user",user);
                    return user;
                }
            }
        }
        return null;
    }

    @Override
	public User selectUserById(String userId) {
        UserExample userExample=new UserExample();
        Criteria criteria=userExample.createCriteria();
        criteria.andUseridEqualTo(userId);
        List<User> list=userMapper.selectByExample(userExample);
        if(list != null) {
            return list.get(0);
        }
		return null;
	}
	@Override
	public boolean addUser(User user) {
		return userMapper.insert(user)==1;
	}
	@Override
	public boolean removeUser(String studentId) {
		UserExample userExample=new UserExample();
		Criteria criteria=userExample.createCriteria();
		criteria.andUseridEqualTo(studentId);
		return userMapper.deleteByExample(userExample)==1;
	}

    @Override
    public boolean updateUser(User user) {
	    UserExample example = new UserExample();
	    Criteria criteria = example.createCriteria();
	    criteria.andUseridEqualTo(user.getUserid());
        return userMapper.updateByExampleSelective(user, example) == 1;
    }

}
