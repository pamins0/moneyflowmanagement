package com.emorphis.cashmanagement.dao;

import java.util.Date;
import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.DashboardFinalBidsAuditTrial;

public interface DashboardFinalBidsAuditTrialDao {
	DashboardFinalBidsAuditTrial findById(int id);
	List<DashboardFinalBidsAuditTrial> getByStatus(String status);
	List<DashboardFinalBidsAuditTrial> getBids(Integer branchId,String status);
	List<DashboardFinalBidsAuditTrial> getBids(Date fromDate, Date toDate, String branchId, String status);
	List<DashboardFinalBidsAuditTrial> getBids(Date fromDate, Date toDate, List<BranchManagement> branchIds, String status);
	List<DashboardFinalBidsAuditTrial> getByPosition(String position);
	List<DashboardFinalBidsAuditTrial> getBidsByPosition(Date fromDate, Date toDate, List<BranchManagement> branchIds, String position);
	List<DashboardFinalBidsAuditTrial> getByPositionAndStatus(String position, String status);	
 }
 