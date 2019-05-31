package com.massimoregoli.mp2019.myactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.myviews.GoLView;

public class GameOfLife extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private GoLView gol;
    private Button btnStartStop, btnClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_of_life);
        gol = findViewById(R.id.goL);
        gol.setOnTouchListener(this);
        btnStartStop = findViewById(R.id.btnStartStop);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnStartStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnStartStop)
            if(gol.isRunning())
                gol.stop();
            else
                gol.run();
        if(v.getId() == R.id.btnClear)
            gol.clear();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.w("GOL", "" + event.getX() + ", " + event.getY());
        gol.changeState(event.getX(), event.getY());
        return false;
    }
}
