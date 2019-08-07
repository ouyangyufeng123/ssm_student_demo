package com.ssm.service;

import com.ssm.model.Student;

import java.util.List;

/**
 *
 * @author ouyangyufeng
 * @date 2019/1/23
 */
public interface StudentService {

    /**
     * <p>通用条件查询学生信息</p>
     * @param sid
     * @param sname
     * @param sage
     * @return
     */
    List<Student> selectByStudent(Long sid, String sname, Integer sage);

    /**
     * <p>查询学生所以信息</p>
     * @return
     */
    List<Student> selectAllStudent();

    /**
     * <p>添加学生信息</p>
     * @param stu
     */
    void insertStudent(Student stu);

    /**
     * <p>删除学生信息</p>
     * @param sid
     */
    void deleteStudent(Long sid);

    /**
     * <p>修改学生信息</p>
     * @param stu
     */
    void updateStudent(Student stu);
}
