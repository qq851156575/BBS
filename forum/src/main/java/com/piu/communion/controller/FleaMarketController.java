package com.piu.communion.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.Page;
import com.piu.base.BaseController;
import com.piu.communion.entity.FleaMarket;
import com.piu.communion.entity.FleaMarketReply;
import com.piu.communion.service.FleaMarketReplyService;
import com.piu.communion.service.FleaMarketService;
import com.piu.communion.utils.ImagesResultUtils;
import com.piu.communion.utils.UploadUtils;
import com.piu.software.entity.FileInterface;
import com.piu.software.service.DownloadService;
import com.piu.sys.utils.UserUtils;

@RequestMapping(value = "/fleaMarket")
@Controller
public class FleaMarketController extends BaseController{
	@Autowired
	DownloadService downloadService;
	@Autowired
	FleaMarketReplyService fleaMarketReplyService;
	@Autowired
	FleaMarketService fleaMarketService;

	@ModelAttribute
	public FleaMarket get(@RequestParam(required = false) String id) {

		if (StringUtils.isNotBlank(id)) {
			return fleaMarketService.get(id);
		} else {
			return new FleaMarket();
		}
	}

	@RequestMapping(value = "list")
	public String list(FleaMarket fleaMarket, Model model, HttpServletRequest request,
					   HttpServletResponse response) {
		/* 接受页码并进行分页 首次进入默认为第一页 判断是否为第一次进去 */
		if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
			fleaMarket.setPageNum(Integer.valueOf(request.getParameter("pageNum")));
		}
		/*
		 * pagehlper根据baseEntity里的pageNum和pageSize进行分页 无需手动添加 如需要更改参数
		 * 在com.piu.base.utils.Constant下更改
		 */
		Page<FleaMarket> page = (Page<FleaMarket>) fleaMarketService
				.findList(fleaMarket);
		/*
		 * 正则表达过滤HTML标签
		 */
		for (FleaMarket list1 : page.getResult()) {
			Pattern pattern = Pattern.compile("<([^>]*)>");
			Matcher matcher = pattern.matcher(list1.getContent());
			StringBuffer sb = new StringBuffer();
			boolean result1 = matcher.find();
			while (result1) {
				matcher.appendReplacement(sb, "");
				result1 = matcher.find();
			}
			matcher.appendTail(sb);
			list1.setContent(sb.toString());
		}
		model.addAttribute("page", page);
		/* model.addAttribute("exchangeOfLearning", exchangeOfLearning); */

		return "communion/fleaMarketList";
	}

	@RequestMapping(value = "post")
	public String post(FleaMarket fleaMarket, FleaMarketReply fleaMarketReply, Model model, HttpServletRequest request,
					   HttpServletResponse response) {
		model.addAttribute("fleaMarket", fleaMarket);
		fleaMarketReply.setFleaMarket(fleaMarket);
		/* 接受页码并进行分页 首次进入默认为第一页 判断是否为第一次进去 */
		if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
			fleaMarketReply.setPageNum(Integer.valueOf(request.getParameter("pageNum")));
		}
		/*
		 * pagehlper根据baseEntity里的pageNum和pageSize进行分页 无需手动添加 如需要更改参数
		 * 在com.piu.base.utils.Constant下更改
		 */
		/*Page<ExchangeOfLearningReply> page = (Page<ExchangeOfLearningReply>)*/
		Page<FleaMarketReply> page = (Page<FleaMarketReply>) fleaMarketReplyService.findByPostId(fleaMarketReply);
		/*for (ExchangeOfLearningReply e : page.getResult()) {
e.setReplyContent(HtmlUtils.filterHtmlTag(e.getReplyContent()));
		}*/
		model.addAttribute("page", page);
		return "communion/fleaMarketPosts";
	}

	@RequestMapping(value = "delete")
	public String delete(FleaMarket fleaMarket, RedirectAttributes redirectAttributes) {
		fleaMarketService.delete(fleaMarket);

		return "redirect:" + "/fleaMarket/list";
	}

	@RequestMapping(value = "form")
	public String form(FleaMarket fleaMarket, RedirectAttributes redirectAttributes, Model model) {
model.addAttribute("fleaMarket",fleaMarket);
		return "communion/fleaMarketAdd";
	}

	@RequestMapping(value = "save")
	public String save(@Valid FleaMarket fleaMarket, RedirectAttributes redirectAttributes,
					   Model model,HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("imageFile");
		if(StringUtils.isNotBlank(file.getOriginalFilename())){
			if(StringUtils.isNotBlank(files.get(0).getOriginalFilename())) {
				List<FileInterface> list = new ArrayList<>();
				for (MultipartFile multipartFiles : files) {
					FileInterface fileInterface = new FileInterface(fleaMarket.getDownload());
					fileInterface.setPath( request.getContextPath()+"/upload/files/images/"+ UploadUtils.uploadFiles(multipartFiles).getName());
					list.add(fileInterface);
				}
				fleaMarket.getDownload().setFileInterfaceList(list);
			}
			fleaMarket.getDownload().setPath(UploadUtils.uploadFiles(file).getPath());
			fleaMarket.getDownload().setSize(UploadUtils.uploadSize(file));
			fleaMarket.getDownload().setUploadDate(new Date());
			fleaMarket.getDownload().setUser(UserUtils.getCurrentUser());
			downloadService.save(fleaMarket.getDownload());
			fleaMarket.setDownUrl(request.getContextPath()+"/download/details?id="+fleaMarket.getDownload().getId());
		}

		fleaMarket.setUser(UserUtils.getCurrentUser());
		fleaMarket.setPostingTime(new Date());
		fleaMarket.setContent(ImagesResultUtils.imagesPathReplacement(fleaMarket.getContent()));
		fleaMarketService.save(fleaMarket);

		return "redirect:" + "/fleaMarket/list";
	}
    @RequestMapping(value = "updateStatus")
	public String updateStatus(FleaMarket fleaMarket){
fleaMarket.setStatus("1");
fleaMarketService.updateStatus(fleaMarket);

		return "redirect:" + "/fleaMarket/list";
	}

	@RequestMapping(value = "removeStatus")
	public String removeStatus(FleaMarket fleaMarket){
		fleaMarket.setStatus("0");
		fleaMarketService.updateStatus(fleaMarket);

		return "redirect:" + "/fleaMarket/list";
	}

    @RequestMapping(value = "audit")
    public String audit(FleaMarket fleaMarket, RedirectAttributes redirectAttributes) {
        fleaMarketService.audit(fleaMarket);

        return "redirect:" + "/fleaMarket/list";
    }
}
