<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:getAsString name="title" /></title>
<%--     <link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link> --%>
<%--     <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link> --%>
<tiles:insertAttribute name="meta-js-css" />
</head>
<body class="skin-blue">
	<%-- <header class="header">
		<tiles:insertAttribute name="header" />
	</header> --%>
	<div class="wrapper row-offcanvas row-offcanvas-left">
	<%-- 	<tiles:insertAttribute name="menu" /> --%>
		<aside class="right-side">
			<tiles:insertAttribute name="body" />
		</aside>
	</div>

	<%-- <footer id="footer">
		<tiles:insertAttribute name="footer" />
	</footer> --%>

</body>
</html>