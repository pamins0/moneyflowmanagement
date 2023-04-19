<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Hierarchy Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Hierarchy</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<section class="content">
	<div class="new_hierarchy_page_1">
		<form:form id="NewEditForm" method="POST"
			modelAttribute="hierarchyControl" autocomplete="off"
			class="form-inline">
			<form:hidden path="orgManagement.id" />
			<c:forEach var="i" begin="0"
				end="${hierarchyControl.orgManagement.orgLevel-1 }">
				<div class="row" style="margin-left: 5pt">
					<div class="form-group col-sm-12">
						<label><fmt:message key="hierarchyManagement.form.Level" /></label>
						<form:input path="hierarchyControlsList[${i}].hierarchyLevel"
							value="${i+1}" id="hierarchyControlsList[${i}].hierarchyLevel"
							cssClass="form-control" readonly="true" />
						<div class="has-error">
							<form:errors path="hierarchyControlsList[${i}].hierarchyLevel"
								cssClass="help-inline" />
						</div>
					</div>
					<div class="form-group col-sm-12">
						<label><fmt:message key="hierarchyManagement.form.Name" /></label>
						<form:input path="hierarchyControlsList[${i}].name"
							id="hierarchyControlsList[${i}].name" cssClass="form-control" />
						<div class="has-error">
							<form:errors path="hierarchyControlsList[${i}].name"
								cssClass="help-inline" />
						</div>
					</div>
					<div class="form-group col-sm-12">
						<label><fmt:message
								key="hierarchyManagement.form.Abbreviation" /></label>
						<form:input path="hierarchyControlsList[${i}].abbreviation"
							id="hierarchyControlsList[${i}].abbreviation"
							cssClass="form-control" />
						<div class="has-error">
							<form:errors path="hierarchyControlsList[${i}].abbreviation"
								cssClass="help-inline" />
						</div>
					</div>
				</div>
			</c:forEach>
			<button type="submit" class="btn btn-primary btn-sm">
				<i class="fa fa-floppy-o"></i> Save
			</button>

		</form:form>
	</div>
</section>