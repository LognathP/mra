// This example requires the Places library. Include the libraries=places
// parameter when you first load the API. For example:
// <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">
function sleep(ms) {
	return new Promise(resolve => setTimeout(resolve, ms));
}

async function initMap() {

	if (document.getElementById('lat').value == '' || document.getElementById('lng').value == '')
		await sleep(1000);
	
	 var options = {
			  componentRestrictions: {country: "ind"}
			 };

	var map = new google.maps.Map(document.getElementById('map'), {
		center: {
			lat: document.getElementById('lat').value == '' ? 13.0826802 : parseFloat(document.getElementById('lat').value)
			, lng: document.getElementById('lng').value == '' ? 80.2707184 : parseFloat(document.getElementById('lng').value)
		},
		zoom: 17
	});
	var uluru = {
		lat: document.getElementById('lat').value == '' ? 13.0826802 : parseFloat(document.getElementById('lat').value)
		, lng: document.getElementById('lng').value == '' ? 80.2707184 : parseFloat(document.getElementById('lng').value)
	};

	var marker = new google.maps.Marker({ position: uluru, map: map });
	
	


	var card = document.getElementById('pac-card');
	var input = document.getElementById('ut_cover_area');
	var types = document.getElementById('type-selector');
	var strictBounds = document.getElementById('strict-bounds-selector');

	map.controls[google.maps.ControlPosition.TOP_RIGHT].push(card);

	var autocomplete = new google.maps.places.Autocomplete(input,options);

	// Bind the map's bounds (viewport) property to the autocomplete object,
	// so that the autocomplete requests use the current map bounds for the
	// bounds option in the request.
	autocomplete.bindTo('bounds', map);
	  autocomplete.setTypes([]);
	// Set the data fields to return when the user selects a place.
	autocomplete.setFields(
		['address_components', 'geometry', 'icon', 'name']);

	var infowindow = new google.maps.InfoWindow();
	var infowindowContent = document.getElementById('infowindow-content');
	infowindow.setContent(infowindowContent);
	var marker = new google.maps.Marker({
		map: map,
		anchorPoint: new google.maps.Point(0, -29)
	});

	autocomplete.addListener('place_changed', function () {
		infowindow.close();
		marker.setVisible(false);
		var place = autocomplete.getPlace();

		//document.getElementById('city2').value = place.name;
		document.getElementById('lat').value = place.geometry.location.lat();
		document.getElementById('lng').value = place.geometry.location.lng();

		// console.log(place.name);
		// console.log(place.geometry.location.lat());
		// console.log(place.geometry.location.lng());
		if (!place.geometry) {
			// User entered the name of a Place that was not suggested and
			// pressed the Enter key, or the Place Details request failed.
			window.alert("No details available for input: '" + place.name + "'");
			return;
		}

		// If the place has a geometry, then present it on a map.
		if (place.geometry.viewport) {
			map.fitBounds(place.geometry.viewport);
		} else {
			map.setCenter(place.geometry.location);
			map.setZoom(17);  // Why 17? Because it looks good.
		}
		marker.setPosition(place.geometry.location);
		marker.setVisible(true);
		

//		var rad = $('#ut_area_limit').val();
//		 // Add circle overlay and bind to marker
//		var circle = new google.maps.Circle();
//		circle.setMap(null);
//	    var circle = new google.maps.Circle({
//	      map: map,
//	      radius: parseInt(rad)*1000,    // 10 miles in metres
//	      fillColor: '#AA0000'
//	    });
//	    circle.bindTo('center', marker, 'position');
//	    

		var address = '';
		if (place.address_components) {
			address = [
				(place.address_components[0] && place.address_components[0].short_name || ''),
				(place.address_components[1] && place.address_components[1].short_name || ''),
				(place.address_components[2] && place.address_components[2].short_name || '')
			].join(' ');
		}

		infowindowContent.children['place-icon'].src = place.icon;
		infowindowContent.children['place-name'].textContent = place.name;
		infowindowContent.children['place-address'].textContent = address;
		infowindow.open(map, marker);
		
	});
	
		





}

