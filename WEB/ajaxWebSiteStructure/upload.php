<?php 
$name=basename($_FILES['uploadedfile']['name']);
$tempname= basename($_FILES['uploadedfile']['tmp_name']);
$datafile= "uploads/".$name; 
if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $datafile))
	{
	   header("Location:./index.php?upload=success");
  }
  else header("Location:./index.php?upload=failure");;
?>
