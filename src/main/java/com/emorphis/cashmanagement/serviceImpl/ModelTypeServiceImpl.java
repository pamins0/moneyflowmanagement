package com.emorphis.cashmanagement.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.ModelTypeDao;
import com.emorphis.cashmanagement.model.ModelType;
import com.emorphis.cashmanagement.service.ModelTypeService;

@Repository
@Transactional
public class ModelTypeServiceImpl implements ModelTypeService {

	@Autowired
	ModelTypeDao modelTypeDao;
	
	public List<ModelType> getAllModelTypes() {
		List<ModelType> modelTypesList = null;
		modelTypesList = modelTypeDao.getAllModelType();
		return modelTypesList;
	}
	
	public ModelType findById(String id) {
		return modelTypeDao.findById(id);
	}
}
