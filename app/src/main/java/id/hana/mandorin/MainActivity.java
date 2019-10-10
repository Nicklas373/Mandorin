package id.hana.mandorin;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        initViews();
        InternetCheck();
    }

    private void InternetCheck() {
        if(adaInternet()){
            // tampilkan peta
            Toast.makeText(MainActivity.this, "Anda Terhubung ke internet", Toast.LENGTH_LONG).show();
        }else{
            // tampilkan pesan
            Toast.makeText(MainActivity.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }

    private boolean adaInternet(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }

    private void initViews() {
        // setting toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // setting view pager
        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        // setting tabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainFragmentAdapter mainFragmentPagerAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mainFragmentPagerAdapter.addFragment(new fragment_home(), getString(R.string.menu_title_1));
        mainFragmentPagerAdapter.addFragment(new fragment_about(), getString(R.string.menu_title_2));
        viewPager.setAdapter(mainFragmentPagerAdapter);
    }
}
