package com.ruoyi.platform.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.platform.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class WebLogAspect {
    private static ObjectMapper objectMapper = new ObjectMapper();

    //两个..代表所有子目录，最后括号里的两个..代表所有参数
    @Pointcut("execution(public * com.ruoyi..controller..*.*(..))")
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
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        log.info("========================================== Start ==========================================");
        // 记录下请求内容
        log.info("URL : {}", request.getRequestURL().toString());
        log.info("HTTP_METHOD : {}", request.getMethod());
        log.info("IP : {}", request.getRemoteAddr());
        log.info("CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("TOKEN : {}", request.getHeader("token"));
        log.info("USER_ID : {}", JwtUtil.getRequestUserId(request));
//        log.info("AUTHORIZATION : {}", request.getHeader("Authorization"));
        log.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));
    }
}