<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<section class="content-header">

	<h1>Currency Chest</h1>

	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Currency Chest</li>
		<li class="active">View</li>
	</ol>
</section>

<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main  Wrapper-view-user">
			<div class="box-header custom_header_form_page">
				<div class="heading-title-wrapper">
					<i class="fa fa-gear"></i>
					<h3 class="box-title">
						View Entity - <span class="edit-module-name">${branch.branchName}</span>
					</h3>
				</div>

				<div class="btn-group custom_group" role="group"
					aria-label="Basic example">
					<a class="btn btn-success" href='./currencychest'> <i
						class="fa fa-arrow-left"></i> Back
					</a>
				</div>
			</div>
			<div class="box-body custom_form_body">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Currency Chest Information</h3>
					</div>
				</div>
				<div class="view-profile">
				<fmt:message key="yyyy.MM.dd" var="pattern" />
				<div class="form-group">
					<label><fmt:message key="Common.form.Organization" /></label>
					${branch.orgManagement.orgType.orgType}
				</div>
				<div class="form-group">
					<label><fmt:message key="Common.form.OrganizationName" /></label>
					${branch.orgManagement.name}
				</div>
				<div class="form-group">
					<label><fmt:message key="CurrencyChest.form.chestName" /></label>
					${branch.branchName}
				</div>
				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.Abbreviation" /></label>
					${branch.abbreviation}
				</div>

				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.chestAddress1" /></label>
					${branch.branchAdd1}
				</div>

				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.chestAddress2" /></label>
					${branch.branchAdd2}
				</div>

				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.chestDetails" /></label>
					${branch.branchDetail}
				</div>
				<div class="form-group">
					<label><fmt:message key="Common.form.HierarchyName" /></label>
					${branch.hierarchyControl.name}
				</div>
				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.chestCashLimit" /></label>
							<fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${branch.branchCashlimit}" />
				</div>
				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.chestDeviationPercentage" /></label>
					${branch.percentage}
				</div>

				<%-- <div class="form-group">
					<label><fmt:message key="CurrencyChest.form.chestCountry" /></label>
					${branch.branchState}
				</div> --%>

				<div class="form-group">
					<label><fmt:message key="CurrencyChest.form.chestState" /></label>
					${branch.branchState}
				</div>

				<div class="form-group">
					<label><fmt:message key="CurrencyChest.form.chestCity" /></label>
					${branch.branchCity}
				</div>

				<div class="form-group">
					<label><fmt:message key="CurrencyChest.form.chestZip" /></label>
					${branch.branchZip}
				</div>

				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.chestLatitude" /></label>
					${branch.branchLatitude}
				</div>
				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.chestLongitude" /></label>
					${branch.branchLongitude}
				</div>

				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.chestLocation" /></label>
					${branch.branchLocation}
				</div>
				<%-- <div class="form-group">
					<label><fmt:message key="CurrencyChest.form.chestStatus" /></label>
					${branch.branchStatus}
				</div> --%>

				<div class="form-group">
					<label><fmt:message key="CurrencyChest.form.chestCode" /></label>
					${branch.branchCode}
				</div>
				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.chestContactNo" /></label>
					${branch.branchContactNo}
				</div>

				<div class="form-group">
					<label><fmt:message key="CurrencyChest.form.chestEmail" /></label>
					${branch.branchEmail}
				</div>
				<c:if test="${branch.branchType == 0}">
					<div class="form-group">
						<label><fmt:message key="CurrencyChest.form.chestType" /></label>
						Participating
					</div>
				</c:if>
				
				<c:if test="${branch.branchType == 1}">
						<div class="form-group">
							<label><fmt:message key="CurrencyChest.form.chestType" /></label>
							Non Participating
						</div>				
				</c:if>
				
				<div class="form-group">
					<label><fmt:message
							key="CurrencyChest.form.chestApprovers" /></label>
					${branch.branchApprovers}
				</div>
				
				<c:if test="${branch.requestApprovalType == 0}">
					<div class="form-group">
						<label><fmt:message key="CurrencyChest.form.RequestApproval" /></label>
						Automatic
					</div>
					
					<div class="form-group">
						<label><fmt:message key="OrgManagement.form.AutoApprovalTime" /></label>
						${branch.hierarchyControl.orgManagement.autoAprovalRequestTime} minutes
					</div>
				</c:if>
				
				<c:if test="${branch.requestApprovalType == 1}">
						<div class="form-group">
							<label><fmt:message key="CurrencyChest.form.RequestApproval" /></label>
							Manual
						</div>				
				</c:if>				
				
				<div class="form-group">
					<label><fmt:message key="CurrencyChest.form.chestCode" /></label>
					${branch.branchCode}
				</div>
				

				<div class="form-group">
					<label><fmt:message key="OrgType.list.CreatedOn" /></label>
					<fmt:formatDate value="${branch.createdTime}" pattern="${pattern}" />
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgType.list.ModifiedOn" /></label>
					<fmt:formatDate value="${branch.modifiedTime}" pattern="${pattern}" />
				</div>
			</div>
		</div></div>
	</div>
</section>


