package com.piu.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.piu.base.CrudService;
import com.piu.base.DataEntity;
import com.piu.sys.dao.ApplicationMessageDao;
import com.piu.sys.dao.RoleDao;
import com.piu.sys.entity.ApplicationMessage;
import com.piu.sys.entity.Role;
import com.piu.sys.entity.User;
import com.piu.sys.utils.UserUtils;

@Service
public class ApplicationMessageService extends CrudService<ApplicationMessageDao,ApplicationMessage>{
	@Autowired
	private ApplicationMessageDao applicationMessageDao;
	@Autowired
	private RoleDao roleDao;
	/**
	 * 查询是否可以申请吧主
	 * 查看是否处在申请中。。。
	 * @return
	 */
	@Transactional(readOnly=true)
	public boolean isApply() {
		return applicationMessageDao.findByUserId(UserUtils.getCurrentUser().getId(),
				DataEntity.DEL_FLAG_NORMAL, 
				UserUtils.RESULT_UNTREATED)==null? true:false;
	}
	/**
	 * 处理是否成为吧主
	 * @param id
	 * @param dispose
	 */
	public void dispose(String id,String dispose) {
		ApplicationMessage applicationMessage = applicationMessageDao.get(id);
		//0为通过  1为驳回
		if(dispose.equals("0")) {
			Role role = new Role();
			role.setUserId(applicationMessage.getCreateBy().getId());
			role.setRoleId(UserUtils.ROLE_BAR);
			role.setPlate(applicationMessage.getApplicationPlate());
			roleDao.insert(role);
			applicationMessage.setResult(UserUtils.RESULT_PASS);
			super.save(applicationMessage);
			
		}else if(dispose.equals("1")) {
			applicationMessage.setResult(UserUtils.RESULT_NOT_PASS);
			super.save(applicationMessage);
		}
		super.delete(applicationMessage);
	}
}
