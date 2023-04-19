package com.emorphis.cashmanagement.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.PlaceCashRequestSwapDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.DateUtility;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class PlaceCashRequestSwapDaoImpl extends AbstractDao<String, PlaceCashRequestSwap>
		implements PlaceCashRequestSwapDao {

	@Autowired
	CommonFilters commonFilters;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Override
	public PlaceCashRequestSwap findById(String id) {
		return getByKey(id);
	}
	
	@Override
	public boolean saveBranchSwapFromSwapingDashboard(PlaceCashRequestSwap placeCashRequestSwap) {
		boolean flag = false;
		try {
			persist(placeCashRequestSwap);
			flag = true;
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<PlaceCashRequestSwap> getAllPlacedSwapBidForTheBranch(BranchManagement branchManagement) {
		Date fromTimestamp = new Date();
		Date fromDate = DateUtility.getDateWithoutTime(fromTimestamp);
		System.out.println("date is : " + fromDate);

		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("amount"));
		commonFilters.getPlaceCashRequestSwapBidAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(
				Restrictions.eq("orgManage.id", branchManagement.getHierarchyControl().getOrgManagement().getId()));
		// criteria.add(Restrictions.ge("modifiedTime", fromDate));
		criteria.add(Restrictions.eq("request.alias", "PLACED"));
		List<PlaceCashRequestSwap> placeCashSwapRequestsList = criteria.list();
		System.out.println("placeCashRequests List size is : " + placeCashSwapRequestsList.size());
		return placeCashSwapRequestsList;
	}

	@Override
	public List<PlaceCashRequestSwap> getAllPlacedSwapBidForTheUserBranch(BranchManagement br) {
		Date fromTimestamp = new Date();
		Date fromDate = DateUtility.getDateWithoutTime(fromTimestamp);
		System.out.println("date is : " + fromDate);

		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("amount"));
		commonFilters.getPlaceCashRequestSwapBidAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Criterion branchFrom = Restrictions.eq("branch.id", br.getId());
		Criterion branchTo = Restrictions.eq("branch1.id", br.getId());
		criteria.add(Restrictions.or(branchFrom, branchTo));
//		criteria.add(Restrictions.eq("branch.id", br.getId()));
		criteria.add(Restrictions.eq("orgManage.id", br.getHierarchyControl().getOrgManagement().getId()));
		// criteria.add(Restrictions.ge("modifiedTime", fromDate));
		criteria.add(Restrictions.eq("request.alias", "PLACED"));
		List<PlaceCashRequestSwap> placeCashSwapRequestsList = criteria.list();
		System.out.println("placeCashRequests List size is : " + placeCashSwapRequestsList.size());
		return placeCashSwapRequestsList;
	}
	
	@Override
	public PlaceCashRequestSwap findByUUID(String uuids) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		PlaceCashRequestSwap placeCashRequestSwap = (PlaceCashRequestSwap) criteria.uniqueResult();
		return placeCashRequestSwap;
	}
}
