function reg_validation() {

	var aa = document.getElementById("fname_id");
	var bb = document.getElementById("lname_id");
	var cc = document.getElementById("email_id");
	var dd = document.getElementById("pass_id");
	var ee = document.getElementById("contact_id");
	var ff = document.getElementById("city_id");
	var gg = document.getElementById("state_id");
	var hh = document.getElementById("country_id");
	var ii = document.getElementsByName("gen");

	var fname_value = aa.value;
	var lname_value = bb.value;
	var email_value = cc.value;
	var pass_value = dd.value;
	var contact_value = ee.value;
	var city_value = ff.value;
	var state_value = gg.value;
	var country_value = hh.value;

	// #900000 red code
	// #33FF25 green code
	// #e3e2e2 grey code
	if (fname_value == "") {
		aa.style.borderColor = "#900000";
		return false;
	} else if (fname_value.length > 30 || fname_value.length < 2) {
		aa.style.borderColor = "#900000";
		return false;
	} else if (lname_value == "") {
		bb.style.borderColor = "#900000";
		return false;
	} else if (lname_value.length > 30 || lname_value.length < 2) {
		bb.style.borderColor = "#900000";
		return false;
	} else if (email_value == "") {
		cc.style.borderColor = "#900000";
		return false;
	} else if (email_value.length > 30 || email_value.length < 0) {
		cc.style.borderColor = "#900000";
		return false;
	} else if (pass_value == "") {
		dd.style.borderColor = "#900000";
		return false;
	} else if (pass_value.length > 20 || pass_value.length < 6) {
		dd.style.borderColor = "#900000";
		return false;
	} else if (contact_value == "") {
		ee.style.borderColor = "#900000";
		return false;
	} else if (contact_value.length > 15 || contact_value.length < 1) {
		ee.style.borderColor = "#900000";
		return false;
	} else if (country_value == "") {
		hh.style.borderColor = "#900000";
		return false;
	} else if (country_value.length > 30 || country_value.length < 2) {
		hh.style.borderColor = "#900000";
		return false;
	} else if (state_value == "") {
		gg.style.borderColor = "#900000";
		return false;
	} else if (state_value.length > 30 || state_value.length < 2) {
		gg.style.borderColor = "#900000";
		return false;
	} else if (city_value == "") {
		ff.style.borderColor = "#900000";
		return false;
	} else if (city_value.length > 30 || city_value.length < 2) {
		ii.style.borderColor = "#900000";
		return false;
	} else if (radioNotChecked(ii)) {
		document.getElementById("error").style.display = false ? "none"
				: "inline";
		return false;
	}

	return true;
}

function radioNotChecked(ff) {
	var c = -1;
	for (var i = 0; i < ff.length; i++) {
		if (ff[i].checked) {
			c = i;
		}
	}
	if (c == -1) {

		return true;
	} else {

		return false;
	}
	return false;
}

function des(ths) {
	ths.style.borderColor = "#e3e2e2";
}

function chk(ths) {
	var val = ths.value;

	if (val == "") {
		ths.style.borderColor = "#900000";

	} else if (val.length > 30 || val.length < 1) {
		ths.style.borderColor = "#900000";

	} else {
		ths.style.borderColor = "#33FF25";
	}

}

function colorReset(ths) {

	ths.style.borderColor = "#33FF25";

}

function chkPass(ths) {
	var val = ths.value;

	if (val == "") {
		ths.style.borderColor = "#900000";

	} else if (val.length > 20 || val.length < 6) {
		ths.style.borderColor = "#900000";

	} else {
		ths.style.borderColor = "#33FF25";
	}
}

function contactClick(ths) { 
	var valueIn = ths.value;
	if (valueIn.length != null & valueIn.length > 5) {
		ths.style.borderColor = "#33FF25";
	} else if (valueIn.length != null & valueIn.length < 5) {
		ths.style.borderColor = "#e3e2e2";
	}
}

/*
 * function isInteger(ss) { var s = ss.value; for (var i = 0; i < s.length; i++) { //
 * Check that current character is number. var c = s.charAt(i); if (!isDigit(c)) {
 * 
 * }// end of if }// end of for }
 */

var specialKeys = new Array();
specialKeys.push(8); // Backspace
function IsNumeric(e, ths) {
	var valueIn = ths.value;
	var keyCode = e.which ? e.which : e.keyCode;
	var ret = ((keyCode >= 48 && keyCode <= 57) || specialKeys.indexOf(keyCode) != -1);
	if (ret == true) {
		 
		if (valueIn.length != null & valueIn.length > 14) {
			return false;
		} else if (valueIn.length != null & valueIn.length > 5) {
			ths.style.borderColor = "#33FF25";
			return true;
		} else     {
			ths.style.borderColor = "#e3e2e2";
			return true;
		}
	 
	}
	if (ret == false) {
		return false;
	}
}

function test(mail) {

	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var mailStatus = xmlhttp.responseText;

			if (mailStatus == 'yes') {
				document.getElementById('email_id').style.borderColor = "#900000";

			} else if (mailStatus == 'invalid') {
				document.getElementById('email_id').style.borderColor = "#900000";

			} else if (mailStatus == 'no') {
				// available user can take this email
				document.getElementById('email_id').style.borderColor = "#33FF25";
			}
		}
	};
	xmlhttp.open("GET", "IsUserRegisterd?id=" + mail, true);
	xmlhttp.send();

}
