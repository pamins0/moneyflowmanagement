Number.prototype.format = function(n, x) {
	var re = '(\\d)(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\.' : '$') + ')';
	return this.toFixed(Math.max(0, ~~n)).replace(new RegExp(re, 'g'), '$1,');
};

function validateQty(event) {
	var key = window.event ? event.keyCode : event.which;
	if (event.keyCode == 8 || event.keyCode == 9) {
		return true;
	} else if (key < 48 || key > 57) {
		return false;
	} else
		return true;
}

function isNumberAndDot(event, ths) {
	var key = window.event ? event.keyCode : event.which;
	if (key === 46 && ths.value.split('.').length === 2) {
		return false;
	}
	if (key == 8 || key == 46) {
		return true;
	} else if (key < 48 || key > 57) {
		return false;
	} else
		return true;
}

function numericOnlyAndOneDot(event) {
	alert(event.value);
	var keyCodeEntered = (event.which) ? event.which
			: (window.event.keyCode) ? window.event.keyCode : -1;
	alert(keyCodeEntered);
	if ((keyCodeEntered >= 48) && (keyCodeEntered <= 57)) {
		return true;
	}
	// '.' decimal point...
	else if (keyCodeEntered == 46) {
		// Allow only 1 decimal point ('.')...
		alert(keyCodeEntered);
		if ((this.value) && (this.value.indexOf('.') >= 0)) {
			alert("inside " + keyCodeEntered);
			return false;
		} else
			return true;
	}
	return false;
}

// Function to allow only numbers to textbox
function isDigit(key) {
	// getting key code of pressed key
	var keycode = (key.which) ? key.which : key.keyCode;
	var phn = $(".mobile").val();
	// comparing pressed keycodes
	// || keycode==116
	if (!(keycode == 8 || keycode == 46 || keycode == 9 || keycode == 17)
			&& (keycode < 48 || keycode > 57)) {
		return false;
	} else {
		// Condition to check textbox contains ten numbers or not
		if (phn.value.length < 10 || phn.value.length > 11) {
			return true;
		} else {
			return false;
		}
	}
}
function checkChar(key) {
	var keyCode = (key.which) ? key.which : key.keyCode;
	if ((keyCode > 64 && keyCode < 91) || (keyCode > 96 && keyCode < 123)
			|| keyCode == 8 || keyCode == 9 || keyCode == 32)
		return true;
	else {
		return false;
	}

}

function checkSpecialChar(Key) {
	var keyCode = (key.which) ? key.which : key.keyCode;
	if ((keyCode > 64 && keyCode < 91) || (keyCode > 96 && keyCode < 123)
			|| keyCode == 8 || keyCode == 9 || keyCode == 32)
		return true;
	else {
		return false;
	}
}
