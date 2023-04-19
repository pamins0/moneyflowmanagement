package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;

public interface RolePermissionService {

	List<RolePermission> getRolePermissionList(Role role);	
	List<RolePermission> getRolePermissionListByUser(User user);
	void updateRolePermission(RolePermission rolePermission);

}
