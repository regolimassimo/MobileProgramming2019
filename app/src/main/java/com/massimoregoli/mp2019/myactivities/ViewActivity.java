package com.massimoregoli.mp2019.myactivities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.adapters.ComuniAdapters;
import com.massimoregoli.mp2019.data.DAOComuni;
import com.massimoregoli.mp2019.data.DBHelper;

public class ViewActivity extends AppCompatActivity {
    RecyclerView rvComuni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        rvComuni = findViewById(R.id.rvComuni);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvComuni.setLayoutManager(mLayoutManager);
        DAOComuni daoComuni = new DAOComuni(this);
        Cursor cursor = daoComuni.getCursor();
        ComuniAdapters ca = new ComuniAdapters(this, cursor);
        rvComuni.setAdapter(ca);
    }
}
