var map;
var service;
var infowindow;
var mapBounds;
var origin;
var numLocs = 0;
var locsCount = 0;
var criteria_radius = 0;
var criteria_keyword = "";
var criteria_openStatus = false;
var markersArr = [];

function initMap() {
	
 	$.ajax({
		url: "http://localhost:8080/getCriteria",
		type: "POST",
		
		success: function(tmp) {
			// convert to meters
			criteria_radius = tmp.radius * 1609;
			criteria_keyword = tmp.type;
			criteria_openStatus = tmp.openStatus;
			
			switch(tmp.city) {
				case "Amarillo":
					origin = {lat: 35.1992, lng: -101.8453};
					break;
				case "Austin":
					origin = {lat: 30.2500, lng: -97.7500};
					break;
				case "College Station":
					origin = {lat: 30.6014, lng: -96.3144};
					break;
				case "Corpus Christi":
					origin = {lat: 27.7428, lng: -97.4019}; 
					break;
				case "Fort Worth":
					origin = {lat: 32.7574, lng: -97.3332};
					break;
				case "Houston":
					origin = {lat: 29.7604, lng: -95.3698};
					break;
				case "Lubbock":
					origin = {lat: 33.5667, lng: -101.8833};
					break;
				case "San Antonio":
					origin = {lat: 29.4167, lng: -98.5000};
					break;
				case "Waco":
					origin = {lat: 31.5514, lng: -97.1558};
					break;
				default: // init to Dallas
					origin = {lat: 32.806437331493, lng: -96.802432100254};
			}
			
			// init map
		 	map = new google.maps.Map(document.getElementById('map'), {
		  		center: origin,
		  		zoom: 10,
		    	scrollwheel: false
		  	});
		  	
		  	// init infowindow
		  	infowindow = new google.maps.InfoWindow();
		
			// init search
			
		  	service = new google.maps.places.PlacesService(map);
		  	service.nearbySearch({
		    	location: origin,
		    	radius: criteria_radius, //max: 50,000 meters ~ 31 Miles
		    	keyword: criteria_keyword,
		    	openNow: criteria_openStatus
		  	}, callback);
		  
		  	// init bounds
		  	mapBounds = new google.maps.LatLngBounds();
		}
	});

}

function callback(results, status) {
	numLocs = results.length;

	// check if GooglePlaces returned results
	if(numLocs > 0) {
		if (status === google.maps.places.PlacesServiceStatus.OK) {
			for (var i = 0; i < numLocs; i++) {
	    		var place = results[i];
	    		var request = { placeId: place.place_id };
	    		service.getDetails(request, details_callBack);
	    	}
	  	}
	}
	// else, get results from Yelp
	else {
		// send NULL object indicating all places have been sent
    	var json = { "price" : -1, "name" : "", "website" : "",
					 "rating" : -1, "address" : "",
					 "phoneNumber" : "", "latitude" : -1,
					 "longitude" : -1 };
		
		$.ajax({
			url: "http://localhost:8080/map",
			data: json,
			type: "POST",
			
			success: function(bizList) {
				// clear markers
				clearMarkers();
			
				var rank = 0;
				for(var i = 0; i < bizList.length && i < 10; i++) {
					if(bizList[i].lat != 0 || bizList[i].lng != 0) {
						// create marker for this place
						createMarker(bizList[i], rank++);
						// add place info to table
						setTableContent(bizList[i]);
					}
				}
				
				// update map bounds
	    		map.fitBounds(mapBounds);
			}
		});
	}
}

function details_callBack(place, status) {
	if(status == google.maps.places.PlacesServiceStatus.OK) {
		var price_level = -1;
		var openStatus = false;
		var name = place.name;
		var website = place.website;
		var rating = place.rating;
		var address = place.formatted_address;
		var phone_number = place.formatted_phone_number;
		var latitude = place.geometry.location.lat();
		var longitude = place.geometry.location.lng();
		var numReviews = 0;
		
		if(place.opening_hours !== undefined) {
			openStatus = place.opening_hours.open_now;
		}
		if(place.reviews !== undefined) {
			numReviews = place.reviews.length;
		}
		if(place.price_level !== undefined) {
			price_level = place.price_level;
		}
		
		var json = { "price" : price_level,
					 "openStatus" : openStatus,
					 "numReviews" : numReviews,
					 "name" : name,
					 "website" : website,
					 "googleRating" : rating,
					 "address" : address,
					 "phoneNumber" : phone_number,
					 "latitude" : latitude,
					 "longitude" : longitude };
		
		$.ajax({
			url: "http://localhost:8080/map",
			data: json,
			type: "POST"
		});
	}
	
	// increase locations counter
	locsCount++;
	
	// check if all results have been sent
	if(locsCount == numLocs) {
		// send NULL object indicating all places have been sent
    	var json = { "price" : -1, "name" : "", "website" : "",
					 "rating" : -1, "address" : "",
					 "phoneNumber" : "", "latitude" : -1,
					 "longitude" : -1 };
		
		$.ajax({
			url: "http://localhost:8080/map",
			data: json,
			type: "POST",
			
			success: function(bizList) {
				// clear markers
				clearMarkers();
			
				var rank = 0;
				for(var i = 0; i < bizList.length && i < 10; i++) {
					if(bizList[i].lat != 0 || bizList[i].lng != 0) {
						// create marker for this place
						createMarker(bizList[i], rank++);
						// add place info to table
						setTableContent(bizList[i]);
					}
				}
				
				// update map bounds
	    		map.fitBounds(mapBounds);
			}
		});
	}
}

function createMarker(place, rank) {
 	var placeLoc = new google.maps.LatLng(place.lat, place.lng);
 	var marker = new google.maps.Marker({
		map: map,
    	position: placeLoc,
    	label: rank.toString(),
    	title: place.name
  	});
  	
  	// push to array of markers
  	markersArr.push(marker);
  
	// handle businesses with no phone, rating, website provided
	var ratingText = " (rating: N/A)</h4>";
	var phoneText = "Phone not provided<br />";
	var websiteText = "No website provided<br />";
	
	if( place.rating != -1 ) {
		ratingText = " (rating: " + place.rating + ")</h4>";
	}
	if( place.phone != "" ) {
		phoneText = "Phone: " + place.phone + "<br />";
	}
	if( place.website != "" ) {
		websiteText = "<a href=\"" + place.website + "\" target=\"blank\">View website</a>";
	}
  
  	google.maps.event.addListener(marker, 'click', function() {
		infowindow.setContent("<h4>" + place.name + ratingText + 
      						place.address +"<br />" + 
      						phoneText +
      						websiteText);
	      						
		infowindow.open(map, this);
	});
	
	// update bounds for map based on this marker
	mapBounds.extend(marker.getPosition());
}

function setTableContent(place) {
	var table = document.getElementById("places-table");
  	var table_body = table.getElementsByTagName("tbody")[0];
  	
	var row = table_body.insertRow( table_body.rows.length );
	var ranking = row.insertCell(0);
	var name = row.insertCell(1);
	var address = row.insertCell(2);
	var phone = row.insertCell(3);
	var rating = row.insertCell(4);
	var openNow = row.insertCell(5);
	
	ranking.innerHTML = table_body.rows.length - 1;
	name.innerHTML = place.name;
	address.innerHTML = place.address;
	
	if(place.phone == "") {
		phone.innerHTML = "N/A";
	} else {
		phone.innerHTML = place.phone;
	}
	rating.innerHTML = place.rating;
	
	if(place.openStatus) {
		openNow.innerHTML = "Yes";
	} else {
		openNow.innerHTML = "No";
	}
}

function clearMarkers() {
	for( var i = 0; i < markersArr.length; i++) {
		markersArr[i].setMap(null);
	}
	markersArr.length = 0;
}

