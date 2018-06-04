package com.ctgu.examination_system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctgu.examination_system.entity.Teacher;
import com.ctgu.examination_system.entity.TeacherExample;
import com.ctgu.examination_system.mapper.TeacherMapper;
import com.ctgu.examination_system.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;
	@Override
	public List<Teacher> findAll() {
		TeacherExample teacherExample=new TeacherExample();
		List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
		if(teachers.size()>0)
			return teachers;
		return null;
	}

}
