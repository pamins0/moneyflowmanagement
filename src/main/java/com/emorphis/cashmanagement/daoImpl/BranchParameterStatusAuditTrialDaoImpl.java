package com.emorphis.cashmanagement.daoImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BranchParameterStatusAuditTrialDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatusAuditTrial;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.DateUtility;

@Repository
public class BranchParameterStatusAuditTrialDaoImpl extends AbstractDao<Integer, BranchParameterStatusAuditTrial>
		implements BranchParameterStatusAuditTrialDao {

	@Autowired
	CommonFilters commonFilters;

	@SuppressWarnings("unchecked")
	public List<BranchParameterStatusAuditTrial> getBranchBetweenDatesByBranchIds(Integer[] branchIds,
			Integer[] branchParameterIds, Date fromDate, Date toDate) {

		Criteria criteria = createEntityCriteria();

		if (branchIds != null) {
			criteria.add(Restrictions.in("branchManagement.id", branchIds));
		}

		if (branchParameterIds != null && branchParameterIds.length > 0) {
			criteria.add(Restrictions.in("branchParameter.id", branchParameterIds));
		}

		if (fromDate != null && toDate != null) {
			// criteria.add(Restrictions.between("createdTime", fromDate,
			// toDate));
			criteria.add(Restrictions.ge("createdTime", fromDate));
			criteria.add(Restrictions.lt("createdTime", new Date(toDate.getTime() + (24 * 60 * 60 * 1000))));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<BranchParameterStatusAuditTrial> getBranchBetweenDatesByBranchIds(
			List<BranchManagement> branchManagements, String[] branchParameterIds, Date fromDate, Date toDate) {
		try {
			Criteria criteria = createEntityCriteria();

			if (branchManagements != null) {
				criteria.add(Restrictions.in("branchManagement", branchManagements));
			}

			if (branchParameterIds != null && branchParameterIds.length > 0) {
				criteria.add(Restrictions.in("branchParameter.id", branchParameterIds));
			}

			criteria.add(Restrictions.eq("operations", "After Insert"));

			if (fromDate != null && toDate != null) {
				// criteria.add(Restrictions.between("createdTime", fromDate,
				// toDate));
				criteria.add(Restrictions.ge("createdTime", fromDate));
				criteria.add(Restrictions.lt("createdTime", new Date(toDate.getTime() + (24 * 60 * 60 * 1000))));
			}
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<BranchParameterStatusAuditTrial> getBranchBetweenDatesByBranchIds(String[] branchIds,
			String[] branchParameterIds, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object branchStatusReport(User user, List<String> branchIdList) {

		String cashGivenToCustomer = "Cash dispensed to walkin customer";
		String cashReceivedFromCustomer = "Cash recieved from regular transacting customer";
		String cashGivenToBNA = "Cash given to ATM vendor";
		String cashReceivedFromBNA = "Cash recieved from BNA vendor";
		String cashGivenToCMS = "Cash given to CMS vendor";
		String cashReceivedFromCMS = "Cash recieved from CMS customer";

		Set<String> brancgParameterList = new HashSet<>();
		brancgParameterList.add(cashGivenToCustomer);
		brancgParameterList.add(cashReceivedFromCustomer);
		brancgParameterList.add(cashGivenToBNA);
		brancgParameterList.add(cashReceivedFromBNA);
		brancgParameterList.add(cashGivenToCMS);
		brancgParameterList.add(cashReceivedFromCMS);

		Criteria criteria = createEntityCriteria();
		criteria = commonFilters.getBranchParameterLevelAliasForDelete(criteria);

		// criteria.add(Restrictions.eq("branch.requestApprovalType", (byte)0));

		criteria.add(Restrictions.eq("status", "APPROVED"));

		criteria.add(Restrictions.in("branchParam.parameterAbbreviation", brancgParameterList));
		criteria.add(Restrictions.in("branch.id", branchIdList));

		if (user.getBranchManagement() != null) {

			if (user.getBranchManagement().getHierarchyControl() != null
					&& user.getBranchManagement().getHierarchyControl().getId() != null
					&& user.getBranchManagement().getHierarchyControl().getId().length() > 0
					&& !user.getBranchManagement().getHierarchyControl().getId().equals("-1")) {
				criteria.add(Restrictions.eq("hier.id", user.getBranchManagement().getHierarchyControl().getId()));
			}

			if (user.getBranchManagement().getId() != null && user.getBranchManagement().getId().length() > 0) {
				criteria.add(Restrictions.eq("branch.id", user.getBranchManagement().getId()));
			}
		}
		if (user.getFromDate() != null) {
			criteria.add(Restrictions.ge("dashboardFinalBidModifiedTime", user.getFromDate()));
		}
		if (user.getToDate() != null) {
			criteria.add(Restrictions.le("dashboardFinalBidModifiedTime", DateUtility.getEndDateTime(user.getToDate())));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("dashboardFinalBidModifiedTime")).addOrder(new BranchParameterASC());
		// .addOrder(Order.asc("branchParam.parameterAbbreviation")).addOrder(Order.asc("branchParam.parameterDetails"));
		List<BranchParameterStatusAuditTrial> branchParameterStatusList = (List<BranchParameterStatusAuditTrial>) criteria
				.list();

		return branchParameterStatusList;
	}

	class BranchParameterASC extends Order {
		public BranchParameterASC() {
			super("", true);
		}

		@Override
		public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
			return "case \n" + "            when branchpara1_.parameter_abbreviation \n"
					+ "            like \"Cash dispensed to walkin customer\" Then 1 \n"
					+ "            when branchpara1_.parameter_abbreviation \n"
					+ "            like  \"Cash recieved from regular transacting customer\" Then 2 \n"
					+ "            when branchpara1_.parameter_abbreviation \n"
					+ "            like  \"Cash given to ATM vendor\" Then 3 \n"
					+ "            when branchpara1_.parameter_abbreviation \n"
					+ "            like  \"Cash recieved from BNA vendor\" Then 4 \n"
					+ "            when branchpara1_.parameter_abbreviation \n"
					+ "            like  \"Cash given to CMS vendor\" Then 5 \n"
					+ "            when branchpara1_.parameter_abbreviation \n"
					+ "            like  \"Cash recieved from CMS customer\" Then 6\n" + "\t\tend";
		}
	}
	
	class BranchOrders extends Order{

		protected BranchOrders(String propertyName, boolean ascending) {
			super(propertyName, ascending);
			// TODO Auto-generated constructor stub
		}
		
		
	}
}
