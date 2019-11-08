package id.hana.mandorin;

import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownloader {
    private static final String TAG = "FileDownloader";
    protected ProgressBar mProgressBar;
    private static final int MEGABYTE = 1024 * 1024;

    public static void downloadFile(String fileUrl, File directory) {

        try {

            Log.v(TAG, "downloadFile() invoked ");
            Log.v(TAG, "downloadFile() fileUrl " + fileUrl);
            Log.v(TAG, "downloadFile() directory " + directory);

            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
            Log.v(TAG, "downloadFile() completed ");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "downloadFile() error" + e.getMessage());
            Log.e(TAG, "downloadFile() error" + e.getStackTrace());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "downloadFile() error" + e.getMessage());
            Log.e(TAG, "downloadFile() error" + e.getStackTrace());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "downloadFile() error" + e.getMessage());
            Log.e(TAG, "downloadFile() error" + e.getStackTrace());
        }
    }
}
