<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>



<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>

<script type="text/javascript">
	var sessionUserId = '${sessionScope['
	scopedTarget.mySession
	'].user.id}';
	var sessionBranchId = '${sessionScope['
	scopedTarget.mySession
	'].user.branchManagement.id}';

	$(document).ready(function() {
		//	var table = $('#sameReportingBranchListTable').DataTable();
		changeCompany();
	});

	setInterval(function() {
		$("#sameReportingBranchListTable").DataTable().ajax.reload();
		//	changeCompany();
	}, 5000);

	//	setInterval( changeCompany , 5000 );

	function changeCompany() {
		/* var table = $('#sameReportingBranchListTable').DataTable();
		table.destroy(); */
		var table1 = $('#sameReportingBranchListTable')
				.DataTable(
						{
							destroy : true,
							'ajax' : {
								"type" : "GET",
								"url" : "${pageContext. request. contextPath}/centralSwapReportingBranchesListForBiddingRestService",
								"dataSrc" : function(json) {
									var return_data = new Array();
									for (var i = 0; i < json.length; i++) {
										var jsonObj = json[i];
										var actionStr = '';
										console
												.log("branch name : "
														+ jsonObj.branchManagement.branchName
														+ " and its alias is : "
														+ jsonObj.requestStatus.alias);
										if (jsonObj.branchManagement.id == sessionBranchId) {
											if (jsonObj.requestStatus.alias == "APPROVED") {
												actionStr = "<a id='actionWithdrawId"
														+ i
														+ "' class='btn btn-success' href='#' data-toggle='modal' data-target='#myModalss' onclick=submitbidforwithdraw('actionWithdrawId"
														+ i
														+ "','"
														+ jsonObj.id
														+ "','"
														+ jsonObj.branchManagement.id
														+ "');><i class='fa'></i> WITHDRAW</a> ";
											} else {
												actionStr = "<a id='actionWithdrawId"+i+"' class='btn btn-success' href='#'> <i class='fa'></i>"
														+ jsonObj.requestStatus.status
														+ "</a>";
											}
										} else if (jsonObj.position == "level") {
											actionStr = "<a id='actionIds"+i+"' class='btn btn-warning' href='#'><i class='fa'></i> LEVEL BRANCH</a>";
										} else if (jsonObj.requestStatus.alias == "APPROVED") {
											console
													.log("branch name : "
															+ jsonObj.branchManagement.branchName
															+ " and its cc group : "
															+ jsonObj.ccGroup);
											if (jsonObj.ccGroup) {
												actionStr = "<a id='actionIds"+i+"' class='btn btn-warning' href='#'><i class='fa'></i> CURRENCY CHEST</a>";
											} else {
												actionStr = "<a id='actionIds"
														+ i
														+ "' class='btn btn-success' href='#' data-toggle='modal' data-target='#swapModal' onclick=swapCashRequest('actionIds"
														+ i
														+ "','"
														+ jsonObj.id
														+ "','"
														+ jsonObj.branchManagement.id
														+ "');><i class='fa'></i> SWAP WITH</a>";
											}
										} else {
											actionStr = "<a class='btn btn-success' href='#'> <i class='fa'></i>"
													+ jsonObj.requestStatus.status
													+ "</a>";
										}
										return_data
												.push({
													'srNo' : i + 1,
													'id' : jsonObj.id,
													'position' : jsonObj.position,
													'branchManagementId' : jsonObj.branchManagement.id,
													'branchName' : jsonObj.branchManagement.branchName,
													'cashlimit' : jsonObj.branchManagement.branchCashlimit
															.format(2),
													'eodTotal' : jsonObj.eodTotal
															.format(2),
													'total' : jsonObj.total
															.format(2),
													'denomination' : '<a href="" onclick="showDenominationInAjax('
															+ "'"
															+ jsonObj.branchManagement.id
															+ "'"
															+ ')" data-toggle="modal"	data-target="#myModalDenomination"><i class="fa"></i>Denominations</a>',
													'action' : actionStr,
												})
									}
									return return_data;
								}, error: function (data) {    
						    		   console.log("error in central swap bid dashboard : "+data);
						    		   window.location.href="./home";
						    		}
							},
							"columns" : [ {
								'data' : 'srNo'
							}, {
								'data' : 'branchName'
							}, {
								'data' : 'cashlimit'
							}, {
								'data' : 'eodTotal'
							}, {
								'data' : 'total'
							}, {
								'data' : 'denomination'
							}, {
								'data' : 'action'
							} ],
							"createdRow" : function(row, data, index) {
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
									background : color
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
	<%-- <c:if
		test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
		<h1>
			<a id="biddashboardapproveid" class="btn btn-success" href="./userapprovebiddashboard">
				<i class="fa"></i> Approve Bid Dashboard </a>
		</h1>
	</c:if> --%>
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
						<span class="has-error text-center">${NoClosedGroupBranchFoundForSwaping}</span>
						<span class="has-error text-center">${BidAlreadyPlaced}</span>
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

<div class="modal fade" id="swapModal" tabindex="-1" role="dialog"
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
					<button type="button" id="savebtnid"
						onclick="return submitSwapping()" class="btn btn-primary">Save
						changes</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	function swapCashRequest(id, dashboardId, requestedBranchId) {
		/* 	alert("Hello - "+id+" dashboard - "+dashboardId+" branch - "+requestedBranchId);	*/
		console.log("Branch id requested for : " + requestedBranchId);
		var data = {}
		data["fromBranchId"] = requestedBranchId;
		data["dashboardId"] = dashboardId;

		//	var baseUrl = "${pageContext.request.contextPath}/swapCashBranchRequest";
		var baseUrl = "${pageContext.request.contextPath}/swapCashBranchRequestRestService";
		$
				.ajax({
					type : "POST",
					contentType : "application/json",
					url : baseUrl,
					data : JSON.stringify(data),
					dataType : 'json',
					timeout : 600000,
					success : function(data) {
						console.log("success in centralswapbid dashboard : "
								+ data.message);
						console
								.log("success in centralswapbid dashboard : "
										+ data.placeCashRequestSwap.branchManagementRequestedFrom.id);
						console.log("list size : "
								+ data.branchManagementsList.length);
						if (data.branchManagementsList.length > 0) {
							var branchList = data.branchManagementsList;
							var swapBranchesOptions = "<select class='s10' id='branchManagementRequestedToId' name='branchManagementRequestedToId'>";
							swapBranchesOptions += "<option value='"+-1+"'>- Select Branch To Swap -</option>";
							for ( var branch in branchList) {
								console.log("branch is : "
										+ branchList[branch].branchName);
								swapBranchesOptions += "<option value="+branchList[branch].id+">"
										+ branchList[branch].branchName
										+ "</option>";
							}
							swapBranchesOptions += "</select>"

							document.getElementById(id).style.pointerEvents = "none";
							document.getElementById(id).style.cursor = "default";
							document.getElementById(id).style.display = "none";

							var newdiv = document.createElement('div');

							newdiv.innerHTML = "<input id='branchManagementRequestedFromId' class='s10' type='hidden' name='branchManagementRequestedFromId' value='"+data.placeCashRequestSwap.branchManagementRequestedFrom.id+"' />";
							newdiv.innerHTML += "<input id='branchManagementRequestedFromName'  class='s10' type='text' readonly='true' name='branchManagementRequestedFromName' value='"+data.placeCashRequestSwap.branchManagementRequestedFrom.branchName+"' />";
							newdiv.innerHTML += "<input id='amountplaced' type='text' class='s10'   readonly='true' name='amountplaced' value='"+data.placeCashRequestSwap.amount+"' />";
							//        		 newdiv.innerHTML += "<select id='swappedBranch' name='branchManagementRequestedToId'>";
							newdiv.innerHTML += swapBranchesOptions;
							//        		 newdiv.innerHTML += "</select>";

							//        		 document.getElementById("d1").appendChild(newdiv);
							$('#savebtnid').css("display", "block");
							$('.modal-title').css("display", "block");
							$('#d1').html(newdiv);
						} else {
							/* $("#swapModal").modal('hide');
							alert("No Branches available For Swapping");	 */
							$('#d1').html("No Branches available For Swapping");
							$('#savebtnid').css("display", "none");
							$('.modal-title').css("display", "none");
						}
					},
					error : function(e) {
						console.log("error in centralswapbid dashboard");
						alert(data.message);
					}
				});

		// 		window.open("./swapCashBranchRequest-"+requestedBranchId+"-"+dashboardId, 'window', 'width=1000,height=500');

	}

	function submitSwapping() {
		var amountId = $("#amountplaced").val();
		var amount = parseInt(amountId);
		var fromBranchId = $("#branchManagementRequestedFromId").val();
		var toBranchId = $("#branchManagementRequestedToId").val();

		console.log("amount : " + amount + " fromBranchId : " + fromBranchId
				+ " toBranchId : " + toBranchId);

		if (toBranchId == "-1") {
			alert("Please Select Branch To Swap Request");
			return false;
		} else {
			console.log("inside else if branch to swap selected ");
			$("#swapModal").modal('hide');
		}

		submitSwappedRequest(fromBranchId, toBranchId, amount);
	}

	function submitSwappedRequest(fromBranchId, toBranchId, amount) {

		var data = {}
		data["fromBranchId"] = fromBranchId;
		data["toBranchId"] = toBranchId;
		data["amount"] = amount;

		var baseUrl = "${pageContext.request.contextPath}/submitSwappedCashRequest";
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
				console.log("error in : " + e + " data is : " + data.message);
				alert(data.message);
			}
		});

	}

	function submitcashRequest(id, toBranchId, toBranchTotallAmount,
			toBranchPosition, fromBranchId) {
		console.log("id is : " + id);
		document.getElementById(id).style.pointerEvents = "none";
		document.getElementById(id).style.cursor = "default";
		document.getElementById(id).style.display = "none";

		console.log("parameters are : " + toBranchId + " | total "
				+ toBranchTotallAmount);
		var data = {}
		data["toBranchId"] = toBranchId;
		data["toBranchTotallAmount"] = toBranchTotallAmount;
		data["toBranchPosition"] = toBranchPosition;
		data["fromBranchId"] = fromBranchId;

		var baseUrl = "${pageContext.request.contextPath}/submitCashRequest";
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
				console.log("error in");
				alert(data.message)
			}
		});
	}

	function showDenominationInAjax(branchToId) {
		console.log("branchid in swapping : " + branchToId);

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
				console.log("dn2000 in ajax response is : "
						+ data.branchParameterStatus.dn2000);
				showDenominations(data.branchParameterStatus.dn2000,
						data.branchParameterStatus.dn500,
						data.branchParameterStatus.dn100,
						data.branchParameterStatus.dn50,
						data.branchParameterStatus.dn20,
						data.branchParameterStatus.dn10,
						data.branchParameterStatus.dn5,
						data.branchParameterStatus.dc1,
						data.branchParameterStatus.dc2,
						data.branchParameterStatus.dc5,
						data.branchParameterStatus.dc10,
						data.branchParameterStatus.others);

			},
			error : function(e) {
				console.log("error in : " + e + " data is : " + data.message);
				console.log("dn2000 in ajax response is : "
						+ data.branchParameterStatus.dn2000);
			}
		});
	}

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
		newdiv.innerHTML += "<h3>Coins";
		newdiv.innerHTML += "<br>";
		newdiv.innerHTML += "<label>1 * " + dc1 + "</label>";

		newdiv.innerHTML += "<label>2 * " + dc2 + "</label>";

		newdiv.innerHTML += "<label>5 * " + dc5 + "</label>";

		newdiv.innerHTML += "<label>10 * " + dc10 + "</label>";

		newdiv.innerHTML += "<label>others * " + others + "</label>";
		//		 document.getElementById("d1").appendChild(newdiv);
		$("#denominationDiv").html(newdiv);
	}

	function submitbidforwithdraw(id, dashboardId, branchId) {
		console.log("id is : " + id);
		document.getElementById(id).style.pointerEvents = "none";
		document.getElementById(id).style.cursor = "default";
		document.getElementById(id).style.display = "none";

		console.log("dashboard id | : " + dashboardId + " | branch id | : "
				+ branchId);
		var data = {}
		data["dashboardId"] = dashboardId;
		data["fromBranchId"] = branchId;

		var baseUrl = "${pageContext.request.contextPath}/withdrawCashRequest";
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
				console.log("error in");
				alert(data.message)
			}
		});
	}

	function funAjax(ths) {
		console.log("ggdghghfghsgdfghsgdfgshdfghgsdhf " + ths);
		alert("shdfhjhsdf " + ths);
	}

	function fun2(toBranchId, fromBranchId) {
		var baseUrl = "${pageContext.request.contextPath}/saveIts";
		var value = 'branchid=' + toBranchId;
		$.ajax({
			type : 'POST',
			dataType : 'json',
			url : baseUrl,
			data : value,
			success : success
		});
	}

	function fun1(toBranchId, fromBranchId) {
		// 		alert("Branch id requested for : "+requestedBranchId);
		//	window.open("./saveBranchCashTransactionRequest-"+requestedBranchId, 'window', 'width=1000,height=500');
		var baseUrl = "${pageContext.request.contextPath}/saveIt";
		/* 	 alert("toBranch id = "+toBranchId);
			 alert("requested branch id = "+fromBranchId); */
		var value = 'branchid=' + toBranchId;

		var jqxhr = $.getJSON(
				"${pageContext. request. contextPath}/saveIt-" + toBranchId,
				function() {
					console.log("success in var jqxhr");

				}).done(function(data) {
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
</script>

<script type="text/javascript">
	/* $(document).ready(function() {
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
<style>
.s10 {
	height: 28px;
	height: 28px;
	padding: 4px;
	margin: 6px;
}
</style>

