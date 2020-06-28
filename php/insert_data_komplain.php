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

$id = $_POST['id'];
$nama_pemesan = $_POST['nama_pemesan'];
$nomor_kontrak =  $_POST['nomor_kontrak'];
$email =  $_POST['email'];
$alamat =  $_POST['alamat']; 
$komplain =  $_POST['komplain']; 
$status_komplain =  $_POST['status_komplain']; 
$tgl_komplain = $_POST['tgl_komplain'];
 
$Sql_Query = "insert into tb_komplain (id, nama_pemesan, nomor_kontrak, email, alamat, komplain, status_komplain, tgl_komplain) values ('$id', '$nama_pemesan', '$nomor_kontrak', '$email', '$alamat', '$komplain', '$status_komplain', '$tgl_komplain')";
 
if(mysqli_query($con,$Sql_Query)){
 
echo 'Data Berhasil di Input';
 
}
else{
 
echo 'Coba Lagi';

}
mysqli_close($con);
?>