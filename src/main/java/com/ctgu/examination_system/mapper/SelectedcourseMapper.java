package com.ctgu.examination_system.mapper;

import com.ctgu.examination_system.entity.Selectedcourse;
import com.ctgu.examination_system.entity.SelectedcourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedcourseMapper {
    int countByExample(SelectedcourseExample example);

    int deleteByExample(SelectedcourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Selectedcourse record);

    int insertSelective(Selectedcourse record);

    List<Selectedcourse> selectByExample(SelectedcourseExample example);

    Selectedcourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Selectedcourse record, @Param("example") SelectedcourseExample example);

    int updateByExample(@Param("record") Selectedcourse record, @Param("example") SelectedcourseExample example);

    int updateByPrimaryKeySelective(Selectedcourse record);

    int updateByPrimaryKey(Selectedcourse record);
}