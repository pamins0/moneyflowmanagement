<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section class="content-header">
	<h1>Hierarchy Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Hierarchy</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main" style="">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Hierarchy List</h3>
					<form:form action="filterHierarchyByOrganization" id="NewEditForm"
						method="POST" modelAttribute="hierarchyControl" autocomplete="off"
						class="form-inline">
						
						<sec:authorize access="hasRole('can_view_everything')">
						<jsp:include page="../UtilityFiles/OrgToOrgManagement.jsp"></jsp:include>
								<div class="form-group button-group-set m20">
									<div class="col-sm-12">
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-floppy-o"></i> Search
										</button>
									</div>
								</div>
						 </sec:authorize>
						<%-- <c:choose>
							<c:when
								test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
								<jsp:include page="../UtilityFiles/OrgToOrgManagement.jsp"></jsp:include>
								<div class="form-group button-group-set m20">
									<div class="col-sm-12">
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-floppy-o"></i> Search
										</button>
									</div>
								</div>
							</c:when>
						</c:choose> --%>
					</form:form>
				</div>
				<%-- <c:if
					test="${ct:isAllowed('can_permission_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./permissionmanagement-new'><i
							class="fa fa-plus"></i> New</a>
					</div>
				</c:if> --%>
			</div>
			<sec:authorize access="hasRole('can_hierarchymanagement_read')">
<%-- 			<c:if --%>
<%-- 				test="${ct:isAllowed('can_hierarchymanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="demo-container">
						<div>
							<span class="has-error text-center">${parentBranchesNotCreated}</span>
							<table id="hierarchyListTable"
								class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">
								<thead>
									<tr class="info">
										<th class="text-center" style="max-width: 40px;"><i
											class="fa fa-cogs"></i></th>
											<sec:authorize access="hasRole('can_view_everything')">
<%-- 										<c:if test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th><fmt:message key="Common.form.OrganizationName" /></th>
<%-- 										</c:if> --%>
										</sec:authorize>
										<th><fmt:message key="hierarchyManagement.list.Level" /></th>
										<th><fmt:message key="hierarchyManagement.list.Name" /></th>
										<th><fmt:message
												key="hierarchyManagement.list.Abbreviation" /></th>
										<th><fmt:message key="hierarchyManagement.list.IsCreated" /></th>
										<th><fmt:message
												key="hierarchyManagement.list.AddDepartment" /></th>
										<th><fmt:message
												key="hierarchyManagement.list.AddDesignation" /></th>
										<th><fmt:message key="hierarchyManagement.list.AddBranch" /></th>
										<th><fmt:message key="hierarchyManagement.list.AddRole" /></th>
										<sec:authorize access="hasRole('can_hierarchymanagement_read')">
<%-- 										<c:if --%>
<%-- 											test="${ct:isAllowed('can_hierarchymanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center"
												style="max-width: 40px; font-size: 18px;"><i
												class="fa fa-eye"></i></th>
<%-- 										</c:if> --%>
                                           </sec:authorize>
                                           <sec:authorize access="hasRole('can_hierarchymanagement_update')">
<%-- 										<c:if --%>
<%-- 											test="${ct:isAllowed('can_hierarchymanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center"
												style="max-width: 15px; font-size: 18px;"><i
												class="fa fa-edit"></i></th>
<%-- 										</c:if> --%>
										</sec:authorize>
										<%-- <c:if
											test="${ct:isAllowed('can_hierarchymanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
											<th class="text-center"
												style="max-width: 15px; font-size: 18px;"><i
												class="fa fa-trash-o"></i></th>
										</c:if> --%>
									</tr>
								</thead>
								<tbody>
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${hierarchyManagementList}" var="hierarchy"
										varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 40px;">${counter.index+1}</td>
											<sec:authorize access="hasRole('can_view_everything')">
<%-- 											<c:if test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td><small>${hierarchy.orgManagement.name}</small>
<%-- 											</c:if>  --%>
											</sec:authorize>
											<td><small>${hierarchy.hierarchyLevel}</small>
											<td><small>${hierarchy.name}</small></td>
											<td><small>${hierarchy.abbreviation}</small></td>
											<td><small>${hierarchy.getBranchManagements().size() eq 0 ? '<i class="fa fa-times" aria-hidden="true"></i>' : '<i class="fa fa-check" aria-hidden="true"></i>'}</small></td>
											<td><small><a
													href="<c:url value='/hierarchymanagementdepartment-${hierarchy.id}-hierarchy'/>"
													data-toggle="tooltip" data-html="true" data-placement="top"
													title="View Designation"><i class="fa fa-eye"
														aria-hidden="true"></i> </a> <a class="btn btn-success"
													href='./hierarchymanagementdepartment-associate-${hierarchy.id}'><i
														class="fa fa-plus"></i>
														${hierarchy.getDepartment().size()}</a></small></td>
											<td><small><a
													href="<c:url value='/hierarchymanagementdesignation-${hierarchy.id}-hierarchy'/>"
													data-toggle="tooltip" data-html="true" data-placement="top"
													title="View Designation"><i class="fa fa-eye"
														aria-hidden="true"></i> </a> <a class="btn btn-success"
													href='./hierarchymanagementdesignation-associate-${hierarchy.id}'><i
														class="fa fa-plus"></i>
														${hierarchy.getDesignation().size()}</a></small></td>
											<td><small><a
													href="<c:url value='/hierarchymanagementbranch-${hierarchy.id}-hierarchy'/>"
													data-toggle="tooltip" data-html="true" data-placement="top"
													title="View Entity"><i class="fa fa-eye"
														aria-hidden="true"></i> </a> <a class="btn btn-success"
													href='./hierarchymanagementbranch-associate-${hierarchy.id}'><i
														class="fa fa-plus"></i>
														${hierarchy.getBranchManagements().size()}</a></small></td>
											<td><small><a
													href="<c:url value='/hierarchymanagementrole-${hierarchy.id}-hierarchy'/>"
													data-toggle="tooltip" data-html="true" data-placement="top"
													title="View Role"><i class="fa fa-eye"
														aria-hidden="true"></i> </a> <a class="btn btn-success"
													href='./hierarchymanagementrole-associate-${hierarchy.id}'><i
														class="fa fa-plus"></i> ${hierarchy.getRoles().size()}</a></small></td>
														
														<sec:authorize access="hasRole('can_hierarchymanagement_read')">
<%-- 											<c:if --%>
<%-- 												test="${ct:isAllowed('can_hierarchymanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 40px;" class="text-center"><a
													href="<c:url value='/hierarchymanagement-${hierarchy.id}-view' />"
													title="View Details"><i class="fa fa-eye"></i></a></td>
<%-- 											</c:if> --%>
											</sec:authorize>
											<sec:authorize access="hasRole('can_hierarchymanagement_update')">
												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_hierarchymanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 40px;" class="text-center"><a
													href="<c:url value='/hierarchymanagement-${hierarchy.id}-edit' />"
													title="Update"><i class="fa fa-edit"></i></a></td>
<%-- 											</c:if> --%>
											</sec:authorize>
											<%-- <c:if
												test="${ct:isAllowed('can_hierarchymanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
												<td style="max-width: 40px;" class="text-center"><a
													href="javascript:onDeleteConfirm('<c:url value="/hierarchymanagement-${hierarchy.id}-delete" />')"
													title="Delete"><i class="fa fa-trash-o"></i></a></td>
											</c:if> --%>
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
		$('#hierarchyListTable').DataTable({
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




