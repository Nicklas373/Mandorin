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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class activity_mandor extends AppCompatActivity {

    /*
     * Recyclerview & Data Adapter Initialization
     */
    List<GetDataAdapter> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;

    /*
     * JSON Data Initialization
     */
    String GET_JSON_DATA_HTTP_URL = "http://mandorin.site/mandorin/php/user/new/read_data_mandor.php";
    String JSON_NAMA_MANDOR = "nama_mandor";
    String JSON_NIK_MANDOR = "nik";
    String JSON_UMUR_MANDOR = "umur_mandor";
    String JSON_ALAMAT_MANDOR = "alamat_mandor";
    String JSON_FOTO_MANDOR = "foto_mandor";
    String JSON_TGL_LAHIR = "tgl_lahir";
    String JSON_TEMPAT = "tempat";
    String JSON_AGAMA = "agama";
    String JSON_LAMA_KERJA = "lama_kerja";
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
     private TextView con_text;
     private ImageView connection;
     private CardView back, refresh_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_mandor);

        /*
         * Recyclerview Layout Initialization
         */
        GetDataAdapter1 = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        /*
         * Layout ID Initializations
         * Textview, Imageview & Button
         */
        con_text = findViewById(R.id.con_text);
        connection = findViewById(R.id.con_image);
        refresh_cv = findViewById(R.id.refresh_activity_data_mandor);
        back = findViewById(R.id.back_activity_mandor);

        /*
         * Internet Connection Module
         * Check user internet before main function run is needed to avoid
         * Null pointer access
         */
        cek_internet();

        refresh_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cek_internet();
            }
        });

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cek_internet();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_mandor.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void cek_internet() {
        if (internet_available()) {
            JSON_DATA_WEB_CALL();
            recyclerView.setVisibility(View.VISIBLE);
            connection.setVisibility(View.GONE);
            con_text.setVisibility(View.GONE);
        } else {
            /* Silence this debugging */
            // Toast.makeText(activity_data_komplain.this, "Harap periksa koneksi internet anda", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.GONE);
            connection.setVisibility(View.VISIBLE);
            con_text.setVisibility(View.VISIBLE);
        }
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }

    public void JSON_DATA_WEB_CALL(){

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
                        if (recyclerViewlayoutManager.getItemCount() == 0) {
                            JSON_PARSE_DATA_AFTER_WEBCALL(response);
                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        recyclerView.setVisibility(View.GONE);
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setNik_mandor(json.getString(JSON_NIK_MANDOR));
                GetDataAdapter2.setNama_mandor(json.getString(JSON_NAMA_MANDOR));
                GetDataAdapter2.setUmur_mandor(json.getString(JSON_UMUR_MANDOR));
                GetDataAdapter2.setAlamat_mandor(json.getString(JSON_ALAMAT_MANDOR));
                GetDataAdapter2.setTempat(json.getString(JSON_TEMPAT));
                GetDataAdapter2.setTgl_lahir(json.getString(JSON_TGL_LAHIR));
                GetDataAdapter2.setAgama(json.getString(JSON_AGAMA));
                GetDataAdapter2.setLama_kerja(json.getString(JSON_LAMA_KERJA));
                GetDataAdapter2.setFoto_mandor("http://www.mandorin.site/mandorin/image/mandor/" + json.getString(JSON_FOTO_MANDOR));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}