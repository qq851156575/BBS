package com.piu.sys.dao;

import com.piu.base.CrudDao;
import com.piu.sys.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictDao extends CrudDao<Dict> {


}
