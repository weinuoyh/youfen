package com.moerlong.youfen.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/*
package:com.moerlong.youfen.aop
project:youfen
date:2018/9/21
name:shaxueting
*/
@Aspect
@Order(1)
@Component
public class WebLogHeadAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.moerlong.*.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        //记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        StringBuffer logs = new StringBuffer();
        logs.append("请求url：" + request.getRequestURL());
        logger.info(logs.toString());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterRetuining(Object ret) throws Throwable {
        logger.info("请求时间：" + (System.currentTimeMillis() - startTime.get()));
        logger.info("返回结果：" + JSONObject.toJSONString(ret));
    }
}
