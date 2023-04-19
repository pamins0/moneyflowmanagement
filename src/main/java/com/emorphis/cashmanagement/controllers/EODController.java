package com.emorphis.cashmanagement.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.Utility;

@Controller
public class EODController {

	private static final Logger log = LoggerFactory.getLogger(EODController.class);

	@Autowired
	UtilityService utilityService;

	@Autowired
	MySession mySession;

	@Autowired
	MessageSource messageSource;

	@Autowired
	Utility utility;

	@RequestMapping(value = { "/eodmanagement" }, method = RequestMethod.GET)
	public String eodManagement(ModelMap model) {
		System.out.println("Inside get method of eodmanage : ");
		if (mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyType() == 0
				&& mySession.getUser().getBranchManagement().getIsgroup() == 0) {
			log.info("Inside if when user is not of branch level and not a currency chest ");
			
			
			
			
			
			return "eod";
		}
		return "redirect:/home";
	}
	
	@RequestMapping(value = { "/vaultmanagement" }, method = RequestMethod.GET)
	public String vaultManagement(ModelMap model) {
		System.out.println("Inside get method of eodmanage : ");
		if (mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyType() == 0
				&& mySession.getUser().getBranchManagement().getIsgroup() == 0) {
			log.info("Inside if when user is not of branch level and not a currency chest ");
			
			
			
			
			
			return "vault";
		}
		return "redirect:/home";
	}
	

}
