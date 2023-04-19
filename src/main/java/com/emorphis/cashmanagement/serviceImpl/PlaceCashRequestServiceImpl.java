package com.emorphis.cashmanagement.serviceImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.PlaceCashRequestDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.model.RequestAjax;
import com.emorphis.cashmanagement.model.RequestStatus;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.DashboardFinalBidService;
import com.emorphis.cashmanagement.service.PlaceCashRequestService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class PlaceCashRequestServiceImpl implements PlaceCashRequestService {

	private static final Logger log = LoggerFactory.getLogger(PlaceCashRequestServiceImpl.class);

	@Autowired
	PlaceCashRequestDao placeCashRequestDao;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	MessageSource messageSource;

	@Autowired
	DashboardFinalBidService dashboardFinalBidService;

	@Autowired
	BranchManagementService branchManagementService;

	@Override
	public PlaceCashRequest findById(String placedRequestId) {
		return placeCashRequestDao.findById(placedRequestId);
	}

	@Override
	public void saveBranchCashRequest(PlaceCashRequest placeCashRequest) {
		placeCashRequest.setCreatedBy(mySession.getUser().getId());
		placeCashRequest.setModifiedBy(mySession.getUser().getId());
		placeCashRequest.setCreated_Time(new Date());
		placeCashRequest.setModified_Time(new Date());
		placeCashRequestDao.saveBranchCashRequest(placeCashRequest);
	}

	@Override
	public boolean saveBranchCashRequestFromDashboard(RequestAjax requestAjax) {
		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		BranchManagement fromBranch = new BranchManagement();
		BranchManagement toBranch = new BranchManagement();
		fromBranch.setId(requestAjax.getFromBranchId());
		toBranch.setId(requestAjax.getToBranchId());

		String uuid = utility.getPlaceCashRequestUUID();
		if (uuid != null) {
			placeCashRequest.setId(uuid);
			requestAjax.setId(uuid);
		}
		/**
		 * Including new concept so that this bid has number of approvers
		 * according to the branches
		 */

		fromBranch = branchManagementService.findById(requestAjax.getFromBranchId());
		placeCashRequest.setApproverFlag(fromBranch.getBranchApprovers());
		/**
		 * End of new concept
		 */
		placeCashRequest.setBranchManagementRequestedTo(toBranch);
		placeCashRequest.setAmount(requestAjax.getToBranchTotallAmount());
		placeCashRequest.setBranchManagementRequestedFrom(fromBranch);

		if (null != requestAjax.getToBranchPosition()) {
			if (requestAjax.getToBranchPosition().equals("below")) {
				placeCashRequest.setRequestType("Remit");
			} else {
				if (requestAjax.getToBranchPosition().equals("excess")) {
					placeCashRequest.setRequestType("Recieve");
				} else {
					placeCashRequest.setRequestType("level");
				}
			}
		}
		String initialBidPlaceStatus = messageSource.getMessage("InitialBidPlacedStatus", null, Locale.ENGLISH);
		RequestStatus requestStatus = utility.getRequestStatusValue("PLACED");
		placeCashRequest.setRequestStatus(requestStatus);
		placeCashRequest.setCreated_Time(new Date());
		placeCashRequest.setModified_Time(new Date());
		placeCashRequest.setCreatedBy(mySession.getUser().getId());
		placeCashRequest.setModifiedBy(mySession.getUser().getId());

		Long currentTimeInMilliSeconds = System.currentTimeMillis();
		String bidApprovalHaultTime = messageSource.getMessage("BidApprovalHaultTime", null, Locale.ENGLISH);
		System.out.println("bidApprovalHault Time : " + bidApprovalHaultTime);
		Long bidApprovalHaultTimeInMilliSeconds = ((Long.parseLong(bidApprovalHaultTime)) * 60 * 1000);
		System.out.println("Current time millis : " + currentTimeInMilliSeconds);
		System.out.println("Approval time millis : " + bidApprovalHaultTimeInMilliSeconds);
		Long haultTimeForBid = currentTimeInMilliSeconds + bidApprovalHaultTimeInMilliSeconds;
		System.out.println("Total time to hault the bids is in millis : " + haultTimeForBid);
		placeCashRequest.setBidHaultTime(BigInteger.valueOf(haultTimeForBid));

		boolean flag = placeCashRequestDao.saveBranchCashRequestFromDashboard(placeCashRequest);
		if (flag) {
			DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
					.findDashboardByBranchId(requestAjax.getToBranchId());
			dashboardFinalBid.setRequestStatus(requestStatus);
		}

		return flag;
	}

	@Override
	public List<PlaceCashRequest> getAllPlacedBidForTheUserBranch(BranchManagement branchManagement) {
		List<PlaceCashRequest> placeCashRequestsList = null;
		placeCashRequestsList = placeCashRequestDao.getAllPlacedBidForTheUserBranch(branchManagement);
		List<PlaceCashRequest> modifiedFinalPlacedRequestBidList = new ArrayList<PlaceCashRequest>();
		Long currentTimeInMilliSeconds = System.currentTimeMillis();
		System.out.println("Curremnt time in millis : " + currentTimeInMilliSeconds);
		BigInteger currentTime = BigInteger.valueOf(currentTimeInMilliSeconds);
		for (PlaceCashRequest placeCashRequest : placeCashRequestsList) {
			BigInteger bidHaultTime = placeCashRequest.getBidHaultTime();
			System.out.println("Bid hault time in millis : " + currentTimeInMilliSeconds + " for bid id : "
					+ placeCashRequest.getId());
			int response = currentTime.compareTo(bidHaultTime);
			if (response == 1) {
				RequestStatus requestStatus = utility.getRequestStatusValue("TIMEOVER");
				placeCashRequest.setRequestStatus(requestStatus);
				DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
				requestStatus = utility.getRequestStatusValue("APPROVED");
				dashboardFinalBid.setRequestStatus(requestStatus);
				System.out.println("Bid set Approved for the branch : "
						+ placeCashRequest.getBranchManagementRequestedTo().getBranchName() + " its id is || "
						+ placeCashRequest.getBranchManagementRequestedTo().getId());
			}

			modifiedFinalPlacedRequestBidList.add(placeCashRequest);
		}
		return modifiedFinalPlacedRequestBidList;
	}

	@Override
	public List<PlaceCashRequest> getAllPlacedBidForTheBranch(BranchManagement branchManagement) {
		List<PlaceCashRequest> placeCashRequestsList = null;
		placeCashRequestsList = placeCashRequestDao.getAllPlacedBidForTheBranch(branchManagement);
		List<PlaceCashRequest> modifiedFinalPlacedRequestBidList = new ArrayList<PlaceCashRequest>();
		Long currentTimeInMilliSeconds = System.currentTimeMillis();
		System.out.println("Curremnt time in millis : " + currentTimeInMilliSeconds);
		BigInteger currentTime = BigInteger.valueOf(currentTimeInMilliSeconds);
		for (PlaceCashRequest placeCashRequest : placeCashRequestsList) {
			BigInteger bidHaultTime = placeCashRequest.getBidHaultTime();
			System.out.println("Bid hault time in millis : " + currentTimeInMilliSeconds + " for bid id : "
					+ placeCashRequest.getId());
			int response = currentTime.compareTo(bidHaultTime);
			if (response == 1) {
				RequestStatus requestStatus = utility.getRequestStatusValue("TIMEOVER");
				placeCashRequest.setRequestStatus(requestStatus);
				placeCashRequest.setModified_Time(new Date());
				DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
				requestStatus = utility.getRequestStatusValue("APPROVED");
				dashboardFinalBid.setRequestStatus(requestStatus);
				dashboardFinalBid.setModifiedTime(new Date());
				System.out.println("Bid set Approved for the branch : "
						+ placeCashRequest.getBranchManagementRequestedTo().getBranchName() + " its id is || "
						+ placeCashRequest.getBranchManagementRequestedTo().getId());
			}

			modifiedFinalPlacedRequestBidList.add(placeCashRequest);
		}
		return modifiedFinalPlacedRequestBidList;
	}

	@Override
	public boolean updateBidForApproval(RequestAjax requestAjax) {
		boolean flag = false;
		try {
			PlaceCashRequest placeCashRequest = new PlaceCashRequest();
			placeCashRequest = placeCashRequestDao.findById(requestAjax.getPlacedRequestId());

			Long currentTimeInMilliSeconds = System.currentTimeMillis();
			System.out.println("Curremnt time in millis : " + currentTimeInMilliSeconds);
			BigInteger currentTime = BigInteger.valueOf(currentTimeInMilliSeconds);
			if (placeCashRequest != null) {
				BigInteger bidHaultTime = placeCashRequest.getBidHaultTime();
				System.out.println("Bid hault time in millis : " + currentTimeInMilliSeconds + " for bid id : "
						+ placeCashRequest.getId());
				int response = currentTime.compareTo(bidHaultTime);
				if (response == 1) {
					RequestStatus requestStatus = utility.getRequestStatusValue("TIMEOVER");
					placeCashRequest.setRequestStatus(requestStatus);
					DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
							.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
					requestStatus = utility.getRequestStatusValue("APPROVED");
					dashboardFinalBid.setRequestStatus(requestStatus);
					System.out.println("Bid set Approved for the branch : "
							+ placeCashRequest.getBranchManagementRequestedTo().getBranchName() + " its id is || "
							+ placeCashRequest.getBranchManagementRequestedTo().getId());
					flag = false;
					return flag;
				}
				if (placeCashRequest.getRequestStatus().getAlias().equals("PLACED")) {
					if (placeCashRequest.getApproverFlag() == 1) {
						RequestStatus requestStatus = utility.getRequestStatusValue("BIDAPPROVED");
						placeCashRequest.setRequestStatus(requestStatus);
						placeCashRequest.setModifiedBy(mySession.getUser().getId());
						placeCashRequest.setModified_Time(new Date());
						placeCashRequest.setApproverFlag((placeCashRequest.getApproverFlag() - 1));

						DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
								.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
						dashboardFinalBid.setRequestStatus(requestStatus);
						dashboardFinalBid.setModifiedTime(new Date());
						System.out.println("Bid set Approved for the branch : "
								+ placeCashRequest.getBranchManagementRequestedTo().getBranchName() + " its id is || "
								+ placeCashRequest.getBranchManagementRequestedTo().getId());
						flag = true;
						return flag;
					} else {
						placeCashRequest.setApproverFlag((placeCashRequest.getApproverFlag() - 1));
						placeCashRequest.setModifiedBy(mySession.getUser().getId());
						placeCashRequest.setModified_Time(new Date());
						flag = true;
					}
				} else {
					flag = false;
					return flag;
				}
			} else {
				return flag;
			}
		} catch (Exception e) {
			log.error(
					"Exception generated in placecashrequestserviceimpl class in method updateBidForApproval due to : "
							+ e);
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean updateAndApproveBidPlaced(RequestAjax requestAjax) {
		boolean flag = false;
		try {
			PlaceCashRequest placeCashRequest = new PlaceCashRequest();
			placeCashRequest = placeCashRequestDao.findById(requestAjax.getPlacedRequestId());

			Long currentTimeInMilliSeconds = System.currentTimeMillis();
			System.out.println("Curremnt time in millis : " + currentTimeInMilliSeconds);
			BigInteger currentTime = BigInteger.valueOf(currentTimeInMilliSeconds);
			if (placeCashRequest != null) {
				BigInteger bidHaultTime = placeCashRequest.getBidHaultTime();
				System.out.println("Bid hault time in millis : " + currentTimeInMilliSeconds + " for bid id : "
						+ placeCashRequest.getId());
				int response = currentTime.compareTo(bidHaultTime);
				if (response == 1) {
					RequestStatus requestStatus = utility.getRequestStatusValue("TIMEOVER");
					placeCashRequest.setRequestStatus(requestStatus);
					DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
							.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
					requestStatus = utility.getRequestStatusValue("APPROVED");
					dashboardFinalBid.setRequestStatus(requestStatus);
					System.out.println("Bid set Approved for the branch : "
							+ placeCashRequest.getBranchManagementRequestedTo().getBranchName() + " its id is || "
							+ placeCashRequest.getBranchManagementRequestedTo().getId());
					flag = false;
					return flag;
				}
				if (placeCashRequest.getRequestStatus().getAlias().equals("PLACED")) {
					if (placeCashRequest.getApproverFlag() == 1) {
						placeCashRequest.setApproverFlag((placeCashRequest.getApproverFlag() - 1));
						placeCashRequest.setAmount(Double.parseDouble(requestAjax.getAmount()));
						RequestStatus requestStatus = utility.getRequestStatusValue("BIDAPPROVED");
						placeCashRequest.setRequestStatus(requestStatus);
						DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
								.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
						dashboardFinalBid.setRequestStatus(requestStatus);
						System.out.println("Bid set Approved for the branch : "
								+ placeCashRequest.getBranchManagementRequestedTo().getBranchName() + " its id is || "
								+ placeCashRequest.getBranchManagementRequestedTo().getId());
						flag = true;
						return flag;
					} else {
						placeCashRequest.setAmount(Double.parseDouble(requestAjax.getAmount()));
						placeCashRequest.setApproverFlag((placeCashRequest.getApproverFlag() - 1));
						placeCashRequest.setModifiedBy(mySession.getUser().getId());
						placeCashRequest.setModified_Time(new Date());
						flag = true;
					}
				} else {
					flag = false;
					return flag;
				}
			} else {
				return flag;
			}
		} catch (Exception e) {
			log.error(
					"Exception generated in placecashrequestserviceimpl class in method updateAndApproveBidPlaced due to : "
							+ e);
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean updateBidForCancel(RequestAjax requestAjax) {
		boolean flag = false;
		PlaceCashRequest placeCashRequest = new PlaceCashRequest();
		placeCashRequest = placeCashRequestDao.findById(requestAjax.getPlacedRequestId());

		Long currentTimeInMilliSeconds = System.currentTimeMillis();
		System.out.println("Curremnt time in millis : " + currentTimeInMilliSeconds);
		BigInteger currentTime = BigInteger.valueOf(currentTimeInMilliSeconds);
		if (placeCashRequest != null) {
			BigInteger bidHaultTime = placeCashRequest.getBidHaultTime();
			System.out.println("Bid hault time in millis : " + currentTimeInMilliSeconds + " for bid id : "
					+ placeCashRequest.getId());
			int response = currentTime.compareTo(bidHaultTime);
			if (response == 1) {
				RequestStatus requestStatus = utility.getRequestStatusValue("TIMEOVER");
				placeCashRequest.setRequestStatus(requestStatus);
				DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
				requestStatus = utility.getRequestStatusValue("APPROVED");
				dashboardFinalBid.setRequestStatus(requestStatus);
				System.out.println("Bid set Approved for the branch : "
						+ placeCashRequest.getBranchManagementRequestedTo().getBranchName() + " its id is || "
						+ placeCashRequest.getBranchManagementRequestedTo().getId());
				flag = false;
				return flag;
			}
			if (placeCashRequest.getRequestStatus().getAlias().equals("PLACED")) {
				RequestStatus requestStatus = utility.getRequestStatusValue("BIDCANCEL");
				placeCashRequest.setRequestStatus(requestStatus);
				DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
				requestStatus = utility.getRequestStatusValue("APPROVED");
				dashboardFinalBid.setRequestStatus(requestStatus);
				System.out.println("Bid set Approved for the branch : "
						+ placeCashRequest.getBranchManagementRequestedTo().getBranchName() + " its id is || "
						+ placeCashRequest.getBranchManagementRequestedTo().getId());
				flag = true;
				return flag;
			} else {
				return true;
			}
		} else {
			return flag;
		}
	}

}
