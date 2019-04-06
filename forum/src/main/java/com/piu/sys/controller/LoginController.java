package com.piu.sys.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piu.base.BaseController;
import com.piu.email.MyMailMessage;
import com.piu.sys.entity.User;
import com.piu.sys.service.UserService;
import com.piu.sys.utils.VerifyUtil;

@Controller
public class LoginController extends BaseController{
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value={"/home","/"})
	public String home(HttpServletRequest request,HttpServletResponse response,Model model){
		return "base/home";
	}
	
	/**
	 * 生成验证码并返回图像 将真实的验证码放入session中
	 * @param request
	 * @param response
	 */
	@RequestMapping(value={"/getVerificationCode"})
	public void getVerificationCode(HttpServletRequest request,HttpServletResponse response){
		try {
			/*生成验证码 
			  verif[0]为验证码 
			  verif[1]为图像的base64码*/
			Object[] Verif = VerifyUtil.createImage();
			ObjectMapper objectMapper = new ObjectMapper();
			request.getSession().setAttribute("realVerif", Verif[0]);
			response.getWriter().print(objectMapper.writeValueAsString(Verif));
			//response.getWriter().print(Verif[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 返回邮箱验证码并发送邮件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value={"/getEmailVerif"})
	public void getEmailVerif(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String email = request.getParameter("email");
		User user = new User();
		user.setMail(email);
		if(userService.get(user)!=null) {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print("邮箱不可用或已被使用！");
			response.setStatus(404);
		}else {
			MyMailMessage mailMessage = new MyMailMessage(email);
			String emailVerif = mailMessage.getEmailVerif();
			request.getSession().setAttribute("emailVerif", emailVerif);
			response.getWriter().print(emailVerif);
			//发送邮件
			super.mailSender.send(mailMessage);
		}
	}
	
	/**
	 * 处理登陆的跳转
	 * @param request
	 * @param response
	 * @return
	 */
	/*@RequestMapping("/login")
	public String login(HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtils.isNotBlank((String)request.getSession().getAttribute("erroLogin"))){
			String preVisitUrl = (String) request.getSession().getAttribute("preVisitUrl");
			获取当前服务器协议，服务器地址，服务器端口号和项目名称
			String context =  request.getScheme() //当前链接使用的协议
				    +"://" + request.getServerName()//服务器地址
				    + ":" + request.getServerPort() //端口号
				    + request.getContextPath() //应用名称，如果应用名称为
				    ;
			分割成重定向的url地址
			String path = preVisitUrl.substring(context.length()+1, preVisitUrl.length());
			logger.info("登录失败跳转地址    "+path);
			return  path;
		}
		
		判断是否为已经有用户登录
		if(!SecurityContextHolder.getContext().getAuthentication().getName().
				  equals("anonymousUser")){
			获取session中的url
			String preVisitUrl = (String) request.getSession().getAttribute("preVisitUrl");
			判断是否为login界面进入  默认跳转/home
			if(StringUtils.isBlank(preVisitUrl) || preVisitUrl.contains("/login")){
				logger.info("login的跳转地址       /   ");
				return "redirect:/";
			}
			获取当前服务器协议，服务器地址，服务器端口号和项目名称
			String context =  request.getScheme() //当前链接使用的协议
				    +"://" + request.getServerName()//服务器地址
				    + ":" + request.getServerPort() //端口号 
				    + request.getContextPath() //应用名称，如果应用名称为
				    ;
			分割成重定向的url地址
			String path = preVisitUrl.substring(context.length()+1, preVisitUrl.length());
			logger.info("login的跳转地址    "+path);
			return "redirect:/" + path;
		}
		保存用户进来时的网页信息
		if(StringUtils.isNotBlank(request.getHeader("Referer"))){
			request.getSession().setAttribute("preVisitUrl", request.getHeader("Referer"));
		}
		logger.info("login的跳转地址    "+"sys/login");
		return "sys/login";
		
	}*/
	/*@RequestMapping("/login")
	public void login(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		//判断是否为已经有用户登录
		if(!SecurityContextHolder.getContext().getAuthentication().getName().
				equals("anonymousUser")){
			response.getWriter().print("不可重复登录");
		}
	}*/
}
