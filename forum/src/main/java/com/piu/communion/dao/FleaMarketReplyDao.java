package com.piu.communion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.piu.base.CrudDao;
import com.piu.communion.entity.FleaMarketReply;
import com.piu.communion.entity.MakeFriendsReply;

@Mapper
public interface FleaMarketReplyDao extends CrudDao<FleaMarketReply>{

	public List<FleaMarketReply> findByPostId(FleaMarketReply fleaMarketReply);
}
