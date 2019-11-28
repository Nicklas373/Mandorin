package id.hana.mandorin;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class activity_status_renovasi extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView Id, nomor_kontrak, alamat_pekerjaan, total_biaya, presentase, estimasi, data_pemesan, rekap_data, surat_kontrak, meta_data_pemesan, meta_rekap_data, meta_surat_kontrak;
    private Button btn_data_pemesan , btn_rekap_data , btn_surat_kontrak, komplain;
    private CardView back;

    private static final String TAG = "activity_status_renovasi";
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
        setContentView(R.layout.activity_status_renovasi);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        Id = findViewById(R.id.user_input_id);
        nomor_kontrak = findViewById(R.id.user_input_nomor_kontrak);
        total_biaya = findViewById(R.id.user_input_total_biaya);
        alamat_pekerjaan = findViewById(R.id.user_input_alamat);
        presentase = findViewById(R.id.user_input_presentasi);
        estimasi = findViewById(R.id.user_input_estimasi);
        data_pemesan = findViewById(R.id.text_link_data_pemesan);
        rekap_data = findViewById(R.id.text_link_rekap_data);
        surat_kontrak = findViewById(R.id.text_link_surat_kontrak);
        meta_data_pemesan = findViewById(R.id.text_link_meta_data_pemesan);
        meta_rekap_data = findViewById(R.id.text_link_meta_rekap_data);
        meta_surat_kontrak = findViewById(R.id.text_link_meta_surat_kontrak);
        back = findViewById(R.id.back_activity_status_renovasi);
        btn_data_pemesan = findViewById(R.id.button_data_pemesan);
        btn_rekap_data = findViewById(R.id.button_rekap_data);
        btn_surat_kontrak = findViewById(R.id.button_surat_kontrak);
        komplain = findViewById(R.id.button_komplain);

        /*
         * Passing data from last activity
         */
        String id_1 = getIntent().getExtras().getString("id");
        final String nomor_kontrak_1 = getIntent().getExtras().getString("nomor_kontrak");
        String nama_pemesan_1 = getIntent().getExtras().getString("nama_pemesan");
        String email_1 = getIntent().getExtras().getString("email");
        String no_telp_1 = getIntent().getExtras().getString("alamat");
        final String alamat_pekerjaan_1 = getIntent().getExtras().getString("alamat_pekerjaan");
        String status_pekerjaan_1 = getIntent().getExtras().getString("status_pekerjaan");
        String total_biaya_1 = getIntent().getExtras().getString("total_biaya");
        String presentase_1 = getIntent().getExtras().getString("presentase");
        String estimasi_waktu_1 = getIntent().getExtras().getString("estimasi_waktu");
        final String data_pemesan_1 = getIntent().getExtras().getString("data_pesanan");
        final String link_data_pemesan_1 = ("https://www.mandorin.site/mandorin/data_pemesan/renovasi/" + data_pemesan_1);
        final String rekap_data_1 = getIntent().getExtras().getString("rekap_data");
        final String link_rekap_data_1 = ("https://www.mandorin.site/mandorin/data_pemesan/renovasi/" + rekap_data_1);
        final String surat_kontrak_1 = getIntent().getExtras().getString("surat_kontrak");
        final String link_surat_kontrak_1 = ("https://www.mandorin.site/mandorin/data_pemesan/renovasi/" + surat_kontrak_1);

        /*
         * TextView Initializations
         */
        Id.setText(nama_pemesan_1);
        nomor_kontrak.setText(nomor_kontrak_1);
        int result = Integer.parseInt(total_biaya_1);
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = result;
        String formattedNumber = formatter.format(myNumber);
        total_biaya.setText("Rp." + formattedNumber);
        alamat_pekerjaan.setText(alamat_pekerjaan_1);
        presentase.setText(presentase_1 + "%");
        estimasi.setText(estimasi_waktu_1);
        data_pemesan.setText(link_data_pemesan_1);
        rekap_data.setText(link_rekap_data_1);
        surat_kontrak.setText(link_surat_kontrak_1);
        meta_data_pemesan.setText(data_pemesan_1);
        meta_rekap_data.setText(rekap_data_1);
        meta_surat_kontrak.setText(surat_kontrak_1);

        /*
         * Check if user downloaded document is exists or not from the beginning
         */

        cek_data_pemesan();
        cek_surat_kontrak();
        cek_rekap_data();

        ActivityCompat.requestPermissions(activity_status_renovasi.this, PERMISSIONS, 112);

        btn_data_pemesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = meta_data_pemesan.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    btn_data_pemesan.setText("Lihat Berkas");
                    Toast.makeText(activity_status_renovasi.this, "Berkas anda ada di download/" + file_data, Toast.LENGTH_LONG).show();
                    openFolder_data_pemesan();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_status_renovasi.this, PERMISSIONS)) {
                            Toast.makeText(activity_status_renovasi.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                            String url = data_pemesan.getText().toString();
                            new DownloadFile().execute(url, data_pemesan_1);
                            Toast.makeText(activity_status_renovasi.this, "Data pesanan anda berhasil di download", Toast.LENGTH_LONG).show();
                            btn_data_pemesan.setText("Lihat Berkas");
                            Toast.makeText(activity_status_renovasi.this, "Berkas anda ada di download/" + file_data, Toast.LENGTH_LONG).show();
                            openFolder_data_pemesan();
                        }
                    } else {
                        Toast.makeText(activity_status_renovasi.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btn_rekap_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = meta_rekap_data.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    btn_rekap_data.setText("Lihat Berkas");
                    Toast.makeText(activity_status_renovasi.this, "Berkas anda ada di download/" + file_data, Toast.LENGTH_LONG).show();
                    openFolder_rekap_data();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_status_renovasi.this, PERMISSIONS)) {
                            Toast.makeText(activity_status_renovasi.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                            String url = rekap_data.getText().toString();
                            new DownloadFile().execute(url, rekap_data_1);
                            Toast.makeText(activity_status_renovasi.this, "Data rekap data anda berhasil di download", Toast.LENGTH_LONG).show();
                            btn_rekap_data.setText("Lihat Berkas");
                            Toast.makeText(activity_status_renovasi.this, "Berkas anda ada di download/" + file_data, Toast.LENGTH_LONG).show();
                            openFolder_rekap_data();
                        }
                    } else {
                        Toast.makeText(activity_status_renovasi.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btn_surat_kontrak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = meta_surat_kontrak.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    btn_surat_kontrak.setText("Lihat Berkas");
                    Toast.makeText(activity_status_renovasi.this, "Berkas anda adalah" + file_data, Toast.LENGTH_LONG).show();
                    openFolder_surat_kontrak();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_status_renovasi.this, PERMISSIONS)) {
                            Toast.makeText(activity_status_renovasi.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                            String url = surat_kontrak.getText().toString();
                            new DownloadFile().execute(url, surat_kontrak_1);
                            Toast.makeText(activity_status_renovasi.this, "Data surat kontrak anda berhasil di download", Toast.LENGTH_LONG).show();
                            btn_surat_kontrak.setText("Lihat Berkas");
                            Toast.makeText(activity_status_renovasi.this, "Berkas anda adalah" + file_data, Toast.LENGTH_LONG).show();
                            openFolder_surat_kontrak();
                        }
                    } else {
                        Toast.makeText(activity_status_renovasi.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_status_renovasi.this, activity_data_status_renovasi.class);
                startActivity(intent);
            }
        });

        komplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_status_renovasi.this, activity_komplain_bangun.class);
                intent.putExtra("nomor_kontrak", nomor_kontrak_1);
                intent.putExtra("alamat", alamat_pekerjaan_1);
                intent.putExtra("status", "renovasi");
                startActivity(intent);
            }
        });
    }

    private void cek_data_pemesan(){
        String file_data = meta_data_pemesan.getText().toString();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
        if (file.exists()) {
            btn_data_pemesan.setText("Lihat Berkas");
        }
    }

    private void cek_surat_kontrak(){
        String file_data = meta_surat_kontrak.getText().toString();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
        if (file.exists()) {
            btn_surat_kontrak.setText("Lihat Berkas");
        }
    }

    private void cek_rekap_data(){
        String file_data = meta_rekap_data.getText().toString();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
        if (file.exists()) {
            btn_rekap_data.setText("Lihat Berkas");
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

    public void openFolder_data_pemesan(){
        try {
            String file_data = meta_data_pemesan.getText().toString();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("Application not found", e.getMessage());
            String file_data = meta_data_pemesan.getText().toString();
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

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}