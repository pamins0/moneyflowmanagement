function printpage() {
	var printButton = document.getElementById("printpagebutton");
	var chngPhotoDiv = document.getElementById("chngPhotoDiv");
	var chngPassDiv = document.getElementById("chngPassDiv");
	var saveBtnDiv = document.getElementById("saveBtnDiv");

	if (chngPhotoDiv != null && chngPassDiv != chngPassDiv	&& saveBtnDiv != null) {
		printButton.style.visibility = 'hidden';
		chngPhotoDiv.style.visibility = 'hidden';
		chngPassDiv.style.visibility = 'hidden';
		saveBtnDiv.style.visibility = 'hidden';
		window.print();
		printButton.style.visibility = 'visible';
		chngPhotoDiv.style.visibility = 'visible';
		chngPassDiv.style.visibility = 'visible';
		saveBtnDiv.style.visibility = 'visible';
	} else {
		printButton.style.visibility = 'hidden';
		window.print();
		printButton.style.visibility = 'visible';
	}
}

function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$('#blah').attr('src', e.target.result).width(260).height(160);
		};
		reader.readAsDataURL(input.files[0]);
	}
}

function check() {
	var old = $('#old').val();
	var npass = $('#new').val();
	var repass = $('#re-enter').val();
	$.ajax({
		url : 'CheckPassword',
		type : 'POST',
		data : {
			oldpass : old,
			newpass : npass,
			repass : repass,
		},
	}).done(function(data) {
		if (data == 'true') {
			$('#modalChangePass').modal('hide');
			$('#passwordChangeSuccess').modal('show');
			$('#old').val('');
			$('#new').val('');
			$('#re-enter').val('');
			$("#passCngDiv").trigger('reset');
		} else if (data == 'false') {
			window.location.href = "Login";
		} else {
			$('#errMsg').text(data);
		}
	});
}
 
	
$('#GenerateMyURL').click(function(event) {
	$.ajax({
		url : 'GenerateMyURL',
		type : 'POST',		
	}).done(function(data) {
		if (data == 'nouser') {
			window.location.href = "Login";
		} else {			
			$('#myUrl').attr("href", "http://www.property4u.com.my/agents/"+data);
			$('#myUrl').html('<small>www.property4u.com.my/agents/'+data+'</small>');
			$( ".hello" ).remove();
		}
	});
});

