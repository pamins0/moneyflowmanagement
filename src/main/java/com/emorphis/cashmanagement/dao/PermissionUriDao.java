package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.PermissionUri;

public interface PermissionUriDao {

	PermissionUri findById(String id);

	List<PermissionUri> getAllUri();

	void save(PermissionUri id);

	void deleteById(String id);
}
