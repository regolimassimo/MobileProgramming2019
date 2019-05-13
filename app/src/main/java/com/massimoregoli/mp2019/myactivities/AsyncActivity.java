package com.massimoregoli.mp2019.myactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.threads.SimpleASyncTask;

public class AsyncActivity extends AppCompatActivity implements View.OnClickListener {
    ViewHolder vh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        vh = new ViewHolder();
        vh.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        SimpleASyncTask sat = new SimpleASyncTask(vh);

        sat.execute(Integer.parseInt(vh.etTimeToWait.getText().toString()));
    }

    public class ViewHolder {
        public EditText etTimeToWait;
        ProgressBar pbElapsedTime, pbPleaseWait;
        Button btnGo;
        Integer time;

        ViewHolder() {
            etTimeToWait = findViewById(R.id.etTimeToWait);
            etTimeToWait.setTransformationMethod(null);
            btnGo = findViewById(R.id.btnGo);
            pbElapsedTime = findViewById(R.id.pbElapsedTime);
            pbPleaseWait = findViewById(R.id.pbPleaseWait);

            pbPleaseWait.setVisibility(View.GONE);
        }

        public void preExecute() {
            etTimeToWait.setEnabled(false);
            btnGo.setEnabled(false);
            pbPleaseWait.setVisibility(View.VISIBLE);
            pbElapsedTime.setProgress(0);
            time = Integer.parseInt(etTimeToWait
                    .getText().toString());
        }

        public void postExecute() {
            etTimeToWait.setEnabled(true);
            btnGo.setEnabled(true);
            pbPleaseWait.setVisibility(View.GONE);
            pbElapsedTime.setProgress(0);
        }

        public void progressUpdate(Integer[] values) {
            int value;

            value = (int)(100 * (int)(float)(values[0])/(float)time);
            pbElapsedTime.setProgress(value);
        }

        public void setOnClickListener(View.OnClickListener clickListener) {
            btnGo.setOnClickListener(clickListener);
        }
    }
}
