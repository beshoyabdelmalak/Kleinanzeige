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
height: 150px;
padding: 5px; 
margin :5px 0;
}

.show{
margin : 5%;
}

.link{
margin: 5%;
}

.show a {
color:black;

}
#user{
display:inline;
float:right;
}
span{
font-size:30px;
}

</style>

  </head>
  <body>
  	<a href="login" title="login" class ="link">Abmelden</a>
    <div class ="show" >
	    <#list result as anzeige>
    	  <div class="column">
        	<a href='anzeigeDetails'>${anzeige.getTitel()}</a>
        	<h4>seit : ${anzeige.getDate()}</h4>
        	<span>Preis : ${anzeige.getPreis()}€</span>
        	<span id="user"><a href='userDetails?username=${anzeige.getErsteller()}'>${anzeige.getErsteller()}</a></span>
      	</div>
      	</#list>
     </div>
     <a href="anzeigeErstellen" class="link">Anzeige Erstellen</a>
    </body>
  </html>