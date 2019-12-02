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

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_user_profile extends AppCompatActivity {

    private CardView back_akun;
    private TextView userfname, usermail, userid, userage, userphone, useraddress, userpic_dummy;
    private CircleImageView userpic;
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
        userpic = (CircleImageView) findViewById(R.id.img_head_1);
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
                    userfname.setText("-");
                } else {
                    userfname.setText(json.getString("nama_lengkap"));
                }
                String usermail_2 = json.getString("email");
                if (usermail_2.equalsIgnoreCase("")) {
                    usermail.setText("-");
                } else {
                    usermail.setText(usermail_2);
                }
                String userage_2 = json.getString("umur");
                if (userage_2.equalsIgnoreCase("")) {
                    userage.setText("-");
                } else {
                    userage.setText(userage_2 + " Tahun");
                }
                String userid_2 = json.getString("nik");
                if (userid_2.equalsIgnoreCase("")) {
                    userid.setText("-");
                } else {
                    userid.setText(userid_2);
                }
                String userphone_2 = json.getString("telp");
                if (userphone_2.equalsIgnoreCase("")) {
                    userphone.setText("-");
                } else {
                    userphone.setText(userphone_2);
                }
                String useraddress_2 = json.getString("alamat");
                if (useraddress_2.equalsIgnoreCase("")) {
                    useraddress.setText("-");
                } else {
                    useraddress.setText(useraddress_2);
                }
                userpic_dummy.setText(json.getString("foto_user"));
                String uri = "@drawable/profil";  // where myresource (without the extension) is the file
                if (userpic_dummy.getText().toString().equalsIgnoreCase("")) {
                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                    Drawable res = getResources().getDrawable(imageResource);
                    userpic.setImageDrawable(res);
                } else {
                    String user_photo = "http://mandorin.site/mandorin/uploads/" + userpic_dummy.getText().toString();
                    Picasso.get().load(user_photo).fit().centerCrop() .into(userpic);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
