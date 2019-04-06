package com.piu.communion.service;

import org.springframework.stereotype.Service;

import com.piu.base.CrudService;
import com.piu.communion.dao.FleaMarketDao;
import com.piu.communion.entity.FleaMarket;

@Service
public class FleaMarketService extends CrudService<FleaMarketDao,FleaMarket>{


    public void updateStatus(FleaMarket fleaMarket){
        dao.updateStatus(fleaMarket);
    }

    public void audit(FleaMarket fleaMarket){
        dao.audit(fleaMarket);
    }
    public void updateFloorCount(FleaMarket fleaMarket){
        dao.updateFloorCount(fleaMarket);
    }
}
