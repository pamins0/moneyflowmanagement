<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script
	src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>

<script>
	$(document).ready(function() {
		$('#NewEditForm').validate({ // initialize the plugin
			rules : {
				name : {
					required : true,
				},
				abbreviation : {
					required : true,
				}
			}
		});
	});

</script>


<section class="content-header">
	<h1>Hierarchy Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Hierarchy</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<c:if test="true">
	<section class="content">
		<div class="row">
			<form:form id="NewEditForm"
				method="POST" modelAttribute="hierarchyControl" autocomplete="on"
				class="form-inline">
				<form:hidden path="id" />
				<div class="col-md-12 table-container-main">
					<div class="box-header custom_header_form_page">
						<div class="heading-title-wrapper">
							<i class="fa fa-gear"></i>
							<c:choose>
								<c:when test="${edit}">
									<h3 class="box-title">
										Edit Hierarchy - <span class="edit-module-name">${name}
										</span>
									</h3>
								</c:when>
								<c:otherwise>
									<h3 class="box-title">New Hierarchy</h3>
								</c:otherwise>
							</c:choose>
						</div>

						<div class="btn-group custom_group" role="group"
							aria-label="Basic example">
							<a class="btn btn-success" href='./hierarchyManagament'> <i
								class="fa fa-arrow-left"></i> Back
							</a>
						</div>
					</div>
					<div class="box-body custom_form_body">
						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12 "><fmt:message
									key="hierarchyManagement.form.Level" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="hierarchyLevel" id="name"
										cssClass="form-control" readonly="true" />
									<div class="has-error">
										<form:errors path="hierarchyLevel" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="hierarchyManagement.form.Name" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="name" id="name" cssClass="form-control" />
									<div class="has-error">
										<form:errors path="name" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"> <fmt:message
									key="hierarchyManagement.form.Abbreviation" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="abbreviation" id="key"
										cssClass="form-control" />
									<div class="has-error">
										<form:errors path="abbreviation" cssClass="help-inline" />
									</div>
								</div>
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
											href="<c:url value='/hierarchyManagament' />"><i
											class="fa fa-times"></i> Cancel</a>
									</c:when>
									<c:otherwise>
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-floppy-o"></i> Save
										</button>
										<a class="btn btn-danger btn-sm"
											href="<c:url value='/hierarchyManagament' />"> <i
											class="fa fa-times"></i> Cancel
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
</c:if>