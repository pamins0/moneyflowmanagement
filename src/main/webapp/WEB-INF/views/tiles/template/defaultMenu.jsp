<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="<c:url value='/static/fonts/font/flaticon.css'/>"
	rel="stylesheet" type="text/css" />

<aside class="left-side sidebar-offcanvas">
	<section class="sidebar">
		<div class="user-panel">
			<div class="pull-left image">
				<%-- 				<img src="<c:url value='/static/img/paraMan.gif'/>" --%>
				<!-- 					class="img-circle" alt="User Image" /> -->
				<span class="Admin-logo">Cash Map</span>
			</div>
			<div class="pull-left info">
				<p>Hello,
					${sessionScope['scopedTarget.mySession'].user.firstName}</p>

				<!-- <a href="#"><i class="fa fa-circle text-success"></i> Online</a>-->
			</div>
		</div>

		<!-- <form action="#" method="get" class="sidebar-form">
			<div class="input-group">
				<input type="text" name="q" class="form-control"
					placeholder="Search..." /> <span class="input-group-btn">
					<button type='submit' name='seach' id='search-btn'
						class="btn btn-flat">
						<i class="fa fa-search"></i>
					</button>
				</span>
			</div>
		</form>--->

		<!-- Side Menu Started -->
		<div class="panel-group" id="accordion">
			<!--Home section-->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<span class="glyph-icon flaticon-home-page"></span> <a
							href="<c:url value='home'/>"> <fmt:message
								key="sidemenu.list.home.home" />
						</a>
					</h4>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<span class="glyph-icon flaticon-home-page"></span> <a
							href="<c:url value='bid'/>"> <fmt:message
								key="sidemenu.list.home.Request" />
						</a>
					</h4>
				</div>
			</div>
			<!--Home section End-->

			<!--ORG Section-->

			<sec:authorize
				access="hasRole('can_orgtype_create') || hasRole('can_orgtype_read')">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#org_type_id"><span
								class="glyph-icon flaticon-business-affiliate-network"> </span>
								<fmt:message key="sidemenu.list.orgtype.orgtype" /></a>
						</h4>
					</div>
					<div id="org_type_id" class="panel-collapse collapse org_type_cls">
						<ul class="list-group">
							<li class="list-group-item"><span
								class="fa fa-angle-double-right"></span> <a
								href="<c:url value='orgtypes'/>"> <fmt:message
										key="sidemenu.list.orgtype.orgtypename" /></a></li>
						</ul>
					</div>
				</div>
			</sec:authorize>
			<!--ORG Section END-->

			<!--ORG Management Section-->
			<sec:authorize
				access="hasRole('can_orgmanagement_create') || hasRole('can_orgmanagement_read')">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#org_management"><span
								class="glyph-icon flaticon-hierarchical-structure"> </span> <fmt:message
									key="sidemenu.list.orgmanagement.orgmanagement" /></a>
						</h4>
					</div>
					<div id="org_management"
						class="panel-collapse collapse org_type_cls">
						<ul class="list-group">
							<li class="list-group-item"><span
								class="fa fa-angle-double-right"></span> <a
								href="<c:url value='orgmanagement?id=org_management'/>"> <fmt:message
										key="sidemenu.list.orgmanagement.orgmanagementname" /></a></li>
						</ul>
					</div>
				</div>
				<%-- 			</c:if> --%>
			</sec:authorize>
			<!--ORG Management Section END-->

			<!--Hierarchy Management Section END-->

			<sec:authorize
				access="hasRole('can_hierarchymanagement_create') || hasRole('can_hierarchymanagement_read')">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#hierarchy_management"><span
								class="glyph-icon flaticon-organization"> </span> <fmt:message
									key="sidemenu.list.hierarchymanagement.hierarchymanagement" /></a>
						</h4>
					</div>
					<div id="hierarchy_management"
						class="panel-collapse collapse org_type_cls">
						<ul class="list-group">
							<li class="list-group-item"><span
								class="fa fa-angle-double-right"></span> <a
								href="<c:url value='hierarchyManagament?id=hierarchy_management'/>">
									<fmt:message
										key="sidemenu.list.hierarchymanagement.hierarchymanagementname" />
							</a></li>
						</ul>
					</div>
				</div>
			</sec:authorize>

			<!--Hierarchy Management Section END-->

			<!--BRANCH Management Section-->

			<sec:authorize
				access="hasRole('can_branchmanagement_create') || hasRole('can_branchmanagement_read')">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#branch_management"><span
								class="glyph-icon flaticon-settings-1"> </span> <fmt:message
									key="sidemenu.list.branchmanagement.branchmanagement" /></a>
						</h4>
					</div>
					<div id="branch_management"
						class="panel-collapse collapse org_type_cls">
						<ul class="list-group">
							<sec:authorize
								access="hasRole('can_branchmanagement_create') || hasRole('can_branchmanagement_read')">
								<li class="list-group-item"><span
									class="fa fa-angle-double-right"></span> <a
									href="<c:url value='branchmanagement?id=branch_management'/>">
										<fmt:message
											key="sidemenu.list.branchmanagement.branchmanagementname" />
								</a></li>
								<%-- <c:if
									test="${sessionScope['scopedTarget.mySession'].user.branchManagement.hierarchyControl.hierarchyLevel eq 1}">
									<li class="list-group-item"><span
										class="fa fa-angle-double-right"></span> <a
										href="<c:url value='closegroupmanagement?id=branch_management'/>">
											<fmt:message
												key="sidemenu.list.branchmanagement.closegroupmanagement" />
									</a></li>
								</c:if> --%>
							</sec:authorize>
							<%-- <c:if
								test="${(sessionScope['scopedTarget.mySession'].user.branchManagement.hierarchyControl.hierarchyType eq 0) && (sessionScope['scopedTarget.mySession'].user.branchManagement.isgroup eq 0)}">
								<li class="list-group-item"><span
									class="fa fa-angle-double-right"></span> <a
									href="<c:url value='eodmanagement?id=eod'/>"> <fmt:message
											key="sidemenu.list.branchmanagement.eodmanagement" />
								</a></li>
								<li class="list-group-item"><span
									class="fa fa-angle-double-right"></span> <a
									href="<c:url value='vaultmanagement'/>"> Vault </a></li>
							</c:if> --%>
						</ul>
					</div>
				</div>
			</sec:authorize>

			<!--BRANCH Management Section END-->


			<!--USER Management Section-->

			<sec:authorize
				access="hasRole('can_usermanagement_create') || hasRole('can_usermanagement_read')">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#user_management"><span
								class="glyph-icon flaticon-multiple-users-silhouette"> </span> <fmt:message
									key="sidemenu.list.usermanagement.usermanagement" /></a>
						</h4>
					</div>
					<div id="user_management"
						class="panel-collapse collapse org_type_cls">
						<ul class="list-group">
							<li class="list-group-item"><span
								class="fa fa-angle-double-right"></span> <a
								href="<c:url value='usermanagement?id=user_management'/>"> <fmt:message
										key="sidemenu.list.usermanagement.usermanagementname" /></a></li>
							<%-- <li class="list-group-item"><span
								class="fa fa-angle-double-right"></span> <a
								href="<c:url value='orgmanagementaudittrail'/>">Org
									Management Audit Trail</a></li> --%>
						</ul>
					</div>
				</div>
				<%-- 			</c:if> --%>
			</sec:authorize>
			<!--USER Management Section END-->

			<!--Permission Management Section-->
			<sec:authorize
				access="hasRole('can_permission_create') || hasRole('can_permission_read')">
				<%-- 			<c:if --%>
				<%-- 				test="${(ct:isAllowed('can_permission_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)==true)  --%>
				<%-- 				|| (ct:isAllowed('can_permission_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)==true)}"> --%>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#permission_management"><span
								class="glyph-icon flaticon-key-to-success"> </span> <fmt:message
									key="sidemenu.list.permissionmanagement.permissionmanagement" /></a>
						</h4>
					</div>
					<div id="permission_management"
						class="panel-collapse collapse org_type_cls">
						<ul class="list-group">
							<li class="list-group-item"><span
								class="fa fa-angle-double-right"></span> <a
								href="<c:url value='permissionmanagement'/>"> <fmt:message
										key="sidemenu.list.permissionmanagement.permissionmanagementname" /></a></li>
						</ul>
					</div>
				</div>
				<%-- 			</c:if> --%>
			</sec:authorize>
			<!--Permssion Management Section END-->

			<!--Role Management Section-->
			<sec:authorize
				access="hasRole('can_role_create') || hasRole('can_role_read')">
				<%-- 			<c:if --%>
				<%-- 				test="${(ct:isAllowed('can_role_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)==true)  --%>
				<%-- 				|| (ct:isAllowed('can_role_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)==true)}"> --%>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#role_management"><span
								class="glyph-icon flaticon-meeting"> </span> <fmt:message
									key="sidemenu.list.rolemanagement.rolemanagement" /></a>
						</h4>
					</div>
					<div id="role_management"
						class="panel-collapse collapse org_type_cls">
						<ul class="list-group">
							<li class="list-group-item"><span
								class="fa fa-angle-double-right"></span> <a
								href="<c:url value='rolemanagement?id=role_management'/>"> <fmt:message
										key="sidemenu.list.rolemanagement.rolemanagementname" /></a></li>
						</ul>
					</div>
				</div>
				<%-- 			</c:if> --%>
			</sec:authorize>
			<!--Role Management Section END-->

			<!--Designation Management Section-->
			<sec:authorize
				access="hasRole('can_designationmanagement_create') || hasRole('can_designationmanagement_read')">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#designation_management"><span
								class="glyph-icon flaticon-user-account-box"> </span> <fmt:message
									key="sidemenu.list.designationmanagement.designationmanagement" /></a>
						</h4>
					</div>
					<div id="designation_management"
						class="panel-collapse collapse org_type_cls">
						<ul class="list-group">
							<li class="list-group-item"><span
								class="fa fa-angle-double-right"></span> <a
								href="<c:url value='designationmanagement?id=designation_management'/>">
									<fmt:message
										key="sidemenu.list.designationmanagement.designationmanagementname" />
							</a></li>
						</ul>
					</div>
				</div>
			</sec:authorize>

			<!--Department Management Section-->
			<sec:authorize
				access="hasRole('can_departmentmanagement_create') || hasRole('can_departmentmanagement_read')">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#department_management"><span
								class="glyph-icon flaticon-user-account-box"> </span> <fmt:message
									key="sidemenu.list.departmentmanagement.departmentmanagement" /></a>
						</h4>
					</div>
					<div id="department_management"
						class="panel-collapse collapse org_type_cls">
						<ul class="list-group">
							<li class="list-group-item"><span
								class="fa fa-angle-double-right"></span> <a
								href="<c:url value='departmentmanagement?id=department_management'/>">
									<fmt:message
										key="sidemenu.list.departmentmanagement.departmentmanagementname" />
							</a></li>
						</ul>
					</div>
				</div>
			</sec:authorize>

			<!--Reports Section-->
			<%-- <c:if test="${(ct:isAllowed('can_designationmanagement_create',requestScope['scopedTarget.requestScopedPermissions'].permissions)==true) 
				|| (ct:isAllowed('can_designationmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)==true)}">
				 --%>

			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#reportSection"><span class="glyph-icon flaticon-badge">
						</span> <fmt:message key="sidemenu.list.reportSection" /></a>
					</h4>
				</div>
				<div id="reportSection"
					class="panel-collapse on collapse org_type_cls">
					<ul class="list-group">
						<li class="list-group-item"><span
							class="fa fa-angle-double-right"> </span> <a
							href="rendarReportDashBoard"> <fmt:message
									key="sidemenu.list.reportSection.viewReportDashBoard" />
						</a></li>
						<%-- <li class="list-group-item"><span
							class="fa fa-angle-double-right"> </span> <a
							href="<c:url value='branchCashDenominationReport'/>"> <fmt:message
									key="sidemenu.list.reportSection.cashDenomination" /></a></li>
						<li class="list-group-item"><span class="fa fa-angle-double-right">
								</span> <a href="<c:url value='branchActiveVsNonActiveReport'/>">
								<fmt:message key="sidemenu.list.reportSection.branchActiveVsNonActive"/></a>
							</li>
						<li class="list-group-item"><span
							class="fa fa-angle-double-right"> </span> <a
							href="<c:url value='bidsPlacedVsAcceptedReport'/>"> <fmt:message
									key="sidemenu.list.reportSection.bidsPlacedVsAcceptedReport" /></a>
						</li>
						<li class="list-group-item"><span
							class="fa fa-angle-double-right"> </span> <a
							href="<c:url value='percentageReport'/>"> <fmt:message
									key="sidemenu.list.reportSection.percentageReport" /></a></li>

						<li class="list-group-item"><span
							class="fa fa-angle-double-right"> </span> <a
							href="<c:url value='branchStatusReport'/>"> <fmt:message
									key="sidemenu.list.reportSection.branchStatus" /></a></li> --%>
					</ul>
				</div>
			</div>





			<%-- 		<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#reportSection"><span class="glyph-icon flaticon-badge">
						</span> <fmt:message key="sidemenu.list.reportSection" /></a>
					</h4>
				</div>
				<div id="reportSection"
					class="panel-collapse on collapse org_type_cls">
					<ul class="list-group">
						<li class="list-group-item"><span
							class="fa fa-angle-double-right"> </span> <a
							href="<c:url value='branchCashAvailabilityReport?id=branchCashAvailabilityReport'/>">
								<fmt:message key="sidemenu.list.reportSection.cashAvailability" />
						</a></li>
						<li class="list-group-item"><span
							class="fa fa-angle-double-right"> </span> <a
							href="<c:url value='branchCashDenominationReport'/>"> <fmt:message
									key="sidemenu.list.reportSection.cashDenomination" /></a></li>
						<li class="list-group-item"><span class="fa fa-angle-double-right">
								</span> <a href="<c:url value='branchActiveVsNonActiveReport'/>">
								<fmt:message key="sidemenu.list.reportSection.branchActiveVsNonActive"/></a>
							</li>
						<li class="list-group-item"><span
							class="fa fa-angle-double-right"> </span> <a
							href="<c:url value='bidsPlacedVsAcceptedReport'/>"> <fmt:message
									key="sidemenu.list.reportSection.bidsPlacedVsAcceptedReport" /></a>
						</li>
						<li class="list-group-item"><span
							class="fa fa-angle-double-right"> </span> <a
							href="<c:url value='percentageReport'/>"> <fmt:message
									key="sidemenu.list.reportSection.percentageReport" /></a></li>

						<li class="list-group-item"><span
							class="fa fa-angle-double-right"> </span> <a
							href="<c:url value='branchStatusReport'/>"> <fmt:message
									key="sidemenu.list.reportSection.branchStatus" /></a></li>
					</ul>
				</div>
			</div>
 --%>
			<!--Designation Management Section END-->
		</div>
	</section>
</aside>
<style>
html, body {
	overflow-x: inherit !important;
}
</style>
<script>
	$(document).ready(function() {

		var div_id = window.location.search.substring(4);
		//alert(div_id);

		$(".panel-heading").click(function() {
			$("#org_management").css("height", "auto");
		});
		$("#" + div_id).removeClass("collapse");
		$("#" + div_id).addClass("in");
	});
</script>


<script>
	
</script>

