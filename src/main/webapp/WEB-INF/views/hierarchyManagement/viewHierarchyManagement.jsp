<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<section class="content-header">

	<h1>Hierarchy</h1>

	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Hierarchy</li>
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
								View Hierarchy - <span class="edit-module-name">${hierarchyControl.name}</span>
							</h3>
				</div>

				<div class="btn-group custom_group" role="group"
					aria-label="Basic example">
					<a class="btn btn-success" href='./hierarchyManagament'> <i
						class="fa fa-arrow-left"></i> Back
					</a>
				</div>
			</div>
			<div class="box-body custom_form_body">	
			<div class="view-profile">			
				<div class="box box-primary">
				
					<div class="box-header">
						<h3 class="box-title">Hierarchy Information</h3>
					</div>
				</div>
				<fmt:message key="yyyy.MM.dd" var="pattern" />
				<div class="form-group">						
					<label><fmt:message key="Common.form.OrganizationName" /></label>
					${hierarchyControl.orgManagement.name}
				</div>	
				<div class="form-group">						
					<label><fmt:message key="hierarchyManagement.list.Name" /></label>
					${hierarchyControl.name}
				</div>	
				<div class="form-group">						
					<label><fmt:message key="hierarchyManagement.list.Abbreviation" /></label>
					${hierarchyControl.abbreviation}
				</div>	
				<div class="form-group">
					<label><fmt:message key="hierarchyManagement.list.Level" /></label>
					${hierarchyControl.hierarchyLevel}
				</div>	
				<div class="form-group">
					<label><fmt:message key="hierarchyManagement.list.CreatedOn" /></label>					
					<fmt:formatDate value="${hierarchyControl.createdTime}" pattern="${pattern}" />
				</div>
				
				<div class="form-group">
					<label><fmt:message key="hierarchyManagement.list.ModifiedOn" /></label>
					<fmt:formatDate value="${hierarchyControl.modifiedTime}" pattern="${pattern}" />					
				</div>				
			</div>
		</div>
	</div>
	</div>
</section>
