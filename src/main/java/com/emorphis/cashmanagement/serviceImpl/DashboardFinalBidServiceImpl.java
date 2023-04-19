package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.DashboardFinalBidDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBid;
import com.emorphis.cashmanagement.model.RequestAjax;
import com.emorphis.cashmanagement.model.RequestStatus;
import com.emorphis.cashmanagement.service.DashboardFinalBidService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class DashboardFinalBidServiceImpl implements DashboardFinalBidService {

	
	@Autowired
	DashboardFinalBidDao dashboardFinalBidDao;
	
	@Autowired
	Utility utility;

	@Override
	public List<DashboardFinalBid> getAllBranchesListhavingExcessCash() {
		return dashboardFinalBidDao.getAllBranchesListhavingExcessCash();
	}

	@Override
	public List<DashboardFinalBid> getAllBranchesListhavingExcessAndBelowCash() {
		return dashboardFinalBidDao.getAllBranchesListhavingExcessAndBelowCash();
	}

	@Override
	public List<DashboardFinalBid> getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid() {

		return dashboardFinalBidDao.getAllBranchesListhavingExcessAndBelowCashFromDashboardFinalBid();
	}

	@Override
	public boolean deleteAllOldBranchEntry(BranchManagement branchManagement) {

		return dashboardFinalBidDao.deleteAllOldBranchEntry(branchManagement);
	}

	@Override
	public DashboardFinalBid findDashboardByBranchId(String branchId) {

		return dashboardFinalBidDao.findDashboardByBranchId(branchId);
	}

	@Override
	public DashboardFinalBid findDashboardByBranchIdAndDashboardId(int dashboardId, String branchId) {
		return dashboardFinalBidDao.findDashboardByBranchIdAndDashboardId(dashboardId, branchId);
	}

	@Override
	public boolean updateDashboardBidStatusForWithdrawn(RequestAjax requestAjax) {
		DashboardFinalBid dashboardFinalBid = findDashboardByBranchIdAndDashboardId(
				Integer.parseInt(requestAjax.getDashboardId()), requestAjax.getFromBranchId());		
		if (!dashboardFinalBid.getRequestStatus().getAlias().equals("APPROVED")) {
			System.out.println("Inside if bid is already has some other status : " + dashboardFinalBid.getRequestStatus().getAlias());
			requestAjax.setMessage("Bid Already " + dashboardFinalBid.getRequestStatus().getAlias());
			return false;
		} else {
			RequestStatus requestStatus = utility.getRequestStatusValue("WITHDRAWN");
			dashboardFinalBid.setRequestStatus(requestStatus);
			dashboardFinalBid.setModifiedTime(new Date());
			requestAjax.setMessage("Bid Withdrwan Successfully");
			return true;
		}
	}

	@Override
	public void updateById(DashboardFinalBid dashboardFinalBid) {
		System.out.println("dashboard id for updation is : " + dashboardFinalBid.getId());
		DashboardFinalBid dashboardFinalBid2 = dashboardFinalBidDao.findById(dashboardFinalBid.getId());

		dashboardFinalBid2.setTotal(dashboardFinalBid.getTotal());
		dashboardFinalBid2.setPosition(dashboardFinalBid.getPosition());
		RequestStatus requestStatus = null;
		if (dashboardFinalBid.getSaveBtn().equals("Save")) {
			requestStatus = utility.getRequestStatusValue("APPROVED");
			System.out.println("request status value : "+requestStatus.getAlias()); 
			dashboardFinalBid2.setRequestStatus(requestStatus);
		} else if (dashboardFinalBid.getSaveBtn().equals("Cancel")) {
			requestStatus = utility.getRequestStatusValue("CANCEL");
			dashboardFinalBid2.setRequestStatus(requestStatus); 
		}
	}
	
	@Override
	public void save(DashboardFinalBid dashboardFinalBid) throws Exception {
		dashboardFinalBidDao.save(dashboardFinalBid);		
	}
}
