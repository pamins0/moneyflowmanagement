<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>Branch Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Organization</li>
		<li class="active">New/Update</li>
	</ol>
</section>

<sec:authorize access="hasRole('can_branchmanagement_update')">
	<%-- <c:if --%>
	<%-- 	test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
	<section class="content">
		<div class="row">

			<div class="col-md-12 table-container-main">
				<div class="box-header custom_header_form_page">
					<div class="heading-title-wrapper">
						<i class="fa fa-gear"></i>
						<c:choose>
							<c:when test="${edit}">
								<h3 class="box-title">
									Edit Branch - <span class="edit-module-name">${branch.branchName}</span>
								</h3>
							</c:when>
							<c:otherwise>
								<h3 class="box-title">New Branch</h3>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<a class="btn btn-success" href='branchmanagement'> <i
							class="fa fa-arrow-left"></i> Back
						</a>
					</div>
				</div>
				<div class="box-body custom_form_body">
					<form:form id="branch" method="POST" modelAttribute="branch"
						autocomplete="off" class="form-inline">
						<form:hidden path="id" id="id" />

						<%-- <div class="form-group">
						<label><fmt:message key="BranchManagement.form.Organizaion" /></label>
						<form:select path="orgManagement.id" cssClass="form-control">
							<form:option value="-1" label="- Project -" />
							<form:options items="${orgManagements}" itemValue="id"
								itemLabel="name" />
						</form:select>
						<div class="has-error">
							<form:errors path="orgManagement.id" cssClass="help-inline" />
						</div>
					</div> --%>


						<sec:authorize
							access="hasRole('can_create_any_organization_branchmanagement')">
							<c:choose>
								<c:when test="${edit}">
								</c:when>
								<c:otherwise>
									<c:set property="orgManagement" target="${branch}" value=""></c:set>
								</c:otherwise>
							</c:choose>

							<jsp:include page="../UtilityFiles/OrgToOrgManagement.jsp"></jsp:include>
						</sec:authorize>
						<sec:authorize
							access="!hasRole('can_create_any_organization_branchmanagement')">
							<div class="form-group">
								<label><fmt:message key="Common.form.Organization" /></label>
								<form:input path="orgManagement.orgType.orgType"
									id="orgManagementOrgTypeName" readonly="true"
									cssClass="form-control" />
							</div>
							<div class="form-group">
								<form:hidden path="orgManagement.id" id="orgManagementId" />
								<label><fmt:message key="Common.form.OrganizationName" /></label>
								<form:input path="orgManagement.name" id="orgManagementName"
									readonly="true" cssClass="form-control" />
							</div>
						</sec:authorize>
						<%-- <c:choose>
							<c:when
								test="${ct:isAllowed('can_create_any_organization_branchmanagement',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
								<c:choose>
									<c:when test="${edit}">
									</c:when>
									<c:otherwise>
										<c:set property="orgManagement" target="${branch}" value=""></c:set>
									</c:otherwise>
								</c:choose>

								<jsp:include page="../UtilityFiles/OrgToOrgManagement.jsp"></jsp:include>
																
							</c:when>
							<c:otherwise>
								<div class="form-group">
									<label><fmt:message key="Common.form.Organization" /></label>
									<form:input path="orgManagement.orgType.orgType"
										id="orgManagementOrgTypeName" readonly="true"
										cssClass="form-control" />
								</div>
								<div class="form-group">
									<form:hidden path="orgManagement.id" id="orgManagementId" />
									<label><fmt:message key="Common.form.OrganizationName" /></label>
									<form:input path="orgManagement.name" id="orgManagementName"
										readonly="true" cssClass="form-control" />
								</div>
							</c:otherwise>
						</c:choose> --%>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchName" /></label>
							<form:input path="branchName" id="branchName"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchName" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchAddress1" /></label>
							<form:input path="branchAdd1" id="branchAdd1"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchAdd1" cssClass="help-inline" />
							</div>
						</div>
						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchAddress2" /></label>
							<form:input path="branchAdd2" id="branchAdd2"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchAdd2" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchCity" /></label>
							<form:input path="branchCity" id="branchCity"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchCity" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchCode" /></label>
							<form:input path="branchCode" id="branchCode"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchCode" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchContactNo" /></label>
							<form:input path="branchContactNo" id="branchContactNo"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchContactNo" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchControl" /></label>
							<form:input path="branchControl" id="branchControl"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchControl" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchDetails" /></label>
							<form:input path="branchDetail" id="branchDetail"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchDetail" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchEmail" /></label>
							<form:input path="branchEmail" id="branchEmail"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchEmail" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchLatitude" /></label>
							<form:input path="branchLatitude" id="branchLatitude"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchLatitude" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchLocation" /></label>
							<form:input path="branchLocation" id="branchLocation"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchLocation" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchLongitude" /></label>
							<form:input path="branchLongitude" id="branchLongitude"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchLongitude" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchState" /></label>
							<form:input path="branchState" id="branchState"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchState" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="BranchManagement.form.BranchStatus" /></label>
							<form:input path="branchStatus" id="branchStatus"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchStatus" cssClass="help-inline" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message key="BranchManagement.form.BranchZip" /></label>
							<form:input path="branchZip" id="branchZip"
								cssClass="form-control" />
							<div class="has-error">
								<form:errors path="branchZip" cssClass="help-inline" />
							</div>
						</div>


						<!-- 					<div class="form-group"> -->
						<!-- 						<label>orgType</label> -->
						<%-- 						<form:select path="orgType.id" cssClass="form-control"> --%>
						<%-- 							<form:option value="-1"  label="- Project -" /> --%>
						<%-- 							<form:options items="${orgTypes}" itemValue="id" itemLabel="orgType"/> --%>
						<%-- 						</form:select> --%>
						<!-- 						<div class="has-error"> -->
						<%-- 							<form:errors path="orgType" cssClass="help-inline"/> --%>
						<!-- 						</div> -->
						<!-- 					</div> -->

						<div class="form-group button-group-set">
							<div class="col-sm-12">
								<c:choose>
									<c:when test="${edit}">
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-check"></i> Update
										</button>
										<a class="btn btn-danger btn-sm"
											href="<c:url value='/branchmanagement' />"><i
											class="fa fa-times"></i> Cancel</a>
									</c:when>
									<c:otherwise>
										<button type="submit" class="btn btn-primary btn-sm">
											<i class="fa fa-floppy-o"></i> Save
										</button>
										<a class="btn btn-danger btn-sm"
											href="<c:url value='/branchmanagement' />"> <i
											class="fa fa-times"></i> Cancel
										</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<%-- </c:if> --%>
</sec:authorize>