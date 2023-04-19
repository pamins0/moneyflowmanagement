package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.OrgTypeDao;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.util.CommonFilters;

@Repository("orgTypeDao")
public class OrgTypeDaoImpl extends AbstractDao<Integer, OrgType> implements OrgTypeDao {

	@Autowired
	CommonFilters commonFilters;
	
	@Autowired
	MySession mySession;
	
	public OrgType findById(String id) { 
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		criteria = commonFilters.getOrganizationTypeLevelAliasForDelete(criteria);
		OrgType orgType = (OrgType) criteria.uniqueResult();

		return orgType;
		// return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<OrgType> getAllOrgTypes() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("orgType"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = commonFilters.getOrganizationTypeLevelAliasForDelete(criteria);
		List<OrgType> orgTypes = (List<OrgType>) criteria.list();
		System.out.println("orgtypes list : "+orgTypes.size()); 
		return orgTypes;
	}

	public void save(OrgType orgType) {
		System.out.println("Inside persist of org types"+orgType.getOrgType());
		persist(orgType);
	}

	public void deleteById(Integer id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		OrgType orgType = (OrgType) crit.uniqueResult();
		delete(orgType);
	}
	
	/**
	 * Applying the concept of uuid
	 */

	@Override
	public OrgType findByUUID(String id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		OrgType orgType = (OrgType) criteria.uniqueResult();
		return orgType;
	}
}
