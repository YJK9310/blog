package com.lrm.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by limi on 2017/10/13.
 */
//拦截所有Controller注解的拦截器
@ControllerAdvice
public class ControllerExceptionHandler {

    //获取日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //标识方法是用来做异常处理的
    @ExceptionHandler(Exception.class)


    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        //记录异常信息 ：路径，异常信息
        logger.error("Requst URL : {}，Exception : {}", request.getRequestURL(),e);
        //
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        //控制返回页面，并添加相关信息
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception", e);
        //返回页面
        mv.setViewName("error/error");
        return mv;
    }
}
