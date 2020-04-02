package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class activity_register extends AppCompatActivity {
    private TextView batal;
    private EditText inputEmail;
    private Button btnSignUp;
    private FirebaseAuth auth;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        btnSignUp = (Button) findViewById(R.id.reg_account);
        inputEmail = (EditText) findViewById(R.id.email_register);
        batal = (TextView) findViewById(R.id.btn_batal) ;

        /*
         * SharedPreferences Declaration
         */
        pref = getSharedPreferences("data_akun", Context.MODE_PRIVATE);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Harap Masukkan Email Anda", Toast.LENGTH_SHORT).show();
                }
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("email", inputEmail.getText().toString());
                editor.apply();
                Intent intent = new Intent(activity_register.this, activity_register_2.class);
                startActivity(intent);
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_register.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}