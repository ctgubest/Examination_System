package com.ctgu.examination_system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ctgu.examination_system.entity.*;
import com.ctgu.examination_system.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctgu.examination_system.entity.CourseExample.Criteria;
import com.ctgu.examination_system.mapper.CourseMapper;
import com.ctgu.examination_system.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public int getCountCourse(String coursename) {
		CourseExample courseExample=new CourseExample();
		Criteria criteria = courseExample.createCriteria();
        criteria.andCourseNameLike("%"+coursename+"%");
		Integer courseCount=courseMapper.countByExample(courseExample);
	    return courseCount;
	}

    @Override
    public int getMyCountCourse(String teacherId, String coursename) {
        CourseExample courseExample=new CourseExample();
        Criteria criteria = courseExample.createCriteria();
        criteria.andCourseNameLike("%"+coursename+"%");
        criteria.andTeacherIdEqualTo(teacherId);
        Integer courseCount=courseMapper.countByExample(courseExample);
        return courseCount;
    }

    @Override
	public List<CourseCustom> findByPaging(int i) {
		PagingVO pagingVO = new PagingVO();
        pagingVO.setToPageNo(i);
        List<Course> list=courseMapper.findByPaging(pagingVO);
        List<CourseCustom> result = getCurseCustom(list);
        return result;
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
		if(course != null) {
			CourseExample courseExample=new CourseExample();
			Criteria criteria = courseExample.createCriteria();
			criteria.andCourseIdEqualTo(course.getCourseId());
			return courseMapper.updateByExampleSelective(course, courseExample) == 1;
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
    public List<CourseCustom> searchCourseByPage(String coursename, int i) {
        PagingVO pagingVO = new PagingVO();
        pagingVO.setToPageNo(i);
        pagingVO.setKeyWord(coursename);
        List<Course> list = courseMapper.findByPaging(pagingVO);
        List<CourseCustom> result = getCurseCustom(list);
        logger.info("页码：{}",pagingVO.getTopageNo());
        logger.info("课程名：{}",pagingVO.getKeyWord());
        logger.info("页数：{}",result.size());
        return result;
    }

    @Override
    public List<CourseCustom> searchMyCourseByPage(String teacherId, String coursename, int i) {
        PagingVO pagingVO = new PagingVO();
        pagingVO.setToPageNo(i);
        pagingVO.setKeyWord(coursename);
        pagingVO.setUserId(teacherId);
        List<Course> list = courseMapper.findByPaging(pagingVO);
        List<CourseCustom> result = getCurseCustom(list);
        return result;
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
        List<CourseCustom> result = new ArrayList<>();
        for (Course course : courseList){
            CourseCustom courseCustom = new CourseCustom();
            org.springframework.beans.BeanUtils.copyProperties(course,courseCustom);
            DepartmentExample deptExample = new DepartmentExample();
            DepartmentExample.Criteria criteria1 = deptExample.createCriteria();
            criteria1.andDepartmentIdEqualTo(course.getDepartmentId());
            List<Department> departmentList = departmentMapper.selectByExample(deptExample);
            if (departmentList != null && departmentList.size() > 0) {
                courseCustom.setDeptName(departmentList.get(0).getDepartmentName());
            }
            result.add(courseCustom);
        }
        System.out.println(result.get(0).getCourseName());
        return result;
    }

    private List<CourseCustom> getCurseCustom(List<Course> list){
        List<CourseCustom> result = new ArrayList<>();
        if (list != null || list.size() > 0){
            for (Course course : list){
                CourseCustom cc = new CourseCustom();
                BeanUtils.copyProperties(course, cc);
                DepartmentExample example = new DepartmentExample();
                DepartmentExample.Criteria criteria = example.createCriteria();
                criteria.andDepartmentIdEqualTo(course.getDepartmentId());
                cc.setDeptName(departmentMapper.selectByExample(example).get(0).getDepartmentName());
                result.add(cc);
            }
        }
        return result;
    }

}
