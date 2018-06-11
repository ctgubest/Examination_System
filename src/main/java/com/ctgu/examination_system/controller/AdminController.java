package com.ctgu.examination_system.controller;

import java.util.List;

import com.ctgu.examination_system.service.TeacherService;
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
	private User user;
	@Autowired
	private Student student;
	@Autowired
	private Course course;

	@Autowired
    private Teacher teacher;

	@Autowired
    private TeacherService teacherService;

	/************************************学生管理部分***************************************/
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
        model.addAttribute("StudentId",studentService.getLargestStuId());
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
        List<Student>list=studentService.searchStudent(username);
        model.addAttribute("studentList", list);
        return "admin/showStudent";
    }
    /***************************************教师管理部分*************************************/
    @RequestMapping("/showTeacher")
    public String showTeacher(Model model, Integer page){
        List<Teacher> list = null;
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

    /***********************************课程管理部分**************************************/
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
    	model.addAttribute("departmentList", departmentList);
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
    	model.addAttribute("CourseId",courseService.getLargestCourId());
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
    public String searchCourse(@RequestParam("username") String username,Model model) {
        System.out.println(username);
        List<Course> list = courseService.searchCourse(username);
        model.addAttribute("courseList", list);
        return "admin/showCourse";
    }

    /***************************************密码管理************************************/
    // 普通用户账号密码重置
    @RequestMapping("/userPasswordRest")
    public String showUserPasswordRest() throws Exception {
        return "admin/userPasswordRest";
    }

    // 普通用户账号密码重置处理
    @RequestMapping(value = "/userPasswordRest", method = {RequestMethod.POST})
    public String userPasswordRest(User user) throws Exception {
        User u = userService.selectUserById(user.getUserid());

        if (u != null) {
            if (userService.updateUser(user)){
                return "admin/userPasswordRest";
            }
        } else {
            return "/failedPage";
        }
        return "/failedPage";
    }

    // 本账户密码重置
    @RequestMapping("/passwordRest")
    public String showPasswordRest() throws Exception {
        return "admin/passwordRest";
    }
}
