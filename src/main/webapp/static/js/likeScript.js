function likeBtn(ths, nid) {
	var uid = document.getElementById("id").value;
	if (uid != "") {
		var parameters = {
			nId : nid,
			uId : uid
		};
		$.getJSON("Like", parameters).done(function(json) {
			var msg = json.msg;
			if (msg != "nullvalue") {
				if (msg == "liked") {
					$(ths).removeClass("fa fa-thumbs-o-up");
					$(ths).addClass("fa fa-thumbs-o-down");
					$(ths).attr( "title", "UnLike" );
					ths.innerHTML = json.likes;
				} else if (msg == "unliked") {
					$(ths).removeClass("fa fa-thumbs-o-down");
					$(ths).addClass("fa fa-thumbs-o-up");
					$(ths).attr( "title", "Like" );
					ths.innerHTML = json.likes;
				}
			} else {
				alert("you must be loged in");
			}
		});
	}
	return false;
};