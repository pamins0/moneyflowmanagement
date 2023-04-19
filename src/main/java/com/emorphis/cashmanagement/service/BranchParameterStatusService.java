package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.Denomination;
import com.emorphis.cashmanagement.model.User;

public interface BranchParameterStatusService {

	void saveBranchEodPosition(BranchParameterStatus branchParameterStatus);

	List<BranchParameterStatus> getAllBranchParameterStatusListByBranchId(String string);

	BranchParameterStatus findEODTotalDetailsByBranchId(String id);

	boolean updateBranchParameterStatusAndProcessForDashboard(DashboardFinalBid dashboardFinalBid, String string) throws Exception;

	boolean cancelEODApprovalForBranch(BranchManagement br, String status);

	boolean approveEODApprovalForBranch(BranchManagement branchManagement, String string);

	void autoApproveEODRequestForDashboardProcess();

	/**
	 * Report of branch status according to denomination and branch parameter
	 * @author gourav
	 * @param user
	 * @return
	 */
	Object branchStatusReport(User user);

	/**
	 * @param branchManagementId
	 */
	Denomination getBranchCurrentCashPosition(String branchManagementId);       
 
}
 