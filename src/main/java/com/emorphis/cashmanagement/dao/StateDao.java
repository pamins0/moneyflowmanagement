package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.State;

public interface StateDao {

	List<State> getStateList(String tagName);
}
