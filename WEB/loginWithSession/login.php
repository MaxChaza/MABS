<?php
if (isset($_POST['submit'])) { // if form has been submitted
// makes sure they filled it in

if(!$_POST['username'] | !$_POST['pass']) {
die('You did not fill in a required field. Please <a href="./login.php">try again</a>
 or <a href=register.php>Click here to Register</a>');
}
// checks it against the database
$pass = md5($_POST['pass']);

include("config.php");
$connection=mysql_connect($host, $login, $passwd) or die("impossible de se connecter");
mysql_select_db($dbname) or die("impossible d'aller sur la bd");
$query = "SELECT * FROM ".$table."  WHERE username = '".$_POST['username']."' AND password = '". $pass."'";
$answer = mysql_query($query) or die(mysql_error());
$check = mysql_num_rows($answer);
if ($check == 0) {
die('That user does not exist in our database.  Please <a href="./login.php">try again</a>
 or <a href=register.php>Click here to Register</a>');
} else {
//{// if login is ok then we add session variables username + passwd
mysql_close($connection);
session_start();
$_SESSION['name']=$_POST['username'];
$_SESSION['pass']=$_POST['pass'];// on met login+pass as session variables server side
header("Location: member.php"); }
}
else
{
//the form has not yet been submitted
?>
<form action="<?php echo $_SERVER['PHP_SELF']?>" method="post">
<table border="0">
<tr><td colspan=2><h1>Login</h1></td></tr>
<tr><td>Username:</td><td>
<input type="text" name="username" maxlength="40">
</td></tr>
<tr><td>Password:</td><td>
<input type="password" name="pass" maxlength="50">
</td></tr>
<tr><td colspan="2" align="right">
<input type="submit" name="submit" value="Login">
</td></tr>
</table>
</form>
<?php
}

?> 
