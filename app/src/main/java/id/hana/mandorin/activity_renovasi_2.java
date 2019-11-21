package id.hana.mandorin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

public class activity_renovasi_2 extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * EditText, Textview, Imageview, CardView & Button
     */
    private EditText nama_bg_awal, nik_bg_awal, no_hp_bg_awal, alamat_bg_awal, tgl_survey;
    private TextView rb_old, data_renovasi, nama_prev , nik_prev, email_statis;
    private Button kirim;
    private CardView back;

    /*
     * Create Web URL Init
     */
    String HttpUrl = "http://mandorin.site/mandorin/php/user/create_renovasi.php";

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
        setContentView(R.layout.activity_renovasi_2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        /*
         * Layout ID Initializations
         */
        nama_bg_awal = findViewById(R.id.input_nama_rn_awal);
        nik_bg_awal = findViewById(R.id.input_nik_rn_awal);
        email_statis = findViewById(R.id.text_statis_email);
        no_hp_bg_awal = findViewById(R.id.input_hp_rn_awal);
        alamat_bg_awal = findViewById(R.id.input_alamat_rn_awal);
        tgl_survey = findViewById(R.id.input_tgl_rn_awal);
        rb_old = findViewById(R.id.rb_old);
        data_renovasi = findViewById(R.id.ren_1_old);
        nama_prev = findViewById(R.id.nama_1_old);
        nik_prev = findViewById(R.id.nik_1_old);
        kirim = findViewById(R.id.button_kirim_rn_awal);
        back = findViewById(R.id.back_activity_renovasi_2);

        /*
         * Binding firebase email first
         */
        email_statis.setText(firebaseUser.getEmail());

        /*
         * SharedPreferences Declaration
         */
        pref = getApplicationContext().getSharedPreferences("data_renovasi", 0);
        nama_prev.setText(pref.getString("nama",null));
        nik_prev.setText(pref.getString("nik",null));
        data_renovasi.setText(pref.getString("data_renovasi", null));
        rb_old.setText(pref.getString("jenis_borongan", null));

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
                    renovasi_dialog();
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
                Intent intent = new Intent(activity_renovasi_2.this, activity_renovasi.class);
                startActivity(intent);
            }
        });
    }

    private void renovasi_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Data Renovasi");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin mengirim data renovasi?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Kirim",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        try
                        {
                            createdata();
                            SharedPreferences.Editor editor;
                            editor=pref.edit();
                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(activity_renovasi_2.this, activity_konfirmasi_bangun_renovasi.class);
                            startActivity(intent);
                        } catch (IllegalArgumentException e)
                        {
                            Toast.makeText(activity_renovasi_2.this, "Proses Gagal!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void createdata() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        Toast.makeText(activity_renovasi_2.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_renovasi_2.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {

                String id = "";
                String nik = nik_bg_awal.getText().toString().trim();
                String nama = nama_bg_awal.getText().toString().trim();
                String email = firebaseUser.getEmail();
                String alamat = alamat_bg_awal.getText().toString().trim();
                String no_hp = no_hp_bg_awal.getText().toString().trim();
                String tgl_sur = tgl_survey.getText().toString().trim();
                String jenis_borongan = rb_old.getText().toString().trim();
                String data_renov = data_renovasi.getText().toString().trim();
                String nik_mandor = nik_prev.getText().toString().trim();
                String nama_mandor = nama_prev.getText().toString().trim();
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
                params.put("data_renovasi", data_renov);
                params.put("status", status);
                params.put("nik_mandor", nik_mandor);
                params.put("nama_mandor", nama_mandor);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_renovasi_2.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}