package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwapAuditTrial;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.DashboardFinalBidService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.service.PlaceCashRequestService;
import com.emorphis.cashmanagement.service.PlaceCashRequestSwapAuditTrialService;
import com.emorphis.cashmanagement.service.PlaceCashRequestSwapService;
import com.emorphis.cashmanagement.service.RolePermissionService;
import com.emorphis.cashmanagement.service.UserManagementService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.MailUtility;
import com.emorphis.cashmanagement.util.NotificationAndMailUtility;
import com.emorphis.cashmanagement.util.Utility;

@Controller
public class UserDashboardController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	MySession mySession;

	@Autowired
	UserManagementService userManagementService;

	@Autowired
	RolePermissionService rolePermissionService;

	@Autowired
	UtilityService utilityService;

	@Autowired
	Utility utility;

	@Autowired
	MailUtility mailUtility;

	@Autowired
	BranchManagementService branchManagementService;

	@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	OrgManagementService orgManagementService;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	PlaceCashRequestService placeCashRequestService;

	@Autowired
	DashboardFinalBidService dashboardFinalBidService;

	@Autowired
	PlaceCashRequestSwapService placeCashRequestSwapService;

	@Autowired
	PlaceCashRequestSwapAuditTrialService placeCashRequestSwapAuditTrialService;

	@Autowired
	NotificationAndMailUtility notificationAndMailUtility;

	String position = "";

	@RequestMapping(value = { "/dashboard" }, method = RequestMethod.GET)
	public String dashboard(ModelMap model) {
		boolean flag = false;
		flag = utility.isAllowed("can_view_everything");
		if (flag) {
			model.addAttribute("BidTypeSuperAdmin", "true");
			return "redirect:/home";
		}
		//Commenting the swapping dashboard currently no need forthis dashboard
		
		/*if (mySession.getUser().getBranchManagement().getIsgroup() != 0
				&& mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement()
						.getCmsApproach() == 0
				&& mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyLevel() == 1) {
			return "redirect:/centraluserdashboard";
		}*/

		if (mySession.getUser().getBranchManagement().getIsgroup() == 0
				&& mySession.getUser().getBranchManagement().getBranchType() == 0) {
			if (mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyType() == 1) {
				return "redirect:/usercurrencychestbiddashboard";
			} else {
				return "redirect:/userbiddashboard"; 
			}
		}

		return "redirect:/home";
	}

	@RequestMapping(value = { "/usercurrencychestbiddashboard" }, method = RequestMethod.GET)
	public String userCurrencyChestBidDashboard(ModelMap model, @ModelAttribute("modelMsg") ModelMap model2) {
		boolean flag = false;
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();
		/**
		 * For Bid Approval List################
		 */
		utility.checkTimeForPlacedBidsInNormalAndSwappingBid(br);

		List<HierarchyControl> hierarchyLists = null;
		List<DashboardFinalBid> sameReportingBranches = new ArrayList<DashboardFinalBid>();
		List<DashboardFinalBid> otherNonReportingBranches = new ArrayList<DashboardFinalBid>();

		BranchManagement branchManagement = new BranchManagement();

		List<DashboardFinalBid> dashboardFinalBidsAllList = null;

		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("branchManagement", br);
			model.addAttribute("initialFilter", true);
		} else {
			System.out.println(" This is super admin : |||||||  user id : " + mySession.getUser().getId()
					+ " having branch : " + mySession.getUser().getBranchManagement().getBranchName());
		}

		model.addAttribute("userBranch", br);
		model.addAttribute("sameBranchReportingList", sameReportingBranches);
		model.addAttribute("otherNonreportingBranches", otherNonReportingBranches);

		List<DashboardFinalBid> finalBranchDashboardList = new ArrayList<DashboardFinalBid>();
		finalBranchDashboardList.addAll(sameReportingBranches);
		/**
		 * Now discard to add the other branches which are not in the group
		 */
		// finalBranchDashboardList.addAll(otherNonReportingBranches);

		model.addAttribute("finalBranchDashboardList", finalBranchDashboardList);

		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		model.addAttribute("placeCashRequest", placeCashRequest);

		model.addAttribute("fromBranchNotExcessCash", model2.get("fromBranchNotExcessCash"));

		return "usercurrencychestbiddashboard";
	}

	@RequestMapping(value = { "/updateandapproverequestfordashboard" }, method = RequestMethod.GET)
	public String upadteandapproverequestfordashboardGet(ModelMap model) {
		boolean flag = false;
		flag = utility.isAllowed("can_be_approver");
		if (flag) {
			BranchManagement branchManagement = mySession.getUser().getBranchManagement();
			DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
					.findDashboardByBranchId(branchManagement.getId());
			model.addAttribute("dashboardFinalBid", dashboardFinalBid);
			model.addAttribute("edit", true);
			if (null != dashboardFinalBid) {
				System.out.println("dashboard final bid id is : " + dashboardFinalBid.getId() + " and amount : "
						+ dashboardFinalBid.getTotal());
				if (dashboardFinalBid.getRequestStatus().getAlias().equals("PENDING")) {
					if (dashboardFinalBid.getPosition().equals("below")) {
						dashboardFinalBid.setRequestType((byte) 1);
					} else if (dashboardFinalBid.getPosition().equals("excess")) {
						dashboardFinalBid.setRequestType((byte) 2);
					} else if (dashboardFinalBid.getPosition().equals("level")) {
						dashboardFinalBid.setRequestType((byte) 3);
					} else {
						dashboardFinalBid.setRequestType((byte) -1);
					}
					return "updateandapproverequestfordashboard";
				} else {
					model.addAttribute("alreadyProcessed", true);
					return "updateandapproverequestfordashboard";
				}
			} else {
				return "redirect:/home";
			}
		} else {
			return "redirect:/home";
		}

	}

	@RequestMapping(value = { "/updateandapproverequestfordashboard" }, method = RequestMethod.POST)
	public String upadteandapproverequestfordashboardPost(ModelMap model, DashboardFinalBid dashboardFinalBid) {
		System.out.println("dashboard final bid request type : " + dashboardFinalBid.getRequestType());
		if (dashboardFinalBid.getRequestType() == 1) {
			dashboardFinalBid.setPosition("below");
		} else if (dashboardFinalBid.getRequestType() == 2) {
			dashboardFinalBid.setPosition("excess");
		} else if (dashboardFinalBid.getRequestType() == 3) {
			dashboardFinalBid.setPosition("level");
		}
		System.out.println("dashboard final bid id is : " + dashboardFinalBid.getId());
		System.out.println("button value is : " + dashboardFinalBid.getSaveBtn());

		dashboardFinalBidService.updateById(dashboardFinalBid);

		return "redirect:/userbiddashboard";
	}

	@RequestMapping(value = { "/centraluserdashboard" }, method = RequestMethod.GET)
	public String centraluserdashboard(ModelMap model, @ModelAttribute("modelMsg") ModelMap model2) {
		try {
			if (mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement()
					.getCmsApproach() == 0) {
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
					System.out.println("Now i am printing branch list................." + branchList.size());
					branchList.forEach(s -> System.out.println("Branch name : " + s.getBranchName()
							+ " and branch id : " + s.getId() + " and branch control : " + s.getBranchControl()));
					Set<BranchManagement> branchManagementsCUGSet = new HashSet<BranchManagement>();
					Set<String> branchManagementsCUGIDSet = new HashSet<String>();

					for (BranchManagement branchManagement : branchList) {
						branchManagementsCUGSet.add(branchManagement);
						for (BranchClosedGroup branchClosedGroup : branchManagement.getBranchClosedGroups()) {
							System.out.println("Branchmanagebee  name is : " + branchManagement.getBranchName());
							System.out.println("Parent branch : " + branchManagement.getBranchName()
									+ " and its closed branch is : "
									+ branchClosedGroup.getClosedGroupBranch().getBranchName());
							branchManagementsCUGSet.add(branchClosedGroup.getClosedGroupBranch());
						}
					}

					branchManagementsCUGSet.forEach(s -> branchManagementsCUGIDSet.add(s.getId()));

					System.out
							.println("branch management set of closed groups are : " + branchManagementsCUGSet.size());
					System.out.println("And now id set of branch management is : " + branchManagementsCUGIDSet.size());

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
					System.out.println(" This is super admin : |  user id : " + mySession.getUser().getId()
							+ " having branch : " + mySession.getUser().getBranchManagement().getBranchName());
					return "redirect:/home";
				}

				model.addAttribute("userBranch", br);
				List<DashboardFinalBid> finalBranchDashboardList = new ArrayList<DashboardFinalBid>();
				finalBranchDashboardList.addAll(hierarchyBasedCUGBranchesList);
				model.addAttribute("finalBranchDashboardList", finalBranchDashboardList);

				System.out.println("dashboard branch list size : " + finalBranchDashboardList.size());
				PlaceCashRequest placeCashRequest = new PlaceCashRequest();
				model.addAttribute("placeCashRequest", placeCashRequest);

				model.addAttribute("NoClosedGroupBranchFoundForSwaping",
						model2.get("NoClosedGroupBranchFoundForSwaping"));
				model.addAttribute("BidAlreadyPlaced", model2.get("BidAlreadyPlaced"));

				return "centraluserbidswapdashboard";
			} else {
				return "redirect:/home";
			}
		} catch (Exception e) {
			System.out.println("Exception generated at centraluserbiddashboard the message is : " + e);
			e.printStackTrace();

			return "redirect:/home";
		}

	}

	@RequestMapping(value = { "/nogroupsavailable" })
	public String noclosedgroupsavailablepopup() {

		return "noclosedgroupsavailablepopup";
	}

	@RequestMapping(value = { "/swapCashDump-{branchId}-{dashboardId}" }, method = RequestMethod.GET)
	public String swapCashDump(@PathVariable String branchId, @PathVariable Integer dashboardId) {
		System.out.println("dashboard id ######## : " + dashboardId + " branch id : " + branchId);
		return "redirect:/home";
	}

	@RequestMapping(value = { "/swapCashBranchRequest-{branchId}-{dashboardId}" }, method = RequestMethod.GET)
	public synchronized String swapCashBranchRequest(RedirectAttributes redirectAttributes,
			@PathVariable String branchId, @PathVariable Integer dashboardId, ModelMap model) {
		try {
			System.out.println("dashboard id : | :" + dashboardId + " and branch id : | :" + branchId);

			BranchManagement branchManagement = null;
			if (null != branchId && !"0".equals(branchId)) {
				branchManagement = branchManagementService.findById(branchId);
				System.out.println("branch management name is : " + branchManagement.getBranchName());
				System.out.println(
						"branch management closed group size is : " + branchManagement.getBranchClosedGroups().size());
			} else {
				return "redirect:/centraluserdashboard";
			}

			DashboardFinalBid dashboardFinalBidForBranch = dashboardFinalBidService.findDashboardByBranchId(branchId);
			if (dashboardFinalBidForBranch.getPosition().equals("excess")) {
				position = "below";
			} else if (dashboardFinalBidForBranch.getPosition().equals("below")) {
				position = "excess";
			}
			System.out.println("the position for which we find the value dashboard bid is : " + position);
			List<DashboardFinalBid> dashboardFinalBidsAllList = null;
			dashboardFinalBidsAllList = dashboardFinalBidService
					.getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();
			System.out.println("before filter as an approved only list : " + dashboardFinalBidsAllList.size());
			dashboardFinalBidsAllList = dashboardFinalBidsAllList.stream()
					.filter(p -> p.getRequestStatus().getAlias().equals("APPROVED") && p.getPosition().equals(position))
					.collect(Collectors.toList());
			System.out.println("after filter as an approved only list : " + dashboardFinalBidsAllList.size());

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
			System.out.println("Now i am printing branch list................." + branchList.size());
			branchList.forEach(s -> System.out.println("Branch name : " + s.getBranchName() + " and branch id : "
					+ s.getId() + " and branch control : " + s.getBranchControl()));
			Set<BranchManagement> branchManagementsCUGSet = new HashSet<BranchManagement>();
			Set<String> branchManagementsCUGIDSet = new HashSet<String>();

			for (BranchManagement branchManagement2 : branchList) {
				branchManagementsCUGSet.add(branchManagement2);
				for (BranchClosedGroup branchClosedGroup : branchManagement2.getBranchClosedGroups()) {
					System.out.println(
							"Parent branch : " + branchManagement2.getBranchName() + " and its closed branch is : "
									+ branchClosedGroup.getClosedGroupBranch().getBranchName());
					branchManagementsCUGSet.add(branchClosedGroup.getClosedGroupBranch());
				}
			}

			branchManagementsCUGSet.forEach(s -> branchManagementsCUGIDSet.add(s.getId()));

			System.out.println("branch management set of closed groups are : " + branchManagementsCUGSet.size());
			System.out.println("And now id set of branch management is : " + branchManagementsCUGIDSet.size());
			Set<BranchClosedGroup> filterClosedGroupBranchMangementSet = new HashSet<BranchClosedGroup>();
			for (BranchClosedGroup branchClosedGroup : closedGroupBranchesSet) {
				if (branchManagementsCUGIDSet.contains(branchClosedGroup.getClosedGroupBranch().getId())) {
					filterClosedGroupBranchMangementSet.add(branchClosedGroup);
				}
			}

			for (BranchClosedGroup branchClosedGroup : filterClosedGroupBranchMangementSet) {
				System.out.println("Closed branch for : " + branchClosedGroup.getParentBranch().getId()
						+ " and its group is : " + branchClosedGroup.getClosedGroupBranch().getId());
				for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsAllList) {
					if (branchClosedGroup.getClosedGroupBranch().getId() == dashboardFinalBid.getBranchManagement()
							.getId()) {
						modifiedClosedGroupBranchMangementSet.add(branchClosedGroup.getClosedGroupBranch());
					}
				}
			}

			if (modifiedClosedGroupBranchMangementSet.size() == 0) {
				System.out.println("If no closed group branches are available for the branch : "
						+ branchManagement.getBranchName());
				model.addAttribute("NoClosedGroupBranchFoundForSwaping",
						messageSource.getMessage("No.Closed.Group.Branch.Found.For.Swaping", null, Locale.ENGLISH));
				redirectAttributes.addFlashAttribute("modelMsg", model);
				return "redirect:/nogroupsavailable";
			}

			System.out.println("Already branch which are InActive in group i.e. size |  "
					+ modifiedClosedGroupInactiveBranchMangementSet.size());
			System.out.println("Already branch which are Active in group i.e. size |  "
					+ modifiedClosedGroupBranchMangementSet.size());

			model.addAttribute("modifiedClosedGroupBranchMangementSet", modifiedClosedGroupBranchMangementSet);
			model.addAttribute("branchManagement", branchManagement);
			PlaceCashRequestSwap placeCashRequestSwap = new PlaceCashRequestSwap();
			placeCashRequestSwap.setAmount(dashboardFinalBidForBranch.getTotal());
			model.addAttribute("placeCashRequestSwap", placeCashRequestSwap);

			return "swapCashBetweenBranches";
		} catch (Exception e) {
			System.out.println("exception generated ij swap cash request method...... is " + e);
			e.printStackTrace();
			return "redirect:/centraluserdashboard";
		}
	}

	@RequestMapping(value = { "/swapCashBranchRequest-{branchId}-{dashboardId}" }, method = RequestMethod.POST)
	public String swapCashBranchRequestPost(@Valid PlaceCashRequestSwap placeCashRequestSwap,
			RedirectAttributes redirectAttributes, @PathVariable String branchId, @PathVariable Integer dashboardId,
			ModelMap model) {
		try {
			System.out.println("dashboard id : | :" + dashboardId + " and branch id : | :" + branchId);
			System.out.println("Branch for swap : " + placeCashRequestSwap.getBranchManagementRequestedFrom().getId());
			System.out.println("Branch to swap : " + placeCashRequestSwap.getBranchManagementRequestedTo().getId());

			DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
					.findDashboardByBranchId(placeCashRequestSwap.getBranchManagementRequestedFrom().getId());
			DashboardFinalBid dashboardFinalBid1 = dashboardFinalBidService
					.findDashboardByBranchId(placeCashRequestSwap.getBranchManagementRequestedTo().getId());
			if (!dashboardFinalBid.getRequestStatus().getAlias().equals("APPROVED")
					|| !dashboardFinalBid1.getRequestStatus().getAlias().equals("APPROVED")) {
				System.out.println("Inside if bid is already has some other status i.e for branch id :"
						+ dashboardFinalBid.getBranchManagement().getBranchName() + " first from : "
						+ dashboardFinalBid.getRequestStatus().getAlias());
				System.out.println("Inside if bid is already has some other status i.e for branch id :"
						+ dashboardFinalBid1.getBranchManagement().getBranchName() + " second to : "
						+ dashboardFinalBid1.getRequestStatus().getAlias());

				model.addAttribute("BidAlreadyPlaced",
						messageSource.getMessage("already.bid.accepted", null, Locale.ENGLISH));
				redirectAttributes.addFlashAttribute("modelMsg", model);
				return "redirect:/centraluserdashboard";
			}

			boolean flag = placeCashRequestSwapService.saveBranchSwapFromSwapingDashboard(placeCashRequestSwap);
			if (flag) {
				boolean mailSent = notificationAndMailUtility.senMailToAllApproversIncludedInSwapping(
						placeCashRequestSwap.getBranchManagementRequestedFrom(),
						placeCashRequestSwap.getBranchManagementRequestedTo(),
						mySession.getUser().getBranchManagement());
				if (mailSent) {
					System.out.println("mail sent to all corresponding branches for swapping is sent.");
				} else {
					System.out.println("mail sent to all corresponding branches for swapping is failed.");
				}
			}

			return "redirect:/centraluserdashboard";
		} catch (Exception e) {
			System.out.println("exception occurs ats swap bid in post method : " + e);
			e.printStackTrace();
			return "redirect:/centraluserdashboard";
		}
	}

	@RequestMapping(value = { "/userapprovebiddashboard" }, method = RequestMethod.GET)
	public String userApproverBidDashboard(ModelMap model) {
		boolean flag = false;
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();

		List<PlaceCashRequest> placeCashRequestsLists = new ArrayList<PlaceCashRequest>();
		placeCashRequestsLists = placeCashRequestService.getAllPlacedBidForTheUserBranch(br);

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

		System.out.println("Modified active swap list : " + modifiedActivePlaceCashRequestSwapsList.size());
		System.out.println("Modified InActive swap list : " + modifiedDeactivePlaceCashRequestSwapsList.size());

		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		flag = utility.isAllowed("can_be_approver");
		if (flag) {
			model.addAttribute("placeCashRequestsLists", placeCashRequestsLists);
			model.addAttribute("placeCashRequestSwapsList", modifiedActivePlaceCashRequestSwapsList);

			model.addAttribute("userBranch", br);
			model.addAttribute("placeCashRequest", placeCashRequest);
			return "userapprovalbiddashboard";
		}
		return "";
	}

	@RequestMapping(value = { "/updateAndSubmitBidApprovalRequest" }, method = RequestMethod.POST)
	public String updateAndSubmitBidApprovalRequest(@Valid PlaceCashRequest placeCashRequest, ModelMap model) {
		System.out.println("Amount is : " + placeCashRequest.getAmount());
		System.out.println("Update id : " + placeCashRequest.getId());
		return "";
	}

	@RequestMapping(value = { "/userbiddashboard" }, method = RequestMethod.GET)
	public String userBidDashboard(ModelMap model, @ModelAttribute("modelMsg") ModelMap model2) {
		boolean flag = false;
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();
		/**
		 * For Bid Approval List################
		 */
		utility.checkTimeForPlacedBidsInNormalAndSwappingBid(br);

		List<HierarchyControl> hierarchyLists = null;
		List<DashboardFinalBid> sameReportingBranches = new ArrayList<DashboardFinalBid>();
		List<DashboardFinalBid> otherNonReportingBranches = new ArrayList<DashboardFinalBid>();

		BranchManagement branchManagement = new BranchManagement();

		List<DashboardFinalBid> dashboardFinalBidsAllList = null;

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
				System.out.println("Closed branch for : " + branchClosedGroup.getParentBranch().getId()
						+ " and its group is : " + branchClosedGroup.getClosedGroupBranch().getId());
				closedgroupBranchesList.add(branchClosedGroup.getClosedGroupBranch());
				branchIdSet.add(branchClosedGroup.getClosedGroupBranch().getId());
			}

			dashboardFinalBidsAllList = dashboardFinalBidService
					.getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();
			for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsAllList) {
				System.out.println("Inside 1 : " + dashboardFinalBid.getBranchManagement().getId() + " and name is :"
						+ dashboardFinalBid.getBranchManagement().getBranchName() + " and size of branchParamStatus : "
						+ dashboardFinalBid.getBranchManagement().getBranchParameterStatuses().size());
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

				/*
				 * List<BranchParameterStatus> branchParameterStatusList =
				 * dashboardFinalBid.getBranchManagement()
				 * .getBranchParameterStatuses(); System.out.
				 * println("Branch parameter status bfeore  list size : " +
				 * branchParameterStatusList.size()); BranchManagement
				 * branchManagement2 = dashboardFinalBid.getBranchManagement();
				 * branchParameterStatusList =
				 * branchManagement2.getBranchParameterStatuses(); System.out.
				 * println("Branch parameter status after list size : " +
				 * branchParameterStatusList.size()); for (BranchParameterStatus
				 * branchParameterStatus : branchParameterStatusList) { //
				 * System.out.println( // "Branch parameer name : " +
				 * branchParameterStatus.getBranchParameter().getParameterName()
				 * ); if
				 * (branchParameterStatus.getBranchParameter().getParameterName(
				 * ) .equals("Total Cash Availability")) {
				 * System.out.println("Denomination for dn200 : " +
				 * branchParameterStatus.getDn2000()); } }
				 */
			}

		} else {
			System.out.println(" This is super admin : |||||||  user id : " + mySession.getUser().getId()
					+ " having branch : " + mySession.getUser().getBranchManagement().getBranchName());
		}

		model.addAttribute("userBranch", br);
		model.addAttribute("sameBranchReportingList", sameReportingBranches);
		model.addAttribute("otherNonreportingBranches", otherNonReportingBranches);

		List<DashboardFinalBid> finalBranchDashboardList = new ArrayList<DashboardFinalBid>();
		finalBranchDashboardList.addAll(sameReportingBranches);
		/**
		 * Now discard to add the other branches which are not in the group
		 */
		// finalBranchDashboardList.addAll(otherNonReportingBranches);

		model.addAttribute("finalBranchDashboardList", finalBranchDashboardList);

		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		model.addAttribute("placeCashRequest", placeCashRequest);

		model.addAttribute("fromBranchNotExcessCash", model2.get("fromBranchNotExcessCash"));

		return "userbiddashboard";
	}

	@RequestMapping(value = { "/userdashboard" }, method = RequestMethod.GET)
	public String home(ModelMap model, @ModelAttribute("modelMsg") ModelMap model2) {
		if (true) {
			return "demofile";
		}

		boolean flag = false;
		model.addAttribute("edit", false);
		List<HierarchyControl> hierarchyLists = null;
		List<BranchManagement> branchList = null;
		List<BranchManagement> sameReportingBranches = new ArrayList<BranchManagement>();
		List<BranchManagement> otherNonReportingBranches = new ArrayList<BranchManagement>();
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();
		BranchManagement branchManagement = new BranchManagement();

		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("branchManagement", br);
			model.addAttribute("initialFilter", true);
			branchList = new ArrayList<BranchManagement>();
			// branchList.add(br);
			// branchList = utility.getAllChildBranchList(br, branchList);
			branchList = (List<BranchManagement>) branchManagementService.getAllBranchGroupedAndNotgrouped();
			System.out.println("Now i am printing branches list.................");
			utility.print(branchList);
			System.out.println("Branch control for user branch is : " + br.getBranchControl());
			for (BranchManagement branchManagement2 : branchList) {
				System.out.println("branch name is :" + branchManagement2.getBranchName() + " and branch control is  "
						+ branchManagement2.getBranchControl());
				if (br.getBranchControl() == branchManagement2.getBranchControl()) {
					sameReportingBranches.add(branchManagement2);
				} else {
					otherNonReportingBranches.add(branchManagement2);
				}
			}

			System.out.println("Total branches list size : " + branchList.size());
			System.out.println("same reporting branches list size : " + sameReportingBranches.size());
			System.out.println("other Non-reporting branches list size : " + otherNonReportingBranches.size());

		} else {
			/*
			 * model.addAttribute("branch", branchManagement); hierarchyLists =
			 * hierarchyManagementService.getAllHierarchyControlList();
			 * utilityService.OrgToHierarchyControlDetails(model); branchList =
			 * (List<BranchManagement>)
			 * branchManagementService.getAllBranchGroupedAndNotgrouped();
			 * System.out.println("Userlist size is : " + branchList.size());
			 */
		}

		/**
		 * Cash required by current or not lets check out
		 */
		List<BranchParameterStatus> branchParameterStatusList = br.getBranchParameterStatuses().stream()
				.filter(p -> p.getBranchParameter().getParameterName().equals("branch_availability"))
				.collect(Collectors.toList());
		for (BranchParameterStatus branchParameterStatus : branchParameterStatusList) {
			Double fromBranchCashPosition = branchParameterStatus.getTotal();
			Double fromBranchMinThresholdValue = br.getMinThresholdAmount();
			Double fromBranchMaxThresholdValue = br.getMaxThresholdAmount();

			if (fromBranchCashPosition < fromBranchMinThresholdValue) {
				model.addAttribute("requireCash", true);
			} else {
				model.addAttribute("requireCash", false);
			}
		}
		model.addAttribute("fromBranchNotExcessCash", model2.get("fromBranchNotExcessCash"));

		model.addAttribute("userBranch", br);
		model.addAttribute("allBranchList", branchList);
		model.addAttribute("sameBranchReportingList", sameReportingBranches);
		model.addAttribute("otherNonreportingBranches", otherNonReportingBranches);

		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		model.addAttribute("placeCashRequest", placeCashRequest);

		return "userdashboard";
	}

	@RequestMapping(value = { "/branchmanagement-userdashboard-{id}-view" }, method = RequestMethod.GET)
	public String branchManagementUserDashboardView(@PathVariable String id, ModelMap model) {
		BranchManagement branchManagement = branchManagementService.findById(id);
		if (branchManagement != null) {
			model.addAttribute("branchManagement", branchManagement);
			model.addAttribute("edit", true);
			return "viewBranchManagamentUserDashboard";
		} else {
			return "redirect:/userdashboard";
		}
	}

	@RequestMapping(value = { "/saveBranchCashTransactionRequest-{id}" }, method = RequestMethod.POST)
	public String saveBranchCashTransactionRequestPost(PlaceCashRequest placeCashRequest, ModelMap model) {
		System.out.println(" placed amount is : " + placeCashRequest.getAmount());
		placeCashRequestService.saveBranchCashRequest(placeCashRequest);
		return "redirect:/userdashboard";
	}

	@RequestMapping(value = "/saveBranchCashTransactionRequest-{id}", method = RequestMethod.GET)
	public String saveBranchCashTransactionRequestGet(RedirectAttributes redirectAttributes, @PathVariable String id,
			ModelMap model) throws Exception {
		System.out.println("branch id requested for is : " + id);
		BranchManagement branchrequestedTo = branchManagementService.findById(id);
		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		placeCashRequest.setBranchManagementRequestedFrom(mySession.getUser().getBranchManagement());
		placeCashRequest.setBranchManagementRequestedTo(branchrequestedTo);

		double requestFromBranchCashLimit = 0;
		double requestFromBranchMinThreshold = 0;
		double requestFromBranchMaxThreshold = 0;
		double requestFromBranchCurrentCashPosition = 0;
		double needCashFrom = 0;

		double requestToBranchCashLimit = 0;
		double requestToBranchMinThreshold = 0;
		double requestToBranchMaxThreshold = 0;
		double requestToBranchCurrentCashPosition = 0;
		double needCashTo = 0;

		System.out.println("Request from branch cash limit : " + requestFromBranchCashLimit);

		requestFromBranchCashLimit = mySession.getUser().getBranchManagement().getBranchCashlimit();
		requestFromBranchMinThreshold = mySession.getUser().getBranchManagement().getMinThresholdAmount();
		requestFromBranchMaxThreshold = mySession.getUser().getBranchManagement().getMaxThresholdAmount();

		for (BranchParameterStatus branchParameterStatus : mySession.getUser().getBranchManagement()
				.getBranchParameterStatuses()) {
			if (branchParameterStatus.getBranchParameter().getParameterName().equals("branch_availability")) {
				System.out.println("Total amount means cash current position : " + branchParameterStatus.getTotal());
				// requestFromBranchCurrentCashPosition =
				// (Long.parseLong(branchParameterStatus.getTotal().toString()));
				requestFromBranchCurrentCashPosition = branchParameterStatus.getTotal();
				System.out.println("Total amount means cash current position after decimal to long: "
						+ requestFromBranchCurrentCashPosition);
			}
		}

		requestToBranchCashLimit = branchrequestedTo.getBranchCashlimit();
		requestToBranchMinThreshold = branchrequestedTo.getMinThresholdAmount();
		requestToBranchMaxThreshold = branchrequestedTo.getMaxThresholdAmount();

		for (BranchParameterStatus branchParameterStatus : branchrequestedTo.getBranchParameterStatuses()) {
			if (branchParameterStatus.getBranchParameter().getParameterName().equals("branch_availability")) {
				System.out.println("Total amount means cash current position : " + branchParameterStatus.getTotal());
				requestToBranchCurrentCashPosition = branchParameterStatus.getTotal();
				System.out.println("Total amount means cash current position after decimal to long: "
						+ requestToBranchCurrentCashPosition);
			}
		}

		if (requestToBranchCurrentCashPosition <= requestToBranchMinThreshold) {
			needCashTo = (requestToBranchMinThreshold - requestToBranchCurrentCashPosition);
			System.out.println("Inside for to if cash is below : " + needCashTo);
		}

		if (requestToBranchCurrentCashPosition >= requestToBranchMaxThreshold) {
			needCashTo = (requestToBranchCurrentCashPosition - requestToBranchMaxThreshold);
			System.out.println("Inside for to if cash is excess : " + needCashTo);
		}

		if (requestToBranchCurrentCashPosition <= requestToBranchMaxThreshold) {
			System.out.println(
					"fourth upper needCashFrom is not having excess cash " + needCashFrom + " : " + needCashTo);
			model.addAttribute("fromBranchNotExcessCash",
					messageSource.getMessage("requested.branch.not.having.excesscash", null, Locale.ENGLISH));
			redirectAttributes.addFlashAttribute("modelMsg", model);
			return "redirect:/userdashboard";
		}

		System.out.println(
				"requested from requestFromBranchCurrentCashPosition : " + requestFromBranchCurrentCashPosition);
		System.out.println("requested from requestFromBranchMinThreshold : " + requestFromBranchMinThreshold);
		System.out.println("requested from requestFromBranchMaxThreshold : " + requestFromBranchMaxThreshold);

		System.out.println("requested to requestToBranchCurrentCashPosition : " + requestToBranchCurrentCashPosition);
		System.out.println("requested to requestToBranchMinThreshold : " + requestToBranchMinThreshold);
		System.out.println("requested to requestToBranchMaxThreshold : " + requestToBranchMaxThreshold);

		if (requestFromBranchCurrentCashPosition <= requestFromBranchMinThreshold) {
			needCashFrom = (requestFromBranchMinThreshold - requestFromBranchCurrentCashPosition);
			placeCashRequest.setRequestType("Recieve");
			System.out.println("Inside for from if cash is below : " + needCashFrom);
		}

		if (requestFromBranchCurrentCashPosition >= requestFromBranchMaxThreshold) {
			needCashFrom = (requestFromBranchCurrentCashPosition - requestFromBranchMaxThreshold);
			placeCashRequest.setRequestType("Remit");
			System.out.println("Inside for from if cash is excess : " + needCashFrom);
		}

		if (needCashFrom <= needCashTo) {
			System.out.println("first nedeCashFrom is less : than needcashto : " + needCashFrom + " : " + needCashTo);
			placeCashRequest.setAmount(needCashFrom);
		}

		if (needCashFrom >= needCashTo) {
			System.out.println("second nedeCashFrom is less : than needcashto : " + needCashFrom + " : " + needCashTo);
			placeCashRequest.setAmount(needCashTo);
		}

		/*
		 * if (needCashFrom == needCashTo) {
		 * System.out.println("third nedeCashFrom is less : than needcashto : "
		 * + needCashFrom + " : " + needCashTo);
		 * placeCashRequest.setAmount(needCashTo); }
		 */

		placeCashRequest.setActualCashReuires(needCashFrom);

		System.out.println("need cash from is : " + needCashFrom);
		System.out.println("need cash to is : " + needCashTo);

		model.addAttribute("placeCashRequest", placeCashRequest);
		model.addAttribute("edit", false);

		if (needCashFrom == 0 || needCashTo == 0) {
			System.out.println("fourth nedeCashFrom is less : than needcashto : " + needCashFrom + " : " + needCashTo);
			model.addAttribute("fromBranchNotExcessCash",
					messageSource.getMessage("requested.branch.not.having.excesscash", null, Locale.ENGLISH));
			redirectAttributes.addFlashAttribute("modelMsg", model);
			return "redirect:/userdashboard";
		}
		return "userRequestCashForm";
	}
}
