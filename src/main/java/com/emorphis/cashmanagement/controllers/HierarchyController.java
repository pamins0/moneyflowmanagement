package com.emorphis.cashmanagement.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.service.HierarchyManagementService;
import com.emorphis.cashmanagement.service.OrgManagementService;
import com.emorphis.cashmanagement.service.OrgTypeService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.Utility;

@Controller
public class HierarchyController {

	@Autowired
	OrgManagementService orgManagementService;

	@Autowired
	HierarchyManagementService hierarchyManagementService;

	@Autowired
	UtilityService utilityService;

	@Autowired
	Utility utility;

	@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	MySession mySession;

	@PreAuthorize("hasAnyRole('can_hierarchymanagement_read')")
	@RequestMapping({ "/hierarchyManagament" })
	public String hierarchyManagament(ModelMap model, @ModelAttribute("modelMsg") ModelMap model2) {
		HierarchyControl hierarchyControl = new HierarchyControl();
		List<HierarchyControl> hierarchyControlsList = null;
		hierarchyControlsList = this.hierarchyManagementService.getAllHierarchyControlList();
		
		BranchManagement br = mySession.getUser().getBranchManagement();

		List<BranchManagement> branchList = new ArrayList<BranchManagement>();

		List<BranchManagement> branchListLowerLevel = new ArrayList<BranchManagement>();
		branchListLowerLevel.add(br);
		branchListLowerLevel = utility.getAllChildBranchList(br, branchListLowerLevel);
		System.out.println("Now i am printing users in viewbranchByHierarchy list.................");
		utility.print(branchListLowerLevel);

		System.out.println("Lowerssss level list : " + branchListLowerLevel.size());

		List<HierarchyControl> filteredHierarchyControlsList = new ArrayList<HierarchyControl>();

		System.out.println("filtered hierarchy List size is : " + filteredHierarchyControlsList.size()
				+ " and fetched hierarchyControlList size is : " + hierarchyControlsList.size());
		boolean flag = false;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			for (HierarchyControl hierarchyControl2 : hierarchyControlsList) {
				// List<BranchManagement> filteredBranchList=
				// hierarchyControl2.getBranchManagements().stream().filter(p ->
				// p.getDeleted()==0).collect(Collectors.toList());
				List<BranchManagement> filteredBranchList = hierarchyControl2.getBranchManagements();

				List<BranchManagement> retainAllList = new ArrayList<BranchManagement>(filteredBranchList);
				retainAllList.retainAll(branchListLowerLevel);
				branchList = retainAllList;

				hierarchyControl2.setBranchManagements(branchList);
				filteredHierarchyControlsList.add(hierarchyControl2);
			}
			model.addAttribute("hierarchyManagementList", filteredHierarchyControlsList);
		} else {
			model.addAttribute("hierarchyManagementList", hierarchyControlsList);
		}

		utilityService.OrgToOrgManagementDetails(model);
		model.addAttribute("hierarchyControl", hierarchyControl);
		model.addAttribute("parentBranchesNotCreated", model2.get("parentBranchesNotCreated"));
		return "hierarchyManagament";
	}

	@PreAuthorize("hasAnyRole('can_hierarchymanagement_create')")
	@RequestMapping(value = { "/addhierarchy-{id}", "/addhierarchy-{id}" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String addHierarchyGet(@PathVariable String id, ModelMap model) {
		HierarchyControl hierarchyControl = new HierarchyControl();
		OrgManagement orgManagement = null;
		if (!id.equals(0)) {
			orgManagement = this.orgManagementService.findById(id);
			if (orgManagement != null) {
				hierarchyControl.setOrgManagement(orgManagement);
				System.out.println("Org management in hierarchy control adding hierarchies list size is " + ": "
						+ orgManagement.getHierarchyControls().size() + " and orgManagement hierarchy level is : "
						+ orgManagement.getOrgLevel());
				if (orgManagement.getOrgLevel() == orgManagement.getHierarchyControls().size()) {
					return "home";
				}
			} else {
				return "home";
			} 
		}

		model.addAttribute("hierarchyControl", hierarchyControl);

		model.addAttribute("edit", "false");

		return "newHierarchyManagement";
	}

	@PreAuthorize("hasAnyRole('can_hierarchymanagement_create')")
	@RequestMapping(value = { "/addhierarchy-{id}", "/addhierarchy-{id}" }, method = RequestMethod.POST)
	public String addHierarchyPost(@PathVariable String id, @Valid HierarchyControl hierarchyControl,
			BindingResult result, ModelMap model) {
		/*
		 * if(result.hasErrors()){ model.addAttribute("hierarchyControl",
		 * hierarchyControl); model.addAttribute("edit", "false"); return
		 * "newHierarchyManagement"; }
		 */
		OrgManagement orgManagement = orgManagementService.findById(hierarchyControl.getOrgManagement().getId()); 
		hierarchyControl.setOrgManagement(orgManagement); 
		System.out.println("Organization id : " + hierarchyControl.getOrgManagement());
		System.out.println("Organization id : " + hierarchyControl.getOrgManagement().getId());
		int generatedId = this.hierarchyManagementService.save(hierarchyControl);
		if (generatedId == 0) {
			return "newHierarchyManagement";
		}
		return "redirect:/hierarchyManagament";
	}

	@PreAuthorize("hasAnyRole('can_hierarchymanagement_read')")
	@RequestMapping(value = { "/organizationHierarchy-{id}-view" }, method = RequestMethod.GET)
	public String organizationHierarchyView(@PathVariable String id, ModelMap model) {
		HierarchyControl hierarchyControl = new HierarchyControl();
		List<HierarchyControl> hierarchyControlsList = null;
		hierarchyControlsList = this.hierarchyManagementService.getAllHierarchyControlListByOrgId(id);

		BranchManagement br = mySession.getUser().getBranchManagement();

		List<BranchManagement> branchList = new ArrayList<BranchManagement>();

		List<BranchManagement> branchListLowerLevel = new ArrayList<BranchManagement>();
		branchListLowerLevel.add(br);
		branchListLowerLevel = utility.getAllChildBranchList(br, branchListLowerLevel);
		System.out.println("Now i am printing users in viewbranchByHierarchy list.................");
		utility.print(branchListLowerLevel);

		System.out.println("Lower level list : " + branchListLowerLevel.size());

		List<HierarchyControl> filteredHierarchyControlsList = new ArrayList<HierarchyControl>();

		for (HierarchyControl hierarchyControl2 : hierarchyControlsList) {
			// List<BranchManagement> filteredBranchList=
			// hierarchyControl2.getBranchManagements().stream().filter(p ->
			// p.getDeleted()==0).collect(Collectors.toList());
			List<BranchManagement> filteredBranchList = hierarchyControl2.getBranchManagements();

			List<BranchManagement> retainAllList = new ArrayList<BranchManagement>(filteredBranchList);
			retainAllList.retainAll(branchListLowerLevel);
			branchList = retainAllList;
			System.out.println("branchlist in hierarchy control : " + branchList.size() + " hierarchy control : "
					+ hierarchyControl2.getName());
		//	hierarchyControl2.setBranchManagements(branchList);
			filteredHierarchyControlsList.add(hierarchyControl2);
		}

		System.out.println("filtered hierarchy List size is : " + filteredHierarchyControlsList.size()
				+ " and fetched hierarchyControlList size is : " + hierarchyControlsList.size());

		model.addAttribute("hierarchyManagementList", filteredHierarchyControlsList);
		utilityService.OrgToOrgManagementDetails(model);
		model.addAttribute("hierarchyControl", hierarchyControl);

		return "hierarchyManagament";
	}

	@PreAuthorize("hasAnyRole('can_hierarchymanagement_update')")
	@RequestMapping(value = { "/hierarchymanagement-{id}-edit" }, method = RequestMethod.GET)
	public String editHierarchyGet(@PathVariable String id, ModelMap model) {
		System.out.println("Hierarchy id for edit is  : " + id);
		HierarchyControl hierarchyControl = this.hierarchyManagementService.findById(id);
		// HierarchyControl hierarchyControl =
		// this.hierarchyManagementService.findByUUID(uuid);
		if (hierarchyControl != null) {
			model.addAttribute("hierarchyControl", hierarchyControl);
			model.addAttribute("edit", Boolean.valueOf(true));
			return "editHierarchyManagement";
		}
		return "redirect:/hierarchyManagament";
	}

	@PreAuthorize("hasAnyRole('can_hierarchymanagement_update')")
	@RequestMapping(value = { "/hierarchymanagement-{id}-edit" }, method = RequestMethod.POST)
	public String editHierarchyPost(@Valid HierarchyControl hierarchyControl, @PathVariable String id, ModelMap model) {
		this.hierarchyManagementService.updateById(hierarchyControl);
		// this.hierarchyManagementService.updateByUUID(hierarchyControl);
		return "redirect:/hierarchyManagament";
	}

	@PreAuthorize("hasAnyRole('can_hierarchymanagement_delete')")
	@RequestMapping(value = { "/hierarchymanagement-{id}-delete" }, method = RequestMethod.GET)
	public String orgTypeDelete(@PathVariable String id, ModelMap model, RedirectAttributes redirectAttributes) {
		boolean flag = this.hierarchyManagementService.deleteById(id);
		// boolean flag = this.hierarchyManagementService.deleteByUUID(uuid);
		if (!flag) {
			redirectAttributes.addAttribute("error_NotAuthorize", Boolean.valueOf(true));
		}
		return "redirect:/hierarchyManagament";
	}

	@PreAuthorize("hasAnyRole('can_hierarchymanagement_read')")
	@RequestMapping(value = { "/hierarchymanagement-{id}-view" }, method = RequestMethod.GET)
	public String orgManagementViewGetMethod(@PathVariable String id, ModelMap model) {
		HierarchyControl hierarchyControl = hierarchyManagementService.findById(id);
		// HierarchyControl hierarchyControl =
		// hierarchyManagementService.findByUUID(uuid);
		if (hierarchyControl != null) {
			model.addAttribute("hierarchyControl", hierarchyControl);
			model.addAttribute("edit", true);
			return "viewHierarchyManagament";
		} else {
			return "redirect:/hierarchyManagament";
		}
	}

	@PreAuthorize("hasAnyRole('can_hierarchymanagement_read')")
	@RequestMapping(value = { "/filterHierarchyByOrganization" }, method = RequestMethod.GET)
	public String filterHierarchyByOrganizationGet(HierarchyControl hierarchyControl, ModelMap model) {

		return "redirect:/hierarchyManagament";
	}

	@PreAuthorize("hasAnyRole('can_hierarchymanagement_read')")
	@RequestMapping(value = { "/filterHierarchyByOrganization" }, method = RequestMethod.POST)
	public String filterHierarchyByOrganizationPost(HierarchyControl hierarchyControl, ModelMap model) {
		boolean flag = false;
		System.out.println("Inside fileterHierarchyByOrganization : " + hierarchyControl.getOrgManagement());
		List<HierarchyControl> hierarchyLists = null;
		flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			hierarchyLists = utilityService.getHierarchyListAccToUserLevel(model);
		} else {
			hierarchyLists = hierarchyManagementService.getAllHierarchyControlListAccToOrganization(hierarchyControl);
		}

		// utilityService.OrgToOrgManagementDetails(model);

		List<OrgType> orgTypeList = orgTypeService.getAllOrgTypes();
		model.addAttribute("orgTypeList", orgTypeList);
		Set<OrgManagement> orgManagementSet = new HashSet<OrgManagement>();

		OrgType orgType = orgTypeService.findById(hierarchyControl.getOrgManagement().getOrgType().getId());
		// System.out.println("Org type is : "+orgType.getOrgDetail());
		if (null != orgType) {
			orgManagementSet = orgType.getOrgManagements();
		}
		model.addAttribute("orgManagementSet", orgManagementSet);

		model.addAttribute("hierarchyControl", hierarchyControl);
		model.addAttribute("hierarchyManagementList", hierarchyLists);
		return "hierarchyManagament";
	}

}
