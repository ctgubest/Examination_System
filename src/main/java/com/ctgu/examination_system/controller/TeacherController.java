package com.ctgu.examination_system.controller;

import com.ctgu.examination_system.entity.CourseCustom;
import com.ctgu.examination_system.entity.SelectedCourseCustom;
import com.ctgu.examination_system.entity.User;
import com.ctgu.examination_system.service.CourseService;
import com.ctgu.examination_system.service.SelectedCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 教师界面
 * Created by 汪俊 on 2018/6/11.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private SelectedCourseService selectedCourseService;

    //显示我的课程
    @GetMapping("showCourse")
    public String stuCourseShow(Model model, HttpServletRequest request) throws Exception {

        User user = (User)request.getSession().getAttribute("user");

        List<CourseCustom> list = courseService.findByTeacherID(user.getUserid());
        model.addAttribute("courseList", list);
        return "teacher/showCourse";
    }

    //显示成绩
    @GetMapping("/gradeCourse")
    public String gradeCourse(Integer id, Model model) throws Exception {
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

        return "teacher/mark";
    }

    //打分
    @PostMapping("/mark")
    public String mark(SelectedCourseCustom scc) throws Exception {

        selectedCourseService.updataOne(scc);

        return "redirect:/teacher/gradeCourse?id="+scc.getCourseId();
    }

    @GetMapping("/passwordRest")
    public String passwordRest() throws Exception {
        return "teacher/passwordRest";
    }
}
