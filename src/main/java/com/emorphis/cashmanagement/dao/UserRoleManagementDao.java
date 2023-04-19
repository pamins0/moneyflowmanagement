
package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;

public interface UserRoleManagementDao {

	UserRole findById(String id);

	List<UserRole> getUserRoleListAccToUser(User user);

	void save(UserRole userRole);

	List<UserRole> getAlUserRoles(User user, String roleId);

	UserRole findByUUID(String uuids); 

}
