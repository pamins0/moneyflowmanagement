/**
 * 
 */
package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.UserManagementDao;
import com.emorphis.cashmanagement.dao.UserRoleManagementDao;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;
import com.emorphis.cashmanagement.service.UserManagementService;
import com.emorphis.cashmanagement.service.UserRoleManagementService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class UserRoleManagementServiceImpl implements UserRoleManagementService {

	@Autowired
	UserRoleManagementDao userRoleManagementDao;

	@Autowired
	UserManagementService userManagementService;

	@Autowired
	UserManagementDao userManagementDao;

	@Autowired
	MySession mySession;
	
	@Autowired
	Utility utility;

	public List<UserRole> getUserRoleList(User user) {
		List<UserRole> userRoleList = userRoleManagementDao.getUserRoleListAccToUser(user);
		return userRoleList;
	}

	@SuppressWarnings("rawtypes")
	public void updateUserRoles(UserRole userRole) {
		try {
			if (null != userRole.getSelectedVA()) {
				System.out.println("Selected array value is : " + userRole.getSelectedVA());
				System.out.println("SelectedVa size is : " + userRole.getSelectedVA().size());
			}
			if (null != userRole.getDeletedVA()) {
				System.out.println("Deleted array value is : " + userRole.getDeletedVA());
				System.out.println("Deleted array size is : " + userRole.getDeletedVA().size());
			}
			if (null != userRole.getInsertedVA()) {
				System.out.println("Inserted Array value is : " + userRole.getInsertedVA());
				System.out.println("Inserted Array size is : " + userRole.getInsertedVA().size());
			}
			System.out.println("User id in UserRole Association is : " + userRole.getUser().getId()
					+ " and username is : " + userRole.getUser().getFirstName());

			User user = userManagementService.findById(userRole.getUser().getId());
			Set<UserRole> userRoleSet = new HashSet<UserRole>();

			Iterator it = userRole.getSelectedVA().iterator();
			UserRole userRoleObj = null;
			Role role = null;
			while (it.hasNext()) {
				String roleId = (String) it.next();
				role = new Role();
				userRoleObj = new UserRole();

				role.setId(roleId);
				userRoleObj.setUser(user);
				userRoleObj.setRole(role);
				userRoleObj.setCreatedTime(new Date());
				userRoleObj.setModifiedTime(new Date());
				userRoleObj.setModifiedBy(mySession.getUser().getId());
				userRoleObj.setCreatedBy(mySession.getUser().getId());
				userRoleObj.setIp(mySession.getIp());
				String uuid = utility.getUserRoleUUID();
				if(uuid !=null){
					userRoleObj.setId(uuid);				
				}
				
				userRoleSet.add(userRoleObj);

				System.out.println("Role id for user id : " + user.getId() + " and name : " + user.getFirstName()
						+ "   is : " + role.getId());
			}
			System.out.println("UserRoleSet size is : " + userRoleSet.size());

			user.setUserRoles(userRoleSet);
			// userManagementService.saveUserRoles(user);

			Set<UserRole> removeUserRoleSet = new HashSet<UserRole>();
			if (userRole.getDeletedVA().size() > 0) {
				UserRole userRoleObj2 = null;
				Role role2 = null;
				Iterator it1 = userRole.getDeletedVA().iterator();
				while (it1.hasNext()) {
					String roleId = it1.next().toString();
					role2 = new Role();
					role2.setId(roleId);
					userRoleObj2 = new UserRole();
					userRoleObj2.setRole(role2);
					userRoleObj2.setUser(user);
					userRoleObj2.setIp(mySession.getIp());

					removeUserRoleSet.add(userRoleObj2);
					System.out.println("Role id for user id: " + user.getId() + " and name : " + user.getFirstName()
							+ "   is : " + role2.getId());
				}
				// user.setUserRoles(removeUserRoleSet);
				System.out.println("RemoveUserRoleSet size is : " + removeUserRoleSet.size());
				deletUserRoleSetByUser(user, removeUserRoleSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UserRole findById(String id) {
		return userRoleManagementDao.findById(id);
	}

	public void deletUserRoleSetByUser(User user, Set<UserRole> removeUserRoleSet) {
		userManagementDao.deletUserRoleSetByUser(user, removeUserRoleSet);
	}

	public void save(UserRole userRole) {
		userRole.setCreatedTime(new Date());
		userRole.setModifiedTime(new Date());
		userRole.setCreatedTime(new Date());
		userRole.setModifiedTime(new Date());
		userRole.setIp(mySession.getIp());
		userRoleManagementDao.save(userRole);
	}

	public void deletUserRoleSetByUser(User user) {

	}

	public List<UserRole> getAllUserRoles(User user, String roleId) {
		List<UserRole> userRoleList = userRoleManagementDao.getAlUserRoles(user, roleId);
		return userRoleList;
	}

}
