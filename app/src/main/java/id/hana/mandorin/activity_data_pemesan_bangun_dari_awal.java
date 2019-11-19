package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
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

public class activity_data_pemesan_bangun_dari_awal extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView Nama, Nik, Email, Status, Luas_tanah, Alamat, Url, Desain;
    private Button Download;
    private CardView back;

    private static final String TAG = "activity_data_pemesan_bangun_dari_awal";
    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pemesan_bangun_dari_awal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        Nama = findViewById(R.id.user_input_nama);
        Nik = findViewById(R.id.user_input_nik);
        Email = findViewById(R.id.user_input_email);
        Status = findViewById(R.id.user_input_status);
        Luas_tanah = findViewById(R.id.user_input_luas_tanah);
        Alamat = findViewById(R.id.user_input_alamat);
        back = findViewById(R.id.back_activity_data_bangun_dari_awal);
        Download = findViewById(R.id.button_download);
        Desain = findViewById(R.id.desain);
        Url = findViewById(R.id.url);

        /*
         * Passing data from last activity
         */
        String id = getIntent().getExtras().getString("id");
        String nik = getIntent().getExtras().getString("nik");
        String nama = getIntent().getExtras().getString("nama");
        String email = getIntent().getExtras().getString("email");
        String alamat = getIntent().getExtras().getString("alamat");
        String luas_tanah = getIntent().getExtras().getString("luas_tanah");
        final String desain_rumah = getIntent().getExtras().getString("desain_rumah");
        final String link = ("https://www.mandorin.site/mandorin/uploads/" + desain_rumah);
        String status = getIntent().getExtras().getString("status");

        /*
         * TextView Initializations
         */
        Nama.setText(nama);
        Nik.setText(nik);
        Email.setText(email);
        Status.setText(status);
        Luas_tanah.setText(luas_tanah);
        Alamat.setText(alamat);
        Desain.setText(desain_rumah);
        Url.setText(link);

        /*
         * Set Scrolling On TextView
         */
        Alamat.setMovementMethod(new ScrollingMovementMethod());

        /*
         * Check if user downloaded document is exists or not from the beginning
         */
        cek_data();

        ActivityCompat.requestPermissions(activity_data_pemesan_bangun_dari_awal.this, PERMISSIONS, 112);

        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = Desain.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    Download.setText("Lihat Berkas");
                    Toast.makeText(activity_data_pemesan_bangun_dari_awal.this, "Berkas anda adalah" + file_data, Toast.LENGTH_LONG).show();
                    openFolder();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_data_pemesan_bangun_dari_awal.this, PERMISSIONS)) {
                            Toast.makeText(activity_data_pemesan_bangun_dari_awal.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                            String url = Url.getText().toString();
                            new DownloadFile().execute(url, desain_rumah);
                            Toast.makeText(activity_data_pemesan_bangun_dari_awal.this, "Desain rumah anda berhasil di download", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(activity_data_pemesan_bangun_dari_awal.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_pemesan_bangun_dari_awal.this, activity_pemesan_bangun_dari_awal.class);
                startActivity(intent);
            }
        });
    }

    private void cek_data(){
        String file_data = Desain.getText().toString();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
        if (file.exists()) {
            Download.setText("Lihat Berkas");
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

    public void openFolder(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + "/Download/");
        intent.setDataAndType(uri, "application/pdf");
        startActivity(Intent.createChooser(intent, "Open folder"));
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}