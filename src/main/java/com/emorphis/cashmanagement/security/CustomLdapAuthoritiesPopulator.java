package com.emorphis.cashmanagement.security;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.RolePermission;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;
import com.emorphis.cashmanagement.service.UserManagementService;

@Component
public class CustomLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	static final Logger logger = LoggerFactory.getLogger(CustomLdapAuthoritiesPopulator.class);

	@Autowired
	UserManagementService userService;

	@Autowired
	MySession mySession;

	@Override
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData,
			String username) {
		User user = userService.findBySSO(username);
		if (user == null) {
			throw new BadCredentialsException("Username not found.");
		}
		logger.info("user:: getFirstName : {}", user.getFirstName());
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (UserRole userRole : user.getUserRoles()) {
			for (RolePermission rolePermission : userRole.getRole().getRolePermissions()) {
				authorities.add(new SimpleGrantedAuthority(rolePermission.getPermission().getAbbreviation()));
			}
		}
		InetAddress ipAddr;
		try {
			ipAddr = InetAddress.getLocalHost();
			System.out.println("ip address is : "+ipAddr.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		mySession.setUser(user);
		mySession.setUserId(username);
		// mySession.setPermissions(authorities);;
		logger.info("authorities : {}", authorities);
		return authorities;
	}
}