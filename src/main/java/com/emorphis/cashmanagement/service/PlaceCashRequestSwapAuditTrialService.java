package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwapAuditTrial;

public interface PlaceCashRequestSwapAuditTrialService {

	List<PlaceCashRequestSwapAuditTrial> getAllSwapBidsAuditTraislBySwapPlaceBidId(
			PlaceCashRequestSwap placeCashRequestSwap);
 
}
