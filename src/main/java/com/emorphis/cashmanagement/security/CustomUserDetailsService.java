package com.emorphis.cashmanagement.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;
import com.emorphis.cashmanagement.service.UserManagementService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserManagementService userService;

	@Autowired
	MySession mySession;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		logger.info("User name is ::" + userId);
		User user = userService.findBySSO(userId);
		if (user == null) {
			logger.info("User not found::" + userId);
			throw new UsernameNotFoundException("Username not found");
		}

		logger.info("User : {}", user.getFirstName());
		mySession.setUser(user);
		/*
		 * Parameters: username the username presented to the
		 * DaoAuthenticationProvider || password the password that should be
		 * presented to the DaoAuthenticationProvider enabled set to true if the
		 * user is enabled accountNonExpired set to true if the account has not
		 * expired credentialsNonExpired set to true if the credentials have not
		 * expired accountNonLocked set to true if the account is not locked
		 * authorities the authorities that should be granted to the caller if
		 * they presented the correct username and password and the user is
		 * enabled. Not null. Throws: IllegalArgumentException - if a null value
		 * was passed either as a parameter or as an element in the
		 * GrantedAuthority collection
		 */

		return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getUserPassword(), true,
				true, true, true, getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (UserRole userRole : user.getUserRoles()) {
			for (RolePermission rolePermission : userRole.getRole().getRolePermissions()) {

				// logger.info("UserProfile : {}",
				// rolePermission.getPermission().getAbbreviation());
				authorities.add(new SimpleGrantedAuthority(rolePermission.getPermission().getAbbreviation()));
			}
		}
		logger.info("authorities : {}", authorities);
		return authorities;
	}

}
