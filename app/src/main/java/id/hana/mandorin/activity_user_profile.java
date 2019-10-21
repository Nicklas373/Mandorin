package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

public class activity_user_profile extends AppCompatActivity {

    private CardView back_akun;
    private TextView userfname, usermail, userid, userage, userphone, useraddress, userpic_dummy;
    private ImageView  userpic;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    JSONObject json = null;
    String str = "";
    HttpResponse response;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pref = getSharedPreferences("data_akun", Context.MODE_PRIVATE);

        userfname = (TextView) findViewById(R.id.userFullName);
        usermail = (TextView) findViewById(R.id.userEmail);
        userid = (TextView) findViewById(R.id.user_id);
        userage = (TextView) findViewById(R.id.user_age);
        userphone = (TextView) findViewById(R.id.user_telp);
        useraddress = (TextView) findViewById(R.id.user_address);
        userpic_dummy = (TextView) findViewById(R.id.dummy_userpic);
        userpic = (ImageView) findViewById(R.id.img_head_1);
        back_akun = (CardView) findViewById(R.id.back_user_profile);

        usermail.setText(pref.getString ("email", null));

        new GetTextViewData(context).execute();

        back_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_user_profile.this, activity_akun.class);
                startActivity(intent);
            }
        });
    }

    private class GetTextViewData extends AsyncTask<Void, Void, Void>
    {
        public Context context;
        String usermail_2 = usermail.getText().toString();

        public GetTextViewData(Context context)
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
            String adress = "http://mandorin.site/mandorin/php/user/test_read.php?email=" + usermail_2;
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
                userfname.setText(json.getString("nama_lengkap"));
                usermail.setText(json.getString("email"));
                userage.setText(json.getString("umur"));
                userid.setText(json.getString("nik"));
                userphone.setText(json.getString("telp"));
                useraddress.setText(json.getString("alamat"));
                userpic_dummy.setText(json.getString("foto_user"));
                String uri = "@drawable/profil";  // where myresource (without the extension) is the file
                if (userpic_dummy.getText().toString().equalsIgnoreCase("")) {
                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                    Drawable res = getResources().getDrawable(imageResource);
                    userpic.setImageDrawable(res);
                } else {
                    Picasso.get().load(userpic_dummy.getText().toString()).fit().centerCrop() .into(userpic);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
