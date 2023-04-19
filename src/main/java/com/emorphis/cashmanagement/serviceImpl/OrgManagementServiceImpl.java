package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.OrgManagementDao;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.util.Utility;

@Repository("orgManagementService")
@Transactional
public class OrgManagementServiceImpl implements OrgManagementService {

	@Autowired
	OrgManagementDao dao;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	public List<OrgManagement> getAllOrgManagementList() {

		return dao.getAllOrgManagementList();
	}

	public String save(OrgManagement orgManagement) { 
		String uuid = utility.getOrgManagementUUID();
		if (uuid != null) {
			orgManagement.setId(uuid);
		} 
		
		orgManagement.setCreatedTime(new Date());
		orgManagement.setModifiedTime(new Date());
		orgManagement.setCreatedBy(mySession.getUser().getId());
		orgManagement.setModifiedBy(mySession.getUser().getId());
		orgManagement.setIp(mySession.getIp()); 
		/*
		 * if (dao.save(orgManagement) > 0) { return true; }
		 */

		int id = dao.save(orgManagement);
	/*	if (id > 0) {
			return uuid;
		}*/
		return uuid;
	}

	public boolean deleteById(String id) {

		// if(dao.deleteById(id)>0){
		// return true;
		// }
		OrgManagement orgManagementUpdate = dao.findById(id);
		orgManagementUpdate.setDeleted((byte) 1);
		orgManagementUpdate.setModifiedBy(mySession.getUser().getId());
		orgManagementUpdate.setModifiedTime(new Date());
		orgManagementUpdate.setIp(mySession.getIp()); 
		return false;
	}

	public OrgManagement findById(String id) {
		return dao.findById(id);
	}

	public void updatebyId(OrgManagement orgManagement) {
		OrgManagement orgManagementUpdate = dao.findById(orgManagement.getId());
		orgManagementUpdate.setOrgType(orgManagement.getOrgType());
		orgManagementUpdate.setName(orgManagement.getName());
		orgManagementUpdate.setDetail(orgManagement.getDetail());
		orgManagementUpdate.setLocation(orgManagement.getLocation());
		orgManagementUpdate.setEmail(orgManagement.getEmail());
		orgManagementUpdate.setContactNo(orgManagement.getContactNo());
		orgManagementUpdate.setBranchControl(orgManagement.getBranchControl());
		orgManagementUpdate.setCentralisedControl(orgManagement.getCentralisedControl());
		orgManagementUpdate.setLatitude(orgManagement.getLatitude());
		orgManagementUpdate.setLongitude(orgManagement.getLongitude());
		orgManagementUpdate.setCountry(orgManagement.getCountry());
		//orgManagementUpdate.setState(orgManagement.getState());
		orgManagementUpdate.setCity(orgManagement.getCity());
		orgManagementUpdate.setZip(orgManagement.getZip());
		orgManagementUpdate.setAdd1(orgManagement.getAdd1());
		orgManagementUpdate.setAdd2(orgManagement.getAdd2());
		orgManagementUpdate.setModifiedTime(new Date());
		orgManagementUpdate.setModifiedBy(mySession.getUser().getId());
		// orgManagementUpdate.setOrgLevel(orgManagement.getOrgLevel());
		orgManagementUpdate.setCmsApproach(orgManagement.getCmsApproach());
		orgManagementUpdate.setIntraBank(orgManagement.isIntraBank());
		orgManagementUpdate.setMaxTimeForIntraBank(orgManagement.getMaxTimeForIntraBank());
		orgManagementUpdate.setVicinity(orgManagement.getVicinity());
		orgManagementUpdate.setAutoAprovalRequestTime(orgManagement.getAutoAprovalRequestTime());
		orgManagementUpdate.setIp(mySession.getIp()); 
	}

	public List<OrgManagement> getAllOrgManagementListByOrgType(String orgTypeId) {
		List<OrgManagement> orgManagementList = dao.getAllOrgManagementListByOrgType(orgTypeId);
		return orgManagementList;
	}

	/**
	 * UUID Concept on orgmanagement
	 *  
	 */
	
	/*@Override
	public OrgManagement findByUUID(String uuid) {
		return dao.findByUUID(uuid);
	}*/
	
	/*@Override
	public void updatebyUUID(OrgManagement orgManagement) {
		OrgManagement orgManagementUpdate = dao.findByUUID(orgManagement.getUuid());
		orgManagementUpdate.setOrgType(orgManagement.getOrgType());
		orgManagementUpdate.setName(orgManagement.getName());
		orgManagementUpdate.setDetail(orgManagement.getDetail());
		orgManagementUpdate.setLocation(orgManagement.getLocation());
		orgManagementUpdate.setEmail(orgManagement.getEmail());
		orgManagementUpdate.setContactNo(orgManagement.getContactNo());
		orgManagementUpdate.setBranchControl(orgManagement.getBranchControl());
		orgManagementUpdate.setCentralisedControl(orgManagement.getCentralisedControl());
		orgManagementUpdate.setLatitude(orgManagement.getLatitude());
		orgManagementUpdate.setLongitude(orgManagement.getLongitude());
		orgManagementUpdate.setCountry(orgManagement.getCountry());
		orgManagementUpdate.setState(orgManagement.getState());
		orgManagementUpdate.setCity(orgManagement.getCity());
		orgManagementUpdate.setZip(orgManagement.getZip());
		orgManagementUpdate.setAdd1(orgManagement.getAdd1());
		orgManagementUpdate.setAdd2(orgManagement.getAdd2());
		orgManagementUpdate.setModifiedTime(new Date());
		orgManagementUpdate.setModifiedBy(mySession.getUser().getId());
		// orgManagementUpdate.setOrgLevel(orgManagement.getOrgLevel());
		orgManagementUpdate.setCmsApproach(orgManagement.getCmsApproach());
		orgManagementUpdate.setIntraBank(orgManagement.isIntraBank());
		orgManagementUpdate.setMaxTimeForIntraBank(orgManagement.getMaxTimeForIntraBank());
		orgManagementUpdate.setVicinity(orgManagement.getVicinity());
	}
	
	@Override
	public boolean deleteByUUID(String uuid) {
		OrgManagement orgManagementUpdate = dao.findByUUID(uuid);
		orgManagementUpdate.setDeleted((byte) 1);
		orgManagementUpdate.setModifiedBy(mySession.getUser().getId());
		orgManagementUpdate.setModifiedTime(new Date());
		return false;
	}*/

}
