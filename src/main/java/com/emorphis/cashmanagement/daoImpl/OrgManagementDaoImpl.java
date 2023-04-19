package com.emorphis.cashmanagement.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.OrgManagementDao;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.Utility;

@Repository("orgManagementDao")
public class OrgManagementDaoImpl extends AbstractDao<String, OrgManagement> implements OrgManagementDao {

	@Autowired
	CommonFilters commonFilters;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@SuppressWarnings("unchecked")
	public List<OrgManagement> getAllOrgManagementList() {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria = commonFilters.getOrganizationLevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			criteria.add(Restrictions.eq("id", mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		List<OrgManagement> orgManagementList = (List<OrgManagement>) criteria.list();
		return orgManagementList;
	}

	public int save(OrgManagement orgManagement) {
		/*
		 * persist(orgManagement); return 1;
		 */
		int id = 0; 
		getSession().save(orgManagement);
		System.out.println("Org management generaed id after save : " + id);
		return id;
	}

	public int deleteById(Integer id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		OrgManagement orgManagement = (OrgManagement) crit.uniqueResult();
		delete(orgManagement);
		return 1;
	}

	public OrgManagement findById(String id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		criteria = commonFilters.getOrganizationLevelAliasForDelete(criteria);
		OrgManagement orgManagement = (OrgManagement) criteria.uniqueResult();

		return orgManagement;
		// return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<OrgManagement> getAllOrgManagementListByOrgType(String orgTypeId) {
		List<OrgManagement> orgManagementList = new ArrayList<OrgManagement>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
		criteria = commonFilters.getOrganizationLevelAliasForDelete(criteria);
		criteria.add(Restrictions.eq("orgType.id", orgTypeId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		orgManagementList = criteria.list();
		return orgManagementList;
	}

	/**
	 * Applying the concept of uuid
	 */

	@Override
	public OrgManagement findByUUID(String id) {
		Criteria criteria = createEntityCriteria(); 
		criteria.add(Restrictions.eq("id", id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		OrgManagement orgManagement = (OrgManagement) criteria.uniqueResult();
		return orgManagement;
	}
}
