package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class activity_data_pembayaran_bangun_dari_awal extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView Nomor_Kontrak, Nama_Pemesan, No_Telp, Pembayaran_1, Pembayaran_2, Pembayaran_3, tgl_1, tgl_2, tgl_3, Status_1, Status_2, Status_3;
    private Button Pembayaran;
    private CardView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pembayaran_bangun_dari_awal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        Nomor_Kontrak = findViewById(R.id.user_input_nomor_kontrak);
        Nama_Pemesan = findViewById(R.id.user_input_nama_pemesan);
        No_Telp = findViewById(R.id.user_input_no_telp);
        Pembayaran_1 = findViewById(R.id.user_input_pembayaran_1);
        Pembayaran_2 = findViewById(R.id.user_input_pembayaran_2);
        Pembayaran_3 = findViewById(R.id.user_input_pembayaran_3);
        tgl_1 = findViewById(R.id.user_input_tgl_1);
        tgl_2 = findViewById(R.id.user_input_tgl_2);
        tgl_3 = findViewById(R.id.user_input_tgl_3);
        back = findViewById(R.id.back_activity_data_pembayaran_bangun_dari_awal);
        Status_1 = findViewById(R.id.user_input_status_1);
        Status_2 = findViewById(R.id.user_input_status_2);
        Status_3 = findViewById(R.id.user_input_status_3);
        Pembayaran = findViewById(R.id.pembayaran_bangun_dari_awal);

        /*
         * Passing data from last activity
         */
        final String id = getIntent().getExtras().getString("id");
        final String nomor_kontrak = getIntent().getExtras().getString("nomor_kontrak");
        final String nama_pemesan = getIntent().getExtras().getString("nama_pemesan");
        final String email = getIntent().getExtras().getString("email");
        final String no_telp = getIntent().getExtras().getString("no_telp");
        final String total_pembayaran = getIntent().getExtras().getString("total_pembayaran");
        final String no_rekening = getIntent().getExtras().getString("no_rekening");
        final String status_satu = getIntent().getExtras().getString("status_satu");
        final String status_dua = getIntent().getExtras().getString("status_dua");
        final String status_tiga = getIntent().getExtras().getString("status_tiga");
        final String total_satu = getIntent().getExtras().getString("total_satu");
        final String total_dua = getIntent().getExtras().getString("total_dua");
        final String total_tiga = getIntent().getExtras().getString("total_tiga");
        final String tgl_satu = getIntent().getExtras().getString("tgl_input_satu");
        final String tgl_dua = getIntent().getExtras().getString("tgl_input_dua");
        final String tgl_tiga = getIntent().getExtras().getString("tgl_input_tiga");
        final String bukti_satu = getIntent().getExtras().getString("bukti_satu");
        final String bukti_dua = getIntent().getExtras().getString("bukti_dua");
        final String bukti_tiga = getIntent().getExtras().getString("bukti_tiga");

        /*
         * TextView Initializations
         */
        Nomor_Kontrak.setText(nomor_kontrak);
        Nama_Pemesan.setText(nama_pemesan);
        No_Telp.setText(no_telp);
        Status_1.setText(status_satu);
        if (Status_1.getText().toString().equalsIgnoreCase("Menunggu")) {
            Status_1.setBackgroundColor(Color.LTGRAY);
            Status_1.setTextColor(Color.BLACK);
        } else if (Status_1.getText().toString().equalsIgnoreCase("Lunas")) {
            Status_1.setBackgroundColor(Color.BLUE);
            Status_1.setTextColor(Color.WHITE);
        } else {
            Status_1.setBackgroundColor(Color.WHITE);
            Status_1.setTextColor(Color.BLACK);
        }
        Status_2.setText(status_dua);
        if (Status_2.getText().toString().equalsIgnoreCase("Menunggu")) {
            Status_2.setBackgroundColor(Color.LTGRAY);
            Status_2.setTextColor(Color.BLACK);
        } else if (Status_2.getText().toString().equalsIgnoreCase("Lunas")) {
            Status_2.setBackgroundColor(Color.BLUE);
            Status_2.setTextColor(Color.WHITE);
        } else if (Status_2.getText().toString().equalsIgnoreCase("Memproses")) {
            Status_2.setBackgroundColor(Color.YELLOW);
            Status_2.setTextColor(Color.BLACK);
        } else {
            Status_2.setBackgroundColor(Color.WHITE);
            Status_2.setTextColor(Color.BLACK);
        }
        Status_3.setText(status_tiga);
        if (Status_3.getText().toString().equalsIgnoreCase("Menunggu")) {
            Status_3.setBackgroundColor(Color.LTGRAY);
            Status_3.setTextColor(Color.BLACK);
        } else if (Status_3.getText().toString().equalsIgnoreCase("Lunas")) {
            Status_3.setBackgroundColor(Color.BLUE);
            Status_3.setTextColor(Color.WHITE);
        } else {
            Status_3.setBackgroundColor(Color.WHITE);
            Status_3.setTextColor(Color.BLACK);
        }
        int result = Integer.parseInt(total_satu);
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = result;
        String formattedNumber = formatter.format(myNumber);
        int result_2 = Integer.parseInt(total_dua);
        NumberFormat formatter_2 = new DecimalFormat("#,###");
        double myNumber_2 = result_2;
        String formattedNumber_2 = formatter_2.format(myNumber_2);
        int result_3 = Integer.parseInt(total_dua);
        NumberFormat formatter_3 = new DecimalFormat("#,###");
        double myNumber_3 = result_3;
        String formattedNumber_3 = formatter_3.format(myNumber_3);
        Pembayaran_1.setText("Rp." + formattedNumber);
        Pembayaran_2.setText("Rp." + formattedNumber_2);
        Pembayaran_3.setText("Rp." + formattedNumber_3);
        tgl_1.setText(tgl_satu);
        if (tgl_1.getText().toString().equalsIgnoreCase("Kosong")) {
            tgl_1.setText("");
        } else {
            tgl_1.setBackgroundColor(Color.WHITE);
            tgl_1.setTextColor(Color.BLACK);
        }
        tgl_2.setText(tgl_dua);
        if (tgl_2.getText().toString().equalsIgnoreCase("Kosong")) {
            tgl_2.setText("");
        } else {
            tgl_2.setBackgroundColor(Color.WHITE);
            tgl_2.setTextColor(Color.BLACK);
        }
        tgl_3.setText(tgl_tiga);
        if (tgl_3.getText().toString().equalsIgnoreCase("Kosong")) {
            tgl_3.setText("");
        } else {
            tgl_3.setBackgroundColor(Color.WHITE);
            tgl_3.setTextColor(Color.BLACK);
        }

        Pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Status_2.getText().toString().equalsIgnoreCase("Menunggu")) {
                    Intent intent = new Intent(activity_data_pembayaran_bangun_dari_awal.this, activity_data_pembayaran_bangun_dari_awal_2.class);
                    intent.putExtra("id", id);
                    intent.putExtra("nomor_kontrak", nomor_kontrak);
                    intent.putExtra("nama_pemesan", nama_pemesan);
                    intent.putExtra("email", email);
                    intent.putExtra("no_telp", no_telp);
                    intent.putExtra("total_pembayaran", total_pembayaran);
                    intent.putExtra("no_rekening", no_rekening);
                    intent.putExtra("status_satu", status_satu);
                    intent.putExtra("status_dua", status_dua);
                    intent.putExtra("status_tiga", status_tiga);
                    intent.putExtra("total_satu", total_satu);
                    intent.putExtra("total_dua", total_dua);
                    intent.putExtra("total_tiga", total_tiga);
                    intent.putExtra("tgl_input_satu", tgl_satu);
                    intent.putExtra("tgl_input_dua", tgl_dua);
                    intent.putExtra("tgl_input_tiga", tgl_tiga);
                    intent.putExtra("bukti_satu", bukti_satu);
                    intent.putExtra("bukti_dua", bukti_dua);
                    intent.putExtra("bukti_tiga", bukti_tiga);
                    startActivity(intent);
                } else if (Status_2.getText().toString().equalsIgnoreCase("Lunas")){
                    Toast.makeText(getApplicationContext(), "Pembayaran ke 2 anda sudah lunas", Toast.LENGTH_SHORT).show();
                } else if (Status_2.getText().toString().equalsIgnoreCase("Memproses")) {
                    Toast.makeText(getApplicationContext(), "Pembayaran ke 2 anda sedang dalam proses", Toast.LENGTH_SHORT).show();
                } else if (Status_3.getText().toString().equalsIgnoreCase("Lunas")){
                    Toast.makeText(getApplicationContext(), "Pembayaran anda sudah lunas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_pembayaran_bangun_dari_awal.this, activity_pemesan_pembayaran_bangun_dari_awal.class);
                startActivity(intent);
            }
        });
    }
}