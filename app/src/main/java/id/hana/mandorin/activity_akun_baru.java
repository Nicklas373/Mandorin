package id.hana.mandorin;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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
import com.google.firebase.auth.FirebaseAuth;
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

public class activity_akun_baru extends AppCompatActivity {

    private CircleImageView user_pic, user_pic_edit;
    private TextView user_mail, user_dummy, user_new_dummy;
    private EditText user_name, user_nik, user_umur, user_phone, user_address;
    private ImageView back, edit_akun, simpan, batal;
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
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_baru);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        auth = FirebaseAuth.getInstance();

        auth.getCurrentUser().reload();

        pref = getSharedPreferences("data_akun", Context.MODE_PRIVATE);

        user_pic = (CircleImageView) findViewById(R.id.img_head_akun_test);
        user_pic_edit = (CircleImageView) findViewById(R.id.user_pic_edit);
        user_dummy = (TextView) findViewById(R.id.dummy_userpic_akun_baru);
        user_new_dummy = (TextView) findViewById(R.id.dummy_new_userpic_akun_baru);
        user_mail = (TextView) findViewById(R.id.user_email);
        user_name = (EditText) findViewById(R.id.user_name);
        user_nik = (EditText) findViewById(R.id.user_nik);
        user_umur = (EditText) findViewById(R.id.user_umur);
        user_phone = (EditText) findViewById(R.id.user_telp);
        user_address = (EditText) findViewById(R.id.user_alamat);
        back = (ImageView) findViewById(R.id.back_activity_akun_baru);
        edit_akun = (ImageView) findViewById(R.id.edit_activity_akun_baru);
        simpan = (ImageView) findViewById(R.id.simpan_edit_akun);
        batal = (ImageView) findViewById(R.id.batal_edit_akun);

        user_mail.setText(pref.getString("email", null));

        new activity_akun_baru.GetTextViewData(context).execute();

        user_pic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                batal.setVisibility(View.GONE);
                simpan.setVisibility(View.GONE);

                user_name.setFocusableInTouchMode(false);
                user_umur.setFocusableInTouchMode(false);
                user_nik.setFocusableInTouchMode(false);
                user_phone.setFocusableInTouchMode(false);
                user_address.setFocusableInTouchMode(false);
                user_pic_edit.setFocusableInTouchMode(false);
                user_pic_edit.setVisibility(View.GONE);

                new activity_akun_baru.GetTextViewData(context).execute();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(user_name.getText().toString())) {
                    user_name.setError("Harap Masukkan Nama");
                } else if (TextUtils.isEmpty(user_umur.getText().toString())) {
                    user_umur.setError("Harap Masukkan Umur");
                } else if (TextUtils.isEmpty(user_nik.getText().toString())) {
                    user_nik.setError("Harap Masukkan NIK");
                } else if (TextUtils.isEmpty(user_phone.getText().toString())) {
                    user_phone.setError("Harap Masukkan Nomor Telepon");
                } else if (TextUtils.isEmpty(user_address.getText().toString())) {
                    user_address.setError("Harap Masukkan Alamat");
                } else {
                    if (internet_available()) {
                        user_pic.setFocusableInTouchMode(false);
                        user_name.setFocusableInTouchMode(false);
                        user_umur.setFocusableInTouchMode(false);
                        user_nik.setFocusableInTouchMode(false);
                        user_phone.setFocusableInTouchMode(false);
                        user_address.setFocusableInTouchMode(false);
                        user_pic_edit.setFocusableInTouchMode(false);
                        user_pic_edit.setVisibility(View.GONE);
                        update_dialog();
                    } else {
                        Toast.makeText(activity_akun_baru.this, "Harap periksa koneksi internet anda", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        edit_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser().isEmailVerified()) {
                    batal.setVisibility(View.VISIBLE);
                    simpan.setVisibility(View.VISIBLE);

                    user_pic.setFocusableInTouchMode(true);
                    user_name.setFocusableInTouchMode(true);
                    user_umur.setFocusableInTouchMode(true);
                    user_nik.setFocusableInTouchMode(true);
                    user_phone.setFocusableInTouchMode(true);
                    user_address.setFocusableInTouchMode(true);
                    user_pic_edit.setFocusableInTouchMode(true);
                    user_pic_edit.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "Harap verifikasi email anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                batal.setVisibility(View.GONE);
                simpan.setVisibility(View.GONE);
                user_pic_edit.setVisibility(View.GONE);

                user_pic_edit.setFocusableInTouchMode(false);
                user_name.setFocusableInTouchMode(false);
                user_umur.setFocusableInTouchMode(false);
                user_nik.setFocusableInTouchMode(false);
                user_phone.setFocusableInTouchMode(false);
                user_address.setFocusableInTouchMode(false);

                Intent intent = new Intent(activity_akun_baru.this, activity_akun.class);
                startActivity(intent);
            }
        });
    }

    private class GetTextViewData extends AsyncTask<Void, Void, Void> {
        public Context context;
        String usermail_2 = user_mail.getText().toString();

        public GetTextViewData(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String adress = "http://mandorin.site/mandorin/php/user/new/read_data_user.php?email=" + usermail_2;
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

            try {
                JSONArray jArray = new JSONArray(str);
                json = jArray.getJSONObject(0);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            try {
                String username = json.getString("nama_lengkap");
                if (username.equalsIgnoreCase("")) {
                    user_name.setText("-");
                } else {
                    user_name.setText(json.getString("nama_lengkap"));
                }
                String usermail_2 = json.getString("email");
                if (usermail_2.equalsIgnoreCase("")) {
                    user_mail.setText("-");
                } else {
                    user_mail.setText(usermail_2);
                }
                String userage_2 = json.getString("umur");
                if (userage_2.equalsIgnoreCase("")) {
                    user_umur.setText("-");
                } else {
                    user_umur.setText(userage_2);
                }
                String userid_2 = json.getString("nik");
                if (userid_2.equalsIgnoreCase("")) {
                    user_nik.setText("-");
                } else {
                    user_nik.setText(userid_2);
                }
                String userphone_2 = json.getString("telp");
                if (userphone_2.equalsIgnoreCase("")) {
                    user_phone.setText("-");
                } else {
                    user_phone.setText(userphone_2);
                }
                String useraddress_2 = json.getString("alamat");
                if (useraddress_2.equalsIgnoreCase("")) {
                    user_address.setText("-");
                } else {
                    user_address.setText(useraddress_2);
                }
                user_dummy.setText(json.getString("foto_user"));
                /*
                 * This is actually need old API version from android, which is not used anymore for now
                 * User will get default profile photo when they already complete registration
                 * So function to change photo when user doesn't have any photo on dB, isn't needed
                 * anymore.
                 */
                String uri = "@drawable/profil";  // where myresource (without the extension) is the file
                if (user_dummy.getText().toString().equalsIgnoreCase("")) {
                    //int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                    //Drawable res = getResources().getDrawable(imageResource);
                    //userpic.setImageDrawable(res);
                } else {
                    /*
                     * This when user already have photo, then app will download the photo using picasso service
                     */
                    String user_photo = "http://mandorin.site/mandorin/uploads/" + user_dummy.getText().toString();
                    Picasso.get().load(user_photo).fit().centerCrop().into(user_pic);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void update_dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Data akun");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin memperbaharui data akun anda?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog = ProgressDialog.show(activity_akun_baru.this, "Edit Akun", "Memperbaharui Data...", true);
                        if (user_new_dummy.getText().toString().equalsIgnoreCase("")) {
                            if (user_dummy.getText().toString().equalsIgnoreCase("")) {
                                finish();
                            } else {
                                String oldimage = user_dummy.getText().toString();
                                user_new_dummy.setText(oldimage);
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
                        update_data_user();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                Intent intent = new Intent(activity_akun_baru.this, activity_akun.class);
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
                            user_new_dummy.setText(selectedFilePath);
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
                        Toast.makeText(activity_akun_baru.this, "Berkas Tidak Di Temukan", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(activity_akun_baru.this, "Kesalahan Alamat Website", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity_akun_baru.this, "Tidak Dapat Membaca File", Toast.LENGTH_SHORT).show();
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
                    user_new_dummy.setText(selectedFilePath);
                    File imgFile = new  File(user_new_dummy.getText().toString());

                    if(imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        ImageView myImage = (ImageView) findViewById(R.id.img_head_akun_test);
                        myImage.setImageBitmap(myBitmap);
                    }
                } else {
                    user_new_dummy.setText("");
                }
            }
        }
    }

    private void update_data_user() {
        String usermail_2 = user_mail.getText().toString();
        String adress = "http://mandorin.site/mandorin/php/user/new/update_data_user.php?email=" + usermail_2;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, adress,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        //Toast.makeText(activity_edit_akun.this, ServerResponse, Toast.LENGTH_LONG).show();
                        if (ServerResponse.length() > 15) {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Success_Notif();
                                }
                            }, 3000L); //3000 L = 3 detik
                        } else {
                            Fail_Notif();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(activity_akun_baru.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                        Fail_Notif();
                        Intent intent = new Intent(activity_akun_baru.this, MainActivity.class);
                        startActivity(intent);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                String nama_edit = user_name.getText().toString();
                String usermail_edit = user_mail.getText().toString();
                String umur_edit  = user_umur.getText().toString();
                String nik_edit   = user_nik.getText().toString();
                String telp_edit   = user_phone.getText().toString();
                String alamat_edit   = user_address.getText().toString();
                String substring = user_new_dummy.getText().toString();
                String desain = substring.substring(substring.lastIndexOf("/")+1);
                // regex filename, so it'll look same for filename and filepath that'll inserted to database
                String regex = desain.replaceAll("\\s", "");

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("nama_lengkap", nama_edit  );
                params.put("email", usermail_edit );
                params.put("umur", umur_edit );
                params.put("nik", nik_edit  );
                params.put("telp", telp_edit  );
                params.put("alamat", alamat_edit  );
                params.put("foto_user", regex);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity_akun_baru.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void Success_Notif(){
        Intent intent;
        PendingIntent pendingIntent;
        NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        String id = "ID_MANDORIN";
        String title = "Mandorin";
        android.support.v4.app.NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                notifManager.createNotificationChannel(mChannel);
            }
        }
        builder = new android.support.v4.app.NotificationCompat.Builder(this,id);
        intent = new Intent(getApplicationContext(), activity_akun_baru.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        builder.setContentTitle("Mandorin")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Data akun anda berhasil di perbaharui!")
                .setDefaults(Notification.COLOR_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH);
        Notification notification = builder.build();
        notifManager.notify(0, notification);
    }

    private void Fail_Notif(){
        Intent intent;
        PendingIntent pendingIntent;
        NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        String id = "ID_MANDORIN";
        String title = "Mandorin";
        android.support.v4.app.NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                notifManager.createNotificationChannel(mChannel);
            }
        }
        builder = new android.support.v4.app.NotificationCompat.Builder(this,id);
        builder.setContentTitle("Mandorin")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Pembaharuan data akun, harap coba lagi !")
                .setDefaults(Notification.COLOR_DEFAULT)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH);
        Notification notification = builder.build();
        notifManager.notify(0, notification);
    }

    private boolean internet_available(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
}