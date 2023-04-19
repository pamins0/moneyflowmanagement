$(document).ready(function() {
	
	$("#back_to_info").click(function() {
		$('#navs a[href="#info"]').tab('show');
	});

	$("#back_to_parties").click(function() {
		$('#navs a[href="#parties"]').tab('show');
	});

	$("#back_to_dates").click(function() {
		$('#navs a[href="#dates"]').tab('show');
	});
	$("#back_to_documents").click(function() {
		$('#navs a[href="#documents"]').tab('show');
	});

	// code for date picker
	$('#datetimepicker').datetimepicker({defaultDate : new Date()});	
	$('#datetimepicker1').datetimepicker();		
	$('#datetimepicker2').datetimepicker({defaultDate : new Date()});	
	$('#datetimepicker3').datetimepicker({defaultDate : new Date()});
	 
	// property selecton change
	$("#propSelection").change(function() {
		var val = $("#propSelection").val();		
		$.ajax({
			url : "GetPropertyDetail",
			dataType : 'json',
			data : {
				id : val,
			},
			type : "post",
		}).done(function(data) {
			
			$("#propertyDetail").html("");
			var htmlForPropertyDetailDiv='<div class="box-body"><div class="user-panel"><div class="pull-left image"><img src="ImageDisplay?id='+data.pid+'"></div><div class="pull-left info"><p>'+data.title+'</p><p><small>Listing Date: '+data.date+'</small></p><p><small>Owner Name: '+data.o_name+'</small></p><p><small>Address: '+data.address+'</small></p></div></div></div>';
			$("#propertyDetail").append(htmlForPropertyDetailDiv);

		}).fail(function(e) {
		});
	});

	// add parties btn click
	$("#addPartiesBtn").click(function() {			
		var typ=$("#partyType").val(); 	 
		if (typ!="") { 
			$.ajax({
				url : "GetParties",
				dataType : 'json',
				data : {				 
					partyTyp: typ, 
				},
				type : "post",
			}).done(function(POJOs) {				
				$("#listUser").html('<input type="hidden" name="addType" value="'+typ+'">');	
				if (POJOs != null) {
					POJOs.forEach(function(entry) {							
						var className=getRandonClass();					
						var addPartiesBtnHtml='<i class="fa fa-user"></i><label> &nbsp;&nbsp;&nbsp;<input type="radio" name="uId" required="required" value="'+entry.uid+'">&nbsp;&nbsp;<small class="'+className+'">'+entry.name+' '+entry.lname+'</small></label><small class="'+className+'">Email:'+entry.email+'</small><small class="'+className+'">Contact: '+entry.contact1+'</small><br>';
						$("#listUser").append(addPartiesBtnHtml);						
					}); 				 
					$("#submitBtn").show();
				} else {
					window.location.href = 'Login';
				}				
			}).fail(function(e) {
			});
		}else{				 
			$("#submitBtn").hide();
			$("#listUser").html('<h1>Please select party type</h1>'); 
		}
	}); 
	
	$("#addOwnerBtn").click(function() { 		
		$('#compose-modal').modal('hide');	
		$('#addOwnerModel').modal('show');
	}); 
	  
	 function getRandonClass() {		 
		var textArray = [
		                 'badge bg-maroon',
		                 'badge bg-red',
		                 'badge bg-yellow',
		                 'badge bg-green',
		                 'badge bg-blue',
		                 'badge bg-black',
			            ];
		
		var randomNumber = Math.floor(Math.random()*textArray.length);	
		var randomClass=textArray[randomNumber];
		return randomClass;
	}//getRandonClass ends here
});
		// selection verify on submit of adding party
function varifySelection() {
	var ans_id = $("input[name='uId']:checked").val();	
	if (ans_id != null && ans_id != '') {
		return true;				
	} 
	return false;
}