package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_detail_mandor_baru extends AppCompatActivity {

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView & Button
     */
    private ImageView back;
    private TextView Nama, Umur_1, Alamat_1, Nik_1, Tempat_1, Agama_1, Lama_Kerja_1, user_tgl;
    private CircleImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mandor_baru);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Layout ID Initializations
         * TextView, CardView & Button
         */
        Nama = findViewById(R.id.txt_name);
        Umur_1 = findViewById(R.id.txt_umur_mandor_1);
        Alamat_1 = findViewById(R.id.txt_alamat_mandor_1);
        Nik_1 = findViewById(R.id.txt_nik_mandor_1);
        Tempat_1 = findViewById(R.id.txt_tempat_mandor);
        Agama_1 = findViewById(R.id.txt_agama_mandor_1);
        Lama_Kerja_1 = findViewById(R.id.txt_lama_kerja_mandor_1);
        back = findViewById(R.id.back_activity_mandor_baru);
        image = findViewById(R.id.img_head);
        user_tgl = findViewById(R.id.dummy_user_tgl);

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
         * TextView Initializations
         */
        Nama.setText(nama);
        Nik_1.setText(nik);
        Umur_1.setText(umur + " Tahun");
        Alamat_1.setText(alamat);
        Tempat_1.setText(tempat + " / " + tgl_lahir);
        user_tgl.setText(tgl_lahir);
        Agama_1.setText(agama);
        Lama_Kerja_1.setText(lama_kerja + " Tahun");

        /*
         * Image passing from last activity
         */
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("foto");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        image.setImageBitmap(bmp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = image.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();

                Intent intent = new Intent(activity_detail_mandor_baru.this, activity_profile_mandor.class);
                intent.putExtra("nama", Nama.getText().toString());
                intent.putExtra("umur", Umur_1.getText().toString());
                intent.putExtra("alamat", Alamat_1.getText().toString());
                intent.putExtra("nik", Nik_1.getText().toString());
                intent.putExtra("tempat", Tempat_1.getText().toString());
                intent.putExtra("tgl_lahir", user_tgl.getText().toString());
                intent.putExtra("agama", Agama_1.getText().toString());
                intent.putExtra("lama_kerja", Lama_Kerja_1.getText().toString());
                intent.putExtra("foto", b);

                startActivity(intent);
            }
        });
    }
}
