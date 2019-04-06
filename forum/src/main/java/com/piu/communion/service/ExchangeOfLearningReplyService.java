package com.piu.communion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piu.base.CrudService;
import com.piu.communion.dao.ExchangeOfLearningReplyDao;
import com.piu.communion.entity.ExchangeOfLearningReply;

@Service
public class ExchangeOfLearningReplyService extends CrudService<ExchangeOfLearningReplyDao, ExchangeOfLearningReply>{

	@Autowired
	ExchangeOfLearningReplyDao exchangeOfLearningReplyDao;
	
	public List<ExchangeOfLearningReply> findByPostId(ExchangeOfLearningReply exchangeOfLearningReply){
		return exchangeOfLearningReplyDao.findByPostId(exchangeOfLearningReply);
	}

	public void audit(ExchangeOfLearningReply exchangeOfLearningReply){
		dao.audit(exchangeOfLearningReply);
	}
}
