<html>
<head>
<title>Inserator</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
 
<body>
  <h1> Inserator </h1>

  <form name = "sign in"  method ="post">
  <input type="hidden" name="which" value="login" />
          <fieldset> <legend>Anmelden:</legend>
            Benutzername:<br> <input type="text" name="Benutzername" ><br> <br>
            <input type="submit" value="Submit">
          </fieldset>
    <#if navtype == "false">
    	<b>  Benutzername nicht vorhanden, versuchen Sie noch einmal</b>	
	<#else>
		<b> wir freuen uns auf Ihren Besuch</b>	
	</#if>
	
	
	
          <br> <br><br> <br>
          
          
          
  </form>
    <form name = "register"  method ="post">
    <input type="hidden" name="which" value="register" />
          <fieldset> <legend>Registrieren:</legend>
          	Name:<br> <input type="text" name="name" ><br>
            Benutzername:<br> <input type="text" name="Benutzername" ><br> <br>
            <input type="submit" value="Submit">
          </fieldset>
    <#if navtype1 == "false">
    	<b>  Benutzername ist schon vorhanden, geben Sie einen anderen ein</b>	
	<#else>
		<b> Registrieren Sie um die attraktivsten Angeboten zu sehen </b>	
	</#if>
          
  </form>
  

</body>
</html>