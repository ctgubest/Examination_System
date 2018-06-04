package com.ctgu.examination_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctgu.examination_system.entity.Department;
import com.ctgu.examination_system.entity.PagingVO;
import com.ctgu.examination_system.entity.Student;
import com.ctgu.examination_system.entity.User;
import com.ctgu.examination_system.service.DepartmentService;
import com.ctgu.examination_system.service.StudentService;
import com.ctgu.examination_system.service.UserService;

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
}
