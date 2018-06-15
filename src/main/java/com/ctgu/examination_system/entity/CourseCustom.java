package com.ctgu.examination_system.entity;

/**
 * Course扩展类    课程
 */
public class CourseCustom extends Course {

    //所属院系名
    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "CourseCustom{" +
                "deptName='" + deptName + '\'' +
                '}';
    }
}
