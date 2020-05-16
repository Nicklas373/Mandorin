package id.hana.mandorin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
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
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class activity_akun extends AppCompatActivity {

    private TextView userdump;
    private CardView lihat_akun, ganti_pass, log_out, back_akun;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;
    JSONObject json = null;
    String str = "";
    HttpResponse response;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(activity_akun.this, activity_login.class));
        } else {
            if (auth.getCurrentUser().isEmailVerified()) {
            } else {
                auth.getCurrentUser().sendEmailVerification();
                Toast.makeText(getApplicationContext(), "Harap verifikasi e-mail anda", Toast.LENGTH_LONG).show();
            }
        }

        setContentView(R.layout.activity_akun);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lihat_akun = (CardView) findViewById(R.id.mandor_akun_1);
        ganti_pass = (CardView) findViewById(R.id.mandor_akun_3);
        log_out = (CardView) findViewById(R.id.mandor_akun_4);
        back_akun = (CardView) findViewById(R.id.back_activity_akun);
        userdump = (TextView) findViewById(R.id.user_dump);

        userdump.setVisibility(View.GONE);

        pref = getApplicationContext().getSharedPreferences("data_akun", 0);

        if(pref.getString("email", null)!=null)
        {
            userdump.setText(pref.getString("email",null));
        }

        lihat_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internet_available()){
                    if (auth.getCurrentUser() != null) {
                            Intent intent = new Intent(activity_akun.this, activity_akun_baru.class);
                            startActivity(intent);
                    } else {
                        check_account_dialog();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ganti_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(internet_available()){
                    if (auth.getCurrentUser() != null) {
                            Intent intent = new Intent(activity_akun.this, activity_reset_pass.class);
                            startActivity(intent);
                    } else {
                        check_account_dialog();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(activity_akun.this, MainActivity.class);
                    startActivity(intent);
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() != null) {
                    log_out_dialog();
                } else {
                    check_account_dialog();
                }
            }
        });
    }

    private void log_out_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Log Out");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin log out?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Log Out",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog = ProgressDialog.show(activity_akun.this, "Log Out", "Memproses...", true);
                        if(internet_available()){
                            update_uid();
                            auth.signOut();
                            editor=pref.edit();
                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(activity_akun.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Anda sudah log out", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                            finish();
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

    private void check_account_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Menu Akun");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Harap Login Untuk Melanjutkan")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Login",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        if(internet_available()){
                            Intent intent = new Intent(activity_akun.this, activity_login.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
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

    private void update_uid() {
        String usermail_2 = userdump.getText().toString();
        String HttpUrl = "http://mandorin.site/mandorin/php/user/new/update_data_uid.php?email=" + usermail_2;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Showing response message coming from server.
                        // Toast.makeText(activity_akun.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Showing error message if something goes wrong.
                        // Toast.makeText(activity_login_2.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Generate current timestamp to firebase id when user log out
                // This may help to prevent data redundant then make invalid when push to DB as primary key
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String email = userdump.getText().toString();
                String cur_uid =  FirebaseInstanceId.getInstance().getToken() + timeStamp;

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("uid", cur_uid);
                params.put("email", email);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_akun.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}