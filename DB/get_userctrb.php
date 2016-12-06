<?php 
    include_once("connection.php"); 
    if($_POST['userinfo_id']) { 
        $uid = $_POST['userinfo_id'];	
		$ctrb = 0;       
        $query = "SELECT usergroup_cont FROM usergroup WHERE user_id='$uid'";      
        $result = mysqli_query($conn, $query);
		
		if ($result->num_rows > 0) {   		
			while($row = $result->fetch_assoc()) {
		        $ctrb = $ctrb + $row['usergroup_cont'];
			}
		}
		mysqli_close($conn);
		echo $ctrb;	     
    } 
?>
