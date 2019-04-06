package com.piu.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piu.base.CrudService;
import com.piu.sys.dao.PostAndReplyDao;
import com.piu.sys.entity.PostAndReply;
import com.piu.sys.utils.UserUtils;
@Service
public class PostAndReplyService extends CrudService<PostAndReplyDao, PostAndReply>{
	@Autowired
	private PostAndReplyDao postAndReplyDao;
	
	public List<PostAndReply> findPostList(PostAndReply postAndReply){
		postAndReply.setUserId(UserUtils.getCurrentUser().getId());
		return (List<PostAndReply>) postAndReplyDao.findPostList(postAndReply);
	}
	
	public List<PostAndReply> findOthersReplyToMeList(PostAndReply postAndReply){
		postAndReply.setUserId(UserUtils.getCurrentUser().getId());
		return (List<PostAndReply>) postAndReplyDao.findOthersReplyToMeList(postAndReply);
	}
	
	public List<PostAndReply> findReplyToOthersList(PostAndReply postAndReply){
		postAndReply.setUserId(UserUtils.getCurrentUser().getId());
		return (List<PostAndReply>) postAndReplyDao.findReplyToOthersList(postAndReply);
	}
}
