<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>




<section class="content-header">
	<h1>Currency Chest</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Currency Chest</li>
		<li class="active">New/Update</li>
	</ol>
</section>

<sec:authorize access="hasRole('can_currencychest_update')">
	<%-- <c:if --%>
	<%-- 	test="${ct:isAllowed('can_currencychest_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
	<section class="content">
		<div class="row">
			<form:form name="myForm" id="NewEditChestForm" method="POST"
				modelAttribute="branchManagement" autocomplete="off"
				class="form-inline">
				<form:hidden path="id" id="id" />
				<form:hidden path="requestFrom" />

				<c:choose>
					<c:when test="${empty branchManagement.urlReferer }">
						<c:set var="referer" value="${header['Referer']}" />
					</c:when>
					<c:otherwise>
						<c:set var="referer" value="${branchManagement.urlReferer}" />
					</c:otherwise>
				</c:choose>
				<form:hidden path="urlReferer" value="${referer}" />

				<div class="col-md-12 table-container-main">
					<div class="box-header custom_header_form_page">
						<div class="heading-title-wrapper">
							<i class="fa fa-gear"></i>
							<c:choose>
								<c:when test="${edit}">
									<h3 class="box-title">
										Edit Chest - <span class="edit-module-name">${branchManagement.branchName}</span>
									</h3>
								</c:when>
								<c:otherwise>
									<h3 class="box-title">New Chest</h3>
								</c:otherwise>
							</c:choose>
						</div>

						<div class="btn-group custom_group" role="group"
							aria-label="Basic example">
							<c:choose>
								<c:when test="${branchManagement.requestFrom == 'byhierarchy'}">
									<a class="btn btn-success" href='${referer}'> <i
										class="fa fa-arrow-left"></i> Back
									</a>
								</c:when>
								<c:otherwise>
									<a class="btn btn-success" href='${referer}'> <i
										class="fa fa-arrow-left"></i> Back
									</a>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="box-body custom_form_body">
						<sec:authorize access="hasRole('can_view_everything')">
							<c:choose>
								<c:when test="${edit}">
								</c:when>
								<c:otherwise>
									<c:set property="orgManagement" target="${branchManagement}"
										value=""></c:set>
								</c:otherwise>
							</c:choose>
							<jsp:include page="../UtilityFiles/OrgToOrgManagement.jsp"></jsp:include>
						</sec:authorize>
						<sec:authorize access="!hasRole('can_view_everything')">
							<div class="form-group col-sm-4">
								<label class="control-label col-sm-4 "><fmt:message
										key="Common.form.Organization" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"></span>

										<form:input path="orgManagement.orgType.orgType"
											id="orgManagementOrgTypeName" readonly="true"
											cssClass="form-control" />
									</div>
								</div>
							</div>
							<div class="form-group col-sm-4">
								<form:hidden path="orgManagement.id" id="orgManagementId" />
								<label class="control-label col-sm-4"><fmt:message
										key="Common.form.OrganizationName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"></span>

										<form:input path="orgManagement.name" id="orgManagementName"
											readonly="true" cssClass="form-control" />
									</div>
								</div>
							</div>

							<div class="form-group col-sm-4">
								<form:hidden path="hierarchyControl.id" id="hierarchyControlId" />
								<label class="control-label col-sm-4 "><fmt:message
										key="Common.form.HierarchyName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-address-book-o" aria-hidden="true"></i></span>

										<form:input path="hierarchyControl.name"
											id="hierarchyControlName" readonly="true"
											cssClass="form-control" />
									</div>
								</div>
							</div>
						</sec:authorize>

						<%-- <c:choose>
							<c:when
								test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
								<c:choose>
									<c:when test="${edit}">
									</c:when>
									<c:otherwise>
										<c:set property="orgManagement" target="${branchManagement}"
											value=""></c:set>
									</c:otherwise>
								</c:choose>
								<jsp:include page="../UtilityFiles/OrgToOrgManagement.jsp"></jsp:include>

							</c:when>
							<c:otherwise>
								<div class="form-group col-sm-4">
									<label class="control-label col-sm-4 "><fmt:message
											key="Common.form.Organization" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>

											<form:input path="orgManagement.orgType.orgType"
												id="orgManagementOrgTypeName" readonly="true"
												cssClass="form-control" />
										</div>
									</div>
								</div>
								<div class="form-group col-sm-4">
									<form:hidden path="orgManagement.id" id="orgManagementId" />
									<label class="control-label col-sm-4"><fmt:message
											key="Common.form.OrganizationName" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>

											<form:input path="orgManagement.name" id="orgManagementName"
												readonly="true" cssClass="form-control" />
										</div>
									</div>
								</div>

								<div class="form-group col-sm-4">
									<form:hidden path="hierarchyControl.id" id="hierarchyControlId" />
									<label class="control-label col-sm-4 "><fmt:message
											key="Common.form.HierarchyName" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="fa fa-address-book-o" aria-hidden="true"></i></span>

											<form:input path="hierarchyControl.name"
												id="hierarchyControlName" readonly="true"
												cssClass="form-control" />
										</div>
									</div>
								</div>


							</c:otherwise>
						</c:choose> --%>

						<div id="branchControlDiv" class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="BranchManagement.form.BranchControl" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:select path="branchControl" cssClass="form-control"
										id="branchControlId">
										<form:option value="-1" label="- Select Reporting Entity -" />
										<form:options items="${branchList}" itemValue="id"
											itemLabel="branchName" />
									</form:select>
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchControlName" cssClass="help-inline" />
							</div>
							<div class="has-error">
								<form:errors path="branchControl" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestName" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="branchName" id="branchName"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchName" cssClass="help-inline" />
							</div>
						</div>


						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.Abbreviation" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="abbreviation" id="abbreviation"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="abbreviation" cssClass="help-inline" />
							</div>
						</div>


						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestAddress1" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchAdd1" id="branchAdd1"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchAdd1" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestAddress2" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchAdd2" id="branchAdd2"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchAdd2" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<span class="has-error text-center">${chestCodeAlreadyExist}</span>
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestCode" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchCode" id="branchCode"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchCode" cssClass="help-inline" />
							</div>
						</div>


						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestState" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchState" id="branchState"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchState" cssClass="help-inline" />
							</div>
						</div>


						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestCity" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchCity" id="branchCity"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchCity" cssClass="help-inline" />
							</div>
						</div>


						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestZip" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchZip" id="branchZip"
										cssClass="form-control"
										onkeypress='return validateQty(event);' />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchZip" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestIfscCode" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchIfscCode" id="branchIfscCode"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchIfscCode" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestContactNo" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchContactNo" id="branchContactNo"
										cssClass="form-control"
										onkeypress='return validateQty(event);' />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchContactNo" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestDetails" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"></span>

									<form:input path="branchDetail" id="branchDetail"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchDetail" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestEmail" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchEmail" id="branchEmail"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchEmail" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestLatitude" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchLatitude" id="branchLatitude"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchLatitude" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestLongitude" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchLongitude" id="branchLongitude"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchLongitude" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.Vicinity" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="vicinity" id="vicinity"
										cssClass="form-control"
										onkeypress='return validateQty(event);' />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="vicinity" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestLocation" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchLocation" id="branchLocation"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchLocation" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestCashLimit" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchCashlimit" id="branchCashlimit"
										cssClass="form-control"
										onkeypress='return validateQty(event);' />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchCashlimit" cssClass="help-inline" />
							</div>
						</div>

						<%-- <div class="form-group col-sm-4">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestDeviationPercentage" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="percentage" id="percentage"
										cssClass="form-control"
										onkeypress='return validateQty(event);' />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="percentage" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4 checklist-section">
							<label id="radioLabel" class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestType" /></label>
							<div class="col-sm-12 m10">

								<form:radiobutton path="branchType"
									onClick="chkPanelChanged(this)" value="0" id="radio1" />
								Participating
								<form:radiobutton path="branchType"
									onClick="chkPanelChanged(this)" value="1" id="radio2" />
								Non Participating
							</div>
						</div>

						<div class="form-group col-sm-4 checklist-section">
							<label id="radioLabel" class="control-label col-sm-4 "><fmt:message
									key="BranchManagement.form.RequestApproval" /></label>
							<div class="col-sm-12 m10">
								<form:radiobutton path="requestApprovalType"
									onClick="chkPanelChanged(this)" value="0" id="radio3" />
								Automatic
								<form:radiobutton path="requestApprovalType"
									onClick="chkPanelChanged(this)" value="1" id="radio4" />
								Manual
							</div>
						</div>
 --%>
						<div class="form-group col-sm-4" id="approverDiv">
							<label class="control-label col-sm-4 "><fmt:message
									key="CurrencyChest.form.chestApprovers" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchApprovers" id="branchApprovers"
										cssClass="form-control"
										onkeypress='return validateQty(event);' />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchApprovers" cssClass="help-inline" />
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
											class="btn btn-primary btn-sm saveBtn">
											<i class="fa fa-floppy-o"></i> Save
										</button>
										<!-- <button type="submit" name="saveBtn" value="SaveAndNew" class="btn btn-primary btn-sm">
												<i class="fa fa-floppy-o"></i> Save And New
											</button> -->
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${branchManagement.requestFrom == 'byhierarchy'}">
										<a class="btn btn-danger btn-sm"
											href="<c:url value='${referer}' />"> <i
											class="fa fa-times"></i> Cancel
										</a>
									</c:when>
									<c:otherwise>
										<a class="btn btn-danger btn-sm"
											href="<c:url value='${referer}' />"> <i
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
	var baseUrl = "${pageContext.request.contextPath}/getBranchManagementOfHeadOffice";
	var orgManagementId=$("#orgManagementId").val();
	
	$("#orgManagementId").change(function(){
		brachManagementAutoComplete();
	});
	$(document).ready(function(){
		brachManagementAutoComplete();
		$("#branchControlId option[value='${branchManagement.branchControl}']").attr("selected", "true");
	});
	function brachManagementAutoComplete(orgManagementId)
	{
		orgManagementId=$("#orgManagementId").val();
		//alert("orgManagementId:::>>"+orgManagementId);
		$('#branchControlId').empty();
		
		$.ajax({
			url : baseUrl + addOrgManagementId(orgManagementId),
			dataType : 'json',			
			type : "get",
			async: false,
		}).done(function(data) {
			console.log("second success : " + data.length);
			$("#branchControlId").append(
					'<option value=' + -1 + '>'
							+ 'Select Reporting Entity' + '</option>');
			for ( var i in data) {
				$("#branchControlId").append(
						'<option value=' + data[i].id + '>'
								+ data[i].branchName + '</option>');
			}
			console.log(data);
		}).fail(function() {
			console.log("error in finding parent hierarcy branches");
			$("#branchControlId").append(
					'<option value=' + -1 + '>'
							+ 'Select Reporting Entity' + '</option>');
		}).always(function() {
			console.log("complete in finding parent hierarcy branches");
		});
	}
	/* {
		$('#branchManagementName').autocomplete(
				{
					autoSelectFirst : true,
					serviceUrl : baseUrl + addOrgManagementId(orgManagementId),
					paramName : "tagName",
					delimiter : ",",
					onSelect : function(suggestion) {
						$("#branchControlId").val(suggestion.data);
						return false;
					},
					transformResult : function(response) {
						return {
							//must convert json to javascript object before process
							suggestions : $.map($.parseJSON(response), function(item) {
								return {
									value : item.branchName,
									data : item.id,
									branchType : item.branchType
								};
							})
						};
					}
				});
	} */
	
	
	
	
	
	
	
	
	
	
	
	/* $("#hierarchyControlId").ready(
			function() {
				var hierarchyControlId=$("#hierarchyControlId").val();
				if (hierarchyControlId!= ""	|| hierarchyControlId != "-1") {
					brachManagementAutoComplete();
				}
					
			}); */
	$("#hierarchyControlId").change(function() {

		$("#branchManagementName").val("");
		$("#branchControlId").val("");
		$('#designationId').empty();

		console.log("hierarchyControlId2::"+$("#hierarchyControlId").val());
		getDesignationsByHierarchyId();
		brachManagementAutoComplete();
	});

	function addOrgManagementId(addOrgManagementId) {
		return '?orgManagementId=' + addOrgManagementId;
	}

	function addHierarchyId() {
		return '?hierarchyId=' + $('#hierarchyControlId').val();
	}

	function getDesignationsByHierarchyId() {
		var id = $('#hierarchyControlId').val();
		$.ajax({
			url : "getHierarchyStatusByHierarchyId",
			dataType : 'json',
			data : {
				hId : id,
			},
			type : "post",
		}).done(function(result) {
			console.log("parent id for hierarchy id is : " + result.parentId);
			console.log("hierarchy name for hierarchy id : " + result.name);
			if (result.parentId > 0) {
				$('#branchManagementName').prop('readonly', false);
				$('#branchControlId').val('');
				console.log("true");
			} else {
				$('#branchManagementName').prop('readonly', true);
				$('#branchControlId').val(0);
				console.log("false");
			}

		}).fail(function(e) {
			console.log(e);
		});
	}
</script>

<script>


	$(document).ready(function(){
		$("#NewEditBranchForm").submit(function(){
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
		else if (key < 48 || key > 57) {
		    return false;
		}
		else return true;
	}
	
	function chkPanelChanged(t) {	
		var radioVal = t.value;
		if(radioVal === "0"){
			/*document.getElementById("branchApprovers").value="";
			document.getElementById('approverDiv').style.display = "none"; */
			$("#branchApprovers").val("");
			$("#approverDiv").hide();

			}else {
			//document.getElementById('approverDiv').style.display = "block";
			$("#approverDiv").show();
			}
	}
</script>

<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>


