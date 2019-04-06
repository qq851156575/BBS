package com.piu.sys.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.piu.base.DataEntity;
import com.piu.sys.utils.UserUtils;

public class User extends DataEntity<User>{

	
	@NotBlank(message="请填写用户名！")
	private String userName;
	@NotBlank(message="请填写密码！")
	@Min(value=6,message="密码最少为六个字符！")
	private String password;
	@Pattern(regexp="0?(13|14|15|17|18|19)[0-9]{9}",message="请填写正确的手机格式！")
	private String phone;
	@NotBlank(message="请填写邮箱！")
	@Email(message="请填写正确的邮箱！")
	private String mail;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Past(message="请填写正确的日期！")
	private Date birthday;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Past(message="请填写正确的日期！")
	private Date dateOfAdmission;
	private String img;
	private String verificationCode;
	private List<Role> roles;
	
	public User() {
		this.img=UserUtils.DEFAULT_HEAD_IMAGE;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getDateOfAdmission() {
		return dateOfAdmission;
	}
	public void setDateOfAdmission(Date dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public void preInsert() {
		if (!this.isNewRecord){
			setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}
	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}
	public String getDelFlag(){
		return this.delFlag;
	}

}
