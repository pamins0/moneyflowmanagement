package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;

public interface RolePermissionDao {

	List<RolePermission> getRolePermissionListAccToUser(Role role);
	List<RolePermission> getRolePermissionListByUser(User user);
	RolePermission findByUUID(String uuids); 

}
