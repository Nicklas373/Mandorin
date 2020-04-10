package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class activity_data_komplain_user extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private EditText nomor_kontrak, alamat_kontrak, data_komplain, status_komplain;
    private CardView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_data_komplain_user);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        nomor_kontrak = findViewById(R.id.user_input_nomor_kontrak);
        alamat_kontrak = findViewById(R.id.user_input_alamat_kontrak);
        data_komplain = findViewById(R.id.user_input_komplain);
        back = findViewById(R.id.back_activity_komplain_user);
        status_komplain = findViewById(R.id.user_input_status_komplain_kontrak);

        /*
         * Passing data from last activity
         */
        String id = getIntent().getExtras().getString("id");
        String nomor_kontrak_1 = getIntent().getExtras().getString("nomor_kontrak");
        String status_kontrak_komplain_1 = getIntent().getExtras().getString("status_komplain");
        String alamat_kontrak_1 = getIntent().getExtras().getString("alamat");
        String komplain_kontrak_1 = getIntent().getExtras().getString("komplain");

        /*
         * TextView Initializations
         */
        nomor_kontrak.setText(nomor_kontrak_1);
        alamat_kontrak.setText(alamat_kontrak_1);
        data_komplain.setText(komplain_kontrak_1);
        status_komplain.setText(status_kontrak_komplain_1);

        /*
         * Set Scrolling On TextView
         */
        data_komplain.setMovementMethod(new ScrollingMovementMethod());
        alamat_kontrak.setMovementMethod(new ScrollingMovementMethod());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_komplain_user.this, activity_data_komplain.class);
                startActivity(intent);
            }
        });
    }
}