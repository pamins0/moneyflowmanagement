package com.emorphis.cashmanagement.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.OrgManagementAuditTrailDao;
import com.emorphis.cashmanagement.service.OrgManagementAuditTrailService;

@Repository("orgManagementAuditTrailService")
@Transactional
public class OrgManagementAuditTrailServiceImpl implements OrgManagementAuditTrailService {

	@Autowired
	OrgManagementAuditTrailDao dao;

//	public List<OrgManagementAuditTrail> getAllOrgManagementAuditTrailList() {
//
//		return dao.getAllOrgManagementAuditTrailList();
//	}

}