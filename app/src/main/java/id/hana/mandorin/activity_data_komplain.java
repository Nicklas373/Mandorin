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

public class activity_data_komplain extends AppCompatActivity {

    /*
     * Recyclerview & Data Adapter Initialization
     */
    List<GetDataKomplainAdapter> GetDataAdapter4;
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
    String JSON_EMAIL_KONTRAK = "email";
    String JSON_ALAMAT_KONTRAK = "alamat";
    String JSON_KOMPLAIN_KONTRAK = "komplain";
    String JSON_STATUS_KOMPLAIN_KONTRAK = "status_komplain";
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView con_text_pemesan_renovasi, empty_data_komplain_text;
    private ImageView connection_pemesan_renovasi, empty_data_komplain;
    private CardView back_pemesan_renovasi, refresh_pemesan_renovasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_data_komplain);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        /*
         * Recyclerview Layout Initialization
         */
        GetDataAdapter4 = new ArrayList<>();
        recyclerView2 = findViewById(R.id.recyclerview_data_komplain);
        recyclerView2.setHasFixedSize(true);
        recyclerViewlayoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(recyclerViewlayoutManager2);

        /*
         * Layout ID Initializations
         * Textview, Imageview & Button
         */
        con_text_pemesan_renovasi = findViewById(R.id.con_text_komplain);
        connection_pemesan_renovasi = findViewById(R.id.con_image_komplain);
        refresh_pemesan_renovasi = findViewById(R.id.refresh_activity_data_komplain);
        back_pemesan_renovasi = findViewById(R.id.back_activity_data_komplain);
        empty_data_komplain = findViewById(R.id.empty_data_komplain);
        empty_data_komplain_text = findViewById(R.id.empty_data_komplain_text);

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
                Intent intent = new Intent(activity_data_komplain.this, activity_kontrak.class);
                startActivity(intent);
            }
        });
    }

    private void cek_internet() {
        if (internet_available()) {
            JSON_DATA_WEB_CALL();
            recyclerView2.setVisibility(View.VISIBLE);
            connection_pemesan_renovasi.setVisibility(View.GONE);
            con_text_pemesan_renovasi.setVisibility(View.GONE);
        } else {
            /* Silence this debugging */
            // Toast.makeText(activity_data_komplain.this, "Harap periksa koneksi internet anda", Toast.LENGTH_LONG).show();
            recyclerView2.setVisibility(View.GONE);
            connection_pemesan_renovasi.setVisibility(View.VISIBLE);
            con_text_pemesan_renovasi.setVisibility(View.VISIBLE);
            empty_data_komplain.setVisibility(View.GONE);
            empty_data_komplain_text.setVisibility(View.GONE);
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
        String GET_JSON_DATA_HTTP_URL = "http://mandorin.site/mandorin/php/user/new/read_data_komplain.php?email=" + email;

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        /* FIXME:
                         * I want to make sure if data on the list isn't empty, if list
                         * is empty then then app should check status from the imageview
                         * which is tell the situation from the internet connection is available
                         * or not.
                         *
                         * NOTES:
                         * If list data already called before then that mean internet connection from the user still working
                         * so don't need to recall to fetch data from the server again.
                         * THIS NEED PROPER FIX WHICH SHOULD RE-LOAD DATA IF CALL TO LOAD DATA, OR IT'LL DUPLICATE THE LIST.
                         *
                         * END OF FIXME
                         */
                        if (recyclerViewlayoutManager2.getItemCount() == 0) {
                            JSON_PARSE_DATA_AFTER_WEBCALL(response);
                        } else {

                        }

                        empty_data_komplain.setVisibility(View.GONE);
                        empty_data_komplain_text.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        recyclerView2.setVisibility(View.GONE);
                        empty_data_komplain.setVisibility(View.VISIBLE);
                        empty_data_komplain_text.setVisibility(View.VISIBLE);
                    }
                });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetDataKomplainAdapter GetDataAdapter3 = new GetDataKomplainAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter3.setId(json.getString(JSON_ID_KONTRAK));
                GetDataAdapter3.setNomor_kontrak(json.getString(JSON_NOMOR_KONTRAK));
                GetDataAdapter3.setEmail(json.getString(JSON_EMAIL_KONTRAK));
                GetDataAdapter3.setAlamat(json.getString(JSON_ALAMAT_KONTRAK));
                GetDataAdapter3.setKomplain(json.getString(JSON_KOMPLAIN_KONTRAK));
                GetDataAdapter3.setStatus_komplain(json.getString(JSON_STATUS_KOMPLAIN_KONTRAK));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetDataAdapter4.add(GetDataAdapter3);
        }
        recyclerViewadapter2 = new recycler_view_data_komplain(GetDataAdapter4, this);
        recyclerView2.setAdapter(recyclerViewadapter2);
    }
}