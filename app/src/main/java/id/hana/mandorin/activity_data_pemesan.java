package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class activity_data_pemesan extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * CardView
     */
    private CardView back, data_renovasi, data_bangun_dari_awal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pemesan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        back = findViewById(R.id.back_activity_pemesan);
        data_renovasi = findViewById(R.id.mandor_pemesan_1);
        data_bangun_dari_awal = findViewById(R.id.mandor_pemesan_2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_pemesan.this, activity_kontrak_renovasi.class);
                startActivity(intent);
            }
        });

        data_renovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_pemesan.this, activity_pemesan_renovasi.class);
                startActivity(intent);
            }
        });

        /*
        data_bangun_dari_awal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_pemesan.this, activity_kontrak_renovasi.class);
                startActivity(intent);
            }
        });
        */
    }
}
