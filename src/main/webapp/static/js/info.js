$(document).ready(function() {
	$.ajax({
		url : "Info",
		dataType : 'json',		 
	}).done(function(data) { 
		$("#totalRewards").text(data.totalRewads+' RM');			 
		$('#myRewards').text(data.totalRewads+' RM');
	}).fail(function(e) {		
	}); 
});
 