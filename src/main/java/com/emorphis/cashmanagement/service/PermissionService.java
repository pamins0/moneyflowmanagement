package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.Permission;

public interface PermissionService {

	List<Permission> getPermissionList();

	void savePermission(Permission permission);

	Permission findById(String id); 

	void updateById(Permission permission);

	void deleteById(String id); 

	List<Permission> getAllPermissionList();   

}
