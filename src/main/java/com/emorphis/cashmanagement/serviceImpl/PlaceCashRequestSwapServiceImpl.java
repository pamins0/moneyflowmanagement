package com.emorphis.cashmanagement.serviceImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.PlaceCashRequestDao;
import com.emorphis.cashmanagement.dao.PlaceCashRequestSwapDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;
import com.emorphis.cashmanagement.model.RequestAjax;
import com.emorphis.cashmanagement.model.RequestStatus;
import com.emorphis.cashmanagement.service.DashboardFinalBidService;
import com.emorphis.cashmanagement.service.PlaceCashRequestSwapService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class PlaceCashRequestSwapServiceImpl implements PlaceCashRequestSwapService {

	@Autowired
	PlaceCashRequestDao placeCashRequestDao;

	@Autowired
	PlaceCashRequestSwapDao placeCashRequestSwapDao;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	MessageSource messageSource;

	@Autowired
	DashboardFinalBidService dashboardFinalBidService;

	@Override
	public boolean saveBranchSwapFromSwapingDashboard(PlaceCashRequestSwap placeCashRequestSwap) {
		String initialBidPlaceStatus = messageSource.getMessage("InitialBidPlacedStatus", null, Locale.ENGLISH);
		RequestStatus requestStatus = utility.getRequestStatusValue("PLACED");
		String uuid = utility.getPlaceCashRequestSwapUUID();
		if(uuid!=null){
			placeCashRequestSwap.setId(uuid); 
		}
		placeCashRequestSwap.setRequestType("SWAP");
		placeCashRequestSwap.setRequestStatus(requestStatus);
		placeCashRequestSwap.setCreated_Time(new Date());
		placeCashRequestSwap.setModified_Time(new Date());
		placeCashRequestSwap.setCreatedBy(mySession.getUser().getId());
		placeCashRequestSwap.setModifiedBy(mySession.getUser().getId());
		placeCashRequestSwap.setApproverFlag(2);

		Long currentTimeInMilliSeconds = System.currentTimeMillis();
		String bidApprovalHaultTime = messageSource.getMessage("BidApprovalHaultTime", null, Locale.ENGLISH);
		System.out.println("bidApprovalHault Time : " + bidApprovalHaultTime);
		Long bidApprovalHaultTimeInMilliSeconds = ((Long.parseLong(bidApprovalHaultTime)) * 60 * 1000);
		System.out.println("Current time millis : " + currentTimeInMilliSeconds);
		System.out.println("Approval time millis : " + bidApprovalHaultTimeInMilliSeconds);
		Long haultTimeForBid = currentTimeInMilliSeconds + bidApprovalHaultTimeInMilliSeconds;
		System.out.println("Total time to hault the bids is in millis : " + haultTimeForBid);
		placeCashRequestSwap.setBidHaultTime(BigInteger.valueOf(haultTimeForBid));

		boolean flag = placeCashRequestSwapDao.saveBranchSwapFromSwapingDashboard(placeCashRequestSwap);
		if (flag) {
			DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
					.findDashboardByBranchId(placeCashRequestSwap.getBranchManagementRequestedFrom().getId());
			DashboardFinalBid dashboardFinalBid1 = dashboardFinalBidService
					.findDashboardByBranchId(placeCashRequestSwap.getBranchManagementRequestedTo().getId());

			dashboardFinalBid.setRequestStatus(requestStatus);
			dashboardFinalBid1.setRequestStatus(requestStatus); 
		}
		return flag;
	}

	@Override
	public List<PlaceCashRequestSwap> getAllPlacedSwapBidForTheBranch(BranchManagement branchManagement) {
		List<PlaceCashRequestSwap> placeCashRequestsSwapList = null;
		placeCashRequestsSwapList = placeCashRequestSwapDao.getAllPlacedSwapBidForTheBranch(branchManagement);
		List<PlaceCashRequestSwap> modifiedFinalPlacedRequestBidList = new ArrayList<PlaceCashRequestSwap>();
		Long currentTimeInMilliSeconds = System.currentTimeMillis();
		System.out.println("Curremnt time in millis : " + currentTimeInMilliSeconds);
		BigInteger currentTime = BigInteger.valueOf(currentTimeInMilliSeconds);
		for (PlaceCashRequestSwap placeCashRequestSwap : placeCashRequestsSwapList) {
			BigInteger bidHaultTime = placeCashRequestSwap.getBidHaultTime();
			System.out.println("Bid hault time in millis : " + currentTimeInMilliSeconds + " for bid id : "
					+ placeCashRequestSwap.getId());
			int response = currentTime.compareTo(bidHaultTime);
			if (response == 1) {
				RequestStatus requestStatus = utility.getRequestStatusValue("TIMEOVER");
				placeCashRequestSwap.setRequestStatus(requestStatus);
				placeCashRequestSwap.setModified_Time(new Date());
				placeCashRequestSwap.setModifiedBy(mySession.getUser().getId());
				DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequestSwap.getBranchManagementRequestedFrom().getId());
				DashboardFinalBid dashboardFinalBid1 = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequestSwap.getBranchManagementRequestedTo().getId());
				requestStatus = utility.getRequestStatusValue("APPROVED");
				dashboardFinalBid.setRequestStatus(requestStatus);
				dashboardFinalBid.setModifiedTime(new Date());
				dashboardFinalBid1.setRequestStatus(requestStatus); 
				dashboardFinalBid1.setModifiedTime(new Date());
				System.out.println("Bid swap Approved for the branch : "
						+ placeCashRequestSwap.getBranchManagementRequestedFrom().getBranchName() + " its id is || "
						+ placeCashRequestSwap.getBranchManagementRequestedFrom().getId());
				System.out.println("Bid swap Approved to the branch : "
						+ placeCashRequestSwap.getBranchManagementRequestedTo().getBranchName() + " its id is || "
						+ placeCashRequestSwap.getBranchManagementRequestedTo().getId());
			}

			modifiedFinalPlacedRequestBidList.add(placeCashRequestSwap);
		}
		return modifiedFinalPlacedRequestBidList;
	}

	@Override
	public List<PlaceCashRequestSwap> getAllPlacedSwapBidForTheUserBranch(BranchManagement br) {
		List<PlaceCashRequestSwap> placeCashRequestsSwapList = null;
		placeCashRequestsSwapList = placeCashRequestSwapDao.getAllPlacedSwapBidForTheUserBranch(br);
		List<PlaceCashRequestSwap> modifiedFinalPlacedRequestBidList = new ArrayList<PlaceCashRequestSwap>();
		Long currentTimeInMilliSeconds = System.currentTimeMillis();
		System.out.println("Curremnt time in millis : " + currentTimeInMilliSeconds);
		BigInteger currentTime = BigInteger.valueOf(currentTimeInMilliSeconds);
		for (PlaceCashRequestSwap placeCashRequestSwap : placeCashRequestsSwapList) {
			BigInteger bidHaultTime = placeCashRequestSwap.getBidHaultTime();
			System.out.println("Bid hault time in millis : " + currentTimeInMilliSeconds + " for bid id : "
					+ placeCashRequestSwap.getId());
			int response = currentTime.compareTo(bidHaultTime);
			if (response == 1) {
				RequestStatus requestStatus = utility.getRequestStatusValue("TIMEOVER");
				placeCashRequestSwap.setRequestStatus(requestStatus); 
				placeCashRequestSwap.setModified_Time(new Date());
				placeCashRequestSwap.setModifiedBy(mySession.getUser().getId());
				DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequestSwap.getBranchManagementRequestedFrom().getId());
				DashboardFinalBid dashboardFinalBid1 = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequestSwap.getBranchManagementRequestedTo().getId());
				requestStatus = utility.getRequestStatusValue("APPROVED");
				dashboardFinalBid.setRequestStatus(requestStatus); 
				dashboardFinalBid.setModifiedTime(new Date());
				dashboardFinalBid1.setRequestStatus(requestStatus); 
				dashboardFinalBid1.setModifiedTime(new Date());
				System.out.println("Bid swap Approved for the branch : "
						+ placeCashRequestSwap.getBranchManagementRequestedFrom().getBranchName() + " its id is || "
						+ placeCashRequestSwap.getBranchManagementRequestedFrom().getId());
				System.out.println("Bid swap Approved to the branch : "
						+ placeCashRequestSwap.getBranchManagementRequestedTo().getBranchName() + " its id is || "
						+ placeCashRequestSwap.getBranchManagementRequestedTo().getId());
			}

			modifiedFinalPlacedRequestBidList.add(placeCashRequestSwap);
		}
		return modifiedFinalPlacedRequestBidList;
	}

	@Override
	public PlaceCashRequestSwap findById(String id) {

		return placeCashRequestSwapDao.findById(id);
	}

	@Override
	public boolean updateBidForApproval(PlaceCashRequestSwap placeCashRequestSwap) {
		boolean flag = false;
		PlaceCashRequestSwap placeCashRequest = new PlaceCashRequestSwap();
		placeCashRequest = placeCashRequestSwapDao.findById(placeCashRequestSwap.getId());

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
				placeCashRequest.setModifiedBy(mySession.getUser().getId());
				placeCashRequest.setModified_Time(new Date());

				DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
				requestStatus = utility.getRequestStatusValue("APPROVED");
				dashboardFinalBid.setRequestStatus(requestStatus); 
				dashboardFinalBid.setModifiedTime(new Date());

				DashboardFinalBid dashboardFinalBid1 = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedFrom().getId());
				dashboardFinalBid1.setRequestStatus(requestStatus); 
				dashboardFinalBid1.setModifiedTime(new Date());

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
					
					DashboardFinalBid dashboardFinalBid1 = dashboardFinalBidService
							.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedFrom().getId());
					dashboardFinalBid1.setRequestStatus(requestStatus); 
					dashboardFinalBid1.setModifiedTime(new Date());
					
					System.out.println("Bid set Approved for the branch : "
							+ placeCashRequest.getBranchManagementRequestedTo().getBranchName() + " its id is || "
							+ placeCashRequest.getBranchManagementRequestedTo().getId());
					
					
					flag = true;
					return flag;
				}else {
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
		return flag;
	} 

	@Override
	public boolean updateBidForCancel(RequestAjax requestAjax) {
		boolean flag = false;
		PlaceCashRequestSwap placeCashRequest = new PlaceCashRequestSwap();
		placeCashRequest = placeCashRequestSwapDao.findById(requestAjax.getPlacedRequestId());

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
				placeCashRequest.setModifiedBy(mySession.getUser().getId());
				placeCashRequest.setModified_Time(new Date());

				DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
				requestStatus = utility.getRequestStatusValue("APPROVED");
				dashboardFinalBid.setRequestStatus(requestStatus); 
				dashboardFinalBid.setModifiedTime(new Date());

				DashboardFinalBid dashboardFinalBid1 = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedFrom().getId());
				dashboardFinalBid1.setRequestStatus(requestStatus); 
				dashboardFinalBid1.setModifiedTime(new Date());

				System.out.println("Bid set Approved for the branch : "
						+ placeCashRequest.getBranchManagementRequestedTo().getBranchName() + " its id is || "
						+ placeCashRequest.getBranchManagementRequestedTo().getId());
				flag = false;
				return flag;
			}
			if (placeCashRequest.getRequestStatus().getAlias().equals("PLACED")) {
				RequestStatus requestStatus = utility.getRequestStatusValue("BIDCANCEL");
				placeCashRequest.setRequestStatus(requestStatus); 
				placeCashRequest.setModifiedBy(mySession.getUser().getId());
				placeCashRequest.setModified_Time(new Date());

				DashboardFinalBid dashboardFinalBid = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedTo().getId());
				requestStatus = utility.getRequestStatusValue("APPROVED");
				dashboardFinalBid.setRequestStatus(requestStatus); 
				dashboardFinalBid.setModifiedTime(new Date());

				DashboardFinalBid dashboardFinalBid1 = dashboardFinalBidService
						.findDashboardByBranchId(placeCashRequest.getBranchManagementRequestedFrom().getId());
				dashboardFinalBid1.setRequestStatus(requestStatus); 
				dashboardFinalBid1.setModifiedTime(new Date());

				System.out.println("Bid set Approved for the branch : "
						+ placeCashRequest.getBranchManagementRequestedFrom().getBranchName() + " its id is || "
						+ placeCashRequest.getBranchManagementRequestedFrom().getId());
				System.out.println("Bid set Approved to the branch : "
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
