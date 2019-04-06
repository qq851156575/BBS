package com.piu.sys.service;

import com.piu.base.CrudService;
import com.piu.sys.dao.DictDao;
import com.piu.sys.entity.Dict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictService extends CrudService<DictDao,Dict> {

    public List<Dict> findAllList(Dict dict){

       return dao.findAllList(dict);
    }
}
