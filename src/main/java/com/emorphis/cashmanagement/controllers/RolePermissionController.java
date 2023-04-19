package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emorphis.cashmanagement.model.Permission;
import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.service.PermissionService;
import com.emorphis.cashmanagement.service.RolePermissionService;
import com.emorphis.cashmanagement.service.RoleService;

@Controller
public class RolePermissionController {

	@Autowired
	RolePermissionService rolePermissionService;

	@Autowired
	RoleService roleService;

	@Autowired
	PermissionService permissionService;

	@RequestMapping(value = { "/editRolePermissionAssociation-{roleId}" }, method = RequestMethod.GET)
	public String rolePermissionManagement(@PathVariable String roleId, ModelMap model) {
		System.out.println("Role id during permission assignment is : " + roleId);
		RolePermission rolePermission = new RolePermission();
		Role role = roleService.findById(roleId);
		rolePermission.setRole(role);
		List<RolePermission> rolePermissionList = rolePermissionService.getRolePermissionList(role);
		System.out.println("RolePermission List size = " + rolePermissionList.size());
		List<Permission> permissionList = permissionService.getAllPermissionList();
		System.out.println("permissionList size is : " + permissionList.size());

		/**
		 * Excluding the permission from Total PermissionList which are already
		 * assigned to a specific role
		 */

		Set<String> assignedhascodeSet = new HashSet<String>();
		Iterator<RolePermission> it1 = rolePermissionList.iterator();
		while (it1.hasNext()) {
			RolePermission rolePermission2 = (RolePermission) it1.next();
			Permission permission2 = rolePermission2.getPermission();
			// System.out.println("Assigned Permission hashcode : " +
			// permission2.hashCode());
			assignedhascodeSet.add(permission2.getId());
		}

		List<Permission> notAssignedPermissionList = new ArrayList<Permission>();
		List<Permission> assignedPermissionList = new ArrayList<Permission>();
		Iterator<Permission> it = permissionList.iterator();
		while (it.hasNext()) {
			Permission permission = (Permission) it.next();
			if (!assignedhascodeSet.contains(permission.getId())) {
				// System.out.println("Permission hascode : " +
				// permission.hashCode());
				notAssignedPermissionList.add(permission);
			} else {
				assignedPermissionList.add(permission);
			}
		}

		/**
		 * End of exclusion
		 */

		model.addAttribute("permissionList", notAssignedPermissionList);
		model.addAttribute("edit", true);
		model.addAttribute("rolePermission", rolePermission);
		model.addAttribute("rolePermissionList", rolePermissionList);
		/**
		 * Changed this commented code to below written code for temporary
		 * usage......
		 */
	//	model.addAttribute("assignedRolePermissionList", assignedPermissionList);

		System.out.println("Assigned Role permission list size : " + rolePermissionList.size());
		return "rolepermissionmanager";

	}

	@RequestMapping(value = { "/saveRolePermissionAssociation" }, method = RequestMethod.POST)
	public String saveRolePermissionAssociation(@Valid RolePermission rolePermission, BindingResult result,
			ModelMap model) {
		System.out.println("Role Id during saving : " + rolePermission.getRole().getId());
		if (null != rolePermission && !rolePermission.getRole().getId().equals("0")) {
			System.out.println("Rolepermission role id : " + rolePermission.getRole().getId());
			rolePermissionService.updateRolePermission(rolePermission);
		}
		return "redirect:/rolemanagement";
	}
}
