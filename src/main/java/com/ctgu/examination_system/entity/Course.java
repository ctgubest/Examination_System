package com.ctgu.examination_system.entity;

import org.springframework.stereotype.Repository;

@Repository
public class Course {
    private Integer id;

    private Integer courseId;

    private String courseName;

    private Integer teacherId;

    private String classroom;

    private Short courseWeek;

    private Short courseTime;

    private Float credit;

    private Integer departmentId;

    private String courseType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom == null ? null : classroom.trim();
    }

    public Short getCourseWeek() {
        return courseWeek;
    }

    public void setCourseWeek(Short courseWeek) {
        this.courseWeek = courseWeek;
    }

    public Short getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(Short courseTime) {
        this.courseTime = courseTime;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType == null ? null : courseType.trim();
    }
}