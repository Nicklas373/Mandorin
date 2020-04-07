package id.hana.mandorin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class activity_data_transaksi_2 extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView Nomor_Rekening, FileName, id_1, nomor_kontrak_1, nama_pemesan_1, email_1, alamat_1, no_telp_1, biaya_desain_1, biaya_konstruksi_1, status_satu_1, status_dua_1, status_tiga_1, status_empat_1, status_desain_1, total_satu_1, total_dua_1, total_tiga_1, total_empat_1, tgl_satu, tgl_dua, tgl_tiga, tgl_empat, tgl_desain_1, bukti_satu_1, bukti_dua_1,  bukti_tiga_1, bukti_empat_1, bukti_desain_1, presentase_1;
    private ImageView kirim;
    private CardView back;
    private ImageView upload;
    ProgressDialog dialog;

    /*
     * Static File Request & Server URL Initializations
     */
    private static final int STORAGE_PERMISSION_CODE = 123;
    private String selectedFilePath;
    private String SERVER_URL = "http://mandorin.site/mandorin/upload_bukti_pembayaran.php";
    private static final int PICK_FILE_REQUEST = 1;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transaksi_2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        id_1 = findViewById(R.id.identitas_pemesan_pembayaran_transaksi_2);
        nomor_kontrak_1 = findViewById(R.id.nomor_kontrak_pembayaran_transaksi_2);
        nama_pemesan_1 = findViewById(R.id.nama_pemesan_pembayaran_transaksi_2);
        email_1 = findViewById(R.id.email_pemesan_pembayaran_transaksi_2);
        alamat_1 = findViewById(R.id.alamat_pemesan_pembayaran_transaksi_2);
        no_telp_1 = findViewById(R.id.no_telp_pemesan_pembayaran_transaksi_2);
        biaya_desain_1 = findViewById(R.id.biaya_desain_pembayaran_transaksi_2);
        biaya_konstruksi_1 = findViewById(R.id.biaya_konstruksi_pembayaran_transaksi_2);
        Nomor_Rekening = findViewById(R.id.user_input_nomor_rekening_transaksi_2);
        status_desain_1 = findViewById(R.id.status_desain_pemesan_pembayaran_transaksi_2);
        status_satu_1 = findViewById(R.id.status_satu_pemesan_pembayaran_transaksi_2);
        status_dua_1 = findViewById(R.id.status_dua_pemesan_pembayaran_transaksi_2);
        status_tiga_1 = findViewById(R.id.status_tiga_pemesan_pembayaran_transaksi_2);
        status_empat_1 = findViewById(R.id.status_empat_pemesan_pembayaran_transaksi_2);
        total_satu_1 = findViewById(R.id.total_satu_pemesan_pembayaran_transaksi_2);
        total_dua_1 = findViewById(R.id.total_dua_pemesan_pembayaran_transaksi_2);
        total_tiga_1 = findViewById(R.id.total_tiga_pemesan_pembayaran_transaksi_2);
        total_empat_1 = findViewById(R.id.total_empat_pemesan_pembayaran_transaksi_2);
        tgl_satu = findViewById(R.id.tgl_satu_pembayaran_transaksi_2);
        tgl_dua = findViewById(R.id.tgl_dua_pembayaran_transaksi_2);
        tgl_tiga = findViewById(R.id.tgl_tiga_pembayaran_transaksi_2);
        tgl_empat = findViewById(R.id.tgl_empat_pembayaran_transaksi_2);
        tgl_desain_1 = findViewById(R.id.tgl_desain_pembayaran_transaksi_2);
        bukti_satu_1 = findViewById(R.id.bukti_satu_pemesan_pembayaran_transaksi_2);
        bukti_dua_1 = findViewById(R.id.bukti_dua_pemesan_pembayaran_transaksi_2);
        bukti_tiga_1 = findViewById(R.id.bukti_tiga_pemesan_pembayaran_transaksi_2);
        bukti_empat_1 = findViewById(R.id.bukti_empat_pemesan_pembayaran_transaksi_2);
        bukti_desain_1 = findViewById(R.id.bukti_desain_pemesan_pembayaran_transaksi_2);
        presentase_1 = findViewById(R.id.presentase_pemesan_pembayaran_transaksi_2);
        FileName = findViewById(R.id.fileName);
        upload = findViewById(R.id.upload_pembayaran_transaksi_2);
        kirim = findViewById(R.id.kirim_activity_transaksi_2);
        back = findViewById(R.id.back_activity_data_transaksi_2);

        /*
         * Passing data from last activity
         */
        final String id = getIntent().getExtras().getString("id");
        final String nomor_kontrak = getIntent().getExtras().getString("nomor_kontrak");
        final String nama_pemesan = getIntent().getExtras().getString("nama_pemesan");
        final String email = getIntent().getExtras().getString("email");
        final String alamat = getIntent().getExtras().getString("alamat");
        final String no_telp = getIntent().getExtras().getString("no_telp");
        final String biaya_desain = getIntent().getExtras().getString("biaya_desain");
        final String biaya_konstruksi = getIntent().getExtras().getString("biaya_konstruksi");
        final String no_rekening = getIntent().getExtras().getString("no_rekening");
        final String status_desain = getIntent().getExtras().getString("status_desain");
        final String status_satu = getIntent().getExtras().getString("status_satu");
        final String status_dua = getIntent().getExtras().getString("status_dua");
        final String status_tiga = getIntent().getExtras().getString("status_tiga");
        final String status_empat = getIntent().getExtras().getString("status_empat");
        final String total_satu = getIntent().getExtras().getString("total_satu");
        final String total_dua = getIntent().getExtras().getString("total_dua");
        final String total_tiga = getIntent().getExtras().getString("total_tiga");
        final String total_empat = getIntent().getExtras().getString("total_empat");
        final String tgl_1 = getIntent().getExtras().getString("tgl_input_satu");
        final String tgl_2 = getIntent().getExtras().getString("tgl_input_dua");
        final String tgl_3 = getIntent().getExtras().getString("tgl_input_tiga");
        final String tgl_4 = getIntent().getExtras().getString("tgl_input_empat");
        final String tgl_desain = getIntent().getExtras().getString("tgl_input_desain");
        final String bukti_satu = getIntent().getExtras().getString("bukti_satu");
        final String bukti_dua = getIntent().getExtras().getString("bukti_dua");
        final String bukti_tiga = getIntent().getExtras().getString("bukti_tiga");
        final String bukti_empat = getIntent().getExtras().getString("bukti_empat");
        final String bukti_desain = getIntent().getExtras().getString("bukti_desain");
        final String presentase = getIntent().getExtras().getString("presentase");

        /*
         * TextView Initializations
         */
        id_1.setText(id);
        nomor_kontrak_1.setText(nomor_kontrak);
        nama_pemesan_1.setText(nama_pemesan);
        email_1.setText(email);
        alamat_1.setText(alamat);
        no_telp_1.setText(no_telp);
        biaya_konstruksi_1.setText(biaya_konstruksi);
        biaya_desain_1.setText(biaya_desain);
        Nomor_Rekening.setText(no_rekening);
        status_desain_1.setText(status_desain);
        status_satu_1.setText(status_satu);
        status_dua_1.setText(status_dua);
        status_tiga_1.setText(status_tiga);
        status_empat_1.setText(status_empat);
        total_satu_1.setText(total_satu);
        total_dua_1.setText(total_dua);
        total_tiga_1.setText(total_tiga);
        total_empat_1.setText(total_empat);
        tgl_satu.setText(tgl_1);
        tgl_dua.setText(tgl_2);
        tgl_tiga.setText(tgl_3);
        tgl_empat.setText(tgl_4);
        tgl_desain_1.setText(tgl_desain);
        bukti_satu_1.setText(bukti_satu);
        bukti_dua_1.setText(bukti_dua);
        bukti_tiga_1.setText(bukti_tiga);
        bukti_empat_1.setText(bukti_empat);
        bukti_desain_1.setText(bukti_desain);
        presentase_1.setText(presentase);

        //Requesting storage permission
        requestStoragePermission();

        tampil_file();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(activity_data_transaksi_2.this, "Menu Pembayaran", "Memproses...", true);
                if (selectedFilePath != null) {
                    kirim_dialog();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
                            uploadFile(selectedFilePath);
                        }
                    }).start();
                } else {
                    Toast.makeText(activity_data_transaksi_2.this, "Anda Belum Menggungah Berkas", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_transaksi_2.this, activity_data_transaksi.class);
                intent.putExtra("id", id);
                intent.putExtra("nomor_kontrak", nomor_kontrak);
                intent.putExtra("nama_pemesan", nama_pemesan);
                intent.putExtra("email", email);
                intent.putExtra("alamat", alamat);
                intent.putExtra("no_telp", no_telp);
                intent.putExtra("biaya_desain", biaya_desain);
                intent.putExtra("biaya_konstruksi", biaya_konstruksi);
                intent.putExtra("no_rekening", no_rekening);
                intent.putExtra("status_desain", status_desain);
                intent.putExtra("status_satu", status_satu);
                intent.putExtra("status_dua", status_dua);
                intent.putExtra("status_tiga", status_tiga);
                intent.putExtra("status_empat", status_empat);
                intent.putExtra("total_satu", total_satu);
                intent.putExtra("total_dua", total_dua);
                intent.putExtra("total_tiga", total_tiga);
                intent.putExtra("total_empat", total_empat);
                intent.putExtra("tgl_input_satu", tgl_1);
                intent.putExtra("tgl_input_dua", tgl_2);
                intent.putExtra("tgl_input_tiga", tgl_3);
                intent.putExtra("tgl_input_empat", tgl_4);
                intent.putExtra("tgl_input_desain", tgl_desain);
                intent.putExtra("bukti_satu", bukti_satu);
                intent.putExtra("bukti_dua", bukti_dua);
                intent.putExtra("bukti_tiga", bukti_tiga);
                intent.putExtra("bukti_empat", bukti_empat);
                intent.putExtra("bukti_desain", bukti_desain);
                intent.putExtra("presentase", presentase);
                startActivity(intent);
            }
        });
    }

    private void tampil_file(){
        if (selectedFilePath != null) {
            String filecheck = selectedFilePath;
            String document = filecheck.substring(filecheck.lastIndexOf("/")+1);
            FileName.setText(document);
            FileName.setVisibility(View.VISIBLE);
        }
    }

    //android upload file to server
    public int uploadFile(final String selectedFilePath) {
        final TextView FileName = findViewById(R.id.fileName);
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];
        /*
         * I should regex filename string to make it don't have any whitespace
         * before sending to server, to prevent any error that cause from whitespace
         * if user download it.
         *
         * However, i need to make same regexed file name and filepath that'll push it to db
         * filepath should get regex too when it want to push to db
         */
        String regex = fileName.replaceAll("\\s","");

        if (!selectedFile.isFile()) {
            dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FileName.setText("Berkas tidak tersedia: " + selectedFilePath);
                }
            });
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", regex);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + regex + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FileName.setText(fileName);
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity_data_transaksi_2.this, "Berkas Tidak Di Temukan", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(activity_data_transaksi_2.this, "Kesalahan Alamat Website", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity_data_transaksi_2.this, "Tidak Dapat Membaca File", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            return serverResponseCode;
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Berkas"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final TextView FileName = findViewById(R.id.fileName);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }

                Uri selectedFileUri = data.getData();
                selectedFilePath = file_path.getPath(this, selectedFileUri);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {

                    String[] parts = selectedFilePath.split("/");
                    final String fileName = parts[parts.length - 1];
                    String regex = fileName.replaceAll("\\s","");
                    FileName.setText(regex);

                    File imgFile = new  File(selectedFilePath);
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        ImageView myImage = (ImageView) findViewById(R.id.upload_pembayaran_transaksi_2);
                        myImage.setImageBitmap(myBitmap);
                    }
                } else {
                    Toast.makeText(this, "Tidak Dapat Mengunggah Ke Website Mandorin", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void kirim_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Kirim Data Pembayaran");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin memproses pembayaran?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Proses",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        try {
                            if (status_dua_1.getText().toString().equalsIgnoreCase("Menunggu")) {
                                if (presentase_1.getText().toString().equalsIgnoreCase("80")) {
                                    createdata();
                                }
                            } else  if (status_tiga_1.getText().toString().equalsIgnoreCase("Menunggu")) {
                                if (presentase_1.getText().toString().equalsIgnoreCase("95")) {
                                    createdata_2();
                                }
                            }
                            Intent intent = new Intent(activity_data_transaksi_2.this, activity_data_pembayaran_konfirmasi.class);
                            startActivity(intent);
                        } catch (IllegalArgumentException e) {
                            Toast.makeText(activity_data_transaksi_2.this, "Proses Gagal!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void createdata() {
        String id_2 = id_1.getText().toString();
        String adress = "http://mandorin.site/mandorin/php/user/new/update_data_pembayaran.php?id=" + id_2;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, adress,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        //Toast.makeText(activity_data_transaksi_2.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_data_transaksi_2.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {

                String id = id_1.getText().toString() ;
                String nomor_kontrak = nomor_kontrak_1.getText().toString();
                String nama_pemesan = nama_pemesan_1.getText().toString();
                String email = email_1.getText().toString();
                String alamat = alamat_1.getText().toString();
                String no_telp = no_telp_1.getText().toString();
                String biaya_desain = biaya_desain_1.getText().toString();
                String biaya_konstruksi = biaya_konstruksi_1.getText().toString();
                String no_rekening = Nomor_Rekening.getText().toString();
                String status_desain = status_desain_1.getText().toString();
                String status = status_satu_1.getText().toString();
                String status_2 = "Memproses";
                String status_3 = status_tiga_1.getText().toString();
                String status_4 = status_empat_1.getText().toString();
                String total = total_satu_1.getText().toString();
                String total_2 = total_dua_1.getText().toString();
                String total_3 = total_tiga_1.getText().toString();
                String total_4 = total_empat_1.getText().toString();
                String tgl_1 = tgl_satu.getText().toString();
                String tgl_2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String tgl_3 = tgl_tiga.getText().toString();
                String tgl_4 = tgl_empat.getText().toString();
                String tgl_desain = tgl_desain_1.getText().toString();

                String substring = bukti_satu_1.getText().toString();
                String desain = substring.substring(substring.lastIndexOf("/")+1);
                String bukti_satu = desain.replaceAll("\\s", "");

                String[] parts = selectedFilePath.split("/");
                final String fileName = parts[parts.length - 1];
                String regex = fileName.replaceAll("\\s","");

                String substring_3 = bukti_tiga_1.getText().toString();
                String desain_3 = substring_3.substring(substring.lastIndexOf("/")+1);
                String bukti_tiga = desain_3.replaceAll("\\s", "");

                String substring_4 = bukti_empat_1.getText().toString();
                String desain_4 = substring_4.substring(substring.lastIndexOf("/")+1);
                String bukti_empat = desain_4.replaceAll("\\s", "");

                String bukti_desain = bukti_desain_1.getText().toString();
                String presentase = presentase_1.getText().toString();

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", id);
                params.put("nomor_kontrak", nomor_kontrak);
                params.put("nama_pemesan", nama_pemesan);
                params.put("email", email);
                params.put("alamat", alamat);
                params.put("no_telp", no_telp);
                params.put("biaya_desain", biaya_desain);
                params.put("biaya_konstruksi", biaya_konstruksi);
                params.put("no_rekening", no_rekening);
                params.put("status_desain", status_desain);
                params.put("status_satu", status);
                params.put("status_dua", status_2);
                params.put("status_tiga", status_3);
                params.put("status_empat", status_4);
                params.put("total_satu", total);
                params.put("total_dua", total_2);
                params.put("total_tiga", total_3);
                params.put("total_empat", total_4);
                params.put("tgl_input_satu", tgl_1);
                params.put("tgl_input_dua", tgl_2);
                params.put("tgl_input_tiga", tgl_3);
                params.put("tgl_input_empat", tgl_4);
                params.put("tgl_input_desain", tgl_desain);
                params.put("bukti_satu", bukti_satu);
                params.put("bukti_dua", regex);
                params.put("bukti_tiga", bukti_tiga);
                params.put("bukti_empat", bukti_empat);
                params.put("bukti_desain", bukti_desain);
                params.put("presentase", presentase);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_data_transaksi_2.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void createdata_2() {
        String id_2 = id_1.getText().toString();
        String adress = "http://mandorin.site/mandorin/php/user/new/update_data_pembayaran.php?id=" + id_2;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, adress,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        //Toast.makeText(activity_data_transaksi_2.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_data_transaksi_2.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {

                String id = id_1.getText().toString() ;
                String nomor_kontrak = nomor_kontrak_1.getText().toString();
                String nama_pemesan = nama_pemesan_1.getText().toString();
                String email = email_1.getText().toString();
                String alamat = alamat_1.getText().toString();
                String no_telp = no_telp_1.getText().toString();
                String biaya_desain = biaya_desain_1.getText().toString();
                String biaya_konstruksi = biaya_konstruksi_1.getText().toString();
                String no_rekening = Nomor_Rekening.getText().toString();
                String status_desain = status_desain_1.getText().toString();
                String status = status_satu_1.getText().toString();
                String status_2 = status_dua_1.getText().toString();
                String status_3 = "Memproses";
                String status_4 = status_empat_1.getText().toString();
                String total = total_satu_1.getText().toString();
                String total_2 = total_dua_1.getText().toString();
                String total_3 = total_tiga_1.getText().toString();
                String total_4 = total_empat_1.getText().toString();
                String tgl_1 = tgl_satu.getText().toString();
                String tgl_2 = tgl_dua.getText().toString();
                String tgl_3 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String tgl_4 = tgl_empat.getText().toString();
                String tgl_desain = tgl_desain_1.getText().toString();

                String substring = bukti_satu_1.getText().toString();
                String desain = substring.substring(substring.lastIndexOf("/")+1);
                String bukti_satu = desain.replaceAll("\\s", "");

                String substring_2 = bukti_dua_1.getText().toString();
                String desain_2 = substring_2.substring(substring.lastIndexOf("/")+1);
                String bukti_dua = desain_2.replaceAll("\\s", "");

                String[] parts = selectedFilePath.split("/");
                final String fileName = parts[parts.length - 1];
                String regex = fileName.replaceAll("\\s","");

                String substring_4 = bukti_empat_1.getText().toString();
                String desain_4 = substring_4.substring(substring.lastIndexOf("/")+1);
                String bukti_empat = desain_4.replaceAll("\\s", "");

                String bukti_desain = bukti_desain_1.getText().toString();
                String presentase = presentase_1.getText().toString();

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", id);
                params.put("nomor_kontrak", nomor_kontrak);
                params.put("nama_pemesan", nama_pemesan);
                params.put("email", email);
                params.put("alamat", alamat);
                params.put("no_telp", no_telp);
                params.put("biaya_desain", biaya_desain);
                params.put("biaya_konstruksi", biaya_konstruksi);
                params.put("no_rekening", no_rekening);
                params.put("status_desain", status_desain);
                params.put("status_satu", status);
                params.put("status_dua", status_2);
                params.put("status_tiga", status_3);
                params.put("status_empat", status_4);
                params.put("total_satu", total);
                params.put("total_dua", total_2);
                params.put("total_tiga", total_3);
                params.put("total_empat", total_4);
                params.put("tgl_input_satu", tgl_1);
                params.put("tgl_input_dua", tgl_2);
                params.put("tgl_input_tiga", tgl_3);
                params.put("tgl_input_empat", tgl_4);
                params.put("tgl_input_desain", tgl_desain);
                params.put("bukti_satu", bukti_satu);
                params.put("bukti_dua", bukti_dua);
                params.put("bukti_tiga", regex);
                params.put("bukti_empat", bukti_empat);
                params.put("bukti_desain", bukti_desain);
                params.put("presentase", presentase);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_data_transaksi_2.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}