package com.massimoregoli.mp2019.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DaoCF {
    private SQLiteDatabase db;
    private Context context;


    public DaoCF(Context context) {
        MyFirstSQLiteOpenHelper mfsloh = new MyFirstSQLiteOpenHelper(context);

        db = mfsloh.getWritableDatabase();
        this.context = context;
    }

    public DaoCF(SQLiteDatabase db) {
        this.db = db;
    }
}
