package id.hana.mandorin;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Azhar Rivaldi on 26/03/2018.
 */

public class activity_reset_pass extends AppCompatActivity {

    private EditText inputEmail;
    private Button selanjutnya;
    private TextView kembali;
    private FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        inputEmail = (EditText) findViewById(R.id.email_reset);
        selanjutnya = (Button) findViewById(R.id.reset_account);
        kembali = (TextView) findViewById(R.id.btn_batal);

        auth = FirebaseAuth.getInstance();

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Masukkan Email Anda", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                dialog = ProgressDialog.show(activity_reset_pass.this, "Reset Password", "Memproses...", true);

                                if (task.isSuccessful()) {
                                    Toast.makeText(activity_reset_pass.this, "Kami Telah Mengirimkan Instruksi Untuk Reset Password di Email Anda", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(activity_reset_pass.this, activity_login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(activity_reset_pass.this, "Gagal Mengirimkan Instruksi ke Email Anda", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(activity_reset_pass.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

}