<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>

<script type="text/javascript">
var sessionUserId = '${sessionScope['scopedTarget.mySession'].user.id}';
var sessionBranchId = '${sessionScope['scopedTarget.mySession'].user.branchManagement.id}';

$(document).ready(function() {
//	var table = $('#sameReportingBranchListTable').DataTable();
	changeCompany();
	changeCurrencyChest();
	
});

setInterval(function() {
	$("#sameReportingBranchListTable").DataTable().ajax.reload();	
}, 500000);

setInterval(function() {
	$("#allCurrencyChestListTable").DataTable().ajax.reload();	
}, 500000);


//setInterval( changeCompany , 5000 );


function changeCompany() {
	
	var table1 = $('#sameReportingBranchListTable').DataTable({
    	 destroy: true,
    	 'ajax'       : {
    	   "type"   : "GET",
    	   "url"    : "${pageContext. request. contextPath}/sameCurrencyChestReportingBranchesListForBiddingRestService",
    	   "async"  : false,
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
    		   console.log("error in user currency chest bid dashboard : "+data);
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

 function changeCurrencyChest() {
	
	var table1 = $('#allCurrencyChestListTable').DataTable({
    	 destroy: true,
    	 'ajax'       : {
    	   "type"   : "GET",
    	   "url"    : "${pageContext. request. contextPath}/allCurrencyChestExistListForBiddingRestService",
    	   "async"  : false,
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
	<h1>Currency Chest Dashboard</h1>
	<sec:authorize access="hasRole('can_be_approver')">
		<%-- 	<c:if --%>
		<%-- 		test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
		<h1>
			<a id="biddashboardapproveid" class="btn btn-success"
				href="./userapprovebiddashboard"> <i class="fa"></i> Approve Bid
				Dashboard
			</a>
		</h1>
		<!-- <h1>
			<a id="approveupdaterequestid" class="btn btn-success"
				href="./updateandapproverequestfordashboard"> <i class="fa"></i>
				Approve And Update Request
			</a>
		</h1> -->
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

			<div class="box-body">
				<div class="demo-container">
					<div>
						<div style="text-align: center; width: 100%;">
							<h4>Branch Request</h4>
						</div>
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
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>



<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">
			<div class="box-body">
				<div class="demo-container">
					<div>
						<div style="font-weight: bold; text-align: center; width: 100%;">
							<h4>Currency Chest Request</h4>
						</div>
						<table id="allCurrencyChestListTable"
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
						</table>
					</div>

					<!-- 		----------------------NonReportingBranches------------------------        -->
				</div>
			</div>

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
				<div class="modal-header denominationDiv" id="denominationDiv">

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
    $('.denominationDiv').html("");
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


	 $('.denominationDiv').html("");


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
	 $(".denominationDiv").html(newdiv);
}
	

function submitcashRequest(id,toBranchId, toBranchTotallAmount, toBranchPosition, fromBranchId){
		console.log("id is : "+id);
	if(confirm("Do you want to accept this request?")){
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