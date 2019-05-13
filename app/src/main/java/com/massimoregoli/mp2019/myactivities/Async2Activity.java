package com.massimoregoli.mp2019.myactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.threads.WaitTask;

public class Async2Activity extends AppCompatActivity implements View.OnClickListener {

    ViewHolder vh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async2);

        vh = new ViewHolder();
        vh.btnGo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnGo) {
            WaitTask wt = new WaitTask(vh);
            try {
                Integer time = Integer.parseInt(vh.etTimeToWait.getText().toString());
                wt.execute(time);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
            }

        }
    }

    public class ViewHolder {
        EditText etTimeToWait;
        Button btnGo;
        ProgressBar pbTimeElapsed, pbPleaseWait;

        public ViewHolder() {
            etTimeToWait = findViewById(R.id.etTimeToWait);
            etTimeToWait.setTransformationMethod(null);
            btnGo = findViewById(R.id.btnGo);

            pbPleaseWait = findViewById(R.id.pbPleaseWait);
            pbTimeElapsed = findViewById(R.id.pbTimeElapsed);

            pbPleaseWait.setVisibility(View.GONE);
        }

        public void pre() {
            etTimeToWait.setEnabled(false);
            btnGo.setEnabled(false);
            pbPleaseWait.setVisibility(View.VISIBLE);
        }

        public void post() {
            etTimeToWait.setEnabled(true);
            btnGo.setEnabled(true);
            pbPleaseWait.setVisibility(View.GONE);
        }

        public void progress(Integer[] values) {
            Integer time = Integer.parseInt(etTimeToWait.getText().toString());
            Integer value = (int)((float)values[0] / (float)(time) * 100);
            pbTimeElapsed.setProgress(value);
        }
    }
}
