function popup(url) {
	var width = 700;
	var height = 400;
	var left = (screen.width - width) / 2;
	//var top = (screen.height - height) / 2;
	var top = 10;
	var params = 'width=' + width + ', height=600';
	params += ', top=' + top + ', left=' + left;
	params += ', directories=no';
	params += ', location=no';
	params += ', menubar=no';
	params += ', resizable=no';
	params += ', scrollbars=no';
	params += ', status=no';
	params += ', toolbar=no';
	newwin = window.open(url, 'windowname5', params);
	if (window.focus) {
		newwin.focus();
	}
	return false;
	
	
}

function newPopup(url) {
	popupWindow = window
			.open(
					url,
					'popUpWindow',
					'height=400,width=700,resizable=no,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes');
}