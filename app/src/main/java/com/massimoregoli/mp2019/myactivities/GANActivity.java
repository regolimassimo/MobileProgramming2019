package com.massimoregoli.mp2019.myactivities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.massimoregoli.mp2019.R;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GANActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnKeyListener {

    // Return TAG
    private final int REQUEST_SPEECH_RECOGNIZER = 3000;
    // Message
    private String mQuestion;
    private static final int REQUEST_RECORD_PERMISSION = 100;
    private SpeechRecognizer speech = null;


    private static final int MAXINT = 100;
    private String mAnswer;
    private Button btnOK;
    private EditText etAttempt;
    private TextView tvResult;
    private int attempts;
    private int guessMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gan);

        etAttempt = findViewById(R.id.etAttempt);
        btnOK = findViewById(R.id.btnNoOfPrime);
        tvResult = findViewById(R.id.tvResult);

        btnOK.setOnClickListener(this);
        etAttempt.setOnKeyListener(this);

        mQuestion = getString(R.string.say_a_number);

        newGame();


    }

    private void newGame() {
        guessMe = new Random().nextInt(MAXINT)+1;
        attempts = 0;

        tvResult.setText("");
        etAttempt.setText("");

    }


    void checkResult(String string) {
        try {
            int attempt = Integer.parseInt(string);
            ++attempts;

            if (attempt > guessMe) {

                tvResult.setText(String.format(Locale.getDefault(), getString(R.string.toobig), attempt));
            }
            if (attempt < guessMe) {
                tvResult.setText(String.format(Locale.getDefault(), getString(R.string.toosmall), attempt));
            }
            if (attempt == guessMe) {
                tvResult.setText(String.format(Locale.getDefault(), getString(R.string.bravo), attempt));
            }
        } catch (NumberFormatException ex) {
            tvResult.setText(String.format(Locale.getDefault(), getString(R.string.error), string));
        }
        etAttempt.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SPEECH_RECOGNIZER) {
            if (resultCode == RESULT_OK) {
                List<String> results = data.getStringArrayListExtra
                        (RecognizerIntent.EXTRA_RESULTS);
                mAnswer = results.get(0); // ! Non necessariamente!
                for (Iterator<String> it = results.iterator(); it.hasNext(); ) {
                    String s = it.next();
                    try {
                        Integer.parseInt(s);
                        checkResult(s);
                        return;
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
                    }
                }
            }
            etAttempt.setText(mAnswer);
            checkResult(mAnswer);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnOK.getId()) {
            startSpeechRecognizer();
        } else {
            mAnswer = etAttempt.getText().toString();
            checkResult(mAnswer);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent
                            (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, mQuestion);
                    startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER);
                } else {
                    Toast.makeText(this, getString(R.string.permnotok), Toast
                            .LENGTH_LONG).show();
                }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            mAnswer = etAttempt.getText().toString();
            if(mAnswer.compareTo("") != 0)
                checkResult(mAnswer);
        }
        return false;
    }


    private void startSpeechRecognizer() {

        ActivityCompat.requestPermissions
                (this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_RECORD_PERMISSION);


    }
}
