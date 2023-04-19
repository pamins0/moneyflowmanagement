function documentDetail(id) {
	$("#listUser").html("");
	$.ajax({
		url : "DownloadDetail",
		type : 'POST',
		dataType : 'json',
		data : {
			fId : id,			 
		},	 
	success : function(data) {
		data.forEach(function(entry) {							 
			var xx='<li><span class="handle"> <i class="fa fa-user"></i> &nbsp;&nbsp; <span class="text">'+entry.uId+'</span> &nbsp;&nbsp;</span><span class="text"> '+entry.message+'</span> &nbsp;  <small class="label label-danger">'+entry.dateTime+'</small></li>';
	$("#listUser").append(xx);
		});
	},
	error : function(data, status, er) {
	}
});
}

function submiting() {
	$("#uploadBtn").hide();
	$("#modalbody").hide();
	$("#uploadLoader").show();	
}

 


