package com.piu.sys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.piu.base.BaseController;
import com.piu.email.MyMailMessage;
import com.piu.sys.entity.ApplicationMessage;
import com.piu.sys.entity.User;
import com.piu.sys.service.ApplicationMessageService;
import com.piu.sys.service.RoleService;
import com.piu.sys.service.UserService;
import com.piu.sys.utils.UserUtils;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService; 
	@Autowired
	ApplicationMessageService applicationMessageService;
	
	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {

		if (StringUtils.isNotBlank(id)) {
			return userService.get(id);
		} else {
			return new User();
		}
	}
	/**
	 * 修改个人信息
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="settings/base")
	public String setProfile(HttpServletRequest request,HttpServletResponse response,Model model){
		/*获取当前登录用户信息，放入session中*/
		User currentUser =  UserUtils.getCurrentUser();
		request.getSession().setAttribute("currentUser", currentUser);
		model.addAttribute("currentUser", currentUser);
		return "user/profile";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="settings/saveUser")
	public String saveUser(@Validated User user,BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response,Model model){
		
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			StringBuilder errors = new StringBuilder();
			for(ObjectError error:errorList) {
				 FieldError fieldError = (FieldError) error;
	                // 属性
	                String field = fieldError.getField();
	                // 错误信息
	                String message = fieldError.getDefaultMessage();
	                errors.append(field+":"+message);
			}
			logger.warn(errors.toString());
			model.addAttribute("message", "保存失败！请填写正确的信息！");
			return "user/profile";
		}else {
			try {
				/*保存用户信息*/
				userService.save(user);
				request.getSession().setAttribute("currentUser", user);
				model.addAttribute("currentUser", user);
				/*model.addAttribute("message", "保存成功");*/
				return "user/profile";
			} catch (Exception e) {
				model.addAttribute("message","保存失败！");
				/*return "redirect:/user/settings/base";*/
				return "user/profile";
			}
		}
	}
	
	
	/**
	 *	安全设置
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="settings/safe")
	public String setSafe(@RequestParam(name="classify",defaultValue="1",required=false) String classify,User user,HttpServletRequest request,HttpServletResponse response,Model model ){
		model.addAttribute("classify", classify);
		User currentUser = null;
		if(request.getSession().getAttribute("currentUser")==null) {
			currentUser =  UserUtils.getCurrentUser();
			request.getSession().setAttribute("currentUser", currentUser);
		}else {
			currentUser = (User) request.getSession().getAttribute("currentUser");
		}
		
		switch (classify) {
		case "1":
			String currentPwd = request.getParameter("currentPwd");
			if(StringUtils.isNotBlank(currentPwd)) {
				if(currentUser.getPassword().equals(currentPwd)) {
					try {
						String pwd = request.getParameter("newPwd");
						String second = request.getParameter("newSecondPwd");
						if(pwd.equals(second)) {
							currentUser.setPassword(pwd);
							userService.save(currentUser);
							return "redirect:"+"/logout";
						}else {
							model.addAttribute("message", "两次密码不同，请重新输入");
						}
						
					} catch (Exception e) {
						model.addAttribute("message", "保存失败，请重新尝试或联系系统管理员！");
					}finally {
						request.getSession().removeAttribute("currentUser");
					}
				}else {
					model.addAttribute("message", "当前密码输入错误，请重新输入!");
				}
			}
			break;
		case "2":
			Boolean isPass = (Boolean) request.getSession().getAttribute("isPass");
			if(isPass==null) {
				request.getSession().setAttribute("isPass", false);
				isPass = false;
			}
			if(!isPass) {
				String captcha = (String) request.getSession().getAttribute("captcha");
				if(StringUtils.isNotBlank(captcha)) {
						if(captcha.equalsIgnoreCase(request.getParameter("captcha"))) {
							request.getSession().setAttribute("isPass", true);
							model.addAttribute("isPass", true);
							request.getSession().removeAttribute("captcha");
						}else {
							model.addAttribute("isPass", false);
						}
					}else {
						model.addAttribute("isPass", false);
					}
			}else{
				model.addAttribute("isPass", true);
				try {
					String captcha = (String) request.getSession().getAttribute("captcha");
					if(StringUtils.isNotBlank(captcha)) {
						if(captcha.equalsIgnoreCase(request.getParameter("newCaptcha"))) {
							String newEmail = request.getParameter("newEmail");
							currentUser.setMail(newEmail);
							userService.save(currentUser);
							model.addAttribute("message", "您的邮箱已成功修改为"+newEmail);
						}
					}
				} catch (Exception e) {
					model.addAttribute("message", "保存失败，请重新尝试或联系系统管理员！");
				}finally {
					request.getSession().removeAttribute("currentUser");
				}
			}
			
			
			break;
		}
		
		return "user/safe";
	}
	
	@RequestMapping(value="settings/forgotPassword")
	public String forgotPassword(HttpServletRequest request,HttpServletResponse response){
		return "user/forgotPassword";
	}
	
	@RequestMapping(value="settings/retrievePassword")
	public String retrievePassword(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			User missUser = (User) request.getSession().getAttribute("missUser");
			model.addAttribute("message", "您所修改的账户为："+missUser.getUserName());
		}catch (Exception e) {
			model.addAttribute("message", "错误操作！请重新尝试！");
			return "user/forgotPassword";
		}
		return "user/retrievePassword";
	}
	@RequestMapping(value="settings/newPassword")
	public String newPassword(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			User missUser = (User) request.getSession().getAttribute("missUser");
			String pwd = request.getParameter("newPwd");
			missUser.setPassword(pwd);
			userService.save(missUser);
		} catch (Exception e) {
			model.addAttribute("message", "修改失败！请重新尝试！");
			return "user/forgotPassword";
		}finally {
			request.getSession().removeAttribute("missUser");
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="forgotPassword/getUsername")
	public void getUsername(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			String email = request.getParameter("email");
			response.setCharacterEncoding("utf-8");
			if(StringUtils.isNotBlank(email)) {
				User user = new User();
				user.setMail(email);
				User resultUser = userService.get(user);
				if(resultUser!=null) {
					request.getSession().setAttribute("missUser", resultUser);
					model.addAttribute("message", "您所修改的账户为："+resultUser.getUserName());
					response.getWriter().print(true);
				}
				else
					response.getWriter().print("此邮箱未绑定用户！");
			}else {
				response.getWriter().print("请填写邮箱");
			}
		} catch (IOException e) {
			response.setStatus(404);
		}
		
	}
	
	/**
	 *   安全设置中发送验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="settings/sendCaptcha")
	public void sendCaptcha(HttpServletRequest request,HttpServletResponse response){
		try {
			String email = request.getParameter("email");
			if(StringUtils.isBlank(email)) 
				email = UserUtils.getCurrentUser().getMail();
			MyMailMessage mailMessage = new MyMailMessage(email);
			String captcha = mailMessage.getEmailVerif();
			request.getSession().setAttribute("captcha", captcha);			
			//发送邮件
			super.mailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(404);
		}
	}
	/**
	 * 	验证验证码是否正确
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="settings/isRealCaptcha")
	public void isRealCaptcha(HttpServletRequest request,HttpServletResponse response){
		try {
			String captcha = (String) request.getSession().getAttribute("captcha");
			if(StringUtils.isNotBlank(captcha)) {
					if(captcha.equalsIgnoreCase(request.getParameter("captcha"))) {
						response.getWriter().print(true);
					}else {
						response.setCharacterEncoding("utf-8");
						response.getWriter().print("验证码错误，请重新输入！");
					}
				}else {
					response.setCharacterEncoding("utf-8");
					response.getWriter().print("系统错误!");
				}
			} catch (IOException e) {
				response.setStatus(404);
			}
		
	}
	
	/**
	 * 	验证密码是否正确
	 * @param request
	 * @param response
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="settings/isRealPwd")
	public void isRealPwd(HttpServletRequest request,HttpServletResponse response){
		try {
			if(UserUtils.getCurrentUser().getPassword().equals(request.getParameter("currentPwd"))) {
				response.getWriter().print(true);
			}else {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("密码错误，请重新输入！");
			}
				
			} catch (IOException e) {
				response.setStatus(404);
			}
		
	}
	/**
	 * 用户注册
	 * @param user
	 * @param request
	 * @param response
	 */
	@RequestMapping("/register")
	public void register(User user,HttpServletRequest request,HttpServletResponse response){
		String realEmailVerif = (String) request.getSession().getAttribute("emailVerif");
		String emailVerif = request.getParameter("emailVeritf");
		if(realEmailVerif.equalsIgnoreCase(emailVerif)){
			userService.save(user);
		}else{
			response.setStatus(404);
		}
	}
	/**
	 * 修改密码
	 * @param user
	 * @param request
	 * @param response
	 */
	@RequestMapping("/changePass")
	public String changePassword(User user,HttpServletRequest request,HttpServletResponse response){
		String realEmailVerif = (String) request.getSession().getAttribute("emailVerif");
		String emailVerif = request.getParameter("emailVeritf");
		if(realEmailVerif.equalsIgnoreCase(emailVerif)){
			userService.save(user);
		}else{
			response.setStatus(404);
		}
		
		return "user/changePassword";
	}
	/**
	 * 查看用户名是否唯一
	 * @param user
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/isUniqueUser")
	public void isUniqueUser(User user,HttpServletRequest request,HttpServletResponse response) throws IOException{
		String username = request.getParameter("userName");
		User temporaryUser = userService.findByUserName(username);
		response.setContentType("text/html;charset=UTF-8");
		if(null==temporaryUser){
			response.getWriter().print("您可以使用该用户名！");
		}else{
			response.getWriter().print("该用户名已被注册！");
		}
	}
	/**
	 * 申请吧主
	 * @param applicationMessage
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("applyForBarOwner")
	public String applyForBarOwner(@ModelAttribute ApplicationMessage applicationMessage,HttpServletRequest request,HttpServletResponse response,Model model) {
		 model.addAttribute("applicationMessage", applicationMessage);
		 model.addAttribute("isApply", applicationMessageService.isApply());
		return "user/applyFor";
	}
	@RequestMapping("saveApplyFor")
	public String saveApplyFor(@ModelAttribute ApplicationMessage applicationMessage,HttpServletRequest request,HttpServletResponse response,Model model) {
		applicationMessage.setResult(UserUtils.RESULT_UNTREATED);
		applicationMessageService.save(applicationMessage);
		return "redirect:/"+"user/applyForBarOwner";
	}
}
