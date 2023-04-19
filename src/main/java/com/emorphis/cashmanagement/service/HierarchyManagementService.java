package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.OrgManagement;

public interface HierarchyManagementService {
	public abstract int save(HierarchyControl paramHierarchyControl);

	public abstract List<HierarchyControl> getAllHierarchyControlList();

	public abstract HierarchyControl findById(String id); 

	public abstract void updateById(HierarchyControl paramHierarchyControl);

	public abstract boolean deleteById(String id); 

	public abstract HierarchyControl findByOrgAndLevel(OrgManagement orgManagement, int i);

	public abstract List<HierarchyControl> getHierarchyListAccToUserLevel();

	public abstract List<HierarchyControl> getAllHierarchyControlListAccToOrganization(
			HierarchyControl hierarchyControl);

	public abstract List<HierarchyControl> getAllHierarchyListByOrgmanagemet(String orgManagementid);

	public abstract List<HierarchyControl> getAllHierarchyControlListByOrgId(String id);

	public abstract List<HierarchyControl> getHierarchyListAccToBranchLevel();   

	/*public abstract HierarchyControl findByUUID(String uuid);

	public abstract boolean deleteByUUID(String uuid);

	public abstract void updateByUUID(HierarchyControl hierarchyControl);      */

 
}
