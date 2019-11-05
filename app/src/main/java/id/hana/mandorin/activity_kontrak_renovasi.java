package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class activity_kontrak_renovasi extends AppCompatActivity {

    private CardView back, data_komplain, data_pemesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontrak_renovasi);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        back = findViewById(R.id.back_activity_kontrak_1);
        data_komplain = findViewById(R.id.mandor_renovasi_3);
        data_pemesan = findViewById(R.id.mandor_renovasi_4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_kontrak_renovasi.this, activity_kontrak.class);
                startActivity(intent);
            }
        });

        data_pemesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_kontrak_renovasi.this, activity_data_pemesan.class);
                startActivity(intent);
            }
        });
    }
}
