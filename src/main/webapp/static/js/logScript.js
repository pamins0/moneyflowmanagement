//code to remember login and password
$(function() {

	if (localStorage.chkbx && localStorage.chkbx != '') {
		$('#remember-me').attr('checked', 'checked');
		$('#userId').val(localStorage.usrname);
		$('#userPassword').val(localStorage.pass);
	} else {
		$('#remember_me').removeAttr('checked');
		$('#userId').val('');
		$('#userPassword').val('');
	}

	$('#remember-me').click(function() {
		if ($('#remember-me').is(':checked')) {
			// save username and password
			localStorage.usrname = $('#userId').val();
			localStorage.pass = $('#userPassword').val();
			localStorage.chkbx = $('#remember-me').val();
		} else {
			localStorage.usrname = '';
			localStorage.pass = '';
			localStorage.chkbx = '';
		}
	});
});