/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 ASCII Printable Characters

 Char	Number	Description
 space  32	space
 !	33	exclamation mark
 "	34	quotation mark
 #	35	number sign
 $	36	dollar sign
 %	37	percent sign
 &	38	ampersand
 '	39	apostrophe
 (	40	left parenthesis
 )	41	right parenthesis
 *	42	asterisk
 +	43	plus sign
 ,	44	comma
 -	45	hyphen
 .	46	period
 /	47	slash
 0	48	digit 0
 1	49	digit 1
 2	50	digit 2
 3	51	digit 3
 4	52	digit 4
 5	53	digit 5
 6	54	digit 6
 7	55	digit 7
 8	56	digit 8
 9	57	digit 9
 :	58	colon
 ;      59	semicolon
 <	60	less-than
 =	61	equals-to
 >	62	greater-than
 ?	63	question mark
 @	64	at sign
 A	65	uppercase A
 B	66	uppercase B
 C	67	uppercase C
 D	68	uppercase D
 E	69	uppercase E
 F	70	uppercase F
 G	71	uppercase G
 H	72	uppercase H
 I	73	uppercase I
 J	74	uppercase J
 K	75	uppercase K
 L	76	uppercase L
 M	77	uppercase M
 N	78	uppercase N
 O	79	uppercase O
 P	80	uppercase P
 Q	81	uppercase Q
 R	82	uppercase R
 S	83	uppercase S
 T	84	uppercase T
 U	85	uppercase U
 V	86	uppercase V
 W	87	uppercase W
 X	88	uppercase X
 Y	89	uppercase Y
 Z	90	uppercase Z
 [	91	left square bracket
 \	92	backslash
 ]	93	right square bracket
 ^	94	caret
 _	95	underscore
 `	96	grave accent
 a	97	lowercase a
 b	98	lowercase b
 c	99	lowercase c
 d	100	lowercase d
 e	101	lowercase e
 f	102	lowercase f
 g	103	lowercase g
 h	104	lowercase h
 i	105	lowercase i
 j	106	lowercase j
 k	107	lowercase k
 l	108	lowercase l
 m	109	lowercase m
 n	110	lowercase n
 o	111	lowercase o
 p	112	lowercase p
 q	113	lowercase q
 r	114	lowercase r
 s	115	lowercase s
 t	116	lowercase t
 u	117	lowercase u
 v	118	lowercase v
 w	119	lowercase w
 x	120	lowercase x
 y	121	lowercase y
 z	122	lowercase z
 {	123	left curly brace
 |	124	vertical bar
 }	125	right curly brace
 ~	126	tilde

 DEL	127	delete (rubout)
 *  
 */

function checkAmount(key) {
	// getting key code of pressed key

	var keycode = (key.which) ? key.which : key.keyCode;
	// alert("key.which" + key.which);
	// alert("key.keyCode" + key.keyCode);
	// alert("keycode" + keycode);
	var amount = $(".amount").val();
	var strAmt = amount + "";
	var arryAmt = strAmt.split(".");
	// comparing pressed keycodes
	if (!(keycode == 8 || keycode == 9 || keycode == 46 || keycode == 17 ) && (keycode < 48 || keycode > 57)) {
		return false;
	} else {
		// check length after 2 digit
		if (arryAmt.length == 2) {
			var aftrDot = arryAmt[arryAmt.length - 1];
			var strAftrDot = aftrDot + "";
			if (keycode == 46) {
				return false;
			}
			if (keycode == 8 || keycode == 9 || keycode == 46 || keycode == 17 ) {
				return true;
			}
			if (strAftrDot.length >= 2) {
				return false;
			}
		} else if (arryAmt.length > 2) {
			return false;
		}
	}
}

/*
 * This method is use to chek is number {0-9} @param {type} key @returns
 * {Boolean}
 */
function isNumber(key) {
	var keycode = (key.which) ? key.which : key.keyCode;
	if (!(keycode == 8 || keycode == 9 || keycode == 17 )
			&& (keycode < 48 || keycode > 57)) {
		return false;
	}
}

/*
 * function for mobile no validation
 */
function mobileNumber(key) {
	var keycode = (key.which) ? key.which : key.keyCode;
	if (!(keycode == 8 || keycode == 43 || keycode == 32 || keycode == 9
			|| keycode == 17 || keycode == 37 )
			&& (keycode < 48 || keycode > 57)) {
		return false;
	} else {
	}
 
}

/*
 * function for Phone no validation
 */

function phoneNumber(key) {

	var keycode = (key.which) ? key.which : key.keyCode;
	// comparing pressed keycodes
	// || keycode==116
	if (!(keycode == 8 || keycode == 43 || keycode == 45 || keycode == 9 || keycode == 32
			|| keycode == 17 || keycode == 37 )
			&& (keycode < 48 || keycode > 57)) {
		return false;
	} else {
	}

}

/*
 * function for charecter Code no validation Like ABC123, ABC_123,ABC-123
 */
function isAlphaNumericAndSomeSpacialChar(key) {

	var keycode = (key.which) ? key.which : key.keyCode;
	if ((keycode >= 65 && keycode <= 90) 
			|| (keycode >= 97 && keycode <= 122)
			|| (keycode >= 48 && keycode <= 57) || (keycode == 45)
			|| (keycode == 95) || (keycode == 8)) {
		return true;
	} else {
		return false;
	}
}

/*
 * function for charecter Code no validation Like ABC 123, ABC_123,ABC-123,ABC
 * def GHI-1
 */
function isAlphaNumericAndSomeSpacialCharWithSpace(key) {
   // alert(key.which);
   //alert(String.fromCharCode(key.keyCode));
	var keycode = (key.which) ? key.which : key.keyCode;
	if ((keycode >= 65 && keycode <= 90) 
			|| (keycode >= 97 && keycode <= 122)
			|| (keycode >= 48 && keycode <= 57) || (keycode == 45)
			|| (keycode == 95) || (keycode == 8) || (keycode == 9)
			|| keycode == 32) {
		return true;
	} else {
		return false;
	}
}

/*
 * function for charecter Code no validation Like ABC123,
 * ABC_123,ABC-123,Abc-123_abc.ads
 */
function isAlphaNumericWithDotAndSomeSpacialChar(key) {

	var keycode = (key.which) ? key.which : key.keyCode;
	if ((keycode >= 65 && keycode <= 90) || (keycode == 46)
			|| (keycode >= 97 && keycode <= 122)
			|| (keycode >= 48 && keycode <= 57) || (keycode == 45)
			|| (keycode == 95) || (keycode == 8) || (keycode == 9)) {
		return true;
	} else {
		return false;
	}
}
/*
 * function for charecter Code no validation Like ABC123, ABC,ABC-xyz,Abc_abc
 */
function isAlphaAndSomeSpacialChar(key) {

	var keycode = (key.which) ? key.which : key.keyCode;
	if ((keycode >= 65 && keycode <= 90) || (keycode == 46)
			|| (keycode >= 97 && keycode <= 122) || (keycode == 45)
			|| (keycode == 95) || (keycode == 8) || (keycode === 9)
			|| (keycode === 32)) {
		return true;
	} else {
		return false;
	}
}
function isAlphaAndSomeSpacialCharDot(key) {

	var keycode = (key.which) ? key.which : key.keyCode;
	if ((keycode >= 65 && keycode <= 90) || (keycode >= 97 && keycode <= 122)
			|| (keycode === 45) || (keycode === 46) || (keycode === 95)
			|| (keycode === 8) || (keycode === 9) || (keycode === 32)) {
		return true;
	} else {
		return false;
	}
}
/*
 * function for alpha numeric abc123, ab12
 */
function isAlphaNumeric(key) {

	var keycode = (key.which) ? key.which : key.keyCode;

	if ((keycode >= 65 && keycode <= 90) || (keycode >= 97 && keycode <= 122)
			|| (keycode >= 48 && keycode <= 57) || (keycode == 8)
			|| (keycode == 9) ) {
		return true;
	} else {
		return false;
	}
}
/*
 * function for number with space & plus specially for fax no 76786, 78 78979,
 * +9845
 */
function isNumberSpace(key) {
	var keycode = (key.which) ? key.which : key.keyCode;

	if (!(keycode == 8 || keycode == 9 || keycode == 17 || keycode == 37
			||  keycode == 43 || keycode == 32)
			&& (keycode < 48 || keycode > 57)) {
		return false;
	}
}
/*
 * function for weightage mainly that weight should be in numbers only no dot and charcter
*/
function isNumberAndNotDot(key) {
    var keycode = (key.which) ? key.which : key.keyCode;
    if (!(keycode == 8 || keycode == 9 || keycode == 17 ) && (keycode < 48 || keycode > 57))
    {
        return false;
    }
}
