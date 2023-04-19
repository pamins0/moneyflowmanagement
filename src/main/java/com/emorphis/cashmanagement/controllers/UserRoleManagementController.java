package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;
import com.emorphis.cashmanagement.service.RoleService;
import com.emorphis.cashmanagement.service.UserManagementService;
import com.emorphis.cashmanagement.service.UserRoleManagementService;

@Controller
public class UserRoleManagementController {

	private static final Logger log = LoggerFactory.getLogger(UserRoleManagementController.class);

	@Autowired
	UserRoleManagementService userRoleManagementService;

	@Autowired
	UserManagementService userManagementService;

	@Autowired
	RoleService roleService;

	@RequestMapping(value = { "/userRoleManagement" }, method = RequestMethod.GET)
	public String userRoleManagement(ModelMap model, @ModelAttribute("userObj") User user1) {
		try {
			UserRole userRole = new UserRole();
			User user = userManagementService.findById(user1.getId());
			System.out.println("url referer link is : " + user1.getUrlReferer());
			user.setUrlReferer(user1.getUrlReferer());
			userRole.setUser(user);
			if (null != user && !"0".equals(user.getId())) {
				List<UserRole> userRoleList = userRoleManagementService.getUserRoleList(user);
				System.out.println("UserRole List size = " + userRoleList.size() + " and user is : " + user);
				System.out.println("user hierarchy is : " + user.getBranchManagement().getHierarchyControl());
				// List<Role> roleList = roleService.getAllRoleList();
				List<Role> roleList = roleService.getAllRoleListByUserHierarchy(user);
				model.addAttribute("edit", true);
				model.addAttribute("userRole", userRole);

				Set<String> assignedhascodeSet = new HashSet<String>();
				Iterator<UserRole> it1 = userRoleList.iterator();
				while (it1.hasNext()) {
					UserRole userRole2 = it1.next();
					Role role2 = userRole2.getRole();
					assignedhascodeSet.add(role2.getId());
				}

				List<Role> notAssignedRoleList = new ArrayList<Role>();
				List<Role> assignedRoleList = new ArrayList<Role>();

				Iterator<Role> it = roleList.iterator();
				while (it.hasNext()) {
					Role role = it.next();
					if (!assignedhascodeSet.contains(role.getId())) {
						notAssignedRoleList.add(role);
					} else {
						assignedRoleList.add(role);
					}
				}

				// model.addAttribute("roleList", roleList);
				model.addAttribute("roleList", notAssignedRoleList);
				model.addAttribute("userRoleList", userRoleList);
				System.out.println("rolelist size is : " + roleList.size());
				model.addAttribute("byDirectRoleAssign", true);
				return "userrolemanager";
			} else {
				return "redirect:/usermanagement";
			}
		} catch (Exception e) {
			log.error("Exception generated in UserRoleManagement class in userRoleManagement method due to : " + e);
			return "redirect:/usermanagement";
		}
	}

	@RequestMapping(value = { "/editRoleAssociation-{userId}" }, method = RequestMethod.GET)
	public String userRoleManagement(@PathVariable String userId, ModelMap model) {

		UserRole userRole = new UserRole();
		User user = userManagementService.findById(userId);
		userRole.setUser(user);
		// if (null != user && 0 != user.getId()) {
		List<UserRole> userRoleList = userRoleManagementService.getUserRoleList(user);
		System.out.println("UserRole List size = " + userRoleList.size());
		// List<Role> roleList = roleService.getAllRoleList();
		List<Role> roleList = roleService.getAllRoleListByUserHierarchy(user);
		model.addAttribute("edit", true);
		model.addAttribute("userRole", userRole);

		Set<String> assignedhascodeSet = new HashSet<String>();
		Iterator<UserRole> it1 = userRoleList.iterator();
		while (it1.hasNext()) {
			UserRole userRole2 = it1.next();
			Role role2 = userRole2.getRole();
			assignedhascodeSet.add(role2.getId());
		}

		List<Role> notAssignedRoleList = new ArrayList<Role>();
		List<Role> assignedRoleList = new ArrayList<Role>();

		Iterator<Role> it = roleList.iterator();
		while (it.hasNext()) {
			Role role = it.next();
			if (!assignedhascodeSet.contains(role.getId())) {
				notAssignedRoleList.add(role);
			} else {
				assignedRoleList.add(role);
			}
		}

		// model.addAttribute("roleList", roleList);
		model.addAttribute("roleList", notAssignedRoleList);
		model.addAttribute("userRoleList", userRoleList);
		System.out.println("rolelist size is : " + roleList.size());
		return "userrolemanager";
		// } else {
		// return "redirect:/usermanagement";
		// }
	}

	@RequestMapping(value = { "/saveUserRoleAssociation" }, method = RequestMethod.POST)
	public String saveUserRoleAssociation(@Valid UserRole userRole, BindingResult result, ModelMap model) {
		if (null != userRole && !userRole.getUser().getId().equals("0")) {
			System.out.println("userRole user ids : " + userRole.getUser().getId());
			userRoleManagementService.updateUserRoles(userRole);
		}
		String urlreferer = userRole.getUser().getUrlReferer();
		return "redirect:" + urlreferer;
		// return "redirect:/usermanagement";
	}
}
