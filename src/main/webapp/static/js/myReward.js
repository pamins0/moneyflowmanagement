function claimThis(txnId,propId,netAmount,finalAmount,cScheme) {
	 
	$("#txnId").val(txnId);		
	$("#netAmount").val(netAmount);
	$("#finalAmount").val(finalAmount);
	$("#cScheme").val(cScheme);
	$.ajax({
		url : "GetPropertyDetail",
		dataType : 'json',
		data : {
			id : propId,
		},
		type : "post",
	}).done(function(data) {		 
		$("#propertyDetail").html("");
		var htmlForPropertyDetailDiv='<div class="box-body"><div class="user-panel"><div class="pull-left image"><img src="ImageDisplay?id='+data.pid+'"></div><div class="pull-left info"><p>'+data.title+'</p><p><small>Listing Date: '+data.date+'</small></p><p><small>Owner Name: '+data.o_name+'</small></p><p><small>Address: '+data.address+'</small></p></div></div></div>';
		$("#propertyDetail").append(htmlForPropertyDetailDiv);
	}).fail(function(e) {
		console.log("Ajax Fail:"+e);
	});	
	console.log('txn:'+txnId);
	console.log('netAmount:'+netAmount);
	console.log('finalAmount:'+finalAmount);
	$('#commissionClaimModel').modal('show');	
}