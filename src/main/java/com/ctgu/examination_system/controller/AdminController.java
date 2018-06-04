package com.ctgu.examination_system.controller;

import java.util.List;

import com.ctgu.examination_system.entity.*;
import com.ctgu.examination_system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctgu.examination_system.service.DepartmentService;
import com.ctgu.examination_system.service.StudentService;
import com.ctgu.examination_system.service.UserService;
import sun.net.www.content.text.Generic;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private StudentService studentService;
	@Autowired 
	private DepartmentService departmentService;
	@Autowired
	private UserService userService;
	@Autowired
	private User user;
	@Autowired
	private Student student;

	@Autowired
    private Teacher teacher;

	@Autowired
    private TeacherService teacherService;
	@RequestMapping(value="/showStudent")
	public String showStudent(Model model,Integer page) {
		List<Student> list=null;
		PagingVO pagingVO=new PagingVO();
		pagingVO.setTotalCount(studentService.getCountStudent());
		if(page==null || page==0) {
			pagingVO.setToPageNo(1);
			list=studentService.findByPaging(1);
		}else {
			pagingVO.setToPageNo(page);
			list=studentService.findByPaging(page);
		}
		model.addAttribute("studentList", list);
		model.addAttribute("pagingVO", pagingVO);
		return "admin/showStudent";
	}
    @RequestMapping(value="/removeStudent",method=RequestMethod.GET)
    @ResponseBody
    public boolean removeStudent(@RequestParam("id")Integer id) {
    	student=studentService.findStudentById(id);
        if(studentService.deleteStudent(id)) {
        	if(student!=null) {
        		return userService.removeUser(student.getStudentId());
        	}
        }
        return false;
    }
    @RequestMapping(value="/enterEditStudent")
    public String enterEditStudent(@RequestParam("id")Integer id,Model model) {
    	student=studentService.findStudentById(id);
    	List<Department> departmentList=departmentService.findAll();
    	model.addAttribute("student", student);
    	model.addAttribute("departmentList", departmentList);
    	return "admin/editStudent";
    }
    @RequestMapping(value="/editStudent",method=RequestMethod.POST)
    public String editStudent(Student student) throws Exception {
    	studentService.editStudent(student);
    	return "redirect:/admin/showStudent";
    }

    @RequestMapping(value = "/addStudent",method = RequestMethod.GET)
    public String showAddStudent(Model model){
    	List<Department> departmentList=departmentService.findAll();
    	model.addAttribute("departmentList",departmentList);
        model.addAttribute("StudentId",studentService.getLargestStuId());
	    return "admin/addStudent";
    }

    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public String addStudent(Student student){
        if(studentService.addStudent(student)) {
        	user.setPassword("123456");
        	user.setRole((short) 2);
        	user.setUserid(student.getStudentId());
        	userService.addUser(user);
        	return "redirect:/admin/showStudent";
        }
        return "/failedPage";
    }

    @RequestMapping(value = "/selectStudent",method = RequestMethod.POST)
    public String searchStudent(@RequestParam("username") String username,Model model){
        List<Student>list=studentService.searchStudent(username);
        model.addAttribute("studentList", list);
        return "admin/showStudent";
    }

    @RequestMapping("/showTeacher")
    public String showTeacher(Model model, Integer page){
        List<Teacher> list=null;
        PagingVO pagingVO=new PagingVO();
        pagingVO.setTotalCount(teacherService.getCountTeacher());
        if(page==null || page==0) {
            pagingVO.setToPageNo(1);
            list=teacherService.findByPaging(1);
        }else {
            pagingVO.setToPageNo(page);
            list=teacherService.findByPaging(page);
        }
        model.addAttribute("teacherList", list);
        model.addAttribute("pagingVO", pagingVO);
        return "admin/showTeacher";
    }

    @RequestMapping(value="/removeTeacher",method=RequestMethod.GET)
    @ResponseBody
    public boolean removeTeacher(@RequestParam("id")Integer id) {
        teacher = teacherService.findTeacherById(id);
        if(teacherService.deleteTeacher(id)) {
            if(teacher != null) {
                return userService.removeUser(teacher.getTeacherId());
            }
        }
        return false;
    }
    @RequestMapping(value="/enterEditTeacher")
    public String enterEditTeacher(@RequestParam("id")Integer id,Model model) {
        teacher = teacherService.findTeacherById(id);
        List<Department> departmentList = departmentService.findAll();
        model.addAttribute("teacher", teacher);
        model.addAttribute("departmentList", departmentList);
        return "admin/editTeacher";
    }
    @RequestMapping(value="/editTeacher",method=RequestMethod.POST)
    public String editTeacher(Teacher teacher) throws Exception {
        teacherService.editTeacher(teacher);
        return "redirect:/admin/showTeacher";
    }

    @RequestMapping(value = "/addTeacher",method = RequestMethod.GET)
    public String showAddTeacher(Model model){
        List<Department> departmentList=departmentService.findAll();
        model.addAttribute("departmentList",departmentList);
        model.addAttribute("TeacherId",teacherService.getLargestTeaId());
        return "admin/addTeacher";
    }

    @RequestMapping(value = "/addTeacher",method = RequestMethod.POST)
    public String addTeacher(Teacher teacher){
        if(teacherService.addTeacher(teacher)) {
            user.setPassword("123456");
            user.setRole((short) 1);
            user.setUserid(teacher.getTeacherId());
            userService.addUser(user);
            return "redirect:/admin/showTeacher";
        }
        return "/failedPage";
    }

    @RequestMapping(value = "/selectTeacher",method = RequestMethod.POST)
    public String searchTeacher(@RequestParam("username") String username,Model model){
        List<Teacher> list = teacherService.searchTeacher(username);
        model.addAttribute("teacherList", list);
        return "admin/showTeacher";
    }
}
