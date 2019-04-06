package com.piu.communion.entity;

import com.piu.base.DataEntity;
import com.piu.sys.entity.User;

import java.util.Date;

public class FleaMarketReply extends DataEntity<FleaMarketReply> {

	
	private static final long serialVersionUID = 1L;

	private FleaMarket fleaMarket;

	private User user;

	private String replyContent;

	private Date replyDate;

	private String superiorReply;
	private Integer floor;
	private String superiorId;
	private User superiorUser;

	
	public FleaMarketReply() {
	}
	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getSuperiorReply() {
		return superiorReply;
	}

	public void setSuperiorReply(String superiorReply) {
		this.superiorReply = superiorReply;
	}

	public String getSuperiorId() {
		return superiorId;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	public User getSuperiorUser() {
		return superiorUser;
	}

	public void setSuperiorUser(User superiorUser) {
		this.superiorUser = superiorUser;
	}
	public FleaMarket getFleaMarket() {
		return fleaMarket;
	}
	public void setFleaMarket(FleaMarket fleaMarket) {
		this.fleaMarket = fleaMarket;
	}

	
}
