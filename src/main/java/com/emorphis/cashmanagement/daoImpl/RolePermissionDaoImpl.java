package com.emorphis.cashmanagement.daoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.RolePermissionDao;
import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;

@Repository
public class RolePermissionDaoImpl extends AbstractDao<Integer, RolePermission> implements RolePermissionDao {

	@SuppressWarnings("unchecked")
	public List<RolePermission> getRolePermissionListAccToUser(Role role) {
		List<RolePermission> rolePermissionList = null;
		Criteria crt = createEntityCriteria();
		if (!role.getId().equals("0")) {
			crt.add(Restrictions.eq("role.id", role.getId()));
		}
		crt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		rolePermissionList = crt.list();
		return rolePermissionList;
	}

	@SuppressWarnings("unchecked")
	public List<RolePermission> getRolePermissionListByUser(User user) {
		List<RolePermission> rolePermissionList = new ArrayList<RolePermission>();
		List<String> roleIdList = new ArrayList<String>();
		Set<UserRole> userRoleSet = user.getUserRoles();

		for (UserRole userRole : userRoleSet) {
			roleIdList.add(userRole.getRole().getId());
		}
	//	System.out.println("Rolesidlist assigned for particular user size is : " + roleIdList.size());
		Criteria crt = createEntityCriteria();
		if (!user.getId().equals("0") && roleIdList.size() > 0) {
			crt.add(Restrictions.in("role.id", roleIdList));
			rolePermissionList = crt.list();
		}
	//	System.out.println("RolePermissionList size is : " + rolePermissionList.size());
		return rolePermissionList;
	}
	
	@Override
	public RolePermission findByUUID(String uuids) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		RolePermission rolePermission = (RolePermission) criteria.uniqueResult();
		return rolePermission;
	}
}
