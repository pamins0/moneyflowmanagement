package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;

public interface RoleService {

	Role findById(String id); 

	List<Role> getAllRoleList();
	
	boolean save(Role role) throws Exception;
	
	void deleteById(String id); 
	
	void updatebyId(Role org) throws Exception;

	void updateRolePermission(RolePermission rolePermission);

	List<Role> getAllRoleListWithoutFilter();

	List<Role> getAllRoleListByUserHierarchy(User user);

	List<Role> getAllRoleListbyhierarchy(HierarchyControl hierarchyControl);

	List<Role> getAllRoleListByLowerHierarchy(int hierarchyLevel);

	List<Role> getAllRoleListAccToHierarchyOrganization(Role role);

	void updateRoleStatusById(String id);     
}
