package com.piu.communion.controller;

import com.piu.communion.entity.ExchangeOfLearning;
import com.piu.communion.service.ExchangeOfLearningService;
import com.piu.sys.entity.User;
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

import com.piu.base.BaseController;
import com.piu.communion.entity.ExchangeOfLearningReply;
import com.piu.communion.service.ExchangeOfLearningReplyService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping(value = "/exchangeOfLearningReply")
@Controller
public class ExchangeOfLearningReplyController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    ExchangeOfLearningReplyService exchangeOfLearningReplyService;
    @Autowired
    ExchangeOfLearningService exchangeOfLearningService;

    @ModelAttribute
    public ExchangeOfLearningReply get(@RequestParam(required = false) String id) {

        if (StringUtils.isNotBlank(id)) {
            return exchangeOfLearningReplyService.get(id);
        } else {
            return new ExchangeOfLearningReply();
        }
    }

    @RequestMapping(value = "save")
    public String save(@Valid ExchangeOfLearningReply exchangeOfLearningReply, RedirectAttributes redirectAttributes,
                       Model model, BindingResult bindingResult) {
        /*
         * String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>"; // 找出IMG标签
         *
         * String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性
         */
        if (StringUtils.isNotBlank(exchangeOfLearningReply.getSuperiorId())) {
            ExchangeOfLearningReply exchangeOfLearningReply1 = exchangeOfLearningReplyService.get(exchangeOfLearningReply.getSuperiorId());
            SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
            String content = "<p>引用  <a>" + exchangeOfLearningReply1.getUser().getUserName() + "</a><span style=\"float: right\">" + f.format(exchangeOfLearningReply1.getReplyDate()) + "</span></p>\n" +
                    "\n" +
                    "\t\t\t\t<p style=\"width: 100%\">" + exchangeOfLearningReply1.getReplyContent() + "</p>";

            exchangeOfLearningReply.setSuperiorUser(userService.get(exchangeOfLearningReply1.getUser().getId()));
            exchangeOfLearningReply.setSuperiorReply(content);
        }
        exchangeOfLearningReply.setUser(UserUtils.getCurrentUser());
        exchangeOfLearningReply.setReplyDate(new Date());
        exchangeOfLearningReplyService.save(exchangeOfLearningReply);
ExchangeOfLearning exchangeOfLearning = exchangeOfLearningService.get(exchangeOfLearningReply.getExchangeOfLearning().getId());
exchangeOfLearning.setFloorCount(exchangeOfLearning.getFloorCount()+1);
exchangeOfLearningService.updateFloorCount(exchangeOfLearning);
        return "redirect:" + "/exchangeOfLearning/post?id=" + exchangeOfLearningReply.getExchangeOfLearning().getId();
    }
    @RequestMapping(value = "audit")
    public String audit(ExchangeOfLearningReply exchangeOfLearningReply, RedirectAttributes redirectAttributes) {
        exchangeOfLearningReplyService.audit(exchangeOfLearningReply);

        return "redirect:" + "/exchangeOfLearning/post?id=" + exchangeOfLearningReply.getExchangeOfLearning().getId();
    }
    @RequestMapping(value = "delete")
    public String delete(ExchangeOfLearningReply exchangeOfLearningReply, RedirectAttributes redirectAttributes) {
        exchangeOfLearningReplyService.delete(exchangeOfLearningReply);

        return "redirect:" + "/exchangeOfLearning/post?id=" + exchangeOfLearningReply.getExchangeOfLearning().getId();
    }
    @RequestMapping(value = "superior")
    public void superior(String superiorId, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        ExchangeOfLearningReply exchangeOfLearningReply1 = exchangeOfLearningReplyService.get(superiorId);
        SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
        String content = "<p>引用  <a>" + exchangeOfLearningReply1.getUser().getUserName() + "</a><span style=\"float: right\">" + f.format(exchangeOfLearningReply1.getReplyDate()) + "</span></p>\n" +
                "\n" +
                "\t\t\t\t<p style=\"border:#ccc;overflow: hidden; display: -webkit-box;-webkit-line-clamp:1;-webkit-box-orient: vertical;\">" + exchangeOfLearningReply1.getReplyContent() + "</p><button onclick=\"remove()\">" +
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
