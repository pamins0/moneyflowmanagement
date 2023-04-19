/**
 * 
 */
package com.emorphis.cashmanagement.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.BidAcceptApprovalDao;
import com.emorphis.cashmanagement.dao.BidRequestApprovalDao;
import com.emorphis.cashmanagement.dao.BidRequestDao;
import com.emorphis.cashmanagement.dao.BidRequestToDao;
import com.emorphis.cashmanagement.dao.BranchHoldingAmountDao;
import com.emorphis.cashmanagement.dao.BranchManagementDao;
import com.emorphis.cashmanagement.model.BidAcceptApproval;
import com.emorphis.cashmanagement.model.BidRequest;
import com.emorphis.cashmanagement.model.BidRequestApproval;
import com.emorphis.cashmanagement.model.BidRequestedTo;
import com.emorphis.cashmanagement.model.BranchHoldingAmount;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.BidRequestService;
import com.emorphis.cashmanagement.util.Constant;
import com.emorphis.cashmanagement.util.Utility;

/**
 * @author gourav
 *
 */
@Repository("bidRequestService")
@Transactional
public class BidRequestServiceImpl implements BidRequestService {

	private static final Logger log = LoggerFactory.getLogger(BidRequestServiceImpl.class);
	@Autowired
	Utility utility;

	@Autowired
	MySession mySession;

	@Autowired
	BidRequestDao bidRequestDao;

	@Autowired
	BidRequestToDao bidRequestToDao;

	@Autowired
	BidRequestApprovalDao bidRequestApprovalDao;

	@Autowired
	BidAcceptApprovalDao bidAcceptApprovalDao;

	@Autowired
	BranchManagementDao branchManagementDao;

	@Autowired
	BranchHoldingAmountDao branchHoldingAmountDao;

	@Override
	public BidRequest findByID(String id) {
		return bidRequestDao.findByID(id);
	}

	@Transactional
	@Override
	public int saveBidRequest(BidRequest bidRequest) {
		try {
			bidRequest.setId(utility.getBidRequestUUID());
			bidRequest.setCreatedBy(mySession.getUser().getId());
			bidRequest.setModifiedBy(mySession.getUser().getId());
			bidRequest.setCreatedTime(new Date());
			bidRequest.setModifiedTime(new Date());
			bidRequest.setRequestPlaceDate(new Date());

			BranchManagement requestPlacedByBranch = branchManagementDao
					.findById(bidRequest.getRequestPlacedById().getId());
			bidRequest.setRequestPlacedById(requestPlacedByBranch);

			log.info("bidRequest.getRequestPlacedById().getBranchApprovers() :::>>"
					+ bidRequest.getRequestPlacedById().getBranchApprovers());
			log.info("bidRequest.getRequestPlacedById().getName() :::>>"
					+ bidRequest.getRequestPlacedById().getBranchName());

			if (bidRequest.getRequestPlacedById().getBranchApprovers() > 0) {
				bidRequest.setRequestStatus(Constant.STATUS_PENDING);
			} else {
				bidRequest.setRequestStatus(Constant.STATUS_APPROVED);
			}

			bidRequestDao.saveBidRequest(bidRequest);

			/**
			 * save bid for requested branch
			 */
			for (String requestToBranchId : bidRequest.getBidRequestedToBranchIdList()) {
				BidRequestedTo bidRequestedTo = new BidRequestedTo();
				bidRequestedTo.setBidRequestId(bidRequest);
				BranchManagement requestToBranch = branchManagementDao.findById(requestToBranchId);
				bidRequestedTo.setRequestToBranchId(requestToBranch);
				bidRequestedTo.setAcceptAmount(bidRequest.getAmount());
				bidRequestedTo.setDn2000(bidRequest.getDn2000());
				bidRequestedTo.setDn500(bidRequest.getDn500());
				bidRequestedTo.setDn100(bidRequest.getDn100());
				bidRequestedTo.setDn50(bidRequest.getDn50());
				bidRequestedTo.setDn20(bidRequest.getDn20());
				bidRequestedTo.setDn10(bidRequest.getDn10());
				bidRequestedTo.setDn5(bidRequest.getDn5());
				bidRequestedTo.setDn2(bidRequest.getDn2());
				bidRequestedTo.setDn1(bidRequest.getDn1());
				bidRequestedTo.setDenominationCustomized(false);
				bidRequestedTo.setBidApprove(false);
				bidRequestedTo.setDenominationCustomized(false);
				bidRequestedTo.setDenominationCustomized(false);
				bidRequestedTo.setDeleted((byte) 0);

				/*
				 * save bid request to data
				 */
				bidRequestedTo.setId(utility.getBidRequestedToUUID());

				bidRequestedTo.setCreatedBy(mySession.getUser().getId());
				bidRequestedTo.setModifiedBy(mySession.getUser().getId());
				bidRequestedTo.setCreatedTime(new Date());
				bidRequestedTo.setModifiedTime(new Date());

				bidRequestToDao.saveBidRequestTo(bidRequestedTo);

			}

			/**
			 * Put amount on Hold
			 */
			if ("OFFLOAD".equals(bidRequest.getRequestType())) {
				BranchHoldingAmount branchHoldingAmount = branchHoldingAmountDao
						.getHoldingAmountByBranchId(bidRequest.getRequestPlacedById().getId());

				if (branchHoldingAmount == null) {
					branchHoldingAmount = new BranchHoldingAmount();
					branchHoldingAmount.setId(bidRequest.getRequestPlacedById().getId());
					branchHoldingAmount.setCreatedBy(mySession.getUser().getId());
					branchHoldingAmount.setCreatedTime(new Date());
					branchHoldingAmount.setBranchManagement(bidRequest.getRequestPlacedById());
				}

				branchHoldingAmount.setModifiedTime(new Date());
				branchHoldingAmount.setModifiedBy(mySession.getUser().getId());

				if (branchHoldingAmount.getAmount() != null && bidRequest.getAmount() != null) {
					branchHoldingAmount.setAmount(utility.add(utility.nullCheck(branchHoldingAmount.getAmount()),
							utility.nullCheck(bidRequest.getAmount())));
				} else if (branchHoldingAmount.getAmount() == null) {
					branchHoldingAmount.setAmount(utility.add(BigDecimal.valueOf(0).toBigInteger(),
							utility.nullCheck(bidRequest.getAmount())));
				}

				branchHoldingAmount.setDn1(
						utility.nullCheck(branchHoldingAmount.getDn1()) + utility.nullCheck(bidRequest.getDn1()));
				branchHoldingAmount.setDn2(
						utility.nullCheck(branchHoldingAmount.getDn2()) + utility.nullCheck(bidRequest.getDn2()));
				branchHoldingAmount.setDn5(
						utility.nullCheck(branchHoldingAmount.getDn5()) + utility.nullCheck(bidRequest.getDn5()));
				branchHoldingAmount.setDn10(
						utility.nullCheck(branchHoldingAmount.getDn10()) + utility.nullCheck(bidRequest.getDn10()));
				branchHoldingAmount.setDn20(
						utility.nullCheck(branchHoldingAmount.getDn20()) + utility.nullCheck(bidRequest.getDn20()));
				branchHoldingAmount.setDn50(
						utility.nullCheck(branchHoldingAmount.getDn50()) + utility.nullCheck(bidRequest.getDn50()));
				branchHoldingAmount.setDn100(
						utility.nullCheck(branchHoldingAmount.getDn100()) + utility.nullCheck(bidRequest.getDn100()));
				branchHoldingAmount.setDn500(
						utility.nullCheck(branchHoldingAmount.getDn500()) + utility.nullCheck(bidRequest.getDn500()));
				branchHoldingAmount.setDn2000(
						utility.nullCheck(branchHoldingAmount.getDn2000()) + utility.nullCheck(bidRequest.getDn2000()));

				branchHoldingAmountDao.saveOrUpdateBranchHoldingAmount(branchHoldingAmount);

			}

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<BidRequest> bidRequestList(BidRequest bidRequest, List<String> requestPlacesedByBranchIdList) {

		return bidRequestDao.bidRequestList(bidRequest, requestPlacesedByBranchIdList);
	}

	@Override
	public int bidApproval(String id) {
		User user = mySession.getUser();
		/**
		 * get Bid details
		 */
		BidRequest bidRequest = bidRequestDao.findByUUID(id);
		/**
		 * Check is already approved
		 */
		List<BidRequestApproval> approvalList = bidRequest.getBidRequestApprovals().stream()
				.filter(u -> u.getApprovarId().getId().equals(user.getId())).collect(Collectors.toList());
		if (approvalList.size() > 0) {
			log.error("Alrady found entry");
			return 0;
		}

		/**
		 * save approvar details
		 */
		BidRequestApproval bidRequestApproval = new BidRequestApproval();
		bidRequestApproval.setId(utility.getBidRequestApprovalUUID());
		bidRequestApproval.setCreatedBy(mySession.getUser().getId());
		bidRequestApproval.setModifiedBy(mySession.getUser().getId());
		bidRequestApproval.setCreatedTime(new Date());
		bidRequestApproval.setModifiedTime(new Date());
		bidRequestApproval.setApproveStatus((byte) 1);
		bidRequestApproval.setBidRequestId(bidRequest);
		bidRequestApproval.setApprovarId(mySession.getUser());

		bidRequestApprovalDao.saveBidRequestApproval(bidRequestApproval);

		if (bidRequest.getRequestPlacedById().getBranchApprovers() <= bidRequest.getBidRequestApprovals().size() + 1) {
			bidRequest.setRequestStatus(Constant.STATUS_APPROVED);
			bidRequest.setModifiedTime(new Date());
			bidRequestDao.saveBidRequest(bidRequest);
		}
		return 0;
	}

	@Override
	public BidRequestedTo findByIDBidRequestedTo(String bidId) {
		return bidRequestToDao.findByIDBidRequestedTo(bidId, null);
	}

	/**
	 * 	
	 */
	@Transactional
	@Override
	public int saveBidRequestedTo(String bidId, BidRequestedTo bidRequestedTo) {
		BidRequestedTo bidRequestedToObj = bidRequestToDao.findByIDBidRequestedTo(bidId, null);

		bidRequestedToObj.setModifiedBy(mySession.getUser().getId());
		bidRequestedToObj.setModifiedTime(new Date());
		bidRequestedToObj.setRequestStatus(Constant.STATUS_PENDING_TO_APPROVE);
		if (bidRequestedTo.isDenominationCustomized()) {

			bidRequestedToObj.setDenominationCustomized(true);

			bidRequestedToObj.setAcceptAmount(bidRequestedTo.getAcceptAmount());
			bidRequestedToObj.setDn2000(bidRequestedTo.getDn2000());
			bidRequestedToObj.setDn500(bidRequestedTo.getDn500());
			bidRequestedToObj.setDn100(bidRequestedTo.getDn100());
			bidRequestedToObj.setDn50(bidRequestedTo.getDn50());
			bidRequestedToObj.setDn20(bidRequestedTo.getDn20());
			bidRequestedToObj.setDn10(bidRequestedTo.getDn10());
			bidRequestedToObj.setDn5(bidRequestedTo.getDn5());
			bidRequestedToObj.setDn2(bidRequestedTo.getDn2());
			bidRequestedToObj.setDn1(bidRequestedTo.getDn1());

			log.info("bidRequestedTo Details::>>>"
					+ bidRequestedToObj.getBidRequestId().getRequestPlacedById().getBranchName() + ":::"
					+ bidRequestedToObj.getRequestToBranchId().getBranchName());
			log.info("Amount::>>>" + bidRequestedToObj.getAcceptAmount() + ">>" + bidRequestedTo.getAcceptAmount());
			log.info("Denomination::>>>2000>>>" + bidRequestedToObj.getDn2000() + ">>" + bidRequestedTo.getDn2000());
			log.info("Denomination::>>>500>>>" + bidRequestedToObj.getDn500() + ">>" + bidRequestedTo.getDn500());
			log.info("Denomination::>>>100>>>" + bidRequestedToObj.getDn100() + ">>" + bidRequestedTo.getDn100());

		} else {

			bidRequestedToObj.setDenominationCustomized(false);
			log.info("bidRequestedTo Details::>>>"
					+ bidRequestedToObj.getBidRequestId().getRequestPlacedById().getBranchName() + ":::"
					+ bidRequestedToObj.getRequestToBranchId().getBranchName() + ":::"
					+ bidRequestedToObj.getAcceptAmount() + "");

		}

		int result = bidRequestToDao.saveBidRequestTo(bidRequestedToObj);

		bidAcceptApproval(bidRequestedToObj);

		return result;
	}

	/**
	 * save Approver details
	 * 
	 * @param bidRequestedTo
	 * @return
	 */
	public int bidAcceptApproval(BidRequestedTo bidRequestedTo) {

		/**
		 * Check is already approved
		 */
		List<BidAcceptApproval> approvalList = bidRequestedTo.getBidAcceptApprovals().stream()
				.filter(u -> u.getApprovarId().getId().equals(mySession.getUser().getId()))
				.collect(Collectors.toList());

		if (approvalList.size() > 0) {
			log.error("Alrady found entry");
			return 0;
		}

		/**
		 * save approvar details
		 */
		BidAcceptApproval bidAcceptApproval = new BidAcceptApproval();
		bidAcceptApproval.setId(utility.getBidAcceptApprovalUUID());
		bidAcceptApproval.setCreatedBy(mySession.getUser().getId());
		bidAcceptApproval.setModifiedBy(mySession.getUser().getId());
		bidAcceptApproval.setCreatedTime(new Date());
		bidAcceptApproval.setModifiedTime(new Date());
		bidAcceptApproval.setApproveStatus((byte) 1);
		bidAcceptApproval.setBidRequestedTo(bidRequestedTo);
		bidAcceptApproval.setApprovarId(mySession.getUser());

		bidAcceptApprovalDao.saveBidAcceptApproval(bidAcceptApproval);

		if (bidRequestedTo.getRequestToBranchId().getBranchApprovers() <= bidRequestedTo.getBidAcceptApprovals().size()
				+ 1) {
			bidRequestedTo.setBidApprove(true);
			bidRequestedTo.setRequestStatus(Constant.STATUS_APPROVED);
			bidRequestedTo.setModifiedTime(new Date());
			bidRequestToDao.saveBidRequestTo(bidRequestedTo);
			
			if(!bidRequestedTo.isDenominationCustomized()){
				BidRequest bidRequest= bidRequestDao.findByID(bidRequestedTo.getBidRequestId().getId());
				
				bidRequest.setRequestAcceptById(mySession.getUser().getBranchManagement());
				bidRequest.setRequestStatus(Constant.STATUS_BID_COMPLETED);
				
				bidRequestDao.saveBidRequest(bidRequest);
			}
			
		}

		return 1;
	}

	@Override
	public int bidAcceptApprove(String bidRequestedToId) {

		BidRequestedTo bidRequestedToObj = bidRequestToDao.findByIDBidRequestedTo(null, bidRequestedToId);
		bidAcceptApproval(bidRequestedToObj);
		return 0;
	}

	@Override
	public List<BidRequestedTo> myDashboardBidList(BidRequest bidRequest, List<String> requestPlacesedByBranchIdList) {

		return bidRequestToDao.myDashboardBidList(bidRequest, requestPlacesedByBranchIdList);
	}

}
