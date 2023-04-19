package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;

public interface UserRoleManagementService {
 
	UserRole findById(String id);
	
	List<UserRole> getUserRoleList(User user);
	
	void deletUserRoleSetByUser(User user);

	void updateUserRoles(UserRole userRole);
	
	void save(UserRole userRole);
	
	List<UserRole> getAllUserRoles(User user, String roleId);

}
