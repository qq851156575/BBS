package com.piu.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.piu.activity.entity.SchoolActivity;
import com.piu.activity.service.SchoolActivityService;
import com.piu.base.BaseController;
import com.piu.communion.entity.ExchangeOfLearning;
import com.piu.communion.entity.FleaMarket;
import com.piu.communion.entity.MakeFriends;
import com.piu.communion.service.ExchangeOfLearningReplyService;
import com.piu.communion.service.ExchangeOfLearningService;
import com.piu.communion.service.FleaMarketService;
import com.piu.communion.service.MakeFriendsReplyService;
import com.piu.communion.service.MakeFriendsService;
import com.piu.software.entity.Download;
import com.piu.software.service.DownloadService;

@Controller
@RequestMapping("/delete")
@PreAuthorize("isAuthenticated()")
public class DeleteManageController extends BaseController{
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
	@Autowired
	private FleaMarketService fleaMarketService;
	@Autowired
	private DownloadService downloadService;
	
	@RequestMapping("post")
	public String post(@RequestParam String id,@RequestParam String plate) {
		switch (plate) {
		case ACTIVITY:
			SchoolActivity schoolActivity = new SchoolActivity();
			schoolActivity.setId(id);
			schoolActivityService.delete(schoolActivity);
			break;
		case EXCHANGE_OF_LEARNING:
			ExchangeOfLearning exchangeOfLearning = new ExchangeOfLearning();
			exchangeOfLearning.setId(id);
			exchangeOfLearningService.delete(exchangeOfLearning);
			break;
		case FLEA_MARKET:
			FleaMarket fleaMarket = new FleaMarket();
			fleaMarket.setId(id);
			fleaMarketService.delete(fleaMarket);
			break;
		case MAKE_FRIENDS:
			MakeFriends makeFriends = new MakeFriends();
			makeFriends.setId(id);
			makeFriendsService.delete(makeFriends);
			break;
		}
		return "redirect:/manage/postManagement";
	}
	/**
	 * 资源的删除
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("resource")
	public String resourceDelete(@RequestParam String id) {
			Download download = downloadService.get(id);
			downloadService.delete(download);
		return "redirect:" + "/manage/resourceManagement";
	}
}
