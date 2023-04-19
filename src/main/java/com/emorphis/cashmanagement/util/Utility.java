package com.emorphis.cashmanagement.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.emorphis.cashmanagement.dao.BidAcceptApprovalDao;
import com.emorphis.cashmanagement.dao.BidRequestApprovalDao;
import com.emorphis.cashmanagement.dao.BidRequestDao;
import com.emorphis.cashmanagement.dao.BidRequestToDao;
import com.emorphis.cashmanagement.dao.BranchClosedGroupDao;
import com.emorphis.cashmanagement.dao.BranchGroupDao;
import com.emorphis.cashmanagement.dao.BranchManagementDao;
import com.emorphis.cashmanagement.dao.BranchParameterStatusDao;
import com.emorphis.cashmanagement.dao.DepartmentDao;
import com.emorphis.cashmanagement.dao.DesignationDao;
import com.emorphis.cashmanagement.dao.HierarchyManagementDao;
import com.emorphis.cashmanagement.dao.OrgManagementDao;
import com.emorphis.cashmanagement.dao.OrgTypeDao;
import com.emorphis.cashmanagement.dao.PermissionDao;
import com.emorphis.cashmanagement.dao.PlaceCashRequestDao;
import com.emorphis.cashmanagement.dao.PlaceCashRequestSwapDao;
import com.emorphis.cashmanagement.dao.RoleDao;
import com.emorphis.cashmanagement.dao.RolePermissionDao;
import com.emorphis.cashmanagement.dao.UserManagementDao;
import com.emorphis.cashmanagement.dao.UserRoleManagementDao;
import com.emorphis.cashmanagement.model.BidAcceptApproval;
import com.emorphis.cashmanagement.model.BidRequest;
import com.emorphis.cashmanagement.model.BidRequestApproval;
import com.emorphis.cashmanagement.model.BidRequestedTo;
import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.BranchParameter;
import com.emorphis.cashmanagement.model.BranchParameterStatus;
import com.emorphis.cashmanagement.model.Department;
import com.emorphis.cashmanagement.model.Designation;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.model.OrgType;
import com.emorphis.cashmanagement.model.Permission;
import com.emorphis.cashmanagement.model.PlaceCashRequest;
import com.emorphis.cashmanagement.model.PlaceCashRequestSwap;
import com.emorphis.cashmanagement.model.RequestStatus;
import com.emorphis.cashmanagement.model.Role;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;
import com.emorphis.cashmanagement.service.BranchParameterService;
import com.emorphis.cashmanagement.service.PlaceCashRequestService;
import com.emorphis.cashmanagement.service.PlaceCashRequestSwapService;
import com.emorphis.cashmanagement.service.RequestStatusService;
import com.emorphis.cashmanagement.service.RolePermissionService;
import com.emorphis.cashmanagement.service.RoleService;
import com.emorphis.cashmanagement.service.UserRoleManagementService;

@Component
public class Utility {

	private static final Logger log = LoggerFactory.getLogger(Utility.class);

	@Autowired
	MySession mySession;

	@Autowired
	RolePermissionService rolePermissionService;

	@Autowired
	RoleService roleService;

	@Autowired
	RequestStatusService requestStatusService;

	@Autowired
	UserRoleManagementService userRoleManagementService;

	@Autowired
	BranchParameterService branchParameterService;

	@Autowired
	PlaceCashRequestService placeCashRequestService;

	@Autowired
	PlaceCashRequestSwapService placeCashRequestSwapService;

	@Autowired
	OrgManagementDao orgManagementDao;

	@Autowired
	OrgTypeDao orgTypeDao;

	@Autowired
	UserManagementDao userManagementDao;

	@Autowired
	BranchManagementDao branchManagementDao;

	@Autowired
	BranchParameterStatusDao branchParameterStatusDao;

	@Autowired
	HierarchyManagementDao hierarchyManagementDao;

	@Autowired
	RoleDao roleDao;

	@Autowired
	UserRoleManagementDao userRoleManagementDao;

	@Autowired
	RolePermissionDao rolePermissionDao;

	@Autowired
	DesignationDao designationDao;

	@Autowired
	DepartmentDao departmentDao;

	@Autowired
	PermissionDao permissionDao;

	@Autowired
	PlaceCashRequestDao placeCashRequestDao;

	@Autowired
	PlaceCashRequestSwapDao placeCashRequestSwapDao;

	@Autowired
	BranchClosedGroupDao branchClosedGroupDao;

	@Autowired
	BranchGroupDao branchGroupDao;

	@Autowired
	BidRequestDao bidRequestDao;
	@Autowired
	BidRequestToDao bidRequestToDao;

	@Autowired
	BidRequestApprovalDao bidRequestApprovalDao;
	
	@Autowired
	BidAcceptApprovalDao bidAcceptApprovalDao;

	List<BranchParameter> branchParametersList = null;

	static Map<String, RequestStatus> requestStatusMap = null;

	static Map<String, BranchParameter> branchParameterStatusMap = null;

	public BranchParameter getBranchParameterValue(String statusVal) {
		if (branchParameterStatusMap == null) {
			log.info("inside initial request branch parameter when map not initialize : ");
			branchParameterStatusMap = new HashMap<String, BranchParameter>();
			initializeBranchParameterStatusMap();
		}

		BranchParameter branchParameter = branchParameterStatusMap.get(statusVal);
		;
		return branchParameter;
	}

	private void initializeBranchParameterStatusMap() {
		List<BranchParameter> branchParamList = branchParameterService.getAllBranchParametersList();
		for (BranchParameter branchParameter : branchParamList) {
			log.info("Branch Param key value pairs are : ");
			log.info(branchParameter.getParameterName() + "   :   " + branchParameter.getId());
			branchParameterStatusMap.put(branchParameter.getParameterAbbreviation(), branchParameter);
		}
	}

	public RequestStatus getRequestStatusValue(String statusVal) {
		if (requestStatusMap == null) {
			System.out.println("Inside initial request status initialization when map not initialized");
			requestStatusMap = new HashMap<String, RequestStatus>();
			initializeRequestStatusMap();
		}

		RequestStatus requestStatus = requestStatusMap.get(statusVal);
		return requestStatus;
	}

	public void initializeRequestStatusMap() {
		List<RequestStatus> requestStatusList = requestStatusService.getAllRequestStatusList();
		for (RequestStatus requestStatus : requestStatusList) {
			System.out.println("RequestStatus key value pairs are : ");
			System.out.println(requestStatus.getStatus() + "   :   " + requestStatus.getId());
			requestStatusMap.put(requestStatus.getStatus(), requestStatus);
		}
	}

	public List<BranchParameter> getAllBranchParameterList() {
		if (branchParametersList == null) {
			System.out.println("Initial for the first time if branch parameter list is null : ");
			branchParametersList = new ArrayList<BranchParameter>();
			initializeBranchParameterList();
		} else {
			System.out.println("branch parameter list is not null......" + branchParametersList.size());
		}
		return branchParametersList;
	}

	public void initializeBranchParameterList() {
		branchParametersList = branchParameterService.getAllBranchParametersList();
	}

	public static Boolean isAllowed(String keyId, List<RolePermission> permissions) {

		Boolean flag = false;

		try {

			for (RolePermission rolePermission : permissions) {

				/*
				 * System.out.println("Permission : " +
				 * rolePermission.getPermission().getTitle() + "  Grant :"+
				 * rolePermission.getGrant()); for (PermissionUri permissionUri
				 * : rolePermission.getPermission().getPermissionUris()) {
				 * System.out.println(" Permission_Uri: "+
				 * permissionUri.getUri()); }
				 */

				if (rolePermission.getPermission().getAbbreviation().equalsIgnoreCase(keyId)) {
					return rolePermission.getGrantPermission() != 0 ? true : false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return flag;
		}
		return flag;
	}

	public Boolean isAllowed(String keyId) {

		List<RolePermission> permissions = rolePermissionService.getRolePermissionListByUser(mySession.getUser());

		Boolean flag = false;

		try {

			for (RolePermission rolePermission : permissions) {

				if (rolePermission.getPermission().getAbbreviation().equalsIgnoreCase(keyId)) {
					return rolePermission.getGrantPermission() != 0 ? true : false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return flag;
		}
		return flag;
	}

	/**
	 * Checking permission for eachUser for permission checking :
	 * 
	 * @param user2
	 * @return
	 */

	public Boolean isAllowedPermissionByUser(String keyId, User user) {

		List<RolePermission> permissions = rolePermissionService.getRolePermissionListByUser(user);

		Boolean flag = false;

		try {

			for (RolePermission rolePermission : permissions) {

				if (rolePermission.getPermission().getAbbreviation().equalsIgnoreCase(keyId)) {
					return rolePermission.getGrantPermission() != 0 ? true : false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return flag;
		}
		return flag;
	}

	public List<User> getBranchAdminsList(User user2) {
		List<User> usersList = new ArrayList<User>();
		// List<Role> roleList = roleService.getAllRoleList();
		List<Role> roleList = roleService.getAllRoleListWithoutFilter();
		System.out.println("role id list size according to user branch admin : " + roleList.size());
		String role_id = "0";
		for (Role role : roleList) {
			if (role.getTitle().trim().equals("Branch Admin")) {
				// System.out.println("role id for Branch admin : " +
				// role.getId());
				role_id = role.getId();
			}
		}

		List<UserRole> userRoleList = userRoleManagementService.getAllUserRoles(user2, role_id);
		for (UserRole userRole : userRoleList) {
			if (user2.getBranchManagement().getId() == userRole.getUser().getBranchManagement().getId()) {
				// System.out.println("Branch Admin users name is : " +
				// userRole.getUser().getFirstName()
				// + " and its id is : " + userRole.getUser().getId());
				usersList.add(userRole.getUser());
			}
		}

		return usersList;
	}

	public List<User> getOrganizationAdminsList(User user2) {
		List<User> usersList = new ArrayList<User>();
		// List<Role> roleList = roleService.getAllRoleList();
		List<Role> roleList = roleService.getAllRoleListWithoutFilter();
		String role_id = "0";
		for (Role role : roleList) {
			if (role.getTitle().trim().equals("Organization Admin")) {
				// System.out.println("role id for organization admin : " +
				// role.getId());
				role_id = role.getId();
			}
		}

		List<UserRole> userRoleList = userRoleManagementService.getAllUserRoles(user2, role_id);
		// System.out.println("Organization admins total size in application is
		// : " + userRoleList.size());
		for (UserRole userRole : userRoleList) {
			if (user2.getBranchManagement().getOrgManagement().getId() == userRole.getUser().getBranchManagement()
					.getOrgManagement().getId()) {
				// System.out.println("Organization users name is : " +
				// userRole.getUser().getFirstName()
				// + " and its id is : " + userRole.getUser().getId());
				usersList.add(userRole.getUser());
			}
		}
		return usersList;
	}

	/**
	 * By Pavan......
	 */

	public List<BranchManagement> getAllChildBranchList(BranchManagement bm, List<BranchManagement> list) {

		for (BranchManagement branch : bm.getBranchControlalias()) {
			list.add(branch);
			getAllChildBranchList(branch, list);
		}
		return list;
	}

	/**
	 * Old Method before applying filter to user roles delete one not comes
	 * 
	 * @param bm
	 */
	/*
	 * public List<User> getAllChildBranchUserList(BranchManagement bm,
	 * List<User> list) {
	 * 
	 * for (BranchManagement branch : getAllChildBranchList(bm, new
	 * ArrayList<BranchManagement>())) { list.addAll(branch.getUsers()); }
	 * return list; }
	 */

	public List<User> getAllChildBranchUserList(BranchManagement bm, List<User> list) {

		for (BranchManagement branch : getAllChildBranchList(bm, new ArrayList<BranchManagement>())) {
			System.out.println("User list : " + branch.getUsers().size());
			list.addAll(getRoleDeletedFilteredList(branch.getUsers()));
		}
		return list;
	}

	public void print(List<BranchManagement> bm) {
		for (BranchManagement branch : bm) {
			// System.out.println(branch.getBranchName());
		}
	}

	public void printUsers(List<User> userList) {
		for (User user : userList) {
			// System.out.println(user.getUserId());
		}
	}

	public void getCurrentUserModel(ModelMap model) {
		User userobj = mySession.getUser();
		model.addAttribute("userObj", userobj);
	}

	public void getCurrentHierarchyModel(ModelMap model) {
		model.addAttribute("userHierarchy", mySession.getUser().getBranchManagement().getHierarchyControl());
	}

	public List<User> getRoleDeletedFilteredList(List<User> usersList) {
		List<User> userFilteredList = new ArrayList<User>();
		for (User user2 : usersList) {
			Set<UserRole> filtereduserRoleSet = user2.getUserRoles().stream().filter(p -> p.getRole().getDeleted() == 0)
					.collect(Collectors.toSet());
			user2.setUserRoles(filtereduserRoleSet);
			userFilteredList.add(user2);
		}
		return userFilteredList;
	}

	/**
	 * Allow url or deny url which directly hit on browser
	 * 
	 * @param orgManagement
	 * @return
	 */

	public boolean allowUrlAccessToOrgManagement(OrgManagement orgManagement) {
		boolean flag = false;
		boolean valid = false;
		if (orgManagement != null) {
			flag = isAllowed("can_view_everything");
			if (!flag) {
				if (orgManagement.getId() == mySession.getUser().getBranchManagement().getHierarchyControl()
						.getOrgManagement().getId()) {
					valid = true;
					return valid;
				} else {
					valid = false;
					return valid;
				}
			} else {
				valid = true;
				return true;
			}
		} else {
			valid = false;
			return valid;
		}
	}

	public void checkTimeForPlacedBidsInNormalAndSwappingBid(BranchManagement branchManagement) {

		List<PlaceCashRequest> placeCashRequestsLists = new ArrayList<PlaceCashRequest>();
		placeCashRequestsLists = placeCashRequestService
				.getAllPlacedBidForTheBranch(mySession.getUser().getBranchManagement());

		List<PlaceCashRequestSwap> placeCashRequestSwapsList = new ArrayList<PlaceCashRequestSwap>();
		placeCashRequestSwapsList = placeCashRequestSwapService
				.getAllPlacedSwapBidForTheBranch(mySession.getUser().getBranchManagement());

	}

	public String getOrgManagementUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getOrgManagementUUID method is : " + uuids);
		OrgManagement orgManagement = orgManagementDao.findByUUID(uuids);
		if (orgManagement != null) {
			getOrgManagementUUID();
		} else {
			return uuids;
		}
		return null;
	}

	public String getOrgTypeUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getOrgTypeUUID method is : " + uuids);
		OrgType orgType = orgTypeDao.findByUUID(uuids);
		if (orgType != null) {
			getOrgTypeUUID();
		} else {
			return uuids;
		}
		return null;
	}

	public String getUserUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getUserUUID method is : " + uuids);
		User user = userManagementDao.findByUUID(uuids);
		if (user != null) {
			getUserUUID();
		} else {
			return uuids;
		}
		return null;
	}

	public String getBranchManagementUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getBranchManagementUUID method is : " + uuids);
		BranchManagement branchManagement = branchManagementDao.findByUUID(uuids);
		if (branchManagement != null) {
			getBranchManagementUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getBranchParameterStatusUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getBranchParameterStatusUUID method is : " + uuids);
		BranchParameterStatus branchParameterStatus = branchParameterStatusDao.findByUUID(uuids);
		if (branchParameterStatus != null) {
			getBranchParameterStatusUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getHierarchyControlUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getHierarchyControlUUID method is : " + uuids);
		HierarchyControl hierarchyControl = hierarchyManagementDao.findByUUID(uuids);
		if (hierarchyControl != null) {
			getHierarchyControlUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getRoleUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getRoleUUID method is : " + uuids);
		Role role = roleDao.findByUUID(uuids);
		if (role != null) {
			getRoleUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getUserRoleUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getUserRoleUUID method is : " + uuids);
		UserRole userRole = userRoleManagementDao.findByUUID(uuids);
		if (userRole != null) {
			getUserRoleUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getUserPermissionUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getUserRoleUUID method is : " + uuids);
		RolePermission rolePermission = rolePermissionDao.findByUUID(uuids);
		if (rolePermission != null) {
			getUserPermissionUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getDesignationUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getDesignationUUID method is : " + uuids);
		Designation designation = designationDao.findByUUID(uuids);
		if (designation != null) {
			getDesignationUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getDepartmentUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getDepartmentUUID method is : " + uuids);
		Department department = departmentDao.findByUUID(uuids);
		if (department != null) {
			getDepartmentUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getPermissionUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getPermissionUUID method is : " + uuids);
		Permission permission = permissionDao.findByUUID(uuids);
		if (permission != null) {
			getPermissionUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getPlaceCashRequestUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getPlaceCashRequestUUID method is : " + uuids);
		PlaceCashRequest placeCashRequest = placeCashRequestDao.findByUUID(uuids);
		if (placeCashRequest != null) {
			getPlaceCashRequestUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getPlaceCashRequestSwapUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getPlaceCashRequestSwapUUID method is : " + uuids);
		PlaceCashRequestSwap placeCashRequestSwap = placeCashRequestSwapDao.findByUUID(uuids);
		if (placeCashRequestSwap != null) {
			getPlaceCashRequestSwapUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getBranchClosedGroupUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getBranchClosedGroupUUID method is : " + uuids);
		BranchClosedGroup branchClosedGroup = branchClosedGroupDao.findByUUID(uuids);
		if (branchClosedGroup != null) {
			getBranchClosedGroupUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getBranchGroupUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getBranchGroupUUID method is : " + uuids);
		BranchGroup branchGroup = branchGroupDao.findByUUID(uuids);
		if (branchGroup != null) {
			getBranchGroupUUID();
		} else {
			return uuids;
		}
		return "";
	}

	public String getBidRequestUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getBidRequestUUID method is : " + uuids);
		BidRequest bidRequest = bidRequestDao.findByUUID(uuids);
		if (bidRequest != null) {
			getBidRequestUUID();
		} else {
			return uuids;
		}
		return null;
	}

	public String getBidRequestedToUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getBidRequestedToUUID method is : " + uuids);
		BidRequestedTo bidRequestedTo = bidRequestToDao.findByUUID(uuids);
		if (bidRequestedTo != null) {
			getBidRequestedToUUID();
		} else {
			return uuids;
		}
		return null;
	}

	public String getBidRequestApprovalUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getBidRequestApprovalUUID method is : " + uuids);
		BidRequestApproval bidRequestApproval = bidRequestApprovalDao.findByUUID(uuids);
		if (bidRequestApproval != null) {
			getBidRequestApprovalUUID();
		} else {
			return uuids;
		}
		return null;
	}

	public String getBidAcceptApprovalUUID() {
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString();
		System.out.println("UUID Generated in getBidAcceptApprovalUUID method is : " + uuids);
		BidAcceptApproval bidAcceptApproval= bidAcceptApprovalDao.findByUUID(uuids);
		if (bidAcceptApproval != null) {
			getBidAcceptApprovalUUID();
		} else {
			return uuids;
		}
		return null;
	}

	public void getBranchParameterAndStatusListIntegrated(ModelMap model, BranchManagement branchManagement2) {
		List<BranchParameter> branchParameterStatusFinalParamList = new ArrayList<BranchParameter>();

		List<BranchParameter> branchParameterStatusAddParamList = new ArrayList<BranchParameter>();
		List<BranchParameter> branchParameterStatusSubParamList = new ArrayList<BranchParameter>();
		List<BranchParameter> branchParameterStatusTotalParamList = new ArrayList<BranchParameter>();
		List<BranchParameter> branchParameterStatusOtherParamList = new ArrayList<BranchParameter>();

		// List<BranchParameter> branchParametersList =
		// utility.getAllBranchParameterList();
		List<BranchParameter> branchParametersList = getAllBranchParameterList();
		System.out.println("Branch parameter list is : " + branchParametersList.size());
		for (BranchParameter branchParameter : branchParametersList) {
			if (branchParameter.getParameterDetails().equals("add")) {
				branchParameterStatusAddParamList.add(branchParameter);
			} else if (branchParameter.getParameterDetails().equals("sub")) {
				branchParameterStatusSubParamList.add(branchParameter);
			} else if (branchParameter.getParameterDetails().equals("total")) {
				branchParameterStatusSubParamList.add(branchParameter);
			} else {
				branchParameterStatusOtherParamList.add(branchParameter);
			}
		}

		branchParameterStatusFinalParamList.addAll(branchParameterStatusAddParamList);
		branchParameterStatusFinalParamList.addAll(branchParameterStatusSubParamList);
		branchParameterStatusFinalParamList.addAll(branchParameterStatusTotalParamList);
		branchParameterStatusFinalParamList.addAll(branchParameterStatusOtherParamList);

		BranchParameterStatus branchParameterStatus = new BranchParameterStatus();
		branchParameterStatus.setBranchManagement(branchManagement2);
		List<BranchParameterStatus> branchParameterStatusFinalModifiedList = new ArrayList<BranchParameterStatus>();
		List<BranchParameterStatus> branchParameterStatusList = branchManagement2.getBranchParameterStatuses();
		log.info("before modifiyng list of branch parameter status is : " + branchParameterStatusList.size());
		branchParameterStatusList.forEach(p -> System.out.println("Before : -----  " + p.getBranchParameter().getId()
				+ " and param name : ------  " + p.getBranchParameter().getParameterName()));
		for (BranchParameter branchParameter : branchParameterStatusFinalParamList) {
			// List<BranchParameterStatus> branchParameterStatusFilteredList
			// = branchParameterStatusList.stream().filter(c ->
			// branchParameter.getId().equals(c.getBranchParameter().getId())).collect(Collectors.toList());
			for (BranchParameterStatus branchParameterStatus2 : branchParameterStatusList) {
				if (branchParameter.getId().equals(branchParameterStatus2.getBranchParameter().getId())) {
					branchParameterStatusFinalModifiedList.add(branchParameterStatus2);
				}
			}
		}
		log.info("after modified branch parameter status list size is : "
				+ branchParameterStatusFinalModifiedList.size());

		branchParameterStatusFinalModifiedList
				.forEach(p -> System.out.println("after : -----  " + p.getBranchParameter().getId()
						+ " and param name : ------  " + p.getBranchParameter().getParameterName()));
		branchParameterStatus.setBranchParameterStatusList(branchParameterStatusFinalModifiedList);
		model.addAttribute("branchParametersList", branchParameterStatusFinalParamList);
		model.addAttribute("branchParameterStatus", branchParameterStatus);
	}

	public BranchParameterStatus getModifiedBranchParameterStatusFilterNonIssuable(
			BranchParameterStatus totalBranchParameterStatus, BranchParameterStatus nonIssuableBranchParameterStatus) {
		BranchParameterStatus modifiedBranchParameterStatus = new BranchParameterStatus();
		modifiedBranchParameterStatus = totalBranchParameterStatus;
		// For Dn2000 notes
		if (nonIssuableBranchParameterStatus.getDn2000() != null) {
			modifiedBranchParameterStatus
					.setDn2000(totalBranchParameterStatus.getDn2000() - nonIssuableBranchParameterStatus.getDn2000());
		} else {
			modifiedBranchParameterStatus.setDn2000(totalBranchParameterStatus.getDn2000());
		}

		// For Dn500 notes
		if (nonIssuableBranchParameterStatus.getDn500() != null) {
			modifiedBranchParameterStatus
					.setDn500(totalBranchParameterStatus.getDn500() - nonIssuableBranchParameterStatus.getDn500());
		} else {
			modifiedBranchParameterStatus.setDn500(totalBranchParameterStatus.getDn500());
		}

		// For Dn100 notes
		if (nonIssuableBranchParameterStatus.getDn100() != null) {
			modifiedBranchParameterStatus
					.setDn100(totalBranchParameterStatus.getDn100() - nonIssuableBranchParameterStatus.getDn100());
		} else {
			modifiedBranchParameterStatus.setDn100(totalBranchParameterStatus.getDn100());
		}

		// For Dn50 notes
		if (nonIssuableBranchParameterStatus.getDn50() != null) {
			modifiedBranchParameterStatus
					.setDn50(totalBranchParameterStatus.getDn50() - nonIssuableBranchParameterStatus.getDn50());
		} else {
			modifiedBranchParameterStatus.setDn50(totalBranchParameterStatus.getDn50());
		}

		// For Dn20 notes
		if (nonIssuableBranchParameterStatus.getDn20() != null) {
			modifiedBranchParameterStatus
					.setDn20(totalBranchParameterStatus.getDn20() - nonIssuableBranchParameterStatus.getDn20());
		} else {
			modifiedBranchParameterStatus.setDn20(totalBranchParameterStatus.getDn20());
		}

		// For Dn10 notes
		if (nonIssuableBranchParameterStatus.getDn10() != null) {
			modifiedBranchParameterStatus
					.setDn10(totalBranchParameterStatus.getDn10() - nonIssuableBranchParameterStatus.getDn10());
		} else {
			modifiedBranchParameterStatus.setDn10(totalBranchParameterStatus.getDn10());
		}

		// For Dn5 notes
		if (nonIssuableBranchParameterStatus.getDn5() != null) {
			modifiedBranchParameterStatus
					.setDn5(totalBranchParameterStatus.getDn5() - nonIssuableBranchParameterStatus.getDn5());
		} else {
			modifiedBranchParameterStatus.setDn5(totalBranchParameterStatus.getDn5());
		}

		// For Dc1 coins
		if (nonIssuableBranchParameterStatus.getDc1() != null) {
			modifiedBranchParameterStatus
					.setDc1(totalBranchParameterStatus.getDc1() - nonIssuableBranchParameterStatus.getDc1());
		} else {
			modifiedBranchParameterStatus.setDc1(totalBranchParameterStatus.getDc1());
		}

		// For Dc2 coins
		if (nonIssuableBranchParameterStatus.getDc2() != null) {
			modifiedBranchParameterStatus
					.setDc2(totalBranchParameterStatus.getDc2() - nonIssuableBranchParameterStatus.getDc2());
		} else {
			modifiedBranchParameterStatus.setDc2(totalBranchParameterStatus.getDc2());
		}

		// For Dc5 coins
		if (nonIssuableBranchParameterStatus.getDc5() != null) {
			modifiedBranchParameterStatus
					.setDc5(totalBranchParameterStatus.getDc5() - nonIssuableBranchParameterStatus.getDc5());
		} else {
			modifiedBranchParameterStatus.setDc5(totalBranchParameterStatus.getDc5());
		}

		// For Dc10 coins
		if (nonIssuableBranchParameterStatus.getDc10() != null) {
			modifiedBranchParameterStatus
					.setDc10(totalBranchParameterStatus.getDc10() - nonIssuableBranchParameterStatus.getDc10());
		} else {
			modifiedBranchParameterStatus.setDc10(totalBranchParameterStatus.getDc10());
		}

		// For others
		if (nonIssuableBranchParameterStatus.getOthers() != null) {
			modifiedBranchParameterStatus
					.setOthers(totalBranchParameterStatus.getOthers() - nonIssuableBranchParameterStatus.getOthers());
		} else {
			modifiedBranchParameterStatus.setOthers(totalBranchParameterStatus.getOthers());
		}

		// For others
		if (nonIssuableBranchParameterStatus.getTotal() != null) {
			modifiedBranchParameterStatus
					.setTotal(totalBranchParameterStatus.getTotal() - nonIssuableBranchParameterStatus.getTotal());
		} else {
			modifiedBranchParameterStatus.setTotal(totalBranchParameterStatus.getTotal());
		}

		return modifiedBranchParameterStatus;
	}

	/**
	 * Calculating the diatnce between two location by theie latitude and
	 * longitude......
	 */
	public double calculateDist(String lat1, String long1, String lat2, String long2) throws Exception {
		int R = 6371; // Radius of the earth in km

		double latitude1 = Double.parseDouble(lat1);
		double longitude1 = Double.parseDouble(long1);

		double latitude2 = Double.parseDouble(lat2);
		double longitude2 = Double.parseDouble(long2);

		double dLat = deg2rad(latitude2 - latitude1); // deg2rad below

		double dLon = deg2rad(longitude2 - longitude1);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(latitude1))
				* Math.cos(deg2rad(longitude1)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double d = R * c; // Distance in km

		return d;
	}

	private double deg2rad(double deg) throws Exception {
		return deg * (Math.PI / 180);
	}

	public Integer nullCheck(Integer param) {
		// if (param == null) {
		// return 0;
		// }
		// return param;
		return ((param == null) ? 0 : param);
	}

	/**
	 * @param amount
	 * @param amount2
	 * @return
	 */
	public BigInteger add(BigInteger var1, BigInteger var2) {
		if (var1 != null && var2 != null) {
			return var1.add(var2);
		} else if (var1 != null) {
			return var1;
		} else if (var2 != null) {
			return var2;
		}

		return BigDecimal.valueOf(0).toBigInteger();
	}

	/**
	 * @param amount
	 * @return
	 */
	public BigInteger nullCheck(BigInteger param) {
		return ((param == null) ? BigDecimal.valueOf(0).toBigInteger() : param);
	}

	/**
	 * @param bigInteger
	 * @param nullCheck
	 * @return
	 */
	public BigInteger substract(BigInteger var1, BigInteger var2) {
		if (var1 != null && var2 != null) {
			return var1.subtract(var2);
		} else if (var1 != null) {
			return var1;
		} else if (var2 != null) {
			return var2;
		}

		return BigDecimal.valueOf(0).toBigInteger();
	}
}
