package com.piu.communion.controller;

import com.github.pagehelper.Page;
import com.piu.base.BaseController;
import com.piu.communion.entity.MakeFriends;
import com.piu.communion.entity.MakeFriendsReply;
import com.piu.communion.service.MakeFriendsReplyService;
import com.piu.communion.service.MakeFriendsService;
import com.piu.communion.utils.ImagesResultUtils;
import com.piu.communion.utils.UploadUtils;
import com.piu.software.entity.FileInterface;
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

@RequestMapping(value = "/makeFriends")
@Controller
public class MakeFriendsController extends BaseController{
	@Autowired
	DownloadService downloadService;
	@Autowired
	MakeFriendsReplyService makeFriendsReplyService;
	@Autowired
	MakeFriendsService makeFriendsService;

	@ModelAttribute
	public MakeFriends get(@RequestParam(required = false) String id) {

		if (StringUtils.isNotBlank(id)) {
			return makeFriendsService.get(id);
		} else {
			return new MakeFriends();
		}
	}

	@RequestMapping(value = "list")
	public String list(MakeFriends makeFriends, Model model, HttpServletRequest request,
					   HttpServletResponse response) {
		/* 接受页码并进行分页 首次进入默认为第一页 判断是否为第一次进去 */
		if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
			makeFriends.setPageNum(Integer.valueOf(request.getParameter("pageNum")));
		}
		/*
		 * pagehlper根据baseEntity里的pageNum和pageSize进行分页 无需手动添加 如需要更改参数
		 * 在com.piu.base.utils.Constant下更改
		 */
		Page<MakeFriends> page = (Page<MakeFriends>) makeFriendsService
				.findList(makeFriends);
		/*
		 * 正则表达过滤HTML标签
		 */
		for (MakeFriends list1 : page.getResult()) {
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

		return "communion/makeFriendsList";
	}

	@RequestMapping(value = "post")
	public String post(MakeFriends makeFriends, MakeFriendsReply makeFriendsReply, Model model, HttpServletRequest request,
					   HttpServletResponse response) {
		model.addAttribute("makeFriends", makeFriends);
		makeFriendsReply.setMakeFriends(makeFriends);
		/* 接受页码并进行分页 首次进入默认为第一页 判断是否为第一次进去 */
		if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
			makeFriendsReply.setPageNum(Integer.valueOf(request.getParameter("pageNum")));
		}
		/*
		 * pagehlper根据baseEntity里的pageNum和pageSize进行分页 无需手动添加 如需要更改参数
		 * 在com.piu.base.utils.Constant下更改
		 */
		Page<MakeFriendsReply> page = (Page<MakeFriendsReply>) makeFriendsReplyService
				.findByPostId(makeFriendsReply);
		model.addAttribute("page", page);
		return "communion/makeFriendsPosts";
	}

	@RequestMapping(value = "delete")
	public String delete(MakeFriends makeFriends, RedirectAttributes redirectAttributes) {
		makeFriendsService.delete(makeFriends);

		return "redirect:" + "/makeFriends/list";
	}

	@RequestMapping(value = "form")
	public String form(MakeFriends makeFriends, RedirectAttributes redirectAttributes, Model model) {
model.addAttribute("makeFriends",makeFriends);
		return "communion/makeFriendsAdd";
	}

	@RequestMapping(value = "save")
	public String save(@Valid MakeFriends makeFriends, RedirectAttributes redirectAttributes,
					   Model model,HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("imageFile");
		if(StringUtils.isNotBlank(file.getOriginalFilename())){
			if(StringUtils.isNotBlank(files.get(0).getOriginalFilename())) {
				List<FileInterface> list = new ArrayList<>();
				for (MultipartFile multipartFiles : files) {
					FileInterface fileInterface = new FileInterface(makeFriends.getDownload());
					fileInterface.setPath( request.getContextPath()+"/upload/files/images/"+ UploadUtils.uploadFiles(multipartFiles).getName());
					list.add(fileInterface);
				}
				makeFriends.getDownload().setFileInterfaceList(list);
			}
			makeFriends.getDownload().setPath(UploadUtils.uploadFiles(file).getPath());
			makeFriends.getDownload().setSize(UploadUtils.uploadSize(file));
			makeFriends.getDownload().setUploadDate(new Date());
			makeFriends.getDownload().setUser(UserUtils.getCurrentUser());
			downloadService.save(makeFriends.getDownload());
			makeFriends.setDownUrl(request.getContextPath()+"/download/details?id="+makeFriends.getDownload().getId());
		}

		makeFriends.setUser(UserUtils.getCurrentUser());
		makeFriends.setPostingTime(new Date());
		makeFriends.setContent(ImagesResultUtils.imagesPathReplacement(makeFriends.getContent()));
		makeFriendsService.save(makeFriends);

		return "redirect:" + "/makeFriends/list";
	}
    @RequestMapping(value = "updateStatus")
	public String updateStatus(MakeFriends makeFriends){
makeFriends.setStatus("1");
makeFriendsService.updateStatus(makeFriends);

		return "redirect:" + "/makeFriends/list";
	}

	@RequestMapping(value = "removeStatus")
	public String removeStatus(MakeFriends makeFriends){
		makeFriends.setStatus("0");
		makeFriendsService.updateStatus(makeFriends);

		return "redirect:" + "/makeFriends/list";
	}

    @RequestMapping(value = "audit")
    public String audit(MakeFriends makeFriends, RedirectAttributes redirectAttributes) {
        makeFriendsService.audit(makeFriends);

        return "redirect:" + "/makeFriends/list";
    }
}
