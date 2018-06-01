package com.ctgu.examination_system.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class Student {
    private Integer id;

    private String studentId;

    private String username;

    private Short gendar;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date enterDate;

    private Integer departmentId;
    
    private Department department;
    
    public Integer getId() {
        return id;
    }

    public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Short getGendar() {
        return gendar;
    }

    public void setGendar(Short gendar) {
        this.gendar = gendar;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

	@Override
	public String toString() {
		return "Student [id=" + id + ", studentId=" + studentId + ", username=" + username + ", gendar=" + gendar
				+ ", birthDate=" + birthDate + ", enterDate=" + enterDate + ", departmentId=" + departmentId
				+ ", department=" + department + "]";
	}
    
}