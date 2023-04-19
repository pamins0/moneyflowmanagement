package com.emorphis.cashmanagement.service;

import java.util.List;
import java.util.Set;

import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.User;

public interface BranchClosedGroupService {

	List<BranchClosedGroup> getBranchClosedBranchList(BranchManagement branchManagement);

	void updateUserRoles(BranchClosedGroup branchClosedGroup);

	List<BranchGroup> getBranchClosedBranchList(String groupId);

	void updateBranchClosedGroupByBranchClusters(BranchManagement branchManagement2,
			List<BranchManagement> branchClusterList, User user) throws Exception;

	void deletClosedBranchesByParentBranch(BranchManagement branchManagement2,
			Set<BranchClosedGroup> branchClosedGroups) throws Exception;  


}
