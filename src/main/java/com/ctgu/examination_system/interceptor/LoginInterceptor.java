package com.ctgu.examination_system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ctgu.examination_system.entity.User;

public class LoginInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	    //登录和注销不拦截
		if(request.getRequestURI().contains("/login") || request.getRequestURI().contains("logout")) {
			return true;
		}
		User user=(User) request.getSession().getAttribute("user");
		//如果未登录则拦截
		if(user==null) {
			response.sendRedirect("/login");
			return false;
		}
		//判断登录角色以及请求的url，禁止越权请求
		if (user.getRole() == 0){
		    if (!request.getRequestURI().contains("admin")){
		        response.sendRedirect("/logout");
		        logger.info("禁止越权，admin！");
		        return false;
            }
        }
        if (user.getRole() == 1){
            if (!request.getRequestURI().contains("teacher")){
                response.sendRedirect("/logout");
                logger.info("禁止越权，teacher！");
                logger.info(request.getRequestURI());
                return false;
            }
        }
        if (user.getRole() == 2){
            if (!request.getRequestURI().contains("student")){
                response.sendRedirect("/logout");
                logger.info("禁止越权，student！");
                return false;
            }
        }
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
