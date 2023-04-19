<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="popwindow_requestcash"> 
<section class="content-header">
	<h1>Request Cash Management</h1>

</section>
<c:if test="true">
	<section class="content">
		<div class="row">
			<form:form id="NewEditRequestCashForm" method="POST"
				modelAttribute="placeCashRequest" class="form-inline">
				<form:hidden path="id" />
				<form:hidden path="branchManagementRequestedFrom.id" />
				<form:hidden path="branchManagementRequestedTo.id" />
				<label>Actual Cash Requires : ${placeCashRequest.actualCashReuires}</label>
				<div class="col-md-12 table-container-main">					
					<div class="box-body custom_form_body">
						<div class="form-group">
							<label><fmt:message key="Bid.form.BidType" /></label>
							<form:select path="requestType" cssClass="form-control"
								id="requestType" readonly="true">
								<form:option value="-1" label="- Select Bid Type -" />
								<form:option value="Remit" label="To Remit" />
								<form:option value="Recieve" label="To Recieve" />
							</form:select>
							<div class="has-error">
								<form:errors path="requestType" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message key="Bid.form.Amount" /></label>
							<form:input onkeypress='return validateQty(event);' path="amount"
								id="amount" cssClass="form-control" readonly="true"/>
							<div class="has-error">
								<form:errors path="amount" cssClass="help-inline" />
							</div>
						</div>

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
											class="btn btn-primary btn-sm saveBtn" onclick="closeWindow()">
											<i class="fa fa-floppy-o"></i> Submit
										</button>
									</c:otherwise>
								</c:choose>
								<%-- <a class="btn btn-danger btn-sm"
									href="<c:url value='/' />"><i
									class="fa fa-times"></i> Close</a> --%>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</section>
</c:if>
</div>
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

function closeWindow() {
	window.close();
}

</script>



