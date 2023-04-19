package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.HierarchyManagementDao;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class HierarchyManagementServiceImpl implements HierarchyManagementService {
	private static final Logger log = LoggerFactory.getLogger(HierarchyManagementServiceImpl.class);

	@Autowired
	HierarchyManagementDao hierarchyManagementDao;

	@Autowired
	MySession mySession;

	@Autowired
	CommonFilters commonFilters;

	@Autowired
	Utility utility;

	public int save(HierarchyControl hierarchyControl) {
		try {
			List<HierarchyControl> hierarchyControlsList = hierarchyControl.getHierarchyControlsList();
			System.out.println("Hierachical control list size from ui is : " + hierarchyControlsList.size());
			System.out.println("Organization id : " + hierarchyControl.getOrgManagement().getId());
			String generatedId = "0";

			int count = 0;
			String ccid = "";
			HierarchyControl hierarchyControlCC = new HierarchyControl();
			for (HierarchyControl hierarchyControl2 : hierarchyControlsList) {
				hierarchyControl2.setOrgManagement(hierarchyControl.getOrgManagement());
				hierarchyControl2.setParentId(generatedId);
				hierarchyControl2.setCreatedTime(new Date());
				hierarchyControl2.setModifiedTime(new Date());
				hierarchyControl2.setCreatedBy(this.mySession.getUser().getId());
				hierarchyControl2.setModifiedBy(this.mySession.getUser().getId());
				hierarchyControl2.setIp(mySession.getIp()); 
				System.out.println("Hierarchical org id : " + hierarchyControl2.getOrgManagement().getId());
				String uuid = utility.getHierarchyControlUUID();
				if (uuid != null) {
					hierarchyControl2.setId(uuid);
					generatedId = uuid;
				}
				this.hierarchyManagementDao.save(hierarchyControl2);
				System.out.println("generated id : " + generatedId);
				if (count == 0) {
					ccid = generatedId;
					count++;
				}
			}

			if (hierarchyControl.getOrgManagement().isCurrencyChest()) {
				hierarchyControlCC.setParentId(generatedId);
				hierarchyControlCC.setName("Currency Chest");
				hierarchyControlCC.setAbbreviation("CC");
				hierarchyControlCC.setHierarchyLevel(2);
				hierarchyControlCC.setHierarchyType(1);
				hierarchyControlCC.setOrgManagement(hierarchyControl.getOrgManagement());
				hierarchyControlCC.setCreatedTime(new Date());
				hierarchyControlCC.setModifiedTime(new Date());
				hierarchyControlCC.setCreatedBy(this.mySession.getUser().getId());
				hierarchyControlCC.setModifiedBy(this.mySession.getUser().getId());
				hierarchyControlCC.setId(utility.getHierarchyControlUUID());
				hierarchyControlCC.setIp(mySession.getIp());
 				this.hierarchyManagementDao.save(hierarchyControlCC);
			}else {
				log.info("currency chest not available for the organization : "+hierarchyControl.getOrgManagement().isCurrencyChest()); 
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
		return 1;
	}

	public List<HierarchyControl> getAllHierarchyControlList() {
		List<HierarchyControl> hierarchyControlsList = null;
		hierarchyControlsList = this.hierarchyManagementDao.getAllHierarchyControlList();
		return hierarchyControlsList;
	}

	public HierarchyControl findById(String id) {
		return this.hierarchyManagementDao.findById(id);
	}

	public void updateById(HierarchyControl hierarchyControl) {
		HierarchyControl hierarchyControl2 = this.hierarchyManagementDao.findById(hierarchyControl.getId());
		hierarchyControl2.setName(hierarchyControl.getName());
		hierarchyControl2.setAbbreviation(hierarchyControl.getAbbreviation());
		hierarchyControl2.setModifiedBy(this.mySession.getUser().getId());
		hierarchyControl2.setModifiedTime(new Date());
		hierarchyControl2.setIp(mySession.getIp()); 
	}

	public boolean deleteById(String id) {
		boolean flag = false;
		HierarchyControl hierarchyControl = this.hierarchyManagementDao.findById(id);
		flag = true;
		hierarchyControl.setDeleted((byte) 1);
		hierarchyControl.setModifiedBy(this.mySession.getUser().getId());
		hierarchyControl.setModifiedTime(new Date());
		hierarchyControl.setIp(mySession.getIp());

		return flag;
	}

	public HierarchyControl findByOrgAndLevel(OrgManagement orgManagement, int level) {
		HierarchyControl hierarchyControl = hierarchyManagementDao.findByOrgAndLevel(orgManagement, level);
		return hierarchyControl;
	}

	public List<HierarchyControl> getHierarchyListAccToUserLevel() {
		List<HierarchyControl> hierarchyControlsList = null;
		hierarchyControlsList = this.hierarchyManagementDao.getHierarchyListAccToUserLevel();
		return hierarchyControlsList;
	}
	
	@Override
	public List<HierarchyControl> getHierarchyListAccToBranchLevel() {
		List<HierarchyControl> hierarchyControlsList = null;
		hierarchyControlsList = this.hierarchyManagementDao.getHierarchyListAccToBranchLevel();
		return hierarchyControlsList;
	}

	public List<HierarchyControl> getAllHierarchyControlListAccToOrganization(HierarchyControl hierarchyControl) {
		List<HierarchyControl> hierarchyControlsList = null;
		hierarchyControlsList = this.hierarchyManagementDao
				.getAllHierarchyControlListAccToOrganization(hierarchyControl);
		System.out.println("Hierarchy Contro list size for fileterHierarchyMethodAccToOrganization : "
				+ hierarchyControlsList.size());
		return hierarchyControlsList;
	}

	public List<HierarchyControl> getAllHierarchyListByOrgmanagemet(String orgManagementid) {
		List<HierarchyControl> hierarchyControlsList = null;
		hierarchyControlsList = this.hierarchyManagementDao.getAllHierarchyListByOrgmanagemet(orgManagementid);
		System.out.println("Hierarchy Contro list size for fileterHierarchyMethodAccToOrganization : "
				+ hierarchyControlsList.size());
		return hierarchyControlsList;
	}

	@Override
	public List<HierarchyControl> getAllHierarchyControlListByOrgId(String id) {
		List<HierarchyControl> hierarchyControlsList = null;
		hierarchyControlsList = this.hierarchyManagementDao.getAllHierarchyControlListByOrgId(id);
		System.out.println("Hierarchy Contro list size for fileterHierarchyMethodAccToOrganization : "
				+ hierarchyControlsList.size());
		return hierarchyControlsList;
	}

	/**
	 * UUID Concept applied
	 */

	/*
	 * @Override public HierarchyControl findByUUID(String uuid) {
	 * 
	 * return hierarchyManagementDao.findByUUID(uuid); }
	 * 
	 * @Override public boolean deleteByUUID(String uuid) { boolean flag =
	 * false; HierarchyControl hierarchyControl =
	 * this.hierarchyManagementDao.findByUUID(uuid); flag = true;
	 * hierarchyControl.setDeleted((byte) 1);
	 * hierarchyControl.setModifiedBy(this.mySession.getUser().getId());
	 * hierarchyControl.setModifiedTime(new Date());
	 * 
	 * return flag; }
	 * 
	 * @Override public void updateByUUID(HierarchyControl hierarchyControl) {
	 * HierarchyControl hierarchyControl2 = this.hierarchyManagementDao
	 * .findByUUID(hierarchyControl.getUuid());
	 * hierarchyControl2.setName(hierarchyControl.getName());
	 * hierarchyControl2.setAbbreviation(hierarchyControl.getAbbreviation());
	 * hierarchyControl2.setModifiedBy(this.mySession.getUser().getId());
	 * hierarchyControl2.setModifiedTime(new Date()); }
	 */

}