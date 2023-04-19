function documentDetail(id) {
	$("#listUser").html(""); 	
	$.ajax({
		url : "ClaimDocumentsDetail",
		type : 'POST',		 
		data : {
			reqId : id,			 
		},	 
	success : function(data) {		 
		$("#listUser").html(data);
	},
	error : function(e) {
		console.log(e);
	}
});
}

function decline(reqId,ths) {		
	$(ths).text('Wait..');
	$('#reqDecId').val(reqId);
	$('#declineModal').modal('show');	
}

function approve(reqId,ths) {		
	$(ths).text('Wait..');
	$('#reqid').val(reqId);
	$('#approveModal').modal('show');	
}

function validateDecline() {
	var reqDecId = $('#reqDecId').val();
	if (reqDecId=='') {
		return false;
	} else {
		return true;
	}
}
function validateApprove() {
	var reqid = $('#reqid').val();
	if (reqid=='') {
		return false;
	} else {
		return true;
	}
}
 
function pendingCommission(txnId) {
	$('#txnId').val(txnId);
	$('#pendingCommissionModal').modal('show');	
}
function validatePendingCommission() {	
	var txnId = $('#txnId').val();
	if (txnId=='') {
		return false;
	} else {
		return true;
	}
}

function setUid(to, claimId,txnId){	 
	$("#div").val(to);
	$("#sub").val('Claim ID: '+claimId);
	$("#message").val('TransactionID: '+txnId+'\nwrite something here..');
} 
 