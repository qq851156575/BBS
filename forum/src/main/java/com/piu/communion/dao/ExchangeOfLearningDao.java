package com.piu.communion.dao;

import org.apache.ibatis.annotations.Mapper;

import com.piu.base.CrudDao;
import com.piu.communion.entity.ExchangeOfLearning;
@Mapper
public interface ExchangeOfLearningDao extends CrudDao<ExchangeOfLearning>{

    public void updateStatus(ExchangeOfLearning exchangeOfLearning);
    public void audit(ExchangeOfLearning exchangeOfLearning);
    public void updateFloorCount(ExchangeOfLearning exchangeOfLearning);
}
