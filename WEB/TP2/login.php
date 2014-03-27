<?php
include("./config.php");
if (isset($_POST['submit'])) { 
$connection=mysql_connect($host, $login, $passwd) or die("impossible de se connecter");
mysql_select_db($dbname) or die("impossible d'aller sur la bd");

//RECHERCHE DU USER
$query="SELECT * FROM ".$table."  WHERE username = '".$_POST['username']."'";
$check = mysql_query($query) or die("essayer plus tard!");

//si le client n'est pas ds la bd il doit s'enregistrer
$numberOfRow = mysql_num_rows($check);
if ($numberOfRow == 0) {//il n'y a aucune ligne correspondante
die("Vous n'existez pas. Cliquer <a href=register.php>ici</a> pour vous enregistrer");
}
//on doit maintenant checker le password maison le fait pas pour l'instant
//tout est OK - on ferme la connection et on envoie le client sur la page input.php
mysql_close($connection);
$header="location:member.php?nom=".$_POST['username'];//on edans l'URLnvoie avec methode GET name=valeur 
header($header);
}

else
{
// le client n'est pas loge - regardez bien imbrication php-html
//regardez bien comment j'execute le fichier lui meme au lieu d'aller ailleurs
//on envoie le formulaire
?>
<form action="<?php echo $_SERVER['PHP_SELF']?>" method="post">
<table border="0">
<tr><td><h1>Login form</h1></td></tr>
<tr><td colspan="0">Username:</td><td>
<input type="text" name="username" maxlength="20">
</td></tr>
<tr><td align="right">
<input type="submit" name="submit" value="Login">
</td></tr>
</table>
</form>
</body>
<?php //faut bien fermer la parenthese
}

?> 
