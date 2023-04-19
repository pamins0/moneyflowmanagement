package com.emorphis.cashmanagement.daoImpl;

import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.OrgManagementAuditTrailDao;


@Repository("orgManagementAuditTrailDao")
public class OrgManagementAuditTrailDaoImpl extends AbstractDao<Integer, Object> implements OrgManagementAuditTrailDao {

//	@SuppressWarnings("unchecked")
//	public List<OrgManagementAuditTrail> getAllOrgManagementAuditTrailList() {
//
//		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
////		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List<OrgManagementAuditTrail> orgManagementAuditTrailList = (List<OrgManagementAuditTrail>) criteria.list();
//		return orgManagementAuditTrailList;
//	}

}
