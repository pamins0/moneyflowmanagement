<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Process Request To Dashboard</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Dashboard</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<c:if test="true">
	<section class="content">
		<div class="row">
			<form:form id="NewEditUpdateForm" method="POST"
				modelAttribute="dashboardFinalBid" class="form-inline"
				>
				<form:hidden path="id" />
				<form:hidden path="requestFrom" />
				<c:choose>
					<c:when test="${empty dashboardFinalBid.urlReferer }">
						<c:set var="referer" value="${header['Referer']}" />
					</c:when>
					<c:otherwise>
						<c:set var="referer" value="${dashboardFinalBid.urlReferer}" />
					</c:otherwise>
				</c:choose>
				<form:hidden path="urlReferer" value="${referer}" />
				<form:hidden path="branchManagement.id" />
				<div class="col-md-12 table-container-main">
					<div class="box-header custom_header_form_page">
						<div class="heading-title-wrapper">
							<i class="fa fa-gear"></i>
							<c:choose>
								<c:when test="${edit}">
									<h3 class="box-title">
										Update And Approve Request </span>
									</h3>
								</c:when>
								<c:otherwise>
									<h3 class="box-title">New Request</h3>
								</c:otherwise>
							</c:choose>
						</div>

						<!-- 						<div class="btn-group custom_group" role="group" -->
						<!-- 							aria-label="Basic example"> -->
						<%-- 							<c:choose> --%>
						<%-- 								<c:when test="${dashboardFinalBid.requestFrom == 'byhierarchy'}"> --%>
						<%-- 									<a class="btn btn-success" href='${referer}'> <i --%>
						<!-- 										class="fa fa-arrow-left"></i> Back -->
						<!-- 									</a> -->
						<%-- 								</c:when> --%>
						<%-- 								<c:otherwise> --%>
						<!-- 									<a class="btn btn-success" href='./userbiddashboard'> <i -->
						<!-- 										class="fa fa-arrow-left"></i> Back -->
						<!-- 									</a> -->
						<%-- 								</c:otherwise> --%>
						<%-- 							</c:choose> --%>
						<!-- 						</div> -->
					</div>
					<div class="box-body custom_form_body">
						<c:choose>
							<c:when test="${alreadyProcessed}">
								<div id="boxes">

									<div id="dialog" class="window">



										<div id="popupfoot ">
											<img src="<c:url value='/static/img/download.jpg'/>"
												class="img-circle" />
											<!-- 			<a href="#" class="close agree">I agree</a> | <a class="agree" -->
											<!-- 				style="color: red;" href="#">I do not agree</a> -->
										</div>

									</div>

									<div id="mask" class="m20"></div>

								</div>
							</c:when>
							<c:otherwise>
								<div class="form-group col-sm-3">
									<label class="control-label col-sm-12 "><fmt:message
											key="UserDashboard.form.updateapprove.bid.BranchName" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>

											<form:input path="branchManagement.branchName"
												readonly="true" id="branchName" cssClass="form-control" />
											<div class="has-error">
												<form:errors path="branchManagement.branchName"
													cssClass="help-inline" />
											</div>
										</div>
									</div>
								</div>
								<div class="form-group col-sm-3">
									<label class="control-label col-sm-4 "><fmt:message
											key="UserDashboard.form.updateapprove.bid.Position" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>
											<form:input path="position" readonly="true" id="position"
												cssClass="form-control" />
											<div class="has-error">
												<form:errors path="position" cssClass="help-inline" />
											</div>
										</div>
									</div>
								</div>
								<div class="form-group col-sm-3">
									<label class="control-label col-sm-8 "><fmt:message
											key="UserDashboard.form.updateapprove.bid.RequestType" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>

											<form:select path="requestType" cssClass="form-control"
												id="requestType">
												<%-- 												<form:option value="-1" label="- Select Request Type -" />											 --%>
												<form:option value="1" label="To Recieve" />
												<form:option value="2" label="To Remit" />
												<form:option value="3" label="Level" />
											</form:select>
											<div class="has-error">
												<form:errors path="requestType" cssClass="help-inline" />
											</div>
										</div>
									</div>
								</div>
								<div class="form-group col-sm-3">
									<label class="control-label col-sm-4 "><fmt:message
											key="UserDashboard.form.updateapprove.bid.Amount" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>
											<fmt:formatNumber groupingUsed="false" var="totalFmt"
												type="number" value="${dashboardFinalBid.total}" />
											<form:input path="total" value="${totalFmt}" id="Total"
												onkeypress="return isNumberAndDot(event,this);"
												cssClass="form-control" />

											<div class="has-error">
												<form:errors path="total" cssClass="help-inline" />
											</div>
										</div>
									</div>
								</div>
								<div class="form-group button-group-set">
									<div class="col-sm-12">
										<c:choose>
											<c:when test="${edit}">
												<button type="submit" name="saveBtn" id="saveBtn"
													value="Save" style="width: 17% !important"
													class="btn btn-primary btn-sm saveBtn">
													<i class="fa fa-floppy-o"></i> Update & Approve
												</button>
												<button type="submit" name="saveBtn" id="saveAndNewBtn"
													value="Cancel" class="btn btn-primary btn-sm saveBtn">
													Cancel Request</button>
											</c:when>
											<c:otherwise>
												<button type="submit" name="saveBtn" id="saveBtn"
													value="Save" class="btn btn-primary btn-sm saveBtn">
													<i class="fa fa-floppy-o"></i> Save
												</button>
												<button type="submit" name="saveBtn" id="saveAndNewBtn"
													value="SaveAndNew" class="btn btn-primary btn-sm saveBtn">
													<i class="fa fa-floppy-o"></i> Save And New
												</button>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

			</form:form>
		</div>
	</section>
</c:if>
<script type="text/javascript">
	/* function checkForm(form)
	 {
	 alert("hello.."+form);
	 document.getElementById("NewEditRoleForm").submit();
	 form.saveBtn.disabled = true;
	 form.saveBtn.value = "Please wait...";
	 return true;
	 } */

	function validateFields() {
		var amount = $('#Total').val();
		console.log("amount val is : "+amount);
		if(amount == ""){
			alert("Please enter valid amount");
			return false;
		}
	}

	$(document)
			.ready(
					function() {
						$("#NewEditUpdateForm")
								.submit(
										function(event) {
											if (confirm('Are you sure that you want to process the request ?')) {
												var amount = $('#Total').val();
												console.log("amount val is : "+amount);
												if(amount == ""){
													alert("Please enter valid amount");
													return false;
												}
												return true;
											} else {
												return false;
											}
										});

						$("#NewEditRoleForm").submit(function() {
							$(".saveBtn").css("display", "none");
							return true;
						});
					});
</script>