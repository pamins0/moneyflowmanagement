<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content">
	<div class="col-md-12 table-container-main">
		<div class="box-header custom_header_form_page">
			<div class="btn-group custom_group" role="group"
				aria-label="Basic example">
				<a class="btn btn-success" href='javascript:window.history.back()'>
					<i class="fa fa-arrow-left"></i> Back
				</a>
			</div>
		</div>
	</div>
	<div class="error-page">
		<h2 class="headline text-info">404</h2>
		<div class="error-content">
			<h3>
				<i class="fa fa-warning text-yellow"></i> Oopsss! Page not found.
			</h3>
			<p>We could not find the page you were looking for. Meanwhile,
				you may go back or try using the later form.</p>
		</div>
	</div>

</section>
