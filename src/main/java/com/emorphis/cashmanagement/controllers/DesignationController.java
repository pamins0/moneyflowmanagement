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

import com.emorphis.cashmanagement.model.Designation;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.DesignationService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.service.UserManagementService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.Utility;
import com.emorphis.cashmanagement.validations.HierarchyControlvalidator;

@Controller
@RequestMapping("/")
public class DesignationController {

	private static final Logger log = LoggerFactory.getLogger(DesignationController.class);

	@Autowired
	DesignationService designationService;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	UtilityService utilityService;

	@Autowired
	UserManagementService userManagementService;
	
	@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	OrgManagementService orgManagementService;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	MessageSource messageSource;

	@Autowired
	HierarchyControlvalidator hierarchyControlvalidator;

	@PreAuthorize("hasAnyRole('can_designationmanagement_create')")
	@RequestMapping(value = { "/hierarchymanagementdesignation-associate-{id}" }, method = RequestMethod.GET)
	public String hierarchyManagementDesignationAssociateGet(@PathVariable String id, ModelMap model) {
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
		// HierarchyControl hierarchyControl =
		// hierarchyManagementService.findByUUID(uuid);
		if (null != hierarchyControl) {
			Designation designation = new Designation();
			designation.setRequestFrom("byhierarchy");
			designation.setHierarchyControl(hierarchyControl);
			// designation.setOrgManagement(hierarchyControl.getOrgManagement());
			model.addAttribute("designation", designation);
			model.addAttribute("edit", "false");
			model.addAttribute("direct", "true");

			return "newEditDesignation";
		}
		return "redirect:/hierarchyManagament";
	}

	@PreAuthorize("hasAnyRole('can_designationmanagement_create')")
	@RequestMapping(value = { "/hierarchymanagementdesignation-associate-{id}" }, method = RequestMethod.POST)
	public String hierarchyManagementDesignationAssociatePost(@RequestParam(value = "saveBtn") String action,
			@PathVariable String id, @Valid Designation designation, BindingResult result, ModelMap model) {
		boolean flag = false;
		System.out.println("Save btn name : " + action);
		System.out.println("Hierarchical Id : " + designation.getHierarchyControl().getId());
		try {
			hierarchyControlvalidator.validate(designation.getHierarchyControl(), result);

			if (result.hasErrors()) {
				System.out.println("Role refere url in validation block is : " + designation.getUrlReferer());
				model.addAttribute("designation", designation);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "true");
				return "newEditDesignation";
			}

			boolean flag1 = designationService.save(designation);
			String urlreferer = designation.getUrlReferer();
			if (action.equals("Save")) {
				if (designation.getRequestFrom().equals("byhierarchy")) {
					return "redirect:" + urlreferer;
					// return "redirect:/hierarchyManagament";
				}
				return "redirect:/designationmanagement";
			} else if (action.equals("SaveAndNew")) {
				System.out.println("Role refere url in validation block is : " + designation.getUrlReferer());
				designation.setTitle("");
				model.addAttribute("designation", designation);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "true");
				return "newEditDesignation";
				// return "redirect:/hierarchymanagementdesignation-associate-"
				// +
				// designation.getHierarchyControl().getId();
			}
		} catch (DataIntegrityViolationException ex) {
			log.error("exception generated in designation new post method due to duplicate data : " + ex);
			flag = utility.isAllowed("can_view_everything");
			System.out.println("designation refere url in validation block is : " + designation.getUrlReferer());
			model.addAttribute("designation", designation);
			model.addAttribute("edit", "false");
			model.addAttribute("direct", "true");
			model.addAttribute("duplicateRoleForHiearchy",
					messageSource.getMessage("duplicateRoleForHiearchy", null, Locale.ENGLISH));
			return "newEditDesignation";
		} catch (Exception e) {
			log.error("Exception generated at hierarchyManagementDesignationAssociatePost method during save is due to : " + e);
		}

		return "redirect:/designationmanagement";
	}

	@PreAuthorize("hasAnyRole('can_designationmanagement_read')")
	@RequestMapping(value = { "/designationmanagement" }, method = RequestMethod.GET)
	public String designationManagement(ModelMap model) {
		boolean flag = false;
		List<HierarchyControl> hierarchyLists = null;
		List<Designation> designationList = null;
		Designation designation = new Designation();

		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			designationList = designationService.getAllDesignationListByLowerHierarchy(
					mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyLevel());
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("designation", designation);
		} else {
			model.addAttribute("designation", designation);
			utilityService.OrgToHierarchyControlDetails(model);
			designationList = designationService.getAllDesignationList();
		}

		model.addAttribute("designationList", designationList);
		model.addAttribute("userHierarchy", mySession.getUser().getBranchManagement().getHierarchyControl());

		return "designationManager";
	}

	@PreAuthorize("hasAnyRole('can_designationmanagement_read')")
	@RequestMapping(value = { "/filterDesignationHierarchyByOrganization" }, method = RequestMethod.GET)
	public String filterBranchHierarchyByOrganizationGet(Designation designation, ModelMap model) {
		return "redirect:/designationmanagement";
	}

	@PreAuthorize("hasAnyRole('can_designationmanagement_read')")
	@RequestMapping(value = { "/filterDesignationHierarchyByOrganization" }, method = RequestMethod.POST)
	public String filterBranchHierarchyByOrganizationPost(Designation designation, ModelMap model) {
		boolean flag = false;
		System.out.println(
				"Inside filterBranchHierarchyByOrganization : " + designation.getHierarchyControl().getOrgManagement());

		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		List<Designation> designationList = null;
		HierarchyControl hierarchyControl = hierarchyManagementService
				.findById(designation.getHierarchyControl().getId());
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			designationList = designationService.getAllDesignationListbyhierarchy(hierarchyControl);
		} else {
			designationList = designationService.getAllDesignationListAccToHierarchyOrganization(designation);

			List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
			model.addAttribute("orgTypeList", orgTypeList);
			Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();

			OrgType orgType = orgTypeService
					.findById(designation.getHierarchyControl().getOrgManagement().getOrgType().getId());

			if (null != orgType) {
				orgManagementSet = orgType.getOrgManagements();
				OrgManagement orgManagement = orgManagementService
						.findById(designation.getHierarchyControl().getOrgManagement().getId());
				if (null != orgManagement) {
					hierarchyLists = orgManagement.getHierarchyControls();
				}
			}
			model.addAttribute("orgManagementSet", orgManagementSet);
			model.addAttribute("hierarchyList", hierarchyLists);
		}

		model.addAttribute("userHierarchy", mySession.getUser().getBranchManagement().getHierarchyControl());
		model.addAttribute("designation", designation);
		model.addAttribute("designationList", designationList);

		return "designationManager";
	}

	@PreAuthorize("hasAnyRole('can_designationmanagement_update')")
	@RequestMapping(value = { "/designationmanagement-{id}-edit" }, method = RequestMethod.GET)
	public String designationManagementEditGet(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		Designation designation = designationService.findById(id);
		model.addAttribute("designation", designation);
		model.addAttribute("edit", "true");
		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			/*
			 * hierarchyLists =
			 * mySession.getUser().getBranchManagement().getHierarchyControl().
			 * getOrgManagement() .getHierarchyControls();
			 */
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			// hierarchyLists =
			// hierarchyManagementService.getAllHierarchyControlList();
			List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
			model.addAttribute("orgTypeList", orgTypeList);
			Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();

			OrgType orgType = orgTypeService
					.findById(designation.getHierarchyControl().getOrgManagement().getOrgType().getId());

			if (null != orgType) {
				orgManagementSet = orgType.getOrgManagements();
				OrgManagement orgManagement = orgManagementService
						.findById(designation.getHierarchyControl().getOrgManagement().getId());
				if (null != orgManagement) {
					hierarchyLists = orgManagement.getHierarchyControls();
				}
			}
			model.addAttribute("orgManagementSet", orgManagementSet);
			model.addAttribute("hierarchyList", hierarchyLists);
		}

		return "newEditDesignation";
	}

	@PreAuthorize("hasAnyRole('can_designationmanagement_update')")
	@RequestMapping(value = { "/designationmanagement-{id}-edit" }, method = RequestMethod.POST)
	public String designationManagementEditPost(@Valid Designation designation, BindingResult result,
			@PathVariable String id, ModelMap model) {
		boolean flag = false;
		System.out.println(designation.getTitle() + "::" + designation.getParent());
		hierarchyControlvalidator.validate(designation.getHierarchyControl(), result);
		if (result.hasErrors()) {
			List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				/*
				 * hierarchyLists =
				 * mySession.getUser().getBranchManagement().getHierarchyControl
				 * ().getOrgManagement() .getHierarchyControls();
				 */
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				model.addAttribute("hierarchyList", hierarchyLists);
			} else {
				// hierarchyLists =
				// hierarchyManagementService.getAllHierarchyControlList();
				List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
				model.addAttribute("orgTypeList", orgTypeList);
				Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();

				OrgType orgType = orgTypeService
						.findById(designation.getHierarchyControl().getOrgManagement().getOrgType().getId());

				if (null != orgType) {
					orgManagementSet = orgType.getOrgManagements();
					OrgManagement orgManagement = orgManagementService
							.findById(designation.getHierarchyControl().getOrgManagement().getId());
					if (null != orgManagement) {
						hierarchyLists = orgManagement.getHierarchyControls();
					}
				}
				model.addAttribute("orgManagementSet", orgManagementSet);
				model.addAttribute("hierarchyList", hierarchyLists);
			}
			model.addAttribute("designation", designation);
			model.addAttribute("edit", "true");
			return "newEditDesignation";
		}
		System.out.println("Url referer in designation edit post is : " + designation.getUrlReferer());
		String urlreferer = designation.getUrlReferer();
		designationService.updatebyId(designation);
		return "redirect:" + urlreferer;
		// return "redirect:/designationmanagement";
	}

	@PreAuthorize("hasAnyRole('can_designationmanagement_delete')")
	@RequestMapping(value = { "/designationmanagement-{id}-delete" }, method = RequestMethod.GET)
	public String designationDeleteGet(@PathVariable String id, ModelMap model, HttpServletRequest request) {
		List<User> usersList = userManagementService.getAllUserListByDesignationId(id);
		if (usersList != null && usersList.size() > 0) {
			System.out.println(
					"hfhhshfjhjsdh : " + messageSource.getMessage("userExistForDesignation", null, Locale.ENGLISH));
			model.addAttribute("userExistForDesignation",
					messageSource.getMessage("userExistForDesignation", null, Locale.ENGLISH));
			return "redirect:/designationmanagement";
		}
		designationService.deleteById(id);
		String referer = request.getHeader("Referer");
		System.out.println("On delete fo designation url referr is : " + referer);
		return "redirect:" + referer;
		// return "redirect:/designationmanagement";
	}

	@PreAuthorize("hasAnyRole('can_designationmanagement_create')")
	@RequestMapping(value = { "/designationmanagement-new" }, method = RequestMethod.GET)
	public String designationManagementNewEditGet(ModelMap model) {
		boolean flag = false;
		Designation designation = new Designation();

		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			designation.setHierarchyControl(mySession.getUser().getBranchManagement().getHierarchyControl());
			/*
			 * hierarchyLists =
			 * mySession.getUser().getBranchManagement().getHierarchyControl().
			 * getOrgManagement() .getHierarchyControls();
			 */
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
		} else {
			utilityService.OrgToHierarchyControlDetails(model);
		}

		model.addAttribute("designation", designation);
		model.addAttribute("edit", "false");
		model.addAttribute("direct", "false");

		return "newEditDesignation";
	}

	@PreAuthorize("hasAnyRole('can_designationmanagement_create')")
	@RequestMapping(value = { "/designationmanagement-new" }, method = RequestMethod.POST)
	public String designationManagementNewEditPost(@Valid Designation designation, BindingResult result,
			ModelMap model) {
		boolean flag = false;
		hierarchyControlvalidator.validate(designation.getHierarchyControl(), result);
		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		try {
			if (result.hasErrors()) {
				flag = utility.isAllowed("can_view_everything");
				if (!flag) {					
					hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
					model.addAttribute("hierarchyList", hierarchyLists);
				} else { 
					utilityService.OrgToHierarchyControlDetails(model);
					// hierarchyLists =
					// hierarchyManagementService.getAllHierarchyControlList();
				}

				model.addAttribute("designation", designation);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "false");
				return "newEditDesignation";
			}
			String action = designation.getSaveBtn();
			System.out.println("Save btn name : " + action);
			System.out.println("Hierarchical Id : " + designation.getHierarchyControl().getId());

			boolean flag1 = designationService.save(designation);
			String urlreferer = designation.getUrlReferer();
			if (action.equals("Save")) {
				// return "redirect:/designationmanagement";
				return "redirect:" + urlreferer;
			} else if (action.equals("SaveAndNew")) {
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
				System.out.println("designation refere url in validation block is : " + designation.getUrlReferer());
				designation.setTitle("");
				model.addAttribute("designation", designation);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "false");
				return "newEditDesignation";
			}
		} catch (DataIntegrityViolationException ex) {
			log.error("exception generated in designation new post method due to duplicate data : " + ex);
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				System.out.println("hierarchy list : " + hierarchyLists.size());
				model.addAttribute("hierarchyList", hierarchyLists);
			} else {
				utilityService.OrgToHierarchyControlDetails(model);
			}
			System.out.println("designation referer url in validation block is : " + designation.getUrlReferer());
			model.addAttribute("designation", designation);
			model.addAttribute("edit", "false");
			model.addAttribute("direct", "false");
			model.addAttribute("duplicateDesignationForHiearchy",
					messageSource.getMessage("duplicateDesignationForHiearchy", null, Locale.ENGLISH));
			return "newEditDesignation";
		} catch (Exception ex) {
			log.error("exception generated in designation new post method due to : " + ex);
			return "redirect:/designationmanagement";
		}

		return "redirect:/designationmanagement";
		// return "redirect:"+urlreferer;
	}

	@PreAuthorize("hasAnyRole('can_designationmanagement_read')")
	@RequestMapping(value = { "/hierarchymanagementdesignation-{id}-hierarchy" }, method = RequestMethod.GET)
	public String hierarchyManagementDesignation(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		model.addAttribute("edit", false);
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
		// HierarchyControl hierarchyControl =
		// hierarchyManagementService.findByUUID(uuid);
		if (null != hierarchyControl) {
			List<HierarchyControl> hierarchyLists = null;
			Designation designation = new Designation();
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				model.addAttribute("hierarchyList", hierarchyLists);
				model.addAttribute("designation", designation);
			} else {
				model.addAttribute("designation", designation);
				hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
				utilityService.OrgToHierarchyControlDetails(model);
			}

			List<Designation> designationList = designationService.getAllDesignationListbyhierarchy(hierarchyControl);
			model.addAttribute("designationList", designationList);
			model.addAttribute("hierarchyFilter", hierarchyControl.getId());
			utility.getCurrentHierarchyModel(model);
			model.addAttribute("edit", false);
			return "designationManager";
		} else {
			return "redirect:/designationmanagement";
		}
	}

}
