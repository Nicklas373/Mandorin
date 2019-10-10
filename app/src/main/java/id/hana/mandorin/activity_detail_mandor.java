package id.hana.mandorin;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_detail_mandor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mandor);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String nik = getIntent().getExtras().getString("nik");
        String nama = getIntent().getExtras().getString("nama");
        String alamat = getIntent().getExtras().getString("alamat");
        String umur = getIntent().getExtras().getString("umur");
        TextView Nama = findViewById(R.id.txt_name);
        TextView Umur = findViewById(R.id.txt_umur);
        TextView Nama_1 = findViewById(R.id.txt_nama_mandor_1);
        TextView Umur_1 = findViewById(R.id.txt_umur_mandor_1);
        TextView Alamat_1 = findViewById(R.id.txt_alamat_mandor_1);
        TextView Nik_1 = findViewById(R.id.txt_nik_mandor_1);
        Nama.setText(nama);
        Nama_1.setText(nama);
        Nik_1.setText(nik);
        Umur.setText(umur);
        Umur_1.setText(umur);
        Alamat_1.setText(alamat);

        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("foto");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        final ImageView image = findViewById(R.id.img_head);
        image.setImageBitmap(bmp);
    }
}