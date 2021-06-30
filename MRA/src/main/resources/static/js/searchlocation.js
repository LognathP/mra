function initAutocomplete() {
    // Create the autocomplete object, restricting the search to geographical
    // location types.

	 var options = {
			  componentRestrictions: {country: "ind"}
			 };
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {!HTMLInputElement} */(document.getElementById('ut_cover_area')),options);

    // When the user selects an address from the dropdown, populate the address
    // fields in the form.
	  autocomplete.setTypes([]);
    autocomplete.addListener('place_changed', fillInAddress);
        
  	}


  



    
    
  function fillInAddress() {
    // Get the place details from the autocomplete object.
    var place = autocomplete.getPlace();
    
    //var lat1 = parseFloat(place.geometry.location.lat());
	//var lng1 = parseFloat(place.geometry.location.lng());
	//console.log(place.formatted_address);
	//console.log();
	//var lat2 = $('#lat').val();
	//var lng2 = $('#lng').val();
    

    var directionsService = new google.maps.DirectionsService();
    //var origin1 = new google.maps.LatLng(lat1,lng1);
    //var destination1 = new google.maps.LatLng(parseFloat(lat2),parseFloat(lng2));
    //calculateDistance(origin1,destination1,directionsService);
    
    //console.log(('#ut_cover_area').val());
    calculateDistance(place.formatted_address,$('#cover_area').val(),directionsService);
    
    
  }
  
  function calculateDistance(origin1, destination1, service) {
	  service.route({
	    origin: origin1,
	    destination: destination1,
	    travelMode: 'DRIVING',
	    }, function(response, status) {
	    if (status === 'OK') {
	    	console.log(response.routes[0].legs[0]);
	    	//console.log(response.routes[0].legs[0].distance.text);
	    	var limit = parseFloat($('#area_limit').val());
	    	var distance = parseFloat(response.routes[0].legs[0].distance.text);
	    	console.log(limit +"  "+distance);
	    	if(distance > limit)
	    		{
	    		$.toast({
				    text: 'Sorry ! Currently We\'re not providing Service in this location',
				    hideAfter: 2000,
				    icon: 'error',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'bottom-center'
  	
				});
	    		}
	    	else
	    		{
	    		$.toast({
				    text: 'Service Available',
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'bottom-center'
  	
				});
	    		}
	    		    	
	    } else {
	      alert('Could not display directions due to: ' + status);
	    }
	  });
	}

  
  


  
