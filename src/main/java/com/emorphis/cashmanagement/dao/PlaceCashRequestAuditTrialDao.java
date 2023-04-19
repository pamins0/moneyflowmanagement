package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.model.PlaceCashRequestAuditTrial;

public interface PlaceCashRequestAuditTrialDao {

	List<PlaceCashRequestAuditTrial> getAllPlacedBidsAuditTraislByPlaceBidId(PlaceCashRequest placeCashRequest);

}
