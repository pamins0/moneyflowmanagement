package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.UserRoleManagementDao;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;

@Repository
public class UserRoleManagementDaoImpl extends AbstractDao<String, UserRole> implements UserRoleManagementDao {

	@SuppressWarnings("unchecked")
	public List<UserRole> getUserRoleListAccToUser(User user) {
		List<UserRole> userRoleList = null;
		Criteria crt = createEntityCriteria();
		crt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (!user.getId().equals("0")) {			
			crt.add(Restrictions.eq("user.id", user.getId()));
		}
		crt.createAlias("role", "rol");
		crt.add(Restrictions.eq("rol.deleted", Byte.valueOf((byte) 0)));
		userRoleList = crt.list();
		return userRoleList;
	}

	public void save(UserRole userRole) {
		persist(userRole);
	}

	public UserRole findById(String id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> getAlUserRoles(User user,String roleId) {
		List<UserRole> userRoleList = null;
		Criteria crt = createEntityCriteria();
		crt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (!roleId.equals("0")) {
			crt.add(Restrictions.eq("role.id", roleId));
		}
		userRoleList = crt.list();
		return userRoleList;
	}

	@Override
	public UserRole findByUUID(String uuids) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		UserRole userRole = (UserRole) criteria.uniqueResult();
		return userRole;
	}
	

}
