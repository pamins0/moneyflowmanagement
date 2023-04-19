package com.emorphis.cashmanagement.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameter;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.service.BranchClosedGroupService;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.BranchParameterService;
import com.emorphis.cashmanagement.service.BranchParameterStatusService;
import com.emorphis.cashmanagement.service.DashboardFinalBidService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.NotificationAndMailUtility;
import com.emorphis.cashmanagement.util.Utility;
import com.emorphis.cashmanagement.validations.BranchValidator;

@Controller
public class BranchParameterStatusController {

	private static final Logger log = LoggerFactory.getLogger(BranchParameterStatusController.class);

	@Autowired
	BranchManagementService branchManagementService;

	@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	OrgManagementService orgManagementService;

	@Autowired
	UtilityService utilityService;

	@Autowired
	MySession mySession;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	Utility utility;

	@Autowired
	BranchValidator branchValidator;

	@Autowired
	BranchClosedGroupService branchClosedGroupService;

	@Autowired
	BranchParameterService branchParameterService;

	@Autowired
	BranchParameterStatusService branchParameterStatusService;

	@Autowired
	NotificationAndMailUtility notificationAndMailUtility;

	@Autowired
	DashboardFinalBidService dashboardFinalBidService;

	@RequestMapping(value = { "/savebrancheodposition" }, method = RequestMethod.GET)
	public String saveBranchEODPositionGet(BranchManagement branchManagement, ModelMap model) {
		System.out.println("Inside get method of saveBranchEODPositionGet : " + branchManagement);
		return "redirect:/branchmanagement";
	}

	@PreAuthorize("hasAnyRole('can_be_maker')")
	@RequestMapping(value = { "/savebrancheodposition" }, method = RequestMethod.POST)
	public String saveBranchEODPosition(@Valid BranchManagement branchManagement, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		if (null != branchManagement
				&& branchManagement.getId().equals(mySession.getUser().getBranchManagement().getId())) {
			System.out.println("Branch id in redirecting from branchEodPosition to saveBranchEODPosition : "
					+ branchManagement.getId());
			BranchManagement branchManagement2 = branchManagementService.findById(branchManagement.getId());
			System.out
					.println("branch parameter status list : " + branchManagement2.getBranchParameterStatuses().size());
			int i = 0;
			/**
			 * Matching the current date with the existing eod entries if its
			 * exist for today then give him error for eod already done for
			 * today
			 */
			for (BranchParameterStatus branchParameterStatus : branchManagement2.getBranchParameterStatuses()) {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				Date modifiedtimeoriginal = branchParameterStatus.getModifiedTime();
				String currentDate = formatter.format(new Date());

				String modifiedTime = formatter.format(modifiedtimeoriginal);

				/*
				 * System.out.println("Modified time original : " +
				 * modifiedtimeoriginal); System.out.println("Modified time : "
				 * + modifiedTime);
				 */
				System.out.println("today date : | : " + currentDate + " for branch id : " + branchManagement2.getId());

				if (currentDate.equals(modifiedTime)) {
					i += 1;
				}
			}
			System.out.println("branchParameterStatus list for particular branch is : "
					+ branchManagement2.getBranchParameterStatuses().size() + " and i value is : | : " + i);
			if (i >= 1) {
				model.addAttribute("eodalreadydone",
						messageSource.getMessage("eod.entry.already.done", null, Locale.ENGLISH));
				redirectAttributes.addFlashAttribute("modelMsg", model);
				return "redirect:/branchmanagement";
			}

			// List<BranchParameterStatus> branchParameterStatus =
			// branchManagement2.getBranchParameterStatuses().stream().filter(p->
			// (new
			// SimpleDateFormat("yyyy-MM-dd").format(getModifiedTime())).collect(Collectors.toList());
		} else {
			String urlreferer = branchManagement.getUrlReferer();
			if (urlreferer == null) {
				return "redirect:/branchmanagement";
			}
			return "redirect:" + urlreferer;
		}

		BranchManagement branchManagement1 = branchManagementService.findById(branchManagement.getId());
		if (null != branchManagement1) {

			model.addAttribute("edit", false);

			List<BranchParameter> branchParameterStatusFinalParamList = new ArrayList<BranchParameter>();

			List<BranchParameter> branchParameterStatusAddParamList = new ArrayList<BranchParameter>();
			List<BranchParameter> branchParameterStatusSubParamList = new ArrayList<BranchParameter>();
			List<BranchParameter> branchParameterStatusTotalParamList = new ArrayList<BranchParameter>();
			List<BranchParameter> branchParameterStatusOtherParamList = new ArrayList<BranchParameter>();

			List<BranchParameter> branchParametersList = utility.getAllBranchParameterList();
			System.out.println("Branch parameter list is : " + branchParametersList.size());
			// branchParametersList.forEach(p->System.out.println(p.getParameterName()));
			for (BranchParameter branchParameter : branchParametersList) {
				if (branchParameter.getParameterDetails().equals("add")) {
					branchParameterStatusAddParamList.add(branchParameter);
				} else if (branchParameter.getParameterDetails().equals("sub")) {
					branchParameterStatusSubParamList.add(branchParameter);
				} else if (branchParameter.getParameterDetails().equals("total")) {
					branchParameterStatusSubParamList.add(branchParameter);
				} else {
					branchParameterStatusOtherParamList.add(branchParameter);
				}
			}

			branchParameterStatusFinalParamList.addAll(branchParameterStatusAddParamList);
			branchParameterStatusFinalParamList.addAll(branchParameterStatusSubParamList);
			branchParameterStatusFinalParamList.addAll(branchParameterStatusTotalParamList);
			branchParameterStatusFinalParamList.addAll(branchParameterStatusOtherParamList);

			BranchParameterStatus branchParameterStatus = new BranchParameterStatus();
			branchParameterStatus.setBranchManagement(branchManagement1);

			model.addAttribute("branchParametersList", branchParameterStatusFinalParamList);
			model.addAttribute("branchParameterStatus", branchParameterStatus);
		}

		return "brancheod";
		// return "redirect:/usermanagement";
	}

	@PreAuthorize("hasAnyRole('can_be_maker')")
	@RequestMapping(value = { "/savebrancheodpositiondb" }, method = RequestMethod.POST)
	public String saveBranchEODPositionInDB(@Valid BranchParameterStatus branchParameterStatus, BindingResult result,
			ModelMap model, RedirectAttributes redirectAttributes) {
		String urlreferer = branchParameterStatus.getUrlReferer();
		System.out.println("url referer : " + urlreferer);
		System.out.println(
				"BranchParameterStatus list size is : " + branchParameterStatus.getBranchParameterStatusList().size());
		for (BranchParameterStatus branchParameterStatus2 : branchParameterStatus.getBranchParameterStatusList()) {
			System.out.println("branchParameter id : " + branchParameterStatus2.getBranchParameter().getId());
			System.out.println("branchParameterStatus branchParameter Name : "
					+ branchParameterStatus2.getBranchParameter().getParameterName());
			System.out.println("branchParameterStatus total : " + branchParameterStatus2.getTotal());
			System.out.println("branchParameterStatus DN_2000 : " + branchParameterStatus2.getDn2000());
			System.out.println("branchParameterStatus DN_50 : " + branchParameterStatus2.getDn50());
		}

		// branchManagementService.saveBranchEodPosition(branchParameterStatus);
		branchParameterStatusService.saveBranchEodPosition(branchParameterStatus);
		System.out.println("Save btn name is : " + branchParameterStatus.getSaveBtn());
		if (branchParameterStatus.getSaveBtn().equals("Save")) {
			boolean flag = utility.isAllowed("can_be_approver");
			boolean mailSend = notificationAndMailUtility.sendMailToApproverForEODEntryApproval();
			if (flag == true) {
				redirectAttributes.addFlashAttribute("branchParamStatus", branchParameterStatus);
				return "redirect:/viewEditBrancheodposition";
			} else {
				// boolean mailSend =
				// notificationAndMailUtility.sendMailToApproverForEODEntryApproval();
			}
		} else if (branchParameterStatus.getSaveBtn().equals("UpdateAndApprove")) {
			System.out.println("inside update and approve if clicked");
			/**
			 * This we remove because currently we dont need automatic dashboard
			 * processing......
			 */
			// return "redirect:/updateandapproverequestfordashboardentries";
			return "redirect:/home";
		}

		return "redirect:" + urlreferer;
	}

	@RequestMapping(value = { "/viewEditBrancheodposition" }, method = RequestMethod.GET)
	public String viewEditBranchEODPositionInDB(BranchManagement branchManagement, BindingResult result,
			ModelMap model, @ModelAttribute("branchParamStatus") BranchParameterStatus branchParameterStatus) {
		System.out.println("IN viewEOD form position : " + branchManagement);
		BranchManagement branchManagement1 = new BranchManagement();
		if (branchManagement != null) {
			branchManagement1.setId(branchManagement.getId());
		} else {
			branchManagement1 = mySession.getUser().getBranchManagement();
		}
		List<BranchParameter> branchParameterStatusFinalParamList = new ArrayList<BranchParameter>();

		List<BranchParameter> branchParameterStatusAddParamList = new ArrayList<BranchParameter>();
		List<BranchParameter> branchParameterStatusSubParamList = new ArrayList<BranchParameter>();
		List<BranchParameter> branchParameterStatusTotalParamList = new ArrayList<BranchParameter>();
		List<BranchParameter> branchParameterStatusOtherParamList = new ArrayList<BranchParameter>();

		List<BranchParameter> branchParametersList = utility.getAllBranchParameterList();
		System.out.println("Branch parameter list in edit is : " + branchParametersList.size());
		// branchParametersList.forEach(p->System.out.println(p.getParameterName()));
		for (BranchParameter branchParameter : branchParametersList) {
			if (branchParameter.getParameterDetails().equals("add")) {
				branchParameterStatusAddParamList.add(branchParameter);
			} else if (branchParameter.getParameterDetails().equals("sub")) {
				branchParameterStatusSubParamList.add(branchParameter);
			} else if (branchParameter.getParameterDetails().equals("total")) {
				branchParameterStatusSubParamList.add(branchParameter);
			} else {
				branchParameterStatusOtherParamList.add(branchParameter);
			}
		}

		branchParameterStatusFinalParamList.addAll(branchParameterStatusAddParamList);
		branchParameterStatusFinalParamList.addAll(branchParameterStatusSubParamList);
		branchParameterStatusFinalParamList.addAll(branchParameterStatusTotalParamList);
		branchParameterStatusFinalParamList.addAll(branchParameterStatusOtherParamList);

		/*
		 * BranchParameterStatus branchParameterStatus = new
		 * BranchParameterStatus();
		 * branchParameterStatus.setBranchManagement(branchManagement1);
		 */
		if (branchParameterStatus != null) {
			System.out.println("inside if reload the page........1 ");
			if (branchParameterStatus.getBranchParameterStatusList() != null) {
				System.out.println("inside if reload the page........2 ");
				model.addAttribute("edit", true);
				model.addAttribute("approveFlag", false);
				model.addAttribute("branchParameterStatus", branchParameterStatus);
			} else {
				System.out.println("inside if reload the page........");
				return "redirect:/branchmanagement";
			}
		} else {
			System.out.println("when branchparemeter status is null........");
			BranchParameterStatus branchParameterStatus2 = new BranchParameterStatus();
			branchParameterStatus2.setBranchManagement(branchManagement1);
			model.addAttribute("branchParameterStatus", branchParameterStatus2);
			model.addAttribute("approveFlag", true);
		}
		for (BranchParameterStatus branchParameterStatus2 : branchParameterStatus.getBranchParameterStatusList()) {
			System.out.println("Branch param : " + branchParameterStatus2.getBranchParameter().getParameterName()
					+ " -- total amt : " + branchParameterStatus2.getTotal() + " dn2000 : "
					+ branchParameterStatus2.getDn2000());
		}
		model.addAttribute("branchParametersList", branchParameterStatusFinalParamList);
		System.out.println("inside if reload the page........3 ");
		return "brancheod";
	}

	@PreAuthorize("hasAnyRole('can_be_approver')")
	@RequestMapping(value = { "/updateandapproverequestfordashboardentries" }, method = RequestMethod.GET)
	public String upadteandapproverequestfordashboardGet(ModelMap model) {
		boolean flag = false;
		flag = utility.isAllowed("can_be_approver");
		if (flag) {
			BranchManagement branchManagement = mySession.getUser().getBranchManagement();
			DashboardFinalBid existingDashboardFinalBid = dashboardFinalBidService
					.findDashboardByBranchId(branchManagement.getId());
			if (existingDashboardFinalBid != null) {
				return "redirect:/dashboard";
			}

			BranchParameterStatus branchParameterStatus = branchParameterStatusService
					.findEODTotalDetailsByBranchId(branchManagement.getId());
			if (branchParameterStatus == null) {
				return "redirect:/dashboard";
			}
			/**
			 * Filtering out total amy from non issuable notes......
			 */
			BranchParameterStatus branchParameterStatusModified = new BranchParameterStatus();
			List<BranchParameterStatus> branchParamStatusFullList = branchManagement.getBranchParameterStatuses();

			for (BranchParameterStatus branchParameterStatus2 : branchParamStatusFullList) {
				log.info("branch param : " + branchParameterStatus2.getBranchParameter().getParameterName()
						+ " and total value : " + branchParameterStatus2.getTotal() + " 2000n | "
						+ branchParameterStatus2.getDn2000() + " 500n | " + branchParameterStatus2.getDn500()
						+ " 10c | " + branchParameterStatus2.getDc10());
				if (branchParameterStatus2.getBranchParameter().getParameterAbbreviation()
						.equals("Non issuable notes- (All Denominations)")) {
					log.info("inside non issuable notes....." + branchParameterStatus2.getTotal());
					if (branchParameterStatus2.getTotal() != null) {
						branchParameterStatusModified
								.setTotal(branchParameterStatus.getTotal() - branchParameterStatus2.getTotal());
					} else {
						branchParameterStatusModified.setTotal(branchParameterStatus.getTotal());
					}
				}
			}

			Double minimumAmt = branchManagement.getMinThresholdAmount();
			Double maximumAmt = branchManagement.getMaxThresholdAmount();
			Double branchCashLimit = branchManagement.getBranchCashlimit();
			Double eodTotal = branchParameterStatusModified.getTotal();
			Double totalrequestedAmt = 0.0;
			String eodPosition = "level";
			System.out.println("min amt - " + minimumAmt + " max amt - " + maximumAmt + " cashlimit amt - "
					+ branchCashLimit + " branch eod total - " + eodTotal);
			if (eodTotal < minimumAmt) {
				totalrequestedAmt = branchCashLimit - eodTotal;
				eodPosition = "below";
			} else if (eodTotal > maximumAmt) {
				totalrequestedAmt = eodTotal - branchCashLimit;
				eodPosition = "excess";
			} else {
				totalrequestedAmt = eodTotal;
				eodPosition = "level";
			}
			DashboardFinalBid dashboardFinalBid = new DashboardFinalBid();
			dashboardFinalBid.setBranchManagement(branchManagement);
			dashboardFinalBid.setPosition(eodPosition);
			dashboardFinalBid.setTotal(totalrequestedAmt);
			dashboardFinalBid.setEodTotal(eodTotal);

			if (null != dashboardFinalBid) {
				log.info("dashboard final bid id is : " + dashboardFinalBid.getId() + " and amount : "
						+ dashboardFinalBid.getTotal());

				if (dashboardFinalBid.getPosition().equals("below")) {
					dashboardFinalBid.setRequestType((byte) 1);
				} else if (dashboardFinalBid.getPosition().equals("excess")) {
					dashboardFinalBid.setRequestType((byte) 2);
				} else if (dashboardFinalBid.getPosition().equals("level")) {
					dashboardFinalBid.setRequestType((byte) 3);
				} else {
					dashboardFinalBid.setRequestType((byte) -1);
				}

				model.addAttribute("dashboardFinalBid", dashboardFinalBid);
				model.addAttribute("edit", true);

				// return "approverequestfordashboard";
				return "redirect:/home";

			} else {
				return "redirect:/home";
			}
		} else {
			return "redirect:/home";
		}
	}

	@PreAuthorize("hasAnyRole('can_be_approver')")
	@RequestMapping(value = { "/updateandapproverequestfordashboardentries" }, method = RequestMethod.POST)
	public String upadteandapproverequestfordashboardPost(ModelMap model, DashboardFinalBid dashboardFinalBid) {
		System.out.println("dashboard final bid request type : " + dashboardFinalBid.getRequestType());
		try {
			if (dashboardFinalBid.getRequestType() == 1) {
				dashboardFinalBid.setPosition("below");
			} else if (dashboardFinalBid.getRequestType() == 2) {
				dashboardFinalBid.setPosition("excess");
			} else if (dashboardFinalBid.getRequestType() == 3) {
				dashboardFinalBid.setPosition("level");
			}
			System.out
					.println("dashboard final bid branch id is : " + mySession.getUser().getBranchManagement().getId());
			System.out.println("button value is : " + dashboardFinalBid.getSaveBtn());

			BranchManagement branchManagement = mySession.getUser().getBranchManagement();
			BranchParameterStatus branchParameterStatus = branchParameterStatusService
					.findEODTotalDetailsByBranchId(branchManagement.getId());
			Double minimumAmt = branchManagement.getMinThresholdAmount();
			Double maximumAmt = branchManagement.getMaxThresholdAmount();
			Double branchCashLimit = branchManagement.getBranchCashlimit();
			Double eodTotal = branchParameterStatus.getTotal();
			Double totalrequestedAmt = 0.0;
			String eodPosition = "level";
			System.out.println("min amt - " + minimumAmt + " max amt - " + maximumAmt + " cashlimit amt - "
					+ branchCashLimit + " branch eod total - " + eodTotal);
			if (eodTotal < minimumAmt) {
				totalrequestedAmt = branchCashLimit - eodTotal;
				eodPosition = "below";
			} else if (eodTotal > maximumAmt) {
				totalrequestedAmt = eodTotal - branchCashLimit;
				eodPosition = "excess";
			} else {
				totalrequestedAmt = eodTotal;
				eodPosition = "level";
			}
			dashboardFinalBid.setEodTotal(eodTotal);
			log.info("After request of dashboard approval form requested eod calculated total amt is : "
					+ totalrequestedAmt + " and changed or form submitted total amt is : "
					+ dashboardFinalBid.getTotal());
			if (totalrequestedAmt != dashboardFinalBid.getTotal()) {
				dashboardFinalBid.setModified_value("YES");
			} else {
				dashboardFinalBid.setModified_value("NO");
			}
			if (dashboardFinalBid.getSaveBtn().equals("Save")) {
				boolean flag = branchParameterStatusService
						.updateBranchParameterStatusAndProcessForDashboard(dashboardFinalBid, "APPROVED");
				if (flag) {
					boolean flagMail = notificationAndMailUtility.sendMailToBrancheForEodApproval(dashboardFinalBid);
					log.info("Mail sent for eod is approved to branch mail and all the approvers value is " + flagMail
							+ " for branch id " + branchManagement.getId());
				}
			} else if (dashboardFinalBid.getSaveBtn().equals("Cancel")) {
				boolean flag = branchParameterStatusService
						.updateBranchParameterStatusAndProcessForDashboard(dashboardFinalBid, "CANCEL");
				boolean flagMail = notificationAndMailUtility.sendMailToBrancheForEodCancel(dashboardFinalBid);
				log.info("Mail sent for eod is canceled to branch mail and all the approvers value is " + flagMail
						+ " for branch id " + branchManagement.getId());
			}
		} catch (Exception e) {
			log.error("error generated at upadteandapproverequestfordashboardPost due to : " + e);
			return "redirect:/home";
		}
		// dashboardFinalBidService.updateById(dashboardFinalBid);

		return "redirect:/userbiddashboard";
	}

	@PreAuthorize("hasAnyRole('can_be_maker')")
	@RequestMapping(value = { "/editbrancheodposition" }, method = RequestMethod.POST)
	public String editBranchEODPositionPost(@Valid BranchManagement branchManagement, BindingResult result,
			ModelMap model, RedirectAttributes redirectAttributes) {
		BranchManagement branchManagement2 = null;
		if (null != branchManagement
				&& branchManagement.getId().equals(mySession.getUser().getBranchManagement().getId())) {
			System.out.println("Branch id in redirecting from branchEodPosition to editBranchEODPositionPost : "
					+ branchManagement.getId());
			branchManagement2 = branchManagementService.findById(branchManagement.getId());
			System.out
					.println("branch parameter status list : " + branchManagement2.getBranchParameterStatuses().size());
			int i = 0;
			/**
			 * Matching the current status as CANCEL with the existing eod
			 * entries if its exist for today then give him error for eod
			 * already done for today
			 */
			for (BranchParameterStatus branchParameterStatus : branchManagement2.getBranchParameterStatuses()) {
				if (!branchParameterStatus.getStatus().equals("CANCEL")) {
					i += 1;
				}
			}
			log.info("branchParameterStatus list for particular branch is : "
					+ branchManagement2.getBranchParameterStatuses().size() + " and i value is : | : " + i);
			if (i >= 1) {
				model.addAttribute("eodalreadydone",
						messageSource.getMessage("eod.entry.already.done", null, Locale.ENGLISH));
				redirectAttributes.addFlashAttribute("modelMsg", model);
				return "redirect:/branchmanagement";
			}
		} else {
			String urlreferer = branchManagement.getUrlReferer();
			if (urlreferer == null) {
				return "redirect:/branchmanagement";
			}
			return "redirect:" + urlreferer;
		}
		if (null != branchManagement2) {
			model.addAttribute("edit", false);
			/**
			 * Getting dynamically common details of branch param and status
			 * details in a filtered and modified patterns
			 */
			utility.getBranchParameterAndStatusListIntegrated(model, branchManagement2);
		}

		return "brancheod";
		// return "redirect:/usermanagement";
	}

	@PreAuthorize("hasAnyRole('can_be_approver')")
	@RequestMapping(value = { "/approvebrancheodposition" }, method = RequestMethod.POST)
	public String approveBranchEODPositionPost(@Valid BranchManagement branchManagement, BindingResult result,
			ModelMap model, RedirectAttributes redirectAttributes) {
		BranchManagement branchManagement2 = null;
		if (null != branchManagement
				&& branchManagement.getId().equals(mySession.getUser().getBranchManagement().getId())) {
			System.out.println("Branch id in redirecting from branchEodPosition to approvebrancheodposition : "
					+ branchManagement.getId());
			branchManagement2 = branchManagementService.findById(branchManagement.getId());
			System.out
					.println("branch parameter status list : " + branchManagement2.getBranchParameterStatuses().size());
			int i = 0;
			/**
			 * Matching the current status as CANCEL with the existing eod
			 * entries if its exist for today then give him error for eod
			 * already done for today
			 */
			for (BranchParameterStatus branchParameterStatus : branchManagement2.getBranchParameterStatuses()) {
				if (!branchParameterStatus.getStatus().equals("PENDING")) {
					i += 1;
				}
			}
			log.info("branchParameterStatus list for particular branch is : "
					+ branchManagement2.getBranchParameterStatuses().size() + " and i value is : | : " + i);
			if (i >= 1) {
				model.addAttribute("eodalreadydone",
						messageSource.getMessage("eod.entry.already.done", null, Locale.ENGLISH));
				redirectAttributes.addFlashAttribute("modelMsg", model);
				return "redirect:/branchmanagement";
			}
		} else {
			String urlreferer = branchManagement.getUrlReferer();
			if (urlreferer == null) {
				return "redirect:/branchmanagement";
			}
			return "redirect:" + urlreferer;
		}
		if (null != branchManagement2) {
			model.addAttribute("edit", true);
			model.addAttribute("approveFlag", true);
			/**
			 * Getting dynamically common details of branch param and status
			 * details in a filtered and modified patterns
			 */
			utility.getBranchParameterAndStatusListIntegrated(model, branchManagement2);
		}

		return "brancheod";
	}

	@RequestMapping(value = { "/cancelEODForBranch" }, method = RequestMethod.GET)
	public String cancelEODApprovalForBranchGet(ModelMap model) {
		DashboardFinalBid dashboardFinalBid = null;
		BranchManagement branchManagement = mySession.getUser().getBranchManagement();
		boolean flag = branchParameterStatusService.cancelEODApprovalForBranch(branchManagement, "CANCEL");
		boolean flagMail = notificationAndMailUtility.sendMailToBrancheForEodCancel(dashboardFinalBid);
		log.info("Mail sent for eod is canceled to branch mail and all the approvers value is " + flagMail
				+ " for branch id " + branchManagement.getId());

		return "redirect:/branchmanagement";
	}

	@PreAuthorize("hasAnyRole('can_be_approver')")
	@RequestMapping(value = { "/approveEODForBranch" }, method = RequestMethod.GET)
	public String approveEODApprovalForBranchGet(ModelMap model) {
		BranchManagement branchManagement = mySession.getUser().getBranchManagement();
		boolean flag = branchParameterStatusService.approveEODApprovalForBranch(branchManagement, "APPROVED");
		if (flag) {
			return "redirect:/updateandapproverequestfordashboardentries";
			/*
			 * boolean flagMail =
			 * notificationAndMailUtility.sendMailToBrancheForEodApproval(
			 * dashboardFinalBid); log.
			 * info("Mail sent for eod is approved to branch mail and all the approvers value is "
			 * + flagMail + " for branch id " + branchManagement.getId());
			 */
		}
		return "redirect:/branchmanagement";
	}

}
