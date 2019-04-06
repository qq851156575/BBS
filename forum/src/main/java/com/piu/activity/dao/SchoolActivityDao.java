package com.piu.activity.dao;

import java.util.List;

import com.piu.communion.entity.MakeFriends;
import org.apache.ibatis.annotations.Mapper;

import com.piu.activity.entity.SchoolActivity;
import com.piu.base.CrudDao;

@Mapper
public interface SchoolActivityDao extends CrudDao<SchoolActivity>{
		public List<SchoolActivity> findLast(SchoolActivity schoolActivity);
	public void audit(SchoolActivity schoolActivity);
}
