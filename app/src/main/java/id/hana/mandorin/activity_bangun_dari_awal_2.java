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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;


public class activity_bangun_dari_awal_2 extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * EditText, Textview, Imageview, CardView & Button
     */
    private EditText nama_bg_awal, nik_bg_awal, no_hp_bg_awal, alamat_bg_awal, tgl_survey;
    private TextView rb_old, luas_old, desain_old, nama_prev , nik_prev, ba_email_statis;
    private Button kirim;
    private CardView back;

    /*
     * Create Web URL Init
     */
    String HttpUrl = "http://mandorin.site/mandorin/php/user/create.php";

    /*
     * SharedPreferences Usage
     * I want to reduce passing usage, since it seems mess with app runtime sometimes
     */
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    /*
     * Firebase initializations
     */
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangun_dari_awal_2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        /*
         * Layout ID Initializations
         */
        nama_bg_awal = findViewById(R.id.input_nama_bg_awal);
        nik_bg_awal = findViewById(R.id.input_nik_bg_awal);
        ba_email_statis = findViewById(R.id.ba_statis_email);
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
         * Binding firebase email first
         */
        ba_email_statis.setText(firebaseUser.getEmail());

        /*
         * SharedPreferences Declaration
         */
        pref = getApplicationContext().getSharedPreferences("data_bangun_dari_awal", 0);
        nama_prev.setText(pref.getString("nama",null));
        nik_prev.setText(pref.getString("nik",null));
        luas_old.setText(pref.getString("luas_tanah", null));
        rb_old.setText(pref.getString("jenis_borongan", null));
        desain_old.setText(pref.getString("desain_rumah", null));

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama_bg_awal.getText().toString().length() == 0) {
                    nama_bg_awal.setError("Harap Masukkan Nama");
                } else if (nik_bg_awal.getText().toString().length() == 0) {
                    nik_bg_awal.setError("Harap Masukkan NIK");
                } else if (no_hp_bg_awal.getText().toString().length() == 0) {
                    no_hp_bg_awal.setError("Harap Masukkan No HP");
                } else if (alamat_bg_awal.getText().toString().length() == 0) {
                    alamat_bg_awal.setError("Harap Masukkan Alamat");
                } else if (tgl_survey.getText().toString().length() == 0) {
                    tgl_survey.setError("Harap Masukkan Tanggal Survey");
                } else {
                    try
                    {
                        createdata();
                        SharedPreferences.Editor editor;
                        editor=pref.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(activity_bangun_dari_awal_2.this, activity_konfirmasi_bangun_renovasi.class);
                        startActivity(intent);
                    } catch (IllegalArgumentException e)
                    {
                        Toast.makeText(activity_bangun_dari_awal_2.this, "Proses Gagal!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor;
                editor=pref.edit();
                editor.clear();
                editor.apply();
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

                String id = "";
                String substring = desain_old.getText().toString();
                String desain = substring.substring(substring.lastIndexOf("/")+1);
                // regex filename, so it'll look same for filename and filepath that'll inserted to database
                String regex = desain.replaceAll("\\s", "");
                String nik = nik_bg_awal.getText().toString().trim();
                String nama = nama_bg_awal.getText().toString().trim();
                String email = firebaseUser.getEmail();
                String alamat = alamat_bg_awal.getText().toString().trim();
                String no_hp = no_hp_bg_awal.getText().toString().trim();
                String tgl_sur = tgl_survey.getText().toString().trim();
                String jenis_borongan = rb_old.getText().toString().trim();
                String luas = luas_old.getText().toString().trim();
                String nama_mandor = nama_prev.getText().toString().trim();
                String nik_mandor = nik_prev.getText().toString().trim();
                String status= "pending";

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("id", id);
                params.put("nik", nik);
                params.put("nama", nama);
                params.put("email", email);
                params.put("no_hp", no_hp);
                params.put("alamat", alamat);
                params.put("tgl_survey", tgl_sur);
                params.put("jenis_borongan", jenis_borongan);
                params.put("luas_tanah", luas);
                params.put("desain_rumah", regex);
                params.put("status", status);
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