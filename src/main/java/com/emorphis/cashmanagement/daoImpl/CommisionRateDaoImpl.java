package com.emorphis.cashmanagement.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.CommisionRateDao;
import com.emorphis.cashmanagement.model.CommisionRate;

@Repository
public class CommisionRateDaoImpl extends AbstractDao<Integer, CommisionRate> implements CommisionRateDao{

	
	public CommisionRate findById(int id) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("id", id));
		/*criteria = this.commonFilters.getHierarchicalControllevelAliasForDelete(criteria);*/
		CommisionRate commisionRate = (CommisionRate) criteria.uniqueResult();
		return commisionRate;
	}
	
	public CommisionRate findByModelTypeId(String id) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("modelType.id", id));
		/*criteria = this.commonFilters.getHierarchicalControllevelAliasForDelete(criteria);*/
		CommisionRate commisionRate = (CommisionRate) criteria.uniqueResult();
		return commisionRate;
	}
}
