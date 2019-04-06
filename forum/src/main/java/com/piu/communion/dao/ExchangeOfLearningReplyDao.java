package com.piu.communion.dao;

import java.util.List;

import com.piu.communion.entity.ExchangeOfLearning;
import org.apache.ibatis.annotations.Mapper;

import com.piu.base.CrudDao;
import com.piu.communion.entity.ExchangeOfLearningReply;

@Mapper
public interface ExchangeOfLearningReplyDao extends CrudDao<ExchangeOfLearningReply>{

	public List<ExchangeOfLearningReply> findByPostId(ExchangeOfLearningReply exchangeOfLearningReply);
	public void audit(ExchangeOfLearningReply exchangeOfLearningReply);
}
