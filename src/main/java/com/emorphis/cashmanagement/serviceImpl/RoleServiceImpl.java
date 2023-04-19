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
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.Permission;
import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.RoleService;
import com.emorphis.cashmanagement.util.Utility;

@Repository("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao dao;

	@Autowired
	MySession mySession;
	
	@Autowired
	Utility utility;

	public Role findById(String id) {

		return dao.findById(id);
	}

	public List<Role> getAllRoleList() {

		return dao.getAllRoleList();
	}

	public boolean save(Role role) throws Exception{
		role.setCreatedBy(mySession.getUser().getId());
		role.setModifiedBy(mySession.getUser().getId());
		role.setCreatedTime(new Date());
		role.setModifiedTime(new Date());
		role.setIp(mySession.getIp());
		String uuid = utility.getRoleUUID();
		if(uuid != null){
			role.setId(uuid); 
		}
		dao.save(role);
		return true;
	}

	public void deleteById(String id) {
		boolean flag = false;
		Role role = dao.findById(id);		
		role.setDeleted((byte) 1);
		role.setModifiedBy(this.mySession.getUser().getId());
		role.setModifiedTime(new Date());
		role.setIp(mySession.getIp());
		flag = true;
	}

	public void updatebyId(Role role1) throws Exception{
		Role role = dao.findById(role1.getId());
		role.setModifiedTime(new Date());
		role.setModifiedBy(mySession.getUser().getId());
		role.setHierarchyControl(role1.getHierarchyControl()); 
		role.setTitle(role1.getTitle());
		role.setIp(mySession.getIp());
	}
	
	@Override
	public void updateRoleStatusById(String id) {
		Role role = dao.findById(id);
		role.setModifiedTime(new Date());
		role.setModifiedBy(mySession.getUser().getId());	
		role.setIp(mySession.getIp());
		if(role.getStatus() == 0){
			role.setStatus((byte)1);
		}else if(role.getStatus() == 1){
			role.setStatus((byte)0);
		} 		
	}

	@SuppressWarnings("rawtypes")
	public void updateRolePermission(RolePermission rolePermission) {
		try {
			Role role = findById(rolePermission.getRole().getId());
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
				// rolePermissionObj.setGrant((byte) 1);
				rolePermissionObj.setCreatedTime(new Date());
				rolePermissionObj.setModifiedTime(new Date());
				rolePermissionObj.setModifiedBy(mySession.getUser().getId());
				rolePermissionObj.setCreatedBy(mySession.getUser().getId());
				rolePermissionObj.setIp(mySession.getIp());

				rolePermissionSet.add(rolePermissionObj);

				System.out.println("Permission id for role id : " + role.getId() + " and name : " + role.getTitle()
						+ "   is : " + permission.getId());
			}
			System.out.println("UserRoleSet size is : " + rolePermissionSet.size());

			role.setRolePermissions(rolePermissionSet);
		} catch (Exception e) {
			System.out.println("exception generated in roleService updateRolePermission method due to : " + e);
			e.printStackTrace();
		}
	}

	public List<Role> getAllRoleListWithoutFilter() {
		// TODO Auto-generated method stub
		return dao.getAllRoleListWithoutFilter();
	}

	public List<Role> getAllRoleListByUserHierarchy(User user) {	
		return dao.getAllRoleListByUserHierarchy(user);
	}

	public List<Role> getAllRoleListbyhierarchy(HierarchyControl hierarchyControl) {	
		return dao.getAllRoleListbyhierarchy(hierarchyControl);
	}

	public List<Role> getAllRoleListByLowerHierarchy(int hierarchyLevel) {
		return dao.getAllRoleListByLowerHierarchy(hierarchyLevel);
	}

	public List<Role> getAllRoleListAccToHierarchyOrganization(Role role) {
		return dao.getAllRoleListAccToHierarchyOrganization(role);
	}

}
