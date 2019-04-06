package com.piu.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piu.base.CrudService;
import com.piu.base.utils.Constant;
import com.piu.sys.dao.RoleDao;
import com.piu.sys.dao.UserDao;
import com.piu.sys.entity.Role;
import com.piu.sys.entity.User;
@Service
public class UserService extends CrudService<UserDao, User> {

	@Autowired
	UserDao userDao;
	@Autowired
	RoleDao roleDao;
	
	public User findByUserName (String userName){
		return userDao.findByUserName(userName);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(User entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			/*默认存入user用户角色 不能存入name否则会存role表*/
			Role role = new Role();
			role.setId(roleDao.byName(Constant.DEFAULT_ROLE_LEVEL).getId());
			role.setUserId(entity.getId());
			userDao.insert(entity);
			roleDao.insert(role);
		}else{
			entity.preUpdate();
			userDao.update(entity);
		}
	}
}
