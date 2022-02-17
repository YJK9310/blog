package com.lrm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by limi on 2017/10/13.
 */
//定义切面。@Aspect负责切面设置
@Aspect
//组件注入
@Component
public class LogAspect {

    //创建日志对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //定义切面：日志功能需要切入哪些位置  任何方法 包名 所有类 所有方法
    @Pointcut("execution(* com.lrm.web.*.*(..))")
    // log是代称
    public void log() {}

    //切面之前的操作
    @Before("log()")
    //JoinPoint 切面信息
    public void doBefore(JoinPoint joinPoint) {
        // 获取ServletRequestAttributes
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取HttpServletRequest
        HttpServletRequest request = attributes.getRequest();
        //url,ip属性获取
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);
    }

    //切面之后的操作
    @After("log()")
    public void doAfter() {
//        logger.info("--------doAfter--------");
    }
    //方法返回的拦截 Object result 进行捕获
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
