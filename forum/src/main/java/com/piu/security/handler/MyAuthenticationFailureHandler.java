package com.piu.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.piu.sys.utils.UserUtils;

public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler implements
AuthenticationFailureHandler {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
					throws IOException, ServletException {
		/*logger.debug("登录失败" + "用户名或密码不正确");
		saveException(request, exception);
		logger.debug("Redirecting to " + UserUtils.getRelativePath(request));
		super.getRedirectStrategy().sendRedirect(request, response,  UserUtils.getRelativePath(request));*/
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
	}

}
