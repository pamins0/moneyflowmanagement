/**
 * 
 */
package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.BidRequest;
import com.emorphis.cashmanagement.model.BidRequestedTo;

/**
 * @author gourav
 *
 */
public interface BidRequestService {

	/**
	 * @param bidRequest
	 * @return
	 */
	int saveBidRequest(BidRequest bidRequest);

	/**
	 * @param bidRequest
	 * @param requestPlacesedByBranchList 
	 * @return
	 */
	List<BidRequest> bidRequestList(BidRequest bidRequest, List<String> requestPlacesedByBranchIdList);


	/**
	 * @param id
	 * @return
	 */
	int bidApproval(String id);

	/**
	 * @param bidRequest
	 * @param requestPlacesedByBranchIdList 
	 * @return
	 */
	List<BidRequestedTo> myDashboardBidList(BidRequest bidRequest, List<String> requestPlacesedByBranchIdList);

	/**
	 * @param id
	 * @return
	 */
	BidRequest findByID(String id);

	/**
	 * @param id
	 * @return
	 */
	BidRequestedTo findByIDBidRequestedTo(String id);

	/**
	 * @param id
	 * @return
	 */
	int saveBidRequestedTo(String id,BidRequestedTo bidRequestedTo);

	/**
	 * @param bidRequestedToId
	 * @param bidRequestedTo
	 */
	int bidAcceptApprove(String bidRequestedToId);

}
