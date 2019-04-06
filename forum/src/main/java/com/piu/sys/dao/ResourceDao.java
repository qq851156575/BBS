package com.piu.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.piu.base.CrudDao;
import com.piu.sys.entity.Resource;
@Mapper
public interface ResourceDao extends CrudDao<Resource>{
	
	List<Resource> findResourceList(Resource resource);

}
