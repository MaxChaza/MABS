<?php
//we cleanly destroy the PHPSESSID by giving a past time limit
//this makes the time in the past to destroy the cookie
session_start();//necessary to check the session data if any
if (isset($_SESSION['name']) && isset($_SESSION['pass'])) {
		unset($_SESSION['name']);unset($_SESSION['pass']);
};
setcookie("PHPSESSID","",time()-3600,"/");//in any case we destroy the cookie
session_destroy(); //created with session_start and clean the session
header("Location: login.php");
?> 
