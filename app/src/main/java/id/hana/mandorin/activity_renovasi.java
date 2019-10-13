package id.hana.mandorin;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

public class activity_renovasi extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView, RadioGroup & Button
     */
    private TextView dummy;
    private EditText data_renovasi;
    private RadioGroup rg;
    private RadioButton rb, rb_2, rb_debug;
    private Button selanjutnya;
    private CardView back;

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
         * Passing data from last activity
         */
        final String nik = getIntent().getExtras().getString("nik");
        final String nama = getIntent().getExtras().getString("nama");

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
                            Bundle bundle = new Bundle();
                            bundle.putString("nik", nik);
                            bundle.putString("nama", nama);
                            bundle.putString("data_renovasi", data_renovasi.getText().toString());
                            bundle.putString("jenis_borongan", dummy.getText().toString());
                            Intent intent = new Intent(activity_renovasi.this, activity_renovasi_2.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_renovasi.this, activity_sewa_jasa.class);
                intent.putExtra("nik", nik);
                intent.putExtra("nama", nama);
                startActivity(intent);
            }
        });
    }
}
