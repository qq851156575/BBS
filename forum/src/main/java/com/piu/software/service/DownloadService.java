package com.piu.software.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.piu.base.CrudService;
import com.piu.base.utils.Constant;
import com.piu.communion.utils.UploadUtils;
import com.piu.software.dao.DownloadDao;
import com.piu.software.entity.Download;
import com.piu.software.entity.FileInterface;

@Service
public class DownloadService extends CrudService<DownloadDao, Download> {

    @Autowired
    FileInterfaceService fileInterfaceService;

    @Override
    public Download get (String id){
        Download download = dao.get(id);
        download.setFileInterfaceList(fileInterfaceService.findList(new FileInterface(download)));
        return download;
    }
    public void save(Download download){
        super.save(download);
        if(download.getFileInterfaceList()!=null) {
            for (FileInterface fileInterface : download.getFileInterfaceList()) {
                fileInterfaceService.save(fileInterface);
            }
        }
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void delete(Download entity) {
    	List<File> filelist = new ArrayList<File>();
    	String filePath = entity.getPath();
    	filelist.add(new File(filePath));
    	for(FileInterface fileInterface:entity.getFileInterfaceList()) {
    		String path = "";
    		if(fileInterface.getPath().contains("/")) 
    			path = UploadUtils.uploadDirectory()+fileInterface.getPath().substring(fileInterface.getPath().lastIndexOf("/"));
    			else
    			path = UploadUtils.uploadDirectory()+fileInterface.getPath().substring(fileInterface.getPath().lastIndexOf("\\"));
    		
    		filelist.add(new File(path));
    	}
    	for(File file:filelist) {
    		if(file.exists()) {
    			file.delete();
    			System.out.println(file.getName()+"已删除");
    		}else {
    			System.out.println(file.getPath()+file.getName()+"未找到");
    		}
    	}
        super.delete(entity);
        fileInterfaceService.delete(new FileInterface(entity));
    }

    public void updateAmountOfDownloads(Download download){
        dao.updateAmountOfDownloads(download);
}
}
