package com.emorphis.cashmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emorphis.cashmanagement.service.OrgManagementAuditTrailService;

@Controller
@RequestMapping("/")
public class OrgManagementAuditTrailController {

	@Autowired
	OrgManagementAuditTrailService orgManagementAuditTrailService;


	@RequestMapping(value = { "/orgmanagementaudittrail", "/orgManagementAuditTrail" }, method = RequestMethod.GET)
	public String orgManagement(ModelMap model) {

		/*OrgManagementAuditTrail orgManagementAuditTrail = new OrgManagementAuditTrail();
		model.addAttribute("orgManagementAuditTrail", orgManagementAuditTrail);
		model.addAttribute("edit", false);
		model.addAttribute("orgManagementAuditTrailList", orgManagementAuditTrailService.getAllOrgManagementAuditTrailList());
		return "orgManagementAuditTrail";*/
		return "";
	}

}
