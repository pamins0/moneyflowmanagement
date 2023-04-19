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

<script>
	/* setInterval(function changeCompany()
	 {
	 var search = {}
	 search["cikmapping.Insurance.inid"] = $("#company").val();
	 // 	alert(search["cikmapping.Insurance.inid"]);
	 // 	search["email"] = $("#email").val();

	
	 $.ajax({
	 type : "GET",
	 contentType : "application",
	 url : "${pageContext. request. contextPath}/currentplacebiddashboard_restService",
	 data : {
	
	 },
	 timeout : 100000,
	 success : function(response) {
	 console.log("SUCCESS: ", response);
	 // 			$('#userlisttable tbody').empty();
	 var table = $('#currentplacebiddashboard_restService').DataTable();
	 table.destroy();
	 // 			$('#userlisttable tbody').empty();
	 loadDataTable(response);
	 },
	 error : function(e) {
	 console.log("ERROR: ", e);
	 display(e);
	 },
	 done : function(e) {
	 console.log("DONE");
	 //	enableSearchButton(true);
	 }
	 });
	 }, 5000);

	 function loadDataTable(data) {
	 alert("hello datatable");
	 }

	 function loadDataTable1(data) {
	 var filingIds ="";
	 var table = $('#currentplacebiddashboard_restService').DataTable( {		
	 "aaData": data,
	 "aoColumns": [
	 { mData: "filingId",  
	 render: function(mData, type, row) {
	 filingIds =mData;
	 return mData ? mData : '';
	 }},
	 { mData: "cikmapping.cik",  
	 render: function(mData, type, row) {		    
	 return mData ? mData : '';
	 }},
	 { mData: "cikmapping.insurance.name",  
	 render: function(mData, type, row) {		    
	 return mData ? mData : '';
	 }},
	 { mData: "filingDate",  
	 render: function(mData, type, row) {		    
	 var dateFmt = mData;
	 var date = new Date(dateFmt);
	 var month = ''+(date.getMonth() + 1);
	 var fulldate = date.getFullYear()+ "/" +(month.length > 1 ? month : ("0" + month)) + "/" + date.getDate();
	 return mData ? fulldate: '';
	 }},
	 { mData: "accessionNo"},
	 { mData: "filingFormType.formType",  
	 render: function(mData, type, row) {		    
	 return mData ? mData : '';
	 }},
	 { mData: "status",  
	 render: function(mData, type, row) {		    
	 return mData=="10" ? "New" : 'Downloaded';
	 }},
	 { mData: "documents", 
	 render: function(mData, type, row) {		    		 
	 var a = filingIds;		    		
	 var curl="<a href='<c:url value='/display-documents-"+a+"' />'>Documents("+mData.length+")</a>";		    		
	 return mData ? curl : '';
	 }},
	 ],
	 "paging":true
	 });
	 }


	 function display(data) {
	 var json = "<h4>Ajax Response</h4><pre>"
	 + JSON.stringify(data, null, 4) + "</pre>";
	 //	$('#feedback').html(json);
	 alert(json);
	 }  */

	function dateConverter(timestamp) {
		var timestamp = new Date(timestamp).getTime();
		var todate = new Date(timestamp).getDate();
		var tomonth = new Date(timestamp).getMonth() + 1;
		var toyear = new Date(timestamp).getFullYear();
		var original_date = todate + '/' + tomonth + '/' + toyear;

		return original_date;
	}

	setInterval(
			function changeCompany() {
				$
						.ajax(
								{
									url : "${pageContext. request. contextPath}/currentplacebiddashboard_restService",
									dataType : 'json',
									data : {

									},
									type : "GET",
								})
						.done(
								function(POJOs) {
									$("#tbodyPlacedBid").empty();
									//$("#tbodyPlacedBid").append(trHtml);
									console.log("data appears : " + POJOs);
									i = 0;
									for ( var pojo in POJOs) {
										console.log("data appears:"
												+ POJOs[pojo].id);
										console
												.log("data appears for branchIfsc : "
														+ POJOs[pojo].branchManagement.branchIfscCode);
										i++;
										var bidTypeFlag = "";
										var bankOverrideFlag = "";
										var actionPerformed = "";

										var bid_created_time = dateConverter(POJOs[pojo].bid_Created_Time);

										//var bid_status = POJOs[pojo].PB_Status.status;
										var bid_status = "APPROVED";

										var sessionUserId = '${sessionScope['
										scopedTarget.mySession
										'].user.id}';
										console.log("user session id : "
												+ sessionUserId);

										if (POJOs[pojo].bid_Type_Flag == 1) {
											bidTypeFlag = 'To Remit';
										} else {
											bidTypeFlag = 'To Recieve';
										}

										if (POJOs[pojo].bank_Override_Flag == 1) {
											bankOverrideFlag = "Intra";
										} else {
											bankOverrideFlag = "Inter";
										}

										if (POJOs[pojo].bidCreatedBy.id == sessionUserId) {
											actionPerformed = "<a href='./withdrawnBidPlacedApprovalFromPortal-"+POJOs[pojo].id+"'>Withdrawn</a>";
										} else {
											actionPerformed = "<a href='./acceptBidPlacedApprovalFromPortal-"+POJOs[pojo].id+"'>Accept</a>";
										}

										var htmlContent = '<tr><td class="text-center" style="max-width: 5px;">'
												+ i
												+ '</td>'
												+ '<td><small>'
												+ bid_created_time
												+ '</small></td>'
												+ '<td><small>'
												+ POJOs[pojo].branchManagement.branchIfscCode
												+ '</small></td>'
												+ '<td><small>'
												+ POJOs[pojo].bidCreatedBy.firstName
												+ '</small></td>'
												+ '<td><small>'
												+ POJOs[pojo].bid_Amount
												+ '</small></td>'
												+ '<td><small>'
												+ bidTypeFlag
												+ '</small></td>'
												+ '<td><small>'
												+ bid_status
												+ '</small></td>'
												+ '<td><small>'
												+ POJOs[pojo].modelType.model_Type
												+ '</small></td>'
												+ '<td><small>'
												+ POJOs[pojo].commission_Rate
														.toFixed(4)
												+ '</small></td>'
												+ '<td><small>'
												+ POJOs[pojo].vicinity
														.toFixed(4)
												+ '</small></td>'
												+ '<td><small>'
												+ bankOverrideFlag
												+ '</small></td>'
												+ '<td><small>'
												+ actionPerformed
												+ '</small></td>' + '/tr>';
										$("#tbodyPlacedBid")
												.append(htmlContent);
									}

								}).fail(function(e) {
						});
			}, 10000);
</script>


<section class="content-header">
	<h1>Current Bidding Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Current Bids</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main" style="">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Current Bid List</h3>


				</div>

			</div>
			<sec:authorize access="hasRole('can_be_approver')">
				<%-- 			<c:if --%>
				<%-- 				test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="demo-container">
						<div>
							<span class="has-error text-center">${alreadyBidAccepted}</span>
							<table id="placedBidListForAccept"
								class="table table-bordered table-striped display"
								style="max-height: 500px; overflow-y: scroll;">
								<thead>
									<fmt:message key="yyyy.MM.dd" var="pattern" />
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
								<tbody id="tbodyPlacedBid">
									<c:forEach items="${placedBidListApproved}" var="placeBid"
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
											<%-- <c:choose>
												<c:when test="${placedBid.bidCreatedBy.id == sessionScope['scopedTarget.mySession'].user.id}">
													<td><small><a
															href="./withdrawnBidPlacedApprovalFromPortal-${placeBid.id}">Withdrawn</a></small></td>
												</c:when>
												<c:otherwise>
													<td><small><a
															href="./acceptBidPlacedApprovalFromPortal-${placeBid.id}">Accept</a></small></td>
												</c:otherwise>
											</c:choose> --%>


											<c:if
												test="${placeBid.bidCreatedBy.id==sessionScope['scopedTarget.mySession'].user.id}">
												<td><small><a
														href="./withdrawnBidPlacedApprovalFromPortal-${placeBid.id}">Withdrawn</a></small></td>
											</c:if>
											<c:if
												test="${placeBid.bidCreatedBy.id!=sessionScope['scopedTarget.mySession'].user.id}">
												<td><small><a
														href="./withdrawnBidPlacedApprovalFromPortal-${placeBid.id}">Accept</a></small></td>
											</c:if>
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
		$('#placedBidListForAccept').DataTable({
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


