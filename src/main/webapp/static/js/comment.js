function addComment(ths) {
	var id = ths.id;
	var cmtElement=$("#cmt");
	var comment = cmtElement.val();

	if (comment != "") {
		$.ajax({
			url : "Comments",
			type : 'POST',
			data : {
				pid : id,
				comment : comment,
			}
		}).done(function(data) {

			window.location.reload(true);

		}).fail(function(e) {
			console.log(e);
			window.location.reload(true);
		});
	} else {
		 
		jQuery.fn.flash = function( color, duration )
		{ 
		    var current = cmtElement.css( 'color' );

		    cmtElement.animate( { color: 'rgb(' + color + ')' }, duration / 2 );
		    cmtElement.animate( { color: current }, duration / 2 );

		};
		
	//	$("#cmt").style.borderColor = "#900000";
	}

}