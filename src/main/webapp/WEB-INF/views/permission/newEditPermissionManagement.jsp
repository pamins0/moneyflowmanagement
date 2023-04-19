<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<style>
.form-group.button-group-set.m3 {
    margin: 2% 0px !important;
}
.has-error {
    top: 100%;
    margin: 0;
}
</style>
<section class="content-header">
	<h1>Permission Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Permission</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<sec:authorize
		   access="hasRole('can_permission_update')">
<%-- <c:if --%>
<%-- 	test="${ct:isAllowed('can_permission_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
	<section class="content">
		<div class="row">
			<form:form id="NewEditForm" method="POST" modelAttribute="permission"
				autocomplete="on" class="form-inline">
				<form:hidden path="id" />
				<div class="col-md-12 table-container-main">
					<div class="box-header custom_header_form_page">
						<div class="heading-title-wrapper">
							<i class="fa fa-gear"></i>
							<c:choose>
								<c:when test="${edit}">
									<h3 class="box-title">
										Edit Permission - <span class="edit-module-name">${permission.title}
										</span>
									</h3>
								</c:when>
								<c:otherwise>
									<h3 class="box-title">New Permission</h3>
								</c:otherwise>
							</c:choose>
						</div>

						<div class="btn-group custom_group" role="group"
							aria-label="Basic example">
							<a class="btn btn-success" href='./permissionmanagement'> <i
								class="fa fa-arrow-left"></i> Back
							</a>
						</div>
					</div>
					<div class="box-body custom_form_body">
						<div class="form-group col-sm-3">

							<label class="control-label col-sm-12 "><fmt:message
									key="Permission.form.ModuleName" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="module" id="name" cssClass="form-control" />
									<div class="has-error">
										<form:errors path="module" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>

						<div class="form-group col-sm-3">
							<label class="control-label col-sm-12 "><fmt:message
									key="Permission.form.ModuleTitle" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="title" id="title" cssClass="form-control" />
									<div class="has-error">
										<form:errors path="title" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>
						<c:choose>
							<c:when test="${edit}">
								<div class="form-group col-sm-3">
									<label class="control-label col-sm-12 "><fmt:message
											key="Permission.form.ModuleAbbreviation" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>
 
											<form:input readonly="true" path="abbreviation" id="abbreviation"
												cssClass="form-control" />
											<div class="has-error">
												<form:errors path="abbreviation" cssClass="help-inline" />
											</div>
										</div>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="form-group col-sm-3">
									<label class="control-label col-sm-12 "><fmt:message
											key="Permission.form.ModuleAbbreviation" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>

											<form:input path="abbreviation" id="abbreviation"
												cssClass="form-control" />
											<div class="has-error">
												<form:errors path="abbreviation" cssClass="help-inline" />
											</div>
										</div>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="form-group col-sm-3">
							<label class="control-label col-sm-12 "><fmt:message
									key="Permission.form.Key" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="keyVal" id="keyVal" cssClass="form-control" />
									<div class="has-error">
										<form:errors path="keyVal" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group button-group-set m3">
							<div class="col-sm-12">
								<c:choose>
									<c:when test="${edit}">
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-check"></i> Update
										</button>
									</c:when>
									<c:otherwise>
										<button type="submit" name="saveBtn" value="Save"
											class="btn btn-primary btn-sm">
											<i class="fa fa-floppy-o"></i> Save
										</button>
										<button type="submit" name="saveBtn" value="SaveAndNew"
											class="btn btn-primary btn-sm">
											<i class="fa fa-floppy-o"></i> Save And New
										</button>
									</c:otherwise>
								</c:choose>
								<button class="btn btn-danger btn-sm"
									href="<c:url value='/permissionmanagement' />"><i
									class="fa fa-times"></i> Cancel</button>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</section>
<%-- </c:if> --%>
</sec:authorize>
