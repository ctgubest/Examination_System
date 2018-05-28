package com.ctgu.examination_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctgu.examination_system.entity.PagingVO;
import com.ctgu.examination_system.entity.Student;
import com.ctgu.examination_system.service.StudentService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private StudentService studentService;
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
	
}
