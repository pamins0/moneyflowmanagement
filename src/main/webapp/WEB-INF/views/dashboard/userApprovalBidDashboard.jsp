<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
small>.btn.btn-success {
	width: auto;
	overflow: hidden;
}
</style>
<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	//var table = $('#sameReportingBranchListTable').DataTable();
	changeCompany();
});

setInterval(function() {
	changeCompany();
}, 500000);


function changeCompany(){
	$.ajax({
	url : "${pageContext. request. contextPath}/placeCashRequestListAndSwapForApprove_restservice",
	dataType : 'json',
	type : "GET",
}).done(function(POJOsList) {
	$("#dashboardApprovingTableBody").empty();
	//$("#tbodyPlacedBid").append(trHtml);
	console.log("data appears : "+POJOsList);
	i=0;
	var POJOs = POJOsList.placeCashRequestsList;
	for ( var pojo in POJOs) {
		console.log("data appears:"+POJOs[pojo].id);
		console.log("data appears from branchName and id : "+POJOs[pojo].branchManagementRequestedFrom.branchName+" and id : | :"+POJOs[pojo].branchManagementRequestedFrom.id);
		console.log("data appears to branchName and id : "+POJOs[pojo].branchManagementRequestedTo.branchName+" and id : | :"+POJOs[pojo].branchManagementRequestedTo.id);
		i++;
		var bidTypeFlag ="";
		var bankOverrideFlag="";
		var actionPerformed="";
		
		var bid_status = "APPROVED";

		var sessionUserId = '${sessionScope['scopedTarget.mySession'].user.id}';
		var sessionBranchId = '${sessionScope['scopedTarget.mySession'].user.branchManagement.id}';
		console.log("user session id : "+sessionUserId);
		console.log("user session id : "+sessionBranchId);
				
	
		
			if(POJOs[pojo].requestStatus.alias == 'PLACED'){
				actionPerformed="<a id='updateActionIds"+i+"'"
				+"class='btn btn-success' href='#'"
				+"data-toggle='modal' data-target='#updateModal'"
				+"onclick=updateActionBid('updateActionIds"+i+"','"+POJOs[pojo].id+"','"+POJOs[pojo].branchManagementRequestedTo.id+"','"+POJOs[pojo].amount+"','"+POJOs[pojo].requestType+"');>"
				+"<i class='fa'></i> UPDATE </a>"
				+"<a id='approveActionIds"+i+"'"
				+"class='btn btn-success' href='#'"
				+"onclick=approveActionBid('approveActionIds"+i+"','"+POJOs[pojo].id+"','"+POJOs[pojo].branchManagementRequestedTo.id+"');>"
				+"<i class='fa'></i> APPROVE </a>"
				+"<a id='cancelActionIds"+i+"'"
				+"class='btn btn-success' href='#'"
				+"onclick=cancelActionBid('cancelActionIds"+i+"','"+POJOs[pojo].id+"','"+POJOs[pojo].branchManagementRequestedTo.id+"');>"
				+"<i class='fa'></i> CANCEL </a>";
			}else {
				actionPerformed="<a class='btn btn-success' href='#'><i"
				+"class='fa'></i>"+ POJOs[pojo].requestStatus.status +"</a>";
			}
	
		
		var htmlContent = '<tr><td class="text-center" style="max-width: 5px;">'+i+'</td>'
		+'<td><small>'+POJOs[pojo].branchManagementRequestedTo.branchName+'</small></td>'
		+'<td><small>'+(POJOs[pojo].amount).format(2)+'</small></td>'
		+'<td><small>'+POJOs[pojo].requestType+'</small></td>'
		+'<td><small><a href="" onclick="showDenominationInAjax('+"'"+POJOs[pojo].branchManagementRequestedTo.id+"'"+')" data-toggle="modal" data-target="#myModalDenomination"> <i class="fa"></i>Denominations</a></small></td>'
		+'<td><small>'+actionPerformed+'</small></td>'
		+'/tr>';
		$("#dashboardApprovingTableBody").append(htmlContent);
	}
	
// 	iterating a placeCashrequestSwap based

	$("#dashboarSwapdApprovingTableBody").empty();
	//$("#tbodyPlacedBid").append(trHtml);
	console.log("data appears : "+POJOsList);
	i=0;
	var POJOs = POJOsList.placeCashRequestSwapsList;
	for ( var pojo in POJOs) {
		console.log("data appears:"+POJOs[pojo].id);
		console.log("data appears from branchName and id : "+POJOs[pojo].branchManagementRequestedFrom.branchName+" and id : | :"+POJOs[pojo].branchManagementRequestedFrom.id);
		console.log("data appears to branchName and id : "+POJOs[pojo].branchManagementRequestedTo.branchName+" and id : | :"+POJOs[pojo].branchManagementRequestedTo.id);
		i++;
		var bidTypeFlag ="";
		var bankOverrideFlag="";
		var actionPerformed="";
		var branchName = "";
		
		var bid_status = "APPROVED";

		var sessionUserId = '${sessionScope['scopedTarget.mySession'].user.id}';
		var sessionBranchId = '${sessionScope['scopedTarget.mySession'].user.branchManagement.id}';
		console.log("user session id : "+sessionUserId);
		console.log("user session id : "+sessionBranchId);
				
		if(POJOs[pojo].branchManagementRequestedTo.id == sessionBranchId){
			branchName = POJOs[pojo].branchManagementRequestedFrom.branchName;
		}else {
			branchName = POJOs[pojo].branchManagementRequestedTo.branchName;
		}
		
			if(POJOs[pojo].requestStatus.alias == 'PLACED'){
				actionPerformed="<a id='approveActionIds"+i+"'"
				+"class='btn btn-success' href='#'"
				+"onclick=approveSwapActionBid('approveActionIds"+i+"','"+POJOs[pojo].id+"');>"
				+"<i class='fa'></i> Approve </a>"
				+"<a id='cancelActionIds"+i+"'"
				+"class='btn btn-success' href='#'"
				+"onclick=cancelSwapActionBid('cancelActionIds"+i+"','"+POJOs[pojo].id+"');>"
				+"<i class='fa'></i> CANCEL </a>";
			}else {
				actionPerformed="<a class='btn btn-success' href='#'><i"
				+"class='fa'></i>"+ POJOs[pojo].requestStatus.status +"</a>";
			}
	
		
		var htmlContent = '<tr><td class="text-center" style="max-width: 5px;">'+i+'</td>'
		+'<td><small>'+branchName+'</small></td>'
		+'<td><small>'+(POJOs[pojo].amount).format(2)+'</small></td>'
		+'<td><small>'+POJOs[pojo].requestType+'</small></td>'
		+'<td><small><a href="" onclick="showDenominationInAjax('+"'"+POJOs[pojo].branchManagementRequestedTo.id+"'"+')" data-toggle="modal"	data-target="#myModalDenomination"> <i class="fa"></i>Denominations</a></small></td>'
		+'<td><small>'+actionPerformed+'</small></td>'
		+'/tr>';
		$("#dashboarSwapdApprovingTableBody").append(htmlContent);
	}
	
	}).fail(function(e) {
		
	});
}

</script>


<section class="content-header">
	<h1>User Approvals Dashboard</h1>
	<h1>
		<a id="biddashboardapproveid" class="btn btn-success"
			href='./dashboard'> <i class="fa"></i> Go To Bidding
		</a>
	</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">User</li>
		<li class="active">Approve Bid</li>
		<li class="active">Dashboard</li>
	</ol>
</section>

<sec:authorize access="hasRole('can_be_approver')">
	<%-- <c:if --%>
	<%-- 	test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
	<section class="content">
		<div class="row">
			<div class="col-md-12 table-container-main">
				<div class="box-body">
					<div class="demo-container">
						<div>
							<span class="has-error text-center">${fromBranchNotExcessCash}</span>


							<h1>Accepted Request Approval</h1>

							<table id="sameReportingApprovalBranchListTable"
								class="table table-bordered table-striped"
								style="max-height: 500px; overflow-y: scroll;">
								<thead>
									<tr class="info">
										<th class="text-center" style="max-width: 40px;"><i
											class="fa fa-cogs"></i></th>
										<th><fmt:message
												key="UserDashboard.list.approve.entity.BidPlacedFrom" /></th>
										<th><fmt:message
												key="UserDashboard.list.approve.entity.BidAmount" /></th>
										<th><fmt:message
												key="UserDashboard.list.approve.entity.BidType" /></th>
										<th><fmt:message
												key="UserDashboard.list.approve.entity.ShowDenominations" /></th>
										<th><fmt:message
												key="UserDashboard.list.approve.entity.UserAction" /></th>
									</tr>
								</thead>
								<tbody id="dashboardApprovingTableBody">
									<%-- <fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${placeCashRequestsLists}" var="branch"
										varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 50px;">${counter.index+1}</td>
											<td><small>${branch.branchManagementRequestedTo.branchName}</small>
											<td><small> <fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${branch.amount}" /></small></td>
											<td><small> ${branch.requestType}</small></td>
											<c:forEach
												items="${branch.branchManagementRequestedTo.branchParameterStatuses}"
												var="branchParameterStatus" varStatus="counter1">
												<c:if
													test="${branchParameterStatus.branchParameter.parameterName == 'Total Cash Availability'}">
													<c:set var="branch_availability_present" value="true" />
													<td><small><a href='' data-toggle="modal"
															data-target="#myModalDenomination"
															onclick="showDenominations('${branchParameterStatus.dn2000}','${branchParameterStatus.dn500}',
												 '${branchParameterStatus.dn100}','${branchParameterStatus.dn50}','${branchParameterStatus.dn20}',
												 '${branchParameterStatus.dn10}','${branchParameterStatus.dn5}',
												 '${branchParameterStatus.dc1}','${branchParameterStatus.dc2}'
												 ,'${branchParameterStatus.dc5}','${branchParameterStatus.dc10}','${branchParameterStatus.others}')">
																<i class="fa"></i> Denominations
														</a> </small></td>
												</c:if>
											</c:forEach>
											<c:if test="${branch_availability_present == 'false'}">
												<td><small></small></td>
											</c:if>

											<c:choose>
												<c:when test="${branch.requestStatus.alias eq 'PLACED'}">
													<td><a id="updateActionIds${counter.index}"
														class="btn btn-success" href='#' data-toggle="modal"
														data-target="#updateModal"
														onclick="updateActionBid(this.id,'${branch.id}','${branch.branchManagementRequestedTo.id}','${branch.amount}', '${branch.requestType}');"><i
															class="fa"></i> UPDATE </a> <a
														id="approveActionIds${counter.index}"
														class="btn btn-success" href='#'
														onclick="approveActionBid(this.id,'${branch.id}','${branch.branchManagementRequestedTo.id}');"><i
															class="fa"></i> APPROVE </a> <a
														id="cancelActionIds${counter.index}"
														class="btn btn-success" href='#'
														onclick="cancelActionBid(this.id,'${branch.id}','${branch.branchManagementRequestedTo.id}');"><i
															class="fa"></i> CANCEL </a></td>
												</c:when>
												<c:otherwise>
													<td><a class="btn btn-success" href='#'><i
															class="fa"></i> ${branch.requestStatus.status} </a></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach> --%>
								</tbody>
							</table>

							<!-- 							swap approver dashboard -->

							<h1 style="margin-top: 75pt">Swapping Request Approval</h1>
							<table style="margin-top: 50pt"
								id="sameReportingSwapApprovalBranchListTable"
								class="table table-bordered table-striped"
								style="max-height: 500px; overflow-y: scroll;">
								<thead>
									<tr class="info">
										<th class="text-center" style="max-width: 40px;"><i
											class="fa fa-cogs"></i></th>
										<th><fmt:message
												key="UserDashboard.list.approve.entity.BidPlacedFrom" /></th>
										<th><fmt:message
												key="UserDashboard.list.approve.entity.BidAmount" /></th>
										<th><fmt:message
												key="UserDashboard.list.approve.entity.BidType" /></th>
										<th><fmt:message
												key="UserDashboard.list.approve.entity.ShowDenominations" /></th>
										<th><fmt:message
												key="UserDashboard.list.approve.entity.UserAction" /></th>
									</tr>
								</thead>
								<tbody id="dashboarSwapdApprovingTableBody">
									<%-- <fmt:message key="yyyy.MM.dd" var="pattern" />
									<c:forEach items="${placeCashRequestSwapsList}" var="branch"
										varStatus="counter">
										<tr>
											<td class="text-center" style="max-width: 50px;">${counter.index+1}</td>
											<c:choose>
												<c:when
													test="${branch.branchManagementRequestedTo.id == userBranch.id}">
													<td><small>${branch.branchManagementRequestedFrom.branchName}</small>
												</c:when>
												<c:otherwise>
													<td><small>${branch.branchManagementRequestedTo.branchName}</small>
												</c:otherwise>
											</c:choose>
											<td><small> ${branch.amount}</small></td>
											<td><small> ${branch.requestType}</small></td>
											<c:forEach
												items="${branch.branchManagementRequestedTo.branchParameterStatuses}"
												var="branchParameterStatus" varStatus="counter1">
												<c:if
													test="${branchParameterStatus.branchParameter.parameterName == 'Total Cash Availability'}">
													<c:set var="branch_availability_present" value="true" />
													<td><small><a href='' data-toggle="modal"
															data-target="#myModalDenomination"
															onclick="showDenominations('${branchParameterStatus.dn2000}','${branchParameterStatus.dn500}',
												 '${branchParameterStatus.dn100}','${branchParameterStatus.dn50}','${branchParameterStatus.dn20}',
												 '${branchParameterStatus.dn10}','${branchParameterStatus.dn5}',
												 '${branchParameterStatus.dc1}','${branchParameterStatus.dc2}'
												 ,'${branchParameterStatus.dc5}','${branchParameterStatus.dc10}','${branchParameterStatus.others}')">
																<i class="fa"></i> Denominations
														</a> </small></td>
												</c:if>
											</c:forEach>
											<c:if test="${branch_availability_present == 'false'}">
												<td><small></small></td>
											</c:if>


											<c:choose>
												<c:when test="${branch.requestStatus.alias eq 'PLACED'}">
													<td><a id="approveActionIds${counter.index}"
														class="btn btn-success" href='#'
														onclick="approveSwapActionBid(this.id,'${branch.id}');"><i
															class="fa"></i> APPROVE </a> <a
														id="cancelActionIds${counter.index}"
														class="btn btn-success" href='#'
														onclick="cancelSwapActionBid(this.id,'${branch.id}');"><i
															class="fa"></i> CANCEL </a></td>
												</c:when>
												<c:otherwise>
													<td><a class="btn btn-success" href='#'><i
															class="fa"></i> ${branch.requestStatus.status} </a></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach> --%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header" id="bidrequestDiv">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Request</h4>
				</div>
				<form:form method="POST" class="form-inline"
					modelAttribute="placeCashRequest">
					<div class="modal-body">
						<div class="box-body custom_form_body" id="d1">

							<!-- append body from javascript -->

						</div>
						<!-- <div class="form-group">
							<input type="button" onclick="return updateCashRequestAndApprove()" value="Submit Request" id="submitRequest" />
					</div> -->
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button"
							onclick="return updateCashRequestAndApprove()"
							class="btn btn-primary">Save changes</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>



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
	<!-- Modal End -->
	<script type="text/javascript">

function showDenominationInAjax(branchToId){
    console.log("branchid : "+branchToId);
 
    var data = {}     
    data["toBranchId"] = branchToId;   
   
    var baseUrl = "${pageContext.request.contextPath}/getDenominationForParticularBranch";
    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : baseUrl,
        data : JSON.stringify(data),
        dataType : 'json',
        timeout : 600000,
        success : function(data) {
            console.log("dn2000 in ajax response is : "+data.branchParameterStatus.dn2000);
            showDenominations(data.branchParameterStatus.dn2000,data.branchParameterStatus.dn500,
                    data.branchParameterStatus.dn100,data.branchParameterStatus.dn50,
                    data.branchParameterStatus.dn20,data.branchParameterStatus.dn10,
                    data.branchParameterStatus.dn5,
                    data.branchParameterStatus.dc1,data.branchParameterStatus.dc2,
                    data.branchParameterStatus.dc5,data.branchParameterStatus.dc10,data.branchParameterStatus.others);
           
        },
        error : function(e) {
            console.log("error in : "+e+" data is : "+data.message);
            console.log("dn2000 in ajax response is : "+data.branchParameterStatus.dn2000);
        }
    });
}

function showDenominations(dn2000,dn500, dn100, dn50,dn20,dn10, dn5, dc1,dc2,dc5,dc10,others){
	 console.log("dn2000 = "+dn2000+" | dn500 = "+dn500+" dn100 = "+dn100+" | dn50 = "+dn50+" | dn20 = "+dn20+" | dn10 = "+dn10+" | dn5 = "+dn5);
	 console.log("dn1 = "+dc1+" | dc2 = "+dc2+" | dc5 = "+dc5+" | dc10 = "+dc10+" others = "+others);
	 
	 var newdiv = document.createElement('div');

	 newdiv.innerHTML =  "<label>2000 * "+dn2000+"</label>";
	
	 newdiv.innerHTML += "<label>500 * "+dn500+"</label>";
	
	 newdiv.innerHTML += "<label>100 * "+dn100+"</label>";
	
	 newdiv.innerHTML += "<label>50 * "+dn50+"</label>";
	
	 newdiv.innerHTML += "<label>20 * "+dn20+"</label>";
	
	 newdiv.innerHTML += "<label>10 * "+dn10+"</label>";
	
	 newdiv.innerHTML += "<label>5 * "+dn5+"</label>";
	 newdiv.innerHTML +=  "<br>";
	 newdiv.innerHTML += "<label>1 * "+dc1+"</label>";
	
	 newdiv.innerHTML += "<label>2 * "+dc2+"</label>";
	
	 newdiv.innerHTML += "<label>5 * "+dc5+"</label>";
	
	 newdiv.innerHTML += "<label>10 * "+dc10+"</label>";
	
	 newdiv.innerHTML +=  "<label>others * "+others+"</label>";
//	 document.getElementById("d1").appendChild(newdiv);
	 $("#denominationDiv").html(newdiv);
}

	function updateActionBid(id, placeRequestId, toBranchId, amount, requestType){
	
	     console.log("id | "+id+" palaceRequestId : | "+placeRequestId+" | toBranchId | "+toBranchId+" amount | "+amount)
		 document.getElementById(id).style.pointerEvents = "none";
		 document.getElementById(id).style.cursor = "default";
	     document.getElementById(id).style.display = "none";

		 
		 var newdiv = document.createElement('div');

		 newdiv.innerHTML =  "<input id='amountFieldId' onkeypress='return validateQty(event);' min="+0+" max="+amount+" type='number' name='amount' value='"+amount+"' />";
		 newdiv.innerHTML += "<input id='originalAmountId' type='hidden' name='originalAmount' value='"+amount+"' />";
		 newdiv.innerHTML += "<input id='requestTypeId' type='text' readonly='true' name='requestType' value='"+requestType+"' />";
		 newdiv.innerHTML += "<input id='placeRequestId' type='hidden' name='id' value='"+placeRequestId+"' />";
		 newdiv.innerHTML += "<input id='branchToId' type='hidden' name='branchManagementRequestedTo.id' value='"+toBranchId+"' />";

// 		 document.getElementById("d1").appendChild(newdiv);
		 $('#d1').html(newdiv);
	}
	
	function updateCashRequestAndApprove(){
		var amountId = $("#amountFieldId").val();
		console.log("amount before : "+amountId);
		if(amountId == "" || amountId == 0){
			amountId = 0;
			alert("Please enter valid amount.");
			return false;
		}
		console.log("amount after : "+amountId);
		var amount = parseInt(amountId);
		var originalAmount = $("#originalAmountId").val();
		var originalAmt = parseInt(originalAmount);
		
		var placeRequestId = $("#placeRequestId").val();
		var branchToId =  $("#branchToId").val();
		var requestTypeId =  $("#requestTypeId").val();
		
		console.log("id name is : "+amount+" orginal amount : "+originalAmt);
		if(amount > originalAmt){	
			alert("You cannot enter the amount more than "+originalAmt);
			return false;
		}else {
			console.log("imnside eekfddhh ");
			$("#updateModal").modal('hide');
		}
		
		updateBidAndApprove(placeRequestId,branchToId,amount,originalAmt,requestTypeId);
		
	}
	
	function updateBidAndApprove(placeRequestId,branchToId,amount,originalAmt,requestTypeId){
		
		console.log("place request id is : " + placeRequestId);

		console.log("amount is : " + amount
				+ " originalAmt : " + originalAmt+" request type is : "+requestTypeId);
		
		var data = {}
		data["placedRequestId"] = placeRequestId; 
		data["toBranchId"] = branchToId;
		data["amount"] = amount;
		
		var baseUrl = "${pageContext.request.contextPath}/approveAndUpdateBidCashRequest";
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : baseUrl,
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 600000,
			success : function(data) {
				console.log("success in : " + data.message);
				alert(data.message);				
				
			},
			error : function(e) {
				console.log("error in : "+e+" data is : "+data.message);
				alert(data.message);				
			}
		});
		
	}
	
	function approveActionBid(id, placeRequestId, toBranchId) {
			console.log("id is : " + id);
			document.getElementById(id).style.pointerEvents = "none";
			document.getElementById(id).style.cursor = "default";
			document.getElementById(id).style.display = "none";

			console.log("parameters are : " + toBranchId
					+ " place request id : " + placeRequestId);
			var data = {}
			data["placedRequestId"] = placeRequestId;
			data["toBranchId"] = toBranchId;

			var baseUrl = "${pageContext.request.contextPath}/approveBidCashRequest";
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : baseUrl,
				data : JSON.stringify(data),
				dataType : 'json',
				timeout : 600000,
				success : function(data) {
					console.log("success in : " + data.message);
					alert(data.message)
				},
				error : function(e) {
					console.log("error in : "+e+" data is : "+data.message);
					alert(data.message)
				}
			});
		}
		
		function cancelActionBid(id, placeRequestId, toBranchId) {
			console.log("id is : " + id);
			if(confirm("Do you want to cancel this request ?")){
				document.getElementById(id).style.pointerEvents = "none";
				document.getElementById(id).style.cursor = "default";
				document.getElementById(id).style.display = "none";
	
				console.log("parameters are : " + toBranchId
						+ " place request id : " + placeRequestId);
				var data = {}
				data["placedRequestId"] = placeRequestId;
				data["toBranchId"] = toBranchId;
	
				var baseUrl = "${pageContext.request.contextPath}/cancelBidCashRequest";
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : baseUrl,
					data : JSON.stringify(data),
					dataType : 'json',
					timeout : 600000,
					success : function(data) {
						console.log("success in : " + data.message);
						alert(data.message)
					},
					error : function(e) {
						console.log("error in : "+e+" data is : "+data.message);
						alert(data.message)
					}
				});
			}
		}
		
		
		function approveSwapActionBid(id, placeRequestId) {
			console.log("id is : " + id);
			document.getElementById(id).style.pointerEvents = "none";
			document.getElementById(id).style.cursor = "default";
			document.getElementById(id).style.display = "none";

			console.log(" place request id : " + placeRequestId);
			var data = {}
			data["placedRequestId"] = placeRequestId;			

			var baseUrl = "${pageContext.request.contextPath}/approveSwapBidCashRequest";
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : baseUrl,
				data : JSON.stringify(data),
				dataType : 'json',
				timeout : 600000,
				success : function(data) {
					console.log("success in : " + data.message);
					alert(data.message)
				},
				error : function(e) {
					console.log("error in : "+e+" data is : "+data.message);
					alert(data.message)
				}
			});
		}

		function cancelSwapActionBid(id, placeRequestId) {
			console.log("id is : " + id);
			if(confirm("Do you want to cancel this request ?")){
				document.getElementById(id).style.pointerEvents = "none";
				document.getElementById(id).style.cursor = "default";
				document.getElementById(id).style.display = "none";
	
				console.log(" place request id : " + placeRequestId);
				var data = {}
				data["placedRequestId"] = placeRequestId;			
	
				var baseUrl = "${pageContext.request.contextPath}/cancelBidSwapCashRequest";
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : baseUrl,
					data : JSON.stringify(data),
					dataType : 'json',
					timeout : 600000,
					success : function(data) {
						console.log("success in : " + data.message);
						alert(data.message)
					},
					error : function(e) {
						console.log("error in : "+e+" data is : "+data.message);
						alert(data.message)
					}
				});
			}
	}
		
	</script>
	<%-- </c:if> --%>
</sec:authorize>
<!-- 
<script type="text/javascript">
	$(document).ready(function() {
		$('#sameReportingApprovalBranchListTable').DataTable({
			"dom" : '<"top"i>rt<"bottom"flp><"clear">'
		});
	});

	$(document)
			.ready(
					function() {
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
 -->
<script type="text/javascript">

function validateQty(event) {
	var key = window.event ? event.keyCode : event.which;
	if (event.keyCode == 8 || event.keyCode == 46
	 || event.keyCode == 37 || event.keyCode == 39) {
	    return true;
	}
	else if (key < 48 || key > 57) {
	    return false;
	}
	else return true;
}
</script>

