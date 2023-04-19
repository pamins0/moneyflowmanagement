package com.emorphis.cashmanagement.interceptors;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.RequestScopedPermissions;
import com.emorphis.cashmanagement.model.User;
import com.emorphis.cashmanagement.model.UserRole;
import com.emorphis.cashmanagement.service.RolePermissionService;
import com.emorphis.cashmanagement.service.UserManagementService;
import com.emorphis.cashmanagement.util.ResourceMessgageExtension;
import com.emorphis.cashmanagement.util.Utility;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	MySession mySession;
		
	@Autowired
	RequestScopedPermissions requestScopedPermissions;
	
	@Autowired
	RolePermissionService rolePermissionService; 
	
	@Autowired
	UserManagementService userManagementService;
	
	@Autowired
	ResourceMessgageExtension resourceMessgageExtension;
	
	@Autowired
	Utility utility;
		 	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			
		// System.out.println("URI IS : "+request.getRequestURI());
		if (!request.getRequestURI().equals("/cashmap/")
				&& !request.getRequestURI().equals("/cashmap/login") 
				&& !request.getRequestURI().equals("/login")
				&& !request.getRequestURI().equals("/cashmap/user/")
				&& !request.getRequestURI().equals("/cashmap/forgotpass")
				&& !request.getRequestURI().equals("/cashmap/belowcashmailtoexcesscashgroup")) { 
		//	 System.out.println("URI inside if IS : "+request.getRequestURI());
			if (null != mySession) {
				User user = mySession.getUser();
				if (null != user) {
					// System.out.println("User logged in is prehandle : " +
										
					Enumeration<String> enumeration = resourceMessgageExtension.getBundleContent("uri_list", Locale.getDefault());
					
				//	System.out.println("URIs");
					while (enumeration.hasMoreElements()) {
						String string = (String) enumeration.nextElement();
				//		System.out.println(string+" : "+ resourceMessgageExtension.getMessage(string,null, Locale.getDefault()));
					}					

					User user2 = userManagementService.findById(user.getId());
					if(null == user2){
						mySession.setUser(null);
						response.sendRedirect("/cashmap/");
						return false;
					}
					System.out.println("User Role set size : before streaming is : "+user2.getUserRoles().size()); 
					Set<UserRole> filtereduserRoleSet= user2.getUserRoles().stream().filter(p -> p.getRole().getDeleted()==0 && p.getRole().getStatus() == 0).collect(Collectors.toSet());
					user2.setUserRoles(filtereduserRoleSet); 
					System.out.println("User Role set size : after streaming is : "+user2.getUserRoles().size());
					mySession.setUser(user2);
					
					requestScopedPermissions.setUser(user2);
					  
					requestScopedPermissions.setPermissions(rolePermissionService.getRolePermissionListByUser(user2));
					
					requestScopedPermissions.setBranchAdminList(utility.getBranchAdminsList(user2)); 
					
					requestScopedPermissions.setOrganizationAdminList(utility.getOrganizationAdminsList(user2));
					
					return true;
				} else {
					// System.out.println("User logged in session value null in pre handle : "+mySession);
					response.sendRedirect("/cashmap/");
					return false;
				}
			} else {
				response.sendRedirect("/cashmap/login");
				return false;
			}
		}else {
		//	System.out.println("gsdgfgsf");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// if (!request.getRequestURI().equals("/cashmanagement/logout")) {
		// // MySession mySession = (MySession)
		// // request.getSession().getAttribute("scopedTarget.mySession");
		// if (null != mySession) {
		// User user = mySession.getUser();
		// if (null != user) {
		// // Login log = (Login)
		// // request.getSession().getAttribute("thisUser");
		// System.out.println("User logged in is posthandle: " +
		// user.getName());
		// } else {
		// System.out.println("Inside else if session is null posthandle..");
		// }
		// }
		// }

		// System.out.println("URI IS in post: "+request.getRequestURI());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// System.out.println("---Request Completed---");
	}

}
