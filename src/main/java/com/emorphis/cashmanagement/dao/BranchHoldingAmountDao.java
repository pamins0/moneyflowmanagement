package com.emorphis.cashmanagement.dao;

import com.emorphis.cashmanagement.model.BranchHoldingAmount;

public interface BranchHoldingAmountDao {

	/**
	 * @param branchManagementId
	 * @return
	 */
	BranchHoldingAmount getHoldingAmountByBranchId(String branchManagementId);

	/**
	 * @param branchHoldingAmount
	 */
	int saveOrUpdateBranchHoldingAmount(BranchHoldingAmount branchHoldingAmount);
	
	
	
}
