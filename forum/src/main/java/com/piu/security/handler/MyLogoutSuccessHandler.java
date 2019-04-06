package com.piu.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


public class MyLogoutSuccessHandler extends
AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		//使头文件中的referer生效 referer为服务器上一次请求的地址
		super.setUseReferer(true);
		logger.info("Referer-->url " + request.getHeader("Referer"));
		super.handle(request, response, authentication);
	}

}
