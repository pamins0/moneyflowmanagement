package com.emorphis.cashmanagement.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emorphis.cashmanagement.dao.PermissionUriDao;
import com.emorphis.cashmanagement.model.PermissionUri;
import com.emorphis.cashmanagement.service.PermissionUriService;

@Repository
@Transactional
public class PermissionUriServiceImpl implements PermissionUriService {

	@Autowired
	PermissionUriDao dao;

	public PermissionUri findById(String id) {
		return dao.findById(id);
	}

	public List<PermissionUri> getAllUri() {
		return dao.getAllUri();
	}

	public void save(PermissionUri entity) {
		dao.save(entity);
	}

	public void deleteById(String id) {
		dao.deleteById(id);
	}

}
