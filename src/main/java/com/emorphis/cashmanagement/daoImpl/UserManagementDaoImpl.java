package com.emorphis.cashmanagement.daoImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.UserManagementDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.RequestScopedPermissions;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class UserManagementDaoImpl extends AbstractDao<String, User> implements UserManagementDao {

	@Autowired
	CommonFilters commonFilters;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	RequestScopedPermissions requestScopedPermissions;

	public int save(User user) {
		int id = 1;
		getSession().save(user);
		System.out.println("User generaed id after save : " + id);
		return id;
	}

	public User findById(String id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));

		criteria = commonFilters.getUserLevelAliasForDelete(criteria);

		User user = (User) criteria.uniqueResult();

		if (null != user) {
			Set<UserRole> filtereduserRoleSet = user.getUserRoles().stream().filter(p -> p.getRole().getDeleted() == 0)
					.collect(Collectors.toSet());
			user.setUserRoles(filtereduserRoleSet);
		}

		return user;
		// return getByKey(id);
	}
	

	public User findBySSO(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("userId", sso));
		User user = (User)crit.uniqueResult();
//		if(user!=null){
//			Hibernate.initialize(user.getUserRoles());
//		}
		return user;
	}
	
	@Override
	public List<User> getAllUserListByDepartmentId(String id) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("department.id", id));
		return criteria.list();
	}
	
	@Override
	public List<User> getAllUserListByDesignationId(String id) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("designation.id", id));
		return criteria.list();
	}


	@Override
	public User findByUsername(String username) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("userId", username));

		criteria = commonFilters.getUserLevelAliasForDelete(criteria);

		User user = (User) criteria.uniqueResult();

		if (null != user) {
			Set<UserRole> filtereduserRoleSet = user.getUserRoles().stream().filter(p -> p.getRole().getDeleted() == 0)
					.collect(Collectors.toSet());
			user.setUserRoles(filtereduserRoleSet);
		}

		return user;
	}

	public void deleteById(Integer id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		User user = (User) crit.uniqueResult();
		delete(user);
	}

	public void saveUserRoles(User user) {
		getSession().saveOrUpdate(user);
	}

	public void deletUserRoleSetByUser(User user, Set<UserRole> removeUserRoleSet) {
		Iterator<UserRole> userRoleSet = removeUserRoleSet.iterator();
		List<String> roleIdList = new ArrayList<String>();
		while (userRoleSet.hasNext()) {
			UserRole userRole = (UserRole) userRoleSet.next();
			roleIdList.add(userRole.getRole().getId());
		}
		System.out.println("RoleList to delete the userRoles from db is : " + roleIdList.iterator().next());
		Session session = getSession();
		String join = "'"+StringUtils.join(roleIdList, "','")+"'";
		String hql = "delete from UserRole where role.id in (" + join + ") and user.id='"
				+ user.getId()+"'";
		Query query = session.createQuery(hql);
		/* System.out.println("Query to delete userRoles is : " + query); */
		System.out.println("Query HQL : " + query.executeUpdate());
	}

	public User findByUserId(String userId) {
		Criteria criteria = createEntityCriteria();
		User user = null;
		try {
			criteria.add(Restrictions.eq("userId", userId));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			System.out.println("user id ########### : " + userId);
			criteria.createAlias("branchManagement", "branch");
			criteria.createAlias("branch.hierarchyControl", "hier");
			criteria.createAlias("hier.orgManagement", "orgManage");
			criteria.createAlias("orgManage.orgType", "orgType");
			
			criteria.add(Restrictions.eq("branch.deleted", Byte.valueOf((byte) 0)));
			criteria.add(Restrictions.eq("hier.deleted", Byte.valueOf((byte) 0)));
			criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
			criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
			criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));
			System.out.println("User id for login ######### : " + userId);
			user = (User) criteria.uniqueResult();
		} catch (Exception e) {
			System.out.println("exception inside : " + e);
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public User authenticateUserForForgotPassword(User user) {
		System.out.println("authenticateUserForForgotPassword in dao  " + user.getEmail());
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("email", user.getEmail()));

		User user2 = (User) criteria.uniqueResult();
		return user2;
	}

	public List<User> getAvailableUser(User user) {		
		Criteria crit = createEntityCriteria();
		crit = commonFilters.getUserLevelAliasForDelete(crit);
		crit.add(Restrictions.or(Restrictions.eq("userId", user.getUserId()), Restrictions.eq("email", user.getEmail()))); 
		boolean flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			crit.add(Restrictions.eq("orgManage.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<User> usersList = crit.list();
		return usersList;
	}

	public List<User> getAllUsersListbyBranch(BranchManagement branchManagement) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("userId"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = commonFilters.getUserLevelAliasForDelete(criteria);
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId()));
		}
		criteria.add(Restrictions.eq("branchManagement.id", branchManagement.getId()));
		List<User> usersList = (List<User>) criteria.list();

		List<User> filterdUsersList = utility.getRoleDeletedFilteredList(usersList);

		return filterdUsersList;
	}

	public List<User> getAllUsersList() {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("createdTime"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		/**
		 * getting user level alias for logical delete operations ...
		 */
		criteria = commonFilters.getUserLevelAliasForDelete(criteria);
		System.out.println("Organization id : "
				+ mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId());
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId()));
		}

		// criteria.add(Restrictions.ne("id", mySession.getUser().getId()));
		@SuppressWarnings("unchecked")
		List<User> usersList = (List<User>) criteria.list();

		List<User> filterdUsersList = utility.getRoleDeletedFilteredList(usersList);

		return filterdUsersList;
	}

	public List<User> getAllUsersListbyBranchHierarchy(User user) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		/**
		 * getting user level alias for logical delete operations ...
		 */
		criteria = commonFilters.getUserLevelAliasForDelete(criteria);
		System.out.println("Organization id : "
				+ mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId());
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId()));
		}

		if (null != user.getBranchManagement().getHierarchyControl()
				&& null != user.getBranchManagement().getHierarchyControl().getId()
				&& !"-1".equals(user.getBranchManagement().getHierarchyControl().getId())) {
			String hierarchyId = user.getBranchManagement().getHierarchyControl().getId();
			System.out.println("Hierarchy id for fileteration is ; " + hierarchyId);
			criteria.add(Restrictions.eq("hier.id", hierarchyId));
		}
		if (null != user.getBranchManagement() && null != user.getBranchManagement().getId()) {
			String branchId = user.getBranchManagement().getId();
			System.out.println("Branch id for fileteration is ; " + branchId);
			criteria.add(Restrictions.eq("branch.id", branchId));
		}

		// criteria.add(Restrictions.ne("id", mySession.getUser().getId()));
		@SuppressWarnings("unchecked")
		List<User> usersList = (List<User>) criteria.list();

		List<User> filterdUsersList = utility.getRoleDeletedFilteredList(usersList);

		return filterdUsersList;
	}

	public List<User> getAllUsersListbyBranchHierarchyAndLowerLevelUsers(User user, List<User> usersLowerLevelList) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		

		/**
		 * getting user level alias for logical delete operations ...
		 */
		criteria = commonFilters.getUserLevelAliasForDelete(criteria);
		

		//filter for chest
		if(user.getEntityType()!=(byte)-1){
			criteria.add(Restrictions.eq("branch.chest", (byte) user.getEntityType()));
		}
	
		
		System.out.println("Organization id : "
				+ mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId());
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId()));
		}

		if (null != user.getBranchManagement().getHierarchyControl()
				&& null != user.getBranchManagement().getHierarchyControl().getId()
				&& !"-1".equals(user.getBranchManagement().getHierarchyControl().getId())) {
			String hierarchyId = user.getBranchManagement().getHierarchyControl().getId();
			System.out.println("Hierarchy id for fileteration is ; " + hierarchyId);
			criteria.add(Restrictions.eq("hier.id", hierarchyId));
		}
		if (null != user.getBranchManagement() && null != user.getBranchManagement().getId()) {
			String branchId = user.getBranchManagement().getId();
			System.out.println("Branch id for fileteration is ; " + branchId);
			criteria.add(Restrictions.eq("branch.id", branchId));
		}

		// criteria.add(Restrictions.ne("id", mySession.getUser().getId()));
		@SuppressWarnings("unchecked")
		List<User> usersList = (List<User>) criteria.list();

		List<User> filterdUsersList = utility.getRoleDeletedFilteredList(usersList);

		return filterdUsersList;
	}

	public List<User> getAllUserListAccToBranchHierarchyOrganization(User user) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = commonFilters.getUserLevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();

		//filter for chest
		if(user.getEntityType()!=(byte)-1){
			criteria.add(Restrictions.eq("branch.chest", (byte) user.getEntityType()));
		}
	
		if (!flag) {

		} else {
			if (null != user.getBranchManagement() && null != user.getBranchManagement().getId()) {
				System.out.println("Inside super admin branch id is ......" + user.getBranchManagement().getId());
				criteria.add(Restrictions.eq("branch.id", user.getBranchManagement().getId()));
			}
			if (null != user.getBranchManagement().getHierarchyControl()
					&& !"-1".equals(user.getBranchManagement().getHierarchyControl().getId())) {
				System.out.println("Inside super admin Hierarchy id is ......"
						+ user.getBranchManagement().getHierarchyControl().getId());
				criteria.add(Restrictions.eq("hier.id", user.getBranchManagement().getHierarchyControl().getId()));
			}
			if (null != user.getBranchManagement().getHierarchyControl().getOrgManagement()
					&& "-1".equals(user.getBranchManagement().getHierarchyControl().getOrgManagement().getId())) {
				System.out.println("Inside super admin organization id is in ......"
						+ user.getBranchManagement().getHierarchyControl().getOrgManagement().getId());
				criteria.add(Restrictions.eq("orgManage.id",
						user.getBranchManagement().getHierarchyControl().getOrgManagement().getId()));
			}
			if (null != user.getBranchManagement().getHierarchyControl().getOrgManagement().getOrgType() && !"-1"
					.equals(user.getBranchManagement().getHierarchyControl().getOrgManagement().getOrgType().getId())) {
				System.out.println("Inside super org type id admin......"
						+ user.getBranchManagement().getHierarchyControl().getOrgManagement().getOrgType().getId());
				criteria.add(Restrictions.eq("orgType.id",
						user.getBranchManagement().getHierarchyControl().getOrgManagement().getOrgType().getId()));
			}
		}
		List<User> usersList = criteria.list();

		List<User> filterdUsersList = utility.getRoleDeletedFilteredList(usersList);

		return filterdUsersList;
	}

	@Override
	public User findByUUID(String id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		User user = (User) criteria.uniqueResult();
		return user;
	}
}
