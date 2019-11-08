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

public class activity_pemesan_bangun_dari_awal extends AppCompatActivity {

    /*
     * Recyclerview & Data Adapter Initialization
     */
    List<GetBangunDariAwalAdapter> GetDataAdapter5;
    RecyclerView recyclerView3;
    RecyclerView.LayoutManager recyclerViewlayoutManager3;
    RecyclerView.Adapter recyclerViewadapter3;

    /*
     * Firebase initializations
     */
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    /*
     * JSON Data Initialization
     */

    String JSON_ID_PEMESAN = "id";
    String JSON_NIK_PEMESAN = "nik";
    String JSON_NAMA_PEMESAN = "nama";
    String JSON_EMAIL_PEMESAN = "email";
    String JSON_ALAMAT_PEMESAN = "alamat";
    String JSON_JENIS_BORONGAN = "jenis_borongan";
    String JSON_LUAS_TANAH = "luas_tanah";
    String JSON_DESAIN_RUMAH = "desain_rumah";
    String JSON_STATUS = "status";
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView con_text_pemesan_bangun_dari_awal;
    private ImageView connection_pemesan_bangun_dari_awal;
    private Button refresh_pemesan_bangun_dari_awal;
    private CardView back_pemesan_bangun_dari_awal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesan_bangun_dari_awal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        /*
         * Recyclerview Layout Initialization
         */
        GetDataAdapter5 = new ArrayList<>();
        recyclerView3 = findViewById(R.id.recyclerview_pemesan_bangun_dari_awal);
        recyclerView3.setHasFixedSize(true);
        recyclerViewlayoutManager3 = new LinearLayoutManager(this);
        recyclerView3.setLayoutManager(recyclerViewlayoutManager3);

        /*
         * Layout ID Initializations
         * Textview, Imageview & Button
         */
        con_text_pemesan_bangun_dari_awal = findViewById(R.id.con_text_pemesan_bangun_dari_awal);
        connection_pemesan_bangun_dari_awal= findViewById(R.id.con_image_pemesan_bangun_dari_awal);
        refresh_pemesan_bangun_dari_awal= findViewById(R.id.refresh_pemesan_bangun_dari_awal);
        back_pemesan_bangun_dari_awal= findViewById(R.id.back_activity_pemesan_bangun_dari_awal);

        /*
         * Internet Connection Module
         * Check user internet before main function run is needed to avoid
         * Null pointer access
         */
        cek_internet();

        refresh_pemesan_bangun_dari_awal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cek_internet();
            }
        });

        back_pemesan_bangun_dari_awal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_pemesan_bangun_dari_awal.this, activity_data_pemesan.class);
                startActivity(intent);
            }
        });
    }

    private void cek_internet() {
        if(internet_available()){
            connection_pemesan_bangun_dari_awal.setVisibility(View.GONE);
            con_text_pemesan_bangun_dari_awal.setVisibility(View.GONE);
            refresh_pemesan_bangun_dari_awal.setVisibility(View.GONE);
            JSON_DATA_WEB_CALL();
        }else{
            connection_pemesan_bangun_dari_awal.setVisibility(View.VISIBLE);
            con_text_pemesan_bangun_dari_awal.setVisibility(View.VISIBLE);
            refresh_pemesan_bangun_dari_awal.setVisibility(View.VISIBLE);
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
        String GET_JSON_DATA_HTTP_URL = "http://mandorin.site/mandorin/php/user/read_pemesan_bangun_dari_awal.php?email=" + email;

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

            GetBangunDariAwalAdapter GetDataAdapter3 = new GetBangunDariAwalAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter3.setId(json.getString(JSON_ID_PEMESAN));
                GetDataAdapter3.setNik(json.getString(JSON_NIK_PEMESAN));
                GetDataAdapter3.setNama(json.getString(JSON_NAMA_PEMESAN));
                GetDataAdapter3.setEmail(json.getString(JSON_EMAIL_PEMESAN));
                GetDataAdapter3.setAlamat(json.getString(JSON_ALAMAT_PEMESAN));
                GetDataAdapter3.setJenis_borongan(json.getString(JSON_JENIS_BORONGAN));
                GetDataAdapter3.setLuas_tanah(json.getString(JSON_LUAS_TANAH));
                GetDataAdapter3.setDesain_rumah(json.getString(JSON_DESAIN_RUMAH));
                GetDataAdapter3.setStatus(json.getString(JSON_STATUS));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter5.add(GetDataAdapter3);
        }

        recyclerViewadapter3 = new recycler_view_pemesan_bangun_dari_awal(GetDataAdapter5, this);

        recyclerView3.setAdapter(recyclerViewadapter3);
    }
}