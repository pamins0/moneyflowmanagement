package com.emorphis.cashmanagement.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.DashboardFinalBidsAuditTrialDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBidsAuditTrial;
import com.emorphis.cashmanagement.util.CommonFilters;

@Repository
public class DashboardFinalBidsAuditTrialDaoImpl extends AbstractDao<Integer, DashboardFinalBidsAuditTrial>
		implements DashboardFinalBidsAuditTrialDao {

	@Autowired
	CommonFilters commonFilters;

	public DashboardFinalBidsAuditTrial findById(int id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<DashboardFinalBidsAuditTrial> getByStatus(String status) {
		Criteria criteria = createEntityCriteria();
		commonFilters.getDashboardFinalBidAuditTrialAliasForDelete(criteria);
		criteria.add(Restrictions.eq("request.alias", status));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<DashboardFinalBidsAuditTrial> getBids(Integer branchId, String status) {

		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("branchManagement.id", branchId));
		commonFilters.getDashboardFinalBidAuditTrialAliasForDelete(criteria);
		criteria.add(Restrictions.eq("request.alias", status));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<DashboardFinalBidsAuditTrial> getBids(Date fromDate, Date toDate, String branchId, String status) {

		Criteria criteria = createEntityCriteria();
		commonFilters.getDashboardFinalBidAuditTrialAliasForDelete(criteria);
		if (branchId != null) {
			criteria.add(Restrictions.eq("branchManagement.id", branchId));
		}
		criteria.add(Restrictions.eq("request.alias", status));
		if (fromDate != null && toDate != null) {
			criteria.add(Restrictions.ge("createdTime", fromDate));
			criteria.add(Restrictions.lt("createdTime", new Date(toDate.getTime() + (24 * 60 * 60 * 1000))));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<DashboardFinalBidsAuditTrial> getByPosition(String position) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("position", position));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<DashboardFinalBidsAuditTrial> getByPositionAndStatus(String position, String status) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("position", position));
		commonFilters.getDashboardFinalBidAuditTrialAliasForDelete(criteria);
		criteria.add(Restrictions.eq("request.alias", status));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DashboardFinalBidsAuditTrial> getBids(Date fromDate, Date toDate,
			List<BranchManagement> branchManagements, String status) {

		Criteria criteria = createEntityCriteria();
		if (branchManagements != null) {
			criteria.add(Restrictions.in("branchManagement", branchManagements));
		}

		if (fromDate != null && toDate != null) {
			criteria.add(Restrictions.ge("createdTime", fromDate));
			criteria.add(Restrictions.lt("createdTime", new Date(toDate.getTime() + (24 * 60 * 60 * 1000))));
		}

		commonFilters.getDashboardFinalBidAuditTrialAliasForDelete(criteria);
		criteria.add(Restrictions.eq("request.alias", status));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<DashboardFinalBidsAuditTrial> getBidsByPosition(Date fromDate, Date toDate,
			List<BranchManagement> branchIds, String position) {
		Criteria criteria = createEntityCriteria();
		if (branchIds != null) {
			criteria.add(Restrictions.in("branchManagement", branchIds));
		}

		if (fromDate != null && toDate != null) {
			criteria.add(Restrictions.ge("createdTime", fromDate));
			criteria.add(Restrictions.lt("createdTime", new Date(toDate.getTime() + (24 * 60 * 60 * 1000))));
		}

		criteria.add(Restrictions.eq("position", position));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
}
