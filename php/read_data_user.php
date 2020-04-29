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

$nama_lengkap = $_GET['nama_lengkap'];
$email = $_GET['email'];
$umur = $_GET['umur'];
$nik = $_GET['nik'];
$telp = $_GET['telp'];
$alamat = $_GET['alamat'];
$foto_user = $_GET['foto_user'];
$last_modified = $_GET['last_modified'];

$sql = "SELECT * FROM tb_user_data where email='$email'";
$result = $con->query($sql);

if ($result->num_rows > 0) {
 
 // output data of each row
 while($row[] = $result->fetch_assoc()) {
 
 $json = json_encode($row);
 
 
 }
} else {
 echo "0 results";
}
echo $json;
$con->close();
?>