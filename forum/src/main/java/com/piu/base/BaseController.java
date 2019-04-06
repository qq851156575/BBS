package com.piu.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.piu.sys.utils.UserUtils;


public abstract class BaseController {
	/**
	 * plate 对应的表
	 */
	protected static final String EXCHANGE_OF_LEARNING = "01";
	protected static final String MAKE_FRIENDS = "02";
	protected static final String FLEA_MARKET = "03";
	protected static final String ACTIVITY = "04";
	
	//邮件发送
	@Autowired
	protected JavaMailSender mailSender;
	//日志
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
		
	/**
	 * 设置导航栏头像
	 * @param request
	 * @param response
	 * @param model
	 */
	public void setimg(HttpServletRequest request,HttpServletResponse response,Model model){
		if(!SecurityContextHolder.getContext().getAuthentication().getName().
				  equals("anonymousUser")){
			model.addAttribute("currentUser", UserUtils.getCurrentUser());
		}
	}
}