package com.ctgu.examination_system.service.impl;

import com.ctgu.examination_system.entity.*;
import com.ctgu.examination_system.mapper.DepartmentMapper;
import com.ctgu.examination_system.mapper.TeacherMapper;
import com.ctgu.examination_system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by 汪俊 on 2018/6/4.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public Integer getCountTeacher() {
        TeacherExample example=new TeacherExample();
        Integer teacherCount=teacherMapper.countByExample(example);
        return teacherCount;
    }

    @Override
    public List<Teacher> findByPaging(int i) {
        PagingVO pagingVO = new PagingVO();
        pagingVO.setToPageNo(i);
        List<Teacher> list = teacherMapper.findByPaging(pagingVO);
        list = setDept(list);
        return list;
    }

    @Override
    public boolean deleteTeacher(Integer id) {
        if(id != null || id > 0) {
            return teacherMapper.deleteByPrimaryKey(id) == 1;
        }
        return false;
    }

    @Override
    public Teacher findTeacherById(Integer id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean editTeacher(Teacher teacher) {
        if(teacher != null) {
            TeacherExample example = new TeacherExample();
            TeacherExample.Criteria criteria = example.createCriteria();
            criteria.andTeacherIdEqualTo(teacher.getTeacherId());
            return teacherMapper.updateByExampleSelective(teacher, example) == 1;
        }
        return false;
    }

    @Override
    public boolean addTeacher(Teacher teacher) {
        return teacherMapper.insert(teacher) == 1;
    }

    @Override
    public List<Teacher> searchTeacher(String username) {
        TeacherExample example = new TeacherExample();
        TeacherExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameLike("%"+username+"%");
        List<Teacher> list = teacherMapper.selectByExample(example);
        list = setDept(list);
        return list;
    }

    @Override
    public Integer getLargestTeaId() {
        Integer id = teacherMapper.getLargestTeaId();
        if (id == null || id == 0){
            return 1001;
        }
        return id + 1;
    }
  
  	@Override
	public List<Teacher> findAll() {
		TeacherExample teacherExample=new TeacherExample();
		List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
		if(teachers.size()>0)
			return teachers;
		return null;
	}

    private List<Teacher> setDept(List<Teacher> list){
        for (Teacher teacher : list){
            DepartmentExample deptExample = new DepartmentExample();
            DepartmentExample.Criteria deptCriteria = deptExample.createCriteria();
            deptCriteria.andDepartmentIdEqualTo(teacher.getDepartmentId());
            List<Department> deptList = departmentMapper.selectByExample(deptExample);
            if (deptList != null && deptList.size() > 0)
                teacher.setDepartment(deptList.get(0));
        }
        return list;
    }
}
