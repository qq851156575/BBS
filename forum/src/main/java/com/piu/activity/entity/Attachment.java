package com.piu.activity.entity;

import com.piu.base.DataEntity;

public class Attachment extends DataEntity<Attachment>{
	
	private String activityId;
	
	private String path;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

}
