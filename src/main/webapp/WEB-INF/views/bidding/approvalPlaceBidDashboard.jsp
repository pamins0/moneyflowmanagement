<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<link
	href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css"
	type="text/css">
<section class="content-header">
	<h1>Approval Bidding Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Approval</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main" style="">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Approval List</h3>
				</div>
			</div>
			<sec:authorize access="hasRole('can_be_approver')">
				<%-- 			<c:if --%>
				<%-- 				test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="demo-container">
						<div>
							<span class="has-error text-center">${alreadyApprovedCountDone}</span>
							<table id="placedBidListForApproval"
								class="table table-bordered table-striped display"
								style="max-height: 500px; overflow-y: scroll;">
								<thead>
									<tr class="info">
										<th class="text-center" style="max-width: 20px;"><i
											class="fa fa-cogs"></i></th>
										<th><fmt:message key="BidApproval.list.DateAndTime" /></th>
										<th><fmt:message key="BidApproval.list.BankDetailsOfIFSC" /></th>
										<th><fmt:message key="BidApproval.list.BidPlacedBy" /></th>
										<th><fmt:message key="BidApproval.list.BidAmount" /></th>
										<th><fmt:message key="BidApproval.list.BidType" /></th>
										<th><fmt:message key="BidApproval.list.BidStatus" /></th>
										<th><fmt:message key="BidApproval.list.ModelType" /></th>
										<th><fmt:message key="BidApproval.list.CommissionRate" /></th>
										<th><fmt:message key="BidApproval.list.Distance" /></th>
										<th><fmt:message key="BidApproval.list.Flag" /></th>
										<th><fmt:message key="BidApproval.list.Action" /></th>
									</tr>
								</thead>
								<tbody>
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${placedBidListForApproval}" var="placeBid"
										varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 5px;">${counter.index+1}</td>
											<td><small><fmt:formatDate
														value="${placeBid.bid_Created_Time}" pattern="${pattern}" /></small></td>
											<td><small>${placeBid.branchManagement.branchIfscCode}</small></td>
											<td><small>${placeBid.bidCreatedBy.firstName}</small></td>
											<td><small>${placeBid.bid_Amount}</small></td>
											<c:choose>
												<c:when test="${placeBid.bid_Type_Flag ==1 }">
													<td><small>To Remit</small></td>
												</c:when>
												<c:otherwise>
													<td><small>To Recieve</small></td>
												</c:otherwise>
											</c:choose>
											<td><small>${placeBid.PB_Status.status}</small></td>
											<td><small>${placeBid.modelType.model_Type}</small></td>
											<td><small>${placeBid.commission_Rate}</small></td>
											<td><small>${placeBid.vicinity}</small></td>
											<c:choose>
												<c:when test="${placeBid.bank_Override_Flag ==1 }">
													<td><small>Intra Bank</small></td>
												</c:when>
												<c:otherwise>
													<td><small>Inter Bank</small></td>
												</c:otherwise>
											</c:choose>
											<td><small><a
													href="./updateBidPlacedApproval-1-${placeBid.id}">Accept</a>
													<a href="./updateBidPlacedApproval-0-${placeBid.id}">Reject</a></small></td>
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

<!-- Tapan sirs work -->

<script type="text/javascript">
	$(document).ready(function() {
		$('#designationListTable').DataTable({
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


