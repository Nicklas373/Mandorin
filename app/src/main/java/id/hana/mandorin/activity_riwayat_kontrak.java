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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class activity_riwayat_kontrak extends AppCompatActivity {

    /*
     * Recyclerview & Data Adapter Initialization
     */
    List<GetRiwayatKontrakAdapter> GetDataAdapter4;
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
    String JSON_ID= "id";
    String JSON_NOMOR_KONTRAK= "nomor_kontrak";
    String JSON_NAMA_PEMESAN = "nama_pemesan";
    String JSON_EMAIL_PEMESAN = "email";
    String JSON_ALAMAT_PEMESAN = "alamat";
    String JSON_STATUS_PEMESAN = "status";
    String JSON_DATA_PEMESAN = "data_pemesan";
    String JSON_REKAP_DATA_PEMESAN = "rekap_data";
    String JSON_SURAT_KONTRAK_PEMESAN = "surat_kontrak";
    String JSON_DESAIN_RUMAH_PEMESAN = "desain_rumah";
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView con_text_pemesan_pembayaran_renovasi;
    private ImageView connection_pemesan_pembayaran_renovasi;
    private Button refresh_pemesan_pembayaran_renovasi;
    private CardView back_pemesan_pembayaran_renovasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_riwayat_kontrak);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        /*
         * Recyclerview Layout Initialization
         */
        GetDataAdapter4 = new ArrayList<>();
        recyclerView2 = findViewById(R.id.recyclerview_riwayat_kontrak);
        recyclerView2.setHasFixedSize(true);
        recyclerViewlayoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(recyclerViewlayoutManager2);

        /*
         * Layout ID Initializations
         * Textview, Imageview & Button
         */
        con_text_pemesan_pembayaran_renovasi = findViewById(R.id.con_text_riwayat_kontrak);
        connection_pemesan_pembayaran_renovasi = findViewById(R.id.con_image_riwayat_kontrak);
        refresh_pemesan_pembayaran_renovasi = findViewById(R.id.refresh_riwayat_kontrak);
        back_pemesan_pembayaran_renovasi = findViewById(R.id.back_activity_riwayat_kontrak);

        /*
         * Internet Connection Module
         * Check user internet before main function run is needed to avoid
         * Null pointer access
         */
        cek_internet();

        refresh_pemesan_pembayaran_renovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cek_internet();
            }
        });

        back_pemesan_pembayaran_renovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_riwayat_kontrak.this, activity_kontrak.class);
                startActivity(intent);
            }
        });
    }

    private void cek_internet() {
        if(internet_available()){
            connection_pemesan_pembayaran_renovasi.setVisibility(View.GONE);
            con_text_pemesan_pembayaran_renovasi.setVisibility(View.GONE);
            refresh_pemesan_pembayaran_renovasi.setVisibility(View.GONE);
            JSON_DATA_WEB_CALL();
        }else{
            connection_pemesan_pembayaran_renovasi.setVisibility(View.VISIBLE);
            con_text_pemesan_pembayaran_renovasi.setVisibility(View.VISIBLE);
            refresh_pemesan_pembayaran_renovasi.setVisibility(View.VISIBLE);
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
        String GET_JSON_DATA_HTTP_URL = "http://mandorin.site/mandorin/php/user/read_riwayat_kontrak.php?email=" + email;

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetRiwayatKontrakAdapter GetDataAdapter3 = new GetRiwayatKontrakAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter3.setId(json.getString(JSON_ID));
                GetDataAdapter3.setNomor_kontrak(json.getString(JSON_NOMOR_KONTRAK));
                GetDataAdapter3.setNama_pemesan(json.getString(JSON_NAMA_PEMESAN));
                GetDataAdapter3.setEmail(json.getString(JSON_EMAIL_PEMESAN));
                GetDataAdapter3.setAlamat(json.getString(JSON_ALAMAT_PEMESAN));
                GetDataAdapter3.setStatus(json.getString(JSON_STATUS_PEMESAN));
                GetDataAdapter3.setData_pemesan(json.getString(JSON_DATA_PEMESAN));
                GetDataAdapter3.setRekap_data(json.getString(JSON_REKAP_DATA_PEMESAN));
                GetDataAdapter3.setSurat_kontrak(json.getString(JSON_SURAT_KONTRAK_PEMESAN));
                GetDataAdapter3.setDesain_rumah(json.getString(JSON_DESAIN_RUMAH_PEMESAN));
            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter4.add(GetDataAdapter3);
        }

        recyclerViewadapter2 = new recycler_view_riwayat_kontrak(GetDataAdapter4, this);

        recyclerView2.setAdapter(recyclerViewadapter2);
    }
}