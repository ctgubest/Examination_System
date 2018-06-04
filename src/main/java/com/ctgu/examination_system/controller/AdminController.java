package com.ctgu.examination_system.controller;

import java.util.List;

import javax.swing.ListModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctgu.examination_system.entity.Course;
import com.ctgu.examination_system.entity.Department;
import com.ctgu.examination_system.entity.PagingVO;
import com.ctgu.examination_system.entity.Student;
import com.ctgu.examination_system.entity.Teacher;
import com.ctgu.examination_system.entity.User;
import com.ctgu.examination_system.service.CourseService;
import com.ctgu.examination_system.service.DepartmentService;
import com.ctgu.examination_system.service.StudentService;
import com.ctgu.examination_system.service.TeacherService;
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
	private CourseService courseService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private User user;
	@Autowired
	private Student student;
	@Autowired
	private Course course;
	//学生管理部分
	@RequestMapping(value="/showStudent",method=RequestMethod.GET)
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
    @RequestMapping(value="/enterEditStudent",method=RequestMethod.GET)
    public String enterEditStudent(@RequestParam("id")Integer id,Model model) {
    	student=studentService.findStudentById(id);
    	List<Department> departmentList=departmentService.findAll();
    	model.addAttribute("student", student);
    	model.addAttribute("departmentList", departmentList);
    	return "admin/editStudent";
    }
    @RequestMapping(value="/editStudent",method=RequestMethod.POST)
    public String editStudent(Student student){
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
        if(studentService.findStudentByStudentId(student.getStudentId())) {
        	studentService.addStudent(student);
        	user.setPassword("123456");
        	user.setRole((short) 2);
        	user.setUserid(student.getStudentId());
        	userService.addUser(user);
        }
        return "redirect:/admin/showStudent";
    }

    @RequestMapping(value = "/selectStudent",method = RequestMethod.POST)
    public String searchStudent(@RequestParam("username") String username,Model model){
        System.out.println(username);
        List<Student>list=studentService.searchStudent(username);
        model.addAttribute("studentList", list);
        return "admin/showStudent";
    }
    //课程管理部分
    @RequestMapping(value="/showCourse",method=RequestMethod.GET)
	public String showCourse(Model model,Integer page) {
		List<Course> list=null;
		PagingVO pagingVO=new PagingVO();
		pagingVO.setTotalCount(courseService.getCountCourse());
		if(page==null || page==0) {
			pagingVO.setToPageNo(1);
			list=courseService.findByPaging(1);
		}else {
			pagingVO.setToPageNo(page);
			list=courseService.findByPaging(page);
		}
		model.addAttribute("courseList", list);
		model.addAttribute("pagingVO", pagingVO);
		return "admin/showCourse";
	}
    @RequestMapping(value="/removeCourse",method=RequestMethod.GET)
    @ResponseBody
    public boolean removeCourse(@RequestParam("id")Integer id) {
    	if(courseService.removeCourse(id))
    		return true;
    	return false; 
    }
    @RequestMapping(value="enterEditCourse",method=RequestMethod.GET)
    public String enterEditCourse(@RequestParam("id")Integer id,Model model) {
    	course = courseService.findById(id);
    	List<Department> departmentList = departmentService.findAll();
    	List<Teacher> teacherList = teacherService.findAll();
    	model.addAttribute("course", course);
    	model.addAttribute("teacherList", teacherList);
    	model.addAttribute("departmenList", departmentList);
    	return "admin/editCourse";
    }
    @RequestMapping(value="editCourse",method=RequestMethod.POST)
    public String editCourse(Course course) {
    	courseService.editCourse(course);
    	return "redirect:/admin/showCourse";
    }
    @RequestMapping(value = "/addCourse",method = RequestMethod.GET)
    public String showAddCourse(Model model){
    	List<Department> departmentList=departmentService.findAll();
    	List<Teacher> teacherList = teacherService.findAll();
    	model.addAttribute("departmentList",departmentList);
    	model.addAttribute("teacherList", teacherList);
	    return "admin/addCourse";
    }

    @RequestMapping(value = "/addCourse",method = RequestMethod.POST)
    public String addCourse(Course course){
        if(courseService.findByCourseId(course.getCourseId())) {
        	courseService.addCourse(course);
        }
        return "redirect:/admin/showCourse";
    }
    @RequestMapping(value = "/selectCourse",method = RequestMethod.POST)
    public String searchCourse(@RequestParam("username") String username,Model model){
        System.out.println(username);
        List<Course>list=courseService.searchCourse(username);
        model.addAttribute("courseList", list);
        return "admin/showCourse";
    }
}
