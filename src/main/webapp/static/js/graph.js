$("#mytext[1]").click(function() {
	$('#ownerListMode').modal('show');
});

$("#selectOwnerBtn").click(function() {
	var uId = $('input[name=uId]:checked').val();
	if (uId) {
		$("#o_name").val(uId);

		$('#ownerListModel').modal('hide');
	}
});

// model 2
$("#o_name2").click(function() {
	$('#ownerListModel2').modal('show');
});

$("#selectOwnerBtn2").click(function() {
	var uId = $('input[name=uId]:checked').val();
	if (uId) {
		$("#o_name2").val(uId);

		$('#ownerListModel2').modal('hide');
	}
});

$("#addOwnerBtn").click(function() {
	$('#ownerListModel').modal('hide');
	$('#addOwnerModel').modal('show');
});

// Date picker
$('#datetimepicker').datetimepicker({
	defaultDate : new Date()
});
$('#datetimepicker1').datetimepicker({
	defaultDate : new Date()
});

// below code is for graphCompareAgent.jsp
var th;
function getAgent(ths) {
	th = ths;
	$('#ownerListModel').modal('show');
}

$(document).ready(function() {
	
	$('table.highchart').highchartTable();
	
	var max_fields = 5; // maximum input boxes allowed
	var wrapper = $("#inputDiv"); // Fields wrapper
	var add_button = $("#addAgentBtn"); // Add button ID
	var x = 1; // initial text box count
	$(add_button).click(function(e) { // on add input button click
		e.preventDefault();
		if (x < max_fields) { // max input box allowed
			x++; // text box increment
			var html='<div class="form-group"><div class="col-lg-2"><input type="email" onclick="getAgent(this);" class="form-control1" name="mytext[]" placeholder="agent Id"/></div><div class="col-lg-2"><a href="#" id="removeBtn" class="input-group-addon" >Remove</a></div><br/> <br/></div>';
			$(wrapper).append(html);
		}
	});

	$(wrapper).on("click", "#removeBtn", function(e) { 
		// user click on remove text
		e.preventDefault();
		$(this).parent().parent('div').remove();
		x--;
	});

	$("#selectOwnerBtn").click(function() {
		var uId = $('input[name=uId]:checked').val();
		if (uId) {
			$(th).val(uId);
			$('#ownerListModel').modal('hide');
		}
	});
	// for search field
	$('#text-search').bind('keyup change', function(ev) {      
        var searchTerm = $(this).val();       
        $('body').removeHighlight();      
        if ( searchTerm ) {          
            $('body').highlight( searchTerm );
        }
    }); 
});

// end code for graphCompareAgent.jsp
