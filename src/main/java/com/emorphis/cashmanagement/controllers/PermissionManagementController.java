package com.emorphis.cashmanagement.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.Permission;
import com.emorphis.cashmanagement.service.PermissionService;
import com.emorphis.cashmanagement.util.Utility;

@Controller
public class PermissionManagementController {

	@Autowired
	PermissionService permissionService;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@PreAuthorize("hasAnyRole('can_permission_read')")
	@RequestMapping(value = { "/permissionmanagement" }, method = RequestMethod.GET)
	public String permissionManagement(ModelMap model) {
		Permission permission = new Permission();
		model.addAttribute("permission", permission);
		List<Permission> permissionsList = permissionService.getPermissionList();
		System.out.println("permission list size : "+permissionsList.size());
		model.addAttribute("permissionsList", permissionsList);
		return "permissionmanagement";
	}

	@PreAuthorize("hasAnyRole('can_permission_create')")
	@RequestMapping(value = { "/permissionmanagement-new" }, method = RequestMethod.GET)
	public String permissionManagementNewGet(ModelMap model) {
		Permission permission = new Permission();
		model.addAttribute("permission", permission);
		model.addAttribute("edit", false);
		return "newpermissionmanagement";
	}

	@PreAuthorize("hasAnyRole('can_permission_create')")
	@RequestMapping(value = { "/permissionmanagement-new" }, method = RequestMethod.POST)
	public String permissionManagementNewPost(@RequestParam(value = "saveBtn") String action,
			@Valid Permission permission, BindingResult result, ModelMap model) {
		if (result.hasFieldErrors()) {
			System.out.println("Inside binding error in permission page fields are null or empty");
			// model.addAttribute("permission", permission);
			model.addAttribute("edit", false);
			return "newpermissionmanagement";
		}
		permissionService.savePermission(permission);
		if (action.equals("Save")) {	
		
			return "redirect:/permissionmanagement";
		} else if (action.equals("SaveAndNew")) {
			Permission permission2 = new Permission();		
			model.addAttribute("permission", permission2);
			model.addAttribute("edit", false);
			return "newpermissionmanagement";
		}		

		return "redirect:/permissionmanagement";
	}

	@PreAuthorize("hasAnyRole('can_permission_update')")
	@RequestMapping(value = { "/permissionmanagement-{id}-edit" }, method = RequestMethod.GET)
	public String permissionManagementEditGet(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			return "redirect:/permissionmanagement";
		}
		Permission permission = permissionService.findById(id);
		model.addAttribute("permission", permission);
		model.addAttribute("edit", true);

		return "newpermissionmanagement";
	}

	@PreAuthorize("hasAnyRole('can_permission_update')")
	@RequestMapping(value = { "/permissionmanagement-{id}-edit" }, method = RequestMethod.POST)
	public String permissionManagementEditPost(@Valid Permission permission, BindingResult result,
			@PathVariable String id, ModelMap model) {
		boolean flag = false;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			return "redirect:/permissionmanagement";
		}
		if (result.hasFieldErrors("module") || result.hasFieldErrors("title") || result.hasFieldErrors("keyVal")) {
			System.out.println("Inside binding error in permission page fields are null or empty");
			// model.addAttribute("permission", permission);
			model.addAttribute("edit", true);
			return "newpermissionmanagement";
		}
		permissionService.updateById(permission);

		return "redirect:/permissionmanagement";
	}

	@PreAuthorize("hasAnyRole('can_permission_delete')")
	@RequestMapping(value = { "/permissionmanagement-{id}-delete",
			"/permissionmanagement-{id}-Delete" }, method = RequestMethod.GET)
	public String branchmanagementDelete(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			return "redirect:/permissionmanagement";
		}
		permissionService.deleteById(id);
		return "redirect:/permissionmanagement";
	}
}
