var propertyType = new Array("RESIDENTIAL", "COMMERCIAL", "INDUSTRIAL",	"AGRICULTURAL");

var catArray = new Array();
catArray[0] = "";
catArray[1] = "Flat|Apartment|Apartment Duplex|Condominium|Serviced Apartment|Serviced Residence|Penthouse|Duplex|Triplex|1-str Terrace/Link House|1.5-str Terrace/Link House|2-str Terrace/Link House|2.5-str Terrace/Link House|3-str Terrace/Link House|3.5-str Terrace/Link House|4-str Terrace/Link House|4.5-str Terrace/Link House|Townhouse|Semi-Detached house|Townhouse Condo|Cluster Homes|Residential Land|1-str Bungalow House|1.5-str Bungalow House|2-str Bungalow House|2.5-str Bungalow House|3-str Bungalow House";
catArray[2] = "Office|Shop-Office|Shop|SOHO|SOFO|SOVO|Retail Space|Retail-Office|Hotel/Resort|Business Centre|Commercial Bungalow|Designer Suites|Commercial Semi-D|Commercial Land";
catArray[3] = "Factory|Semi-D Factory|Industrial Land|Warehouse|Detached Factory|Link Factory|Light Industrial";
catArray[4] = "Agricultural Land";

function printPropertyType(propId) {
	// given the id of the <select> tag as function argument, it inserts <option> tags
	var option_element = document.getElementById(propId);
	var option1=option_element.options[0];
	option_element.length = 0;
	//option_element.options[0] = new Option('-Property Type-', '');
	option_element.options[0]=option1;
	option_element.selectedIndex = 0;
	for (var i = 0; i < propertyType.length; i++) {
		option_element.options[option_element.length] = new Option(propertyType[i],
				propertyType[i]);
	}
}

function print_category(categoryId, state_index) {
	var option_element = document.getElementById(categoryId);
	var option1=option_element.options[0];
	option_element.length = 0;
	//option_element.options[0] = new Option('-Category-', '');
	option_element.options[0]=option1;
	option_element.selectedIndex = 0;
	var catArr = catArray[state_index].split("|");
	for (var i = 0; i < catArr.length; i++) {
		option_element.options[option_element.length] = new Option(catArr[i],
				catArr[i]);
	}
}


	function getContacts(Uid) {	
		$.ajax({
			url : "GetPOwnerContact",
			type : 'POST',
			dataType : 'json',
			data : {
				uId : Uid,				 
			}
		}).done(function(agent) {	
			$('#idcont1').val(agent.contact);
			$('#idcont2').val(agent.contact2);			
			$("#ownerDetail").html("");
			var htmlForPropertyDetailDiv='<div class="box-body"><div class="user-panel"><div class="pull-left image"><img src="DisplayProfile?id='+agent.uid+'"></div><div class="pull-left info"><p>'+agent.name+'</p><p><small>Contact: '+agent.contact+'</small></p><p><small>Contact: '+agent.contact2+'</small></p><p><small>Email: '+agent.email+'</small></p></div></div></div>';		
			$("#ownerDetail").append(htmlForPropertyDetailDiv);			
		});
	}
	
	$("#o_name").click(function() {	  
		$('#ownerListModel').modal('show');		
	}); 
	
	$("#selectOwnerBtn").click(function() {		
		var uId=$('input[name=uId]:checked').val();
		if(uId){			
			$("#o_name").val(uId);			
			getContacts(uId);			
			$('#ownerListModel').modal('hide');	
		} 			
	}); 
	
	$("#addOwnerBtn").click(function() { 		
		$('#ownerListModel').modal('hide');	
		$('#addOwnerModel').modal('show');
	}); 
	
	$("#addByAgent").click(function() {	  
		$('#listingByModel').modal('show');		
	});
	 
	$("#selectListingBy").click(function() {	
		var uId=$('input[name=listingUId]:checked').val();		
		if(uId){			
			$("#addByAgent").val(uId); 
			$('#listingByModel').modal('hide');	
		} 			
	});  
	 
	// formating price 
	$('#price').priceFormat({
		 prefix: '',
		 thousandsSeparator: '',
	});
	
	/*// calculating sqft
	$("#width,#length").keyup(function() {
		$('#area').val($('#width').val() * $('#length').val() + " sqft");
	});	*/
		
	// search buttons functionality
	$(document).ready(function() {
		 $('#listingBySearchField').bind('keyup change', function(ev) {
			var searchTerm = $(this).val();				
			$('body').removeHighlight();				
			if (searchTerm) {					
				$('body').highlight(searchTerm);
			}
		});
		 
		$('#ownerListModelSearchField').bind('keyup change', function(ev) {				 
			var searchTerm = $(this).val();					
			$('body').removeHighlight();				 
			if (searchTerm) {					 
				$('body').highlight(searchTerm);
			}
		});
	});
	
	// this for populating country and property type
	print_country("country");	
	printPropertyType("propertyType");		
	
	// code for updateProperty
	
	function deleteImage(ths) {
		var imgId = ths.id;
		var imgDiv= imgId+"_div";	
		$("#"+imgId).html(' wait..');
		$.ajax({
			url : "UpdateDeleteImage",
			//dataType : 'json',
			data : {
				id : imgId,
			},
			type : "post",
		}).done(function(resp) {
			 if(resp=='ok'){
				 $("#"+imgDiv).remove();				 
			 }else if(resp=='noUser'){
				 window.location.href = "Login";
			 }else{
				 $("#"+imgId).html('');				 
			 }			 		
		}).fail(function(e) {
			$("#"+imgId).html('');
		});
	}
	
	function removePreview(ths) {
		var imgId = ths.id;
		var imgDiv= imgId+"_div";			
		$("#"+imgDiv).remove();
		var imgBrowse = $("#"+imgDiv);
		var files = imgBrowse.files;
		var file = files[imgId];
		files.push(imgId,1);
	}
	
	 function readURL(input) { 
		var files = input.files;
		var filesArr = Array.prototype.slice.call(files);		
		$('#imagesShowcase').html('');
		var i=0;		
		filesArr.forEach(function(f) {				
			var reader = new FileReader(f);
			reader.onload = function (e) {				
				var htm='<div class="col-xs-6 col-md-3" id="'+i+'_div"><a class="thumbnail" style="height: 100px; width: 100px;"><img style="max-height: 70px; max-width: 90px;" src="'+e.target.result+'" alt="propImg"><label><i onclick="removePreview(this);" id="'+i+'" class="fa fa-trash-o"></i></label></a></div>';
				$('#imagesShowcase').append(htm);
				i++;
			};
			reader.readAsDataURL(f); 			
		});	 
	} 
	 