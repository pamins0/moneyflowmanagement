package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.State;

public interface StateService {

	List<State> getStateList(String tagName);
}
