package com.ctgu.examination_system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctgu.examination_system.entity.Department;
import com.ctgu.examination_system.entity.DepartmentExample;
import com.ctgu.examination_system.mapper.DepartmentMapper;
import com.ctgu.examination_system.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentMapper departmentMapper;
	@Override
	public List<Department> findAll() {
		DepartmentExample dExample=new DepartmentExample();
		List<Department> departments=departmentMapper.selectByExample(dExample);
		if(departments.size()>0)
			return departments;
		return null;
	}

}
