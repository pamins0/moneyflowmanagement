<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>

<section class="content-header">
	<h1>User Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">User</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Users List</h3>
					<section class="content">
						<div class="row">
							<form:form action="filterUserbyBranchHierarchy" id="NewEditForm"
								method="POST" modelAttribute="user" autocomplete="off"
								class="form-inline">

								<sec:authorize access="hasRole('can_view_everything')">
									<jsp:include page="../UtilityFiles/OrgToBranchManagement.jsp"></jsp:include>
								</sec:authorize>

								<sec:authorize access="!hasRole('can_view_everything')">
									<div class="form-group">
										<form:hidden
											path="branchManagement.hierarchyControl.orgManagement.orgType.id"
											id="orgManagementId" />
									</div>
									<div class="form-group">
										<form:hidden
											path="branchManagement.hierarchyControl.orgManagement.id"
											id="orgManagementId" />
									</div>
									<div class="form-group">
										<c:if test="${initialFilter}">
											<%-- 												<c:set property="branchManagement" target="${user}" value=""></c:set> --%>
										</c:if>
										<label><fmt:message key="Common.form.HierarchyName" /></label>
										<form:select path="branchManagement.hierarchyControl.id"
											cssClass="form-control" id="hierarchyId">
											<form:option value="-1" label="- Hierarchy -" />
											<form:options items="${hierarchyList}" itemValue="id"
												itemLabel="name" />
										</form:select>
										<div class="has-error">
											<form:errors path="branchManagement.hierarchyControl.id"
												cssClass="help-inline" />
										</div>
									</div>
									<div class="form-group">
										<form:input placeholder="Enter entity name"
											path="branchManagement.branchName" id="branchManagementName"
											cssClass="form-control" />
										<form:hidden path="branchManagement.id"
											id="branchManagementId" />
									</div>
								</sec:authorize>
								<%-- 								<c:choose> --%>
								<%-- 									<c:when --%>
								<%-- 										test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
								<%-- 										<jsp:include page="../UtilityFiles/OrgToBranchManagement.jsp"></jsp:include> --%>
								<%-- 									</c:when> --%>
								<%-- 									<c:otherwise> --%>
								<!-- 										<div class="form-group"> -->
								<%-- 											<form:hidden --%>
								<%-- 												path="branchManagement.hierarchyControl.orgManagement.orgType.id" --%>
								<%-- 												id="orgManagementId" /> --%>
								<!-- 										</div> -->
								<!-- 										<div class="form-group"> -->
								<%-- 											<form:hidden --%>
								<%-- 												path="branchManagement.hierarchyControl.orgManagement.id" --%>
								<%-- 												id="orgManagementId" /> --%>
								<!-- 										</div> -->
								<!-- 										<div class="form-group"> -->
								<%-- 											<c:if test="${initialFilter}"> --%>
								<%-- 																								<c:set property="branchManagement" target="${user}" value=""></c:set> --%>
								<%-- 											</c:if> --%>
								<%-- 											<label><fmt:message key="Common.form.HierarchyName" /></label> --%>
								<%-- 											<form:select path="branchManagement.hierarchyControl.id" --%>
								<%-- 												cssClass="form-control" id="hierarchyId"> --%>
								<%-- 												<form:option value="-1" label="- Hierarchy -" /> --%>
								<%-- 												<form:options items="${hierarchyList}" itemValue="id" --%>
								<%-- 													itemLabel="name" /> --%>
								<%-- 											</form:select> --%>
								<!-- 											<div class="has-error"> -->
								<%-- 												<form:errors path="branchManagement.hierarchyControl.id" --%>
								<%-- 													cssClass="help-inline" /> --%>
								<!-- 											</div> -->
								<!-- 										</div> -->
								<!-- 										<div class="form-group"> -->
								<%-- 											<form:input placeholder="Enter entity name" --%>
								<%-- 												path="branchManagement.branchName" id="branchManagementName" --%>
								<%-- 												cssClass="form-control" /> --%>
								<%-- 											<form:hidden path="branchManagement.id" --%>
								<%-- 												id="branchManagementId" /> --%>
								<!-- 										</div> -->
								<%-- 									</c:otherwise> --%>
								<%-- 								</c:choose> --%>

								<%-- <div class="form-group">
									<label><fmt:message key="Common.form.EntityType" /></label>
									<form:select path="entityType" cssClass="form-control"
										id="entityTypeId">
										<form:option value="-1" label="- All -" />
										<form:option value="0" label="Branch" />
										<form:option value="1" label="Chest" />
									</form:select>
									<div class="has-error">
										<form:errors path="entityType" cssClass="help-inline" />
									</div>
								</div> --%>


								<div class="form-group button-group-set m20">
									<div class="col-sm-12">
										<button type="submit" class="btn btn-primary btn-sm">
											Search</button>
									</div>
								</div>
							</form:form>
						</div>
					</section>
				</div>
				<sec:authorize access="hasRole('can_usermanagement_create')">
					<%-- 				<c:if --%>
					<%-- 					test="${ct:isAllowed('can_usermanagement_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./usermanagement-new'><i
							class="fa fa-plus"></i> New</a>
					</div>
					<%-- 				</c:if> --%>
				</sec:authorize>
			</div>

			<sec:authorize access="hasRole('can_usermanagement_read')">
				<%-- 				<c:if --%>
				<%-- 					test="${ct:isAllowed('can_usermanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="demo-container">
						<div>

							<table id="userListTable"
								class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">
								<thead>
									<tr class="info">
										<th class="text-center" style="max-width: 40px;"><i
											class="fa fa-cogs"></i></th>

										<sec:authorize access="hasRole('can_view_everything')">
											<%-- 											<c:if --%>
											<%-- 												test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th data-hide="phone"><fmt:message
													key="Common.form.OrganizationName" /></th>
											<%-- 											</c:if> --%>
										</sec:authorize>
										<th><fmt:message key="UserManagement.list.Name" /></th>
										<th data-hide="phone, tablet"><fmt:message
												key="UserManagement.list.BranchName" /></th>
										<th><fmt:message key="UserManagement.list.ContactNo" /></th>
										<th><fmt:message key="UserManagement.list.Email" /></th>
										<th data-hide="phone"><fmt:message
												key="UserManagement.list.UserId" /></th>
										<th data-hide="phone, tablet"><fmt:message
												key="UserManagement.list.BranchLocation" /></th>

										<sec:authorize
											access="hasRole('can_usermanagement_assignrole')">
											<%-- 											<c:if --%>
											<%-- 												test="${ct:isAllowed('can_usermanagement_assignrole',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th data-hide="phone, tablet"><fmt:message
													key="UserManagement.list.Roles" /></th>
											<%-- 											</c:if> --%>
										</sec:authorize>

										<sec:authorize
											access="hasRole('can_usermanagement_read')">
											<%-- 											<c:if --%>
											<%-- 												test="${ct:isAllowed('can_usermanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center" style="max-width: 40px;"><i
												class="fa fa-eye"></i></th>
											<%-- 											</c:if> --%>
										</sec:authorize>
										<sec:authorize
											access="hasRole('can_usermanagement_update')">
											<%-- 											<c:if --%>
											<%-- 												test="${ct:isAllowed('can_usermanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center" style="max-width: 40px;"><i
												class="fa fa-edit"></i></th>
											<%-- 											</c:if> --%>
										</sec:authorize>
										<sec:authorize
											access="hasRole('can_usermanagement_delete')">
											<%-- 											<c:if --%>
											<%-- 												test="${ct:isAllowed('can_usermanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center" style="max-width: 40px;"><i
												class="fa fa-trash-o"></i></th>
											<%-- 											</c:if> --%>
										</sec:authorize>
									</tr>
								</thead>
								<%--<c:if test="${!can_view_everything}">
								 <tbody>
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:set var="count" value="1"></c:set>
									<c:set var="count" value="${count+1}" scope="request"/>
									<jsp:include page="userNode.jsp"/>
								</tbody>
								</c:if>
								
								<c:if test="${can_view_everything}"> --%>
								<tbody>
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${usersList}" var="user" varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 50px;">${counter.index+1}</td>

											<sec:authorize access="hasRole('can_view_everything')">
												<%-- 												<c:if --%>
												<%-- 													test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td><small>${user.branchManagement.orgManagement.name}</small></td>
												<%-- 												</c:if> --%>
											</sec:authorize>
											<td><small>${user.firstName} ${user.lastName}</small>
											<td><small>${user.branchManagement.branchName}</small></td>
											<td><small>${user.contactNo}</small></td>
											<td><small>${user.email}</small></td>
											<td><small>${user.userId}</small></td>
											<td><small>${user.branchManagement.branchLocation}</small></td>

											<sec:authorize
												access="hasRole('can_usermanagement_assignrole')">
												<%-- 												<c:if --%>
												<%-- 													test="${ct:isAllowed('can_usermanagement_assignrole',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td><small> <c:if
															test="${userObj.id != user.id}">
															<a style="color: red"
																href="<c:url value='/editRoleAssociation-${user.id}'/>"
																class="p0"> ${user.userRoles.size() eq 0 ? 'Associate Roles' : 'Roles('.concat(user.userRoles.size()).concat(")")}
															</a>
														</c:if>
												</small></td>
												<%-- 												</c:if> --%>
											</sec:authorize>
											<sec:authorize
												access="hasRole('can_usermanagement_read')">
												<%-- 												<c:if --%>
												<%-- 													test="${ct:isAllowed('can_usermanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 40px;" class="text-center"><a
													href="<c:url value='/usermanagement-${user.id}-view' />"
													title="View Details"><i class="fa fa-eye"></i></a></td>
												<%-- 												</c:if> --%>
											</sec:authorize>
											<sec:authorize
												access="hasRole('can_usermanagement_update')">
												<%-- 												<c:if --%>
												<%-- 													test="${ct:isAllowed('can_usermanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 40px;" class="text-center"><c:if
														test="${userObj.id != user.id}">
														<a
															href="<c:url value='/usermanagement-${user.id}-edit' />"
															title="Update"><i class="fa fa-edit"></i></a>
													</c:if></td>
												<%-- 												</c:if> --%>
											</sec:authorize>
											<sec:authorize
												access="hasRole('can_usermanagement_delete')">
												<%-- 												<c:if --%>
												<%-- 													test="${ct:isAllowed('can_usermanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 40px;" class="text-center"><c:if
														test="${userObj.id != user.id}">
														<a
															href="javascript:onDeleteConfirm('<c:url value="/usermanagement-${user.id}-delete" />')"
															title="Delete"><i class="fa fa-trash-o"></i></a>
													</c:if></td>
												<%-- 												</c:if> --%>
											</sec:authorize>
										</tr>
									</c:forEach>
								</tbody>
								<%-- </c:if>	 --%>
							</table>
						</div>
					</div>
				</div>
				<%-- 				</c:if> --%>
			</sec:authorize>
		</div>
	</div>
</section>

<script>
	var baseUrl = "${pageContext.request.contextPath}/getBranchManagementForHierarchyAutoComplete";
	$("#hierarchyId").ready(
			function() {
				if ($("#hierarchyId").val() != ""
						|| $("#hierarchyId").val() != "-1") {
					//$("#branchManagementName").val("");
					//$("#branchManagementId").val("");
					//	$('#designationId').empty();
					//	getDesignationsByHierarchyId();

					$('#branchManagementName').autocomplete(
							{
								autoSelectFirst : true,
								serviceUrl : baseUrl + addHierarchyId(),
								paramName : "tagName",
								delimiter : ",",
								onSelect : function(suggestion) {
									$("#branchManagementId").val(
											suggestion.data);
									//getDesignationsByHierarchyId();
									if (suggestion.branchType == 1) {
										$('#approverDiv').show(true);
										console.log("true");
									} else {
										$('#approverDiv').hide(true);
										console.log("false");
									}
									return false;
								},
								transformResult : function(response) {
									return {
										//must convert json to javascript object before process
										suggestions : $.map($
												.parseJSON(response), function(
												item) {
											return {
												value : item.branchName,
												data : item.id,
												branchType : item.branchType
											};
										})
									};
								}
							});
				}

			});
	$("#hierarchyId").change(function() {

		$("#branchManagementName").val("");
		$("#branchManagementId").val("");
		$('#designationId').empty();
		getDesignationsByHierarchyId();

		$('#branchManagementName').autocomplete({
			autoSelectFirst : true,
			serviceUrl : baseUrl + addHierarchyId(),
			paramName : "tagName",
			delimiter : ",",
			onSelect : function(suggestion) {
				$("#branchManagementId").val(suggestion.data);
				if (suggestion.branchType == 1) {
					$('#approverDiv').show(true);
					console.log("true");
				} else {
					$('#approverDiv').show(false);
					console.log("false");
				}
				return false;
			},
			transformResult : function(response) {
				return {
					//must convert json to javascript object before process
					suggestions : $.map($.parseJSON(response), function(item) {
						return {
							value : item.branchName,
							data : item.id,
							branchType : item.branchType
						};
					})
				};
			}
		});
	});

	function addHierarchyId() {
		return '?hierarchyId=' + $('#hierarchyId').val();
	}

	function getDesignationsByHierarchyId() {

		var id = $('#hierarchyId').val();

		$.ajax({
			url : "getDesignationsByHierarchyId",
			dataType : 'json',
			data : {
				hId : id,
			},
			type : "post",
		}).done(
				function(result) {
					$('#designationId').empty();
					$('#designationId').append(
							$("<option></option>").attr("value", "-1").text(
									"-Select Designation-"));
					result.forEach(function(res) {
						console.log(res.title);
						$('#designationId').append(
								$("<option></option>").attr("value", res.id)
										.text(res.title));
					});
				}).fail(function(e) {
			console.log(e);
		});
	}
</script>



<!-- Tapan sirs work -->

<script type="text/javascript">
	$(document).ready(function() {
		$('#userListTable').DataTable({
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

