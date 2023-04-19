package com.emorphis.cashmanagement.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.util.Utility;
import com.emorphis.cashmanagement.validations.OrgManagementValidator;
import com.emorphis.cashmanagement.validations.OrgTypeValidator;

@Controller
@RequestMapping("/")
public class OrgManagementController {
	
	private static final Logger log = LoggerFactory.getLogger(OrgManagementController.class);

	@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	OrgManagementService orgManagementService;
	
	@Autowired
	OrgTypeValidator orgTypeValidator;

	@Autowired
	OrgManagementValidator orgManagementValidator;
	
	@Autowired
	Utility utility;
	

	@PreAuthorize("hasAnyRole('can_orgmanagement_read')")
	@RequestMapping(value = { "/orgmanagement", "/orgManagement" }, method = RequestMethod.GET)
	public String orgManagement(ModelMap model) {
		OrgManagement orgManagement = new OrgManagement();
		model.addAttribute("orgManagement", orgManagement);
		model.addAttribute("edit", false);
		model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
		model.addAttribute("orgManagementList", orgManagementService.getAllOrgManagementList());
		return "orgManagement";
	}

	@PreAuthorize("hasAnyRole('can_orgmanagement_create')")
	@RequestMapping(value = { "/orgmanagement-new", "/orgManagement-new" }, method = RequestMethod.GET)
	public String newOrgManagement(ModelMap model) {

		OrgManagement orgManagement = new OrgManagement();
		model.addAttribute("orgManagement", orgManagement);
		model.addAttribute("edit", false);
		model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
		return "newEditOrgManagement";
	}

	@PreAuthorize("hasAnyRole('can_orgmanagement_create')")
	@RequestMapping(value = { "/orgmanagement-new", "/orgManagement-new" }, method = RequestMethod.POST)
	public String saveOrgManagementPostMethod(@Valid OrgManagement orgManagement, BindingResult result,
			ModelMap model) {
		System.out.println("Org type id on save of orgnaization management is : "+orgManagement.getOrgType().getId()); 
		orgManagementValidator.validate(orgManagement, result);
		if (result.hasErrors()) {
			System.out.println("Org type id on error binfing on save of orgnaization management is : "+orgManagement.getOrgType().getId());
			model.addAttribute("edit", false);
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			model.addAttribute("orgManagement", orgManagement);
			return "newEditOrgManagement";
		}
		
		log.info("Organization currency chest value is : "+orgManagement.isCurrencyChest()); 
		
		String generatedId = orgManagementService.save(orgManagement);
		if(generatedId != null){
			return "redirect:/addhierarchy-"+generatedId;
		}
		return "newEditOrgManagement";
	}

	@PreAuthorize("hasAnyRole('can_orgmanagement_delete')")
	@RequestMapping(value = { "/orgmanagement-{id}-delete" }, method = RequestMethod.GET)
	public String orgManagementDelete(@PathVariable String id, ModelMap model) {

		orgManagementService.deleteById(id);
		return "redirect:/orgmanagement";
	}

	@PreAuthorize("hasAnyRole('can_orgmanagement_update')")
	@RequestMapping(value = { "/orgmanagement-{id}-edit" }, method = RequestMethod.GET)
	public String orgManagementEditGetMethod(@PathVariable String id, ModelMap model) {
		OrgManagement orgManagement = orgManagementService.findById(id);
		boolean valid = false;
		valid = utility.allowUrlAccessToOrgManagement(orgManagement);
		if(!valid){
			return "redirect:/orgmanagement";
		}
		if (orgManagement != null) {
			model.addAttribute("orgManagement", orgManagement);
			model.addAttribute("edit", true);
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
		//	model.addAttribute("orgManagementList", orgManagementService.getAllOrgManagementList());
			return "newEditOrgManagement";
		} else {
			return "redirect:/orgmanagement";
		}
	}

	@PreAuthorize("hasAnyRole('can_orgmanagement_update')")
	@RequestMapping(value = { "/orgmanagement-{id}-edit" }, method = RequestMethod.POST)
	public String orgManagementEditPostMethod(@Valid OrgManagement orgManagement,BindingResult result,
			@PathVariable String id, ModelMap model) {
		System.out.println("String id to edit orgmanagement : "+id); 
	
		orgManagementValidator.validate(orgManagement, result);
	
		if (result.hasErrors()) {
			System.out.println("Org type id on error of edit post binding on save of orgnaization management is : "+orgManagement.getOrgType().getId());
			model.addAttribute("edit", true);
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			model.addAttribute("orgManagement", orgManagement);
			return "newEditOrgManagement";
		}
		orgManagementService.updatebyId(orgManagement);
//		orgManagementService.updatebyUUID(orgManagement); 
		return "redirect:/orgmanagement";
	}
	

	@PreAuthorize("hasAnyRole('can_orgmanagement_read')")
	@RequestMapping(value = { "/orgmanagement-{id}-view" }, method = RequestMethod.GET)
	public String orgManagementViewGetMethod(@PathVariable String id, ModelMap model) {
		OrgManagement orgManagement = orgManagementService.findById(id);
		if (orgManagement != null) {
			model.addAttribute("orgManagement", orgManagement);
			model.addAttribute("edit", true);
			return "viewOrgManagement";
		} else {
			return "redirect:/orgmanagement";
		}
	}

}
