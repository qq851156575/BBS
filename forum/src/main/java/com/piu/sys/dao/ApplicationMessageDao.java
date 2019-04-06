package com.piu.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.piu.base.CrudDao;
import com.piu.sys.entity.ApplicationMessage;
import com.piu.sys.entity.Dict;

@Mapper
public interface ApplicationMessageDao extends CrudDao<ApplicationMessage>{
	ApplicationMessage findByUserId(String userID,String delFlag,String result);
}
