package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.PermissionDao;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.Permission;
import com.emorphis.cashmanagement.service.PermissionService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	PermissionDao permissionDao;

	@Autowired
	MySession mySession;
	
	@Autowired
	Utility utility;

	public List<Permission> getPermissionList() {
		List<Permission> permissionList = permissionDao.getPermissionList();
		return permissionList;
	}

	public void savePermission(Permission permission) {
		System.out.println("Permission abbreviation is : " + permission.getAbbreviation());
		if (permission.getAbbreviation().equals("")) {
			permission.setAbbreviation(permission.getTitle());
		}
		permission.setCreatedTime(new Date());
		permission.setModifiedTime(new Date());
		permission.setCreatedBy(mySession.getUser().getId());
		permission.setModifiedBy(mySession.getUser().getId());
		String uuid = utility.getPermissionUUID();
		if(uuid != null){
			permission.setId(uuid); 
		}
		permissionDao.savePermission(permission);
	}

	public Permission findById(String id) {
		return permissionDao.findById(id);
	}

	public void updateById(Permission permission) {
		System.out.println("User id on permission updation is : " + mySession.getUser().getId()
				+ " and abbreviation name is : " + permission.getAbbreviation());
		Permission permission2 = permissionDao.findById(permission.getId());
		permission2.setModifiedTime(new Date());
		permission2.setModifiedBy(mySession.getUser().getId());
		permission2.setModule(permission.getModule());
		permission2.setTitle(permission.getTitle());
		permission2.setKeyVal(permission.getKeyVal());
	}

	public void deleteById(String id) {
		permissionDao.deleteById(id);
	}

	public List<Permission> getAllPermissionList() {
		return permissionDao.getPermissionList();
	}
}
