package id.hana.mandorin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class activity_layanan_jasa extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * EditText, Textview, Imageview, CardView & Button
     */
    private EditText alamat_lj, tgl_survey, data_lj, nama_lj, nik_lj, no_hp_lj, ba_email_statis;
    private TextView dummy_1, dummy_2, nama_mandor , nik_mandor;
    private RadioGroup rg, rg_2;
    private ImageView kirim, datepicker;
    private CardView back;
    private int mYear, mMonth, mDay;
    ProgressDialog dialog;

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

    // Json parser Initializations
    JSONObject json = null;
    String str = "";
    HttpResponse response;
    Context context;

    /*
     * Create Web URL Init
     */
    String HttpUrl = "http://mandorin.site/mandorin/php/user/new/insert_data_pemesan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_jasa);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        /*
         * Layout ID Initializations
         */
          nama_lj = findViewById(R.id.nama_pemesan_layanan_jasa);
          nik_lj = findViewById(R.id.nik_pemesan_layanan_jasa);
          no_hp_lj = findViewById(R.id.hp_pemesan_layanan_jasa);
          ba_email_statis = findViewById(R.id.layanan_statis_email);
          alamat_lj = findViewById(R.id.input_alamat_layanan_jasa);
          tgl_survey = findViewById(R.id.input_tgl_layanan_jasa);
          data_lj = findViewById(R.id.input_data_layanan_jasa);
          dummy_1 = findViewById(R.id.dummy_1);
          dummy_2 = findViewById(R.id.dummy_2);
          nama_mandor = findViewById(R.id.nama_mandor);
          nik_mandor = findViewById(R.id.nik_mandor);
          rg = findViewById(R.id.rg_borongan);
          rg_2 = findViewById(R.id.rg_desain);
          kirim = findViewById(R.id.kirim_activity_layanan_jasa);
          back = findViewById(R.id.back_activity_layanan_jasa);
          datepicker = findViewById(R.id.kalender);

        /*
         * Binding firebase email first
         */
        ba_email_statis.setText(firebaseUser.getEmail());

        /*
         * SharedPreferences Declaration
         */
        pref = getApplicationContext().getSharedPreferences("data_mandor", 0);
        final String nik = pref.getString("nik",null);
        final String nama = pref.getString("nama",null);

        /*
         * Binding mandor name and nik
         */
        nama_mandor.setText(nama);
        nik_mandor.setText(nik);

        /* Grab current user data for name, nik & call number directly from
         * Current table in database
         */
        new activity_layanan_jasa.GetUserAccountData(context).execute();

        /*
         * Set automated current date for survey date on this input
         * User now should can input that date by datepicker not
         * Manually written on EditText
         */
        SimpleDateFormat CurDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String date = CurDate.format(Calendar.getInstance().getTime());
        tgl_survey.setText(date);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data();
            }
        });

        tgl_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor;
                editor = pref.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(activity_layanan_jasa.this, activity_mandor.class);
                startActivity(intent);
            }
        });
    }

    private void datepicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tgl_survey.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void send_data(){
            if (nama_lj.getText().toString().length() == 0) {
                nama_lj.setError("Harap Masukkan Nama");
                Toast.makeText(getApplicationContext(), "Harap tidak ada data yang kosong", Toast.LENGTH_SHORT).show();
            } else if (nik_lj.getText().toString().length() == 0) {
                nik_lj.setError("Harap Masukkan NIK");
                Toast.makeText(getApplicationContext(), "Harap tidak ada data yang kosong", Toast.LENGTH_SHORT).show();
            } else if (no_hp_lj.getText().toString().length() == 0) {
                no_hp_lj.setError("Harap Masukkan No HP");
                Toast.makeText(getApplicationContext(), "Harap tidak ada data yang kosong", Toast.LENGTH_SHORT).show();
            } else if (alamat_lj.getText().toString().length() == 0) {
                alamat_lj.setError("Harap Masukkan Alamat");
                Toast.makeText(getApplicationContext(), "Harap tidak ada data yang kosong", Toast.LENGTH_SHORT).show();
            } else if (tgl_survey.getText().toString().length() == 0) {
                tgl_survey.setError("Harap Masukkan Tanggal Survey");
                Toast.makeText(getApplicationContext(), "Harap tidak ada data yang kosong", Toast.LENGTH_SHORT).show();
            } else if (data_lj.getText().toString().length() == 0){
                data_lj.setError("Harap Masukkan Data Pekerjaan");
                Toast.makeText(getApplicationContext(), "Harap tidak ada data yang kosong", Toast.LENGTH_SHORT).show();
            } else {
                int id = rg.getCheckedRadioButtonId();
                int id_2 = rg_2.getCheckedRadioButtonId();
                if(id == R.id.rb_1) {
                    String new_dummy = ((RadioButton) findViewById(id)).getText().toString();
                    dummy_1.setText(new_dummy);
                }  else if (id == R.id.rb_2) {
                    String new_dummy_2 = ((RadioButton) findViewById(id)).getText().toString();
                    dummy_1.setText(new_dummy_2);
                }
                if(id_2 == R.id.rb_3) {
                    String next_dummy = ((RadioButton) findViewById(id_2)).getText().toString();
                    dummy_2.setText(next_dummy);
                }  else if (id_2 == R.id.rb_4) {
                    String next_dummy_2 = ((RadioButton) findViewById(id_2)).getText().toString();
                    dummy_2.setText(next_dummy_2);
                }
                send_dialog();
            }
    }

    private void send_dialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Kirim Data");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin memproses layanan?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Proses",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog = ProgressDialog.show(activity_layanan_jasa.this, "Kirim Data", "Mengirim Data...", true);
                        insert_data_pemesan();
                        SharedPreferences.Editor editor;
                        editor = pref.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(activity_layanan_jasa.this, activity_konfirmasi_bangun_renovasi.class);
                        startActivity(intent);
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

    private void insert_data_pemesan() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        // Toast.makeText(activity_layanan_jasa.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        // Toast.makeText(activity_layanan_jasa.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {

                String id = "";
                String nik = nik_lj.getText().toString();
                String nama = nama_lj.getText().toString();
                String email = ba_email_statis.getText().toString();
                String no_hp = no_hp_lj.getText().toString();
                String alamat = alamat_lj.getText().toString();
                String tgl_sur = tgl_survey.getText().toString();
                String jenis_borongan = dummy_1.getText().toString();
                String data_desain = dummy_2.getText().toString();
                String data_keterangan = data_lj.getText().toString();
                String status= "pending";
                String nik_mandor_1 = nik_mandor.getText().toString().trim();
                String nama_mandor_1 = nama_mandor.getText().toString().trim();
                SimpleDateFormat CurDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                String tanggal = CurDate.format(Calendar.getInstance().getTime());

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
                params.put("data_desain", data_desain);
                params.put("data_keterangan", data_keterangan);
                params.put("status", status);
                params.put("nik_mandor", nik_mandor_1);
                params.put("nama_mandor", nama_mandor_1);
                params.put("tgl_pemesan", tanggal);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_layanan_jasa.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private class GetUserAccountData extends AsyncTask<Void, Void, Void>
    {
        public Context context;
        String usermail_2 = ba_email_statis.getText().toString();

        public GetUserAccountData(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            String adress = "http://mandorin.site/mandorin/php/user/new/read_data_user.php?email=" + usermail_2;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost(adress);

            try {
                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                JSONArray jArray = new JSONArray(str);
                json = jArray.getJSONObject(0);

            } catch ( JSONException e) {
                e.printStackTrace();
            }

            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            try {
                String username = json.getString("nama_lengkap");
                if (username.equalsIgnoreCase("")) {
                    nama_lj.setText("-");
                } else {
                    nama_lj.setText(json.getString("nama_lengkap"));
                }
                String userid_2 = json.getString("nik");
                if (userid_2.equalsIgnoreCase("")) {
                    nik_lj.setText("-");
                } else {
                    nik_lj.setText(userid_2);
                }
                String userphone_2 = json.getString("telp");
                if (userphone_2.equalsIgnoreCase("")) {
                    no_hp_lj.setText("-");
                } else {
                    no_hp_lj.setText(userphone_2);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}