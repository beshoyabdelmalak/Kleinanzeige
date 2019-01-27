<!doctype html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Nachricht </title>
  <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
    <a href="hauptseite" title="zurück in Hauptseite"> Hauptseite  </a>

  	<div>
	    <#list nachrichten as nachricht>
    	  <div class="message">
    	  	<#if nachricht.getAbsender() == user>
	    	  	<p><strong>${nachricht.getAbsender()}:</strong> ${nachricht.getText()}</p>
	    	<#else>
	    	  	<p><strong>Du:</strong> ${nachricht.getText()}</p>
	    	</#if>
      	</div>
      	</#list>
  	</div>
  	 
  	<form action="nachricht" method="post">
  		<input type="hidden" name="user" value=${user} />
  		<textarea name="message" rows="6" cols="120" place></textarea>
  		<br>
 		<input type="submit">
	</form>
  	
    </body>
  </html>