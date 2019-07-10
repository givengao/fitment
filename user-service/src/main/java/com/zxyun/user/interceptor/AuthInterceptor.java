package com.zxyun.user.interceptor;

import com.zxyun.user.annotation.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求开始处理--{}", System.currentTimeMillis());
        request.setAttribute("handleBegin", System.currentTimeMillis());

        if (handler == null) {
            return false;
        }

        if (handler instanceof  HandlerMethod) {
            Auth auth = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);

            if (auth == null) {
                return true;
            } else {
                Cookie[] cookies = request.getCookies();
                log.info("开始权限校验--{}");
            }
        } else {

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
