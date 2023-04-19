<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Branch Account Detail</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Branch</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Branch Accounts</h3>
				</div>
				<%-- <c:if test="${ct:isAllowed('can_branchmanagementaccount_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./branchaccount-new'><i class="fa fa-plus"></i> New</a>
					</div>
				</c:if> --%>
			</div>
			<sec:authorize
				access="hasRole('can_branchmanagementaccount_read')">
				<%-- 			<c:if test="${ct:isAllowed('can_branchmanagementaccount_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="box-body table-responsive">
						<table id="branchAccountListTable"
							class="table table-bordered table-striped"
							style="max-height: 500px; overflow-y: scroll;">
							<thead>
								<tr>
									<th class="text-center" style="max-width: 20px;"><i
										class="fa fa-cogs"></i></th>
									<th><fmt:message key="BranchAccount.list.AccountNumber" /></th>
									<th><fmt:message key="BranchAccount.list.Name" /></th>
									<th><fmt:message key="BranchAccount.list.BranchName" /></th>
									<th><fmt:message key="BranchAccount.list.BranchCode" /></th>
									<th><fmt:message key="BranchAccount.list.IFSC" /></th>
									<th><fmt:message key="BranchAccount.list.TransferAmount" /></th>
									<th><fmt:message key="BranchAccount.list.Location" /></th>
									<th><fmt:message key="BranchAccount.list.CreatedOn" /></th>

									<sec:authorize
										access="hasRole('can_branchmanagementaccount_update')">
										<%-- 								<c:if test="${ct:isAllowed('can_branchmanagementaccount_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
										<th class="text-center" style="max-width: 15px;"><i
											class="fa fa-edit btn btn-xs bg-yellow"></i></th>
										<%-- 								</c:if> --%>
									</sec:authorize>
									<sec:authorize
										access="hasRole('can_branchmanagementaccount_delete')">
										<%-- 									<c:if --%>
										<%-- 										test="${ct:isAllowed('can_branchmanagementaccount_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
										<th class="text-center" style="max-width: 15px;"><i
											class="fa fa-trash-o btn btn-xs bg-red"></i></th>
										<%-- 									</c:if> --%>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
								<fmt:message key="yyyy.MM.dd" var="pattern" />
								<c:forEach items="${branchAccounts}" var="account"
									varStatus="counter">
									<tr>
										<td class="text-center" style="max-width: 5px;">${counter.index+1}</td>
										<td><small>${account.accountNumber}</small></td>
										<td><small>${account.name}</small></td>
										<td><small>${account.branchManagement.branchName}</small></td>
										<td><small>${account.branchCode}</small></td>
										<td><small>${account.ifsc}</small></td>
										<td><small>${account.transferAmount}</small></td>
										<td><small>${account.location}</small></td>
										<td><small><fmt:formatDate
													value="${account.createdTime}" pattern="${pattern}" /></small></td>
										<sec:authorize
											access="hasRole('can_branchmanagementaccount_update')">
											<%-- 										<c:if --%>
											<%-- 											test="${ct:isAllowed('can_branchmanagementaccount_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<td style="max-width: 15px;" class="text-center"><a
												href="<c:url value='/branchaccount-${account.id}-edit' />"
												title="Update"><i class="fa fa-edit"></i></a></td>
											<%-- 										</c:if> --%>
										</sec:authorize>
										<sec:authorize
											access="hasRole('can_branchmanagementaccount_delete')">
											<%-- 										<c:if --%>
											<%-- 											test="${ct:isAllowed('can_branchmanagementaccount_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<td style="max-width: 15px;" class="text-center"><a
												href="javascript:onDeleteConfirm('<c:url value="/branchaccount-${account.id}-delete" />')"
												title="Delete"><i class="fa fa-trash-o"></i></a></td>
											<%-- 										</c:if> --%>
										</sec:authorize>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<%-- 			</c:if> --%>
			</sec:authorize>
		</div>
	</div>
</section>