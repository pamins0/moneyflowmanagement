package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emorphis.cashmanagement.model.BidRequest;
import com.emorphis.cashmanagement.model.BidRequestedTo;
import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.BidRequestService;
import com.emorphis.cashmanagement.service.BranchClosedGroupService;
import com.emorphis.cashmanagement.service.BranchParameterStatusService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.Constant;
import com.emorphis.cashmanagement.util.Utility;

@Controller
@RequestMapping("/")
public class BidRequestController {
	private static final Logger log = LoggerFactory.getLogger(BidRequestController.class);

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	UtilityService utilityService;

	@Autowired
	BidRequestService bidRequestService;

	@Autowired
	BranchClosedGroupService branchClosedGroupService;

	@Autowired
	BranchParameterStatusService branchParameterStatusService;

	// @PreAuthorize("hasAnyRole('can_bid_create','can_bid_read','can_bid_update','can_bid_delete','can_bid_approve')")
	@RequestMapping(value = { "/bid", "/Bid" }, method = RequestMethod.GET)
	public String bid(ModelMap model) {
		if (mySession == null || mySession.getUser() == null) {
			return "redirect:/logout";
		}
		BidRequest bidRequest = new BidRequest();
		model.addAttribute("bid", bidRequest);
		List<BidRequest> bidRequestList = bidRequestList(bidRequest, model);
		model.addAttribute("bidRequestList", bidRequestList);
		model.addAttribute("myuser", mySession.getUser());
		return "bid";
	}

	/**
	 * Open Bid Request page
	 * 
	 * @param model
	 * @return
	 */
	// @PreAuthorize("hasRole('can_bid_create')")
	@RequestMapping(value = { "/bid-request", "/Bid-Request" }, method = RequestMethod.GET)
	public String bidRequest(BidRequest bidRequest, ModelMap model) {
		if (mySession == null || mySession.getUser() == null) {
			return "redirect:/logout";
		}

		// BidRequest bidRequest = new BidRequest();

		User user = mySession.getUser();
		boolean isBranchLevel = false;
		int hierarchyLevel = user.getBranchManagement().getHierarchyControl().getHierarchyLevel();
		int orgHierarchyLevel = user.getBranchManagement().getHierarchyControl().getOrgManagement().getOrgLevel();
		if (hierarchyLevel == orgHierarchyLevel) {
			isBranchLevel = true;
			bidRequest.setRequestPlacedById(user.getBranchManagement());
			List<BranchClosedGroup> closedBranchesList = branchClosedGroupService
					.getBranchClosedBranchList(mySession.getUser().getBranchManagement());
			// log.info("List of closed branches are " + closedBranchesList);
			model.addAttribute("closedBranch", closedBranchesList);
		} else {

			BranchManagement currentUserBranch = mySession.getUser().getBranchManagement();
			List<BranchManagement> requestPlacesedByBranchList = new ArrayList<>();
			requestPlacesedByBranchList.add(currentUserBranch);
			utility.getAllChildBranchList(currentUserBranch, requestPlacesedByBranchList);
			List<BranchManagement> requestPlacesedByOnlyBranchList = requestPlacesedByBranchList.stream()
					.filter(b -> b.getIsgroup() == 0).collect(Collectors.toList());
			model.addAttribute("branchList", requestPlacesedByOnlyBranchList);
		}
		// else{
		// List<HierarchyControl> hierarchyList =
		// utilityService.getHierarchyListAccToUserLevel(model);
		// model.addAttribute("hierarchyList", hierarchyList);
		// }

		// Denomination denomination=
		// branchParameterStatusService.getAvailableCashOfBranch();

		model.addAttribute("availableCash", "");
		model.addAttribute("isBranchLevel", isBranchLevel);
		model.addAttribute("bid", bidRequest);

		return "bidRequest";
	}

	/**
	 * Save Bid Request
	 * 
	 * @param bidRequest
	 * @param model
	 * @return
	 */
	// @PreAuthorize("hasRole('can_bid_create')")
	@RequestMapping(value = { "/bid-request", "/Bid-Request" }, method = RequestMethod.POST)
	public String bidRequestPost(BidRequest bidRequest, ModelMap model) {
		if (mySession == null || mySession.getUser() == null) {
			return "redirect:/logout";
		}

		/**
		 * save bid
		 */

		int result = bidRequestService.saveBidRequest(bidRequest);

		if (result == 1) {
			return "redirect:/bid";
		} else {
			return bidRequest(bidRequest, model);
		}
	}

	@RequestMapping(value = { "/bid-request-list", "/Bid-Request-List" }, method = RequestMethod.GET)
	public List<BidRequest> bidRequestList(BidRequest bidRequest, ModelMap model) {
		log.info("Bid Place By Branch==" + bidRequest.getRequestPlacedById());

		/**
		 * get All Inner branches
		 */
		BranchManagement currentUserBranch = mySession.getUser().getBranchManagement();
		List<BranchManagement> requestPlacesedByBranchList = new ArrayList<>();
		requestPlacesedByBranchList.add(currentUserBranch);
		utility.getAllChildBranchList(currentUserBranch, requestPlacesedByBranchList);

		List<String> requestPlacesedByBranchIdList = requestPlacesedByBranchList.stream().map(BranchManagement::getId)
				.collect(Collectors.toList());
		requestPlacesedByBranchIdList.forEach(e -> log.info("Bid Place By Branch Id==" + e));

		return bidRequestService.bidRequestList(bidRequest, requestPlacesedByBranchIdList);
	}

	/**
	 * ***************************************************************************************************************
	 */

	/**
	 * get list of pending approval
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/bid-approval-{id}" }, produces = "application/json", method = RequestMethod.GET)
	public String bidApproval(@PathVariable("id") String id, ModelMap model) {
		if (mySession == null || mySession.getUser() == null) {
			return "redirect:/logout";
		}
		int result = bidRequestService.bidApproval(id);
		return "redirect:/bid";
	}

	// @PreAuthorize("hasAnyRole('can_bid_create','can_bid_read','can_bid_update','can_bid_delete','can_bid_approve')")
	@RequestMapping(value = { "/bid-dashboard", "/Bid-Dashboard" }, method = RequestMethod.GET)
	public String bidDashboard(ModelMap model) {
		if (mySession == null || mySession.getUser() == null) {
			return "redirect:/logout";
		}

		/**
		 * get All Inner branches
		 */
		BranchManagement currentUserBranch = mySession.getUser().getBranchManagement();
		List<BranchManagement> requestPlacesedByBranchList = new ArrayList<>();
		requestPlacesedByBranchList.add(currentUserBranch);
		utility.getAllChildBranchList(currentUserBranch, requestPlacesedByBranchList);

		List<String> requestPlacesedToBranchIdList = requestPlacesedByBranchList.stream().map(BranchManagement::getId)
				.collect(Collectors.toList());
		requestPlacesedToBranchIdList.forEach(e -> log.info("Bid Place By Branch Id==" + e));

		/**
		 * List of Bid
		 */

		BidRequest bidRequest = new BidRequest();
		model.addAttribute("bid", bidRequest);
		bidRequest.setRequestStatus(Constant.STATUS_APPROVED);

		List<BidRequestedTo> bidRequestToList = bidRequestService.myDashboardBidList(bidRequest,
				requestPlacesedToBranchIdList);

		model.addAttribute("bid", bidRequest);
		model.addAttribute("bidRequestToList", bidRequestToList);
		return "bidDashboard";
	}

	/**
	 * get accept bid
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/bid-accept-{bidId}" }, produces = "application/json", method = RequestMethod.GET)
	public String bidAcceptPage(@PathVariable("bidId") String id, ModelMap model) {
		if (mySession == null || mySession.getUser() == null) {
			return "redirect:/logout";
		}
		BidRequestedTo bidRequestedTo = bidRequestService.findByIDBidRequestedTo(id);
		model.addAttribute("bidRequestedTo", bidRequestedTo);
		return "bidAccept";
	}

	/**
	 * Accept Bid Request
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/bid-accept-{bidId}" }, produces = "application/json", method = RequestMethod.POST)
	public String bidAccept(@PathVariable("bidId") String bidId, BidRequestedTo bidRequestedTo, ModelMap model) {

		if (mySession == null || mySession.getUser() == null) {
			return "redirect:/logout";
		}

		if (bidRequestService.saveBidRequestedTo(bidId, bidRequestedTo) > 0) {

			return "redirect:/bid-dashboard";
		}

		return "redirect:/bid-accept-" + bidId;
	}

	/**
	 * Accept Bid Request
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {
			"/bid-accept-approve-{bidRequestedTo}" }, produces = "application/json", method = RequestMethod.GET)
	public String bidAcceptApprove(@PathVariable("bidRequestedTo") String bidRequestedToId, ModelMap model) {

		if (mySession == null || mySession.getUser() == null) {
			return "redirect:/logout";
		}
		log.info("bidRequestedToId   :::>>" + bidRequestedToId);
		bidRequestService.bidAcceptApprove(bidRequestedToId);

		return "redirect:/bid-dashboard";
	}

}
