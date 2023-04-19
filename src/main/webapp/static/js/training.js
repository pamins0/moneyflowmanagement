$("#docRadio,#urlRadio").change(function(event) {	
	var type=this.value;
	if(type=='document'){
		$("#urlDiv").hide("slow");		 
		$("#docDiv").show("slow");		
	}else if(type=='video'){
		$("#docDiv").hide("slow");				
		$("#urlDiv").show("slow");			
	}else{		
	}
});