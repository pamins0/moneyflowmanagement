
<script>

// This is Global delete message
function onDeleteConfirm(url){
//     alert(url);
    if(confirm("Are you sure to delete this record ?")){
        window.location=url;
    }
}
</script>

<script type="text/javascript">
/*$(document).ready(function() {
	  function setHeight() {
	    windowHeight = $(window).innerHeight();
	    $('.left-side').css('min-height', windowHeight);
	  };
	  setHeight();	  
	  $(window).resize(function() {
	    setHeight();
	  });
	});*/
</script>
<!-- 


 <script>
var dataTable = $('projectlisttable').dataTable({
    sPaginationType: "full_numbers"
});

dataTable.columnFilter({
  sPlaceHolder: "head:before",
  aoColumns: [
      { type: "select" },  
      { type: "select" },        
      { type: "select" },  
      { type: "select" },  
      { type: "select" }
  ]
});

	jQuery(function($){
		$('#projectlisttable').footable();
	});
</script> -->

