package com.piu.activity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piu.activity.dao.SchoolActivityDao;
import com.piu.activity.entity.SchoolActivity;
import com.piu.base.CrudService;
@Service
public class SchoolActivityService extends CrudService<SchoolActivityDao, SchoolActivity>{
		
	public List<SchoolActivity> findLast(SchoolActivity schoolActivity){
		return dao.findLast(schoolActivity);
	}
	public void audit(SchoolActivity schoolActivity){
		dao.audit(schoolActivity);
	}
}
