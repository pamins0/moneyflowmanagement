package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.service.UserManagementService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.Utility;
import com.emorphis.cashmanagement.validations.UserValidator;

@Controller
public class UserManagementController {
	
	private static final Logger log = LoggerFactory.getLogger(UserManagementController.class);

	@Autowired
	UserManagementService userManagementService;

	@Autowired
	BranchManagementService branchManagementService;

	@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	OrgManagementService orgManagementService;

	@Autowired
	UtilityService utilityService;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	MessageSource messageSource;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	/*
	 * @Autowired
	 * 
	 * @Qualifier("userValidator") Validator validator;
	 */

	@Autowired
	UserValidator validator;

	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		System.out.println("Inside initbinder method which is autocalled");
		// binder.setValidator(validator);
		StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
		binder.registerCustomEditor(String.class, stringtrimmer);
	}

	/**
	 * New CashManagement Updated Implementation....... Pankaj Birla*
	 * 
	 */

	@PreAuthorize("hasAnyRole('can_usermanagement_create')")
	@RequestMapping(value = { "/branchuser-associate-{id}", "/branchuser-Associate-{id}" }, method = RequestMethod.GET)
	public String userBranchAssociateNewGetMethod(@PathVariable String id, ModelMap model) {
		System.out.println("branch id in new user branch association is : " + id);
		BranchManagement branchManagement = branchManagementService.findById(id);
		User user = new User();
		user.setBranchManagement(branchManagement);
		System.out.println("Designations list : " + branchManagement.getHierarchyControl().getDesignation().size());
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("direct", "true");

		utilityService.OrgToOrgManagementDetails(model);

		return "newEditUserManagement";
	}

	@PreAuthorize("hasAnyRole('can_usermanagement_create')")
	@RequestMapping(value = { "/branchuser-associate-{id}", "/branchuser-Associate-{id}" }, method = RequestMethod.POST)
	public String userBranchAssociateNewPostMethod(@PathVariable String id, @Valid User user, BindingResult result,
			ModelMap model, RedirectAttributes redirectAttributes) {
		user.setEditable("false");
		validator.validate(user, result);
		if (result.hasErrors()) {
			System.out.println("id is for user or branch : " + id);
			BranchManagement branchManagement = branchManagementService.findById(id);
			user.setBranchManagement(branchManagement);
			model.addAttribute("user", user);
			model.addAttribute("edit", false);
			model.addAttribute("direct", "true");
			return "newEditUserManagement";
		}
		User user2 = userManagementService.getAvailableUser(user);
		if (user2 != null) {
			model.addAttribute("user", user);
			model.addAttribute("edit", false);
			model.addAttribute("direct", "true");
			utilityService.OrgToOrgManagementDetails(model);
			if (user.getUserId().trim().equalsIgnoreCase(user2.getUserId().trim())) {
				model.addAttribute("useridAlreadyExist",
						messageSource.getMessage("non.unique.userid", null, Locale.ENGLISH));
			}
			if (user.getEmail().trim().equalsIgnoreCase(user2.getEmail().trim())) {
				model.addAttribute("userEmailAlreadyExist",
						messageSource.getMessage("non.unique.userEmail", null, Locale.ENGLISH));
			}
			result.addError(result.getGlobalError());
			System.out.println("If user already exist......");
			return "newEditUserManagement";
		}
		String generatedId = userManagementService.save(user);
		user.setId(generatedId);
		if (null != user && !"0".equals(user.getId())) {
			System.out.println("Inside redirecting if : " + user.getId() + " user hierarchy id : "
					+ userBranchAssociateNewGetMethod(id, model));
			redirectAttributes.addFlashAttribute("userObj", user);
			return "redirect:/userRoleManagement";
		}
		return "redirect:/usermanagement";
	}

	@PreAuthorize("hasAnyRole('can_usermanagement_read')")
	@RequestMapping(value = { "/branchusermanagement-{id}-branch" }, method = RequestMethod.GET)
	public String branchUserManagement(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		List<HierarchyControl> hierarchyLists = null;
		BranchManagement branchManagement = branchManagementService.findById(id);
		if (null != branchManagement) {
			User userobj = mySession.getUser();
			User user = new User();
			user.setEntityType((byte) -1);
			userobj.setEntityType((byte) -1);
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				model.addAttribute("hierarchyList", hierarchyLists);
				model.addAttribute("user", userobj);
			} else {
				model.addAttribute("user", user);
				hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
				utilityService.OrgToHierarchyControlDetails(model);
			}

			List<User> usersList = userManagementService.getAllUsersListbyBranch(branchManagement);

			model.addAttribute("usersList", usersList);
			model.addAttribute("userObj", userobj);
			model.addAttribute("edit", false);
			System.out.println("usersList size is : " + usersList.size());
			return "usermanager";

		} else {
			return "redirect:/usermanagement";
		}
	}

	@PreAuthorize("hasAnyRole('can_usermanagement_create')")
	@RequestMapping(value = { "/usermanagement-new" }, method = RequestMethod.GET)
	public String newUserManagementGet(ModelMap model) {
		boolean flag = false;
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("direct", false);
		List<HierarchyControl> hierarchyLists = null;

		utilityService.OrgToOrgManagementDetails(model);

		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
		} else {
			hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
		}
		model.addAttribute("hierarchyList", hierarchyLists);
		return "newEditUserManagement";
	}

	@PreAuthorize("hasAnyRole('can_usermanagement_create')")
	@RequestMapping(value = { "/usermanagement-new" }, method = RequestMethod.POST)
	public String newUserManagementPost(@Valid User user, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		boolean flag = false;
		System.out.println("user object is : " + user.getGender());
		user.setEditable("false");
		validator.validate(user, result);
		List<HierarchyControl> hierarchyLists = null;
		if (result.hasErrors()) {
			if (user.getWayToCreate().equals("false")) {
				model.addAttribute("direct", false);
			} else {
				model.addAttribute("direct", true);
			}
			utilityService.OrgToOrgManagementDetails(model);
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			} else {
				hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
			}
			model.addAttribute("hierarchyList", hierarchyLists);
			System.out.println("Hierarchy id : " + user.getBranchManagement().getHierarchyControl().getId());
			if (!"-1".equals(user.getBranchManagement().getHierarchyControl().getId())) {
				HierarchyControl hierarchyControl = hierarchyManagementService
						.findById(user.getBranchManagement().getHierarchyControl().getId());
				user.getBranchManagement().setHierarchyControl(hierarchyControl);
			}
			model.addAttribute("user", user);
			model.addAttribute("edit", false);
			return "newEditUserManagement";
		}
		User user2 = userManagementService.getAvailableUser(user);
		if (user2 != null) {
			model.addAttribute("user", user);
			model.addAttribute("edit", false);
			if (user.getWayToCreate().equals("false")) {
				model.addAttribute("direct", false);
			} else {
				model.addAttribute("direct", true);
			}
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			} else {
				hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
			}
			model.addAttribute("hierarchyList", hierarchyLists);
			System.out.println("Hierarchy id : " + user.getBranchManagement().getHierarchyControl().getId());
			if (!"-1".equals(user.getBranchManagement().getHierarchyControl().getId())) {
				HierarchyControl hierarchyControl = hierarchyManagementService
						.findById(user.getBranchManagement().getHierarchyControl().getId());
				user.getBranchManagement().setHierarchyControl(hierarchyControl);
			}
			model.addAttribute("useridAlreadyExist",
					messageSource.getMessage("non.unique.userid", null, Locale.ENGLISH));
			if (user.getUserId().trim().equalsIgnoreCase(user2.getUserId().trim())) {
				model.addAttribute("useridAlreadyExist",
						messageSource.getMessage("non.unique.userid", null, Locale.ENGLISH));
			}
			if (user.getEmail().trim().equalsIgnoreCase(user2.getEmail().trim())) {
				model.addAttribute("userEmailAlreadyExist",
						messageSource.getMessage("non.unique.userEmail", null, Locale.ENGLISH));
			}
			result.addError(result.getGlobalError());
			System.out.println("If user already exist......");
			return "newEditUserManagement";
		}

		String generatedId = userManagementService.save(user);
		user.setId(generatedId);
		if (null != user && !"0".equals(user.getId())) {
			System.out.println("Inside redirecting if : " + user.getId()+" and url referer : "+user.getUrlReferer());
			redirectAttributes.addFlashAttribute("userObj", user);
			return "redirect:/userRoleManagement";
		}
		String urlreferer = user.getUrlReferer();
		return "redirect:" + urlreferer;
		// return "redirect:/usermanagement";
	}

	@PreAuthorize("hasAnyRole('can_usermanagement_update')")
	@RequestMapping(value = { "/usermanagement-{id}-edit", "/usermanagement-{id}-Edit" }, method = RequestMethod.GET)
	public String editUserManagementGet(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		User user = userManagementService.findById(id);

		if (user == null) {
			return "redirect:/usermanagement";
		}

		model.addAttribute("user", user);
		model.addAttribute("edit", true);

		utilityService.OrgToOrgManagementDetails(model);

		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
		} else {
			hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
		}

		model.addAttribute("hierarchyList", hierarchyLists);
		System.out.println("User edit function users name is  : " + user.getFirstName() + user.getLastName());
		return "newEditUserManagement";
	}

	@PreAuthorize("hasAnyRole('can_usermanagement_update')")
	@RequestMapping(value = { "/usermanagement-{id}-edit", "/usermanagement-{id}-Edit" }, method = RequestMethod.POST)
	public String editUserManagementPost(@PathVariable String id, @Valid User user, BindingResult result,
			ModelMap model) {
		boolean flag = false;
		System.out.println("branch id in edit is : " + user.getBranchManagement().getId());
		validator.validate(user, result);
		if (result.hasErrors()) {

			utilityService.OrgToOrgManagementDetails(model);

			List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			} else {
				hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
			}

			model.addAttribute("hierarchyList", hierarchyLists);
			System.out.println("User edit function users name is  : " + user.getFirstName() + user.getLastName());
			if (!"-1".equals(user.getBranchManagement().getHierarchyControl().getId())) {
				HierarchyControl hierarchyControl = hierarchyManagementService
						.findById(user.getBranchManagement().getHierarchyControl().getId());
				user.getBranchManagement().setHierarchyControl(hierarchyControl);
			}
			model.addAttribute("user", user);
			model.addAttribute("edit", true);

			return "newEditUserManagement";
		}
		userManagementService.updateById(user);
		String urlreferer = user.getUrlReferer();
		return "redirect:" + urlreferer;
		// return "redirect:/usermanagement";
	}

	@PreAuthorize("hasAnyRole('can_usermanagement_read')")
	@RequestMapping(value = { "/usermanagement-{id}-view" }, method = RequestMethod.GET)
	public String orgManagementViewGetMethod(@PathVariable String id, ModelMap model) {
		User user = userManagementService.findById(id);
		if (user != null) {
			model.addAttribute("user", user);
			model.addAttribute("edit", true);
			return "viewUserManagament";
		} else {
			return "redirect:/usermanagement";
		}
	}

	@PreAuthorize("hasAnyRole('can_usermanagement_delete')")
	@RequestMapping(value = { "/usermanagement-{id}-delete",
			"/userManagement-{id}-Delete" }, method = RequestMethod.GET)
	public String userManagementDelete(@PathVariable String id, ModelMap model, HttpServletRequest request) {
		userManagementService.deleteById(id);
		String referer = request.getHeader("Referer");
		System.out.println("On delete fo designation url referr is : " + referer);
		return "redirect:" + referer;
		// return "redirect:/usermanagement";
	}

	@PreAuthorize("hasAnyRole('can_usermanagement_read')")
	@RequestMapping(value = { "/usermanagement" }, method = RequestMethod.GET)
	public String userManagement(ModelMap model) {
		boolean flag = false;
		User user = new User();
		User userobj = mySession.getUser();
		user.setEntityType((byte) -1);
		userobj.setEntityType((byte) -1);

		List<HierarchyControl> hierarchyLists = null;
		List<User> usersList = null;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			BranchManagement br = mySession.getUser().getBranchManagement();
			usersList = new ArrayList<User>();
			// usersList.add(userobj);
			usersList.addAll(utility.getRoleDeletedFilteredList(br.getUsers()));
			usersList = utility.getAllChildBranchUserList(br, usersList);
			System.out.println("Now i am printing users listss.................");
			utility.printUsers(usersList);
			model.addAttribute("user", userobj);
			model.addAttribute("initialFilter", true);
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			model.addAttribute("user", user);
			hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
			usersList = userManagementService.getAllUsersList();
			System.out.println("Userlist size is s: " + usersList.size());
			utilityService.OrgToHierarchyControlDetails(model);
		}

		model.addAttribute("userObj", userobj);
		model.addAttribute("usersList", usersList);
		System.out.println("Users list size is : " + usersList.size());
		model.addAttribute("edit", false);

		return "usermanager";
	}

	/**
	 * by pavan
	 */

	@PreAuthorize("hasAnyRole('can_usermanagement_read')")
	@RequestMapping(value = { "/filterUserbyBranchHierarchy" }, method = RequestMethod.GET)
	public String filterUserbyBranchHierarchyGet(User user, ModelMap model) {

		return "redirect:/usermanagement";
	}

	@PreAuthorize("hasAnyRole('can_usermanagement_read')")
	@RequestMapping(value = { "/filterUserbyBranchHierarchy" }, method = RequestMethod.POST)
	public String filterUserbyBranchHierarchyPost(User user, ModelMap model) {
		boolean flag = false;
		System.out.println("inside post of filterUserbyBranchHierarchy method : " + user.getBranchManagement());
		List<HierarchyControl> hierarchyLists = null;
		List<User> usersList = new ArrayList<User>();
		User userobj = mySession.getUser();

		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			BranchManagement br = mySession.getUser().getBranchManagement();
			List<User> usersListLowerLevel = new ArrayList<User>();
			// usersListLowerLevel.add(userobj);
			usersListLowerLevel.addAll(utility.getRoleDeletedFilteredList(br.getUsers()));
			usersListLowerLevel = utility.getAllChildBranchUserList(br, usersListLowerLevel);
			// usersList =
			// userManagementService.getAllUsersListbyBranchHierarchy(user);
			List<User> usersFileterdList = new ArrayList<User>();
			usersFileterdList = userManagementService.getAllUsersListbyBranchHierarchyAndLowerLevelUsers(user,
					usersListLowerLevel);

			System.out.println("Lower level list : " + usersListLowerLevel.size());
			System.out.println("Filtered list : " + usersFileterdList.size());

			List<User> retainAllList = new ArrayList<User>(usersFileterdList);
			retainAllList.retainAll(usersListLowerLevel);

			usersList = retainAllList;

			System.out.println("Retain all list size is : " + retainAllList.size());
			System.out.println("Now i am printing users list.................");
			utility.printUsers(usersList);
		} else {
			System.out.println("user object entity chest type is : " + user.getEntityType());
			usersList = userManagementService.getAllUserListAccToBranchHierarchyOrganization(user);

			List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
			model.addAttribute("orgTypeList", orgTypeList);
			Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();

			OrgType orgType = orgTypeService
					.findById(user.getBranchManagement().getHierarchyControl().getOrgManagement().getOrgType().getId());

			if (null != orgType) {
				orgManagementSet = orgType.getOrgManagements();
				OrgManagement orgManagement = orgManagementService
						.findById(user.getBranchManagement().getHierarchyControl().getOrgManagement().getId());
				if (null != orgManagement) {
					hierarchyLists = orgManagement.getHierarchyControls();
				}
			}
			model.addAttribute("orgManagementSet", orgManagementSet);
			model.addAttribute("hierarchyList", hierarchyLists);

			System.out.println("Userlist size is : " + usersList.size());
		}

		model.addAttribute("usersList", usersList);
		model.addAttribute("userObj", userobj);
		model.addAttribute("user", user);

		return "usermanager";
	}
}
