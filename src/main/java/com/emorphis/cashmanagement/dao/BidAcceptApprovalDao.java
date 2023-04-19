package com.emorphis.cashmanagement.dao;

import com.emorphis.cashmanagement.model.BidAcceptApproval;

/**
 * @author gourav
 *
 */
public interface BidAcceptApprovalDao {

	/**
	 * @param bidRequest
	 * @return
	 */
	int saveBidAcceptApproval(BidAcceptApproval bidAcceptApproval);

	/**
	 * @param uuids
	 * @return
	 */
	BidAcceptApproval findByUUID(String uuids);
}
