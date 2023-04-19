package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.PlaceCashRequest;

public interface PlaceCashRequestDao {

	void saveBranchCashRequest(PlaceCashRequest placeCashRequest);

	boolean saveBranchCashRequestFromDashboard(PlaceCashRequest placeCashRequest);

	List<PlaceCashRequest> getAllPlacedBidForTheBranch(BranchManagement branchManagement);

	PlaceCashRequest findById(String parseInt);

	List<PlaceCashRequest> getAllPlacedBidForTheUserBranch(BranchManagement branchManagement);

	PlaceCashRequest findByUUID(String uuids);    
  
}
