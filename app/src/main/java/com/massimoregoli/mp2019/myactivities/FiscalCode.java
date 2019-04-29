package com.massimoregoli.mp2019.myactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;

import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.adapters.MyCursorAdapter;
import com.massimoregoli.mp2019.data.MyFirstSQLiteOpenHelper;

public class FiscalCode extends AppCompatActivity {

    private AutoCompleteTextView actvComune;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiscal_code);

        actvComune = findViewById(R.id.actvComune);


        MyCursorAdapter ca = new MyCursorAdapter(this);
        actvComune.setAdapter(ca);
        actvComune.setOnItemClickListener(ca);

    }
}
