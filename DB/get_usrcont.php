<?php 
    include_once("connection.php"); 
    if(isset($_POST['txtUserid']) ) { 
        $userid = $_POST['txtUserid'];	
	$usercont = '0';       
        
        $query = "SELECT usergroup_cont FROM usergroup ". 
        "WHERE user_id='$userid'"; 
        
        $result = mysqli_query($conn, $query);
        
	if(isset($_POST['mobile']) && $_POST['mobile'] == "android"){ 
                echo "success";                 
        } 
	if ($result->num_rows > 0) {   		
		while($row = $result->fetch_assoc()) {
		        $usercont = $usercont + $row["usergroup_cont"];
   		 }
	}

	echo $usercont;	     
    } 
?>
