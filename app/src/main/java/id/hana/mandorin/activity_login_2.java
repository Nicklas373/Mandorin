package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class activity_login_2 extends AppCompatActivity {

    private TextView buat_akun, reset_akun, dummy_debug;
    private EditText inputPassword;
    private FirebaseAuth auth;
    private Button btnlogin_2;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String HttpUrl = "http://mandorin.site/mandorin/php/user/create_init_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        inputPassword = (EditText) findViewById(R.id.password_login);
        btnlogin_2 = (Button) findViewById(R.id.next_account_2);
        buat_akun = (TextView) findViewById(R.id.buat_akun);
        reset_akun = (TextView) findViewById(R.id.reset_acc);
        dummy_debug = (TextView) findViewById(R.id.dummy_usermail);

        auth = FirebaseAuth.getInstance();

        pref = getApplicationContext().getSharedPreferences("data_akun", 0);

        if(pref.getString("email", null)!=null)
        {
            dummy_debug.setText(pref.getString("email",null));
            editor=pref.edit();
            editor.clear();
            editor.apply();
        }

        buat_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internet_available()){
                    startActivity(new Intent(activity_login_2.this, activity_register.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reset_akun.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(internet_available()){
                            startActivity(new Intent(activity_login_2.this, activity_reset_pass.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btnlogin_2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String email_2 = dummy_debug.getText().toString();
                        final String password = inputPassword.getText().toString();

                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(getApplicationContext(), "Harap Masukkan Password Anda", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        auth.signInWithEmailAndPassword(email_2, password)
                                .addOnCompleteListener(activity_login_2.this, new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {

                                        if (!task.isSuccessful()) {
                                            if (password.length() < 6) {
                                                inputPassword.setError("Password Minimal 6 karakter");
                                            } else {
                                                Toast.makeText(activity_login_2.this, "Login Gagal / Password Salah", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        else {
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putString("email", dummy_debug.getText().toString());
                                            editor.apply();
                                            init_user_data();
                                            Intent intent = new Intent(activity_login_2.this, activity_akun.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    }
                });
    }

    private void init_user_data() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        Toast.makeText(activity_login_2.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_login_2.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {

                String fname = "";
                String email = dummy_debug.getText().toString();
                String umur = "";
                String nik = "";
                String telp = "";
                String alamat = "";
                String foto_user ="";
                String last_modified = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("nama_lengkap", fname);
                params.put("email", email);
                params.put("umur", umur);
                params.put("nik", nik);
                params.put("telp", telp);
                params.put("alamat", alamat);
                params.put("foto_user", foto_user);
                params.put("last_modified", last_modified);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_login_2.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}