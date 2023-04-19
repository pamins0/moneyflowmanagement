<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>

<script type="text/javascript">

function changeCompanyOld(){
	$.ajax({
		url : "${pageContext. request. contextPath}/sameReportingBranchesList_restservice",
		dataType : 'json',
		data : {
			
		},
		type : "GET",
	}).done(function(POJOs) {
		$("#dashboardTableBody").empty();
		//$("#tbodyPlacedBid").append(trHtml);
		console.log("data appears : "+POJOs);
		i=0;
		for ( var pojo in POJOs) {
			console.log("data appears:"+POJOs[pojo].id);
			console.log("data appears for branchIfsc : "+POJOs[pojo].branchManagement.branchIfscCode);
			i++;
			var bidTypeFlag ="";
			var bankOverrideFlag="";
			var actionPerformed="";
			
			var bid_status = "APPROVED";

			var sessionUserId = '${sessionScope['scopedTarget.mySession'].user.id}';
			var sessionBranchId = '${sessionScope['scopedTarget.mySession'].user.branchManagement.id}';
			console.log("user session id : "+sessionUserId);
			console.log("user session id : "+sessionBranchId);
					
			/* if(POJOs[pojo].branchManagement.id == sessionBranchId){
				actionPerformed="<a href='./withdrawnBidPlacedApprovalFromPortal-"+POJOs[pojo].id+"'>Withdrawn</a>";
			}else {
				actionPerformed = "<a href='./acceptBidPlacedApprovalFromPortal-"+POJOs[pojo].id+"'>Accept</a>";			
			} */
			
			var color = "";
			
			if(POJOs[pojo].branchManagement.id == sessionBranchId){
				color = "#fcda0033";
			}else if (POJOs[pojo].position == 'excess') {
				color="#f2040433";
			}else if (POJOs[pojo].position == 'below') {
				color="#0c2b024d";
			}else if (POJOs[pojo].position == 'level') {
				color="#7b81ed";
			}
			
			if(POJOs[pojo].sameGroup == 'false'){
				actionPerformed='';
			}else if (POJOs[pojo].maker) {
				if(POJOs[pojo].branchManagement.id == sessionBranchId){
					if(POJOs[pojo].requestStatus.alias == 'APPROVED'){
						actionPerformed ="<a id='actionWithdrawId"+i+"' class='btn btn-success' href='#'"
						+"data-toggle='modal' data-target='#myModalss'"
						+"onclick=submitbidforwithdraw('actionWithdrawId"+i+"','"+POJOs[pojo].id+"','"+POJOs[pojo].branchManagement.id+"');><i class='fa'></i> WITHDRAW</a>";	
					}else {
						actionPerformed="<a id='actionWithdrawId"+i+"' class='btn btn-success' href='#'> <i class='fa'></i> "+POJOs[pojo].requestStatus.status+"</a>";
					}
				}else if (POJOs[pojo].position == 'level') { 
					actionPerformed ="";
				}else if (POJOs[pojo].requestStatus.alias == 'APPROVED') {
					actionPerformed="<a id='actionIds"+i+"' class='btn btn-success' href='#'" 
					+"onclick=submitcashRequest('actionIds"+i+"','"+POJOs[pojo].branchManagement.id+"','"+POJOs[pojo].total+"','"+POJOs[pojo].position+"','"+sessionBranchId+"');><i class='fa'></i> ACCEPT</a>";
				}else {
					actionPerformed="<a class='btn btn-success' href='#'> <i class='fa'></i> "+POJOs[pojo].requestStatus.status+"</a>";
				}
			}else {
				actionPerformed="";
			}
			
			var htmlContent = '<tr bgcolor='+color+'><td class="text-center" style="max-width: 5px;">'+i+'</td>'
			+'<td><small>'+POJOs[pojo].branchManagement.branchName+'</small></td>'
			+'<td><small>'+(POJOs[pojo].branchManagement.branchCashlimit).format(2)+'</small></td>'
			+'<td><small>'+(POJOs[pojo].eodTotal).format(2)+'</small></td>'
			+'<td><small>'+(POJOs[pojo].total).format(2)+'</small></td>'
			+'<td><small><a href="" onclick="showDenominationInAjax('+"'"+POJOs[pojo].branchManagement.id+"'"+')" data-toggle="modal"	data-target="#myModalDenomination"> <i class="fa"></i>Denominations</a></small></td>'
			+'<td><small>'+actionPerformed+'</small></td>'
			+'/tr>';
			$("#dashboardTableBody").append(htmlContent);
			
		}
		
	}).fail(function(e) {
	});
}

/* $(document).ready(function() {
	var table = $('#sameReportingBranchListTable').DataTable();
	changeCompany();
});

setInterval(function() {
	changeCompany();
}, 500000); */

function changeCompanyLatestBack() {
	$
	.ajax(
			{
// 				url : "${pageContext. request. contextPath}/sameReportingBranchesList_restservice",
				url : "${pageContext. request. contextPath}/sameReportingBranchesListForBiddingRestService",
				dataType : 'json',
				data : {

				},
				type : "GET",
			}).done(function(POJOs) {
		var table = $('#sameReportingBranchListTable').DataTable();
		table.destroy();
		loadDataTable(POJOs);
	}).fail(function(e) {
	});
}

function loadDataTable(data){
	console.log("fgfgfgfffg : " + data);
	
	var bidTypeFlag = "";
	var bankOverrideFlag = "";
	var actionPerformed = "";

	var bid_status = "APPROVED";

	var sessionUserId = '${sessionScope['scopedTarget.mySession'].user.id}';
	var sessionBranchId = '${sessionScope['scopedTarget.mySession'].user.branchManagement.id}';
	console.log("user session id : "+ sessionUserId);
	console.log("user session branch id : "+ sessionBranchId);
	
	console.log("bid status id : "+bid_status);

	var color = "";		
	
	var filingIds = "";
	var branchId = "";
	var dashboardId = "";
	var status = "";
	var total = "";
	var i = 0;
	
	var table = $('#sameReportingBranchListTable').DataTable({
		"aaData" : data,
		"aoColumns" : [ {
			mData : "id",
			render : function(mData, type, row) {
				dashboardId = mData;
				return mData ? ++i : '';
			}
		}, {
			mData : "branchManagement",
			render : function(mData, type, row) {				
				return mData ? mData.branchName : '';
			}
		}, {
			mData : "branchManagement",
			render : function(mData, type, row) {
				return mData ? mData.branchCashlimit.format(2) : '';
			}
		}, {
			mData : "eodTotal",
			render : function(mData, type, row) {
				return mData ? mData.format(2) : '';
			}
		},{
			mData : "total",
			render : function(mData, type, row) {
				total = mData;
				return mData ? mData.format(2) : '';
			}
		},{	
			mData : "branchManagement.id",
			render : function(mData, type, row) {
				branchId = mData;
				var denominationUrl = '<a href="" onclick="showDenominationInAjax('+"'"+mData+"'"+')" data-toggle="modal"	data-target="#myModalDenomination"><i class="fa"></i>Denominations</a>';
				return mData ? denominationUrl: '';
			} 
		},{
			mData : "requestStatus",
			render : function(mData, type, row) {
				console.log("alias status : "+mData.alias+" and position : "+row.position);
				if(branchId == sessionBranchId){
					if(mData.alias == "APPROVED"){
						actionPerformed = "<a id='actionWithdrawId"+i+"' class='btn btn-success' href='#' data-toggle='modal' data-target='#myModalss' onclick=submitbidforwithdraw('actionWithdrawId"+i+"','"+dashboardId+"','"+branchId+"');><i class='fa'></i> WITHDRAW</a> ";								
					}else {
						actionPerformed = "<a id='actionWithdrawId"+i+"' class='btn btn-success' href='#'> <i class='fa'></i>"+mData.status+"</a>"; 
					}
				}else if (row.position == "level") {
					actionPerformed = "";
				}else if (mData.alias == "APPROVED") {
					actionPerformed = "<a id='actionIds"+i+"' class='btn btn-success' href='#' data-toggle='modal' data-target='#swapModal' onclick=submitcashRequest('actionIds"+i+"','"+branchId+"','"+total+"','excess','"+sessionBranchId+"');><i class='fa'></i> ACCEPT</a>"; 
				}else {
					actionPerformed = "<a class='btn btn-success' href='#'> <i class='fa'></i>"+mData.status+"</a>";
				}
				return mData ? actionPerformed : '';
			}
		}, ],
		  "createdRow": function ( row, data, index ) {
			  if (data.branchManagement.id == sessionBranchId) {
					color = "#ffc000";
				} else if (data.position == 'excess') {
					color = "#f24033";
				} else if (data.position == 'below') {
					color = "#0cb04d";
				} else if (data.position == 'level') {
					color = "#7b81ed";
				}
			 console.log("EOD TOTAL AFTER :   "+data.eodTotal);
			    $(row).css({
			      background: color
			    });
			  } ,
		"paging" : true
	});
}



var sessionUserId = '${sessionScope['scopedTarget.mySession'].user.id}';
var sessionBranchId = '${sessionScope['scopedTarget.mySession'].user.branchManagement.id}';

$(document).ready(function() {
//	var table = $('#sameReportingBranchListTable').DataTable();
	changeCompany();
});

setInterval(function() {
	$("#sameReportingBranchListTable").DataTable().ajax.reload();
//	changeCompany();
}, 500000);

//setInterval( changeCompany , 5000 );


function changeCompany() {
	/* var table = $('#sameReportingBranchListTable').DataTable();
	table.destroy(); */
	var table1 = $('#sameReportingBranchListTable').DataTable({
    	 destroy: true,
    	 'ajax'       : {
    	   "type"   : "GET",
    	   "url"    : "${pageContext. request. contextPath}/sameCurrencyChestReportingBranchesListForBiddingRestService",
    	   "dataSrc": function (json) {
    		 
    	     var return_data = new Array();
    	     for(var i=0;i< json.length; i++){
    	     	var jsonObj=json[i];
    	     	var actionStr='';
    	     	console.log("branch name : "+jsonObj.branchManagement.branchName +" and its alias is : "+jsonObj.requestStatus.alias);
    	     	if(jsonObj.branchManagement.id == sessionBranchId){
					if(jsonObj.requestStatus.alias == "APPROVED"){
						actionStr="<a id='actionWithdrawId"+i+"' class='btn btn-success' href='#' data-toggle='modal' data-target='#myModalss' onclick=submitbidforwithdraw('actionWithdrawId"+i+"','"+jsonObj.id+"','"+jsonObj.branchManagement.id+"');><i class='fa'></i> WITHDRAW</a> ";								
					}else {
						actionStr="<a id='actionWithdrawId"+i+"' class='btn btn-success' href='#'> <i class='fa'></i>"+jsonObj.requestStatus.status+"</a>";
					}
				}else if (jsonObj.position == "level") {
					actionStr="<a id='actionIds"+i+"' class='btn btn-warning' href='#'><i class='fa'></i> LEVEL BRANCH</a>";
				}else if (jsonObj.requestStatus.alias == "APPROVED") {					
						actionStr="<a id='actionIds"+i+"' class='btn btn-success' href='#' onclick=submitcashRequest('actionIds"+i+"','"+jsonObj.branchManagement.id+"','"+jsonObj.total+"','"+jsonObj.position+"','"+sessionBranchId+"');><i class='fa'></i> ACCEPT</a>";
				}else {
					actionStr="<a class='btn btn-success' href='#'> <i class='fa'></i>"+jsonObj.requestStatus.status+"</a>";
				}
    	       return_data.push({
      	         'srNo': i+1,
    	         'id': jsonObj.id,
    	         'position': jsonObj.position,
    	         'branchManagementId': jsonObj.branchManagement.id,
    	         'branchName': jsonObj.branchManagement.branchName,
    	         'cashlimit':jsonObj.branchManagement.branchCashlimit.format(2),
    	         'eodTotal': jsonObj.eodTotal.format(2),
    	         'total': jsonObj.total.format(2),
    	         'denomination': '<a href="" onclick="showDenominationInAjax('+"'"+jsonObj.branchManagement.id+"'"+')" data-toggle="modal"	data-target="#myModalDenomination"><i class="fa"></i>Denominations</a>',
    	         'action': actionStr,
    	       })
    	     }
    	     return return_data;
    	   }, error: function (data) {    
    		   console.log("error in user bid dashboard : "+data);
    		   window.location.href="./home";
    		}
    	 },
    	 "columns"    : [
        	   {'data': 'srNo'},
          	   {'data': 'branchName'},
          	   {'data': 'cashlimit'},
          	   {'data': 'eodTotal'},
          	   {'data': 'total'},
          	   {'data': 'denomination'},
          	   {'data': 'action'}
    	 ],
		 "createdRow": function ( row, data, index ) {
			 	if (data.branchManagementId == sessionBranchId) {
					color = "#ffc000";
				} else if (data.position == 'excess') {
					color = "#ff6347";
				} else if (data.position == 'below') {
					color = "#3CB371";
				} else if (data.position == 'level') {
					color = "#8f8bff";
				}
			   $(row).css({
			     background: color
			   });
			 } 
    });
}

Number.prototype.format = function(n, x) {
	var re = '(\\d)(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\.' : '$')
			+ ')';
	return this.toFixed(Math.max(0, ~~n)).replace(new RegExp(re, 'g'),
			'$1,');
};



</script>

<section class="content-header">
	<h1>User Dashboard</h1>
	<sec:authorize access="hasRole('can_be_approver')">
		<%-- 	<c:if --%>
		<%-- 		test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
		<h1>
			<a id="biddashboardapproveid" class="btn btn-success"
				href="./userapprovebiddashboard"> <i class="fa"></i> Approve Bid
				Dashboard
			</a>
		</h1>
		<h1>
			<a id="approveupdaterequestid" class="btn btn-success"
				href="./updateandapproverequestfordashboardentries"> <i
				class="fa"></i> Approve And Update Request
			</a>
		</h1>
		<%-- 	</c:if> --%>
	</sec:authorize>
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
							class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
							style="max-height: 500px; overflow-y: scroll;"
							aria-describedby="userListTable_info" role="grid">
							<thead>
								<tr class="info">
									<th class="text-center" style="max-width: 40px;"><i
										class="fa fa-cogs"></i></th>
									<th><fmt:message
											key="UserDashboard.list.entity.BidPlacedBy" /></th>
									<th><fmt:message key="UserDashboard.list.entity.CashLimit" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.EODPosition" /></th>
									<th><fmt:message key="UserDashboard.list.entity.BidAmount" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.ShowDenominations" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.UserAction" /></th>
								</tr>
							</thead>
							<tfoot>
								<tr class="info">
									<th class="text-center" style="max-width: 40px;"><i
										class="fa fa-cogs"></i></th>
									<th><fmt:message
											key="UserDashboard.list.entity.BidPlacedBy" /></th>
									<th><fmt:message key="UserDashboard.list.entity.CashLimit" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.EODPosition" /></th>
									<th><fmt:message key="UserDashboard.list.entity.BidAmount" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.ShowDenominations" /></th>
									<th><fmt:message
											key="UserDashboard.list.entity.UserAction" /></th>
								</tr>
							</tfoot>
							<%-- 	
							<tbody id="dashboardTableBody">
								<fmt:message key="yyyy.MM.dd" var="pattern" />
								<c:forEach items="${finalBranchDashboardList}" var="branch"
									varStatus="counter">
									<c:set var="color" value="" />
									<c:if test="${branch.branchManagement.id == userBranch.id}">
										<c:set var="color" value="#fcda0033" />
									</c:if>
									<c:if test="${branch.branchManagement.id != userBranch.id}">
										<c:if test="${branch.position eq 'excess'}">
											<c:set var="color" value="#f2040433" />
										</c:if>
										<c:if test="${branch.position eq 'below'}">
											<c:set var="color" value="#0c2b024d" />
										</c:if>
										<c:if test="${branch.position eq 'level'}">
											<c:set var="color" value="#7b81ed" />
										</c:if>
									</c:if>
									<tr bgcolor="<c:out value="${color}"/>">
										<td class="text-center" style="max-width: 50px;">${counter.index+1}</td>
										<td><small>${branch.branchManagement.branchName}</small>
										<td><small><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${branch.branchManagement.branchCashlimit}" /></small>
										<td><small><fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${branch.eodTotal}" /></small>
										<td><small> <fmt:formatNumber type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${branch.total}" />
										</small></td>
										<c:set var="branch_availability_present" value="false" />
										<c:set var="branch_availability_present_total" value="" />
										<c:forEach
											items="${branch.branchManagement.branchParameterStatuses}"
											var="branchParameterStatus" varStatus="counter1">
											<c:if
												test="${branchParameterStatus.branchParameter.parameterName == 'Total Cash Availability'}">
												<c:set var="branch_availability_present" value="true" />
												<td><small><a href='' data-toggle="modal"
														data-target="#myModalDenomination"
														onclick="showDenominationInAjax('${branchParameterStatus.branchManagement.id}')">
															<i class="fa"></i> Denominations
													</a> </small></td>
											</c:if>
										</c:forEach>
										<c:if test="${branch_availability_present == 'false'}">
											<td><small></small></td>
										</c:if>
										<c:choose>
											<c:when test="${branch.sameGroup eq 'true'}">
												<c:set var="branch_maker" value="false" />
												<c:choose>
													<c:when
														test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
														<c:set var="branch_maker" value="true" />
														<c:choose>
															<c:when
																test="${branch.branchManagement.id == userBranch.id}">
																<c:choose>
																	<c:when test="${branch.requestStatus.alias eq 'APPROVED'}">
																		<td><a id="actionWithdrawId${counter.index}"
																			class="btn btn-success" href='' data-toggle="modal"
																			data-target="#myModalss"
																			onclick="submitbidforwithdraw(this.id,'${branch.id}','${branch.branchManagement.id}');"><i
																				class="fa"></i> WITHDRAW </a></td>
																	</c:when>
																	<c:otherwise>
																		<td><a id="actionWithdrawId${counter.index}"
																			class="btn btn-success" href='#'><i class="fa"></i>
																				${branch.requestStatus.status} </a></td>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${branch.position eq 'level'}">
																		<td></td>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${branch.requestStatus.alias eq 'APPROVED'}">
																				<td><a id="actionIds${counter.index}"
																					class="btn btn-success" href='#'
																					onclick="submitcashRequest(this.id,'${branch.branchManagement.id}','${branch.total}', '${branch.position}', '${userBranch.id}');"><i
																						class="fa"></i> ACCEPT </a></td>
																			</c:when>
																			<c:otherwise>
																				<td><a class="btn btn-success" href='#'><i
																						class="fa"></i> ${branch.requestStatus.status} </a></td>
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</tbody>
						 --%>
						</table>
					</div>

					<!-- 		----------------------NonReportingBranches------------------------        -->
				</div>
			</div>
			<%-- 			</c:if> --%>
		</div>
	</div>
</section>



<!-- Modal Start -->
<div class="modal fade" id="myModals" tabindex="-1" role="dialog"
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
    console.log("branchid in : "+branchToId);
   
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
	 newdiv.innerHTML +=  "<h3>Coins";
	 newdiv.innerHTML +=  "<br>";
	 newdiv.innerHTML += "<label>1 * "+dc1+"</label>";
	
	 newdiv.innerHTML += "<label>2 * "+dc2+"</label>";
	
	 newdiv.innerHTML += "<label>5 * "+dc5+"</label>";
	
	 newdiv.innerHTML += "<label>10 * "+dc10+"</label>";
	
	 newdiv.innerHTML +=  "<label>others * "+others+"</label>";
//	 document.getElementById("d1").appendChild(newdiv);
	 $("#denominationDiv").html(newdiv);
}
	

function submitcashRequest(id,toBranchId, toBranchTotallAmount, toBranchPosition, fromBranchId){
		console.log("id is : "+id);
	if(confirm("Do you want to accept this request ?")){
		document.getElementById(id).style.pointerEvents="none";
		document.getElementById(id).style.cursor="default";
		document.getElementById(id).style.display="none";
		
		console.log("parameters are : "+toBranchId+" | total "+toBranchTotallAmount);
		var data = {}
		data["toBranchId"] = toBranchId;
		data["toBranchTotallAmount"] = toBranchTotallAmount;
		data["toBranchPosition"] = toBranchPosition;
		data["fromBranchId"] = fromBranchId;
		
		var baseUrl = "${pageContext.request.contextPath}/submitCashRequest";
		$.ajax({
            type: "POST",
            contentType: "application/json",
            url: baseUrl,
            data: JSON.stringify(data),
            dataType: 'json',
            timeout: 600000,
            success: function (data) {
                console.log("success in : "+data.message);
                alert(data.message)
            },
            error: function (e) {             
              console.log("error in");
              alert(data.message)
            }
	});	
   }
}
	
	function submitbidforwithdraw(id,dashboardId,branchId){		
		if(confirm("Do you want to withdraw the request?")){
			console.log("id is : "+id);
			document.getElementById(id).style.pointerEvents="none";
			document.getElementById(id).style.cursor="default";
			document.getElementById(id).style.display="none";
			
			console.log("dashboard id | : "+dashboardId+" | branch id | : "+branchId);
			var data = {}
			data["dashboardId"] = dashboardId;
			data["fromBranchId"] = branchId;
			
			var baseUrl = "${pageContext.request.contextPath}/withdrawCashRequest";
			console.log("baseUrl : "+baseUrl);
			$.ajax({
	            type: "POST",
	            contentType: "application/json",
	            url: baseUrl,
	            data: JSON.stringify(data),
	            dataType: 'json',
	            timeout: 600000,
	            success: function (data) {
	                console.log("success in : "+data.message);
	                alert(data.message)
	            },
	            error: function (e) {             
	              console.log("error in");
	              alert(data.message)
	            }
		});	
		}
	}
	
	function funAjax(ths) {
		console.log("ggdghghfghsgdfghsgdfgshdfghgsdhf "+ths);
		alert("shdfhjhsdf "+ths);
	}
	
	function fun2(toBranchId,fromBranchId){
		var baseUrl = "${pageContext.request.contextPath}/saveIts";
		var value ='branchid='+toBranchId;
		$.ajax({
			  type: 'POST',
			  dataType: 'json',
			  url: baseUrl,
			  data: value,
			  success: success
			});
	}

	function fun1(toBranchId,fromBranchId) {
// 		alert("Branch id requested for : "+requestedBranchId);
	//	window.open("./saveBranchCashTransactionRequest-"+requestedBranchId, 'window', 'width=1000,height=500');
		var baseUrl = "${pageContext.request.contextPath}/saveIt";
	/* 	 alert("toBranch id = "+toBranchId);
		 alert("requested branch id = "+fromBranchId); */
		 var value ='branchid='+toBranchId;
		
		 var jqxhr = $.getJSON(
					"${pageContext. request. contextPath}/saveIt-"
							+ toBranchId, function() {
						console.log("success in var jqxhr");

					}).done(
					function(data) {
						alert("success");
						console.log("second success " + data.length);						
						console.log(data);
					}).fail(function() {
				console.log("error");
			}).always(function() {
				console.log("complete");
			});
		 
		alert("helo");
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

<script type="text/javascript">
/* 	$(document).ready(function() {
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
					}); */
</script>