<html>
<head>
<title> Anzeige editieren </title>
<link rel="stylesheet" type="text/css" href="style.css">
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
            Titel: <input type="text" name="Titel" maxlength ="100" value = "${valueOfTitel}" required> <br>
            Text: <input type="text" name="Text" value = "${valueOfText}" required> <br>
            Preis: <input type="number" name="Preis"min= "0" value = "${valueOfPreis}" required> € <br><br>
            
             <fieldset > <legend>geben Sie die Kategorie Ihrer Anzeige an:</legend>
			       <input name="chk[]" type="checkbox" value="Digitale Waren" ${valueOfchk1} >Digitale Waren
				   <input name="chk[]" type="checkbox" value="Haus & Garten" ${valueOfchk2}>Haus & Garten
				   <input name="chk[]" type="checkbox" value="Mode & Kosmetik" ${valueOfchk3}>Mode & Kosmetik
				   <input name="chk[]" type="checkbox" value="Multimedia & Elektronik" ${valueOfchk4} >Multimedia & Elektronik
			  </fieldset><br><br>
			  
            <input type="submit" name="submit" value="editieren" onclick="return val();" />
			<input type="reset" value="Reset" />
          </fieldset>
  </form> 
  <a href="hauptseite" title="zurück in die Hauptseite" >Hauptseite</a>
 
  
</body>
</html>