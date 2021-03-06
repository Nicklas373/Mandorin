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
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;

public class activity_register_4 extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private ImageView back, next;
    private TextView old_nama, old_nik, old_umur;
    private EditText ed_alamat, ed_telp;

    /*
     * Declare other think
     */
    private String SERVER_URL = "http://mandorin.site/mandorin/php/user/create_init_user.php";

    /*
     * Firebase initializations
     */
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_4);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        next = (ImageView) findViewById(R.id.next_activity_register_4);
        back = (ImageView) findViewById(R.id.back_activity_register_4);
        ed_alamat = (EditText) findViewById(R.id.isi_alamat);
        ed_telp = (EditText) findViewById(R.id.isi_telp);
        old_nama = (TextView) findViewById(R.id.dummy_nama_reg_4);
        old_umur = (TextView) findViewById(R.id.dummy_nik_reg_4);
        old_nik = (TextView) findViewById(R.id.dummy_umur_reg_4);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        /*
         * Passing data from last activity
         */
        final String isi_nama = getIntent().getExtras().getString("isi_nama");
        final String isi_umur = getIntent().getExtras().getString("isi_umur");
        final String isi_nik = getIntent().getExtras().getString("isi_nik");

        old_nama.setText(isi_nama);
        old_umur.setText(isi_umur);
        old_nik.setText(isi_nik);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_register_4.this, activity_register_3.class);
                intent.putExtra("isi_nama", isi_nama);
                intent.putExtra("isi_umur", isi_umur);
                intent.putExtra("isi_nik", isi_nik);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_alamat.getText().toString().length() == 0) {
                    ed_alamat.setError("Harap Masukkan Data Komplain Anda");
                } else if (ed_telp.getText().toString().length() == 0) {
                    ed_telp.setError("Harap Masukkan Data Komplain Anda");
                } else {
                    reg_dialog();
                }
            }
        });
    }

    private void reg_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Data Akun");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah data yang anda masukkan sudah benar ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Kirim",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        try {
                            update_data_user();
                            dialog = ProgressDialog.show(activity_register_4.this, "Menu Daftar Akun", "Memproses Data Akun...", true);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(activity_register_4.this, MainActivity.class);
                        startActivity(intent);
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

    private void update_data_user() {
        final String email_1 = firebaseUser.getEmail();
        String adress = "http://mandorin.site/mandorin/php/user/new/update_data_user.php?id=" + email_1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, adress,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        //Toast.makeText(activity_data_transaksi_2.this, ServerResponse, Toast.LENGTH_LONG).show();
                        if (ServerResponse.length() > 10) {
                            Success_Notif();
                        } else {
                            Fail_Notif();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_register_4.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                String nama = old_nama.getText().toString();
                String umur = old_umur.getText().toString();
                String nik = old_nik.getText().toString();
                String telp = ed_telp.getText().toString();
                String email = firebaseUser.getEmail();
                String alamat = ed_alamat.getText().toString();

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("nama_lengkap", nama);
                params.put("umur", umur);
                params.put("nik", nik);
                params.put("telp", telp);
                params.put("alamat", alamat);
                params.put("foto_user", "akun.png");
                params.put("email", email);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_register_4.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void Success_Notif(){
        Intent intent;
        PendingIntent pendingIntent;
        NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        String id = "ID_MANDORIN";
        String title = "Mandorin";
        String message = "Data akun anda telah di sukses di perbaharui!";
        String reply = "Lihat disini untuk melihat data akun anda";
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
        intent = new Intent(getApplicationContext(), activity_akun_baru.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentTitle("Mandorin")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Menu Registrasi")
                .setContentText(message)
                .setDefaults(Notification.COLOR_DEFAULT)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(Notification.PRIORITY_HIGH)
                .addAction(R.drawable.mandorin_icon, reply,
                        notifyPendingIntent);
        Notification notification = builder.build();
        notifManager.notify(0, notification);
    }

    private void Fail_Notif(){
        Intent intent;
        PendingIntent pendingIntent;
        NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        String id = "ID_MANDORIN";
        String title = "Mandorin";
        String message = "Pembaharuan data akun anda gagal, harap coba lagi!";
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
        builder.setContentTitle("Mandorin")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Menu Registrasi")
                .setContentText(message)
                .setDefaults(Notification.COLOR_DEFAULT)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(Notification.PRIORITY_HIGH);
        Notification notification = builder.build();
        notifManager.notify(0, notification);
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}