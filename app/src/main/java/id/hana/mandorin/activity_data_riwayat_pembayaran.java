package id.hana.mandorin;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
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
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
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

public class activity_data_riwayat_pembayaran extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView Nama, Nomor_Kontrak, Alamat, Data, Orig_Data, Status;
    private Button Download;
    private CardView back;
    ProgressDialog dialog;

    private static final String TAG = "activity_data_riwayat_pembayaran";
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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_data_riwayat_pembayaran);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        Nama = findViewById(R.id.user_input_nama_pemesan_riwayat_pembayaran);
        Nomor_Kontrak = findViewById(R.id.user_input_nomor_kontrak_riwayat_pembayaran);
        Alamat = findViewById(R.id.user_input_alamat_riwayat_pembayaran);
        back = findViewById(R.id.back_activity_data_riwayat_pembayaran);
        Download = findViewById(R.id.button_download_riwayat_pembayaran);
        Data = findViewById(R.id.link_riwayat_pembayaran);
        Orig_Data = findViewById(R.id.data_riwayat_pembayaran);
        Status = findViewById(R.id.user_input_status_riwayat_pembayaran);

        /*
         * Passing data from last activity
         */
        String id = getIntent().getExtras().getString("id");
        String nik = getIntent().getExtras().getString("nomor_kontrak");
        String nama = getIntent().getExtras().getString("nama_pemesan");
        String email = getIntent().getExtras().getString("email");
        String alamat = getIntent().getExtras().getString("alamat");
        final String data= getIntent().getExtras().getString("data");
        String status = getIntent().getExtras().getString("status");
        /*
         * I should set status text before set another text, it for declare correct link
         * to download PDF file for each status, during table has been merged into one table
         * 'tb_riwayat_pembayaran' due efficiency reason.
         */
        Status.setText(status);
        if (Status.getText().toString().equalsIgnoreCase("Bangunan Baru")){
            final String link = ("https://www.mandorin.site/mandorin/riwayat_pembayaran/bangunan_baru/" + data);
            Data.setText(link);
        } else if (Status.getText().toString().equalsIgnoreCase("Renovasi")) {
            final String link = ("https://www.mandorin.site/mandorin/riwayat_pembayaran/renovasi/" + data);
            Data.setText(link);
        }

        /*
         * TextView Initializations
         */
        Nama.setText(nama);
        Nomor_Kontrak.setText(nik);
        Alamat.setText(alamat);
        Orig_Data.setText(data);

        /*
         * Set Scrolling On TextView
         */
        Alamat.setMovementMethod(new ScrollingMovementMethod());

        /*
         * Check if user downloaded document is exists or not from the beginning
         */
        cek_data();

        ActivityCompat.requestPermissions(activity_data_riwayat_pembayaran.this, PERMISSIONS, 112);

        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = Orig_Data.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    Download.setText("Lihat Berkas");
                    Toast.makeText(activity_data_riwayat_pembayaran.this, "Berkas anda adalah " + file_data, Toast.LENGTH_LONG).show();
                    openFolder();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_data_riwayat_pembayaran.this, PERMISSIONS)) {
                            Toast.makeText(activity_data_riwayat_pembayaran.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                            String url = Data.getText().toString();
                            new activity_data_riwayat_pembayaran.DownloadFile().execute(url, data);
                            Toast.makeText(activity_data_riwayat_pembayaran.this, "Berkas anda adalah " + file_data, Toast.LENGTH_LONG).show();
                            openFolder();
                        }
                    } else {
                        Toast.makeText(activity_data_riwayat_pembayaran.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_riwayat_pembayaran.this, activity_riwayat_pembayaran.class);
                startActivity(intent);
            }
        });
    }

    private void cek_data(){
        String file_data = Orig_Data.getText().toString();
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
        try {
            String file_data = Orig_Data.getText().toString();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("Application not found", e.getMessage());
            String file_data = Orig_Data.getText().toString();
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