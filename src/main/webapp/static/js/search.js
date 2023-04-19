 $("#searchbtn").click(function(e) {
	var title = $("#search").val();
	if (title!= "") {
		$("#searchDiv").html("");
		$.ajax({
			url : "Search",
			type : 'POST',
			dataType : 'json',
			data : {
				title : title,
			}
		}).done(function(data) {
			if(data!=""){
			var count=0;
			data.forEach(function(entry) {				
				 var a;				 
				if(count%2==0)
				{
					 a='<div class="col-lg-3 col-xs-6"><div class="small-box bg-aqua"><div class="inner"><h3>Size :'+entry.size+'</h3><p>Price :'+entry.price+'</p><p>Address :'+entry.address+'</p></div><div class="icon"><i class="ion ion-bag"></i></div><a href="ViewProperties?id='+entry.pid+'"class="small-box-footer"> View All Detail <i class="fa fa-arrow-circle-right"></i></a></div></div>';
				}else
					{
					 
					  a='<div class="col-lg-3 col-xs-6"><div class="small-box bg-green"><div class="inner"><h3>	Size :'+entry.size+'</h3><p>Price :'+entry.price+'</p><p>Address :'+entry.address+'</p></div><div class="icon"><i class="ion ion-stats-bars"></i></div><a href="ViewProperties?id='+entry.pid+'"class="small-box-footer"> View All Detail <i class="fa fa-arrow-circle-right"></i></a></div></div>';
					}
				count++;
				$("#searchDiv").append(a);			
			});
		}else
			{
				var a='<div class="alert alert-danger alert-dismissable"><i class="fa fa-ban"></i><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button><b>Alert!</b><br/>OOOPS...     <br/>Data is not found.</div>';
				$("#searchDiv").append(a);
			}
		});
	}
	;
});
 
$('#search-btn').keyup(function(e) {
	if (e.keyCode == 13) {
		search();
	}
});
