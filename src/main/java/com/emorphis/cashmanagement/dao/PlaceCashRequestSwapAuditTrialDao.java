package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwapAuditTrial;

public interface PlaceCashRequestSwapAuditTrialDao {

	List<PlaceCashRequestSwapAuditTrial> getAllSwapBidsAuditTraislBySwapPlaceBidId(
			PlaceCashRequestSwap placeCashRequestSwap); 

}
