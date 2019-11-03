package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class activity_kontrak  extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * CardView
     */
    private CardView back, status, riwayat, data_komplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontrak);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        back = findViewById(R.id.back_activity_kontrak);
        status = findViewById(R.id.mandor_kontrak_1);
        riwayat = findViewById(R.id.mandor_kontrak_2);
        data_komplain = findViewById(R.id.mandor_renovasi_3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_kontrak.this, MainActivity.class);
                startActivity(intent);
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_kontrak.this, activity_kontrak_renovasi.class);
                startActivity(intent);
            }
        });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_kontrak.this, activity_kontrak_riwayat.class);
                startActivity(intent);
            }
        });
    }
}
