package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.model.RequestAjax;

public interface PlaceCashRequestService {

	void saveBranchCashRequest(PlaceCashRequest placeCashRequest);

	boolean saveBranchCashRequestFromDashboard(RequestAjax requestAjax);

	List<PlaceCashRequest> getAllPlacedBidForTheBranch(BranchManagement branchManagement);

	boolean updateBidForApproval(RequestAjax requestAjax);

	PlaceCashRequest findById(String placedRequestId);

	boolean updateBidForCancel(RequestAjax requestAjax);

	boolean updateAndApproveBidPlaced(RequestAjax requestAjax);

	List<PlaceCashRequest> getAllPlacedBidForTheUserBranch(BranchManagement branchManagement);        

}
