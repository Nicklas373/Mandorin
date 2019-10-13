package id.hana.mandorin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.pm.ActivityInfo;
import android.widget.EditText;
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


public class activity_bangun_dari_awal_2 extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * EditText, Textview, Imageview, CardView & Button
     */
    private EditText nama_bg_awal, nik_bg_awal, email_bg_awal, no_hp_bg_awal, alamat_bg_awal, tgl_survey;
    private TextView rb_old, luas_old, desain_old, nama_prev , nik_prev;
    private Button kirim;
    private CardView back;

    /*
     * Create Web URL Init
     */
    String HttpUrl = "http://mandorin.site/mandorin/php/user/create.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangun_dari_awal_2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         */
        nama_bg_awal = findViewById(R.id.input_nama_bg_awal);
        nik_bg_awal = findViewById(R.id.input_nik_bg_awal);
        email_bg_awal = findViewById(R.id.input_email_bg_awal);
        no_hp_bg_awal = findViewById(R.id.input_hp_bg_awal);
        alamat_bg_awal = findViewById(R.id.input_alamat_bg_awal);
        tgl_survey = findViewById(R.id.input_tgl_bg_awal);
        rb_old = findViewById(R.id.rb_old);
        luas_old = findViewById(R.id.luas_old);
        desain_old = findViewById(R.id.desain_old);
        nama_prev = findViewById(R.id.nama_old);
        nik_prev = findViewById(R.id.nik_old);
        kirim = findViewById(R.id.button_kirim_bg_awal);
        back = findViewById(R.id.back_activity_bangun_dari_awal_2);

        /*
         * Passing data from last activity
         */
        if(getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            nama_prev.setText(bundle.getString("nama"));
            nik_prev.setText(bundle.getString("nik"));
            luas_old.setText(bundle.getString("luas_tanah"));
            rb_old.setText(bundle.getString("jenis_borongan"));
            desain_old.setText(bundle.getString("desain_rumah"));
        } else {
            nama_prev.setText(getIntent().getStringExtra("nama"));
            nik_prev.setText(getIntent().getStringExtra("nik"));
            luas_old.setText(getIntent().getStringExtra("luas_tanah" + " m"));
            rb_old.setText(getIntent().getStringExtra("data1"));
            desain_old.setText(getIntent().getStringExtra("data2"));
        }

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama_bg_awal.getText().toString().length() == 0) {
                    nama_bg_awal.setError("Harap Masukkan Nama");
                } else if (nik_bg_awal.getText().toString().length() == 0) {
                    nik_bg_awal.setError("Harap Masukkan NIK");
                } else if (email_bg_awal.getText().toString().length() == 0) {
                    email_bg_awal.setError("Harap Masukkan Email");
                } else if (no_hp_bg_awal.getText().toString().length() == 0) {
                    no_hp_bg_awal.setError("Harap Masukkan No HP");
                } else if (alamat_bg_awal.getText().toString().length() == 0) {
                    alamat_bg_awal.setError("Harap Masukkan Alamat");
                } else if (tgl_survey.getText().toString().length() == 0) {
                    tgl_survey.setError("Harap Masukkan Tanggal Survey");
                } else {
                    createdata();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_bangun_dari_awal_2.this, activity_bangun_dari_awal.class);
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
                        Toast.makeText(activity_bangun_dari_awal_2.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_bangun_dari_awal_2.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {

                String substring = desain_old.getText().toString();
                String desain = substring.substring(substring.lastIndexOf("/")+1);
                String DS = ("https://www.mandorin.site/mandorin/upload/" + desain);
                String nik = nik_bg_awal.getText().toString().trim();
                String nama = nama_bg_awal.getText().toString().trim();
                String email = email_bg_awal.getText().toString().trim();
                String alamat = alamat_bg_awal.getText().toString().trim();
                String no_hp = no_hp_bg_awal.getText().toString().trim();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String tgl_sur = tgl_survey.getText().toString().trim();
                String jenis_borongan = rb_old.getText().toString().trim();
                String luas = luas_old.getText().toString().trim();
                String nama_mandor = nama_prev.getText().toString().trim();
                String nik_mandor = nik_prev.getText().toString().trim();

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("nik", nik);
                params.put("nama", nama);
                params.put("email", email);
                params.put("no_hp", no_hp);
                params.put("alamat", alamat);
                params.put("tgl_daftar", date);
                params.put("tgl_survey", tgl_sur);
                params.put("jenis_borongan", jenis_borongan);
                params.put("luas_tanah", luas);
                params.put("desain_rumah", DS);
                params.put("nik_mandor", nik_mandor);
                params.put("nama_mandor", nama_mandor);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_bangun_dari_awal_2.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}