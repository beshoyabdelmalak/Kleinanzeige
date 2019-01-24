<!doctype html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>User Profile </title>
  <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
    <a href="hauptseite" title="zurück in Hauptseite"> Hauptseite  </a>
  	<div class="user_information">
  		<h1>Profile von ${username}</h1>
  		<h3>${name}</h3>
  		<p>Beitrittsdatum : ${date}</p>
  		<p>Anzahl von verkaufter artikel : ${items}</p>
  	</div>
  	<hr>

  	<div>
  		<h2>Angeboten</h2>
	    <#list result as anzeige>
    	  <div class="column">
    	  	<h4><a href="anzeigeDetails?id=${anzeige.getId()}" >${anzeige.getTitel()} </a></h4>
        	<h4>seit : ${anzeige.getDate()}</h4>
        	<hr>
        	<span>Preis : ${anzeige.getPreis()}€</span>
        	<p class="status"> ${anzeige.getStatus()} </p>
      	</div>
      	</#list>
  	</div>
  	<div>
  		<hr>
  		<h2>Gekauft</h2>
	    <#list purchased as anzeige>
    	  <div class= "purchased">
    	  	<h4>${anzeige.getTitel()} </h4>
    	  	<h3>${anzeige.getText()} </h3>
        	<h4>seit : ${anzeige.getDate()}</h4>
        	<span>Preis : ${anzeige.getPreis()}€</span>
        	<a href='user?username=${anzeige.getErsteller()}'>${anzeige.getErsteller()}</a>
        	<br>
        	<br>
        	<hr>
        	<p>${anzeige.getStatus()}</p>
      	</div>
      	</#list>
  	</div>
    </body>
  </html>
  <script>
  var x = document.getElementsByClassName("status");
  var i;
  for(i=0 ; i< x.length ; i++){
	if(x[i].innerText == "verkauft"){
		x[i].parentNode.style.backgroundColor = "#696969";
	}
	}
  </script>
