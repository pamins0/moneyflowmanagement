package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.User;


public interface SecurityUserService {
	
	User findById(String id);
	
	User findBySSO(String sso);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserBySSO(String sso);

	List<User> findAllUsers(); 
	
	boolean isUserSSOUnique(String id, String sso);

}