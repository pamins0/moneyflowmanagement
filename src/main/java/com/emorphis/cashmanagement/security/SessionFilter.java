package com.emorphis.cashmanagement.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.service.UserManagementService;

public class SessionFilter {
	private static final Logger log = LoggerFactory.getLogger(SessionFilter.class);

	@Autowired
	MySession mySession;
	@Autowired
	UserManagementService userManagementService;

	private boolean isUserLogedIn() {

		if (mySession.getUser() == null) {

			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
			log.info(" UserId in isUserLoggedin is " + userId);
			if (userId != null) {
				mySession.setUser(userManagementService.findBySSO(userId));
			}
		}

		return ((mySession.getUser() == null) ? false : true);
	}

}
