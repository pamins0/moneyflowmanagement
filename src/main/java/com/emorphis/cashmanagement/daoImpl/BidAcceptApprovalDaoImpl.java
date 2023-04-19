/**
 * 
 */
package com.emorphis.cashmanagement.daoImpl;

import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BidAcceptApprovalDao;
import com.emorphis.cashmanagement.model.BidAcceptApproval;

/**
 * @author gourav
 *
 */
@Repository
public class BidAcceptApprovalDaoImpl extends AbstractDao<String, BidAcceptApproval> implements BidAcceptApprovalDao {

	@Override
	public BidAcceptApproval findByUUID(String uuids) {
		return getByKey(uuids);
	}

	@Override
	public int saveBidAcceptApproval(BidAcceptApproval bidAcceptApproval) {
		saveOrUpdate(bidAcceptApproval);
		return 1;
	}
}
