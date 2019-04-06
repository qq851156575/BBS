package com.piu.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.piu.security.exception.VerificationCodeException;
import com.piu.sys.utils.UserUtils;

/**
 * 验证码验证拦截器
 * @author wd
 *
 */
public class ValidationFilter extends UsernamePasswordAuthenticationFilter{
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if(null!=UserUtils.getSession()){
			//验证验证码  期间会报一个空指针错误 是因为当时还没有创建session;
		String verificationCode = req.getParameter("verificationCode");
			if(null!=verificationCode){
				String realVerif = (String) UserUtils.getSession().getAttribute("realVerif");
				if(!verificationCode.equalsIgnoreCase(realVerif)){
					throw new VerificationCodeException("验证码错误");
				}
			}
		}
		super.doFilter(req, res, chain);
	}
	
}
