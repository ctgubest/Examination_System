package com.ctgu.examination_system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctgu.examination_system.entity.Department;
import com.ctgu.examination_system.entity.DepartmentExample;
import com.ctgu.examination_system.entity.PagingVO;
import com.ctgu.examination_system.entity.Student;
import com.ctgu.examination_system.entity.StudentExample;
import com.ctgu.examination_system.mapper.DepartmentMapper;
import com.ctgu.examination_system.mapper.StudentMapper;
import com.ctgu.examination_system.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Override
	public Integer getCountStudent() {
			StudentExample studentExample=new StudentExample();
			Integer studentCount=studentMapper.countByExample(studentExample);
		return studentCount;
	}
	@Override
	public List<Student> finddByPaging(int i) {
		PagingVO pagingVO = new PagingVO();
        pagingVO.setToPageNo(i);
        List<Student> list=studentMapper.findByPaging(pagingVO);
        for(int index=0;index<list.size();index++) {
        	DepartmentExample dExample=new DepartmentExample();
        	com.ctgu.examination_system.entity.DepartmentExample.Criteria criteria=dExample.createCriteria();
        	criteria.andDepartmentIdEqualTo(list.get(index).getDepartmentId());
        	List<Department> lists=departmentMapper.selectByExample(dExample);
        	list.get(index).setDepartment(lists.get(0));
        }
        return list;
	}
	@Override
	public boolean deleteStudent(Integer id) {
		boolean flag=false;
		if(id!=null || id>0) {
			int res=studentMapper.deleteByPrimaryKey(id);
			if(res==1)
				flag=true;
		}
		return false;
	}

}
