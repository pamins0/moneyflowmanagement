<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:forEach items="${branchNode.branchControl}" var="branchNode"
	varStatus="counter">
	<c:forEach items="${branchNode.users}" var="user">
		<tr>
			<%-- <td class="text-center" style="max-width: 5px;">${counter.index+1}</td> --%>
			<td class="text-center" style="max-width: 5px;">${count}</td>
			<c:set var="count" value="${count + 1}" scope="request"></c:set>
			<td><small>${user.firstName}</small>
			<td><small>${user.branchManagement.orgManagement.name}</small></td>
			<td><small>${user.branchManagement.branchName}</small></td>
			<td><small>${user.contactNo}</small></td>
			<td><small>${user.email}</small></td>
			<td><small>${user.userId}</small></td>
			<td><small>${user.branchManagement.branchLocation}</small></td>

			<sec:authorize access="hasRole('can_usermanagement_assignrole')">
				<%-- 				<c:if --%>
				<%-- 					test="${ct:isAllowed('can_usermanagement_assignrole',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<td><small> <a style="color: red"
						href="<c:url value='/editRoleAssociation-${user.id}'/>" class="p0">${user.userRoles.size() eq 0 ? 'Associate Roles' : 'Roles('.concat(user.userRoles.size()).concat(")")}</a>
				</small></td>
				<%-- 				</c:if> --%>
			</sec:authorize>
			<sec:authorize access="hasRole('can_usermanagement_update')">

				<%-- 				<c:if --%>
				<%-- 					test="${ct:isAllowed('can_usermanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<td style="max-width: 15px;" class="text-center"><a
					href="<c:url value='/usermanagement-${user.id}-edit' />"
					title="Update"><i class="fa fa-edit"></i></a></td>
				<%-- 				</c:if> --%>
			</sec:authorize>
			<sec:authorize access="hasRole('can_usermanagement_delete')">
				<%-- 		<c:if --%>
				<%-- 			test="${ct:isAllowed('can_usermanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<td style="max-width: 15px;" class="text-center"><a
					href="javascript:onDeleteConfirm('<c:url value="/usermanagement-${user.id}-delete" />')"
					title="Delete"><i class="fa fa-trash-o"></i></a></td>
				<%-- 		</c:if> --%>
			</sec:authorize>
		</tr>
	</c:forEach>
	<c:set var="branchNode" value="${branchNode}" scope="request" />
	<jsp:include page="userNode.jsp" />
</c:forEach>