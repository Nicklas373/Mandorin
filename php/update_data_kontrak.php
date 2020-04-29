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
$nomor_kontrak = $_POST['nomor_kontrak']; 
$nama_pemesan = $_POST['nama_pemesan']; 
$email = $_POST['email']; 
$no_telp = $_POST['no_telp']; 
$alamat_pekerjaan = $_POST['alamat_pekerjaan']; 
$status_pekerjaan = $_POST['status_pekerjaan'];
$presentase = $_POST['presentase'];
$waktu_mulai = $_POST['waktu_mulai'];
$waktu_akhir = $_POST['waktu_akhir'];
$data_desain = $_POST['data_desain'];
$data_rekap = $_POST['data_rekap']; 
$surat_kontrak = $_POST['surat_kontrak']; 
$role_kontrak = $_POST['role_kontrak']; 
$role_komplain = $_POST['role_komplain']; 
 
$Sql_Query = "update tb_kontrak set id = '$id', nomor_kontrak = '$nomor_kontrak', nama_pemesan = '$nama_pemesan', no_telp = '$no_telp', alamat_pekerjaan = '$alamat_pekerjaan', presentase = '$presentase', waktu_mulai = '$waktu_mulai', waktu_akhir = '$waktu_akhir', data_desain = '$data_desain', data_rekap = '$data_rekap', surat_kontrak = '$surat_kontrak', role_kontrak = '$role_kontrak', role_komplain = '$role_komplain' where id = '$id'";
 
if(mysqli_query($con,$Sql_Query)){
 
echo 'Data Berhasil di kirim';
 
}
else{
 
echo 'Coba Lagi';

}
mysqli_close($con);
?>