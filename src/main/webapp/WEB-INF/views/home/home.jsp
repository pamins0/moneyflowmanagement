<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div style="" align="center"></div>

<%-- <ct:isAllowedDiv divKey = "hello"></ct:isAllowedDiv> --%>

<!--  <div class="container">
	<div class="row">

		<div class="inner-block">
			<div class="market-updates">
			

			




			
				<div class="clearfix"></div>
			</div>
		</div>

	</div>
</div> -->

<div class="container-fluid" style="overflow-x: hidden;">
	<div class="row">
		<div class="col-md-12">
			<div class="card">
				<%-- 				<c:if --%>
				<%-- 					test="${ct:isAllowed('can_see_card_image',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="card-image">
					<img class="img-responsive" src="./static/img/home_bank_cash.png">
				</div>
				<%-- 				</c:if> --%>
			</div>
		</div>
	</div>
</div>




<script type="text/javascript">
	$(function() {

		$('#show').on('click', function() {
			$('.card-reveal').slideToggle('slow');
		});

		$('.card-reveal .close').on('click', function() {
			$('.card-reveal').slideToggle('slow');
		});
	});
</script>
