package com.ctgu.examination_system.service.impl;

import java.util.List;

import com.ctgu.examination_system.entity.*;
import com.ctgu.examination_system.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctgu.examination_system.entity.CourseExample.Criteria;
import com.ctgu.examination_system.mapper.CourseMapper;
import com.ctgu.examination_system.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public int getCountCourse() {
		CourseExample courseExample=new CourseExample();
		Integer courseCount=courseMapper.countByExample(courseExample);
	return courseCount;
	}

	@Override
	public List<Course> findByPaging(int i) {
		PagingVO pagingVO = new PagingVO();
        pagingVO.setToPageNo(i);
        List<Course> list=courseMapper.findByPaging(pagingVO);
        if (list == null || list.size() == 0){
            return null;
        }
        return list;
	}

	@Override
	public boolean removeCourse(Integer id) {
		if(courseMapper.selectByPrimaryKey(id)!=null) {
			if(courseMapper.deleteByPrimaryKey(id)==1)
				return true;
		}
		return false;
	}

	@Override
	public Course findById(Integer id) {
		return courseMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean editCourse(Course course) {
		if(course!=null) {
			CourseExample courseExample=new CourseExample();
			return courseMapper.updateByExampleSelective(course, courseExample)==1;
		}
		return false;
	}

	@Override
	public boolean findByCourseId(Integer courseId) {
		CourseExample courseExample=new CourseExample();
		Criteria criteria=courseExample.createCriteria();
		criteria.andCourseIdEqualTo(courseId);
		List<Course>list=courseMapper.selectByExample(courseExample);
		if(list==null || list.size()==0)
			return true;
		return false;
	}

	@Override
	public boolean addCourse(Course course) {
		return courseMapper.insert(course)==1;
	}

	@Override
	public List<Course> searchCourse(String coursename) {
		CourseExample courseExample=new CourseExample();
		Criteria criteria=courseExample.createCriteria();
		criteria.andCourseNameLike("%"+coursename+"%");
		List<Course> list = courseMapper.selectByExample(courseExample);
		return list;
	}

    @Override
    public Integer getLargestCourId() {
        Integer id = courseMapper.getLargestCourId();
	    if (id == null || id == 0){
	        return 100001;
        }
        return id + 1;
    }

    @Override
    public List<CourseCustom> findByTeacherID(String teacherid) {
	    CourseExample example = new CourseExample();
	    Criteria criteria = example.createCriteria();
	    criteria.andTeacherIdEqualTo(teacherid);
        List<Course> courseList = courseMapper.selectByExample(example);
        List<CourseCustom> result = null;
        for (Course course : courseList){
            CourseCustom courseCustom = new CourseCustom();
            org.springframework.beans.BeanUtils.copyProperties(course,courseCustom);
            DepartmentExample deptExample = new DepartmentExample();
            DepartmentExample.Criteria criteria1 = deptExample.createCriteria();
            criteria1.andDepartmentIdEqualTo(course.getCourseId());
            courseCustom.setDeptName(departmentMapper.selectByExample(deptExample).get(0).getDepartmentName());
            result.add(courseCustom);
        }
        return result;
    }

}
