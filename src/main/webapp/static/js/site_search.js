jQuery("#searchBtn").click(function(e) {

	var title = jQuery("#searchField").val();
	if (title != "") {
		jQuery("#listingDiv").html("");
		jQuery.ajax({
			url : "SiteSearch",
			type : 'POST',
			dataType : 'json',
			data : {
				title : title,
			}
		}).done(function(data) {
			if (data != "") {
				data.forEach(function(listing) {
					a = '<div class="cruise-list listing-style3 cruise"> <article class="box"> <figure class="col-md-4 col-sm-4"> <br> <a href="PropertyDetail?id='
						+ listing.pid
						+ '"><img width="270" height="160" title="'
						+ listing.title
						+ '" src="ImageDisplay?id='
						+ listing.pid
						+ '"></a></figure> <div class="details col-md-8 col-sm-8"><div class="clearfix"><h4 class="box-title pull-left"><a href="PropertyDetail?id='
						+ listing.pid
						+ '" class="button btn-small pull-right">'
						+ listing.title
						+ '</a> </h4> <span class="price pull-right"><small>Price: </small>'
						+ listing.price
						+ '</span> </div> <div class="character clearfix"> <div class="col-xs-4 cruise-logo"> <span class="skin-color">Posted By:</span><br>'
						+ listing.listedByName
						+ '<br><span class="skin-color">Size: </span>'
						+ listing.size
						+ '<br> <span class="skin-color">Date: </span>'
						+ listing.date
						+ '</div><div class="col-xs-4 date"> <i class="soap-icon-clock yellow-color"></i><div><span class="skin-color">Tenure: </span>'
						+ listing.tenure
						+ '<br><span class="skin-color">Area: </span>'
						+ listing.lArea
						+ '<br><span class="skin-color">Build Up: </span>'
						+ listing.bUp
						+ '</div></div><div class="col-xs-6 departure"><i class="soap-icon-departure yellow-color"></i><div><span class="skin-color">Address: </span><br>'
						+ listing.address2
						+ '</div></div></div><div class="clearfix"><div class="review pull-left"><div class="five-stars-container"><span class="five-stars" style="width: 60%;"></span></div><span>'
						+ listing.views
						+ ' views</span></div><a href="PropertyDetail?id='
						+ listing.pid
						+ '" target="_blank" class="button btn-small pull-right">View Full Detail</a></div></div></article></div>';
					jQuery("#listingDiv").append(a);
				});
			} else {
				var a = '';
				jQuery("#listingDiv").append(a);
			}
		});
	};
});

jQuery("#communicate").click(function(e) {

	var name = jQuery("#name").val();
	var email = jQuery("#email").val();
	var no = jQuery("#no").val();
	var message = jQuery("#message").val();
	var agentId = jQuery("#agentId").val();
	var pId = jQuery("#pId").val();
	if (email != "" && message != "" && pId != "") {
		jQuery.ajax({
			url : "CommunicateAgent",
			type : 'POST',
			data : {
				Name : name,
				email : email,
				no : no,
				message : message,
				pid : pId,
				agentId : agentId,
			}
		}).done(function(data) {			
			if (data == "done") {				
				jQuery("#name").val('');
				jQuery("#email").val('');
				jQuery("#no").val('');
				jQuery("#message").val('');
				
				jQuery("#compose-model").modal('hide');
			}else{
				console.log(data);
			}			
		}).fail(function(e) {
			console.log(e);
		});
		 
	}

});