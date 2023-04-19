package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.RoleDao;
import com.emorphis.cashmanagement.dao.RolePermissionDao;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.Permission;
import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.RolePermissionService;
import com.emorphis.cashmanagement.service.RoleService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

	@Autowired
	RolePermissionDao rolePermissionDao;

	@Autowired
	RoleService roleService;

	@Autowired
	RoleDao roleDao;

	@Autowired
	MySession mySession;
	
	@Autowired
	Utility utility;

	public List<RolePermission> getRolePermissionList(Role role) {
		List<RolePermission> rolePermissionsList = rolePermissionDao.getRolePermissionListAccToUser(role);
		return rolePermissionsList;
	}
	public List<RolePermission> getRolePermissionListByUser(User user) {	
		return rolePermissionDao.getRolePermissionListByUser(user);
	}
	
	@SuppressWarnings("rawtypes")
	public void updateRolePermission(RolePermission rolePermission) {

		try {
			if (null != rolePermission.getSelectedVA()) {
				System.out.println("Selected array value is : " + rolePermission.getSelectedVA());
				System.out.println("SelectedVa size is : " + rolePermission.getSelectedVA().size());
			}
			if (null != rolePermission.getDeletedVA()) {
				System.out.println("Deleted array value is : " + rolePermission.getDeletedVA());
				System.out.println("Deleted array size is : " + rolePermission.getDeletedVA().size());
			}
			if (null != rolePermission.getInsertedVA()) {
				System.out.println("Inserted Array value is : " + rolePermission.getInsertedVA());
				System.out.println("Inserted Array size is : " + rolePermission.getInsertedVA().size());
			}
			System.out.println("Role id in RolePermission Association is : " + rolePermission.getRole().getId()
					+ " and username is : " + rolePermission.getRole().getTitle());

			Role role = roleService.findById(rolePermission.getRole().getId());
			Set<RolePermission> rolePermissionSet = new HashSet<RolePermission>();

			Iterator it = rolePermission.getSelectedVA().iterator();
			RolePermission rolePermissionObj = null;
			Permission permission = null;
			while (it.hasNext()) { 
				String permissionId = (String) it.next();
				permission = new Permission();
				rolePermissionObj = new RolePermission();

				permission.setId(permissionId);
				rolePermissionObj.setRole(role);
				rolePermissionObj.setPermission(permission);
				rolePermissionObj.setGrantPermission((byte) 1);
				rolePermissionObj.setCreatedTime(new Date());
				rolePermissionObj.setModifiedTime(new Date());
				rolePermissionObj.setModifiedBy(mySession.getUser().getId());
				rolePermissionObj.setCreatedBy(mySession.getUser().getId());
				String uuid = utility.getUserPermissionUUID();
				if(uuid !=null){ 
					rolePermissionObj.setId(uuid);				
				}
				rolePermissionSet.add(rolePermissionObj);

				System.out.println("Permission id for role id : " + role.getId() + " and name : " + role.getTitle()
						+ "   is : " + permission.getId() + " for user id : " + mySession.getUser().getId());
			}
			System.out.println("UserRoleSet size is : " + rolePermissionSet.size());

			role.setRolePermissions(rolePermissionSet);
			// userManagementService.saveUserRoles(user);
			Set<RolePermission> removeRolePermissionSet = new HashSet<RolePermission>();
			if (rolePermission.getDeletedVA().size() > 0) {
				RolePermission rolePermission2 = null;
				Permission permission2 = null;
				Iterator it1 = rolePermission.getDeletedVA().iterator();
				while (it1.hasNext()) {
					String permissionId = it1.next().toString();
					permission2 = new Permission();
					permission2.setId(permissionId);
					rolePermission2 = new RolePermission();
					rolePermission2.setPermission(permission2);
					rolePermission2.setRole(role);

					removeRolePermissionSet.add(rolePermission2);
					System.out.println("In deletedVA Permission id for role id: " + role.getId() + " and name : "
							+ role.getTitle() + "   is : " + permission2.getId());
				}
				// user.setUserRoles(removeUserRoleSet);
				System.out.println("RemoveRolePermissionSet size is : " + removeRolePermissionSet.size());
				deletRolePermissionSetByRole(role, removeRolePermissionSet);
			}
		} catch (Exception ex) {
			System.out.println("exception generated at catch of updating rolepermission......");
			ex.printStackTrace();
		}
		System.out.println("3 After inserting role based permissions......");
	}

	public void deletRolePermissionSetByRole(Role role, Set<RolePermission> removeRolePermissionSet) {
		roleDao.deletRolePermissionSetByUser(role, removeRolePermissionSet);
	}
}
