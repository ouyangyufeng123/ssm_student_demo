package com.ssm.error;

import com.ssm.error.impl.IError;

/**
 * Created by ouyangyufeng on 2018/9/20.
 */
public enum DefaultError implements IError {
    SYSTEM_INTERNAL_ERROR("0000", "System Internal Error"),
    STUDENT_DATA_ERROR("1000", "数据不存在!"),
    STUDENT_INSERt_ERROR("1001", "学生信息添加失败!"),
    STUDENT_DELETE_ERROR("1002", "学生信息删除失败!"),
    STUDENT_UPDATE_ERROR("1003", "学生信息修改失败!");

    String errorCode;
    String errorMessage;

    private DefaultError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private DefaultError(String errorCode, String errorMessage, String extErrorMsg) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return "SYS." + this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}

