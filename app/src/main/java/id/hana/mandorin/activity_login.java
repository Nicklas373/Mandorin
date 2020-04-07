package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class activity_login extends AppCompatActivity {

    private TextView buat_akun;
    private EditText inputEmail;
    private ImageView login;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        inputEmail = (EditText) findViewById(R.id.email_login);
        login = (ImageView) findViewById(R.id.next_activity_login);
        buat_akun = (TextView) findViewById(R.id.buat_akun);

        pref = getApplicationContext().getSharedPreferences("data_akun", 0);

        buat_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internet_available()){
                    startActivity(new Intent(activity_login.this, activity_register.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(internet_available()){
                            final String email = inputEmail.getText().toString();

                            if (TextUtils.isEmpty(email)) {
                                Toast.makeText(getApplicationContext(), "Harap Masukkan Email Anda", Toast.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("email", inputEmail.getText().toString());
                                editor.apply();
                                Intent intent = new Intent(activity_login.this, activity_login_2.class);
                                startActivity(intent);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}