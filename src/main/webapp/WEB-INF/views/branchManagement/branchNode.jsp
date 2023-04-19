<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:forEach items="${branchNode.branchControl}" var="branchNode"
	varStatus="counter">
	<tr>
		<%-- <td class="text-center" style="max-width: 5px;">${counter.index+1}</td> --%>
		<td class="text-center" style="max-width: 5px;">${count}</td>
		<c:set var="count" value="${count + 1}" scope="request"></c:set>
		<td><small>${branchNode.orgManagement.name}</small></td>
		<td style="display: none;"><small>${branchNode.hierarchyControl.hierarchyLevel}</small></td>
		<td><small>${branchNode.branchName}</small></td>
		<td><small>${branchNode.branchCode}</small></td>
		<td><small>${branchNode.abbreviation}</small></td>
		<td><small>${branchNode.branchCashlimit}</small></td>
		<td><small> <span class=""> <a
					href="<c:url value='/branchusermanagement-${branchNode.id}-branch'/>"
					data-toggle="tooltip" data-html="true" data-placement="top"
					title="View Users"> <i class="fa fa-eye" aria-hidden="true"></i>
				</a>
			</span> <a class="btn btn-success"
				href='./branchuser-associate-${branchNode.id}'><i
					class="fa fa-plus"></i>${branchNode.getUsers().size()}</a>
		</small></td>
		<td><small> <span class=""> <a
					href="<c:url value='/branchcontact-${branchNode.id}-branchNode'/>"
					data-toggle="tooltip" data-html="true" data-placement="top"
					title="View Contacts"> <i class="fa fa-eye" aria-hidden="true"></i>
				</a>
			</span> <a class="btn btn-success"
				href='./branchcontact-associate-${branchNode.id}'><i
					class="fa fa-plus"></i>${branchNode.getBranchContacts().size()}</a>
		</small></td>
		<td><small><span class=""><a
					href="<c:url value='/branchaccount-${branchNode.id}-branch'/>"
					data-toggle="tooltip" data-html="true" data-placement="top"
					title="View Accounts"><i class="fa fa-eye" aria-hidden="true"></i>
				</a></span> <a class="btn btn-success"
				href='./branchaccount-associate-${branchNode.id}'><i
					class="fa fa-plus"></i>
					${branchNode.getBranchAccountDetails().size()}</a></small></td>
		<sec:authorize access="hasRole('can_branchmanagement_update')">
			<%-- 		<c:if test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<td style="max-width: 15px;" class="text-center"><a
				href="<c:url value='/branchmanagement-${branchNode.id}-edit' />"
				title="Update"><i class="fa fa-edit"></i></a></td>
			<%-- 		</c:if> --%>
		</sec:authorize>
		<sec:authorize access="hasRole('can_branchmanagement_delete')">
			<%-- 		<c:if test="${ct:isAllowed('can_branchmanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<td style="max-width: 15px;" class="text-center"><a
				href="javascript:onDeleteConfirm('<c:url value="/branchmanagement-${branchNode.id}-delete" />')"
				title="Delete"><i class="fa fa-trash-o"></i></a></td>
			<%-- 		</c:if> --%>
		</sec:authorize>
	</tr>
	<c:set var="branchNode" value="${branchNode}" scope="request" />
	<jsp:include page="branchNode.jsp" />
</c:forEach>