package com.piu.sys.entity;

import com.piu.base.DataEntity;

public class Role extends DataEntity<Role>{
    private String enName;
    private String cnName;
    private String userId;
    private String roleId;
    private String plate;
    
    public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.roleId = id;
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.id = roleId;
		this.roleId = roleId;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
    
    
}
