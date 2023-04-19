$(document).ready(
		function() {
			$("#submit").click(
					function() {						
						var testId = $("#idTestId").val();
						var que = $("#idQue").val();
						var ans1 = $("#idAns1").val();
						var ans2 = $("#idAns2").val();
						var ans3 = $("#idAns3").val();
						var ans4 = $("#idAns4").val();
						var cans = $("#idCans").val();												 

						if (testId == '' || que == '' || ans1 == '' || ans2 == '' || ans3 == ''	|| ans4 == '' || cans == '') {
							$('#success').fadeOut(200).hide();
							$('#error').fadeOut(200).show();
						} else {
							
							$("#submit").text('Wait..');
							$('#submit').prop('disabled', true);
							
						// AJAX Code To Submit Form.
							
							$.ajax({
								type : "POST",
								url : "AddQuestion",
								data : {
									testId : testId,
									que : que,
									ans1 : ans1,
									ans2 : ans2,
									ans3 : ans3,
									ans4 : ans4,
									cans : cans,
								},
								cache : false,
								success : function(result) {
									$('#success').fadeIn(20).show();
									$('#error').fadeOut(20).hide();									 
									$("#totalQuestions").val(result);
									$("#questionForm")[0].reset();
									$("#submit").text('Submit');
									$('#submit').prop('disabled', false);
								}
							});
						}
						return false;
					});
		});

function clearMessage() {
	$('#error').fadeOut(20).hide();
	$('#success').fadeOut(20).hide();
}


function queDetails() {
    var id = $('#queId').val(); 
     $.ajax({
		  url : 'EditQuestions',
		  type : 'GET',
		  dataType:'json',
			data : {
				queId : id,
			},
	 }).done(function(data) {
		 console.log(data);
		 if(data == 'nouser'){
			 window.location.href = "Login"; 
		 }else if(data == 'error'){
			 window.location.href = "500"; 
		 }else{			
		    $('#idQue').val(data.question);
		    $('#idAns1').val(data.opt1);
			$('#idAns2').val(data.opt2);
			$('#idAns3').val(data.opt3);
			$('#idAns4').val(data.opt4);
			$('#ansid').text('Current Correct Answer is option No:'+data.ansId);
			$('#queid').val(data.qId);
			$('#testid').val(data.testId);
			$('#ansNo').val(data.ansId);
			$("#dId").attr("href", 'DeleteQuestion?id='+data.qId);
		 }	 
		  
});  	
}