package com.emorphis.cashmanagement.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.User;

public interface UtilityService {

	public void OrgToBranchDetails(ModelMap model);

	public void OrgToOrgManagementDetails(ModelMap model);
	
	public void OrgToHierarchyControlDetails(ModelMap model);
	
	public List<HierarchyControl> getHierarchyListAccToUserLevel(ModelMap model);

	public List<HierarchyControl> getHierarchyListAccToBranchLevel(ModelMap model);

	public int makeClusterClusterGroup(BranchManagement branch, User user) throws Exception;  
}
