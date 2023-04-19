package com.emorphis.cashmanagement.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BranchHoldingAmountDao;
import com.emorphis.cashmanagement.model.BranchHoldingAmount;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class BranchHoldingAmountDaoImpl extends AbstractDao<String, BranchHoldingAmount>
		implements BranchHoldingAmountDao {

	@Autowired
	Utility utility;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emorphis.cashmanagement.dao.BranchHoldingAmountDao#
	 * getHoldingAmountByBranchId(java.lang.String)
	 */
	@Override
	public BranchHoldingAmount getHoldingAmountByBranchId(String branchManagementId) {
		// TODO Auto-generated method stub

		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("branchManagement.id", branchManagementId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (BranchHoldingAmount) criteria.uniqueResult();
	}
	
	@Override
	public int saveOrUpdateBranchHoldingAmount(BranchHoldingAmount branchHoldingAmount){
		saveOrUpdate(branchHoldingAmount);
		return 1;
	}

}
