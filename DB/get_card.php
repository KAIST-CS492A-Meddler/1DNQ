<?php 
    include_once("connection.php"); 	
    if($_POST['cinfo_id']) { 
        $cid = $_POST['cinfo_id'];        
        $query = "SELECT * FROM card WHERE card_id ='$cid'"; 
        
        $result = mysqli_query($conn, $query);   
		$result2 = array(); 	
		if ($result->num_rows > 0) {   	
			$row = $result->fetch_assoc();
			//array_push($result2, array('card_id'=>$row[0],'card_datetime'=>$row[1],'card_type'=>$row[2],'card_maker'=>$row[3],'card_group'=>$row[4], 'card_question'=>$row[5], 'card_answer'=>$row[6], 'card_hint'=>$row[7],'card_difficulty'=>$row[8]), 'card_quality'=>$row[9]), 'card_solvednum'=>$row[9]), 'card_evalnum'=>$row[10]));		
			array_push($result2, $row);
		}
		$json = json_encode(array("result"=>$result2));
		echo $json;
		mysqli_close($conn);	
		exit;  
    } 
?>
