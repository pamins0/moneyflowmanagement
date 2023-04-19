<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script src="./static/js/bootstrap-multiselect.js"></script>

<link rel="stylesheet" href="./static/css/bootstrap-multiselect.css">


<section class="content-header">
	<h1>Bid Request</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Request</li>
		<li class="active">Create</li>
	</ol>
</section>
<%-- <sec:authorize access="hasRole('can_bid_update')"> --%>

<section class="content">
	<div class="row">
		<form:form id="bidRequestForm" method="POST" modelAttribute="bid"
			autocomplete="off" class="form-inline">
			<form:hidden path="id" />
			<div class="col-md-12 table-container-main full-height">
				<div class="box-header custom_header_form_page">
					<div class="heading-title-wrapper">
						<i class="fa fa-gear"></i>
						<h3 class="box-title">Request</h3>
					</div>
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./bid'> <i
							class="fa fa-arrow-left"></i> Back
						</a>
					</div>
				</div>

				<div class="box-body custom_form_body disable-overflow">

					<div class="form-group col-sm-2"
						style="overflow: visible !important;">
						<label class="control-label col-sm-12"><fmt:message
								key="Bid.form.RequestType" /></label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"></span>
								<form:select path="requestType" cssClass="form-control"
									id="requestType">
									<form:option value="OFFLOAD" label="OFFLOAD" />
									<form:option value="INDEND" label="INDEND" />
								</form:select>
								<div class="has-error">
									<form:errors path="requestType" cssClass="help-inline" />
								</div>
							</div>
						</div>
					</div>

					<c:if test="${isBranchLevel==true}">
						<form:hidden path="requestPlacedById.id" id="branchManagementId" />


						<div class="form-group col-sm-4 multiselect-div">
							<label class="control-label col-sm-12"><fmt:message
									key="Bid.form.NearMe" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:select path="bidRequestedToBranchIdList"
										cssClass="form-control multiselect"
										id="bidRequestedToBranchIdList" multiple="multiple">
										<form:options items="${closedBranch}"
											itemValue="closedGroupBranch.id"
											itemLabel="closedGroupBranch.branchName" />
									</form:select>
									<div class="has-error">
										<form:errors path="bidRequestedToBranchIdList"
											cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>

					</c:if>
					<c:if test="${isBranchLevel==false}">

						<div class="form-group col-sm-3">
							<label class="control-label col-sm-12"><fmt:message
									key="Bid.form.From" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:select path="requestPlacedById.id"
										cssClass="form-control" id="branchManagementId"
										onchange="getBranchClosedGroup()">
										<form:option value="-1" label="- Select Branch -" />
										<form:options items="${branchList}" itemValue="id"
											itemLabel="branchName" />
									</form:select>
								</div>
							</div>
							<div class="has-error">
								<form:errors path="requestPlacedById.branchName"
									cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4 multiselect-div">
							<label class="control-label col-sm-12"><fmt:message
									key="Bid.form.To" /></label>
							<div class="col-sm-12">
								<div class="input-group" id="dynamic-multiselect">
									<span class="input-group-addon"></span>

									<form:select path="bidRequestedToBranchIdList"
										cssClass="form-control multiselect"
										id="bidRequestedToBranchIdList" multiple="multiple">
									</form:select>
									<div class="has-error">
										<form:errors path="bidRequestedToBranchIdList"
											cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>

					</c:if>

					<div class="form-group col-sm-2">
						<label class="control-label col-sm-12 "> <fmt:message
								key="Bid.form.Amount" />
						</label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"></span>
								<form:input path="amount" id="amount" cssClass="form-control" />
								<span id="available-amount"></span>
							</div>
						</div>
						<div class="has-error">
							<form:errors path="amount" cssClass="help-inline" />
						</div>
					</div>

					<table class="table table-bordered table-striped"
						style="max-height: 500px; overflow-y: scroll;">
						<thead>
							<tr>
								<th colspan="9"><fmt:message key="Bid.form.Denomination" /></th>
							</tr>
							<tr>
								<th><fmt:message key="Bid.form.2000" /></th>
								<th><fmt:message key="Bid.form.500" /></th>
								<th><fmt:message key="Bid.form.100" /></th>
								<th><fmt:message key="Bid.form.50" /></th>
								<th><fmt:message key="Bid.form.20" /></th>
								<th><fmt:message key="Bid.form.10" /></th>
								<th><fmt:message key="Bid.form.5" /></th>
								<th><fmt:message key="Bid.form.2" /></th>
								<th><fmt:message key="Bid.form.1" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><form:input path="dn2000" id="dn2000"
										cssClass="form-control" /></td>
								<td><form:input path="dn500" id="dn500"
										cssClass="form-control" /></td>
								<td><form:input path="dn100" id="dn100"
										cssClass="form-control" /></td>
								<td><form:input path="dn50" id="dn50"
										cssClass="form-control" /></td>
								<td><form:input path="dn20" id="dn20"
										cssClass="form-control" /></td>
								<td><form:input path="dn10" id="dn10"
										cssClass="form-control" /></td>
								<td><form:input path="dn5" id="dn5" cssClass="form-control" /></td>
								<td><form:input path="dn2" id="dn2" cssClass="form-control" /></td>
								<td><form:input path="dn1" id="dn1" cssClass="form-control" /></td>
							</tr>

						</tbody>
						<tfoot id="available-denomination">
						</tfoot>
					</table>





					<div class="clearfix"></div>

					<div class="form-group button-group-set">
						<div class="col-sm-12">
							<c:choose>
								<c:when test="${edit}">
									<button type="submit" class="btn btn-primary btn-sm">
										<i class="fa fa-check"></i> Update
									</button>
									<a class="btn btn-danger btn-sm" href="<c:url value='./bid' />"><i
										class="fa fa-times"></i> Cancel</a>
								</c:when>
								<c:otherwise>
									<button type="submit" class="btn btn-primary btn-sm">
										<i class="fa fa-floppy-o"></i> Save
									</button>
									<a class="btn btn-danger btn-sm" href="<c:url value='./bid' />">
										<i class="fa fa-times"></i> Cancel
									</a>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>


			</div>

		</form:form>
	</div>
</section>

<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>
<script type="text/javascript">
<!-- Branch Auto Complete//-->
	// 	var baseUrl = "${pageContext.request.contextPath}/getBranchManagementForHierarchyAutoComplete";
	// 	$('#branchManagementName').autocomplete({
	// 		autoSelectFirst : true,
	// 		serviceUrl : baseUrl,
	// 		paramName : "tagName",
	// 		params : {
	// 			'hierarchyId' : function() {
	// 				return $('#hierarchyId').val();
	// 			}
	// 		},
	// 		delimiter : ",",
	// 		onSelect : function(suggestion) {
	// 			$("#branchManagementId").val(suggestion.data);
	// 			//getDesignationsByHierarchyId();
	// 			/* if (suggestion.branchType == 1) {
	// 				$('#approverDiv').show(true);
	// 				console.log("true");
	// 			} else {
	// 				$('#approverDiv').hide(true);
	// 				console.log("false");
	// 			} */
	// 			getBranchClosedGroup();
	// 			return false;
	// 		},
	// 		transformResult : function(response) {
	// 			return {
	// 				//must convert json to javascript object before process
	// 				suggestions : $.map($.parseJSON(response), function(item) {
	// 					return {
	// 						value : item.branchName,
	// 						data : item.id,
	// 						branchType : item.branchType
	// 					};
	// 				})
	// 			};
	// 		}
	// 	});
</script>

<script type="text/javascript">
	function getBranchClosedGroup() {
		var branchId = $("#branchManagementId").val();

		// 		alert("call " + branchId);
		if (branchId == null || branchId == '' || branchId == '-1') {
			return false;
		}
		$
				.ajax(
						{
							url : "${pageContext.request.contextPath}/getBranchClosedGroup-"
									+ branchId,
							dataType : 'json',
							data : {},
							type : "post",
						})
				.done(
						function(result) {

							//$('#bidRequestedToBranchIdList').empty();
							var htmlVar = "<span class='input-group-addon'></span>"
									+ "<select class='form-control multiselect' name='bidRequestedToBranchIdList' id='bidRequestedToBranchIdList' multiple='multiple'>";
							result
									.forEach(function(res) {
										//alert("Res::"	+ res.closedGroupBranch.branchName);
										htmlVar += "<option value='"+res.closedGroupBranch.id+"'>"
												+ res.closedGroupBranch.branchName
												+ "</option>";
									});

							htmlVar += "</select>";
							$('#dynamic-multiselect').html(htmlVar);
							loadMultiSelect();
						}).fail(function(e) {
					console.log(e);
				});
		getBranchCurrentCashPosition();
	}

	$(document).ready(function() {
		loadMultiSelect();

	});

	function loadMultiSelect() {

		$('.multiselect').multiselect({
			includeSelectAllOption : true,
			enableFiltering : true

		});
	}
	
	function getBranchCurrentCashPosition() {
		var branchId = $("#branchManagementId").val();

		// 		alert("call " + branchId);
		if (branchId == null || branchId == '' || branchId == '-1') {
			return false;
		}
		$
				.ajax(
						{
							url : "${pageContext.request.contextPath}/getBranchCurrentCashPosition-"
									+ branchId,
							dataType : 'json',
							data : {},
							type : "post",
						})
				.done(
						function(res) {

							$('#available-amount').html(res.amount);
							var htmlVar = "<tr><td>"+res.dn2000+"</td><td>"+res.dn500+"</td><td>"+res.dn100+"</td><td>"+res.dn50+"</td><td>"+res.dn20+"</td><td>"+res.dn10+"</td><td>"+res.dn5+"</td><td>"+res.dn2+"</td><td>"+res.dn1+"</td></tr>";
							$('#available-denomination').html(htmlVar);
						}).fail(function(e) {
					console.log(e);
				});
	}


	
	
	
		$(document).ready(function() {
			//getBranchClosedGroup();
			getBranchCurrentCashPosition();
		});
</script>