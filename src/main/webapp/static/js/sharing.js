function shareIt(shareitemid) {	
	$.ajax({
		url : "ShareUserList",
		type : "GET",		
		data : {
			shareItemId : shareitemid,
		},
	}).done(function(resp) {
		if (resp != 'nouser') {			
			$('#shareItemDialogModel').html(resp);
			$('#shareItemDialogModel').modal('show');
		} else {
		}
	}).fail(function(e) {
		console.log('share it ajax fail');
	});
}

function clr() {
	$('#errLabel').html('');
}

function validateChk(inputdata) {
	var selectedItems = [];
	var count = 0;
	for (var i = 0; i < inputdata.length; i++) {
		if (inputdata[i].checked) {
			selectedItems[count] = inputdata[i].value;
			count++;
		}
	}
	var remark =  $('#shareremark').val();	
	if (count == 0) {
		$('#errLabel').html('Select atleast one member!');
		return false;
	} else if(remark ==null || remark ==""){
		$('#errLabel').html('Please write something as remark!');
		return false;
	}
}