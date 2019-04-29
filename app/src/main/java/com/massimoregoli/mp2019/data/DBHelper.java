package com.massimoregoli.mp2019.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    static final String NAME = "NAME";
    static final String PROV = "PROV";
    static final String CODE = "CODE";
    private static final int VERSION = 1;

    public DBHelper(@Nullable Context context,
                    @Nullable String name) {
        super(context, name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
