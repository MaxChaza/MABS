<?php
//This code runs if the form has been submitted
if (isset($_POST['submit'])) {
if (!$_POST['username'] | !$_POST['pass'] | !$_POST['pass2'] ) {//should be done with JS client side
die('You did not complete all of the required fields. Please try again ');
}
// this makes sure both passwords entered match - should be done client side
if ($_POST['pass'] != $_POST['pass2']) {die('Your passwords did not match. '); }

// checks if the username is in use
$username = $_POST['username'];

include("config.php");
$connection=mysql_connect($host, $login, $passwd) or die("impossible de se connecter");
mysql_select_db($dbname) or die("impossible d'aller sur la bd");

$query = "SELECT * FROM ".$table."  WHERE username = '".$_POST['username']."'";
$answer = mysql_query($query) or die(mysql_error());
$check = mysql_num_rows($answer);
//if the name exists it gives an error
if ($check != 0) {die('Sorry, the username '.$username.' is already in use.'); }

// here we encrypt the password - no need to decrypt
$pass = md5($_POST['pass']);

// now we insert it into the database
$insert = "INSERT INTO ".$table. " (username, password) VALUES ('".$username."', '".$pass."')";
mysql_query($insert);
mysql_close($connection);
?>

<h1>Registered</h1>
<p>Thank you, you have registered - you may now <a href="./login.php">login</a>.</p>
<?php
}
else
{
?>
<form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post">
<table border="0">
<tr><td>Username:</td><td>
<input type="text" name="username" maxlength="60">
</td></tr>
<tr><td>Password:</td><td>
<input type="password" name="pass" maxlength="10">
</td></tr>
<tr><td>Confirm Password:</td><td>
<input type="password" name="pass2" maxlength="10">
</td></tr>
<tr><th colspan=2><input type="submit" name="submit" value="Register"></th></tr> </table>
</form>

<?php
}
?> 
