<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content">
	<div class="row">
		<div class="container right_content_area">
			<div class="col-md-11 right_content_inner">
				<div id="header" class="fixed-header">
					<nav class="navbar navbar-default custom-header-area"
						role="navigation">
						<div class="navbar-header">
							<a class="navbar-brand" href="#">UserRole Management</a>
						</div>
					</nav>
				</div>

				<!-- Search form starts here -->

				<div id="header-4" class="fixed-header-4">
					<div class="box-table">
						<div class="box-header col-sm-8 ">
							<h3>
								<i class="halflings-icon white align-justify"></i><span
									class="break"></span>Associate Roles To User - <span
									class="edit-module-name">${userRole.user.firstName}
									${userRole.user.secondName} ${userRole.user.lastName}</span>
							</h3>
						</div>
						<!--<div class="box-header col-sm-4">
							 <a href="filin-manger.html" class="btn btn-success btn-lg btn3d"
								onclick="saveData(false)"> <span
								class="glyphicon glyphicon-save"></span> Save Security
							</a> <a href="filin-manger.html" class="btn btn-success btn-lg btn3d"
								onclick="saveData(false)"> <span
								class="glyphicon glyphicon-save"></span> Reset
							</a> <a href="filin-manger.html" class="btn btn-success btn-lg btn3d"
								onclick="saveData(false)"> <span
								class="glyphicon glyphicon-save"></span> back
							</a> 
						</div>-->
					</div>
				</div>
				<!-- <script>
					var mcaArray = new Array();
					var y = 0;
					<c:forEach items="${userRoleList}" var="userRoles">
						<c:forEach items="${userRoles.role.rolePermissions}" var="rolePermission">
							var title = '${rolePermission.permission.title}';									
							if(title === "can_be_checker"  || title === "can_be_approver" || title === "can_be_maker"){
								mcaArray[y] = '${rolePermission.permission.id}';
								console.log("j value : "+y+" and title "+title+" and unique array : "+mcaArray[y]);
								y++;
							}
							</c:forEach>
					</c:forEach>

					<c:forEach items="${roleList}" var="roles">
						 <c:forEach items="${roles.rolePermissions}" var="rolePermission">
							var title = '${rolePermission.permission.title}';										
							if(title === "can_be_checker"  || title === "can_be_approver" || title === "can_be_maker"){
								mcaArray[y] = '${rolePermission.permission.id}';
								console.log("y value : "+y+" and title "+title+" and unique array : "+mcaArray[y]);
								y++;
							}
						</c:forEach>
					</c:forEach>
				</script> -->
				<div id="header-2" class="fixed-header-2">
					<div class="form-Assoc">
						<form:form name="userRoleForm" id="userRoleForm"
							commandName="userRole" method="POST" class="aswd_dms_tbl"
							action="saveUserRoleAssociation">
							<form:hidden path="user.id" value="${user.id}" />
							<c:set var="referer" value="./usermanagement?id=user_management" />
							<c:choose>
								<c:when test="${byDirectRoleAssign}">									
									<c:set var="referer" value="${userRole.user.urlReferer}" />									
								</c:when>
								<c:otherwise>									
									<c:set var="referer" value="${header['Referer']}" />
								</c:otherwise>
							</c:choose>
							<form:hidden path="user.urlReferer" value="${referer}" />
							<div class="products col-sm-5 adding-section">
								<p class="heading">
									Select Roles:<br> <select multiple="" size="8"
										name="insertedVA" id="insertedVA" class="add-section_pro"
										ondblclick="addItem()">
										<c:forEach items="${roleList}" var="roles">
											<c:set value="false" var="flag" />																			
												<c:forEach items="${roles.rolePermissions}" var="rolePermission">													
													<c:set value="${rolePermission.permission.title}" var="title" />													
													<c:if test="${title == 'can_be_checker' || title == 'can_be_maker' || title == 'can_be_approver' }">
														<c:set value="true" var="flag" />												
													</c:if> 																							
												</c:forEach>
											<option mca="${flag}" value="${roles.id}">${roles.title}</option>										
										</c:forEach>
									</select>
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
									Selected Roles:<br> <select multiple="" size="8"
										name="selectedVAs" id="selectedVA" class="add-section_pro"
										ondblclick="removeItem()">
										<c:forEach items="${userRoleList}" var="userRoles">
											<c:set value="false" var="flag" />																			
												<c:forEach items="${userRoles.role.rolePermissions}" var="rolePermission">													
													<c:set value="${rolePermission.permission.title}" var="title" />													
													<c:if test="${title == 'can_be_checker' || title == 'can_be_maker' || title == 'can_be_approver' }">
														<c:set value="true" var="flag" />												
													</c:if> 																							
												</c:forEach>
											<option mca="${flag}" value="${userRoles.role.id}">${userRoles.role.title}</option>											
										</c:forEach>
									</select>
								</p>
							</div>
							<div id="hiddenvar"></div>
							<p>
								<button type="submit" class="btn btn-success btn-lg savebtn"
									onclick="return saveData()">Save</button>
								<a href="${referer}" class="btn btn-success btn-lg" id="cancelbtn">
									Cancel </a>
							</p>

							<script>
								// additeam-RemoveIteam
								var uniqueArray = new Array();
								var selectedArray = new Array();
								var deletedArray = new Array();
								var insertedArray = new Array();
								var indexInserted = new Array();								
								var j = 0;
								
							<%int i = 0;%>
								<c:forEach items="${userRoleList}" var="userRoles">
								selectedArray[
							<%=i%>
								] = '${userRoles.role.id}';
							<%i++;%>									
									<c:forEach items="${userRoles.role.rolePermissions}" var="rolePermission">
										var title = '${rolePermission.permission.title}';									
										if(title === "can_be_checker"  || title === "can_be_approver" || title === "can_be_maker"){
											uniqueArray[j] = '${rolePermission.permission.id}';
											console.log("j value : "+j+" and title "+title+" and unique array : "+uniqueArray[j]);
											j++;
										}
									</c:forEach>								
								</c:forEach>
								
								<c:forEach items="${roleList}" var="roles">
									 <c:forEach items="${roles.rolePermissions}" var="rolePermission">
										var title = '${rolePermission.permission.title}';										
										if(title === "can_be_checker"  || title === "can_be_approver" || title === "can_be_maker"){
											uniqueArray[j] = '${rolePermission.permission.id}';
											console.log("j value : "+j+" and title "+title+" and unique array : "+uniqueArray[j]);
											j++;
										}
									</c:forEach>
								</c:forEach>
							
								

								function addItem() {
									var fromList = document
											.getElementById("insertedVA");
									var toList = document
											.getElementById("selectedVA");
									p = 0;
									for (j = 0; j < fromList.length; j++) {
										if (fromList.options[j].selected == true) {
											console
													.log("fromList selected value : "
															+ fromList.options[j].value
															+ " and value is : "
															+ fromList.options[j].text);
											var match = false;
											for (q = 0; q < toList.length; q++) {
												if (toList.options[q].value == fromList.options[j].value) {
													match = true;
													$(toList.options[q]).show();
													console
															.log("if to and from list matches : "
																	+ toList.options[q].text
																	+ " and from list value : "
																	+ fromList.options[j].text);
													break;
												}
											}
											if (!match) {
												$(fromList.options[j]).hide();
												var mcaVal = $(fromList.options[j]).attr('mca');
												console.log("mca value : "+mcaVal+" and text is : "+fromList.options[j].text);
												
												 var opt = document.createElement('option');
												 opt.value = fromList.options[j].value;
												 opt.text = fromList.options[j].text;
												 if(mcaVal === 'true'){
													console.log("inside if ............. ");
												 	opt.setAttribute('mca', 'true');
												 }else if (mcaVal === 'false') {
													console.log("inside else ........... ");
													opt.setAttribute('mca', 'false');
												 } 
												 toList.options.add(opt);
												
												/* toList[toList.length] = new Option(
														fromList.options[j].text,
														fromList.options[j].value).setAttribute("mca","hjh");; */
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
															.log("If found inseted index is : "
																	+ insertedArray.length
																	+ " and value is : "
																	+ fromList.options[j].value);
												}
											}

											match = false;
											var n = 0;
											while (n < deletedArray.length) {
												console
														.log("inside while matching deleted array length : "
																+ deletedArray.length);
												if (fromList.options[j].value == deletedArray[n]) {
													console
															.log("matched toList to deleted array : "
																	+ deletedArray[n])
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
												console
														.log("deletedArray length = "
																+ deletedArray.length);
											}
											console
													.log("inserted array : "
															+ insertedArray.length
															+ " i.e inseted array : "
															+ insertedArray
															+ " deleted array length : "
															+ deletedArray.length
															+ " and deleted array is : "
															+ deletedArray);
										}
									}
								}

								function removeItem() {
									var fromList = document
											.getElementById("insertedVA");
									var toList = document
											.getElementById("selectedVA");
									for (j = toList.options.length - 1; j > -1; j--) {
										if (toList.options[j].selected == true) {
											match = false;
											for (m = 0; m < selectedArray.length; ++m) {
												if (toList.options[j].value == selectedArray[m]) {
													match = true;
													console
															.log("When tolist value present in selected array "
																	+ toList.options[j].value);
													break;
												}
											}

											for (q = 0; q < fromList.length; q++) {
												if (fromList.options[q].value == toList.options[j].value) {
													console
															.log("if from and to lists matched : "
																	+ toList.options[j].value
																	+ " text is : "
																	+ toList.options[j].text);
													match = false;
													$(fromList.options[q])
															.show();
													break;
												}
											}

											if (match) {
												deletedArray[deletedArray.length] = toList.options[j].value;
												fromList[fromList.length] = new Option(
														toList.options[j].text,
														toList.options[j].value);
												$(toList.options[j]).hide();
												console
														.log("Insewrting data to deleted array : "
																+ deletedArray
																+ " -- deleted array length "
																+ deletedArray.length);
											}

											match = false;
											var n = 0;
											while (n < insertedArray.length) {
												console
														.log("inside while matching inserted array length : "
																+ insertedArray.length);
												if (toList.options[j].value == insertedArray[n]) {
													console
															.log("matched toList to inserted array : "
																	+ insertedArray[n])
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
												console
														.log("new inserted array in right side length : "
																+ insertedArray.length);
											}
											if (match) {
												//	deletedArray[deletedArray.length] = toList.options[j].value;
												for (q = 0; q < fromList.length; q++) {
													if (fromList.options[q].value == toList.options[j].value) {
														$(fromList.options[q])
																.show();
														toList.options[j] = null;
														console
																.log("fromList selected value return to show at last : "
																		+ fromList.options[q].value);
														break;
													}
												}
											}
											//check if fund is already there
											console
													.log("inserted array in remove item is  : "
															+ insertedArray.length
															+ " i.e inseted array : "
															+ insertedArray
															+ " deleted array : "
															+ deletedArray.length
															+ " and deleted array is : "
															+ deletedArray);

										}
									}
								}

								function saveData() {
									$(".savebtn").css("display", "none");
									text = "";
									var x = 0;									
									var toLists = document.getElementById("selectedVA");									
									
									for (var k = 0 ; k < toLists.options.length; k++) {
										console.log("toList value for k "+k+" is : "+toLists.options[k].text);
										var mcaValFinal = $(toLists.options[k]).attr('mca');
										var displayVal = $(toLists.options[k]).css('display');
									//	alert(displayVal);
										console.log("tolist value : "+mcaValFinal+" value for text : "+toLists.options[k].text);
										if(mcaValFinal === 'true' && displayVal != 'none'){
											x++;											
										}
									}
									
									if(x > 1){
										alert("You cannot select maker checker or approver role for single user.");
										$(".savebtn").css("display", "block");
										return false;
									}
									console.log("x value is : "+x);
									for (var i = 0; i < deletedArray.length; ++i) {
										console
												.log("appending in input field delete array value : "
														+ deletedArray[i]);
										text += "<input type='hidden' name='deletedVA' value='" + deletedArray[i] + "'>";
									}
									for (var i = 0; i < insertedArray.length; i++) {
										console
												.log("appending in input field inserted array value : i "
														+ i
														+ " and array value "
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

<script type="text/javascript">
	document.getElementById('cancelbtn').onclick = function(e) {
		$("#cancelbtn").css("display", "none");
		return true;
	}
</script>