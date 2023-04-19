package com.emorphis.cashmanagement.service;

import com.emorphis.cashmanagement.model.CommisionRate;

public interface CommisionRateService {

	CommisionRate findById(int parseInt);
 
	CommisionRate findByModelTypeId(String string);  

}
