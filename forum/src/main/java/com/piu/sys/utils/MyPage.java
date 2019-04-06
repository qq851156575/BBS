package com.piu.sys.utils;

import com.github.pagehelper.PageInfo;

public class MyPage<T> extends PageInfo<T>{
	
	private static final int  PAGE_NUM = 1;
	private static final int  PAGE_SIZE = 5;
	
	public MyPage() {
		super();
		super.setPageNum(PAGE_NUM);
		super.setPageSize(PAGE_SIZE);
	}

}
