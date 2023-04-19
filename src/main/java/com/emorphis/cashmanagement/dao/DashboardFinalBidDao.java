package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBid;

public interface DashboardFinalBidDao {

	List<DashboardFinalBid> getAllBranchesListhavingExcessCash();

	List<DashboardFinalBid> getAllBranchesListhavingExcessAndBelowCash();

	List<DashboardFinalBid> getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();

	boolean deleteAllOldBranchEntry(BranchManagement branchManagement);

	DashboardFinalBid findDashboardByBranchId(String branchId); 

	DashboardFinalBid findDashboardByBranchIdAndDashboardId(int dashboardId, String branchId); 

	DashboardFinalBid findById(int id);

	void save(DashboardFinalBid dashboardFinalBid) throws Exception;  

 
}
