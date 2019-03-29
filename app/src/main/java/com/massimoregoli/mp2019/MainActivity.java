package com.massimoregoli.mp2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.massimoregoli.mp2019.myactivities.GANActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button  btnGAN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGAN = findViewById(R.id.myButton);


        btnGAN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, GANActivity.class);
        startActivity(i);
    }
}
