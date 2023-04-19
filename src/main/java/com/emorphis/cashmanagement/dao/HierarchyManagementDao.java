package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.OrgManagement;

public interface HierarchyManagementDao {
	public abstract int save(HierarchyControl paramHierarchyControl);

	public abstract List<HierarchyControl> getAllHierarchyControlList();
 
	public abstract HierarchyControl findById(String id);

	public abstract HierarchyControl findByOrgAndLevel(OrgManagement orgManagement, int level);

	public abstract List<HierarchyControl> getHierarchyListAccToUserLevel();

	public abstract List<HierarchyControl> getAllHierarchyControlListAccToOrganization(
			HierarchyControl hierarchyControl);

	public abstract List<HierarchyControl> getAllHierarchyListByOrgmanagemet(String orgManagementid);

	public abstract HierarchyControl findByUUID(String uuid);

	public abstract List<HierarchyControl> getAllHierarchyControlListByOrgId(String id);

	public abstract List<HierarchyControl> getHierarchyListAccToBranchLevel();  
}
  