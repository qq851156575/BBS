package com.piu.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.piu.base.CrudDao;
import com.piu.sys.entity.User;

@Mapper
public interface UserDao extends CrudDao<User>{
	User findByUserName(String userName);
}
