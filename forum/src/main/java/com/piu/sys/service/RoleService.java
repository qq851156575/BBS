package com.piu.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piu.base.CrudService;
import com.piu.sys.dao.RoleDao;
import com.piu.sys.entity.Role;

@Service
public class RoleService extends CrudService<RoleDao, Role> {

	@Autowired
	RoleDao roleDao;
	
	
}
