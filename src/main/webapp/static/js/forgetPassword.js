$(document).ready(function() {
	$('#forgetButton').click(function(event) {		 
		var uid = $('#emailId').val();	 
		
		if(uid!=""){
		$('#inputEmail').html("Wait...");
		 $('#forgetButton').attr("disabled", true);
		
		$.ajax({
			url : "ForgotPassword",		
			type : 'POST',
			data : {
				uId : uid,				 
			}
		}).done(function(response) {
			console.log(response);
			if (response == 'yes') {
				  
				var x='<div id="inputEmail" class="alert alert-success alert-dismissable"><i class="fa fa-check"></i>We’ve sent you a passwrd reset link to your E-mail <br></div>';
			 $('#123').html(x);
			 $('#button').hide();
			 
			}else{				
			 
			} 
		}).fail(function() {
				console.log();
		});
		}
	});

});


$('#submit').click(function(){                                         
    $(".error").hide();
    var hasError = false;
    var passwordVal = $("#password").val();
    var checkVal = $("#password-check").val();
    var userid=$('#uid').val();
   
    if (passwordVal == '') {
        $("#password").after('<span class="error">Please enter a password.</span>');
        hasError = true;
    } else if (checkVal == '') {
        $("#password-check").after('<span class="error">Please re-enter your password.</span>');
        hasError = true;
    } else if (passwordVal != checkVal ) {   
        $("#password-check").after('<span class="error">Passwords do not match.</span>');
        hasError = true;
    }
    
    if(hasError) { 
    	return false; 
    	}
    
	else {		
		
		//$('#submit').attr("disabled", true);
		 
		$.ajax({
			url : "ResetPass",		
			type : 'POST',
			data : {
				uId :userid,
				password:checkVal,				 
			}
		}).done(function(response) {
			console.log(response);
			if(response=='update')
			{				
				//var x='<div id="inputEmail" class="alert alert-success alert-dismissable"><i class="fa fa-check"></i>your password is changed..!<br></div>';
				$('#login-box').hide();
				$('#successPass').show();
				
			}else
				{				
				var x='<div id="inputEmail" class="alert alert-success alert-dismissable"><i class="fa fa-check"></i>your password is not changed..!<br></div>';
				$('#pass').append(x);
				}
			
		});
		
		}
});	
