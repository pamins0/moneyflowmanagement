<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<section class="content-header">

	<h1>Organizations</h1>

	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Organization</li>
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
								View Organization - <span class="edit-module-name">${orgManagement.name}</span>
							</h3>
				</div>

				<div class="btn-group custom_group" role="group"
					aria-label="Basic example">
					<a class="btn btn-success" href='./orgmanagement'> <i
						class="fa fa-arrow-left"></i> Back
					</a>
				</div>
			</div>
			<div class="box-body custom_form_body">				
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Organization Information</h3>
					</div>
				</div>
				<div class="view-profile">
				<fmt:message key="yyyy.MM.dd" var="pattern" />
				<div class="form-group">						
					<label><fmt:message key="OrgManagement.form.OrgType" /></label>
					${orgManagement.orgType.orgType}
				</div>	
				<div class="form-group">						
					<label><fmt:message key="OrgManagement.form.OrgName" /></label>
					${orgManagement.name}
				</div>	
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.Address1" /></label>
					${orgManagement.add1}
				</div>	
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.Address2" /></label>
					${orgManagement.add2}
				</div>
				
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.ContactNo" /></label>
					${orgManagement.contactNo}
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.CmsApproach" /></label>
					<c:if test="${orgManagement.cmsApproach == 0}">
						Centralized
					</c:if>
					<c:if test="${orgManagement.cmsApproach == 1}">
						Decentralized
					</c:if>
					
				</div>
				
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.Details" /></label>
					${orgManagement.detail}
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.Email" /></label>
					${orgManagement.email}
				</div>
				
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.Latitude" /></label>
					${orgManagement.latitude}
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.Longitude" /></label>
					${orgManagement.longitude}
				</div>
				
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.Location" /></label>
					${orgManagement.location}
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.Levels" /></label>
					${orgManagement.orgLevel}
				</div>
				
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.Country" /></label>
					${orgManagement.country}
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.State" /></label>
					${orgManagement.city.state.stateName}
				</div>
				
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.City" /></label>
					${orgManagement.city.cityName}
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.Zip" /></label>
					${orgManagement.zip}
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.AutoApprovalTime" /></label>
					${orgManagement.autoAprovalRequestTime} minutes
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgManagement.form.NeedIntraBank" /></label>
					${orgManagement.intraBank}
				</div>
<!-- 				<div class="form-group"> -->
<%-- 					<label><fmt:message key="OrgManagement.form.MaxTimeForIntraBank" /></label> --%>
<%-- 					${orgManagement.maxTimeForIntraBank} --%>
<!-- 				</div> -->
				
				<div class="form-group">	
					<label><fmt:message key="OrgType.list.CreatedOn" /></label>
					<fmt:formatDate value="${orgManagement.createdTime}" pattern="${pattern}" />
				</div>
				<div class="form-group">
					<label><fmt:message key="OrgType.list.ModifiedOn" /></label>
					<fmt:formatDate value="${orgManagement.modifiedTime}" pattern="${pattern}" />					
				</div>
			</div>
			</div>
		</div>
	</div>
</section>


