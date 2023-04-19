<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<style>.right-side{margin:0px;}</style>
<div class="popwindow_requestcash">
	<section class="content-header">
		<h2 style="text-align:center;">Swap Branch Request</h2>
	</section>
	<c:if test="true">
		<section class="content">
			<div class="row"> 
				<form:form onsubmit="return checkValidation()" name="NewEditRequestCashForm" id="NewEditRequestCashForm"
					method="POST" modelAttribute="placeCashRequestSwap"
					class="form-inline m220">
					<form:hidden path="id" /> 
					<form:hidden path="branchManagementRequestedFrom.id"
						value="${branchManagement.id}" />
					<div class="col-md-12 table-container-main">
						<div class="box-body custom_form_body">
							<div class="form-group col-sm-12 m0">
								<label  class="control-label col-sm-12"><fmt:message key="swap.request.form.branch" /></label>
								<form:input readonly="true"
									path="branchManagementRequestedFrom.branchName"
									value="${branchManagement.branchName}"
									id="branchManagementRequestedFromBranchname"
									cssClass="form-control" />
							</div>
							<div class="form-group col-sm-12 m0">
								<label class="control-label col-sm-12"><fmt:message key="swap.request.form.amount" /></label>
								<form:input onkeypress='return validateQty(event);'
									path="amount" id="amount" readonly="true" cssClass="form-control" />
								<div class="has-error">
									<form:errors path="amount" cssClass="help-inline" />
								</div>
							</div>
							<div class="form-group col-sm-12 m0">
								<label class="control-label col-sm-12"><fmt:message key="swap.request.form.branchGroup" /></label>
								<form:select path="branchManagementRequestedTo.id"
									cssClass="form-control" id="branchManagementRequestedToBranchname" readonly="true">
									<form:option value="-1" label="- Select Branch To Swap -" />
									<form:options items="${modifiedClosedGroupBranchMangementSet}"
										itemValue="id" itemLabel="branchName" />
								</form:select>
								<div class="has-error">
									<form:errors path="branchManagementRequestedTo.id"
										cssClass="help-inline" />
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
												class="btn btn-primary btn-sm saveBtn m5 pull-right"
												onclick="setTimeout(closeSelf, 3000)">Submit</button>
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

function checkValidation() {
	var branchIdSwap = $('#branchManagementRequestedToBranchname').val();
	if(branchIdSwap == -1){ 
		alert("Please select branch to swap with");
		return false;
	}else {
		return true;
	}
}

function closeSelf() {
	
		console.log("inside if after 3 seconds : condition satisfies");
        window.close();
	   
}

</script>



