package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.ModelType;

public interface ModelTypeDao {

	List<ModelType> getAllModelType();
 
	ModelType findById(String id);

}
