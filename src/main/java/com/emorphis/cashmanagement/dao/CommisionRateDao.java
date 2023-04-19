package com.emorphis.cashmanagement.dao;

import com.emorphis.cashmanagement.model.CommisionRate;

public interface CommisionRateDao {

	CommisionRate findById(int id);

	CommisionRate findByModelTypeId(String id);  

}
