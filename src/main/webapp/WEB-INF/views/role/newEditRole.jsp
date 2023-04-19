<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
.has-error {
	top: 100%;
}
</style>
<section class="content-header">
	<h1>Role Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Permission</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<sec:authorize access="hasRole('can_role_create')">
	<%-- <c:if test="${ct:isAllowed('can_role_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
	<section class="content">
		<div class="row">
			<form:form id="NewEditRoleForm" method="POST" modelAttribute="role"
				class="form-inline">
				<form:hidden path="id" />
				<form:hidden path="requestFrom" />
				<c:choose>
					<c:when test="${empty role.urlReferer }">
						<c:set var="referer" value="${header['Referer']}" />
					</c:when>
					<c:otherwise>
						<c:set var="referer" value="${role.urlReferer}" />
					</c:otherwise>
				</c:choose>
				<form:hidden path="urlReferer" value="${referer}" />
				<div class="col-md-12 table-container-main">
					<div class="box-header custom_header_form_page">
						<div class="heading-title-wrapper">
							<i class="fa fa-gear"></i>
							<c:choose>
								<c:when test="${edit}">
									<h3 class="box-title">
										Edit Role - <span class="edit-module-name">${role.title}
										</span>
									</h3>
								</c:when>
								<c:otherwise>
									<h3 class="box-title">New Role</h3>
								</c:otherwise>
							</c:choose>
						</div>

						<div class="btn-group custom_group" role="group"
							aria-label="Basic example">
							<c:choose>
								<c:when test="${role.requestFrom == 'byhierarchy'}">
									<a class="btn btn-success" href='${referer}'> <i
										class="fa fa-arrow-left"></i> Back
									</a>
								</c:when>
								<c:otherwise>
									<a class="btn btn-success" href='${referer}'> <i
										class="fa fa-arrow-left"></i> Back
									</a>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<span class="has-error text-center">${duplicateRoleForHiearchy}</span>
					<div class="box-body custom_form_body">
						<c:choose>
							<c:when test="${direct}">
								<div class="form-group col-sm-4">
									<%-- 									<form:hidden path="orgManagement.id" /> --%>
									<label class="control-label col-sm-12 "><fmt:message
											key="Common.form.OrganizationName" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>

											<form:input path="hierarchyControl.orgManagement.name"
												readonly="true" id="orgName" cssClass="form-control" />
											<div class="has-error">
												<form:errors path="hierarchyControl.orgManagement.name"
													cssClass="help-inline" />
											</div>
										</div>
									</div>
								</div>
								<div class="form-group col-sm-4">
									<form:hidden path="hierarchyControl.id" />
									<label class="control-label col-sm-12"><fmt:message
											key="Common.form.HierarchyName" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"><i>*</i></span>

											<form:input path="hierarchyControl.name" id="name"
												readonly="true" cssClass="form-control" />
											<div class="has-error">
												<form:errors path="hierarchyControl.name"
													cssClass="help-inline" />
											</div>
										</div>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<sec:authorize access="hasRole('can_view_everything')">
									<jsp:include page="../UtilityFiles/OrgToHierarchy.jsp"></jsp:include>
								</sec:authorize>
								<sec:authorize access="!hasRole('can_view_everything')">
									<div class="form-group col-sm-4">
										<%-- 											<form:hidden path="orgManagement.id" /> --%>
										<label class="control-label col-sm-12 "><fmt:message
												key="Common.form.OrganizationName" /></label>
										<div class="col-sm-12">
											<div class="input-group">
												<span class="input-group-addon"></span>

												<form:input path="hierarchyControl.orgManagement.name"
													readonly="true" id="orgName" cssClass="form-control" />
												<div class="has-error">
													<form:errors path="hierarchyControl.orgManagement.name"
														cssClass="help-inline" />
												</div>
											</div>
										</div>
									</div>
									<c:if test="${edit==false}">
										<c:set property="hierarchyControl" target="${role}" value=""></c:set>
									</c:if>

									<div class="form-group col-sm-4">
										<label class="control-label col-sm-12 "><fmt:message
												key="Common.form.HierarchyName" /></label>
										<div class="col-sm-12">
											<div class="input-group">
												<span class="input-group-addon"><i>*</i></span>

												<form:select path="hierarchyControl.id"
													cssClass="form-control" id="hierarchyId">
													<form:option value="-1" label="- Select Hierarchy -" />
													<form:options items="${hierarchyList}" itemValue="id"
														itemLabel="name" />
												</form:select>
												<div class="has-error">
													<form:errors path="hierarchyControl.id"
														cssClass="help-inline" />
												</div>
											</div>
										</div>
									</div>
								</sec:authorize>
								<%-- <c:choose>
									<c:when test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">								
										<jsp:include page="../UtilityFiles/OrgToHierarchy.jsp"></jsp:include>										
									</c:when>
									<c:otherwise>
										<div class="form-group col-sm-4">
											<form:hidden path="orgManagement.id" />
											<label class="control-label col-sm-12 "><fmt:message key="Common.form.OrganizationName" /></label>
											<div class="col-sm-12">
							<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>
						
											<form:input path="hierarchyControl.orgManagement.name" readonly="true"
												id="orgName" cssClass="form-control" />
											<div class="has-error">
												<form:errors path="hierarchyControl.orgManagement.name" cssClass="help-inline" />
											</div>
										</div>
										</div>
										</div>
										<c:if test="${edit==false}">
											<c:set property="hierarchyControl" target="${role}" value=""></c:set>
										</c:if>
										
										<div class="form-group col-sm-4">
											<label class="control-label col-sm-12 "><fmt:message key="Common.form.HierarchyName" /></label>
											<div class="col-sm-12">
							<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>
						
											<form:select path="hierarchyControl.id" cssClass="form-control"
												id="hierarchyId">
												<form:option value="-1" label="- Select Hierarchy -" />
												<form:options items="${hierarchyList}" itemValue="id"
													itemLabel="name" />
											</form:select>
											<div class="has-error">
												<form:errors path="hierarchyControl.id" cssClass="help-inline" />
											</div>
										</div>
										</div>
										</div>
									</c:otherwise>
								</c:choose> --%>
							</c:otherwise>
						</c:choose>
						<div class="form-group col-sm-3">
							<label class="control-label col-sm-12 "><fmt:message
									key="Role.form.RoleName" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="title" id="name" cssClass="form-control" />
									<div class="has-error">
										<form:errors path="title" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group button-group-set">
							<div class="col-sm-12">
								<c:choose>
									<c:when test="${edit}">
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-check"></i> Update
										</button>
									</c:when>
									<c:otherwise>
										<button type="submit" name="saveBtn" id="saveBtn" value="Save"
											class="btn btn-primary btn-sm saveBtn">
											<i class="fa fa-floppy-o"></i> Save
										</button>
										<button type="submit" name="saveBtn" id="saveAndNewBtn"
											value="SaveAndNew" class="btn btn-primary btn-sm saveBtn">
											<i class="fa fa-floppy-o"></i> Save And New
										</button>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${role.requestFrom == 'byhierarchy'}">
										<a class="btn btn-danger btn-sm cancel-btn"
											href="<c:url value='${referer}' />"><i
											class="fa fa-times"></i> Cancel</a>
									</c:when>
									<c:otherwise>
										<a class="btn btn-danger btn-sm cancel-btn"
											href="<c:url value='${referer}' />"><i
											class="fa fa-times"></i> Cancel</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>

			</form:form>
		</div>
	</section>
	<%-- </c:if> --%>
</sec:authorize>
<script type="text/javascript">
	/* function checkForm(form)
	 {
	 alert("hello.."+form);
	 document.getElementById("NewEditRoleForm").submit();
	 form.saveBtn.disabled = true;
	 form.saveBtn.value = "Please wait...";
	 return true;
	 } */

	$(document).ready(function() {
		$("#NewEditRoleForm").submit(function() {
			$(".saveBtn").css("display", "none");
			return true;
		});
	});
</script>