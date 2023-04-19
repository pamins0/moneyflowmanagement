<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<section class="content-header">
	<h1>Entity Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Entity</li>
		<li class="active">New/Update</li>
	</ol>
</section>

<section class="content">
	<div class="row">
		<form:form name="myForm" id="NewEditBranchForm" method="POST"
			modelAttribute="branch" autocomplete="off" class="form-inline">
			<form:hidden path="id" id="id" />
			<form:hidden path="requestFrom" />

			<c:choose>
				<c:when test="${empty branch.urlReferer }">
					<c:set var="referer" value="${header['Referer']}" />
				</c:when>
				<c:otherwise>
					<c:set var="referer" value="${branch.urlReferer}" />
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
									Edit Entity - <span class="edit-module-name">${branch.branchName}</span>
								</h3>
							</c:when>
							<c:otherwise>
								<h3 class="box-title">New Entity</h3>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<c:choose>
							<c:when test="${branch.requestFrom == 'byhierarchy'}">
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
				<div class="box-body custom_form_body">
					<div class="form-group">
						<label><fmt:message key="BranchManagement.form.BranchName" /></label>
						<form:input path="branchName" id="branchName"
							cssClass="form-control" />
						<div class="has-error">
							<form:errors path="branchName" cssClass="help-inline" />
						</div>
					</div>

					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.Abbreviation" /></label>
						<form:input path="abbreviation" id="abbreviation"
							cssClass="form-control" />
						<div class="has-error">
							<form:errors path="abbreviation" cssClass="help-inline" />
						</div>
					</div>

					<div class="form-group button-group-set">
						<div class="col-sm-12">
							<button type="submit" name="saveBtn" value="Save"
											 class="btn btn-primary btn-sm saveBtn">
												<i class="fa fa-floppy-o"></i> Save
							</button>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</section>