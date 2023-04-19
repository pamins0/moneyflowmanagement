package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.BranchManagementDao;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.Utility;

@Repository("branchManagementService")
@Transactional
public class BranchManagementServiceImpl implements BranchManagementService {

	private static final Logger log = LoggerFactory.getLogger(BranchManagementServiceImpl.class);

	@Autowired
	BranchManagementDao dao;

	@Autowired
	MySession mySession;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	Utility utility;

	@Autowired
	UtilityService utilityService;

	public BranchManagement findById(String id) {
		return dao.findById(id);
	}

	@Override
	public BranchManagement findByIdWithDelete(String id) {
		return dao.findByIdWithDelete(id);
	}

	public List<BranchManagement> getAllBranch() {
		return dao.getAllBranch();
	}

	public String save(BranchManagement management) {
		management.setCreatedBy(mySession.getUser().getId());
		management.setModifiedBy(mySession.getUser().getId());
		management.setCreatedTime(new Date());
		management.setModifiedTime(new Date());
		management.setIp(mySession.getIp());

		if (management.getBranchCashlimit() != null && management.getBranchCashlimit() > 0) {
			if (management.getPercentage() != null && management.getPercentage() > 0
					&& management.getPercentage() < 100) {

				Double branchCashLimit = management.getBranchCashlimit();
				Double branchCashLimitPercent = management.getPercentage();
				Double calculatePercentAmount = 0.0;

				calculatePercentAmount = (branchCashLimit * branchCashLimitPercent) / 100;

				management.setMinThresholdAmount(branchCashLimit - calculatePercentAmount);
				management.setMaxThresholdAmount(branchCashLimit + calculatePercentAmount);
			} else {
				management.setMinThresholdAmount(management.getBranchCashlimit());
				management.setMaxThresholdAmount(management.getBranchCashlimit());
			}
		} else {
			management.setMinThresholdAmount(management.getBranchCashlimit());
			management.setMaxThresholdAmount(management.getBranchCashlimit());
		}

		String uuid = utility.getBranchManagementUUID();
		if (uuid != null) {
			management.setId(uuid);
		}

		int id = dao.save(management);
		return uuid;
	}

	public void deleteById(String id) {
		BranchManagement branchManagement = dao.findById(id);

		/*
		 * List<BranchManagement> branchListLowerLevel = new
		 * ArrayList<BranchManagement>();
		 * branchListLowerLevel.add(branchManagement); branchListLowerLevel =
		 * utility.getAllChildBranchList(branchManagement,
		 * branchListLowerLevel);
		 * System.out.println("Now i am printing users list.................");
		 * utility.print(branchListLowerLevel);
		 */

		branchManagement.setDeleted((byte) 1);
		branchManagement.setModifiedTime(new Date());
		branchManagement.setModifiedBy(mySession.getUser().getId());
		branchManagement.setIp(mySession.getIp());
		// dao.deleteById(id);
	}

	public boolean updateById(BranchManagement bm) {
		boolean flag = false;
		BranchManagement branch = dao.findById(bm.getId());
		
		log.info("branch latitude and longitude for branch : " + branch.getBranchName() + " is : "
				+ branch.getBranchLatitude() + " and " + branch.getBranchLongitude());
		log.info("bm latitude and longitude : " + branch.getBranchName() + "  is : " + bm.getBranchLatitude() + " and "
				+ bm.getBranchLongitude());

		if ((branch.getBranchLatitude().trim().equals(bm.getBranchLatitude().trim()))
				&& (branch.getBranchLongitude().trim().equals(bm.getBranchLongitude().trim()))) {
			log.info("latitude and longitude not changed for the branch : " + branch.getBranchName());
			flag = true;
		} else {
			flag = false;
		}

		branch.setBranchAdd1(bm.getBranchAdd1());
		branch.setBranchAdd2(bm.getBranchAdd2());
		//branch.setBranchCity(bm.getBranchCity());
		branch.setCity(bm.getCity());
		branch.setBranchCode(bm.getBranchCode());
		branch.setBranchContactNo(bm.getBranchContactNo());
		branch.setBranchDetail(bm.getBranchDetail());
		branch.setBranchEmail(bm.getBranchEmail());
		branch.setBranchLatitude(bm.getBranchLatitude());
		branch.setBranchLocation(bm.getBranchLocation());
		branch.setBranchLongitude(bm.getBranchLongitude());
		branch.setBranchName(bm.getBranchName());
		//branch.setBranchState(bm.getBranchState());
		branch.setBranchStatus(bm.getBranchStatus());
		branch.setBranchZip(bm.getBranchZip());
		branch.setDeleted(bm.getDeleted());
		branch.setModifiedTime(new Date());
		branch.setBranchType(bm.getBranchType());
		branch.setAbbreviation(bm.getAbbreviation());
		branch.setBranchCashlimit(bm.getBranchCashlimit());
		branch.setPercentage(bm.getPercentage());

		branch.setHierarchyControl(bm.getHierarchyControl());
		branch.setBranchControl(bm.getBranchControl());
		branch.setVicinity(bm.getVicinity());
		branch.setBranchIfscCode(bm.getBranchIfscCode());
		branch.setBranchApprovers(bm.getBranchApprovers());
		branch.setRequestApprovalType(bm.getRequestApprovalType());
		branch.setIsgroup(bm.getIsgroup());
		branch.setIp(mySession.getIp());

		if (bm.getBranchCashlimit() != null && bm.getBranchCashlimit() > 0) {
			if (bm.getPercentage() != null && bm.getPercentage() > 0 && bm.getPercentage() < 100) {

				Double branchCashLimit = bm.getBranchCashlimit();
				Double branchCashLimitPercent = bm.getPercentage();
				Double calculatePercentAmount = 0.0;

				calculatePercentAmount = (branchCashLimit * branchCashLimitPercent) / 100;

				System.out.println("branch cash limit = " + branchCashLimit);
				System.out.println("branch percent on limit = " + branchCashLimitPercent);
				System.out.println("calculated branch percent on limit = " + calculatePercentAmount);

				branch.setMinThresholdAmount(branchCashLimit - calculatePercentAmount);
				branch.setMaxThresholdAmount(branchCashLimit + calculatePercentAmount);
			} else {
				branch.setMinThresholdAmount(bm.getBranchCashlimit());
				branch.setMaxThresholdAmount(bm.getBranchCashlimit());
			}
		} else {
			branch.setMinThresholdAmount(bm.getBranchCashlimit());
			branch.setMaxThresholdAmount(bm.getBranchCashlimit());
		}
		
		return flag;
	}

	public List<BranchManagement> getAllBranchManagementListByOrgmanagemet(String orgManagementid) {
		List<BranchManagement> branchManagementList = dao.getAllBranchManagementListByOrgmanagemet(orgManagementid);

		return branchManagementList;
	}

	public List<BranchManagement> getAllBranchManagementListByHierarchyId(String parentId) {
		List<BranchManagement> branchManagementsList = dao.getAllBranchManagementListByHierarchyId(parentId);
		return branchManagementsList;
	}

	public List<BranchManagement> getAllBranchnotGrouped() {

		return dao.getAllBranchNotGrouped();
	}

	public List<BranchManagement> getAllBranchGrouped() {
		return dao.getAllBranchGrouped();
	}

	public List<BranchManagement> getAllBranchManagementListByHierarchyIdAndAutoComleteName(String hierarchyId,
			String tagName) {
		return dao.getAllBranchManagementListByHierarchyIdAndTagName(hierarchyId, tagName);
	}

	public List<BranchManagement> getAllBranchManagementListByHierarchyIdAndAutoComleteNameForBranchControl(
			String hierarchyId, String tagName) {
		return dao.getAllBranchManagementListByHierarchyIdAndAutoComleteNameForBranchControl(hierarchyId, tagName);
	}

	public Object getAllBranchGroupedAndNotgrouped() {
		return dao.getAllBranchGroupedAndNotgrouped();
	}

	public List<BranchManagement> getAllBranchListbyhierarchy(HierarchyControl hierarchyControl) {
		return dao.getAllBranchListbyhierarchy(hierarchyControl);
	}

	public List<BranchManagement> getAllBranchListAccToHierarchyOrganization(BranchManagement branchManagement) {

		return dao.getAllBranchListAccToHierarchyOrganization(branchManagement);
	}

	public List<BranchManagement> getAllBranchListbyBranchHierarchyAndLowerLevelBranches(
			BranchManagement branchManagement, List<BranchManagement> branchListLowerLevel) {
		return dao.getAllBranchListbyBranchHierarchyAndLowerLevelBranches(branchManagement, branchListLowerLevel);
	}

	@Override
	public BranchManagement getAvailableBranchCode(BranchManagement branch) {
		List<BranchManagement> branchManagementList = dao.getAvailableBranchCode(branch);
		if (branchManagementList != null && branchManagementList.size() > 0) {
			return branchManagementList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<BranchManagement> getAllBranchesListForMailUtility() {
		return dao.getAllBranchesListForMailUtility();
	}

	@Override
	public List<BranchManagement> getAllBranchListByBranchHierarchy(BranchManagement branchManagement) {
		return dao.getAllBranchListByBranchHierarchy(branchManagement);
	}

	/**
	 * @author gourav This method is use to get remaining Branch list for
	 *         branchGroup
	 */
	@Override
	public List<BranchManagement> getRemainingBranchListByBranchHierarchy(List<BranchGroup> branchGroupList) {
		return dao.getRemainingBranchListByBranchHierarchy(branchGroupList);
	}

	@Override
	public List<BranchManagement> getAllCurrencyChestListExcludingBranch(Set<BranchManagement> branchCCListFromGroup) {
		return dao.getAllCurrencyChestListExcludingBranch(branchCCListFromGroup);
	}

	@Override
	public void saveBranchEodPosition(BranchParameterStatus branchParameterStatus) {
		System.out
				.println("BranchparameterStatus branch id is : " + branchParameterStatus.getBranchManagement().getId());
		BranchManagement branchManagement = findById(branchParameterStatus.getBranchManagement().getId());
		branchManagement.setBranchParameterStatuses(branchParameterStatus.getBranchParameterStatusList());
	}

	/**
	 * For Currency Chest
	 */

	@Override
	public int saveChest(BranchManagement management) {
		management.setCreatedBy(mySession.getUser().getId());
		management.setModifiedBy(mySession.getUser().getId());
		management.setCreatedTime(new Date());
		management.setModifiedTime(new Date());
		management.setIp(mySession.getIp());
		/*
		 * if (management.getBranchCashlimit() != null &&
		 * management.getBranchCashlimit() > 0) { if (management.getPercentage()
		 * != null && management.getPercentage() > 0 &&
		 * management.getPercentage() < 100) {
		 * 
		 * Double branchCashLimit = management.getBranchCashlimit(); Double
		 * branchCashLimitPercent = management.getPercentage(); Double
		 * calculatePercentAmount = 0.0;
		 * 
		 * calculatePercentAmount = (branchCashLimit * branchCashLimitPercent) /
		 * 100;
		 * 
		 * management.setMinThresholdAmount(branchCashLimit -
		 * calculatePercentAmount);
		 * management.setMaxThresholdAmount(branchCashLimit +
		 * calculatePercentAmount); } else {
		 * management.setMinThresholdAmount(management.getBranchCashlimit());
		 * management.setMaxThresholdAmount(management.getBranchCashlimit()); }
		 * } else {
		 */
		management.setMinThresholdAmount(management.getBranchCashlimit());
		management.setMaxThresholdAmount(management.getBranchCashlimit());
		// }
		String uuid = utility.getBranchManagementUUID();
		if (uuid != null) {
			management.setId(uuid);
		}
		int id = dao.save(management);
		return id;
	}

	@Override
	public List<BranchManagement> getAllCurrencyChestList() {

		return dao.getAllCurrencyChestList();
	}

	/**
	 * UUID Concept starts
	 */

	@Override
	public BranchManagement findByUUID(String uuid) {

		return dao.findByUUID(uuid);
	}

	/*
	 * @Override public void deleteByUUID(String uuid) { BranchManagement
	 * branchManagement = dao.findByUUID(uuid);
	 * 
	 * 
	 * List<BranchManagement> branchListLowerLevel = new
	 * ArrayList<BranchManagement>();
	 * branchListLowerLevel.add(branchManagement); branchListLowerLevel =
	 * utility.getAllChildBranchList(branchManagement, branchListLowerLevel);
	 * System.out.println("Now i am printing users list.................");
	 * utility.print(branchListLowerLevel);
	 * 
	 * 
	 * branchManagement.setDeleted((byte) 1);
	 * branchManagement.setModifiedTime(new Date());
	 * branchManagement.setModifiedBy(mySession.getUser().getId()); //
	 * dao.deleteById(id);
	 * 
	 * }
	 * 
	 * @Override public void updateByUUID(BranchManagement bm) {
	 * 
	 * BranchManagement branch = dao.findByUUID(bm.getUuid()); //
	 * branch.setBranchAccountDetails(bm.getBranchAccountDetails());
	 * branch.setBranchAdd1(bm.getBranchAdd1());
	 * branch.setBranchAdd2(bm.getBranchAdd2());
	 * branch.setBranchCity(bm.getBranchCity());
	 * branch.setBranchCode(bm.getBranchCode());
	 * branch.setBranchContactNo(bm.getBranchContactNo());
	 * branch.setBranchDetail(bm.getBranchDetail());
	 * branch.setBranchEmail(bm.getBranchEmail());
	 * branch.setBranchLatitude(bm.getBranchLatitude());
	 * branch.setBranchLocation(bm.getBranchLocation());
	 * branch.setBranchLongitude(bm.getBranchLongitude());
	 * branch.setBranchName(bm.getBranchName());
	 * branch.setBranchState(bm.getBranchState());
	 * branch.setBranchStatus(bm.getBranchStatus());
	 * branch.setBranchZip(bm.getBranchZip());
	 * branch.setDeleted(bm.getDeleted()); branch.setModifiedTime(new Date());
	 * branch.setBranchType(bm.getBranchType());
	 * branch.setAbbreviation(bm.getAbbreviation());
	 * branch.setBranchCashlimit(bm.getBranchCashlimit());
	 * branch.setPercentage(bm.getPercentage());
	 * 
	 * branch.setHierarchyControl(bm.getHierarchyControl());
	 * branch.setBranchControl(bm.getBranchControl());
	 * branch.setVicinity(bm.getVicinity());
	 * branch.setBranchIfscCode(bm.getBranchIfscCode());
	 * branch.setBranchApprovers(bm.getBranchApprovers());
	 * 
	 * if (bm.getBranchCashlimit() != null && bm.getBranchCashlimit() > 0) { if
	 * (bm.getPercentage() != null && bm.getPercentage() > 0 &&
	 * bm.getPercentage() < 100) {
	 * 
	 * Double branchCashLimit = bm.getBranchCashlimit(); Integer
	 * branchCashLimitPercent = bm.getPercentage(); Double
	 * calculatePercentAmount = 0.0;
	 * 
	 * calculatePercentAmount = (branchCashLimit * branchCashLimitPercent) /
	 * 100;
	 * 
	 * System.out.println("branch cash limit = " + branchCashLimit);
	 * System.out.println("branch percent on limit = " +
	 * branchCashLimitPercent);
	 * System.out.println("calculated branch percent on limit = " +
	 * calculatePercentAmount);
	 * 
	 * branch.setMinThresholdAmount(branchCashLimit - calculatePercentAmount);
	 * branch.setMaxThresholdAmount(branchCashLimit + calculatePercentAmount); }
	 * else { branch.setMinThresholdAmount(bm.getBranchCashlimit());
	 * branch.setMaxThresholdAmount(bm.getBranchCashlimit()); } } else {
	 * branch.setMinThresholdAmount(bm.getBranchCashlimit());
	 * branch.setMaxThresholdAmount(bm.getBranchCashlimit()); } }
	 */

	@Override
	public List<BranchManagement> getAllBranchManagementListForClusterMaking(BranchManagement branchManagement) {
		return dao.getAllBranchManagementListForClusterMaking(branchManagement);
	}
	
	@Override
	public List<BranchManagement> getCorrespondentBranchList(BranchManagement branchManagement,
			Set<BranchManagement> associatedCorrespondentBranchesList) {
		return dao.getCorrespondentBranchList(branchManagement, associatedCorrespondentBranchesList);
	}
}
