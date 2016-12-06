<?php 
    include_once("connection.php"); 
    if($_POST['userinfo_id']) { 
		$userid = $_POST['userinfo_id'];	        
        $query = "SELECT * FROM user WHERE user_id ='$userid'"; 
        $result = mysqli_query($conn, $query);   
		$result2 = array();   
	
		if ($result->num_rows > 0) {   	
			$row = $result->fetch_assoc();
			//array_push($result2, array('user_id'=>$row[0],'user_nick'=>$row[1],'user_name'=>$row[2],'user_age'=>$row[3],'user_gender'=>$row[4], 'user_deviceid'=>$row[5], 'user_exp'=>$row[6], 'user_quality'=>$row[7],'user_token'=>$row[8]));
			array_push($result2, $row);
		}

		$json = json_encode(array("result"=>$result2));
		echo $json;
		mysqli_close($conn);	
		exit;  
    } 
?>
