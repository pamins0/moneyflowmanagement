package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.OrgType;

public interface OrgTypeDao {

	OrgType findById(String string); 

	List<OrgType> getAllOrgTypes();

	void save(OrgType id);

	void deleteById(Integer id);

	OrgType findByUUID(String uuids); 
}
