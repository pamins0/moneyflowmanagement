package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.OrgTypeDao;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.util.Utility;

@Repository("orgTypeService")
@Transactional
public class OrgTypeServiceImpl implements OrgTypeService {

	@Autowired
	OrgTypeDao dao;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;
	
	@Override
	public OrgType findById(String id) {
		return dao.findById(id);
	}

	/*public OrgType findById(Integer id) {

	//	return dao.findById(id);
	}*/

	public List<OrgType> getAllOrgTypes() {

		return dao.getAllOrgTypes();
	}

	public void save(OrgType orgType) {		
		String uuid = utility.getOrgTypeUUID();
		if (uuid != null) {
			orgType.setId(uuid);
		} 
		orgType.setCreatedTime(new Date());
		orgType.setModifiedTime(new Date());
		orgType.setCreatedBy(mySession.getUser().getId());
		orgType.setModifiedBy(mySession.getUser().getId());
		orgType.setIp(mySession.getIp()); 
		dao.save(orgType);
	}	

	public boolean deleteById(String id) {
		boolean flag = false;
		OrgType orgType = dao.findById(id);
		System.out.println("OrgType id : for user id : "
				+ mySession.getUser().getBranchManagement().getOrgManagement().getOrgType().getId());
		System.out.println("OrgType id : for given org type id for deletion is : " + id);
		flag = utility.isAllowed("can_view_everything");
		System.out.println("OrgType id : for given org type id for deletion is : " + id+" and flag value is : "+flag);
		if (!flag) {			
			if (null != orgType) {
				if (orgType.getId() != mySession.getUser().getBranchManagement().getOrgManagement().getOrgType()
						.getId()) {
					flag = false;
					return flag;
				}
			}
		} else {
			
		}
		
		System.out.println("OrgType id : for given org type id for deletion after if matching is  : " + id);
		
		flag = true;
		orgType.setDeleted((byte) 1);
		orgType.setModifiedBy(mySession.getUser().getId());
		orgType.setModifiedTime(new Date());
		orgType.setIp(mySession.getIp()); 
		// dao.deleteById(id);
		return flag;
	}

	public void updatebyId(OrgType org) {
		OrgType orgType = dao.findById(org.getId());
		orgType.setOrgType(org.getOrgType());
		orgType.setOrgDetail(org.getOrgDetail());
		orgType.setModifiedBy(mySession.getUser().getId());
		orgType.setModifiedTime(new Date());
		orgType.setIp(mySession.getIp()); 
	}
	
	/**
	 * UUID Concept applied
	 */

	/*@Override
	public OrgType findByUUID(String uuid) {	
		return dao.findByUUID(uuid); 
	}*/
	
	/*public void updatebyUUID(OrgType org) {
		OrgType orgType = dao.findByUUID(org.getUuid());
		orgType.setOrgType(org.getOrgType());
		orgType.setOrgDetail(org.getOrgDetail());
		orgType.setModifiedBy(mySession.getUser().getId());
		orgType.setModifiedTime(new Date());
	}*/
	
	/*@Override
	public boolean deleteByUUID(String id) {
		boolean flag = false;
		OrgType orgType = dao.findById(id);
		System.out.println("OrgType id : for user id : "
				+ mySession.getUser().getBranchManagement().getOrgManagement().getOrgType().getId());
		System.out.println("OrgType uuid : for given org type id for deletion is : " + id);
		flag = utility.isAllowed("can_view_everything");
		System.out.println("OrgType id : for given org type id for deletion is : " + id+" and flag value is : "+flag);
		if (!flag) {			
			if (null != orgType) {
				if (orgType.getId() != mySession.getUser().getBranchManagement().getOrgManagement().getOrgType()
						.getId()) {
					flag = false;
					return flag;
				}
			}
		} else {
			
		}
		
		System.out.println("OrgType id : for given org type id for deletion after if matching is  : " + id);
		
		flag = true;
		orgType.setDeleted((byte) 1);
		orgType.setModifiedBy(mySession.getUser().getId());
		orgType.setModifiedTime(new Date());
		// dao.deleteById(id);
		return flag;
	}	*/
}
