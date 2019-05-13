package com.massimoregoli.mp2019.myactivities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.adapters.ComuniAdapter;
import com.massimoregoli.mp2019.data.DAOComuni;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    RecyclerView rvComuni;
    Button btnRefresh;
    EditText etSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
        etSearch = findViewById(R.id.etFilter);
        etSearch.addTextChangedListener(this);
        rvComuni = findViewById(R.id.rvComuni);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvComuni.setLayoutManager(mLayoutManager);
        DAOComuni daoComuni = new DAOComuni(this);
        Cursor cursor = daoComuni.getAll();
        ComuniAdapter ca = new ComuniAdapter(this, cursor);
        rvComuni.setAdapter(ca);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.w("VA", "bFT "+start+","+count+","+after);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.w("VA", "oTC "+start+","+before+","+count);
        if(s.length() > 2 || count == 0) {
            DAOComuni daoComuni = new DAOComuni(this);
            Cursor cursor = daoComuni.getComuneByName(s.toString());
            ComuniAdapter ca = new ComuniAdapter(this, cursor);
            rvComuni.setAdapter(ca);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
