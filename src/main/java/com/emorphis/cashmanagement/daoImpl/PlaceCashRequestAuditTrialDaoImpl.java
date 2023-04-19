package com.emorphis.cashmanagement.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.PlaceCashRequestAuditTrialDao;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.model.PlaceCashRequestAuditTrial;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.DateUtility;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class PlaceCashRequestAuditTrialDaoImpl extends AbstractDao<Integer, PlaceCashRequestAuditTrial>
		implements PlaceCashRequestAuditTrialDao {

	@Autowired
	CommonFilters commonFilters;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Override
	public List<PlaceCashRequestAuditTrial> getAllPlacedBidsAuditTraislByPlaceBidId(PlaceCashRequest placeCashRequest) {
		Date fromTimestamp = new Date();
		Date fromDate = DateUtility.getDateWithoutTime(fromTimestamp);
		System.out.println("in placeCashReuestAuditTrial date is : " + fromDate + " and place cash request id is : "
				+ placeCashRequest.getId());

		Criteria criteria = createEntityCriteria();
		commonFilters.getPlaceCashRequestBidAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("place_cash_Request_id", placeCashRequest.getId()));
		// criteria.add(Restrictions.ge("modifiedTime", fromDate));
		criteria.add(Restrictions.eq("request.alias", "PLACED"));
		List<PlaceCashRequestAuditTrial> placeCashRequestAuditTrialsList = criteria.list();
		System.out.println("placeCashRequestSwapAuditTrials List size is : " + placeCashRequestAuditTrialsList.size());
		return placeCashRequestAuditTrialsList;
	}
}
