<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Role</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Role</li>
	</ol>
</section>
<section class="content">
	<div class="row">

		<div class="col-md-12 table-container-main">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Role List</h3>
				</div>
			</div>
			<sec:authorize access="hasRole('can_role_read')">
				<%-- 			<c:if test="${ct:isAllowed('can_role_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="demo-container">
						<div>
							<c:set var="parentID" value="-1" />
							<c:set var="count" value="0" />
							<table class="role_tbl_page">
								<c:forEach items="${roleList}" var="role" varStatus="counter">
									<tr id="${role.id }"
										class="tr-role-tree-${role.parent} table-row">
										<td class="td-colspan" colspan="${role.parent+1 }"></td>
										<td><span id="td-role-tree-${role.id }"
											class="role-heading-cls td-role-tree-${role.parent}"><i
												class="fa fa-folder-open-o font-awosome-icon"
												aria-hidden="true"></i> ${role.title}</span> <span
											class="action_btn_before_div"></span> <sec:authorize
												access="hasRole('can_role_create')">
												<%-- 										<c:if test="${ct:isAllowed('can_role_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<span class=""
													onclick="addRole('child-role-new-${role.id}')"><i
													class="fa fa-plus-square-o"></i></span>
												<%-- 										</c:if> --%>
											</sec:authorize> <sec:authorize access="hasRole('can_role_update')">
												<%-- 										<c:if test="${ct:isAllowed('can_role_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<span class=""
													onclick="editRole('child-role-edit-${role.id}')"><i
													class="fa fa-pencil-square-o" aria-hidden="true"></i> </span>
												<%-- 										</c:if> --%>
											</sec:authorize> <sec:authorize access="hasRole('can_role_delete')">
												<%-- 										<c:if test="${ct:isAllowed('can_role_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<span class=""><a href="role-${role.id}-delete"><i
														class="fa fa-trash-o" aria-hidden="true"></i> </a></span>
												<%-- 										</c:if> --%>
											</sec:authorize> <span class=""><a
												href="<c:url value='/editRolePermissionAssociation-${role.id}'/>"
												data-toggle="tooltip" data-html="true"
												data-placement="right" title="Assign Permission"><i
													class="fa fa-check" aria-hidden="true"></i> </a></span></td>
										<td>
											<div id="child-role-new-${role.id}" class="child-role-new">
												<form:form method="POST" action="role-new"
													modelAttribute="role" autocomplete="off"
													class="form-inline">
													<form:hidden path="parent" value="${role.id}" id="parent" />
													<form:input path="title" id="title" />
													<button type="submit" class="btn btn-primary btn-sm">
														<i class="fa fa-floppy-o"></i> Save
													</button>
													<a class="btn btn-danger btn-sm"
														href="javascript:closeAll();"> <i class="fa fa-times"></i>Cancel
													</a>
												</form:form>
											</div>
											<div id="child-role-edit-${role.id}" class="child-role-edit">
												<form:form method="POST" action="role-${role.id}-edit"
													modelAttribute="role" autocomplete="off"
													class="form-inline">
													<form:input path="title" value="${role.title}" id="title" />
													<button type="submit" class="btn btn-primary btn-sm">
														<i class="fa fa-check"></i> Update
													</button>
													<a class="btn btn-danger btn-sm"
														href="javascript:closeAll();"> <i class="fa fa-times"></i>
														Cancel
													</a>
												</form:form>
											</div>
										</td>
									</tr>
								</c:forEach>
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
	closeAll();
	var counter = 0;
	function show(id) {

		if (counter == 0) {
		}

		$('.tr-role-tree-' + id).each(function() {
			$('.tr-role-tree-' + id).toggle();
			show($(this).attr('id'));
		});
		counter++;
	}

	function closeAll() {
		$(".child-role-new").hide();
		$(".child-role-edit").hide();
	}
	function addRole(id) {
		$(".child-role-new").hide();
		$(".child-role-edit").hide();
		$("#" + id).show();
	}

	function editRole(id) {
		$(".child-role-new").hide();
		$(".child-role-edit").hide();
		$("#" + id).toggle();
	}

	$(document).ready(function() {
		$('.table-row').each(function() {
			var colspan = $(this).find(".td-colspan").attr("colspan");
			var width = colspan * 15;
			$(this).find(".td-colspan").css('width', width + 'px');
		});
	});
</script>
