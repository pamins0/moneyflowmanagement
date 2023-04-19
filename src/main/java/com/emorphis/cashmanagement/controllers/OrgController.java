package com.emorphis.cashmanagement.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;

@Controller
public class OrgController {

	@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	OrgManagementService orgManagementService;
	
	@Autowired
	MessageSource messageSource;
	
	/*
	 * @RequestMapping(value = { "/404" }, method = RequestMethod.GET) public
	 * String pageNotFound(ModelMap model) { return "404"; }
	 */

	@PreAuthorize("hasAnyRole('can_orgtype_create','can_orgtype_read')")
	@RequestMapping(value = { "/orgtypes" }, method = RequestMethod.GET)
	public String orgTypesGetMethod(ModelMap model) {
		OrgType orgType = new OrgType();
		model.addAttribute("orgType", orgType);
		model.addAttribute("edit", false);
		model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
	/*	boolean flagval = (Boolean)flag;
		if(flagval){
			model.addAttribute("usernotauthorize", messageSource.getMessage("user.not.authoriz", null, Locale.ENGLISH));
		}*/
		return "orgtype";
	}

	@PreAuthorize("hasAnyRole('can_orgtype_create','can_orgtype_read')")
	@RequestMapping(value = { "/orgtypes" }, method = RequestMethod.POST)
	public String orgTypesPostMethod(@Valid OrgType orgType, BindingResult result, ModelMap model) {
		/*if (result.hasErrors()) {
			System.out.println("Inside error in result......");
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			return "orgtype";
		}*/
		if (result.hasFieldErrors("orgType")) { 
			System.out.println("Inside error in result......");
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			model.addAttribute("orgType", orgType);
			model.addAttribute("edit", false);
			return "orgtype";
		}
		// saving organization detail in to DB
		orgTypeService.save(orgType);
		return "redirect:/orgtypes";
	}

	@PreAuthorize("hasAnyRole('can_orgtype_delete')")
	@RequestMapping(value = { "/orgtype-{id}-delete" }, method = RequestMethod.GET)
	public String orgTypeDelete(@PathVariable String id, ModelMap model, RedirectAttributes redirectAttributes) { 		
		boolean flag = orgTypeService.deleteById(id);
//		boolean flag = orgTypeService.deleteByUUID(uuid);
		if(!flag){
			redirectAttributes.addAttribute("error_NotAuthorize", true);
		}
		return "redirect:/orgtypes";
	}

	@PreAuthorize("hasAnyRole('can_orgtype_update')")
	@RequestMapping(value = { "/orgtype-{id}-edit" }, method = RequestMethod.GET)
	public String orgTypesEditGetMethod(@PathVariable String id, ModelMap model) {
		OrgType orgType = orgTypeService.findById(id);
//		OrgType orgType = orgTypeService.findByUUID(uuid);
		if (orgType != null) {
			model.addAttribute("orgType", orgType);
			model.addAttribute("edit", true);
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			return "orgtype";
		} else {
			return "redirect:/orgtypes";
		}
	}

	@PreAuthorize("hasAnyRole('can_orgtype_update')")
	@RequestMapping(value = { "/orgtype-{id}-edit" }, method = RequestMethod.POST)
	public String orgTypesEditPostMethod(@Valid OrgType orgType, BindingResult result, @PathVariable String id, ModelMap model) {
		/*if (result.hasErrors()) {
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			return "orgtype";
		}*/
		if (result.hasFieldErrors("orgType")) { 
			System.out.println("Inside error in result......");
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			model.addAttribute("orgType", orgType);
			model.addAttribute("edit", true);
			return "orgtype";
		}
		orgTypeService.updatebyId(orgType);
//		orgTypeService.updatebyUUID(orgType);
		return "redirect:/orgtypes";
	}
	

	@PreAuthorize("hasAnyRole('can_orgtype_read')")
	@RequestMapping(value = { "/orgtype-{id}-view" }, method = RequestMethod.GET)
	public String orgTypesViewGetMethod(@PathVariable String id, ModelMap model) {
		OrgType orgType = orgTypeService.findById(id);
//		OrgType orgType = orgTypeService.findByUUID(uuid);
		System.out.println("uuid for the view of orgtype is : "+id);
		if (orgType != null) {
			model.addAttribute("orgType", orgType);
			model.addAttribute("edit", true);
//			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			return "vieworgtype";
		} else {
			return "redirect:/orgtypes";
		}
	}

}
