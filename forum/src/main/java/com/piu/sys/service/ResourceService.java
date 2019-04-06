package com.piu.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piu.base.CrudService;
import com.piu.sys.dao.ResourceDao;
import com.piu.sys.entity.Resource;
import com.piu.sys.utils.UserUtils;
@Service
@Transactional(readOnly=true)
public class ResourceService extends CrudService<ResourceDao, Resource>{
	@Autowired
	private ResourceDao resourceDao;
	
	public List<Resource> findResourceList(Resource resource){
		String userId = UserUtils.getCurrentUser().getId();
		resource.setUserId(userId);
		return resourceDao.findResourceList(resource);
	}
}
