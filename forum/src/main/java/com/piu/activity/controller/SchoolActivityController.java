package com.piu.activity.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.piu.activity.utils.SchoolActivityUtils;
import com.piu.communion.utils.HtmlUtils;
import com.piu.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.piu.activity.entity.SchoolActivity;
import com.piu.activity.service.SchoolActivityService;
import com.piu.communion.entity.ExchangeOfLearning;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/schoolActivity")
public class SchoolActivityController {

    @Autowired
    private SchoolActivityService schoolActivityService;


    @ModelAttribute
    public SchoolActivity get(@RequestParam(required = false) String id) {

        if (StringUtils.isNotBlank(id)) {
            return schoolActivityService.get(id);
        } else {
            return new SchoolActivity();
        }
    }

    @RequestMapping("list")
    public String list(@RequestParam(defaultValue = "1", required = false, name = "pageNum") Integer pageNum, SchoolActivity schoolActivity, Model model, HttpServletRequest request,
                       HttpServletResponse response) {
        schoolActivity.setPageSize(5);
        schoolActivity.setPageNum(pageNum);
        Page<SchoolActivity> page = (Page<SchoolActivity>) schoolActivityService
                .findList(schoolActivity);
        for (SchoolActivity schoolActivity1 :page.getResult()) {

            schoolActivity1.setContent(HtmlUtils.filterHtmlTag(schoolActivity1.getContent()));
        }
        model.addAttribute("page", page);
        return "activity/activityList";
    }

    @RequestMapping("details")
    public String details(SchoolActivity schoolActivity, Model model, HttpServletRequest request,
                          HttpServletResponse response) {
        model.addAttribute("schoolActivity", schoolActivity);
        return "activity/activityDetails";
    }

    @RequestMapping("form")
    public String form(SchoolActivity schoolActivity, Model model, HttpServletRequest request,
                       HttpServletResponse response) {
model.addAttribute("schoolActivity",schoolActivity);
        return "activity/activityAdd";
    }

    @RequestMapping("save")
    public String save(SchoolActivity schoolActivity, Model model, HttpServletRequest request,
                       HttpServletResponse response, @RequestParam("imageFile") MultipartFile file) {

        schoolActivity.setUser(UserUtils.getCurrentUser());
        schoolActivity.setPostTime(new Date());
        schoolActivity.setPosterPath(request.getContextPath() + "/upload/activity/images/" + SchoolActivityUtils.activityImg(file).getName());
        schoolActivityService.save(schoolActivity);
        return "redirect:" + "/schoolActivity/list";
    }

    @RequestMapping("last")
    public String last(SchoolActivity schoolActivity, Model model, HttpServletRequest request,
                       HttpServletResponse response) {
        schoolActivity.setPageSize(5);
        List<SchoolActivity> list = schoolActivityService.findLast(schoolActivity);
        model.addAttribute("list",list);
        return "activity/latestActivities";
    }

    @RequestMapping(value = "delete")
    public String delete(SchoolActivity schoolActivity, RedirectAttributes redirectAttributes) {
        schoolActivityService.delete(schoolActivity);

        return "redirect:" + "/schoolActivity/list";
    }

    @RequestMapping(value = "audit")
    public String audit(SchoolActivity schoolActivity, RedirectAttributes redirectAttributes) {
        schoolActivityService.audit(schoolActivity);

        return "redirect:" + "/schoolActivity/list";
    }

}

