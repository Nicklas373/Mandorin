package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class activity_webview_invoice extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView con_text_webview_invoice;
    private ImageView connection_webview_invoice;
    private Button refresh_webview_invoice;
    private CardView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_invoice);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * Textview, Imageview & Button
         */
        con_text_webview_invoice = findViewById(R.id.con_text_webview_invoice);
        connection_webview_invoice = findViewById(R.id.con_image_webview_invoice);
        refresh_webview_invoice = findViewById(R.id.refresh_webview_invoice);
        back = findViewById(R.id.back_activity_webview_invoice);
        final WebView web = (WebView) findViewById(R.id.web_view);

        /*
         * Passing data from last activity
         */
        final String id = getIntent().getExtras().getString("id");
        final String nomor_kontrak = getIntent().getExtras().getString("nomor_kontrak");
        final String nama_pemesan = getIntent().getExtras().getString("nama_pemesan");
        final String email = getIntent().getExtras().getString("email");
        final String alamat = getIntent().getExtras().getString("alamat");
        final String no_telp = getIntent().getExtras().getString("no_telp");
        final String biaya_desain = getIntent().getExtras().getString("biaya_desain");
        final String biaya_konstruksi = getIntent().getExtras().getString("biaya_konstruksi");
        final String no_rekening = getIntent().getExtras().getString("no_rekening");
        final String status_desain = getIntent().getExtras().getString("status_desain");
        final String status_satu = getIntent().getExtras().getString("status_satu");
        final String status_dua = getIntent().getExtras().getString("status_dua");
        final String status_tiga = getIntent().getExtras().getString("status_tiga");
        final String status_empat = getIntent().getExtras().getString("status_empat");
        final String total_satu = getIntent().getExtras().getString("total_satu");
        final String total_dua = getIntent().getExtras().getString("total_dua");
        final String total_tiga = getIntent().getExtras().getString("total_tiga");
        final String total_empat = getIntent().getExtras().getString("total_empat");
        final String tgl_desain = getIntent().getExtras().getString("tgl_input_desain");
        final String tgl_satu = getIntent().getExtras().getString("tgl_input_satu");
        final String tgl_dua = getIntent().getExtras().getString("tgl_input_dua");
        final String tgl_tiga = getIntent().getExtras().getString("tgl_input_tiga");
        final String tgl_empat = getIntent().getExtras().getString("tgl_input_empat");
        final String bukti_satu = getIntent().getExtras().getString("bukti_satu");
        final String bukti_dua = getIntent().getExtras().getString("bukti_dua");
        final String bukti_tiga = getIntent().getExtras().getString("bukti_tiga");
        final String bukti_empat = getIntent().getExtras().getString("bukti_empat");
        final String bukti_desain = getIntent().getExtras().getString("bukti_desain");
        final String presentase = getIntent().getExtras().getString("presentase");
        final String invoice = getIntent().getExtras().getString("invoice");

        if(internet_available()){
            connection_webview_invoice.setVisibility(View.GONE);
            con_text_webview_invoice.setVisibility(View.GONE);
            refresh_webview_invoice.setVisibility(View.GONE);

            String url = "http://mandorin.site/administrator/invoice/" + invoice + "/" + nomor_kontrak;
            WebSettings webSettings = web.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            web.getSettings().setBuiltInZoomControls(true);
            web.getSettings().setDisplayZoomControls(false);
            web.loadUrl(url);
            web.setWebViewClient(new WebViewClient());
        } else {
            web.setVisibility(View.GONE);
            connection_webview_invoice.setVisibility(View.VISIBLE);
            con_text_webview_invoice.setVisibility(View.VISIBLE);
            refresh_webview_invoice.setVisibility(View.VISIBLE);
        }

        refresh_webview_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://mandorin.site/administrator/invoice/" + invoice + "/" + nomor_kontrak;
                WebSettings webSettings = web.getSettings();
                webSettings.setJavaScriptEnabled(true);
                web.loadUrl(url);
                web.setWebViewClient(new WebViewClient());

                if(internet_available()){
                    connection_webview_invoice.setVisibility(View.GONE);
                    con_text_webview_invoice.setVisibility(View.GONE);
                    refresh_webview_invoice.setVisibility(View.GONE);

                    web.setVisibility(View.VISIBLE);
                } else {
                    web.setVisibility(View.GONE);
                    connection_webview_invoice.setVisibility(View.VISIBLE);
                    con_text_webview_invoice.setVisibility(View.VISIBLE);
                    refresh_webview_invoice.setVisibility(View.VISIBLE);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_webview_invoice.this, activity_data_transaksi.class);
                intent.putExtra("id", id);
                intent.putExtra("nomor_kontrak", nomor_kontrak);
                intent.putExtra("nama_pemesan", nama_pemesan);
                intent.putExtra("email", email);
                intent.putExtra("alamat", alamat);
                intent.putExtra("no_telp", no_telp);
                intent.putExtra("biaya_desain", biaya_desain);
                intent.putExtra("biaya_konstruksi", biaya_konstruksi);
                intent.putExtra("no_rekening", no_rekening);
                intent.putExtra("status_desain", status_desain);
                intent.putExtra("status_satu", status_satu);
                intent.putExtra("status_dua", status_dua);
                intent.putExtra("status_tiga", status_tiga);
                intent.putExtra("status_empat", status_empat);
                intent.putExtra("total_satu", total_satu);
                intent.putExtra("total_dua", total_dua);
                intent.putExtra("total_tiga", total_tiga);
                intent.putExtra("total_empat", total_empat);
                intent.putExtra("tgl_input_satu", tgl_satu);
                intent.putExtra("tgl_input_dua", tgl_dua);
                intent.putExtra("tgl_input_tiga", tgl_tiga);
                intent.putExtra("tgl_input_empat", tgl_empat);
                intent.putExtra("tgl_input_desain", tgl_desain);
                intent.putExtra("bukti_satu", bukti_satu);
                intent.putExtra("bukti_dua", bukti_dua);
                intent.putExtra("bukti_tiga", bukti_tiga);
                intent.putExtra("bukti_empat", bukti_empat);
                intent.putExtra("bukti_desain", bukti_desain);
                intent.putExtra("presentase", presentase);
                startActivity(intent);
            }
        });
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }

    private void reload(){
        WebView web = (WebView) findViewById(R.id.web_view);
        final String invoice = getIntent().getExtras().getString("invoice");
        final String nomor_kontrak = getIntent().getExtras().getString("nomor_kontrak");
        String url = "http://mandorin.site/administrator/invoice/" + invoice + "/" + nomor_kontrak;
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setDisplayZoomControls(false);
        web.loadUrl(url);
        web.setWebViewClient(new WebViewClient());
    }
}