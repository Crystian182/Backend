<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="http://localhost:8080/SpringApp/images/j.png">

    <title>Homepage</title>

    <!-- Bootstrap core CSS -->
    <link href="https://getbootstrap.com/docs/4.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="jumbotron.css" rel="stylesheet">
   
  </head>

  <body>

    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
      <a class="navbar-brand" href="#"></a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="#">Università del Salento<span class="sr-only">(current)</span></a>
          </li>

        </ul>
        
 		<form class="form-inline my-2 my-lg-0" action="loginProcess">
          <input class="form-control mr-sm-2" name="email" type="text" placeholder="email" required="required">
          <input class="form-control mr-sm-2" name="password" type="password" placeholder="password" required="required">
          <input type='submit' name="Send" class="btn btn-outline-success my-2 my-sm-0" value="Login" />
		</form>

		
		<% if(request.getSession().getAttribute("valid") != null && !(boolean)request.getSession().getAttribute("valid")) {
					request.getSession().setAttribute("valid", null);%>
					<div style="color:red">
					 Utente non riconosciuto!
					</div>
				<%
			} 
			%>

		        
      </div>
    </nav>

    <main role="main">

      <div class="jumbotron">
        <div class="container">
			
          <h1 class="display-3">Benvenuto nel nostro Portale!</h1>
          <p>Quest'area è accessibile solo dal personale di segreteria e quello docente.</p>
        </div>
      </div>
      <div class="container">
      
        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel" data-interval="3000">
		  <div class="carousel-inner">
		    <div class="carousel-item active">
		      <img class="d-block w-100" src="http://localhost:8080/SpringApp/images/off_formativa.jpg" alt="First slide">
		    </div>

		    <div class="carousel-item">
		      <img class="d-block w-100" src="http://localhost:8080/SpringApp/images/censis_hp.jpg" alt="Second slide">
		    </div>

		    <div class="carousel-item">
		      <img class="d-block w-100" src="http://localhost:8080/SpringApp/images/ricerca.jpg" alt="Third slide">
		    </div>

		</div>

		  <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
		    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="sr-only">Previous</span>
		  </a>

		  <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
		    <span class="carousel-control-next-icon" aria-hidden="true"></span>
		    <span class="sr-only">Next</span>

		  </a>

		</div>

        </div>


    </main>

    <footer class="container">
      <p>&copy; @Copyright2018</p>
    </footer>

    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
    <script src="https://getbootstrap.com/docs/4.0/assets/js/vendor/popper.min.js"></script>
    <script src="https://getbootstrap.com/docs/4.0/dist/js/bootstrap.min.js"></script>
  </body>
</html>  		                    