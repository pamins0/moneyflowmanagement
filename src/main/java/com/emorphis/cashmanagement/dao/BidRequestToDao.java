package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.BidRequest;
import com.emorphis.cashmanagement.model.BidRequestedTo;

/**
 * @author gourav
 *
 */
public interface BidRequestToDao {

	/**
	 * @param bidRequest
	 * @return
	 */
	int saveBidRequestTo(BidRequestedTo bidRequestedTo);

	/**
	 * @param uuids
	 * @return
	 */
	BidRequestedTo findByUUID(String uuids);

	/**
	 * @param bidId
	 * @return
	 */
	BidRequestedTo findByIDBidRequestedTo(String bidId, String bidRequestToId);

	/**
	 * @param bidRequest
	 * @param requestPlacesedByBranchIdList
	 * @return
	 */
	List<BidRequestedTo> myDashboardBidList(BidRequest bidRequest, List<String> requestPlacesedByBranchIdList);

}
