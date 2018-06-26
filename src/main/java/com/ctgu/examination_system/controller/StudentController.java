package com.ctgu.examination_system.controller;

import com.ctgu.examination_system.entity.CourseCustom;
import com.ctgu.examination_system.entity.PagingVO;
import com.ctgu.examination_system.entity.SelectedCourseCustom;
import com.ctgu.examination_system.entity.User;
import com.ctgu.examination_system.service.CourseService;
import com.ctgu.examination_system.service.SelectedCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 学生控制层
 * Created by 汪俊 on 2018/6/11.
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseService courseService;

    @Autowired
    private SelectedCourseService selectedCourseService;

    @RequestMapping(value = "/showCourse")
    public String stuCourseShow(Model model, Integer page) throws Exception {

        List<CourseCustom> list = null;
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(courseService.getCountCourse());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = courseService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = courseService.findByPaging(page);
        }
        model.addAttribute("courseList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "student/showCourse";
    }

    // 选课操作
    @RequestMapping(value = "/stuSelectedCourse")
    public String stuSelectedCourse(@RequestParam("courseId") int courseId, HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        SelectedCourseCustom selectedCourseCustom = new SelectedCourseCustom();
        selectedCourseCustom.setCourseId(courseId);
        selectedCourseCustom.setStudentId(user.getUserid());

        SelectedCourseCustom s = selectedCourseService.findOne(selectedCourseCustom);

        if (s == null) {    //若为空，则表示可以选课
            selectedCourseService.save(selectedCourseCustom);
            return "redirect:/student/selectedCourse";
        } else {        //不为空，则表示已选了该课
            System.err.println("you have already selected this course");
            return "redirect:/failedPage";
        }
    }
/*
    // 退课操作
    @RequestMapping(value = "/outCourse")
    public String outCourse(int id, HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");

        SelectedCourseCustom selectedCourseCustom = new SelectedCourseCustom();
        selectedCourseCustom.setCourseId(id);
        selectedCourseCustom.setStudentId(user.getUserid());

        selectedCourseService.remove(selectedCourseCustom);

        return "redirect:/student/selectedCourse";
    }*/

    // 已选课程
    @RequestMapping(value = "/selectedCourse")
    public String selectedCourse(Model model, HttpServletRequest request) throws Exception {
        //获取当前用户名
        User user = (User) request.getSession().getAttribute("user");

        List<SelectedCourseCustom> list = selectedCourseService.findSelectedCourseByStudentID(user.getUserid());

        for (SelectedCourseCustom sc : list){
            System.out.println(sc.getCourseCustom().getCourseName());
        }

        model.addAttribute("selectedCourseList", list);

        return "student/selectedCourse";
    }

    // 已修课程
    @RequestMapping(value = "/overCourse")
    public String overCourse(Model model,HttpServletRequest request) throws Exception {

        User user = (User)request.getSession().getAttribute("user");

        List<SelectedCourseCustom> list = selectedCourseService.findOveredCourseByStudentID(user.getUserid());

        for (SelectedCourseCustom sc : list){
            logger.info("课程名称={}",sc.getCourseCustom().getCourseName());
            logger.info("学分={}",sc.getCourseCustom().getCredit());
            logger.info("上课时间={}",sc.getCourseCustom().getCourseTime());
            logger.info("成绩={}",sc.getScore());
        }

        model.addAttribute("selectedCourseList", list);

        return "student/overCourse";
    }

    //修改密码
    @RequestMapping(value = "/passwordRest")
    public String passwordRest() throws Exception {
        return "student/passwordRest";
    }
}
