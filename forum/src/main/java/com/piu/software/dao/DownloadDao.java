package com.piu.software.dao;

import com.piu.base.CrudDao;
import com.piu.software.entity.Download;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DownloadDao extends CrudDao<Download> {
    public void updateAmountOfDownloads(Download download);
}
