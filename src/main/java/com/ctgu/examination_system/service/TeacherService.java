package com.ctgu.examination_system.service;

import com.ctgu.examination_system.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * teacher接口
 * Created by 汪俊 on 2018/6/4.
 */
public interface TeacherService {
    Integer getCountTeacher();

    List<Teacher> findAll();

    List<Teacher> findByPaging(int i);


    boolean deleteTeacher(Integer id);


    Teacher findTeacherById(Integer id);


    boolean editTeacher(Teacher teacher);

    boolean addTeacher(Teacher teacher);

    List<Teacher> searchTeacher(String username);

    Integer getLargestTeaId();
}
