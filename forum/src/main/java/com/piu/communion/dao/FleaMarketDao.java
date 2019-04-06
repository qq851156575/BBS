package com.piu.communion.dao;

import org.apache.ibatis.annotations.Mapper;

import com.piu.base.CrudDao;
import com.piu.communion.entity.FleaMarket;

@Mapper
public interface FleaMarketDao extends CrudDao<FleaMarket>{

    public void updateStatus(FleaMarket fleaMarket);
    public void audit(FleaMarket fleaMarket);
    public void updateFloorCount(FleaMarket fleaMarket);
}
