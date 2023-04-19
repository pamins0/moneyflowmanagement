package com.emorphis.cashmanagement.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.CityDao;
import com.emorphis.cashmanagement.model.City;
import com.emorphis.cashmanagement.service.CityService;

@Repository
@Transactional
public class CityServiceImpl implements CityService {

	@Autowired
	CityDao dao;

	@Override
	public List<City> getCityListByState(int stateId, String tagName) {
		return dao.getCityListByState(stateId, tagName);
	}

}
