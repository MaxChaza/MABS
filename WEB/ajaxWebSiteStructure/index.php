<?php  
include("./txt/header.txt");  
include("./txt/menu.txt"); 
if ($_GET['upload']=="success") {echo "<div id='content'>Your file has been successfully uploaded.<br>Welcome on our website</div>"; }
	else {if ($_GET['upload']=="failure") {echo "<div id='content'>Please try again!</div>"; }
	       else {echo "<div id='content'>Welcome on our website</div>";} }
include("./txt/footer.txt"); 
?>


