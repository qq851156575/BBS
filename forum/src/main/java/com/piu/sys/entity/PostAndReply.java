package com.piu.sys.entity;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.piu.base.DataEntity;

public class PostAndReply extends DataEntity<PostAndReply>{
	//用户id
	private String userId;
	//标题
	private String title;
	//评论人
	private String critic;
	//时间
	private Date time;
	//回复内容
	private String reply;
	//类别
	private String category;
	
	public PostAndReply() {
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCritic() {
		return critic;
	}
	public void setCritic(String critic) {
		this.critic = critic;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		/**
		 * 过滤html标签
		 */
		Pattern pattern = Pattern.compile("<([^>]*)>");
		Matcher matcher = pattern.matcher(reply);
		StringBuffer sb = new StringBuffer();
		boolean result = matcher.find();
		while (result) {
			matcher.appendReplacement(sb, "");
			result = matcher.find();
		}
		matcher.appendTail(sb);
		this.reply = sb.toString();
	}
	public void setDelflag(String delFlag) {
		super.delFlag = delFlag;
	}
	
	
}
