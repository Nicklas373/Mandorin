package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class activity_profile_mandor extends AppCompatActivity {

    private CardView profil_mandor, sewa_jasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_mandor);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        profil_mandor = findViewById(R.id.mandor_menu_1);
        sewa_jasa = findViewById(R.id.mandor_menu_3);

        String nik = getIntent().getExtras().getString("nik");
        String nama = getIntent().getExtras().getString("nama");
        String alamat = getIntent().getExtras().getString("alamat");
        String umur = getIntent().getExtras().getString("umur");
        final TextView Nama = findViewById(R.id.nama);
        final TextView Umur = findViewById(R.id.umur);
        final TextView Alamat = findViewById(R.id.alamat);
        final TextView Nik = findViewById(R.id.nik);
        Nama.setText(nama);
        Nik.setText(nik);
        Umur.setText(umur);
        Alamat.setText(alamat);

        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("foto");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        final ImageView image = findViewById(R.id.foto);
        image.setImageBitmap(bmp);

        profil_mandor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = image.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                Intent intent = new Intent(activity_profile_mandor.this, activity_detail_mandor.class);
                intent.putExtra("nama", Nama.getText().toString());
                intent.putExtra("umur", Umur.getText().toString());
                intent.putExtra("alamat", Alamat.getText().toString());
                intent.putExtra("nik", Nik.getText().toString());
                intent.putExtra("foto", b);
                startActivity(intent);
            }
        });

        /*
        sewa_jasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_profile_mandor.this, activity_sewa_jasa.class);
                startActivity(intent);
            }
        });
        */
    }
}