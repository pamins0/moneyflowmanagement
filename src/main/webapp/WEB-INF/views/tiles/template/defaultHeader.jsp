<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header class="header">
	<a class="logo" target=" " href="https://www.yesbank.in/"><img src="<c:url value='/static/img/cms.png'/>" class="img-circle" /></a>
	<nav class="navbar navbar-static-top">
		<a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span><span class="icon-bar"></span>
		</a>

		<%-- <div class="alert text-yellow bold" id="success-alert">${successMessage}</div> --%>


		<%-- <div style="" align="center">
<p>Hello <span style="font-weight: bold;"> ${sessionScope['scopedTarget.mySession'].user.name},</span></p>
<p>Your Branch is : <span style="font-weight: bold;"> ${sessionScope['scopedTarget.mySession'].user.branchManagement.branchName}</span></p>
<p>Your Branch Organization is : <span style="font-weight: bold;"> ${sessionScope['scopedTarget.mySession'].user.branchManagement.orgManagement.name}</span></p>
<p>Your Organization Type is : <span style="font-weight: bold;"> ${sessionScope['scopedTarget.mySession'].user.branchManagement.orgManagement.orgType.orgType}</span></p>
</div> --%>

		<div class="navbar-right">
			<ul class="nav navbar-nav">
				<li class="dropdown user user-menu"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"> <i
						class="glyphicon glyphicon-user"></i> <span>Hello
							${sessionScope['scopedTarget.mySession'].user.firstName} ${sessionScope['scopedTarget.mySession'].user.secondName} ${sessionScope['scopedTarget.mySession'].user.lastName}<i
							class="caret"></i>
					</span>

				</a>
					<ul class="dropdown-menu">
						<li class="user-header bg-light-blue"><img
							src="<c:url value='/static/img/logo.png'/>" class="img-circle" />
							<p>
								${sessionScope['scopedTarget.mySession'].user.firstName} ${sessionScope['scopedTarget.mySession'].user.secondName} ${sessionScope['scopedTarget.mySession'].user.lastName}<small>${sessionScope['scopedTarget.mySession'].user.designation.title}</small>
								<small>${sessionScope['scopedTarget.mySession'].user.branchManagement.orgManagement.name}</small>
								<small>${sessionScope['scopedTarget.mySession'].user.branchManagement.hierarchyControl.hierarchyLevel}</small>
							</p>
						</li>
<!-- 						Menu Body -->
						<li class="user-body">
							<div class="col-xs-16 text-center">
								<a href="#"> Entity  : <c:out value="${sessionScope['scopedTarget.mySession'].user.branchManagement.branchName}" /></a>
							</div>
							<div class="col-xs-16 text-center">
								<a href="#"> User Id  : <c:out value="${sessionScope['scopedTarget.mySession'].user.userId}" /></a>
							</div>
							<div class="col-xs-16 text-center">
								<a href="#">Role As : 
								[
								<c:forEach items="${sessionScope['scopedTarget.mySession'].user.userRoles}" var="userRole">
									${userRole.role.title}
								</c:forEach>
								]
								</a>
							</div>
						</li>
						<li class="user-footer">
							<div class="pull-left">
							<c:set var="userId" scope="session" value="${sessionScope['scopedTarget.mySession'].user.id}"/>
								<a href="<c:url value='/usermanagement-${userId}-view'/>" class="btn btn-success btn-flat">&nbsp;&nbsp;Profile&nbsp;&nbsp;</a>
							</div>
							<div class="pull-right">
								<a href="<c:url value='logout'/>"
									class="btn btn-success btn-flat">&nbsp;&nbsp;Sign
									out&nbsp;&nbsp;</a>
							</div>
						</li>
					</ul></li>
			</ul>
		</div>
	</nav>
</header>
