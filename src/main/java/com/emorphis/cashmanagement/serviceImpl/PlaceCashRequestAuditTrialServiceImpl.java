package com.emorphis.cashmanagement.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.PlaceCashRequestAuditTrialDao;
import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.model.PlaceCashRequestAuditTrial;
import com.emorphis.cashmanagement.service.PlaceCashRequestAuditTrialService;

@Transactional
@Repository
public class PlaceCashRequestAuditTrialServiceImpl implements PlaceCashRequestAuditTrialService {

	@Autowired
	PlaceCashRequestAuditTrialDao placeCashRequestAuditTrialDao;
	
	@Override
	public List<PlaceCashRequestAuditTrial> getAllPlacedBidsAuditTraislByPlaceBidId(PlaceCashRequest placeCashRequest) {
		
		return placeCashRequestAuditTrialDao.getAllPlacedBidsAuditTraislByPlaceBidId(placeCashRequest);
	}
}
