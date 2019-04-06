package com.piu.communion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piu.base.CrudService;
import com.piu.communion.dao.ExchangeOfLearningDao;
import com.piu.communion.entity.ExchangeOfLearning;
@Service
public class ExchangeOfLearningService extends CrudService<ExchangeOfLearningDao, ExchangeOfLearning>{


    public void updateStatus(ExchangeOfLearning exchangeOfLearning){
        dao.updateStatus(exchangeOfLearning);
    }

    public void audit(ExchangeOfLearning exchangeOfLearning){
        dao.audit(exchangeOfLearning);
    }
    public void updateFloorCount(ExchangeOfLearning exchangeOfLearning){
        dao.updateFloorCount(exchangeOfLearning);
    }
}
