<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- <script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script> -->

<script src="./static/js/jquery-ui.js"></script>
<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">

<script>
	$(function() {
		$('#fromDate').datepicker(
				{
					dateFormat : 'dd-mm-yy',
					onSelect : function(selectedDate1) {

						selectedDate1 = selectedDate1.split("-");
						selectedDate1 = new Date(selectedDate1[2],
								selectedDate1[1] - 1, selectedDate1[0]);

						/*    $("#toDate").removeAttr('disabled');
						   $("#toDate").attr('disabled', 'disabled'); */

						var fromMinDate = new Date(selectedDate1.getFullYear(),
								selectedDate1.getMonth(), selectedDate1
										.getDate() + 1);
						$("#toDate").datepicker("option", "minDate",
								fromMinDate);
						$('#toDate').val("");

						$("#toDate").datepicker(
								{
									minDate : fromMinDate,
									dateFormat : 'dd-mm-yy',
									onSelect : function(selectedDate2) {

										selectedDate2 = selectedDate2
												.split("-");
										selectedDate2 = new Date(
												selectedDate2[2],
												selectedDate2[1] - 1,
												selectedDate2[0]);

									}
								});
					}
				});
	});
</script>

<section class="content-header">
	<h1>Cash Availability/EOD Report</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Branch</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">
			<div class="box-header">
				<div class="heading-title-wrapper" style="width: 1080px;">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Search Criteria</h3>
					<section class="content">
						<div class="row">
							<form:form action="branchCashAvailabilityReport" id="NewEditForm"
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
										<label><fmt:message key="Common.form.BranchName" /></label>
										<form:input placeholder="Enter entity name"
											path="branchManagement.branchName" id="branchManagementName"
											cssClass="form-control" />
										<form:hidden path="branchManagement.id"
											id="branchManagementId" />
									</div>
								</sec:authorize>
								<%-- <c:choose>
										<c:when test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">								
											<jsp:include page="../UtilityFiles/OrgToBranchManagement.jsp"></jsp:include>
										</c:when>
										<c:otherwise>
											<div class="form-group">
												<form:hidden path="branchManagement.hierarchyControl.orgManagement.orgType.id" id="orgManagementId" />
											</div>
											<div class="form-group">
												<form:hidden path="branchManagement.hierarchyControl.orgManagement.id" id="orgManagementId" />
											</div>
											<div class="form-group">
											<c:if test="${initialFilter}"> 
											</c:if>
												<label><fmt:message key="Common.form.HierarchyName" /></label>
												<form:select path="branchManagement.hierarchyControl.id" cssClass="form-control"
													id="hierarchyId">
													<form:option value="-1" label="- Hierarchy -" />
													<form:options items="${hierarchyList}" itemValue="id"
														itemLabel="name" />
												</form:select>
												<div class="has-error">
													<form:errors path="branchManagement.hierarchyControl.id" cssClass="help-inline" />
												</div>
											</div>
											
											<div class="form-group">
												<label><fmt:message key="Common.form.BranchName"/></label>
												<form:input placeholder="Enter entity name"
													path="branchManagement.branchName" id="branchManagementName"
													cssClass="form-control" />
												<form:hidden path="branchManagement.id" id="branchManagementId" />
											</div>
										</c:otherwise>
									</c:choose> --%>

								<div class="form-group">
									<label><fmt:message key="Common.form.FromDate" /></label>
									<form:input readonly="true" placeholder="From Date"
										cssClass="form-control" path="fromDate" id="fromDate" />
								</div>

								<div class="form-group">
									<label><fmt:message key="Common.form.ToDate" /></label>
									<form:input readonly="true" placeholder="To Date"
										cssClass="form-control" path="toDate" id="toDate" />
								</div>

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
			</div>
			<%-- <c:if test="${ct:isAllowed('can_usermanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<c:if test="${not empty auditTrials}">
				<div class="box-body">
					<div class="demo-container">
						<div>
							<table id="userListTable"
								class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">

								<thead>
									<tr class="info">
										<th class="text-center" style="max-width: 40px;">Sr No.</th>
										<th data-hide="phone, tablet"><fmt:message
												key="Report.list.CreatedTime" /></th>
										<th><fmt:message key="Report.list.BranchId" /></th>
										<th><fmt:message key="Report.list.Total" /></th>
										<th><fmt:message key="Report.list.ShowDenominations" /></th>
									</tr>
								</thead>

								<tbody>
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${auditTrials}" var="branch"
										varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 50px;">${counter.index+1}</td>
											<td><small><fmt:formatDate
														value="${branch.createdTime}" pattern="${pattern}" /></small></td>
											<td><small>${branch.branchManagement.branchName}</small></td>
											<td><small><fmt:formatNumber type="number"
														minFractionDigits="2" maxFractionDigits="2"
														value="${branch.total}" /></small>
											<td><small> <a href='' data-toggle="modal"
													data-target="#myModalDenomination"
													onclick="showDenominations('${branch.dn2000}','${branch.dn500}',
															 '${branch.dn100}','${branch.dn50}',
															 '${branch.dn20}','${branch.dn10}',
															 '${branch.dn5}', '${branch.dc1}',
															 '${branch.dc2}', '${branch.dc5}',
															 '${branch.dc10}','${branch.others}')">
														<i class="fa"></i> Denominations
												</a>
											</small></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</c:if>
			<%-- </c:if> --%>
		</div>
	</div>
</section>

<div class="modal fade" id="myModalDenomination" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>

			</div>
			<h4 class="modal-title" id="myModalLabel">Denominations</h4>
			<div class="modal-body">
				<div class="modal-header" id="denominationDiv">

					<!-- 				Append denomination here -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>



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

<script>
	function showDenominations(dn2000, dn500, dn100, dn50, dn20, dn10, dn5,
			dc1, dc2, dc5, dc10, others) {
		console.log("dn2000 = " + dn2000 + " | dn500 = " + dn500 + " dn100 = "
				+ dn100 + " | dn50 = " + dn50 + " | dn20 = " + dn20
				+ " | dn10 = " + dn10 + " | dn5 = " + dn5);
		console.log("dn1 = " + dc1 + " | dc2 = " + dc2 + " | dc5 = " + dc5
				+ " | dc10 = " + dc10 + " others = " + others);

		var newdiv = document.createElement('div');

		newdiv.innerHTML = "<label>2000 * " + dn2000 + "</label>";

		newdiv.innerHTML += "<label>500 * " + dn500 + "</label>";

		newdiv.innerHTML += "<label>100 * " + dn100 + "</label>";

		newdiv.innerHTML += "<label>50 * " + dn50 + "</label>";

		newdiv.innerHTML += "<label>20 * " + dn20 + "</label>";

		newdiv.innerHTML += "<label>10 * " + dn10 + "</label>";

		newdiv.innerHTML += "<label>5 * " + dn5 + "</label>";
		newdiv.innerHTML += "<br>";
		newdiv.innerHTML += "<label>1 * " + dc1 + "</label>";

		newdiv.innerHTML += "<label>2 * " + dc2 + "</label>";

		newdiv.innerHTML += "<label>5 * " + dc5 + "</label>";

		newdiv.innerHTML += "<label>10 * " + dc10 + "</label>";

		newdiv.innerHTML += "<label>others * " + others + "</label>";
		//	 document.getElementById("d1").appendChild(newdiv);
		$("#denominationDiv").html(newdiv);
	}
</script>



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
