<?php 
    include_once("connection.php");
	$query = "INSERT INTO user (user_id, user_nick, user_name, user_age, user_gender, user_deviceid, user_token) VALUES ('$_POST[myinfo_id]', '$_POST[myinfo_nick]', '$_POST[myinfo_name]', $_POST[myinfo_age], $_POST[myinfo_gender], '$_POST[myinfo_deviceid]', '$_POST[myinfo_token]')";

	if(isset($_POST['myinfo_id']) && isset($_POST['myinfo_nick']) && isset($_POST['myinfo_name']) && isset($_POST['myinfo_age']) && isset($_POST['myinfo_gender']) && isset($_POST['myinfo_deviceid']) && isset($_POST['myinfo_token'])) {  
		$userid = $_POST['myinfo_id'];
		$usernk = $_POST['myinfo_nick'];
		$usernm = $_POST['myinfo_name'];
		$userage = $_POST['myinfo_age'];
		$usergen = $_POST['myinfo_gender'];
		$userdev = $_POST['myinfo_deviceid'];
		$usertok = $_POST['myinfo_token'];      
        
        $query = "INSERT INTO user VALUES ('$userid', '$usernk', '$usernm', $userage, $usergen, '$userdev', 0, 0.0, $usertok)"; 
		//$query = "INSERT INTO user VALUES ('sample', 'sample', 'sample', 40, 2, 'sample', 1, 1.0, 'token')";
		//$query = "INSERT INTO user (user_id, user_nick, user_name, user_age, user_gender, user_deviceid, user_token) VALUES ('$userid', '$usernk', '$usernm', $userage, $usergen, '$userdev', '$usertok')";
        
        $result = mysqli_query($conn, $query);
		echo "Account created";
		mysqli_close($conn);
		exit;
    }
?>
