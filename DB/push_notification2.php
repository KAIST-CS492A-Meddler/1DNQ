<?php

// API access key from Google API's Console
define( 'API_ACCESS_KEY', 'AAAALuVy3dg:APA91bHHmLRyRXom0C2nXczD7wvuVfLDppnZVNALMzLcTDjCW-PHWgwQxu9bwBydybkXyKk-lgavv1G5Jb5x__OO-2DJyq761fxYu9BQ-FB3UrtaN-dDPBrett1wFHdxbrePHwmWgRVXTIZeBqB-ppkb7eculr0Trg' );

//replace to the array of registration_ids
$registrationIds = array( $_GET['user_deviceid'] );
$to = 'cwxISA8zv14:APA91bHCSfNZ7hQj-mMqJu57bE9mJEX9DuNvQnmD4fEO7nMhCJ8oNvkHAMy0F_iMuFLioWTtLvwYrmNE2mPZ2_zwRQZwQPz-mcdQ7HCW4Yl9OFYHQad7QAk91G7kh037BhQUBmMvOetw';
$url = 'https://fcm.googleapis.com/fcm/send';
	
// prep the bundle
include_once("connection.php"); 
$query = "SELECT card_id, card_datetime, card_type, card_maker, card_group, card_question, card_answer, card_difficulty FROM card ORDER BY RAND() LIMIT 1"; 
$result = mysqli_query($conn, $query);
$msg = array();
$row = $result->fetch_assoc();
$msg = array
(
	'card_id' 	=> $row['card_id'],
	'card_datetime'	=> $row['card_datetime'],
	'card_type' => $row['card_type'],
	'card_maker' => $row['card_maker'] ,
	'card_group' => $row['card_group'] ,
	'card_question' => $row['card_question'],
	'card_answer' => $row['card_answer'],
	'card_difficulty' => $row['card_difficulty']
);


//$msg = array
//(
//	'card_id' 	=> '1',
//	'card_datetime'	=> 'datetime',
//	'card_type' => '1',
//	'card_maker' => 'user_name' ,
//	'card_group' => 'group_name' ,
//	'card_question' => 'question' ,
//	'card_answer' => 'answer',
//	'card_difficulty' => '1'
//);


$noti = array(
	'title' => 'New Problem has come!',
	'text' => 'one day n question'
);

 

$fields = array
(
	'to' 	=> $to,
	'data'	=> $msg,
	'notification' => $noti
);
 
$headers = array(
	'Authorization:key =' . 'AAAALuVy3dg:APA91bHHmLRyRXom0C2nXczD7wvuVfLDppnZVNALMzLcTDjCW-PHWgwQxu9bwBydybkXyKk-lgavv1G5Jb5x__OO-2DJyq761fxYu9BQ-FB3UrtaN-dDPBrett1wFHdxbrePHwmWgRVXTIZeBqB-ppkb7eculr0Trg',
	'Content-Type: application/json'
);

$ch = curl_init();
curl_setopt( $ch,CURLOPT_URL, $url);
curl_setopt( $ch,CURLOPT_POST, true );
curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
$result = curl_exec($ch );
curl_close( $ch );

echo $result;
 ?>