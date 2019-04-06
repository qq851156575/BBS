package com.piu.software.entity;

import com.piu.base.DataEntity;
import com.piu.sys.entity.User;

import java.util.Date;
import java.util.List;

public class Download extends DataEntity<Download> {
    private static final long serialVersionUID = 1L;
    private User user;

    private String path;

    private String introduce;

    private String version;
    private Integer amountOfDownloads =0;

    private String size;

    private String label;

    private String category;

    private String title;
    private Date uploadDate;
    private String language;
    private String system;
    private String cost;

    public Download() {
		super.setPageSize(16);
	}
    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    private List<FileInterface> fileInterfaceList;

    public List<FileInterface> getFileInterfaceList() {
        return fileInterfaceList;
    }

    public void setFileInterfaceList(List<FileInterface> fileInterfaceList) {
        this.fileInterfaceList = fileInterfaceList;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }


    public Integer getAmountOfDownloads() {
        return amountOfDownloads;
    }

    public void setAmountOfDownloads(Integer amountOfDownloads) {
        this.amountOfDownloads = amountOfDownloads;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
