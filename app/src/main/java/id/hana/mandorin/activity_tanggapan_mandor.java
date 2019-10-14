package id.hana.mandorin;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class activity_tanggapan_mandor extends AppCompatActivity {

    private TextView dummy_old_tg, nama_prev , nik_prev;;
    private EditText nama_tg, lokasi_tg, tg;
    private RadioGroup radio_group;
    private Button kirim_tg;
    private CardView back_tg;

    /*
     * Create Web URL Init
     */
    String HttpUrl = "http://mandorin.site/mandorin/php/user/create_tanggapan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanggapan_mandor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dummy_old_tg = findViewById(R.id.dummy_old);
        nama_tg = findViewById(R.id.input_nama_tg_awal);
        lokasi_tg = findViewById(R.id.input_lokasi_tg_awal);
        tg = findViewById(R.id.input_tanggapan_tg_awal);
        radio_group = findViewById(R.id.rg_tipe);
        back_tg = findViewById(R.id.back_activity_tanggapan_mandor);
        kirim_tg = findViewById(R.id.button_kirim_tg_awal);
        nama_prev = findViewById(R.id.tg_nama_old);
        nik_prev = findViewById(R.id.tg_nik_old);

        /*
         * Passing data from last activity
         */
        final String nik = getIntent().getExtras().getString("nik");
        final String nama = getIntent().getExtras().getString("nama");

        nama_prev.setText(nama);
        nik_prev.setText(nik);

        kirim_tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama_tg.getText().toString().length() == 0) {
                    nama_tg.setError("Harap Masukkan Nama Anda");
                } else if (lokasi_tg.getText().toString().length() == 0) {
                    lokasi_tg.setError("Harap Masukkan Lokasi Pekerjaan");
                } else if (tg.getText().toString().length() == 0) {
                    tg.setError("Harap Masukkan Tanggapan Anda");
                } else {
                        int id = radio_group.getCheckedRadioButtonId();
                        switch (id)
                        {
                            case R.id.test_2:
                                String new_dummy = ((RadioButton)findViewById(id)).getText().toString();
                                dummy_old_tg.setText(new_dummy);
                                break;
                            case R.id.test_3:
                                String new_dummy_2 = ((RadioButton)findViewById(id)).getText().toString();
                                dummy_old_tg.setText(new_dummy_2);
                                break;
                        }
                        try
                        {
                            createdata();
                            Intent intent = new Intent(activity_tanggapan_mandor.this, activity_konfirmasi_bangun_renovasi.class);
                            startActivity(intent);
                        } catch (IllegalArgumentException e)
                        {
                            Toast.makeText(activity_tanggapan_mandor.this, "Proses Gagal!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }
        });

        back_tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_tanggapan_mandor.this, activity_sewa_jasa.class);
                intent.putExtra("nik", nik);
                intent.putExtra("nama", nama);
                startActivity(intent);
            }
        });
    }

    private void createdata() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        Toast.makeText(activity_tanggapan_mandor.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_tanggapan_mandor.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {

                String nama = nama_tg.getText().toString().trim();
                String lokasi = lokasi_tg.getText().toString().trim();
                String tipe_kerja = dummy_old_tg.getText().toString().trim();
                String tanggapan = tg.getText().toString().trim();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String nama_mandor = nama_prev.getText().toString().trim();
                String nik_mandor = nik_prev.getText().toString().trim();

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("nama", nama);
                params.put("lokasi", lokasi);
                params.put("tipe_kerja", tipe_kerja);
                params.put("tgl_saran", date);
                params.put("tanggapan", tanggapan);
                params.put("nik_mandor", nik_mandor);
                params.put("nama_mandor", nama_mandor);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_tanggapan_mandor.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

}
