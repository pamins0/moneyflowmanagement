package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.OrgType;

public interface OrgTypeService {

	OrgType findById(String string); 

	List<OrgType> getAllOrgTypes();
	
	void save(OrgType orgType);
	
	boolean deleteById(String id); 
	
	void updatebyId(OrgType org);

	
}
