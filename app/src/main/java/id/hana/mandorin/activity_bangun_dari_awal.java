package id.hana.mandorin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.pm.ActivityInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class activity_bangun_dari_awal extends AppCompatActivity {

    /*
     * Static File Request & Server URL Initializations
     */
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedFilePath;
    private String SERVER_URL = "http://mandorin.site/mandorin/upload_file.php";

    /*
     * Layout Component Initializations
     * Textview, Imageview, CardView, RadioGroup & Button
     */
    private TextView FileName, dummy, filename_details, tgl_daftar;
    private EditText luas_tanah;
    private RadioGroup rg;
    private RadioButton rb, rb_2, rb_debug;
    private Button upload, selanjutnya;
    private CardView back;
    ProgressDialog dialog;

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
         * Passing data from last activity
         */
        final String nik = getIntent().getExtras().getString("nik");
        final String nama = getIntent().getExtras().getString("nama");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        tampil_file();

        selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (luas_tanah.getText().toString().length() == 0) {
                    luas_tanah.setError("Harap Masukkan Luas Tanah");
                } else {
                    if (selectedFilePath != null) {
                        int id = rg.getCheckedRadioButtonId();
                        switch (id)
                        {
                            case R.id.rb_1:
                                String new_dummy = ((RadioButton)findViewById(id)).getText().toString();
                                dummy.setText(new_dummy);
                                break;
                            case R.id.rb_2:
                                String new_dummy_2 = ((RadioButton)findViewById(id)).getText().toString();
                                dummy.setText(new_dummy_2);
                                break;
                        }
                        String filecheck = selectedFilePath;
                        String[] parts = filecheck.split("\\.");
                        String ext = parts[parts.length - 1];
                        if (ext.equals("pdf")) {
                            dialog = ProgressDialog.show(activity_bangun_dari_awal.this, "", "Uploading File...", true);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //creating new thread to handle Http Operations
                                    uploadFile(selectedFilePath);
                                }
                            }).start();

                            Bundle bundle = new Bundle();
                            bundle.putString("nik", nik);
                            bundle.putString("nama", nama);
                            bundle.putString("luas_tanah", luas_tanah.getText().toString());
                            bundle.putString("jenis_borongan", dummy.getText().toString());
                            bundle.putString("desain_rumah", FileName.getText().toString());
                            Intent intent = new Intent(activity_bangun_dari_awal.this, activity_bangun_dari_awal_2.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else{
                            Toast.makeText(activity_bangun_dari_awal.this, "Harap Unggah Berkas Berupa PDF", Toast.LENGTH_LONG).show();
                        }
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
                intent.putExtra("nik", nik);
                intent.putExtra("nama", nama);
                startActivity(intent);
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent, "Silahkan Pilih Dokumen Yang Ingin Di Unggah.."), PICK_FILE_REQUEST);
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
                connection.setRequestProperty("uploaded_file", selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

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
}