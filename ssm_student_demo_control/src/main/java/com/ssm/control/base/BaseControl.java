package com.ssm.control.base;

import com.ssm.model.Student;
import com.ssm.pojo.response.StudentResponse;
import com.ssm.pojo.vo.StudentVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ouyangyufeng
 */
public class BaseControl {

    public StudentResponse BaseFind(List<Student> listStu, StudentResponse stuResp){
        List<StudentVo> listStuVo = new ArrayList<>();
        for (Student stu : listStu) {
            StudentVo stuVo = new StudentVo();
            BeanUtils.copyProperties(stu, stuVo);
            listStuVo.add(stuVo);
        }
        stuResp.setStudentVo(listStuVo);
        return stuResp;
    }

}
