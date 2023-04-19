<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Branch Contacts</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Branch Contact</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<sec:authorize
	access="hasRole('can_branchmanagementcontact_update')">
	<%-- <c:if test="${ct:isAllowed('can_branchmanagementcontact_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
	<section class="content">
		<div class="row">

			<div class="col-md-12 table-container-main">
				<div class="box-header custom_header_form_page">
					<div class="heading-title-wrapper">
						<i class="fa fa-gear"></i>
						<c:choose>
							<c:when test="${edit}">
								<h3 class="box-title">
									Edit Branch Contact - <span class="edit-module-name">${branchcontact.branchManagement.branchName}</span>
								</h3>
							</c:when>
							<c:otherwise>
								<h3 class="box-title">New Branch Contact</h3>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='branchcontact'> <i
							class="fa fa-arrow-left"></i> Back
						</a>
					</div>
				</div>
				<div class="box-body custom_form_body">
					<form:form id="branchContact" method="POST"
						modelAttribute="branchcontact" autocomplete="off"
						class="form-inline">
						<form:hidden path="id" id="id" />

						<c:choose>
							<c:when test="${branchassociate}">
								<div class="form-group">
									<form:hidden path="branchManagement.id" id="branchManagementId" />
									<label><fmt:message key="Common.form.BranchName" /></label>
									<form:input path="branchManagement.branchName"
										id="branchManagementName" readonly="true"
										cssClass="form-control" />
								</div>
							</c:when>
							<c:otherwise>
								<sec:authorize
									access="hasRole('can_create_any_organization_branchmanagement_branchcontact')">

									<c:choose>
										<c:when test="${edit}">
										</c:when>
										<c:otherwise>
											<c:set property="branchManagement" target="${branchcontact}"
												value=""></c:set>
										</c:otherwise>
									</c:choose>
								</sec:authorize>
								<sec:authorize
									access="!hasRole('can_create_any_organization_branchmanagement_branchcontact')">
									<div class="form-group">
										<label><fmt:message key="Common.form.Organization" /></label>
										<form:input
											path="branchManagement.orgManagement.orgType.orgType"
											id="branchManagementOrgManagementOrgTypeName" readonly="true"
											cssClass="form-control" />
									</div>
									<div class="form-group">
										<label><fmt:message key="Common.form.OrganizationName" /></label>
										<form:input path="branchManagement.orgManagement.name"
											id="branchManagementOrgManagementName" readonly="true"
											cssClass="form-control" />
									</div>
									<div class="form-group">
										<label><fmt:message key="Common.form.BranchName" /></label>
										<form:select path="branchManagement.id"
											cssClass="form-control" id="branchManagementId">
											<form:option value="-1" label="- Branch -" />
											<form:options items="${branchListbyOrg}" itemValue="id"
												itemLabel="branchName" />
										</form:select>
										<div class="has-error">
											<form:errors path="branchManagement.id"
												cssClass="help-inline" />
										</div>
									</div>
								</sec:authorize>
								<%-- <c:choose>
									<c:when
										test="${(ct:isAllowed('can_create_any_organization_branchmanagement_branchcontact',requestScope['scopedTarget.requestScopedPermissions'].permissions)==true)}">
										<c:choose>
											<c:when test="${edit}">
											</c:when>
											<c:otherwise>
												<c:set property="branchManagement" target="${branchcontact}"
													value=""></c:set>
											</c:otherwise>
										</c:choose>
										<jsp:include page="../UtilityFiles/OrgToBranch.jsp"></jsp:include>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<label><fmt:message key="Common.form.Organization" /></label>
											<form:input
												path="branchManagement.orgManagement.orgType.orgType"
												id="branchManagementOrgManagementOrgTypeName"
												readonly="true" cssClass="form-control" />
										</div>
										<div class="form-group">
											<label><fmt:message
													key="Common.form.OrganizationName" /></label>
											<form:input path="branchManagement.orgManagement.name"
												id="branchManagementOrgManagementName" readonly="true"
												cssClass="form-control" />
										</div>
										<div class="form-group">
											<label><fmt:message key="Common.form.BranchName" /></label>
											<form:select path="branchManagement.id"
												cssClass="form-control" id="branchManagementId">
												<form:option value="-1" label="- Branch -" />
												<form:options items="${branchListbyOrg}" itemValue="id"
													itemLabel="branchName" />
											</form:select>
											<div class="has-error">
												<form:errors path="branchManagement.id"
													cssClass="help-inline" />
											</div>
										</div>
									</c:otherwise>
								</c:choose> --%>

							</c:otherwise>
						</c:choose>

						<div class="form-group">
							<label><fmt:message key="BranchContact.form.ContactName" /></label>
							<form:input path="contactName" id="contactName"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="contactName" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message key="BranchContact.form.ContactEmail" /></label>
							<form:input path="contactEmail" id="contactEmail"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="contactEmail" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchContact.form.ContactNumber" /></label>
							<form:input path="branchContactNumber" id="branchContactNumber"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchContactNumber" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group button-group-set">
							<div class="col-sm-12">
								<c:choose>
									<c:when test="${edit}">
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-check"></i> Update
										</button>
										<a class="btn btn-danger btn-sm"
											href="<c:url value='/branchcontact' />"><i
											class="fa fa-times"></i> Cancel</a>
									</c:when>
									<c:otherwise>
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-floppy-o"></i> Save
										</button>
										<a class="btn btn-danger btn-sm"
											href="<c:url value='/branchcontact' />"> <i
											class="fa fa-times"></i> Cancel
										</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<%-- </c:if> --%>
</sec:authorize>