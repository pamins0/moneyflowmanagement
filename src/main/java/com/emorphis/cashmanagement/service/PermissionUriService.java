package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.PermissionUri;

public interface PermissionUriService {

	PermissionUri findById(String id);

	List<PermissionUri> getAllUri();

	void save(PermissionUri id);

	void deleteById(String id);
}
