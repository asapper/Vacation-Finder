var map;
var service;
var infowindow;
var mapBounds;
var origin;
var numLocs = 0;
var locsCount = 0;
var criteria_radius = 0;
var criteria_keyword = "";

function initMap() {
	
 	$.ajax({
		url: "http://localhost:8080/getCriteria",
		type: "POST",
		
		success: function(tmp) {
			// convert to meters
			criteria_radius = tmp.radius * 1609;
			criteria_keyword = tmp.type;
			
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
		    	scrollwheel: false
		  	});
		  	
		  	// init infowindow
		  	infowindow = new google.maps.InfoWindow();
		
			// init search
			
		  	service = new google.maps.places.PlacesService(map);
		  	service.nearbySearch({
		    	location: origin,
		    	radius: criteria_radius, //max: 50,000 meters ~ 31 Miles
		    	keyword: criteria_keyword
		  	}, callback);
		  
		  	// init bounds
		  	mapBounds = new google.maps.LatLngBounds();
		}
	});

}

function callback(results, status) {
	if (status === google.maps.places.PlacesServiceStatus.OK) {
		numLocs = results.length;
		for (var i = 0; i < numLocs; i++) {
    		var place = results[i];
    		var request = { placeId: place.place_id };
    		service.getDetails(request, details_callBack);
    	}
  	}
}

function details_callBack(place, status) {
	if(status == google.maps.places.PlacesServiceStatus.OK) {
		var price_level = -1;
		var name = place.name;
		var website = place.website;
		var rating = place.rating;
		var address = place.formatted_address;
		var phone_number = place.formatted_phone_number;
		var latitude = place.geometry.location.lat();
		var longitude = place.geometry.location.lng();
		
		if(place.price_level !== undefined) {
			price_level = place.price_level;
		}
		
		var json = { "price" : price_level, "name" : name, "website" : website,
					 "rating" : rating, "address" : address,
					 "phoneNumber" : phone_number, "latitude" : latitude,
					 "longitude" : longitude };
		
		$.ajax({
			url: "http://localhost:8080/map",
			data: json,
			type: "POST"
		});
		
		
		/*
		console.log("*** For place \"" + place.name + "\"");
		console.log("Latitude: " + place.geometry.location.lat());
		console.log("Longitude: " + place.geometry.location.lng());
		
		console.log("HTML attributions: " + place.html_attributions);
		console.log("Icon: " + place.icon);
		console.log("Google page: " + place.url);
		console.log("Reviews: ");
		
		//for(var k = 0; k < place.reviews.length; k++) {
			//console.log("\t[" + k + "] Author: " + place.reviews[k].author_name);
			//console.log("\tAuthor's rating: " + place.reviews[k].rating);
			//console.log("\tAuthor's text: " + place.reviews[k].text);
		//}
		
		if(place.opening_hours !== undefined) {
			console.log("Opening hours: --open now " + place.opening_hours.open_now);
		}
		if(place.price_level !== undefined) {
			console.log("Price level: " + place.price_level);
		}
		*/
		
		// update map bounds
	    map.fitBounds(mapBounds);
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
				console.log("After passing all locs ret is: " + bizList);
				for(var i = 0; i < bizList.length && i < 10; i++) {
					console.log("Name: " + bizList[i].name + "; " + bizList[i].address);
					
					// create marker for this place
					createMarker(bizList[i]);
					// add place info to table
					setTableContent(bizList[i]);
				}
			}
		});
	}
}

function createMarker(place) {
 	var placeLoc = new google.maps.LatLng(place.lat, place.lng);
 	var marker = new google.maps.Marker({
		map: map,
    	position: placeLoc
  	});
  
  	google.maps.event.addListener(marker, 'click', function() {
  		if( place.website !== undefined ) {
			infowindow.setContent("<h4>" + place.name + " (rating: " + place.rating + ")</h4>" + 
	      						place.address +"<br />" + 
	      						"Phone: " + place.phone + "<br />" +
	      						"<a href=\"" + place.website + "\" target=\"blank\">View website</a>");
		} else {
			infowindow.setContent("<h4>" + place.name + " (rating: " + place.rating + ")</h4>" + 
	      						place.address +"<br />" + 
	      						"Phone: " + place.phone + "<br />" +
	      						"No website provided<br />");
		}
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
	
	ranking.innerHTML = table_body.rows.length;
	name.innerHTML = place.name;
	address.innerHTML = place.address;
	phone.innerHTML = place.phone;
	rating.innerHTML = place.rating;
}

