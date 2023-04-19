package com.emorphis.cashmanagement.dao;

import java.util.List;
import java.util.Set;

import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;

public interface UserManagementDao {

	List<User> getAllUsersList();

	int save(User user);

	User findById(String id);
 
	void deleteById(Integer id);

	void saveUserRoles(User user);

	void deletUserRoleSetByUser(User user, Set<UserRole> removeUserRoleSet);

	User findByUserId(String userId);

	List<User> getAvailableUser(User user); 

	List<User> getAllUsersListbyBranch(BranchManagement branchManagement);

	List<User> getAllUsersListbyBranchHierarchy(User user);

	List<User> getAllUsersListbyBranchHierarchyAndLowerLevelUsers(User user, List<User> usersList);

	List<User> getAllUserListAccToBranchHierarchyOrganization(User user);

	User authenticateUserForForgotPassword(User user);

	User findByUUID(String uuids);

	User findByUsername(String sso);

	User findBySSO(String sso);

	List<User> getAllUserListByDepartmentId(String id);

	List<User> getAllUserListByDesignationId(String id);     
 
}
