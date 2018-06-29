package com.ctgu.examination_system.controller;

import com.ctgu.examination_system.entity.Course;
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
        pagingVO.setTotalCount(courseService.getCountCourse(""));
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
    @ResponseBody
    public int stuSelectedCourse(@RequestParam("courseId") int courseId, HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        SelectedCourseCustom selectedCourseCustom = new SelectedCourseCustom();
        selectedCourseCustom.setCourseId(courseId);
        selectedCourseCustom.setStudentId(user.getUserid());

        SelectedCourseCustom s = selectedCourseService.findOne(selectedCourseCustom);

        if (s == null) {    //若为空，则表示可以选课
            selectedCourseService.save(selectedCourseCustom);
            return 1;
        } else {        //不为空，则表示已选了该课
            logger.info("这门课你已经选了");
            return 0;
        }
    }

    // 退课操作
    @RequestMapping(value = "/outCourse")
    public String outCourse(@RequestParam("courseId") int courseId, HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");

        SelectedCourseCustom selectedCourseCustom = new SelectedCourseCustom();
        selectedCourseCustom.setCourseId(courseId);
        selectedCourseCustom.setStudentId(user.getUserid());

        selectedCourseService.remove(selectedCourseCustom);

        return "redirect:/student/selectedCourse";
    }

    // 已选课程
    @RequestMapping(value = "/selectedCourse")
    public String selectedCourse(Model model, HttpServletRequest request, Integer page) throws Exception {
        //获取当前用户名
        User user = (User) request.getSession().getAttribute("user");

        List<SelectedCourseCustom> selectedCourseCustomList = null;
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(selectedCourseService.getCountSelectedCourse(user.getUserid(),false));
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            selectedCourseCustomList = selectedCourseService.findSelectedCourseByPage(user.getUserid(), 1);
        } else {
            pagingVO.setToPageNo(page);
            selectedCourseCustomList = selectedCourseService.findSelectedCourseByPage(user.getUserid(), page);
        }
        logger.info("已选课程：");
        for (SelectedCourseCustom sc : selectedCourseCustomList){
            logger.info("课程名称={}",sc.getCourseCustom().getCourseName());
            logger.info("学分={}",sc.getCourseCustom().getCredit());
            logger.info("上课时间={}",sc.getCourseCustom().getCourseTime());
            logger.info("成绩={}",sc.getScore());
        }

        model.addAttribute("selectedCourseList", selectedCourseCustomList);
        model.addAttribute("pagingVO", pagingVO);
        return "student/selectedCourse";
    }

    // 已修课程
    @RequestMapping(value = "/overCourse")
    public String overCourse(Model model,HttpServletRequest request, Integer page) throws Exception {

        User user = (User)request.getSession().getAttribute("user");

        List<SelectedCourseCustom> list = null;

        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(selectedCourseService.getCountSelectedCourse(user.getUserid(),true));
        logger.info("总页数：{}",pagingVO.getTotalCount());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = selectedCourseService.findOveredCourseByPage(user.getUserid(), 1);
        } else {
            pagingVO.setToPageNo(page);
            list = selectedCourseService.findOveredCourseByPage(user.getUserid(), page);
        }


        for (SelectedCourseCustom sc : list){
            logger.info("课程名称={}",sc.getCourseCustom().getCourseName());
            logger.info("学分={}",sc.getCourseCustom().getCredit());
            logger.info("上课时间={}",sc.getCourseCustom().getCourseTime());
            logger.info("成绩={}",sc.getScore());
        }

        model.addAttribute("selectedCourseList", list);
        model.addAttribute("pagingVO", pagingVO);
        return "student/overCourse";
    }

    //是否已经修完某个课程
    @GetMapping("/isOvered")
    @ResponseBody
    public int isOvered(@RequestParam("courseId") int courseId, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        return selectedCourseService.isOvered(user.getUserid(),courseId);
    }

    //搜索课程
    @PostMapping("/searchCourse")
    public String searchCourse(@RequestParam("courseName") String courseName,Integer page,Model model){

        List<CourseCustom> courseList = null;
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(courseService.getCountCourse(courseName));
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            courseList = courseService.searchCourseByPage(courseName,1);
        } else {
            pagingVO.setToPageNo(page);
            courseList = courseService.searchCourseByPage(courseName,page);
        }
        model.addAttribute("courseList", courseList);
        model.addAttribute("pagingVO", pagingVO);
        return "student/showCourse";
    }

    //修改密码
    @RequestMapping(value = "/passwordRest")
    public String passwordRest() throws Exception {
        return "student/passwordRest";
    }
}
