package com.emorphis.cashmanagement.service;
import java.util.List;

import com.emorphis.cashmanagement.model.OrgManagement;

public interface OrgManagementService {

	List<OrgManagement> getAllOrgManagementList();

	String save(OrgManagement orgManagement); 

	boolean deleteById(String id); 

	OrgManagement findById(String id); 

	void updatebyId(OrgManagement orgManagement);

	List<OrgManagement> getAllOrgManagementListByOrgType(String orgTypeId);   

}
