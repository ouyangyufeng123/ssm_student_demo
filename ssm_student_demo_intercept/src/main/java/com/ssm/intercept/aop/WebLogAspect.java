package com.ssm.intercept.aop;

import com.ssm.service.impl.StudentServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *
 * @author ouyangyufeng
 * @date 2019/1/25
 */
//@Aspect
//@Component
public class WebLogAspect {
    /**
     * 实例化日志
     */
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    /**
     * <p>方法切入点</p>
     */
    @Pointcut("execution(* com.ssm.control.*.*(..))")
    public void webLog() {
    }

    /**
     * <p>开始前执行</p>
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer sb = new StringBuffer();
        String url = request.getRequestURL().toString();
        String http_method = request.getMethod();
        String contentType = request.getContentType();
        String args = Arrays.toString(joinPoint.getArgs());
        sb.append("\t\n\t请求地址：" + url +
                "\r\n\t数据参数：" + args +
                "\r\n\t数据格式：" + contentType +
                "\r\n\t请求类型：" + http_method);
        // 记录下请求内容
        logger.info(sb.toString());
    }

    /**
     * <p>结束后执行</p>
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret + "\r\n");
    }

}
