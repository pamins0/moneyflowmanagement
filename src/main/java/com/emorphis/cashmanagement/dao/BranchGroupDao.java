package com.emorphis.cashmanagement.dao;

import java.util.List;
import java.util.Set;

import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;

public interface BranchGroupDao {

	BranchGroup findByUUID(String uuids);
	
	List<BranchGroup> getGroupBranchList(BranchGroup branchGroup);

	boolean saveBranchGroup(BranchGroup branchGroupObj);

	void deleteBranchGroup(List<String> deletedVA);

	List<BranchManagement> getBranchManagementListByGroupId(String groupId);

	boolean deleteByGroupId(String groupId);

	Set<String> getCCGroupList();  
  
}
