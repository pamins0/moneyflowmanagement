<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<section class="content-header">

	<h1>User Management</h1>

	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>User</li>
		<li class="active">View</li>
	</ol>
</section>

<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main Wrapper-view-user">
			<div class="box-header custom_header_form_page">
				<div class="heading-title-wrapper">
					<i class="fa fa-gear"></i>
					<h3 class="box-title">
						View User - <span class="edit-module-name">${user.firstName} ${user.secondName} ${user.lastName}</span>
					</h3>
				</div>

				<div class="btn-group custom_group" role="group"
					aria-label="Basic example">
					<a class="btn btn-success" href='./usermanagement'> <i
						class="fa fa-arrow-left"></i> Back
					</a>
				</div>
			</div>
			<div class="box-body custom_form_body">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Entity Information</h3>
					</div>
				</div>
				<div class="view-profile">
				<fmt:message key="yyyy.MM.dd" var="pattern" />
				<fmt:message key="MM/dd/yyyy" var="pattern_mdy" />
				
				<div class="form-group">
					<label class=""><fmt:message key="Common.form.Organization" /></label>
					<span>${user.branchManagement.hierarchyControl.orgManagement.orgType.orgType}</span>
				</div>
				<div class="form-group">
					<label><fmt:message key="Common.form.OrganizationName" /></label>
					<span>${user.branchManagement.hierarchyControl.orgManagement.name}</span>
				</div>
				<div class="form-group">
					<label><fmt:message key="Common.form.HierarchyName" /></label>
					<span>${user.branchManagement.hierarchyControl.name}</span>
				</div>
				<div class="form-group">
					<label><fmt:message key="Common.form.BranchName" /></label>
					<span>${user.branchManagement.branchName}</span>
				</div>
				<div class="form-group">
					<label><fmt:message
							key="UserManagement.form.Name" /></label>
					<span>${user.firstName}</span>
				</div>

				<div class="form-group">
					<label><fmt:message
							key="UserManagement.form.SecondName" /></label>
					${user.secondName}
				</div>

				<div class="form-group">
					<label><fmt:message
							key="UserManagement.form.LastName" /></label>
					${user.lastName}
				</div>

				<div class="form-group">
					<label><fmt:message
							key="UserManagement.form.Gender" /></label>
					<c:if test="${user.gender == 1}">
						Male
					</c:if>
					<c:if test="${user.gender == 2}">
						Female
					</c:if>
					<c:if test="${user.gender == 3}">
						Other
					</c:if>
				</div>
				
				<div class="form-group">
					<label><fmt:message
							key="UserManagement.form.ContactNo" /></label> 
					${user.contactNo}
				</div>
				
				<div class="form-group">
					<label><fmt:message key="UserManagement.form.DateOfBirth" /></label>
					<fmt:formatDate value="${user.dateOfBirth}" pattern="${pattern}" />
<%-- 					${user.dateOfBirth} --%>
				</div>

				<div class="form-group">
					<label><fmt:message key="UserManagement.form.DateOfJoining" /></label>
					<fmt:formatDate value="${user.dateOfJoining}" pattern="${pattern}" />
<%-- 					${user.dateOfJoining} --%>
				</div>

				<div class="form-group">
					<label><fmt:message key="UserManagement.form.PostingStartDate" /></label>
					<fmt:formatDate value="${user.postingStartDate}" pattern="${pattern}" />
<%-- 					${user.postingStartDate} --%>
				</div>

				<div class="form-group">
					<label><fmt:message key="UserManagement.form.PostingEndDate" /></label>
					<fmt:formatDate value="${user.postingEndDate}" pattern="${pattern}" /> 
<%-- 					${user.postingEndDate} --%>
				</div>

				<div class="form-group">
					<label><fmt:message key="UserManagement.form.Email" /></label>
					${user.email}
				</div>

				<div class="form-group">
					<label><fmt:message
							key="UserManagement.form.DesignationName" /></label>
					${user.designation.title}
				</div>
				<div class="form-group">
					<label><fmt:message
							key="UserManagement.form.UserCode" /></label>
					${user.userCode}
				</div>

				<div class="form-group">
					<label><fmt:message
							key="UserManagement.form.UserId" /></label>
					${user.userId}
				</div>

				<div class="form-group">
					<label><fmt:message key="UserManagement.form.Roles" /></label>
					[
					<c:forEach items="${user.userRoles}" var="userRole">
						${userRole.role.title}
					</c:forEach>
					]
				</div>

				<div class="form-group">
					<label><fmt:message key="UserManagement.list.CreatedOn" /></label>
					<fmt:formatDate value="${user.createdTime}" pattern="${pattern}" />
				</div>
				<div class="form-group">
					<label><fmt:message key="UserManagement.list.ModifiedOn" /></label>
					<fmt:formatDate value="${user.modifiedTime}" pattern="${pattern}" />
				</div>
				</div>
			</div>
		</div>
	</div>
</section>


