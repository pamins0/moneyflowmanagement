package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;

public interface PlaceCashRequestSwapDao {

	boolean saveBranchSwapFromSwapingDashboard(PlaceCashRequestSwap placeCashRequestSwap);

	List<PlaceCashRequestSwap> getAllPlacedSwapBidForTheBranch(BranchManagement branchManagement);

	List<PlaceCashRequestSwap> getAllPlacedSwapBidForTheUserBranch(BranchManagement br);

	PlaceCashRequestSwap findById(String string);

	PlaceCashRequestSwap findByUUID(String uuids);	  

}
