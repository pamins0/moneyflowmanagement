<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Group Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Group</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Branch List</h3>
				</div>
				<%-- <c:if
					test="${ct:isAllowed('can_branchmanagement_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./branchmanagement-new'><i
							class="fa fa-plus"></i> New</a>
					</div>
				</c:if> --%>
			</div>
			<sec:authorize access="hasRole('can_branchmanagement_read')">
<%-- 			<c:if --%>
<%-- 				test="${ct:isAllowed('can_branchmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="box-body table-responsive">
		
					
						<table id="branchListTable"
							class="table table-bordered table-striped"
							style="max-height: 500px; overflow-y: scroll;">
							<thead>
								<tr>
									<th class="text-center" style="max-width: 20px;"><i
										class="fa fa-cogs"></i></th>
									<th><fmt:message key="OrgManagement.list.OrgName" /></th>
									<th><fmt:message key="BranchManagement.list.BranchName" /></th>
									<th><fmt:message key="BranchManagement.list.BranchCode" /></th>
									<th><fmt:message key="BranchManagement.list.Abbreviation" /></th>
									<th><fmt:message key="BranchManagement.list.BranchCashLimit" /></th>
									<th><fmt:message key="BranchManagement.list.AddContact" /></th>
									<th><fmt:message key="BranchManagement.list.AddAccount" /></th>
									
									<sec:authorize access="hasRole('can_branchmanagement_update')">
<%-- 									<c:if --%>
<%-- 										test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
										<th class="text-center" style="max-width: 15px;"><i
											class="fa fa-edit btn btn-xs bg-yellow"></i></th>
<%-- 									</c:if> --%>
									</sec:authorize>
									<sec:authorize access="hasRole('can_branchmanagement_delete')">
<%-- 									<c:if --%>
<%-- 										test="${ct:isAllowed('can_branchmanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
										<th class="text-center" style="max-width: 15px;"><i
											class="fa fa-trash-o btn btn-xs bg-red"></i></th>
<%-- 									</c:if> --%>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
							<fmt:message key="yyyy.MM.dd" var="pattern" />
								<c:forEach items="${branches}" var="branch" varStatus="counter">
									<tr>
										<td class="text-center" style="max-width: 5px;">${counter.index+1}</td>
										<td><small>${branch.orgManagement.name}</small></td>
										<td><small>${branch.branchName}</small></td>
										<td><small>${branch.branchCode}</small></td>
										<td><small>${branch.abbreviation}</small></td>
										<td><small>${branch.branchCashlimit}</small></td>
										<td><small><span class=""><a
													href="<c:url value='/branchcontact-${branch.id}-branch'/>"
													data-toggle="tooltip" data-html="true" data-placement="top"
													title="View Contacts"><i class="fa fa-eye"
														aria-hidden="true"></i> </a></span> <a class="btn btn-success"
												href='./branchcontact-associate-${branch.id}'><i
													class="fa fa-plus"></i>
													${branch.getBranchContacts().size()}</a></small></td>
										<td><small><span class=""><a
													href="<c:url value='/branchaccount-${branch.id}-branch'/>"
													data-toggle="tooltip" data-html="true" data-placement="top"
													title="View Accounts"><i class="fa fa-eye"
														aria-hidden="true"></i> </a></span> <a class="btn btn-success"
												href='./branchaccount-associate-${branch.id}'><i
													class="fa fa-plus"></i>
													${branch.getBranchAccountDetails().size()}</a></small></td>
													
													<sec:authorize access="hasRole('can_branchmanagement_update')">
<%-- 										<c:if --%>
<%-- 											test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<td style="max-width: 15px;" class="text-center"><a
												href="<c:url value='/branchmanagement-${branch.id}-edit' />"
												title="Update"><i class="fa fa-edit"></i></a></td>
<%-- 										</c:if> --%>
										</sec:authorize>
										<sec:authorize access="hasRole('can_branchmanagement_delete')">
<%-- 										<c:if --%>
<%-- 											test="${ct:isAllowed('can_branchmanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<td style="max-width: 15px;" class="text-center"><a
												href="javascript:onDeleteConfirm('<c:url value="/branchmanagement-${branch.id}-delete" />')"
												title="Delete"><i class="fa fa-trash-o"></i></a></td>
<%-- 										</c:if> --%>
										</sec:authorize>
									</tr>
								</c:forEach>
							</tbody>
						</table>
		<!--<script src="//code.jquery.com/jquery-1.12.4.js"></script>--->
		
		<!-- <script src="./static/js/plugins/datatables/jquery.dataTables.js" type="text/javascript"></script> 
		<script src="./static/js/plugins/datatables/dataTables.bootstrap.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function() { $("#branchListTable").dataTable(); });
		</script> -->
						
						</div>
				</div>
<%-- 			</c:if> --%>
			</sec:authorize>
		</div>
	</div>
</section>



			