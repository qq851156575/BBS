package com.piu.communion.entity;

import com.piu.base.DataEntity;
import com.piu.software.entity.Download;
import com.piu.sys.entity.User;

import java.util.Date;

public class MakeFriends extends DataEntity<MakeFriends>{

	private static final long serialVersionUID = 1L;

	private Download download;
	private String downUrl;

	private User user;

	private Integer hot;

	private String content;

	private String status="0";

	private Integer type;

	private Date postingTime;

	private String title;

	private Integer floorCount = 0;

	public MakeFriends(){
		this.download = new Download();
	}
	public Integer getFloorCount() {
		return floorCount;
	}

	public void setFloorCount(Integer floorCount) {
		this.floorCount = floorCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getPostingTime() {
		return postingTime;
	}

	public void setPostingTime(Date postingTime) {
		this.postingTime = postingTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Download getDownload() {
		return download;
	}

	public void setDownload(Download download) {
		this.download = download;
	}
	
	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	
}
