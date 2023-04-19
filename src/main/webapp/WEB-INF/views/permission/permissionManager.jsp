<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section class="content-header">
	<h1>Permission Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Permission</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main" style="">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Permission List</h3>
				</div>
				<sec:authorize access="hasRole('can_permission_create')">
					<%-- 				<c:if --%>
					<%-- 					test="${ct:isAllowed('can_permission_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./permissionmanagement-new'><i
							class="fa fa-plus"></i> New</a>
					</div>
					<%-- 				</c:if> --%>
				</sec:authorize>
			</div>

			<sec:authorize access="hasRole('can_permission_read')">
				<%-- 			<c:if --%>
				<%-- 				test="${ct:isAllowed('can_permission_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="demo-container">
						<div>

							<table id="permissionListTable"
								class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">
								<thead>
									<tr class="info">
										<th class="text-center" style="max-width: 40px;"><i
											class="fa fa-cogs"></i></th>
										<th><fmt:message key="Permission.list.Name" /></th>
										<th><fmt:message key="Permission.list.Title" /></th>
										<th><fmt:message key="Permission.list.Abbreviation" /></th>
										<th><fmt:message key="Permission.list.Key" /></th>
										<th data-hide="phone, tablet"><fmt:message
												key="Permission.list.CreatedOn" /></th>
										<th data-hide="phone, tablet"><fmt:message
												key="Permission.list.ModifiedOn" /></th>
										<sec:authorize access="hasRole('can_permission_update')">
											<%-- 										<c:if --%>
											<%-- 											test="${ct:isAllowed('can_permission_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center"
												style="max-width: 40px; font-size: 18px;"><i
												class="fa fa-edit"></i></th>
											<%-- 										</c:if> --%>
										</sec:authorize>
										<%-- <sec:authorize access="hasRole('can_permission_delete')">
																					<c:if
																						test="${ct:isAllowed('can_permission_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
											<th class="text-center"
												style="max-width: 40px; font-size: 18px;"><i
												class="fa fa-trash-o"></i></th>
																					</c:if>
										</sec:authorize> --%>
									</tr>
								</thead>
								<tbody>
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${permissionsList}" var="permission"
										varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 5px;">${counter.index+1}</td>
											<td><small>${permission.module}</small>
											<td><small>${permission.title}</small></td>
											<td><small>${permission.abbreviation}</small></td>
											<td><small>${permission.keyVal}</small></td>
											<td><small><fmt:formatDate
														value="${permission.createdTime}" pattern="${pattern}" /></small></td>
											<td><small><fmt:formatDate
														value="${permission.modifiedTime}" pattern="${pattern}" /></small></td>
											<sec:authorize access="hasRole('can_permission_update')">
												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_permission_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 40px;" class="text-center"><a
													href="<c:url value='/permissionmanagement-${permission.id}-edit' />"
													title="Update"><i class="fa fa-edit"></i></a></td>
												<%-- 											</c:if> --%>
											</sec:authorize>
											<sec:authorize access="hasRole('can_permission_delete')">
												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_permission_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<%-- <td style="max-width: 40px;" class="text-center"><a
													href="javascript:onDeleteConfirm('<c:url value="/permissionmanagement-${permission.id}-delete" />')"
													title="Delete"><i class="fa fa-trash-o"></i></a></td> --%>
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


	<!-- 	<script src="./static/js/datatable/jquery-1.12.1.min.js" type="text/javascript"></script> -->
	<!-- 	<script src="./static/js/plugins/datatables/jquery.dataTables.js" -->
	<!-- 		type="text/javascript"></script> -->
	<!-- 	<script src="./static/js/plugins/datatables/dataTables.bootstrap.js" -->
	<!-- 		type="text/javascript"></script> -->

</section>

<!-- Tapan sirs work -->
<!-- <script src="//code.jquery.com/jquery-1.12.4.js"></script>
<script src="./static/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js" type="text/javascript"></script> -->


<script type="text/javascript">
	$(document).ready(function() {
		$('#permissionListTable').DataTable({
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



