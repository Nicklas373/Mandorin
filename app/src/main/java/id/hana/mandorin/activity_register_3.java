package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class activity_register_3 extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView back;
    private EditText ed_nama, ed_nik, ed_umur;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btn_next = (Button) findViewById(R.id.next_data);
        back = (TextView) findViewById(R.id.btn_batal_data);
        ed_nama = (EditText) findViewById(R.id.isi_nama);
        ed_nik = (EditText) findViewById(R.id.isi_nik);
        ed_umur = (EditText) findViewById(R.id.isi_umur);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ed_nama.getText().toString())) {
                    ed_nama.setError("Harap Masukkan Nama");
                } else if (TextUtils.isEmpty(ed_nik.getText().toString())) {
                    ed_nik.setError("Harap Masukkan NIK");
                } else if (TextUtils.isEmpty(ed_umur.getText().toString())) {
                    ed_umur.setError("Harap Masukkan Umur");
                } else {
                    String nama, umur, nik;

                    nama = ed_nama.getText().toString();
                    umur = ed_umur.getText().toString();
                    nik = ed_nik.getText().toString();

                    Intent intent = new Intent(activity_register_3.this, activity_register_4.class);
                    intent.putExtra("isi_nama", nama);
                    intent.putExtra("isi_umur", umur);
                    intent.putExtra("isi_nik", nik);
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_register_3.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}