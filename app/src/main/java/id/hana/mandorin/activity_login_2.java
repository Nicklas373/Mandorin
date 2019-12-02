package id.hana.mandorin;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class activity_login_2 extends AppCompatActivity {

    private TextView buat_akun, reset_akun, dummy_debug;
    private EditText inputPassword;
    private FirebaseAuth auth;
    private Button btnlogin_2;
    ProgressDialog dialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

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
                                            dialog = ProgressDialog.show(activity_login_2.this, "Login Akun", "Memproses...", true);
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putString("email", dummy_debug.getText().toString());
                                            editor.apply();
                                            Intent intent = new Intent(activity_login_2.this, activity_akun.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    }
                });
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}