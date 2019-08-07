package com.ssm.pojo.response;

import com.ssm.pojo.base.BaseResponse;
import com.ssm.pojo.vo.StudentVo;
import lombok.Data;

import java.util.List;

/**
 * Created by ouyangyufeng on 2019/1/24.
 */
@Data
public class StudentResponse extends BaseResponse {

    private List<StudentVo> studentVo;
}
