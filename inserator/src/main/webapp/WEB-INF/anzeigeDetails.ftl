<html>
<head>

<title>Anzeige Details</title>
<link rel="stylesheet" type="text/css" href="style.css">
<script>
function myFunction() {
  location.reload();
}
</script>

</head>
 
<body>
     <a href="login" title="Abmelden">Abmelden  </a>
     <a href="hauptseite" title="zurück in Hauptseite"> Hauptseite  </a>
  <h1> Anzeige Details </h1>
	<#list anzeigeDeteils as anzeige>
        <strong>${anzeige.getTitel()}</strong>
        <p>Beschreibung : ${anzeige.getText()}</p>
        <p>Status : ${anzeige.getStatus()}</p>
       	<p>seit : ${anzeige.getDate()}</p>
       	<hr>
       	<br>
       	<span>Preis : ${anzeige.getPreis()}€</span>
       	<span><a href='user?username=${anzeige.getErsteller()}' class ="link">${anzeige.getErsteller()}</a></span><br><br>
       	
       	  
		 <#if kaeufer == '${anzeige.getErsteller()}'>
	    	<form action= "anzeigeDetails?id=${anzeige.getId()}" method = "post">
				   <input type="submit" name="vomVerkäufer" onclick="myFunction()" value = "Löschen">
				   <#if status == "aktiv   ">
				   <input type="submit" name="vomVerkäufer" value = "editieren">
				   </#if>
			</form>
		<#else>
			 <#if status == "aktiv   ">
				 <form method = "post">
					   <input type="submit" name="vomKäufer" onclick="myFunction()" value= 'kaufen'>
				 </form>
		     </#if>
		</#if>
		
		 <form action="anzeigeDetails?id=${anzeige.getId()}" method="post" >
	        kommentieren: <input type="text" name="kommentarfield" /> <br/>
		    <input type="submit" value="submit" onclick="myFunction()" />
		  </form>
	     <table class="datatable">
	     <tr>
	     	<th>Kommentaren</th>
	     </tr>
		     <#list kommentaren as kommentar>
		     <tr>
		        <td>${kommentar.getText()}</td>
		     </tr>
		     </#list>
	     </table>
     </#list>
     
     


</body>
</html>