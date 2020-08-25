package id.hana.mandorin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class activity_register_2 extends AppCompatActivity {

    private TextView dbg;
    private EditText inputPassword, inputPasswordver;
    private ImageView back, next;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;
    ProgressDialog dialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        auth = FirebaseAuth.getInstance();

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        next = (ImageView) findViewById(R.id.next_activity_register_2);
        inputPassword = (EditText) findViewById(R.id.password_register);
        inputPasswordver = (EditText) findViewById(R.id.password_register_ver);
        dbg = (TextView) findViewById(R.id.dummy_usermail_register);
        back = (ImageView) findViewById(R.id.back_activity_register_2);

        pref = getApplicationContext().getSharedPreferences("data_akun", 0);

        if (pref.getString("email", null) != null) {
            dbg.setText(pref.getString("email", null));
            editor = pref.edit();
            editor.clear();
            editor.apply();
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = dbg.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Masukkan Password Anda", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password terlalu singkat, minimal 6 karakter", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (inputPassword.getText().toString().equals(inputPasswordver.getText().toString())) {
                    if (internet_available()) {
                        //create user
                        dialog = ProgressDialog.show(activity_register_2.this, "Register Akun", "Memproses...", true);
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(activity_register_2.this, new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {

                                        if (!task.isSuccessful()) {
                                            Toast.makeText(activity_register_2.this, "Akun gagal di buat", Toast.LENGTH_LONG).show();
                                            finish();
                                        } else {
                                            try {
                                                auth.getCurrentUser().sendEmailVerification();
                                                insert_data_user();
                                                insert_uid();
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("email", dbg.getText().toString());
                                                editor.apply();
                                                update_account();
                                                Success_Notif();
                                            } catch (IllegalArgumentException e) {
                                                Fail_Notif();
                                                e.printStackTrace();
                                                finish();
                                            }
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(activity_register_2.this, "Harap periksa koneksi internet anda", Toast.LENGTH_LONG).show();
                    }
                } else {
                    inputPasswordver.setError("Cek password anda kembali");
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_register_2.this, activity_register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void insert_data_user() {
        String HttpUrl = "http://mandorin.site/mandorin/php/user/new/insert_data_user.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Showing response message coming from server.
                        // Toast.makeText(activity_register_2.this, "Create Account Success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_register_2.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String nama = "-";
                String email = dbg.getText().toString();
                String umur = "-";
                String nik = "-";
                String telp = "-";
                String alamat = "-";
                String foto_user = "akun.png";

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("nama_lengkap", nama);
                params.put("email", email);
                params.put("umur", umur);
                params.put("nik", nik);
                params.put("telp", telp);
                params.put("alamat", alamat);
                params.put("foto_user", foto_user);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_register_2.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void insert_uid() {
        String HttpUrl = "http://mandorin.site/mandorin/php/user/new/insert_data_uid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Showing response message coming from server.
                        // Toast.makeText(activity_register_2.this, "Create UID Success", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_register_2.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String uid = FirebaseInstanceId.getInstance().getToken();
                String email = dbg.getText().toString();

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("uid", uid);
                params.put("email", email);
                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_register_2.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }

    private void update_account() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Isi data akun");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin mengisi data akun anda?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        startActivity(new Intent(activity_register_2.this, activity_register_3.class));
                        finish();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(activity_register_2.this, MainActivity.class));
                        finish();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void Success_Notif(){
        Intent intent;
        NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        String id = "ID_MANDORIN";
        String title = "Mandorin";
        String message = "Selamat, registrasi akun anda telah berhasil ! Harap cek email anda untuk melakukan verifikasi email pada akun mandorin";
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
        builder.setContentTitle("Mandorin")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setDefaults(Notification.COLOR_DEFAULT)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(Notification.PRIORITY_HIGH);
        Notification notification = builder.build();
        notifManager.notify(0, notification);
    }

    private void Fail_Notif(){
        Intent intent;
        PendingIntent pendingIntent;
        NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        String id = "ID_MANDORIN";
        String title = "Mandorin";
        String message = "Registrasi akun gagal, harap coba lagi!";
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
                .setContentText(message)
                .setDefaults(Notification.COLOR_DEFAULT)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(Notification.PRIORITY_HIGH);
        Notification notification = builder.build();
        notifManager.notify(0, notification);
    }
}