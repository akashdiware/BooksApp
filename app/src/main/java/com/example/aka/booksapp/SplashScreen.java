package com.example.aka.booksapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashScreen extends AppCompatActivity {
    ProgressDialog progressDialog;
    String books_url = "https://drive.google.com/uc?id=1tMyUV2Ugl0xBeeTODDz6zijWhJ0lhpuK&export=download";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setTitle("The Logical Atheist");
        try{
            FileInputStream file = openFileInput("book.pdf");
            file.close();
//            toastMessage("Successfully opened file!");
            Intent intent = new Intent(this, PageViewActivity.class);
            startActivity(intent);
            finish();
        }catch(Exception e){
            Log.e("Error Main 1", e.getMessage());
        }

        try{
            new DownloadBooks().execute(books_url);
        }catch (Exception e){
//            toastMessage("Error here!");
            Log.e("Error Main", e.getMessage());
            toastMessage("Unable to download book please restart application!");
        }


    }
    void callActivity(){
//        toastMessage("Successfully opened file in two!");
        Intent intent = new Intent(this, PageViewActivity.class);
        startActivity(intent);
        finish();

    }

    void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class DownloadBooks extends AsyncTask<String, Integer, Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SplashScreen.this);
            progressDialog.setTitle("Download");
            progressDialog.setMessage("Please relax while downloading book!");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s != 1){
//                toastMessage("Unable to download book please restart application!");
            }else{
                callActivity();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Integer doInBackground(String... urls) {
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int fileLength = connection.getContentLength();

                InputStream input = new BufferedInputStream(connection.getInputStream());
//
                String filename = "book.pdf";
                FileOutputStream out = openFileOutput(filename, Context.MODE_PRIVATE);
                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1){
                    total += count;
                    publishProgress((int) (total*100 / fileLength));
                    out.write(data, 0, count);
                }
                input.close();
                out.flush();
                out.close();
                Log.d("Success", "S");
                return 1;
            }catch (Exception e){
                Log.e("Error DoBackground", e.getMessage());
                e.printStackTrace();
            }
            return 0;
        }
    }
}
