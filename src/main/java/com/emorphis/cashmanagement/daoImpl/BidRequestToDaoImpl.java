/**
 * 
 */
package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BidRequestToDao;
import com.emorphis.cashmanagement.model.BidRequest;
import com.emorphis.cashmanagement.model.BidRequestedTo;
import com.emorphis.cashmanagement.model.MySession;

/**
 * @author gourav
 *
 */
@Repository
public class BidRequestToDaoImpl extends AbstractDao<String, BidRequestedTo> implements BidRequestToDao {

	@Autowired
	MySession mySession;

	@Override
	public BidRequestedTo findByUUID(String uuids) {
		return getByKey(uuids);
	}

	@Override
	public int saveBidRequestTo(BidRequestedTo bidRequestTo) {
		saveOrUpdate(bidRequestTo);
		return 1;
	}

	@Override
	public BidRequestedTo findByIDBidRequestedTo(String bidId, String bidRequestToId) {

		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.createAlias("bidRequestId", "bidRequest");
		if (bidId != null) {
			criteria.add(Restrictions.eq("bidRequest.id", bidId));
		} else {
			criteria.add(Restrictions.eq("id", bidRequestToId));
		}
		// criteria.add(Restrictions.eq("bidRequest.requestStatus",
		// Constant.STATUS_APPROVED));
		criteria.add(Restrictions.eq("requestToBranchId.id", mySession.getUser().getBranchManagement().getId()));
		return (BidRequestedTo) criteria.uniqueResult();
	}

	@Override
	public List<BidRequestedTo> myDashboardBidList(BidRequest bidRequest, List<String> requestPlacesedToBranchIdList) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		criteria.createAlias("bidRequestId", "bidRequestId");
		criteria.createAlias("requestToBranchId", "requestToBranchId");
		criteria.add(Restrictions.eq("deleted", (byte) 0));
		criteria.add(Restrictions.eq("bidRequestId.deleted", (byte) 0));
		criteria.add(Restrictions.eq("deleted", (byte) 0));

		if (bidRequest.getRequestPlacedById() != null) {
			criteria.add(
					Restrictions.eq("bidRequestId.requestPlacedById.id", bidRequest.getRequestPlacedById().getId()));
		}

		if (bidRequest.getRequestStatus() != null) {
			criteria.add(Restrictions.eq("bidRequestId.requestStatus", bidRequest.getRequestStatus()));
		}
		if (requestPlacesedToBranchIdList != null) {
			criteria.add(Restrictions.in("requestToBranchId.id", requestPlacesedToBranchIdList));
		} else {
			criteria.add(Restrictions.eq("requestToBranchId.id", mySession.getUser().getBranchManagement().getId()));
		}
		criteria.add(Restrictions.eq("bidApprove", false));
		return criteria.list();
	}
}
