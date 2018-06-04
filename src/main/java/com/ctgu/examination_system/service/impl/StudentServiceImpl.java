package com.ctgu.examination_system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctgu.examination_system.entity.Department;
import com.ctgu.examination_system.entity.DepartmentExample;
import com.ctgu.examination_system.entity.PagingVO;
import com.ctgu.examination_system.entity.Student;
import com.ctgu.examination_system.entity.StudentExample;
import com.ctgu.examination_system.entity.StudentExample.Criteria;
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
        list = setDept(list);
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
			StudentExample example = new StudentExample();
            StudentExample.Criteria criteria = example.createCriteria();
            criteria.andStudentIdEqualTo(student.getStudentId());
			return studentMapper.updateByExampleSelective(student, example) == 1;
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
        criteria.andUsernameLike("%"+username+"%");
        List<Student> list = studentMapper.selectByExample(example);
        list = setDept(list);
        return list;
    }
	@Override
	public boolean findStudentByStudentId(String studentId) {
		StudentExample studentExample=new StudentExample();
		Criteria criteria=studentExample.createCriteria();
		criteria.andStudentIdEqualTo(studentId);
		List<Student>list=studentMapper.selectByExample(studentExample);
		if(list==null || list.size()==0)
			return true;
		return false;
	}

    @Override
    public int getLargestStuId() {
	    int id = studentMapper.getLargestStuId();
	    if (id == 0){
	        return 10001;
        }
        return id + 1;
    }

    //给学生设置部门
    private List<Student> setDept(List<Student> list){
        for(Student student : list) {
            DepartmentExample example = new DepartmentExample();
            DepartmentExample.Criteria criteria = example.createCriteria();
            criteria.andDepartmentIdEqualTo(student.getDepartmentId());
            List<Department> deptList = departmentMapper.selectByExample(example);
            if (deptList != null && deptList.size() != 0) {	//如果取出的数据不为空
                student.setDepartment(deptList.get(0));
            }
        }
        return list;
    }
}
