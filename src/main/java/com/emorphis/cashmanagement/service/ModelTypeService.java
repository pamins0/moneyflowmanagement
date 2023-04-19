package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.ModelType;

public interface ModelTypeService {

	List<ModelType> getAllModelTypes();

	ModelType findById(String id); 

}
