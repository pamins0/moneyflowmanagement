package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.User;

public interface BranchParameterStatusDao {

	boolean deleteAll(List<BranchParameterStatus> branchParameterStatusList, BranchManagement branchManagement);

	List<BranchParameterStatus> getAllBranchParameterStatusListByBranchId(String toBranchId);

	BranchParameterStatus findByUUID(String uuids);

	BranchParameterStatus findEODTotalDetailsByBranchId(String branchId);

	List<BranchParameterStatus> findAllBranchParameterStatusEligibleForAutoApproval();

	/**
	 * @author gourav
	 * @param user
	 * @param branchIdList 
	 * @return
	 */
	Object branchStatusReport(User user, List<String> branchIdList);     

	
  
}
