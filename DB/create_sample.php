<?php 
    include_once("connection.php");
	if($_POST['sample_id']){
	$temp = $_POST['sample_id'];
	$query = "INSERT INTO sample (sample_id) VALUES ('$temp')";

	}else{
	$query = "INSERT INTO sample (sample_id) VALUES ('NO_ISSET')";
	}
	mysqli_query($conn, $query);
?>
