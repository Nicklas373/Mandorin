package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class activity_data_transaksi extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private EditText Nomor_Kontrak, Nama_Pemesan, No_Telp, Biaya_desain, Biaya_konstruksi, Tgl_input_satu, Tgl_input_dua, Tgl_input_tiga, Tgl_input_empat, Total_biaya;
    private TextView Status_satu, Status_dua, Status_tiga, Status_empat, Presentase;
    private Button Pembayaran, Pembayaran_2;
    private ImageView img_status_pembayaran;
    private CardView back, invoice_1, invoice_2, invoice_3, invoice_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transaksi);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        Nomor_Kontrak = findViewById(R.id.user_input_nomor_kontrak_transaksi);
        Nama_Pemesan = findViewById(R.id.user_input_nama_pemesan_transaksi);
        No_Telp = findViewById(R.id.user_input_no_telp_transaksi);
        Biaya_desain = findViewById(R.id.user_input_biaya_desain);
        Biaya_konstruksi = findViewById(R.id.user_input_biaya_konstruksi);
        Status_satu = findViewById(R.id.user_input_status_1);
        Status_dua = findViewById(R.id.user_input_status_2);
        Status_tiga = findViewById(R.id.user_input_status_3);
        Status_empat = findViewById(R.id.user_input_status_4);
        back = findViewById(R.id.back_activity_data_transaksi);
        Tgl_input_satu = findViewById(R.id.user_input_tgl_1_transaksi);
        Tgl_input_dua = findViewById(R.id.user_input_tgl_2_transaksi);
        Tgl_input_tiga = findViewById(R.id.user_input_tgl_3_transaksi);
        Tgl_input_empat = findViewById(R.id.user_input_tgl_4_transaksi);
        Total_biaya = findViewById(R.id.user_input_biaya_sekarang);
        img_status_pembayaran = findViewById(R.id.image_pembayaran_transaksi);
        Presentase = findViewById(R.id.user_input_presentase);
        Pembayaran = findViewById(R.id.proses_transaksi);
        Pembayaran_2 = findViewById(R.id.proses_transaksi_2);
        invoice_1 = findViewById(R.id.cv_invoice_1);
        invoice_2 = findViewById(R.id.cv_invoice_2);
        invoice_3 = findViewById(R.id.cv_invoice_3);
        invoice_4 = findViewById(R.id.cv_invoice_4);

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

        /*
         * TextView Initializations
         */
        Nomor_Kontrak.setText(nomor_kontrak);
        Nama_Pemesan.setText(nama_pemesan);
        No_Telp.setText(no_telp);
        Tgl_input_satu.setText(tgl_satu);
        Tgl_input_dua.setText(tgl_dua);
        Tgl_input_tiga.setText(tgl_tiga);
        Tgl_input_empat.setText(tgl_empat);
        int b_desain = Integer.parseInt(biaya_desain);
        NumberFormat f_desain = new DecimalFormat("#,###");
        double m_desain = b_desain;
        String f_desain_2 = f_desain.format(m_desain);
        Biaya_desain.setText("Rp." + f_desain_2);
        int b_konstruksi = Integer.parseInt(biaya_konstruksi);
        NumberFormat f_konstruksi = new DecimalFormat("#,###");
        double m_konstruksi = b_konstruksi;
        String f_konstruksi_2 = f_konstruksi.format(m_konstruksi);
        Biaya_konstruksi.setText("Rp." + f_konstruksi_2);
        if (Tgl_input_satu.getText().toString().equalsIgnoreCase("")) {
            Tgl_input_satu.setText("-");
        } else {
            int result = Integer.parseInt(total_satu);
            NumberFormat formatter = new DecimalFormat("#,###");
            double myNumber = result;
            String formattedNumber = formatter.format(myNumber);
            Tgl_input_satu.setText(tgl_satu + " / " + "Rp." + formattedNumber);
        }
        if (Tgl_input_dua.getText().toString().equalsIgnoreCase("")) {
            Tgl_input_dua.setText("Belum Ada Pembayaran");
        } else {
            int result = Integer.parseInt(total_dua);
            NumberFormat formatter = new DecimalFormat("#,###");
            double myNumber = result;
            String formattedNumber = formatter.format(myNumber);
            Tgl_input_dua.setText(tgl_dua + " / " + "Rp." + formattedNumber);
        }
        if (Tgl_input_tiga.getText().toString().equalsIgnoreCase("")) {
            Tgl_input_tiga.setText("Belum Ada Pembayaran");
        } else {
            int result = Integer.parseInt(total_tiga);
            NumberFormat formatter = new DecimalFormat("#,###");
            double myNumber = result;
            String formattedNumber = formatter.format(myNumber);
            Tgl_input_tiga.setText(tgl_tiga + " / " + "Rp." + formattedNumber);
        }
        if (Tgl_input_empat.getText().toString().equalsIgnoreCase("")) {
            Tgl_input_empat.setText("Belum Ada Pembayaran");
        } else {
            int result = Integer.parseInt(total_empat);
            NumberFormat formatter = new DecimalFormat("#,###");
            double myNumber = result;
            String formattedNumber = formatter.format(myNumber);
            Tgl_input_empat.setText(tgl_empat + " / " + "Rp." + formattedNumber);
        }
        Status_satu.setText(status_satu);
        Status_dua.setText(status_dua);
        Status_tiga.setText(status_tiga);
        Status_empat.setText(status_empat);
        invoice_1.setVisibility(View.VISIBLE);
        invoice_2.setVisibility(View.GONE);
        invoice_3.setVisibility(View.GONE);
        invoice_4.setVisibility(View.GONE);
        Presentase.setText(presentase);
        Total_biaya.setText("-");
        Pembayaran.setVisibility(View.GONE);
        Pembayaran_2.setVisibility(View.GONE);
        if (Status_satu.getText().toString().equalsIgnoreCase("Lunas")) {
            if (Presentase.getText().toString().equalsIgnoreCase("50")) {
                img_status_pembayaran.setImageDrawable(getResources().getDrawable(R.drawable.lunas_0, getApplicationContext().getTheme()));
                Total_biaya.setText("-");
                Toast.makeText(getApplicationContext(), "Pembayaran ke 1 anda sudah lunas", Toast.LENGTH_LONG).show();
            }
        }
        if (Status_dua.getText().toString().equalsIgnoreCase("Menunggu")) {
            if (Presentase.getText().toString().equalsIgnoreCase("80")) {
                Pembayaran.setVisibility(View.VISIBLE);
                invoice_2.setVisibility(View.GONE);
                img_status_pembayaran.setImageDrawable(getResources().getDrawable(R.drawable.lunas_1, getApplicationContext().getTheme()));
                int result = Integer.parseInt(total_dua);
                NumberFormat formatter = new DecimalFormat("#,###");
                double myNumber = result;
                String formattedNumber = formatter.format(myNumber);
                Total_biaya.setText("Rp." + formattedNumber);

                Pembayaran.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity_data_transaksi.this, activity_data_transaksi_2.class);
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
        } else if (Status_dua.getText().toString().equalsIgnoreCase("Memproses")) {
            if (Presentase.getText().toString().equalsIgnoreCase("80")) {
                Pembayaran.setVisibility(View.GONE);
                invoice_2.setVisibility(View.GONE);
                img_status_pembayaran.setImageDrawable(getResources().getDrawable(R.drawable.lunas_2, getApplicationContext().getTheme()));
                Total_biaya.setText("Rp. -");
                Toast.makeText(getApplicationContext(), "Pembayaran ke 2 anda sedang di proses", Toast.LENGTH_LONG).show();
            }
        } else if (Status_dua.getText().toString().equalsIgnoreCase("Lunas")) {
            if (Presentase.getText().toString().equalsIgnoreCase("80")) {
                Pembayaran.setVisibility(View.GONE);
                invoice_2.setVisibility(View.VISIBLE);
                img_status_pembayaran.setImageDrawable(getResources().getDrawable(R.drawable.lunas_3, getApplicationContext().getTheme()));
                Total_biaya.setText("-");
                Toast.makeText(getApplicationContext(), "Pembayaran ke 2 anda sudah lunas", Toast.LENGTH_LONG).show();
            }
        }
        if (Status_tiga.getText().toString().equalsIgnoreCase("Menunggu")) {
            if (Presentase.getText().toString().equalsIgnoreCase("95")) {
                Pembayaran_2.setVisibility(View.VISIBLE);

                /* I want to make sure if invoice from second payment is visible when
                 * user already paid or processed
                 */
                if ((Status_dua.getText().toString().equalsIgnoreCase("Lunas")) || (Status_dua.getText().toString().equalsIgnoreCase("Memproses"))) {
                    invoice_2.setVisibility(View.VISIBLE);
                }

                invoice_3.setVisibility(View.GONE);
                img_status_pembayaran.setImageDrawable(getResources().getDrawable(R.drawable.lunas_4, getApplicationContext().getTheme()));
                int result = Integer.parseInt(total_tiga);
                NumberFormat formatter = new DecimalFormat("#,###");
                double myNumber = result;
                String formattedNumber = formatter.format(myNumber);
                Total_biaya.setText("Rp." + formattedNumber);

                Pembayaran_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity_data_transaksi.this, activity_data_transaksi_2.class);
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
        } else if (Status_tiga.getText().toString().equalsIgnoreCase("Memproses")) {
            if (Presentase.getText().toString().equalsIgnoreCase("95")) {
                /* I want to make sure if invoice from second payment is visible when
                 * user already paid or processed
                 */
                if ((Status_dua.getText().toString().equalsIgnoreCase("Lunas")) || (Status_dua.getText().toString().equalsIgnoreCase("Memproses"))) {
                    invoice_2.setVisibility(View.VISIBLE);
                }

                Pembayaran_2.setVisibility(View.GONE);
                invoice_3.setVisibility(View.GONE);
                img_status_pembayaran.setImageDrawable(getResources().getDrawable(R.drawable.lunas_5, getApplicationContext().getTheme()));
                Total_biaya.setText("Rp. -");
                Toast.makeText(getApplicationContext(), "Pembayaran ke 3 anda sedang diproses", Toast.LENGTH_SHORT).show();
            }
        } else if (Status_tiga.getText().toString().equalsIgnoreCase("Lunas")) {
            if (Presentase.getText().toString().equalsIgnoreCase("95")) {

                /* I want to make sure if invoice from second payment is visible when
                 * user already paid or processed
                 */
                if ((Status_dua.getText().toString().equalsIgnoreCase("Lunas")) || (Status_dua.getText().toString().equalsIgnoreCase("Memproses"))) {
                    invoice_2.setVisibility(View.VISIBLE);
                }

                Pembayaran_2.setVisibility(View.GONE);
                invoice_3.setVisibility(View.VISIBLE);
                img_status_pembayaran.setImageDrawable(getResources().getDrawable(R.drawable.lunas_6, getApplicationContext().getTheme()));
                Total_biaya.setText("Rp. -");
                Toast.makeText(getApplicationContext(), "Pembayaran ke 3 anda sudah lunas", Toast.LENGTH_SHORT).show();
            }
        }
        if (Status_empat.getText().toString().equalsIgnoreCase("Menunggu")) {
            if (Presentase.getText().toString().equalsIgnoreCase("100")) {
                Pembayaran_2.setVisibility(View.GONE);
                /* I want to make sure if invoice from second payment is visible when
                 * user already paid or processed
                 */
                if ((Status_dua.getText().toString().equalsIgnoreCase("Lunas")) || (Status_dua.getText().toString().equalsIgnoreCase("Memproses"))) {
                    invoice_2.setVisibility(View.VISIBLE);
                }

                /* I want to make sure if invoice from third payment is visible when
                 * user already paid or processed
                 */
                if ((Status_tiga.getText().toString().equalsIgnoreCase("Lunas")) || (Status_tiga.getText().toString().equalsIgnoreCase("Memproses"))) {
                    invoice_3.setVisibility(View.VISIBLE);
                }

                invoice_4.setVisibility(View.GONE);
                img_status_pembayaran.setImageDrawable(getResources().getDrawable(R.drawable.lunas_6, getApplicationContext().getTheme()));
                int result = Integer.parseInt(total_empat);
                NumberFormat formatter = new DecimalFormat("#,###");
                double myNumber = result;
                String formattedNumber = formatter.format(myNumber);
                Total_biaya.setText("Rp." + formattedNumber);
            }
        } else if (Status_empat.getText().toString().equalsIgnoreCase("Lunas")) {
            if (Presentase.getText().toString().equalsIgnoreCase("100")) {
                Pembayaran_2.setVisibility(View.GONE);
                /* I want to make sure if invoice from second payment is visible when
                 * user already paid or processed
                 */
                if ((Status_dua.getText().toString().equalsIgnoreCase("Lunas")) || (Status_dua.getText().toString().equalsIgnoreCase("Memproses"))) {
                    invoice_2.setVisibility(View.VISIBLE);
                }

                /* I want to make sure if invoice from third payment is visible when
                 * user already paid or processed
                 */
                if ((Status_tiga.getText().toString().equalsIgnoreCase("Lunas")) || (Status_tiga.getText().toString().equalsIgnoreCase("Memproses"))) {
                    invoice_3.setVisibility(View.VISIBLE);
                }

                invoice_4.setVisibility(View.VISIBLE);
                img_status_pembayaran.setImageDrawable(getResources().getDrawable(R.drawable.lunas_7, getApplicationContext().getTheme()));
                Toast.makeText(getApplicationContext(), "Pembayaran ke 4 anda sudah lunas", Toast.LENGTH_SHORT).show();
            }
        }

        invoice_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_transaksi.this, activity_webview_invoice.class);
                intent.putExtra("invoice", "satu");
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

        invoice_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_transaksi.this, activity_webview_invoice.class);
                intent.putExtra("invoice", "dua");
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

        invoice_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_transaksi.this, activity_webview_invoice.class);
                intent.putExtra("invoice", "tiga");
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

        invoice_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_transaksi.this, activity_webview_invoice.class);
                intent.putExtra("invoice", "empat");
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_data_transaksi.this, activity_status_transaksi.class);
                startActivity(intent);
            }
        });
    }
}