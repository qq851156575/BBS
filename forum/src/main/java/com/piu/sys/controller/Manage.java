package com.piu.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.jta.UserTransactionAdapter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.piu.base.BaseController;
import com.piu.base.utils.Constant;
import com.piu.software.entity.Download;
import com.piu.software.service.DownloadService;
import com.piu.sys.entity.ApplicationMessage;
import com.piu.sys.entity.PostAndReply;
import com.piu.sys.entity.Resource;
import com.piu.sys.entity.User;
import com.piu.sys.service.ApplicationMessageService;
import com.piu.sys.service.PostAndReplyService;
import com.piu.sys.service.ResourceService;
import com.piu.sys.service.UserService;
import com.piu.sys.utils.UserUtils;

@Controller
@RequestMapping("/manage")
@PreAuthorize("isAuthenticated()")
public class Manage extends BaseController{
	@Autowired
	private PostAndReplyService postAndReplyService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private DownloadService downloadService;
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationMessageService applicationMessageService;
	/**
	 * 评论管理
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/reviewManagement")
	public String reviewManagement(HttpServletRequest request,HttpServletResponse response,Model model){
		/*
		 * 判断进来的分类是否相同  相同则改变页码  不同则从第一页开始	
		 */
		//当前classify
		String classify = request.getParameter("classify");
		PostAndReply postAndReply = new PostAndReply();
		//上一次的classify
		String post_classify = (String) request.getSession().getAttribute("reply_classify");
		if(StringUtils.isBlank(post_classify)){
			//如果上一次的classify即说明是第一次
			classify = UserUtils.MANAGE_CLASSIFY_FIRST;
			/*request.getSession().setAttribute("reply_classify", classify);*/
		}else{
			//如果classify为空说明可能为第一次（上面已经判断过）或翻页操作  此处为翻页操作  
			if(StringUtils.isBlank(classify)){
				classify = post_classify;
			}
			if(post_classify.equals(classify)){
				if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
					postAndReply.setPageNum(Integer.valueOf(request.getParameter("pageNum")));
				}
			}
		}
		if(StringUtils.isBlank(classify) || classify.equals(UserUtils.MANAGE_CLASSIFY_FIRST)){
			/*
			 * pagehlper根据baseEntity里的pageNum和pageSize进行分页 无需手动添加 如需要更改参数
			 * 在com.piu.base.utils.Constant下更改
			 * 前端由classify来判断所选条件
			 */
			request.getSession().setAttribute("reply_classify", classify);
			Page<PostAndReply> page = (Page<PostAndReply>) postAndReplyService.findOthersReplyToMeList(postAndReply);
			model.addAttribute("page", page);
		}else if(classify.equals(UserUtils.MANAGE_CLASSIFY_SECOND)){
			request.getSession().setAttribute("reply_classify", classify);
			Page<PostAndReply> page = (Page<PostAndReply>) postAndReplyService.findReplyToOthersList(postAndReply);
			model.addAttribute("page", page);
		}
		return "user/reviewManagement";
	}
	
	/**
	 * 帖子管理
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/postManagement")
	public String postManagement(HttpServletRequest request,HttpServletResponse response,Model model){
		/*
		 * 判断进来的分类是否相同  相同则改变页码  不同则从第一页开始	
		 */
		//当前classify
		String classify = request.getParameter("classify");
		PostAndReply postAndReply = new PostAndReply();
		//上一次的classify
		String post_classify = (String) request.getSession().getAttribute("post_classify");
		if(StringUtils.isBlank(post_classify)){
			//如果上一次的classify即说明是第一次
			classify = UserUtils.MANAGE_CLASSIFY_FIRST;
			request.getSession().setAttribute("post_classify", classify);
		}else{
			//如果classify为空说明可能为第一次（上面已经判断过）或翻页操作  此处为翻页操作  
			if(StringUtils.isBlank(classify)){
				classify = post_classify;
			}
			if(post_classify.equals(classify)){
				if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
					postAndReply.setPageNum(Integer.valueOf(request.getParameter("pageNum")));
				}
			}
		}
		if(StringUtils.isBlank(classify) || classify.equals(UserUtils.MANAGE_CLASSIFY_FIRST)){
			/*
			 * pagehlper根据baseEntity里的pageNum和pageSize进行分页 无需手动添加 如需要更改参数
			 * 在com.piu.base.utils.Constant下更改
			 * 前端由classify来判断所选条件
			 */
			request.getSession().setAttribute("post_classify", classify);
			Page<PostAndReply> page = (Page<PostAndReply>) postAndReplyService.findPostList(postAndReply);
			model.addAttribute("page", page);
		}else if(classify.equals(UserUtils.MANAGE_CLASSIFY_SECOND)){
			request.getSession().setAttribute("post_classify", classify);
			postAndReply.setDelflag(Constant.DEL_FLAG_DELETE);
			Page<PostAndReply> page = (Page<PostAndReply>) postAndReplyService.findPostList(postAndReply);
			model.addAttribute("page", page);
		}
		return "user/postManagement";
	}
	
	/**
	 * 资源管理
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("resourceManagement")
	public String resourceManagement(
			@RequestParam(name="pageNum",required=false,defaultValue="1") Integer pageNum,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model){
		Resource resource = new Resource();
		resource.setPageNum(pageNum);
		/*
		 * 判断进来的分类是否相同  相同则改变页码  不同则从第一页开始	
		 */
		//当前classify
		String classify = request.getParameter("classify");
		//上一次的classify
		String post_classify = (String) request.getSession().getAttribute("resource_classify");
		if(StringUtils.isBlank(post_classify)){
			//如果上一次的classify即说明是第一次
			classify = UserUtils.MANAGE_CLASSIFY_FIRST;
			request.getSession().setAttribute("resource_classify", classify);
		}else{
			//如果classify为空说明可能为第一次（上面已经判断过）或翻页操作  此处为翻页操作  
			if(StringUtils.isBlank(classify)){
				classify = post_classify;
			}
		}
		switch (classify) {
		case UserUtils.MANAGE_CLASSIFY_FIRST:
			request.getSession().setAttribute("resource_classify", classify);
			Page<Resource> page1 = (Page<Resource>) resourceService.findResourceList(resource);
			model.addAttribute("page", page1);
			break;
		case UserUtils.MANAGE_CLASSIFY_SECOND:
			request.getSession().setAttribute("resource_classify", classify);
			resource.setDelflag(Constant.DEL_FLAG_DELETE);
			Page<Resource> page2 = (Page<Resource>) resourceService.findResourceList(resource);
			model.addAttribute("page", page2);
			break;
		}
		return "user/resourceManagement";
	}
	
	/**
	 * 用户列表
	 * @param user
	 * @param request
	 * @param pageNum
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("authorization")
	public String authorization(@ModelAttribute User user,HttpServletRequest request,
			@RequestParam(name="pageNum",required=false,defaultValue="1") Integer pageNum,
			HttpServletResponse response,
			Model model) {
		Page<User> page = (Page<User>) userService.findList(user);
		model.addAttribute("page", page);
		return "user/authorization";
	}
	/**
	 * 用来更改用户一些状态
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveUser")
	public void saveUser(HttpServletRequest request,HttpServletResponse response) {
		try {
			String delflag = request.getParameter("delflag");
			String username = request.getParameter("username");
			if(StringUtils.isNotBlank(username)) {
				User user = userService.findByUserName(username);
				if(StringUtils.isBlank(delflag)) {
					response.setStatus(404);
					return;
				}
				user.setDelFlag(delflag);
				userService.save(user);
			}else {
				response.setStatus(404);
			}
		}catch (Exception e) {
			response.setStatus(404);
		}
	}
	/**
	 * 请求管理  审批成为吧主
	 * @param pageNum
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("applyManagement")
	public String applyManagement(
			@RequestParam(name="pageNum",required=false,defaultValue="1") Integer pageNum,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model){
		ApplicationMessage applicationMessage = new ApplicationMessage();
		applicationMessage.setPageNum(pageNum);
		/*
		 * 判断进来的分类是否相同  相同则改变页码  不同则从第一页开始	
		 */
		//当前classify
		String classify = request.getParameter("classify");
		//上一次的classify
		String apply_classify = (String) request.getSession().getAttribute("apply_classify");
		if(StringUtils.isBlank(apply_classify)){
			//如果上一次的classify即说明是第一次
			classify = UserUtils.MANAGE_CLASSIFY_FIRST;
			request.getSession().setAttribute("apply_classify", classify);
		}else{
			//如果classify为空说明可能为第一次（上面已经判断过）或翻页操作  此处为翻页操作  
			if(StringUtils.isBlank(classify)){
				classify = apply_classify;
			}
		}
		switch (classify) {
		case UserUtils.MANAGE_CLASSIFY_FIRST:
			request.getSession().setAttribute("apply_classify", classify);
			Page<ApplicationMessage> page1 = (Page<ApplicationMessage>) applicationMessageService.findList(applicationMessage);
			model.addAttribute("page", page1);
			break;
		case UserUtils.MANAGE_CLASSIFY_SECOND:
			request.getSession().setAttribute("apply_classify", classify);
			applicationMessage.setDelflag(Constant.DEL_FLAG_DELETE);
			Page<ApplicationMessage> page2 = (Page<ApplicationMessage>) applicationMessageService.findList(applicationMessage);
			model.addAttribute("page", page2);
			break;
		}
		return "user/applyManagement";
	}
	
	@RequestMapping("dispose")
	public String dispose(
			@RequestParam String id,
			@RequestParam String dispose,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) {
		applicationMessageService.dispose(id, dispose);
		return "redirect:/"+"manage/applyManagement?classify=1";
	}
	
}
