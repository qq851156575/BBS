package com.piu.communion.dao;

import com.piu.base.CrudDao;
import com.piu.communion.entity.ExchangeOfLearningReply;
import com.piu.communion.entity.MakeFriendsReply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MakeFriendsReplyDao extends CrudDao<MakeFriendsReply>{

	public List<MakeFriendsReply> findByPostId(MakeFriendsReply makeFriendsReply);
	public void audit(MakeFriendsReply makeFriendsReply);
}
