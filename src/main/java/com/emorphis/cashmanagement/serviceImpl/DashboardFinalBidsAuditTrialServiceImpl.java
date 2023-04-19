package com.emorphis.cashmanagement.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.BranchManagementDao;
import com.emorphis.cashmanagement.dao.DashboardFinalBidsAuditTrialDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBidsAuditTrial;
import com.emorphis.cashmanagement.service.DashboardFinalBidsAuditTrialService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class DashboardFinalBidsAuditTrialServiceImpl implements DashboardFinalBidsAuditTrialService {

	@Autowired
	DashboardFinalBidsAuditTrialDao auditTrialDao;

	@Autowired
	BranchManagementDao branchManagementDao;

	@Autowired
	Utility utility;

	public DashboardFinalBidsAuditTrial findById(Integer id) {
		return auditTrialDao.findById(id);
	}

	public List<DashboardFinalBidsAuditTrial> getByStatus(String status) {
		return auditTrialDao.getByStatus(status);
	}

	public List<DashboardFinalBidsAuditTrial> getBids(Integer branchId, String status) {
		return auditTrialDao.getBids(branchId, status);
	}

	public List<DashboardFinalBidsAuditTrial> getBids(Date fromDate, Date toDate, String branchId, String status) {
		return auditTrialDao.getBids(fromDate, toDate, branchId, status);
	}

	public List<DashboardFinalBidsAuditTrial> getByPosition(String position) {
		return auditTrialDao.getByPosition(position);
	}

	public List<DashboardFinalBidsAuditTrial> getByPositionAndStatus(String position, String status) {
		return auditTrialDao.getByPositionAndStatus(position, status);
	}

	@Override
	public List<DashboardFinalBidsAuditTrial> getBidsByHierarchyId(Date fromDate, Date toDate, String hierarchyId,
			String status) {

		List<BranchManagement> list = branchManagementDao.getAllBranchManagementListByHierarchyId(hierarchyId);
		List<BranchManagement> branchList = new ArrayList<BranchManagement>();
		for (BranchManagement branchManagement : list) {
			branchList.add(branchManagement);
			branchList = utility.getAllChildBranchList(branchManagement, branchList);
		}
		return auditTrialDao.getBids(fromDate, toDate, branchList, status);
	}

	@Override
	public List<DashboardFinalBidsAuditTrial> getByHierarchyIdAndPosition(Date fromDate, Date toDate,
			String hierarchyId, String position) {

		List<BranchManagement> list = branchManagementDao.getAllBranchManagementListByHierarchyId(hierarchyId);

		List<BranchManagement> branchList = new ArrayList<BranchManagement>();

		for (BranchManagement branchManagement : list) {
			branchList.add(branchManagement);
			branchList = utility.getAllChildBranchList(branchManagement, branchList);
		}

		return auditTrialDao.getBidsByPosition(fromDate, toDate, branchList, position);
	}

	public List<DashboardFinalBidsAuditTrial> getBidsByBranchIdAndPosition(Date fromDate, Date toDate, String branchId, String position) {
		List<BranchManagement> branchIds = new ArrayList<>();
		BranchManagement branchManagement =  branchManagementDao.findById(branchId);
		branchIds.add(branchManagement);		
		return auditTrialDao.getBidsByPosition(fromDate, toDate, branchIds, position);
	}

}
