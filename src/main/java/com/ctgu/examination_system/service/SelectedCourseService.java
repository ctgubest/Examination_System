package com.ctgu.examination_system.service;

import com.ctgu.examination_system.entity.SelectedCourseCustom;

import java.util.List;

/**
 * 选课表servic层
 */
public interface SelectedCourseService {

    //根据课程ID查询课程
    List<SelectedCourseCustom> findByCourseID(Integer id) throws Exception;

    //根据课程id分页查询课程
    List<SelectedCourseCustom> findByCourseIDPaging(Integer page, Integer id) throws Exception;

    //获取该课程学生数
    Integer countByCourseID(Integer id) throws Exception;

    //查询指定学生成绩
    SelectedCourseCustom findOne(SelectedCourseCustom selectedCourseCustom) throws Exception;

    //打分
    void updataOne(SelectedCourseCustom selectedCourseCustom) throws Exception;

    //选课
    void save(SelectedCourseCustom selectedCourseCustom) throws Exception;

    //根据学生id查找已选课程
    List<SelectedCourseCustom> findSelectedCourseByStudentID(String StudentId) throws Exception;

    //根据学生id查找已修课程
    List<SelectedCourseCustom> findOveredCourseByStudentID(String StudentId) throws Exception;

    //退课
    void remove(SelectedCourseCustom selectedCourseCustom) throws Exception;

    //是否已经修完某个课
    int isOvered(String userId, int courseId);

}
