package com.emorphis.cashmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
// @EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity(debug = false)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	// @Autowired
	// @Qualifier("customUserDetailsService")
	// UserDetailsService userDetailsService;
	//
	// @Autowired
	// CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	CustomLdapAuthoritiesPopulator customLdapAuthoritiesPopulator;

	// @Override
	// protected void configure(AuthenticationManagerBuilder authManagerBuilder)
	// throws Exception {
	// authManagerBuilder.authenticationProvider(activeDirectoryLdapAuthenticationProvider())
	// .userDetailsService(userDetailsService);
	// }
	//
	// @Autowired
	// protected void configureGlobal(AuthenticationManagerBuilder auth) throws
	// Exception {
	// auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
	// }
	// @Autowired
	// UserManagementService userManagementService;

	/**
	 * this is use for @EnableGlobalMethodSecurity
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http
		// .authorizeRequests()
		// .antMatchers("/").permitAll()
		// .anyRequest().authenticated();
		// .and()
		// .formLogin()
		// .and()
		// .logout();

		http.authorizeRequests().antMatchers("/resources/**", "/static/**").permitAll().and().formLogin()
				.loginPage("/login")// default is /login with an HTTP get
				.failureUrl("/login?error") // default is /login?error
				.loginProcessingUrl("/login")// default is /login
				.usernameParameter("userId").passwordParameter("userPassword")//
//				.and().rememberMe().rememberMeParameter("remember")//
				// .tokenRepository(tokenRepository).tokenValiditySeconds(1)//
				.and().exceptionHandling().accessDeniedPage("/Access_Denied")//
				// .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logout?success")//
				// .invalidateHttpSession(false)//
				.and().csrf().disable();//
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication().ldapAuthoritiesPopulator(customLdapAuthoritiesPopulator)
				.userSearchBase("DC=cms,DC=testing").userSearchFilter("(&(sAMAccountName={0})(ObjectClass=user))")
				// .groupSearchBase("ou=groups")
				.contextSource(contextSource())
		// .userDetailsContextMapper(userDetailsContextMapper())
		;

		// .passwordCompare()
		// .passwordEncoder(new LdapShaPasswordEncoder())
		// .passwordAttribute("userPassword");

		// auth.userDetailsService(userDetailsService);
		// auth.authenticationProvider(customAuthenticationProvider);

		// auth.authenticationProvider(customLdapAuthoritiesPopulator);
	}

	@Bean
	public DefaultSpringSecurityContextSource contextSource() {
		DefaultSpringSecurityContextSource ctxSrc = new DefaultSpringSecurityContextSource("ldap://35.160.10.251:389/"
		/* Arrays.asList("ldap://35.160.10.251:389/"), "DC=cms,DC=testing" */);
		ctxSrc.setUserDn("CN=Administrator,CN=Users,DC=cms,DC=testing");
		ctxSrc.setPassword("Tech@one23");
		return ctxSrc;
	}

	// @Bean
	// public UserDetailsContextMapper userDetailsContextMapper() {
	// return new LdapUserDetailsMapper() {
	// @Override
	// public UserDetails mapUserFromContext(DirContextOperations ctx, String
	// username,
	// Collection<? extends GrantedAuthority> authorities) {
	// UserDetails details = super.mapUserFromContext(ctx, username,
	// authorities);
	// return new CustomLdapUserDetails((LdapUserDetails)
	// details,userManagementService);
	// }
	// };
	// }

	/*
	 * @Bean
	 * 
	 * public AuthenticationProvider activeDirectoryLdapAuthenticationProvider()
	 * { ActiveDirectoryLdapAuthenticationProvider provider = new
	 * ActiveDirectoryLdapAuthenticationProvider( "cms.testing",
	 * "ldap://35.160.10.251:389" //
	 * ,"CN=Administrator784512,CN=Users,DC=cms,DC=testing" );
	 * provider.setConvertSubErrorCodesToExceptions(true);
	 * provider.setUseAuthenticationRequestCredentials(true);
	 * provider.setSearchFilter("(sAMAccountName={0})"); provider.
	 * 
	 * return provider; }
	 */

	//
	// public interface UserDetailsContextMapper {
	// UserDetails mapUserFromContext(DirContextOperations ctx, String username,
	// Collection<GrantedAuthority> authorities);
	//
	// void mapUserToContext(UserDetails user, DirContextAdapter ctx);
	// }

}

//
// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class SecurityConfiguration extends GlobalMethodSecurityConfiguration
// {
//
// @Autowired
// @Qualifier("customUserDetailsService")
// UserDetailsService userDetailsService;
//
//// @Autowired
//// PersistentTokenRepository tokenRepository;
//
// @Autowired
// public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws
// Exception {
// auth.userDetailsService(userDetailsService);
// auth.authenticationProvider(authenticationProvider());
// }
//
// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }
//
// @Bean
// public DaoAuthenticationProvider authenticationProvider() {
// DaoAuthenticationProvider authenticationProvider = new
// DaoAuthenticationProvider();
// authenticationProvider.setUserDetailsService(userDetailsService);
// authenticationProvider.setPasswordEncoder(passwordEncoder());
// return authenticationProvider;
// }
//
//// @Bean
//// public PersistentTokenBasedRememberMeServices
// getPersistentTokenBasedRememberMeServices() {
//// PersistentTokenBasedRememberMeServices tokenBasedservice = new
// PersistentTokenBasedRememberMeServices(
//// "remember", userDetailsService, tokenRepository);
//// return tokenBasedservice;
//// }
//
// @Bean
// public AuthenticationTrustResolver getAuthenticationTrustResolver() {
// return new AuthenticationTrustResolverImpl();
// }
//
// // @Autowired
// // private RoleHierarchy roleHierarchy;
//
// @Override
// protected MethodSecurityExpressionHandler createExpressionHandler() {
// final DefaultMethodSecurityExpressionHandler handler = new
// DefaultMethodSecurityExpressionHandler();
// // handler.setRoleHierarchy(this.roleHierarchy);
// return handler;
// }
//
// /**
// * WebSecurityConfig
// *
// * @author gourav
// *
// */
// @Configuration
// public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//// @Autowired
//// PersistentTokenRepository tokenRepository;
//
// @Autowired
// @Qualifier("customUserDetailsService")
// UserDetailsService userDetailsService;
//
// @Override
// protected void configure(HttpSecurity http) throws Exception {
//
// http.authorizeRequests().antMatchers("/resources/**",
// "/static/**").permitAll().and().formLogin()
// .loginPage("/login")// default is /login with an HTTP get
// .failureUrl("/login?error") // default is /login?error
// .loginProcessingUrl("/login")// default is /login
// .failureUrl("/logout")// default is /login
// .usernameParameter("userId").passwordParameter("userPassword")//
// .and().rememberMe().rememberMeParameter("remember")//
// //.tokenRepository(tokenRepository).tokenValiditySeconds(1)//
// .and().exceptionHandling().accessDeniedPage("/Access_Denied")//
// // .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logout?success")//
// // .invalidateHttpSession(false)//
// .and().csrf().disable();//
// }
//
// // @Value("${ad.domain}")
// // private String AD_DOMAIN;
// //
// // @Value("${ad.url}")
// // private String AD_URL;
// //
// // @Override
// // protected void configure(HttpSecurity http) throws Exception {
// // http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
// // }
//
// @Override
// protected void configure(AuthenticationManagerBuilder authManagerBuilder)
// throws Exception {
// authManagerBuilder.authenticationProvider(activeDirectoryLdapAuthenticationProvider())
// .userDetailsService(userDetailsService);
// }
//
// @Bean
// public AuthenticationManager authenticationManager() {
// return new
// ProviderManager(Arrays.asList(activeDirectoryLdapAuthenticationProvider()));
// }
//
// @Bean
// public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
// ActiveDirectoryLdapAuthenticationProvider provider = new
// ActiveDirectoryLdapAuthenticationProvider(
// "cms.testing", "ldap://35.160.10.251:389");
// provider.setConvertSubErrorCodesToExceptions(true);
// provider.setUseAuthenticationRequestCredentials(true);
// return provider;
// }
//
// /**
// * New Try
// */
// // @Override
// // protected void configure(HttpSecurity http) throws Exception {
// // http.requiresChannel().anyRequest().requiresSecure();
// //
// // // Only requests matching regex are handled by this security
// // configurer
// // http.requestMatchers().regexMatchers("/login", "/login.+",
// // "/oauth/.+", "/j_spring_security_check", "/logout"); // note 2
// //
// // AuthenticationEntryPoint entryPoint = entryPoint();
// // http.exceptionHandling().authenticationEntryPoint(entryPoint);
// // http.formLogin(); // note 3i
// // http.addFilter(usernamePasswordAuthenticationFilter());
// // http.authorizeRequests().antMatchers("/resources/**",
// // "/static/**").permitAll();
// // http.authorizeRequests().antMatchers("/login").permitAll();
// // http.authorizeRequests().antMatchers("/oauth/**").authenticated();
// //
// http.authorizeRequests().antMatchers("/j_spring_security_check").anonymous().and().csrf().disable();
// // // note 3iii
// //
// // }
//
//// private UsernamePasswordAuthenticationFilter
// usernamePasswordAuthenticationFilter() {
//// UsernamePasswordAuthenticationFilter filter = new
// UsernamePasswordAuthenticationFilter();
//// filter.setAuthenticationManager(authenticationManager());
//// AuthenticationFailureHandler failureHandler = new
// SimpleUrlAuthenticationFailureHandler(
//// "/login?login_error=true");
//// filter.setAuthenticationFailureHandler(failureHandler);
//// return filter;
//// }
//
// }
//
// }
