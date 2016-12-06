<?php 
    include_once("connection.php"); 	
    if($_POST['userinfo_id']) {
        $uid = $_POST['userinfo_id'];		
        $query = "SELECT group_id FROM usergroup WHERE user_id ='$uid'"; 
        $result = mysqli_query($conn, $query);   
		$result3 = array();     
	
		if ($result->num_rows > 0) {   		
			while($row = $result->fetch_assoc()) {
				$gid = $row['group_id'];				
				$query2 = "SELECT * FROM groups WHERE group_id = '$gid'";
				$result2 = mysqli_query($conn, $query2);
				if($result2->num_rows > 0){
					while($row2 = $result2->fetch_assoc()){
						//array_push($result3, array('group_id'=>$row2[0],'group_goal'=>$row2[1],'group_name'=>$row2[2],'group_leader'=>$row2[3],'group_usernum'=>$row2[4],'group_date'=>$row2[5],'group_desc'=> $row2[6]));
						array_push($result3, $row2);
					}
				}				        
			}
		}

		$json = json_encode(array("result"=>$result3));
		echo $json;
		mysqli_close($conn);	
		exit;  
    } 
?>
