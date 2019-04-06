package com.piu.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.piu.activity.entity.SchoolActivity;
import com.piu.activity.service.SchoolActivityService;
import com.piu.base.BaseController;
import com.piu.communion.entity.ExchangeOfLearning;
import com.piu.communion.entity.MakeFriends;
import com.piu.communion.service.ExchangeOfLearningReplyService;
import com.piu.communion.service.ExchangeOfLearningService;
import com.piu.communion.service.MakeFriendsReplyService;
import com.piu.communion.service.MakeFriendsService;

@Controller
@RequestMapping("/jump")
@PreAuthorize("isAuthenticated()")
public class JumpController extends BaseController{
	@Autowired
	private ExchangeOfLearningService exchangeOfLearningService;
	@Autowired
	private ExchangeOfLearningReplyService exchangeOfLearningReplyService;
	@Autowired
	private MakeFriendsService makeFriendsService;
	@Autowired
	private MakeFriendsReplyService makeFriendsReplyService;
	@Autowired
	private SchoolActivityService schoolActivityService;
	
	
	@RequestMapping("post")
	public String post(@RequestParam String id,@RequestParam String plate) {
		switch (plate) {
		case ACTIVITY:
			return "redirect:/schoolActivity/details?id="+id;
		case EXCHANGE_OF_LEARNING:
			return "redirect:/exchangeOfLearning/post?id="+id;
		case FLEA_MARKET:
			return "redirect:/fleaMarket/post?id="+id;
		case MAKE_FRIENDS:
			return "redirect:/makeFriends/post?id="+id;
		default:
			return "redirect:/";
		}
		
	}
	
}
