package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BranchParameterDao;
import com.emorphis.cashmanagement.model.BranchParameter;

@Repository
public class BranchParameterDaoImpl extends AbstractDao<Integer, BranchParameter> implements BranchParameterDao {

	
	@Override
	public List<BranchParameter> getAllBranchParametersList() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));  
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<BranchParameter> branchParametersList = criteria.list();
		
		return branchParametersList;
	}
}
