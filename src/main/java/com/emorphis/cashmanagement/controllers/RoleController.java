package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.UserRole;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.service.RoleService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.RoleCustom;
import com.emorphis.cashmanagement.util.Utility;
import com.emorphis.cashmanagement.validations.HierarchyControlvalidator;

@Controller
@RequestMapping("/")
public class RoleController {

	private static final Logger log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	MySession mySession;

	@Autowired
	RoleService roleService;

	@Autowired
	Utility utility;

	@Autowired
	UtilityService utilityService;

	@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	OrgManagementService orgManagementService;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	HierarchyControlvalidator hierarchyControlvalidator;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = { "/role" }, method = RequestMethod.GET)
	public String role(ModelMap model) {

		Role role = new Role();
		model.addAttribute("role", role);
		model.addAttribute("edit", false);
		List<Role> roleList = roleService.getAllRoleList();

		List<RoleCustom> list = rolesToRoleCustoms(roleList);

		// RoleCustom custom = getWithChilds(list);

		// custom.getChilds();

		model.addAttribute("roleList", roleList);

		return "role";
	}

	private List<RoleCustom> rolesToRoleCustoms(List<Role> roles) {

		List<RoleCustom> customsChilds = new ArrayList<RoleCustom>();
		for (Role role : roles) {
			customsChilds.add(new RoleCustom(role));
		}
		return customsChilds;
	}

	public static RoleCustom getRoleTest(String id) {
		RoleCustom role = null;

		return role;
	}

	private RoleCustom getWithChilds(List<RoleCustom> customs) {
		RoleCustom custom = new RoleCustom();
		List<RoleCustom> childs = new ArrayList<RoleCustom>();
		Map<Integer, Integer> mainMap = new HashMap<Integer, Integer>();
		int parent = 0;
		for (RoleCustom roleCustom : customs) {
			getRoleTest(roleCustom.getId());
		}

		/*
		 * for (RoleCustom roleCustom : customs) { for (RoleCustom roleCustom2 :
		 * customs) { if (roleCustom.getId() == roleCustom2.getParent()) {
		 * childs.add(getWithChilds(customs)); } } roleCustom.setChilds(childs);
		 * } custom.setChilds(childs);
		 */
		return null;
	}

	public List<MegaMenuDTO> getParentChildHierarchy(List<Role> roleList) {

		ArrayList<Pair> pairs = new ArrayList<Pair>();
		for (Role role : roleList) {
			pairs.add(new Pair("" + role.getId(), "" + role.getParent()));
		}
		// ...

		// Arrange
		// String corresponds to the Id
		Map<String, MegaMenuDTO> hm = new HashMap<String, MegaMenuDTO>();

		// you are using MegaMenuDTO as Linked list with next and before link

		// populate a Map
		for (Pair p : pairs) {

			// ----- Child -----
			MegaMenuDTO mmdChild;
			if (hm.containsKey(p.getChildId())) {
				mmdChild = hm.get(p.getChildId());
			} else {
				mmdChild = new MegaMenuDTO();
				hm.put(p.getChildId(), mmdChild);
			}
			mmdChild.setId(p.getChildId());
			mmdChild.setParentId(p.getParentId());
			// no need to set ChildrenItems list because the constructor created
			// a new empty list

			// ------ Parent ----
			MegaMenuDTO mmdParent;
			if (hm.containsKey(p.getParentId())) {
				mmdParent = hm.get(p.getParentId());
			} else {
				mmdParent = new MegaMenuDTO();
				hm.put(p.getParentId(), mmdParent);
			}
			mmdParent.setId(p.getParentId());
			mmdParent.setParentId("null");
			mmdParent.addChildrenItem(mmdChild);

		}

		// Get the root
		List<MegaMenuDTO> DX = new ArrayList<MegaMenuDTO>();
		for (MegaMenuDTO mmd : hm.values()) {
			if (mmd.getParentId().equals("null"))
				DX.add(mmd);
		}

		// Print
		for (MegaMenuDTO mmd : DX) {
			System.out.println("DX contains " + DX.size() + " that are : " + mmd);
		}

		return DX;

	}

	@RequestMapping(value = { "/role-new" }, method = RequestMethod.POST)
	public String saveRolePostMethod(@Valid Role role, BindingResult result, ModelMap model) {
		try {
			roleService.save(role);
		} catch (Exception e) {
			log.error("Exception generated at role-new method of role controller class due to : " + e);
		}
		return "redirect:/role";
	}

	@RequestMapping(value = { "/role-{id}-edit" }, method = RequestMethod.POST)
	public String roleEditPostMethod(@Valid Role role, @PathVariable String id, ModelMap model) {
		try {
			System.out.println(role.getTitle() + "::" + role.getParent());
			roleService.updatebyId(role);
		} catch (Exception e) {
			log.error("exception generated in role edit post method in role controller : " + e);
		}
		return "redirect:/role";
	}

	@RequestMapping(value = { "/role-{id}-delete" }, method = RequestMethod.GET)
	public String roleDelete(@PathVariable String id, ModelMap model) {

		roleService.deleteById(id);
		return "redirect:/role";
	}

	/**
	 * 
	 * New CashManagement Updated Implementation....... *
	 * 
	 * 
	 */

	@PreAuthorize("hasAnyRole('can_role_create')")
	@RequestMapping(value = { "/hierarchymanagementrole-associate-{id}" }, method = RequestMethod.GET)
	public String hierarchyManagementRoleAssociateGet(@PathVariable String id, ModelMap model) {
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
		// HierarchyControl hierarchyControl =
		// hierarchyManagementService.findByUUID(uuid);
		if (null != hierarchyControl) {
			Role role = new Role();
			role.setRequestFrom("byhierarchy");
			role.setHierarchyControl(hierarchyControl);
			// role.setOrgManagement(hierarchyControl.getOrgManagement());
			model.addAttribute("role", role);
			model.addAttribute("edit", "false");
			model.addAttribute("direct", "true");
			return "newEditRole";
		}
		return "redirect:/hierarchyManagament";
	}

	@PreAuthorize("hasAnyRole('can_role_create')")
	@RequestMapping(value = { "/hierarchymanagementrole-associate-{id}" }, method = RequestMethod.POST)
	public String hierarchyManagementRoleAssociatePost(@RequestParam(value = "saveBtn") String action,
			@PathVariable String id, @Valid Role role, BindingResult result, ModelMap model) {
		boolean flag = false;
		System.out.println("Save btn name : " + action);
		// System.out.println("Hierarchical Id : " +
		// role.getHierarchyControl().getUuid());

		try {
			hierarchyControlvalidator.validate(role.getHierarchyControl(), result);

			if (result.hasErrors()) {
				/*
				 * List<HierarchyControl> hierarchyLists = new
				 * ArrayList<HierarchyControl>(); flag =
				 * utility.isAllowed("can_view_everything"); if (!flag) {
				 * hierarchyLists =
				 * mySession.getUser().getBranchManagement().getHierarchyControl
				 * (). getOrgManagement() .getHierarchyControls();
				 * model.addAttribute("hierarchyList", hierarchyLists); } else {
				 * utilityService.OrgToHierarchyControlDetails(model); }
				 */
				System.out.println("Role refere url in validation block is : " + role.getUrlReferer());
				model.addAttribute("role", role);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "true");
				return "newEditRole";
			}
			boolean flag1 = roleService.save(role);
			String urlreferer = role.getUrlReferer();
			if (action.equals("Save")) {
				if (role.getRequestFrom().equals("byhierarchy")) {
					return "redirect:" + urlreferer;
				}
				return "redirect:/rolemanagement";
			} else if (action.equals("SaveAndNew")) {
				System.out.println("Role refere url in validation block is : " + role.getUrlReferer());
				role.setTitle("");
				model.addAttribute("role", role);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "true");
				return "newEditRole";

				// return "redirect:/hierarchymanagementrole-associate-" +
				// role.getHierarchyControl().getId();
			}
		} catch (DataIntegrityViolationException ex) {
			log.error("exception generated in role new post method due to duplicate data : " + ex);
			flag = utility.isAllowed("can_view_everything");
			System.out.println("Role refere url in validation block is : " + role.getUrlReferer());
			model.addAttribute("role", role);
			model.addAttribute("edit", "false");
			model.addAttribute("direct", "true");
			model.addAttribute("duplicateRoleForHiearchy",
					messageSource.getMessage("duplicateRoleForHiearchy", null, Locale.ENGLISH));
			return "newEditRole";
		} catch (Exception e) {
			log.error(
					"Exception generated at hierarchyManagementRoleAssociatePost method during save is due to : " + e);
		}
		return "redirect:/rolemanagement";
	}

	@PreAuthorize("hasAnyRole('can_role_read')")
	@RequestMapping(value = { "/rolemanagement" }, method = RequestMethod.GET)
	public String roleManagement(ModelMap model) {
		System.out.println("role management list method d......");
		boolean flag = false;
		List<HierarchyControl> hierarchyLists = null;
		List<Role> roleList = null;
		Role role = new Role();

		Role role1 = null;
		Set<UserRole> userRoleSet = mySession.getUser().getUserRoles();
		if (null != userRoleSet) {
			model.addAttribute("userRoleSet", userRoleSet);
		}

		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			roleList = roleService.getAllRoleListByLowerHierarchy(
					mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyLevel());
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("role", role);
		} else {
			model.addAttribute("role", role);
			utilityService.OrgToHierarchyControlDetails(model);
			roleList = roleService.getAllRoleList();
		}

		model.addAttribute("roleList", roleList);
		model.addAttribute("userHierarchy", mySession.getUser().getBranchManagement().getHierarchyControl());

		return "roleManager";
	}

	@PreAuthorize("hasAnyRole('can_role_read')")
	@RequestMapping(value = { "/filterRoleHierarchyByOrganization" }, method = RequestMethod.GET)
	public String filterBranchHierarchyByOrganizationGet(Role role, ModelMap model) {
		System.out.println("Inside get request method : ");
		return "redirect:/rolemanagement";
	}

	@PreAuthorize("hasAnyRole('can_role_read')")
	@RequestMapping(value = { "/filterRoleHierarchyByOrganization" }, method = RequestMethod.POST)
	public String filterBranchHierarchyByOrganizationPost(Role role, ModelMap model) {
		boolean flag = false;
		System.out.println(
				"Inside filterBranchHierarchyByOrganization : " + role.getHierarchyControl().getOrgManagement());

		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		List<Role> roleList = null;
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(role.getHierarchyControl().getId());
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			roleList = roleService.getAllRoleListbyhierarchy(hierarchyControl);
		} else {
			roleList = roleService.getAllRoleListAccToHierarchyOrganization(role);

			List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
			model.addAttribute("orgTypeList", orgTypeList);
			Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();

			OrgType orgType = orgTypeService
					.findById(role.getHierarchyControl().getOrgManagement().getOrgType().getId());

			if (null != orgType) {
				orgManagementSet = orgType.getOrgManagements();
				OrgManagement orgManagement = orgManagementService
						.findById(role.getHierarchyControl().getOrgManagement().getId());
				if (null != orgManagement) {
					hierarchyLists = orgManagement.getHierarchyControls();
				}
			}
			model.addAttribute("orgManagementSet", orgManagementSet);
			model.addAttribute("hierarchyList", hierarchyLists);
		}

		model.addAttribute("userHierarchy", mySession.getUser().getBranchManagement().getHierarchyControl());
		model.addAttribute("role", role);
		model.addAttribute("roleList", roleList);

		model.addAttribute("byFilterHierarchy", true);

		return "roleManager";
	}

	@PreAuthorize("hasAnyRole('can_role_update')")
	@RequestMapping(value = { "/rolemanagement-{id}-edit" }, method = RequestMethod.GET)
	public String roleManagementEditGet(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		Role role = roleService.findById(id);
		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			System.out.println("hierarchy list size acc to user level size is : " + hierarchyLists.size());
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
			model.addAttribute("orgTypeList", orgTypeList);
			Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();

			OrgType orgType = orgTypeService
					.findById(role.getHierarchyControl().getOrgManagement().getOrgType().getId());

			if (null != orgType) {
				orgManagementSet = orgType.getOrgManagements();
				OrgManagement orgManagement = orgManagementService
						.findById(role.getHierarchyControl().getOrgManagement().getId());
				if (null != orgManagement) {
					hierarchyLists = orgManagement.getHierarchyControls();
				}
			}
			model.addAttribute("orgManagementSet", orgManagementSet);
			model.addAttribute("hierarchyList", hierarchyLists);
		}

		model.addAttribute("role", role);
		model.addAttribute("edit", "true");
		return "newEditRole";
	}

	@PreAuthorize("hasAnyRole('can_role_update')")
	@RequestMapping(value = { "/rolemanagement-{id}-edit" }, method = RequestMethod.POST)
	public String roleManagementEditPost(@Valid Role role, BindingResult result, @PathVariable String id,
			ModelMap model) {
		boolean flag = false;
		System.out.println(role.getTitle() + "::" + role.getParent());
		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		try {
			hierarchyControlvalidator.validate(role.getHierarchyControl(), result);
			if (result.hasErrors()) {
				System.out.println("inside error : " + role.getHierarchyControl().getName());
				flag = utility.isAllowed("can_view_everything");
				if (!flag) {
					hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
					model.addAttribute("hierarchyList", hierarchyLists);
				} else {
					List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
					model.addAttribute("orgTypeList", orgTypeList);
					Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();
					OrgType orgType = orgTypeService
							.findById(role.getHierarchyControl().getOrgManagement().getOrgType().getId());
					if (null != orgType) {
						orgManagementSet = orgType.getOrgManagements();
						OrgManagement orgManagement = orgManagementService
								.findById(role.getHierarchyControl().getOrgManagement().getId());
						if (null != orgManagement) {
							hierarchyLists = orgManagement.getHierarchyControls();
						}
					}
					model.addAttribute("orgManagementSet", orgManagementSet);
					model.addAttribute("hierarchyList", hierarchyLists);
				}
				model.addAttribute("role", role);
				model.addAttribute("edit", "true");
				return "newEditRole";
			}
			roleService.updatebyId(role);
			String urlreferer = role.getUrlReferer();
			return "redirect:" + urlreferer;
		} catch (DataIntegrityViolationException ex) {
			log.error("exception generated in role new post method due to duplicate data : " + ex);
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				model.addAttribute("hierarchyList", hierarchyLists);
			} else {
				List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
				model.addAttribute("orgTypeList", orgTypeList);
				Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();
				OrgType orgType = orgTypeService
						.findById(role.getHierarchyControl().getOrgManagement().getOrgType().getId());
				if (null != orgType) {
					orgManagementSet = orgType.getOrgManagements();
					OrgManagement orgManagement = orgManagementService
							.findById(role.getHierarchyControl().getOrgManagement().getId());
					if (null != orgManagement) {
						hierarchyLists = orgManagement.getHierarchyControls();
					}
				}
				model.addAttribute("orgManagementSet", orgManagementSet);
				model.addAttribute("hierarchyList", hierarchyLists);
			}
			model.addAttribute("role", role);
			model.addAttribute("edit", "true");
			model.addAttribute("duplicateRoleForHiearchy",
					messageSource.getMessage("duplicateRoleForHiearchy", null, Locale.ENGLISH));
			return "newEditRole";
		} catch (Exception ex) {
			log.error("exception generated in role new post method due to : " + ex);
			return "redirect:/rolemanagement";
		}

		// return "redirect:/rolemanagement";
	}

	@PreAuthorize("hasAnyRole('can_role_delete')")
	@RequestMapping(value = { "/rolemanagement-{id}-delete" }, method = RequestMethod.GET)
	public String roleDeleteGet(@PathVariable String id, ModelMap model, HttpServletRequest request) {
		roleService.deleteById(id);
		String referer = request.getHeader("Referer");
		System.out.println("On delete fo designation url referr is : " + referer);
		return "redirect:" + referer;
		// return "redirect:/rolemanagement";
	}

	@PreAuthorize("hasAnyRole('can_role_create')")
	@RequestMapping(value = { "/rolemanagement-new" }, method = RequestMethod.GET)
	public String roleManagementNewEditGet(ModelMap model) {
		boolean flag = false;
		Role role = new Role();

		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			role.setHierarchyControl(mySession.getUser().getBranchManagement().getHierarchyControl());
			/*
			 * hierarchyLists =
			 * mySession.getUser().getBranchManagement().getHierarchyControl().
			 * getOrgManagement() .getHierarchyControls();
			 */
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			utilityService.OrgToHierarchyControlDetails(model);
			// hierarchyLists =
			// hierarchyManagementService.getAllHierarchyControlList();
		}

		model.addAttribute("role", role);
		model.addAttribute("edit", "false");
		model.addAttribute("direct", "false");

		return "newEditRole";
	}

	@PreAuthorize("hasAnyRole('can_role_create')")
	@RequestMapping(value = { "/rolemanagement-new" }, method = RequestMethod.POST)
	public String roleManagementNewEditPost(@Valid Role role, BindingResult result, ModelMap model) {
		boolean flag = false;
		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		System.out.println("action button name in new role save : " + role.getSaveBtn() + " and action parameter : ");
		hierarchyControlvalidator.validate(role.getHierarchyControl(), result);
		try {
			if (result.hasErrors()) {
				flag = utility.isAllowed("can_view_everything");
				if (!flag) {
					/*
					 * hierarchyLists =
					 * mySession.getUser().getBranchManagement().
					 * getHierarchyControl().getOrgManagement()
					 * .getHierarchyControls();
					 */
					hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
					System.out.println("hierarchy list : " + hierarchyLists.size());
					model.addAttribute("hierarchyList", hierarchyLists);
				} else {
					utilityService.OrgToHierarchyControlDetails(model);
				}
				System.out.println("Role refere url in validation block is : " + role.getUrlReferer());
				model.addAttribute("role", role);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "false");
				return "newEditRole";
			}
			String action = role.getSaveBtn();
			System.out.println("Save btn name : " + role.getSaveBtn());
			System.out.println("Hierarchical Id in post method : " + role.getHierarchyControl().getId());

			boolean flag1 = roleService.save(role);
			String urlreferer = role.getUrlReferer();
			if (action.equals("Save")) {
				return "redirect:" + urlreferer;
			} else if (action.equals("SaveAndNew")) {
				// return "redirect:/hierarchymanagementrole-associate-" +
				// role.getHierarchyControl().getId();
				// List<HierarchyControl> hierarchyLists = new
				// ArrayList<HierarchyControl>();
				flag = utility.isAllowed("can_view_everything");
				if (!flag) {
					hierarchyLists = mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement()
							.getHierarchyControls();
					model.addAttribute("hierarchyList", hierarchyLists);
				} else {
					utilityService.OrgToHierarchyControlDetails(model);
					// hierarchyLists =
					// hierarchyManagementService.getAllHierarchyControlList();
				}
				System.out.println("Role refere url in validation block is : " + role.getUrlReferer());
				role.setTitle("");
				model.addAttribute("role", role);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "false");
				return "newEditRole";
			}
		} catch (DataIntegrityViolationException ex) {
			log.error("exception generated in role new post method due to duplicate data : " + ex);
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				System.out.println("hierarchy list : " + hierarchyLists.size());
				model.addAttribute("hierarchyList", hierarchyLists);
			} else {
				utilityService.OrgToHierarchyControlDetails(model);
			}
			System.out.println("Role refere url in validation block is : " + role.getUrlReferer());
			model.addAttribute("role", role);
			model.addAttribute("edit", "false");
			model.addAttribute("direct", "false");
			model.addAttribute("duplicateRoleForHiearchy",
					messageSource.getMessage("duplicateRoleForHiearchy", null, Locale.ENGLISH));
			return "newEditRole";
		} catch (Exception ex) {
			log.error("exception generated in role new post method due to : " + ex);
			return "redirect:/rolemanagement";
		}
		return "redirect:/rolemanagement";
	}

	@PreAuthorize("hasAnyRole('can_role_read')")
	@RequestMapping(value = { "/hierarchymanagementrole-{id}-hierarchy" }, method = RequestMethod.GET)
	public String hierarchyManagementRole(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
		if (null != hierarchyControl) {
			List<HierarchyControl> hierarchyLists = null;
			Role role = new Role();
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				model.addAttribute("hierarchyList", hierarchyLists);
				model.addAttribute("role", role);
			} else {
				model.addAttribute("role", role);
				hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
				utilityService.OrgToHierarchyControlDetails(model);
			}

			List<Role> roleList = roleService.getAllRoleListbyhierarchy(hierarchyControl);
			model.addAttribute("roleList", roleList);
			utility.getCurrentHierarchyModel(model);
			model.addAttribute("edit", false);
			return "roleManager";
		} else {
			return "redirect:/rolemanagement";
		}
	}

	@PreAuthorize("hasAnyRole('can_role_update')")
	@RequestMapping(value = { "/rolemanagement-{id}-editstatus" }, method = RequestMethod.GET)
	public String roleManagementEditRoleStatus(@Valid Role role, BindingResult result, 
			@PathVariable String id, ModelMap model) {
		boolean flag = false;
		log.info("Id val is : "+id);
		roleService.updateRoleStatusById(id);
		return "redirect:/rolemanagement";
	}
}
