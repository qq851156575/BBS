package com.piu.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.piu.sys.utils.UserUtils;
/**
 * 头像拦截器
 * @author lenovo
 *
 */
@Component
public class ImgFilter extends HandlerInterceptorAdapter{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		try {
			if(!SecurityContextHolder.getContext().getAuthentication().getName().
					equals("anonymousUser")){
				request.setAttribute("currentUser", UserUtils.getCurrentUser());
			}
		} catch (Exception e) {
			logger.warn(e.toString()+"--->SecurityContextHolder.getContext()");
		}
		super.postHandle(request, response, handler, modelAndView);
	}

}
