package com.massimoregoli.mp2019;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.massimoregoli.mp2019.myactivities.FilesActivity;
import com.massimoregoli.mp2019.myactivities.GANActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button  btnGAN, btnFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGAN = findViewById(R.id.myButton);
        btnGAN.setOnClickListener(this);
        btnFiles = findViewById(R.id.btnFiles);
        btnFiles.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnGAN.getId()) {
            Intent i = new Intent(this, GANActivity.class);
            startActivity(i);
        }
        if(v.getId() == btnFiles.getId()) {
            Intent i = new Intent(this, FilesActivity.class);
            startActivity(i);
        }
    }

}
