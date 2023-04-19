<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<style>
table {
	border-collapse: collapse;
}

th, td {
	border: 1px solid #c6c7cc;
}

th {
	font-weight: bold;
}
</style>


<section class="content-header">
	<h1>Entity Management EOD</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li>Entity EOD</li>
		<li class="active">New/Update</li>
	</ol>
</section>

<%-- <c:if 
	test="${ct:isAllowed('can_branchmanagement_update',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">
	--%>
<section class="content">
	<div class="row">
		<form:form action="savebrancheodpositiondb" name="myForm"
			id="NewEditBranchForm" method="POST"
			modelAttribute="branchParameterStatus" autocomplete="off"
			class="form-inline">
			<form:hidden path="id" id="id" />
			<form:hidden path="branchManagement.id" id="branchManagementId" />
			<form:hidden path="requestFrom" />
			<c:set var="eodreadonlyflag" value="false" /> 
			<c:if test="${approveFlag}">
				<c:set var="eodreadonlyflag" value="true" />
			</c:if>

			<c:choose>
				<c:when test="${empty branchParameterStatus.urlReferer }">
					<c:set var="referer" value="${header['Referer']}" />
				</c:when>
				<c:otherwise>
					<c:set var="referer" value="${branchParameterStatus.urlReferer}" />
				</c:otherwise>
			</c:choose>
			<form:hidden path="urlReferer" value="${referer}" />

			<div class="col-md-12 table-container-main">
				<div class="box-header custom_header_form_page">
					<div class="heading-title-wrapper">
						<i class="fa fa-gear"></i>
						<c:choose>
							<c:when test="${edit}">
								<h3 class="box-title">
									Edit EOD <span class="edit-module-name">${branchManagement.branchName}</span>
								</h3>
							</c:when>
							<c:otherwise>
								<h3 class="box-title">New EOD -
									${branchParameterStatus.branchManagement.branchName}</h3>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="btn-group custom_group" role="group"
						aria-label="Basic example">
						<c:choose>
							<c:when
								test="${branchParameterStatus.requestFrom == 'byhierarchy'}">
								<a class="btn btn-success" href='${referer}'> <i
									class="fa fa-arrow-left"></i> Back
								</a>
							</c:when>
							<c:otherwise>
								<a class="btn btn-success" href='${referer}'> <i
									class="fa fa-arrow-left"></i> Back
								</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="box-body custom_form_body">


					<table style="display: block !important;">
						<thead>
							<tr>
								<th scope="col" colspan="1">Entity Parameter</th>
								<th scope="col" colspan="7">Denominations</th>
								<th scope="col" colspan="4">Coins</th>
								<th scope="col" colspan="1">Others</th>
								<th scope="col">Totals</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td></td>

								<td>2000</td>
								<td>500</td>
								<td>100</td>
								<td>50</td>
								<td>20</td>
								<td>10</td>
								<td>5</td>
								<td>1</td>
								<td>2</td>
								<td>5</td>
								<td>10</td>
								<td></td>
								<td></td>
							</tr>

							<c:forEach items="${branchParametersList}" var="branchParam"
								varStatus="status">
								<thead>
									<form:hidden
										path="branchParameterStatusList[${status.index}].branchParameter.id"
										value="${branchParam.id}" />
									<tr id="branch_tr${status.index}"
										count="${branchParametersList.size()}"
										operator="${branchParam.parameterDetails}">
										<td><c:set value="=" var="sign" /> <c:if
												test="${branchParam.parameterDetails eq 'add' }">
												<c:set value="(+)" var="sign" />
											</c:if> <c:if test="${branchParam.parameterDetails eq 'sub' }">
												<c:set value="(-)" var="sign" />
											</c:if> <form:input
												path="branchParameterStatusList[${status.index}].branchParameter.parameterName"
												value="${sign} ${branchParam.parameterName}" vindex="0"
												id="branchParameterStatus_branchparam[${status.index}]"
												readonly="true" style="width: 300px;" /></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dn2000"
												value="" id="branchParameterStatus_dn2000_${status.index}"
												vindex="1"
												cssClass="form-control num note-${branchParam.parameterDetails}-2000n"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dn2000"
													cssClass="help-inline" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dn500"
												value="" id="branchParameterStatus_dn500_${status.index}"
												vindex="2"
												cssClass="form-control num note-${branchParam.parameterDetails}-500n"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dn500"
													cssClass="help-inline" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dn100"
												value="" id="branchParameterStatus_dn100_${status.index}"
												vindex="3"
												cssClass="form-control num note-${branchParam.parameterDetails}-100n"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dn100"
													cssClass="help-inline" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dn50"
												value="" id="branchParameterStatus_dn50_${status.index}"
												vindex="4"
												cssClass="form-control num note-${branchParam.parameterDetails}-50n"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dn50"
													cssClass="help-inline" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dn20"
												value="" id="branchParameterStatus_dn20_${status.index}"
												vindex="5"
												cssClass="form-control num note-${branchParam.parameterDetails}-20n"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dn20"
													cssClass="help-inline" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dn10"
												value="" id="branchParameterStatus_dn10_${status.index}"
												vindex="6"
												cssClass="form-control num note-${branchParam.parameterDetails}-10n"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dn10"
													cssClass="help-inline" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dn5"
												value="" id="branchParameterStatus_dn5_${status.index}"
												vindex="7"
												cssClass="form-control num note-${branchParam.parameterDetails}-5n"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dn5"
													cssClass="help-inline" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dc1"
												value="" id="branchParameterStatus_dc1_${status.index}"
												vindex="8"
												cssClass="form-control num note-${branchParam.parameterDetails}-1c"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dc1"
													cssClass="help-inline" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dc2"
												value="" id="branchParameterStatus_dc2_${status.index}"
												vindex="9"
												cssClass="form-control num note-${branchParam.parameterDetails}-2c"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dc2"
													cssClass="help-inline" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dc5"
												value="" id="branchParameterStatus_dc5_${status.index}"
												vindex="10"
												cssClass="form-control num note-${branchParam.parameterDetails}-5c"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dc5"
													cssClass="help-inline" onchange="valChange($(this).val());"
													onkeyup="this.onchange();" onpaste="this.onchange();"
													oninput="this.onchange();" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].dc10"
												value="" id="branchParameterStatus_dc10_${status.index}"
												vindex="11"
												cssClass="form-control num note-${branchParam.parameterDetails}-10c"
												onchange="valChange($(this));" onkeyup="this.onchange();"
												onpaste="this.onchange();" oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].dc10"
													cssClass="help-inline" />
											</div></td>

										<td><form:input readonly="${eodreadonlyflag}" onkeypress='return validateQty(event);'
												path="branchParameterStatusList[${status.index}].others"
												value="" id="branchParameterStatus_others_${status.index}"
												vindex="12"
												cssClass="form-control num note-${branchParam.parameterDetails}-1o"
												style="width: 100px;" onchange="valChange($(this));"
												onkeyup="this.onchange();" onpaste="this.onchange();"
												oninput="this.onchange();" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].others"
													cssClass="help-inline" />
											</div></td>

										<td>										
										<%-- <fmt:formatNumber groupingUsed="false" var="totalFmt" type="number"
												value="${branchParameterStatusList[status.index].total}" /> --%>
										<form:input onkeyup="return"
												path="branchParameterStatusList[${status.index}].total"
												value="" id="branchParameterStatus_total_${status.index}"
												vindex="13" cssClass="form-control" style="width: 100px;"
												readonly="true" />
											<div class="has-error">
												<form:errors
													path="branchParameterStatusList[${status.index}].total"
													cssClass="help-inline" />
											</div></td>

									</tr>

								</thead>
							</c:forEach>
						</tbody>
					</table>

					<div class="form-group button-group-set">
						<div class="col-sm-12">
							<c:choose>
								<c:when test="${edit eq 'true'}">
									<c:choose>
										<c:when test="${approveFlag eq 'false'}">
											<button type="submit" name="saveBtn" value="UpdateAndApprove"
												onclick="return validateDenominations()"
												class="btn btn-primary btn-sm" style="width: 17% !important">
												<i class="fa fa-check"></i> Update & Approve
											</button>
										</c:when>
										<c:otherwise>
											<a class="btn btn-primary btn-sm" style="float:none;" href="<c:url value='approveEODForBranch' />">
												<i class="fa fa-check"></i> Approve
											</a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<button type="submit" name="saveBtn" value="Save"
										onclick="return validateDenominations()"
										class="btn btn-primary btn-sm saveBtn">
										<i class="fa fa-floppy-o"></i> Save
									</button>
									<!-- <button type="submit" name="saveBtn" value="SaveAndNew" class="btn btn-primary btn-sm">
												<i class="fa fa-floppy-o"></i> Save And New
											</button> -->
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${edit eq 'false'}">
									<a class="btn btn-danger btn-sm"
										href="<c:url value='${referer}' />"> <i
										class="fa fa-times"></i> Cancel
									</a>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${approveFlag eq 'false'}">
											<a class="btn btn-danger btn-sm"
												href="<c:url value='${referer}' />"> <i
												class="fa fa-times"></i> Cancel
											</a>
										</c:when>
										<c:otherwise>
											<a class="btn btn-danger btn-sm"
												href="<c:url value='cancelEODForBranch' />"> <i
												class="fa fa-times"></i> Cancel Eod
											</a> 
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</section>
<%-- </c:if> --%>

<script type="text/javascript">
	$(document).bind("contextmenu", function (event) {
		  event.preventDefault();
	});
	
	/* $(document).ready(function(){		  
		  prevententer();
	});
	
	
	function prevententer () {
	  console.log("inside prevent right click....");
	  if(event.button == 2) {
		  console.log("inside prevent right click....again");
		  return false;
	  }
	} */
	
</script>

<script>
	var currency_notes_coines = [ 2000, 500, 100, 50, 20, 10, 5, 1, 2, 5, 10, 1 ];
	/*last 1 for custom input value(others).*/

	var row_count = $("#branch_tr0").attr('count');
	console.log("row count : " + row_count);
	var operator = "";
	var table_rows_id = [];
	var row_all_inputs_val_temp_arr = [];
	var add_arr_of_arr = [];
	var sub_arr_of_arr = [];
	var total_arr = [];
	var index_of_htotal_row = 0;
	var index_of_hatm_row = 0;
	var index_of_hnonissue_row = 0;
	var index_of_hissue_row = 0;
	var atm_arr = [];
	var nonissue_arr = [];
	var issue_arr = [];

	for (var i = 0; i < row_count; i++) {
		table_rows_id.push('branch_tr' + i);
	}

	var valChange = function(obj) {

		var change_element_id = obj.attr('id');

		add_arr_of_arr = [];
		sub_arr_of_arr = [];
		total_arr = [];
		atm_arr = [];
		nonissue_arr = [];
		issue_arr = [];

		for (var i = 0; i < table_rows_id.length; i++) {

			row_all_inputs_val_temp_arr = [];
			operator = $('#' + table_rows_id[i]).attr('operator');

			if (operator == "total") {
				index_of_htotal_row = i;
			}
			if (operator == "atm") {
				index_of_hatm_row = i;
			}
			if (operator == "nonissue") {
				index_of_hnonissue_row = i;
			}
			if (operator == "issue") {
				index_of_hissue_row = i;
			}

			$('#' + table_rows_id[i] + ' input').each(
					function(index) {
						var input = $(this);

						/*console.log('Type: ' + input.attr('type')
						+ 'Name: ' + input.attr('name') + 'Value: '
						+ input.val());*/

						/*not use input value of entity parameter(index 0) and totals(index 13).*/
						if (index != 0 && index != 13) {
							if (input.val() == "") {
								row_all_inputs_val_temp_arr.push(0);
							} else {
								row_all_inputs_val_temp_arr.push(parseInt(input
										.val()));
							}
						}

					});

			if (operator == 'add') {
				add_arr_of_arr.push(row_all_inputs_val_temp_arr);
			} else if (operator == 'sub') {
				sub_arr_of_arr.push(row_all_inputs_val_temp_arr);
			} else if (operator == 'total') {
				total_arr.push(row_all_inputs_val_temp_arr);
			} else if (operator == 'atm') {
				atm_arr.push(row_all_inputs_val_temp_arr);
			} else if (operator == 'nonissue') {
				nonissue_arr.push(row_all_inputs_val_temp_arr);
			} else if (operator == 'issue') {
				issue_arr.push(row_all_inputs_val_temp_arr);
			}

			/* for sum value row by and put on vrtcl total*/
			if (true) {
				var row_total = 0;
				for (var j = 0; j < row_all_inputs_val_temp_arr.length; j++) {
					row_total = row_total
							+ (row_all_inputs_val_temp_arr[j] * currency_notes_coines[j]);
				}

				$("#branchParameterStatus_total_" + i).val(row_total);
			}
		}

		/* make single array of add from 2d array of add */
		var col_add_arr = [];
		for (var i = 0; i < add_arr_of_arr.length; i++) {
			for (var j = 0; j < add_arr_of_arr[i].length; j++) {
				var col_sum = 0;
				for (var k = 0; k < add_arr_of_arr.length; k++) {
					col_sum = col_sum + add_arr_of_arr[k][j];
				}
				col_add_arr.push(col_sum);
			}
		}

		/* make single array of sub from 2d array of sub */
		var col_sub_arr = [];
		for (var i = 0; i < sub_arr_of_arr.length; i++) {
			for (var j = 0; j < sub_arr_of_arr[i].length; j++) {
				var col_sum = 0;
				for (var k = 0; k < sub_arr_of_arr.length; k++) {
					col_sum = col_sum + sub_arr_of_arr[k][j];
				}
				col_sub_arr.push(col_sum);
			}
		}

		/* hard core common part ids of horzntl row*/
		var hrzntl_total_row_ids = [ 'branchParameterStatus_dn2000_',
				'branchParameterStatus_dn500_', 'branchParameterStatus_dn100_',
				'branchParameterStatus_dn50_', 'branchParameterStatus_dn20_',
				'branchParameterStatus_dn10_', 'branchParameterStatus_dn5_',
				'branchParameterStatus_dc1_', 'branchParameterStatus_dc2_',
				'branchParameterStatus_dc5_', 'branchParameterStatus_dc10_',
				'branchParameterStatus_others_' ];

		/* make a horzntl total row */
		for (var i = 0; i < hrzntl_total_row_ids.length; i++) {

			// 			This is my code with if else
			if ((col_add_arr[i] - col_sub_arr[i]) < 0) {
				/*Adjusting horizontal total also with this val*/
				var enteredValue = $("#" + change_element_id).val();
				console.log("id is : " + change_element_id + " and value is : "
						+ enteredValue);
				var data = change_element_id;
				var arr = data.split('_');
				console.log("arra values are index[0]: " + arr[0]
						+ " index[1] : " + arr[1] + " index[2] : " + arr[2]);
				var currency = arr[1].substring(2);
				currency = parseInt(currency);
				console.log("currency val : " + currency);
				var subtractValue = 0;
				subtractValue = parseInt(subtractValue);
				if (currency === "hers") {
					subtractValue = subtractValue * 1;
				} else {
					subtractValue = subtractValue * currency;
				}
				var total_id = arr[0] + "_total_" + arr[2];
				console.log("total id is : " + total_id);

				var totalIdValue = $("#" + total_id).val();
				totalIdValue = parseInt(totalIdValue);
				if (totalIdValue === subtractValue) {
					$("#" + total_id).val("");
				} else {
					$("#" + total_id).val(totalIdValue - totalIdValue);
				}

				/*Assigning blank to the current value if alerts conditin success*/
				$("#" + change_element_id).val("");
				alert("You can not remit more amount than the total amount exist");
				//return;
			} else {
				$("#" + hrzntl_total_row_ids[i] + index_of_htotal_row).val(
						col_add_arr[i] - col_sub_arr[i]);
			}

			// 			This is suraj sir code
			/* $("#" + hrzntl_total_row_ids[i] + index_of_htotal_row).val(
					col_add_arr[i] - col_sub_arr[i]); */
		}

		/* manage last 3 rows vertically. */
		var _operator = obj.closest("tr").attr('operator');
		var _vindex = obj.attr('vindex');
		_vindex = parseInt(_vindex) - 1;

		var atm_row_index_val = $(
				"#" + hrzntl_total_row_ids[_vindex] + index_of_hatm_row).val();
		var nonissue_row_index_val = $(
				"#" + hrzntl_total_row_ids[_vindex] + index_of_hnonissue_row)
				.val();
		var issue_row_index_val = $(
				"#" + hrzntl_total_row_ids[_vindex] + index_of_hissue_row)
				.val();

		var total_row_index_val = $(
				"#" + hrzntl_total_row_ids[_vindex] + index_of_htotal_row)
				.val();

		if (atm_row_index_val == "") {
			atm_row_index_val = 0;
		}
		if (nonissue_row_index_val == "") {
			nonissue_row_index_val = 0;
		}
		if (issue_row_index_val == "") {
			issue_row_index_val = 0;
		}
		if (total_row_index_val == "") {
			total_row_index_val = 0;
		}

		atm_row_index_val = parseInt(atm_row_index_val);
		nonissue_row_index_val = parseInt(nonissue_row_index_val);
		issue_row_index_val = parseInt(issue_row_index_val);
		total_row_index_val = parseInt(total_row_index_val);

		/*console
		.log( _vindex
				+ " | "
				+ _operator
				+ " | "
				+ (atm_row_index_val + nonissue_row_index_val + issue_row_index_val)
				+ " | " + total_row_index_val);*/

		if (_operator == 'atm' || _operator == 'nonissue'
				|| _operator == 'issue') {
			if ((atm_row_index_val + nonissue_row_index_val + issue_row_index_val) > total_row_index_val) {
				$("#" + change_element_id).val("");
				alert("You can not enter more than total denominations.");
				//return;

			}

		} else {
			if ((atm_row_index_val + nonissue_row_index_val + issue_row_index_val) > total_row_index_val) {
				$("#" + hrzntl_total_row_ids[_vindex] + index_of_hatm_row).val(
						"");
				$("#" + hrzntl_total_row_ids[_vindex] + index_of_hnonissue_row)
						.val("");
				$("#" + hrzntl_total_row_ids[_vindex] + index_of_hissue_row)
						.val("");
			}
		}

		/* manage last 3 rows horzntl for sum. */
		var atm_total = 0;
		var nonissue_total = 0;
		var issue_total = 0;

		for (var j = 0; j < hrzntl_total_row_ids.length; j++) {

			var atm_row_index_val = $(
					"#" + hrzntl_total_row_ids[j] + index_of_hatm_row).val();
			var nonissue_row_index_val = $(
					"#" + hrzntl_total_row_ids[j] + index_of_hnonissue_row)
					.val();
			var issue_row_index_val = $(
					"#" + hrzntl_total_row_ids[j] + index_of_hissue_row).val();

			if (atm_row_index_val == "") {
				atm_row_index_val = 0;
			}
			if (nonissue_row_index_val == "") {
				nonissue_row_index_val = 0;
			}
			if (issue_row_index_val == "") {
				issue_row_index_val = 0;
			}

			atm_row_index_val = parseInt(atm_row_index_val);
			nonissue_row_index_val = parseInt(nonissue_row_index_val);
			issue_row_index_val = parseInt(issue_row_index_val);

			atm_total = atm_total
					+ (atm_row_index_val * currency_notes_coines[j]);
			nonissue_total = nonissue_total
					+ (nonissue_row_index_val * currency_notes_coines[j]);
			issue_total = issue_total
					+ (issue_row_index_val * currency_notes_coines[j]);
		}

		$("#branchParameterStatus_total_" + index_of_hatm_row).val(atm_total);
		$("#branchParameterStatus_total_" + index_of_hnonissue_row).val(
				nonissue_total);
		$("#branchParameterStatus_total_" + index_of_hissue_row).val(
				issue_total);

		return;
	}

	function validateDenominations() {

		var i = 0;
		i = parseInt(i);
		$('.num').each(function() {
			console.log("pankaj : " + $(this).val());
			var numVal = $(this).val();
			if (numVal == "") {
				numVal = 0;
			}
			numVal = parseInt(numVal);

			if (numVal > 0) {
				i++;
				return false;
			}
		});

		if (i == 0) {
			alert("Please fill eod properly otherwise cancel the request");
			return false;
		}
		// 		alert("hello mjjjj "+i);
		var currency_notes_coines = [ "2000n", "500n", "100n", "50n", "20n",
				"10n", "5n", "1c", "2c", "5c", "10c", "1o" ];

		for (var j = 0; j < currency_notes_coines.length; j++) {
			var currency = currency_notes_coines[j];
			var noteTotal = $(".note-total-" + currency).val();
			var noteNonIssue = $(".note-nonissue-" + currency).val();
			var noteIssue = $(".note-issue-" + currency).val();
			var noteAtm = $(".note-atm-" + currency).val();

			if (noteTotal == "") {
				noteTotal = 0;
			}
			if (noteNonIssue == "") {
				noteNonIssue = 0;
			}
			if (noteIssue == "") {
				noteIssue = 0;
			}
			if (noteAtm == "") {
				noteAtm = 0;
			}

			noteTotal = parseInt(noteTotal);
			noteNonIssue = parseInt(noteNonIssue);
			noteIssue = parseInt(noteIssue);
			noteAtm = parseInt(noteAtm);

			if (noteTotal != (noteAtm + noteNonIssue + noteIssue)) {
				var totalRemains = noteTotal
						- (noteAtm + noteNonIssue + noteIssue);
				alert("Your "
						+ currency
						+ " denomination for issue, nonissue and atm fit notes not matched with total denomination required more "
						+ totalRemains);
				return false;
			} else {
				console.log("success valid");
			}
		}

		/* 
		var noteTotal2000 = $(".note-2000-total").val();
		var noteNonIssue2000 = $(".note-2000-nonissue").val();
		var noteIssue2000 = $(".note-2000-issue").val();
		var noteAtm2000 = $(".note-2000-atm").val();

		console.log("note c 2000 Total : " + noteTotal2000);
		console.log("note c 2000 Atm : " + noteAtm2000);
		console.log("note c 2000 NonIssue : " + noteNonIssue2000);
		console.log("note c 2000 Issue : " + noteIssue2000);

		if (noteTotal2000 == "") {
			noteTotal2000 = 0;
		}
		if (noteNonIssue2000 == "") {
			noteNonIssue2000 = 0;
		}
		if (noteIssue2000 == "") {
			noteIssue2000 = 0;
			console.log("inside issue : " + noteIssue2000);
		}
		if (noteAtm2000 == "") {
			noteAtm2000 = 0;
		}

		noteTotal2000 = parseInt(noteTotal2000);
		noteNonIssue2000 = parseInt(noteNonIssue2000);
		noteIssue2000 = parseInt(noteIssue2000);
		noteAtm2000 = parseInt(noteAtm2000);

		console.log("note 2000 Total : " + noteTotal2000);
		console.log("note 2000 Atm : " + noteAtm2000);
		console.log("note 2000 NonIssue : " + noteNonIssue2000);
		console.log("note 2000 Issue : " + noteIssue2000);

		if (noteTotal2000 != (noteAtm2000 + noteNonIssue2000 + noteIssue2000)) {
			alert("Your 2000 denomination for issue, nonissue and atm fit notes not matched with total denomination");
			return false;
		} else {
			console.log("success valid");
		}
		
		
		
		var noteTotal500 = $(".note-500-total").val();
		var noteNonIssue500 = $(".note-500-nonissue").val();
		var noteIssue500 = $(".note-500-issue").val();
		var noteAtm500 = $(".note-500-atm").val();

		if (noteTotal500 == "") {
			noteTotal500 = 0;
		}
		if (noteNonIssue500 == "") {
			noteNonIssue500 = 0;
		}
		if (noteIssue500 == "") {
			noteIssue500 = 0;			
		}
		if (noteAtm500 == "") {
			noteAtm500 = 0;
		}

		noteTotal500 = parseInt(noteTotal500);
		noteNonIssue500 = parseInt(noteNonIssue500);
		noteIssue500 = parseInt(noteIssue500);
		noteAtm500 = parseInt(noteAtm500);

		if (noteTotal500 != (noteAtm500 + noteNonIssue500 + noteIssue500)) {
			alert("Your 500 denomination for issue, nonissue and atm fit notes not matched with total denomination");
			return false;
		} else { 
			console.log("success valid");
		}
		
		
		
		var noteTotal100 = $(".note-100-total").val();
		var noteNonIssue100 = $(".note-100-nonissue").val();
		var noteIssue100 = $(".note-100-issue").val();
		var noteAtm100 = $(".note-100-atm").val();

		if (noteTotal100 == "") {
			noteTotal100 = 0;
		}
		if (noteNonIssue100 == "") {
			noteNonIssue100 = 0;
		}
		if (noteIssue100 == "") {
			noteIssue100 = 0;			
		}
		if (noteAtm100 == "") {
			noteAtm100 = 0;
		}

		noteTotal100 = parseInt(noteTotal100);
		noteNonIssue100 = parseInt(noteNonIssue100);
		noteIssue100 = parseInt(noteIssue100);
		noteAtm100 = parseInt(noteAtm100);

		if (noteTotal100 != (noteAtm100 + noteNonIssue100 + noteIssue100)) {
			alert("Your 100 denomination for issue, nonissue and atm fit notes not matched with total denomination");
			return false;
		} else { 
			console.log("success valid");
		}
		
		
		
		var noteTotal50 = $(".note-50-total").val();
		var noteNonIssue50 = $(".note-50-nonissue").val();
		var noteIssue50 = $(".note-50-issue").val();
		var noteAtm50 = $(".note-50-atm").val();

		if (noteTotal50 == "") {
			noteTotal50 = 0;
		}
		if (noteNonIssue50 == "") {
			noteNonIssue50 = 0;
		}
		if (noteIssue50 == "") {
			noteIssue50 = 0;			
		}
		if (noteAtm50 == "") {
			noteAtm50 = 0;
		}

		noteTotal50 = parseInt(noteTotal50);
		noteNonIssue50 = parseInt(noteNonIssue50);
		noteIssue50 = parseInt(noteIssue50);
		noteAtm50 = parseInt(noteAtm50);

		if (noteTotal50 != (noteAtm50 + noteNonIssue50 + noteIssue50)) {
			alert("Your 50 denomination for issue, nonissue and atm fit notes not matched with total denomination");
			return false;
		} else { 
			console.log("success valid");
		}
		
		
		
		var noteTotal20 = $(".note-20-total").val();
		var noteNonIssue20 = $(".note-20-nonissue").val();
		var noteIssue20 = $(".note-20-issue").val();
		var noteAtm20 = $(".note-20-atm").val();

		if (noteTotal20 == "") {
			noteTotal20 = 0;
		}
		if (noteNonIssue20 == "") {
			noteNonIssue20 = 0;
		}
		if (noteIssue20 == "") {
			noteIssue20 = 0;			
		}
		if (noteAtm20 == "") {
			noteAtm20 = 0;
		}

		noteTotal20 = parseInt(noteTotal20);
		noteNonIssue20 = parseInt(noteNonIssue20);
		noteIssue20 = parseInt(noteIssue20);
		noteAtm20 = parseInt(noteAtm20);

		if (noteTotal20 != (noteAtm20 + noteNonIssue20 + noteIssue20)) {
			alert("Your 20 denomination for issue, nonissue and atm fit notes not matched with total denomination");
			return false;
		} else { 
			console.log("success valid");
		}
		 */
	}

	/* function calLessAmt(obj) {
		var change_element_id = obj.attr('id');
		if (_operator == 'atm') {
			if ((atm_row_index_val + nonissue_row_index_val + issue_row_index_val) < total_row_index_val) {
				$("#" + change_element_id).val("");
				alert("You can not enter less than total denominations.");
				//return;
			}
		}
	} */
	/*$(document).ready(function(){

		//iterate through each textboxes and add keyup
		//handler to trigger sum event
		$(".num").each(function() {			
			$(this).keyup(function(){				
				calculateSum(this);
			});
		});

	});
	 */

	/*function calculateSum(ths) {
		console.log("object id : "+ths.id+"   and value : "+ths.value);
		var sum = 0;
		//iterate through each textboxes and add the values
		$(".num").each(function() {
			//add only if the value is number
			
			if(!isNaN(this.value) && this.value.length!=0) {
				var id = this.id;
				console.log("id is : "+id);
				var res = id.split("_"); 
				console.log("index 1 res : "+res[1]+" and total response is : "+res)
				
				if(res[1].startsWith("dn2000")){
					var totVal = document.getElementById("branchParameterStatus_total[0]").value
					
					var mul = (this.value * 2000) 
					sum+= parseFloat(mul)	
					var totalHorizontal = document.getElementById("branchParameterStatus_total[0]").value;
					console.log("sum is : "+sum);
					console.log("totalHorizontal is : "+totalHorizontal);
					var total_at_index = parseFloat(totalHorizontal)+parseFloat(sum);
					
					console.log("total_at_index is : "+total_at_index);
					document.getElementById("branchParameterStatus_total[0]").value=total_at_index;
				}
				
			}

		});*/
	//.toFixed() method will roundoff the final sum to 2 decimal places
	// 		$("#sum").html(sum.toFixed(2));
	/*}*/
</script>




