package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.CommisionRate;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.Denomination;
import com.emorphis.cashmanagement.model.Department;
import com.emorphis.cashmanagement.model.Designation;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MultiListResponse;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.model.PlaceCashRequestAuditTrial;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwapAuditTrial;
import com.emorphis.cashmanagement.model.RequestAjax;
import com.emorphis.cashmanagement.service.BranchClosedGroupService;
import com.emorphis.cashmanagement.service.BranchGroupService;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.BranchParameterStatusService;
import com.emorphis.cashmanagement.service.CommisionRateService;
import com.emorphis.cashmanagement.service.DashboardFinalBidService;
import com.emorphis.cashmanagement.service.DepartmentService;
import com.emorphis.cashmanagement.service.DesignationService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.service.PlaceCashRequestAuditTrialService;
import com.emorphis.cashmanagement.service.PlaceCashRequestService;
import com.emorphis.cashmanagement.service.PlaceCashRequestSwapAuditTrialService;
import com.emorphis.cashmanagement.service.PlaceCashRequestSwapService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.NotificationAndMailUtility;
import com.emorphis.cashmanagement.util.Utility;

@RestController
public class RestFullController {

	private static final Logger log = LoggerFactory.getLogger(RestFullController.class);

	@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	OrgManagementService orgManagementService;

	@Autowired
	BranchManagementService branchManagementService;

	@Autowired
	DesignationService designationService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	CommisionRateService commisionRateService;

	@Autowired
	PlaceCashRequestService placeCashRequestService;

	@Autowired
	PlaceCashRequestSwapService placeCashRequestSwapService;

	@Autowired
	NotificationAndMailUtility notificationAndMailUtility;

	@Autowired
	MySession mySession;

	@Autowired
	UtilityService utilityService;

	@Autowired
	Utility utility;

	@Autowired
	DashboardFinalBidService dashboardFinalBidService;

	@Autowired
	BranchParameterStatusService branchParameterStatusService;

	@Autowired
	PlaceCashRequestSwapAuditTrialService placeCashRequestSwapAuditTrialService;

	@Autowired
	PlaceCashRequestAuditTrialService placeCashRequestAuditTrialService;

	@Autowired
	BranchGroupService branchGroupService;

	@Autowired
	BranchClosedGroupService branchClosedGroupService;

	@RequestMapping(value = { "/getOrgManagementForOrgType-{orgTypeid}" }, produces = "application/json")
	public @ResponseBody List<OrgManagement> getOrgManagementForOrgType(@PathVariable("orgTypeid") String orgTypeId,
			ModelMap model) {
		log.info("Org Type id in response method of OrgManagement is : " + orgTypeId);
		List<OrgManagement> orgManagementList = orgManagementService.getAllOrgManagementListByOrgType(orgTypeId);
		log.info("OrgManagementList according to orgType : " + orgManagementList.size());
		return orgManagementList;
	}

	@RequestMapping(value = { "/getBranchManagementForOrgManagement-{orgManagementid}" }, produces = "application/json")
	public @ResponseBody List<BranchManagement> getBranchManagementForOrgManagement(
			@PathVariable("orgManagementid") String orgManagementid, ModelMap model) {
		log.info("Org Management id in response method of BranchManagement is : " + orgManagementid);
		List<BranchManagement> branchManagementList = branchManagementService
				.getAllBranchManagementListByOrgmanagemet(orgManagementid);
		log.info("BranchManagementList according to orgType : " + branchManagementList.size());
		return branchManagementList;
	}

	@RequestMapping(value = { "/getHierarchyControlForOrgManagement-{orgManagementid}" }, produces = "application/json")
	public @ResponseBody List<HierarchyControl> getHierarchyControlForOrgManagement(
			@PathVariable("orgManagementid") String orgManagementid, ModelMap model) {
		log.info("Org Management id in response method of BranchManagement is : " + orgManagementid);
		List<HierarchyControl> hierarchyList = hierarchyManagementService
				.getAllHierarchyListByOrgmanagemet(orgManagementid);
		log.info("hierarchyList according to orgmanagement : " + hierarchyList.size());
		return hierarchyList;
	}

	@RequestMapping(value = {
			"/getHierarchyControlByUserLevelAndOrgManagement-{orgManagementid}" }, produces = "application/json")
	public @ResponseBody List<HierarchyControl> getHierarchyControlByUserLevelAndOrgManagement(
			@PathVariable("orgManagementid") String orgManagementid, ModelMap model) {
		log.info("Org Management id in response method of BranchManagement is : " + orgManagementid);
		List<HierarchyControl> hierarchyList = null;
		boolean flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyList = utilityService.getHierarchyListAccToUserLevel(model);
		} else {
			hierarchyList = hierarchyManagementService.getAllHierarchyListByOrgmanagemet(orgManagementid);
		}
		log.info("hierarchyList according to orgmanagement : " + hierarchyList.size());
		return hierarchyList;
	}

	@RequestMapping(value = {
			"/getHierarchyControlByBranchLevelAndOrgManagement-{orgManagementid}" }, produces = "application/json")
	public @ResponseBody List<HierarchyControl> getHierarchyControlByBranchLevelAndOrgManagement(
			@PathVariable("orgManagementid") String orgManagementid, ModelMap model) {
		log.info("Org Management id in response method of BranchManagement is : " + orgManagementid);
		List<HierarchyControl> hierarchyList = null;
		boolean flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyList = utilityService.getHierarchyListAccToBranchLevel(model);
		} else {
			hierarchyList = hierarchyManagementService.getAllHierarchyListByOrgmanagemet(orgManagementid);
		}
		log.info("hierarchyList according to orgmanagement : " + hierarchyList.size());
		return hierarchyList;
	}

	@RequestMapping(value = {
			"/getBranchManagementForHierarchyAutoComplete" }, produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody List<BranchManagement> getBranchManagementForHierarchyAutoComplete(
			@RequestParam("tagName") String tagName, @RequestParam("hierarchyId") String hierarchyId) {
		log.info("Hierarchy Management id in response method of BranchManagementHierarchy is : " + hierarchyId
				+ " and tag name is : " + tagName);
		BranchManagement branchManagement = mySession.getUser().getBranchManagement();
		List<BranchManagement> branchListLowerLevel = null;
		branchListLowerLevel = new ArrayList<BranchManagement>();
		branchListLowerLevel.add(branchManagement);
		branchListLowerLevel = utility.getAllChildBranchList(branchManagement, branchListLowerLevel);
		log.info("Now i am printing users list.................");
		branchListLowerLevel.forEach(p -> log.info("branch name is : " + p.getBranchName()));
		utility.print(branchListLowerLevel);
		List<BranchManagement> branchList = new ArrayList<BranchManagement>();

		List<BranchManagement> branchManagementList = branchManagementService
				.getAllBranchManagementListByHierarchyIdAndAutoComleteName(hierarchyId, tagName);
		branchManagementList.forEach(p -> log.info("branch name by hierarchy id is : " + p.getBranchName()));

		List<BranchManagement> retainAllList = new ArrayList<BranchManagement>(branchManagementList);
		retainAllList.retainAll(branchListLowerLevel);

		branchList = retainAllList;

		log.info("BranchManagementList according to hierarchyId and tagName is : " + branchManagementList.size());
		System.out
				.println("BranchManagementList according to lowe level hierarchy is : " + branchListLowerLevel.size());
		log.info("RetainAll Branch list after both compare is : " + retainAllList.size());

		branchList.forEach(p -> log.info("branch name after retaining is : " + p.getBranchName()));

		return branchList;
	}

	@RequestMapping(value = {
			"/getDesignationsByHierarchyId" }, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody List<Designation> getDesignationsByHierarchyId(@RequestParam String hId) {
		List<Designation> designationList = designationService.getByHierarchyId(hId);
		log.info("Designations size for the hId:" + hId + " is " + designationList.size());
		return designationList;
	}

	@RequestMapping(value = {
			"/getDepartmentsByHierarchyId" }, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody List<Department> getDepartmentsByHierarchyId(@RequestParam String hId) {
		List<Department> departmentList = departmentService.getByHierarchyId(hId);
		log.info("Departments size for the hId:" + hId + " is " + departmentList.size());
		return departmentList;
	}

	@RequestMapping(value = {
			"/getBranchManagementForHierarchyAutoCompleteForBranchControl" }, produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody List<BranchManagement> getBranchManagementForHierarchyAutoCompleteForBranchControl(
			@RequestParam("tagName") String tagName, @RequestParam("hierarchyId") String hierarchyId) {
		log.info("Hierarchy Management id in response method of BranchManagementHierarchy is : " + hierarchyId
				+ " and tag name is : " + tagName);
		if (null != hierarchyId && !"".equals(hierarchyId) && !hierarchyId.equals("-1")) {
			HierarchyControl hierarchyControl = hierarchyManagementService.findById(hierarchyId);
			String hierarchyIdNew = hierarchyControl.getParentId();
			List<BranchManagement> mainBranchList = new ArrayList<>();
			List<BranchManagement> branchManagementList = branchManagementService
					.getAllBranchManagementListByHierarchyIdAndAutoComleteNameForBranchControl("" + hierarchyIdNew,
							tagName);
			log.info("BranchManagementList according to hierarchyId : " + hierarchyIdNew + " and tagName is : "
					+ branchManagementList.size());
			boolean flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				BranchManagement branchManagement = mySession.getUser().getBranchManagement();
				List<BranchManagement> childBranchList = new ArrayList<>();
				childBranchList.add(branchManagement);
				utility.getAllChildBranchList(branchManagement, childBranchList);
				List<BranchManagement> retainAllList = new ArrayList<BranchManagement>(branchManagementList);
				retainAllList.retainAll(childBranchList);
				mainBranchList = retainAllList;
			} else {
				mainBranchList = branchManagementList;
			}
			return mainBranchList;
		}

		return null;
	}

	@RequestMapping(value = {
			"/getBranchManagementOfHeadOffice" }, produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody List<BranchManagement> getBranchManagementOfHeadOffice(
			// @RequestParam("tagName") String tagName,
			@RequestParam("orgManagementId") String orgManagementId) {
		String tagName = "";
		log.info("Hierarchy Management id in response method of HO is : " + orgManagementId + " and tag name is : "
				+ tagName);
		if (null != orgManagementId && !"".equals(orgManagementId) && !orgManagementId.equals("-1")) {
			OrgManagement orgManagement = orgManagementService.findById(orgManagementId);
			HierarchyControl hierarchyControl = hierarchyManagementService.findByOrgAndLevel(orgManagement, 1);

			String hierarchyIdNew = hierarchyControl.getId();
			List<BranchManagement> branchManagementList = branchManagementService
					.getAllBranchManagementListByHierarchyIdAndAutoComleteNameForBranchControl("" + hierarchyIdNew,
							tagName);
			log.info("BranchManagementList according to hierarchyId : " + hierarchyIdNew + " and tagName is : "
					+ branchManagementList.size());
			return branchManagementList;
		}

		return null;
	}

	@RequestMapping(value = {
			"/getHierarchyStatusByHierarchyId" }, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody HierarchyControl getHierarchyStatusByHierarchyId(@RequestParam String hId) {
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(hId);
		return hierarchyControl;
	}

	/**
	 * For Bidding Process................
	 */
	@RequestMapping(value = { "/getCommisionRateAccoToModelType-{modelType}" }, produces = "application/json")
	public @ResponseBody CommisionRate getCommisionRateAccoToModelType(@PathVariable("modelType") String modelTypeId,
			ModelMap model) {
		CommisionRate commisionRate = commisionRateService.findByModelTypeId(modelTypeId);
		log.info("Commision Rate for Model id : " + modelTypeId + " is : " + commisionRate.getCommission_Rate());
		return commisionRate;
	}

	@RequestMapping(value = { "/saveIt-{branchid}" }, produces = "application/text")
	public @ResponseBody String SaveData(@PathVariable("branchid") String branchid) {
		log.info("ToBranch id $$$$$$$$$$$$$$$$$$$$ : " + branchid);
		return "pankaj";
	}

	/**
	 * New Dashboard for bidding is started from here..............
	 * 
	 * @param requestAjax
	 * @return
	 */

	@RequestMapping(value = { "/submitCashRequest" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> submitCashRequestPost(@RequestBody RequestAjax requestAjax) {
		log.info("ToBranch id $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getToBranchId());
		log.info("FromBranch id $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getFromBranchId());
		log.info("To Branch psoition $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getToBranchPosition());
		log.info("To Branch Amount $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getToBranchTotallAmount());

		boolean alreadyPlacedBid = false;
		DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
				.findDashboardByBranchId(requestAjax.getToBranchId());
		if (!dashboardFinalBid.getRequestStatus().getAlias().equals("APPROVED")) {
			log.info("Inside if bid is already has some other status : "
					+ dashboardFinalBid.getRequestStatus().getAlias());
			requestAjax.setMessage(
					"Request Not Submitted due to request already " + dashboardFinalBid.getRequestStatus().getAlias());
			return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
		}

		boolean flag = placeCashRequestService.saveBranchCashRequestFromDashboard(requestAjax);

		boolean approverFlag = utility.isAllowed("can_be_approver");
		log.info("Place cash request id : " + requestAjax.getId());
		boolean approvedOnceFlag = false;
		if (approverFlag) {
			requestAjax.setPlacedRequestId(requestAjax.getId());
			approvedOnceFlag = placeCashRequestService.updateBidForApproval(requestAjax);
		}
		log.info("Place cash request id after approving  : " + requestAjax.getId());
		if (flag) {
			/**
			 * Now sending mail to the approving permission authority of the
			 * branch who placed the bid
			 */
			boolean mailSend = notificationAndMailUtility.sendMailToApprover(requestAjax.getFromBranchId(),
					requestAjax.getToBranchId());
			requestAjax.setMessage("Bid Placed and Sent For Approval");
		} else {
			requestAjax.setMessage("Bid Placed Failed");
		}

		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	@RequestMapping(value = { "/centralSwapReportingBranchesList_restservice" }, produces = "application/json")
	public @ResponseBody List<DashboardFinalBid> centralSwapReportingBranchesList(ModelMap model) {
		boolean flag = false;
		BranchManagement br = null;
		List<BranchManagement> branchList = null;
		Set<DashboardFinalBid> hierarchyBasedCUGBranchesList = new LinkedHashSet<DashboardFinalBid>();
		br = mySession.getUser().getBranchManagement();

		/**
		 * Checking time if its over or remains.......
		 */
		utility.checkTimeForPlacedBidsInNormalAndSwappingBid(br);

		List<HierarchyControl> hierarchyLists = null;
		List<DashboardFinalBid> dashboardFinalBidsAllList = null;

		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("branchManagement", br);

			dashboardFinalBidsAllList = dashboardFinalBidService
					.getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();

			branchList = new ArrayList<BranchManagement>();
			branchList.add(br);
			branchList = utility.getAllChildBranchList(br, branchList);
			log.info("Now i am printing branch list................." + branchList.size());
			branchList.forEach(s -> log.info("Branch name : " + s.getBranchName() + " and branch id : " + s.getId()
					+ " and branch control : " + s.getBranchControl()));
			Set<BranchManagement> branchManagementsCUGSet = new HashSet<BranchManagement>();
			Set<String> branchManagementsCUGIDSet = new HashSet<String>();

			for (BranchManagement branchManagement : branchList) {
				branchManagementsCUGSet.add(branchManagement);
				for (BranchClosedGroup branchClosedGroup : branchManagement.getBranchClosedGroups()) {
					log.info("Parent branch : " + branchManagement.getBranchName() + " and its closed branch is : "
							+ branchClosedGroup.getClosedGroupBranch().getBranchName());
					branchManagementsCUGSet.add(branchClosedGroup.getClosedGroupBranch());
				}
			}

			branchManagementsCUGSet.forEach(s -> branchManagementsCUGIDSet.add(s.getId()));

			log.info("branch management set of closed groups are : " + branchManagementsCUGSet.size());
			log.info("And now id set of branch management is : " + branchManagementsCUGIDSet.size());

			/**
			 * NOW GETTING FINAL DASHBOARD LIST BY THERE GROUP
			 * LIST...............
			 */
			for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsAllList) {
				if (branchManagementsCUGIDSet.contains(dashboardFinalBid.getBranchManagement().getId())) {
					hierarchyBasedCUGBranchesList.add(dashboardFinalBid);
				}
			}

		} else {
			log.info(" This is super admin : |  user id : " + mySession.getUser().getId() + " having branch : "
					+ mySession.getUser().getBranchManagement().getBranchName());
		}

		model.addAttribute("userBranch", br);
		List<DashboardFinalBid> finalBranchDashboardList = new ArrayList<DashboardFinalBid>();
		finalBranchDashboardList.addAll(hierarchyBasedCUGBranchesList);
		model.addAttribute("finalBranchDashboardList", finalBranchDashboardList);

		log.info("dashboard branch list size : " + finalBranchDashboardList.size());
		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		model.addAttribute("placeCashRequest", placeCashRequest);

		/*
		 * model.addAttribute("NoClosedGroupBranchFoundForSwaping",
		 * model2.get("NoClosedGroupBranchFoundForSwaping"));
		 * model.addAttribute("BidAlreadyPlaced",
		 * model2.get("BidAlreadyPlaced"));
		 */

		return finalBranchDashboardList;

	}

	@RequestMapping(value = { "/sameReportingBranchesList_restservice" }, produces = "application/json")
	public @ResponseBody List<DashboardFinalBid> sameReportingBranches(ModelMap model) {
		boolean flag = false;
		log.info("Inside ajax of user bid dashboard");
		utility.checkTimeForPlacedBidsInNormalAndSwappingBid(mySession.getUser().getBranchManagement());

		List<HierarchyControl> hierarchyLists = null;
		List<DashboardFinalBid> sameReportingBranches = new ArrayList<DashboardFinalBid>();
		List<DashboardFinalBid> otherNonReportingBranches = new ArrayList<DashboardFinalBid>();
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();
		List<DashboardFinalBid> dashboardFinalBidsAllList = null;

		BranchManagement branchManagement3 = branchManagementService.findById(br.getId());
		log.info("################ for branch id :  " + br.getId() + " size is : "
				+ branchManagement3.getBranchParameterStatuses().size());
		log.info("branch obje is : " + branchManagement3);
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("branchManagement", br);
			model.addAttribute("initialFilter", true);

			List<BranchManagement> closedgroupBranchesList = new ArrayList<BranchManagement>();
			Set<String> branchIdSet = new HashSet<String>();
			Set<BranchClosedGroup> closedGroupBranchesSet = br.getBranchClosedGroups();
			for (BranchClosedGroup branchClosedGroup : closedGroupBranchesSet) {
				log.info("Closed branch for : " + branchClosedGroup.getParentBranch().getId() + " and its group is : "
						+ branchClosedGroup.getClosedGroupBranch().getId());
				closedgroupBranchesList.add(branchClosedGroup.getClosedGroupBranch());
				branchIdSet.add(branchClosedGroup.getClosedGroupBranch().getId());
			}

			boolean makerPermission = utility.isAllowed("can_be_maker");
			log.info("maker valyue :********************   " + makerPermission);
			dashboardFinalBidsAllList = dashboardFinalBidService
					.getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();
			for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsAllList) {
				log.info("Inside 1 : " + dashboardFinalBid.getBranchManagement().getId() + " and name is :"
						+ dashboardFinalBid.getBranchManagement().getBranchName() + " and size of branchParamStatus : "
						+ dashboardFinalBid.getBranchManagement().getBranchParameterStatuses().size());
				dashboardFinalBid.setMaker(makerPermission);
				if (dashboardFinalBid.getBranchManagement().getId() == br.getId()) {
					model.addAttribute("branch_position", dashboardFinalBid.getPosition());
					dashboardFinalBid.setSameGroup("true");
					sameReportingBranches.add(dashboardFinalBid);
				} else {
					if (branchIdSet.contains(dashboardFinalBid.getBranchManagement().getId())) {
						dashboardFinalBid.setSameGroup("true");
						sameReportingBranches.add(dashboardFinalBid);
					} else {
						dashboardFinalBid.setSameGroup("false");
						otherNonReportingBranches.add(dashboardFinalBid);
					}
				}
			}

		} else {
			log.info(" This is super admin : |||||||  user id : " + mySession.getUser().getId() + " having branch : "
					+ mySession.getUser().getBranchManagement().getBranchName());
		}

		model.addAttribute("userBranch", br);
		model.addAttribute("sameBranchReportingList", sameReportingBranches);
		model.addAttribute("otherNonreportingBranches", otherNonReportingBranches);

		List<DashboardFinalBid> finalBranchDashboardList = new ArrayList<DashboardFinalBid>();
		finalBranchDashboardList.addAll(sameReportingBranches);
		// finalBranchDashboardList.addAll(otherNonReportingBranches);

		model.addAttribute("finalBranchDashboardList", finalBranchDashboardList);

		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		model.addAttribute("placeCashRequest", placeCashRequest);

		return finalBranchDashboardList;
	}

	@RequestMapping(value = { "/placeCashRequestListForApprove_restservice" }, produces = "application/json")
	public @ResponseBody List<PlaceCashRequest> placeCashRequestListForApprove_restservice(ModelMap model) {
		boolean flag = false;
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();
		List<PlaceCashRequest> placeCashRequestsLists = new ArrayList<PlaceCashRequest>();
		placeCashRequestsLists = placeCashRequestService
				.getAllPlacedBidForTheUserBranch(mySession.getUser().getBranchManagement());

		List<PlaceCashRequestSwap> placeCashRequestSwapsList = new ArrayList<PlaceCashRequestSwap>();
		placeCashRequestSwapsList = placeCashRequestSwapService
				.getAllPlacedSwapBidForTheBranch(mySession.getUser().getBranchManagement());

		flag = utility.isAllowed("can_be_approver");
		if (flag) {
			model.addAttribute("placeCashRequestsLists", placeCashRequestsLists);
			model.addAttribute("userBranch", br);
			return placeCashRequestsLists;
		}
		return placeCashRequestsLists;
	}

	@RequestMapping(value = { "/placeCashRequestListAndSwapForApprove_restservice" }, produces = "application/json")
	public @ResponseBody MultiListResponse placeCashRequestListAndSwapForApprove(ModelMap model) {
		boolean flag = false;
		MultiListResponse multiListResponse = new MultiListResponse();
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();

		List<PlaceCashRequest> placeCashRequestsLists = new ArrayList<PlaceCashRequest>();
		placeCashRequestsLists = placeCashRequestService.getAllPlacedBidForTheUserBranch(br);

		List<PlaceCashRequest> modifiedActivePlaceCashRequestList = new ArrayList<PlaceCashRequest>();
		List<PlaceCashRequest> modifiedDeactivePlaceCashRequestList = new ArrayList<PlaceCashRequest>();

		for (PlaceCashRequest placeCashRequest : placeCashRequestsLists) {
			boolean flag1 = false;
			List<PlaceCashRequestAuditTrial> placeCashRequestAuditTrialsList = placeCashRequestAuditTrialService
					.getAllPlacedBidsAuditTraislByPlaceBidId(placeCashRequest);
			log.info("original place casgh request from audit trial is : " + placeCashRequestAuditTrialsList.size());
			for (PlaceCashRequestAuditTrial placeCashRequestAuditTrial : placeCashRequestAuditTrialsList) {
				if (placeCashRequestAuditTrial.getRequest_Modified_by().equals(mySession.getUser().getId())) {
					flag1 = false;
				} else {
					flag1 = true;
				}
			}
			if (flag1) {
				modifiedActivePlaceCashRequestList.add(placeCashRequest);
			} else {
				modifiedDeactivePlaceCashRequestList.add(placeCashRequest);
			}
		}
		log.info("Modified active request list : " + modifiedActivePlaceCashRequestList.size());
		log.info("Modified InActive request list : " + modifiedDeactivePlaceCashRequestList.size());

		List<PlaceCashRequestSwap> placeCashRequestSwapsList = new ArrayList<PlaceCashRequestSwap>();
		placeCashRequestSwapsList = placeCashRequestSwapService.getAllPlacedSwapBidForTheUserBranch(br);

		List<PlaceCashRequestSwap> modifiedActivePlaceCashRequestSwapsList = new ArrayList<PlaceCashRequestSwap>();
		List<PlaceCashRequestSwap> modifiedDeactivePlaceCashRequestSwapsList = new ArrayList<PlaceCashRequestSwap>();

		for (PlaceCashRequestSwap placeCashRequestSwap : placeCashRequestSwapsList) {
			boolean flag1 = false;
			List<PlaceCashRequestSwapAuditTrial> placeCashRequestSwapAuditTrialsList = placeCashRequestSwapAuditTrialService
					.getAllSwapBidsAuditTraislBySwapPlaceBidId(placeCashRequestSwap);
			for (PlaceCashRequestSwapAuditTrial placeCashRequestSwapAuditTrial : placeCashRequestSwapAuditTrialsList) {
				if (placeCashRequestSwapAuditTrial.getUserModifiedBy().getBranchManagement().getId() == mySession
						.getUser().getBranchManagement().getId()) {
					log.info(
							"user modified id is : " + placeCashRequestSwapAuditTrial.getUserModifiedBy().getFirstName()
									+ " and user branch id : "
									+ placeCashRequestSwapAuditTrial.getUserModifiedBy().getBranchManagement()
											.getBranchName()
									+ " and id branch is : "
									+ placeCashRequestSwapAuditTrial.getUserModifiedBy().getBranchManagement().getId());
					flag1 = false;
				} else {
					flag1 = true;
				}
			}
			if (flag1) {
				modifiedActivePlaceCashRequestSwapsList.add(placeCashRequestSwap);
			} else {
				modifiedDeactivePlaceCashRequestSwapsList.add(placeCashRequestSwap);
			}
		}

		log.info("Modified active swap list : " + modifiedActivePlaceCashRequestSwapsList.size());
		log.info("Modified InActive swap list : " + modifiedDeactivePlaceCashRequestSwapsList.size());

		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		flag = utility.isAllowed("can_be_approver");
		if (flag) {
			// model.addAttribute("placeCashRequestsLists",
			// placeCashRequestsLists);
			model.addAttribute("placeCashRequestsLists", modifiedActivePlaceCashRequestList);
			model.addAttribute("placeCashRequestSwapsList", modifiedActivePlaceCashRequestSwapsList);

			model.addAttribute("userBranch", br);
			model.addAttribute("placeCashRequest", placeCashRequest);
			// return "userapprovalbiddashboard";
		}
		multiListResponse.setPlaceCashRequestsList(modifiedActivePlaceCashRequestList);
		multiListResponse.setPlaceCashRequestSwapsList(modifiedActivePlaceCashRequestSwapsList);

		return multiListResponse;
	}

	@RequestMapping(value = { "/approveBidCashRequest" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> approveBidCashRequest(@RequestBody RequestAjax requestAjax) {
		log.info("ToBranch id      $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getToBranchId());
		log.info("PlaceRequest id  $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getPlacedRequestId());

		boolean alreadyPlacedBid = false;
		DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
				.findDashboardByBranchId(requestAjax.getToBranchId());
		if (!dashboardFinalBid.getRequestStatus().getAlias().equals("PLACED")) {
			log.info("Inside if bid is already has some other status : "
					+ dashboardFinalBid.getRequestStatus().getAlias());
			requestAjax.setMessage("Request already " + dashboardFinalBid.getRequestStatus().getStatus());
			return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
		}

		boolean flag = placeCashRequestService.updateBidForApproval(requestAjax);
		if (flag) {

			/**
			 * Now sending mail to both the branches permission authority of the
			 * branch who placed the bid and accepts the bid
			 */

			boolean mailSend = notificationAndMailUtility
					.sendMailToApproverAndBidPlacedByBranches(requestAjax.getPlacedRequestId());

			requestAjax.setMessage("Bid Approved");
		} else {
			requestAjax.setMessage("Bid Approval Failed");
		}

		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	@RequestMapping(value = { "/cancelBidCashRequest" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> cancelBidCashRequest(@RequestBody RequestAjax requestAjax) {
		log.info("ToBranch id      $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getToBranchId());
		log.info("PlaceRequest id  $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getPlacedRequestId());

		boolean alreadyPlacedBid = false;
		DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
				.findDashboardByBranchId(requestAjax.getToBranchId());
		if (!dashboardFinalBid.getRequestStatus().getAlias().equals("PLACED")) {
			log.info("Inside if bid is already has some other status : "
					+ dashboardFinalBid.getRequestStatus().getAlias());
			requestAjax.setMessage("Bid Closed already " + dashboardFinalBid.getRequestStatus().getAlias());
			return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
		}

		boolean flag = placeCashRequestService.updateBidForCancel(requestAjax);
		if (flag) {

			/**
			 * Now sending mail to both the branches permission authority of the
			 * branch who placed the bid and accepts the bid
			 */
			// boolean mailSend = notificationAndMailUtility
			// .sendMailToApproverAndBidPlacedByBranches(Integer.parseInt(requestAjax.getPlacedRequestId()));
			requestAjax.setMessage("Request Cancel");
		} else {
			requestAjax.setMessage("Requeslt Cancel Failed");
		}

		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	@RequestMapping(value = { "/approveSwapBidCashRequest" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> approveSwapBidCashRequest(@RequestBody RequestAjax requestAjax) {
		log.info("PlaceRequest id  $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getPlacedRequestId());

		boolean alreadyPlacedBid = false;
		PlaceCashRequestSwap placeCashRequestSwap = placeCashRequestSwapService
				.findById(requestAjax.getPlacedRequestId());
		if (!placeCashRequestSwap.getRequestStatus().getAlias().equals("PLACED")) {
			log.info("Inside if bid is already has some other status : "
					+ placeCashRequestSwap.getRequestStatus().getStatus());
			requestAjax.setMessage("Bid Closed already " + placeCashRequestSwap.getRequestStatus().getStatus());
			return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
		}

		boolean flag = placeCashRequestSwapService.updateBidForApproval(placeCashRequestSwap);
		if (flag) {

			/**
			 * Now sending mail to both the branches permission authority of the
			 * branch who placed the bid and accepts the bid
			 */
			// boolean mailSend = notificationAndMailUtility
			// .sendMailToApproverAndBidPlacedByBranches(Integer.parseInt(requestAjax.getPlacedRequestId()));
			requestAjax.setMessage("Bid Approved");
		} else {
			requestAjax.setMessage("Bid Approval Failed");
		}

		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	@RequestMapping(value = { "/cancelBidSwapCashRequest" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> cancelBidSwapCashRequest(@RequestBody RequestAjax requestAjax) {
		log.info("PlaceRequest id cancel swap request $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getPlacedRequestId());

		boolean alreadyPlacedBid = false;
		PlaceCashRequestSwap placeCashRequestSwap = placeCashRequestSwapService
				.findById(requestAjax.getPlacedRequestId());
		if (!placeCashRequestSwap.getRequestStatus().getAlias().equals("PLACED")) {
			log.info("Inside if bid is already has some other status : "
					+ placeCashRequestSwap.getRequestStatus().getAlias());
			requestAjax.setMessage("Bid Closed already " + placeCashRequestSwap.getRequestStatus().getAlias());
			return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
		}

		boolean flag = placeCashRequestSwapService.updateBidForCancel(requestAjax);
		if (flag) {

			/**
			 * Now sending mail to both the branches permission authority of the
			 * branch who placed the bid and accepts the bid
			 */
			// boolean mailSend = notificationAndMailUtility
			// .sendMailToApproverAndBidPlacedByBranches(Integer.parseInt(requestAjax.getPlacedRequestId()));
			requestAjax.setMessage("Bid Cancel");
		} else {
			requestAjax.setMessage("Bid Cancel Failed");
		}

		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	@RequestMapping(value = { "/approveAndUpdateBidCashRequest" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> approveAndUpdateBidCashRequest(@RequestBody RequestAjax requestAjax) {
		log.info("ToBranch id        $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getToBranchId());
		log.info("PlaceRequest id    $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getPlacedRequestId());
		log.info("Amount Updated is  $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getAmount());

		boolean alreadyPlacedBid = false;
		DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
				.findDashboardByBranchId(requestAjax.getToBranchId());
		if (!dashboardFinalBid.getRequestStatus().getAlias().equals("PLACED")) {
			log.info("Inside if bid is already has some other status : "
					+ dashboardFinalBid.getRequestStatus().getAlias());
			requestAjax.setMessage("Bid Closed already " + dashboardFinalBid.getRequestStatus().getAlias());
			return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
		}

		boolean flag = placeCashRequestService.updateAndApproveBidPlaced(requestAjax);
		if (flag) {

			/**
			 * Now sending mail to both the branches permission authority of the
			 * branch who placed the bid and accepts the bid
			 */
			boolean mailSend = notificationAndMailUtility
					.sendMailToApproverAndBidPlacedByBranches(requestAjax.getPlacedRequestId());
			requestAjax.setMessage("Bid Approved");
		} else {
			requestAjax.setMessage("Bid Updation And Approval Failed");
		}

		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	@RequestMapping(value = { "/withdrawCashRequest" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> withdrawCashRequest(@RequestBody RequestAjax requestAjax) {

		log.info("Dashboard id      $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getDashboardId());
		log.info("Branch id         $$$$$$$$$$$$$$$$$$$$ : " + requestAjax.getFromBranchId());

		boolean flag = false;

		flag = dashboardFinalBidService.updateDashboardBidStatusForWithdrawn(requestAjax);

		if (flag) {

			/**
			 * Now sending mail to both the branches permission authority of the
			 * branch who placed the bid and accepts the bid
			 */
			// boolean mailSend = notificationAndMailUtility
			// .sendMailToApproverAndBidPlacedByBranches(Integer.parseInt(requestAjax.getPlacedRequestId()));
			log.info("Inside success");
			// requestAjax.setMessage("Bid Withdrwan Successfully");
		} else {
			log.info("Inside fails");
			// requestAjax.setMessage("Bid Withdrwan Failed");
		}

		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	@RequestMapping(value = { "/getDenominationForParticularBranch" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> getDenominationForParticularBranch(@RequestBody RequestAjax requestAjax) {
		log.info("branch id ************************** " + requestAjax.getToBranchId());
		List<BranchParameterStatus> branchParameterStatusList = branchParameterStatusService
				.getAllBranchParameterStatusListByBranchId(requestAjax.getToBranchId());
		log.info("branch parameter status list size is : " + branchParameterStatusList.size());
		BranchParameterStatus modifiedBranchParameterStatus = new BranchParameterStatus();
		BranchParameterStatus totalBranchParameterStatus = new BranchParameterStatus();
		BranchParameterStatus nonIssuableBranchParameterStatus = new BranchParameterStatus();
		for (BranchParameterStatus branchParameterStatus : branchParameterStatusList) {
			if (branchParameterStatus.getBranchParameter().getParameterAbbreviation()
					.equals("Total Cash Availability")) {
				totalBranchParameterStatus = branchParameterStatus;
			}
			if (branchParameterStatus.getBranchParameter().getParameterAbbreviation()
					.equals("Non issuable notes- (All Denominations)")) {
				nonIssuableBranchParameterStatus = branchParameterStatus;
			}
		}

		modifiedBranchParameterStatus = utility.getModifiedBranchParameterStatusFilterNonIssuable(
				totalBranchParameterStatus, nonIssuableBranchParameterStatus);
		log.info("modified branch status : " + modifiedBranchParameterStatus.getDn2000());
		if (modifiedBranchParameterStatus != null) {
			requestAjax.setBranchParameterStatus(modifiedBranchParameterStatus);
		}
		log.info("branch parameter status parameter name is : "
				+ requestAjax.getBranchParameterStatus().getBranchParameter().getParameterName());
		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	/**
	 * For Swapping find the branches to swap with the given branch id in its
	 * closed group
	 * 
	 * @param requestAjax
	 * @return
	 */
	String position = "";

	@RequestMapping(value = { "/swapCashBranchRequest" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> swapCashBranchRequest(@RequestBody RequestAjax requestAjax) {
		try {
			requestAjax.setMessage("Swap Branch");
			int dashboardId = Integer.parseInt(requestAjax.getDashboardId());
			String branchId = requestAjax.getFromBranchId();
			log.info("dashboard id : | :" + dashboardId + " and branch id : | :" + branchId);

			BranchManagement branchManagement = null;
			if (null != branchId && !"0".equals(branchId)) {
				branchManagement = branchManagementService.findById(branchId);
				log.info("branch management name is : " + branchManagement.getBranchName());
				log.info("branch management closed group size is : " + branchManagement.getBranchClosedGroups().size());
			} else {
				// return "redirect:/centraluserdashboard";
			}

			DashboardFinalBid dashboardFinalBidForBranch = dashboardFinalBidService.findDashboardByBranchId(branchId);
			if (dashboardFinalBidForBranch.getPosition().equals("excess")) {
				position = "below";
			} else if (dashboardFinalBidForBranch.getPosition().equals("below")) {
				position = "excess";
			}
			log.info("the position for which we find the value dashboard bid is : " + position);
			List<DashboardFinalBid> dashboardFinalBidsAllList = null;
			dashboardFinalBidsAllList = dashboardFinalBidService
					.getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();
			log.info("before filter as an approved only list : " + dashboardFinalBidsAllList.size());
			dashboardFinalBidsAllList = dashboardFinalBidsAllList.stream()
					.filter(p -> p.getRequestStatus().getAlias().equals("APPROVED") && p.getPosition().equals(position))
					.collect(Collectors.toList());
			log.info("after filter as an approved only list : " + dashboardFinalBidsAllList.size());

			Set<BranchManagement> modifiedClosedGroupBranchMangementSet = new HashSet<BranchManagement>();
			Set<BranchManagement> modifiedClosedGroupInactiveBranchMangementSet = new HashSet<BranchManagement>();

			Set<BranchClosedGroup> closedGroupBranchesSet = branchManagement.getBranchClosedGroups();
			/**
			 * Now finding if this closed groups set is present for the user who
			 * logged in, his hierarchy barnch and its associated closed group
			 * set or not
			 */
			BranchManagement br = null;
			List<BranchManagement> branchList = null;
			br = mySession.getUser().getBranchManagement();

			branchList = new ArrayList<BranchManagement>();
			branchList.add(br);
			branchList = utility.getAllChildBranchList(br, branchList);
			log.info("Now i am printing branch list................." + branchList.size());
			branchList.forEach(s -> log.info("Branch name : " + s.getBranchName() + " and branch id : " + s.getId()
					+ " and branch control : " + s.getBranchControl()));
			Set<BranchManagement> branchManagementsCUGSet = new HashSet<BranchManagement>();
			Set<String> branchManagementsCUGIDSet = new HashSet<String>();

			for (BranchManagement branchManagement2 : branchList) {
				branchManagementsCUGSet.add(branchManagement2);
				for (BranchClosedGroup branchClosedGroup : branchManagement2.getBranchClosedGroups()) {
					log.info("Parent branch : " + branchManagement2.getBranchName() + " and its closed branch is : "
							+ branchClosedGroup.getClosedGroupBranch().getBranchName());
					branchManagementsCUGSet.add(branchClosedGroup.getClosedGroupBranch());
				}
			}

			branchManagementsCUGSet.forEach(s -> branchManagementsCUGIDSet.add(s.getId()));

			log.info("branch management set of closed groups are : " + branchManagementsCUGSet.size());
			log.info("And now id set of branch management is : " + branchManagementsCUGIDSet.size());
			Set<BranchClosedGroup> filterClosedGroupBranchMangementSet = new HashSet<BranchClosedGroup>();
			for (BranchClosedGroup branchClosedGroup : closedGroupBranchesSet) {
				if (branchManagementsCUGIDSet.contains(branchClosedGroup.getClosedGroupBranch().getId())) {
					filterClosedGroupBranchMangementSet.add(branchClosedGroup);
				}
			}

			for (BranchClosedGroup branchClosedGroup : filterClosedGroupBranchMangementSet) {
				log.info("Closed branch for : " + branchClosedGroup.getParentBranch().getId() + " and its group is : "
						+ branchClosedGroup.getClosedGroupBranch().getId());
				for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsAllList) {
					if (branchClosedGroup.getClosedGroupBranch().getId() == dashboardFinalBid.getBranchManagement()
							.getId()) {
						modifiedClosedGroupBranchMangementSet.add(branchClosedGroup.getClosedGroupBranch());
					}
				}
			}

			if (modifiedClosedGroupBranchMangementSet.size() == 0) {
				log.info("If no closed group branches are available for the branch : "
						+ branchManagement.getBranchName());
				/*
				 * model.addAttribute("NoClosedGroupBranchFoundForSwaping",
				 * messageSource.getMessage(
				 * "No.Closed.Group.Branch.Found.For.Swaping", null,
				 * Locale.ENGLISH));
				 * redirectAttributes.addFlashAttribute("modelMsg", model);
				 * return "redirect:/nogroupsavailable";
				 */
				requestAjax.setMessage("No branches in group available for swapping");
			}

			log.info("Already branch which are InActive in group i.e. size |  "
					+ modifiedClosedGroupInactiveBranchMangementSet.size());
			log.info("Already branch which are Active in group i.e. size |  "
					+ modifiedClosedGroupBranchMangementSet.size());

			// model.addAttribute("modifiedClosedGroupBranchMangementSet",
			// modifiedClosedGroupBranchMangementSet);
			// model.addAttribute("branchManagement", branchManagement);
			PlaceCashRequestSwap placeCashRequestSwap = new PlaceCashRequestSwap();
			placeCashRequestSwap.setBranchManagementRequestedFrom(branchManagement);
			placeCashRequestSwap.setAmount(dashboardFinalBidForBranch.getTotal());
			// model.addAttribute("placeCashRequestSwap", placeCashRequestSwap);
			requestAjax.setPlaceCashRequestSwap(placeCashRequestSwap);
			requestAjax.setBranchManagementsList(modifiedClosedGroupBranchMangementSet);
			return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
		} catch (Exception e) {
			log.info("exception generated ij swap cash request method...... is " + e);
			e.printStackTrace();
			requestAjax.setMessage("Invalid input");
			// return new ResponseEntity<RequestAjax>(requestAjax,
			// HttpStatus.OK);
		}
		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	@RequestMapping(value = { "/submitSwappedCashRequest" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> submitSwappedCashRequest(@RequestBody RequestAjax requestAjax) {
		try {
			requestAjax.setMessage("Request Swapped : ");
			String fromBranchId = requestAjax.getFromBranchId();
			String toBranchId = requestAjax.getToBranchId();
			log.info("from branch id : | :" + fromBranchId + " and to branch id : | :" + toBranchId + " and amount : "
					+ requestAjax.getAmount());

			PlaceCashRequestSwap placeCashRequestSwap = new PlaceCashRequestSwap();

			BranchManagement branchManagementFrom = new BranchManagement();
			branchManagementFrom.setId(fromBranchId);
			BranchManagement branchManagementTo = new BranchManagement();
			branchManagementTo.setId(toBranchId);

			placeCashRequestSwap.setBranchManagementRequestedFrom(branchManagementFrom);
			placeCashRequestSwap.setBranchManagementRequestedTo(branchManagementTo);
			placeCashRequestSwap.setAmount(Double.parseDouble(requestAjax.getAmount()));

			DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
					.findDashboardByBranchId(placeCashRequestSwap.getBranchManagementRequestedFrom().getId());
			DashboardFinalBid dashboardFinalBid1 = dashboardFinalBidService
					.findDashboardByBranchId(placeCashRequestSwap.getBranchManagementRequestedTo().getId());
			if (!dashboardFinalBid.getRequestStatus().getAlias().equals("APPROVED")
					|| !dashboardFinalBid1.getRequestStatus().getAlias().equals("APPROVED")) {
				log.info("Inside if bid is already has some other status i.e for branch id :"
						+ dashboardFinalBid.getBranchManagement().getBranchName() + " first from : "
						+ dashboardFinalBid.getRequestStatus().getAlias());
				log.info("Inside if bid is already has some other status i.e for branch id :"
						+ dashboardFinalBid1.getBranchManagement().getBranchName() + " second to : "
						+ dashboardFinalBid1.getRequestStatus().getAlias());

				requestAjax.setMessage("Request for the branches already processed ?");
				return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
			}

			boolean flag = placeCashRequestSwapService.saveBranchSwapFromSwapingDashboard(placeCashRequestSwap);
			if (flag) {
				boolean mailSent = notificationAndMailUtility.senMailToAllApproversIncludedInSwapping(
						placeCashRequestSwap.getBranchManagementRequestedFrom(),
						placeCashRequestSwap.getBranchManagementRequestedTo(),
						mySession.getUser().getBranchManagement());
				if (mailSent) {
					log.info("mail sent to all corresponding branches for swapping is sent.");
				} else {
					log.info("mail sent to all corresponding branches for swapping is failed.");
				}
			}

		} catch (Exception e) {
			log.info("exception occurs ats swap bid in post method : " + e);
			e.printStackTrace();
			requestAjax.setMessage("Request Not Swapped ");
			return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
		}
		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	/**
	 * New Bidding Concept implemented for new closed group approaches by group
	 * id concepts.......
	 */

	@RequestMapping(value = { "/sameReportingBranchesListForBiddingRestService" }, produces = "application/json")
	public @ResponseBody List<DashboardFinalBid> sameReportingBranchesForBidding(ModelMap model) {
		boolean flag = false;
		log.info("Inside ajax of user bid dashboard");
		utility.checkTimeForPlacedBidsInNormalAndSwappingBid(mySession.getUser().getBranchManagement());

		List<HierarchyControl> hierarchyLists = null;
		List<DashboardFinalBid> selfReportingBranches = new ArrayList<DashboardFinalBid>();
		List<DashboardFinalBid> sameReportingBranches = new ArrayList<DashboardFinalBid>();
		List<DashboardFinalBid> otherNonReportingBranches = new ArrayList<DashboardFinalBid>();
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();
		List<DashboardFinalBid> dashboardFinalBidsAllList = null;

		BranchManagement branchManagement3 = branchManagementService.findById(br.getId());
		log.info("################ for branch id :  " + br.getId() + " size is : "
				+ branchManagement3.getBranchParameterStatuses().size());
		log.info("branch obje is : " + branchManagement3);
		flag = utility.isAllowed("can_view_everything");
		boolean chestExist = true;
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("branchManagement", br);
			model.addAttribute("initialFilter", true);

			Set<String> branchIdSet = new HashSet<String>();
			List<BranchGroup> branchManagementGroupList = new ArrayList<BranchGroup>();
			for (BranchGroup branchGroup : br.getBranchGroups()) {
				log.info("Group id : " + branchGroup.getGroupId() + " branch name is : "
						+ branchGroup.getBranchManagement().getBranchName());
				branchManagementGroupList = branchGroupService.getBranchGroupList(branchGroup);
				branchManagementGroupList
						.forEach(n -> log.info("Branch Name : " + n.getBranchManagement().getBranchName()));

				branchIdSet = branchManagementGroupList.stream().map(p -> p.getBranchManagement().getId())
						.collect(Collectors.toSet());
				if (branchManagementGroupList.stream()
						.anyMatch(x -> x.getBranchManagement().getHierarchyControl().getHierarchyType() == 1)) {
					chestExist = false;
				}

			}

			boolean makerPermission = utility.isAllowed("can_be_maker");
			log.info("maker value :********************   " + makerPermission);
			dashboardFinalBidsAllList = dashboardFinalBidService
					.getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();
			for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsAllList) {
				log.info("Inside 1 : " + dashboardFinalBid.getBranchManagement().getId() + " and name is :"
						+ dashboardFinalBid.getBranchManagement().getBranchName() + " and size of branchParamStatus : "
						+ dashboardFinalBid.getBranchManagement().getBranchParameterStatuses().size());
				dashboardFinalBid.setMaker(makerPermission);
				if (dashboardFinalBid.getBranchManagement().getId() == br.getId()) {
					model.addAttribute("branch_position", dashboardFinalBid.getPosition());
					dashboardFinalBid.setSameGroup("true");
					selfReportingBranches.add(dashboardFinalBid);
				} else {
					if (branchIdSet.contains(dashboardFinalBid.getBranchManagement().getId())) {
						dashboardFinalBid.setSameGroup("true");
						sameReportingBranches.add(dashboardFinalBid);
					} else {
						dashboardFinalBid.setSameGroup("false");
						otherNonReportingBranches.add(dashboardFinalBid);
					}
				}
			}
		} else {
			log.info(" This is super admin : |||||||  user id : " + mySession.getUser().getId() + " having branch : "
					+ mySession.getUser().getBranchManagement().getBranchName());
		}

		model.addAttribute("userBranch", br);
		model.addAttribute("sameBranchReportingList", sameReportingBranches);
		model.addAttribute("otherNonreportingBranches", otherNonReportingBranches);

		List<DashboardFinalBid> finalBranchDashboardList = new ArrayList<DashboardFinalBid>();
		finalBranchDashboardList.addAll(selfReportingBranches);
		if (chestExist || br.getHierarchyControl().getHierarchyType() == 1) {
			finalBranchDashboardList.addAll(sameReportingBranches);
		}
		// finalBranchDashboardList.addAll(otherNonReportingBranches);

		model.addAttribute("finalBranchDashboardList", finalBranchDashboardList);

		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		model.addAttribute("placeCashRequest", placeCashRequest);

		return finalBranchDashboardList;
	}

	@RequestMapping(value = { "/centralSwapReportingBranchesListForBiddingRestService" }, produces = "application/json")
	public @ResponseBody List<DashboardFinalBid> centralSwapReportingBranchesListForBiddingRestService(ModelMap model) {
		boolean flag = false;
		BranchManagement br = null;
		List<BranchManagement> branchList = null;
		Set<DashboardFinalBid> hierarchyBasedCUGBranchesList = new LinkedHashSet<DashboardFinalBid>();
		br = mySession.getUser().getBranchManagement();

		/**
		 * Checking time if its over or remains.......
		 */
		utility.checkTimeForPlacedBidsInNormalAndSwappingBid(br);

		List<HierarchyControl> hierarchyLists = null;
		List<DashboardFinalBid> dashboardFinalBidsAllList = null;

		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("branchManagement", br);

			dashboardFinalBidsAllList = dashboardFinalBidService
					.getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();

			branchList = new ArrayList<BranchManagement>();
			branchList.add(br);
			branchList = utility.getAllChildBranchList(br, branchList);
			log.info("Now i am printing branch listss................." + branchList.size());

			Set<BranchManagement> branchManagementsCUGSet = new HashSet<BranchManagement>();
			Set<String> branchManagementsCUGIDSet = new HashSet<String>();

			for (BranchManagement branchManagement : branchList) {
				branchManagementsCUGSet.add(branchManagement);
				log.info("adding parent branch : " + branchManagement.getBranchName());
				for (BranchGroup branchGroup : branchManagement.getBranchGroups()) {
					log.info("Group id : " + branchGroup.getGroupId() + " branch name is : "
							+ branchGroup.getBranchManagement().getBranchName());
					List<BranchGroup> branchManagementGroupList = branchGroupService.getBranchGroupList(branchGroup);
					branchManagementGroupList
							.forEach(n -> log.info("Branch Name : " + n.getBranchManagement().getBranchName()));
					Set<BranchManagement> branchManagementByGroupSet = branchManagementGroupList.stream()
							.map(p -> p.getBranchManagement()).collect(Collectors.toSet());
					branchManagementsCUGSet.addAll(branchManagementByGroupSet);
				}
			}

			branchManagementsCUGSet.forEach(s -> branchManagementsCUGIDSet.add(s.getId()));

			log.info("branch management set of closed groups are : " + branchManagementsCUGSet.size());
			log.info("And now id set of branch management is : " + branchManagementsCUGIDSet.size());

			/**
			 * NOW GETTING FINAL DASHBOARD LIST BY THERE GROUP
			 * LIST...............
			 */
			Set<String> ccGroupList = branchGroupService.getCCGroupList();
			for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsAllList) {
				if (branchManagementsCUGIDSet.contains(dashboardFinalBid.getBranchManagement().getId())) {
					if (dashboardFinalBid.getBranchManagement().getBranchGroups() != null
							&& dashboardFinalBid.getBranchManagement().getBranchGroups().size() > 0) {
						String groupId = dashboardFinalBid.getBranchManagement().getBranchGroups().stream().findFirst()
								.get().getGroupId();
						log.info("group id in dashboard bid : " + groupId + " and branch name is : "
								+ dashboardFinalBid.getBranchManagement().getBranchName());
						if (ccGroupList.contains(groupId)) {
							log.info("inside if branch group id matches with cc group id ");
							dashboardFinalBid.setCcGroup(true);
						}
					}
					log.info("bidding value is : " + dashboardFinalBid.isCcGroup());
					hierarchyBasedCUGBranchesList.add(dashboardFinalBid);
				}
			}

		} else {
			log.info(" This is super admin : |  user id : " + mySession.getUser().getId() + " having branch : "
					+ mySession.getUser().getBranchManagement().getBranchName());
		}

		model.addAttribute("userBranch", br);
		List<DashboardFinalBid> finalBranchDashboardList = new ArrayList<DashboardFinalBid>();
		finalBranchDashboardList.addAll(hierarchyBasedCUGBranchesList);
		model.addAttribute("finalBranchDashboardList", finalBranchDashboardList);

		log.info("dashboard branch list size : " + finalBranchDashboardList.size());
		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		model.addAttribute("placeCashRequest", placeCashRequest);

		return finalBranchDashboardList;
	}

	@RequestMapping(value = { "/swapCashBranchRequestRestService" }, method = RequestMethod.POST)
	public ResponseEntity<RequestAjax> swapCashBranchRequestRestService(@RequestBody RequestAjax requestAjax) {
		try {
			requestAjax.setMessage("Swap Branch");
			int dashboardId = Integer.parseInt(requestAjax.getDashboardId());
			String branchId = requestAjax.getFromBranchId();
			log.info("dashboard id : | :" + dashboardId + " and branch id : | :" + branchId);

			BranchManagement branchManagement = null;
			if (null != branchId && !"0".equals(branchId)) {
				branchManagement = branchManagementService.findById(branchId);
				log.info("branch management name is : " + branchManagement.getBranchName());
			} else {
				// return "redirect:/centraluserdashboard";
			}

			DashboardFinalBid dashboardFinalBidForBranch = dashboardFinalBidService.findDashboardByBranchId(branchId);
			if (dashboardFinalBidForBranch.getPosition().equals("excess")) {
				position = "below";
			} else if (dashboardFinalBidForBranch.getPosition().equals("below")) {
				position = "excess";
			}
			log.info("the position for which we find the value for swaping dashboard bid is : " + position);
			List<DashboardFinalBid> dashboardFinalBidsAllList = null;
			dashboardFinalBidsAllList = dashboardFinalBidService
					.getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();
			log.info("before filter as an approved only list : " + dashboardFinalBidsAllList.size());
			dashboardFinalBidsAllList = dashboardFinalBidsAllList.stream()
					.filter(p -> p.getRequestStatus().getAlias().equals("APPROVED") && p.getPosition().equals(position))
					.collect(Collectors.toList());
			log.info("after filter as an approved only list : " + dashboardFinalBidsAllList.size());

			Set<BranchManagement> modifiedClosedGroupBranchMangementSet = new HashSet<BranchManagement>();
			Set<BranchManagement> modifiedClosedGroupInactiveBranchMangementSet = new HashSet<BranchManagement>();

			/**
			 * Now finding if this closed groups set is present for the user who
			 * logged in, his hierarchy barnch and its associated closed group
			 * set or not
			 */
			BranchManagement br = null;
			List<BranchManagement> branchList = null;
			br = mySession.getUser().getBranchManagement();

			branchList = new ArrayList<BranchManagement>();
			branchList.add(br);
			branchList = utility.getAllChildBranchList(br, branchList);
			log.info("Now i am printing branch list................." + branchList.size());
			branchList.forEach(s -> log.info("Branch name : " + s.getBranchName() + " and branch id : " + s.getId()
					+ " and branch control : " + s.getBranchControl()));
			Set<BranchManagement> branchManagementsCUGSet = new HashSet<BranchManagement>();
			Set<String> branchManagementsCUGIDSet = new HashSet<String>();

			for (BranchManagement branchManagement2 : branchList) {
				branchManagementsCUGSet.add(branchManagement2);
				for (BranchGroup branchGroup : branchManagement2.getBranchGroups()) {
					List<BranchGroup> branchManagementGroupList = branchGroupService.getBranchGroupList(branchGroup);
					Set<BranchManagement> branchManagementByGroupSet = branchManagementGroupList.stream()
							.map(p -> p.getBranchManagement()).collect(Collectors.toSet());
					branchManagementsCUGSet.addAll(branchManagementByGroupSet);
				}
			}

			branchManagementsCUGSet.forEach(s -> branchManagementsCUGIDSet.add(s.getId()));

			Set<BranchManagement> branchManagementClosedGroup = new HashSet<>();
			for (BranchGroup branchGroup : branchManagement.getBranchGroups()) {
				List<BranchGroup> branchManagementGroupList = branchGroupService.getBranchGroupList(branchGroup);
				branchManagementClosedGroup = branchManagementGroupList.stream().map(p -> p.getBranchManagement())
						.collect(Collectors.toSet());
				break;
			}

			for (BranchManagement branchManagement3 : branchManagementClosedGroup) {
				log.info("Branch name in closed group is : " + branchManagement3.getId());
				for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsAllList) {
					if (branchManagement3.getId() == dashboardFinalBid.getBranchManagement().getId()) {
						modifiedClosedGroupBranchMangementSet.add(branchManagement3);
					}
				}
			}

			if (modifiedClosedGroupBranchMangementSet.size() == 0) {
				log.info("If no closed group branches are available for the branch : "
						+ branchManagement.getBranchName());
				requestAjax.setMessage("No branches in group available for swapping");
			}

			log.info("Already branch which are InActive in group i.e. size |  "
					+ modifiedClosedGroupInactiveBranchMangementSet.size());
			PlaceCashRequestSwap placeCashRequestSwap = new PlaceCashRequestSwap();
			placeCashRequestSwap.setBranchManagementRequestedFrom(branchManagement);
			placeCashRequestSwap.setAmount(dashboardFinalBidForBranch.getTotal());
			// model.addAttribute("placeCashRequestSwap", placeCashRequestSwap);
			requestAjax.setPlaceCashRequestSwap(placeCashRequestSwap);
			requestAjax.setBranchManagementsList(modifiedClosedGroupBranchMangementSet);
			return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
		} catch (Exception e) {
			log.info("exception generated ij swap cash request method...... is " + e);
			e.printStackTrace();
			requestAjax.setMessage("Invalid input");
			// return new ResponseEntity<RequestAjax>(requestAjax,
			// HttpStatus.OK);
		}
		return new ResponseEntity<RequestAjax>(requestAjax, HttpStatus.OK);
	}

	/**
	 * CurrencyChest Dashboard ......
	 */

	@RequestMapping(value = {
			"/sameCurrencyChestReportingBranchesListForBiddingRestService" }, produces = "application/json")
	public @ResponseBody List<DashboardFinalBid> sameCurrencyChestReportingBranchesListForBiddingRestService(
			ModelMap model, HttpServletResponse response) {
		boolean flag = false;
		log.info("Inside ajax of user bid dashboard");
		utility.checkTimeForPlacedBidsInNormalAndSwappingBid(mySession.getUser().getBranchManagement());

		List<HierarchyControl> hierarchyLists = null;
		List<DashboardFinalBid> selfReportingBranches = new ArrayList<DashboardFinalBid>();
		List<DashboardFinalBid> sameReportingBranches = new ArrayList<DashboardFinalBid>();
		List<DashboardFinalBid> otherNonReportingBranches = new ArrayList<DashboardFinalBid>();
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();
		List<DashboardFinalBid> dashboardFinalBidsAllList = null;

		// BranchManagement branchManagement3 = br;
		BranchManagement branchManagement3 = branchManagementService.findById(br.getId());
		log.info("################ for branch id :  " + br.getId() + " size is : "
				+ branchManagement3.getBranchParameterStatuses().size());
		log.info("branch obje is : " + branchManagement3);
		flag = utility.isAllowed("can_view_everything");
		boolean chestExist = true;
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("branchManagement", br);
			model.addAttribute("initialFilter", true);

			Set<String> branchIdSet = new HashSet<String>();
			List<BranchGroup> branchManagementGroupList = new ArrayList<BranchGroup>();
			for (BranchGroup branchGroup : br.getBranchGroups()) {
				log.info("Group id : " + branchGroup.getGroupId() + " branch name is : "
						+ branchGroup.getBranchManagement().getBranchName());
				branchManagementGroupList = branchGroupService.getBranchGroupList(branchGroup);
				branchManagementGroupList
						.forEach(n -> log.info("Branch Name : " + n.getBranchManagement().getBranchName()));
				Set<String> branchIdSetInner = new HashSet<>();
				branchIdSetInner = branchManagementGroupList.stream().map(p -> p.getBranchManagement().getId())
						.collect(Collectors.toSet());
				branchIdSet.addAll(branchIdSetInner);
				branchIdSet.forEach(n -> log.info("In currency chest branch id set value is : " + n));
				if (branchManagementGroupList.stream()
						.anyMatch(x -> x.getBranchManagement().getHierarchyControl().getHierarchyType() == 1)) {
					chestExist = false;
				}

			}

			boolean makerPermission = utility.isAllowed("can_be_maker");
			log.info("maker value :********************   " + makerPermission);
			dashboardFinalBidsAllList = dashboardFinalBidService
					.getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();
			for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsAllList) {
				/*
				 * log.info("Inside 1 : " +
				 * dashboardFinalBid.getBranchManagement().getId() +
				 * " and name is :" +
				 * dashboardFinalBid.getBranchManagement().getBranchName() +
				 * " and size of branchParamStatus : " +
				 * dashboardFinalBid.getBranchManagement().
				 * getBranchParameterStatuses().size());
				 */
				dashboardFinalBid.setMaker(makerPermission);
				if (dashboardFinalBid.getBranchManagement().getId() == br.getId()) {
					model.addAttribute("branch_position", dashboardFinalBid.getPosition());
					dashboardFinalBid.setSameGroup("true");
					selfReportingBranches.add(dashboardFinalBid);
				} else {
					if (branchIdSet.contains(dashboardFinalBid.getBranchManagement().getId())) {
						dashboardFinalBid.setSameGroup("true");
						sameReportingBranches.add(dashboardFinalBid);
					} else {
						dashboardFinalBid.setSameGroup("false");
						otherNonReportingBranches.add(dashboardFinalBid);
					}
				}
			}
		} else {
			log.info(" This is super admin : |||||||  user id : " + mySession.getUser().getId() + " having branch : "
					+ mySession.getUser().getBranchManagement().getBranchName());
		}

		model.addAttribute("userBranch", br);
		model.addAttribute("sameBranchReportingList", sameReportingBranches);
		model.addAttribute("otherNonreportingBranches", otherNonReportingBranches);

		List<DashboardFinalBid> finalBranchDashboardList = new ArrayList<DashboardFinalBid>();
		finalBranchDashboardList.addAll(selfReportingBranches);
		if (chestExist || br.getHierarchyControl().getHierarchyType() == 1) {
			log.info("inside if chest exist or hierarchytype value is 1 list size " + sameReportingBranches.size());
			finalBranchDashboardList.addAll(sameReportingBranches);
		}
		// finalBranchDashboardList.addAll(otherNonReportingBranches);

		model.addAttribute("finalBranchDashboardList", finalBranchDashboardList);

		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		model.addAttribute("placeCashRequest", placeCashRequest);

		return finalBranchDashboardList;
	}

	@RequestMapping(value = { "/allCurrencyChestExistListForBiddingRestService" }, produces = "application/json")
	public @ResponseBody List<DashboardFinalBid> allCurrencyChestExistListForBiddingRestService(ModelMap model) {
		boolean flag = false;
		log.info("Inside ajax of user currency chest bid dashboard");
		utility.checkTimeForPlacedBidsInNormalAndSwappingBid(mySession.getUser().getBranchManagement());

		List<HierarchyControl> hierarchyLists = null;
		List<DashboardFinalBid> selfReportingBranches = new ArrayList<DashboardFinalBid>();
		List<DashboardFinalBid> sameReportingCurrencyChestBranches = new ArrayList<DashboardFinalBid>();
		List<DashboardFinalBid> otherCurrencyChestBranches = new ArrayList<DashboardFinalBid>();
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();
		List<DashboardFinalBid> dashboardFinalBidsAllList = null;

		BranchManagement branchManagement3 = branchManagementService.findById(br.getId());
		log.info("################ for branch currency chest id :  " + br.getId() + " size is : "
				+ branchManagement3.getBranchParameterStatuses().size());
		flag = utility.isAllowed("can_view_everything");
		boolean chestExist = true;
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("branchManagement", br);
			model.addAttribute("initialFilter", true);

			Set<String> branchIdSet = new HashSet<String>();
			Set<String> ccIdSet = new HashSet<>();
			Set<BranchManagement> branchSetForCCInSameGroup = new HashSet<>();
			Set<BranchManagement> branchSetForCCInAll = new HashSet<>();

			/**
			 * Adding only the currency chest which are in the groups of the
			 * currency chest user logged in ......
			 */
			List<BranchGroup> branchManagementGroupList = new ArrayList<BranchGroup>();
			for (BranchGroup branchGroup : br.getBranchGroups()) {
				log.info("In currency Chest Group id : " + branchGroup.getGroupId() + " branch name is : "
						+ branchGroup.getBranchManagement().getBranchName());
				branchManagementGroupList = branchGroupService.getBranchGroupList(branchGroup);
				branchManagementGroupList.forEach(n -> log
						.info("In currency Chest - currency chest Name : " + n.getBranchManagement().getBranchName()));
				branchIdSet = branchManagementGroupList.stream()
						.filter(x -> x.getBranchManagement().getHierarchyControl().getHierarchyType() == 1)
						.map(p -> p.getBranchManagement().getId()).collect(Collectors.toSet());
				branchSetForCCInSameGroup = branchManagementGroupList.stream()
						.filter(x -> x.getBranchManagement().getHierarchyControl().getHierarchyType() == 1)
						.map(p -> p.getBranchManagement()).collect(Collectors.toSet());
				branchSetForCCInSameGroup
						.forEach(n -> log.info("In currency chest - currency chest branch id set value is : "
								+ n.getId() + " and cashlimit is : " + n.getBranchCashlimit()));
				if (branchManagementGroupList.stream()
						.anyMatch(x -> x.getBranchManagement().getHierarchyControl().getHierarchyType() == 1)) {
					chestExist = false;
				}
			}

			/**
			 * Now finding all the currency chest in organization and filtering
			 * the chest who are already select in below code in the same
			 * group...
			 */
			List<HierarchyControl> hierarchyList = mySession.getUser().getBranchManagement().getHierarchyControl()
					.getOrgManagement().getHierarchyControls();
			Set<List<BranchManagement>> branchSetForAllCCByHierarchyControl = hierarchyList.stream()
					.filter(x -> x.getHierarchyType() == 1).map(p -> p.getBranchManagements())
					.collect(Collectors.toSet());
			for (List<BranchManagement> branchList : branchSetForAllCCByHierarchyControl) {
				for (BranchManagement branchManagement : branchList) {
					if (!branchIdSet.contains(branchManagement.getId())) {
						branchSetForCCInAll.add(branchManagement);
						ccIdSet.add(branchManagement.getId());
					}
				}
			}

			log.info("my currency chest ids : " + mySession.getUser().getBranchManagement().getHierarchyControl()
					.getOrgManagement().getHierarchyControls());

			boolean makerPermission = utility.isAllowed("can_be_maker");
			log.info("maker value :********************   " + makerPermission);

			/**
			 * Now making custom dashboard object and its list......
			 */
			DashboardFinalBid dashboardFinalBid = null;
			int count = 1;
			for (BranchManagement branchManagement : branchSetForCCInSameGroup) {
				dashboardFinalBid = new DashboardFinalBid();
				dashboardFinalBid.setId(count++);
				dashboardFinalBid.setBranchManagement(branchManagement);
				dashboardFinalBid.setPosition("excess");
				dashboardFinalBid.setEodTotal(2500000000.00);
				dashboardFinalBid.setTotal(500000000.00);
				dashboardFinalBid.setRequestStatus(utility.getRequestStatusValue("APPROVED"));
				sameReportingCurrencyChestBranches.add(dashboardFinalBid);
			}

			for (BranchManagement branchManagement : branchSetForCCInAll) {
				dashboardFinalBid = new DashboardFinalBid();
				dashboardFinalBid.setId(count++);
				dashboardFinalBid.setBranchManagement(branchManagement);
				dashboardFinalBid.setPosition("below");
				dashboardFinalBid.setEodTotal(2500000000.00);
				dashboardFinalBid.setTotal(500000000.00);
				dashboardFinalBid.setRequestStatus(utility.getRequestStatusValue("APPROVED"));
				otherCurrencyChestBranches.add(dashboardFinalBid);
			}

			sameReportingCurrencyChestBranches
					.forEach(n -> log.info("same reporting cc : " + n.getBranchManagement().getBranchName()));
			otherCurrencyChestBranches
					.forEach(n -> log.info("all non-reporting cc : " + n.getBranchManagement().getBranchName()));

		} else {
			log.info(" This is super admin : |||||||  user id : " + mySession.getUser().getId() + " having branch : "
					+ mySession.getUser().getBranchManagement().getBranchName());
		}

		model.addAttribute("userBranch", br);

		List<DashboardFinalBid> finalBranchDashboardList = new ArrayList<DashboardFinalBid>();
		finalBranchDashboardList.addAll(selfReportingBranches);

		finalBranchDashboardList.addAll(sameReportingCurrencyChestBranches);
		finalBranchDashboardList.addAll(otherCurrencyChestBranches);

		finalBranchDashboardList.sort((p1, p2) -> p1.getBranchManagement().getBranchName()
				.compareTo(p2.getBranchManagement().getBranchName()));

		model.addAttribute("finalBranchDashboardList", finalBranchDashboardList);

		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		model.addAttribute("placeCashRequest", placeCashRequest);

		return finalBranchDashboardList;
	}

	@RequestMapping(value = {
			"/getBranchClosedGroup-{branchManagementId}" }, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody List<BranchClosedGroup> getBranchClosedGroup(
			@PathVariable("branchManagementId") String branchManagementId, ModelMap model) {
		log.info("branchManagementId : " + branchManagementId);
		BranchManagement branchManagement = branchManagementService.findById(branchManagementId);
		log.info(" BranchManagement branchManagementId : " + branchManagement.getId());
		List<BranchClosedGroup> branchClosedGroupList = branchClosedGroupService
				.getBranchClosedBranchList(branchManagement);
		log.info("branchClosedGroupList according to branchId : " + branchClosedGroupList.size());
		return branchClosedGroupList;
	}

	/**
	 * Get Current cash position
	 * 
	 * @param branchManagementId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {
			"/getBranchCurrentCashPosition-{branchManagementId}" }, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Denomination getBranchCurrentCashPosition(
			@PathVariable("branchManagementId") String branchManagementId, ModelMap model) {
		log.info("branchManagementId : " + branchManagementId);

		return branchParameterStatusService.getBranchCurrentCashPosition(branchManagementId);
	}

}
