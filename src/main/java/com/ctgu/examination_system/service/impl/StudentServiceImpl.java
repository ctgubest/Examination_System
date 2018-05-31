package com.ctgu.examination_system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctgu.examination_system.entity.Department;
import com.ctgu.examination_system.entity.DepartmentExample;
import com.ctgu.examination_system.entity.PagingVO;
import com.ctgu.examination_system.entity.Student;
import com.ctgu.examination_system.entity.StudentExample;
import com.ctgu.examination_system.mapper.DepartmentMapper;
import com.ctgu.examination_system.mapper.StudentMapper;
import com.ctgu.examination_system.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Override
	public Integer getCountStudent() {
			StudentExample studentExample=new StudentExample();
			Integer studentCount=studentMapper.countByExample(studentExample);
		return studentCount;
	}
	@Override
	public List<Student> findByPaging(int i) {
		PagingVO pagingVO = new PagingVO();
        pagingVO.setToPageNo(i);
        List<Student> list=studentMapper.findByPaging(pagingVO);
        if (list == null || list.size() == 0){
            return null;
        }
        for(int index=0;index<list.size();index++) {
        	DepartmentExample dExample=new DepartmentExample();
        	com.ctgu.examination_system.entity.DepartmentExample.Criteria criteria=dExample.createCriteria();
        	criteria.andDepartmentIdEqualTo(list.get(index).getDepartmentId());
        	List<Department> lists=departmentMapper.selectByExample(dExample);
        	if (lists != null && lists.size() != 0) {	//如果取出的数据不为空
				list.get(index).setDepartment(lists.get(0));
			}
        }
        return list;
	}
	@Override
	public boolean deleteStudent(Integer id) {
		if(id!=null || id>0) {
			return studentMapper.deleteByPrimaryKey(id) == 1;
		}
		return false;
	}
	@Override
	public Student findStudentById(Integer id) {
		return studentMapper.selectByPrimaryKey(id);
	}
	@Override
	public boolean editStudent(Student student) {
		if(student !=null) {
			StudentExample studentExample=new StudentExample();
			return studentMapper.updateByExampleSelective(student, studentExample) == 1;
		}
		return false;
	}

    @Override
    public boolean addStudent(Student student) {
        return studentMapper.insert(student) == 1;
    }

    @Override
    public List<Student> searchStudent(String username) {
	    StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameLike(username);
        return studentMapper.selectByExample(example);
    }

}
