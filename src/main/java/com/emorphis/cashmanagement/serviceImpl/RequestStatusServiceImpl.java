package com.emorphis.cashmanagement.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.RequestStatusDao;
import com.emorphis.cashmanagement.model.RequestStatus;
import com.emorphis.cashmanagement.service.RequestStatusService;

@Repository
@Transactional
public class RequestStatusServiceImpl implements RequestStatusService {

	@Autowired
	RequestStatusDao requestStatusDao;

	@Override
	public List<RequestStatus> getAllRequestStatusList() {
		List<RequestStatus> requestStatusList = null;
		requestStatusList = requestStatusDao.getAllRequestStatus();
		return requestStatusList;
	} 
}
