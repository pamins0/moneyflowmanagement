package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emorphis.cashmanagement.model.BranchParameterStatusAuditTrial;
import com.emorphis.cashmanagement.model.DashboardFinalBidsAuditTrial;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.BranchParameterStatusAuditTrialService;
import com.emorphis.cashmanagement.service.BranchParameterStatusService;
import com.emorphis.cashmanagement.service.DashboardFinalBidsAuditTrialService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.UserManagementService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.Utility;

@Controller
public class ReportsController {
	private static final Logger log = LoggerFactory.getLogger(ReportsController.class);
	@Autowired
	UserManagementService userManagementService;

	@Autowired
	BranchParameterStatusAuditTrialService branchParameterStatusAuditTrialService;

	@Autowired
	UtilityService utilityService;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	DashboardFinalBidsAuditTrialService dashboardFinalBidsAuditTrialService;

	@Autowired
	BranchParameterStatusService branchParameterStatusService;

	/**
	 * This report is based on branch currency status and denomination
	 * 
	 * @author gourav
	 * @param user
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/branchStatusReport", method = RequestMethod.GET)
	private String branchStatusReport(User user, ModelMap model) {
		boolean flag = false;
		// User userobj = mySession.getUser();
		List<HierarchyControl> hierarchyLists = null;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			/*
			 * if(user.getBranchManagement()!=null
			 * &&user.getBranchManagement().getHierarchyControl()!=null){
			 * 
			 * }
			 */
			// model.addAttribute("user", userObj);
			model.addAttribute("hierarchyList", hierarchyLists);
		}
		// else {
		model.addAttribute("user", user);
		// }

		if (user.isSearch()) {

			model.addAttribute("branchStatusReport", branchParameterStatusAuditTrialService.branchStatusReport(user));
		}
		/*
		 * else { model.addAttribute("initialFilter", true); }
		 */

		return "branchStatusReport";
	}

	/**
	 * This report is based on branch currency status and denomination
	 * 
	 * @author gourav
	 * @param user
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/branchStatusReport", method = RequestMethod.POST)
	private String branchStatusReportFilter(User user, ModelMap model) {
		user.setSearch(true);
		return branchStatusReport(user, model);
	}

	// Cash Availability
	@RequestMapping(value = "/branchCashAvailabilityReport", method = RequestMethod.GET)
	private String branchReport(ModelMap model) {

		boolean flag = false;
		User user = new User();
		User userobj = mySession.getUser();
		List<HierarchyControl> hierarchyLists = null;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("user", userobj);
			model.addAttribute("initialFilter", true);
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			model.addAttribute("user", user);
		}

		model.addAttribute("auditTrials", null);
		model.addAttribute("edit", false);

		return "branchCashAvailabilityReport";
		// return "branchReport";
	}

	@RequestMapping(value = "/branchCashAvailabilityReport", method = RequestMethod.POST)
	private String branchReportData(User user, ModelMap model) {

		System.out.println("From date:" + user.getFromDate());
		System.out.println("To date:" + user.getToDate());
		System.out.println("Branch id:" + user.getBranchManagement().getId());
		System.out.println("Hierarchy id:" + user.getBranchManagement().getHierarchyControl().getId());
		System.out.println("Branch IsGroup:" + user.getBranchManagement().getIsgroup());

		Date fromDate = user.getFromDate();
		Date toDate = user.getToDate();
		/**
		 * For total branch param details.
		 */
		// String[] branchParameterIds = {
		// "parhnyjshri-7d34-4872-b9c7-a4044532fa5c" };
		String[] branchParameterIds = { "7" };
		String hierarchyId = user.getBranchManagement().getHierarchyControl().getId();
		String branchId = user.getBranchManagement().getId();
		List<BranchParameterStatusAuditTrial> auditTrials;
		if (hierarchyId != null
				&& (user.getBranchManagement().getId().equals("") || user.getBranchManagement().getId() == null)) {
			auditTrials = branchParameterStatusAuditTrialService.getBranchBetweenDatesByHierarchy(hierarchyId,
					branchParameterIds, fromDate, toDate);
		} else {
			auditTrials = branchParameterStatusAuditTrialService.getBranchBetweenDatesByBranchId(branchId,
					branchParameterIds, fromDate, toDate);
		}

		// System.out.println("ListBranchParameterStatusAuditTrial:" +
		// auditTrials.size());

		boolean flag = false;
		List<HierarchyControl> hierarchyLists = null;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("user", user);
			model.addAttribute("initialFilter", true);
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			model.addAttribute("user", new User());
		}

		model.addAttribute("edit", false);
		model.addAttribute("auditTrials", auditTrials);
		return "branchCashAvailabilityReport";
		// return "branchReport";
	}

	// DenominationReport

	@RequestMapping(value = "/branchCashDenominationReport", method = RequestMethod.GET)
	private String branchCashDenominationReport(ModelMap model) {

		boolean flag = false;
		User user = new User();
		User userobj = mySession.getUser();
		List<HierarchyControl> hierarchyLists = null;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("user", userobj);
			model.addAttribute("initialFilter", true);
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			model.addAttribute("user", user);
		}

		model.addAttribute("auditTrials", null);
		model.addAttribute("edit", false);

		return "branchCashDenominationReport";
	}

	@RequestMapping(value = "/branchCashDenominationReport", method = RequestMethod.POST)
	private String branchCashDenominationReportData(User user, ModelMap model) {

		System.out.println("From date:" + user.getFromDate());
		System.out.println("To date:" + user.getToDate());
		System.out.println("Branch id:" + user.getBranchManagement().getId());

		Date fromDate = user.getFromDate();
		Date toDate = user.getToDate();
		// Integer[] branchParameterIds = {8,9,10,7};
		/*
		 * String[] branchParameterIds = { "5bd9ce83-7d34-4872-b9c7-jfhy78961209",
		 * "5bd9ce83-4859-0000-b9c7-a4044532fa5c",
		 * "5bd9ce83-7d34-4872-b9c7-hagthsgthybh",
		 * "parhnyjshri-7d34-4872-b9c7-a4044532fa5c" };
		 */
		String[] branchParameterIds = { "8", "9", "10", "7" };
		String hierarchyId = user.getBranchManagement().getHierarchyControl().getId();
		String branchId = user.getBranchManagement().getId();
		List<BranchParameterStatusAuditTrial> auditTrials = new ArrayList<BranchParameterStatusAuditTrial>();
		if (hierarchyId != null
				&& (user.getBranchManagement().getId().equals("") || user.getBranchManagement().getId() == null)) {
			auditTrials = branchParameterStatusAuditTrialService.getBranchBetweenDatesByHierarchy(hierarchyId,
					branchParameterIds, fromDate, toDate);
		} else {
			auditTrials = branchParameterStatusAuditTrialService.getBranchBetweenDatesByBranchId(branchId,
					branchParameterIds, fromDate, toDate);
		}

		// System.out.println("ListBranchParameterStatusAuditTrial:" +
		// auditTrials.size());

		boolean flag = false;
		List<HierarchyControl> hierarchyLists = null;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("user", user);
			model.addAttribute("initialFilter", true);
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			model.addAttribute("user", new User());
		}

		model.addAttribute("edit", false);
		model.addAttribute("auditTrials", auditTrials);
		return "branchCashDenominationReport";
	}

	// active vs non active branch
	@RequestMapping(value = "/branchActiveVsNonActiveReport", method = RequestMethod.GET)
	private String branchActiveVsNonActiveReport(ModelMap model) {

		boolean flag = false;
		User user = new User();
		User userobj = mySession.getUser();
		List<HierarchyControl> hierarchyLists = null;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("user", userobj);
			model.addAttribute("initialFilter", true);
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			model.addAttribute("user", user);
		}

		model.addAttribute("auditTrials", null);
		model.addAttribute("edit", false);

		return "branchActiveVsNonActiveReport";
	}

	@RequestMapping(value = "/branchActiveVsNonActiveReport", method = RequestMethod.POST)
	private String branchActiveVsNonActiveReportData(User user, ModelMap model) {

		System.out.println("From date:" + user.getFromDate());
		System.out.println("To date:" + user.getToDate());
		System.out.println("Branch id:" + user.getBranchManagement().getId());

		Date fromDate = user.getFromDate();
		Date toDate = user.getToDate();
		// Integer[] branchParameterIds = { 8, 9 };
		// String[] branchParameterIds = {
		// "5bd9ce83-7d34-4872-b9c7-jfhy78961209",
		// "5bd9ce83-4859-0000-b9c7-a4044532fa5c" };
		String[] branchParameterIds = { "8", "9" };
		String hierarchyId = user.getBranchManagement().getHierarchyControl().getId();
		String branchId = user.getBranchManagement().getId();
		List<BranchParameterStatusAuditTrial> auditTrials;
		if (hierarchyId != null && !hierarchyId.equals("-1") && user.getBranchManagement().getId() == null) {
			auditTrials = branchParameterStatusAuditTrialService.getBranchBetweenDatesByHierarchy(hierarchyId,
					branchParameterIds, fromDate, toDate);
		} else {
			auditTrials = branchParameterStatusAuditTrialService.getBranchBetweenDatesByBranchId(branchId,
					branchParameterIds, fromDate, toDate);
		}

		System.out.println("ListBranchParameterStatusAuditTrial:" + auditTrials.size());

		boolean flag = false;
		List<HierarchyControl> hierarchyLists = null;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("user", user);
			model.addAttribute("initialFilter", true);
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			model.addAttribute("user", new User());
		}

		model.addAttribute("edit", false);
		model.addAttribute("auditTrials", auditTrials);
		return "branchActiveVsNonActiveReport";
	}

	// placed vs accepted
	@RequestMapping(value = "/bidsPlacedVsAcceptedReport", method = RequestMethod.GET)
	private String bidsPlacedVsAcceptedReport(User user, ModelMap model) {

		List<HierarchyControl> hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
		User userobj = mySession.getUser();
		model.addAttribute("user", userobj);
		model.addAttribute("hierarchyList", hierarchyLists);

		List<DashboardFinalBidsAuditTrial> totalPlacedBidsList = dashboardFinalBidsAuditTrialService
				.getByStatus("APPROVED");
		List<DashboardFinalBidsAuditTrial> totalAcceptedBidsList = dashboardFinalBidsAuditTrialService
				.getByStatus("BIDAPPROVED");
		model.addAttribute("totalPlacedBids", totalPlacedBidsList.size());
		model.addAttribute("totalAcceptedBids", totalAcceptedBidsList.size());

		return "bidsPlacedVsAcceptedReport";
	}

	@RequestMapping(value = "/bidsPlacedVsAcceptedReport", method = RequestMethod.POST)
	private String bidsPlacedVsAcceptedReportData(User user, ModelMap model) {

		System.out.println("From date:" + user.getFromDate());
		System.out.println("To date:" + user.getToDate());
		System.out.println("Branch id:" + user.getBranchManagement().getId());

		Date fromDate = user.getFromDate();
		Date toDate = user.getToDate();
		String branchId = user.getBranchManagement().getId();

		List<HierarchyControl> hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);

		String hierarchyId = user.getBranchManagement().getHierarchyControl().getId();

		if (hierarchyId != null && !hierarchyId.equals("-1") && branchId == null) {

			List<DashboardFinalBidsAuditTrial> totalPlacedBids = dashboardFinalBidsAuditTrialService
					.getBidsByHierarchyId(fromDate, toDate, hierarchyId, "APPROVED");
			List<DashboardFinalBidsAuditTrial> totalAcceptedBids = dashboardFinalBidsAuditTrialService
					.getBidsByHierarchyId(fromDate, toDate, hierarchyId, "BIDAPPROVED");
			model.addAttribute("totalPlacedBids", totalPlacedBids.size());
			model.addAttribute("totalAcceptedBids", totalAcceptedBids.size());
		} else {
			model.addAttribute("totalPlacedBids",
					dashboardFinalBidsAuditTrialService.getBids(fromDate, toDate, branchId, "APPROVED").size());
			model.addAttribute("totalAcceptedBids",
					dashboardFinalBidsAuditTrialService.getBids(fromDate, toDate, branchId, "BIDAPPROVED").size());
		}

		model.addAttribute("user", user);
		model.addAttribute("hierarchyList", hierarchyLists);

		return "bidsPlacedVsAcceptedReport";
	}

	// Percentage Report
	@RequestMapping(value = "/percentageReport", method = RequestMethod.GET)
	private String percentageReport(User user, ModelMap model) {

		List<HierarchyControl> hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
		User userobj = mySession.getUser();
		model.addAttribute("user", userobj);
		model.addAttribute("hierarchyList", hierarchyLists);

		model.addAttribute("totalExcess", dashboardFinalBidsAuditTrialService.getByPosition("excess").size());
		model.addAttribute("totalBelow", dashboardFinalBidsAuditTrialService.getByPosition("below").size());
		model.addAttribute("totalLevel", dashboardFinalBidsAuditTrialService.getByPosition("level").size());

		return "percentageReport";
	}

	@RequestMapping(value = "/percentageReport", method = RequestMethod.POST)
	private String percentageReportData(User user, ModelMap model) {

		System.out.println("From date:" + user.getFromDate());
		System.out.println("To date:" + user.getToDate());
		System.out.println("Branch id:" + user.getBranchManagement().getId());

		Date fromDate = user.getFromDate();
		Date toDate = user.getToDate();
		String branchId = user.getBranchManagement().getId();

		List<HierarchyControl> hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);

		String hierarchyId = user.getBranchManagement().getHierarchyControl().getId();

		if (hierarchyId != null && !hierarchyId.equals("-1") && branchId == null) {

			model.addAttribute("totalExcess", dashboardFinalBidsAuditTrialService
					.getByHierarchyIdAndPosition(fromDate, toDate, hierarchyId, "excess").size());
			model.addAttribute("totalBelow", dashboardFinalBidsAuditTrialService
					.getByHierarchyIdAndPosition(fromDate, toDate, hierarchyId, "below").size());
			model.addAttribute("totalLevel", dashboardFinalBidsAuditTrialService
					.getByHierarchyIdAndPosition(fromDate, toDate, hierarchyId, "level").size());

		} else {
			model.addAttribute("totalExcess", dashboardFinalBidsAuditTrialService
					.getBidsByBranchIdAndPosition(fromDate, toDate, branchId, "excess").size());
			model.addAttribute("totalBelow", dashboardFinalBidsAuditTrialService
					.getBidsByBranchIdAndPosition(fromDate, toDate, branchId, "below").size());
			model.addAttribute("totalLevel", dashboardFinalBidsAuditTrialService
					.getBidsByBranchIdAndPosition(fromDate, toDate, branchId, "level").size());

		}

		model.addAttribute("user", user);
		model.addAttribute("hierarchyList", hierarchyLists);

		return "percentageReport";
	}

	@RequestMapping(value = "rendarReportDashBoard", method = RequestMethod.GET)
	public String rendarReportDashBoard(ModelMap model) {
		boolean flag = utility.isAllowed("can_view_everything");
		if (flag) {
			log.info("Admin is being log in::::::::");
			// select all branch list for drop down
			return "blackPage";
		}
		byte b = mySession.getUser().getBranchManagement().getChest();
		boolean hasCurrencyChest = b == 0 ? false : true;
		log.info("Brach Having Currency Chest::::::::" + hasCurrencyChest);
		List<HierarchyControl> hierarchyList = utilityService.getHierarchyListAccToUserLevel(model);
		model.addAttribute("hierarchyList", hierarchyList);
		model.addAttribute("hasCurrencyChest", hasCurrencyChest);
		return "reportDashBoard";
	}

}
