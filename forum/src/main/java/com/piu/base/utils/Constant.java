package com.piu.base.utils;

import java.io.File;

public class Constant {
	/*默认当前页数*/
	public static final Integer DEFAULT_PAGE_NUM = 1;
	/*每页显示数据*/
	public static final Integer DEFAULT_PAGE_SIZE = 20;
	/*默认权限等级*/
	public static final String DEFAULT_ROLE_LEVEL = "USER";
	/*绝对路径分隔符*/
	public static final String URL_SEPARATOR = File.separator;
	/*项目名称*/
	public static final String PROJECT_NAME = "piu";
	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
	
}
