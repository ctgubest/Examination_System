package com.ctgu.examination_system.service.impl;

import com.ctgu.examination_system.entity.*;
import com.ctgu.examination_system.mapper.CourseMapper;
import com.ctgu.examination_system.mapper.DepartmentMapper;
import com.ctgu.examination_system.mapper.SelectedcourseMapper;
import com.ctgu.examination_system.mapper.StudentMapper;
import com.ctgu.examination_system.service.DepartmentService;
import com.ctgu.examination_system.service.SelectedCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现类
 * Created by 汪俊 on 2018/6/11.
 */
@Service
public class SelectedCourseServiceImpl implements SelectedCourseService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SelectedcourseMapper selectedcourseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<SelectedCourseCustom> findByCourseID(Integer id) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(id);

        List<Selectedcourse> list = selectedcourseMapper.selectByExample(example);
        List<SelectedCourseCustom> secList = new ArrayList<SelectedCourseCustom>();
        for (Selectedcourse s: list) {
            SelectedCourseCustom sec = new SelectedCourseCustom();
            BeanUtils.copyProperties(s, sec);
            //判断是否完成类该课程
            if (sec.getScore() != null) {
                sec.setOver(true);
            }
            sec.setStudent(findStudentBtStuId(sec.getStudentId()));
            secList.add(sec);
        }

        return secList;
    }

    @Override
    public List<SelectedCourseCustom> findByCourseIDPaging(Integer page, Integer id) throws Exception {
        return null;
    }

    //获取该课程学生数
    @Override
    public Integer countByCourseID(Integer id) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(id);
        return selectedcourseMapper.countByExample(example);
    }

    @Override
    public SelectedCourseCustom findOne(SelectedCourseCustom selectedCourseCustom) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(selectedCourseCustom.getCourseId());
        criteria.andStudentIdEqualTo(selectedCourseCustom.getStudentId());
        List<Selectedcourse> list = selectedcourseMapper.selectByExample(example);

        if (list != null && list.size() > 0) {
            SelectedCourseCustom sc = new SelectedCourseCustom();
            BeanUtils.copyProperties(list.get(0), sc);
            sc.setStudent(findStudentBtStuId(sc.getStudentId()));
            return sc;
        }
        return null;
    }

    @Override
    public void updataOne(SelectedCourseCustom selectedCourseCustom) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(selectedCourseCustom.getCourseId());
        criteria.andStudentIdEqualTo(selectedCourseCustom.getStudentId());
        selectedcourseMapper.updateByExampleSelective(selectedCourseCustom, example);

    }

    @Override
    public void save(SelectedCourseCustom selectedCourseCustom) throws Exception {
        selectedcourseMapper.insertSelective(selectedCourseCustom);
    }

    //已选课程
    @Override
    public List<SelectedCourseCustom> findSelectedCourseByStudentID(String StudentId) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(StudentId);
        List<Selectedcourse> list = selectedcourseMapper.selectByExample(example);
        List<SelectedCourseCustom> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SelectedCourseCustom sc = new SelectedCourseCustom();
                BeanUtils.copyProperties(list.get(i), sc);
                sc.setStudent(findStudentBtStuId(sc.getStudentId()));

                CourseExample courseExample = new CourseExample();
                CourseExample.Criteria criteria1 = courseExample.createCriteria();
                criteria1.andCourseIdEqualTo(sc.getCourseId());
                List<Course> courseList = courseMapper.selectByExample(courseExample);
                Course course = courseList.get(0);
                CourseCustom courseCustom = new CourseCustom();
                //类拷贝
                BeanUtils.copyProperties(course, courseCustom);
                courseCustom.setDeptName(getDeptName(course.getDepartmentId()));
                sc.setCourseCustom(courseCustom);
                result.add(sc);
            }
        }
        return result;
    }

    //已修课程
    @Override
    public List<SelectedCourseCustom> findOveredCourseByStudentID(String StudentId) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(StudentId);
        criteria.andScoreIsNotNull();
        List<Selectedcourse> list = selectedcourseMapper.selectByExample(example);
        List<SelectedCourseCustom> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SelectedCourseCustom sc = new SelectedCourseCustom();
                //类拷贝
                BeanUtils.copyProperties(list.get(i), sc);
                sc.setStudent(findStudentBtStuId(sc.getStudentId()));

                CourseExample courseExample = new CourseExample();
                CourseExample.Criteria criteria1 = courseExample.createCriteria();
                criteria1.andCourseIdEqualTo(sc.getCourseId());
                List<Course> courseList = courseMapper.selectByExample(courseExample);
                Course course = courseList.get(0);
                CourseCustom courseCustom = new CourseCustom();
                //类拷贝
                BeanUtils.copyProperties(course, courseCustom);
                courseCustom.setDeptName(getDeptName(course.getDepartmentId()));
                sc.setCourseCustom(courseCustom);
                sc.setOver(true);
                result.add(sc);
            }
        }
        return result;
    }

    @Override
    public void remove(SelectedCourseCustom selectedCourseCustom) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();

        criteria.andCourseIdEqualTo(selectedCourseCustom.getCourseId());
        criteria.andStudentIdEqualTo(selectedCourseCustom.getStudentId());

        selectedcourseMapper.deleteByExample(example);
    }

    //根据学生id查找学生
    private Student findStudentBtStuId(String studentId){
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria1 = studentExample.createCriteria();
        criteria1.andStudentIdEqualTo(studentId);
        Student student = studentMapper.selectByExample(studentExample).get(0);
        return student;
    }

    //根据部门id查找部门名称
    private String getDeptName(int deptId){
        DepartmentExample departmentExample = new DepartmentExample();
        DepartmentExample.Criteria criteria = departmentExample.createCriteria();
        criteria.andDepartmentIdEqualTo(deptId);
        List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
        Department department = departmentList.get(0);
        return department.getDepartmentName();
    }
}
