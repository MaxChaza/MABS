<?php
// Connects to your Database
include("./config.php");
//This code runs if the form has been submitted
if (isset($_POST['submit'])) {

//This makes sure they did not leave any fields blank
if (!$_POST['username']  ) {//doit se faire cote client avec javascript
die('You did not complete all of the required fields');
}
//on se connecte
$connection=mysql_connect($host, $login, $passwd) or die("impossible de se connecter");
mysql_select_db($dbname) or die("impossible d'aller sur la bd");
// checks if the username is in use

$usercheck = $_POST['username'];
$query="SELECT username FROM users WHERE username = '$usercheck'";
$check = mysql_query($query) or die(mysql_error());
$numberOfRow = mysql_num_rows($check);

//if the name exists it gives an error
if ($numberOfRow != 0) {//on doit faire mieux
die('Sorry, the username '.$_POST['username'].' is already in use. Start again <a href='.$_SERVER['PHP_SELF'].'>here</a>');
}

// now we insert it into the database
$insert = "INSERT INTO users (username) VALUES ('".$_POST['username']."')";
$add_member = mysql_query($insert);
mysql_close($connection);
?>


<h1>Registered</h1>
<p>Thank you, you have registered - you may now <a href="login.php">login</a>.</p>
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
