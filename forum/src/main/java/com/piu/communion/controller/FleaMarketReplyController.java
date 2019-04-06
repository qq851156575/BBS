package com.piu.communion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.piu.base.BaseController;
import com.piu.communion.entity.FleaMarket;
import com.piu.communion.entity.FleaMarketReply;
import com.piu.communion.service.FleaMarketReplyService;
import com.piu.communion.service.FleaMarketService;
import com.piu.sys.service.UserService;
import com.piu.sys.utils.UserUtils;

@RequestMapping(value = "/fleaMarketReply")
@Controller
public class FleaMarketReplyController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
	FleaMarketReplyService fleaMarketReplyService;
	@Autowired
	FleaMarketService fleaMarketService;


    @ModelAttribute
    public FleaMarketReply get(@RequestParam(required = false) String id) {

        if (StringUtils.isNotBlank(id)) {
            return fleaMarketReplyService.get(id);
        } else {
            return new FleaMarketReply();
        }
    }

    @RequestMapping(value = "save")
    public String save(@Valid FleaMarketReply fleaMarketReply, RedirectAttributes redirectAttributes,
                       Model model, BindingResult bindingResult) {
        /*
         * String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>"; // 找出IMG标签
         *
         * String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性
         */
        if (StringUtils.isNotBlank(fleaMarketReply.getSuperiorId())) {
            FleaMarketReply fleaMarketReply1 = fleaMarketReplyService.get(fleaMarketReply.getSuperiorId());
            SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
            String content = "<p>引用  <a>" + fleaMarketReply1.getUser().getUserName() + "</a><span style=\"float: right\">" + f.format(fleaMarketReply1.getReplyDate()) + "</span></p>\n" +
                    "\n" +
                    "\t\t\t\t<p style=\"width: 100%\">" + fleaMarketReply1.getReplyContent() + "</p>";

            fleaMarketReply.setSuperiorUser(userService.get(fleaMarketReply1.getUser().getId()));
            fleaMarketReply.setSuperiorReply(content);
        }
        fleaMarketReply.setUser(UserUtils.getCurrentUser());
        fleaMarketReply.setReplyDate(new Date());
        fleaMarketReplyService.save(fleaMarketReply);
        FleaMarket fleaMarket = fleaMarketService.get(fleaMarketReply.getFleaMarket().getId());
        fleaMarket.setFloorCount(fleaMarket.getFloorCount()+1);
        fleaMarketService.updateFloorCount(fleaMarket);
        return "redirect:" + "/fleaMarket/post?id=" + fleaMarketReply.getFleaMarket().getId();
    }
   
    @RequestMapping(value = "delete")
    public String delete(FleaMarketReply fleaMarketReply, RedirectAttributes redirectAttributes) {
        fleaMarketReplyService.delete(fleaMarketReply);

        return "redirect:" + "/fleaMarket/post?id=" + fleaMarketReply.getFleaMarket().getId();
    }
    @RequestMapping(value = "superior")
    public void superior(String superiorId, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        FleaMarketReply fleaMarketReply1 = fleaMarketReplyService.get(superiorId);
        SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
        String content = "<p>引用  <a>" + fleaMarketReply1.getUser().getUserName() + "</a><span style=\"float: right\">" + f.format(fleaMarketReply1.getReplyDate()) + "</span></p>\n" +
                "\n" +
                "\t\t\t\t<p style=\"border:#ccc;overflow: hidden; display: -webkit-box;-webkit-line-clamp:1;-webkit-box-orient: vertical;\">" + fleaMarketReply1.getReplyContent() + "</p><button onclick=\"remove()\">" +
                "取消对其回复</button>";
        //这句话的意思，是让浏览器用utf8来解析返回的数据
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        response.setCharacterEncoding("UTF-8");
        String data = "中国";
        PrintWriter pw = response.getWriter();
        pw.write(content);
    }

}
