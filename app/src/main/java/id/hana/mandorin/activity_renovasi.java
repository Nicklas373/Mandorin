package id.hana.mandorin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class activity_renovasi extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView, RadioGroup & Button
     */
    private TextView dummy;
    private EditText data_renovasi;
    private RadioGroup rg;
    private RadioButton rb, rb_2;
    private Button selanjutnya;
    private CardView back;

    /*
     * SharedPreferences Usage
     * I want to reduce passing usage, since it seems mess with app runtime sometimes
     */
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renovasi);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        data_renovasi = findViewById(R.id.et_renovasi);
        dummy = findViewById(R.id.dummy);
        selanjutnya = findViewById(R.id.button_selanjutnya);
        rg = findViewById(R.id.rg_borongan);
        rb = findViewById(R.id.rb_1);
        rb_2 = findViewById(R.id.rb_2);
        back = findViewById(R.id.back_activity_renovasi);

        /*
         * SharedPreferences Declaration
         */
        pref = getApplicationContext().getSharedPreferences("data_mandor", 0);
        final String nik = pref.getString("nik",null);
        final String nama = pref.getString("nama",null);

        selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data_renovasi.getText().toString().length() == 0) {
                    data_renovasi.setError("Harap Masukkan Data Renovasi");
                } else {
                            int id = rg.getCheckedRadioButtonId();
                            switch (id)
                            {
                                case R.id.rb_1:
                                    String new_dummy = ((RadioButton)findViewById(id)).getText().toString();
                                    dummy.setText(new_dummy);
                                break;
                                case R.id.rb_2:
                                    String new_dummy_2 = ((RadioButton)findViewById(id)).getText().toString();
                                    dummy.setText(new_dummy_2);
                                break;
                            }
                            pref = getApplicationContext().getSharedPreferences("data_renovasi", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("nik", nik);
                            editor.putString("nama", nama);
                            editor.putString("data_renovasi", data_renovasi.getText().toString());
                            editor.putString("jenis_borongan", dummy.getText().toString());
                            editor.apply();
                            Intent intent = new Intent(activity_renovasi.this, activity_renovasi_2.class);
                            startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_renovasi.this, activity_sewa_jasa.class);
                startActivity(intent);
            }
        });
    }
}
