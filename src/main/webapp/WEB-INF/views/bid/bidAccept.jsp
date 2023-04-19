<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<section class="content-header">
	<h1>Request Accept</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Request</li>
		<li class="active">Accept</li>
	</ol>
</section>
<%-- <sec:authorize access="hasRole('can_bid_update')"> --%>

<section class="content">
	<div class="row">
		<form:form id="bidRequestForm" method="POST" 
			modelAttribute="bidRequestedTo" autocomplete="off"
			class="form-inline">
			<form:hidden path="id" />
			<div class="col-md-12 table-container-main full-height">
				<div class="box-header custom_header_form_page">
					<div class="heading-title-wrapper">
						<i class="fa fa-gear"></i>
						<h3 class="box-title">Request</h3>
					</div>
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='./bid-dashboard'> <i
							class="fa fa-arrow-left"></i> Back
						</a>
					</div>
				</div>

				<div class="box-body custom_form_body disable-overflow">

					<div class="form-group col-sm-2"
						style="overflow: visible !important;">
						<label class="control-label col-sm-12"><fmt:message
								key="Bid.form.RequestType" /></label>
						<div class="col-sm-12">${bidRequestedTo.bidRequestId.requestType}</div>
					</div>


					<div class="form-group col-sm-2"
						style="overflow: visible !important;">
						<label class="control-label col-sm-12"><fmt:message
								key="Bid.form.RequestBy" /></label>
						<div class="col-sm-12">${bidRequestedTo.bidRequestId.requestPlacedById.branchName}</div>
					</div>



					<div class="form-group col-sm-2"
						style="overflow: visible !important;">
						<label class="control-label col-sm-12"><fmt:message
								key="Bid.form.PaymentType" /></label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"></span>
								<form:select path="denominationCustomized"
									cssClass="form-control" id="denominationCustomized">
									<form:option value="0" label="FULL" />
									<form:option value="1" label="PARTIAL" />
								</form:select>
								<div class="has-error">
									<form:errors path="denominationCustomized"
										cssClass="help-inline" />
								</div>
							</div>
						</div>
					</div>

					<div class="form-group col-sm-2"
						style="overflow: visible !important;">
						<label class="control-label col-sm-12"><fmt:message
								key="Bid.form.Amount" /></label>
						<div class="col-sm-12">
							${bidRequestedTo.bidRequestId.amount}</div>
						<div class="has-error"></div>
					</div>

					<div class="form-group col-sm-2" id="bid-amount"
						style="overflow: visible !important;">
						<label class="control-label col-sm-12"><fmt:message
								key="Bid.form.PartialAmount" /></label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"></span>
								<form:input path="acceptAmount" id="amount"
									cssClass="form-control" />
								<span id="available-amount"></span>
							</div>
						</div>
						<div class="has-error">
							<form:errors path="acceptAmount" cssClass="help-inline" />
						</div>
					</div>

					<div class="clearfix"></div>

					<table class="table table-bordered table-striped"
						style="max-height: 500px; overflow-y: scroll; margin-top: 10px;">
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
								<td>${bidRequestedTo.bidRequestId.dn2000}</td>
								<td>${bidRequestedTo.bidRequestId.dn500}</td>
								<td>${bidRequestedTo.bidRequestId.dn100}</td>
								<td>${bidRequestedTo.bidRequestId.dn50}</td>
								<td>${bidRequestedTo.bidRequestId.dn20}</td>
								<td>${bidRequestedTo.bidRequestId.dn10}</td>
								<td>${bidRequestedTo.bidRequestId.dn5}</td>
								<td>${bidRequestedTo.bidRequestId.dn2}</td>
								<td>${bidRequestedTo.bidRequestId.dn1}</td>
							</tr>
							<tr id="bid-amount-denomination">
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
					</table>


					<div class="clearfix"></div>

					<div class="form-group button-group-set">
						<div class="col-sm-12">

							<button type="submit" class="btn btn-primary btn-sm">
								<i class="fa fa-floppy-o"></i> Accept Request
							</button>
							<a class="btn btn-danger btn-sm"
								href="<c:url value='./bid-dashboard' />"> <i
								class="fa fa-times"></i> Cancel
							</a>

						</div>
					</div>
				</div>


			</div>

		</form:form>
	</div>
</section>

<script>
	$(document).ready(function() {
		partialPayment();
	});
	$("#denominationCustomized").change(function() {
		partialPayment();
	});
	function partialPayment() {
		var denominationCustomized = $("#denominationCustomized").val();
		// 		alert(denominationCustomized);
		if (denominationCustomized == 0) {
			$("#bid-amount").hide();
			$("#bid-amount-denomination").hide();
		} else {
			$("#bid-amount").show();
			$("#bid-amount-denomination").show();
		}
	}
</script>