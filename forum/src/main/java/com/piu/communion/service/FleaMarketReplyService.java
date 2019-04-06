package com.piu.communion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.piu.base.CrudService;
import com.piu.communion.dao.FleaMarketReplyDao;
import com.piu.communion.entity.FleaMarketReply;
import com.piu.communion.entity.MakeFriendsReply;

@Service
public class FleaMarketReplyService extends CrudService<FleaMarketReplyDao, FleaMarketReply>{


	public List<FleaMarketReply> findByPostId(FleaMarketReply fleaMarketReply){
		return dao.findByPostId(fleaMarketReply);
	}

}
