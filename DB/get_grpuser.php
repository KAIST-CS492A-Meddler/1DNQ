<?php 
    include_once("connection.php"); 
    if(isset($_POST['txtGroupid']) ) { 
        $grpid = $_POST['txtGroupid'];	
        
        $query = "SELECT * FROM usergroup ". 
        "WHERE group_id ='$grpid'"; 
        
        $result = mysqli_query($conn, $query);   
	$result2 = array();   
	
	if ($result->num_rows > 0) {   		
		while($row = $result->fetch_assoc()) {
			array_push($result2, array('user_id'=>$row[0],'usergroup_cardnum'=>$row[1],'usergroup_exp'=>$row[2],'usergroup_cont'=>$row[3],'usergroup_regisdate'=>$row[4]));
   		 }
	}

	$json = json_encode(array("result"=>$result2));
	echo $json;

	mysqli_close($conn);	
	exit;  
    } 
?>
