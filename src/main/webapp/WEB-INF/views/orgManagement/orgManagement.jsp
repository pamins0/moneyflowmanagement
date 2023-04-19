<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Organization Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Organization</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Organization List</h3>
				</div>

				<sec:authorize access="hasRole('can_orgmanagement_create')">
					<%-- 				<c:if --%>
					<%-- 					test="${ct:isAllowed('can_orgmanagement_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./orgmanagement-new'><i
							class="fa fa-plus"></i> New</a>
					</div>
					<%-- 				</c:if> --%>
				</sec:authorize>
			</div>
			<sec:authorize access="hasRole('can_orgmanagement_read')">
				<%-- 			<c:if --%>
				<%-- 				test="${ct:isAllowed('can_orgmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="demo-container">
						<table id="orgmanagementlisttable"
							class="table table-bordered table-striped"
							style="max-height: 500px; overflow-y: scroll;">
							<thead>
								<tr class="info">
									<!-- 								<th data-toggle="true" class="text-center" style="max-width: 20px;"></th> -->
									<!-- 								<th data-type="number">#</th> -->
									<th class="text-center" style="max-width: 40px;"><i
										class="fa fa-cogs"></i></th>
									<th><fmt:message key="OrgManagement.list.OrgType" /></th>
									<th><fmt:message key="OrgManagement.list.OrgName" /></th>
									<th><fmt:message key="OrgManagement.list.Location" /></th>
									<th data-hide="phone"><fmt:message
											key="OrgManagement.list.Email" /></th>
									<th data-hide="phone"><fmt:message
											key="OrgManagement.list.ContactNo" /></th>
									<th data-hide="phone, tablet"><fmt:message
											key="OrgManagement.list.CmsApproach" /></th>
									<th data-hide="phone, tablet"><fmt:message
											key="OrgManagement.list.AddHierarchy" /></th>
									<%-- <th data-hide="phone, tablet"><fmt:message key="OrgManagement.list.CreatedOn" /></th>
								<th data-hide="phone, tablet"><fmt:message key="OrgManagement.list.ModifiedOn" /></th> --%>
									<sec:authorize access="hasRole('can_orgmanagement_read')">
										<%-- 									<c:if --%>
										<%-- 										test="${ct:isAllowed('can_orgmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
										<th class="text-center"
											style="max-width: 40px; font-size: 18px;"><i
											class="fa fa-eye"></i></th>
										<%-- 									</c:if> --%>
									</sec:authorize>
									<sec:authorize
										access="hasRole('can_orgmanagement_update')">
										<%-- 									<c:if --%>
										<%-- 										test="${ct:isAllowed('can_orgmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
										<th class="text-center"
											style="max-width: 40px; font-size: 18px;"><i
											class="fa fa-edit"></i></th>
										<%-- 									</c:if> --%>
									</sec:authorize>
									<sec:authorize
										access="hasRole('can_orgmanagement_delete')">
										<%-- 									<c:if --%>
										<%-- 										test="${ct:isAllowed('can_orgmanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
										<th class="text-center"
											style="max-width: 40px; font-size: 18px;"><i
											class="fa fa-trash-o"></i></th>
										<%-- 									</c:if> --%>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
								<fmt:message key="yyyy.MM.dd" var="pattern" />
								<c:forEach items="${orgManagementList}" var="org"
									varStatus="counter">
									<tr>
										<!-- 								<td></td> -->
										<td class="text-center" style="max-width: 40px;">${counter.index+1}</td>
										<td><small>${org.orgType.orgType}</small>
										<td><small>${org.name}</small></td>
										<td><small>${org.location}</small></td>
										<td><small>${org.email}</small></td>
										<td><small>${org.contactNo}</small></td>
										<td><small>${org.cmsApproach eq 0 ? 'Centralized' : 'De-Centralized'}</small></td>
										<td><small> <c:choose>
													<c:when test="${org.currencyChest}">
														<c:choose>
															<c:when
																test="${org.getHierarchyControls().size() - 1 eq org.orgLevel}">
																<a
																	href="<c:url value='/organizationHierarchy-${org.id}-view'/>"
																	data-toggle="tooltip" data-html="true"
																	data-placement="top" title="View Hierarchy"><i
																	class="fa fa-eye" aria-hidden="true"></i> </a>
																<a class="btn btn-success not-active" href='#'>
																	${org.getHierarchyControls().size()}</a>
															</c:when>
															<c:otherwise>
																<a class="btn btn-success"
																	href='./addhierarchy-${org.id}'><i
																	class="fa fa-plus"></i> </a>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when
																test="${org.getHierarchyControls().size() eq org.orgLevel}">
																<a
																	href="<c:url value='/organizationHierarchy-${org.id}-view'/>"
																	data-toggle="tooltip" data-html="true"
																	data-placement="top" title="View Hierarchy"><i
																	class="fa fa-eye" aria-hidden="true"></i> </a>
																<a class="btn btn-success btn-block not-active" href='#'>
																	${org.getHierarchyControls().size()}</a>
															</c:when>
															<c:otherwise>
																<a class="btn btn-success"
																	href='./addhierarchy-${org.id}'><i
																	class="fa fa-plus"></i> </a>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
										</small></td>
										<%-- <td><small><fmt:formatDate
															value="${org.createdTime}" pattern="${pattern}" /></small></td>
									<td class="editable_table_before_btn"><small><fmt:formatDate
															value="${org.modifiedTime}" pattern="${pattern}" /></small></td> --%>
										<sec:authorize access="hasRole('can_orgmanagement_read')">
											<%-- 										<c:if --%>
											<%-- 											test="${ct:isAllowed('can_orgmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<td style="max-width: 40px;" class="text-center"><a
												href="<c:url value='/orgmanagement-${org.id}-view' />"
												title="View Details"><i class="fa fa-eye"></i></a></td>
											<%-- 										</c:if> --%>
										</sec:authorize>
										<sec:authorize
											access="hasRole('can_orgmanagement_update')">
											<%-- 										<c:if --%>
											<%-- 											test="${ct:isAllowed('can_orgmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<td style="max-width: 40px;" class="text-center"><a
												href="<c:url value='/orgmanagement-${org.id}-edit' />"
												title="Update"><i class="fa fa-edit"></i></a></td>
											<%-- 										</c:if> --%>
										</sec:authorize>
										<sec:authorize
											access="hasRole('can_orgmanagement_delete')">
											<%-- 										<c:if --%>
											<%-- 											test="${ct:isAllowed('can_orgmanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<td style="max-width: 40px;" class="text-center"><a
												href="javascript:onDeleteConfirm('<c:url value="/orgmanagement-${org.id}-delete" />')"
												title="Delete"><i class="fa fa-trash-o"></i></a></td>
											<%-- 										</c:if> --%>
										</sec:authorize>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot class="hide-if-no-paging">
								<tr>
									<td colspan="6">
										<div class="pagination pagination-centered"></div>
									</td>
								</tr>
							</tfoot>
						</table>
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
		$('#orgmanagementlisttable').DataTable({
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

