<html>
<head>
<title>Inserator</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
 
<body>
  <h1> Inserator </h1>

  <form name = "sign in"  method ="post">
          <fieldset> <legend>Anmelden:</legend>
            Benutzername:<br> <input type="text" name="Benutzername" ><br> <br>
            <input type="submit" value="Submit">
          </fieldset>
    <#if navtype == "false">
    	<b>  Benutzername nicht vorhanden, versuchen Sie noch einmal</b>	
	<#else>
		<b> wir freuen uns auf Ihren Besuch</b>	
	</#if>
          
  </form>

</body>
</html>