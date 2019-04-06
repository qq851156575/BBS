package com.piu.communion.service;

import com.piu.base.CrudService;
import com.piu.communion.dao.ExchangeOfLearningReplyDao;
import com.piu.communion.dao.MakeFriendsReplyDao;
import com.piu.communion.entity.ExchangeOfLearningReply;
import com.piu.communion.entity.MakeFriendsReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MakeFriendsReplyService extends CrudService<MakeFriendsReplyDao, MakeFriendsReply>{


	public List<MakeFriendsReply> findByPostId(MakeFriendsReply makeFriendsReply){
		return dao.findByPostId(makeFriendsReply);
	}

	public void audit(MakeFriendsReply makeFriendsReply){
		dao.audit(makeFriendsReply);
	}
}
