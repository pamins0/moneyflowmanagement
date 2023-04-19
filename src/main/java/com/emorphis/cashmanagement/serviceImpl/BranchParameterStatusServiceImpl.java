package com.emorphis.cashmanagement.serviceImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.BranchHoldingAmountDao;
import com.emorphis.cashmanagement.dao.BranchParameterStatusDao;
import com.emorphis.cashmanagement.model.BranchHoldingAmount;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.Denomination;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.BranchParameterStatusService;
import com.emorphis.cashmanagement.service.DashboardFinalBidService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class BranchParameterStatusServiceImpl implements BranchParameterStatusService {

	private static final Logger log = LoggerFactory.getLogger(BranchParameterStatusServiceImpl.class);

	@Autowired
	BranchParameterStatusDao branchParameterStatusDao;

	@Autowired
	BranchManagementService branchManagementService;

	@Autowired
	DashboardFinalBidService dashboardFinalBidService;

	@Autowired
	BranchHoldingAmountDao branchHoldingAmountDao;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Override
	public void saveBranchEodPosition(BranchParameterStatus branchParameterStatus) {
		System.out
				.println("BranchparameterStatus branch id is : " + branchParameterStatus.getBranchManagement().getId());
		try {
			BranchManagement branchManagement = branchManagementService
					.findById(branchParameterStatus.getBranchManagement().getId());

			List<BranchParameterStatus> branchParameterStatusList = branchManagement.getBranchParameterStatuses();
			System.out.println("branchParamStatus list for branch id : " + branchManagement.getId() + " is : ||  "
					+ branchParameterStatusList.size());

			// boolean flag =
			// branchParameterStatusDao.deleteAll(branchParameterStatusList,
			// branchManagement);
			boolean flag = true;
			boolean deleteFlag = false;
			List<BranchParameterStatus> modifiedBranchParameterList = new ArrayList<BranchParameterStatus>();
			if (branchParameterStatusList.size() > 0) {
				if (flag) {
					deleteFlag = true;
				} else {
					deleteFlag = false;
				}
			} else {
				deleteFlag = true;
			}

			/**
			 * Setting the time for approval automatic if consist
			 */
			Long currentTimeInMilliSeconds = System.currentTimeMillis();
			log.info("current time in milliseconds : " + currentTimeInMilliSeconds);
			String requestApprovalHaultTime = branchManagement.getHierarchyControl().getOrgManagement()
					.getAutoAprovalRequestTime();
			log.info("branch organization current auto approval time in minutes : " + requestApprovalHaultTime);
			Long requestApprovalHaultTimeInMilliSeconds = ((Long.parseLong(requestApprovalHaultTime)) * 60 * 1000);
			log.info("branch organization current auto approval time in milliseconds : "
					+ requestApprovalHaultTimeInMilliSeconds);
			Long totalHaultTimeForApprove = currentTimeInMilliSeconds + requestApprovalHaultTimeInMilliSeconds;
			log.info("total time for request auto approval for branch i.e | " + branchManagement.getId() + " time is : "
					+ totalHaultTimeForApprove);

			if (deleteFlag) {
				for (BranchParameterStatus branchParameterStatus2 : branchParameterStatus
						.getBranchParameterStatusList()) {
					branchParameterStatus2.setBranchManagement(branchManagement);
					branchParameterStatus2.setCreatedTime(new Date());
					branchParameterStatus2.setModifiedTime(new Date());
					branchParameterStatus2.setCreatedBy(mySession.getUser().getId());
					branchParameterStatus2.setModifiedBy(mySession.getUser().getId());
					branchParameterStatus2.setIp(mySession.getIp());
					if (branchParameterStatus.getSaveBtn().equals("Save")) {
						branchParameterStatus2.setStatus("PENDING");
						branchParameterStatus2.setApproverFlag(1);

						if (branchManagement.getRequestApprovalType() == 0) {
							branchParameterStatus2.setEodHaultTime(BigInteger.valueOf(totalHaultTimeForApprove));
						}

					} else if (branchParameterStatus.getSaveBtn().equals("UpdateAndApprove")) {
						branchParameterStatus2.setStatus("APPROVED");
						log.info("approved flag is : " + branchParameterStatus2.getApproverFlag());
						branchParameterStatus2.setApproverFlag(0);
						if (branchManagement.getRequestApprovalType() == 0) {
							branchParameterStatus2.setEodHaultTime(BigInteger.valueOf(totalHaultTimeForApprove));
						}
						branchParameterStatus2.setProcessedBy("MANUAL");
					}

					/**
					 * Now entering the details of branch approvers to approve
					 * the details of......
					 */
					// branchParameterStatus2.setApproverFlag(branchManagement.getBranchApprovers());
					/**
					 * Now only setting one approver for this......
					 */
					// branchParameterStatus2.setApproverFlag(1);
					/**
					 * End of approver flag saubmitting......
					 */

					String uuid = utility.getBranchParameterStatusUUID();
					if (uuid != null) {
						branchParameterStatus2.setId(uuid);
					}

					modifiedBranchParameterList.add(branchParameterStatus2);
				}
				branchManagement.setBranchParameterStatuses(modifiedBranchParameterList);

				// boolean flag1 =
				// dashboardFinalBidService.deleteAllOldBranchEntry(branchManagement);
			}
		} catch (Exception e) {
			log.error(
					"Exception generated in during eod save and deleting the old entries of parameterstatus and dashboard entries "
							+ e);
		}

	}

	@Override
	public boolean updateBranchParameterStatusAndProcessForDashboard(DashboardFinalBid dashboardFinalBid, String status)
			throws Exception {
		boolean flag = false;
		try {
			BranchManagement branchManagement = mySession.getUser().getBranchManagement();
			/*
			 * List<BranchParameterStatus> branchParameterStatusList =
			 * branchManagement.getBranchParameterStatuses(); for
			 * (BranchParameterStatus branchParameterStatus :
			 * branchParameterStatusList) {
			 * branchParameterStatus.setStatus(status);
			 * branchParameterStatus.setModifiedBy(mySession.getUser().getId());
			 * branchParameterStatus.setModifiedTime(new Date()); } flag = true;
			 */
			log.info("branch id : " + branchManagement.getId() + " total eod amt : " + dashboardFinalBid.getEodTotal()
					+ " total amount : " + dashboardFinalBid.getTotal() + " position : "
					+ dashboardFinalBid.getPosition());
			if (status.equals("APPROVED")) {
				dashboardFinalBid.setBranchManagement(branchManagement);
				dashboardFinalBid.setModifiedTime(new Date());
				dashboardFinalBid.setCreatedTime(new Date());
				dashboardFinalBid.setIp(mySession.getIp());
				if (branchManagement.getBranchType() == 0) {
					dashboardFinalBid.setRequestStatus(utility.getRequestStatusValue("APPROVED"));
				} else if (branchManagement.getBranchType() == 1) {
					dashboardFinalBid.setRequestStatus(utility.getRequestStatusValue("NONPARTICIPATING"));
				}
				dashboardFinalBidService.save(dashboardFinalBid);
				log.info("branch eod saved for bidding in dashboard table for branch id : " + branchManagement.getId()
						+ " or branch name : " + branchManagement.getBranchName());
				flag = true;
			} else if (status.equals("CANCEL")) {
				log.info("hence status is cancel so dashboard entry not done for branch id : "
						+ branchManagement.getId());
				dashboardFinalBid.setBranchManagement(branchManagement);
				dashboardFinalBid.setModifiedTime(new Date());
				dashboardFinalBid.setCreatedTime(new Date());
				dashboardFinalBid.setRequestStatus(utility.getRequestStatusValue("CANCEL"));
				dashboardFinalBid.setIp(mySession.getIp());
				dashboardFinalBidService.save(dashboardFinalBid);
				log.info("branch eod saved for bidding in dashboard table for branch id : " + branchManagement.getId()
						+ " or branch name : " + branchManagement.getBranchName());
				flag = true;
			}
		} catch (Exception e) {
			log.error("Exception generated in updateBranchParameterStatusAndProcessForDashboard due to : " + e);
			flag = false;
		}
		return flag;
	}

	@Override
	public List<BranchParameterStatus> getAllBranchParameterStatusListByBranchId(String toBranchId) {

		return branchParameterStatusDao.getAllBranchParameterStatusListByBranchId(toBranchId);
	}

	@Override
	public BranchParameterStatus findEODTotalDetailsByBranchId(String branchId) {

		return branchParameterStatusDao.findEODTotalDetailsByBranchId(branchId);
	}

	@Override
	public boolean cancelEODApprovalForBranch(BranchManagement branchManagement, String status) {
		boolean flag = false;
		List<BranchParameterStatus> branchParameterStatusList = branchManagement.getBranchParameterStatuses();
		for (BranchParameterStatus branchParameterStatus : branchParameterStatusList) {
			branchParameterStatus.setStatus(status);
			branchParameterStatus.setModifiedBy(mySession.getUser().getId());
			branchParameterStatus.setModifiedTime(new Date());
			branchParameterStatus.setIp(mySession.getIp());
		}
		flag = true;
		return flag;
	}

	@Override
	public boolean approveEODApprovalForBranch(BranchManagement branchManagement, String status) {
		boolean flag = false;
		List<BranchParameterStatus> branchParameterStatusList = branchManagement.getBranchParameterStatuses();
		for (BranchParameterStatus branchParameterStatus : branchParameterStatusList) {
			branchParameterStatus.setStatus(status);
			branchParameterStatus.setApproverFlag((branchParameterStatus.getApproverFlag() - 1));
			branchParameterStatus.setModifiedBy(mySession.getUser().getId());
			branchParameterStatus.setModifiedTime(new Date());
			branchParameterStatus.setIp(mySession.getIp());
		}
		flag = true;
		return flag;
	}

	@Override
	public void autoApproveEODRequestForDashboardProcess() {

		List<BranchParameterStatus> branchParameterStatusList = branchParameterStatusDao
				.findAllBranchParameterStatusEligibleForAutoApproval();
		for (BranchParameterStatus branchParameterStatus : branchParameterStatusList) {
			try {
				log.info("for auto approve branch parameter | "
						+ branchParameterStatus.getBranchParameter().getParameterName() + " branch name | "
						+ branchParameterStatus.getBranchManagement().getBranchName());
				log.info("branchParam by branch is : "
						+ branchParameterStatus.getBranchManagement().getBranchParameterStatuses().size());
				Long currentTimeInMillis = System.currentTimeMillis();
				BigInteger currentTime = BigInteger.valueOf(currentTimeInMillis);
				int response = currentTime.compareTo(branchParameterStatus.getEodHaultTime());
				if (response == 1) {
					for (BranchParameterStatus branchParameterStatus2 : branchParameterStatus.getBranchManagement()
							.getBranchParameterStatuses()) {
						branchParameterStatus2.setStatus("APPROVED");
						branchParameterStatus2.setApproverFlag(0);
						branchParameterStatus2.setModifiedTime(new Date());
						branchParameterStatus2.setProcessedBy("AUTOMATIC");
					}

					/**
					 * Filtering out total amy from non issuable notes......
					 */
					BranchParameterStatus branchParameterStatusModified = new BranchParameterStatus();
					List<BranchParameterStatus> branchParamStatusFullList = branchParameterStatus.getBranchManagement()
							.getBranchParameterStatuses();
					/*
					 * branchParameterStatusModified =
					 * getModifiedWithoutNonIssuableTotal(
					 * branchParamStatusFullList, branchParameterStatus);
					 */
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

					/**
					 * inserting into dashboard table.....
					 */
					BranchManagement branchManagement = branchParameterStatus.getBranchManagement();

					Double minimumAmt = branchManagement.getMinThresholdAmount();
					Double maximumAmt = branchManagement.getMaxThresholdAmount();
					Double branchCashLimit = branchManagement.getBranchCashlimit();
					Double eodTotal = branchParameterStatusModified.getTotal();
					Double totalrequestedAmt = 0.0;
					String eodPosition = "level";
					log.info("min amt - " + minimumAmt + " max amt - " + maximumAmt + " cashlimit amt - "
							+ branchCashLimit + " branch eod total - " + eodTotal + " for the branch id : "
							+ branchManagement.getId() + " original total : " + branchParameterStatus.getTotal()
							+ " modified after non issuable subtraction its : "
							+ branchParameterStatusModified.getTotal());
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
					dashboardFinalBid.setModifiedTime(new Date());
					dashboardFinalBid.setCreatedTime(new Date());
					dashboardFinalBid.setIp(mySession.getIp());

					dashboardFinalBid.setRequestStatus(utility.getRequestStatusValue("APPROVED"));

					dashboardFinalBidService.save(dashboardFinalBid);
				}

			} catch (Exception e) {
				log.info("Exception generated at autoApproveEODRequestForDashboardProcess method for branch id : "
						+ branchParameterStatus.getBranchManagement().getId() + " due to : " + e);
			}
		}

		log.info("list size for auto approval branch param status is : | : " + branchParameterStatusList.size());

	}

	public BranchParameterStatus getModifiedWithoutNonIssuableTotal(
			List<BranchParameterStatus> branchParamStatusFullList, BranchParameterStatus branchParameterStatusTotal) {
		BranchParameterStatus branchParameterStatusModified = new BranchParameterStatus();
		for (BranchParameterStatus branchParameterStatus2 : branchParamStatusFullList) {
			log.info("branch param : " + branchParameterStatus2.getBranchParameter().getParameterName()
					+ " and total value : " + branchParameterStatus2.getTotal() + " 2000n | "
					+ branchParameterStatus2.getDn2000() + " 500n | " + branchParameterStatus2.getDn500() + " 10c | "
					+ branchParameterStatus2.getDc10());
			if (branchParameterStatus2.getBranchParameter().getParameterAbbreviation()
					.equals("Non issuable notes- (All Denominations)")) {
				log.info("inside non issuable notes....." + branchParameterStatus2.getDn2000());
				if (branchParameterStatus2.getDn2000() != null) {
					branchParameterStatusModified.setDn2000(
							branchParameterStatusTotal.getDn2000() - branchParameterStatusModified.getDn2000());
				} else {
					branchParameterStatusModified.setDn2000(branchParameterStatusTotal.getDn2000());
				}
			}
		}
		/**
		 * This is incomplete method.....
		 */
		return branchParameterStatusModified;
	}

	@Override
	public Object branchStatusReport(User user) {
		// TODO Auto-generated method stub
		BranchManagement branchManagement = mySession.getUser().getBranchManagement();
		List<BranchManagement> branchManagementList = new ArrayList<>();
		branchManagementList.add(branchManagement);
		utility.getAllChildBranchList(branchManagement, branchManagementList);

		List<String> branchIdList = branchManagementList.stream().map(b -> b.getId()).collect(Collectors.toList());

		return branchParameterStatusDao.branchStatusReport(user, branchIdList);
	}

	@Override
	public Denomination getBranchCurrentCashPosition(String branchManagementId) {

		// TODO Auto-generated method stub

		BranchParameterStatus eod = branchParameterStatusDao.findEODTotalDetailsByBranchId(branchManagementId);
		BranchHoldingAmount holdingAmount = branchHoldingAmountDao.getHoldingAmountByBranchId(branchManagementId);

		if (eod == null) {
			eod = new BranchParameterStatus();
		}
		if (holdingAmount == null) {
			holdingAmount = new BranchHoldingAmount();
		}

		Denomination denomination = new Denomination();
		if (eod.getTotal() != null && holdingAmount.getAmount() != null) {

			denomination
					.setAmount(BigDecimal.valueOf(eod.getTotal()).toBigInteger().subtract(holdingAmount.getAmount()));
		} else if (eod.getTotal() != null) {
			denomination.setAmount(BigDecimal.valueOf(eod.getTotal()).toBigInteger());
		} else if (holdingAmount.getAmount() != null) {
			denomination.setAmount(holdingAmount.getAmount());
		} else {
			denomination.setAmount(BigDecimal.valueOf(0).toBigInteger());
		}
		denomination.setDn1(nullCheck(eod.getDc1()) - nullCheck(holdingAmount.getDn1()));
		denomination.setDn2(nullCheck(eod.getDc2()) - nullCheck(holdingAmount.getDn2()));
		denomination.setDn5(nullCheck(eod.getDc5()) + nullCheck(eod.getDn5()) - nullCheck(holdingAmount.getDn5()));
		denomination.setDn10(nullCheck(eod.getDn10()) + nullCheck(eod.getDc10()) - nullCheck(holdingAmount.getDn10()));
		denomination.setDn20(nullCheck(eod.getDn20()) - nullCheck(holdingAmount.getDn20()));
		denomination.setDn50(nullCheck(eod.getDn50()) - nullCheck(holdingAmount.getDn50()));
		denomination.setDn100(nullCheck(eod.getDn100()) - nullCheck(holdingAmount.getDn100()));
		denomination.setDn500(nullCheck(eod.getDn500()) - nullCheck(holdingAmount.getDn500()));
		denomination.setDn2000(nullCheck(eod.getDn2000()) - nullCheck(holdingAmount.getDn2000()));
		return denomination;
	}

	public Integer nullCheck(Integer param) {
		// if (param == null) {
		// return 0;
		// }
		// return param;
		return ((param == null) ? 0 : param);
	}

}
