function getname(ths) {
	var cat = ths.id;
	var trHtml = '<tr><th>Image</th><th>Name</th><th>Contact</th><th>Email</th><th>View/Edit</th></tr>';
	$.ajax({
		url : "AddressBook",
		dataType : 'json',
		data : {
			id : cat,
		},
		type : "post",
	}).done(function(POJOs) {
		$("#usersDiv").html("");
		$("#usersDiv").append(trHtml);
		POJOs.forEach(function(pojo) {
			var htmlContent = '<tr><td><div class="chat" id="chat-box"><div class="item"> <img src="DisplayProfile?id='+ pojo.uid + '" alt="user image" class="online"/></div></div></td><td>' + pojo.name + '</td><td>' + pojo.contact1+ '</td><td><a href="mailto:'+ pojo.email + '?Subject=Inquiry" target="_top">'+ pojo.email + '</a></td><td> <div class="box-footer"><button class="btn btn-info" data-toggle="modal"  data-target="#info" onclick=\'(getinfo("'+ pojo.uid+ ','+ cat+ '"))\'><i class="fa fa-info-circle"></i></button>&nbsp;&nbsp;&nbsp;<button class="btn btn-warning" data-toggle="modal"  data-target="#compose-modal" onclick=\'(getDetail("'+ pojo.uid+ ','+ cat+ '"))\'><i class="fa fa-edit"></i></button></div></td></tr>';
			$("#usersDiv").append(htmlContent);
		});
	}).fail(function(e) {
	});
}

function displayData(cat) {
	var trHtml = '<tr><th>Image</th><th>Name</th><th>Contact</th><th>Email</th><th>View/Edit</th></tr>';
	$.ajax({
		url : "AddressBook",
		dataType : 'json',
		data : {
			id : cat,
		},
		type : "post",
	}).done(function(POJOs) {		
		$("#usersDiv").html("");
		$("#usersDiv").append(trHtml);
		POJOs.forEach(function(pojo) {
			var htmlContent = '<tr><td><div class="chat" id="chat-box"><div class="item"><img src="DisplayProfile?id=' + pojo.uid + '" alt="user image" class="online"/></div></div></td><td>' + pojo.name	+ '</td><td>' + pojo.contact1 + '</td><td><a href="mailto:'+ pojo.email + '?Subject=Inquiry" target="_top">'+ pojo.email + '</a></td><td> <div class="box-footer"><button class="btn btn-info" data-toggle="modal"  data-target="#info" onclick=\'(getinfo("' + pojo.uid + ',' + cat + '"))\'><i class="fa fa-info-circle"></i></button>&nbsp;&nbsp;&nbsp;<button class="btn btn-warning" data-toggle="modal"  data-target="#compose-modal" onclick=\'(getDetail("' + pojo.uid + ',' + cat + '"))\'><i class="fa fa-edit"></i></button></div></td></tr>';
			$("#usersDiv").append(htmlContent);
		});
	}).fail(function(e) {
	});
}

function getDetail(uid, cat) {	
	$.ajax({
		url : 'GetInfo',
		dataType : 'json',
		data : {
			empid : uid,
			catType : cat,
		},
		type : 'POST',
	}).done(function(data) {
		$('#fnameid').val(data.name);
		$('#lnameid').val(data.lname);
		$('#emailid').val(data.email);
		$('#paddressid').val(data.address);
		$('#saddressid').val(data.address2);
		$('#countryid').val(data.country);		
		$('#stateid').val(data.state);
		$('#cityid').val(data.city);		
		$('#pcontactid').val(data.contact1);
		$('#scontactid').val(data.contact2);		
		$('#companynameid').val(data.past);
		$('#genderid').val(data.gender);
		$('#submitid').val(uid);	
	});
}

function getinfo(uid, cat) {
	var id = uid;
	console.log(id);
	$.ajax({
		url : 'GetInfo',
		dataType : 'json',
		data : {
			empid : id,
			catType : cat,
		},
		type : 'POST',
	}).done(function(data) {		
		$('#nameid1').val(data.name);
		$('#emailid1').val(data.email);
		$('#addressid1').val(data.address);
		$('#contactid12').val(data.contact1);
		$('#contactid11').val(data.contact2);
		$('#submitid1').val(data.uid);
	});
}
$(document).ready(function (e) {
	$("#updateForm").on('submit',(function(e) {
		e.preventDefault();		
		$.ajax({
			url: "UpdateAddressBook", // Url to which the request is send
			type: "POST",             // Type of request to be send, called as method
			data: new FormData(this), // Data sent to server, a set of key/value pairs (i.e. form fields and values)
			contentType: false,       // The content type used when sending data to the server.
			cache: false,             // To unable request pages to be cached
			processData:false,        // To send DOMDocument or non processed data file it is set to false
		}).done(function(data) {	  // A function to be called if request succeeds
			displayData(data);
			$('#compose-modal').modal('hide');
		}).fail(function() {
		});
	}));
});
