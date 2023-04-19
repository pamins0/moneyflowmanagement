package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.ModelTypeDao;
import com.emorphis.cashmanagement.model.ModelType;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.RequestScopedPermissions;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class ModelTypeDaoImpl extends AbstractDao<String, ModelType> implements ModelTypeDao {

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	RequestScopedPermissions requestScopedPermissions;

	@Autowired
	CommonFilters commonFilters;

	@SuppressWarnings("unchecked")
	public List<ModelType> getAllModelType() {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<ModelType> modelTypesList = criteria.list();
		return modelTypesList;
	}

	public ModelType findById(String id) {
		return getByKey(id);
	}
}
