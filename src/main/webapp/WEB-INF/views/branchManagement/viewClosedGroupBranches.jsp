<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<section class="content-header">

	<h1>Entity Group Management</h1>

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
					<a class="btn btn-success" href='./branchmanagement'> <i
						class="fa fa-arrow-left"></i> Back
					</a>
				</div>
			</div>
			<div class="box-body custom_form_body">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Closed Group Entities</h3>
					</div>
				</div>
				<div class="view-profile">
					<c:forEach items="${branchManagement.branchClosedGroups}" var="branch"
						varStatus="status">
						<div class="form-group">
							<label>Branch Name</label> ${branch.closedGroupBranch.branchName}
						</div>
						<div class="form-group">
							<label>Branch Code</label> ${branch.closedGroupBranch.branchCode}
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</section>


