package com.emorphis.cashmanagement.daoImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.RoleDao;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.RequestScopedPermissions;
import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.Utility;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao<String, Role> implements RoleDao {

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	RequestScopedPermissions requestScopedPermissions;

	@Autowired
	CommonFilters commonFilters;

	public Role findById(String id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<Role> getAllRoleList() {
		boolean flag = false;

		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getRoleManagementlevelAliasForDelete(criteria);

		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {

			criteria.add(Restrictions.eq("orgManage.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		List<Role> roles = (List<Role>) criteria.list();
		return roles;
	}

	public void save(Role role) throws Exception{
		persist(role);
	}

	public void deleteById(Integer id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Role role = (Role) crit.uniqueResult();
		delete(role);
	}

	public void deletRolePermissionSetByUser(Role role, Set<RolePermission> removeRolePermissionSet) {
		System.out.println(
				"permission set size for deleting from rolepermission table is : " + removeRolePermissionSet.size());
		Iterator<RolePermission> rolePermissionSet = removeRolePermissionSet.iterator();
		List<String> permissionIdList = new ArrayList<String>();
		while (rolePermissionSet.hasNext()) {
			RolePermission rolepermission = (RolePermission) rolePermissionSet.next();
			permissionIdList.add(rolepermission.getPermission().getId());
		}
		System.out.println(
				"PermissionList to delete the RolePermission from db is : " + permissionIdList.iterator().next());
		Session session = getSession();
		String join = "'" + StringUtils.join(permissionIdList, "','") + "'";
		String hql = "delete from RolePermission where permission.id in (" + join + ") and role.id='" + role.getId()
				+ "'";
		System.out.println("HQL QUERY IN PERMISSION DELETE FROM ROLE iS : " + hql);
		Query query = session.createQuery(hql);
		/* System.out.println("Query to delete userRoles is : " + query); */
		System.out.println("Query HQL : " + query.executeUpdate());
	}

	public List<Role> getAllRoleListWithoutFilter() {
		// TODO Auto-generated method stub
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("parent"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Role> roles = (List<Role>) criteria.list();
		return roles;
	}

	public List<Role> getAllRoleListByUserHierarchy(User user) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getRoleManagementlevelAliasForDelete(criteria);

		System.out.println("Hierarchy id : " + user.getBranchManagement().getHierarchyControl().getId());
		// flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(
					Restrictions.eq("hierarchyControl.id", user.getBranchManagement().getHierarchyControl().getId()));
			// criteria.createAlias("hierarchyControl", "heirarchy");
			/**
			 * Alias is created in above method of name hierarchy
			 */
			// criteria.createAlias("hierarchy.orgManagement", "organization");
			criteria.add(Restrictions.eq("orgManage.id",
					user.getBranchManagement().getHierarchyControl().getOrgManagement().getId()));
		}
		List<Role> roles = (List<Role>) criteria.list();
		return roles;
	}

	public List<Role> getAllRoleListbyhierarchy(HierarchyControl hierarchyControl) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getRoleManagementlevelAliasForDelete(criteria);

		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));
			criteria.add(Restrictions.ge("hier.hierarchyLevel",
					mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyLevel()));
		}
		if (null != hierarchyControl && !"-1".equals(hierarchyControl.getId())) {
			criteria.add(Restrictions.eq("hierarchyControl.id", hierarchyControl.getId()));
		}
		List<Role> roles = (List<Role>) criteria.list();
		return roles;
	}

	public List<Role> getAllRoleListByLowerHierarchy(int hierarchyLevel) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getRoleManagementlevelAliasForDelete(criteria);
		criteria.addOrder(Order.asc("hier.hierarchyLevel")).addOrder(Order.asc("title")); 
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));
			criteria.add(Restrictions.ge("hier.hierarchyLevel", hierarchyLevel));
		}

		List<Role> roles = (List<Role>) criteria.list();
		return roles;
	}

	public List<Role> getAllRoleListAccToHierarchyOrganization(Role role) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getRoleManagementlevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {

		} else {
			System.out.println("Inside super admin......" + role.getHierarchyControl().getId());
			if (null != role.getHierarchyControl() && !"-1".equals(role.getHierarchyControl().getId())) {
				System.out.println("Inside super admin hierarchy id is ......" + role.getHierarchyControl().getId());
				criteria.add(Restrictions.eq("hier.id", role.getHierarchyControl().getId()));
			}
			if (null != role.getHierarchyControl().getOrgManagement()
					&& !"-1".equals(role.getHierarchyControl().getOrgManagement().getId())) {
				System.out.println("Inside super admin organization id is ......"
						+ role.getHierarchyControl().getOrgManagement().getId());
				criteria.add(Restrictions.eq("orgManage.id", role.getHierarchyControl().getOrgManagement().getId()));
			}
			if (null != role.getHierarchyControl().getOrgManagement().getOrgType()
					&& !"-1".equals(role.getHierarchyControl().getOrgManagement().getOrgType().getId())) {
				System.out.println("Inside super org type id admin......"
						+ role.getHierarchyControl().getOrgManagement().getOrgType().getId());
				criteria.add(Restrictions.eq("orgType.id",
						role.getHierarchyControl().getOrgManagement().getOrgType().getId()));
			}
		}
		List<Role> roleList = criteria.list();

		return roleList;
	}

	@Override
	public Role findByUUID(String uuids) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Role role = (Role) criteria.uniqueResult();
		return role;
	}
}
