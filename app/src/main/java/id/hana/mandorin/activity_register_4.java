package id.hana.mandorin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class activity_register_4 extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView back, old_nama, old_nik, old_umur;
    private EditText ed_alamat, ed_telp;
    private Button btn_prs;

    /*
     * Declare other think
     */
    private String SERVER_URL = "http://mandorin.site/mandorin/php/user/create_init_user.php";

    /*
     * Firebase initializations
     */
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_4);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btn_prs = (Button) findViewById(R.id.btn_prs);
        back = (TextView) findViewById(R.id.btn_kembali_reg_4);
        ed_alamat = (EditText) findViewById(R.id.isi_alamat);
        ed_telp = (EditText) findViewById(R.id.isi_telp);
        old_nama = (TextView) findViewById(R.id.dummy_nama_reg_4);
        old_umur = (TextView) findViewById(R.id.dummy_nik_reg_4);
        old_nik = (TextView) findViewById(R.id.dummy_umur_reg_4);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        /*
         * Passing data from last activity
         */
        final String isi_nama = getIntent().getExtras().getString("isi_nama");
        final String isi_umur = getIntent().getExtras().getString("isi_umur");
        final String isi_nik = getIntent().getExtras().getString("isi_nik");

        old_nama.setText(isi_nama);
        old_umur.setText(isi_umur);
        old_nik.setText(isi_nik);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_register_4.this, activity_register_3.class);
                intent.putExtra("isi_nama", isi_nama);
                intent.putExtra("isi_umur", isi_umur);
                intent.putExtra("isi_nik", isi_nik);
                startActivity(intent);
            }
        });

        btn_prs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_alamat.getText().toString().length() == 0) {
                    ed_alamat.setError("Harap Masukkan Data Komplain Anda");
                } else if (ed_telp.getText().toString().length() == 0) {
                    ed_telp.setError("Harap Masukkan Data Komplain Anda");
                } else {
                    reg_dialog();
                }
            }
        });
    }

    private void reg_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Data Akun");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah data yang anda masukkan sudah benar ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Kirim",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        try
                        {
                            update_reg();
                            dialog = ProgressDialog.show(activity_register_4.this, "Menu Daftar Akun", "Memproses Data Akun...", true);
                            Intent intent = new Intent(activity_register_4.this, activity_register_5.class);
                            startActivity(intent);
                        } catch (IllegalArgumentException e)
                        {
                            Toast.makeText(activity_register_4.this, "Proses Gagal!", Toast.LENGTH_SHORT).show();
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

    private void update_reg() {
        final String email_1 = firebaseUser.getEmail();
        String adress = "http://mandorin.site/mandorin/php/user/new/update_data_user.php?id=" + email_1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, adress,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        //Toast.makeText(activity_data_transaksi_2.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_register_4.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                String nama = old_nama.getText().toString();
                String umur = old_umur.getText().toString();
                String nik = old_nik.getText().toString();
                String telp = ed_telp.getText().toString();
                String email = firebaseUser.getEmail();
                String alamat = ed_alamat.getText().toString();

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("nama_lengkap", nama);
                params.put("umur", umur);
                params.put("nik", nik);
                params.put("telp", telp);
                params.put("alamat", alamat);
                params.put("foto_user", "akun.png");
                params.put("email", email);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_register_4.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}