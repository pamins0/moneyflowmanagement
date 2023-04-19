package com.emorphis.cashmanagement.service;

import java.util.Date;
import java.util.List;

import com.emorphis.cashmanagement.model.DashboardFinalBidsAuditTrial;

public interface DashboardFinalBidsAuditTrialService {

	DashboardFinalBidsAuditTrial findById(Integer id);
	List<DashboardFinalBidsAuditTrial> getByStatus(String status);
	List<DashboardFinalBidsAuditTrial> getBids(Integer branchId,String status);
	List<DashboardFinalBidsAuditTrial> getBids(Date fromDate, Date toDate, String branchId, String status);
	List<DashboardFinalBidsAuditTrial> getBidsByHierarchyId(Date fromDate, Date toDate, String hierarchyId, String status);
	List<DashboardFinalBidsAuditTrial> getByHierarchyIdAndPosition(Date fromDate, Date toDate, String hierarchyId, String position);
	List<DashboardFinalBidsAuditTrial> getBidsByBranchIdAndPosition(Date fromDate, Date toDate, String branchId, String position);
	List<DashboardFinalBidsAuditTrial> getByPosition(String position);
	List<DashboardFinalBidsAuditTrial> getByPositionAndStatus(String position, String status);
}
  