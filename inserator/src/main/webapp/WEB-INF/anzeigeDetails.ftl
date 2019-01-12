<html>
<head>

<title>Anzeige Details</title>
<style>
.link{
margin: 5%;
}
</style>

</head>
 
<body>
     <a href="login" title="Abmelden" class ="link">Abmelden  </a>
     <a href="hauptseite" title="zurück in Hauptseite" class ="link"> Hauptseite  </a>
  <h1> Anzeige Details </h1>
	<#list result as anzeige>
        <strong>${anzeige.getTitel()}</strong>
        <p>Beschreibung : ${anzeige.getText()}</p>
       	<p>seit : ${anzeige.getDate()}</p>
       	<span>Preis : ${anzeige.getPreis()}€</span>
       	<span id="user"><a href='userDetails?username=${anzeige.getErsteller()}' class ="link">${anzeige.getErsteller()}</a></span> 
     </#list>


</body>
</html>