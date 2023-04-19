package com.emorphis.cashmanagement.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.PlaceCashRequestDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.DateUtility;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class PlaceCashRequestDaoImpl extends AbstractDao<String, PlaceCashRequest> implements PlaceCashRequestDao {

	@Autowired
	CommonFilters commonFilters;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Override
	public void saveBranchCashRequest(PlaceCashRequest placeCashRequest) {
		persist(placeCashRequest);
	}

	@Override
	public boolean saveBranchCashRequestFromDashboard(PlaceCashRequest placeCashRequest) {
		boolean flag = false;
		try {
			persist(placeCashRequest);
			flag = true;
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<PlaceCashRequest> getAllPlacedBidForTheBranch(BranchManagement branchManagement) {
		Date fromTimestamp = new Date();
		Date fromDate = DateUtility.getDateWithoutTime(fromTimestamp);
		System.out.println("date is : " + fromDate);

		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("amount"));
		commonFilters.getPlaceCashRequestBidAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(
				Restrictions.eq("orgManage.id", branchManagement.getHierarchyControl().getOrgManagement().getId()));
		// criteria.add(Restrictions.ge("modifiedTime", fromDate));
		criteria.add(Restrictions.eq("request.alias", "PLACED"));
		List<PlaceCashRequest> placeCashRequestsList = criteria.list();
		System.out.println("placeCashRequests List size is : " + placeCashRequestsList.size());
		return placeCashRequestsList;
	}
	
	@Override
	public List<PlaceCashRequest> getAllPlacedBidForTheUserBranch(BranchManagement branchManagement) {
		Date fromTimestamp = new Date();
		Date fromDate = DateUtility.getDateWithoutTime(fromTimestamp);
		System.out.println("date is : " + fromDate);

		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("amount"));
		commonFilters.getPlaceCashRequestBidAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(
				Restrictions.eq("branch.id", branchManagement.getId()));		
		criteria.add(
				Restrictions.eq("orgManage.id", branchManagement.getHierarchyControl().getOrgManagement().getId()));
		// criteria.add(Restrictions.ge("modifiedTime", fromDate));
		criteria.add(Restrictions.eq("request.alias", "PLACED"));
		List<PlaceCashRequest> placeCashRequestsList = criteria.list();
		System.out.println("placeCashRequests List size is : " + placeCashRequestsList.size());
		return placeCashRequestsList;
	}
	
	@Override
	public PlaceCashRequest findById(String id) {
		return getByKey(id);
	}
	
	@Override
	public PlaceCashRequest findByUUID(String uuids) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		PlaceCashRequest placeCashRequest = (PlaceCashRequest) criteria.uniqueResult();
		return placeCashRequest;
	}
}
