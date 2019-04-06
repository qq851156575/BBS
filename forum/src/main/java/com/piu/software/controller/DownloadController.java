package com.piu.software.controller;

import com.github.pagehelper.Page;
import com.piu.software.entity.Download;
import com.piu.software.entity.FileInterface;



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
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/download")
@Controller
public class DownloadController {

    @Autowired
    DownloadService downloadService;
    @ModelAttribute
    public Download get(@RequestParam(required = false) String id) {

        if (StringUtils.isNotBlank(id)) {
            return downloadService.get(id);
        } else {
            return new Download();
        }
    }
    @RequestMapping(value = "list")
    public String list(Download download, Model model, HttpServletRequest request,
                       HttpServletResponse response) {
        /* 接受页码并进行分页 首次进入默认为第一页 判断是否为第一次进去 */
        if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
            download.setPageNum(Integer.valueOf(request.getParameter("pageNum")));
        }
        /*
         * pagehlper根据baseEntity里的pageNum和pageSize进行分页 无需手动添加 如需要更改参数
         * 在com.piu.base.utils.Constant下更改
         */
        Page<Download> page = (Page<Download>) downloadService
                .findList(download);

        model.addAttribute("page", page);
        return "download/software";
    }


    @RequestMapping(value = "save")
    public String save(Download download, RedirectAttributes redirectAttributes,
                       Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("imageFile");
        if(StringUtils.isBlank(file.getOriginalFilename())){
           return  "redirect:" + "/download/form";
        }
        if(StringUtils.isNotBlank(files.get(0).getOriginalFilename())) {
            List<FileInterface> list = new ArrayList<>();
            for (MultipartFile multipartFiles : files) {
                FileInterface fileInterface = new FileInterface(download);
                fileInterface.setPath( request.getContextPath()+"/upload/files/images/"+UploadUtils.uploadFiles(multipartFiles).getName());
                list.add(fileInterface);
            }
            download.setFileInterfaceList(list);
        }
        download.setPath(UploadUtils.uploadFiles(file).getPath());
        download.setSize(UploadUtils.uploadSize(file));
        download.setUploadDate(new Date());
        download.setUser(UserUtils.getCurrentUser());
        downloadService.save(download);
        return "redirect:" + "/download/list";
    }

    @RequestMapping(value = "form")
    public String form() {

        return "download/uploading";
    }

    @RequestMapping(value = "details")
    public String details(Download download,Model model) {
model.addAttribute("download",download);
        return "download/softwareDetails";
    }

    @RequestMapping(value = "download")
    public void download(Download download,HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        //设置ContentType字段值
        response.setContentType("text/html;charset=utf-8");
        //获取所要下载的文件名称
        File file = new File(download.getPath());

        download.setAmountOfDownloads(download.getAmountOfDownloads()+1);
        downloadService.updateAmountOfDownloads(download);
        String filename = file.getName();
        //下载文件所在目录
        String folder = file.getAbsolutePath();
        //通知浏览器以下载的方式打开
        response.addHeader("Content-type", "appllication/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename="+filename);
        //通知文件流读取文件
        InputStream in = new FileInputStream(file);
        //获取response对象的输出流
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        //循环取出流中的数据
        while((len = in.read(buffer)) != -1){
            out.write(buffer,0,len);
        }
    }
}
