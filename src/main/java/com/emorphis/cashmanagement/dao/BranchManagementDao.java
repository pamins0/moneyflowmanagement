package com.emorphis.cashmanagement.dao;

import java.util.List;
import java.util.Set;

import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.HierarchyControl;

public interface BranchManagementDao {

	BranchManagement findById(String branchId);

	List<BranchManagement> getAllBranch();

	int save(BranchManagement management);

	void deleteById(Integer id);

	List<BranchManagement> getAllBranchManagementListByOrgmanagemet(String orgManagementid);

	List<BranchManagement> getAllBranchManagementListByHierarchyId(String parentId);

	List<BranchManagement> getAllBranchNotGrouped();

	List<BranchManagement> getAllBranchGrouped();

	List<BranchManagement> getAllBranchManagementListByHierarchyIdAndTagName(String hierarchyId, String tagName);

	List<BranchManagement> getAllBranchManagementListByHierarchyIdAndAutoComleteNameForBranchControl(String hierarchyId,
			String tagName);

	Object getAllBranchGroupedAndNotgrouped();

	List<BranchManagement> getAllBranchListbyhierarchy(HierarchyControl hierarchyControl);

	List<BranchManagement> getAllBranchListAccToHierarchyOrganization(BranchManagement branchManagement);

	List<BranchManagement> getAllBranchListbyBranchHierarchyAndLowerLevelBranches(BranchManagement branchManagement,
			List<BranchManagement> branchListLowerLevel);

	List<BranchManagement> getAvailableBranchCode(BranchManagement branch);

	List<BranchManagement> getAllBranchesListForMailUtility();

	List<BranchManagement> getAllBranchListByBranchHierarchy(BranchManagement branchManagement);

	void deletClosedGroupSetByParentBranch(BranchManagement branchManagement, Set<BranchClosedGroup> removeUserRoleSet) throws Exception;

	void deletCrossClosedGroupSetByParentBranch(BranchManagement branchManagement,
			Set<BranchClosedGroup> removeUserRoleSet);

	List<BranchManagement> getAllCurrencyChestList();

	BranchManagement findByUUID(String uuid);

	List<BranchManagement> getRemainingBranchListByBranchHierarchy(List<BranchGroup> branchGroupList);

	List<BranchManagement> getAllCurrencyChestListExcludingBranch(Set<BranchManagement> branchCCListFromGroup);

	List<BranchManagement> getAllBranchManagementListForClusterMaking(BranchManagement branchManagement);

	BranchManagement findByIdWithDelete(String id);

	List<BranchManagement> getCorrespondentBranchList(BranchManagement branchManagement,
			Set<BranchManagement> associatedCorrespondentBranchesList);  
} 
