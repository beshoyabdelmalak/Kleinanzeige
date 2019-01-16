<!doctype html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Hauptseite </title>
  <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
  	<a href="login" title="Abmelden">Abmelden  </a>
  	<a href="anzeigeErstellen">Anzeige Erstellen</a>
    <div class ="show" >
	    <#list result as anzeige>
    	  <div class="column">
        	<h4><a href="anzeigeDetails?id=${anzeige.getId()}" name = "anzeigeId" title = "um Dateils zu zeigen, drücken Sie hier">${anzeige.getTitel()}</a></h4>
        	<h4>seit : ${anzeige.getDate()}</h4>
        	<span>Preis : ${anzeige.getPreis()}€</span>
        	<span id="user"><a href='user?username=${anzeige.getErsteller()}'>${anzeige.getErsteller()}</a></span>
      	</div>
      	</#list>
     </div>
    </body>
  </html>