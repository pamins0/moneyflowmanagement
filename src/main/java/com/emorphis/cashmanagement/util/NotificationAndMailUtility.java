package com.emorphis.cashmanagement.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.UserManagementService;

@Component
public class NotificationAndMailUtility {
	
	private static final Logger log = LoggerFactory.getLogger(NotificationAndMailUtility.class);

	@Autowired
	BranchManagementService branchManagementService;

	@Autowired
	UserManagementService userManagementService;

	@Autowired
	MailUtility mailUtility;

	@Autowired
	MySession mySession;
	
	@Autowired
	Environment environment;

	public boolean sendMailToApprover(String branchId, String toBranchId) {
		boolean flag = false; 
		if (!"0".equals(branchId)) {
			BranchManagement branchManagement = branchManagementService.findById(branchId);
			BranchManagement branchManagementTo = branchManagementService.findById(toBranchId);
			List<User> usersList = branchManagement.getUsers();
			List<User> userApproverList = userManagementService.getAllApprovedUsersList(usersList);
			flag = mailUtility.sendMailToApprovers(userApproverList);
			if (flag) {
				log.info("mail sent successfully to user of the branch who are approvers : ");
			} else {
				log.info("mail not sent or failed to user of the branch who are approvers : ");
			}
			List<String> acceptedBranchList = new ArrayList<>();
			acceptedBranchList.add(branchManagementTo.getBranchEmail());
			flag = mailUtility.sendMailToAcceptedBranch(acceptedBranchList, branchManagement);
		}
		return flag;
	}

	public boolean sendMailToAllApproverofBranches(List<BranchManagement> allBranchesList) {
		boolean flag = false;
		try{
		List<User> userApproversList = new ArrayList<User>();

		if (allBranchesList != null) {
			for (BranchManagement branchManagement : allBranchesList) {
				/*System.out.println("branch managemet in mailutiliy is : " + branchManagement.getBranchName());				
				System.out.println("user list size : "+branchManagement.getUsers().size()); */
				userApproversList.addAll(userManagementService.getAllApprovedUsersList(branchManagement.getUsers()));				
			}
						
			String hostUrl = environment.getProperty("host.url");
			System.out.println("Hosted url is : "+hostUrl); 
			flag = mailUtility.sendMailToAllBranchApprovers(userApproversList,
					"Please update and approve the request for operation",
					hostUrl+"/updateandapproverequestfordashboard");
		}
		}catch (Exception e) {
			System.out.println("Exception in notification mail utility : "+e); 
			e.printStackTrace();
		}

		return flag;
	}

	public boolean sendMailToApproverAndBidPlacedByBranches(String placedRequestId) {
		boolean flag = false;
		if (!"0".equals(placedRequestId)) {
			flag = mailUtility.sendMailToApproverAndBidPlacedByBranches(placedRequestId);
		}

		return flag;
	}

	public boolean senMailToAllApproversIncludedInSwapping(BranchManagement branchManagementRequestedFrom,
			BranchManagement branchManagementRequestedTo, BranchManagement branchManagement) {
		boolean flag = false;
		if (null != branchManagementRequestedFrom.getId() && null != branchManagementRequestedTo.getId()) {
			BranchManagement branchManagementFrom = branchManagementService
					.findById(branchManagementRequestedFrom.getId());
			BranchManagement branchManagementTo = branchManagementService.findById(branchManagementRequestedTo.getId());

			List<User> usersListFrom = branchManagementFrom.getUsers();
			List<User> usersListTo = branchManagementTo.getUsers();

			List<User> userApproverListFrom = userManagementService.getAllApprovedUsersList(usersListFrom);
			List<User> userApproverListTo = userManagementService.getAllApprovedUsersList(usersListTo);

			List<User> userFinalApproverList = new ArrayList<User>();
			userFinalApproverList.addAll(userApproverListFrom);
			userFinalApproverList.addAll(userApproverListTo);

			List<String> branchMailHierarchyWiseList = new ArrayList<String>();

			int hierarchyLevel = branchManagement.getHierarchyControl().getHierarchyLevel();

			String branchControlFrom = branchManagementFrom.getBranchControl();
			boolean submittedFrom = true;
			while (submittedFrom) {
				BranchManagement branchManagementControl = branchManagementService.findById(branchControlFrom);
				if (branchManagementControl.getHierarchyControl().getHierarchyLevel() == hierarchyLevel) {
					branchMailHierarchyWiseList.add(branchManagementControl.getBranchEmail());
					submittedFrom = false;
				} else {
					branchMailHierarchyWiseList.add(branchManagementControl.getBranchEmail());
					branchControlFrom = branchManagementControl.getBranchControl();
				}
			}

			String branchControlTo = branchManagementTo.getBranchControl();
			boolean submittedTo = true;
			while (submittedTo) {
				BranchManagement branchManagementControl = branchManagementService.findById(branchControlTo);
				if (branchManagementControl.getHierarchyControl().getHierarchyLevel() == hierarchyLevel) {
					branchMailHierarchyWiseList.add(branchManagementControl.getBranchEmail());
					submittedTo = false;
				} else {
					branchMailHierarchyWiseList.add(branchManagementControl.getBranchEmail());
					branchControlTo = branchManagementControl.getBranchControl();
				}
			}

			branchMailHierarchyWiseList.add(mySession.getUser().getEmail());

			flag = mailUtility.sendMailToApproversForSwapping(userFinalApproverList, branchMailHierarchyWiseList,
					branchManagementFrom.getBranchName(), branchManagementTo.getBranchName());
			if (flag) {
				System.out.println("mail sent successfully to user of the branch who are approvers : ");
			} else {
				System.out.println("mail not sent or failed to user of the branch who are approvers : ");
			}
		}
		return flag;
	}

	public boolean sendMailToApproverForEODEntryApproval() {
		boolean flag = false;
		List<User> userApproverListForEODBranch = userManagementService.getAllApprovedUsersList(mySession.getUser().getBranchManagement().getUsers());
		flag = mailUtility.sendMailToApproversForEODFormApproval(userApproverListForEODBranch);		
		
		return flag;
	}

	public boolean sendMailToBrancheForEodApproval(DashboardFinalBid dashboardFinalBid) {
		boolean flag = false;		
		List<User> userApproverListForEODBranch = userManagementService.getAllApproversAndMakersUsersList(mySession.getUser().getBranchManagement().getUsers());
		flag = mailUtility.sendMailToBrancheForEodApproval(userApproverListForEODBranch,dashboardFinalBid);
		return flag;
	}

	public boolean sendMailToBrancheForEodCancel(DashboardFinalBid dashboardFinalBid) {
		boolean flag = false;		
		List<User> userApproverListForEODBranch = userManagementService.getAllApproversAndMakersUsersList(mySession.getUser().getBranchManagement().getUsers());
		flag = mailUtility.sendMailToBrancheForEodCancel(userApproverListForEODBranch,dashboardFinalBid);
		return flag;
	}

}
