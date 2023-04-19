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
	<h1>Role Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Role</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main" style="">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Role List</h3>
					<form:form action="filterRoleHierarchyByOrganization"
						id="NewEditForm" method="POST" modelAttribute="role"
						autocomplete="off" class="form-inline">
						<sec:authorize access="hasRole('can_view_everything')">
							<jsp:include page="../UtilityFiles/OrgToHierarchy.jsp"></jsp:include>
							<div class="form-group button-group-set m20">
								<div class="col-sm-12">
									<button type="submit" class="btn btn-primary btn-sm">
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
							<div class="form-group col-sm-4">
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
									<button type="submit" class="btn btn-primary btn-sm m20">
										<i class="fa fa-floppy-o"></i> Search
									</button>
								</div>
							</div>
						</sec:authorize>
						<%-- <c:choose>
							<c:when
								test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
								<jsp:include page="../UtilityFiles/OrgToHierarchy.jsp"></jsp:include>
								<div class="form-group button-group-set m20">
									<div class="col-sm-12">
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-floppy-o"></i> Search
										</button>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<select id="searchInputSelect" class="form-control">
												<option value="-1" selected>--All Hierarchy--</option>
												<c:forEach items="${hierarchyList}" var="hier">
													<option value="${hier.hierarchyLevel}">${hier.name}</option>
												</c:forEach>
											</select>
								<div class="form-group">
									<form:hidden path="hierarchyControl.orgManagement.orgType.id"
										id="orgManagementId" />
									<label><fmt:message key="Common.form.Organization" /></label>
												<form:input path="hierarchyControl.orgManagement.orgType.orgType"
													id="orgManagementOrgTypeName" readonly="true"
													cssClass="form-control" />
								</div>
								<div class="form-group">
									<form:hidden path="hierarchyControl.orgManagement.id"
										id="orgManagementId" />
									<label><fmt:message key="Common.form.OrganizationName" /></label>
												<form:input path="orgManagement.name" id="orgManagementName"
													readonly="true" cssClass="form-control" />
								</div>
								<div class="form-group col-sm-4">
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
										<button type="submit" class="btn btn-primary btn-sm m20">
											<i class="fa fa-floppy-o"></i> Search
										</button>
									</div>
								</div>
							</c:otherwise>
						</c:choose> --%>
					</form:form>

				</div>
				<sec:authorize access="hasRole('can_role_create')">
					<%-- 				<c:if --%>
					<%-- 					test="${ct:isAllowed('can_role_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./rolemanagement-new'><i
							class="fa fa-plus"></i> New</a>
					</div>
					<%-- 				</c:if> --%>
				</sec:authorize>
			</div>
			<sec:authorize access="hasRole('can_role_read')">
				<%-- 			<c:if --%>
				<%-- 				test="${ct:isAllowed('can_role_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="demo-container">
						<div>
							<table id="roleListTable"
								class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">
								<thead>
									<tr class="info">
										<th class="text-center" style="max-width: 40px;"><i
											class="fa fa-cogs"></i></th>
										<sec:authorize access="hasRole('can_view_everything')">
											<%-- 										<c:if --%>
											<%-- 											test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th><fmt:message key="Common.form.OrganizationName" /></th>
											<%-- 										</c:if> --%>
										</sec:authorize>
										<th><fmt:message key="Common.form.HierarchyName" /></th>
										<th><fmt:message key="Role.list.RoleName" /></th>
										<th data-hide="phone, tablet"><fmt:message
												key="Permission.list.CreatedOn" /></th>
										<th data-hide="phone, tablet"><fmt:message
												key="Permission.list.ModifiedOn" /></th>
										<sec:authorize access="hasRole('can_role_assignpermission')">
											<%-- 										<c:if --%>
											<%-- 											test="${ct:isAllowed('can_role_assignpermission',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="wrapper-permision"><fmt:message
													key="Role.list.AssignPermission" /></th>
											<%-- 										</c:if> --%>
										</sec:authorize>

										<sec:authorize access="hasRole('can_role_update')">
											<th class="wrapper-permision"><fmt:message
													key="Role.list.Status" /></th>
										</sec:authorize>
										<sec:authorize access="hasRole('can_role_update')">
											<%-- 										<c:if --%>
											<%-- 											test="${ct:isAllowed('can_role_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center"
												style="max-width: 40px; font-size: 18px;"><i
												class="fa fa-edit"></i></th>
											<%-- 										</c:if> --%>
										</sec:authorize>
										<sec:authorize access="hasRole('can_role_delete')">
											<%-- 										<c:if --%>
											<%-- 											test="${ct:isAllowed('can_role_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center"
												style="max-width: 40px; font-size: 18px;"><i
												class="fa fa-trash-o"></i></th>
											<%-- 										</c:if> --%>
										</sec:authorize>
									</tr>
								</thead>
								<tbody>
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${roleList}" var="role" varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 5px;">${counter.index+1}</td>
											<sec:authorize access="hasRole('can_view_everything')">
												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td><small>${role.hierarchyControl.orgManagement.name}</small></td>
												<%-- 											</c:if> --%>
											</sec:authorize>
											<td><small>${role.hierarchyControl.name}</small></td>
											<td><small>${role.title}</small></td>
											<td><small><fmt:formatDate
														value="${role.createdTime}" pattern="${pattern}" /></small></td>
											<td><small><fmt:formatDate
														value="${role.modifiedTime}" pattern="${pattern}" /></small></td>
											<sec:authorize access="hasRole('can_role_assignpermission')">
												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_role_assignpermission',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td><c:if
														test="${userHierarchy.id != role.hierarchyControl.id}">
														<small><a class="btn btn-success"
															href='./editRolePermissionAssociation-${role.id}'><i
																class="fa fa-plus"></i>
																${role.getRolePermissions().size()}</a></small>
													</c:if></td>
												<%-- 											</c:if> --%>
											</sec:authorize>
											<sec:authorize access="hasRole('can_role_update')">
												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_role_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 15px;" class="text-center"><c:if
														test="${userHierarchy.id != role.hierarchyControl.id}">
														<c:choose>
															<c:when test="${role.status == 0}">
																<small><a class="btn btn-success"
																	href='./rolemanagement-${role.id}-editstatus'>
																		Disable</a></small>
															</c:when>
															<c:otherwise>
																<small><a class="btn btn-success"
																	href='./rolemanagement-${role.id}-editstatus'>
																		Enable</a></small>
															</c:otherwise>
														</c:choose>
													</c:if> <c:if
														test="${userHierarchy.id == role.hierarchyControl.id}">
														<!-- 															Blank -->
													</c:if></td>
												<%-- 											</c:if> --%>
											</sec:authorize>
											<sec:authorize access="hasRole('can_role_update')">

												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_role_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 15px;" class="text-center"><c:if
														test="${userHierarchy.id != role.hierarchyControl.id}">
														<a
															href="<c:url value='/rolemanagement-${role.id}-edit' />"
															title="Update"><i class="fa fa-edit"></i></a>
													</c:if></td>
												<%-- 											</c:if> --%>
											</sec:authorize>
											<sec:authorize access="hasRole('can_role_delete')">
												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_role_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 15px;" class="text-center"><c:if
														test="${userHierarchy.id != role.hierarchyControl.id}">
														<a
															href="javascript:onDeleteConfirm('<c:url value="/rolemanagement-${role.id}-delete" />')"
															title="Delete"><i class="fa fa-trash-o"></i></a>
													</c:if></td>
												<%-- 											</c:if> --%>
											</sec:authorize>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<%-- 			</c:if> --%>
			</sec:authorize>
		</div>
	</div>


</section>


<!-- Tapan sirs work -->

<script type="text/javascript">
	$(document).ready(function() {
		$('#roleListTable').DataTable({
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



