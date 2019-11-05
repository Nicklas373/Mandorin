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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class activity_pemesan_renovasi extends AppCompatActivity {

    /*
     * Recyclerview & Data Adapter Initialization
     */
    List<GetRenovasiDataAdapter> GetDataAdapter4;
    RecyclerView recyclerView2;
    RecyclerView.LayoutManager recyclerViewlayoutManager2;
    RecyclerView.Adapter recyclerViewadapter2;

    /*
     * JSON Data Initialization
     */
    String GET_JSON_DATA_HTTP_URL = "http://mandorin.site/mandorin/php/user/read_pemesan_renovasi.php";
    String JSON_ID_PEMESAN = "id";
    String JSON_NIK_PEMESAN = "nik";
    String JSON_NAMA_PEMESAN = "nama";
    String JSON_EMAIL_PEMESAN = "email";
    String JSON_ALAMAT_PEMESAN = "alamat";
    String JSON_JENIS_BORONGAN = "jenis_borongan";
    String JSON_DATA_RENOVASI = "data_renovasi";
    String JSON_STATUS = "status";
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView con_text_pemesan_renovasi;
    private ImageView connection_pemesan_renovasi;
    private Button refresh_pemesan_renovasi;
    private CardView back_pemesan_renovasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_pemesan_renovasi);

        /*
         * Recyclerview Layout Initialization
         */
        GetDataAdapter4 = new ArrayList<>();
        recyclerView2 = findViewById(R.id.recyclerview_pemesan_renovasi);
        recyclerView2.setHasFixedSize(true);
        recyclerViewlayoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(recyclerViewlayoutManager2);

        /*
         * Layout ID Initializations
         * Textview, Imageview & Button
         */
        con_text_pemesan_renovasi = findViewById(R.id.con_text_pemesan_renovasi);
        connection_pemesan_renovasi = findViewById(R.id.con_image_pemesan_renovasi);
        refresh_pemesan_renovasi = findViewById(R.id.refresh_pemesan_renovasi);
        back_pemesan_renovasi = findViewById(R.id.back_activity_pemesan_renovasi);

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

        back_pemesan_renovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_pemesan_renovasi.this, activity_data_pemesan.class);
                startActivity(intent);
            }
        });
    }

    private void cek_internet() {
        if(internet_available()){
            connection_pemesan_renovasi.setVisibility(View.GONE);
            con_text_pemesan_renovasi.setVisibility(View.GONE);
            refresh_pemesan_renovasi.setVisibility(View.GONE);
            JSON_DATA_WEB_CALL();
        }else{
            connection_pemesan_renovasi.setVisibility(View.VISIBLE);
            con_text_pemesan_renovasi.setVisibility(View.VISIBLE);
            refresh_pemesan_renovasi.setVisibility(View.VISIBLE);
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

            GetRenovasiDataAdapter GetDataAdapter3 = new GetRenovasiDataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter3.setId(json.getString(JSON_ID_PEMESAN));
                GetDataAdapter3.setNik(json.getString(JSON_NIK_PEMESAN));
                GetDataAdapter3.setNama(json.getString(JSON_NAMA_PEMESAN));
                GetDataAdapter3.setEmail(json.getString(JSON_EMAIL_PEMESAN));
                GetDataAdapter3.setAlamat(json.getString(JSON_ALAMAT_PEMESAN));
                GetDataAdapter3.setJenis_borongan(json.getString(JSON_JENIS_BORONGAN));
                GetDataAdapter3.setData_renovasi(json.getString(JSON_DATA_RENOVASI));
                GetDataAdapter3.setStatus(json.getString(JSON_STATUS));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter4.add(GetDataAdapter3);
        }

        recyclerViewadapter2 = new recycler_view_pemesan_renovasi(GetDataAdapter4, this);

        recyclerView2.setAdapter(recyclerViewadapter2);
    }
}