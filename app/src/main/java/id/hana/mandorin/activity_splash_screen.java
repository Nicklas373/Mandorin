package id.hana.mandorin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

public class activity_splash_screen extends AppCompatActivity {

    TextView tvSplash, app_ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //menghilangkan ActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        tvSplash = (TextView) findViewById(R.id.tvSplash);
        app_ver = (TextView) findViewById(R.id.app_ver);

        app_ver.setText(BuildConfig.VERSION_NAME);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), activity_welcome.class));
                finish();
            }
        }, 3000L); //3000 L = 3 detik
    }
}