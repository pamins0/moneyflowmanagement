<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Branch Contact list</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Branch Contacts</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Branch Contact List</h3>
				</div>
				<%-- <c:if test="${ct:isAllowed('can_branchmanagementcontact_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
				<div class="btn-group custom_group" role="group"
					aria-label="Basic example">
					<a class="btn btn-success" href='./newbranchcontact'><i class="fa fa-plus"></i> New</a>
				</div>
				</c:if> --%>
			</div>

			<sec:authorize
				access="hasRole('can_branchmanagementcontact_read')">
				<%-- 			<c:if test="${ct:isAllowed('can_branchmanagementcontact_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="box-body table-responsive">
						<table id="branchListTable"
							class="table table-bordered table-responsive table-striped"
							style="max-height: 500px; overflow-y: scroll;">
							<thead>
								<tr>
									<th class="text-center" style="max-width: 20px;"><i
										class="fa fa-cogs"></i></th>
									<th><fmt:message key="BranchContact.list.Organization" /></th>
									<th><fmt:message key="BranchContact.list.OrganizationName" /></th>
									<th><fmt:message key="BranchContact.list.BranchName" /></th>
									<th><fmt:message key="BranchManagement.list.BranchCode" /></th>
									<th><fmt:message key="BranchContact.list.ContactName" /></th>
									<th><fmt:message key="BranchContact.list.ContactEmail" /></th>
									<th><fmt:message key="BranchContact.list.ContactNo" /></th>
									<th><fmt:message key="BranchContact.list.CreatedOn" /></th>
									<th><fmt:message key="BranchContact.list.ModifiedOn" /></th>

									<sec:authorize
										access="hasRole('can_branchmanagementcontact_update')">
										<%-- 								<c:if test="${ct:isAllowed('can_branchmanagementcontact_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
										<th class="text-center" style="max-width: 15px;"><i
											class="fa fa-edit btn btn-xs bg-yellow"></i></th>
										<%-- 								</c:if> --%>
									</sec:authorize>
									<sec:authorize
										access="hasRole('can_branchmanagementcontact_delete')">
										<%-- 								<c:if test="${ct:isAllowed('can_branchmanagementcontact_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
										<th class="text-center" style="max-width: 15px;"><i
											class="fa fa-trash-o btn btn-xs bg-red"></i></th>
										<%-- 								</c:if> --%>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
								<fmt:message key="yyyy.MM.dd" var="pattern" />
								<c:forEach items="${branchcontacts}" var="contact"
									varStatus="counter">
									<tr>
										<td class="text-center" style="max-width: 5px;">${counter.index+1}</td>
										<td><small>${contact.branchManagement.orgManagement.orgType.orgType}</small></td>
										<td><small>${contact.branchManagement.orgManagement.name}</small></td>
										<td><small>${contact.branchManagement.branchName}</small></td>
										<td><small>${contact.branchManagement.branchCode}</small></td>
										<td><small>${contact.contactName}</small></td>
										<td><small>${contact.contactEmail}</small></td>
										<td><small>${contact.branchContactNumber}</small></td>
										<td><small><fmt:formatDate
													value="${contact.createdTime}" pattern="${pattern}" /></small></td>
										<td><small><fmt:formatDate
													value="${contact.modifiedTime}" pattern="${pattern}" /></small></td>
										<sec:authorize
											access="hasRole('can_branchmanagementcontact_update')">
											<%-- 									<c:if test="${ct:isAllowed('can_branchmanagementcontact_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<td style="max-width: 15px;" class="text-center"><a
												href="<c:url value='/branchcontact-${contact.id}-edit' />"
												title="Update"><i class="fa fa-edit"></i></a></td>
											<%-- 									</c:if> --%>
										</sec:authorize>
										<sec:authorize
											access="hasRole('can_branchmanagementcontact_delete')">
											<%-- 									<c:if test="${ct:isAllowed('can_branchmanagementcontact_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<td style="max-width: 15px;" class="text-center"><a
												href="javascript:onDeleteConfirm('<c:url value="/branchcontact-${contact.id}-delete" />')"
												title="Delete"><i class="fa fa-trash-o"></i></a></td>
											<%-- 									</c:if> --%>
										</sec:authorize>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<%-- 			</c:if> --%>
			</sec:authorize>
		</div>
	</div>
</section>