<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content-header">
	<h1>User Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>User</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<form:form id="NewEditForm" method="POST" modelAttribute="userRole" autocomplete="off" class="form-inline">
			<form:hidden path="id" />
			<div class="col-md-12 table-container-main">
				<div class="box-header custom_header_form_page">
					<div class="heading-title-wrapper">
						<i class="fa fa-gear"></i>
						<c:choose>
							<c:when test="${edit}">
								<h3 class="box-title">Edit User Role</h3>
							</c:when>
							<c:otherwise>
								<h3 class="box-title">New User Role</h3>
							</c:otherwise>
						</c:choose>
					</div>	

					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./usermanagement'>
						<i class="fa fa-arrow-left"></i> Back</a>
					</div>
				</div>
				<div class="box-body custom_form_body">
					<div class="form-group">
						<label>Roles</label>
						<form:select path="role.id" cssClass="form-control">
							<form:option value="-1" label="- Branch -" />
							<form:options items="${roleList}" itemValue="id" itemLabel="title" />
						</form:select>						
						<div class="has-error">
							<form:errors path="role.id" cssClass="help-inline" />
						</div>
					</div>
					<form:hidden path="user.id" value="" />

					<div class="form-group">
						<label>User Name</label>
						<form:input path="user.name" id="name" cssClass="form-control" />
						<div class="has-error">
							<form:errors path="user.name" cssClass="help-inline" />
						</div>
					</div>
					<div class="form-group button-group-set">
						<div class="col-sm-12">
							<c:choose>
								<c:when test="${edit}">
									<button type="submit" class="btn btn-primary btn-sm">
										<i class="fa fa-check"></i> Update
									</button>
									<a class="btn btn-danger btn-sm"
										href="<c:url value='/usermanagement' />"><i
										class="fa fa-times"></i> Cancel</a>
								</c:when>
								<c:otherwise>
									<button type="submit" class="btn btn-primary btn-sm">
										<i class="fa fa-floppy-o"></i> Save
									</button>
									<a class="btn btn-danger btn-sm" href="<c:url value='/usermanagement' />">
										<i class="fa fa-times"></i> Cancel
									</a>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</section>