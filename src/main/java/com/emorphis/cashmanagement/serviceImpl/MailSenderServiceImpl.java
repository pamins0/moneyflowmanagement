package com.emorphis.cashmanagement.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.DashboardFinalBidService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.MailSenderService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.MailUtility;
import com.emorphis.cashmanagement.util.NotificationAndMailUtility;
import com.emorphis.cashmanagement.util.Utility;

@Service
@Transactional
public class MailSenderServiceImpl implements MailSenderService {

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
	NotificationAndMailUtility notificationAndMailUtility;

	@Override
	public boolean mailToAllBranchesToUpdateApproveBidAmount() {
		boolean flag = false;
		System.out.println("Inside mail utility to access level of branches .....");

		List<DashboardFinalBid> dashboardFinalBidsList = null;

		List<BranchManagement> allBranchesList = new ArrayList<BranchManagement>();

		// flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			dashboardFinalBidsList = dashboardFinalBidService.getAllBranchesListhavingExcessAndBelowCash();
			for (DashboardFinalBid dashboardFinalBid : dashboardFinalBidsList) {

				System.out.println("Inside &&&&&&&& mailToAllBranchesToUpdateApproveBidAmount branch id : " + dashboardFinalBid.getBranchManagement().getId() + " and name is :"
						+ dashboardFinalBid.getBranchManagement().getBranchName());				
				BranchManagement branchManagement = dashboardFinalBid.getBranchManagement();
				
				System.out.println("User for particular branch fetched : "+branchManagement.getBranchName()); 
				/*for(User user : dashboardFinalBid.getBranchManagement().getUsers()){
					System.out.println("User name is : "+user.getFirstName()+" branch id : "+user.getBranchManagement().getBranchName());
				}*/
        		allBranchesList.add(branchManagement); 
			} 
			System.out.println("all dashboard cash branches list are : " + allBranchesList.size());
			// System.out.println("Now i am printing total branches list excess
			// cash branches before ");
			// utility.print(allBranchesList); 
		} else {
			System.out.println("If user is super admin no mail utility for super adminss mailToAllBranchesToUpdateApproveBidAmount..............");
		}	
		
		flag = notificationAndMailUtility.sendMailToAllApproverofBranches(allBranchesList);
		
		return flag;
	}

	@Override
	public boolean mailToAccessCashBranchesInClosedGroups() {
		boolean flag = false;
		System.out.println("Inside mail utility to access level of branches .....");

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
					+ " and below cash branches size is : " + belowCashBranchesList.size()
					+ " and level cash branches size is : " + extraCashBranchesList.size());
			System.out.println("Now i am printing total branches list excess cash branches before ");
			// utility.print(excessCashBranchesList);
		} else {
			System.out.println("If user is super admin no mail utility for super adminss..............");
		}

		/**
		 * Implementing the branch short position cash mailing to users having
		 * excess cash.
		 */

		if (excessCashBranchesList.size() > 0) {
			System.out.println("Now i am printing total branches list excess cash branches again");
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
					if (mailFlag) {
						flag = true;
					}
				} else {
					System.out
							.println("no below cash holders in this branch group exist : " + branchManagement.getId());
				}
			}
		}

		return flag;
	}

}
