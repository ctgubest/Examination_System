package com.ctgu.examination_system.service.impl;

import com.ctgu.examination_system.entity.*;
import com.ctgu.examination_system.mapper.SelectedcourseMapper;
import com.ctgu.examination_system.mapper.StudentMapper;
import com.ctgu.examination_system.service.SelectedCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现类
 * Created by 汪俊 on 2018/6/11.
 */
@Service
public class SelectedCourseServiceImpl implements SelectedCourseService {

    @Autowired
    private SelectedcourseMapper selectedcourseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<SelectedCourseCustom> findByCourseID(Integer id) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(id);

        List<Selectedcourse> list = selectedcourseMapper.selectByExample(example);
        List<SelectedCourseCustom> secList = new ArrayList<SelectedCourseCustom>();
        for (Selectedcourse s: list) {
            SelectedCourseCustom sec = new SelectedCourseCustom();
            BeanUtils.copyProperties(s, sec);
            //判断是否完成类该课程
            if (sec.getScore() != null) {
                sec.setOver(true);
            }
            sec.setStudent(findStudentBtStuId(sec.getStudentId()));
            secList.add(sec);
        }

        return secList;
    }

    @Override
    public List<SelectedCourseCustom> findByCourseIDPaging(Integer page, Integer id) throws Exception {
        return null;
    }

    //获取该课程学生数
    @Override
    public Integer countByCourseID(Integer id) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(id);
        return selectedcourseMapper.countByExample(example);
    }

    @Override
    public SelectedCourseCustom findOne(SelectedCourseCustom selectedCourseCustom) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(selectedCourseCustom.getCourseId());
        criteria.andStudentIdEqualTo(selectedCourseCustom.getStudentId());
        List<Selectedcourse> list = selectedcourseMapper.selectByExample(example);

        if (list.size() > 0) {
            SelectedCourseCustom sc = new SelectedCourseCustom();
            BeanUtils.copyProperties(list.get(0), sc);
            sc.setStudent(findStudentBtStuId(sc.getStudentId()));
            return sc;
        }
        return null;
    }

    @Override
    public void updataOne(SelectedCourseCustom selectedCourseCustom) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(selectedCourseCustom.getCourseId());
        criteria.andStudentIdEqualTo(selectedCourseCustom.getStudentId());
        selectedcourseMapper.updateByExample(selectedCourseCustom, example);

    }

    @Override
    public void save(SelectedCourseCustom selectedCourseCustom) throws Exception {
        selectedcourseMapper.insertSelective(selectedCourseCustom);
    }

    @Override
    public List<SelectedCourseCustom> findByStudentID(Integer id) throws Exception {
        return null;
    }

    @Override
    public void remove(SelectedCourseCustom selectedCourseCustom) throws Exception {
        SelectedcourseExample example = new SelectedcourseExample();
        SelectedcourseExample.Criteria criteria = example.createCriteria();

        criteria.andCourseIdEqualTo(selectedCourseCustom.getCourseId());
        criteria.andStudentIdEqualTo(selectedCourseCustom.getStudentId());

        selectedcourseMapper.deleteByExample(example);
    }

    private Student findStudentBtStuId(String studentId){
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria1 = studentExample.createCriteria();
        criteria1.andStudentIdEqualTo(studentId);
        Student student = studentMapper.selectByExample(studentExample).get(0);
        return student;
    }
}
