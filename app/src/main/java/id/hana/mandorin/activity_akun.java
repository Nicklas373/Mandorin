package id.hana.mandorin;

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

import com.google.firebase.auth.FirebaseAuth;

public class activity_akun extends AppCompatActivity {

    private TextView userdump;
    private CardView lihat_akun, edit_akun, ganti_pass, log_out, back_akun;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(activity_akun.this, activity_login.class));
            finish();
        }

        setContentView(R.layout.activity_akun);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lihat_akun = (CardView) findViewById(R.id.mandor_akun_1);
        edit_akun = (CardView) findViewById(R.id.mandor_akun_2);
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
                    Intent intent = new Intent(activity_akun.this, activity_user_profile.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internet_available()){
                    Intent intent = new Intent(activity_akun.this, activity_edit_akun.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ganti_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(internet_available()){
                    Intent intent = new Intent(activity_akun.this, activity_reset_pass.class);
                    startActivity(intent);
                }else{
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
                log_out_dialog();
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
                        if(internet_available()){
                            auth.signOut();
                            editor=pref.edit();
                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(activity_akun.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Anda sudah log out", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
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

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}
