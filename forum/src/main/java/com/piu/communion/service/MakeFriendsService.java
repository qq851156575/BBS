package com.piu.communion.service;

import com.piu.base.CrudService;
import com.piu.communion.dao.MakeFriendsDao;
import com.piu.communion.entity.MakeFriends;
import org.springframework.stereotype.Service;

@Service
public class MakeFriendsService extends CrudService<MakeFriendsDao,MakeFriends>{


    public void updateStatus(MakeFriends makeFriends){
        dao.updateStatus(makeFriends);
    }

    public void audit(MakeFriends makeFriends){
        dao.audit(makeFriends);
    }
    public void updateFloorCount(MakeFriends makeFriends){
        dao.updateFloorCount(makeFriends);
    }
}
