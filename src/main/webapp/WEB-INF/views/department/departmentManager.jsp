<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<link
	href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css"
	type="text/css">
<section class="content-header">
	<h1>Department Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Department</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main" style="">
			<div class="box-header">
				<span class="has-error ">${userExistForDepartment}</span>
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Department List</h3>

					<form:form action="filterDepartmentHierarchyByOrganization"
						id="NewEditForm" method="POST" modelAttribute="department"
						autocomplete="off" class="form-inline">
						<sec:authorize access="hasRole('can_view_everything')">
							<jsp:include page="../UtilityFiles/OrgToHierarchy.jsp"></jsp:include>
							<div class="form-group button-group-set">
								<div class="col-sm-12">
									<button type="submit" class="btn btn-primary btn-sm m20">
										<i class="fa fa-floppy-o"></i> Search
									</button>
								</div>
							</div>
						</sec:authorize>
						<sec:authorize access="!hasRole('can_view_everything')">
							<div class="form-group">
								<form:hidden path="hierarchyControl.orgManagement.orgType.id"
									id="orgManagementId" />
								<%-- <label><fmt:message key="Common.form.Organization" /></label>
												<form:input path="hierarchyControl.orgManagement.orgType.orgType"
													id="orgManagementOrgTypeName" readonly="true"
													cssClass="form-control" /> --%>
							</div>
							<div class="form-group">
								<form:hidden path="hierarchyControl.orgManagement.id"
									id="orgManagementId" />
								<%-- <label><fmt:message key="Common.form.OrganizationName" /></label>
												<form:input path="orgManagement.name" id="orgManagementName"
													readonly="true" cssClass="form-control" /> --%>
							</div>
							<div class="form-group">
								<label><fmt:message key="Common.form.HierarchyName" /></label>
								<form:select path="hierarchyControl.id" cssClass="form-control"
									id="hierarchyControlId">
									<form:option value="-1" label="- Hierarchy -" />
									<form:options items="${hierarchyList}" itemValue="id"
										itemLabel="name" />
								</form:select>
								<div class="has-error">
									<form:errors path="hierarchyControl.id" cssClass="help-inline" />
								</div>
							</div>
							<div class="form-group button-group-set">
								<div class="col-sm-12">
									<button type="submit" class="btn btn-primary btn-sm">
										<i class="fa fa-floppy-o"></i> Search
									</button>
								</div>
							</div>
						</sec:authorize>
					</form:form>

				</div>
				<sec:authorize access="hasRole('can_departmentmanagement_create')">
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./departmentmanagement-new'><i
							class="fa fa-plus"></i> New</a>
					</div>
				</sec:authorize>
			</div>
			<sec:authorize access="hasRole('can_departmentmanagement_read')">
				<div class="box-body">
					<div class="demo-container">
						<div>
							<table id="departmentListTable"
								class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">
								<thead>
									<tr class="info">
										<th class="text-center" style="max-width: 40px;"><i
											class="fa fa-cogs"></i></th>
										<sec:authorize access="hasRole('can_view_everything')">
											<th><fmt:message key="Common.form.OrganizationName" /></th>
										</sec:authorize>
										<th><fmt:message key="Common.form.HierarchyName" /></th>
										<th><fmt:message key="Department.list.DepartmentName" /></th>
										<th data-hide="phone, tablet"><fmt:message
												key="Permission.list.CreatedOn" /></th>
										<th data-hide="phone, tablet"><fmt:message
												key="Permission.list.ModifiedOn" /></th>
										<th data-hide="phone, tablet"><fmt:message
												key="Department.list.UsersList" /></th>

										<sec:authorize
											access="hasRole('can_departmentmanagement_update')">
											<th class="text-center"
												style="max-width: 40px; font-size: 18px;"><i
												class="fa fa-edit"></i></th>
										</sec:authorize>
										<sec:authorize
											access="hasRole('can_departmentmanagement_delete')">
											<th class="text-center"
												style="max-width: 40px; font-size: 18px;"><i
												class="fa fa-trash-o"></i></th>
										</sec:authorize>
									</tr>
								</thead>
								<tbody>
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${departmentList}" var="department"
										varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 40px;">${counter.index+1}</td>
											<sec:authorize access="hasRole('can_view_everything')">
												<td><small>${department.hierarchyControl.orgManagement.name}</small></td>
											</sec:authorize>
											<td><small>${department.hierarchyControl.name}</small></td>
											<td><small>${department.title}</small></td>
											<td><small><fmt:formatDate
														value="${department.createdTime}" pattern="${pattern}" /></small></td>
											<td><small><fmt:formatDate
														value="${department.modifiedTime}" pattern="${pattern}" /></small></td>
											<td><small>
											<a class="btn btn-success"
													href='#'><i
														class="fa fa-plus"></i>
														${department.getUsers().size()}</a>											
											</small></td>
											<sec:authorize
												access="hasRole('can_departmentmanagement_update')">
												<td style="max-width: 40px;" class="text-center"><c:if
														test="${userHierarchy.id != department.hierarchyControl.id}">
														<a
															href="<c:url value='/departmentmanagement-${department.id}-edit' />"
															title="Update"><i class="fa fa-edit"></i></a>
													</c:if></td>
											</sec:authorize>
											<sec:authorize
												access="hasRole('can_departmentmanagement_delete')">
												<td style="max-width: 40px;" class="text-center"><c:if
														test="${userHierarchy.id != department.hierarchyControl.id}">
														<a
															href="javascript:onDeleteConfirm('<c:url value="/departmentmanagement-${department.id}-delete" />')"
															title="Delete"><i class="fa fa-trash-o"></i></a>
													</c:if></td>
											</sec:authorize>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</div>
</section>

<!-- Tapan sirs work -->

<script type="text/javascript">
	$(document).ready(function() {
		$('#departmentListTable').DataTable({
			"dom" : '<"top"i>rt<"bottom"flp><"clear">'
		});
	});

	$(document)
			.ready(
					function() {
						//alert(11);
						$('.bottom').addClass('search_showentry_cls');
						$('.top').addClass('Showing_entry_cls');
						$(
								'.search_showentry_cls .dataTables_length, .search_showentry_cls .dataTables_filter')
								.insertBefore(
										'.table.table-bordered.table-striped.dataTable');
						$('.Showing_entry_cls')
								.insertAfter(
										'.table.table-bordered.table-striped.dataTable');
					});
</script>


