<?php 
    include_once("connection.php"); 
    if(isset($_POST['ginfo_id']) && isset($_POST['ginfo_goal']) && isset($_POST['ginfo_name']) && isset($_POST['ginfo_leader'])) { 
		$gid = $_POST['ginfo_id'];
		$ggl = $_POST['ginfo_goal'];
		$gnm = $_POST['ginfo_name'];
		$gld = $_POST['ginfo_leader'];
		$gdesc = "No description";
		if($_POST['ginfo_desc']){
			$gdesc = $_POST['ginfo_desc'];
		}	
		$gdate = date("Y-m-d H:i:s");
	       
        $query = "INSERT INTO groups VALUES ('$gid', '$ggl', '$gnm', '$gld', 1, '$gdate', '$gdesc')"; 
		$query2 = "INSERT INTO usergroup VALUES ('$gid', '$gld', '', '', '', '$gdate')"; 		
		
        mysqli_query($conn, $query);
		mysqli_query($conn, $query2);
		
		echo "Group created";
		
		mysqli_close($conn);
		exit;     
    } 
?>
