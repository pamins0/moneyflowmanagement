package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.City;

public interface CityService {
	
	List<City> getCityListByState(int stateId, String tagName);

}
