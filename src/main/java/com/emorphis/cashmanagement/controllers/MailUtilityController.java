package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.DashboardFinalBidService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.MailSenderService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.MailUtility;
import com.emorphis.cashmanagement.util.Utility;

@Controller
public class MailUtilityController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	UtilityService utilityService;

	@Autowired
	Utility utility;

	@Autowired
	MailUtility mailUtility;

	@Autowired
	BranchManagementService branchManagementService;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	DashboardFinalBidService dashboardFinalBidService;
	
	@Autowired
	MailSenderService mailSenderService; 

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/belowcashmailtoexcesscashgroup" }, method = RequestMethod.GET)
	public String mailToAccessCashBranchesInClosedGroups(ModelMap model) {
		
		/*boolean flag1 = mailSenderService.mailToAllBranchesToUpdateApproveBidAmount();
		System.out.println("flag value after mailToAllBranchesToUpdateApproveBidAmount is : "+flag1); */
		   
		boolean flag = false;
		System.out.println("Inside mail utility to access level of branches .....");
		List<HierarchyControl> hierarchyLists = null;
		List<BranchManagement> branchList = null;
		List<DashboardFinalBid> dashboardFinalBidsList = null;

		List<BranchManagement> excessCashBranchesList = new ArrayList<BranchManagement>();
		List<BranchManagement> belowCashBranchesList = new ArrayList<BranchManagement>();
		List<BranchManagement> extraCashBranchesList = new ArrayList<BranchManagement>();

		// flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			dashboardFinalBidsList = dashboardFinalBidService.getAllBranchesListhavingExcessAndBelowCash();
			for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsList) {
				System.out.println("Inside 1 : " + dashboardFinalBid.getBranchManagement().getId() + " and name is :"
						+ dashboardFinalBid.getBranchManagement().getBranchName());
				if (dashboardFinalBid.getPosition().equals("excess")) {
					excessCashBranchesList.add(dashboardFinalBid.getBranchManagement());
				} else {
					if (dashboardFinalBid.getPosition().equals("below")) {
						belowCashBranchesList.add(dashboardFinalBid.getBranchManagement());
					} else {
						extraCashBranchesList.add(dashboardFinalBid.getBranchManagement());
						System.out.println(
								"Inside 1111 when the position of branch is something else for dashboard id : | "
										+ dashboardFinalBid.getId() + " and position : i.e | position is | "
										+ dashboardFinalBid.getPosition() + "  and id is | "
										+ dashboardFinalBid.getBranchManagement().getId() + " and name is :"
										+ dashboardFinalBid.getBranchManagement().getBranchName());
					}
				}
			}
			System.out.println("excess cash branches list are : " + excessCashBranchesList.size()
					+ " and below cash branches size is : " + belowCashBranchesList.size());
			System.out.println("Now i am printing total branches list excess cash branches");
			utility.print(excessCashBranchesList);
		} else {
			System.out.println("If user is super admin no mail utility for super admin..............");
		}

		/**
		 * Implementing the branch short position cash mailing to users having
		 * excess cash.
		 */

		if (excessCashBranchesList.size() > 0) {
			for (BranchManagement branchManagement : excessCashBranchesList) {
				List<BranchManagement> toMailBranchesHavingBelowCashList = new ArrayList<BranchManagement>();
				Set<BranchClosedGroup> closedGroupBranchesSet = branchManagement.getBranchClosedGroups();
				System.out.println("branch id : " + branchManagement.getId() + "  closed group branchset size is : "
						+ closedGroupBranchesSet.size());
				List<BranchManagement> closedgroupBranchesList = new ArrayList<BranchManagement>();
				for (BranchClosedGroup branchClosedGroup : closedGroupBranchesSet) {
					System.out.println("Closed branch for : " + branchClosedGroup.getParentBranch().getId()
							+ " and its group is : " + branchClosedGroup.getClosedGroupBranch().getId());
					closedgroupBranchesList.add(branchClosedGroup.getClosedGroupBranch());
				}
				List<BranchManagement> uniqueClosedGroupBranchHavingBelowCash = new ArrayList<>(belowCashBranchesList);
				uniqueClosedGroupBranchHavingBelowCash.retainAll(closedgroupBranchesList);

				System.out.println("retainall branches having low cash in closed group are : "
						+ uniqueClosedGroupBranchHavingBelowCash.size());
				uniqueClosedGroupBranchHavingBelowCash.forEach(System.out::println);
				toMailBranchesHavingBelowCashList = uniqueClosedGroupBranchHavingBelowCash;
				if (toMailBranchesHavingBelowCashList.size() > 0) {
					boolean mailFlag = mailUtility.sendMailToExcessCashBranchOfBelowCashBranches(
							toMailBranchesHavingBelowCashList, branchManagement);
				} else {
					System.out
							.println("no below cash holders in this branch group exist : " + branchManagement.getId());
				}
			}
		}

		return "redirect:/home";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/mailutilityaccesscash" }, method = RequestMethod.GET)
	public String mailToAccessCashBranches(ModelMap model) {
		boolean flag = false;
		System.out.println("Inside mail utility to access level of branches .....");
		List<HierarchyControl> hierarchyLists = null;
		List<BranchManagement> branchList = null;
		List<BranchManagement> excessCashBranchesList = new ArrayList<BranchManagement>();
		// flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			branchList = (List<BranchManagement>) branchManagementService.getAllBranchesListForMailUtility();
			for (BranchManagement branchManagement : branchList) {
				System.out.println("Inside 1 : " + branchManagement.getBranchParameterStatuses().size()
						+ " and branch name : " + branchManagement.getBranchName());
				List<BranchParameterStatus> filteredBranchParameterStatusList = branchManagement
						.getBranchParameterStatuses().stream()
						.filter(p -> p.getBranchParameter().getParameterName().equals("branch_availability"))
						.collect(Collectors.toList());

				System.out.println("Inside 2 : " + filteredBranchParameterStatusList.size());
				BranchParameterStatus branchParameterStatus = null;
				if (null != filteredBranchParameterStatusList) {
					for (BranchParameterStatus branchParameterStatus2 : filteredBranchParameterStatusList) {
						System.out.println(
								"Inside 3 : " + branchParameterStatus2.getBranchParameter().getParameterName());
						System.out.println("Inside 4 : " + branchParameterStatus2.getTotal());
						branchParameterStatus = branchParameterStatus2;
					}
				}
				double branchCurrentCashPosition = 0;
				double branchMaxThreshold = branchManagement.getMaxThresholdAmount();
				if (branchParameterStatus != null) {
					if (0.0 != branchParameterStatus.getTotal()) {
						branchCurrentCashPosition = branchParameterStatus.getTotal();
					}
				}

				System.out.println("Inside 5 : " + branchCurrentCashPosition);
				System.out.println("Inside 6 : " + branchMaxThreshold);

				if (branchCurrentCashPosition > branchMaxThreshold) {
					System.out.println("Inside 7 : " + branchMaxThreshold);
					excessCashBranchesList.add(branchManagement);
				}
				System.out.println("excess cash branches list are : " + excessCashBranchesList.size());
			}

			System.out.println("Now i am printing total branches list................." + branchList.size());
			utility.print(branchList);
		} else {
			System.out.println("If user is super admin no mail utility for super admin..............");
		}

		/**
		 * Implementing the branch short position cash mailing to users having
		 * access cash.
		 */

		if (excessCashBranchesList.size() > 0) {
			System.out.println("excess cash branch list size final : " + excessCashBranchesList.size());
			for (BranchManagement branchManagement : excessCashBranchesList) {
				List<BranchManagement> toMailBranchesHavingBelowCashList = new ArrayList<BranchManagement>();
				String branchControl = branchManagement.getBranchControl();

				for (BranchManagement branchManagementControls : branchList) {
					List<BranchManagement> belowcashLimitsBranches = new ArrayList<BranchManagement>();

					if (branchControl == branchManagementControls.getBranchControl()) {
						List<BranchParameterStatus> filteredBranchParameterStatusList = branchManagementControls
								.getBranchParameterStatuses().stream()
								.filter(p -> p.getBranchParameter().getParameterName().equals("branch_availability"))
								.collect(Collectors.toList());
						System.out.println("branch parameter list : " + filteredBranchParameterStatusList.size());
						BranchParameterStatus branchParameterStatus = null;
						if (null != filteredBranchParameterStatusList) {
							for (BranchParameterStatus branchParameterStatus2 : filteredBranchParameterStatusList) {
								System.out.println("Inside 10 : "
										+ branchParameterStatus2.getBranchParameter().getParameterName());
								System.out.println("Inside 11 : " + branchParameterStatus2.getTotal());
								branchParameterStatus = branchParameterStatus2;
							}
						}
						if (branchManagementControls.getMaxThresholdAmount() > branchParameterStatus.getTotal()) {
							toMailBranchesHavingBelowCashList.add(branchManagementControls);
						}
						System.out.println("Excess cash branches are sending mail to them : " + branchManagement.getId()
								+ " and branch name is : " + branchManagement.getBranchName() + " and branch email : "
								+ branchManagement.getBranchEmail() + " and toMailUnderExcesscashBranch size is : "
								+ toMailBranchesHavingBelowCashList.size());

					}

				}
				// now mailing this branches to a excess branch
				boolean mailFlag = mailUtility.sendMailToBranchesHavingBelowCash(toMailBranchesHavingBelowCashList,
						branchManagement);

			}
		}

		return "redirect:/home";
	}
	
	
	
	
}
