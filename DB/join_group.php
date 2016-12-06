<?php 
    include_once("connection.php"); 
    if($_POST['ginfo_id'] && $_POST['userinfo_id']) { 
		$gid = $_POST['ginfo_id'];
		$uid = $_POST['userinfo_id'];
		
		$jdate = date("Y-m-d H:i:s");
	       
        $query = "UPDATE groups SET group_usernum=group_usernum+1 WHERE group_id = '$gid'"; 
		$query2 = "INSERT INTO usergroup VALUES ('$gid', '$uld', '', '', '', '$jdate')"; 		
		
        mysqli_query($conn, $query);
		mysqli_query($conn, $query2);
		mysqli_close($conn);
		exit;     
    } 
?>
