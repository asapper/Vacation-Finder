var map;
var infowindow;
var service;
var mapBounds;
var numLocs = 0;
var locsCount = 0;
var locsArr = [];

function initMap() {
	
	$.ajax({
		url: "http://localhost:8080/express",
		type: "POST",
		
		success: function(tmp) {
			var origin;
			var keyword;
		
			switch(tmp) {
				case 1:
					// Dallas, TX - camping
					origin = {lat: 32.806437331493, lng: -96.802432100254};
					keyword = "campground";
					break;
				case 2:
					// Houston, TX - hunting
					origin = {lat: 29.7604, lng: -95.3698};
					keyword = "biking";
					break;
				default:
					// Austin, TX - hiking
					origin = {lat: 30.2500, lng: -97.7500};
					keyword = "hiking";
					break;
			}
			
			map = new google.maps.Map(document.getElementById('map'), {
			    center: origin,
			    zoom: 15,
			    scrollwheel: false
			});
			
			infowindow = new google.maps.InfoWindow();
			
			service = new google.maps.places.PlacesService(map);
			service.nearbySearch({
			    location: origin,
			    radius: 50000,
				keyword: keyword
			}, callback);
			  
			// init bounds
			mapBounds = new google.maps.LatLngBounds();
		}
	});
}

function callback(results, status) {
  numLocs = results.length;
  
  if (status === google.maps.places.PlacesServiceStatus.OK) {
    for (var i = 0; i < results.length; i++) {
      var place = results[i];
	  var request = { placeId: place.place_id };
	  service.getDetails(request, details_callback);
    }
  }
}

function createMarker(place, rank) {
  var placeLoc = place.geometry.location;
  var marker = new google.maps.Marker({
    map: map,
    position: place.geometry.location,
    label: rank.toString(),
    title: place.name
  });
  
  google.maps.event.addListener(marker, 'click', function() {
  	var websiteText = "No website provided<br />";
  
  	if(place.website !== undefined) {
  		websiteText = "<a href=\"" + place.website + "\" target=\"blank\">View website</a>";
 	}

  	// add info to marker
  	infowindow.setContent("<h4>" + place.name + 
  						" (rating: " + place.rating + ")</h4>" +
  						place.formatted_address + "<br />" +
  						"Phone: " + place.formatted_phone_number + "<br />" +
  						websiteText);
  
  	infowindow.open(map, this);
  });
  
  // update bounds for map based on this marker
  mapBounds.extend(marker.getPosition());
}

function details_callback(details, status) {
	if(status == google.maps.places.PlacesServiceStatus.OK) {
	  	// add location
	  	locsArr.push(details);
	}
	
	// increase locations counter
  	locsCount++;
  
  	// check if details for all results have been received
  	if(locsCount == numLocs) {
  		for(var j = 0; j < locsArr.length && j < 10; j++) {
  			// create marker
  			createMarker(locsArr[j], j);
  			// set table contents
	  		setTableContent(locsArr[j], j);
  		}
  		
  		// update map bounds
  		map.fitBounds(mapBounds);
  	} 
}

function setTableContent(place, rank) {
	var table = document.getElementById("places-table");
  	var table_body = table.getElementsByTagName("tbody")[0];
  	
	var row = table_body.insertRow( table_body.rows.length );
	var ranking = row.insertCell(0);
	var name = row.insertCell(1);
	var address = row.insertCell(2);
	var phone = row.insertCell(3);
	var rating = row.insertCell(4);
	var openNow = row.insertCell(5);
	
	ranking.innerHTML = rank.toString();
	name.innerHTML = place.name;
	address.innerHTML = place.formatted_address;
	
	if(place.formatted_phone_number === undefined) {
		phone.innerHTML = "N/A";
	} else {
		phone.innerHTML = place.formatted_phone_number;
	}
	
	if(place.rating === undefined) {
		rating.innerHTML = 2;
	} else {
		rating.innerHTML = place.rating;
	}
	
	if(place.opening_hours !== undefined) {
		if(place.opening_hours.open_now) {
			openNow.innerHTML = "Yes";
		} else {
			openNow.innerHTML = "No";
		}
	} else {
		openNow.innerHTML = "Yes";
	}
}



