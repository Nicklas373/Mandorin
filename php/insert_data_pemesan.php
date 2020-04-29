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
$nik =  $_POST['nik'];
$nama =  $_POST['nama'];
$email =  $_POST['email']; 
$no_hp =  $_POST['no_hp']; 
$alamat =  $_POST['alamat']; 
$tgl_survey =  $_POST['tgl_survey']; 
$jenis_borongan =  $_POST['jenis_borongan']; 
$data_desain =  $_POST['data_desain']; 
$data_keterangan =  $_POST['data_keterangan'];
$status = $_POST['status'];
$nik_mandor =  $_POST['nik_mandor']; 
$nama_mandor =  $_POST['nama_mandor']; 
 
$Sql_Query = "insert into tb_pemesanan (id, nik, nama, email, no_hp, alamat, tgl_survey, jenis_borongan, data_desain, data_keterangan, status, nik_mandor, nama_mandor) values ('$id', '$nik', '$nama', '$email', '$no_hp', '$alamat', '$tgl_survey', '$jenis_borongan', '$data_desain', '$data_keterangan', '$status', '$nik_mandor', '$nama_mandor')";
 
if(mysqli_query($con,$Sql_Query)){
 
echo 'Data Berhasil di Input';
 
}
else{
 
echo 'Coba Lagi';

}
mysqli_close($con);
?>