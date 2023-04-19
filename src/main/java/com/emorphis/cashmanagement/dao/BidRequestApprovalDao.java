package com.emorphis.cashmanagement.dao;

import com.emorphis.cashmanagement.model.BidRequestApproval;

/**
 * @author gourav
 *
 */
public interface BidRequestApprovalDao {

	/**
	 * @param bidRequest
	 * @return
	 */
	int saveBidRequestApproval(BidRequestApproval bidRequest);

	/**
	 * @param uuids
	 * @return
	 */
	BidRequestApproval findByUUID(String uuids);
}
