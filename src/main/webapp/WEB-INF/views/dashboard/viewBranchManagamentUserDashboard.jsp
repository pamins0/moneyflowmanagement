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
						View Entity - <span class="edit-module-name">${branchManagement.branchName}</span>
					</h3>
				</div>

				<div class="btn-group custom_group" role="group"
					aria-label="Basic example">
					<a class="btn btn-success" href='./userdashboard'> <i
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
					<%-- 	<div class="form-group">
					<label><fmt:message key="Common.form.Organization" /></label>
					${branchManagement.orgManagement.orgType.orgType}
				</div>
				<div class="form-group">
					<label><fmt:message key="Common.form.OrganizationName" /></label>
					${branchManagement.orgManagement.name}
				</div> --%>
					<div class="form-group">
						<label><fmt:message key="BranchManagement.form.BranchName" /></label>
						${branchManagement.branchName}
					</div>
					<%-- <div class="form-group">
					<label><fmt:message
							key="BranchManagement.form.Abbreviation" /></label>
					${branchManagement.abbreviation}
				</div> --%>

					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchAddress1" /></label>
						${branchManagement.branchAdd1}
					</div>

					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchAddress2" /></label>
						${branchManagement.branchAdd2}
					</div>

					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchDetails" /></label>
						${branchManagement.branchDetail}
					</div>
					<%-- <div class="form-group">
					<label><fmt:message key="Common.form.HierarchyName" /></label>
					${branchManagement.hierarchyControl.name}
				</div> --%>
					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchCashLimit" /></label>
						${branchManagement.branchCashlimit}
					</div>
					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchMinThreshold" /></label>
						${branchManagement.minThresholdAmount}
					</div>
					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchMaxThreshold" /></label>
						${branchManagement.maxThresholdAmount}
					</div>

					<%-- <div class="form-group">
					<label><fmt:message key="BranchManagement.form.BranchCountry" /></label>
					${branch.branchState}
				</div> --%>

					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchState" /></label>
						${branchManagement.branchState}
					</div>

					<div class="form-group">
						<label><fmt:message key="BranchManagement.form.BranchCity" /></label>
						${branchManagement.branchCity}
					</div>

					<div class="form-group">
						<label><fmt:message key="BranchManagement.form.BranchZip" /></label>
						${branchManagement.branchZip}
					</div>

					<%-- <div class="form-group">
					<label><fmt:message
							key="BranchManagement.form.BranchLatitude" /></label>
					${branchManagement.branchLatitude}
				</div>
				<div class="form-group">
					<label><fmt:message
							key="BranchManagement.form.BranchLongitude" /></label>
					${branchManagement.branchLongitude}
				</div> --%>

					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchLocation" /></label>
						${branchManagement.branchLocation}
					</div>
					<%-- <div class="form-group">
					<label><fmt:message key="BranchManagement.form.BranchStatus" /></label>
					${branch.branchStatus}
				</div> --%>

					<div class="form-group">
						<label><fmt:message key="BranchManagement.form.BranchCode" /></label>
						${branchManagement.branchCode}
					</div>
					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchContactNo" /></label>
						${branchManagement.branchContactNo}
					</div>

					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchEmail" /></label>
						${branchManagement.branchEmail}
					</div>
					<%-- <c:if test="${branchManagement.branchType == 1}">
					<div class="form-group">
						<label><fmt:message key="BranchManagement.form.BranchType" /></label>
						Participating
					</div>
					<div class="form-group">
						<label><fmt:message
								key="BranchManagement.form.BranchApprovers" /></label>
						${branchManagement.branchApprovers}
					</div>
				</c:if> --%>

					<%-- <c:if test="${branchManagement.branchType == 0}">
						<div class="form-group">
							<label><fmt:message key="BranchManagement.form.BranchType" /></label>
							Non Participating
						</div>				
				</c:if> --%>


					<%-- <div class="form-group">
					<label><fmt:message key="OrgType.list.CreatedOn" /></label>
					<fmt:formatDate value="${branchManagement.createdTime}" pattern="${pattern}" />
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgType.list.ModifiedOn" /></label>
					<fmt:formatDate value="${branchManagement.modifiedTime}" pattern="${pattern}" />
				</div> --%>

					<c:forEach items="${branchManagement.branchParameterStatuses}"
						var="branchParameterStatus" varStatus="counter1">
						<div class="form-group">
							<%-- <label><fmt:message
									key="UserDashboard.list.EntityParameterStatus.ParameterName" /></label> --%>
							<label>${branchParameterStatus.branchParameter.parameterName}</label>

							${branchParameterStatus.total}
						</div>
						<%-- <div class="form-group">
							<label><fmt:message
									key="UserDashboard.list.EntityParameterStatus.TotalCashAvailable" /></label>
							${branchParameterStatus.total}
						</div> --%>
					</c:forEach>


				</div>
			</div>
		</div>
	</div>
</section>


