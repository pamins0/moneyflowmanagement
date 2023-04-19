package com.emorphis.cashmanagement.service;

import java.util.Date;
import java.util.List;

import com.emorphis.cashmanagement.model.BranchParameterStatusAuditTrial;
import com.emorphis.cashmanagement.model.User;

public interface BranchParameterStatusAuditTrialService {
	List<BranchParameterStatusAuditTrial> getBranchBetweenDatesByBranchId(String branchId, String[] branchParameterIds,
			Date fromDate, Date toDate);

	List<BranchParameterStatusAuditTrial> getBranchBetweenDatesByHierarchy(String hierarchyId,
			String[] branchParameterIds, Date fromDate, Date toDate);

	/**
	 * Report of branch status according to denomination and branch parameter
	 * 
	 * @author gourav
	 * @param user
	 * @return
	 */
	Object branchStatusReport(User user);
}
