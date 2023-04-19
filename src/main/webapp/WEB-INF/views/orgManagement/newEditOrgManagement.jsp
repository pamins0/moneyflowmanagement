<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="./static/js/countries.js"></script>
<!-- <div class="form-group">
	<select class="form-control"
		onchange="print_state('state',this.selectedIndex);" id="country"
		name="country" required="required">
		<option value="">-Country-</option>
	</select>
</div> -->
<!-- <div class="form-group">
	<select class="form-control" name="state" id="state"
		required="required">
		<option value="">-State-</option>
	</select>
</div>
<div class="form-group">
	<input type="text" class="form-control" name="city" id="idcity"
		placeholder="City" required="required">
</div> -->

<section class="content-header">
	<h1>Organization Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Organization</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<sec:authorize access="hasRole('can_orgmanagement_update')">
	<%-- <c:if --%>
	<%-- 	test="${ct:isAllowed('can_orgmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>

	<section class="content">
		<div class="row">
			<form:form id="NewEditOrganizationForm" method="POST"
				modelAttribute="orgManagement" autocomplete="off"
				class="form-inline">
				<form:hidden path="id" />
				<div class="col-md-12 table-container-main">
					<div class="box-header custom_header_form_page">
						<div class="heading-title-wrapper">
							<i class="fa fa-gear"></i>
							<c:choose>
								<c:when test="${edit}">
									<h3 class="box-title">
										Edit Organization - <span class="edit-module-name">${orgManagement.name}</span>
									</h3>
								</c:when>
								<c:otherwise>
									<h3 class="box-title">New Organization</h3>
								</c:otherwise>
							</c:choose>
						</div>

						<div class="btn-group custom_group" role="group"
							aria-label="Basic example">
							<a class="btn btn-success" href='./orgmanagement'> <i
								class="fa fa-arrow-left"></i> Back
							</a>
						</div>
					</div>
					<div class="box-body custom_form_body">
						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12 "><fmt:message
									key="OrgManagement.form.OrgType" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>
									<form:select path="orgType.id" cssClass="form-control">
										<form:option value="-1" label="- Org Type -" />
										<form:options items="${orgTypes}" itemValue="id"
											itemLabel="orgType" />
									</form:select>

								</div>
							</div>
							<div class="has-error">
								<form:errors path="orgType.id" cssClass="help-inline" />
							</div>
						</div>


						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12 "><fmt:message
									key="OrgManagement.form.OrgName" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>
									<form:input path="name" id="name" cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="name" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.Location" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>
									<form:input path="location" id="location"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="location" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.Email" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>
									<form:input path="email" id="email" cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="email" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.ContactNo" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="contactNo" id="contactNo"
										cssClass="form-control"
										onkeypress='return validateQty(event);' />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="contactNo" cssClass="help-inline" />
							</div>
						</div>


						<c:choose>
							<c:when test="${edit}">
								<div class="form-group col-sm-4">
									<label class="control-label col-sm-12"><fmt:message
											key="OrgManagement.form.Levels" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="fa fa-address-book-o" aria-hidden="true"></i></span>
											<form:input readonly="true" path="orgLevel" id="orgLevel"
												cssClass="form-control"
												onkeypress='return validateQty(event);' />
										</div>
									</div>
									<div class="has-error">
										<form:errors path="orgLevel" cssClass="help-inline" />
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="form-group col-sm-4">
									<label class="control-label col-sm-12"><fmt:message
											key="OrgManagement.form.Levels" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"><i>*</i></span>

											<form:input path="orgLevel" id="orgLevel"
												cssClass="form-control"
												onkeypress='return validateQty(event);' />
										</div>
									</div>
									<div class="has-error">
										<form:errors path="orgLevel" cssClass="help-inline" />
									</div>
								</div>
							</c:otherwise>
						</c:choose>

						<div class="clearfix"></div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.Latitude" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="latitude" id="latitude"
										cssClass="form-control" />
									<div class="has-error">
										<form:errors path="latitude" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.Longitude" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="longitude" id="longitude"
										cssClass="form-control" />
									<div class="has-error">
										<form:errors path="longitude" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.Country" /></label>
							<%-- <form:select class="form-control"
								onchange="print_state('state',this.selectedIndex);" id="country"
								path="country" required="required">
								<form:option value="" label="- country -" />  
							</form:select> --%>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="country" placeholder="Country" id="country"
										cssClass="form-control" />
									<div class="has-error">
										<form:errors path="country" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.State" /></label>
							<%-- <form:select
								class="form-control" path="state" id="state" required="required">
								<form:option value="" label="- State -"></form:option>
							</form:select> --%>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

<%-- 									<form:input path="state" placeholder="State" id="state" --%>
<%-- 										cssClass="form-control" /> --%>
										<form:input path="city.state.stateName" cssClass="form-control" id="state-autocomplete-search"/>
                                        <form:hidden path="city.state.id" id="state-autocomplete-id"/>
									<div class="has-error">
										<form:errors path="" cssClass="help-inline"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.City" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

<%-- 									<form:input path="city" class="form-control" id="idcity" --%>
<%-- 										placeholder="City" /> --%>
										<form:input path="city.cityName" cssClass="form-control" id="city-autocomplete-search" onkeypress="myFunction()"/>
                                       <form:hidden path="city.id" id="city-autocomplete-id"/>
									<div class="has-error">
										<form:errors path="city" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>
						<%-- <div class="form-group">
							<label><fmt:message key="OrgManagement.form.State" /></label>
							<form:input path="state" id="state" cssClass="form-control" />
							<div class="has-error">
								<form:errors path="state" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message key="OrgManagement.form.City" /></label>
							<form:input path="city" id="city" cssClass="form-control" />
							<div class="has-error">
								<form:errors path="city" cssClass="help-inline" />
							</div>
						</div> --%>
						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.Zip" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="zip" id="zip" cssClass="form-control"
										onkeypress='return validateQty(event);' />
									<div class="has-error">
										<form:errors path="zip" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group col-sm-4 h100">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.Details" /></label>
							<div class="col-sm-12">



								<form:textarea path="detail" id="detail" cssClass="form-control" />
								<div class="has-error">
									<form:errors path="detail" cssClass="help-inline" />
								</div>
							</div>

						</div>
						<div class="form-group col-sm-4 h100">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.Address1" /></label>
							<div class="col-sm-12">

								<form:textarea path="add1" id="add1" cssClass="form-control" />
								<div class="has-error">
									<form:errors path="add1" cssClass="help-inline" />
								</div>
							</div>
						</div>

						<div class="form-group col-sm-4 h100">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.Address2" /></label>
							<div class="col-sm-12">

								<form:textarea path="add2" id="add2" cssClass="form-control" />
								<div class="has-error">
									<form:errors path="add2" cssClass="help-inline" />
								</div>
							</div>
						</div>



						<%-- <div class="form-group col-sm-4 check-point">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.CmsApproach" /></label>
							<div class="col-sm-12 m10">
								<form:radiobutton onchange="check(this)" path="cmsApproach"
									value="0" />
								Centralized
								<form:radiobutton onchange="check(this)" path="cmsApproach"
									value="1" />
								Decentralized
							</div>
						</div> --%>



						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.Vicinity" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="vicinity" id="vicinity"
										cssClass="form-control"
										onkeypress='return validateQty(event);' />
									<div class="has-error">
										<form:errors path="vicinity" cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="OrgManagement.form.AutoApprovalTime" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>
									<form:input path="autoAprovalRequestTime"
										id="autoAprovalRequestTime" cssClass="form-control"
										onkeypress='return validateQty(event);' />
									<div class="has-error">
										<form:errors path="autoAprovalRequestTime"
											cssClass="help-inline" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group col-sm-4">

							<div class="col-sm-1">
								<div class="input-group">

									<form:checkbox path="currencyChest" id="currencyChest"
										cssClass="form-control" />
									<div class="has-error">
										<form:errors path="currencyChest" cssClass="help-inline" />
									</div>
								</div>
							</div>
							<label class="control-label col-sm-10"><fmt:message
									key="OrgManagement.form.CurrencyChest" /></label>
						</div>
						<%-- <div class="form-group col-sm-4">
							<div class="col-sm-1">
								<div class="input-group">

									<form:checkbox path="intraBank" id="intraBank"
										cssClass="form-control" />
									<div class="has-error">
										<form:errors path="intraBank" cssClass="help-inline" />
									</div>
								</div>
							</div>
							<label class="control-label col-sm-10"><fmt:message
									key="OrgManagement.form.NeedIntraBank" /></label>
						</div> --%>
						<%-- <div class="form-group">
							<label><fmt:message key="OrgManagement.form.MaxTimeForIntraBank" /></label>
							<form:input path="maxTimeForIntraBank" id="maxTimeForIntraBank" cssClass="form-control" onkeypress='return event.charCode >= 48 && event.charCode <= 57'/>
							<div class="has-error">
								<form:errors path="maxTimeForIntraBank" cssClass="help-inline" />
							</div>
						</div> --%>

						<div class="form-group button-group-set">
							<div class="col-sm-12">
								<c:choose>
									<c:when test="${edit}">
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-check"></i> Update
										</button>
										<a class="btn btn-danger btn-sm"
											href="<c:url value='/orgmanagement' />"><i
											class="fa fa-times"></i> Cancel</a>
									</c:when>
									<c:otherwise>
										<button type="submit" class="btn btn-primary btn-sm saveBtn">
											<i class="fa fa-floppy-o"></i> Save
										</button>
										<a class="btn btn-danger btn-sm"
											href="<c:url value='/orgmanagement' />"> <i
											class="fa fa-times"></i> Cancel
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
	<%-- </c:if> --%>
</sec:authorize>

<script>
	$(document).ready(function() {
		$("#NewEditOrganizationForm").submit(function() {
			$(".saveBtn").css("display", "none");
			return true;
		});
	});

	print_country("country");
</script>
<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>
<script>
	var stateURL = "${pageContext.request.contextPath}/statelist";
	
	$('#state-autocomplete-search').autocomplete({
		minChars: 0,
		autoSelectFirst : true,
		appentTo : "#autoParent",
		serviceUrl : stateURL,
		paramName : "tagName",
		delimiter : ",",
		onSelect : function(suggestion) {
			//alert(suggestion.data);
	$("#city-autocomplete-search").val("");
	$("#city-autocomplete-id").val("");
			$("#state-autocomplete-id").val(suggestion.data);
			$("#state-autocomplete-id").attr("value",suggestion.data);
			return false;
		},
		transformResult : function(response) {
			return {
				suggestions : $.map($.parseJSON(response), function(item) {
					return {
						value : item.stateName,
						data : item.id
					};
				})
			};
		}
	});


 	var cityURL = "${pageContext.request.contextPath}/citybystate";
// 	alert("CityURL:::"+cityURL);
	$('#city-autocomplete-search').autocomplete({
		minChars: 0,
		autoSelectFirst : true,
		appentTo : "#autoParent",
		serviceUrl : cityURL,
		paramName : "tagName",
		params: {
	        'stateId': function() {
	            return $('#state-autocomplete-id').val();
	        }
	    },
		delimiter : ",",
		onSelect : function(suggestion) {
			$("#city-autocomplete-id").val(suggestion.data);
			return false;
		},
		transformResult : function(response) {
			return {
				suggestions : $.map($.parseJSON(response), function(item) {
					return {
						value : item.cityName,
						data : item.id
					};
				})
			};
		}
	});

	
</script>
<script>
function myFunction(){
	if($("#state-autocomplete-id").val()==''){
	alert("please select state");
	}
//if($("#location").val().length == 0 ) { $("#finalSearch").attr('disabled', 'true'); 
}
</script>

