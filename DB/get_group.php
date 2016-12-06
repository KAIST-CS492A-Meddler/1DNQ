<?php 
    include_once("connection.php"); 
    if($_POST['ginfo_id']) { 
        $gid = $_POST['ginfo_id'];	        
        $query = "SELECT * FROM groups WHERE group_id ='$gid'"; 
        $result = mysqli_query($conn, $query);
		$result2 = array();   
		if ($result->num_rows > 0) {   	
			$row = $result->fetch_assoc();
			//array_push($result2, array('group_id'=>$row[0],'group_goal'=>$row[1],'group_name'=>$row[2],'group_leader'=>$row[3],'group_usernum'=>$row[4], 'group_date'=>$row[5], 'group_desc'=>$row[6]));		
			array_push($result2, $row);
		}
		$json = json_encode(array("result"=>$result2));
		echo $json;
		mysqli_close($conn);	
		exit;  
    } 
?>
