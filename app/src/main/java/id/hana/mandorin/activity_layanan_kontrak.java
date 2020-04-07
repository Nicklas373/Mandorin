package id.hana.mandorin;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class activity_layanan_kontrak extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView Id, nomor_kontrak, alamat_pekerjaan, estimasi, presentase, data_desain, rekap_data, surat_kontrak, meta_data_desain, meta_rekap_data, meta_surat_kontrak;
    private ImageView img_data_desain , img_rekap_data , img_surat_kontrak, img_presentase, komplain;
    private CardView back;

    private static final String TAG = "activity_layanan_kontrak";
    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_kontrak);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        Id = findViewById(R.id.user_input_id_layanan_kontrak);
        nomor_kontrak = findViewById(R.id.user_input_nomor_kontrak_layanan_kontrak);
        alamat_pekerjaan = findViewById(R.id.user_input_alamat_layanan_kontrak);
        img_presentase = findViewById(R.id.img_presentase);
        presentase = findViewById(R.id.dummy_presentase);
        estimasi = findViewById(R.id.user_input_estimasi_layanan_kontrak);
        data_desain = findViewById(R.id.text_link_data_desain);
        rekap_data = findViewById(R.id.text_link_rekap_data);
        surat_kontrak = findViewById(R.id.text_link_surat_kontrak);
        meta_data_desain = findViewById(R.id.text_link_meta_data_desain);
        meta_rekap_data = findViewById(R.id.text_link_meta_rekap_data);
        meta_surat_kontrak = findViewById(R.id.text_link_meta_surat_kontrak);
        back = findViewById(R.id.back_activity_status_layanan_kontrak);
        komplain = findViewById(R.id.ajukan_komplain);
        img_data_desain = findViewById(R.id.image_data_desain_layanan_kontrak);
        img_rekap_data = findViewById(R.id.image_rekap_data_layanan_kontrak);
        img_surat_kontrak = findViewById(R.id.image_surat_kontrak_layanan_kontrak);

        /*
         * Passing data from last activity
         */
        final String id_1 = getIntent().getExtras().getString("id");
        final String nomor_kontrak_1 = getIntent().getExtras().getString("nomor_kontrak");
        final String nama_pemesan_1 = getIntent().getExtras().getString("nama_pemesan");
        final String email_1 = getIntent().getExtras().getString("email");
        final String no_telp_1 = getIntent().getExtras().getString("alamat");
        final String alamat_pekerjaan_1 = getIntent().getExtras().getString("alamat_pekerjaan");
        final String status_pekerjaan_1 = getIntent().getExtras().getString("status_pekerjaan");
        final String presentase_1 = getIntent().getExtras().getString("presentase");
        final String waktu_awal_1 = getIntent().getExtras().getString("waktu_mulai");
        final String waktu_akhir_1 = getIntent().getExtras().getString("waktu_akhir");
        final String data_desain_1 = getIntent().getExtras().getString("data_desain");
        final String role_kontrak_1 = getIntent().getExtras().getString("role_kontrak");
        final String role_komplain_1 = getIntent().getExtras().getString("role_komplain");
        final String link_data_desain_1 = ("http://www.mandorin.site/mandorin/data_kontrak/" + data_desain_1);
        final String rekap_data_1 = getIntent().getExtras().getString("data_rekap");
        final String link_rekap_data_1 = ("http://www.mandorin.site/mandorin/data_kontrak/" + rekap_data_1);
        final String surat_kontrak_1 = getIntent().getExtras().getString("surat_kontrak");
        final String link_surat_kontrak_1 = ("http://www.mandorin.site/mandorin/data_kontrak/" + surat_kontrak_1);

        /*
         * TextView Initializations
         */
        Id.setText(nama_pemesan_1);
        nomor_kontrak.setText(nomor_kontrak_1);
        alamat_pekerjaan.setText(alamat_pekerjaan_1);
        presentase.setText(presentase_1);
        if (presentase.getText().toString().equalsIgnoreCase("0")){
            img_presentase.setImageDrawable(getResources().getDrawable(R.drawable.status_layanan_1, getApplicationContext().getTheme()));
        } else if (presentase.getText().toString().equalsIgnoreCase("50")) {
            img_presentase.setImageDrawable(getResources().getDrawable(R.drawable.status_layanan_2, getApplicationContext().getTheme()));
        } else if (presentase.getText().toString().equalsIgnoreCase("80")) {
            img_presentase.setImageDrawable(getResources().getDrawable(R.drawable.status_layanan_3, getApplicationContext().getTheme()));
        } else if (presentase.getText().toString().equalsIgnoreCase("95")) {
            img_presentase.setImageDrawable(getResources().getDrawable(R.drawable.status_layanan_4, getApplicationContext().getTheme()));
        }else if (presentase.getText().toString().equalsIgnoreCase("100")) {
            img_presentase.setImageDrawable(getResources().getDrawable(R.drawable.status_layanan_5, getApplicationContext().getTheme()));
        }
        estimasi.setText(waktu_awal_1 + " sd " + waktu_akhir_1);
        data_desain.setText(link_data_desain_1);
        rekap_data.setText(link_rekap_data_1);
        surat_kontrak.setText(link_surat_kontrak_1);
        meta_data_desain.setText(data_desain_1);
        meta_rekap_data.setText(rekap_data_1);
        meta_surat_kontrak.setText(surat_kontrak_1);

        /*
         * Check if user downloaded document is exists or not from the beginning
         */
        cek_data_desain();
        cek_surat_kontrak();
        cek_rekap_data();

        ActivityCompat.requestPermissions(activity_layanan_kontrak.this, PERMISSIONS, 112);

        img_data_desain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = meta_data_desain.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    img_data_desain.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                    Toast.makeText(activity_layanan_kontrak.this, "Berkas anda " + file_data, Toast.LENGTH_LONG).show();
                    openFolder_data_desain();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_layanan_kontrak.this, PERMISSIONS)) {
                            Toast.makeText(activity_layanan_kontrak.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                           send_dialog_data_desain();
                        }
                    } else {
                        Toast.makeText(activity_layanan_kontrak.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        img_rekap_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = meta_rekap_data.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    img_rekap_data.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                    Toast.makeText(activity_layanan_kontrak.this, "Berkas anda " + file_data, Toast.LENGTH_LONG).show();
                    openFolder_rekap_data();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_layanan_kontrak.this, PERMISSIONS)) {
                            Toast.makeText(activity_layanan_kontrak.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                           send_dialog_rekap_data();
                        }
                    } else {
                        Toast.makeText(activity_layanan_kontrak.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        img_surat_kontrak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = meta_surat_kontrak.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    img_surat_kontrak.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                    Toast.makeText(activity_layanan_kontrak.this, "Berkas anda " + file_data, Toast.LENGTH_LONG).show();
                    openFolder_surat_kontrak();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_layanan_kontrak.this, PERMISSIONS)) {
                            Toast.makeText(activity_layanan_kontrak.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                           send_dialog_surat_kontrak();
                        }
                    } else {
                        Toast.makeText(activity_layanan_kontrak.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_layanan_kontrak.this, activity_data_kontrak.class);
                startActivity(intent);
            }
        });

        komplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_layanan_kontrak.this, activity_komplain_bangun.class);
                intent.putExtra("id", id_1);
                intent.putExtra("nomor_kontrak", nomor_kontrak_1);
                intent.putExtra("alamat", alamat_pekerjaan_1);
                intent.putExtra("nama_pemesan", nama_pemesan_1);
                intent.putExtra("email", email_1);
                intent.putExtra("alamat_pekerjaan", alamat_pekerjaan_1);
                intent.putExtra("no_telp",no_telp_1);
                intent.putExtra("status_pekerjaan", status_pekerjaan_1);
                intent.putExtra("presentase", presentase_1);
                intent.putExtra("waktu_mulai", waktu_awal_1);
                intent.putExtra("waktu_akhir", waktu_akhir_1);
                intent.putExtra("data_desain", data_desain_1);
                intent.putExtra("data_rekap", rekap_data_1);
                intent.putExtra("surat_kontrak", surat_kontrak_1);
                intent.putExtra("role_kontrak", role_kontrak_1);
                intent.putExtra("role_komplain", role_komplain_1);
                startActivity(intent);
            }
        });
    }

    private void cek_data_desain(){
        String file_data = meta_data_desain.getText().toString();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
        if (file.exists()) {
            img_data_desain.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
        }
    }

    private void cek_surat_kontrak(){
        String file_data = meta_surat_kontrak.getText().toString();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
        if (file.exists()) {
            img_surat_kontrak.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
        }
    }

    private void cek_rekap_data(){
        String file_data = meta_rekap_data.getText().toString();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
        if (file.exists()) {
            img_rekap_data.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];
            String fileName = strings[1];
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            File pdfFile = new File(folder, fileName);

            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType)
                {
                    //
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType)
                {
                    //
                }
            }};

            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();

                }
                FileDownloader.downloadFile(fileUrl, pdfFile);
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void openFolder_data_desain(){
        try {
            String file_data = meta_data_desain.getText().toString();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("Application not found", e.getMessage());
            String file_data = meta_data_desain.getText().toString();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(Intent.createChooser(intent, "Open folder"));
        } catch (Exception e){
            e.printStackTrace();
            Log.d("Unknown error", e.getMessage());
        }
    }

    public void openFolder_surat_kontrak(){
        try {
            String file_data = meta_surat_kontrak.getText().toString();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("Application not found", e.getMessage());
            String file_data = meta_surat_kontrak.getText().toString();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(Intent.createChooser(intent, "Open folder"));
        } catch (Exception e){
            e.printStackTrace();
            Log.d("Unknown error", e.getMessage());
        }
    }

    public void openFolder_rekap_data(){
        try {
            String file_data = meta_rekap_data.getText().toString();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("Application not found", e.getMessage());
            String file_data = meta_rekap_data.getText().toString();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(Intent.createChooser(intent, "Open folder"));
        } catch (Exception e){
            e.printStackTrace();
            Log.d("Unknown error", e.getMessage());
        }
    }

    private void send_dialog_data_desain() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Data Desain Rumah");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin mendownload data desain rumah ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Download",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        final String data_desain_1 = getIntent().getExtras().getString("data_desain");
                        String url = data_desain.getText().toString();
                        String file_data = meta_data_desain.getText().toString();
                        new DownloadFile().execute(url, data_desain_1);
                        Toast.makeText(activity_layanan_kontrak.this, "Data pesanan anda berhasil di download", Toast.LENGTH_LONG).show();
                        img_data_desain.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                        Toast.makeText(activity_layanan_kontrak.this, "Berkas anda " + file_data, Toast.LENGTH_LONG).show();
                        openFolder_data_desain();
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

    private void send_dialog_rekap_data() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Data Rekap");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin mendownload data rekap ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Download",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        String url = rekap_data.getText().toString();
                        String file_data = meta_rekap_data.getText().toString();
                        final String rekap_data_1 = getIntent().getExtras().getString("data_rekap");
                        new DownloadFile().execute(url, rekap_data_1);
                        Toast.makeText(activity_layanan_kontrak.this, "Data rekap data anda berhasil di download", Toast.LENGTH_LONG).show();
                        img_rekap_data.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                        Toast.makeText(activity_layanan_kontrak.this, "Berkas anda " + file_data, Toast.LENGTH_LONG).show();
                        openFolder_rekap_data();
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

    private void send_dialog_surat_kontrak() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Data Surat Kontrak");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin mendownload data surat kontrak ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Download",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        final String surat_kontrak_1 = getIntent().getExtras().getString("surat_kontrak");
                        String file_data = meta_surat_kontrak.getText().toString();
                        String url = surat_kontrak.getText().toString();
                        new DownloadFile().execute(url, surat_kontrak_1);
                        Toast.makeText(activity_layanan_kontrak.this, "Data surat kontrak anda berhasil di download", Toast.LENGTH_LONG).show();
                        img_surat_kontrak.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                        Toast.makeText(activity_layanan_kontrak.this, "Berkas anda " + file_data, Toast.LENGTH_LONG).show();
                        openFolder_surat_kontrak();
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

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}