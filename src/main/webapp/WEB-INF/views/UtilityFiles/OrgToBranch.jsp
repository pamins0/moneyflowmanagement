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
		$('#branchManagementId').empty();

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
					$("#branchManagementId").append(
							'<option value=' + -1 + '>'
									+ 'Select BranchManagement' + '</option>');
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
		$('#branchManagementId').empty();

		var jqxhr = $.getJSON(
				"${pageContext. request. contextPath}/getBranchManagementForOrgManagement-"
						+ orgManagement, function() {
					console.log("success in var jqxhr");
				}).done(
				function(data) {
					console.log("second success : " + data.length);
					$("#branchManagementId").append(
							'<option value=' + -1 + '>'
									+ 'Select BranchManagement' + '</option>');
					for ( var i in data) {
						$("#branchManagementId").append(
								'<option value=' + data[i].id + '>'
										+ data[i].branchName + '</option>');
					}
					console.log(data);
				}).fail(function() {
			console.log("error");
		}).always(function() {
			console.log("complete");
		});
	}
</script>

<div class="form-group">
	<label><fmt:message key="Common.form.Organization" /></label>
	<form:select path="branchManagement.orgManagement.orgType.id"
		cssClass="form-control" onchange="changeOrgManagement()"
		id="orgTypeId">
		<form:option value="-1" label="- OrgType -" />
		<form:options items="${orgTypeList}" itemValue="id"
			itemLabel="orgType" />
	</form:select>
	<div class="has-error">
		<form:errors path="branchManagement.orgManagement.orgType.id"
			cssClass="help-inline" />
	</div>
</div>
<div class="form-group">
	<label><fmt:message key="Common.form.OrganizationName" /></label>
	<form:select path="branchManagement.orgManagement.id"
		cssClass="form-control" id="orgManagementId"
		onchange="changeBranchManagement()">
		<form:option value="-1" label="- OrgManagement -" />
		<form:options items="${orgManagementSet}" itemValue="id"
			itemLabel="name" />
	</form:select>
	<div class="has-error">
		<form:errors path="branchManagement.orgManagement.id"
			cssClass="help-inline" />
	</div>
</div>
<div class="form-group">
	<label><fmt:message key="Common.form.BranchName" /></label>
	<form:select path="branchManagement.id" cssClass="form-control"
		id="branchManagementId">
		<form:option value="-1" label="- Branch -" />
		<form:options items="${branchList}" itemValue="id"
			itemLabel="branchName" />
	</form:select>
	<div class="has-error">
		<form:errors path="branchManagement.id" cssClass="help-inline" />
	</div>
</div>