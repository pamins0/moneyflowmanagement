<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="/WEB-INF/tags/spring-form"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/jstl-core"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/jstl-fmt"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tags/security"%>

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
					<h3 class="box-title">Request Dashboard</h3>
				</div>

				<sec:authorize access="hasRole('can_be_maker')">
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href="./bid"> <i
							class="fa fa-arrow-left"></i> Back
						</a>
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
								<th class="text-center" style="max-width: 40px;"><i
									class="fa fa-cogs"></i></th>
								<th><fmt:message key="Bid.list.BidPlacedByBranch" /></th>
								<th><fmt:message key="Bid.list.RequestPlaceDate" /></th>
								<th><fmt:message key="Bid.list.RequestType" /></th>
								<th><fmt:message key="Bid.list.Amount" /></th>
								<th><fmt:message key="Bid.list.PartialAmount" /></th>
								<th><fmt:message key="Bid.list.Action" /></th>
							</tr>
						</thead>
						<tbody>
							<fmt:message key="yyyy.MM.dd" var="pattern" />
							<fmt:message key="yyyy-MM-dd" var="pattern1" />
							<c:forEach items="${bidRequestToList}" var="bidRequestTo"
								varStatus="counter">

								<c:if test="${bidRequestTo.bidRequestId.requestType eq 'OFFLOAD'}">
									<tr style="background: red; color: white;">
								</c:if>
								<c:if test="${bidRequestTo.bidRequestId.requestType eq 'INDEND'}">
									<tr style="background: green; color: white;">
								</c:if>
								<!-- 								<td></td> -->
								<td class="text-center" style="max-width: 40px;">${counter.index+1}</td>

								<td><small>${bidRequestTo.bidRequestId.requestPlacedById.branchName}</small></td>
								<td><small><fmt:formatDate
											value="${bidRequestTo.bidRequestId.requestPlaceDate}"
											pattern="${pattern}" /></small></td>
								<td><small>${bidRequestTo.bidRequestId.requestType}</small></td>
								<td><small>${bidRequestTo.bidRequestId.amount}</small></td>
								<td><small>${bidRequestTo.acceptAmount}</small></td>
								<td>
								${bidRequestTo.requestStatus}
									<%-- 										<sec:authorize access="hasRole('can_be_approver')"> --%>
									<c:if test="${bidRequestTo.requestStatus eq null || bidRequestTo.requestStatus eq '' ||bidRequestTo.requestStatus eq 'PENDING'}">
										<a href="./bid-accept-${bidRequestTo.bidRequestId.id }"><small>
												<button class="approve-button" bidId="${bidRequestTo.bidRequestId.id}">Accept
													Request</button>
										</small></a>										
									</c:if>
									<c:if
										test="${bidRequestTo.requestStatus eq 'PENDING TO APPROVE'}">
										<a href="./bid-accept-approve-${bidRequestTo.id }"><small>
												<button class="approve-button" bidId="${bidRequestTo.bidRequestId.id}">Approve</button>
										</small></a>										
									</c:if>
									 <%-- 										</sec:authorize> --%>
								</td>

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
