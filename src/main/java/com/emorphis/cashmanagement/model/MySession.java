package com.emorphis.cashmanagement.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
// @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MySession {

	private User user;

	private List<RolePermission> permissions;

	private String userId;

	private String baseUrl;

	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<RolePermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<RolePermission> permissions) {
		this.permissions = permissions;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "MySession [user=" + user + ", userId=" + userId + "]";
	}

}
