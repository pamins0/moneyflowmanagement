package com.emorphis.cashmanagement.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.StateDao;
import com.emorphis.cashmanagement.model.State;
import com.emorphis.cashmanagement.service.StateService;

@Repository
@Transactional
public class StateServiceImpl implements StateService {

	@Autowired 
	StateDao dao;
	@Override
	public List<State> getStateList( String tagName) {
		
		return dao.getStateList(tagName);
	}

}
