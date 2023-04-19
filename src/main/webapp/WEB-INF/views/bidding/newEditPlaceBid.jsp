<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


	<script>
	function getCommissionRate() {
		//alert("hello..."+$('#modelTypeId').val());
		$('#commission_Rate').empty();
		var modelType = $('#modelTypeId').val();
		if(modelType == -1){			
			$('#commission_Rate').val();
			return false;
		}
		console.log("modelType id is : " + modelType);
		

		var jqxhr = $.getJSON(
				"${pageContext. request. contextPath}/getCommisionRateAccoToModelType-"
						+ modelType, function() {
					console.log("success in var commission rate");
				}).done(
				function(data) {
					
				value=data.commission_Rate.toFixed(4);
			
				console.log("second success " + value);
					$("#commission_Rate").val(value);
					
					console.log("commission_Rate is : "+data.commission_Rate);
				}).fail(function() {
			console.log("error");
		}).always(function() {
			console.log("complete");
		});
	}
	</script>

<section class="content-header">
	<h1>BidPlace Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Bid Place</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<c:if test="true">
	<section class="content">
		<div class="row">
			<form:form id="NewEditBiddingForm" method="POST" modelAttribute="placedBidTable"
				class="form-inline">
				<form:hidden path="id" />
				<div class="col-md-12 table-container-main">
					<div class="box-header custom_header_form_page">
						<div class="heading-title-wrapper">
							<i class="fa fa-gear"></i>
							<c:choose>
								<c:when test="${edit}">
									<h3 class="box-title">
										Edit Bid - <span class="edit-module-name">${placedBid.title}
										</span>
									</h3>
								</c:when>
								<c:otherwise>
									<h3 class="box-title">New Bid</h3>
								</c:otherwise>
							</c:choose>
						</div>

						<div class="btn-group custom_group" role="group"
							aria-label="Basic example">
							<a class="btn btn-success" href='./biddashboard'> <i
								class="fa fa-arrow-left"></i> Back
							</a>
						</div>
					</div>
					<div class="box-body custom_form_body">						
						<div class="form-group">
							<label><fmt:message key="Bid.form.Amount" /></label>
							<form:input onkeypress='return validateQty(event);' path="bid_Amount" id="bid_Amount"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="bid_Amount" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message key="Bid.form.BidType" /></label>
							<form:select path="bid_Type_Flag" cssClass="form-control"
								id="bid_Type_Flag">
								<form:option value="-1" label="- Select Bid Type -" />
								<form:option value="1" label="To Remit" />
								<form:option value="2" label="To Recieve" />
							</form:select>
							<div class="has-error">
								<form:errors path="bid_Type_Flag" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message key="Bid.form.Model" /></label>
							<form:select path="modelType.id" cssClass="form-control"
								id="modelTypeId" onchange="getCommissionRate()">
								<form:option value="-1" label="- Select Model Type -" />
								<form:options items="${modelTypesList}" itemValue="id"
									itemLabel="model_Type" /> 
							</form:select>
							<div class="has-error">
								<form:errors path="modelType.id" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message key="Bid.form.CommissionRate" /></label>
							<form:input path="commission_Rate" id="commission_Rate"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="commission_Rate" cssClass="help-inline" />
							</div>
						</div>
						
						<div class="form-group">
							<label><fmt:message key="Bid.form.BidStatus" /></label>
							<form:select path="PB_Status.id" cssClass="form-control"
								id="PB_StatusId">
<%-- 								<form:option value="-1" label="- Select Model Type -" /> --%>
								<form:options items="${bidStatusList}" itemValue="id"
									itemLabel="status" /> 
							</form:select>
							<div class="has-error">
								<form:errors path="PB_Status.id" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message key="Bid.form.CommissionAmount" /></label>
							<form:input path="commission" id="bid_Amount"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="commission" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message key="Bid.form.Vicinity" /></label>
							<form:input onkeypress='return validateQty(event);'  path="vicinity" id="vicinity"
								cssClass="form-control" value="${placedBidTable.branchManagement.vicinity}" />
							<div class="has-error">
								<form:errors path="vicinity" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group bid-place-wrapper">
							<label>Need Total Approvals</label> 
							${placedBidTable.branchManagement.branchApprovers}
						</div>
						<c:if test="${placedBidTable.branchManagement.hierarchyControl.orgManagement.intraBank==true}">
							<div class="form-group">
								<label><fmt:message key="Bid.form.IntraBankOvereideTime" /></label>
								<form:input path="intra_Bank_Override_time" id="intra_Bank_Override_time"
									cssClass="form-control" value="${placedBidTable.branchManagement.hierarchyControl.orgManagement.maxTimeForIntraBank}" />
								<div class="has-error">
									<form:errors path="intra_Bank_Override_time" cssClass="help-inline" />
								</div>
							</div>
						</c:if>
						
						
						<div class="form-group button-group-set">
							<div class="col-sm-12">
								<c:choose>
									<c:when test="${edit}">
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-check"></i> Update
										</button>
									</c:when>
									<c:otherwise>
										<button type="submit" name="saveBtn" value="Save"
											class="btn btn-primary btn-sm saveBtn">
											<i class="fa fa-floppy-o"></i> Save
										</button>
										<!-- <button type="submit" name="saveBtn" value="SaveAndNew"
											class="btn btn-primary btn-sm">
											<i class="fa fa-floppy-o"></i> Save And New
										</button> -->
									</c:otherwise>
								</c:choose>
								<a class="btn btn-danger btn-sm"
									href="<c:url value='/biddashboard' />"><i
									class="fa fa-times"></i> Cancel</a>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</section>	
</c:if>
<script type="text/javascript">

$(document).ready(function(){
	$("#NewEditBiddingForm").submit(function(){
		$(".saveBtn").css("display", "none");
		return true;
	});
});

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