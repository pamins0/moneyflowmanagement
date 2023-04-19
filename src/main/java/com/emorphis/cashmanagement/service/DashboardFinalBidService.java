package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.RequestAjax;

public interface DashboardFinalBidService {

	List<DashboardFinalBid> getAllBranchesListhavingExcessCash();

	List<DashboardFinalBid> getAllBranchesListhavingExcessAndBelowCash();

	List<DashboardFinalBid> getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();

	boolean deleteAllOldBranchEntry(BranchManagement branchManagement);

	DashboardFinalBid findDashboardByBranchId(String string); 

	DashboardFinalBid findDashboardByBranchIdAndDashboardId(int parseInt, String parseInt2);

	boolean updateDashboardBidStatusForWithdrawn(RequestAjax requestAjax);

	void updateById(DashboardFinalBid dashboardFinalBid);

	void save(DashboardFinalBid dashboardFinalBid) throws Exception;

	        
 
}
