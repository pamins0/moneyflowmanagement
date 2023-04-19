<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Entity Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Entity</li>
		<li class="active">New/Update</li>
	</ol>
</section>
<sec:authorize access="hasRole('can_branchmanagement_update')">
	<%-- <c:if --%>
	<%-- 	test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
	<section class="content">
		<div class="row">
			<form:form name="myForm" id="NewEditBranchForm" method="POST"
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
										Edit Entity - <span class="edit-module-name">${branchManagement.branchName}</span>
									</h3>
								</c:when>
								<c:otherwise>
									<h3 class="box-title">New Entity</h3>
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
						<sec:authorize
							access="hasRole('can_view_everything')">
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
						<sec:authorize
							access="!hasRole('can_view_everything')">
							<div class="form-group col-sm-4">
								<label class="control-label col-sm-12 "><fmt:message
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
								<label class="control-label col-sm-12"><fmt:message
										key="Common.form.OrganizationName" /></label>
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"></span>

										<form:input path="orgManagement.name" id="orgManagementName"
											readonly="true" cssClass="form-control" />
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
									<label class="control-label col-sm-12 "><fmt:message
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
									<label class="control-label col-sm-12"><fmt:message
											key="Common.form.OrganizationName" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>

											<form:input path="orgManagement.name" id="orgManagementName"
												readonly="true" cssClass="form-control" />
										</div>
									</div>
								</div>
							</c:otherwise>
						</c:choose> --%>

						<c:choose>
							<c:when test="${minlevels}">
								<div class="form-group col-sm-4">
									<form:hidden path="hierarchyControl.id" id="hierarchyControlId" />
									<label class="control-label col-sm-12 "><fmt:message
											key="Common.form.HierarchyName" /></label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"></span>

											<form:input path="hierarchyControl.name"
												id="hierarchyControlName" readonly="true"
												cssClass="form-control" />
											<form:hidden path="branchControl" id="branchManagementId" />
										</div>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${parent}">
										<div class="form-group col-sm-4">
											<form:hidden path="hierarchyControl.id"
												id="hierarchyControlId" />
											<label class="control-label col-sm-12 "><fmt:message
													key="Common.form.HierarchyName" /></label>
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="fa fa-address-book-o" aria-hidden="true"></i></span>

													<form:input path="hierarchyControl.name"
														id="hierarchyControlName" readonly="true"
														cssClass="form-control" />
													<form:hidden path="branchControl" value="0"
														id="branchManagementId" />
												</div>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="form-group col-sm-4">
											<label class="control-label col-sm-12 "><fmt:message
													key="Common.form.HierarchyName" /></label>
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon"><i>*</i></span>

													<%-- <form:select path="hierarchyControl.id"
														cssClass="form-control" id="hierarchyId">
														<form:option value="-1" label="- Select Hierarchy -" />
														<form:options items="${hierarchyList}" itemValue="id"
															itemLabel="name"/>
													</form:select> --%>
													<form:select path="hierarchyControl.id"
														cssClass="form-control" id="hierarchyId">
														<form:option value="-1" label="- Select Hierarchyg -"
															hierarchytype="0" />
														<c:forEach var="hierarchy" items="${hierarchyList}">
															<form:option value="${hierarchy.id}"
																label="${hierarchy.name}"
																hierarchyType="${hierarchy.hierarchyType}" />
														</c:forEach>
													</form:select>
												</div>
											</div>
											<div class="has-error">
												<form:errors path="hierarchyControl.id"
													cssClass="help-inline" />
											</div>
										</div>

										<div id="branchControlDiv" class="form-group col-sm-4">
											<label class="control-label col-sm-12 "><fmt:message
													key="BranchManagement.form.BranchControl" /></label>
											<div class="col-sm-12">
												<div class="input-group">
													<span class="input-group-addon"><i>*</i></span>

													<form:input placeholder="Enter entity name"
														path="branchControlName" id="branchManagementName"
														cssClass="form-control" />
												</div>
											</div>
											<div class="has-error">
												<form:errors path="branchControlName" cssClass="help-inline" />
											</div>
											<div class="has-error">
												<form:errors path="branchControl" cssClass="help-inline" />
											</div>
											<form:hidden path="branchControl" id="branchManagementId" />
										</div>

									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>


						<div class="form-group col-sm-4" id="autoParent">
							<label class="control-label col-sm-12 "><fmt:message
									key="BranchManagement.form.BranchName" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="branchName" id="branchName"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchName" cssClass="help-inline" />
							</div>
						</div>


						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12 "><fmt:message
									key="BranchManagement.form.Abbreviation" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="abbreviation" id="abbreviation"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="abbreviation" cssClass="help-inline" />
							</div>
						</div>


						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12 "><fmt:message
									key="BranchManagement.form.BranchAddress1" /></label>
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
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.BranchAddress2" /></label>
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
							<span class="has-error text-center">${branchCodeAlreadyExist}</span>
							<label class="control-label col-sm-12 "><fmt:message
									key="BranchManagement.form.BranchCode" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="branchCode" id="branchCode"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchCode" cssClass="help-inline" />
							</div>
						</div>


						<%-- <div class="form-group col-sm-4">
							<label class="control-label col-sm-12 "><fmt:message
									key="BranchManagement.form.BranchState" /></label>
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
						</div> --%>

                         <div class="form-group col-sm-4">
							<label class="control-label col-sm-12 "><fmt:message
									key="BranchManagement.form.BranchState" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

<%-- 									<form:input path="" id="" --%>
<%-- 										cssClass="form-control" /> --%>
										<form:input path="city.state.stateName" cssClass="form-control" id="state-autocomplete-search"/>
                                        <form:hidden path="city.state.id" id="state-autocomplete-id"/>
								</div>
							</div>
							<div class="has-error">
								<form:errors path="" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12 "><fmt:message
									key="BranchManagement.form.City" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

<%-- 									<form:input path="city" id="city" --%>
<%-- 										cssClass="form-control" /> --%>
										<form:input path="city.cityName" cssClass="form-control" id="city-autocomplete-search" onkeypress="myFunction()"/>
                                        <form:hidden path="city.id" id="city-autocomplete-id"/>
								</div>
							</div>
							<div class="has-error">
								<form:errors path="city" cssClass="help-inline" />
							</div>
						</div>


						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.BranchZip" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

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
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.BranchIfscCode" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="branchIfscCode" id="branchIfscCode"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchIfscCode" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.BranchContactNo" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="branchContactNo" id="branchContactNo"
										cssClass="form-control"
										onkeypress='return validateQty(event);' />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchContactNo" cssClass="help-inline" />
							</div>
						</div>

						<%-- 	<div class="form-group">
								<label><fmt:message
										key="BranchManagement.form.BranchControl" /></label>
								<form:input path="branchControl" id="branchControl"
									cssClass="form-control" />
								<div class="has-error">
									<form:errors path="branchControl" cssClass="help-inline" />
								</div>
							</div> --%>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.BranchDetails" /></label>
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
							<span class="has-error text-center">${branchEmailAlreadyExist}</span>
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.BranchEmail" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="branchEmail" id="branchEmail"
										cssClass="form-control" />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchEmail" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.BranchLatitude" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchLatitude" id="branchLatitude"
										cssClass="form-control" onkeypress='return isNumberAndDot(event,this);'/>
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchLatitude" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.BranchLongitude" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="branchLongitude" id="branchLongitude"
										cssClass="form-control" onkeypress='return isNumberAndDot(event,this);'/>
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchLongitude" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.Vicinity" /></label>
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
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.BranchLocation" /></label>
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


						<%-- 						<div class="form-group">
								<label><fmt:message
										key="BranchManagement.form.BranchStatus" /></label>
								<form:input path="branchStatus" id="branchStatus"
									cssClass="form-control" />
								<div class="has-error">
									<form:errors path="branchStatus" cssClass="help-inline" />
								</div>
							</div>
						--%>


						<div class="form-group col-sm-4">
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.BranchCashLimit" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i>*</i></span>

									<form:input path="branchCashlimit" id="branchCashlimit"
										cssClass="form-control"
										onkeypress='return validateQty(event);' />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="branchCashlimit" cssClass="help-inline" />
							</div>
						</div>

						<%-- <div class="form-group col-sm-4 for-branch-only">
							<label class="control-label col-sm-12"><fmt:message
									key="BranchManagement.form.Percentage" /></label>
							<div class="col-sm-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-address-book-o" aria-hidden="true"></i></span>

									<form:input path="percentage" id="percentage"
										cssClass="form-control"
										onkeypress='return isNumberAndDot(event,this);' />
								</div>
							</div>
							<div class="has-error">
								<form:errors path="percentage" cssClass="help-inline" />
							</div>
						</div> --%>

						<%-- <div class="form-group col-sm-4">
								<label class="control-label col-sm-4 "><fmt:message
										key="BranchManagement.form.BranchMinThreshold" /></label>
								<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-address-book-o" aria-hidden="true"></i></span>						
									
								<form:input  path="minThresholdAmount" id="minThresholdAmount"
									cssClass="form-control" onkeypress='return validateQty(event);' />
								<div class="has-error">
									<form:errors path="minThresholdAmount" cssClass="help-inline" />
								</div>
							</div> 
							</div>
							</div>
							
							<div class="form-group col-sm-4">
								<label class="control-label col-sm-4 "><fmt:message
										key="BranchManagement.form.BranchMaxThreshold" /></label>
								<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-address-book-o" aria-hidden="true"></i></span>						
									
								<form:input  path="maxThresholdAmount" id="maxThresholdAmount"
									cssClass="form-control" onkeypress='return validateQty(event);' />
								<div class="has-error">
									<form:errors path="maxThresholdAmount" cssClass="help-inline" />
								</div>
							</div> 
							</div>
							</div> --%>

						<%-- <div class="form-group col-sm-4 checklist-section for-branch-only">
							<label id="radioLabel" class="control-label col-sm-12 "><fmt:message
									key="BranchManagement.form.BranchType" /></label>
							<div class="col-sm-12 m10">

								<form:radiobutton path="branchType"
									onClick="chkPanelChanged(this)" value="0" id="radio1" />
								Participating
								<form:radiobutton path="branchType"
									onClick="chkPanelChanged(this)" value="1" id="radio2" />
								Non Participating
							</div>
						</div> --%>

						<%-- <div class="form-group col-sm-4 checklist-section for-branch-only">
							<label id="radioLabel" class="control-label col-sm-12 "><fmt:message
									key="BranchManagement.form.RequestApproval" /></label>
							<div class="col-sm-12 m10">
								<form:radiobutton path="requestApprovalType"
									onClick="chkPanelChanged(this)" value="0" id="radio3" />
								Automatic
								<form:radiobutton path="requestApprovalType"
									onClick="chkPanelChanged(this)" value="1" id="radio4" />
								Manual
							</div>
						</div> --%>

						<div class="form-group col-sm-4" id="approverDiv">
							<label class="control-label col-sm-12 "><fmt:message
									key="BranchManagement.form.BranchApprovers" /></label>
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
	var baseUrlHierarchy = "${pageContext.request.contextPath}/getHierarchyControlByBranchLevelAndOrgManagement";
	$("#orgManagementId").change(function() {
		hierarchyDropdown();
	});
	$(document)
			.ready(
					function() {
						hierarchyDropdown();
						$(
								"#hierarchyId option[value='${branchManagement.hierarchyControl.id}']")
								.attr("selected", "true");
					});
	function hierarchyDropdown() {
		var orgManagementId = $("#orgManagementId").val();
		//alert("orgManagementId:::>>"+orgManagementId);
		$('#hierarchyId').empty();
		$.ajax({
			url : baseUrlHierarchy + "-" + orgManagementId,
			dataType : 'json',
			type : "get",
			async : false,
		}).done(
				function(data) {
					hierarchyType = "${hierarchy.hierarchyType}"
					console.log("second success : " + data.length);
					$("#hierarchyId").append(
							'<option value=' + -1 + ' hierarchyType="0">'
									+ 'Select Hierarchy' + '</option>');
					for ( var i in data) {
						$("#hierarchyId").append(
								'<option value=' + data[i].id + ' hierarchyType='+data[i].hierarchyType +'>'
										+ data[i].name + '</option>');
					}
					console.log(data);
				}).fail(
				function() {
					console.log("error in finding hierarchies");
					$("#hierarchyId").append(
							'<option value=' + -1 + '>' + 'Select Hierarchy'
									+ '</option>');
				}).always(function() {
			console.log("complete in finding hierarcy");
		});
	}
</script>


<script>
	var baseUrl = "${pageContext.request.contextPath}/getBranchManagementForHierarchyAutoCompleteForBranchControl";
	$("#hierarchyId").ready(
			function() {
				if ($("#hierarchyId").val() != ""
						|| $("#hierarchyId").val() != "-1") {
					//$("#branchManagementName").val("");
					//$("#branchManagementId").val("");
					//	$('#designationId').empty();
					//	getDesignationsByHierarchyId();

					$('#branchManagementName').autocomplete(
							{
								autoSelectFirst : true,
								appentTo : "#autoParent",
								serviceUrl : baseUrl + addHierarchyId(),
								paramName : "tagName",
								delimiter : ",",
								onSelect : function(suggestion) {
									$("#branchManagementId").val(
											suggestion.data);
									return false;
								},
								transformResult : function(response) {
									return {
										//must convert json to javascript object before process
										suggestions : $.map($
												.parseJSON(response), function(
												item) {
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

		$('#branchManagementName').autocomplete({
			autoSelectFirst : true,
			appentTo : "#autoParent",
			serviceUrl : baseUrl + addHierarchyId(),
			paramName : "tagName",
			delimiter : ",",
			onSelect : function(suggestion) {
				$("#branchManagementId").val(suggestion.data);
				return false;
			},
			transformResult : function(response) {
				return {
					suggestions : $.map($.parseJSON(response), function(item) {
						return {
							value : item.branchName,
							data : item.id
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
			if (result.parentId != '0') {
				$('#branchManagementName').prop('readonly', false);
				$('#branchManagementId').val('');
				console.log("true");
			} else {
				$('#branchManagementName').prop('readonly', true);
				$('#branchManagementId').val(0);
				console.log("false");
			}

		}).fail(function(e) {
			console.log(e);
		});
	}
</script>

<script>
	$(document).ready(function() {
		hideShowFieldsForHierarchyType();
		$("#NewEditBranchForm").submit(function() {
			$(".saveBtn").css("display", "none");
			return true;
		});
	});

	function chkPanelChanged(t) {
		var radioVal = t.value;
		if (radioVal === "0") {
			/*document.getElementById("branchApprovers").value="";
			document.getElementById('approverDiv').style.display = "none"; */
			$("#branchApprovers").val("");
			$("#approverDiv").hide();

		} else {
			//document.getElementById('approverDiv').style.display = "block";
			$("#approverDiv").show();
		}
	}

	function hideShowFieldsForHierarchyType() {
		var tagValue = $("#hierarchyId option:selected").attr("hierarchyType");
		//alert(tagValue);
		if (tagValue == 0) {
			$(".for-branch-only").css("display", "block");
		} else {
			$(".for-branch-only").css("display", "none");
		}
	}

	$("#hierarchyId").change(function() {
		hideShowFieldsForHierarchyType();
	});
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



