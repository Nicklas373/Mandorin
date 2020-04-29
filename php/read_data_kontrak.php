<?php

//This script is designed by Android-Examples.com
//Define your host here.
$servername = "localhost";
//Define your database username here.
$username = "u5331750_admin";
//Define your database password here.
$password = "detrim97";
//Define your database name here.
$dbname = "u5331750_mandorin";

$con = mysqli_connect($hostname,$username,$password,$dbname);

if ($con->connect_error) {
 die("Connection failed: " . $c0n->connect_error);
} 

$email =  $_GET['email'];

$sql = "SELECT * FROM tb_kontrak where email='$email' ORDER BY id DESC";
$result = $con->query($sql);

if ($result->num_rows >0) {
 // output data of each row
 while($row[] = $result->fetch_assoc()) {
 
 $tem = $row;
 
 $json = json_encode($tem);

 }
 
} else {
 echo "0 results";
}
 echo $json;
$con->close();
?>