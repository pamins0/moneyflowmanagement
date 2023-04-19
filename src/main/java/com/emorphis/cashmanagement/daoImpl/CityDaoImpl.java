package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.CityDao;
import com.emorphis.cashmanagement.model.City;

@Repository
public class CityDaoImpl extends AbstractDao<Integer, City> implements CityDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<City> getCityListByState(int stateId, String tagName) {
		Criteria criteria = createEntityCriteria();
		criteria.createAlias("state", "state");
		criteria.add(Restrictions.eq("state.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("state.id", stateId));

		//criteria.add(Restrictions.like("cityName", tagName, MatchMode.ANYWHERE));
		criteria.add(Restrictions.ilike("cityName", tagName, MatchMode.ANYWHERE));
		return criteria.list();
	}

}
