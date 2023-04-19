package com.emorphis.cashmanagement.model;

import java.util.List;

public class RequestScopedPermissions {
	
	private User user;

	private List<RolePermission> permissions;

	public List<RolePermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<RolePermission> permissions) {
		this.permissions = permissions;
	}
	
	private List<User> branchAdminList;
	
	private List<User> organizationAdminList;

	public List<User> getBranchAdminList() {
		return branchAdminList;
	}

	public void setBranchAdminList(List<User> branchAdminList) {
		this.branchAdminList = branchAdminList;
	}

	public List<User> getOrganizationAdminList() {
		return organizationAdminList;
	}

	public void setOrganizationAdminList(List<User> organizationAdminList) {
		this.organizationAdminList = organizationAdminList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}
