package com.emorphis.cashmanagement.security;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;
import com.emorphis.cashmanagement.service.UserManagementService;

public class CustomLdapUserDetails implements LdapUserDetails {
	static final Logger logger = LoggerFactory.getLogger(CustomLdapUserDetails.class);
	private static final long serialVersionUID = 1L;

//	@Autowired
	private UserManagementService userService;

	private LdapUserDetails details;
	// private Environment env;

	public CustomLdapUserDetails(LdapUserDetails details
	// , Environment env
			,UserManagementService userService
	) {
		this.details = details;
		// this.env = env;
		this.userService = userService;
	}

	public boolean isEnabled() {
		return details.isEnabled();
		// && getUsername().equals(env.getRequiredProperty("ldap.username"))
	}

	public String getDn() {
		return details.getDn();
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		String username=details.getUsername();
		logger.info("CustomLdapUserDetails:::details.getUsername()  "+username);		
		User user = new User();
		user = userService.findBySSO(username);
		if (user == null) {
			throw new BadCredentialsException("Username not found.");
		}
		logger.info("CustomLdapUserDetails::user:: getFirstName : {}", user.getFirstName());
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (UserRole userRole : user.getUserRoles()) {
			for (RolePermission rolePermission : userRole.getRole().getRolePermissions()) {
				authorities.add(new SimpleGrantedAuthority(rolePermission.getPermission().getAbbreviation()));
			}
		}
		logger.info("CustomLdapUserDetails:::authorities : {}", authorities);
		return authorities;

		// return details.getAuthorities();
	}

	public String getPassword() {
		return details.getPassword();
	}

	public String getUsername() {
		return details.getUsername();
	}

	public boolean isAccountNonExpired() {
		return details.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return details.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return details.isCredentialsNonExpired();
	}
}