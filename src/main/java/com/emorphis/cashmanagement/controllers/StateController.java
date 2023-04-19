package com.emorphis.cashmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emorphis.cashmanagement.model.State;
import com.emorphis.cashmanagement.service.StateService;

@Controller
@RequestMapping("/")
public class StateController {

	@Autowired
	StateService service;

	@RequestMapping(value = { "/statelist" }, produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody List<State> getStateList(@RequestParam("tagName") String tagName,ModelMap model) {
		System.out.println("statelist calling::"+tagName);
		List<State> stateList=service.getStateList(tagName);
		System.out.println(""+stateList.size());
		return stateList;
	}

	@RequestMapping(value = { "/stateTest" }, method = RequestMethod.GET)
	public String getStatetest(ModelMap model) {
		// service.getStateList(id);

		return "stateTest";
	}
}
