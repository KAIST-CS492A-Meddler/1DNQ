<?php 
    include_once("connection.php"); 
	  
    if(isset($_POST['cinfo_type']) && isset($_POST['cinfo_question']) && isset($_POST['cinfo_answer']) && isset($_POST['cinfo_maker']) && isset($_POST['cinfo_group'])) { 
		$c_num = '0';  		
		$cdat = date("Y-m-d H:i:s");
		$ctyp = 1;
		$cque = $_POST['cinfo_question'];
		$cans = $_POST['cinfo_answer'];
		$cmak = $_POST['cinfo_maker'];
		$cgrp = $_POST['cinfo_group'];
		$chnt = $_POST['cinfo_hint'];
        //Get number of cards of user made in the group
		$query = "SELECT usergroup_cardnum FROM usergroup WHERE user_id='$cmak'";         
		$result = mysqli_query($conn, $query);        
		if ($result->num_rows > 0) {   		
			while($row = $result->fetch_assoc()) {
				$c_num = $c_num + $row['usergroup_cardnum'];
			}
		}	 
		echo $c_num;
		$cid = $_POST['cinfo_maker']."-".(String)($c_num+1);
	    //Add card data into table
		if($chnt){
		}else{
			$chnt = "No description";
		}     
		$query = "INSERT INTO card VALUES ('$cid', '$cdat', $ctyp, '$cmak', '$cgrp', '$cque', '$cans', '$chnt', -1, -1,  0, 0)"; 			
		mysqli_query($conn, $query);	
		//Update usergroup info
		$c_num = $c_num+1;    
		$query = "UPDATE usergroup SET usergroup_cardnum='$c_num' WHERE user_id='$cmak'";         
		mysqli_query($conn, $query);
		
		mysqli_close($conn);   
		exit;   
    } 
?>
