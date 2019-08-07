package com.ssm.mapper;

import com.ssm.model.Student;
import com.ssm.model.example.StudentExample;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ouyangyufeng
 */
@Mapper
public interface StudentMapper {
    int countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(Long sid);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(Long sid);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    /**
     * 查询所有学生信息
     *
     * @return
     */
    @Select("select * from student")
    List<Student> selectStudent();

    /**
     * 添加学生信息
     *
     * @param stu
     */
    @Insert("INSERT INTO student VALUES(#{sid}, #{sname}, #{sage})")
    void insertStudent(Student stu);
}