<!doctype html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Hauptseite </title>
  <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
  	<a href="logout" title="Abmelden">Abmelden  </a>
  	<a href="anzeigeErstellen">Anzeige Erstellen</a>
  	<form action="hauptseite">
	  <select name = "sort">
	    <option value="" disabled selected>Sortieren nach</option>
	    <option value="Titel aufsteigend">Titel aufsteigend</option>
	    <option value="Titel absteigend">Titel absteigend</option>
	    <option value="Erstellungsdatum aufsteigend">Erstellungsdatum aufsteigend</option>
	    <option value="Erstellungsdatum absteigend">Erstellungsdatum absteigend</option>
	  </select>
	  <select name = "filter">
	    <option value="" disabled selected>Kategorie auswählen</option>
	    <option value="Digitale Waren">Digitale Waren</option>
	    <option value="Haus & Garten">Haus & Garten</option>
	    <option value="Mode & Kosmetik">Mode & Kosmetik</option>
	    <option value="Multimedia & Elektronik">Multimedia & Elektronik</option>
	  </select>
	  <input type="submit">
	</form>
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