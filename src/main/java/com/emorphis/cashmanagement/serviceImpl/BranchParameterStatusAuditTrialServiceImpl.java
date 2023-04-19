package com.emorphis.cashmanagement.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.BranchManagementDao;
import com.emorphis.cashmanagement.dao.BranchParameterStatusAuditTrialDao;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatusAuditTrial;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.BranchParameterStatusAuditTrialService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class BranchParameterStatusAuditTrialServiceImpl implements BranchParameterStatusAuditTrialService {

	@Autowired
	BranchParameterStatusAuditTrialDao dao;
	
	@Autowired
	BranchManagementDao branchManagementDao;
	
	@Autowired
	Utility utility;

	@Autowired
	MySession mySession;
	

	public List<BranchParameterStatusAuditTrial> getBranchBetweenDatesByBranchId(String branchId ,String[] branchParameterIds, Date fromDate, Date toDate) {
		BranchManagement branch = branchManagementDao.findById(branchId);
		List<BranchManagement> branchList = new ArrayList<BranchManagement>();
		branchList.add(branch);
		branchList = utility.getAllChildBranchList(branch, branchList);
		
		return dao.getBranchBetweenDatesByBranchIds(branchList, branchParameterIds, fromDate, toDate);
	}
	
	public List<BranchParameterStatusAuditTrial> getBranchBetweenDatesByHierarchy(String hierarchyId,String[] branchParameterIds, Date fromDate, Date toDate) {
		
		
		List<BranchManagement> list= branchManagementDao.getAllBranchManagementListByHierarchyId(hierarchyId);
		
		List<BranchManagement> branchList = new ArrayList<BranchManagement>();
		
		for (BranchManagement branchManagement : list) {
			branchList.add(branchManagement);
			branchList = utility.getAllChildBranchList(branchManagement, branchList);
		}	
		
		return dao.getBranchBetweenDatesByBranchIds(branchList, branchParameterIds, fromDate, toDate);
	}
	

	
	@Override
	public Object branchStatusReport(User user) {
		// TODO Auto-generated method stub
		BranchManagement branchManagement=mySession.getUser().getBranchManagement();
		List<BranchManagement> branchManagementList = new ArrayList<>();
		branchManagementList.add(branchManagement);
		utility.getAllChildBranchList(branchManagement, branchManagementList);
		
		List<String> branchIdList= branchManagementList.stream().map(b-> b.getId()).collect(Collectors.toList());
		
		return dao.branchStatusReport(user,branchIdList);	
	}
}
