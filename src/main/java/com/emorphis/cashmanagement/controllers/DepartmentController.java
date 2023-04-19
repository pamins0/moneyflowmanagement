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

import com.emorphis.cashmanagement.model.Department;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.DepartmentService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.service.UserManagementService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.Utility;
import com.emorphis.cashmanagement.validations.HierarchyControlvalidator;

@Controller
public class DepartmentController {

	private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	DepartmentService departmentService;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	UtilityService utilityService;

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

	@PreAuthorize("hasRole('can_departmentmanagement_read')")
	@RequestMapping(value = { "/departmentmanagement" }, method = RequestMethod.GET)
	public String designationManagement(ModelMap model) {
		boolean flag = false;
		List<HierarchyControl> hierarchyLists = null;
		List<Department> departmentList = null;
		Department department = new Department();

		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			departmentList = departmentService.getAllDepartmentListByLowerHierarchy(
					mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyLevel());
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			model.addAttribute("department", department);
		} else {
			model.addAttribute("department", department);
			utilityService.OrgToHierarchyControlDetails(model);
			departmentList = departmentService.getAllDepartmentList();
		}

		model.addAttribute("departmentList", departmentList);
		model.addAttribute("userHierarchy", mySession.getUser().getBranchManagement().getHierarchyControl());

		return "departmentManager";
	}

	@PreAuthorize("hasRole('can_departmentmanagement_create')")
	@RequestMapping(value = { "/departmentmanagement-new" }, method = RequestMethod.GET)
	public String departmentManagementNewEditGet(ModelMap model) {
		boolean flag = false;
		Department department = new Department();

		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			department.setHierarchyControl(mySession.getUser().getBranchManagement().getHierarchyControl());
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

		model.addAttribute("department", department);
		model.addAttribute("edit", "false");
		model.addAttribute("direct", "false");

		return "newEditDepartment";
	}

	@PreAuthorize("hasRole('can_departmentmanagement_create')")
	@RequestMapping(value = { "/departmentmanagement-new" }, method = RequestMethod.POST)
	public String designationManagementNewEditPost(@Valid Department department, BindingResult result, ModelMap model) {
		boolean flag = false;
		hierarchyControlvalidator.validate(department.getHierarchyControl(), result);
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

				model.addAttribute("department", department);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "false");
				return "newEditDepartment";
			}
			String action = department.getSaveBtn();
			System.out.println("Save btn name : " + action);
			System.out.println("Hierarchical Id : " + department.getHierarchyControl().getId());

			boolean flag1 = departmentService.save(department);
			String urlreferer = department.getUrlReferer();
			if (action.equals("Save")) {
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
				System.out.println("designation refere url in validation block is : " + department.getUrlReferer());
				department.setTitle("");
				model.addAttribute("department", department);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "false");
				return "newEditDepartment";
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
			System.out.println("department referer url in validation block is : " + department.getUrlReferer());
			model.addAttribute("department", department);
			model.addAttribute("edit", "false");
			model.addAttribute("direct", "false");
			model.addAttribute("duplicateDepartmentForHiearchy",
					messageSource.getMessage("duplicateDepartmentForHiearchy", null, Locale.ENGLISH));
			return "newEditDepartment";
		} catch (Exception ex) {
			log.error("exception generated in designation new post method due to : " + ex);
			return "redirect:/departmentmanagement";
		}

		return "redirect:/departmentmanagement";
		// return "redirect:"+urlreferer;
	}

	@PreAuthorize("hasRole('can_departmentmanagement_update')")
	@RequestMapping(value = { "/departmentmanagement-{id}-edit" }, method = RequestMethod.GET)
	public String departmentManagementEditGet(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		Department department = departmentService.findById(id);
		model.addAttribute("department", department);
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
					.findById(department.getHierarchyControl().getOrgManagement().getOrgType().getId());

			if (null != orgType) {
				orgManagementSet = orgType.getOrgManagements();
				OrgManagement orgManagement = orgManagementService
						.findById(department.getHierarchyControl().getOrgManagement().getId());
				if (null != orgManagement) {
					hierarchyLists = orgManagement.getHierarchyControls();
				}
			}
			model.addAttribute("orgManagementSet", orgManagementSet);
			model.addAttribute("hierarchyList", hierarchyLists);
		}

		return "newEditDepartment";
	}

	@PreAuthorize("hasRole('can_departmentmanagement_update')")
	@RequestMapping(value = { "/departmentmanagement-{id}-edit" }, method = RequestMethod.POST)
	public String departmentManagementEditPost(@Valid Department department, BindingResult result,
			@PathVariable String id, ModelMap model) {
		boolean flag = false;
		System.out.println(department.getTitle() + "::" + department.getParent());
		hierarchyControlvalidator.validate(department.getHierarchyControl(), result);
		if (result.hasErrors()) {
			List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				model.addAttribute("hierarchyList", hierarchyLists);
			} else {
				List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
				model.addAttribute("orgTypeList", orgTypeList);
				Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();

				OrgType orgType = orgTypeService
						.findById(department.getHierarchyControl().getOrgManagement().getOrgType().getId());

				if (null != orgType) {
					orgManagementSet = orgType.getOrgManagements();
					OrgManagement orgManagement = orgManagementService
							.findById(department.getHierarchyControl().getOrgManagement().getId());
					if (null != orgManagement) {
						hierarchyLists = orgManagement.getHierarchyControls();
					}
				}
				model.addAttribute("orgManagementSet", orgManagementSet);
				model.addAttribute("hierarchyList", hierarchyLists);
			}
			model.addAttribute("department", department);
			model.addAttribute("edit", "true");
			return "newEditDepartment";
		}
		System.out.println("Url referer in department edit post is : " + department.getUrlReferer());
		String urlreferer = department.getUrlReferer();
		departmentService.updatebyId(department);
		return "redirect:" + urlreferer;
	}

	@Autowired
	UserManagementService userManagementService;

	@PreAuthorize("hasRole('can_departmentmanagement_delete')")
	@RequestMapping(value = { "/departmentmanagement-{id}-delete" }, method = RequestMethod.GET)
	public String departmentDeleteGet(@PathVariable String id, ModelMap model, HttpServletRequest request) {
		// Department department = departmentService.findById(id);
		List<User> usersList = userManagementService.getAllUserListByDepartmentId(id);
		if (usersList != null && usersList.size() > 0) {
			System.out.println(
					"hfhhshfjhjsdh : " + messageSource.getMessage("userExistForDepartment", null, Locale.ENGLISH));
			model.addAttribute("userExistForDepartment",
					messageSource.getMessage("userExistForDepartment", null, Locale.ENGLISH));
			return "redirect:/departmentmanagement";
		}
		departmentService.deleteById(id);
		String referer = request.getHeader("Referer");
		System.out.println("On delete of department url referr is : " + referer);
		return "redirect:" + referer;
	}

	@PreAuthorize("hasRole('can_departmentmanagement_create')")
	@RequestMapping(value = { "/hierarchymanagementdepartment-associate-{id}" }, method = RequestMethod.GET)
	public String hierarchyManagementDepartmentAssociateGet(@PathVariable String id, ModelMap model) {
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
		if (null != hierarchyControl) {
			Department department = new Department();
			department.setRequestFrom("byhierarchy");
			department.setHierarchyControl(hierarchyControl);
			// designation.setOrgManagement(hierarchyControl.getOrgManagement());
			model.addAttribute("department", department);
			model.addAttribute("edit", "false");
			model.addAttribute("direct", "true");

			return "newEditDepartment";
		}
		return "redirect:/hierarchyManagament";
	}

	@PreAuthorize("hasRole('can_departmentmanagement_create')")
	@RequestMapping(value = { "/hierarchymanagementdepartment-associate-{id}" }, method = RequestMethod.POST)
	public String hierarchyManagementDesignationAssociatePost(@RequestParam(value = "saveBtn") String action,
			@PathVariable String id, @Valid Department department, BindingResult result, ModelMap model) {
		boolean flag = false;
		System.out.println("Save btn name : " + action);
		System.out.println("Hierarchical Id : " + department.getHierarchyControl().getId());
		try {
			hierarchyControlvalidator.validate(department.getHierarchyControl(), result);

			if (result.hasErrors()) {
				System.out.println("Role refere url in validation block is : " + department.getUrlReferer());
				model.addAttribute("department", department);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "true");
				return "newEditDepartment";
			}

			boolean flag1 = departmentService.save(department);
			String urlreferer = department.getUrlReferer();
			if (action.equals("Save")) {
				if (department.getRequestFrom().equals("byhierarchy")) {
					return "redirect:" + urlreferer;
				}
				return "redirect:/departmentmanagement";
			} else if (action.equals("SaveAndNew")) {
				System.out.println("Department refere url in validation block is : " + department.getUrlReferer());
				department.setTitle("");
				model.addAttribute("department", department);
				model.addAttribute("edit", "false");
				model.addAttribute("direct", "true");
				return "newEditDepartment";
			}
		} catch (DataIntegrityViolationException ex) {
			log.error("exception generated in department new post method due to duplicate data : " + ex);
			flag = utility.isAllowed("can_view_everything");
			System.out.println("department refere url in validation block is : " + department.getUrlReferer());
			model.addAttribute("department", department);
			model.addAttribute("edit", "false");
			model.addAttribute("direct", "true");
			model.addAttribute("duplicateDepartmentForHiearchy",
					messageSource.getMessage("duplicateDepartmentForHiearchy", null, Locale.ENGLISH));
			return "newEditDepartment";
		} catch (Exception e) {
			log.error(
					"Exception generated at hierarchyManagementDepartmentAssociatePost method during save is due to : "
							+ e);
		}
		return "redirect:/departmentmanagement";
	}

	@PreAuthorize("hasRole('can_departmentmanagement_read')")
	@RequestMapping(value = { "/filterDepartmentHierarchyByOrganization" }, method = RequestMethod.GET)
	public String filterBranchHierarchyByOrganizationGet(Department department, ModelMap model) {
		return "redirect:/departmentmanagement";
	}

	@PreAuthorize("hasRole('can_departmentmanagement_read')")
	@RequestMapping(value = { "/filterDepartmentHierarchyByOrganization" }, method = RequestMethod.POST)
	public String filterBranchHierarchyByOrganizationPost(Department department, ModelMap model) {
		boolean flag = false;
		System.out.println(
				"Inside filterBranchHierarchyByOrganization : " + department.getHierarchyControl().getOrgManagement());

		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		List<Department> departmentList = null;
		HierarchyControl hierarchyControl = hierarchyManagementService
				.findById(department.getHierarchyControl().getId());
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			departmentList = departmentService.getAllDepartmentListbyhierarchy(hierarchyControl);
		} else {
			departmentList = departmentService.getAllDepartmentListAccToHierarchyOrganization(department);

			List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
			model.addAttribute("orgTypeList", orgTypeList);
			Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();

			OrgType orgType = orgTypeService
					.findById(department.getHierarchyControl().getOrgManagement().getOrgType().getId());

			if (null != orgType) {
				orgManagementSet = orgType.getOrgManagements();
				OrgManagement orgManagement = orgManagementService
						.findById(department.getHierarchyControl().getOrgManagement().getId());
				if (null != orgManagement) {
					hierarchyLists = orgManagement.getHierarchyControls();
				}
			}
			model.addAttribute("orgManagementSet", orgManagementSet);
			model.addAttribute("hierarchyList", hierarchyLists);
		}

		model.addAttribute("userHierarchy", mySession.getUser().getBranchManagement().getHierarchyControl());
		model.addAttribute("department", department);
		model.addAttribute("departmentList", departmentList);

		return "departmentManager";
	}
	
	
	@PreAuthorize("hasRole('can_departmentmanagement_read')")
	@RequestMapping(value = { "/hierarchymanagementdepartment-{id}-hierarchy" }, method = RequestMethod.GET)
	public String hierarchyManagementDepartment(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		model.addAttribute("edit", false);
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
		// HierarchyControl hierarchyControl =
		// hierarchyManagementService.findByUUID(uuid);
		if (null != hierarchyControl) {
			List<HierarchyControl> hierarchyLists = null;
			Department department = new Department();
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				model.addAttribute("hierarchyList", hierarchyLists);
				model.addAttribute("department", department);
			} else {
				model.addAttribute("department", department);
				hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
				utilityService.OrgToHierarchyControlDetails(model);
			}

			List<Department> departmentList = departmentService.getAllDepartmentListbyhierarchy(hierarchyControl);
			model.addAttribute("departmentList", departmentList);
			model.addAttribute("hierarchyFilter", hierarchyControl.getId());
			utility.getCurrentHierarchyModel(model);
			model.addAttribute("edit", false);
			return "departmentManager";
		} else {
			return "redirect:/departmentmanagement";
		}
	}
	

}
