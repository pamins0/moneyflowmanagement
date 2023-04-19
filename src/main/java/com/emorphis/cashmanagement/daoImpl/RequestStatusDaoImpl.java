package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.RequestStatusDao;
import com.emorphis.cashmanagement.model.RequestStatus;

@Repository 
public class RequestStatusDaoImpl extends AbstractDao<String, RequestStatus> implements RequestStatusDao {

	
	@Override
	public List<RequestStatus> getAllRequestStatus() {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<RequestStatus> requestStatusList = criteria.list();
		return requestStatusList;
	}
}
