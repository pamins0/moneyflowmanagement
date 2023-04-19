package com.emorphis.cashmanagement.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class AppController {

//	@Autowired
//	SecurityUserService userService;
//	
//	@Autowired
//	UserProfileService userProfileService;
//	
//	@Autowired
//	MessageSource messageSource;
//
//	@Autowired
//	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
//	
//	@Autowired
//	AuthenticationTrustResolver authenticationTrustResolver;
//	
//
//	/**
//	 * This method will provide UserProfile list to views
//	 */
//	@ModelAttribute("roles")
//	public List<UserProfile> initializeProfiles() {
//		return userProfileService.findAll();
//	}
//	
//	
//	
//	
//	/**
//	 * This method will list all existing users.
//	 */
//	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
//	public String listUsers(ModelMap model) {
//
//		List<User> users = userService.findAllUsers();
//		model.addAttribute("users", users);
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "userslist";
//	}
//
//	/**
//	 * This method will provide the medium to add a new user.
//	 */
//	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
//	public String newUser(ModelMap model) {
//		User user = new User();
//		model.addAttribute("user", user);
//		model.addAttribute("edit", false);
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "registration";
//	}
//
//	/**
//	 * This method will be called on form submission, handling POST request for
//	 * saving user in database. It also validates the user input
//	 */
//	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
//	public String saveUser(@Valid User user, BindingResult result,
//			ModelMap model) {
//
//		if (result.hasErrors()) {
//			return "registration";
//		}
//
//		/*
//		 * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
//		 * and applying it on field [sso] of Model class [User].
//		 * 
//		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
//		 * framework as well while still using internationalized messages.
//		 * 
//		 */
//		if(!userService.isUserSSOUnique(user.getId(), user.getUserId())){
//			FieldError ssoError =new FieldError("user","userId",messageSource.getMessage("non.unique.userId", new String[]{user.getUserId()}, Locale.getDefault()));
//		    result.addError(ssoError);
//			return "registration";
//		}
//		
//		userService.saveUser(user);
//
//		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
//		model.addAttribute("loggedinuser", getPrincipal());
//		//return "success";
//		return "registrationsuccess";
//	}
//
//
//	/**
//	 * This method will provide the medium to update an existing user.
//	 */
//	@RequestMapping(value = { "/edit-user-{userId}" }, method = RequestMethod.GET)
//	public String editUser(@PathVariable String userId, ModelMap model) {
//		User user = userService.findBySSO(userId);
//		model.addAttribute("user", user);
//		model.addAttribute("edit", true);
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "registration";
//	}
//	
//	/**
//	 * This method will be called on form submission, handling POST request for
//	 * updating user in database. It also validates the user input
//	 */
//	@RequestMapping(value = { "/edit-user-{userId}" }, method = RequestMethod.POST)
//	public String updateUser(@Valid User user, BindingResult result,
//			ModelMap model, @PathVariable String userId) {
//
//		if (result.hasErrors()) {
//			return "registration";
//		}
//
//		/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
//		if(!userService.isUserSSOUnique(user.getId(), user.getUserId())){
//			FieldError ssoError =new FieldError("user","userId",messageSource.getMessage("non.unique.userId", new String[]{user.getUserId()}, Locale.getDefault()));
//		    result.addError(ssoError);
//			return "registration";
//		}*/
//
//
//		userService.updateUser(user);
//
//		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "registrationsuccess";
//	}
//
//	
//	/**
//	 * This method will delete an user by it's SSOID value.
//	 */
//	@RequestMapping(value = { "/delete-user-{userId}" }, method = RequestMethod.GET)
//	public String deleteUser(@PathVariable String userId) {
//		userService.deleteUserBySSO(userId);
//		return "redirect:/list";
//	}
//	
//
//	
//	
//	
//	
//	
//	
//	
//	/**
//	 * This method handles Access-Denied redirect.
//	 */
//	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
//	public String accessDeniedPage(ModelMap model) {
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "accessDenied";
//	}
//
//	/**
//	 * This method handles login GET requests.
//	 * If users is already logged-in and tries to goto login page again, will be redirected to list page.
//	 */
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String loginPage() {
//		if (isCurrentAuthenticationAnonymous()) {
//			return "login";
//	    } else {
//	    	return "redirect:/list";  
//	    }
//	}
//
//	/**
//	 * This method handles logout requests.
//	 * Toggle the handlers if you are RememberMe functionality is useless in your app.
//	 */
//	@RequestMapping(value="/logout", method = RequestMethod.GET)
//	public String logoutPage (HttpServletRequest request, HttpServletResponse response){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null){    
//			//new SecurityContextLogoutHandler().logout(request, response, auth);
//			//persistentTokenBasedRememberMeServices.logout(request, response, auth);
//			SecurityContextHolder.getContext().setAuthentication(null);
//		}
//		return "redirect:/login?logout";
//	}
//
//	/**
//	 * This method returns the principal[user-name] of logged-in user.
//	 */
//	private String getPrincipal(){
//		String userName = null;
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		if (principal instanceof UserDetails) {
//			userName = ((UserDetails)principal).getUsername();
//		} else {
//			userName = principal.toString();
//		}
//		return userName;
//	}
//	
//	/**
//	 * This method returns true if users is already authenticated [logged-in], else false.
//	 */
//	private boolean isCurrentAuthenticationAnonymous() {
//	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    return authenticationTrustResolver.isAnonymous(authentication);
//	}
//
//	/**
//	 * Test
//	 */
//	@RequestMapping(value ="/test", method = RequestMethod.GET)
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//	public String test(ModelMap model) {
//		List<User> users = userService.findAllUsers();
//		model.addAttribute("users", users);
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "test";
//	}
//	
//	/**
//	 * Ajax Test
//	 */
//	@RequestMapping(value ="/ajaxtest", method = RequestMethod.GET)
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//	public String AjaxTest(ModelMap model) {
//		List<User> users = userService.findAllUsers();
//		model.addAttribute("users", users);
//		model.addAttribute("loggedinuser", getPrincipal());
//		return "test";
//	}
	/*@Autowired
	OrgTypeService orgTypeService;

	@Autowired
	OrgManagementService orgManagementService;
	
	@Autowired
	MessageSource messageSource;

	
	 * @RequestMapping(value = { "/404" }, method = RequestMethod.GET) public
	 * String pageNotFound(ModelMap model) { return "404"; }
	 

	@RequestMapping(value = { "/orgtypes" }, method = RequestMethod.GET)
	public String orgTypesGetMethod( ModelMap model) {
		OrgType orgType = new OrgType();
		model.addAttribute("orgType", orgType);
		model.addAttribute("edit", false);
		model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
		boolean flagval = (Boolean)flag;
		if(flagval){
			model.addAttribute("usernotauthorize", messageSource.getMessage("user.not.authoriz", null, Locale.ENGLISH));
		}
		return "orgtype";
	}

	@RequestMapping(value = { "/orgtypes" }, method = RequestMethod.POST)
	public String orgTypesPostMethod(@Valid OrgType orgType, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			return "orgtype";
		}
		// saving organization detail in to DB
		orgTypeService.save(orgType);
		return "redirect:/orgtypes";
	}

	@RequestMapping(value = { "/orgtype-{id}-delete" }, method = RequestMethod.GET)
	public String orgTypeDelete(@PathVariable Integer id, ModelMap model, RedirectAttributes redirectAttributes) { 		
		boolean flag = orgTypeService.deleteById(id);
		if(!flag){
			redirectAttributes.addAttribute("error_NotAuthorize", true);
		}
		return "redirect:/orgtypes";
	}

	@RequestMapping(value = { "/orgtype-{id}-edit" }, method = RequestMethod.GET)
	public String orgTypesEditGetMethod(@PathVariable Integer id, ModelMap model) {
		OrgType orgType = orgTypeService.findById(id);
		if (orgType != null) {
			model.addAttribute("orgType", orgType);
			model.addAttribute("edit", true);
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			return "orgtype";
		} else {
			return "redirect:/orgtypes";
		}
	}

	@RequestMapping(value = { "/orgtype-{id}-edit" }, method = RequestMethod.POST)
	public String orgTypesEditPostMethod(@Valid OrgType orgType, BindingResult result, @PathVariable Integer id, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("orgTypes", orgTypeService.getAllOrgTypes());
			return "orgtype";
		}
		orgTypeService.updatebyId(orgType);
		return "redirect:/orgtypes";
	}*/
}
