package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class activity_status_pembayaran extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * CardView
     */
    private CardView back, renovasi, bangun_dari_awal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_pembayaran);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        back = findViewById(R.id.back_activity_status_pembayaran);
        renovasi = findViewById(R.id.mandor_transaksi_1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_status_pembayaran.this, activity_renovasi.class);
                startActivity(intent);
            }
        });

        renovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_status_pembayaran.this, activity_pemesan_pembayaran_renovasi.class);
                startActivity(intent);
            }
        });

        bangun_dari_awal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_status_pembayaran.this, activity_pemesan_pembayaran_bangun_dari_awal.class);
                startActivity(intent);
            }
        });

    }
}
