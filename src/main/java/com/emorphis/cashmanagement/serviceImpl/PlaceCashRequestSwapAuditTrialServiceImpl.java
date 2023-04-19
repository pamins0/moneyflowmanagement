package com.emorphis.cashmanagement.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.PlaceCashRequestSwapAuditTrialDao;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwapAuditTrial;
import com.emorphis.cashmanagement.service.PlaceCashRequestSwapAuditTrialService;

@Repository
@Transactional
public class PlaceCashRequestSwapAuditTrialServiceImpl implements PlaceCashRequestSwapAuditTrialService {
	
	@Autowired
	PlaceCashRequestSwapAuditTrialDao placeCashRequestSwapAuditTrialDao;
	
	@Override
	public List<PlaceCashRequestSwapAuditTrial> getAllSwapBidsAuditTraislBySwapPlaceBidId(
			PlaceCashRequestSwap placeCashRequestSwap) {
		
		return placeCashRequestSwapAuditTrialDao.getAllSwapBidsAuditTraislBySwapPlaceBidId(placeCashRequestSwap);
	}
}
