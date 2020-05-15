package id.hana.mandorin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

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
import java.util.HashMap;
import java.util.Map;

public class activity_login_2 extends AppCompatActivity {

    private TextView reset_akun, cur_email, old_email, old_uid;
    private EditText inputPassword;
    private ImageView next, back;
    private FirebaseAuth auth;
    ProgressDialog dialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    JSONObject json = null;
    String str = "";
    HttpResponse response;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        inputPassword = (EditText) findViewById(R.id.password_login);
        next = (ImageView) findViewById(R.id.next_activity_login_2);
        back = (ImageView) findViewById(R.id.back_activity_login_2);
        reset_akun = (TextView) findViewById(R.id.reset_acc);
        cur_email = (TextView) findViewById(R.id.cur_usermail);
        old_email = (TextView) findViewById(R.id.dummy_usermail);
        old_uid = (TextView) findViewById(R.id.dummy_uid);

        auth = FirebaseAuth.getInstance();

        pref = getApplicationContext().getSharedPreferences("data_akun", 0);

        if(pref.getString("email", null)!=null)
        {
            cur_email.setText(pref.getString("email",null));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internet_available()){
                    startActivity(new Intent(activity_login_2.this, activity_register.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reset_akun.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(internet_available()){
                            startActivity(new Intent(activity_login_2.this, activity_reset_pass.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog = ProgressDialog.show(activity_login_2.this, "Login Akun", "Memproses...", true);
                        final String email_2 = cur_email.getText().toString();
                        final String password = inputPassword.getText().toString();

                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(getApplicationContext(), "Harap Masukkan Password Anda", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        auth.signInWithEmailAndPassword(email_2, password)
                                .addOnCompleteListener(activity_login_2.this, new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {

                                        if (!task.isSuccessful()) {
                                            if (password.length() < 6) {
                                                inputPassword.setError("Password Minimal 6 karakter");
                                            } else {
                                                Toast.makeText(activity_login_2.this, "Login Gagal / Password Salah", Toast.LENGTH_LONG).show();
                                            }
                                            finish();
                                        }
                                        else {
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putString("email", cur_email.getText().toString());
                                            editor.apply();
                                            new activity_login_2.GetUIDData(context).execute();
                                            CheckUID();
                                            Intent intent = new Intent(activity_login_2.this, activity_akun.class);
                                            startActivity(intent);
                                            finish();
                                            Toast.makeText(activity_login_2.this, "Login Berhasil, Selamat Datang! " + email_2, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                });
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }

    private class GetUIDData extends AsyncTask<Void, Void, Void> {
        public Context context;
        String usermail_2 = cur_email.getText().toString();

        public GetUIDData(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String adress = "http://mandorin.site/mandorin/php/user/new/read_data_uid.php?email=" + usermail_2;
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

            try {
                JSONArray jArray = new JSONArray(str);
                json = jArray.getJSONObject(0);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            try {
                String useruid = json.getString("uid");
                if (useruid.equalsIgnoreCase("")) {
                    old_uid.setText("-");
                } else {
                    old_uid.setText(useruid);
                }
                String usermail = json.getString("email");
                if (usermail.equalsIgnoreCase("")) {
                    old_email.setText("-");
                } else {
                    old_email.setText(usermail);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void CheckUID() {
        String last_uid = old_uid.getText().toString();
        String cur_uid = FirebaseInstanceId.getInstance().getToken();

        // Begin Check
        if (last_uid.equals(cur_uid)) {
            //Toast.makeText(activity_login_2.this, "Same ID", Toast.LENGTH_LONG).show();
        } else {
            old_uid.setText(cur_uid);
            update_uid();
        }
    }

    private void update_uid() {
        String usermail_2 = cur_email.getText().toString();
        String HttpUrl = "http://mandorin.site/mandorin/php/user/new/update_data_uid.php?email=" + usermail_2;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Showing response message coming from server.
                        // Toast.makeText(activity_login_2.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Showing error message if something goes wrong.
                        // Toast.makeText(activity_login_2.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String email = cur_email.getText().toString();
                String cur_uid = old_uid.getText().toString();

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("uid", cur_uid);
                params.put("email", email);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_login_2.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}