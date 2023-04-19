package com.emorphis.cashmanagement.dao;

import java.util.List;
import java.util.Set;

import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;

public interface BranchClosedGroupDao {

	List<BranchClosedGroup> getBranchClosedBranchList(BranchManagement branchManagement);

	void saveCrossBranchClosedGroupByparentBranch(BranchClosedGroup branchClosedGroup, Set<BranchClosedGroup> branchClosedGroupsSet);

	BranchClosedGroup findByUUID(String uuids);

	List<BranchGroup> getGroupBranchList(String groupId);  
  
}
