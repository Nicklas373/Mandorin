package id.hana.mandorin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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

import java.util.HashMap;
import java.util.Map;

public class activity_register_2 extends AppCompatActivity {

    private TextView batal, dbg;
    private EditText inputPassword, inputPasswordver;
    private Button btnSignUp;
    private FirebaseAuth auth;
    ProgressDialog dialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        auth = FirebaseAuth.getInstance();

        btnSignUp = (Button) findViewById(R.id.reg_account);
        inputPassword = (EditText) findViewById(R.id.password_register);
        inputPasswordver = (EditText) findViewById(R.id.password_register_ver);
        dbg = (TextView) findViewById(R.id.dummy_usermail_register);
        batal = (TextView) findViewById(R.id.btn_batal) ;

        pref = getApplicationContext().getSharedPreferences("data_akun", 0);

        if(pref.getString("email", null)!=null)
        {
            dbg.setText(pref.getString("email",null));
            editor=pref.edit();
            editor.clear();
            editor.apply();
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = dbg.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Masukkan Password Anda", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password terlalu pendek, minimal 6 karakter", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (inputPassword.getText().toString().equals(inputPasswordver.getText().toString())){
                    //create user
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(activity_register_2.this, new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    Toast.makeText(activity_register_2.this, "Akun berhasil di buat", Toast.LENGTH_SHORT).show();

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(activity_register_2.this, "Akun gagal di buat", Toast.LENGTH_LONG).show();
                                    } else {
                                        createdata();
                                        try
                                        {
                                            dialog = ProgressDialog.show(activity_register_2.this, "Register Akun", "Memproses...", true);
                                            SharedPreferences.Editor editor = pref.edit ();
                                            editor.putString("email",dbg.getText().toString());
                                            editor.apply();
                                            startActivity(new Intent(activity_register_2.this, activity_akun.class));
                                            finish();
                                        } catch (IllegalArgumentException e)
                                        {
                                            Toast.makeText(activity_register_2.this, "Proses Gagal!", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });

                } else {
                    inputPasswordver.setError("Cek password anda kembali");
                }
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
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

    private void createdata() {
        String HttpUrl = "http://mandorin.site/mandorin/php/user/new/insert_data_user.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        //Toast.makeText(activity_register_2.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_register_2.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                String nama = "Data Kosong";
                String email = dbg.getText().toString();
                String umur = "Data Kosong";
                String nik = "Data Kosong";
                String telp = "Data Kosong";
                String alamat = "Data Kosong";
                String foto_user = "Data Kosong";


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
}