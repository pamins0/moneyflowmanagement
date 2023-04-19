package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.BranchCorrespondentGroup;

public interface BranchCorrespondentDao {

	void saveBranchCorrespondentGroup(BranchCorrespondentGroup branchCorrespondentGroup2);

	void deleteBranchCorrespondentGroup(List<String> deletedList, BranchCorrespondentGroup branchCorrespondentGroup);
 
}
