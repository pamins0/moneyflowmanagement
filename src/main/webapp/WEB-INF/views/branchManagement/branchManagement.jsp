<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:choose>
	<c:when test="${groupmanager}">
		<section class="content-header">
			<h1>Group Management</h1>
			<ol class="breadcrumb">
				<li><a><i class="fa fa-dashboard"></i> Home</a></li>
				<li class="active">Group</li>
			</ol>
		</section>
	</c:when>
	<c:otherwise>
		<section class="content-header">
			<h1>Entity Management</h1>
			<ol class="breadcrumb">
				<li><a><i class="fa fa-dashboard"></i> Home</a></li>
				<li class="active">Entity</li>
			</ol>
		</section>
	</c:otherwise>
</c:choose>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">

			<c:choose>
				<c:when test="${groupmanager}">
					<div class="box-header">
						<div class="heading-title-wrapper">
							<i class="fa fa-tasks"></i>
							<h3 class="box-title">Group List</h3>
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
				</c:when>
				<c:otherwise>
					<div class="box-header">
						<div class="heading-title-wrapper">
							<i class="fa fa-tasks"></i>
							<h3 class="box-title">Entity List</h3>
							<span style="bottom: 176px; !important"
								class="has-error text-center">${EodAlreadyDone}</span>
							<form:form action="filterBranchHierarchyByOrganization"
								id="NewEditForm" method="POST" modelAttribute="branch"
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
									<div class="form-group">
										<c:if test="${initialFilter}">
											<%-- <c:set property="hierarchyControl" target="${branch}"
													value=""></c:set> --%>
										</c:if>
										<label><fmt:message key="Common.form.HierarchyName" /></label>
										<form:select path="hierarchyControl.id"
											cssClass="form-control" id="hierarchyControlId">
											<form:option value="-1" label="- Hierarchy -" />
											<form:options items="${hierarchyList}" itemValue="id"
												itemLabel="name" />
										</form:select>
										<div class="has-error">
											<form:errors path="hierarchyControl.id"
												cssClass="help-inline" />
										</div>
									</div>
									<div class="form-group button-group-set">
										<div class="col-sm-12">
											<button type="submit" class="btn btn-primary btn-sm">
												<i class="fa fa-floppy-o"></i> Search
											</button>
										</div>
									</div>
								</sec:authorize>

								<%--<c:choose>
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
										<div class="form-group">
											<c:if test="${initialFilter}">
												<c:set property="hierarchyControl" target="${branch}"
													value=""></c:set>
											</c:if>
											<label><fmt:message key="Common.form.HierarchyName" /></label>
											<form:select path="hierarchyControl.id"
												cssClass="form-control" id="hierarchyControlId">
												<form:option value="-1" label="- Hierarchy -" />
												<form:options items="${hierarchyList}" itemValue="id"
													itemLabel="name" />
											</form:select>
											<div class="has-error">
												<form:errors path="hierarchyControl.id"
													cssClass="help-inline" />
											</div>
										</div>
										<div class="form-group button-group-set">
											<div class="col-sm-12">
												<button type="submit" class="btn btn-primary btn-sm">
													<i class="fa fa-floppy-o"></i> Search
												</button>
											</div>
										</div>
									</c:otherwise> </c:choose>--%>

							</form:form>


						</div>
						<sec:authorize access="hasRole('can_branchmanagement_create')">
							<%-- 						 <c:if test="${ct:isAllowed('can_branchmanagement_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
							<div class="btn-group custom_group" role="group"
								aria-label="Basic example">
								<a class="btn btn-success" href='./branchmanagement-new'><i
									class="fa fa-plus"></i> New</a>
							</div>
							<%-- 						</c:if>  --%>
						</sec:authorize>
					</div>
				</c:otherwise>
			</c:choose>

			<sec:authorize access="hasRole('can_branchmanagement_read')">
				<%-- 			<c:if --%>
				<%-- 				test="${ct:isAllowed('can_branchmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="box-body table-responsive">
						<%-- <div class="form-group col-md-4">
							<select id="searchInputSelect" class="form-control">
								<option value="-1" selected>--All Hierarchy--</option>
								<c:forEach items="${hierarchyList}" var="hier">
									<option value="${hier.hierarchyLevel}">${hier.name}</option>
								</c:forEach>
							</select>
						</div> --%>

						<div class="table-responsive">
							<table id="branchListTable"
								class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">
								<thead>
									<tr>
										<th class="text-center" style="max-width: 40px;"><i
											class="fa fa-cogs"></i></th>
										<sec:authorize access="hasRole('can_view_everything')">
											<%-- 										<c:if test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th><fmt:message key="OrgManagement.list.OrgName" /></th>
											<%-- 										</c:if>	 --%>
										</sec:authorize>
										<th><fmt:message key="hierarchyManagement.list.Name" />
										<th><fmt:message key="BranchManagement.list.BranchName" /></th>
										<th><fmt:message key="BranchManagement.list.BranchCode" /></th>
										<th><fmt:message key="BranchManagement.list.Abbreviation" /></th>
										<th><fmt:message
												key="BranchManagement.list.BranchCashLimit" /></th>
										<th><fmt:message
												key="BranchManagement.list.AddClosedGroups" /></th>
										<th><fmt:message
												key="BranchManagement.list.AddCorrespondent" /></th>
										<th style="max-width: 80px;"><fmt:message
												key="BranchManagement.list.AddUser" /></th>

										<%-- 									<c:if --%
										<%-- 										 test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
										<c:if
											test="${(userBranch.id == branch.id) && (branch.isgroup == 0) && (branch.hierarchyControl.hierarchyType == 0)}">
											<th style="max-width: 80px;"><fmt:message
													key="BranchManagement.list.EOD" /></th>
										</c:if>
										<%-- 									</c:if> --%>
										<%-- <th><fmt:message key="BranchManagement.list.AddContact" /></th>
									<th><fmt:message key="BranchManagement.list.AddAccount" /></th> --%>
										<sec:authorize access="hasRole('can_branchmanagement_read')">
											<%-- 										<c:if test="${ct:isAllowed('can_branchmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center" style="max-width: 40px;"><i
												class="fa fa-eye btn btn-xs "></i></th>
											<%-- 										</c:if> --%>
										</sec:authorize>
										<sec:authorize access="hasRole('can_branchmanagement_update')">
											<%-- 										<c:if test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center" style="max-width: 40px;"><i
												class="fa fa-edit btn btn-xs "></i></th>
											<%-- 										</c:if> --%>
										</sec:authorize>
										<sec:authorize access="hasRole('can_branchmanagement_delete')">
											<%-- 										<c:if test="${ct:isAllowed('can_branchmanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
											<th class="text-center" style="max-width: 40px;"><i
												class="fa fa-trash-o btn btn-xs"></i></th>
											<%-- 										</c:if> --%>
										</sec:authorize>
									</tr>
								</thead>
								<tbody id="fbody">
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${branches}" var="branch" varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 70px;">${counter.index+1}</td>
											<sec:authorize access="hasRole('can_view_everything')">
												<%-- 											<c:if test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td><small>${branch.orgManagement.name}</small></td>
												<%-- 											</c:if>	 --%>
											</sec:authorize>
											<td><small>${branch.hierarchyControl.name}</small></td>
											<td><small>${branch.branchName}</small></td>
											<td><small>${branch.branchCode}</small></td>
											<td><small>${branch.abbreviation}</small></td>
											<td><small><fmt:formatNumber type="number"
														minFractionDigits="2" maxFractionDigits="2"
														value="${branch.branchCashlimit}" /></small></td>

											<td><small> <c:choose>
														<c:when
															test="${userBranch.hierarchyControl.hierarchyLevel eq 1}">
															<c:if test="${branch.isgroup == 0}">
																<span class=""><a
																	href="<c:url value='/viewclosedgroupbranchmanagement-${branch.id}-branch'/>"
																	data-toggle="tooltip" data-html="true"
																	data-placement="top" title="View Closed Group Branches"><i
																		class="fa fa-eye" aria-hidden="true"></i> </a></span>

															 <a class="btn btn-success"
																	style="text-decoration: none;"><i 
																	class="fa"></i>
																	${branch.getBranchClosedGroups().size()}
																	</a>
															</c:if>
														</c:when>
														<c:otherwise>
														
														</c:otherwise>
													</c:choose>
											</small></td>

											<td><small><span class=""><a
													class="btn btn-success"
													href='./associatecorrespondentbranch-${branch.id}'><i
														class="fa fa-plus"></i> ${branch.getParentCorrespondentBranch().size()}</a></small></td>

											<td><small><span class=""><a
														href="<c:url value='/branchusermanagement-${branch.id}-branch'/>"
														data-toggle="tooltip" data-html="true"
														data-placement="top" title="View Users"><i
															class="fa fa-eye" aria-hidden="true"></i> </a></span> <a
													class="btn btn-success"
													href='./branchuser-associate-${branch.id}'><i
														class="fa fa-plus"></i> ${branch.getUsers().size()}</a></small></td>

											<c:if
												test="${(userBranch.id == branch.id) && (branch.isgroup == 0) && (branch.hierarchyControl.hierarchyType == 0)}">

												<td><small> <sec:authorize
															access="hasRole('can_be_maker')">
															<%-- 														<c:if test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
															<c:choose>
																<c:when test="${editEOD}">
																	<form:form action='editbrancheodposition' method='POST'
																		modelAttribute="branch">
																		<form:hidden path='id' value='${branch.id}' />
																		<button type='submit' title="Edit EOD" name='EOD'
																			value='EOD' class="btn eod btn-primary">Edit</button>
																	</form:form>
																</c:when>
																<c:otherwise>

																</c:otherwise>
															</c:choose>
															<form:form action='savebrancheodposition' method='POST'
																modelAttribute="branch">
																<form:hidden path='id' value='${branch.id}' />
																<button type='submit' title="Fill EOD" name='EOD'
																	value='EOD' class="btn eod btn-primary">EOD</button>
															</form:form>
															<%-- 														</c:if> --%>
														</sec:authorize> <sec:authorize access="hasRole('can_be_approver')">
															<%-- 														<c:if test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
															<c:if test="${approveEOD}">
																<form:form action='approvebrancheodposition'
																	method='POST' modelAttribute="branch">
																	<form:hidden path='id' value='${branch.id}' />
																	<button title="Approve EOD" type='submit' name='EOD'
																		value='EOD' class="btn eod btn-primary">Approve</button>
																</form:form>
															</c:if>
															<%-- 														</c:if> --%>
														</sec:authorize>
												</small></td>
											</c:if>


											<sec:authorize access="hasRole('can_branchmanagement_read')">
												<%-- 											<c:if test="${ct:isAllowed('can_branchmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 15px;" class="text-center">
													<%-- 												<c:if test="${userBranch.id != branch.id}"> --%>
													<a
													href="<c:url value='/branchmanagement-${branch.id}-view' />"
													title="View Details"><i class="fa fa-eye"></i></a> <%-- 												</c:if> --%>
												</td>
												<%-- 											</c:if> --%>
											</sec:authorize>

											<sec:authorize
												access="hasRole('can_branchmanagement_update')">
												<%-- 											<c:if test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 15px;" class="text-center"><c:if
														test="${userBranch.id != branch.id}">
														<c:set value="" var="branchChest" />
														<c:choose>
															<c:when test="${branch.chest==0}">
																<c:set value="branchmanagement" var="branchChest" />
															</c:when>
															<c:otherwise>
																<c:set value="currencychest" var="branchChest" />
															</c:otherwise>
														</c:choose>
														<a
															href="<c:url value='/${branchChest}-${branch.id}-edit' />"
															title="Update"><i class="fa fa-edit"></i></a>
													</c:if></td>
												<%-- 											</c:if> --%>
											</sec:authorize>
											<sec:authorize
												access="hasRole('can_branchmanagement_delete')">
												<%-- 											<c:if test="${ct:isAllowed('can_branchmanagement_delete',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
												<td style="max-width: 15px;" class="text-center"><c:if
														test="${userBranch.id != branch.id}">
														<a
															href="javascript:onDeleteConfirm('<c:url value="/branchmanagement-${branch.id}-delete" />')"
															title="Delete"><i class="fa fa-trash-o"></i></a>
													</c:if></td>
												<%-- 											</c:if> --%>
											</sec:authorize>
										</tr>
									</c:forEach>
								</tbody>
								<%-- </c:if> --%>
							</table>
						</div>
					</div>
				</div>
				<%-- 			</c:if> --%>
			</sec:authorize>
		</div>
	</div>
</section>

<!-- For filtertion of data on the basis of hierarchy selection -->

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



