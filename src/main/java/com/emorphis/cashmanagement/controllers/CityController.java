package com.emorphis.cashmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emorphis.cashmanagement.model.City;
import com.emorphis.cashmanagement.service.CityService;

@Controller
@RequestMapping("/")
public class CityController {

	@Autowired
	CityService service;

	@RequestMapping(value = { "/citybystate" }, produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody List<City> getStateList(@RequestParam("tagName") String tagName, @RequestParam("stateId") Integer stateId,
			ModelMap model) {
		System.out.println("call City::"+tagName+" for State::"+stateId);
		return service.getCityListByState(stateId,tagName);
	}

}
