<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
	function changeOrgManagement() {
		var orgType = $('#orgTypeId').val();
		console.log("orgType id : " + orgType);
		$('#orgManagementId').empty();
		$('#hierarchyId').empty();
		$('#branchManagementName').val('');
		$('#branchManagementId').val('');

		var jqxhr = $.getJSON(
				"${pageContext. request. contextPath}/getOrgManagementForOrgType-"
						+ orgType, function() {
					console.log("success in var jqxhr");

				}).done(
				function(data) {
					console.log("second success " + data.length);
					$("#orgManagementId").append(
							'<option value=' + -1 + '>'
									+ 'Select OrgManagement' + '</option>');
					$("#hierarchyId").append(
							'<option value=' + -1 + '>' + 'Select Hierarchy'
									+ '</option>');
					$('#branchManagementName').val('');
					$('#branchManagementId').val('');
					for ( var i in data) {
						$("#orgManagementId").append(
								'<option value=' + data[i].id + '>'
										+ data[i].name + '</option>');
					}
					console.log(data);
				}).fail(function() {
			console.log("error");
		}).always(function() {
			console.log("complete");
		});
	}

	function changeBranchManagement() {
		var orgManagement = $('#orgManagementId').val();
		console.log("orgManagement id : " + orgManagement);
		$('#hierarchyId').empty();

		var jqxhr = $.getJSON(
				"${pageContext. request. contextPath}/getHierarchyControlForOrgManagement-"
						+ orgManagement, function() {
					console.log("success in var jqxhr");
				}).done(
				function(data) {
					console.log("second success : " + data.length);
					$("#hierarchyId").append(
							'<option value=' + -1 + '>' + 'Select Hierarchy'
									+ '</option>');
					$('#branchManagementName').val('');
					$('#branchManagementId').val('');
					for ( var i in data) {
						$("#hierarchyId").append(
								'<option value=' + data[i].id + '>'
										+ data[i].name + '</option>');
					}
					console.log(data);
				}).fail(function() {
			console.log("error");
		}).always(function() {
			console.log("complete");
		});
	}
</script>

<div class="form-group col-sm-3 w100">
	<label><fmt:message key="Common.form.Organization" /></label>
	<form:select
		path="branchManagement.hierarchyControl.orgManagement.orgType.id"
		cssClass="form-control" onchange="changeOrgManagement()"
		id="orgTypeId">
		<form:option value="-1" label="- OrgType -" />
		<form:options items="${orgTypeList}" itemValue="id"
			itemLabel="orgType" />
	</form:select>
	<div class="has-error">
		<form:errors
			path="branchManagement.hierarchyControl.orgManagement.orgType.id"
			cssClass="help-inline" />
	</div>
</div>
<div class="form-group col-sm-3 w100">
	<label><fmt:message key="Common.form.OrganizationName" /></label>
	<form:select path="branchManagement.hierarchyControl.orgManagement.id"
		cssClass="form-control" id="orgManagementId"
		onchange="changeBranchManagement()">
		<form:option value="-1" label="- OrgManagement -" />
		<form:options items="${orgManagementSet}" itemValue="id"
			itemLabel="name" />
	</form:select>
	<div class="has-error">
		<form:errors path="branchManagement.hierarchyControl.orgManagement.id"
			cssClass="help-inline" />
	</div>
</div>
<div class="form-group col-sm-3 w100">
	<label><fmt:message key="Common.form.HierarchyName" /></label>
	<form:select path="branchManagement.hierarchyControl.id"
		cssClass="form-control" id="hierarchyId">
		<form:option value="-1" label="- Hierarchy -" />
		<form:options items="${hierarchyList}" itemValue="id" itemLabel="name" />
	</form:select>
	<div class="has-error">
		<form:errors path="branchManagement.hierarchyControl.id"
			cssClass="help-inline" />
	</div>
</div>
<div class="form-group col-sm-3 w100">
<label>&nbsp;</label>
	<form:input placeholder="Enter branch name"
		path="branchManagement.branchName" id="branchManagementName"
		cssClass="form-control" />
	<form:hidden path="branchManagement.id" id="branchManagementId" />
</div>

