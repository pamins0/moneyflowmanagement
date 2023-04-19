package com.emorphis.cashmanagement.service;

import java.util.List;
import java.util.Set;

import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;

public interface BranchGroupService {
	
	void updateBranchGroup(BranchGroup branchGroup);

	List<BranchGroup> getBranchGroupList(BranchGroup branchGroup);

	List<BranchManagement> getBranchManagementListByGroupId(String groupId);

	boolean deleteByGroupId(String groupId);  

	Set<String> getCCGroupList();

	void updateBranchColsedGroup(BranchGroup branchGroup);   

}
