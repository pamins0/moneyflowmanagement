package com.emorphis.cashmanagement.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.BranchClosedGroupService;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class UtilityServiceImpl implements UtilityService {

	private static final Logger log = LoggerFactory.getLogger(UtilityServiceImpl.class);

	@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	OrgManagementService orgManagementService;

	@Autowired
	BranchManagementService branchManagementService;

	@Autowired
	BranchClosedGroupService branchClosedGroupService;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	Utility utility;

	@Autowired
	Environment environment;

	public void OrgToBranchDetails(ModelMap model) {
		List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
		model.addAttribute("orgTypeList", orgTypeList);

		Set<OrgManagement> orgManagementSet = null;
		for (OrgType orgType : orgTypeList) {
			orgManagementSet = orgType.getOrgManagements();
			break;
		}

		System.out.println("OrgManagementSet size in utilityService for = " + orgManagementSet.size());
		orgManagementSet.clear();
		model.addAttribute("orgManagementSet", orgManagementSet);

		List<BranchManagement> branchList = new ArrayList<BranchManagement>();
		model.addAttribute("branchList", branchList);
	}

	public void OrgToOrgManagementDetails(ModelMap model) {
		List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
		model.addAttribute("orgTypeList", orgTypeList);

		Set<OrgManagement> orgManagementSet = null;
		for (OrgType orgType : orgTypeList) {
			orgManagementSet = orgType.getOrgManagements();
			break;
		}

		System.out.println("OrgManagementSet size in utilityService for = " + orgManagementSet.size());
		orgManagementSet.clear();
		model.addAttribute("orgManagementSet", orgManagementSet);

		/*
		 * List<BranchManagement> branchList = new
		 * ArrayList<BranchManagement>(); model.addAttribute("branchList",
		 * branchList);
		 */

	}

	public void OrgToHierarchyControlDetails(ModelMap model) {
		List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
		model.addAttribute("orgTypeList", orgTypeList);

		Set<OrgManagement> orgManagementSet = null;
		for (OrgType orgType : orgTypeList) {
			orgManagementSet = orgType.getOrgManagements();
			break;
		}

		System.out.println("OrgManagementSet size in utilityService for = " + orgManagementSet.size());
		orgManagementSet.clear();
		model.addAttribute("orgManagementSet", orgManagementSet);

		List<HierarchyControl> hierarchyList = new ArrayList<HierarchyControl>();
		model.addAttribute("hierarchyList", hierarchyList);

	}

	public List<HierarchyControl> getHierarchyListAccToUserLevel(ModelMap model) {
		List<HierarchyControl> hierarchyControlsList = hierarchyManagementService.getHierarchyListAccToUserLevel();
		return hierarchyControlsList;
	}

	@Override
	public List<HierarchyControl> getHierarchyListAccToBranchLevel(ModelMap model) {
		List<HierarchyControl> hierarchyControlsList = hierarchyManagementService.getHierarchyListAccToBranchLevel();
		return hierarchyControlsList;
	}

	@Override
	public int makeClusterClusterGroup(BranchManagement branchManagement, User user) throws Exception {
		String lat1 = "";
		String long1 = "";
		String lat2 = "";
		String long2 = "";
		try {
			Double clusterDistance = Double.parseDouble(environment.getProperty("cluster.distance"));
			List<BranchManagement> allBranchManagementsList = branchManagementService
					.getAllBranchManagementListForClusterMaking(branchManagement);
			log.info("distance for cluster radius to make cluster cluster group is : " + clusterDistance
					+ " and branchManagement list size : " + allBranchManagementsList.size());
			List<BranchManagement> branchClusterList = null;
			for (BranchManagement branchManagement2 : allBranchManagementsList) {
				/*
				 * if (!branchManagement2.getId().equals(
				 * "e81965ee-7369-4d2a-b368-f824e4b2167f") &&
				 * !branchManagement2.getId().equals(
				 * "35a861ca-2fac-4601-9627-9e7a8fe1ba05")) {
				 * System.out.println("id not matched : "+branchManagement2.
				 * getBranchName()); continue; }
				 */
				try {
					if (branchManagement2.getBranchClosedGroups().size() > 0) {
						log.info("branch closed group size for branch : " + branchManagement.getBranchName() + " is : "
								+ branchManagement.getBranchClosedGroups().size());
						branchClosedGroupService.deletClosedBranchesByParentBranch(branchManagement2,
								branchManagement2.getBranchClosedGroups());
						log.info(
								"branch closed group size after delete for branch : " + branchManagement.getBranchName()
										+ " is : " + branchManagement.getBranchClosedGroups().size());
					}
				} catch (Exception e) {
					log.error("exception generated during deleteing the closed branch group for branch name : "
							+ branchManagement2.getBranchName()+" due to : "+e);
					continue;
				}
				lat1 = branchManagement2.getBranchLatitude();
				long1 = branchManagement2.getBranchLongitude();
				branchClusterList = new ArrayList<>();
				for (BranchManagement branchManagement3 : allBranchManagementsList) {
					if (branchManagement2.equals(branchManagement3)) {
						continue;
					}
					lat2 = branchManagement3.getBranchLatitude();
					long2 = branchManagement3.getBranchLongitude();
					try {
						double branchDistance = utility.calculateDist(lat1, long1, lat2, long2);
						System.out.println("main distance : " + branchManagement2.getBranchName() + " and "
								+ branchManagement3.getBranchName() + " distance : " + branchDistance);
						if (clusterDistance >= branchDistance) {
							log.info("matched lat long for the branches parent is : "
									+ branchManagement2.getBranchName() + " lat and long is : " + lat1 + " & " + long1);
							log.info("matched lat long for the branches child is : " + branchManagement3.getBranchName()
									+ " lat and long is : " + lat2 + " & " + long2);
							branchManagement3.setBranchDistance(branchDistance); 
							branchClusterList.add(branchManagement3);
							log.info("distance from branch : " + branchManagement2.getBranchName() + " to branch : "
									+ branchManagement3.getBranchName() + " is return : " + branchDistance);
						}
					} catch (Exception ex) {
						log.error(
								"error generated in make makeClusterClusterGroup in lat long distance finding for branch name : "
										+ branchManagement3.getBranchName() + " i.e exception is " + ex);
						ex.printStackTrace();
					}
				}

				if (branchClusterList.size() > 0) {
					log.info("clusterlist size within radius for branch : " + branchManagement2.getBranchName()
							+ " is : " + branchClusterList.size());
					branchClosedGroupService.updateBranchClosedGroupByBranchClusters(branchManagement2,
							branchClusterList, user);
				}
			}
		} catch (Exception ex) {
			log.error("error generated in make makeClusterClusterGroup for branch id : " + ex);
			ex.printStackTrace();
		}
		return 0;
	}
}
