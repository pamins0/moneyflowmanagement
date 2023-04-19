<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<section class="content-header">

	<h1>Organization Management Audit Trail</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Org Management Audit Trail</li>
	</ol>
</section>
<section class="content">
	<div class="row">

		<div class="col-md-12 table-container-main">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Organization Management Audit Trail List</h3>
				</div>
			</div>
			<div class="box-body">
				<div class="demo-container">
					<table id="orgManagementAuditTriallisttable"
						class="table demo table-condensed table-hover">
						<thead>
							<tr class="info">
								<th data-toggle="true" class="text-center"
									style="max-width: 20px;"></th>
								<th data-type="number">#</th>
								<th><fmt:message key="OrgManagement.list.OrgType" /></th>
								<th><fmt:message key="OrgManagement.list.OrgName" /></th>
								<th><fmt:message key="OrgManagement.list.Location" /></th>
								<th data-hide="phone"><fmt:message key="OrgManagement.list.Email" /></th>
								<th data-hide="phone"><fmt:message key="OrgManagement.list.ContactNo" /></th>
								<th data-hide="phone, tablet"><fmt:message key="OrgManagement.list.BranchControl" /></th>
								<th data-hide="phone, tablet"><fmt:message key="OrgManagement.list.CentralisedControl" /></th>
								<th data-hide="phone, tablet"><fmt:message key="OrgManagement.list.Latitude" /></th>
								<th data-hide="phone, tablet"><fmt:message key="OrgManagement.list.Longitude" /></th>
								<th data-hide="phone, tablet"><fmt:message key="OrgManagement.list.CreatedOn" /></th>
								<th data-hide="phone, tablet" class="editable_table_before_btn"><fmt:message key="OrgManagement.list.ModifiedOn" /></th>
								<th data-hide="phone, tablet">Operation</th>
							</tr>
						</thead>
						<tbody>
							<fmt:message key="yyyy.MM.dd" var="pattern" />
							<c:forEach items="${orgManagementAuditTrailList}" var="org"
								varStatus="counter">
								<tr>
									<td></td>
									<td class="text-center" style="max-width: 5px;">${counter.index+1}</td>
									<td><small>${org.orgType.orgType}</small>
									<td><small>${org.name}</small></td>
									<td><small>${org.location}</small></td>
									<td><small>${org.email}</small></td>
									<td><small>${org.contactNo}</small></td>
									<td><small>${org.branchControl}</small></td>
									<td><small>${org.centralisedControl}</small></td>
									<td><small>${org.latitude}</small></td>
									<td><small>${org.longitude}</small></td>
									<td><small><fmt:formatDate
												value="${org.createdTime}" pattern="${pattern}" /></small></td>
									<td><small><fmt:formatDate
												value="${org.modifiedTime}" pattern="${pattern}" /></small></td>
									<td><small>${org.operation}</small></td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot class="hide-if-no-paging">
							<tr>
								<td colspan="6">
									<div class="pagination pagination-centered"></div>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
</section>


<!-- Tapan sirs work -->

<script type="text/javascript">
$(document).ready(function() {
    $('#orgManagementAuditTriallisttable').DataTable( {
        "dom": '<"top"i>rt<"bottom"flp><"clear">'
    } );
} );

$(document).ready(function(){
	//alert(11);
	$('.bottom').addClass('search_showentry_cls');
	$('.top').addClass('Showing_entry_cls');
	$('.search_showentry_cls .dataTables_length, .search_showentry_cls .dataTables_filter').insertBefore('.table.table-bordered.table-striped.dataTable');
	$('.Showing_entry_cls').insertAfter('.table.table-bordered.table-striped.dataTable');
});

</script>

