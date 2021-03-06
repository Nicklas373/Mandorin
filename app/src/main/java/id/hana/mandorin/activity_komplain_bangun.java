package id.hana.mandorin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class activity_komplain_bangun extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView nama_pemesan_1_1;
    private EditText komplain_1, nomor_kontrak_1_1, alamat_kontrak_1;
    private ImageView kirim;
    private CardView back;

    /*
     * Create Web URL Init
     */
    String HttpUrl = "http://mandorin.site/mandorin/php/user/new/insert_data_komplain.php";

    /*
     * Firebase initializations
     */
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komplain_bangun);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        nomor_kontrak_1_1 = findViewById(R.id.user_input_nomor_kontrak);
        alamat_kontrak_1 = findViewById(R.id.user_input_alamat_kontrak);
        nama_pemesan_1_1 = findViewById(R.id.nama_pemesan_komplain_1);
        komplain_1 = findViewById(R.id.et_komplain);
        back = findViewById(R.id.back_activity_komplain_bangun);
        kirim = findViewById(R.id.kirim_activity_komplain_bangun);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        /*
         * Passing data from last activity
         */
        final String id_1 = getIntent().getExtras().getString("id");
        final String nomor_kontrak_1 = getIntent().getExtras().getString("nomor_kontrak");
        final String nama_pemesan_1 = getIntent().getExtras().getString("nama_pemesan");
        final String email_1 = getIntent().getExtras().getString("email");
        final String no_telp_1 = getIntent().getExtras().getString("no_telp");
        final String alamat_pekerjaan_1 = getIntent().getExtras().getString("alamat_pekerjaan");
        final String status_pekerjaan_1 = getIntent().getExtras().getString("status_pekerjaan");
        final String presentase_1 = getIntent().getExtras().getString("presentase");
        final String waktu_awal_1 = getIntent().getExtras().getString("waktu_mulai");
        final String waktu_akhir_1 = getIntent().getExtras().getString("waktu_akhir");
        final String data_desain_1 = getIntent().getExtras().getString("data_desain");
        final String rekap_data_1 = getIntent().getExtras().getString("data_rekap");
        final String surat_kontrak_1 = getIntent().getExtras().getString("surat_kontrak");
        final String role_kontrak_1 = getIntent().getExtras().getString("role_kontrak");
        final String role_komplain_1 = getIntent().getExtras().getString("role_komplain");
        final String rekap_admin_1 = getIntent().getExtras().getString("rekap_admin");
        final String nama_mandor_1 = getIntent().getExtras().getString("nama_mandor");

        /*
         * TextView Initializations
         */
        nomor_kontrak_1_1.setText(nomor_kontrak_1);
        alamat_kontrak_1.setText(alamat_pekerjaan_1);
        nama_pemesan_1_1.setText(nama_pemesan_1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_komplain_bangun.this, activity_layanan_kontrak.class);
                intent.putExtra("id", id_1);
                intent.putExtra("nomor_kontrak", nomor_kontrak_1);
                intent.putExtra("alamat", alamat_pekerjaan_1);
                intent.putExtra("nama_pemesan", nama_pemesan_1);
                intent.putExtra("email", email_1);
                intent.putExtra("no_telp",no_telp_1);
                intent.putExtra("alamat_pekerjaan", alamat_pekerjaan_1);
                intent.putExtra("status_pekerjaan", status_pekerjaan_1);
                intent.putExtra("presentase", presentase_1);
                intent.putExtra("waktu_mulai", waktu_awal_1);
                intent.putExtra("waktu_akhir", waktu_akhir_1);
                intent.putExtra("data_desain", data_desain_1);
                intent.putExtra("data_rekap", rekap_data_1);
                intent.putExtra("surat_kontrak", surat_kontrak_1);
                intent.putExtra("role_kontrak", role_kontrak_1);
                intent.putExtra("role_komplain", role_komplain_1);
                intent.putExtra("rekap_admin", rekap_admin_1);
                intent.putExtra("nama_mandor", nama_mandor_1);
                startActivity(intent);
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (komplain_1.getText().toString().length() == 0) {
                    komplain_1.setError("Harap Masukkan Data Komplain Anda");
                } else {
                    if (internet_available()) {
                        komplain_dialog();
                    } else {
                        Toast.makeText(activity_komplain_bangun.this, "Harap periksa koneksi internet anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void komplain_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Data Komplain");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin mengirim data komplain?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Kirim",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        try
                        {
                            insert_data_komplain();
                            update_data_kontrak();
                            dialog = ProgressDialog.show(activity_komplain_bangun.this, "Menu Komplain", "Mengirim Data Komplain...", true);
                        } catch (IllegalArgumentException e)
                        {
                            Toast.makeText(activity_komplain_bangun.this, "Proses Gagal!", Toast.LENGTH_SHORT).show();
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

    private void insert_data_komplain() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        if (ServerResponse.length() > 10) {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Success_Notif();
                                    Intent intent = new Intent(activity_komplain_bangun.this, activity_komplain_konfirmasi.class);
                                    startActivity(intent);
                                }
                            }, 3000L); //3000 L = 3 detik

                        } else {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Fail_Notif();
                                    Intent intent = new Intent(activity_komplain_bangun.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }, 3000L); //3000 L = 3 detik

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_komplain_bangun.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Fail_Notif();
                                Intent intent = new Intent(activity_komplain_bangun.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }, 3000L); //3000 L = 3 detik
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {

                String nama_pemesan = nama_pemesan_1_1.getText().toString();
                String nomor_kontrak = nomor_kontrak_1_1.getText().toString();
                String alamat_kontrak = alamat_kontrak_1.getText().toString();
                String komplain_kontrak = komplain_1.getText().toString();
                String email = firebaseUser.getEmail();
                String status_komplain = "pending";
                SimpleDateFormat CurDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String tanggal = CurDate.format(Calendar.getInstance().getTime());

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("nama_pemesan", nama_pemesan);
                params.put("nomor_kontrak", nomor_kontrak);
                params.put("email", email);
                params.put("alamat", alamat_kontrak);
                params.put("komplain", komplain_kontrak);
                params.put("status_komplain", status_komplain);
                params.put("tgl_komplain", tanggal);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_komplain_bangun.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void update_data_kontrak() {
        final String id_1 = getIntent().getExtras().getString("id");
        String adress = "http://mandorin.site/mandorin/php/user/new/update_data_kontrak.php?id=" + id_1;
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
                        Toast.makeText(activity_komplain_bangun.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                String id_1 = getIntent().getExtras().getString("id");
                String nomor_kontrak_1 = getIntent().getExtras().getString("nomor_kontrak");
                String nama_pemesan_1 = getIntent().getExtras().getString("nama_pemesan");
                String email_1 = getIntent().getExtras().getString("email");
                String no_telp_1 = getIntent().getExtras().getString("no_telp");
                String alamat_pekerjaan_1 = getIntent().getExtras().getString("alamat_pekerjaan");
                String status_pekerjaan_1 = getIntent().getExtras().getString("status_pekerjaan");
                String presentase_1 = getIntent().getExtras().getString("presentase");
                String waktu_awal_1 = getIntent().getExtras().getString("waktu_mulai");
                String waktu_akhir_1 = getIntent().getExtras().getString("waktu_akhir");
                String data_desain_1 = getIntent().getExtras().getString("data_desain");
                String rekap_data_1 = getIntent().getExtras().getString("data_rekap");
                String surat_kontrak_1 = getIntent().getExtras().getString("surat_kontrak");
                String role_kontrak_1 = getIntent().getExtras().getString("role_kontrak");
                String role_komplain_1 = getIntent().getExtras().getString("role_komplain");
                int komplain_1 = Integer.parseInt(role_komplain_1);
                int komplain = komplain_1 + 1;
                String total_komplain = Integer.toString(komplain);
                String rekap_admin_1 = getIntent().getExtras().getString("rekap_admin");
                String nama_mandor_1 = getIntent().getExtras().getString("nama_mandor");

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", id_1);
                params.put("nomor_kontrak", nomor_kontrak_1);
                params.put("nama_pemesan", nama_pemesan_1);
                params.put("email", email_1);
                params.put("no_telp", no_telp_1);
                params.put("alamat_pekerjaan", alamat_pekerjaan_1);
                params.put("status_pekerjaan", status_pekerjaan_1);
                params.put("presentase", presentase_1);
                params.put("waktu_mulai", waktu_awal_1);
                params.put("waktu_akhir", waktu_akhir_1);
                params.put("data_desain", data_desain_1);
                params.put("data_rekap", rekap_data_1);
                params.put("surat_kontrak", surat_kontrak_1);
                params.put("role_kontrak", role_kontrak_1);
                params.put("role_komplain", total_komplain);
                params.put("rekap_admin", rekap_admin_1);
                params.put("nama_mandor", nama_mandor_1);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_komplain_bangun.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void Success_Notif(){
        Intent intent;
        PendingIntent pendingIntent;
        NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        String id = "ID_MANDORIN";
        String title = "Mandorin";
        String message = "Data komplain anda sudah kami terima, kami akan memproses data komplain anda untuk selanjutnya.";
        String reply = "Lihat komplain anda disini";
        android.support.v4.app.NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.enableLights(true);
                notifManager.createNotificationChannel(mChannel);
            }
        }
        builder = new android.support.v4.app.NotificationCompat.Builder(this,id);
        intent = new Intent(getApplicationContext(), activity_data_komplain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentTitle("Mandorin")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Data Komplain")
                .setContentText(message)
                .setDefaults(Notification.COLOR_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(notifyPendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(Notification.PRIORITY_HIGH)
                .addAction(R.drawable.mandorin_icon, reply,
                            notifyPendingIntent);
        Notification notification = builder.build();
        notifManager.notify(0, notification);
    }

    private void Fail_Notif(){
        Intent intent;
        NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        String id = "ID_MANDORIN";
        String title = "Mandorin";
        String message = "Pengiriman data komplain anda gagal, harap coba lagi !";
        android.support.v4.app.NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                notifManager.createNotificationChannel(mChannel);
            }
        }
        builder = new android.support.v4.app.NotificationCompat.Builder(this,id);
        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentTitle("Mandorin")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Data Komplain")
                .setContentText(message)
                .setDefaults(Notification.COLOR_DEFAULT)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setContentIntent(notifyPendingIntent)
                .setPriority(Notification.PRIORITY_HIGH);
        Notification notification = builder.build();
        notifManager.notify(0, notification);
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}