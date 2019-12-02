package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class activity_transaksi  extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * CardView
     */
    private CardView back, s_pembayaran, s_riwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        back = findViewById(R.id.back_activity_transaksi);
        s_pembayaran = findViewById(R.id.mandor_transaksi_1);
        s_riwayat = findViewById(R.id.mandor_transaksi_2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_transaksi.this, MainActivity.class);
                startActivity(intent);
            }
        });

        s_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_transaksi.this, activity_status_transaksi.class);
                startActivity(intent);
            }
        });

        s_riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_transaksi.this, activity_riwayat_pembayaran.class);
                startActivity(intent);
            }
        });
    }
}
