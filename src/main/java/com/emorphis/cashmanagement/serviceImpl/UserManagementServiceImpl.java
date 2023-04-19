package com.emorphis.cashmanagement.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.BranchManagementDao;
import com.emorphis.cashmanagement.dao.OrgManagementDao;
import com.emorphis.cashmanagement.dao.OrgTypeDao;
import com.emorphis.cashmanagement.dao.UserManagementDao;
import com.emorphis.cashmanagement.dao.UserManagementDaoIndependent;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.RolePermissionService;
import com.emorphis.cashmanagement.service.UserManagementService;
import com.emorphis.cashmanagement.util.Utility;

@Repository("userManagementService")
@Transactional
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	UserManagementDao userManagementDao;

	@Autowired
	RolePermissionService rolePermissionService;

	@Autowired
	UserManagementDaoIndependent userManagementDaoIndependent;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	OrgTypeDao orgTypeDao;

	public List<User> getAllUsersList() {
		return userManagementDao.getAllUsersList();
	}

	public String save(User user) {
		String uuid = utility.getUserUUID();
		if (uuid != null) {
			user.setId(uuid);
		}
		user.setModifiedBy(mySession.getUser().getId());
		user.setCreatedBy(mySession.getUser().getId());
		user.setCreatedTime(new Date());
		user.setModifiedTime(new Date());
		user.setIp(mySession.getIp());
		int id = userManagementDao.save(user);
		return user.getId();
	}

	public User findById(String id) {
		return userManagementDao.findById(id);
	}

	public User findBySSO(String sso) {
		System.out.println("findBySSO call...");
		return userManagementDao.findBySSO(sso);
	}

	public void updateById(User user) {
		User user2 = userManagementDao.findById(user.getId());
		System.out.println("On user update for user id : " + user.getId() + " the branch updated is : "
				+ user.getBranchManagement().getBranchName());
		user2.setBranchManagement(user.getBranchManagement());
		user2.setFirstName(user.getFirstName());
		user2.setSecondName(user.getSecondName());
		user2.setLastName(user.getLastName());
		user2.setGender(user.getGender());
		user2.setApprover(user.getApprover());
		user2.setDateOfBirth(user.getDateOfBirth());
		user2.setDateOfJoining(user.getDateOfJoining());
		user2.setPostingStartDate(user.getPostingStartDate());
		user2.setPostingEndDate(user.getPostingEndDate());
		user2.setContactNo(user.getContactNo());
		user2.setEmail(user.getEmail());
		user2.setUserCode(user.getUserCode());
		user2.setDesignation(user.getDesignation());
		user2.setDepartment(user.getDepartment());
		user2.setModifiedTime(new Date());
		user2.setModifiedBy(mySession.getUser().getId());
		user2.setEpfo_no(user.getEpfo_no());
		user2.setIp(mySession.getIp());
	}

	public void deleteById(String id) {
		User user = userManagementDao.findById(id);
		user.setDeleted((byte) 1);
		user.setModifiedBy(mySession.getUser().getId());
		user.setModifiedTime(new Date());
		user.setIp(mySession.getIp());
		// userManagementDao.deleteById(id);
	}

	@Autowired
	BranchManagementDao branchManagementDao;

	@Autowired
	OrgManagementDao orgManagementDao;

	private User findByUserId(String userId) {
		User user = userManagementDao.findByUserId(userId);
		if (user != null) {
			System.out.println("user is : " + user.getFirstName());
		} else {
			System.out.println("user is : " + user);
		}
		return user;
	}

	public User authenticateUser(User user) {
		System.out.println("User id for password : " + user.getUserPassword());
		User user2 = findByUserId(user.getUserId().trim());
		if (null != user2) {
			if (user.getUserId().trim().equalsIgnoreCase(user2.getUserId())
					//&& user.getUserPassword().trim().equals(user2.getUserPassword())
					) {
				return user2;
			}
		}
		return null;
	}

	@Override
	public User authenticateUserForForgotPassword(User user) {
		User user2 = userManagementDao.authenticateUserForForgotPassword(user);
		if (null != user2) {
			if (user.getEmail().equals(user2.getEmail())) {
				return user2;
			}
		}
		return null;
	}
	
	@Override
	public List<User> getAllUserListByDepartmentId(String id) {
		return userManagementDao.getAllUserListByDepartmentId(id);
	}
	
	@Override
	public List<User> getAllUserListByDesignationId(String id) {
		return userManagementDao.getAllUserListByDesignationId(id);
	}

	public User getAvailableUser(User user) {
		List<User> usersList = userManagementDao.getAvailableUser(user);
		if (usersList != null && usersList.size() > 0) {
			return usersList.get(0);
		} else {
			return null;
		}
	}

	public List<User> getAllUsersListbyBranch(BranchManagement branchManagement) {
		return userManagementDao.getAllUsersListbyBranch(branchManagement);
	}

	public List<User> getAllUsersListbyBranchHierarchy(User user) {
		return userManagementDao.getAllUsersListbyBranchHierarchy(user);
	}

	public List<User> getAllUsersListbyBranchHierarchyAndLowerLevelUsers(User user, List<User> usersList) {
		return userManagementDao.getAllUsersListbyBranchHierarchyAndLowerLevelUsers(user, usersList);
	}

	public List<User> getAllUserListAccToBranchHierarchyOrganization(User user) {
		return userManagementDao.getAllUserListAccToBranchHierarchyOrganization(user);
	}

	public List<User> getAllApproversAndMakersUsersList(List<User> usersList) {
		List<User> usersApprovedList = new ArrayList<User>();
		for (User user : usersList) {
			boolean flag = false;
			boolean flag1 = false;
			flag = utility.isAllowedPermissionByUser("can_be_approver", user);
			flag1 = utility.isAllowedPermissionByUser("can_be_maker", user);
			if (flag || flag1) {
				usersApprovedList.add(user);
			}
		}
		return usersApprovedList;
	}

	public List<User> getAllApprovedUsersList(List<User> usersList) {
		List<User> usersApprovedList = new ArrayList<User>();
		for (User user : usersList) {
			boolean flag = false;
			flag = utility.isAllowedPermissionByUser("can_be_approver", user);
			if (flag) {
				usersApprovedList.add(user);
			}
		}
		return usersApprovedList;
	}

	public List<User> getAllMakerUsersList(List<User> usersList) {
		List<User> usersApprovedList = new ArrayList<User>();
		for (User user : usersList) {
			boolean flag = false;
			flag = utility.isAllowedPermissionByUser("can_be_maker", user);
			if (flag) {
				usersApprovedList.add(user);
			}
		}
		return usersApprovedList;
	}

	/*public List<User> getAllCheckerApprovedUsersList(List<User> usersList, PlacedBidTable placedBidTable) {
		List<User> usersApprovedList = new ArrayList<User>();
		for (User user : usersList) {
			boolean flag = false;
			flag = utility.isAllowedPermissionByUser("can_be_checker", user);
			if (flag) {
				
				 * int userLimit =
				 * placedBidTable.getBid_Amount().compareTo(user.getCash_limit()
				 * ); if(userLimit == -1 || userLimit == 0) {
				 * usersApprovedList.add(user); }
				 
			}
		}
		return usersApprovedList;
	}*/

}
