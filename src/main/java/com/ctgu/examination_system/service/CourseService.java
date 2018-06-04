package com.ctgu.examination_system.service;

import java.util.List;

import com.ctgu.examination_system.entity.Course;

public interface CourseService {

	int getCountCourse();

	List<Course> findByPaging(int i);

	boolean removeCourse(Integer id);

	Course findById(Integer id);

	boolean editCourse(Course course);

	boolean findByCourseId(Integer courseId);

	boolean addCourse(Course course);

	List<Course> searchCourse(String username);

    Integer getLargestCourId();
}
