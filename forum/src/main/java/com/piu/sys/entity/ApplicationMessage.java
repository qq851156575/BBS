package com.piu.sys.entity;

import java.util.Date;

import com.piu.base.DataEntity;

public class ApplicationMessage extends DataEntity<ApplicationMessage>{
	
	private String applicationContent;
	private String applicationPlate;
	private Date applicationTime;
	private String result;
	
	
	public String getApplicationContent() {
		return applicationContent;
	}
	public void setApplicationContent(String applicationContent) {
		this.applicationContent = applicationContent;
	}
	public String getApplicationPlate() {
		return applicationPlate;
	}
	public void setApplicationPlate(String applicationPlate) {
		this.applicationPlate = applicationPlate;
	}
	public Date getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setDelflag(String delFlag) {
		this.delFlag = delFlag;
	}
	public User getCreateBy() {
		return this.createBy;
	}

}
