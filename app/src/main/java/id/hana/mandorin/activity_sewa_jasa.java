package id.hana.mandorin;

import android.content.Intent;
import android.content.SharedPreferences;
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

    /*
     * SharedPreferences Usage
     * I want to reduce passing usage, since it seems mess with app runtime sometimes
     */
    SharedPreferences pref;
    SharedPreferences.Editor editor;

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
         * SharedPreferences Declaration
         */
        pref = getApplicationContext().getSharedPreferences("data_mandor", 0);
        final String nik = pref.getString("nik",null);
        final String nama = pref.getString("nama",null);

        bg_awal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_sewa_jasa.this, activity_bangun_dari_awal.class);
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
                startActivity(intent);
            }
        });
    }
}