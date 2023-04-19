package com.emorphis.cashmanagement.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.CommisionRateDao;
import com.emorphis.cashmanagement.model.CommisionRate;
import com.emorphis.cashmanagement.service.CommisionRateService;

@Repository
@Transactional
public class CommisionRateServiceImpl implements CommisionRateService {

	@Autowired
	CommisionRateDao commisionRateDao;
	
	public CommisionRate findById(int id) {
		
		return this.commisionRateDao.findById(id);
	}
	
	public CommisionRate findByModelTypeId(String id) {
	
		return this.commisionRateDao.findByModelTypeId(id);
	}
}
