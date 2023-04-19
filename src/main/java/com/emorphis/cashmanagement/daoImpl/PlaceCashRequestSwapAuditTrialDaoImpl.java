package com.emorphis.cashmanagement.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.PlaceCashRequestSwapAuditTrialDao;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwapAuditTrial;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.DateUtility;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class PlaceCashRequestSwapAuditTrialDaoImpl extends AbstractDao<Integer, PlaceCashRequestSwapAuditTrial> implements PlaceCashRequestSwapAuditTrialDao {

	@Autowired
	CommonFilters commonFilters;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;
	
	@Override
	public List<PlaceCashRequestSwapAuditTrial> getAllSwapBidsAuditTraislBySwapPlaceBidId(
			PlaceCashRequestSwap placeCashRequestSwap) {
		Date fromTimestamp = new Date();
		Date fromDate = DateUtility.getDateWithoutTime(fromTimestamp);
		System.out.println("date is : " + fromDate+" and place cash request swap id is : "+placeCashRequestSwap.getId());

		Criteria criteria = createEntityCriteria();		
		commonFilters.getPlaceCashRequestSwapBidAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(
				Restrictions.eq("place_cash_Request_swap_id", placeCashRequestSwap.getId()));
		// criteria.add(Restrictions.ge("modifiedTime", fromDate));
		criteria.add(Restrictions.eq("request.alias", "PLACED"));
		List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrialsList = criteria.list();
		System.out.println("placeCashRequestSwapAuditTrials List size is : " + placeCashRequestSwapAuditTrialsList.size());
		return placeCashRequestSwapAuditTrialsList;
	}
}
