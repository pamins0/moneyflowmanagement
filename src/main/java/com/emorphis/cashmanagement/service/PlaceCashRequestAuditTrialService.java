package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.model.PlaceCashRequestAuditTrial;

public interface PlaceCashRequestAuditTrialService {

	List<PlaceCashRequestAuditTrial> getAllPlacedBidsAuditTraislByPlaceBidId(PlaceCashRequest placeCashRequest);
 
}
