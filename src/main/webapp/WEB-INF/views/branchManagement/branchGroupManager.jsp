<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<section class="content-header">
	<h1>Closed Group Management</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Closed Group</li>
	</ol>
</section>

<section class="content">
	<form:form name="userRoleForm" id="userRoleForm"
		onsubmit="return validateForm()" commandName="branchGroup"
		method="POST" class="aswd_dms_tbl" action="saveBranchGroupAssociation">
		<div class="row">
			<div class="col-md-12 table-container-main">
				<div class="box-header">
					<div class="heading-title-wrapper">
						<i class="fa fa-tasks"></i>
						<h3 class="box-title">Entity List</h3>
					</div>


					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<p>
							<button type="submit" class="btn btn-success btn-lg">Save</button>
							<a href='./closegroupmanagement' class="btn btn-danger btn-lg"
								onclick=""> Cancel </a>
						</p>
					</div>

					<div class="fixed-header-4">
						<div class="box-table">
							<div class="box-header col-sm-4 ">
								<i class="halflings-icon white align-justify"></i><span
									class="break"></span>Associate Closed Group Name - <span
									class="edit-module-name"></span>
								<form:input path="groupName" id="groupName" maxlength="100"/>
								<form:hidden path="groupId" value="${groupId}" />
							</div>
							<div class="products col-sm-5" style="display: none;">
								<p class="heading">
									Selected Branches:<br> <select multiple="multiple"
										name="insertedVA">
										<c:forEach items="${selectedBranchList}" var="selectedBranch">
											<option value="${selectedBranch.branchManagement.id}"
												selected="selected">${selectedBranch.branchManagement.branchName}</option>
										</c:forEach>
									</select>
								</p>
							</div>
							<div class="has-error">
								<form:errors path="groupName" cssClass="help-inline" />
							</div>
						</div>
					</div>
				</div>
				<div class="box-body">
					<div class="demo-container">
						<table id="orgmanagementlisttable"
							class="table table-bordered table-striped default no-paging footable-loaded footable dataTable"
							style="max-height: 500px; overflow-y: scroll;">
							<thead>
								<tr>
									<th class="text-center" style="width: 20px;"><i
										class="fa fa-cogs"></i></th>
									<th>Entity Name</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${selectedBranchList}" var="branch"
									varStatus="counter">
									<tr>
										<td class="text-center"><input type="checkbox"
											checked="checked" value="${branch.branchManagement.id}"
											name="selectedVA" class="checkbox-branch"> <%-- ${counter.index+1} --%></td>
										<td><small>${branch.branchManagement.branchName}</small></td>
									</tr>
								</c:forEach>

								<c:forEach items="${deselectedBranchList}" var="branch"
									varStatus="counter">
									<tr>
										<td class="text-center"><input type="checkbox"
											name="selectedVA" value="${branch.id}"
											class="checkbox-branch"> <%-- ${counter.index+1}${branch.id} --%>
										</td>
										<td><small>${branch.branchName}</small></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</section>

<!-- Tapan sirs work -->

<script type="text/javascript">
	var table;
	$(document).ready(function() {
		table = $('#orgmanagementlisttable').DataTable({
			"paging" : false,
			"dom" : '<"top"i>rt<"bottom"flp><"clear">'
		});
	});

	$(document)
			.ready(
					function() {
						//alert(11);
						$('.bottom').addClass('search_showentry_cls');
						$('.top').addClass('Showing_entry_cls');
						$(
								'.search_showentry_cls .dataTables_length, .search_showentry_cls .dataTables_filter')
								.insertBefore(
										'.table.table-bordered.table-striped.dataTable');
						$('.Showing_entry_cls')
								.insertAfter(
										'.table.table-bordered.table-striped.dataTable');
					});
</script>

<script>
	function validateForm() {
		var groupName = $("#groupName").val();
		if (groupName === "") {
			alert("Group Name is Required");
			return false;
		}
		$("#orgmanagementlisttable_filter").find("input").val("");
		table.search("").draw();
		var flag = false;
		$(".checkbox-branch").each(function() {
			if ($(this).prop("checked") == true) {
				//alert("Checkbox is checked." + $(this).val());
				flag=true;
			}
		});
		if(!flag){
			alert("Atleast 1 branch should be selected");
			return flag;
		}
		return true;
	}
</script>

















