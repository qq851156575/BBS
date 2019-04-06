package com.piu.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.piu.base.CrudDao;
import com.piu.sys.entity.PostAndReply;
@Mapper
public interface PostAndReplyDao extends CrudDao<PostAndReply>{
	List<PostAndReply> findPostList(PostAndReply postAndReply);
	List<PostAndReply> findReplyToOthersList(PostAndReply postAndReply);
	List<PostAndReply> findOthersReplyToMeList(PostAndReply postAndReply);
}
