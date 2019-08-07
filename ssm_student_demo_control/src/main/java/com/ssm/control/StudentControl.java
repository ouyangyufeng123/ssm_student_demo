package com.ssm.control;

import com.ssm.control.base.BaseControl;
import com.ssm.model.Student;
import com.ssm.pojo.base.BaseResponse;
import com.ssm.pojo.response.StudentResponse;
import com.ssm.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生信息查询接口
 * @author ouyangyufeng
 * @date 2019/1/24
 */
@Api(description = "学生信息查询接口")
@RequestMapping("/student")
@RestController
public class StudentControl extends BaseControl {

    @Autowired
    private StudentService stuService;

    /**
     * <p>查询所有学生信息</p>
     *
     * @param sid:   用户id
     * @param sname: 用户姓名
     * @param sage:  用户年龄
     * @return StudentResponse
     */
    @GetMapping("/findByStudent")
    @ApiOperation(value = "单条件查询学生信息", notes = "传入相应单参数则条件查询数据，不传任何参数则查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid", value = "学生id", required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "sname", value = "学生姓名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sage", value = "学生年龄", required = false, dataType = "int", paramType = "query")})
    public StudentResponse findByStudent(Long sid, String sname, Integer sage) {
        StudentResponse stuResp = new StudentResponse();
        List<Student> listStu = stuService.selectByStudent(sid, sname, sage);
        if(listStu != null){
            stuResp = BaseFind(listStu, stuResp);
        }
        return stuResp;
    }

    /**
     * <p>查询学生所有信息</p>
     *
     * @return StudentResponse
     */
    @ApiOperation(value = "查询学生所有信息")
    @GetMapping("/findAllStudent")
    public StudentResponse findAllStudent(){
        StudentResponse stuResp = new StudentResponse();
        List<Student> listStu = stuService.selectAllStudent();
        if(listStu != null){
            stuResp = BaseFind(listStu, stuResp);
        }
        return stuResp;
    }

    /**
     * <p>添加学生信息</p>
     *
     * @param stu: 学生对象
     * @return BaseResponse
     */
    @ApiOperation(value = "添加学生信息")
    @PostMapping("/insertStudent")
    public BaseResponse insertStudent(@RequestBody Student stu) {
        stuService.insertStudent(stu);
        return new BaseResponse();
    }

    /**
     * <p>删除学生信息</p>
     *
     * @param sid: 学生id
     * @return BaseResponse
     */
    @ApiOperation(value = "删除学生信息")
    @DeleteMapping("/deleteStudent")
    public BaseResponse deleteStudent(@RequestParam Long sid) {
        stuService.deleteStudent(sid);
        return new BaseResponse();
    }

    /**
     * <p>修改学生信息</p>
     *
     * @param stu: 学生对象
     * @return BaseResponse
     */
    @ApiOperation(value = "修改学生信息")
    @PutMapping("/updateStudent")
    public BaseResponse updateStudent(@RequestBody Student stu) {
        stuService.updateStudent(stu);
        return new BaseResponse();
    }
}
