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

public class activity_data_riwayat_kontrak extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView Nama, Nomor_Kontrak, Alamat, Status, Data_Pemesan, Orig_Data_Pemesan, Rekap_Data, Orig_Rekap_Data, Desain_Rumah, Orig_Desain_Rumah, Surat_Kontrak, Orig_Surat_Kontrak;
    private ImageView btn_data_pemesan, btn_rekap_data, btn_surat_kontrak, btn_desain_rumah;
    private CardView back, cv_desain_rumah;
    ProgressDialog dialog;

    private static final String TAG = "activity_data_riwayat_kontrak";
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
        setContentView(R.layout.activity_data_riwayat_kontrak);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        Nama = findViewById(R.id.user_input_id_riwayat_kontrak);
        Nomor_Kontrak = findViewById(R.id.user_input_nama_pemesan);
        Alamat = findViewById(R.id.user_input_alamat_riwayat_kontrak);
        Status = findViewById(R.id.user_input_status_riwayat_kontrak);
        back = findViewById(R.id.back_activity_riwayat_kontrak);
        btn_data_pemesan = findViewById(R.id.image_data_pemesan_riwayat_kontrak);
        btn_rekap_data = findViewById(R.id.image_rekap_data_riwayat_kontrak);
        btn_surat_kontrak = findViewById(R.id.image_surat_kontrak_riwayat_kontrak);
        btn_desain_rumah = findViewById(R.id.image_desain_rumah_riwayat_kontrak);
        Data_Pemesan = findViewById(R.id.text_link_data_pemesan);
        Orig_Data_Pemesan = findViewById(R.id.text_link_raw_data_pemesan);
        Rekap_Data = findViewById(R.id.text_link_rekap_data);
        Orig_Rekap_Data = findViewById(R.id.text_link_raw_rekap_data);
        Surat_Kontrak = findViewById(R.id.text_link_surat_kontrak);
        Orig_Surat_Kontrak = findViewById(R.id.text_link_raw_surat_kontrak);
        Desain_Rumah = findViewById(R.id.text_link_desain_rumah);
        Orig_Desain_Rumah = findViewById(R.id.text_link_raw_desain_rumah);
        cv_desain_rumah = findViewById(R.id.cv_title_desain_rumah_riwayat_kontrak);


        /*
         * Passing data from last activity
         */
        String id = getIntent().getExtras().getString("id");
        String nik = getIntent().getExtras().getString("nomor_kontrak");
        String nama = getIntent().getExtras().getString("nama_pemesan");
        String email = getIntent().getExtras().getString("email");
        String alamat = getIntent().getExtras().getString("alamat");
        final String data_pemesan = getIntent().getExtras().getString("data_pemesan");
        final String rekap_data = getIntent().getExtras().getString("rekap_data");
        final String surat_kontrak = getIntent().getExtras().getString("surat_kontrak");
        final String desain_rumah = getIntent().getExtras().getString("desain_rumah");
        String status = getIntent().getExtras().getString("status");
        /*
         * I should set status text before set another text, it for declare correct link
         * to download PDF file for each status, during table has been merged into one table
         * 'tb_riwayat_kontrak' due efficiency reason.
         */
        Status.setText(status);
        if (Status.getText().toString().equalsIgnoreCase("Bangunan Baru")){
            final String link_data_pemesan = ("https://www.mandorin.site/mandorin/riwayat_kontrak/bangunan_baru/" + data_pemesan);
            final String link_surat_kontrak = ("https://www.mandorin.site/mandorin/riwayat_kontrak/bangunan_baru/" + surat_kontrak);
            final String link_rekap_data = ("https://www.mandorin.site/mandorin/riwayat_kontrak/bangunan_baru/" + rekap_data);
            final String link_desain_rumah = ("https://www.mandorin.site/mandorin/riwayat_kontrak/bangunan_baru/" + desain_rumah);
            Data_Pemesan.setText(link_data_pemesan);
            Rekap_Data.setText(link_rekap_data);
            Surat_Kontrak.setText(link_surat_kontrak);
            Desain_Rumah.setText(link_desain_rumah);
        } else if (Status.getText().toString().equalsIgnoreCase("Renovasi")) {
            final String link_data_pemesan = ("https://www.mandorin.site/mandorin/riwayat_kontrak/renovasi/" + data_pemesan);
            final String link_surat_kontrak = ("https://www.mandorin.site/mandorin/riwayat_kontrak/renovasi/" + surat_kontrak);
            final String link_rekap_data = ("https://www.mandorin.site/mandorin/riwayat_kontrak/renovasi/" + rekap_data);
            Data_Pemesan.setText(link_data_pemesan);
            Rekap_Data.setText(link_rekap_data);
            Surat_Kontrak.setText(link_surat_kontrak);
            Desain_Rumah.setVisibility(View.GONE);
            cv_desain_rumah.setVisibility(View.GONE);
            btn_desain_rumah.setVisibility(View.GONE);
        }

        /*
         * TextView Initializations
         */
        Nama.setText(nama);
        Nomor_Kontrak.setText(nik);
        Alamat.setText(alamat);
        Orig_Data_Pemesan.setText(data_pemesan);
        Orig_Surat_Kontrak.setText(surat_kontrak);
        Orig_Desain_Rumah.setText(desain_rumah);
        Orig_Rekap_Data.setText(rekap_data);

        /*
         * Set Scrolling On TextView
         */
        Alamat.setMovementMethod(new ScrollingMovementMethod());

        /*
         * Check if user downloaded document is exists or not from the beginning
         */
        cek_data();

        ActivityCompat.requestPermissions(activity_data_riwayat_kontrak.this, PERMISSIONS, 112);

        btn_data_pemesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = Orig_Data_Pemesan.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    btn_data_pemesan.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                    Toast.makeText(activity_data_riwayat_kontrak.this, "Berkas anda adalah " + file_data, Toast.LENGTH_LONG).show();
                    openFolder_Data_Pemesan();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_data_riwayat_kontrak.this, PERMISSIONS)) {
                            Toast.makeText(activity_data_riwayat_kontrak.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                            String url = Data_Pemesan.getText().toString();
                            new activity_data_riwayat_kontrak.DownloadFile().execute(url, data_pemesan);
                            btn_data_pemesan.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                            Toast.makeText(activity_data_riwayat_kontrak.this, "Berkas anda adalah " + file_data, Toast.LENGTH_LONG).show();
                            openFolder_Data_Pemesan();
                        }
                    } else {
                        Toast.makeText(activity_data_riwayat_kontrak.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btn_surat_kontrak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = Orig_Surat_Kontrak.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    btn_surat_kontrak.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                    Toast.makeText(activity_data_riwayat_kontrak.this, "Berkas anda adalah " + file_data, Toast.LENGTH_LONG).show();
                    openFolder_Surat_Kontrak();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_data_riwayat_kontrak.this, PERMISSIONS)) {
                            Toast.makeText(activity_data_riwayat_kontrak.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                            String url = Surat_Kontrak.getText().toString();
                            new activity_data_riwayat_kontrak.DownloadFile().execute(url, surat_kontrak);
                            btn_surat_kontrak.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                            Toast.makeText(activity_data_riwayat_kontrak.this, "Berkas anda adalah " + file_data, Toast.LENGTH_LONG).show();
                            openFolder_Surat_Kontrak();
                        }
                    } else {
                        Toast.makeText(activity_data_riwayat_kontrak.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btn_desain_rumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = Orig_Desain_Rumah.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    btn_desain_rumah.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                    Toast.makeText(activity_data_riwayat_kontrak.this, "Berkas anda adalah " + file_data, Toast.LENGTH_LONG).show();
                    openFolder_Desain_Rumah();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_data_riwayat_kontrak.this, PERMISSIONS)) {
                            Toast.makeText(activity_data_riwayat_kontrak.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                            String url = Desain_Rumah.getText().toString();
                            new activity_data_riwayat_kontrak.DownloadFile().execute(url, desain_rumah);
                            btn_desain_rumah.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                            Toast.makeText(activity_data_riwayat_kontrak.this, "Berkas anda adalah " + file_data, Toast.LENGTH_LONG).show();
                            openFolder_Desain_Rumah();
                        }
                    } else {
                        Toast.makeText(activity_data_riwayat_kontrak.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btn_rekap_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_data = Orig_Rekap_Data.getText().toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file_data);
                if (file.exists()) {
                    btn_rekap_data.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                    Toast.makeText(activity_data_riwayat_kontrak.this, "Berkas anda adalah " + file_data, Toast.LENGTH_LONG).show();
                    openFolder_Rekap_Data();
                } else {
                    if(internet_available()) {
                        if (!hasPermissions(activity_data_riwayat_kontrak.this, PERMISSIONS)) {
                            Toast.makeText(activity_data_riwayat_kontrak.this, "Anda belum menyetujui akses penyimpanan", Toast.LENGTH_LONG).show();
                        } else {
                            String url = Rekap_Data.getText().toString();
                            new activity_data_riwayat_kontrak.DownloadFile().execute(url, rekap_data);
                            btn_rekap_data.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
                            Toast.makeText(activity_data_riwayat_kontrak.this, "Berkas anda adalah " + file_data, Toast.LENGTH_LONG).show();
                            openFolder_Rekap_Data();
                        }
                    } else {
                        Toast.makeText(activity_data_riwayat_kontrak.this, "Harap cek konektivitas anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_riwayat_kontrak.this, activity_riwayat_kontrak.class);
                startActivity(intent);
            }
        });
    }

    private void cek_data(){
        String data_pemesan = Orig_Data_Pemesan.getText().toString();
        String rekap_data = Orig_Rekap_Data.getText().toString();
        String desain_rumah = Orig_Desain_Rumah.getText().toString();
        String surat_kontrak = Orig_Surat_Kontrak.getText().toString();
        File file_data_pemesan = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), data_pemesan);
        File file_rekap_data = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), rekap_data);
        File file_desain_rumah = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), desain_rumah);
        File file_surat_kontrak = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), surat_kontrak);
        if (file_data_pemesan.exists()) {
            btn_data_pemesan.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
        }
        if (file_rekap_data.exists()) {
            btn_rekap_data.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
        }
        if (file_desain_rumah.exists()) {
            btn_desain_rumah.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
        }
        if (file_surat_kontrak.exists()) {
            btn_surat_kontrak.setImageDrawable(getResources().getDrawable(R.drawable.download_c, getApplicationContext().getTheme()));
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

    public void openFolder_Data_Pemesan(){
        try {
            String file_data = Orig_Data_Pemesan.getText().toString();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("Application not found", e.getMessage());
            String file_data = Orig_Data_Pemesan.getText().toString();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(Intent.createChooser(intent, "Open folder"));
        } catch (Exception e){
            e.printStackTrace();
            Log.d("Unknown error", e.getMessage());
        }
    }

    public void openFolder_Rekap_Data(){
        try {
            String file_data = Orig_Rekap_Data.getText().toString();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("Application not found", e.getMessage());
            String file_data = Orig_Rekap_Data.getText().toString();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(Intent.createChooser(intent, "Open folder"));
        } catch (Exception e){
            e.printStackTrace();
            Log.d("Unknown error", e.getMessage());
        }
    }

    public void openFolder_Surat_Kontrak(){
        try {
            String file_data = Orig_Surat_Kontrak.getText().toString();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("Application not found", e.getMessage());
            String file_data = Orig_Surat_Kontrak.getText().toString();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(Intent.createChooser(intent, "Open folder"));
        } catch (Exception e){
            e.printStackTrace();
            Log.d("Unknown error", e.getMessage());
        }
    }

    public void openFolder_Desain_Rumah(){
        try {
            String file_data = Orig_Desain_Rumah.getText().toString();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + file_data);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d("Application not found", e.getMessage());
            String file_data = Orig_Desain_Rumah.getText().toString();
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