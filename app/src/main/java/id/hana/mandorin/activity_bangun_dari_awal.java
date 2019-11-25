package id.hana.mandorin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class activity_bangun_dari_awal extends AppCompatActivity {

    /*
     * Static File Request & Server URL Initializations
     */
    private static final int STORAGE_PERMISSION_CODE = 123;
    private String selectedFilePath;
    private String SERVER_URL = "http://mandorin.site/mandorin/upload_file.php";
    private static final int PICK_FILE_REQUEST = 1;

    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView, RadioGroup & Button
     */
    private TextView FileName, dummy, filename_details;
    private EditText luas_tanah;
    private RadioGroup rg;
    private RadioButton rb, rb_2, rb_debug;
    private Button upload, selanjutnya;
    private CardView back;
    ProgressDialog dialog;

    /*
     * SharedPreferences Usage
     * I want to reduce passing usage, since it seems mess with app runtime sometimes
     */
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangun_dari_awal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FileName = findViewById(R.id.fileName);
        filename_details = findViewById(R.id.fileName_title);
        luas_tanah = findViewById(R.id.et_luas_tanah);
        dummy = findViewById(R.id.dummy);
        upload = findViewById(R.id.button_upload);
        selanjutnya = findViewById(R.id.button_selanjutnya);
        rg = findViewById(R.id.rg_borongan);
        rb = findViewById(R.id.rb_1);
        rb_2 = findViewById(R.id.rb_2);
        back = findViewById(R.id.back_activity_bangun_dari_awal);

        /*
         * SharedPreferences Declaration
         */
        pref = getApplicationContext().getSharedPreferences("data_mandor", 0);
        final String nik = pref.getString("nik",null);
        final String nama = pref.getString("nama",null);

        //Requesting storage permission
        requestStoragePermission();

        tampil_file();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (luas_tanah.getText().toString().length() == 0) {
                    luas_tanah.setError("Harap Masukkan Luas Tanah");
                } else {
                    if (selectedFilePath != null) {
                        int id = rg.getCheckedRadioButtonId();
                        switch (id) {
                            case R.id.rb_1:
                                String new_dummy = ((RadioButton) findViewById(id)).getText().toString();
                                dummy.setText(new_dummy);
                                break;
                            case R.id.rb_2:
                                String new_dummy_2 = ((RadioButton) findViewById(id)).getText().toString();
                                dummy.setText(new_dummy_2);
                                break;
                        }
                        dialog = ProgressDialog.show(activity_bangun_dari_awal.this, "", "Memproses...", true);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //creating new thread to handle Http Operations
                                    uploadFile(selectedFilePath);
                                }
                            }).start();

                            pref = getApplicationContext().getSharedPreferences("data_bangun_dari_awal", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("nik", nik);
                            editor.putString("nama", nama);
                            editor.putString("luas_tanah", luas_tanah.getText().toString());
                            editor.putString("jenis_borongan", dummy.getText().toString());
                            editor.putString("desain_rumah", FileName.getText().toString());
                            editor.apply();
                            Intent intent = new Intent(activity_bangun_dari_awal.this, activity_bangun_dari_awal_2.class);
                            startActivity(intent);
                    } else {
                        Toast.makeText(activity_bangun_dari_awal.this, "Anda Belum Menggungah Berkas", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_bangun_dari_awal.this, activity_sewa_jasa.class);
                startActivity(intent);
            }
        });
    }

    private void tampil_file(){
        if (selectedFilePath != null) {
            String filecheck = selectedFilePath;
            String document = filecheck.substring(filecheck.lastIndexOf("/")+1);
            FileName.setText(document);
            filename_details.setVisibility(View.VISIBLE);
            FileName.setVisibility(View.VISIBLE);
        }
    }

    //android upload file to server
    public int uploadFile(final String selectedFilePath) {
        final TextView FileName = findViewById(R.id.fileName);
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];
        /*
         * I should regex filename string to make it don't have any whitespace
         * before sending to server, to prevent any error that cause from whitespace
         * if user download it.
         *
         * However, i need to make same regexed file name and filepath that'll push it to db
         * filepath should get regex too when it want to push to db
         */
        String regex = fileName.replaceAll("\\s","");

        if (!selectedFile.isFile()) {
            dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FileName.setText("Berkas tidak tersedia: " + selectedFilePath);
                }
            });
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", regex);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + regex + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FileName.setText("http://mandorin.site/mandorin/uploads/" + fileName);
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity_bangun_dari_awal.this, "Berkas Tidak Di Temukan", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(activity_bangun_dari_awal.this, "Kesalahan Alamat Website", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity_bangun_dari_awal.this, "Tidak Dapat Membaca File", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            return serverResponseCode;
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih PDF"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final TextView FileName = findViewById(R.id.fileName);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }

                Uri selectedFileUri = data.getData();
                selectedFilePath = file_path.getPath(this, selectedFileUri);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    FileName.setText(selectedFilePath);
                } else {
                    Toast.makeText(this, "Tidak Dapat Mengunggah Ke Website Mandorin", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Perijinan aplikasi sudah berhasil, aplikasi dapat mengakses penyimpanan anda", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Anda menolak perijinan aplikasi", Toast.LENGTH_LONG).show();
            }
        }
    }
}