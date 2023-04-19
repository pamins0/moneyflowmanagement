package com.emorphis.cashmanagement.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.service.RolePermissionService;
import com.emorphis.cashmanagement.service.UserManagementService;
import com.emorphis.cashmanagement.service.UtilityService;
import com.emorphis.cashmanagement.util.MailUtility;
import com.emorphis.cashmanagement.util.Utility;

@Controller
@RequestMapping("/")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	MySession mySession;

	@Autowired
	UserManagementService userManagementService;

	@Autowired
	RolePermissionService rolePermissionService;

	@Autowired
	UtilityService utilityService;

	@Autowired
	Utility utility;

	@Autowired
	MailUtility mailUtility;

	@PreAuthorize("hasRole('can_orgtype_create111')")
	@RequestMapping(value = { "/test" }, method = RequestMethod.GET)
	public String test(ModelMap model) {
		return "home";
	}

	@RequestMapping(value = { "/Access_Denied" }, method = RequestMethod.GET)
	public String AccessDenied(ModelMap model) {
		log.error(
				"shdjhfjhjshdjfhjshdjfhjshdjfhjhsdjfhjshdjfhjshdjfhjshdjhfjhjshdjfhjshdjfhjshdjfhjhsdjfhjshdjfhjshdjfhj");
		return "Access_Denied";
	}

	// the home/base URI, takes on home page with public/user posts.
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(ModelMap model, HttpServletRequest request) {
		if (!isUserLogedIn()) {
			System.out.println("Inside if not logged in home......");
			return "redirect:/login";
		}
		log.info("Inside home if its logged in......");
		log.info("remote addres is : "+request.getRemoteAddr());
		log.info("remote host is : "+request.getRemoteHost());
		String remoteIp = request.getRemoteHost();
		mySession.setIp(remoteIp);
		log.info("remote address set on session is : "+mySession.getIp());
		return "home";
	}

	private boolean isUserLogedIn() {
//		if (mySession.getUser() == null) {
//
//			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
//			log.info(" UserId in isUserLoggedin is " + userId);
//			if (userId != null) {
//				mySession.setUser(userManagementService.findBySSO(userId));
//			}
//		}
		return ((mySession.getUser() == null) ? false : true);
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(ModelMap model, HttpServletResponse response) {
		/*
		 * response.setHeader("Cache-Control","no-cache"); //Forces caches to
		 * obtain a new copy of the page from the origin server
		 * response.setHeader("Cache-Control","no-store"); //Directs caches not
		 * to store the page under any circumstance
		 * response.setDateHeader("Expires", 0); //Causes the proxy cache to see
		 * the page as "stale" response.setHeader("Pragma","no-cache"); //HTTP
		 * 1.0 backward compatibility
		 */
		System.out.println("Inside login......");
		if (!isUserLogedIn()) {
			System.out.println("Inside if login......");
			User user = new User();
			model.addAttribute("user", user);

			return "login";
		}
		System.out.println("Inside already logged in......");
		return "redirect:/home";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String loginAuth(@Valid User user, BindingResult result, ModelMap model, HttpServletRequest request) {

		try {
			URL url = new URL(request.getRequestURL().toString());
			log.info("full url is : " + url.toString());
			String ur = url.toString();
			String mainUrl = ur.substring(0, ur.lastIndexOf("/") + 1);

			log.info("main url is : " + mainUrl);
			mySession.setBaseUrl(mainUrl);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (result.hasFieldErrors("userId") || result.hasFieldErrors("userPassword")) {
			System.out.println("Inside binding error in login page fields are null or empty");
			return "login";
		}
		System.out.println("Inside if login authenticates......");
		User user2 = userManagementService.authenticateUser(user);

		if (user2 == null) {
			model.addAttribute("loginErrorMessages",
					messageSource.getMessage("loginErrorMessage", null, Locale.ENGLISH));
			result.addError(result.getGlobalError());
			return "login";
		}
		/*
		 * HttpServletRequest request = null;
		 * System.out.println("context path for the project is : " +
		 * request.getContextPath());
		 */
		mySession.setUser(user2);
		mySession.setUserId(user2.getId());
		mySession.setPermissions(rolePermissionService.getRolePermissionListByUser(user2));
		System.out.println("After setting session value : " + mySession.getUser().getFirstName());

		return "redirect:/home";
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout(ModelMap model, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		// if (isUserLogedIn()) {
		System.out.println("Invalidating session......");
		session.invalidate();
		SecurityContextHolder.getContext().setAuthentication(null);
		// }
		/*
		 * Authentication auth =
		 * SecurityContextHolder.getContext().getAuthentication(); if (auth !=
		 * null){ new SecurityContextLogoutHandler().logout(request, response,
		 * auth); // persistentTokenBasedRememberMeServices.logout(request,
		 * response, auth);
		 * SecurityContextHolder.getContext().setAuthentication(null); }
		 */

		/*
		 * response.setHeader("Cache-Control","no-cache"); //Forces caches to
		 * obtain a new copy of the page from the origin server
		 * response.setHeader("Cache-Control","no-store"); //Directs caches not
		 * to store the page under any circumstance
		 * response.setDateHeader("Expires", 0); //Causes the proxy cache to see
		 * the page as "stale" response.setHeader("Pragma","no-cache"); //HTTP
		 * 1.0 backward compatibility
		 */
		return "redirect:/login";
	}

	@RequestMapping(value = { "/forgotpass" }, method = RequestMethod.GET)
	public String forgotPasswordGet(ModelMap model) {
		if (!isUserLogedIn()) {
			System.out.println("Inside if forgot password get......");
			User user = new User();
			model.addAttribute("user", user);
			return "forgotpassowrd";
		}
		System.out.println("Inside already logged in......");
		return "redirect:/home";
	}

	@RequestMapping(value = { "/forgotpass" }, method = RequestMethod.POST)
	public String forgotPasswordPost(@Valid User user, BindingResult result, ModelMap model) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Boolean b = user.getEmail().matches(EMAIL_REGEX);
		System.out.println("b vaklue is : " + b);
		/*
		 * if(!b){
		 * System.out.println("If by regex email is not verified : "+user.
		 * getEmail()); }
		 */
		if (result.hasFieldErrors("email")) {
			System.out.println("b value inside result is : " + b);
			model.addAttribute("user", user);
			return "forgotpassowrd";
		}
		System.out.println("If by regex email is not outise verified : " + user.getEmail());
		User user2 = userManagementService.authenticateUserForForgotPassword(user);
		if (null != user2) {
			System.out.println("Inside forgot password : " + user2.getEmail() + " user id is : " + user2.getUserId());
			mailUtility.sendMailForgotPassword(
					"Hello " + user2.getFirstName() + " your password is : " + user2.getUserPassword(), user2);
		} else {
			model.addAttribute("invalidemailidforgotpassword",
					messageSource.getMessage("invalidemailidforgotpassword", null, Locale.ENGLISH));
			return "forgotpassowrd";
		}
		return "redirect:/login";
	}

}
