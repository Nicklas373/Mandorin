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

$nama_lengkap =  $_POST['nama_lengkap'];
$umur =  $_POST['umur']; 
$nik =  $_POST['nik']; 
$telp =  $_POST['telp']; 
$alamat =  $_POST['alamat']; 
$foto_user =  $_POST['foto_user']; 
$email =  $_POST['email']; 
$id =  $_POST['id']; 
 
$Sql_Query = "insert into tb_user_data (nama_lengkap, umur, nik, telp, alamat, foto_user, email, id) values ('$nama_lengkap', '$umur', '$nik', '$telp', '$alamat', '$foto_user', '$email', '$id')";
 
if(mysqli_query($con,$Sql_Query)){
 
echo 'Data Berhasil di Input';
 
}
else{
 
echo 'Coba Lagi';

}
mysqli_close($con);
?>