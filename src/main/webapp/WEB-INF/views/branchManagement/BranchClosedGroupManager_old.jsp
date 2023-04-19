<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content">
	<div class="row">
		<div class="container right_content_area">
			<div class="col-md-11 right_content_inner">
				<div id="header" class="fixed-header">
					<nav class="navbar navbar-default custom-header-area" role="navigation">
						<div class="navbar-header">
							<a class="navbar-brand" href="#">Closed Group Management</a>							
						</div>
					</nav>
				</div>

				<!-- Search form starts here -->

				<div id="header-4" class="fixed-header-4">
					<div class="box-table">
						<div class="box-header col-sm-8 ">
							<h3>
								<i class="halflings-icon white align-justify"></i><span
									class="break"></span>Associate Closed Group Branches - <span class="edit-module-name">${branchClosedGroup.parentBranch.branchName}</span>
							</h3>
						</div>
					</div>
				</div>
				<div id="header-2" class="fixed-header-2">
					<div class="form-Assoc">
						<form:form name="userRoleForm" id="userRoleForm" commandName="branchClosedGroup"
							method="POST" class="aswd_dms_tbl"
							action="saveClosedBranchAssociation">
							<form:hidden path="parentBranch.id" value="${parentBranch.id}" />
							<c:choose>
								<c:when test="${byDirectRoleAssign}">
									<c:set var="referer" value="${branchManagement.referer}" />
								</c:when>
								<c:otherwise>
									<c:set var="referer" value="${header['Referer']}" />
								</c:otherwise>
							</c:choose>							
							<form:hidden path="parentBranch.urlReferer" value="${referer}" />
							<div class="products col-sm-5 adding-section">
								<p class="heading">
									Select Branch:<br>
									<form:select id="insertedVA" path="insertedVA"
										ondblclick="addItem()" cssClass="add-section" size="8">
										<%-- <form:option label="New Roles" value="" /> --%>
										<form:options items="${roleList}" itemLabel="branchName"
											itemValue="id" />
									</form:select>
								</p>
							</div>
							<div class="col-sm-2 c0">
								<div class=" DataEntry">
									<div class="new">
										<div class="add">
											<a href="javascript:addItem()"><i class="fa fa-plus"></i></a>
										</div>
										<div class="add">
											<a href="javascript:removeItem()"><i class="fa fa-minus"></i></a>
										</div>
									</div>
								</div>
							</div>
							<div class="products col-sm-5">
								<p class="heading">
									Selected Branches:<br> <select multiple="" size="8"
										name="selectedVAs" id="selectedVA" class="add-section_pro"
										ondblclick="removeItem()">
										<c:forEach items="${userRoleList}" var="userRoles">
											<option value="${userRoles.closedGroupBranch.id}">${userRoles.closedGroupBranch.branchName}</option>
										</c:forEach>
									</select>
								</p>
							</div>
							<div id="hiddenvar"></div>
							<p>
								<button type="submit" class="btn btn-success btn-lg"
									onclick="saveData()">
									Save
								</button>
								<a href='${referer}' class="btn btn-success btn-lg"
									onclick=""> Cancel
								</a>
							</p>

							<script>
								// additeam-RemoveIteam

								var selectedArray = new Array();
								var deletedArray = new Array();
								var insertedArray = new Array();
								var indexInserted = new Array();
							<%int i = 0;%>
								<c:forEach items="${userRoleList}" var="userRoles">
								selectedArray[
							<%=i%>
								] = '${userRoles.closedGroupBranch.id}';
							<%i++;%>
								</c:forEach>
								
								function addItem() {									
									var fromList = document.getElementById("insertedVA");
									var toList = document.getElementById("selectedVA");									
									p = 0;
									for(j=0; j<fromList.length; j++){
										if (fromList.options[j].selected == true) {
											console.log("fromList selected value : "+fromList.options[j].value+" and value is : "+fromList.options[j].text);
											var match = false;
											for (q = 0; q < toList.length; q++) {
												if (toList.options[q].value == fromList.options[j].value) {
													match = true;	
													$(toList.options[q]).show();
													console.log("if to and from list matches : "+toList.options[q].text+" and from list value : "+fromList.options[j].text); 
													break;
												}
											}
											if (!match) {
												$(fromList.options[j]).hide();
												toList[toList.length] = new Option(
														fromList.options[j].text,
														fromList.options[j].value);
												found = false;
												for (m = 0; m < selectedArray.length; ++m) {
													if (fromList.options[j].value == selectedArray[m]) {
														found = true;
														break;
													}
												}
												if (!found) {
													insertedArray[insertedArray.length] = fromList.options[j].value;
													console.log("If found inseted index is : "+ insertedArray.length + " and value is : "+fromList.options[j].value );
												}
											}
											
											match = false;
											var n = 0;
											while (n < deletedArray.length) {
												console.log("inside while matching deleted array length : "+deletedArray.length);
												if (fromList.options[j].value == deletedArray[n]) {													
													console.log("matched toList to deleted array : "+deletedArray[n])
													fromList.options[j] = null;		
													match = true;
													break
												}
												++n;
											}
											if (match) {
												var newDeletedArray = new Array();
												var u = 0;
												for (t = 0; t < deletedArray.length; ++t) {
													if (t != n) {
														newDeletedArray[u] = deletedArray[t]
														u++;
													}
												}
												deletedArray = newDeletedArray;
												console.log("deletedArray length = "+deletedArray.length);
											}
											console.log("inserted array : "+insertedArray.length+" i.e inseted array : "+insertedArray+" deleted array length : "+deletedArray.length+" and deleted array is : "+deletedArray+" selected array is : "+selectedArray);
										}
									}
								}
								
								
								function removeItem() {
									var fromList = document.getElementById("insertedVA");
									var toList = document.getElementById("selectedVA");
									for (j = toList.options.length - 1; j > -1; j--) {
										if (toList.options[j].selected == true) {
											match = false;											
											for (m = 0; m < selectedArray.length; ++m) {
												if (toList.options[j].value == selectedArray[m]) {
													match = true;
													console.log("When tolist value present in selected array "+toList.options[j].value);
													break;
												}
											}
											
											for (q = 0; q < fromList.length; q++) {
												if (fromList.options[q].value == toList.options[j].value) {
													console.log("if from and to lists matched : "+toList.options[j].value +" text is : "+toList.options[j].text);
													match = false;	
													$(fromList.options[q]).show();										
													break;
												}
											}

											if (match) {
												deletedArray[deletedArray.length] = toList.options[j].value;
												fromList[fromList.length] = new Option(
														toList.options[j].text,
														toList.options[j].value);
												$(toList.options[j]).hide();
												console.log("Insewrting data to deleted array : "+deletedArray+" -- deleted array length "+ deletedArray.length);
											}
											
											match = false;
											var n = 0;
											while (n < insertedArray.length) {
												console.log("inside while matching inserted array length : "+insertedArray.length);
												if (toList.options[j].value == insertedArray[n]) {
													console.log("matched toList to inserted array : "+insertedArray[n])
												//	$(fromList.options[j]).show();
												//	console.log("fromList selected value return to show : "+fromList.options[j].value);
													
													match = true;
													break;
												}
												++n;
											}
											if (match) {
												var newInsertedArray = new Array();
												var u = 0;
												for (t = 0; t < insertedArray.length; ++t) {												
													if (t != n) {
														newInsertedArray[u] = insertedArray[t]
														u++;
													}
												}
												insertedArray = newInsertedArray;
												console.log("new inserted array in right side length : "+insertedArray.length);
											}
											if (match) {
												//	deletedArray[deletedArray.length] = toList.options[j].value;
												for (q = 0; q < fromList.length; q++) {
													if (fromList.options[q].value == toList.options[j].value) {
														$(fromList.options[q]).show();
														toList.options[j] = null;
														console.log("fromList selected value return to show at last : "+fromList.options[q].value);
														break;
													}
												}
											}
											//check if fund is already there
											console.log("inserted array in remove item is  : "+insertedArray.length+" i.e inseted array : "+insertedArray+" deleted array : "+deletedArray.length+" and deleted array is : "+deletedArray+" selected array is : "+selectedArray);
											
										}
									}
								}

								function addItems() {
									var fromList = document
											.getElementById("insertedVA");
									var toList = document
											.getElementById("selectedVA");

									console
											.log("In remove item fromList length : "
													+ fromList.length);
									console
											.log("In remove item toList length : "
													+ toList.length);
									
									p = 0;
									for (j = 0; j < fromList.length; j++) {
										if (fromList.options[j].selected == true) {
											console
													.log("fromList selected value : "
															+ fromList.options[j].value);
											var match = false;
											//check if role is already there
											for (q = 0; q < toList.length; q++) {
												if (toList.options[q].value == fromList.options[j].value) {
													match = true;
													break;
												}
											}
											if (!match) {
												console
														.log("FromList option value at "
																+ j
																+ " is: "
																+ fromList.options[j].value);
												indexInserted[p++] = j;
												$(fromList.options[j]).hide();
												//$('.adding-section').remove();
												toList[toList.length] = new Option(
														fromList.options[j].text,
														fromList.options[j].value)
												found = false;
												for (m = 0; m < selectedArray.length; ++m) {
													if (fromList.options[j].value == selectedArray[m]) {
														found = true;
														break;
													}
												}

												if (!found) {
													insertedArray[insertedArray.length] = fromList.options[j].value;
													console
															.log("insertedArray values : "
																	+ insertedArray);
												}
											}

											match = false;
											var n = 0;
											while (n < deletedArray.length) {
												if (fromList.options[j].value == deletedArray[n]) {
													match = true;
													break
												}
												++n;
											}
											if (match) {
												var newDeletedArray = new Array();
												var u = 0;
												for (t = 0; t < deletedArray.length; ++t) {
													if (t != n) {
														newDeletedArray[u] = deletedArray[t]
														u++;
													}
												}
												deletedArray = newDeletedArray;
											}
										}
									}
								}

								function removeItems() {
									var fromList = document
											.getElementById("insertedVA");
									var toList = document
											.getElementById("selectedVA");

									console
											.log("In remove item fromList length : "
													+ fromList.length);
									console
											.log("In remove item toList length : "
													+ toList.length);

									for (j = toList.options.length - 1; j > -1; j--) {
										if (toList.options[j].selected == true) {
											match = false;
											for (m = 0; m < selectedArray.length; ++m) {
												if (toList.options[j].value == selectedArray[m]) {
													match = true;
													break;
												}
											}

											if (match) {
												deletedArray[deletedArray.length] = toList.options[j].value;
											}

											match = false;
											var n = 0;
											while (n < insertedArray.length) {
												if (toList.options[j].value == insertedArray[n]) {
													console
															.log("in remove item value : "
																	+ toList.options[j].value);
													$(fromList.options[j])
															.show();
													match = true;
													break;
												}
												++n;
											}
											if (match) {
												var newInsertedArray = new Array();
												var u = 0;
												for (t = 0; t < insertedArray.length; ++t) {
													console.log("t value : "
															+ t
															+ " and n value : "
															+ n)
													if (t != n) {
														newInsertedArray[u] = insertedArray[t]
														u++;
													}
												}
												insertedArray = newInsertedArray;
												console
														.log("insertedarray length in remove item : "
																+ insertedArray.length);
											}
											if (match) {
												//	deletedArray[deletedArray.length] = toList.options[j].value;
												for (q = 0; q < fromList.length; q++) {
													if (fromList.options[q].value == toList.options[j].value) {
														console
																.log("value inside : "
																		+ fromList.options[q].value);
														$(fromList.options[q])
																.show();
														break;
													}
												}

											}
											//check if fund is already there
											toList.options[j] = null
										}
									}
									//$(selectList.options[j]).show();
								}

								function saveData() {
									text = "";
									for (var i = 0; i < deletedArray.length; ++i) {
										console
												.log("appending in input field delete array value : "
														+ deletedArray[i]);
										text += "<input type='hidden' name='deletedVA' value='" + deletedArray[i] + "'>";
									}
									for (var i = 0; i < insertedArray.length; i++) {
										console.log("appending in input field inserted array value : i "+i+" and array value "
														+ insertedArray[i]);
										text += "<input type='hidden' name='selectedVA' value='" + insertedArray[i] + "'>";
									}
									text += "<input type='hidden' name='N_directfiling' value='" +false + "'>";

									var divVar = document
											.getElementById("hiddenvar");
									divVar.innerHTML = text;
									console
											.log("On Submit array inserted length is : "
													+ insertedArray.length);
// 									alert("inserted array : "+insertedArray+" length is : "+insertedArray.length);
// 									alert("deleted array : "+deletedArray+" length is : "+deletedArray.length);
									/* var fromList = document.getElementById("insertedVA");
									var toList = document.getElementById("selectedVA");
									alert("from length is : "+fromList.options.length+" fromList values : "+fromList);
									alert("to length is : "+toList.options.length+" toList values : "+toList); */
									document.userRoleForm.submit();
								}

								function goBack() {
									/*                
									 if(false) {
									 window.close();
									 }
									 */

									// 									document.location.href = "documentdisp.jsp?N_filingid=15674&N_directfiling=false";
								}
							</script>

						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>