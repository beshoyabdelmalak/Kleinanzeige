<!doctype html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Hauptseite </title>
  <style>
* {
  box-sizing: border-box;
}

  .column {
  display: inline-block;
  background-color:#aaa;
  width: 300px;
  height: 300px;
  padding: 5px; 
  margin :5px 0;
}

.show{
margin : 20px;
}

.link{
margin: 20px;
}

.show a {
color:black;

}


</style>

  </head>
  <body>
  	<a href="login" title="login" class ="link">Abmelden</a>
    <div class ="show" >
	    <#list result as user>
    	  <div class="column">
        	<a href='anzeigeDetails'>${user.titel}</a>
        	<h2>seit : ${user.getDate()}</h2></br>
        	<h1> Preis : ${user.getPreis()}</h1>
      	</div>
      	</#list>
     </div>
     <a href="anzeigeErstellen" class="link">Anzeige Erstellen</a>
    </body>
  </html>