package id.hana.mandorin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;

public class activity_profile_mandor extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private TextView Nama, Umur, Alamat, Nik, Tempat, Tgl_lahir, Agama, Lama_Kerja;
    private CardView profil_mandor, sewa_jasa, back;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    /*
     * SharedPreferences Usage
     * I want to reduce passing usage, since it seems mess with app runtime sometimes
     */
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_mandor);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        profil_mandor = findViewById(R.id.mandor_menu_1);
        sewa_jasa = findViewById(R.id.mandor_menu_3);
        Nama = findViewById(R.id.nama);
        Umur = findViewById(R.id.umur);
        Alamat = findViewById(R.id.alamat);
        Tempat = findViewById(R.id.text_tempat_profile_mandor);
        Tgl_lahir = findViewById(R.id.text_tgl_lahir_profile_mandor);
        Agama = findViewById(R.id.text_agama_profile_mandor);
        Lama_Kerja = findViewById(R.id.text_lama_kerja_profile_mandor);
        Nik = findViewById(R.id.nik);
        back = findViewById(R.id.back_activity_profile_mandor);

        /*
         * Passing data from last activity
         */
        String nik = getIntent().getExtras().getString("nik");
        String nama = getIntent().getExtras().getString("nama");
        String alamat = getIntent().getExtras().getString("alamat");
        String umur = getIntent().getExtras().getString("umur");
        String tempat = getIntent().getExtras().getString("tempat");
        String tgl_lahir = getIntent().getExtras().getString("tgl_lahir");
        String agama = getIntent().getExtras().getString("agama");
        String lama_kerja = getIntent().getExtras().getString("lama_kerja");

        /*
         * SharedPreferences Declaration
         */
        pref = getApplicationContext().getSharedPreferences("data_mandor", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("nik", nik);
        editor.putString("nama", nama);
        editor.putString("alamat", alamat);
        editor.putString("umur", umur);
        editor.putString("tempat", tempat);
        editor.putString("tgl_lahir", tgl_lahir);
        editor.putString("agama", agama);
        editor.putString("lama_kerja", lama_kerja);
        editor.apply();

        /*
         * TextView Initializations
         */
        Nama.setText(nama);
        Nik.setText(nik);
        Umur.setText(umur);
        Alamat.setText(alamat);
        Tempat.setText(tempat);
        Tgl_lahir.setText(tgl_lahir);
        Agama.setText(agama);
        Lama_Kerja.setText(lama_kerja);

        /*
         * Image passing from last activity
         */
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("foto");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        final ImageView image = findViewById(R.id.foto);
        image.setImageBitmap(bmp);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();

        profil_mandor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable drawable = image.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();

                Intent intent = new Intent(activity_profile_mandor.this, activity_detail_mandor.class);
                /*
                 * Only use passed intent on specific layout
                 */
                intent.putExtra("nama", Nama.getText().toString());
                intent.putExtra("umur", Umur.getText().toString());
                intent.putExtra("alamat", Alamat.getText().toString());
                intent.putExtra("nik", Nik.getText().toString());
                intent.putExtra("tempat", Tempat.getText().toString());
                intent.putExtra("tgl_lahir", Tgl_lahir.getText().toString());
                intent.putExtra("agama", Agama.getText().toString());
                intent.putExtra("lama_kerja", Lama_Kerja.getText().toString());
                intent.putExtra("foto", b);
                startActivity(intent);
            }
        });

        sewa_jasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() != null) {
                    auth.getCurrentUser().reload();
                    if (auth.getCurrentUser().isEmailVerified()) {
                        Intent intent = new Intent(activity_profile_mandor.this,  activity_layanan_jasa.class);
                        startActivity(intent);
                    } else {
                        ver_acc_dialog();
                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity_profile_mandor.this);

                    // set title dialog
                    alertDialogBuilder.setTitle("Menu Layanan Jasa");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Harap Login Untuk Melanjutkan")
                            .setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Login",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog = ProgressDialog.show(activity_profile_mandor.this, "Menu Login", "Memproses...", true);
                                    Intent intent = new Intent(activity_profile_mandor.this, activity_login.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
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
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor;
                editor=pref.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(activity_profile_mandor.this, activity_mandor.class);
                startActivity(intent);
            }
        });
    }

    private void ver_acc_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Verifikasi E-Mail");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Verifikasi e-mail di perlukan untuk mengakses menu ini, verifikasi ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        if(internet_available()){
                            auth.getCurrentUser().sendEmailVerification();
                            Toast.makeText(getApplicationContext(), "E-mail verifikasi telah dikirim ke  " + auth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Harap Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                            finish();
                        }
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

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}