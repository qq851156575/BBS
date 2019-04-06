package com.piu.sys.entity;

import java.util.Date;

import com.piu.base.DataEntity;

public class Resource extends DataEntity<Resource>{
	private String userId;
	
	private String fileName;
	
	private String summary;
	
	private Date uploadDate;

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public void setDelflag(String delFlag) {
		super.delFlag = delFlag;
	}
	
	
}
