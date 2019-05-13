package com.massimoregoli.mp2019.myactivities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.threads.PrimeTask;

public class PrimeActivity extends AppCompatActivity implements View.OnClickListener {
    PrimeTask pt;
    ViewHolder vh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime);
        vh = new ViewHolder();
        vh.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNoOfPrime) {
            pt = new PrimeTask(this);
            Long value = Long.parseLong(vh.etInput.getText().toString());
            pt.execute(new Long(value), "#");
        }
        if(v.getId() == R.id.btnCancel) {
            if(pt.getStatus() == AsyncTask.Status.RUNNING) {
                pt.cancel(true);
            }
        }
    }

    class ViewHolder {
        EditText etInput;
        TextView tvOutput;
        Button btnOK, btnCancel;

        public ViewHolder() {
            etInput = findViewById(R.id.etInput);
            tvOutput = findViewById(R.id.tvOutput);
            btnOK = findViewById(R.id.btnNoOfPrime);
            btnCancel = findViewById(R.id.btnCancel);
        }

        public void setOnClickListener(PrimeActivity primeActivity) {
            btnOK.setOnClickListener(primeActivity);
            btnCancel.setOnClickListener(primeActivity);
        }
    }
}
