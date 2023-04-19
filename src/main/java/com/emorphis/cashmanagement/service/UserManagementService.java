package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.User;

public interface UserManagementService {

	List<User> getAllUsersList();

	String save(User user);

	User findById(String id);

	User findBySSO(String sso);

	void updateById(User user);

	void deleteById(String id);

	User authenticateUser(User user);

	User getAvailableUser(User user);

	List<User> getAllUsersListbyBranch(BranchManagement branchManagement);

	List<User> getAllUsersListbyBranchHierarchy(User user);

	List<User> getAllUsersListbyBranchHierarchyAndLowerLevelUsers(User user, List<User> usersList);

	List<User> getAllUserListAccToBranchHierarchyOrganization(User user);

	List<User> getAllApprovedUsersList(List<User> usersList);

	List<User> getAllApproversAndMakersUsersList(List<User> usersList);

	User authenticateUserForForgotPassword(User user);

	List<User> getAllUserListByDepartmentId(String id);

	List<User> getAllUserListByDesignationId(String id);  

}
