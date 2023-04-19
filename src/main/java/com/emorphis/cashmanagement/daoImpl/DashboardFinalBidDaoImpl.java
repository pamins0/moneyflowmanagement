package com.emorphis.cashmanagement.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.DashboardFinalBidDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.DateUtility;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class DashboardFinalBidDaoImpl extends AbstractDao<Integer, DashboardFinalBid> implements DashboardFinalBidDao {

	@Autowired
	CommonFilters commonFilters;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;
	
	@Autowired
	Environment environment;

	@Override
	public List<DashboardFinalBid> getAllBranchesListhavingExcessCash() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("branchManagement.hierarchyControl.id"));
		commonFilters.getDashboardFinalBidAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		String orgId = environment.getProperty("yesbank.organization");
		criteria.add(Restrictions.eq("orgManage.id", orgId)); 
		criteria.add(Restrictions.eq("position", "max"));

		return criteria.list();
	}

	@Override
	public List<DashboardFinalBid> getAllBranchesListhavingExcessAndBelowCash() {
		Criteria criteria = createEntityCriteria();
		commonFilters.getDashboardFinalBidAliasForDelete(criteria);
		criteria.addOrder(Order.asc("hier.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	//	String orgId = "415c4a68-c4e9-473a-87fe-b162baa2efa8";
		String orgId = environment.getProperty("yesbank.organization");
		criteria.add(Restrictions.eq("orgManage.id", orgId));
		// criteria.add(Restrictions.eq("position", "max"));
		List<DashboardFinalBid> dashboardFinalBidsList = criteria.list();
		System.out.println("dashboard list size is : " + dashboardFinalBidsList.size());
		return criteria.list();
	}

	@Override
	public List<DashboardFinalBid> getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid() {

		Date fromTimestamp = new Date();
		Date fromDate = DateUtility.getDateWithoutTime(fromTimestamp);

		System.out.println("date is : " + fromDate);

		Criteria criteria = createEntityCriteria();
		commonFilters.getDashboardFinalBidAliasForDelete(criteria);
		criteria.addOrder(Order.asc("hier.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("orgManage.id",
				mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId()));
		// criteria.add(Restrictions.ge("modifiedTime", fromDate));

		criteria.add(Restrictions.and(Restrictions.ne("request.alias", "PENDING"), Restrictions.ne("request.alias", "CANCEL")));
		List<DashboardFinalBid> dashboardFinalBidsList = criteria.list();
		System.out.println("dashboard list size is : " + dashboardFinalBidsList.size());
		return criteria.list();
	}

	@Override
	public boolean deleteAllOldBranchEntry(BranchManagement branchManagement) {
		Session session = getSession();
		String hql = "delete from DashboardFinalBid where branchManagement.id ='" + branchManagement.getId()+"'";
		Query query = session.createQuery(hql);
		/* System.out.println("Query to delete userRoles is : " + query); */
		int i = query.executeUpdate();
		System.out.println("Query HQL : " + i);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public DashboardFinalBid findDashboardByBranchId(String branchId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("branchManagement.id", branchId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		DashboardFinalBid dashboardFinalBid = (DashboardFinalBid) criteria.uniqueResult();
		return dashboardFinalBid;
	}

	@Override
	public DashboardFinalBid findDashboardByBranchIdAndDashboardId(int dashboardId, String branchId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", dashboardId));
		criteria.add(Restrictions.eq("branchManagement.id", branchId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		DashboardFinalBid dashboardFinalBid = (DashboardFinalBid) criteria.uniqueResult();
		return dashboardFinalBid;
	}

	@Override
	public DashboardFinalBid findById(int id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		DashboardFinalBid dashboardFinalBid = (DashboardFinalBid) criteria.uniqueResult();
		return dashboardFinalBid;
	}
	
	@Override
	public void save(DashboardFinalBid dashboardFinalBid) throws Exception {
		persist(dashboardFinalBid);		
	}
}
