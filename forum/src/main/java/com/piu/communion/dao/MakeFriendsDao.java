package com.piu.communion.dao;

import com.piu.base.CrudDao;
import com.piu.communion.entity.ExchangeOfLearning;
import com.piu.communion.entity.MakeFriends;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MakeFriendsDao extends CrudDao<MakeFriends>{

    public void updateStatus(MakeFriends makeFriends);
    public void audit(MakeFriends makeFriends);
    public void updateFloorCount(MakeFriends makeFriends);
}
