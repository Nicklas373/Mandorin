package id.hana.mandorin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_edit_akun extends AppCompatActivity {

    private TextView editmail, cancel, dummyimage, dummynewimage;
    private EditText editname, editid, editage, editphone, editaddress;
    private CircleImageView picedit;
    private Button save;
    private CardView back;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    JSONObject json = null;
    String str = "";
    HttpResponse response;
    Context context;
    private String selectedFilePath;
    private static final int PICK_FILE_REQUEST = 1;
    private String SERVER_URL = "http://mandorin.site/mandorin/upload_file.php";
    ProgressDialog dialog;

    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pref = getSharedPreferences("data_akun", Context.MODE_PRIVATE);

        editname = (EditText) findViewById(R.id.userEditFullName);
        editmail = (TextView) findViewById(R.id.userEditEmail);
        editage = (EditText) findViewById(R.id.userEditAge);
        editid = (EditText) findViewById(R.id.user_edit_id);
        editphone = (EditText) findViewById(R.id.user_edit_telp);
        editaddress = (EditText) findViewById(R.id.user_edit_address);
        picedit = (CircleImageView) findViewById(R.id.img_head_akun_test);
        dummyimage = (TextView) findViewById(R.id.dummy_userpic_edit);
        dummynewimage = (TextView) findViewById(R.id.dummy_new_pic);
        cancel = (TextView) findViewById(R.id.btn_cancel);
        back = (CardView) findViewById(R.id.back_activity_edit_akun);
        save = (Button) findViewById(R.id.save_account);

        editmail.setText(pref.getString ("email", null));

        new GetImageData(context).execute();

        picedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_edit_akun.this, activity_akun.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editname.getText().toString())) {
                    editname.setError("Harap Masukkan Nama");
                    return;
                } else if (TextUtils.isEmpty(editmail.getText().toString())) {
                    editmail.setError("Harap Masukkan Email");
                    return;
                } else if (TextUtils.isEmpty(editage.getText().toString())) {
                    editage.setError("Harap Masukkan Umur");
                    return;
                } else if (TextUtils.isEmpty(editid.getText().toString())) {
                    editid.setError("Harap Masukkan NIK");
                    return;
                } else if (TextUtils.isEmpty(editphone.getText().toString())) {
                    editphone.setError("Harap Masukkan Nomor Telepon");
                    return;
                } else if (TextUtils.isEmpty(editaddress.getText().toString())) {
                    editaddress.setError("Harap Masukkan Alamat");
                    return;
                } else {
                    update_dialog();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_edit_akun.this, activity_akun.class);
                startActivity(intent);
            }
        });
    }

    private void update_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Data Diri");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin memperbaharui data diri anda?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog = ProgressDialog.show(activity_edit_akun.this, "Edit Akun", "Memperbaharui Data...", true);
                        if (dummynewimage.getText().toString().equalsIgnoreCase("")) {
                            if (dummyimage.getText().toString().equalsIgnoreCase("")) {
                                finish();
                            } else {
                                String oldimage = dummyimage.getText().toString();
                                dummynewimage.setText(oldimage);
                            }
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //creating new thread to handle Http Operations
                                    uploadFile(selectedFilePath);
                                }
                            }).start();
                        }
                        dialog.dismiss();
                        createdata_2();
                        Intent intent = new Intent(activity_edit_akun.this, activity_user_profile.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Data berhasil di perbaharui", Toast.LENGTH_SHORT).show();
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
                            dummynewimage.setText(selectedFilePath);
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
                        Toast.makeText(activity_edit_akun.this, "Berkas Tidak Di Temukan", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(activity_edit_akun.this, "Kesalahan Alamat Website", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity_edit_akun.this, "Tidak Dapat Membaca File", Toast.LENGTH_SHORT).show();
            }
            return serverResponseCode;
        }
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    dummynewimage.setText(selectedFilePath);
                    File imgFile = new  File(dummynewimage.getText().toString());

                    if(imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        ImageView myImage = (ImageView) findViewById(R.id.img_head_akun_test);

                        myImage.setImageBitmap(myBitmap);

                    }
                } else {
                    dummynewimage.setText("");
                }
            }
        }
    }

    private void createdata_2() {
        String usermail_2 = editmail.getText().toString();
        String adress = "http://mandorin.site/mandorin/php/user/test_update.php?email=" + usermail_2;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, adress,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        //Toast.makeText(activity_edit_akun.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_edit_akun.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                String nama_edit = editname.getText().toString();
                String usermail_edit = editmail.getText().toString();
                String umur_edit  = editage.getText().toString();
                String nik_edit   = editid.getText().toString();
                String telp_edit   = editphone.getText().toString();
                String alamat_edit   = editaddress.getText().toString();
                String substring = dummynewimage.getText().toString();
                String desain = substring.substring(substring.lastIndexOf("/")+1);
                // regex filename, so it'll look same for filename and filepath that'll inserted to database
                String regex = desain.replaceAll("\\s", "");

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("nama_lengkap", nama_edit  );
                params.put("email", usermail_edit );
                params.put("umur", umur_edit  );
                params.put("nik", nik_edit  );
                params.put("telp", telp_edit  );
                params.put("alamat", alamat_edit  );
                params.put("foto_user", regex);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_edit_akun.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private class GetImageData extends AsyncTask<Void, Void, Void>
    {
        public Context context;
        String usermail_2 = editmail.getText().toString();

        public GetImageData(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            String adress = "http://mandorin.site/mandorin/php/user/test_read.php?email=" + usermail_2;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost(adress);

            try {
                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                JSONArray jArray = new JSONArray(str);
                json = jArray.getJSONObject(0);

            } catch ( JSONException e) {
                e.printStackTrace();
            }

            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            try {
                dummyimage.setText(json.getString("foto_user"));
                String uri = "@drawable/profil";  // where myresource (without the extension) is the file
                if (dummyimage.getText().toString().equalsIgnoreCase("")) {
                    //int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                    ///Drawable res = getResources().getDrawable(imageResource);
                    //picedit.setImageDrawable(res);
                    dummyimage.setText("");
                } else {
                    String user_photo = "http://mandorin.site/mandorin/uploads/" + dummyimage.getText().toString();
                    Picasso.get().load(user_photo).fit().centerCrop() .into(picedit);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
