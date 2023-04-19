<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="/WEB-INF/tags/spring-form"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/jstl-core"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/jstl-fmt"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tags/security"%>

<style>
/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	position: relative;
	background-color: #fefefe;
	margin: auto;
	padding: 0;
	border: 1px solid #888;
	width: 80%;
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
	-webkit-animation-name: animatetop;
	-webkit-animation-duration: 0.4s;
	animation-name: animatetop;
	animation-duration: 0.4s
}

/* Add Animation */
@
-webkit-keyframes animatetop {
	from {top: -300px;
	opacity: 0
}

to {
	top: 0;
	opacity: 1
}

}
@
keyframes animatetop {
	from {top: -300px;
	opacity: 0
}

to {
	top: 0;
	opacity: 1
}

}

/* The Close Button */
.close {
	color: white;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

.modal-header {
	padding: 2px 16px;
	background-color: #5cb85c;
	color: white;
}

.modal-body {
	padding: 2px 16px;
}

.modal-footer {
	padding: 2px 16px;
	background-color: #5cb85c;
	color: white;
}
</style>

<script type="text/javascript">
	var sessionUserId = '${sessionScope['
	scopedTarget.mySession
	'].user.id}';
	console.log("sessionUserId : " + sessionuserId);
</script>

<section class="content-header">
	<h1>Request</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Request</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Request</h3>
				</div>

				<sec:authorize access="hasRole('can_be_maker')">
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./bid-request'><i
							class="fa fa-plus"></i>Request</a>
							
						<a class="btn btn-success" href='./bid-dashboard'><i
							class="fa"></i>Dashboard</a>
					</div>
				</sec:authorize>
				
				
			</div>
			<%-- 			<sec:authorize access="hasRole('can_bid_read')"> --%>
			<%-- 			<c:if --%>
			<%-- 				test="${ct:isAllowed('can_orgmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<div class="box-body">
				<div class="demo-container">
					<table id="orgmanagementlisttable"
						class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">
						<thead>
							<tr class="info">
								<!-- 								<th data-toggle="true" class="text-center" style="max-width: 20px;"></th> -->
								<!-- 								<th data-type="number">#</th> -->
								<th class="text-center" style="max-width: 40px;"><i
									class="fa fa-cogs"></i></th>
								<th><fmt:message key="Common.form.BranchName" /></th>
								<th><fmt:message key="Bid.list.RequestPlaceDate" /></th>
								<th><fmt:message key="Bid.list.RequestType" /></th>
								<th><fmt:message key="Bid.list.Amount" /></th>
								<th><fmt:message key="Bid.list.BidAcceptBy" /></th>
								<th><fmt:message key="Bid.list.Status" /></th>
								<th><fmt:message key="Bid.list.Action" /></th>



								<%-- 								<th><fmt:message key="Bid.list.Sent" /></th> --%>
								<%-- 								<th><fmt:message key="Bid.list.Receive" /></th> --%>
								<%-- 								<th><fmt:message key="Bid.list.RequestedBranches" /></th> --%>
								<%-- 								<th><fmt:message key="Bid.list.Denomination" /></th> --%>



							</tr>
						</thead>
						<tbody>
							<fmt:message key="yyyy.MM.dd" var="pattern" />
							<fmt:message key="yyyy-MM-dd" var="pattern1" />
							<c:forEach items="${bidRequestList}" var="bid"
								varStatus="counter">
								<tr>
									<!-- 								<td></td> -->
									<td class="text-center" style="max-width: 40px;">${counter.index+1}</td>
									<td><small>${bid.requestPlacedById.branchName}</small></td>
									<td><small><fmt:formatDate
												value="${bid.requestPlaceDate}" pattern="${pattern}" /></small></td>
									<td><small>${bid.requestType}</small></td>
									<td><small>${bid.amount}</small></td>
									<td><small>${bid.requestAcceptById.branchName}</small></td>
									<td><small>${bid.requestStatus}</small></td>
									<td><c:if test="${myuser.branchManagement.isgroup eq 0 }">
											<c:if test="${bid.requestStatus eq 'PENDING'}">
												<sec:authorize access="hasRole('can_be_approver')">
													<c:set var="contains" value="false" />
													<c:forEach var="item" items="${bid.bidRequestApprovals}">
														<c:if test="${item.approvarId.id eq myuser.id}">
															<c:set var="contains" value="true" />
														</c:if>
													</c:forEach>
													<c:choose>
														<c:when test="${contains}">
															<a href="#"><small>
																	<button class="approve-button" bidId="${bid.id}">Approved</button>
															</small></a>
														</c:when>
														<c:otherwise>
															<a href="./bid-approval-${bid.id }"><small>
																	<button class="approve-button" bidId="${bid.id}">Approve</button>
															</small></a>
														</c:otherwise>
													</c:choose>
													<%-- <a href="./bid-approval-${bid.id }"><small>
																<button class="approve-button" bidId="${bid.id}">Approve</button>
														</small></a> --%>
												</sec:authorize>
											</c:if>
										</c:if></td>
									<%-- 									<td><small>${bid.sent}</small></td> --%>
									<%-- 									<td><small>${bid.receive}</small></td> --%>
									<%-- 	<td><small>${bid.bidRequestedTo.size()}</small>

										<div>
											<!-- Trigger/Open The Modal -->
											<button id="group-${bid.id}" class="open-group-branches">Group
												Branches</button>

											<!-- The Modal -->
											<div id="myModal-${bid.id}" class="modal">
												<!-- Modal content -->
												<div class="modal-content">
													<div class="modal-header">
														<span class="close-modal">&times;</span>
														<h2>Modal Header</h2>
													</div>
													<div class="modal-body">
														<c:forEach items="${bid.bidRequestedTo}" var="branchList"
															varStatus="branchCounter">
															<table>
																<tr>
																	<td>${branchCounter.index+1}</td>
																	<td>${branchList.requestToBranchId.branchName}</td>
																</tr>
															</table>
														</c:forEach>
													</div>
													<div class="modal-footer">
														<h3>Modal Footer</h3>
													</div>
												</div>
											</div>
										</div>
										
										
										</td> --%>
									<%-- 									<td><small>${bid.dn2000},${bid.dn500},${bid.dn100},${bid.dn50},${bid.dn20},${bid.dn10},${bid.dn5},${bid.dn2},${bid.dn1}</small></td> --%>
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
			<%-- 			</sec:authorize> --%>
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
