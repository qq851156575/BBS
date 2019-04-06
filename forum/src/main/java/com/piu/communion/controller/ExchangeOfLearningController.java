package com.piu.communion.controller;

import com.github.pagehelper.Page;
import com.piu.base.BaseController;
import com.piu.communion.entity.ExchangeOfLearning;
import com.piu.communion.entity.ExchangeOfLearningReply;
import com.piu.software.entity.FileInterface;
import com.piu.communion.service.ExchangeOfLearningReplyService;
import com.piu.communion.service.ExchangeOfLearningService;
import com.piu.communion.utils.ImagesResultUtils;
import com.piu.communion.utils.UploadUtils;
import com.piu.software.service.DownloadService;
import com.piu.sys.utils.UserUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping(value = "/exchangeOfLearning")
@Controller
public class ExchangeOfLearningController extends BaseController{
	@Autowired
	DownloadService downloadService;
	@Autowired
	ExchangeOfLearningReplyService exchangeOfLearningReplyService;
	@Autowired
	ExchangeOfLearningService exchangeOfLearningService;

	@ModelAttribute
	public ExchangeOfLearning get(@RequestParam(required = false) String id) {

		if (StringUtils.isNotBlank(id)) {
			return exchangeOfLearningService.get(id);
		} else {
			return new ExchangeOfLearning();
		}
	}

	@RequestMapping(value = "list")
	public String list(ExchangeOfLearning exchangeOfLearning, Model model, HttpServletRequest request,
					   HttpServletResponse response) {
		/* 接受页码并进行分页 首次进入默认为第一页 判断是否为第一次进去 */
		if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
			exchangeOfLearning.setPageNum(Integer.valueOf(request.getParameter("pageNum")));
		}
		/*
		 * pagehlper根据baseEntity里的pageNum和pageSize进行分页 无需手动添加 如需要更改参数
		 * 在com.piu.base.utils.Constant下更改
		 */
		Page<ExchangeOfLearning> page = (Page<ExchangeOfLearning>) exchangeOfLearningService
				.findList(exchangeOfLearning);
		/*
		 * 正则表达过滤HTML标签
		 */
		for (ExchangeOfLearning list1 : page.getResult()) {
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

		return "communion/exchangeOfLearningList";
	}

	@RequestMapping(value = "post")
	public String post(ExchangeOfLearning exchangeOfLearning,ExchangeOfLearningReply exchangeOfLearningReply, Model model, HttpServletRequest request,
					   HttpServletResponse response) {
		model.addAttribute("exchangeOfLearning", exchangeOfLearning);
		exchangeOfLearningReply.setExchangeOfLearning(exchangeOfLearning);
		/* 接受页码并进行分页 首次进入默认为第一页 判断是否为第一次进去 */
		if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
			exchangeOfLearningReply.setPageNum(Integer.valueOf(request.getParameter("pageNum")));
		}
		/*
		 * pagehlper根据baseEntity里的pageNum和pageSize进行分页 无需手动添加 如需要更改参数
		 * 在com.piu.base.utils.Constant下更改
		 */
		/*Page<ExchangeOfLearningReply> page = (Page<ExchangeOfLearningReply>)*/
		Page<ExchangeOfLearningReply> page = (Page<ExchangeOfLearningReply>) exchangeOfLearningReplyService
				.findByPostId(exchangeOfLearningReply);
		/*for (ExchangeOfLearningReply e : page.getResult()) {
e.setReplyContent(HtmlUtils.filterHtmlTag(e.getReplyContent()));
		}*/
		model.addAttribute("page", page);
		return "communion/exchangeOfLearningPosts";
	}

	@RequestMapping(value = "delete")
	public String delete(ExchangeOfLearning exchangeOfLearning, RedirectAttributes redirectAttributes) {
		exchangeOfLearningService.delete(exchangeOfLearning);

		return "redirect:" + "/exchangeOfLearning/list";
	}

	@RequestMapping(value = "form")
	public String form(ExchangeOfLearning exchangeOfLearning, RedirectAttributes redirectAttributes, Model model) {
model.addAttribute("exchangeOfLearning",exchangeOfLearning);
		return "communion/exchangeOfLearningAdd";
	}

	@RequestMapping(value = "save")
	public String save(@Valid ExchangeOfLearning exchangeOfLearning, RedirectAttributes redirectAttributes,
					   Model model,HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("imageFile");
		if(StringUtils.isNotBlank(file.getOriginalFilename())){
			if(StringUtils.isNotBlank(files.get(0).getOriginalFilename())) {
				List<FileInterface> list = new ArrayList<>();
				for (MultipartFile multipartFiles : files) {
					FileInterface fileInterface = new FileInterface(exchangeOfLearning.getDownload());
					fileInterface.setPath( request.getContextPath()+"/upload/files/images/"+ UploadUtils.uploadFiles(multipartFiles).getName());
					list.add(fileInterface);
				}
				exchangeOfLearning.getDownload().setFileInterfaceList(list);
			}
			exchangeOfLearning.getDownload().setPath(UploadUtils.uploadFiles(file).getPath());
			exchangeOfLearning.getDownload().setSize(UploadUtils.uploadSize(file));
			exchangeOfLearning.getDownload().setUploadDate(new Date());
			exchangeOfLearning.getDownload().setUser(UserUtils.getCurrentUser());
			downloadService.save(exchangeOfLearning.getDownload());
			exchangeOfLearning.setDownUrl(request.getContextPath()+"/download/details?id="+exchangeOfLearning.getDownload().getId());
		}

		exchangeOfLearning.setUser(UserUtils.getCurrentUser());
		exchangeOfLearning.setPostingTime(new Date());
		exchangeOfLearning.setContent(ImagesResultUtils.imagesPathReplacement(exchangeOfLearning.getContent()));
		exchangeOfLearningService.save(exchangeOfLearning);

		return "redirect:" + "/exchangeOfLearning/list";
	}
    @RequestMapping(value = "updateStatus")
	public String updateStatus(ExchangeOfLearning exchangeOfLearning){
exchangeOfLearning.setStatus("1");
exchangeOfLearningService.updateStatus(exchangeOfLearning);

		return "redirect:" + "/exchangeOfLearning/list";
	}

	@RequestMapping(value = "removeStatus")
	public String removeStatus(ExchangeOfLearning exchangeOfLearning){
		exchangeOfLearning.setStatus("0");
		exchangeOfLearningService.updateStatus(exchangeOfLearning);

		return "redirect:" + "/exchangeOfLearning/list";
	}

    @RequestMapping(value = "audit")
    public String audit(ExchangeOfLearning exchangeOfLearning, RedirectAttributes redirectAttributes) {
        exchangeOfLearningService.audit(exchangeOfLearning);

        return "redirect:" + "/exchangeOfLearning/list";
    }
}
