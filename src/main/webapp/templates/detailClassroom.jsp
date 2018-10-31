        <%@ page import ="it.unisalento.se.saw.domain.Classroom"%>
        <% Classroom classroom = (Classroom)request.getSession().getAttribute("classroom"); %>
<!DOCTYPE html>
<html>
  <head>
  <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <title>Aula <%=classroom.getName() %></title>
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 70%;
        margin: 0;
        padding: 0;
      }
    </style>
  </head>
  <body>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1 class="text-center">
                Aula <%=classroom.getName() %>
            </h1>
            <h3 class="text-center">
                Edificio <%=classroom.getBuilding().getName() %>
            </h3>
        </div>
        <div id="no-more-tables">
            <table class="col-md-12 table-bordered table-striped table-condensed cf" align="center">
        		<tbody>
        			<tr>
        				<th>Indirizzo</th>
        				<th><%=classroom.getBuilding().getAddress() %></th>
        			</tr>
        			<tr>
        				<th>Posti disponibili</th>
        				<th><%=classroom.getBuilding().getAddress() %></th>
        			</tr>
        			<tr>
        				<th>Strumenti didattica</th>
        				<th><%=classroom.getBuilding().getAddress() %></th>
        			</tr>
        			<tr>
        				<th>Ticket</th>
        				<th><%=classroom.getBuilding().getAddress() %></th>
        			</tr>
        			
        			
        		</tbody>
        	</table>
</div>
</div>
</div>
<br>
    <div id="map"></div>
    <script>
      function initMap() {
    	  var myLatLng = {lat: <%=classroom.getLatitude() %>, lng: <%=classroom.getLongitude() %>};
          var map = new google.maps.Map(document.getElementById('map'), {
          center: myLatLng,
          zoom: 19,
          mapTypeId: 'satellite'
        });
        
        var marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            title: 'Aula <%=classroom.getName() %>'
          });
      }
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDeqBKjtOUNXR_H33V1oWKYSWWjsGA3J-E&callback=initMap"
    async defer></script>
  </body>
</html>