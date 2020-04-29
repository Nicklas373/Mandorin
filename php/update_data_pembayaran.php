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
$alamat = $_POST['alamat']; 
$no_telp = $_POST['no_telp']; 
$biaya_desain = $_POST['biaya_desain']; 
$biaya_konstruksi = $_POST['biaya_konstruksi'];
$no_rekening = $_POST['no_rekening'];
$status_desain = $_POST['status_desain'];
$status_satu = $_POST['status_satu'];
$status_dua = $_POST['status_dua'];
$status_tiga = $_POST['status_tiga']; 
$status_empat = $_POST['status_empat']; 
$total_satu = $_POST['total_satu']; 
$total_dua = $_POST['total_dua']; 
$total_tiga = $_POST['total_tiga']; 
$total_empat = $_POST['total_empat']; 
$tgl_input_satu = $_POST['tgl_input_satu']; 
$tgl_input_dua = $_POST['tgl_input_dua']; 
$tgl_input_tiga = $_POST['tgl_input_tiga'];
$tgl_input_empat = $_POST['tgl_input_empat'];
$tgl_input_desain = $_POST['tgl_input_desain']; 
$bukti_satu = $_POST['bukti_satu']; 
$bukti_dua = $_POST['bukti_dua'];
$bukti_tiga = $_POST['bukti_tiga'];
$bukti_empat = $_POST['bukti_empat'];
$bukti_desain = $_POST['bukti_desain'];
$presentase = $_POST['presentase'];
 
$Sql_Query = "update tb_pembayaran set id = '$id', nomor_kontrak = '$nomor_kontrak', nama_pemesan = '$nama_pemesan', alamat = '$alamat', no_telp = '$no_telp', biaya_desain = '$biaya_desain', biaya_konstruksi = '$biaya_konstruksi', no_rekening = '$no_rekening', status_desain = '$status_desain', status_satu = '$status_satu', status_dua = '$status_dua', status_tiga = '$status_tiga', status_empat = '$status_empat', total_satu = '$total_satu', total_dua = '$total_dua', total_tiga = '$total_tiga', total_empat = '$total_empat', tgl_input_satu = '$tgl_input_satu', tgl_input_dua = '$tgl_input_dua', tgl_input_tiga = '$tgl_input_tiga', tgl_input_empat = '$tgl_input_empat', tgl_input_desain = '$tgl_input_desain', bukti_satu = '$bukti_satu', bukti_dua = '$bukti_dua', bukti_tiga = '$bukti_tiga', bukti_empat = '$bukti_empat', bukti_desain = '$bukti_desain', presentase = '$presentase' where id = '$id'";
 
if(mysqli_query($con,$Sql_Query)){
 
echo 'Data Berhasil di kirim';
 
}
else{
 
echo 'Coba Lagi';

}
mysqli_close($con);
?>