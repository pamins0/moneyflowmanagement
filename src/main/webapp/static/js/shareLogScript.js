$(document).ready(function() {

	$('#741258963').click(function(event) {
		$("#741258963").prop('disabled', true);
		$('#7412589613').attr('src', 'img/loader2.gif');		
		var uid = $('#456123789').val();
		var upass = $('#321456987').val();		
		$.ajax({
			url : "ShareLogin",	
			type : "POST",
			data : {
				uId : uid,
				uPass : upass,
			}
		}).done(function(input) {
			console.log(input);
			if (input == 'ok') {
				window.location.href = "SharedItems";
			} else if (input == 'invalid') {
				$('#errLabel').text("Invalid Detail");
				$('#7412589613').attr('src', 'img/lookInside.png');
				$("#741258963").prop('disabled', false);
			} else if (input == 'internalError') {
				$('#errLabel').text('Please try later.!');
				$('#7412589613').attr('src', 'img/lookInside.png');
				$("#741258963").prop('disabled', false);
			} else if (input == 'existing') {
				window.location.href = "SharedItems";
			} else {
				$('#errLabel').text(input);
				$('#7412589613').attr('src', 'img/lookInside.png');
				$("#741258963").prop('disabled', false);
			}
		}).fail(function(e) {
			$('#errLabel').text("Oops! Try again.");
			$('#7412589613').attr('src', 'img/lookInside.png');
			$("#741258963").prop('disabled', false);
		});
	});
});

function clr() {
	document.getElementById("errLabel").innerHTML = "";
}

$(document).keypress(function(event){	 
	 var keycode = (event.keyCode ? event.keyCode : event.which);
	 if(keycode == '13'){
	  $('#741258963').click(); 
	 }	 
});
