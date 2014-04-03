<?php
session_start();
if (isset($_SESSION['name']) && isset($_SESSION['pass']))
{
	echo "<script>alert(document.cookie)</script>";
echo "<br/>Admin Area<p>";
echo "Your Content<p>";
echo "<a href=logout.php>Logout</a>";

}
else { 
setcookie("PHPSESSID","",time()-3600,"/");//we destroy the cookie due to session_start
session_destroy();
header("Location: login.php"); }
?> 
