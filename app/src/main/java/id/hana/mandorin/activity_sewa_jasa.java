package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class activity_sewa_jasa extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private CardView bg_awal, renovasi, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_jasa);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * CardView
         */
        bg_awal = findViewById(R.id.sewa_jasa_menu_2);
        back = findViewById(R.id.back_activity_sewa_jasa);
        renovasi = findViewById(R.id.sewa_jasa_menu_1);

        /*
         * Passing data from last activity
         */
        final String nik = getIntent().getExtras().getString("nik");
        final String nama = getIntent().getExtras().getString("nama");

        bg_awal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_sewa_jasa.this, activity_bangun_dari_awal.class);
                intent.putExtra("nik", nik);
                intent.putExtra("nama", nama);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_sewa_jasa.this, activity_mandor.class);
                startActivity(intent);
            }
        });

        renovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_sewa_jasa.this, activity_renovasi.class);
                intent.putExtra("nik", nik);
                intent.putExtra("nama", nama);
                startActivity(intent);
            }
        });
    }

}