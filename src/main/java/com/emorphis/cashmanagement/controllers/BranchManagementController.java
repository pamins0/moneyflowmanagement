package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchCorrespondentGroup;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.BranchClosedGroupService;
import com.emorphis.cashmanagement.service.BranchCorrespondentService;
import com.emorphis.cashmanagement.service.BranchGroupService;
import com.emorphis.cashmanagement.service.BranchManagementService;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.Utility;
import com.emorphis.cashmanagement.validations.BranchValidator;

@Controller
@RequestMapping("/")
public class BranchManagementController {

	private static final Logger log = LoggerFactory.getLogger(BranchManagementController.class);

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
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	Utility utility;

	@Autowired
	BranchValidator branchValidator;

	@Autowired
	BranchClosedGroupService branchClosedGroupService;

	@Autowired
	BranchGroupService branchGroupService;
	
	@Autowired
	BranchCorrespondentService branchCorrespondentService;

	/*
	 * @InitBinder public void InitBinder(WebDataBinder binder) { System.out.
	 * println("Inside initbinder of branch controller method which is autocalled"
	 * ); // binder.setValidator(validator); StringTrimmerEditor stringtrimmer =
	 * new StringTrimmerEditor(true); binder.registerCustomEditor(String.class,
	 * stringtrimmer); }
	 */

	@RequestMapping(value = { "/branch-branchassociates" }, method = RequestMethod.GET)
	public String branchManagementAssociates(ModelMap model,
			@ModelAttribute("branchObj") BranchManagement branchManagement) {
		model.addAttribute("branchmanagement", branchManagement);
		return "branchassociates";
	}

	/**
	 * 
	 * New CashManagement Updated Implementation....... Pankaj Birla*
	 * 
	 * 
	 */

	/**
	 * 
	 * Group Management Implementation Pankaj Birla
	 * 
	 */

	@RequestMapping(value = { "/groupmanagement", "/groupManagement" }, method = RequestMethod.GET)
	public String groupManagementGetMethod(ModelMap model) {
		model.addAttribute("edit", false);
		model.addAttribute("groupmanager", "true");
		model.addAttribute("branches", branchManagementService.getAllBranchGrouped());
		return "branchmManagement";
	}

	/**
	 * 
	 * Branch Management Implementation Pankaj Birla
	 * 
	 */

	@RequestMapping(value = { "/testpermission" }, method = RequestMethod.GET)
	public String testPermission() {
		log.info("inside test permission method......");
		return "redirect:/home";
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_create')")
	@RequestMapping(value = { "/hierarchymanagementbranch-associate-{id}" }, method = RequestMethod.GET)
	public String branchHierarchyAssociateNewGetMethod(RedirectAttributes redirectAttributes, @PathVariable String id,
			ModelMap model) {
		boolean flag = false;
		BranchManagement branch = new BranchManagement();
		branch.setRequestFrom("byhierarchy");
		branch.setOrgManagement(mySession.getUser().getBranchManagement().getOrgManagement());
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
		// HierarchyControl hierarchyControl =
		// hierarchyManagementService.findByUUID(uuid);
		if (null != hierarchyControl) {
			branch.setHierarchyControl(hierarchyControl);
			System.out.println("Hierarchical control parent id : " + hierarchyControl.getParentId());
			if (!hierarchyControl.getParentId().equals("0")) {
				List<BranchManagement> branchManagementListByLessHierarchy = new ArrayList<BranchManagement>();
				branchManagementListByLessHierarchy = branchManagementService
						.getAllBranchManagementListByHierarchyId(hierarchyControl.getParentId());
				System.out
						.println("branch by hierarchy id list zise id : " + branchManagementListByLessHierarchy.size());
				if (branchManagementListByLessHierarchy.size() > 0) {
					List<HierarchyControl> hierarchyLists = null;
					flag = utility.isAllowed("can_view_everything");
					if (!flag) {
						hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
					} else {
						hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
					}
					model.addAttribute("hierarchyList", hierarchyLists);
					model.addAttribute("branchManagementListByLessHierarchy", branchManagementListByLessHierarchy);
				} else {
					model.addAttribute("parentBranchesNotCreated",
							messageSource.getMessage("hierarchy.parent.branch.notCreated", null, Locale.ENGLISH));
					redirectAttributes.addFlashAttribute("modelMsg", model);
					return "redirect:/hierarchyManagament";
				}
			} else {
				System.out.println("inside else if its head office or parentt top office id name is : "
						+ hierarchyControl.getName());
				model.addAttribute("parent", "true");
				model.addAttribute("parentHierarchyName", hierarchyControl.getName());
			}

			branch.setOrgManagement(hierarchyControl.getOrgManagement());
			model.addAttribute("branchManagement", branch);
			model.addAttribute("groups", "true");

			utilityService.OrgToOrgManagementDetails(model);
			model.addAttribute("edit", "false");
			return "newEditBranchManagementByGroup";
		}
		return "redirect:/hierarchyManagament";
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_create')")
	@RequestMapping(value = { "/hierarchymanagementbranch-associate-{id}" }, method = RequestMethod.POST)
	public String branchHierarchyAssociateNewPostMethod(RedirectAttributes redirectAttributes,
			@RequestParam(value = "saveBtn") String action, @Valid BranchManagement branch, BindingResult result,
			@PathVariable String id, ModelMap model) {
		boolean flag = false;
		System.out.println("Hierarchical control object is : " + branch.getHierarchyControl());
		System.out.println("branch control id on save is : " + branch.getBranchControl() + " and branch email is : "
				+ branch.getBranchEmail());
		branchValidator.validate(branch, result);
		if (result.hasErrors()) {
			branch.setRequestFrom("byhierarchy");
			// branch.setOrgManagement(mySession.getUser().getBranchManagement().getOrgManagement());
			HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
			if (null != hierarchyControl) {
				branch.setHierarchyControl(hierarchyControl);
				System.out.println("Hierarchical control parent id : " + hierarchyControl.getParentId());
				if (!hierarchyControl.getParentId().equals("0")) {
					List<BranchManagement> branchManagementListByLessHierarchy = new ArrayList<BranchManagement>();
					branchManagementListByLessHierarchy = branchManagementService
							.getAllBranchManagementListByHierarchyId(hierarchyControl.getParentId());
					System.out.println(
							"branch by hierarchy id list zise id : " + branchManagementListByLessHierarchy.size());
					if (branchManagementListByLessHierarchy.size() > 0) {
						List<HierarchyControl> hierarchyLists = null;
						flag = utility.isAllowed("can_view_everything");
						if (!flag) {
							hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
						} else {
							hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
						}
						model.addAttribute("hierarchyList", hierarchyLists);
						model.addAttribute("branchManagementListByLessHierarchy", branchManagementListByLessHierarchy);
					} else {
						model.addAttribute("parentBranchesNotCreated",
								messageSource.getMessage("hierarchy.parent.branch.notCreated", null, Locale.ENGLISH));
						redirectAttributes.addFlashAttribute("modelMsg", model);
						return "redirect:/hierarchyManagament";
					}
				} else {
					System.out.println("inside else if its head office or parentt top office id name is : "
							+ hierarchyControl.getName());
					model.addAttribute("parent", "true");
					model.addAttribute("parentHierarchyName", hierarchyControl.getName());
				}

				branch.setOrgManagement(hierarchyControl.getOrgManagement());
				model.addAttribute("branchManagement", branch);
				model.addAttribute("groups", "true");

				utilityService.OrgToOrgManagementDetails(model);
				model.addAttribute("edit", "false");
				return "newEditBranchManagementByGroup";
			}
		}
		BranchManagement branchManagement = branchManagementService.getAvailableBranchCode(branch);
		if (branchManagement != null) {
			branch.setRequestFrom("byhierarchy");
			// branch.setOrgManagement(mySession.getUser().getBranchManagement().getOrgManagement());
			HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
			if (null != hierarchyControl) {
				branch.setHierarchyControl(hierarchyControl);
				System.out.println("Hierarchical control parent id : " + hierarchyControl.getParentId()
						+ " and branch email is : " + branchManagement.getBranchEmail());
				if (!hierarchyControl.getParentId().equals(0)) {
					List<BranchManagement> branchManagementListByLessHierarchy = new ArrayList<BranchManagement>();
					branchManagementListByLessHierarchy = branchManagementService
							.getAllBranchManagementListByHierarchyId(hierarchyControl.getParentId());
					System.out.println(
							"branch by hierarchy id list zise id : " + branchManagementListByLessHierarchy.size());
					if (branchManagementListByLessHierarchy.size() > 0) {
						List<HierarchyControl> hierarchyLists = null;
						flag = utility.isAllowed("can_view_everything");
						if (!flag) {
							hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
						} else {
							hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
						}
						model.addAttribute("hierarchyList", hierarchyLists);
						model.addAttribute("branchManagementListByLessHierarchy", branchManagementListByLessHierarchy);
					} else {
						model.addAttribute("parentBranchesNotCreated",
								messageSource.getMessage("hierarchy.parent.branch.notCreated", null, Locale.ENGLISH));
						redirectAttributes.addFlashAttribute("modelMsg", model);
						return "redirect:/hierarchyManagament";
					}
				} else {
					System.out.println("inside else if its head office or parentt top office id name is : "
							+ hierarchyControl.getName());
					model.addAttribute("parent", "true");
					model.addAttribute("parentHierarchyName", hierarchyControl.getName());
				}

				branch.setOrgManagement(hierarchyControl.getOrgManagement());
				model.addAttribute("branchManagement", branch);
				model.addAttribute("groups", "true");

				utilityService.OrgToOrgManagementDetails(model);
				model.addAttribute("edit", "false");
				if (branch.getBranchCode().trim().equalsIgnoreCase(branchManagement.getBranchCode().trim())) {
					model.addAttribute("branchCodeAlreadyExist",
							messageSource.getMessage("non.unique.branchCode", null, Locale.ENGLISH));
				}
				if (branch.getBranchEmail().trim().equalsIgnoreCase(branchManagement.getBranchEmail().trim())) {
					model.addAttribute("branchEmailAlreadyExist",
							messageSource.getMessage("non.unique.branchEmail", null, Locale.ENGLISH));
				}
				System.out.println("orginal email : " + branch.getBranchEmail() + " and found duplicate if : "
						+ branchManagement.getBranchEmail());
				result.addError(result.getGlobalError());
				return "newEditBranchManagementByGroup";
			}
		}

		HierarchyControl hierarchyControl = hierarchyManagementService.findById(branch.getHierarchyControl().getId());
		if (null != hierarchyControl) {
			if (hierarchyControl.getOrgManagement().getOrgLevel() == hierarchyControl.getHierarchyLevel()
					|| hierarchyControl.getHierarchyType() == 1) {
				branch.setIsgroup((byte) 0);
			} else {
				branch.setIsgroup((byte) 1);
			}
		}
		if (branch.getBranchControl().equals("0")) {
			branch.setBranchControl(null);
		}
		String generatedBranchId = branchManagementService.save(branch);

		log.info("branch group level in edit value in  post is : " + branch.getIsgroup());
		if (true) {
			if (branch.getIsgroup() == 0) {
				User user = mySession.getUser();
				BranchManagement branchManagement1 = branchManagementService.findById(branch.getId());
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						log.info("now setting cluster to cluster group for the branch : "
								+ branchManagement1.getBranchName() + " and orgManagement id :"
								+ branchManagement1.getHierarchyControl().getOrgManagement().getId());
						try {
							int clusterCount = utilityService.makeClusterClusterGroup(branchManagement1, user);
						} catch (Exception e) {
							log.error("error generated during cluster cluster distance for branch id : "
									+ branchManagement1.getId() + " due to : " + e);
						}
					}
				});
				t.start();
			}
		}

		String urlreferer = branch.getUrlReferer();
		if (action.equals("Save")) {
			/*
			 * if (branch.getRequestFrom().equals("byhierarchy")) { return
			 * "redirect:/hierarchyManagament"; } return
			 * "redirect:/hierarchyManagament";
			 */
			return "redirect:" + urlreferer;
		} else if (action.equals("SaveAndNew")) {
			if (branch.getRequestFrom().equals("byhierarchy")) {
				System.out.println("Inside branch hierarchy : " + branch.getHierarchyControl().getId());
				branchHierarchyAssociateNewGetMethod(branch.getHierarchyControl().getId(), model);
				// return
				// "hierarchymanagementbranch-associate-"+branch.getHierarchyControl().getId()
			}
			// return
			// "hierarchymanagementbranch-associate-"+branch.getHierarchyControl().getId();
			System.out.println("Inside branch hierarchy outside: " + branch.getHierarchyControl().getId());
			branchHierarchyAssociateNewGetMethod(branch.getHierarchyControl().getId(), model);
		}

		return "redirect:/hierarchyManagament";
	}

	public String branchHierarchyAssociateNewGetMethod(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		BranchManagement branch = new BranchManagement();
		branch.setRequestFrom("byhierarchy");
		branch.setOrgManagement(mySession.getUser().getBranchManagement().getOrgManagement());
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
		if (null != hierarchyControl) {
			branch.setHierarchyControl(hierarchyControl);
			System.out.println("Hierarchical control parent id : " + hierarchyControl.getParentId());
			if (!hierarchyControl.getParentId().equals(0)) {
				List<BranchManagement> branchManagementListByLessHierarchy = new ArrayList<BranchManagement>();
				branchManagementListByLessHierarchy = branchManagementService
						.getAllBranchManagementListByHierarchyId(hierarchyControl.getParentId());
				System.out
						.println("branch by hierarchy id list zise id : " + branchManagementListByLessHierarchy.size());
				if (branchManagementListByLessHierarchy.size() > 0) {
					List<HierarchyControl> hierarchyLists = null;
					flag = utility.isAllowed("can_view_everything");
					if (!flag) {
						hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
					} else {
						hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
					}
					model.addAttribute("hierarchyList", hierarchyLists);
					model.addAttribute("branchManagementListByLessHierarchy", branchManagementListByLessHierarchy);
				} else {
					model.addAttribute("parentBranchesNotCreated",
							messageSource.getMessage("hierarchy.parent.branch.notCreated", null, Locale.ENGLISH));
					// redirectAttributes.addFlashAttribute("modelMsg", model);
					return "redirect:/hierarchyManagament";
				}
			} else {
				System.out.println("inside else if its head office or parentt top office id name is : "
						+ hierarchyControl.getName());
				model.addAttribute("parent", "true");
				model.addAttribute("parentHierarchyName", hierarchyControl.getName());
			}

			branch.setOrgManagement(hierarchyControl.getOrgManagement());
			model.addAttribute("branch", branch);
			model.addAttribute("groups", "true");

			utilityService.OrgToOrgManagementDetails(model);
			model.addAttribute("edit", "false");
			return "newEditBranchManagementByGroup";
		}
		return "redirect:/hierarchyManagament";
	}

	@RequestMapping(value = { "/branchmanagement-new-old", "/branchManagement-New-old" }, method = RequestMethod.GET)
	public String branchmanagementNewGetMethod_Old(ModelMap model) {
		BranchManagement branch = new BranchManagement();
		branch.setOrgManagement(mySession.getUser().getBranchManagement().getOrgManagement());
		model.addAttribute("branch", branch);
		model.addAttribute("edit", false);
		model.addAttribute("parent", "false");

		utilityService.OrgToOrgManagementDetails(model);

		Integer levelCountInOrg = branch.getOrgManagement().getOrgLevel();
		if (levelCountInOrg > 1) {
			System.out.println("level form org is : " + levelCountInOrg);
			HierarchyControl hierarchyControl = hierarchyManagementService.findByOrgAndLevel(branch.getOrgManagement(),
					(levelCountInOrg - 1));
			System.out.println("hierarchy id in new branch : " + hierarchyControl.getId());
			model.addAttribute("branchManagementListByLessHierarchy",
					branchManagementService.getAllBranchManagementListByHierarchyId(hierarchyControl.getId()));
			branch.setHierarchyControl(hierarchyControl);
		} else {
			HierarchyControl hierarchyControl = hierarchyManagementService.findByOrgAndLevel(branch.getOrgManagement(),
					(levelCountInOrg));
			branch.setHierarchyControl(hierarchyControl);
			model.addAttribute("minlevels", "true");
			model.addAttribute("parent", "true");
			return "newEditBranchManagementByGroup";
		}
		return "newEditBranchManagementByGroup";
	}

	@RequestMapping(value = { "/branchmanagement-newss", "/branchManagement-Newss" }, method = RequestMethod.GET)
	public String branchmanagementNewGetMethod1(ModelMap model) {
		BranchManagement branch = new BranchManagement();

		model.addAttribute("branch", branch);
		model.addAttribute("edit", false);

		return "newEditBranchManagementByGroups";
	}

	@RequestMapping(value = { "/branchmanagement-newss", "/branchManagement-Newss" }, method = RequestMethod.POST)
	public String branchmanagementNewGetMethod11(@Valid BranchManagement branch, BindingResult result, ModelMap model) {
		System.out.println("Barrebndshfjhsjdf JKJjk fsdh : " + branch.getAbbreviation());
		if (result.hasErrors()) {
			System.out.println("Barrebndshfjhsjdf JKJjk fsdh inside : " + branch.getAbbreviation());
			model.addAttribute("branch", branch);
			model.addAttribute("edit", false);
			return "newEditBranchManagementByGroups";
		}
		return "newEditBranchManagementByGroups";
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_create')")
	@RequestMapping(value = { "/branchmanagement-new", "/branchManagement-New" }, method = RequestMethod.GET)
	public String branchmanagementNewGetMethod(ModelMap model) {
		boolean flag = false;
		BranchManagement branch = new BranchManagement();

		utilityService.OrgToOrgManagementDetails(model);
		List<HierarchyControl> hierarchyLists = null;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			branch.setOrgManagement(mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement());
		} else {
			hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
		}
		model.addAttribute("hierarchyList", hierarchyLists);

		model.addAttribute("branchManagement", branch);
		model.addAttribute("edit", false);
		model.addAttribute("parent", "false");

		return "newEditBranchManagementByGroup";
		// return "ncheckradiobutton";
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_create')")
	@RequestMapping(value = { "/branchmanagement-new", "/branchManagement-New" }, method = RequestMethod.POST)
	public String branchmanagementNewPostMethod(@Valid BranchManagement branch, BindingResult result, ModelMap model,
			@RequestParam(value = "saveBtn") String action) {
		boolean flag = false;
		System.out.println("Hierarchical control object is : " + branch.getHierarchyControl().getId());
		System.out.println("branch control id on save is : " + branch.getBranchControl());
		branchValidator.validate(branch, result);
		if (result.hasErrors()) {
			utilityService.OrgToOrgManagementDetails(model);
			List<HierarchyControl> hierarchyLists = null;
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				branch.setOrgManagement(
						mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement());
			} else {
				hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
			}
			model.addAttribute("hierarchyList", hierarchyLists);
			System.out.println("branch name is : " + hierarchyLists.size());

			model.addAttribute("branchManagement", branch);
			model.addAttribute("edit", false);
			model.addAttribute("parent", "false");
			return "newEditBranchManagementByGroup";
		}
		BranchManagement branchManagement = branchManagementService.getAvailableBranchCode(branch);
		if (branchManagement != null) {
			utilityService.OrgToOrgManagementDetails(model);
			List<HierarchyControl> hierarchyLists = null;
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				branch.setOrgManagement(
						mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement());
			} else {
				hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
			}
			model.addAttribute("hierarchyList", hierarchyLists);
			System.out.println("branch name is : " + hierarchyLists.size());
			model.addAttribute("branchManagement", branch);
			model.addAttribute("edit", false);
			model.addAttribute("parent", "false");

			if (branch.getBranchCode().trim().equalsIgnoreCase(branchManagement.getBranchCode().trim())) {
				model.addAttribute("branchCodeAlreadyExist",
						messageSource.getMessage("non.unique.branchCode", null, Locale.ENGLISH));
			}
			if (branch.getBranchEmail().trim().equalsIgnoreCase(branchManagement.getBranchEmail().trim())) {
				model.addAttribute("branchEmailAlreadyExist",
						messageSource.getMessage("non.unique.branchEmail", null, Locale.ENGLISH));
			}
			result.addError(result.getGlobalError());
			return "newEditBranchManagementByGroup";
		}

		HierarchyControl hierarchyControl = hierarchyManagementService.findById(branch.getHierarchyControl().getId());
		if (null != hierarchyControl) {
			if (hierarchyControl.getOrgManagement().getOrgLevel() == hierarchyControl.getHierarchyLevel()
					|| hierarchyControl.getHierarchyType() == 1) {
				branch.setIsgroup((byte) 0);
			} else {
				branch.setIsgroup((byte) 1);
			}
		}
		System.out.println("Branch control alias name is : " + branch.getBranchControl());
		if (branch.getBranchControl().equals("0")) {
			branch.setBranchControl(null);
		}
		String generatedBranchId = branchManagementService.save(branch);
		log.info("generated uuid for the branch name : " + branch.getBranchName() + " is returned as "
				+ generatedBranchId + " and also get from branch obj is : " + branch.getId());

		log.info("branch group level in edit value in post is : " + branch.getIsgroup());
		if (branch.getIsgroup() == 0) {
			User user = mySession.getUser();
			BranchManagement branchManagement1 = branchManagementService.findById(branch.getId());
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					log.info("now setting cluster to cluster group for the branch : "
							+ branchManagement1.getBranchName() + " and orgManagement id :"
							+ branchManagement1.getHierarchyControl().getOrgManagement().getId());
					try {
						int clusterCount = utilityService.makeClusterClusterGroup(branchManagement1, user);
					} catch (Exception e) {
						log.error("error generated during cluster cluster distance for branch id : "
								+ branchManagement1.getId() + " due to : " + e);
					}
				}
			});
			t.start();
		}

		String urlreferer = branch.getUrlReferer();
		if (action.equals("Save")) {
			/*
			 * if (branch.getRequestFrom().equals("byhierarchy")) { return
			 * "redirect:/hierarchyManagament"; } return
			 * "redirect:/branchmanagement";
			 */
			return "redirect:" + urlreferer;

		} else if (action.equals("SaveAndNew")) {
			if (branch.getRequestFrom().equals("byhierarchy")) {
				// branchmanagementNewGetMethod(model);
				return "branchmanagement-new";
			}
			return "redirect:/hierarchyManagament";
		}

		/**
		 * calculate radius of other branch with this branch and takes under
		 * cluster cluster group when there distance is of given parameter.
		 * cluster.distance
		 */

		return "redirect:" + urlreferer;
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_delete')")
	@RequestMapping(value = { "/branchmanagement-{id}-delete",
			"/branchManagement-{id}-Delete" }, method = RequestMethod.GET)
	public String branchmanagementDelete(@PathVariable String id, ModelMap model, HttpServletRequest request) {
		branchManagementService.deleteById(id);
		// branchManagementService.deleteByUUID(uuid);
		String referer = request.getHeader("Referer");
		System.out.println("On delete for branchmanagementDelete url referr is : " + referer);
		return "redirect:" + referer;
		// return "redirect:/branchmanagement";
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_update')")
	@RequestMapping(value = { "/branchmanagement-{id}-edit",
			"/branchManagement-{id}-Edit" }, method = RequestMethod.GET)
	public String branchmanagementEditGetMethod(RedirectAttributes redirectAttributes, @PathVariable String id,
			ModelMap model) {
		boolean flag = false;
		BranchManagement branch = branchManagementService.findById(id);
		log.info("branch group level in edit value in get is : " + branch.getIsgroup());
		if (null != branch) {
			BranchManagement branchControlName = branchManagementService.findById(branch.getBranchControl());
			if (null != branchControlName) {
				System.out.println("Branch cpontrol name is : " + branchControlName.getBranchName());
				branch.setBranchControlName(branchControlName.getBranchName());
			} else {
				System.out
						.println("Branch cpontrol name is : in else when parent id is : " + branch.getBranchControl());
				branch.setBranchControlName("Parent Branch");
			}
		}
		HierarchyControl hierarchyControl = null;
		if (branch != null) {
			model.addAttribute("branchManagement", branch);
			model.addAttribute("edit", true);

			List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
			model.addAttribute("orgTypeList", orgTypeList);
			Set<OrgManagement> orgManagementSet = null;
			OrgType orgType = branch.getOrgManagement().getOrgType();
			orgManagementSet = orgType.getOrgManagements();
			System.out.println("OrgManagementSet size IN EDIT for = " + orgManagementSet.size());
			model.addAttribute("orgManagementSet", orgManagementSet);

			System.out.println("HIERARCHY ID FOR BRANCH EDIT IS : " + branch.getHierarchyControl());
			hierarchyControl = branch.getHierarchyControl();
			Integer levelCountInOrg = branch.getOrgManagement().getOrgLevel();
			if (levelCountInOrg > 1) {
				if (!hierarchyControl.getParentId().equals(0)) {
					List<HierarchyControl> hierarchyLists = null;
					flag = utility.isAllowed("can_view_everything");
					if (!flag) {
						hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
					} else {
						hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
					}
					model.addAttribute("hierarchyList", hierarchyLists);
				} else {
					model.addAttribute("parent", "true");
				}
			} else {
				model.addAttribute("parent", "true");
				model.addAttribute("minlevels", "true");
				return "newEditBranchManagementByGroup";
			}
			return "newEditBranchManagementByGroup";
		} else {
			return "redirect:/branchmanagement";
		}
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_update')")
	@RequestMapping(value = { "/branchmanagement-{id}-edit",
			"/branchManagement-{id}-Edit" }, method = RequestMethod.POST)
	public String branchmanagementEditPostMethod(@PathVariable String id, @Valid BranchManagement branch,
			BindingResult result, ModelMap model) {
		boolean flag = false;
		branchValidator.validate(branch, result);
		if (result.hasErrors()) {
			BranchManagement branchManagement = branchManagementService.findById(id);
			HierarchyControl hierarchyControl = null;
			model.addAttribute("branchManagement", branch);
			model.addAttribute("edit", true);
			System.out.println(
					"Branch in edit post validation result if is : " + branch.getOrgManagement().getOrgType().getId());
			List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
			model.addAttribute("orgTypeList", orgTypeList);
			Set<OrgManagement> orgManagementSet = null;
			OrgType orgType = branchManagement.getOrgManagement().getOrgType();
			orgManagementSet = orgType.getOrgManagements();
			System.out.println("OrgManagementSet size IN EDIT for = " + orgManagementSet.size());
			model.addAttribute("orgManagementSet", orgManagementSet);

			System.out.println("HIERARCHY ID FOR BRANCH EDIT IS : " + branchManagement.getHierarchyControl());
			hierarchyControl = branchManagement.getHierarchyControl();
			Integer levelCountInOrg = branchManagement.getOrgManagement().getOrgLevel();
			if (levelCountInOrg > 1) {
				if (!hierarchyControl.getParentId().equals(0)) {
					List<HierarchyControl> hierarchyLists = null;
					flag = utility.isAllowed("can_view_everything");
					if (!flag) {
						hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
					} else {
						hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
					}
					model.addAttribute("hierarchyList", hierarchyLists);
				} else {
					model.addAttribute("parent", "true");
				}
			} else {
				model.addAttribute("parent", "true");
				model.addAttribute("minlevels", "true");
				return "newEditBranchManagementByGroup";
			}
			return "newEditBranchManagementByGroup";
		}

		HierarchyControl hierarchyControl = hierarchyManagementService.findById(branch.getHierarchyControl().getId());
		if (null != hierarchyControl) {
			if (hierarchyControl.getOrgManagement().getOrgLevel() == hierarchyControl.getHierarchyLevel()
					|| hierarchyControl.getHierarchyType() == 1) {
				branch.setIsgroup((byte) 0);
			} else {
				branch.setIsgroup((byte) 1);
			}
		}

		boolean latlongUpdate = branchManagementService.updateById(branch);
		/**
		 * calculate radius of other branch with this branch and takes under
		 * cluster cluster group when there distance is of given parameter.
		 * cluster.distance
		 */
		User user = mySession.getUser();
		if (!latlongUpdate) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					log.info("branch group level in edit value in post is : " + branch.getIsgroup());
					BranchManagement branchManagement = branchManagementService.findById(branch.getId());
					if (branchManagement.getIsgroup() == 0) {
						log.info("now setting cluster to cluster group for the branch : "
								+ branchManagement.getBranchName() + " and orgManagement id :"
								+ branchManagement.getHierarchyControl().getOrgManagement().getId());
						try {
							/**
							 * We are passing user object because session is not
							 * active for different thread other than current
							 */
							int clusterCount = utilityService.makeClusterClusterGroup(branchManagement, user);
						} catch (Exception e) {
							log.error("error generated during cluster cluster distance for branch id : "
									+ branchManagement.getId() + " due to : " + e);
						}
					}
				}
			});
			t.start();
		}

		String urlreferer = branch.getUrlReferer();
		return "redirect:" + urlreferer;
		// return "redirect:/branchmanagement";
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_read')")
	@RequestMapping(value = { "/branchmanagement-{id}-view" }, method = RequestMethod.GET)
	public String orgManagementViewGetMethod(@PathVariable String id, ModelMap model) {
		BranchManagement branch = branchManagementService.findById(id);
		// BranchManagement branch = branchManagementService.findByUUID(uuid);

		if (branch != null) {
			model.addAttribute("branch", branch);
			model.addAttribute("edit", true);
			return "viewBranchManagament";
		} else {
			return "redirect:/branchmanagement";
		}
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_read')")
	@RequestMapping(value = { "/hierarchymanagementbranch-{id}-hierarchy" }, method = RequestMethod.GET)
	public String hierarchyManagementBranch(@PathVariable String id, ModelMap model) {
		boolean flag = false;
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
		// HierarchyControl hierarchyControl =
		// hierarchyManagementService.findByUUID(uuid);
		System.out.println("hierarchy control branch list size : " + hierarchyControl.getBranchManagements().size());
		if (null != hierarchyControl) {
			List<HierarchyControl> hierarchyLists = null;
			BranchManagement br = null;
			br = mySession.getUser().getBranchManagement();
			BranchManagement branchManagement = new BranchManagement();
			flag = utility.isAllowed("can_view_everything");
			if (!flag) {
				hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
				model.addAttribute("hierarchyList", hierarchyLists);
				model.addAttribute("branch", br);
			} else {
				model.addAttribute("branch", branchManagement);
				hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
				utilityService.OrgToHierarchyControlDetails(model);
			}

			List<BranchManagement> branchManagementsList = branchManagementService
					.getAllBranchListbyhierarchy(hierarchyControl);
			System.out.println("branchmanagement list by hierarchy control size : " + branchManagementsList.size());

			List<BranchManagement> branchList = new ArrayList<BranchManagement>();

			List<BranchManagement> branchListLowerLevel = new ArrayList<BranchManagement>();
			branchListLowerLevel.add(br);
			branchListLowerLevel = utility.getAllChildBranchList(br, branchListLowerLevel);
			System.out.println("Now i am printing users in viewbranchByHierarchy list.................");
			utility.print(branchListLowerLevel);

			/*
			 * List<BranchManagement> branchFileterdList = new
			 * ArrayList<BranchManagement>(); branchFileterdList =
			 * branchManagementService
			 * .getAllBranchListbyBranchHierarchyAndLowerLevelBranches(
			 * branchManagement, branchListLowerLevel);
			 */

			System.out.println("Lower level list : " + branchListLowerLevel.size());
			System.out.println("Filtered list : " + branchManagementsList.size());

			List<BranchManagement> retainAllList = new ArrayList<BranchManagement>(branchManagementsList);
			retainAllList.retainAll(branchListLowerLevel);

			branchList = retainAllList;
			if (!flag) {
				model.addAttribute("branches", branchList);
			} else {
				model.addAttribute("branches", branchManagementsList);
			}
			model.addAttribute("userBranch", br);
			model.addAttribute("edit", false);
			System.out.println("BranchManagementsList size is : " + branchManagementsList.size());
			return "branchmManagement";
		} else {
			return "redirect:/branchmanagement";
		}
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_read')")
	@RequestMapping(value = { "/branchmanagement", "/branchManagement" }, method = RequestMethod.GET)
	public String branchManagementGetMethod(ModelMap model, @ModelAttribute("modelMsg") ModelMap model2) {
		return branchManagement(model, model2, null);
	}
	/*
	 * 
	 * { boolean flag = false; model.addAttribute("edit", false);
	 * List<HierarchyControl> hierarchyLists = null; List<BranchManagement>
	 * branchList = null; BranchManagement br = null; br =
	 * mySession.getUser().getBranchManagement(); BranchManagement
	 * branchManagement = new BranchManagement();
	 * 
	 * flag = utility.isAllowed("can_view_everything"); if (!flag) {
	 * hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
	 * model.addAttribute("hierarchyList", hierarchyLists);
	 * model.addAttribute("branch", br); model.addAttribute("initialFilter",
	 * true); branchList = new ArrayList<BranchManagement>();
	 * branchList.add(br); branchList = utility.getAllChildBranchList(br,
	 * branchList);
	 * 
	 * branchList = branchManagementService.
	 * getAllBranchListbyBranchHierarchyAndLowerLevelBranches(branchManagement,
	 * branchList);
	 * 
	 * System.out.println("Now i am printing users list.................");
	 * utility.print(branchList); } else { model.addAttribute("branch",
	 * branchManagement); hierarchyLists =
	 * hierarchyManagementService.getAllHierarchyControlList();
	 * utilityService.OrgToHierarchyControlDetails(model); branchList =
	 * (List<BranchManagement>)
	 * branchManagementService.getAllBranchGroupedAndNotgrouped();
	 * System.out.println("Userlist size is : " + branchList.size()); } int i =
	 * 0; int j = 0; List<BranchParameterStatus> branchParameterStatusList =
	 * br.getBranchParameterStatuses(); if (branchParameterStatusList != null) {
	 * for (BranchParameterStatus branchParameterStatus :
	 * branchParameterStatusList) { if
	 * (branchParameterStatus.getStatus().equals("CANCEL")) { i += 1; } else if
	 * (branchParameterStatus.getStatus().equals("PENDING")) { j += 1; }
	 * 
	 * } } if (i > 0) { model.addAttribute("editEOD", true); } if (j > 0) {
	 * model.addAttribute("approveEOD", true); }
	 * model.addAttribute("userBranch", br); model.addAttribute("branches",
	 * branchList); model.addAttribute("EodAlreadyDone",
	 * model2.get("eodalreadydone"));
	 * 
	 * return "branchmManagement"; }
	 */

	private String branchManagement(ModelMap model, ModelMap model2, BranchManagement branchManagement) {
		boolean flag = false;
		model.addAttribute("edit", false);
		List<HierarchyControl> hierarchyLists = new ArrayList<HierarchyControl>();
		List<BranchManagement> branchList = new ArrayList<>();
		BranchManagement br = null;
		br = mySession.getUser().getBranchManagement();

		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
			model.addAttribute("hierarchyList", hierarchyLists);
			if (branchManagement == null) {
				model.addAttribute("branch", br);
			} else {
				model.addAttribute("branch", branchManagement);
			}
			model.addAttribute("initialFilter", true);

			List<BranchManagement> branchListLowerLevel = new ArrayList<BranchManagement>();
			branchListLowerLevel.add(br);
			branchListLowerLevel = utility.getAllChildBranchList(br, branchListLowerLevel);

			branchList = branchManagementService
					.getAllBranchListbyBranchHierarchyAndLowerLevelBranches(branchManagement, branchListLowerLevel);

			System.out.println("Now i am printing users list................." + branchList.size());
			utility.print(branchList);
		} else if (branchManagement == null) {
			model.addAttribute("branch", new BranchManagement());
			hierarchyLists = hierarchyManagementService.getAllHierarchyControlList();
			utilityService.OrgToHierarchyControlDetails(model);
			branchList = (List<BranchManagement>) branchManagementService.getAllBranchGroupedAndNotgrouped();
			System.out.println("Userlist size is : " + branchList.size());
		} else {
			model.addAttribute("branch", branchManagement);
			branchList = branchManagementService.getAllBranchListAccToHierarchyOrganization(branchManagement);

			List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
			model.addAttribute("orgTypeList", orgTypeList);
			Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();

			OrgType orgType = orgTypeService
					.findById(branchManagement.getHierarchyControl().getOrgManagement().getOrgType().getId());

			if (null != orgType) {
				orgManagementSet = orgType.getOrgManagements();
				OrgManagement orgManagement = orgManagementService
						.findById(branchManagement.getHierarchyControl().getOrgManagement().getId());
				if (null != orgManagement) {
					hierarchyLists = orgManagement.getHierarchyControls();
				}
			}
			model.addAttribute("orgManagementSet", orgManagementSet);
			model.addAttribute("hierarchyList", hierarchyLists);

		}

		int i = 0;
		int j = 0;
		List<BranchParameterStatus> branchParameterStatusList = br.getBranchParameterStatuses();
		if (branchParameterStatusList != null) {
			for (BranchParameterStatus branchParameterStatus : branchParameterStatusList) {
				if (branchParameterStatus.getStatus().equals("CANCEL")) {
					i += 1;
				} else if (branchParameterStatus.getStatus().equals("PENDING")) {
					j += 1;
				}

			}
		}
		if (i > 0) {
			model.addAttribute("editEOD", true);
		}
		if (j > 0) {
			model.addAttribute("approveEOD", true);
		}
		model.addAttribute("userBranch", br);
		model.addAttribute("branches", branchList);
		if (model2 != null) {
			System.out.println("inside eod already done method...." + model2.get("eodalreadydone"));
			model.addAttribute("EodAlreadyDone", model2.get("eodalreadydone"));
		}
		return "branchmManagement";

	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_read')")
	@RequestMapping(value = { "/filterBranchHierarchyByOrganization" }, method = RequestMethod.GET)
	public String filterBranchHierarchyByOrganizationGet(BranchManagement branchManagement, ModelMap model) {
		System.out.println("Inside get method of filterBranchHierarchyByOrganization : " + branchManagement);
		return "redirect:/branchmanagement";
	}

	@PreAuthorize("hasAnyRole('can_branchmanagement_read')")
	@RequestMapping(value = { "/filterBranchHierarchyByOrganization" }, method = RequestMethod.POST)
	public String filterBranchHierarchyByOrganizationPost(BranchManagement branchManagement, ModelMap model) {

		return branchManagement(model, null, branchManagement);

		/*
		 * boolean flag = false;
		 * System.out.println("Inside filterBranchHierarchyByOrganization : " +
		 * branchManagement.getHierarchyControl().getOrgManagement());
		 * 
		 * List<HierarchyControl> hierarchyLists = new
		 * ArrayList<HierarchyControl>(); List<BranchManagement> branchList =
		 * null; BranchManagement br = null; br =
		 * mySession.getUser().getBranchManagement(); flag =
		 * utility.isAllowed("can_view_everything"); if (!flag) { hierarchyLists
		 * = utilityService.getHierarchyListAccToUserLevel(model);
		 * model.addAttribute("hierarchyList", hierarchyLists);
		 * List<BranchManagement> branchListLowerLevel = new
		 * ArrayList<BranchManagement>(); branchListLowerLevel.add(br);
		 * branchListLowerLevel = utility.getAllChildBranchList(br,
		 * branchListLowerLevel);
		 * System.out.println("Now i am printing users list.................");
		 * utility.print(branchListLowerLevel);
		 * 
		 * List<BranchManagement> branchFileterdList = new
		 * ArrayList<BranchManagement>(); branchFileterdList =
		 * branchManagementService.
		 * getAllBranchListbyBranchHierarchyAndLowerLevelBranches(
		 * branchManagement, branchListLowerLevel);
		 * 
		 * System.out.println("Lower level list : " +
		 * branchListLowerLevel.size()); System.out.println("Filtered list : " +
		 * branchFileterdList.size());
		 * 
		 * List<BranchManagement> retainAllList = new
		 * ArrayList<BranchManagement>(branchFileterdList);
		 * retainAllList.retainAll(branchListLowerLevel);
		 * 
		 * branchList = retainAllList;
		 * 
		 * System.out.
		 * println("retain list size from lowerlevel list to filtered list is :  "
		 * + branchList.size() + " and retain list size is : " +
		 * retainAllList.size()); } else { branchList =
		 * branchManagementService.getAllBranchListAccToHierarchyOrganization(
		 * branchManagement);
		 * 
		 * List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
		 * model.addAttribute("orgTypeList", orgTypeList); Set<OrgManagement>
		 * orgManagementSet = new HashSet<OrgManagement>();
		 * 
		 * OrgType orgType = orgTypeService
		 * .findById(branchManagement.getHierarchyControl().getOrgManagement().
		 * getOrgType().getId());
		 * 
		 * if (null != orgType) { orgManagementSet =
		 * orgType.getOrgManagements(); OrgManagement orgManagement =
		 * orgManagementService
		 * .findById(branchManagement.getHierarchyControl().getOrgManagement().
		 * getId()); if (null != orgManagement) { hierarchyLists =
		 * orgManagement.getHierarchyControls(); } }
		 * model.addAttribute("orgManagementSet", orgManagementSet);
		 * model.addAttribute("hierarchyList", hierarchyLists); }
		 * 
		 * model.addAttribute("userBranch", br); model.addAttribute("branch",
		 * branchManagement); model.addAttribute("branches", branchList);
		 * 
		 * return "branchmManagement";
		 */
	}

	@RequestMapping(value = { "/editClosedGroups-{id}" }, method = RequestMethod.GET)
	public String addEditBranchClosedGroups(@PathVariable String id, ModelMap model) {
		System.out.println("Inside get method of addBranchClosedGroups : " + id);

		BranchGroup branchGroup = new BranchGroup();
		BranchManagement branchManagement = branchManagementService.findById(id);
		Set<BranchGroup> branchGroupSet = branchManagement.getBranchGroups();
		List<BranchGroup> branchGroupList = new ArrayList<>();
		String groupId = null;
		if (branchGroupSet != null && branchGroupSet.size() > 0) {
			groupId = branchGroupSet.stream().findFirst().get().getGroupId();
			branchGroup.setGroupId(groupId);
			branchGroupList = branchGroupService.getBranchGroupList(branchGroup);
			System.out.println("branchClosedGroupList List size = " + branchGroupList.size());
		}

		List<BranchManagement> branchManagementsListByBranchHierarchy = branchManagementService
				.getRemainingBranchListByBranchHierarchy(branchGroupList);
		model.addAttribute("edit", true);
		model.addAttribute("branchGroup", branchGroup);
		model.addAttribute("branchManagement", branchManagement);
		model.addAttribute("selectedBranchList", branchGroupList);
		model.addAttribute("deselectedBranchList", branchManagementsListByBranchHierarchy);
		model.addAttribute("byDirectRoleAssign", false);

		return "branchgroupmanager";
	}

	@RequestMapping(value = { "/saveClosedBranchAssociation" }, method = RequestMethod.POST)
	public String saveUserRoleAssociation(@Valid BranchGroup branchGroup, BindingResult result, ModelMap model) {
		if (null != branchGroup) {
			branchGroupService.updateBranchGroup(branchGroup);
		}
		// String urlreferer = branchGroup.getParentBranch().getUrlReferer();
		// return "redirect:" + urlreferer;
		return "redirect:/branchmanagement";
	}

	/**
	 * list of close group
	 * 
	 * @author gourav
	 * @param model
	 * @param branchGroup
	 * @return
	 */
	@RequestMapping(value = { "/closegroupmanagement" }, method = RequestMethod.GET)
	public String closeGroupManagement(ModelMap model, BranchGroup branchGroup) {
		List<BranchGroup> branchClosedGroupList = branchGroupService.getBranchGroupList(branchGroup);

		Map<String, BranchGroup> map = new HashMap<>();
		for (BranchGroup bg : branchClosedGroupList) {
			int count = 1;
			if (map.containsKey(bg.getGroupId())) {
				count = map.get(bg.getGroupId()).getCount() + 1;
			}
			bg.setCount(count);

			map.put(bg.getGroupId(), bg);
		}

		model.addAttribute("branchGroup", branchGroup);
		model.addAttribute("groupMap", map);
		return "closegroupmanagement";
	}

	/**
	 * This is for create new close group of branches
	 * 
	 * @author gourav
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/closegroupmanagement-new" }, method = RequestMethod.GET)
	public String closeGroupManagementNew(ModelMap model) {
		String groupId = null;
		return closeGroupData(groupId, model);
	}

	/**
	 * method is use to edit group details
	 * 
	 * @author gourav
	 * @param groupId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/closegroupmanagement-{groupId}-edit" }, method = RequestMethod.GET)
	public String closeGroupManagementEdit(@PathVariable String groupId, ModelMap model) {
		return closeGroupData(groupId, model);
	}

	/**
	 * This is logic of add or edit close group
	 * 
	 * @author gourav
	 * @param groupId
	 * @param model
	 * @return
	 */
	private String closeGroupData(String groupId, ModelMap model) {
		BranchGroup branchGroup = new BranchGroup();
		List<BranchGroup> branchGroupList = new ArrayList<>();
		// Get already inserted Group details
		if (groupId != null) {
			branchGroup.setGroupId(groupId);
			branchGroupList = branchGroupService.getBranchGroupList(branchGroup);
			if (branchGroupList != null && branchGroupList.size() > 0) {
				branchGroup.setGroupName(branchGroupList.get(0).getGroupName());
			}
			System.out.println("branchClosedGroupList List size = " + branchGroupList.size());
		}
		/*
		 * Set<BranchManagement> branchCCListFromGroup =
		 * branchGroupList.stream().map(p->p.getBranchManagement()).filter(q->q.
		 * getHierarchyControl().getHierarchyType() ==
		 * 1).collect(Collectors.toSet()); List<BranchManagement>
		 * currencyChestList =
		 * branchManagementService.getAllCurrencyChestListExcludingBranch(
		 * branchCCListFromGroup);
		 */

		List<BranchManagement> branchManagementsListByBranchHierarchy = branchManagementService
				.getRemainingBranchListByBranchHierarchy(branchGroupList);

		model.addAttribute("edit", true);
		model.addAttribute("branchGroup", branchGroup);
		model.addAttribute("selectedBranchList", branchGroupList);
		model.addAttribute("deselectedBranchList", branchManagementsListByBranchHierarchy);
		// model.addAttribute("byDirectRoleAssign", false);
		return "branchgroupmanager";
	}

	/**
	 * 
	 * @param branchGroup
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/saveBranchGroupAssociation" }, method = RequestMethod.POST)
	public String saveBranchGroupAssociation(@Valid BranchGroup branchGroup, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			closeGroupData(branchGroup.getGroupId(), model);
			return "branchgroupmanager";
		}
		branchGroupService.updateBranchColsedGroup(branchGroup);
		return "redirect:/closegroupmanagement";
	}

	/**
	 * method is use to view group details
	 * 
	 * @author gourav
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/closegroupmanagement-{groupId}-view" }, method = RequestMethod.GET)
	public String closeGroupManagementView(@PathVariable String groupId, ModelMap model) {
		BranchGroup branchGroup = new BranchGroup();
		branchGroup.setGroupId(groupId);
		List<BranchGroup> branchGroups = branchGroupService.getBranchGroupList(branchGroup);
		if (branchGroups != null && branchGroups.size() > 0) {
			branchGroup.setGroupName(branchGroups.get(0).getGroupName());
		}
		model.addAttribute("branchGroupList", branchGroups);
		model.addAttribute("branchGroup", branchGroup);
		return "viewGroupBranches";
	}

	/**
	 * method is use to delete group
	 * 
	 * @author gourav
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/closegroupmanagement-{groupId}-delete" }, method = RequestMethod.GET)
	public String closeGroupManagementDelete(@PathVariable String groupId, ModelMap model) {
		branchGroupService.deleteByGroupId(groupId);
		return "redirect:/closegroupmanagement";
	}

	@RequestMapping(value = { "/editClosedGroups-{id}-old" }, method = RequestMethod.GET)
	public String addEditBranchClosedGroupsOld(@PathVariable String id, ModelMap model) {
		System.out.println("Inside get method of addBranchClosedGroups : " + id);

		BranchClosedGroup branchClosedGroup = new BranchClosedGroup();
		BranchManagement branchManagement = branchManagementService.findById(id);
		// BranchManagement branchManagement =
		// branchManagementService.findByUUID(uuid);
		branchClosedGroup.setParentBranch(branchManagement);
		// if (null != user && 0 != user.getId()) {
		List<BranchClosedGroup> branchClosedGroupList = branchClosedGroupService
				.getBranchClosedBranchList(branchManagement);
		System.out.println("branchClosedGroupList List size = " + branchClosedGroupList.size());
		// List<Role> roleList = roleService.getAllRoleList();

		List<BranchManagement> branchManagementsListByBranchHierarchy = branchManagementService
				.getAllBranchListByBranchHierarchy(branchManagement);
		model.addAttribute("edit", true);
		model.addAttribute("branchClosedGroup", branchClosedGroup);

		Set<String> assignedhascodeSet = new HashSet<String>();
		Iterator<BranchClosedGroup> it1 = branchClosedGroupList.iterator();
		while (it1.hasNext()) {
			BranchClosedGroup BranchClosedGroup2 = it1.next();
			BranchManagement branchManagement1 = BranchClosedGroup2.getClosedGroupBranch();
			assignedhascodeSet.add(branchManagement1.getId());
		}

		List<BranchManagement> notAssignedBranchList = new ArrayList<BranchManagement>();
		List<BranchManagement> assignedBranchList = new ArrayList<BranchManagement>();

		Iterator<BranchManagement> it = branchManagementsListByBranchHierarchy.iterator();
		while (it.hasNext()) {
			BranchManagement branchManagement2 = it.next();
			if (!assignedhascodeSet.contains(branchManagement2.getId())) {
				notAssignedBranchList.add(branchManagement2);
			} else {
				assignedBranchList.add(branchManagement2);
			}
		}

		// model.addAttribute("roleList", roleList);
		model.addAttribute("roleList", notAssignedBranchList);
		model.addAttribute("userRoleList", branchClosedGroupList);
		model.addAttribute("byDirectRoleAssign", false);

		System.out.println("notAssignedBranchList size is : " + notAssignedBranchList.size());
		return "branchclosedmanager";
	}

	@RequestMapping(value = { "/saveClosedBranchAssociation-old" }, method = RequestMethod.POST)
	public String saveUserRoleAssociationOld(@Valid BranchClosedGroup branchClosedGroup, BindingResult result,
			ModelMap model) {
		if (null != branchClosedGroup && !branchClosedGroup.getParentBranch().getId().equals("0")) {
			System.out.println("branchClosedGroup parent branch ids : " + branchClosedGroup.getParentBranch().getId());
			branchClosedGroupService.updateUserRoles(branchClosedGroup);
		}
		String urlreferer = branchClosedGroup.getParentBranch().getUrlReferer();
		return "redirect:" + urlreferer;
		// return "redirect:/usermanagement";
	}

	@RequestMapping(value = { "/viewclosedgroupbranchmanagement-{id}-branch-old" }, method = RequestMethod.GET)
	public String viewGroupBranchmanagement(@PathVariable String id, ModelMap model) {
		BranchManagement branchManagement = branchManagementService.findById(id);
		Set<BranchGroup> branchGroupList = branchManagement.getBranchGroups();
		if (branchGroupList != null && branchGroupList.size() > 0) {
			String groupId = branchGroupList.stream().findFirst().get().getGroupId();
			BranchGroup branchGroup = new BranchGroup();
			branchGroup.setGroupId(groupId);
			List<BranchGroup> branchGroups = branchGroupService.getBranchGroupList(branchGroup);
			model.addAttribute("branchGroupList", branchGroups);
		}
		model.addAttribute("branchManagement", branchManagement);
		return "viewGroupBranches";
	}

	@RequestMapping(value = { "/viewclosedgroupbranchmanagement-{id}-branch" }, method = RequestMethod.GET)
	public String viewClosedGroupBranchmanagement(@PathVariable String id, ModelMap model) {
		BranchManagement branchManagement = branchManagementService.findById(id);
		// BranchManagement branchManagement =
		// branchManagementService.findByUUID(uuid);
		System.out.println(
				"branch closed group size without filter is : " + branchManagement.getBranchClosedGroups().size());
		Set<BranchClosedGroup> branchClosedGroupsList = branchManagement.getBranchClosedGroups().stream()
				.filter(p -> p.getClosedGroupBranch().getDeleted() != (byte) 1).collect(Collectors.toSet());
		System.out.println("Now after filtered with not equal to 0 value size is : " + branchClosedGroupsList.size());
		branchManagement.setBranchClosedGroups(branchClosedGroupsList);
		model.addAttribute("branchManagement", branchManagement);
		return "viewClosedGroupBranches";
	}

	/**
	 * 
	 * @param branchManagement
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * 
	 * 		Unused method
	 */
	@RequestMapping(value = { "/brancheodposition" }, method = RequestMethod.POST)
	public String branchEODPosition(@Valid BranchManagement branchManagement, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		if (null != branchManagement && !branchManagement.getId().equals("0")) {
			System.out.println("branchClosedGroup parent branch ids after eod click : " + branchManagement.getId());
		}
		System.out.println("Inside redirecting if : " + branchManagement.getId());
		redirectAttributes.addFlashAttribute("branchId", branchManagement.getId());
		return "redirect:/savebrancheodposition";
		// return "redirect:/usermanagement";
	}

	@RequestMapping(value = { "/brancheodposition" }, method = RequestMethod.GET)
	public String branchEODPositionGet(BranchManagement branchManagement, ModelMap model) {
		System.out.println("Inside get method of branchEODPositionGet : " + branchManagement);
		return "redirect:/branchmanagement";
	}

	/**
	 * Correspondent branch management......
	 */
	
	@RequestMapping(value = { "/associatecorrespondentbranch-{id}" }, method = RequestMethod.GET)
	public String associateCorrespondentBranch(@PathVariable String id, ModelMap model) {
		System.out.println("Inside get method of associating correspondent branch for branch id : " + id);
		return associateCorrespondentBranchByBranchId(id,model);
	}
	
	public String associateCorrespondentBranchByBranchId(String id, ModelMap model){
		BranchManagement branchManagement = branchManagementService.findById(id);
		BranchCorrespondentGroup branchCorrespondentGroup = new BranchCorrespondentGroup();
		branchCorrespondentGroup.setParentCorrespondentBranch(branchManagement); 
		
		List<BranchCorrespondentGroup> branchCorrespondentGroupsList = new ArrayList<>();
		// Get already inserted correspondent branch details
		if (branchManagement != null) {
			System.out.println("branchClosedGroupList List size = " + branchManagement.getAssociateCorrespondentBranch());
		}
		
		Set<BranchManagement> associatedCorrespondentBranchesList = new HashSet<>();
		associatedCorrespondentBranchesList = branchManagement.getParentCorrespondentBranch().stream().map(p->p.getAssociateCorrespondentBranch()).collect(Collectors.toSet()); 
		System.out.println("associatedgroup Set size is = " + associatedCorrespondentBranchesList.size());
		List<BranchManagement> branchManagementsCorrespondentList = branchManagementService
				.getCorrespondentBranchList(branchManagement,associatedCorrespondentBranchesList);
		log.info("branchManagementsCorrespondentList Set size is = " + associatedCorrespondentBranchesList.size());		
		
		model.addAttribute("edit", true);
		model.addAttribute("branchCorrespondentGroup", branchCorrespondentGroup);
		model.addAttribute("selectedBranchList", associatedCorrespondentBranchesList);
		model.addAttribute("deselectedBranchList", branchManagementsCorrespondentList);
	
		return "branchCorrespondentManager";
	}
	
	@RequestMapping(value = { "/saveCorrespondentBranchAssociation" }, method = RequestMethod.POST)
	public String saveCorrespondentBranchAssociation(@Valid BranchCorrespondentGroup branchCorrespondentGroup, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			associateCorrespondentBranchByBranchId(branchCorrespondentGroup.getParentCorrespondentBranch().getId(), model);
			return "branchmanagement";
		}
		branchCorrespondentService.updateBranchCorrespondentGroup(branchCorrespondentGroup);
		return "redirect:/branchmanagement";
	}


}
