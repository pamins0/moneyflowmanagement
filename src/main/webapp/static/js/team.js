	// json servlet call					
		$('#jsonServletCall').click(function(event) {
			var allMembersList = new Object();
			$("#listUser").html("");
			$.ajax({
				url : "Json",
				type : 'POST',
				dataType : 'json',
				data : JSON.stringify(allMembersList),
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					data.forEach(function(entry) {									 
						var xx='<li><span class="handle"> <i class="fa fa-user"></i> </span>  <input type="checkbox" name="selectedMembers" value= "'+entry.uid+'" /> <span class="text"> '+entry.name+' '+entry.lname+'</span> <span class="text">'+entry.city+'</span> <small class="label label-danger"> '+entry.role+'</small></li>';
						$("#listUser").append(xx);
					});
				},
				error : function(data, status, er) {

				}
			});
		});
		
		function getMultipleCheckbox(inputdata) {
			var selectedItems = [];
			var count = 0;
			for (var i = 0; i < inputdata.length; i++) {
				if (inputdata[i].checked) {
					selectedItems[count] = inputdata[i].value;
					count++;
				}
			}
			if (count == 0) {
				alert("Select atleast one Person");
				return false;
			}
		}