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
import com.emorphis.cashmanagement.dao.BidRequestDao;
import com.emorphis.cashmanagement.model.BidRequest;
import com.emorphis.cashmanagement.model.MySession;

/**
 * @author gourav
 *
 */
@Repository
public class BidRequestDaoImpl extends AbstractDao<String, BidRequest> implements BidRequestDao {

	@Autowired
	MySession mySession;

	@Override
	public BidRequest findByUUID(String uuids) {
		return getByKey(uuids);
	}

	@Override
	public BidRequest findByID(String id) {
		return getByKey(id);
	}

	@Override
	public int saveBidRequest(BidRequest bidRequest) {
		saveOrUpdate(bidRequest);
		return 1;
	}

	@Override
	public List<BidRequest> bidRequestList(BidRequest bidRequest, List<String> requestPlacesedByBranchIdList) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("deleted", (byte) 0));
		if (requestPlacesedByBranchIdList != null) {
			criteria.add(Restrictions.or(Restrictions.in("requestPlacedById.id", requestPlacesedByBranchIdList),
					Restrictions.in("requestAcceptById.id", requestPlacesedByBranchIdList)));
		}
		if (bidRequest.getRequestStatus() != null) {
			criteria.add(Restrictions.eq("", bidRequest.getRequestStatus()));
		}
		return criteria.list();
	}

	@Override
	public List<BidRequest> myDashboardBidList(BidRequest bidRequest, List<String> requestPlacesedToBranchIdList) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		criteria.createAlias("bidRequestedTo", "bidRequestedTo");
		criteria.createAlias("bidRequestedTo.requestToBranchId", "requestToBranchId");
		criteria.add(Restrictions.eq("deleted", (byte) 0));
		criteria.add(Restrictions.eq("bidRequestedTo.deleted", (byte) 0));

		if (bidRequest.getRequestPlacedById() != null) {
			criteria.add(Restrictions.eq("requestPlacedById.id", bidRequest.getRequestPlacedById().getId()));
		}

		if (bidRequest.getRequestStatus() != null) {
			criteria.add(Restrictions.eq("requestStatus", bidRequest.getRequestStatus()));
		}
		if (requestPlacesedToBranchIdList != null) {
			criteria.add(Restrictions.in("requestToBranchId.id", requestPlacesedToBranchIdList));
		} else {
			criteria.add(Restrictions.eq("requestToBranchId.id", mySession.getUser().getBranchManagement().getId()));
		}
		criteria.add(Restrictions.eq("bidRequestedTo.bidApprove", false));
		return criteria.list();
	}

}
