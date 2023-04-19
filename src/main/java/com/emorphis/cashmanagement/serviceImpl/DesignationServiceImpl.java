package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.DesignationDao;
import com.emorphis.cashmanagement.model.Designation;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.service.DesignationService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class DesignationServiceImpl implements DesignationService {

	@Autowired
	DesignationDao dao;
	
	@Autowired
	MySession mySession;
	
	@Autowired
	Utility utility;
	
	public boolean save(Designation designation) throws Exception{
		designation.setCreatedBy(mySession.getUser().getId());
		designation.setModifiedBy(mySession.getUser().getId());
		designation.setCreatedTime(new Date());
		designation.setModifiedTime(new Date());
		designation.setIp(mySession.getIp());
		String uuid = utility.getDesignationUUID();
		if(uuid != null){
			designation.setId(uuid); 
		}
		dao.save(designation);
		return true;
	}

	public List<Designation> getAllDesignationList() {
		
		return dao.getAllDesignationList();
	}

	public Designation findById(String id) {
		return dao.findById(id);
	}

	public void updatebyId(Designation designation1) {
		Designation designation = dao.findById(designation1.getId());
		designation.setModifiedTime(new Date());
		designation.setModifiedBy(mySession.getUser().getId()); 
		designation.setHierarchyControl(designation1.getHierarchyControl()); 
		designation.setTitle(designation1.getTitle());
		designation.setIp(mySession.getIp());
	}

	public void deleteById(String id) {
		boolean flag = false;
		Designation designation = dao.findById(id);
		flag = true;
		designation.setDeleted((byte) 1);
		designation.setModifiedBy(this.mySession.getUser().getId());
		designation.setModifiedTime(new Date());
		designation.setIp(mySession.getIp());
	}

	public List<Designation> getByHierarchyId(String hId) {		
		return dao.getByHierarchyId(hId);
	}

	public List<Designation> getAllDesignationListbyhierarchy(HierarchyControl hierarchyControl) {
		
		return dao.getAllDesignationListbyhierarchy(hierarchyControl);
	}

	public List<Designation> getAllDesignationListByLowerHierarchy(int hierarchyLevel) {
		return dao.getAllDesignationListByLowerHierarchy(hierarchyLevel);
	}
	
	public List<Designation> getAllDesignationListAccToHierarchyOrganization(Designation designation) {
		return dao.getAllDesignationListAccToHierarchyOrganization(designation);
	}

}
