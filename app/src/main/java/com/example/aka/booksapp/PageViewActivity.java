package com.example.aka.booksapp;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.FileInputStream;

public class PageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);
        setTitle("The Logical Atheist");
        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        try{
            FileInputStream file = openFileInput("book.pdf");
            pdfView.fromStream(file)
                    .enableSwipe(true)
                    .enableAntialiasing(true)
                    .swipeHorizontal(true)
                    .load();
            toastMessage("IN Page View");
        }catch (Exception e){
            toastMessage("Unable to open!");
        }
    }
    void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
