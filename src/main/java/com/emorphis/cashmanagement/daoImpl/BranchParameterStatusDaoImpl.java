package com.emorphis.cashmanagement.daoImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BranchParameterStatusDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameter;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class BranchParameterStatusDaoImpl extends AbstractDao<Integer, BranchParameterStatus>
		implements BranchParameterStatusDao {

	@Autowired
	CommonFilters commonFilters;

	@Autowired
	Utility utility;

	@Override
	public boolean deleteAll(List<BranchParameterStatus> branchParameterStatusList, BranchManagement branchManagement) {
		Session session = getSession();
		String hql = "delete from BranchParameterStatus where branchManagement.id ='" + branchManagement.getId() + "'";
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
	public List<BranchParameterStatus> getAllBranchParameterStatusListByBranchId(String toBranchId) {
		List<BranchParameterStatus> branchParameterStatusList = new ArrayList<BranchParameterStatus>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		// commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		// criteria.add(Restrictions.eq("orgManagement.id",
		// mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId()));
		criteria.add(Restrictions.eq("branchManagement.id", toBranchId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		branchParameterStatusList = criteria.list();
		return branchParameterStatusList;
	}

	@Override
	public BranchParameterStatus findByUUID(String uuid) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuid));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		BranchParameterStatus branchParameterStatus = (BranchParameterStatus) criteria.uniqueResult();
		return branchParameterStatus;
	}

	@Override
	public BranchParameterStatus findEODTotalDetailsByBranchId(String branchId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("branchManagement.id", branchId));
		BranchParameter branchParameter = utility.getBranchParameterValue("Total Cash Availability");
		criteria.add(Restrictions.eq("branchParameter.id", branchParameter.getId()));
		// criteria.add(Restrictions.eq("branchParameter.id", "7"));
		// Previous done
		criteria.addOrder(Order.desc("createdTime"));
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (BranchParameterStatus) criteria.uniqueResult();
	}

	@Override
	public List<BranchParameterStatus> findAllBranchParameterStatusEligibleForAutoApproval() {
		List<BranchParameterStatus> branchParameterStatusList = new ArrayList<BranchParameterStatus>();
		Criteria criteria = createEntityCriteria();
		criteria = commonFilters.getBranchParameterLevelAliasForDelete(criteria);
		BranchParameter branchParameter = utility.getBranchParameterValue("Total Cash Availability");
		criteria.add(Restrictions.eq("branchParam.id", branchParameter.getId()));
		criteria.add(Restrictions.eq("branch.requestApprovalType", (byte) 0));
		criteria.add(Restrictions.eq("status", "PENDING"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		branchParameterStatusList = (List<BranchParameterStatus>) criteria.list();
		return branchParameterStatusList;
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

		// criteria.add(Restrictions.eq("branch.requestApprovalType", (byte)
		// 0));
		// criteria.add(Restrictions.eq("status", "PENDING"));

		criteria.add(Restrictions.in("branchParam.parameterAbbreviation", brancgParameterList));
		criteria.add(Restrictions.in("branch.id", branchIdList));

		if (user.getBranchManagement().getId() != null && user.getBranchManagement().getId().length() > 0) {
			criteria.add(Restrictions.eq("branch.id", user.getBranchManagement().getId()));
		}
		if (user.getFromDate() != null) {
			criteria.add(Restrictions.ge("modifiedTime", user.getFromDate()));
		}
		if (user.getToDate() != null) {
			criteria.add(Restrictions.le("modifiedTime", user.getToDate()));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("modifiedBy")).addOrder(Order.asc("branchParam.parameterAbbreviation"))
				.addOrder(Order.asc("branchParam.parameterDetails"));
		List<BranchParameterStatus> branchParameterStatusList = (List<BranchParameterStatus>) criteria.list();

		/*
		 * Map<String, List<BranchParameterStatus>> branchStatusMap= new
		 * HashMap<>(); for (BranchParameterStatus branchParameterStatus :
		 * branchParameterStatusList) { String flow="";
		 * if(cashGivenToCustomer.equals(branchParameterStatus.
		 * getBranchParameter().getParameterAbbreviation())){
		 * branchParameterStatus.setPrameterType("Branch"); flow="In Flow"; }
		 * else if(cashReceivedFromCustomer.equals(branchParameterStatus.
		 * getBranchParameter().getParameterAbbreviation())){
		 * branchParameterStatus.setPrameterType("Branch"); flow="Out Flow"; }
		 * else
		 * if(cashGivenToBNA.equals(branchParameterStatus.getBranchParameter().
		 * getParameterAbbreviation())){
		 * branchParameterStatus.setPrameterType("BNA"); flow="In Flow"; } else
		 * if(cashReceivedFromBNA.equals(branchParameterStatus.
		 * getBranchParameter().getParameterAbbreviation())){
		 * branchParameterStatus.setPrameterType("BNA"); flow="Out Flow"; } else
		 * if(cashGivenToCMS.equals(branchParameterStatus.getBranchParameter().
		 * getParameterAbbreviation())){
		 * branchParameterStatus.setPrameterType("CMS"); flow="In Flow"; } else
		 * if(cashReceivedFromCMS.equals(branchParameterStatus.
		 * getBranchParameter().getParameterAbbreviation())){
		 * branchParameterStatus.setPrameterType("CMS"); flow="Out Flow"; }
		 * 
		 * List<BranchParameterStatus> branchParameterStatusTempList= new
		 * ArrayList<BranchParameterStatus>();
		 * if(branchStatusMap.containsKey(flow)){
		 * branchParameterStatusTempList=branchStatusMap.get(flow); }
		 * branchParameterStatusTempList.add(branchParameterStatus);
		 * branchStatusMap.put(flow, branchParameterStatusTempList); }
		 * System.out.println("branchStatusMap::>"+branchStatusMap.size());
		 */
		return branchParameterStatusList;
	}

}
