package com.ctgu.examination_system.service;

import java.util.List;

import com.ctgu.examination_system.entity.Course;
import com.ctgu.examination_system.entity.CourseCustom;

public interface CourseService {

	int getCountCourse(String coursename);

    int getMyCountCourse(String teacherId, String coursename);

	List<CourseCustom> findByPaging(int i);

	boolean removeCourse(Integer id);

	Course findById(Integer id);

	boolean editCourse(Course course);

	boolean findByCourseId(Integer courseId);

	boolean addCourse(Course course);

    List<CourseCustom> searchCourseByPage(String coursename, int i);

    List<CourseCustom> searchMyCourseByPage(String teacherId, String coursename, int i);

    Integer getLargestCourId();

    //根据教师id查询课程
    List<CourseCustom> findByTeacherID(String userid);
}
