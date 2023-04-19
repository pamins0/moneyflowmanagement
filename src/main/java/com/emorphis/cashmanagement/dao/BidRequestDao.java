package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.BidRequest;

/**
 * @author gourav
 *
 */
public interface BidRequestDao {

	/**
	 * @param bidRequest
	 * @return
	 */
	int saveBidRequest(BidRequest bidRequest);

	/**
	 * @param uuids
	 * @return
	 */
	BidRequest findByUUID(String uuids);

	/**
	 * @param bidRequest
	 * @param requestPlacesedByBranchList 
	 */
	List<BidRequest> bidRequestList(BidRequest bidRequest, List<String> requestPlacesedByBranchIdList);

	/**
	 * @param bidRequest
	 * @param requestPlacesedByBranchIdList 
	 * @return
	 */
	List<BidRequest> myDashboardBidList(BidRequest bidRequest, List<String> requestPlacesedByBranchIdList);

	/**
	 * @param id
	 * @return
	 */
	BidRequest findByID(String id);

}
