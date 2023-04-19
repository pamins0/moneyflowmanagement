package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.StateDao;
import com.emorphis.cashmanagement.model.State;

@Repository
public class StateDaoImpl extends AbstractDao<Integer, State> implements StateDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<State> getStateList(String tagName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));
		
		//criteria.add(Restrictions.like("stateName", tagName,MatchMode.ANYWHERE));
		criteria.add(Restrictions.ilike("stateName", tagName,MatchMode.ANYWHERE));
		return criteria.list();	
	}

}
