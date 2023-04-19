<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<h1 style="text-align: center; font-weight: bold;">Cash
				Management System</h1>
		</div>
		<div class="col-md-12">
			<h2 style="text-align: center;">Bid Dashboard</h2>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">
			<sec:authorize access="hasRole('can_view_everything')">
				<h1 style="text-align: left;">
					<a href="./currentplacebiddashboard"> Current Bids </a>
				</h1>
				<sec:authorize access="hasRole('can_be_maker')">
					<%-- 				<c:if --%>
					<%-- 					test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
					<h2 style="text-align: right:;">
						<a href="./neweditplacebid"> Place Bids </a>
					</h2>
					<%-- 				</c:if> --%>
				</sec:authorize>

				<sec:authorize access="hasRole('can_be_approver')">
					<%-- 				<c:if --%>
					<%-- 					test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
					<h2 style="text-align: right:;">
						<a href="./approveplacebiddashboard"> Approve Bids </a>
					</h2>
					<%-- 				</c:if> --%>
				</sec:authorize>
			</sec:authorize>
			<sec:authorize access="!hasRole('can_view_everything')">
				<c:if
					test="${requestScope['scopedTarget.requestScopedPermissions'].user.branchManagement.hierarchyControl.orgManagement.cmsApproach == 0}">
					<c:if
						test="${requestScope['scopedTarget.requestScopedPermissions'].user.branchManagement.hierarchyControl.hierarchyLevel == 1}">
						<h1 style="text-align: left;">
							<a href="./currentplacebiddashboard"> Current Bids </a>
						</h1>

						<sec:authorize access="hasRole('can_be_maker')">
							<%-- 						<c:if --%>
							<%-- 							test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
							<h2 style="text-align: right:;">
								<a href="./neweditplacebid"> Place Bids </a>
							</h2>
							<%-- 						</c:if> --%>
						</sec:authorize>

						<sec:authorize access="hasRole('can_be_approver')">
							<%-- 						<c:if --%>
							<%-- 							test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
							<h2 style="text-align: right:;">
								<a href="./approveplacebiddashboard"> Approve Bids </a>
							</h2>
							<%-- 						</c:if> --%>
						</sec:authorize>
					</c:if>

					<c:if
						test="${requestScope['scopedTarget.requestScopedPermissions'].user.branchManagement.hierarchyControl.hierarchyLevel == sessionScope['scopedTarget.mySession'].user.branchManagement.hierarchyControl.orgManagement.orgLevel}">
						<h1 style="text-align: left;">
							<a href="./currentplacebiddashboard"> Current Bids </a>
						</h1>

						<sec:authorize access="hasRole('can_be_maker')">
							<%-- 							<c:if --%>
							<%-- 								test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
							<h2 style="text-align: right:;">
								<a href="./neweditplacebid"> Place Bids </a>
							</h2>
							<%-- 							</c:if> --%>
						</sec:authorize>
						<sec:authorize access="hasRole('can_be_approver')">
							<%-- 							<c:if --%>
							<%-- 								test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
							<h2 style="text-align: right:;">
								<a href="./approveplacebiddashboard"> Approve Bids </a>
							</h2>
							<%-- 							</c:if> --%>
						</sec:authorize>
					</c:if>
				</c:if>

				<c:if
					test="${requestScope['scopedTarget.requestScopedPermissions'].user.branchManagement.hierarchyControl.orgManagement.cmsApproach == 1}">
					<c:if
						test="${requestScope['scopedTarget.requestScopedPermissions'].user.branchManagement.hierarchyControl.hierarchyLevel == sessionScope['scopedTarget.mySession'].user.branchManagement.hierarchyControl.orgManagement.orgLevel}">
						<h1 style="text-align: left;">
							<a href="./currentplacebiddashboard"> Current Bids </a>
						</h1>
						<sec:authorize access="hasRole('can_be_maker')">
							<%-- 						<c:if --%>
							<%-- 							test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
							<h2 style="text-align: right:;">
								<a href="./neweditplacebid"> Place Bids </a>
							</h2>
							<%-- 						</c:if> --%>
						</sec:authorize>

						<sec:authorize access="hasRole('can_be_approver')">
							<%-- 						<c:if --%>
							<%-- 							test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
							<h2 style="text-align: right:;">
								<a href="./approveplacebiddashboard"> Approve Bids </a>
							</h2>
							<%-- 						</c:if> --%>
						</sec:authorize>
					</c:if>
				</c:if>
			</sec:authorize>


			<%-- 			<c:choose> --%>
			<%-- 				<c:when --%>
			<%-- 					test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<!-- 					<h1 style="text-align: left;"> -->
			<!-- 						<a href="./currentplacebiddashboard"> Current Bids </a> -->
			<!-- 					</h1> -->
			<%-- 					<c:if --%>
			<%-- 						test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<!-- 						<h2 style="text-align: right:;"> -->
			<!-- 							<a href="./neweditplacebid"> Place Bids </a> -->
			<!-- 						</h2> -->
			<%-- 					</c:if> --%>
			<%-- 					<c:if --%>
			<%-- 						test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<!-- 						<h2 style="text-align: right:;"> -->
			<!-- 							<a href="./approveplacebiddashboard"> Approve Bids </a> -->
			<!-- 						</h2> -->
			<%-- 					</c:if> --%>
			<%-- 				</c:when> --%>
			<%-- 				<c:otherwise> --%>
			<%-- 					<c:if --%>
			<%-- 						test="${requestScope['scopedTarget.requestScopedPermissions'].user.branchManagement.hierarchyControl.orgManagement.cmsApproach == 0}"> --%>
			<%-- 						<c:if --%>
			<%-- 							test="${requestScope['scopedTarget.requestScopedPermissions'].user.branchManagement.hierarchyControl.hierarchyLevel == 1}"> --%>
			<!-- 							<h1 style="text-align: left;"> -->
			<!-- 								<a href="./currentplacebiddashboard"> Current Bids </a> -->
			<!-- 							</h1> -->
			<%-- 							<c:if --%>
			<%-- 								test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<!-- 								<h2 style="text-align: right:;"> -->
			<!-- 									<a href="./neweditplacebid"> Place Bids </a> -->
			<!-- 								</h2> -->
			<%-- 							</c:if> --%>
			<%-- 							<c:if --%>
			<%-- 								test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<!-- 								<h2 style="text-align: right:;"> -->
			<!-- 									<a href="./approveplacebiddashboard"> Approve Bids </a> -->
			<!-- 								</h2> -->
			<%-- 							</c:if> --%>
			<%-- 						</c:if> --%>

			<%-- 						<c:if --%>
			<%-- 							test="${requestScope['scopedTarget.requestScopedPermissions'].user.branchManagement.hierarchyControl.hierarchyLevel == sessionScope['scopedTarget.mySession'].user.branchManagement.hierarchyControl.orgManagement.orgLevel}"> --%>
			<!-- 							<h1 style="text-align: left;"> -->
			<!-- 								<a href="./currentplacebiddashboard"> Current Bids </a> -->
			<!-- 							</h1> -->
			<%-- 							<c:if --%>
			<%-- 								test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<!-- 								<h2 style="text-align: right:;"> -->
			<!-- 									<a href="./neweditplacebid"> Place Bids </a> -->
			<!-- 								</h2> -->
			<%-- 							</c:if> --%>
			<%-- 							<c:if --%>
			<%-- 								test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<!-- 								<h2 style="text-align: right:;"> -->
			<!-- 									<a href="./approveplacebiddashboard"> Approve Bids </a> -->
			<!-- 								</h2> -->
			<%-- 							</c:if> --%>
			<%-- 						</c:if> --%>
			<%-- 					</c:if> --%>

			<%-- 					<c:if --%>
			<%-- 						test="${requestScope['scopedTarget.requestScopedPermissions'].user.branchManagement.hierarchyControl.orgManagement.cmsApproach == 1}"> --%>
			<%-- 						<c:if --%>
			<%-- 							test="${requestScope['scopedTarget.requestScopedPermissions'].user.branchManagement.hierarchyControl.hierarchyLevel == sessionScope['scopedTarget.mySession'].user.branchManagement.hierarchyControl.orgManagement.orgLevel}"> --%>
			<!-- 							<h1 style="text-align: left;"> -->
			<!-- 								<a href="./currentplacebiddashboard"> Current Bids </a> -->
			<!-- 							</h1> -->
			<%-- 							<c:if --%>
			<%-- 								test="${ct:isAllowed('can_be_maker',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<!-- 								<h2 style="text-align: right:;"> -->
			<!-- 									<a href="./neweditplacebid"> Place Bids </a> -->
			<!-- 								</h2> -->
			<%-- 							</c:if> --%>
			<%-- 							<c:if --%>
			<%-- 								test="${ct:isAllowed('can_be_approver',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
			<!-- 								<h2 style="text-align: right:;"> -->
			<!-- 									<a href="./approveplacebiddashboard"> Approve Bids </a> -->
			<!-- 								</h2> -->
			<%-- 							</c:if> --%>
			<%-- 						</c:if> --%>
			<%-- 					</c:if> --%>
			<%-- 				</c:otherwise> --%>
			<%-- 			</c:choose> --%>



		</div>
	</div>

</div>
