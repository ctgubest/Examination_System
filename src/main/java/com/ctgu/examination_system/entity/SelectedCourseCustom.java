package com.ctgu.examination_system.entity;

/**
 * 已选课程扩展类
 * Created by 汪俊 on 2018/6/11.
 */
public class SelectedCourseCustom extends Selectedcourse{
    private Student student;

    private CourseCustom courseCustom;

    private Boolean over = false;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CourseCustom getCourseCustom() {
        return courseCustom;
    }

    public void setCourseCustom(CourseCustom courseCustom) {
        this.courseCustom = courseCustom;
    }

    public Boolean getOver() {
        return over;
    }

    public void setOver(Boolean over) {
        this.over = over;
    }
}
