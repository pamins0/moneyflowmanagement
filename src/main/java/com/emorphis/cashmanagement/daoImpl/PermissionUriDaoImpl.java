package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.PermissionUriDao;
import com.emorphis.cashmanagement.model.PermissionUri;

@Repository
public class PermissionUriDaoImpl extends AbstractDao<String, PermissionUri> implements PermissionUriDao {

	public PermissionUri findById(String id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<PermissionUri> getAllUri() {
		Criteria criteria = createEntityCriteria();
		List<PermissionUri> permissionUris = (List<PermissionUri>) criteria.list();
		return permissionUris;
	}

	public void save(PermissionUri entity) {
		persist(entity);
	}

	public void deleteById(String id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		PermissionUri permissionUri = (PermissionUri) crit.uniqueResult();
		delete(permissionUri);
	}

}
