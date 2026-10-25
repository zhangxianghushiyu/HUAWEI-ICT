package com.ruoyi.web.controller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    //两个..代表所有子目录，最后括号里的两个..代表所有参数
//    @Pointcut("execution(public * com.app.controller.*.*(..))")
    @Pointcut("execution(public * com.ruoyi.web.controller.platform..*.*(..))")
    public void logPointCut() {
    }

    @After("logPointCut()")
    public void doAfter() throws Throwable {
        // 处理完请求，返回内容
        log.info("========================================== End ==========================================");
    }

    //
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        String result = null;
        Object obj = pjp.proceed();// ob 为方法的返回值
        try {
            long startTime = System.currentTimeMillis();
            if (obj instanceof Serializable) {
                result = obj.toString();
            } else {
                if (obj != null) {
                    result = objectMapper.writeValueAsString(obj);
                }
            }
            // 打印出参
            log.info("返回参数:{}", result);
            log.info("耗时:{}", (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            log.error("异常信息：{}", e.getMessage());
        }
        return obj;
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("========================================== Start ==========================================");
        // 记录下请求内容
        log.info("URL : {}", request.getRequestURL().toString());
        log.info("HTTP_METHOD : {}", request.getMethod());
        log.info("IP : {}", request.getRemoteAddr());
        log.info("CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("HEADER : {}", request.getHeader("userId"));
        log.info("AUTHORIZATION : {}", request.getHeader("Authorization"));
        log.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));
    }
}
