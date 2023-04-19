package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.Permission;

public interface PermissionDao {

	List<Permission> getPermissionList();

	void savePermission(Permission permission);

	Permission findById(String id); 

	void deleteById(String id);

	Permission findByUUID(String uuids);  

}
