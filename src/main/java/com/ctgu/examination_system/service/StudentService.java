package com.ctgu.examination_system.service;

import java.util.List;

import com.ctgu.examination_system.entity.Student;

public interface StudentService {
	
	Integer getCountStudent(String username);


	List<Student> findByPaging(int i);


	boolean deleteStudent(Integer id);


	Student findStudentById(Integer id);


	boolean editStudent(Student student);

	boolean addStudent(Student student);

    List<Student> searchStudentByPage(String username, int i);

	boolean findStudentByStudentId(String studentId);

    Integer getLargestStuId();
}
