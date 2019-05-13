package com.massimoregoli.mp2019.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class MyFirstSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String NAME = "codfisc.db";
    private static final int VERSION = 1;
    private static final String CREATE_TBL_COMUNI = "CREATE TABLE `comuni` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`name`\tTEXT,\n" +
            "\t`prov`\tTEXT,\n" +
            "\t`code`\tTEXT\n" +
            ");";
    private Context context;


    public MyFirstSQLiteOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_COMUNI);
        fillTable(db);
    }

    public void fillTable(SQLiteDatabase db) {
        String line;
        db.beginTransaction();
        try {
            InputStreamReader isr =
                    new InputStreamReader(context
                            .getAssets()
                            .open("comuni.csv"));
            BufferedReader bis = new BufferedReader(isr);
            while((line = bis.readLine()) != null) {
                Comune comune = new Comune(line);
                if(comune != null) {
                    add(db, comune);
                }
            }
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public void add(SQLiteDatabase db, Comune comune) {
        ContentValues cvComune = new ContentValues();
        cvComune.put("name", comune.getComune());
        cvComune.put("code", comune.getCodice());
        cvComune.put("prov", comune.getProv());

        db.insert("comuni", null, cvComune);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor fetchItemsByDesc(String inputText) throws SQLException {
        Cursor mCursor = getWritableDatabase().query(true, "comuni", new String[] {"_id", "name",
                        "code", "prov"}, "name" + " like '" + inputText + "%'", null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
