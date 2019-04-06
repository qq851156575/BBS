package com.piu.sys.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.piu.sys.entity.CustomUser;
import com.piu.sys.entity.User;

public class UserUtils {
	/*默认头像的base64*/
	public static final String DEFAULT_HEAD_IMAGE="data:image/png;base64,/9j/4AAQSkZJRgABAQAAAQABAAD//gA7Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcgSlBFRyB2NjIpLCBxdWFsaXR5ID0gOTAK/9sAQwADAgIDAgIDAwMDBAMDBAUIBQUEBAUKBwcGCAwKDAwLCgsLDQ4SEA0OEQ4LCxAWEBETFBUVFQwPFxgWFBgSFBUU/9sAQwEDBAQFBAUJBQUJFA0LDRQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQU/8AAEQgAPAA8AwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8A+t8CjFJ+VH5UALgUYFavh3w1e+JrwwWiDavMkr8Ig9z/AEr0S1+DenrEBc31zJJjkxBUH5EGgDybAowK7/xF8JrmwgefTZzeooyYXXEmPbHB+nFcAQQcHg+lABgUYFJ+VH5UALn3oz70fjRzQB734F0iPR/DNkigCSZBNK3cswz+gwPwrfz71jeDdTTVfDOnzI2SsSxuPRlGD/LP41tc0AJn3rxX4o6RHpfiUywgJHdJ5xAHAbJDfyz+Ne1814z8WdTS98SJBG24WsQRiP7xJJH6igDis+9Gfej8aPx/SgA/Oj86MfWux+HPg1fEV611dqTYW5GV/wCej9dv07n8PWgDS+F1nr0E5ntowulyn959oJVX90759+ler/nSJGI0VFUKqjAVeABTsGgDM8Q/2odMlGkCL7WRgGY4wPbtn68V4BqVrdWd7NFexyx3QYlxL94k9/fPrX0lj61z3jLwjB4p05lwEvYwTDN3B/un2NAHgv50fnT54JLaaSGVGSSNirKeoI4IpmPrQAfhX0J4T0hdE8PWVqFw4jDSe7nk/qf0rwGxjWS9t1YZVpFBH419K4FACY9qMe1GKMUAGPajHtRjrRigDxv4s6Qtj4hjukXCXce44/vjg/pj864nHtXqnxmjU2WmPj5hI4B+oH+FeVgAigD/2Q==";
	/*管理页面指  全部 or 收到的回复*/
	public static final String MANAGE_CLASSIFY_FIRST = "1";
	/*管理页面指  回收站 or 发表的回复*/
	public static final String MANAGE_CLASSIFY_SECOND = "2";
	/*请求申请吧主的结果状态  0:未处理 ；1：通过；2：未通过；*/
	public static final String RESULT_UNTREATED = "0";
	public static final String RESULT_PASS = "1";
	public static final String RESULT_NOT_PASS = "2";
	/*权限等级  10:系统管理员 ；20：吧主；30：普通用户；*/
	public static final String ROLE_ADMIN = "10";
	public static final String ROLE_BAR = "20";
	public static final String ROLE_USER = "30";
	
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public static User getCurrentUser() {
		try {
			CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (customUser != null&&customUser.getUser()!=null) {
				if(null==getSession().getAttribute("currentUser")) {
					getSession().setAttribute("currentUser", customUser.getUser());
					return customUser.getUser();
				}else {
					return (User) getSession().getAttribute("currentUser");
				}
			}
		} catch (Exception e) {
			return new User();
		}
		return new User();
	}
	/**
	 * 获取当前session
	 * @param request
	 */
	public static HttpSession getSession(){
		if(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())==null){
			return null;
		}
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		if(null==session){
			return null;
		}else{
			return session;
		}
	}
	/**
	 * 获取请求前网址的相对路径(referer)
	 * @param request
	 * @return
	 */
	public static String getRelativePath(HttpServletRequest request){
		String preVisitUrl = getAbsolutePath(request);
		//获取当前服务器协议，服务器地址，服务器端口号和项目名称
		String context =  request.getScheme() //当前链接使用的协议
			    +"://" + request.getServerName()//服务器地址
			    + ":" + request.getServerPort() //端口号 
			    + request.getContextPath() //应用名称，如果应用名称为
			    ;
		//分割成重定向的url地址
		String path = preVisitUrl.substring(context.length()+1, preVisitUrl.length());
		return path;
	}
	/**
	 * 获取请求前网址的绝对路径(referer)
	 * @param request
	 * @return
	 */
	public static String getAbsolutePath(HttpServletRequest request){
		return request.getHeader("Referer");
	}

}
