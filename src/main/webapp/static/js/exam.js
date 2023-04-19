
var timerInterval;

function submitAndGet(testid, ansid) {

	$.ajax({
		url : "StartExam",
		type : 'POST',
		dataType : 'json',
		data : {
			testId : testid,
			ansId : ansid,
		}
	}).done(function(examPOJO) {
		// examPOJO is response
		//$("#testDiv").hide("slow");
		//$("#testActionDiv").hide("slow");
		
		if (examPOJO.message!='end') { 
			
			$('#qnoShow').text(examPOJO.qNo);
			$('#question').text(examPOJO.question);
			$('#opt1').text(examPOJO.opt1);
			$('#opt2').text(examPOJO.opt2);
			$('#opt3').text(examPOJO.opt3);
			$('#opt4').text(examPOJO.opt4);
			$('#totalQuestions_success').text(' : '+examPOJO.totalQuestions);
			$('#totalAttempts_success').text(' : '+examPOJO.totalAttempts);
			$('#totalQuestions_timeUp').text(' : '+examPOJO.totalQuestions);
			$('#totalAttempts_timeUp').text(' : '+examPOJO.totalAttempts);
			
			//$('input[name="answers"]').prop('checked', false); 
			//$('input[name="answers"]').removeAttr('checked');
			 
			$("#testDiv").show("slow");
			$("#testActionDiv").show("slow"); 
			
		} else{
			
		// when no more questions in test (attempted all)			
			$("#testDiv").hide("slow");
			$("#testActionDiv").hide("slow");
			$("#successDiv").show("slow");
			clearInterval(timerInterval);		 			
		}		
	});
}

$('#acceptTerm').click(function(event) {
	// this function executes when user accepts the terms
	// and this function starts test and loads question
	var chkVal = $('#acceptTermCheck:checked').val() ? true : false;
	if (chkVal) {

		$("#termDiv").hide("slow", function() {
		    // retriving values
			var test_id = $('#testId').val();
			var ans_id = $("input[name='answers']:checked").val();
			submitAndGet(test_id, ans_id);
			// starting timer here
			timerInterval= setInterval("MyTimer()", 1000);
		});

	} else {
		console.log("terms not accepted");
	}

});

$('#next').click(function(event) {
	// this function executes when user accepts the terms
	// and this function starts test and loads question
	
	var ans_id = $("input[name='answers']:checked").val();	
	if (ans_id != null && ans_id != '') {
		var test_id = $('#testId').val();
		submitAndGet(test_id, ans_id);

	} else {
		console.log("option not selected");
	}

});

// script for timer
function MyTimer() {
	var valueTimer = $('#hdnTimer').val();
	if (valueTimer > 0) {
		valueTimer = valueTimer - 1;
		hours = (valueTimer / 3600).toString().split('.')[0];
		mins = ((valueTimer % 3600) / 60).toString().split('.')[0];
		secs = ((valueTimer % 3600) % 60).toString();

		if (hours.length == 1)
			hours = '0' + hours;
		if (mins.length == 1)
			mins = '0' + mins;
		if (secs.length == 1)
			secs = '0' + secs;
		$('#idTimerLCD').text(' ' + mins + ':' + secs);
		$('#hdnTimer').val(valueTimer);
		document.title = $('#idTimerLCD').text();
		
	} else {		
		
		$("#testDiv").hide("slow");
		$("#testActionDiv").hide("slow");
		$("#timeUpDiv").show("slow");
		clearInterval(timerInterval);		 
	}
}
