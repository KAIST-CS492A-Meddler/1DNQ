<?php

$host = 'localhost';
$user = 'root';
$pw = 'meddler';
$dbname = '1DNQ';

$conn = mysqli_connect($host, $user, $pw, $dbname);

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
?>