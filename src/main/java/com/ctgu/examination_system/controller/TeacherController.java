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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 教师界面
 * Created by 汪俊 on 2018/6/11.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseService courseService;

    @Autowired
    private SelectedCourseService selectedCourseService;

    //显示我的课程
    @GetMapping("/showCourse")
    public String stuCourseShow(Model model, Integer page, HttpServletRequest request) throws Exception {

        User user = (User)request.getSession().getAttribute("user");

        List<CourseCustom> courseList = null;
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(courseService.getMyCountCourse(user.getUserid(),""));
        logger.info("总页数：{}",pagingVO.getTotalCount());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            courseList = courseService.searchMyCourseByPage(user.getUserid(),"",1);
        } else {
            pagingVO.setToPageNo(page);
            courseList = courseService.searchMyCourseByPage(user.getUserid(),"",page);
        }
        model.addAttribute("courseList", courseList);
        model.addAttribute("pagingVO", pagingVO);
        model.addAttribute("courseList", courseList);
        return "teacher/showCourse";
    }

    //显示成绩
    @GetMapping("/gradeCourse")
    public String gradeCourse(@RequestParam("courseId")Integer id, Model model) throws Exception {
        if (id == null) {
            return "";
        }
        List<SelectedCourseCustom> list = selectedCourseService.findByCourseID(id);
        model.addAttribute("selectedCourseList", list);
        return "teacher/showGrade";
    }

    //打分
    @GetMapping("/mark")
    public String markUI(SelectedCourseCustom scc, Model model) throws Exception {

        SelectedCourseCustom selectedCourseCustom = selectedCourseService.findOne(scc);

        model.addAttribute("selectedCourse", selectedCourseCustom);

        return "teacher/score";
    }

    //打分
    @PostMapping("/mark")
    public String mark(SelectedCourseCustom scc) throws Exception {

        selectedCourseService.updataOne(scc);

        return "redirect:/teacher/gradeCourse?courseId="+scc.getCourseId();
    }

    @RequestMapping(value = "/selectCourse",method = RequestMethod.POST)
    public String searchCourse(@RequestParam("courseName") String courseName, Integer page, Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<CourseCustom> courseList = null;
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(courseService.getMyCountCourse(user.getUserid(),""));
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            courseList = courseService.searchMyCourseByPage(user.getUserid(),courseName,1);
        } else {
            pagingVO.setToPageNo(page);
            courseList = courseService.searchMyCourseByPage(user.getUserid(),courseName,page);
        }
        model.addAttribute("courseList", courseList);
        model.addAttribute("pagingVO", pagingVO);

        return "teacher/showCourse";
    }

    @GetMapping("/passwordRest")
    public String passwordRest() throws Exception {
        return "teacher/passwordRest";
    }
}
