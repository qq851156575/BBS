package com.piu.software.dao;

import com.piu.base.CrudDao;
import com.piu.software.entity.FileInterface;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileInterfaceDao  extends CrudDao<FileInterface>{
}
