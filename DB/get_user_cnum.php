<?php 
    include_once("connection.php"); 
    if($_POST['txtUserid']){ 
		$userid = $_POST['txtUserid'];
		$c_num = '0';       
        
        $query = "SELECT usergroup_cardnum FROM usergroup WHERE user_id='$userid'";         
        $result = mysqli_query($conn, $query);
        
		if ($result->num_rows > 0) {   		
			while($row = $result->fetch_assoc()) {
					$c_num = $c_num + $row["usergroup_cardnum"];
			}
		}
		mysqli_close($conn);	
		echo $c_num;
    } 
?>
