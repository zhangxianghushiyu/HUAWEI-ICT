package com.ruoyi.platform.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.constant.Constant;
import com.ruoyi.platform.enums.TokenEnum;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class InterceptorConfig implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //路径
        String requestUrl = request.getRequestURI();
        String requestMethod = request.getMethod();
        //OPTIONS方式直接放行(浏览器访问接口时，会提前使用OPTIONS方式进行请求)
        if ("OPTIONS".equals(requestMethod)) {
            log.info("requestURI:{},requestMethod：{}_放行", requestUrl, requestMethod);
            return true;
        }
        //获取token
        String token = request.getHeader(Constant.TOKEN);
        if (StrUtil.isEmpty(token)) {
            return handleReturnInfo(response, TokenEnum.TOKEN_IS_NULL.getCode(), TokenEnum.TOKEN_IS_NULL.getMessage());
        }
        //验证token
        boolean verifyResult = JwtUtil.verifyJWT(token);
        if (!verifyResult) {
            return handleReturnInfo(response, TokenEnum.TOKEN_IS_EXPIRE.getCode(), TokenEnum.TOKEN_IS_EXPIRE.getMessage());
        }
        //处理单点登陆问题
        //查看redis中的信息
        Integer userId = JwtUtil.getJWTId(token);
        Object o = redisUtil.get(Constant.USER_LOGIN_TOKEN + userId);
        if (Objects.isNull(o)) {
            return handleReturnInfo(response, TokenEnum.TOKEN_IS_NULL.getCode(), TokenEnum.TOKEN_IS_NULL.getMessage());
        }
        String s = o.toString();
        if (!s.equals(token)) {
            return handleReturnInfo(response, TokenEnum.TOKEN_REPEAT_LOGIN.getCode(), TokenEnum.TOKEN_REPEAT_LOGIN.getMessage());
        }
        //防重复提交
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
        if (Objects.nonNull(annotation)) {
            Object route = redisUtil.get(Constant.USER_REPEAT_SUBMIT + userId);
            if (Objects.nonNull(route) && route.toString().equals(requestUrl)) {
                return handleReturnInfo(response, Constant.ResultCode.ERROR.getCode(), annotation.message());
            }
            redisUtil.set(Constant.USER_REPEAT_SUBMIT + userId, requestUrl, annotation.interval(), TimeUnit.MILLISECONDS);
        }
        return true;
    }

    /**
     * 处理错误返回信息
     */
    private boolean handleReturnInfo(HttpServletResponse response, Integer code, String msg) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out;
        JSONObject res = new JSONObject();
        res.putOnce("code", code);
        res.putOnce("msg", msg);
        out = response.getWriter();
        out.append(res.toString());
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
