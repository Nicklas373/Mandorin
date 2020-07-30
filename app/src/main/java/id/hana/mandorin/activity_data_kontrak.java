package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class activity_data_kontrak extends AppCompatActivity {

    /*
     * Recyclerview & Data Adapter Initialization
     */
    List<GetDataKontrakAdapter> GetDataAdapter4;
    RecyclerView recyclerView2;
    RecyclerView.LayoutManager recyclerViewlayoutManager2;
    RecyclerView.Adapter recyclerViewadapter2;

    /*
     * Firebase initializations
     */
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    /*
     * JSON Data Initialization
     */

    String JSON_ID_KONTRAK = "id";
    String JSON_NOMOR_KONTRAK = "nomor_kontrak";
    String JSON_NAMA_KONTRAK = "nama_pemesan";
    String JSON_EMAIL_KONTRAK = "email";
    String JSON_NO_TELP_KONTRAK = "no_telp";
    String JSON_ALAMAT_KONTRAK = "alamat_pekerjaan";
    String JSON_STATUS_KONTRAK = "status_pekerjaan";
    String JSON_PRESENTASE_KONTRAK  = "presentase";
    String JSON_WAKTU_MULAI_KONTRAK  = "waktu_mulai";
    String JSON_WAKTU_AKHIR_KONTRAK  = "waktu_akhir";
    String JSON_DATA_DESAIN_KONTRAK = "data_desain";
    String JSON_DATA_REKAP_KONTRAK= "data_rekap";
    String JSON_SURAT_KONTRAK = "surat_kontrak";
    String JSON_ROLE_KONTRAK = "role_kontrak";
    String JSON_ROLE_KOMPLAIN = "role_komplain";
    String JSON_REKAP_ADMIN = "rekap_admin";
    String JSON_NAMA_MANDOR = "nama_mandor";
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView con_text_pemesan_renovasi, empty_data_kontrak_text;
    private ImageView connection_pemesan_renovasi, empty_data_kontrak;
    private CardView back_pemesan_renovasi, refresh_pemesan_renovasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_data_kontrak);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        /*
         * Recyclerview Layout Initialization
         */
        GetDataAdapter4 = new ArrayList<>();
        recyclerView2 = findViewById(R.id.recyclerview_data_kontrak);
        recyclerView2.setHasFixedSize(true);
        recyclerViewlayoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(recyclerViewlayoutManager2);

        /*
         * Layout ID Initializations
         * Textview, Imageview & Button
         */
        con_text_pemesan_renovasi = findViewById(R.id.con_text_data_kontrak);
        connection_pemesan_renovasi = findViewById(R.id.con_image_data_kontrak);
        refresh_pemesan_renovasi = findViewById(R.id.refresh_activity_data_kontrak);
        back_pemesan_renovasi = findViewById(R.id.back_activity_data_kontrak);
        empty_data_kontrak = findViewById(R.id.empty_data_kontrak);
        empty_data_kontrak_text = findViewById(R.id.empty_data_kontrak_text);

        /*
         * Internet Connection Module
         * Check user internet before main function run is needed to avoid
         * Null pointer access
         */
        cek_internet();

        refresh_pemesan_renovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cek_internet();
            }
        });

        connection_pemesan_renovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cek_internet();
            }
        });

        back_pemesan_renovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_kontrak.this, activity_kontrak.class);
                startActivity(intent);
            }
        });
    }

    private void cek_internet() {
        if (internet_available()) {
            connection_pemesan_renovasi.setVisibility(View.GONE);
            con_text_pemesan_renovasi.setVisibility(View.GONE);
            JSON_DATA_WEB_CALL();
        } else {
            Toast.makeText(activity_data_kontrak.this, "Harap periksa koneksi internet anda", Toast.LENGTH_LONG).show();
            connection_pemesan_renovasi.setVisibility(View.VISIBLE);
            con_text_pemesan_renovasi.setVisibility(View.VISIBLE);
        }
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }

    public void JSON_DATA_WEB_CALL(){
        /*
         * Let's separate this json for sorting
         */
        String email = firebaseUser.getEmail();
        String GET_JSON_DATA_HTTP_URL = "http://mandorin.site/mandorin/php/user/new/read_data_kontrak.php?email=" + email;

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                        empty_data_kontrak.setVisibility(View.INVISIBLE);
                        empty_data_kontrak_text.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        empty_data_kontrak.setVisibility(View.VISIBLE);
                        empty_data_kontrak_text.setVisibility(View.VISIBLE);
                    }
                });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetDataKontrakAdapter GetDataAdapter3 = new GetDataKontrakAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter3.setId(json.getString(JSON_ID_KONTRAK));
                GetDataAdapter3.setNomor_kontrak(json.getString(JSON_NOMOR_KONTRAK));
                GetDataAdapter3.setNama_pemesan(json.getString(JSON_NAMA_KONTRAK));
                GetDataAdapter3.setEmail(json.getString(JSON_EMAIL_KONTRAK));
                GetDataAdapter3.setNo_telp(json.getString(JSON_NO_TELP_KONTRAK));
                GetDataAdapter3.setAlamat_pekerjaan(json.getString(JSON_ALAMAT_KONTRAK));
                GetDataAdapter3.setStatus_pekerjaan(json.getString(JSON_STATUS_KONTRAK));
                GetDataAdapter3.setPresentase(json.getString(JSON_PRESENTASE_KONTRAK));
                GetDataAdapter3.setWaktu_mulai(json.getString(JSON_WAKTU_MULAI_KONTRAK));
                GetDataAdapter3.setWaktu_akhir(json.getString(JSON_WAKTU_AKHIR_KONTRAK));
                GetDataAdapter3.setData_desain(json.getString(JSON_DATA_DESAIN_KONTRAK));
                GetDataAdapter3.setData_rekap(json.getString(JSON_DATA_REKAP_KONTRAK));
                GetDataAdapter3.setSurat_kontrak(json.getString(JSON_SURAT_KONTRAK));
                GetDataAdapter3.setRole_kontrak(json.getString(JSON_ROLE_KONTRAK));
                GetDataAdapter3.setRole_komplain(json.getString(JSON_ROLE_KOMPLAIN));
                GetDataAdapter3.setRekap_admin(json.getString(JSON_REKAP_ADMIN));
                GetDataAdapter3.setNama_mandor(json.getString(JSON_NAMA_MANDOR));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter4.add(GetDataAdapter3);
        }

        recyclerViewadapter2 = new recycler_view_data_kontrak(GetDataAdapter4, this);
        recyclerView2.setAdapter(recyclerViewadapter2);
    }
}