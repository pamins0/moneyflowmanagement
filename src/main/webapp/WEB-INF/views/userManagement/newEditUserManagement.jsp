<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<script src="./static/js/jquery-ui.js"></script>

<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">



<script>
    $(function() {
    	var date = new Date();
    	var fromMinDate = new Date(date.getFullYear(), date.getMonth(), date.getDate()); 
    	var toMinDate = new Date(date.getFullYear(), date.getMonth(), date.getDate()) ;
    	var endMinDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    	var todayDate = new Date();
    
        var newOrEdit = '${edit}';
       // alert("123"+ newOrEdit + !(newOrEdit));
    	
    
        fromMinDate = new Date('${user.dateOfBirth}');
    	toMinDate = new Date('${user.dateOfJoining}');
    	endMinDate = new Date('${user.postingStartDate}');
    		
        fromMinDate = new Date(fromMinDate.getFullYear() + 18, fromMinDate.getMonth(), fromMinDate.getDate() + 1);
    	toMinDate = new Date(toMinDate.getFullYear(), toMinDate.getMonth(), toMinDate.getDate() + 1);
    	endMinDate = new Date(endMinDate.getFullYear(), endMinDate.getMonth(), endMinDate.getDate() + 1);
    	
    	
    	if(newOrEdit == 'false')
    	{
    		 //alert("12");
    		 $("#dateOfJoining").attr('disabled', 'disabled');
             $("#postingStartDate").attr('disabled', 'disabled');
             $("#postingEndDate").attr('disabled', 'disabled');
    	}
    	
    	    	
    	$("#dateOfBirth").datepicker({
    		maxDate : todayDate,
    		dateFormat: 'dd-mm-yy',
            onSelect: function(selectedDate1) {

			    selectedDate1 = selectedDate1.split("-");
			    selectedDate1 = new Date(selectedDate1[2], selectedDate1[1] - 1, selectedDate1[0]);
			   
			    if(newOrEdit){
                $("#dateOfJoining").removeAttr('disabled');
                $("#postingStartDate").attr('disabled', 'disabled');
                $("#postingEndDate").attr('disabled', 'disabled');
			    }

                fromMinDate = new Date(selectedDate1.getFullYear() + 18, selectedDate1.getMonth(), selectedDate1.getDate() + 1);
                $("#dateOfJoining").datepicker("option", "minDate", fromMinDate);
				$('#dateOfJoining').val("");
                $('#postingStartDate').val("");
                $('#postingEndDate').val("");

                
            }
        });
           
          $("#dateOfJoining").datepicker({
              minDate: fromMinDate,//"Fri Apr 14 2017 00:00:00 GMT+0530 (India Standard Time)",
		      dateFormat: 'dd-mm-yy',
              onSelect: function(selectedDate2) {

                  selectedDate2 = selectedDate2.split("-");
                  selectedDate2 = new Date(selectedDate2[2], selectedDate2[1] - 1, selectedDate2[0]);

                  if(newOrEdit){
                  $("#postingStartDate").removeAttr('disabled');
                  $("#postingEndDate").attr('disabled', 'disabled');
                  }
                  
                  toMinDate = new Date(selectedDate2.getFullYear(), selectedDate2.getMonth(), selectedDate2.getDate() + 1);
                  $("#postingStartDate").datepicker("option", "minDate", toMinDate);
                  $('#postingStartDate').val("");
                  $('#postingEndDate').val("");
              }
          });
          
          $("#postingStartDate").datepicker({
              minDate: toMinDate,
              dateFormat: 'dd-mm-yy',
              onSelect: function(selectedDate3) {

                  selectedDate3 = selectedDate3.split("-");
                  selectedDate3 = new Date(selectedDate3[2], selectedDate3[1] - 1, selectedDate3[0]);

                  if(newOrEdit){
                  $("#postingEndDate").removeAttr('disabled');
                  }
                  
                  endMinDate = new Date(selectedDate3.getFullYear(), selectedDate3.getMonth(), selectedDate3.getDate() + 1);
                  $("#postingEndDate").datepicker("option", "minDate", endMinDate);
                  $('#postingEndDate').val("");
              }
          });
          
          $("#postingEndDate").datepicker({
              minDate: endMinDate,
              dateFormat: 'dd-mm-yy',
              onSelect: function(selectedDate4) {

                  selectedDate4 = selectedDate4.split("-");
                  selectedDate4 = new Date(selectedDate4[2], selectedDate4[1] - 1, selectedDate4[0]);

              }
          });
    });
</script>



<section class="content-header">
	<h1>User Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>User</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<%-- <c:if
	test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
<section class="content">
	<div class="row">
		<form:form id="NewEditUserForm" method="POST" modelAttribute="user"
			autocomplete="off" class="form-inline">
			<fmt:message key="yyyy.MM.dd" var="pattern" />
			<fmt:message key="dd-MM-yyyy" var="pattern_1" />
			<form:hidden path="id" />
			<form:hidden path="wayToCreate" value="${direct}" />
			<c:choose>
				<c:when test="${empty user.urlReferer }">
					<c:set var="referer" value="${header['Referer']}" />
				</c:when>
				<c:otherwise>
					<c:set var="referer" value="${user.urlReferer}" />
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
									Edit User - <span class="edit-module-name">
										${user.firstName} ${user.secondName} ${user.lastName}</span>
								</h3>
							</c:when>
							<c:otherwise>
								<h3 class="box-title">New User</h3>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='${referer}'> <i
							class="fa fa-arrow-left"></i> Back
						</a>
					</div>
				</div>
				<div class="box-body custom_form_body">
					<c:choose>
						<c:when test="${direct}">
							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12 "><fmt:message
										key="Common.form.Organization" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"></span>

										<form:input
											path="branchManagement.orgManagement.orgType.orgType"
											id="branchManagementOrgManagementOrgTypeName" readonly="true"
											cssClass="form-control" />
									</div>
								</div>
							</div>
							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12"><fmt:message
										key="Common.form.OrganizationName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon">*</span>

										<form:input path="branchManagement.orgManagement.name"
											id="branchManagementOrgManagementName" readonly="true"
											cssClass="form-control" />
									</div>
								</div>
							</div>
						</c:when>
					</c:choose>

					<c:choose>
						<c:when test="${direct}">
							<div class="form-group col-sm-4">
								<form:hidden path="branchManagement.id" id="branchManagementId" />
								<label class="control-label col-sm-12"><fmt:message
										key="Common.form.BranchName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"></span>

										<form:input path="branchManagement.branchName"
											id="branchManagementbBranchName" readonly="true"
											cssClass="form-control" />
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12"><fmt:message
										key="Common.form.HierarchyName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i>*</i></span>

										<form:select path="branchManagement.hierarchyControl.id"
											cssClass="form-control" id="hierarchyId">
											<form:option value="-1" label="- Select Hierarchy -" />
											<form:options items="${hierarchyList}" itemValue="id"
												itemLabel="name" />
										</form:select>
										<div class="has-error">
											<form:errors path="branchManagement.hierarchyControl.id"
												cssClass="help-inline" />
										</div>
									</div>
								</div>
							</div>

							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12"><fmt:message
										key="Common.form.BranchName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i>*</i></span>

										<form:input placeholder="Enter Entity Name"
											path="branchManagement.branchName" id="branchManagementName"
											cssClass="form-control" />
										<!--<input type="text" value="" id="branchManagementId" /> -->
										<form:hidden path="branchManagement.id"
											id="branchManagementId" />
									</div>
								</div>
								<div class="has-error">
									<form:errors path="branchManagement.branchName"
										cssClass="help-inline" />
								</div>
							</div>

						</c:otherwise>
					</c:choose>

					<div class="form-group col-sm-4">
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.Name" /></label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"><i>*</i></span>

								<form:input path="firstName" id="firstName"
									cssClass="form-control" />
							</div>
						</div>
						<div class="has-error">
							<form:errors path="firstName" cssClass="help-inline" />
						</div>
					</div>

					<div class="form-group col-sm-4">
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.SecondName" /></label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"></span>

								<form:input path="secondName" id="secondName"
									cssClass="form-control" />
							</div>
						</div>
						<div class="has-error">
							<form:errors path="secondName" cssClass="help-inline" />
						</div>
					</div>

					<div class="form-group col-sm-4">
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.LastName" /></label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"><i>*</i></span>

								<form:input path="lastName" id="lastName"
									cssClass="form-control" />
							</div>
						</div>
						<div class="has-error">
							<form:errors path="lastName" cssClass="help-inline" />
						</div>
					</div>


					<div class="form-group col-sm-4 Wrapper-gender">
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.Gender" /></label>
						<div class="col-sm-12">

							<div class="gender-m">
								<form:radiobutton onchange="check(this)" path="gender" value="1" />
								MALE
							</div>
							<div class="gender-m">
								<form:radiobutton onchange="check(this)" path="gender" value="2" />
								FEMALE
							</div>
							<div class="gender-m">
								<form:radiobutton onchange="check(this)" path="gender" value="3" />
								OTHER
							</div>
						</div>
						<div class="has-error">
							<form:errors path="gender" cssClass="help-inline" />
						</div>
					</div>


					<div class="form-group col-sm-4">
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.EPFONO" /></label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"></span>

								<form:input path="epfo_no" id="epfo_no" cssClass="form-control" />
							</div>
						</div>
						<div class="has-error">
							<form:errors path="epfo_no" cssClass="help-inline" />
						</div>
					</div>

					<div class="form-group col-sm-4">
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.DateOfBirth" /></label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"></span>

								<fmt:formatDate value="${user.dateOfBirth}"
									var="dateOfBirthString" pattern="${pattern_1}" />
								<form:input path="dateOfBirth" readonly="true"
									value="${dateOfBirthString}" id="dateOfBirth"
									cssClass="form-control" disabled="disabled" />
							</div>
						</div>
						<div class="has-error">
							<form:errors path="dateOfBirth" cssClass="help-inline" />
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.ContactNo" /></label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"><i>*</i></span>
								<form:input path="contactNo" id="contactNo"
									cssClass="form-control" onkeypress='return validateQty(event);' />
							</div>
						</div>
						<div class="has-error">
							<form:errors path="contactNo" cssClass="help-inline" />
						</div>
					</div>

					<div class="form-group col-sm-4">
						<span class="has-error text-center">${userEmailAlreadyExist}</span>
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.Email" /></label>
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
								key="UserManagement.form.UserCode" /></label>
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"><i>*</i></span>

								<form:input path="userCode" id="userCode"
									cssClass="form-control" />
							</div>
						</div>
						<div class="has-error">
							<form:errors path="userCode" cssClass="help-inline" />
						</div>
					</div>

					<div class="form-group col-sm-4">
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.DateOfJoining" /></label>
						<fmt:formatDate value="${user.dateOfJoining}"
							var="dateOfJoiningString" pattern="${pattern_1}" />
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"></span>

								<form:input path="dateOfJoining" readonly="true"
									value="${dateOfJoiningString }" id="dateOfJoining"
									cssClass="form-control" disabled="disabled" />
							</div>
						</div>
						<div class="has-error">
							<form:errors path="dateOfJoining" cssClass="help-inline" />
						</div>
					</div>

					<div class="form-group col-sm-4">
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.PostingStartDate" /></label>
						<fmt:formatDate value="${user.postingStartDate}"
							var="postingStartDateString" pattern="${pattern_1}" />
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"></span>

								<form:input path="postingStartDate" readonly="true"
									value="${postingStartDateString }" id="postingStartDate"
									cssClass="form-control" disabled="disabled" />
							</div>
						</div>
						<div class="has-error">
							<form:errors path="postingStartDate" cssClass="help-inline" />
						</div>
					</div>

					<div class="form-group col-sm-4">
						<label class="control-label col-sm-12"><fmt:message
								key="UserManagement.form.PostingEndDate" /></label>
						<fmt:formatDate value="${user.postingEndDate}"
							var="postingEndDateString" pattern="${pattern_1}" />
						<div class="col-sm-12">
							<div class="input-group">
								<span class="input-group-addon"></span>

								<form:input path="postingEndDate" readonly="true"
									value="${postingEndDateString}" id="postingEndDate"
									cssClass="form-control" />
							</div>
						</div>
						<div class="has-error">
							<form:errors path="postingEndDate" cssClass="help-inline" />
						</div>
					</div>


					<c:choose>
						<c:when test="${edit}">
							<!-- 							<h3 class="box-title">Edit User</h3> -->
						</c:when>
						<c:otherwise>
							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12"><fmt:message
										key="UserManagement.form.Password" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i>*</i></span>

										<form:password path="userPassword" id="userPassword"
											cssClass="form-control" onkeypress="return AvoidSpace(event)" />
									</div>
								</div>
								<div class="has-error">
									<form:errors path="userPassword" cssClass="help-inline" />
								</div>
							</div>
							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12"><fmt:message
										key="UserManagement.form.ConfirmPassword" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i>*</i></span>

										<form:password path="confirmPassword" id="confirmPassword"
											cssClass="form-control" onkeypress="return AvoidSpace(event)" />
									</div>
								</div>
								<div class="has-error">
									<form:errors path="confirmPassword" cssClass="help-inline" />
								</div>
							</div>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="${edit}">
							<!-- 							<h3 class="box-title">Edit User</h3> -->
						</c:when>
						<c:otherwise>
							<div class="form-group col-sm-4">
								<span class="has-error text-center">${useridAlreadyExist}</span>
								<label class="control-label col-sm-12"><fmt:message
										key="UserManagement.form.UserId" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i>*</i></span>

										<form:input path="userId" id="userId" cssClass="form-control" />
									</div>
								</div>
								<div class="has-error">
									<form:errors path="userId" cssClass="help-inline" />
								</div>
							</div>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="${direct}">
							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12"><fmt:message
										key="Designation.form.DesignationName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i>*</i></span>

										<form:select path="designation.id" cssClass="form-control"
											id="designationId">
											<form:option value="-1" label="- Designation -" />
											<form:options
												items="${user.branchManagement.hierarchyControl.designation}"
												itemValue="id" itemLabel="title" />
										</form:select>
									</div>
								</div>
								<div class="has-error">
									<form:errors path="designation.id" cssClass="help-inline" />
								</div>
							</div>

							<!-- Department select -->

							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12"><fmt:message
										key="Department.form.DepartmentName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i>*</i></span>

										<form:select path="department.id" cssClass="form-control"
											id="departmentId">
											<form:option value="-1" label="- Department -" />
											<form:options
												items="${user.branchManagement.hierarchyControl.department}"
												itemValue="id" itemLabel="title" />
										</form:select>
									</div>
								</div>
								<div class="has-error">
									<form:errors path="department.id" cssClass="help-inline" />
								</div>
							</div>
						</c:when>

						<c:otherwise>
							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12"><fmt:message
										key="Designation.form.DesignationName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i>*</i></span>

										<form:select path="designation.id" cssClass="form-control"
											id="designationId">
											<form:option value="-1" label="- Designation -" />
											<form:options
												items="${user.branchManagement.hierarchyControl.designation}"
												itemValue="id" itemLabel="title" />
										</form:select>
									</div>
								</div>
								<div class="has-error">
									<form:errors path="designation.id" cssClass="help-inline" />
								</div>
							</div>

							<!-- Department select -->

							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12"><fmt:message
										key="Department.form.DepartmentName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i>*</i></span>

										<form:select path="department.id" cssClass="form-control"
											id="departmentId">
											<form:option value="-1" label="- Department -" />
											<form:options
												items="${user.branchManagement.hierarchyControl.department}"
												itemValue="id" itemLabel="title" />
										</form:select>
									</div>
								</div>
								<div class="has-error">
									<form:errors path="department.id" cssClass="help-inline" />
								</div>
							</div>
						</c:otherwise>
					</c:choose>

					<div class="form-group button-group-set">
						<div class="col-sm-12">
							<c:choose>
								<c:when test="${edit}">
									<button type="submit" class="btn btn-primary btn-sm">
										<i class="fa fa-check"></i> Update
									</button>
									<a class="btn btn-danger btn-sm"
										href="<c:url value='${referer}' />"><i class="fa fa-times"></i>
										Cancel</a>
								</c:when>
								<c:otherwise>
									<button type="submit" class="btn btn-primary btn-sm saveBtn"
										onclick="return Validate()">
										<i class="fa fa-floppy-o"></i> Save
									</button>
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
<%-- <c:if test="${user.branchManagement.branchType == 1}">
	<script>
		$('#approverDiv').show(true);
	</script>
</c:if> --%>
<script>
	var baseUrl = "${pageContext.request.contextPath}/getBranchManagementForHierarchyAutoComplete";
	$("#hierarchyId").ready(function() {
		if($("#hierarchyId").val() != "" || $("#hierarchyId").val() != "-1"){
			//$("#branchManagementName").val("");
			//$("#branchManagementId").val("");
		//	$('#designationId').empty();
			var hierarchyId = $("#hierarchyId").val();
		//	getDesignationsByHierarchyId();
			console.log("hierarchy id : "+hierarchyId);
			$('#branchManagementName').autocomplete({
				autoSelectFirst : true,
				serviceUrl : baseUrl + addHierarchyId(),
				paramName : "tagName",
				delimiter : ",",
				onSelect : function(suggestion) {
					$("#branchManagementId").val(suggestion.data);
					//getDesignationsByHierarchyId();
					/* if (suggestion.branchType == 1) {
						$('#approverDiv').show(true);
						console.log("true");
					} else {
						$('#approverDiv').hide(true);
						console.log("false");
					} */
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
		}
		
		
	});
	$("#hierarchyId").change(function() {

		$("#branchManagementName").val("");
		$("#branchManagementId").val("");
		$('#designationId').empty();
		getDesignationsByHierarchyId();
		getDepartmentsByHierarchyId();

		$('#branchManagementName').autocomplete({
			autoSelectFirst : true,
			serviceUrl : baseUrl + addHierarchyId(),
			paramName : "tagName",
			delimiter : ",",
			onSelect : function(suggestion) {
				$("#branchManagementId").val(suggestion.data);				
				/* if (suggestion.branchType==1) {
					$('#approverDiv').show(true);
					console.log("true");
				} else {
					$('#approverDiv').show(false);
					console.log("false");
				} */
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
	});

	function addHierarchyId() {
		return '?hierarchyId=' + $('#hierarchyId').val();
	}
	
	function getDesignationsByHierarchyId() {
		
		var id = $('#hierarchyId').val();
		console.log("hierarchy id in department fetching is : "+id);
		$.ajax({
			url : "getDepartmentsByHierarchyId",
			dataType : 'json',
			data : {
				hId : id,
			},
			type : "post",
		}).done(
				function(result) {
					$('#departmentId').empty();
					$('#departmentId').append(
							$("<option></option>").attr("value", "-1").text("-Select Department-"));
					result.forEach(function(res) {
						console.log(res.title);
						$('#departmentId').append(
								$("<option></option>").attr("value", res.id).text(res.title));
					});					
				}).fail(function(e) {
			console.log(e);
		});
	}
	
	function getDepartmentsByHierarchyId() {
			
			var id = $('#hierarchyId').val();
			console.log("hierarchy id in designation fetching is : "+id);
			$.ajax({
				url : "getDesignationsByHierarchyId",
				dataType : 'json',
				data : {
					hId : id,
				},
				type : "post",
			}).done(
					function(result) {
						$('#designationId').empty();
						$('#designationId').append(
								$("<option></option>").attr("value", "-1").text("-Select Designation-"));
						result.forEach(function(res) {
							console.log(res.title);
							$('#designationId').append(
									$("<option></option>").attr("value", res.id).text(res.title));
						});					
					}).fail(function(e) {
				console.log(e);
			});
		}
	
</script>
<script type="text/javascript">
 	
 	$(document).ready(function(){
		$("#NewEditUserForm").submit(function(){
			$(".saveBtn").css("display", "none");
			return true;
		});
	});
 	
 	function AvoidSpace(event) {
 	    var k = event ? event.which : window.event.keyCode;
 	    if (k == 32) return false;
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
	
 
    function Validate() {
        var password = document.getElementById("userPassword").value;
        var confirmPassword = document.getElementById("confirmPassword").value; 
        if (password != confirmPassword) {
            alert("Passwords do not match.");
            return false;
        }
        return true;
    }
</script>
<%-- </c:if> --%>