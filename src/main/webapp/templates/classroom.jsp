<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import ="java.util.List"%>
    <%@ page import ="it.unisalento.se.saw.domain.Building"%>
    <%@ page import ="it.unisalento.se.saw.domain.Classroom"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="icon" href="http://localhost:8080/SpringApp/images/j.png">
	
    <title>Benvenuto</title>

    <!-- Bootstrap core CSS -->
    <link href="https://getbootstrap.com/docs/4.0/dist/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
    <link href="jumbotron.css" rel="stylesheet">
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
    .bs-example{
    	margin: 20px;
    }
</style>

    
  </head>


<body>

 <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
  <a class="navbar-brand" href="#">Benvenuto</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      
 
      
       <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Insegnamenti</a>
            <div class="dropdown-menu" aria-labelledby="dropdown01">
              <a class="dropdown-item" href="#">Triennale</a>
              <a class="dropdown-item" href="#">Magistrale</a>
           
            </div>
          </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="" id="dropdown02" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Segnalazioni</a>
            <div class="dropdown-menu" aria-labelledby="dropdown01">
              <a class="dropdown-item" href="#">Fai una segnalazione</a>
              <a class="dropdown-item" href="#">Stato segnalazioni</a>
           	  <a class="dropdown-item" href="#">Segnalazioni presentate</a>
            </div>
          </li>
      <li class="nav-item active">
        <a class="nav-link" href="#">Messaggi<span class="sr-only">(current)</span></a>
      </li>
    </ul>
   		
      <button class="btn btn-outline-success my-2 my-sm-0">Logout</button>
 
  </div>
</nav>

<%List<Building> building = (List<Building>)request.getSession().getAttribute("building"); %>

<form style="margin-top:100px; margin-left:10px;" action="<%=request.getContextPath()%>/staff/classroom/search">
	<select id="buildings" name="build">
	
	    <option value=0>--- Seleziona un edificio ---</option>
	
		<% for(int i=0; i<building.size(); i++){ %>
			<option value=<%=building.get(i).getIdbuilding() %>><%=building.get(i).getName() %></option>
		<%}%>
	
	</select>
  <input type="text" name="name" class="textbox" placeholder="Search">
  <input title="Search" value="Cerca" type="submit" class="button">
</form>

<% if(request.getSession().getAttribute("valid") != null && !(boolean)request.getSession().getAttribute("valid")) {
					request.getSession().setAttribute("valid", null);%>
					<div style="color:red">
					 Completa i campi!
					</div>
				<%
	} else if (request.getSession().getAttribute("valid") != null && (boolean)request.getSession().getAttribute("valid")){
		request.getSession().setAttribute("valid", null);
		List<Classroom> classroom = (List<Classroom>)request.getSession().getAttribute("classroom");
		if(classroom.isEmpty()){ %>
			<div style="color:red">
			 Nessuna corrispondenza!
			</div>
		<%} else {
				request.getSession().setAttribute("valid", null);%>
				
				<div class="bs-example">
    <table class="table table-hover" style="margin-top:100px; margin-left:10px;">
        <thead>
            <tr>
                <th>#</th>
                <th>Nome</th>
                <th>Edificio</th>
                <th> </th>
                <th> </th>
            </tr>
        </thead>
        <tbody>
        		<%for(int i=0; i<classroom.size(); i++){%>
				
            <tr>
                <td><%=i+1 %></td>
                <td><%=classroom.get(i).getName() %></td>
                <td><%=classroom.get(i).getBuilding().getName() %></td>
                <td><img src="http://localhost:8080/SpringApp/images/view.png" alt="Visualizza" height="30" width="30" onclick="window.open('./' + <%=classroom.get(i).getIdclassroom() %>, 'Dettagli', 'height=600,width=400, left=500,top=60')" /></td>
                <td><img src="http://localhost:8080/SpringApp/images/edit.png" alt="Modifica" height="30" width="30" onclick="window.open('./edit/' + <%=classroom.get(i).getIdclassroom() %>, 'Modifica', 'height=600,width=1200, left=60,top=60')" /></td>
            </tr>
            	<%}%>
        </tbody>
    </table>
</div>
		
		<%}%>
		<%}%>
	
	


 <footer class="container">
      <p>&copy; Company 2017-2018</p>
    </footer>

<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
    <script src="https://getbootstrap.com/docs/4.0/assets/js/vendor/popper.min.js"></script>
    <script src="https://getbootstrap.com/docs/4.0/dist/js/bootstrap.min.js"></script>
 


</body>
</html>