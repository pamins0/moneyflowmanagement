<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script>
	/*$(document).ready(function(){
	 $('.org_type_cls').addClass('in');
	 });*/
</script>
<section class="content-header">
	<c:choose>
		<c:when test="${edit}">
			<h1>
				Edit Organization Type - <span class="edit-module-name">
					${orgType.orgType}</span>
			</h1>
		</c:when>
		<c:otherwise>
			<h1>Organizations</h1>
		</c:otherwise>
	</c:choose>

	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Organization</li>
		<li class="active">New/Update</li>
		<li class="active">Organization Type</li>
	</ol>
</section>

<section class="content">
	<div class="row">
		<sec:authorize access="hasRole('can_orgtype_create')">
			<%-- 		<c:if --%>
			<%-- 			test="${ct:isAllowed('can_orgtype_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<form:form method="POST" modelAttribute="orgType" autocomplete="off">
				<form:hidden path="id" />
				<div class="col-md-12  ">
					<div class="box box-primary">
						<div class="box-header">
							<h3 class="box-title">Organization Information</h3>
						</div>
						<div class="box-body ">
							<label><fmt:message key="OrgType.form.OrgType" /><span class="start_box">*</span></label>
							<div class="form-group">
								<form:input path="orgType" id="orgType" cssClass="form-control"
									requierd="required" />
								<div class="has-error">
									<form:errors path="orgType" cssClass="help-inline" />
								</div>
							</div>
							<label><fmt:message key="OrgType.form.Description" /></label>
							<div class="form-group">
								<form:textarea path="orgDetail" id="orgDetail"
									cssClass="form-control" />
								<div class="has-error">
									<form:errors path="orgDetail" cssClass="help-inline" />
								</div>
							</div>

							<div class="form-group">
								<c:choose>
									<c:when test="${edit}">
										<input type="submit" value="Update"
											class="btn btn-primary btn-sm" />
										<a class="btn btn-danger btn-sm"
											href="<c:url value='/orgtypes' />"> <i
											class="fa fa-times"></i> Cancel
										</a>
									</c:when>
									<c:otherwise>
										<input type="submit" value="Save"
											class="btn btn-primary btn-sm" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</form:form>
			<%-- 		</c:if> --%>
		</sec:authorize>



		<span class="has-error text-center">${usernotauthorize}</span>
		<sec:authorize access="hasRole('can_orgtype_read')">
			<%-- 		<c:if --%>
			<%-- 			test="${ct:isAllowed('can_orgtype_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<div class="col-md-12">
				<div class="box box-warning">
					<div class="box-body">
						<div class="box-header">
							<i class="fa fa-tasks"></i>
							<h3 class="box-title">Currently Running Organizations</h3>
						</div>
						<div class="box-body">
							<div class="box-body table-responsive org-type-wrapper">
								<table id="orglisttable"
									class="table table-bordered table-striped"
									style="max-height: 500px; overflow-y: scroll;">
									<thead>
										<tr>
											<th class="text-center" style="max-width: 20px;"><i
												class="fa fa-cogs"></i></th>
											<th><fmt:message key="OrgType.list.OrgType" /></th>
											<th><fmt:message key="OrgType.list.CreatedOn" /></th>
											<th><fmt:message key="OrgType.list.ModifiedOn" /></th>
											<sec:authorize access="hasRole('can_orgtype_read')">
												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_orgtype_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<th class="text-center" style="max-width: 15px;"><i
													class="fa fa-eye btn btn-md"></i></th>
												<%-- 											</c:if> --%>
											</sec:authorize>
											<sec:authorize access="hasRole('can_orgtype_update')">
												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_orgtype_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<th class="text-center" style="max-width: 15px;"><i
													class="fa fa-edit btn btn-md"></i></th>
												<%-- 											</c:if> --%>
											</sec:authorize>
											<sec:authorize access="hasRole('can_orgtype_delete')">
												<%-- 											<c:if --%>
												<%-- 												test="${ct:isAllowed('can_orgtype_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<th style="max-width: 15px;" class="text-center"><i
													class="fa fa-trash-o btn btn-md"></i></th>
												<%-- 											</c:if> --%>
											</sec:authorize>
										</tr>
									</thead>
									<tbody>
										<fmt:message key="yyyy.MM.dd" var="pattern" />
										<fmt:message key="yyyy-MM-dd" var="pattern1" />
										<c:forEach items="${orgTypes}" var="org" varStatus="counter">
											<tr>
												<td class="text-center" style="max-width: 5px;">${counter.index+1}</td>
												<td><small>${org.orgType}</small></td>
												<td><small><fmt:formatDate
															value="${org.createdTime}" pattern="${pattern}" /></small></td>
												<td><small><fmt:formatDate
															value="${org.modifiedTime}" pattern="${pattern}" /></small></td>
												<sec:authorize access="hasRole('can_orgtype_read')">
													<%-- 												<c:if --%>
													<%-- 													test="${ct:isAllowed('can_orgtype_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
													<td><a href="<c:url value='/orgtype-${org.id}-view'/>"
														title="View Details"><i class="fa fa-eye"></i></a></td>
													<%-- 												</c:if> --%>
												</sec:authorize>
												<sec:authorize access="hasRole('can_orgtype_update')">
													<%-- 												<c:if --%>
													<%-- 													test="${ct:isAllowed('can_orgtype_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
													<td><a href="<c:url value='/orgtype-${org.id}-edit'/>"
														title="Update"><i class="fa fa-edit"></i></a></td>
													<%-- 												</c:if> --%>
												</sec:authorize>
												<sec:authorize access="hasRole('can_orgtype_delete')">
													<%-- 												<c:if --%>
													<%-- 													test="${ct:isAllowed('can_orgtype_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
													<td><a
														href="javascript:onDeleteConfirm('<c:url value='/orgtype-${org.id}-delete' />')"
														title="Delete"><i class="fa fa-trash-o"></i></a></td>
													<%-- 												</c:if> --%>
												</sec:authorize>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%-- 		</c:if> --%>
		</sec:authorize>
	</div>
</section>

<style>
.has-error {
	margin: 0px;
	position: relative;
}
</style>
<!-- Tapan sirs work -->

<script type="text/javascript">
	$(document).ready(function() {
		$('#orglisttable').DataTable({
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

