<html>
<head>
<title> Anzeige editieren </title>
<script type="text/javascript">
function val(){
var chks = document.getElementsByName('chk[]');
var hasChecked = false;
for (var i = 0; i < chks.length; i++)
{
if (chks[i].checked)
{
hasChecked = true;
break;
}
}
if (hasChecked == false)
{
	alert("Bitte wählen Sie mindestens eine Kategorie aus");
	return false;
}
return true;
}
</script>
</head>
 
<body>
	<h1> Editieren Sie Ihre Anzeige </h1>
    <form name = "anzeigeErstellung"  method="post">
          <fieldset> <legend>Anzeige anlegen:</legend>
            Titel:<br> <input type="text" name="Titel" maxlength ="100" placeholder= "HP Probook 480" required> <br>
            Text:<br> <input type="text" name="Text" placeholder= "neue Ware" required> <br>
            Preis:<br> <input type="number" name="Preis"min= "0" placeholder= "900" required> € <br><br>
            
             <fieldset > <legend>geben Sie die Kategorie Ihrer Anzeige an:</legend>
			       <input name="chk[]" type="checkbox" value="Digitale Waren">Digitale Waren
				   <input name="chk[]" type="checkbox" value="Haus & Garten">Haus & Garten
				   <input name="chk[]" type="checkbox" value="Mode & Kosmetik">Mode & Kosmetik
				   <input name="chk[]" type="checkbox" value="Multimedia & Elektronik">Multimedia & Elektronik
			  </fieldset><br><br>
			  
            <input type="submit" name="submit" value="editieren" onclick="return val();" />
			<input type="reset" value="Reset" />
          </fieldset>
  </form> 
  <a href="hauptseite" title="zurück in die Hauptseite" class ="link">Hauptseite</a>
 
  
</body>
</html>