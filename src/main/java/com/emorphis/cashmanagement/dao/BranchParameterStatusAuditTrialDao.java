package com.emorphis.cashmanagement.dao;

import java.util.Date;
import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatusAuditTrial;
import com.emorphis.cashmanagement.model.User;

public interface BranchParameterStatusAuditTrialDao {

	List<BranchParameterStatusAuditTrial> getBranchBetweenDatesByBranchIds(String[] branchIds,
			String[] branchParameterIds, Date fromDate, Date toDate);

	List<BranchParameterStatusAuditTrial> getBranchBetweenDatesByBranchIds(List<BranchManagement> branchManagements,
			String[] branchParameterIds, Date fromDate, Date toDate);

	/**
	 * @author gourav
	 * @param user
	 * @param branchIdList
	 * @return
	 */
	Object branchStatusReport(User user, List<String> branchIdList);
}
