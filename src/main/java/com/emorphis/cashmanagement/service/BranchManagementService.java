package com.emorphis.cashmanagement.service;

import java.util.List;
import java.util.Set;

import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.HierarchyControl;

public interface BranchManagementService {

	BranchManagement findById(String id);

	List<BranchManagement> getAllBranch();

	String save(BranchManagement management);

	void deleteById(String id);

	boolean updateById(BranchManagement branchManagement);

	List<BranchManagement> getAllBranchManagementListByOrgmanagemet(String orgManagementid);

	List<BranchManagement> getAllBranchManagementListByHierarchyId(String i);

	List<BranchManagement> getAllBranchnotGrouped();

	List<BranchManagement> getAllBranchGrouped();

	List<BranchManagement> getAllBranchManagementListByHierarchyIdAndAutoComleteName(String hierarchyId,
			String tagName);

	List<BranchManagement> getAllBranchManagementListByHierarchyIdAndAutoComleteNameForBranchControl(String hierarchyId,
			String tagName);

	Object getAllBranchGroupedAndNotgrouped();

	List<BranchManagement> getAllBranchListbyhierarchy(HierarchyControl hierarchyControl);

	List<BranchManagement> getAllBranchListAccToHierarchyOrganization(BranchManagement branchManagement);

	List<BranchManagement> getAllBranchListbyBranchHierarchyAndLowerLevelBranches(BranchManagement branchManagement,
			List<BranchManagement> branchListLowerLevel);

	BranchManagement getAvailableBranchCode(BranchManagement branch);
 
	List<BranchManagement> getAllBranchesListForMailUtility();

	List<BranchManagement> getAllBranchListByBranchHierarchy(BranchManagement branchManagement);

	void saveBranchEodPosition(BranchParameterStatus branchParameterStatus);

	/**
	 * Currency Chest methods......
	 * 
	 * @param branch
	 * @return
	 */

	int saveChest(BranchManagement branch);

	List<BranchManagement> getAllCurrencyChestList();

	BranchManagement findByUUID(String uuid);

	List<BranchManagement> getRemainingBranchListByBranchHierarchy(List<BranchGroup> branchGroupList);

	List<BranchManagement> getAllCurrencyChestListExcludingBranch(Set<BranchManagement> branchCCListFromGroup);

	List<BranchManagement> getAllBranchManagementListForClusterMaking(BranchManagement branch);

	BranchManagement findByIdWithDelete(String id);

	List<BranchManagement> getCorrespondentBranchList(BranchManagement branchManagement,
			Set<BranchManagement> associatedCorrespondentBranchesList);   

	/*
	 * void updateByUUID(BranchManagement branch);
	 * 
	 * void deleteByUUID(String uuid);
	 */
}
