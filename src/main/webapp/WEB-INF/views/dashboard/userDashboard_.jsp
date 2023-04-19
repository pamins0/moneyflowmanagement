<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>

<section class="content-header">
	<h1>User Dashboard</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">User</li>
		<li class="active">Dashboard</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">

			<%-- 			<c:if --%>
			<%-- 				test="${ct:isAllowed('can_usermanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<div class="box-body">
				<div class="demo-container">
					<div>
						<span class="has-error text-center">${fromBranchNotExcessCash}</span>
						<table id="sameReportingBranchListTable"
							class="table table-bordered table-striped"
							style="max-height: 500px; overflow-y: scroll;">
							<thead>
								<tr class="info">
									<th class="text-center" style="max-width: 40px;"><i
										class="fa fa-cogs"></i></th>
									<th><fmt:message
											key="UserDashboard.list.entity.EntityName" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.EntityLimit" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.EntityMinThreshold" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.EntityMaxThreshold" /></th>
									<th><fmt:message
											key="UserDashboard.list.EntityParameterStatus.TotalCashAvailable" /></th>
									<th class="text-center" style="max-width: 40px;"><i
										class="fa fa-eye"></i></th>
									<th class="text-center" style="max-width: 40px;"><i
										class="fa fa-arrows"></i></th>
								</tr>
							</thead>
							<tbody>
								<fmt:message key="yyyy.MM.dd" var="pattern" />
								<c:forEach items="${sameBranchReportingList}" var="branch"
									varStatus="counter">
									<tr>
										<td class="text-center" style="max-width: 50px;">${counter.index+1}</td>
										<td><small>${branch.branchName}</small>
										<td><small>${branch.branchCashlimit}</small></td>
										<td><small>${branch.minThresholdAmount}</small></td>
										<td><small>${branch.maxThresholdAmount}</small></td>
										<c:set var="branch_availability_present" value="false" />
										<c:set var="branch_availability_present_total" value="" />
										<c:forEach items="${branch.branchParameterStatuses}"
											var="branchParameterStatus" varStatus="counter1">
											<c:if
												test="${branchParameterStatus.branchParameter.parameterName == 'branch_availability'}">
												<c:set var="branch_availability_present" value="true" />
												<c:set var="branch_availability_present_total"
													value="${branchParameterStatus.total}" />
												<td><small>${branchParameterStatus.total}</small></td>
											</c:if>
										</c:forEach>
										<c:if test="${branch_availability_present == 'false'}">
											<td><small></small></td>
										</c:if>
										<td style="max-width: 40px;" class="text-center"><a
											href="<c:url value='/branchmanagement-userdashboard-${branch.id}-view' />"
											target="_blank" title="View Details"><i class="fa fa-eye"></i></a></td>
										<c:choose>
											<c:when test="${requireCash}">
												<td><small> <c:if
															test="${userBranch.id != branch.id}">
															<c:choose>
																<c:when
																	test="${branch.maxThresholdAmount lt branch_availability_present_total}">
																	<a class="btn btn-success" href='' data-toggle="modal"
																		data-target="#myModals" onclick="fun('${branch.id}');"><i
																		class="fa"></i> Request</a>
																</c:when>
																<c:otherwise>

																</c:otherwise>
															</c:choose>
														</c:if>
												</small></td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

					<!-- 		----------------------NonReportingBranches------------------------        -->

					<div>
						<table id="otherNonreportingBranchesListTable"
							class="table table-bordered table-striped"
							style="max-height: 500px; overflow-y: scroll;">
							<thead>
								<tr class="info">
									<th class="text-center" style="max-width: 40px;"><i
										class="fa fa-cogs"></i></th>
									<th><fmt:message
											key="UserDashboard.list.entity.EntityName" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.EntityLimit" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.EntityMinThreshold" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.EntityMaxThreshold" /></th>
									<th><fmt:message
											key="UserDashboard.list.EntityParameterStatus.TotalCashAvailable" /></th>
									<th class="text-center" style="max-width: 40px;"><i
										class="fa fa-eye"></i></th>
									<!-- <th class="text-center" style="max-width: 40px;"><i
										class="fa fa-eye"></i></th> -->
								</tr>
							</thead>
							<tbody>
								<fmt:message key="yyyy.MM.dd" var="pattern" />
								<c:forEach items="${otherNonreportingBranches}" var="branch"
									varStatus="counter">
									<tr>
										<td class="text-center" style="max-width: 50px;">${counter.index+1}</td>
										<td><small>${branch.branchName}</small>
										<td><small>${branch.branchCashlimit}</small></td>
										<td><small>${branch.minThresholdAmount}</small></td>
										<td><small>${branch.maxThresholdAmount}</small></td>
										<c:set var="branch_availability_present" value="false" />
										<c:forEach items="${branch.branchParameterStatuses}"
											var="branchParameterStatus" varStatus="counter1">
											<c:if
												test="${branchParameterStatus.branchParameter.parameterName == 'branch_availability'}">
												<c:set var="branch_availability_present" value="true" />
												<td><small>${branchParameterStatus.total}</small></td>
											</c:if>
										</c:forEach>
										<c:if test="${branch_availability_present == 'false'}">
											<td><small></small></td>
										</c:if>
										<td style="max-width: 40px;" class="text-center"><a
											href="<c:url value='/branchmanagement-userdashboard-${branch.id}-view' />"
											target="_blank" title="View Details"><i class="fa fa-eye"></i></a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<%-- 			</c:if> --%>
		</div>
	</div>
</section>


<!-- Modal Start -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header" id="d1">

				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Request</h4>

			</div>
			<form action="saveBranchCashTransactionRequest" method="POST"
				class="form-inline">
				<div class="modal-body">
					<div class="box-body custom_form_body">
						<div class="form-group">
							<label><fmt:message key="Bid.form.Amount" /></label> <input
								onkeypress='return validateQty(event);' name="amount"
								id="amount" cssClass="form-control" />
							<div class="has-error">
								<%-- 								<form:errors path="amount" cssClass="help-inline" /> --%>
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message key="Bid.form.BidType" /></label> <select
								name="requestType" cssClass="form-control" id="requestType">
								<option value="-1">- Select Bid Type -</option>
								<option value="Remit">To Remit</option>
								<option value="Recieve">To Recieve</option>
							</select>
							<div class="has-error">
								<%-- 								<form:errors path="requestType" cssClass="help-inline" /> --%>
							</div>
						</div>

						<div class="form-group">
							<input type="submit" value="Submit Request" id="submitRequest" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- Modal End -->

<script type="text/javascript">
	$(document).ready(function() {
		$('#sameReportingBranchListTable').DataTable({
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

<script type="text/javascript">
	function fun(requestedBranchId) {
// 		alert("Branch id requested for : "+requestedBranchId);
		window.open("./saveBranchCashTransactionRequest-"+requestedBranchId, 'window', 'width=1000,height=500');
		
		/* alert("branchName= "+branch);
		
		<c:set var="branchs" value="${branch}" />
		var branchNames = '${branchs.branchName}';
		alert("sdhjfsdf ::: "+branchNames);
	//	alert("hsdfhshdf ::: "+${branch.branchName});
// 		alert("branchCashLimit= "+branchCashLimit);
		 var newdiv = document.createElement('div');

		 newdiv.innerHTML = "<input type='text' name='myInputs[]' value='"+branch.branchName+"'>";

		 document.getElementById("d1").appendChild(newdiv);
		 alert("call 2 ");
		 counter++; */
	}
	
	function validateQty(event) {
		var key = window.event ? event.keyCode : event.which;
		if (event.keyCode == 8 || event.keyCode == 46
		 || event.keyCode == 37 || event.keyCode == 39) {
		    return true;
		}
		else if ( key < 48 || key > 57 ) {
		    return false;
		}
		else return true;
	}
</script>

