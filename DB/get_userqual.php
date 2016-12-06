<?php 
    include_once("connection.php"); 
    if($_POST['userinfo_id']) { 
		$uid = $_POST['userinfo_id'];	
		$num = 0;
		$qual = 0;       
		$avgq = -1;
        $query = "SELECT card_quality FROM card WHERE card_maker ='$uid'"; 
        $result = mysqli_query($conn, $query);        
	
		if ($result->num_rows > 0) {   		
			while($row = $result->fetch_assoc()) {
				$qual = $qual + (float)$row["card_qual"];
				$num = $num + 1;		        
			}
		}

		if($num > 0){
			$avgq = $qual/$num;
		}
		echo $avgq;	
		exit;  
    } 
?>
