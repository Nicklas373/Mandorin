package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class activity_data_pemesan_renovasi extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView Nama, Nik, Email, Status, Data_Renovasi, Alamat;
    private CardView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_data_pemesan_renovasi);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        Nama = findViewById(R.id.user_input_nama);
        Nik = findViewById(R.id.user_input_nik);
        Email = findViewById(R.id.user_input_email);
        Status = findViewById(R.id.user_input_status);
        Data_Renovasi = findViewById(R.id.user_input_data_renovasi);
        Alamat = findViewById(R.id.user_input_alamat);
        back = findViewById(R.id.back_activity_data_pemesan_renovasi);

        /*
         * Passing data from last activity
         */
        String id = getIntent().getExtras().getString("id");
        String nik = getIntent().getExtras().getString("nik");
        String nama = getIntent().getExtras().getString("nama");
        String email = getIntent().getExtras().getString("email");
        String alamat = getIntent().getExtras().getString("alamat");
        String data_renovasi = getIntent().getExtras().getString("data_renovasi");
        String status = getIntent().getExtras().getString("status");

        /*
         * TextView Initializations
         */
        Nama.setText(nama);
        Nik.setText(nik);
        Email.setText(email);
        Status.setText(status);
        Data_Renovasi.setText(data_renovasi);
        Alamat.setText(alamat);

        /*
         * Set Scrolling On TextView
         */
        Data_Renovasi.setMovementMethod(new ScrollingMovementMethod());
        Alamat.setMovementMethod(new ScrollingMovementMethod());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_pemesan_renovasi.this, activity_pemesan_renovasi.class);
                startActivity(intent);
            }
        });
    }
}
