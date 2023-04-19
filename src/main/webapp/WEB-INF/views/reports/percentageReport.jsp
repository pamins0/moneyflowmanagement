<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- <script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script> -->

<script src="./static/js/jquery-ui.js"></script>
<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">


<script>
    $(function() {
        $('#fromDate').datepicker({
            dateFormat: 'dd-mm-yy',
            onSelect: function(selectedDate1) {
				
			    selectedDate1 = selectedDate1.split("-");
			    selectedDate1 = new Date(selectedDate1[2], selectedDate1[1] - 1, selectedDate1[0]);
			    
             /*    $("#toDate").removeAttr('disabled');
                $("#toDate").attr('disabled', 'disabled'); */
															
                var fromMinDate = new Date(selectedDate1.getFullYear(), selectedDate1.getMonth(), selectedDate1.getDate() + 1);
                $("#toDate").datepicker("option", "minDate", fromMinDate);
				$('#toDate').val("");
				
                $("#toDate").datepicker({
                    minDate: fromMinDate,
					dateFormat: 'dd-mm-yy',
                    onSelect: function(selectedDate2) {
				
                        selectedDate2 = selectedDate2.split("-");
                        selectedDate2 = new Date(selectedDate2[2], selectedDate2[1] - 1, selectedDate2[0]);
				
                    }
                });
            }
        });
    });
</script>




<section class="content-header">
	<h1>Percentage Report</h1>
	<ol class="breadcrumb">
		<li><a><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Bids</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-md-12 table-container-main">
			<div class="box-header">
				<div class="heading-title-wrapper">
					<i class="fa fa-tasks"></i>
					<h3 class="box-title">Bids Analytics</h3>
					<section class="content">
						<div class="row">
							<form:form action="percentageReport" id="NewEditForm"
								method="POST" modelAttribute="user" autocomplete="off"
								class="form-inline">
								<sec:authorize access="hasRole('can_view_everything')">
								<jsp:include page="../UtilityFiles/OrgToBranchManagement.jsp"></jsp:include>
								</sec:authorize>
								<sec:authorize access="!hasRole('can_view_everything')">
								<div class="form-group">
												<form:hidden path="branchManagement.hierarchyControl.orgManagement.orgType.id" id="orgManagementId" />
											</div>
											<div class="form-group">
												<form:hidden path="branchManagement.hierarchyControl.orgManagement.id" id="orgManagementId" />
											</div>
											<div class="form-group">											
												<label><fmt:message key="Common.form.HierarchyName" /></label>
												<form:select path="branchManagement.hierarchyControl.id" cssClass="form-control"
													id="hierarchyId">
													<form:option value="-1" label="- Hierarchy -" />
													<form:options items="${hierarchyList}" itemValue="id"
														itemLabel="name" />
												</form:select>
												<div class="has-error">
													<form:errors path="branchManagement.hierarchyControl.id" cssClass="help-inline" />
												</div>
											</div>
											<div class="form-group">
												<form:input placeholder="Enter entity name"
													path="branchManagement.branchName" id="branchManagementName"
													cssClass="form-control" />
												<form:hidden path="branchManagement.id" id="branchManagementId" />
											</div>
								 </sec:authorize>
									<%-- <c:choose>
										<c:when test="${ct:isAllowed('can_view_everything',requestScope['scopedTarget.requestScopedPermissions'].permissions)}">								
											<jsp:include page="../UtilityFiles/OrgToBranchManagement.jsp"></jsp:include>
										</c:when>
										<c:otherwise>
											<div class="form-group">
												<form:hidden path="branchManagement.hierarchyControl.orgManagement.orgType.id" id="orgManagementId" />
											</div>
											<div class="form-group">
												<form:hidden path="branchManagement.hierarchyControl.orgManagement.id" id="orgManagementId" />
											</div>
											<div class="form-group">											
												<label><fmt:message key="Common.form.HierarchyName" /></label>
												<form:select path="branchManagement.hierarchyControl.id" cssClass="form-control"
													id="hierarchyId">
													<form:option value="-1" label="- Hierarchy -" />
													<form:options items="${hierarchyList}" itemValue="id"
														itemLabel="name" />
												</form:select>
												<div class="has-error">
													<form:errors path="branchManagement.hierarchyControl.id" cssClass="help-inline" />
												</div>
											</div>
											<div class="form-group">
												<form:input placeholder="Enter entity name"
													path="branchManagement.branchName" id="branchManagementName"
													cssClass="form-control" />
												<form:hidden path="branchManagement.id" id="branchManagementId" />
											</div>
										</c:otherwise>
									</c:choose> --%>
									
									<div class="form-group">
										<form:input readonly="true" placeholder="From Date" cssClass="form-control" path="fromDate" id="fromDate" />
									</div>
									
									<div class="form-group">
										<form:input readonly="true" placeholder="To Date" cssClass="form-control" path="toDate" id="toDate" />
									</div>
									
									<div class="form-group button-group-set m20">
										<div class="col-sm-12">
											<button type="submit" class="btn btn-primary btn-sm"> Search</button>
										</div>
									</div>	
							</form:form>
						</div>						
					</section>
				</div>				
			</div>
			<%-- <c:if test="${ct:isAllowed('can_usermanagement_read',requestScope['scopedTarget.requestScopedPermissions'].permissions)}"> --%>
				<%-- <c:if test="${not empty auditTrials}"> --%>
				<div class="box-body">
					<div class="demo-container">
						<div>
							<%-- <!-- <table id="userListTable" -->
							<table 
								class="table table-bordered table-striped"
								style="max-height: 500px; overflow-y: scroll;">
								<thead>
									<tr class="info">
										<!-- <th class="text-center" style="max-width: 40px;"><i class="fa fa-cogs"></i></th> -->
										<th><fmt:message key="Report.list.totalExcess" /></th>
										<th><fmt:message key="Report.list.totalBelow" /></th>
										<th><fmt:message key="Report.list.totalLevel" /></th>										
									</tr>
								</thead>								
								
								<tbody>
									<c:forEach items="${auditTrials}" var="branch" varStatus="counter"> 
										<tr>
											<td class="text-center" style="max-width: 50px;">${counter.index+1}</td>
											<td bgcolor="#f2040433"><small>${totalExcess}</small></td>
											<td bgcolor="#0c2b024d"><small>${totalBelow}</small>
											<td bgcolor="#7b81ed"><small>${totalLevel}</small>
										</tr>
									</c:forEach>
								</tbody>								
							</table> --%>
						</div>
					</div>
				</div>
				<%-- </c:if> --%>
			<%-- </c:if> --%>
		</div>
	</div>
</section>

<section class="content">
				<div class="row">
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div  class="small-box bg-red">
							<div class="inner">
								<h3>${totalExcess}</h3>
								<p><fmt:message key="Report.list.totalExcess" /></p>
							</div>
							<!-- <div class="icon">
								<i class="fa fa-book"></i>
							</div>
							<a href="ViewTrainingDocuments" class="small-box-footer"> View
								All <i class="fa fa-arrow-circle-right"></i>
							</a> -->
						</div>
					</div>
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${totalBelow}</h3>
								<p><fmt:message key="Report.list.totalBelow" /></p>
							</div>
							<!-- <div class="icon">
								<i class="fa fa-video-camera"></i>
							</div>
							<a href="ViewTrainingVideos" class="small-box-footer"> View
								All <i class="fa fa-arrow-circle-right"></i>
							</a> -->
						</div>
					</div>
					<div class="col-lg-3 col-xs-6">					
						<div class="small-box bg-purple">
							<div class="inner">
								<h3>${totalLevel}</h3>
								<p><p><fmt:message key="Report.list.totalLevel" /></p></p>
							</div>
							<!-- <div class="icon">
								<i class="fa fa-list-alt"></i>
							</div>
							<a href="Exams" class="small-box-footer"> Select
								One <i class="fa fa-arrow-circle-right"></i>
							</a> -->
						</div>
					</div>					
				</div>
			</section>


<div class="modal fade" id="myModalDenomination" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<h4 class="modal-title" id="myModalLabel">Denominations</h4>
			<div class="modal-body">
				<div class="modal-header" id="denominationDiv">
					<!-- Append denomination here -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>


<script>
	var baseUrl = "${pageContext.request.contextPath}/getBranchManagementForHierarchyAutoComplete";
	$("#hierarchyId").ready(function() {
		if($("#hierarchyId").val() != "" || $("#hierarchyId").val() != "-1"){
			//$("#branchManagementName").val("");
			//$("#branchManagementId").val("");
		//	$('#designationId').empty();
		//	getDesignationsByHierarchyId();

			$('#branchManagementName').autocomplete({
				autoSelectFirst : true,
				serviceUrl : baseUrl + addHierarchyId(),
				paramName : "tagName",
				delimiter : ",",
				onSelect : function(suggestion) {
					$("#branchManagementId").val(suggestion.data);
					//getDesignationsByHierarchyId();
					if (suggestion.branchType == 1) {
						$('#approverDiv').show(true);
						console.log("true");
					} else {
						$('#approverDiv').hide(true);
						console.log("false");
					}
					return false;
				},
				transformResult : function(response) {
					return {
						//must convert json to javascript object before process
						suggestions : $.map($.parseJSON(response), function(item) {
							return {
								value : item.branchName,
								data : item.id,
								branchType : item.branchType
							};
						})
					};
				}
			});
		}
		
		
	});
	$("#hierarchyId").change(function() {

		$("#branchManagementName").val("");
		$("#branchManagementId").val("");
		$('#designationId').empty();
		getDesignationsByHierarchyId();

		$('#branchManagementName').autocomplete({
			autoSelectFirst : true,
			serviceUrl : baseUrl + addHierarchyId(),
			paramName : "tagName",
			delimiter : ",",
			onSelect : function(suggestion) {
				$("#branchManagementId").val(suggestion.data);				
				if (suggestion.branchType==1) {
					$('#approverDiv').show(true);
					console.log("true");
				} else {
					$('#approverDiv').show(false);
					console.log("false");
				}
				return false;
			},
			transformResult : function(response) {
				return {
					//must convert json to javascript object before process
					suggestions : $.map($.parseJSON(response), function(item) {
						return {
							value : item.branchName,
							data : item.id,
							branchType : item.branchType
						};
					})
				};
			}
		});
	});

	function addHierarchyId() {
		return '?hierarchyId=' + $('#hierarchyId').val();
	}
	
	function getDesignationsByHierarchyId() {

		var id = $('#hierarchyId').val();

		$.ajax({
			url : "getDesignationsByHierarchyId",
			dataType : 'json',
			data : {
				hId : id,
			},
			type : "post",
		}).done(
				function(result) {
					$('#designationId').empty();
					$('#designationId').append(
							$("<option></option>").attr("value", "-1").text("-Select Designation-"));
					result.forEach(function(res) {
						console.log(res.title);
						$('#designationId').append(
								$("<option></option>").attr("value", res.id).text(res.title));
					});					
				}).fail(function(e) {
			console.log(e);
		});
	}
</script>

<script>
function showDenominations(dn2000,dn500, dn100, dn50,dn20,dn10, dn5, dc1,dc2,dc5,dc10,others){
	 console.log("dn2000 = "+dn2000+" | dn500 = "+dn500+" dn100 = "+dn100+" | dn50 = "+dn50+" | dn20 = "+dn20+" | dn10 = "+dn10+" | dn5 = "+dn5);
	 console.log("dn1 = "+dc1+" | dc2 = "+dc2+" | dc5 = "+dc5+" | dc10 = "+dc10+" others = "+others);
	 
	 var newdiv = document.createElement('div');

	 newdiv.innerHTML =  "<label>2000 * "+dn2000+"</label>";
	
	 newdiv.innerHTML += "<label>500 * "+dn500+"</label>";
	
	 newdiv.innerHTML += "<label>100 * "+dn100+"</label>";
	
	 newdiv.innerHTML += "<label>50 * "+dn50+"</label>";
	
	 newdiv.innerHTML += "<label>20 * "+dn20+"</label>";
	
	 newdiv.innerHTML += "<label>10 * "+dn10+"</label>";
	
	 newdiv.innerHTML += "<label>5 * "+dn5+"</label>";
	 newdiv.innerHTML +=  "<br>";
	 newdiv.innerHTML += "<label>1 * "+dc1+"</label>";
	
	 newdiv.innerHTML += "<label>2 * "+dc2+"</label>";
	
	 newdiv.innerHTML += "<label>5 * "+dc5+"</label>";
	
	 newdiv.innerHTML += "<label>10 * "+dc10+"</label>";
	
	 newdiv.innerHTML +=  "<label>others * "+others+"</label>";
//	 document.getElementById("d1").appendChild(newdiv);
	 $("#denominationDiv").html(newdiv);
}

</script>

<!-- Tapan sirs work -->
 <script>
$(document).ready(function() {
    $('#userListTable').DataTable( {
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

