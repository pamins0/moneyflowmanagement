/**
 * 
 */
package com.emorphis.cashmanagement.daoImpl;

import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BidRequestApprovalDao;
import com.emorphis.cashmanagement.model.BidRequestApproval;

/**
 * @author gourav
 *
 */
@Repository
public class BidRequestApprovalDaoImpl extends AbstractDao<String, BidRequestApproval> implements BidRequestApprovalDao {

	@Override
	public BidRequestApproval findByUUID(String uuids) {
		// TODO Auto-generated method stub
		return getByKey(uuids);
	}

	@Override
	public int saveBidRequestApproval(BidRequestApproval bidRequest) {
		// TODO Auto-generated method stub
		saveOrUpdate(bidRequest);
		return 1;
	}


}
