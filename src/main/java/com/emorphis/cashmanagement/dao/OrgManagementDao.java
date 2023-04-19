package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.OrgManagement;

public interface OrgManagementDao {

	List<OrgManagement> getAllOrgManagementList();

	int save(OrgManagement orgManagement);

	int deleteById(Integer id);

	OrgManagement findById(String id); 

	List<OrgManagement> getAllOrgManagementListByOrgType(String orgTypeId);

	OrgManagement findByUUID(String uuids); 

}
