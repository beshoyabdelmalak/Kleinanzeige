<html>
<head>
<title> Anzeigeerstellung </title>

</head>
 
<body>
	<h1> Ihre neue Anzeige </h1>
    <form name = "anzeigeErstellung" action="hello.ftl" method="post">
          <fieldset> <legend>Anzeige anlegen:</legend>
            Titel:<br> <input type="text" name="Titel" > <br>
            Text:<br> <input type="text" name="Text" > <br>
            Preis:<br> <input type="text" name="Preis" ><br><br>
             <fieldset> <legend>geben Sie die Kategorie Ihrer Anzeige:</legend>
			        <ul>
				      <li> 
				        <label>
				          <input type="checkbox" name="Kategorie" value="Digitale Waren">
				          Digitale Waren
				        </label>
				      </li>
				      <li> 
				        <label>
				           <input type="checkbox" name="Kategorie" value="Haus & Garten">
				           Haus & Garten
				        </label>
				      </li>
				      <li>  
				        <label>
				          <input type="checkbox" name="Kategorie" value="Mode & Kosmetik">
				          Mode & Kosmetik
				        </label>
				      </li>
				      <li>
				        <label>
				          <input type="checkbox" name="Kategorie" value="Multimedia & Elektronik">
				          Multimedia & Elektronik
				        </label>
				      </li>
			    </ul>  
			  </fieldset><br><br>
            <input type="submit" value="Submit">
          </fieldset>
  </form> 
 
  
</body>
</html>