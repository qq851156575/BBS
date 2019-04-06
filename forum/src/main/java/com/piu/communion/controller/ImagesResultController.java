package com.piu.communion.controller;

import com.piu.communion.entity.ImagesResult;
import com.piu.communion.utils.ImagesResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
public class ImagesResultController {
	
	
	@RequestMapping("/uploadImg")
	public ImagesResult uploadImg(MultipartFile myFileName,HttpSession session,HttpServletRequest request) throws IllegalStateException, IOException{
	String realName = "";
		if (myFileName != null) {
	String fileName = myFileName.getOriginalFilename();
	String fileNameExtension = fileName.substring(fileName.lastIndexOf("."));
	// 生成实际存储的真实文件名

	realName = UUID.randomUUID().toString() + fileNameExtension;


	String realPath = ImagesResultUtils.uploadTemp();
	File uploadFile = new File(realPath, realName);
    if (!uploadFile.getParentFile().exists()) {
        uploadFile.getParentFile().mkdirs();
    }
	myFileName.transferTo(uploadFile);
	}
	String [] str = { request.getContextPath()+"/upload/temp/" + realName};
	return ImagesResultUtils.success(str);
	}
}
