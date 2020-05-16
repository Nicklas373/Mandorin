package id.hana.mandorin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class activity_register_3 extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private ImageView back, next;
    private EditText ed_nama, ed_nik, ed_umur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        next = (ImageView) findViewById(R.id.next_activity_register_3);
        back = (ImageView) findViewById(R.id.back_activity_register_3);
        ed_nama = (EditText) findViewById(R.id.isi_nama);
        ed_nik = (EditText) findViewById(R.id.isi_nik);
        ed_umur = (EditText) findViewById(R.id.isi_umur);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ed_nama.getText().toString())) {
                    ed_nama.setError("Harap Masukkan Nama");
                } else if (TextUtils.isEmpty(ed_nik.getText().toString())) {
                    ed_nik.setError("Harap Masukkan NIK");
                } else if (TextUtils.isEmpty(ed_umur.getText().toString())) {
                    ed_umur.setError("Harap Masukkan Umur");
                } else {
                    String nama, umur, nik;

                    nama = ed_nama.getText().toString();
                    umur = ed_umur.getText().toString();
                    nik = ed_nik.getText().toString();

                    Intent intent = new Intent(activity_register_3.this, activity_register_4.class);
                    intent.putExtra("isi_nama", nama);
                    intent.putExtra("isi_umur", umur);
                    intent.putExtra("isi_nik", nik);
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_dialog();
            }
        });
    }

    private void back_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Buat Akun");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin membatalkan pengisian data akun ?")
                .setMessage("Jika anda membatalkan pengisian data akun, anda di haruskan untuk mengisi data anda melalui menu edit akun untuk proses pelayanan jasa")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog = ProgressDialog.show(activity_register_3.this, "Buat Akun", "Kembali ke beranda...", true);
                        dialog.dismiss();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(activity_register_3.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }, 3000L); //3000 L = 3 detik
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}