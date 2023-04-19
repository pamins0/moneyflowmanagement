package com.emorphis.cashmanagement.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;
import com.emorphis.cashmanagement.service.UserManagementService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	@Autowired
	private UserManagementService userService;

	@Autowired
	MySession mySession;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		logger.info("CustomAuthenticationProvider:::>>>" + username + ":::" + password);
		User user = userService.findBySSO(username);

		if (user == null) {
			throw new BadCredentialsException("Username not found.");
		}

		logger.info("CustomAuthenticationProvider:::getFirstName>>>" + user.getFirstName());

		// if (!password.equals(user.getPassword())) {
		// throw new BadCredentialsException("Wrong password.");
		// }

		Collection<? extends GrantedAuthority> authorities = getGrantedAuthorities(user);
		mySession.setUser(user);
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
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

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}