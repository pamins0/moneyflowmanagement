
<table>
  <tr>
    <td>State</td>
    <td>
    <input name="state" id="state-autocomplete-search"/>
    <input type="hidden" name="stateId" id="state-autocomplete-id"/>
    </td>
    <td>City</td>
    <td>
    <input name="city" id="city-autocomplete-search"/>
    <input type="hidden" name="cityId" id="city-autocomplete-id"/>
    </td>
  </tr>
</table>

<script src="./static/js/autocomplete/jquery.autocomplete.min.js"></script>
<script>


	var stateURL = "${pageContext.request.contextPath}/statelist";
	
	$('#state-autocomplete-search').autocomplete({
		 minChars: 0,
		autoSelectFirst : true,
		appentTo : "#autoParent",
		serviceUrl : stateURL,
		paramName : "tagName",
		delimiter : ",",
		onSelect : function(suggestion) {
			//alert(suggestion.data);
	$("#city-autocomplete-search").val("");
	$("#city-autocomplete-id").val("");
			$("#state-autocomplete-id").val(suggestion.data);
			$("#state-autocomplete-id").attr("value",suggestion.data);
			return false;
		},
		transformResult : function(response) {
			return {
				suggestions : $.map($.parseJSON(response), function(item) {
					return {
						value : item.stateName,
						data : item.id
					};
				})
			};
		}
	});


 	var cityURL = "${pageContext.request.contextPath}/citybystate";
// 	alert("CityURL:::"+cityURL);
	$('#city-autocomplete-search').autocomplete({
		minChars: 0,
		autoSelectFirst : true,
		appentTo : "#autoParent",
		serviceUrl : cityURL,
		paramName : "tagName",
		params: {
	        'stateId': function() {
	            return $('#state-autocomplete-id').val();
	        }
	    },
		delimiter : ",",
		onSelect : function(suggestion) {
			$("#city-autocomplete-id").val(suggestion.data);
			return false;
		},
		transformResult : function(response) {
			return {
				suggestions : $.map($.parseJSON(response), function(item) {
					return {
						value : item.cityName,
						data : item.id
					};
				})
			};
		}
	});

	
</script>


