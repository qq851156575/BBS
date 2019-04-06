package com.piu.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.piu.base.CrudDao;
import com.piu.sys.entity.Role;

@Mapper
public interface RoleDao extends CrudDao<Role>{
	
	Role byName(String name);

}
