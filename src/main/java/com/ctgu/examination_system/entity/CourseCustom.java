package com.ctgu.examination_system.entity;

/**
 * Course��չ��    �γ�
 */
public class CourseCustom extends Course {

    //����Ժϵ��
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
