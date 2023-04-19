<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Close Group Management</h1>
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
					<h3 class="box-title">Group List</h3>
				</div>
				<sec:authorize access="hasRole('can_branchmanagement_create')">
					<%-- 				<c:if test="${ct:isAllowed('can_branchmanagement_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./closegroupmanagement-new'><i
							class="fa fa-plus"></i> New</a>
					</div>
					<%-- 				</c:if> --%>
				</sec:authorize>
			</div>

			<sec:authorize access="hasRole('can_branchmanagement_read')">
				<%-- 			<c:if test="${ct:isAllowed('can_branchmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="box-body table-responsive">

						<div class="table-responsive">
							<table id="branchListTable"
								class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">
								<thead>
									<tr>
										<th class="text-center" style="max-width: 40px;"><i
											class="fa fa-cogs"></i></th>
										<th><fmt:message key="CloseGroup.list.GroupName" /></th>
										<th><fmt:message key="CloseGroup.list.GroupEntity" /> <sec:authorize
												access="hasRole('can_branchmanagement_read')">
												<%-- 										<c:if test="${ct:isAllowed('can_branchmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<th class="text-center" style="max-width: 40px;"><i
													class="fa fa-eye btn btn-xs "></i></th>
												<%-- 											</c:if>  --%>
											</sec:authorize> <sec:authorize
												access="hasRole('can_branchmanagement_update')">
												<%-- 												<c:if test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<th class="text-center" style="max-width: 40px;"><i
													class="fa fa-edit btn btn-xs "></i></th>
												<%-- 											</c:if>  --%>
											</sec:authorize> <sec:authorize
												access="hasRole('can_branchmanagement_delete')">
												<%-- 												<c:if test="${ct:isAllowed('can_branchmanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<th class="text-center" style="max-width: 40px;"><i
													class="fa fa-trash-o btn btn-xs"></i></th>
												<%-- 											</c:if> --%>
											</sec:authorize>
									</tr>
								</thead>
								<tbody id="fbody">
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${groupMap}" var="group" varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 70px;">${counter.index+1}</td>
											<td><small>${group.value.groupName}</small></td>
											<td><small>${group.value.count}</small></td>

											<sec:authorize
												access="hasRole('can_branchmanagement_read')">
												<%-- 											<c:if test="${ct:isAllowed('can_branchmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 15px;" class="text-center"><a
													href="<c:url value='/closegroupmanagement-${group.key}-view' />"
													title="View Details"><i class="fa fa-eye"></i></a> <%-- 												</c:if> --%>
												</td>
												<%-- 											</c:if> --%>
											</sec:authorize>
											<sec:authorize
												access="hasRole('can_branchmanagement_update')">
												<%-- 											<c:if test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 15px;" class="text-center"><a
													href="<c:url value='/closegroupmanagement-${group.key}-edit' />"
													title="Update"><i class="fa fa-edit"></i></a></td>
												<%-- 											</c:if> --%>
											</sec:authorize>
											<sec:authorize
												access="hasRole('can_branchmanagement_delete')">
												<%-- 											<c:if test="${ct:isAllowed('can_branchmanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 15px;" class="text-center"><a
													href="javascript:onDeleteConfirm('<c:url value="/closegroupmanagement-${group.key}-delete" />')"
													title="Delete"><i class="fa fa-trash-o"></i></a></td>
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

<script>
	$("#searchInputSelect").change(function() {
		var indexColumn = 2;
		//console.log("value=%o", this.value);
		//split the current value of searchInput
		var data = this.value.split(" ");
		//create a jquery object of the rows
		var jo = $("#fbody").find("tr")
		//hide all the rows
		.hide();
		//debugger;
		//Recusively filter the jquery object to get results.
		jo.filter(function(i, v) {
			var $t = $(this).children(":eq(" + indexColumn + ")");
			if (data == "-1") {
				return true;
			} else if ($t.is(":contains('" + data + "')")) {
				return true;
			}
			return false;
		})
		//show the rows that match.
		.show();
	});
</script>


<!-- Tapan sirs work paginations -->

<script type="text/javascript">
	$(document).ready(function() {
		$('#branchListTable').DataTable({
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



