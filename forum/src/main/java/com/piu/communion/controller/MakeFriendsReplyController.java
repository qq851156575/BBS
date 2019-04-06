package com.piu.communion.controller;

import com.piu.base.BaseController;
import com.piu.communion.entity.ExchangeOfLearning;
import com.piu.communion.entity.ExchangeOfLearningReply;
import com.piu.communion.entity.MakeFriends;
import com.piu.communion.entity.MakeFriendsReply;
import com.piu.communion.service.ExchangeOfLearningReplyService;
import com.piu.communion.service.ExchangeOfLearningService;
import com.piu.communion.service.MakeFriendsReplyService;
import com.piu.communion.service.MakeFriendsService;
import com.piu.sys.service.UserService;
import com.piu.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping(value = "/makeFriendsReply")
@Controller
public class MakeFriendsReplyController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    MakeFriendsReplyService makeFriendsReplyService;
    @Autowired
    MakeFriendsService makeFriendsService;

    @ModelAttribute
    public MakeFriendsReply get(@RequestParam(required = false) String id) {

        if (StringUtils.isNotBlank(id)) {
            return makeFriendsReplyService.get(id);
        } else {
            return new MakeFriendsReply();
        }
    }

    @RequestMapping(value = "save")
    public String save(@Valid MakeFriendsReply makeFriendsReply, RedirectAttributes redirectAttributes,
                       Model model, BindingResult bindingResult) {
        /*
         * String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>"; // 找出IMG标签
         *
         * String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性
         */
        if (StringUtils.isNotBlank(makeFriendsReply.getSuperiorId())) {
            MakeFriendsReply makeFriendsReply1 = makeFriendsReplyService.get(makeFriendsReply.getSuperiorId());
            SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
            String content = "<p>引用  <a>" + makeFriendsReply1.getUser().getUserName() + "</a><span style=\"float: right\">" + f.format(makeFriendsReply1.getReplyDate()) + "</span></p>\n" +
                    "\n" +
                    "\t\t\t\t<p style=\"width: 100%\">" + makeFriendsReply1.getReplyContent() + "</p>";

            makeFriendsReply.setSuperiorUser(userService.get(makeFriendsReply1.getUser().getId()));
            makeFriendsReply.setSuperiorReply(content);
        }
        makeFriendsReply.setUser(UserUtils.getCurrentUser());
        makeFriendsReply.setReplyDate(new Date());
        makeFriendsReplyService.save(makeFriendsReply);
        MakeFriends makeFriends = makeFriendsService.get(makeFriendsReply.getMakeFriends().getId());
        makeFriends.setFloorCount(makeFriends.getFloorCount()+1);
        makeFriendsService.updateFloorCount(makeFriends);
        return "redirect:" + "/makeFriends/post?id=" + makeFriendsReply.getMakeFriends().getId();
    }
    @RequestMapping(value = "audit")
    public String audit(MakeFriendsReply makeFriendsReply, RedirectAttributes redirectAttributes) {
        makeFriendsReplyService.audit(makeFriendsReply);

        return "redirect:" + "/makeFriends/post?id=" + makeFriendsReply.getMakeFriends().getId();
    }
    @RequestMapping(value = "delete")
    public String delete(MakeFriendsReply makeFriendsReply, RedirectAttributes redirectAttributes) {
        makeFriendsReplyService.delete(makeFriendsReply);

        return "redirect:" + "/makeFriends/post?id=" + makeFriendsReply.getMakeFriends().getId();
    }
    @RequestMapping(value = "superior")
    public void superior(String superiorId, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        MakeFriendsReply makeFriendsReply1 = makeFriendsReplyService.get(superiorId);
        SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
        String content = "<p>引用  <a>" + makeFriendsReply1.getUser().getUserName() + "</a><span style=\"float: right\">" + f.format(makeFriendsReply1.getReplyDate()) + "</span></p>\n" +
                "\n" +
                "\t\t\t\t<p style=\"border:#ccc;overflow: hidden; display: -webkit-box;-webkit-line-clamp:1;-webkit-box-orient: vertical;\">" + makeFriendsReply1.getReplyContent() + "</p><button onclick=\"remove()\">" +
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
