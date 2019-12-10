package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class activity_bantuan extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView telp_user, email_user, web_user;
    private CardView cv_telp, cv_email, cv_web, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_bantuan);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        telp_user = findViewById(R.id.user_text);
        email_user = findViewById(R.id.user_email);
        web_user = findViewById(R.id.user_website);
        back = findViewById(R.id.back_activity_bantuan);
        cv_telp = findViewById(R.id.cv_title_telp);
        cv_email = findViewById(R.id.cv_title_email);
        cv_web = findViewById(R.id.cv_title_website);

        telp_user.setText("+6281398890051");
        email_user.setText("admin@mandorin.site");
        web_user.setText("www.mandorin.site");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_bantuan.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cv_telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telp_user.getText().toString(), null));
                startActivity(intent);
            }
        });

        cv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                composeEmail( "admin@mandorin.site", "Dukungan Mandorin");
            }
        });

        cv_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("http://www.mandorin.site");
            }
        });
    }

    public void composeEmail(String addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:admin@mandorin.site")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
