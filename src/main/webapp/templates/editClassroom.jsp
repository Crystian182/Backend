<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.List"%>
<%@ page import ="it.unisalento.se.saw.domain.Building"%>
<%@ page import ="it.unisalento.se.saw.domain.Classroom"%>
<%@ page import ="it.unisalento.se.saw.domain.Instruments"%>
<%@ page import ="it.unisalento.se.saw.domain.ClassroomHasInstruments"%>
<%Classroom classroom = (Classroom)request.getSession().getAttribute("classroom");%>
<%List<Building> building = (List<Building>)request.getSession().getAttribute("building"); %>
<%List<Instruments> instruments = (List<Instruments>)request.getSession().getAttribute("instruments"); %>
<%List<ClassroomHasInstruments> instrumentsInClassroom = (List<ClassroomHasInstruments>)request.getSession().getAttribute("instrumentsInClassroom"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modifica aula</title>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style>
#contatti{
  background-color: #70c3be;
  letter-spacing: 2px;
  }
#contatti a{
  color: #fff;
  text-decoration: none;
}


@media (max-width: 575.98px) {

  #contatti{padding-bottom: 800px;}
  #contatti .maps iframe{
    width: 100%;
    height: 450px;
  }
 }


@media (min-width: 576px) {

   #contatti{padding-bottom: 800px;}

   #contatti .maps iframe{
     width: 100%;
     height: 450px;
   }
 }

@media (min-width: 768px) {

  #contatti{padding-bottom: 350px;}

  #contatti .maps iframe{
    width: 100%;
    height: 850px;
  }
}

@media (min-width: 992px) {
  #contatti{padding-bottom: 200px;}

   #contatti .maps iframe{
     width: 100%;
     height: 700px;
   }
}


#author a{
  color: #fff;
  text-decoration: none;
    
}
</style>

<style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #description {
        font-family: Roboto;
        font-size: 15px;
        font-weight: 300;
      }

      #infowindow-content .title {
        font-weight: bold;
      }

      #infowindow-content {
        display: none;
      }

      #map #infowindow-content {
        display: inline;
      }

      .pac-card {
        margin: 10px 10px 0 0;
        border-radius: 2px 0 0 2px;
        box-sizing: border-box;
        -moz-box-sizing: border-box;
        outline: none;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
        background-color: #fff;
        font-family: Roboto;
      }

      #pac-container {
        padding-bottom: 12px;
        margin-right: 12px;
      }

      .pac-controls {
        display: inline-block;
        padding: 5px 11px;
      }

      .pac-controls label {
        font-family: Roboto;
        font-size: 13px;
        font-weight: 300;
      }

      #pac-input {
        background-color: #fff;
        font-family: Roboto;
        font-size: 15px;
        font-weight: 300;
        margin-left: 12px;
        padding: 0 11px 0 13px;
        text-overflow: ellipsis;
        width: 400px;
      }

      #pac-input:focus {
        border-color: #4d90fe;
      }

      #title {
        color: #fff;
        background-color: #4d90fe;
        font-size: 25px;
        font-weight: 500;
        padding: 6px 12px;
      }
      #target {
        width: 345px;
      }
    </style>

</head>
<body>

	<div class="row" id="contatti">
		<div class="container mt-5" >
		    <div class="row" style="height:550px;">
			    <div class="col-md-6 maps" >
			         <input id="pac-input" class="controls" type="text" placeholder="Search Box">
			    <div id="map"></div>
			    <script>
				    function initAutocomplete() {
				         var myLatLng = {lat: <%=classroom.getLatitude() %>, lng: <%=classroom.getLongitude() %>};
				         var map = new google.maps.Map(document.getElementById('map'), {
				         center: myLatLng,
				         zoom: 19,
				         mapTypeId: 'satellite'
				    });
				          
			        document.getElementById("hiddenFieldLat").value=<%=classroom.getLatitude() %>;
			        document.getElementById("hiddenFieldLon").value=<%=classroom.getLongitude() %>;
			        var markers = [];
			        var marker = new google.maps.Marker({
			             map: map,
			             title: 'Aula <%=classroom.getName() %>',
			             position: myLatLng,
			             draggable:true
			        });
			        markers.push(marker);
				    
			        google.maps.event.addListener(marker, 'dragend', function() {
	        		     updatePosition(marker.getPosition().lat(), marker.getPosition().lng());
	        		});
			          
			        function updatePosition(lat, lng) {
			       		 document.getElementById("hiddenFieldLat").value= lat;
			       		 document.getElementById("hiddenFieldLon").value= lng;
			        }
				
			        // Create the search box and link it to the UI element.
			        var input = document.getElementById('pac-input');
			        var searchBox = new google.maps.places.SearchBox(input);
			        map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
			
			        // Bias the SearchBox results towards current map's viewport.
			        map.addListener('bounds_changed', function() {
			        	searchBox.setBounds(map.getBounds());
			        });
				
			        //var markers = [];
			        // Listen for the event fired when the user selects a prediction and retrieve
			        // more details for that place.
			        searchBox.addListener('places_changed', function() {
			          	var places = searchBox.getPlaces();
			          	if (places.length == 0) {
			            return;
			        }
				
			        // Clear out the old markers.
			        markers.forEach(function(marker) {
			            marker.setMap(null);
			        });
			        markers = [];
			
			        // For each place, get the icon, name and location.
			        var bounds = new google.maps.LatLngBounds();
			        places.forEach(function(place) {
			        	if (!place.geometry) {
			            	console.log("Returned place contains no geometry");
			            	return;
			            }
				            
		            marker = new google.maps.Marker({
		                map: map,
		                title: place.name,
		                position: place.geometry.location,
		                draggable:true
		            })
		
		            // Create a marker for each place.
		            markers.push(marker);
				            
		            google.maps.event.addListener(marker, 'dragend', function() {
		          		updatePosition(marker.getPosition().lat(), marker.getPosition().lng());
		            });
				
		            document.getElementById("hiddenFieldLat").value=place.geometry.location.lat();
		            document.getElementById("hiddenFieldLon").value=place.geometry.location.lng();
		            
		            if (place.geometry.viewport) {
			            // Only geocodes have viewport.
			            bounds.union(place.geometry.viewport);
		            } else {
		              	bounds.extend(place.geometry.location);
		            }
		          	});
			        map.fitBounds(bounds);
			        });
		      		}
			
			    </script>
			    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCF09XamyyB7B1of7vJzmw_ESXfPSmthuI&libraries=places&callback=initAutocomplete"
			         async defer></script>
			    </div>
			      <div class="col-md-6">
			        	<h2 class="text-uppercase mt-3 font-weight-bold text-white">Modifica Aula <%=classroom.getName() %></h2>
			        	<form action="classroomProcess" method="post">
			          		<div class="row">
			            		<div class="col-lg-6">
			             			 <div class="form-group">
			              				Nome aula: 
			                			<input type="text" name="name" value="<%=classroom.getName() %>" required>
			              			</div>
		            			</div>
			            		<div class="col-lg-6">
			              			<div class="form-group">
			              				Edificio:  <br>
			                			<select id="buildings" name="build">
										<% for(int i=0; i<building.size(); i++){ 
											if(building.get(i).getIdbuilding() == classroom.getBuilding().getIdbuilding()) { %>
												<option selected value=<%=building.get(i).getIdbuilding() %>><%=building.get(i).getName() %></option>
											<%} else {%>
											<option value=<%=building.get(i).getIdbuilding() %>><%=building.get(i).getName() %></option>
											<%}%>
										<%}%>
										</select>
			              			</div>
			            		</div>
			            	</div>
			            <div class="row">
			            	<div class="col-lg-6">
			              		<div class="form-group">
			              			Posti disponibili:
			                		<input type="number" name="seats" value="<%=classroom.getSeats() %>" required>
		              			</div>
			            	</div>
			            	<div class="col-12">
			              		<div class="form-group">
			              			Strumenti didattica:
					                <div class="checkbox checkbox-circle">
					                    	<% for(int i=0; i<instruments.size(); i++){ %>
					                    		<%for(int j=0; j<instrumentsInClassroom.size(); j++){
					                    			if(instrumentsInClassroom.get(j).getInstruments().getIdinstruments() == instruments.get(i).getIdinstruments()){ %>
					                    				<label><input name="checkbox" id="<%=instruments.get(i).getIdinstruments() %>" type="checkbox" checked="" /> <%=instruments.get(i).getName() %></label>
					                    				<input name="quantity" id="<%=instruments.get(i).getIdinstruments() %>"  type="text" for="<%=instruments.get(i).getIdinstruments() %>" value="<%=instrumentsInClassroom.get(j).getQuantity() %>">
					                    				<br>
					                  					<% break;
					                    			} else if (j == instrumentsInClassroom.size()-1) {%>
					                    				<label><input name="checkbox" id="<%=instruments.get(i).getIdinstruments() %>" type="checkbox" /> <%=instruments.get(i).getName() %></label>
					                    				<input type="text" for="<%=instruments.get(i).getIdinstruments() %>" placeholder="Quantità">
					                    				<br>
													<%} %>
						                        <%}%>
											<%}%>
											<label><input id="altro" type="checkbox"/> Altro</label>
					                    	<input type="text" id="namealtro" placeholder="Nuovo strumento">
					                    	<input type="number" id="quantityaltro" placeholder="Quantità">

					                    	
			                    	</div>
			              		</div>
			            	</div>
							<div class="col-12">
			              		<div class="form-group">
			            			<input type="hidden" name="hiddenLat" id="hiddenFieldLat"/>
			            			<input type="hidden" name="hiddenLon" id="hiddenFieldLon"/>
			            		</div>
			            	</div>
			            	<div class="col-12">
			              		<button class="btn btn-light" type="submit" onclick="fun()">Invia</button>
			            	</div>
			            	
			          	</div>
			        </form>

			      </div>
	    </div>
	</div>
</div>

</body>
</html>