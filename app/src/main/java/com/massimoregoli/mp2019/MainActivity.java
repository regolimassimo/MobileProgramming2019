package com.massimoregoli.mp2019;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.massimoregoli.mp2019.myactivities.Async2Activity;
import com.massimoregoli.mp2019.myactivities.AsyncActivity;
import com.massimoregoli.mp2019.myactivities.CocktailActivity;
import com.massimoregoli.mp2019.myactivities.GANActivity;
import com.massimoregoli.mp2019.myactivities.GameOfLife;
import com.massimoregoli.mp2019.myactivities.HTMLCodeActivity;
import com.massimoregoli.mp2019.myactivities.PrimeActivity;
import com.massimoregoli.mp2019.myactivities.QRCodeGenerator;
import com.massimoregoli.mp2019.myactivities.ViewActivity;
import com.massimoregoli.mp2019.threads.CocktailTask;
import com.massimoregoli.mp2019.threads.MusicTask;
import com.massimoregoli.mp2019.threads.PrimeTask;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button  btnGAN, btnFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

//        Evaluator evaluator = new Evaluator();
//        String expr = "cos(x_)*sin(x_)";
//        try {
//            String s = evaluator.evaluate(expr.replace("x_", "3"));
//            Log.w("MA", s);
//        } catch (EvaluationException e) {
//            e.printStackTrace();
//        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        100);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
//        Intent i = new Intent(this, HTMLCodeActivity.class);
//        startActivity(i);
//        finish();

//        Intent i = new Intent(this, CocktailActivity.class);
//        startActivity(i);
//        finish();
//        Intent i = new Intent(this, GameOfLife.class);
//        startActivity(i);
//        finish();
        Intent i = new Intent(this, QRCodeGenerator.class);
        startActivity(i);
        finish();
//        MusicTask mt = new MusicTask(this);
//        mt.execute("bee gees");
//        Intent i = new Intent(this, ViewActivity.class);
//        startActivity(i);
//        finish();
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
            MusicTask mt = new MusicTask(this);
            mt.execute("the+beatles");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}
