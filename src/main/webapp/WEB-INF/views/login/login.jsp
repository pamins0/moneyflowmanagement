<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
body {
	padding-top: 90px;
}

.panel-login {
	border: 2px solid #14699b;
	-webkit-box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.2);
	box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.2);
}

.panel-login>.panel-heading {
	color: #00415d;
	background-color: #fff;
	border-color: #fff;
	text-align: center;
}

.panel-login>.panel-heading a {
	text-decoration: none;
	color: #666;
	font-weight: bold;
	font-size: 15px;
	-webkit-transition: all 0.1s linear;
	-moz-transition: all 0.1s linear;
	transition: all 0.1s linear;
}

.panel-login>.panel-heading a.active {
	color: #029f5b;
	font-size: 18px;
}

.panel-login>.panel-heading hr {
	margin-top: 10px;
	margin-bottom: 0px;
	clear: both;
	border: 0;
	height: 1px;
	background-image: -webkit-linear-gradient(left, rgba(0, 0, 0, 0),
		rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0));
	background-image: -moz-linear-gradient(left, rgba(0, 0, 0, 0),
		rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0));
	background-image: -ms-linear-gradient(left, rgba(0, 0, 0, 0),
		rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0));
	background-image: -o-linear-gradient(left, rgba(0, 0, 0, 0),
		rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0));
}

.panel-login input[type="text"], .panel-login input[type="email"],
	.panel-login input[type="password"] {
	height: 45px;
	border: 1px solid #ddd;
	font-size: 16px;
	-webkit-transition: all 0.1s linear;
	-moz-transition: all 0.1s linear;
	transition: all 0.1s linear;
}

.panel-login input:hover, .panel-login input:focus {
	outline: none;
	-webkit-box-shadow: none;
	-moz-box-shadow: none;
	box-shadow: none;
	border-color: #ccc;
}

.btn-login {
	background-color: #59B2E0;
	outline: none;
	color: #0062a8;
	font-size: 14px;
	height: auto;
	font-weight: normal;
	padding: 14px 0;
	text-transform: uppercase;
	border-color: #59B2E6;
}

.btn-login:hover, .btn-login:focus {
	color: #fff;
	background-color: #53A3CD;
	border-color: #53A3CD;
}

.forgot-password {
	text-decoration: underline;
	color: #888;
}

.forgot-password:hover, .forgot-password:focus {
	text-decoration: underline;
	color: #666;
}

.btn-register {
	background-color: #1CB94E;
	outline: none;
	color: #fff;
	font-size: 14px;
	height: auto;
	font-weight: normal;
	padding: 14px 0;
	text-transform: uppercase;
	border-color: #1CB94A;
}

.btn-register:hover, .btn-register:focus {
	color: #fff;
	background-color: #1CA347;
	border-color: #1CA347;
}

.login-container {
	max-width: 450px;
	margin: 0 auto;
	padding: 0 15px;
}

input[type="radio"], input[type="checkbox"] {
	margin: 0px 0 0;
	margin-top: 1px \9;
	line-height: normal;
	vertical-align: middle;
}

.has-error {
	color: red;
	position: relative;
/* 	top: 109px; */
	left: -15px;
}
</style>
<script>
	function myFunction() {
		var str = "Visit W3Schools!";
		var n = str.search("s");
		alert(n);
	}
</script>
<body style="width: 100%; height: 100%;">
	<div class="container">
		<div class="row">
			<div class="login-container">
				<div class="panel panel-login">
					<div class="panel-heading">
					<div class="row" style="margin-bottom: 11px;">
					<div class="col-xs-12 text-center">
					<img src="/cashmanagement/static/img/cms.png" class="img-circle">
					</div>
					</div>
						<div class="row">
							<div class="col-xs-6">
								<a href="./login" class="active" id="login-form-link">Login</a>
							</div>
							<div class="col-xs-6">
								<a href="./forgotpass" id="" style="cursor: not-allowed; pointer-events: none;">Forgot Password?</a>
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<%@ page isELIgnored="false"%>
								<span class="has-error text-center"> <c:if
										test="${param.error != null}">
										<div class="alert alert-danger">
											<p>Invalid username and password.</p>
										</div>
									</c:if> <c:if test="${param.logout != null}">
										<div class="alert alert-success">
											<p>You have been logged out successfully.</p>
										</div>
									</c:if> <%-- 								${loginErrorMessages} --%>
								</span>
								<form:form modelAttribute="user" id="login-form" method="post"
									role="form" style="display: block;">
									<div class="form-group">
										<form:input path="userId" id="userId" cssClass="form-control"
											placeholder="User Id" autocomplete="off" />
										<span><form:errors path="userId" cssClass="has-error" />
										</span>
									</div>
									<div class="form-group">
										<form:password path="userPassword" id="userPassword"
											cssClass="form-control" placeholder="Password" />
										<span><form:errors path="userPassword"
												cssClass="has-error" /> </span>
									</div>
									<div
										class="form-group text-center pull-left clearfix col-lg-12">
										<input type="checkbox" tabindex="3" class="" name="remember"
											id="remember-me"> <label style="color: #111111;"
											for="remember"> Remember Me</label>
									</div>
									<div class="form-group clearfix">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3 btn_login_set">
												<button type="submit" name="login-submit" id="login-submit"
													tabindex="4"
													class=" form-control btn btn-success btn-login">
													Log In</button>
											</div>
										</div>
									</div>
								</form:form>
							</div>


							<%-- <form:form action="forgotpass" modelAttribute="user" id="register-form" method="POST"
									role="form" style="display: none;">
								<div class="form-group" style="padding: 0 20px;">
									<input type="text" name="email" id="email" tabindex="1"
										class="form-control" placeholder="Email Address"/>
								</div>
								<div class="form-group">
									<div class="row">
										<div class="col-sm-6 col-sm-offset-3 btn_login_set">
											<button type="submit" name="register-submit"
												id="register-submit" tabindex="4"
												class="form-control btn btn-success btn-register">
												<i class="fa fa-envelope-o"></i> Submit
											</button>
										</div>
									</div>
								</div>
							</form:form> --%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>




	<script src="<c:url value='/static/js/jquery.min.js' />">
		
	</script>
	<script src="<c:url value='/static/js/bootstrap.min.js'/> "></script>
	<script src="<c:url value='/static/js/logScript.js'/> "></script>
	<script type="text/javascript">
		$(function() {

			$('#login-form-link').click(function(e) {
				$("#login-form").delay(100).fadeIn(100);
				$("#register-form").fadeOut(100);
				$('#register-form-link').removeClass('active');
				$(this).addClass('active');
				e.preventDefault();
			});
			$('#register-form-link').click(function(e) {
				$("#register-form").delay(100).fadeIn(100);
				$("#login-form").fadeOut(100);
				$('#login-form-link').removeClass('active');
				$(this).addClass('active');
				e.preventDefault();
			});

		});
	</script>
</body>