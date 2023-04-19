package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;
import com.emorphis.cashmanagement.model.RequestAjax;

public interface PlaceCashRequestSwapService { 

	boolean saveBranchSwapFromSwapingDashboard(PlaceCashRequestSwap placeCashRequestSwap);

	List<PlaceCashRequestSwap> getAllPlacedSwapBidForTheBranch(BranchManagement branchManagement);

	List<PlaceCashRequestSwap> getAllPlacedSwapBidForTheUserBranch(BranchManagement br);

	PlaceCashRequestSwap findById(String parseInt);

	boolean updateBidForCancel(RequestAjax requestAjax);

	boolean updateBidForApproval(PlaceCashRequestSwap placeCashRequestSwap);     

}
