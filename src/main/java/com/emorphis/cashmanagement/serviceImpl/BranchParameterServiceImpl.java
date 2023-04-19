package com.emorphis.cashmanagement.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.BranchParameterDao;
import com.emorphis.cashmanagement.model.BranchParameter;
import com.emorphis.cashmanagement.service.BranchParameterService;

@Repository
@Transactional
public class BranchParameterServiceImpl implements BranchParameterService {

	@Autowired
	BranchParameterDao branchParameterDao;

	@Override
	public List<BranchParameter> getAllBranchParametersList() {
		return branchParameterDao.getAllBranchParametersList();
	}
}
