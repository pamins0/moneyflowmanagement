package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.City;

public interface CityDao  {

	
	List<City> getCityListByState(int stateId, String tagName);
}
