package com.emorphis.cashmanagement.dao;

import java.util.List;
import java.util.Set;

import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;

public interface RoleDao {

	Role findById(String id); 

	List<Role> getAllRoleList();

	void save(Role id) throws Exception;

	void deleteById(Integer id);

	void deletRolePermissionSetByUser(Role role, Set<RolePermission> removeRolePermissionSet);

	List<Role> getAllRoleListWithoutFilter();

	List<Role> getAllRoleListByUserHierarchy(User user);

	List<Role> getAllRoleListbyhierarchy(HierarchyControl hierarchyControl);

	List<Role> getAllRoleListByLowerHierarchy(int hierarchyLevel);

	List<Role> getAllRoleListAccToHierarchyOrganization(Role role);

	Role findByUUID(String uuids);  
}
