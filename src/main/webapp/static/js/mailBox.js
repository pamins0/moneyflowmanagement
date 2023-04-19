function getMultipleCheckbox(inputdata) {
	var selectedItems = [];
	var count = 0;
	for (var i = 0; i < inputdata.length; i++) {
		if (inputdata[i].checked) {
			selectedItems[count] = inputdata[i].value;
			count++;
		}
	}
	if (count == 0) {
		alert("Select atleast one mail");
		return false;
	} else {
		$.ajax({
			url : "MailDelete",
			type : 'POST',
			data : {
				selItem : selectedItems,
			}
		}).done(function(data) {
			window.location.reload(true);
		});
	}
} 
function viewMail(id, rstatus) {
	$.ajax({
		url : "ViewMail",
		type : 'POST',
		dataType : 'json',
		data : {
			mid : id,
			status : rstatus,
		}
	}).done(function(data) {
		if (data != null) {
			$("#sub").text('Subject : ' + data.sub);
			$("#date").text('Date : ' + data.date + ' ' + 'Time : '+ data.time);
			$("#to").text('From : ' + data.sender);
			$("#cc").text('CC : ' + data.cc);
			$("#bcc").text('Bcc : ' + data.bcc);
			$("#msg").text(data.msg);
			$("#dId").attr("href", 'DownloadAttachment?id='+data.mailId);
			$("#replyButton").val(data.sender);
			$("#subjectForReply").val('Reply : '+data.sub);
		}
	});
}

//reply button code

function reply(ths) {
	$('#compose-modal1').modal('hide');
	$('#compose-modal').modal('show');
	var replyTo=$(ths).val();
	var subjectForReply=$("#subjectForReply").val();
	$("#emailTo").val(replyTo);
	$("#subject").val(subjectForReply);
}

function starStatus(id, ths) {	
	$.ajax({
		url : "StarStatus",
		type : 'POST',		 
		data : {
			mailId : id,			
		}
	}).done(function(response) {	
		if (response == "y") {			
			$(ths).removeClass("fa fa-star-o");
			$(ths).addClass("fa fa-star");			
		}else if (response == "n") {			
			$(ths).removeClass("fa fa-star");
			$(ths).addClass("fa fa-star-o");			
		}
	});
}

//viewAgents.jsp 

function setUid(ths){
	var to=ths.id;	
	$("#div").val(to);
}

function sendMailToAgent(from,sub,message) {
	var to=$("#div").val();	 
	$.ajax({
		url : "MailByAgent",
		type : 'POST',	 
		data : {
			message:message,
			sub:sub,
			email_to:to,		 
		}
	}).done( function(response) {	
		if(response=="agent"){			
			window.location.href = "ViewAgentDetail";
			$("#compose-modal").model('hide');
		}	
	});	
}
 