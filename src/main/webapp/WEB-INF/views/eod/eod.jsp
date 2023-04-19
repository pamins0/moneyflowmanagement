<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<section class="content-header">
	<h1>EOD</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">EOD</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">

			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Teller Main</h3>
				</div>
			</div>
<sec:authorize access="hasRole('can_branchmanagement_read')">
<%-- 			<c:if --%>
<%-- 				test="${ct:isAllowed('can_branchmanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<div class="box-body">
					<div class="box-body table-responsive">
					
					<table id="branchListTable"
								class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">
								<thead>
									<tr>
										<th style="width: 70px;"><fmt:message key="EOD.header.intradayTellerLimit" /></th>
										<th>4545422</th>
										<th class="text-center"><fmt:message
												key="EOD.header.intradayLimitBreachedBy" /></th>
										<th colspan="2">0</th>
									</tr>
									<tr>
										<th style="width: 70px;"><fmt:message key="EOD.header.accountNoGL" /></th>
										<th><fmt:message key="EOD.header.txnType" /></th>
										<th><fmt:message key="EOD.header.txnAmount" /></th>
										<th><fmt:message key="EOD.header.tf" /></th>
										<th><fmt:message key="EOD.header.DEP-WDL" /></th>
										
									</tr>
									<tr>
										<th style="width: 70px;"><fmt:message key="EOD.header.brCode" /></th>
										<th>BR00147</th>
										<th colspan="3"><fmt:message
												key="EOD.header.tellerCashBalance" /></th>
										
										
									</tr>
									<tr>
										<th style="width: 70px;"><fmt:message key="EOD.header.tellerFCRID" /></th>
										<th>AJE4503016</th>
										<th colspan="3"><fmt:message
												key="EOD.header.numberOfNoteProcessed" /></th>
										
										
									</tr>
								</thead>
								<tbody id="fbody">
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<tr>
										<td style="width: 70px;">
										<select>
												<option value="1">BUY FROM VAULT</option>
												<option value="2">SELL TO VAULT</option>
										</select>
										</td>
										<td><input type="text" size="20"/></td>
										<td><input type="text" size="20"/></td>
										<td>
											true
										</td>
										<td>D/W</td>
										
									</tr>
								</tbody>
							</table>
							
						<div class="table-responsive">
							<table id="branchListTable"
								class="table table-bordered table-striped default footable-loaded footable dataTable no-footer"
								style="max-height: 500px; overflow-y: scroll;"
								aria-describedby="userListTable_info" role="grid">
								<thead>
									<tr>
								
										<th colspan="11"><fmt:message
												key="EOD.header.denominationWithTeller" /></th>
										<th><%=new Date()%></th>
									</tr>
									<tr>
									
										<th><fmt:message key="EOD.header.2000-New" /></th>
										<th><fmt:message key="EOD.header.1000" /></th>
										<th><fmt:message key="EOD.header.500-Old" /></th>
										<th><fmt:message key="EOD.header.500" /></th>
										<th><fmt:message key="EOD.header.100" /></th>
										<th><fmt:message key="EOD.header.50" /></th>
										<th><fmt:message key="EOD.header.20" /></th>
										<th><fmt:message key="EOD.header.10" /></th>
										<th><fmt:message key="EOD.header.5" /></th>
										<th><fmt:message key="EOD.header.2" /></th>
										<th><fmt:message key="EOD.header.1" /></th>
										<th><fmt:message key="EOD.header.tellerTxnTotal" /></th>
									</tr>
									<tr>
									
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
									</tr>
									<tr>
										
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
										<th>0</th>
									</tr>
								</thead>
								<tbody id="fbody">
									<fmt:message key="yyyy.MM.dd" var="pattern" />
									<tr>
									
										<td><input type="text" size="10"/></td>
										<td><input type="text" size="10"/></td>
										<td><input type="text" size="10"/></td>
										<td><input type="text" size="10"/></td>
										<td><input type="text" size="10"/></td>
										<td><input type="text" size="10"/></td>
										<td><input type="text" size="10"/></td>
										<td><input type="text" size="10"/></td>
										<td><input type="text" size="10"/></td>
										<td><input type="text" size="10"/></td>
										<td><input type="text" size="10"/></td>
										<td></td>
									</tr>
									<tr>
									<td>
									Buy from Vault
									</td>
									<td>
									20000
									</td>
									<td>
									
									</td>
									<td>
									true
									</td>
									<td>
									W
									</td>
									</tr>
									<tr>
									<td>
									10
									</td>
									<td>
									0
									</td>
									<td>
									0
									</td>
									<td>
									0
									</td>
									<td>
									0
									</td><td>
									10
									</td>
									<td>
									0
									</td>
									<td>
									0
									</td>
									<td>
									0
									</td>
									<td>
									0
									</td><td>
									10
									</td>
									<td>
									0
									</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
<%-- 			</c:if> --%>
			</sec:authorize>
		</div>
	</div>
</section>





