package com.massimoregoli.mp2019.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public static final String TABLE_NAME = "comuni";
    private static final String DBNAME = "comuni";
    private Context context;
    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            InputStream is = context.getAssets().open("comuni.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String H = br.readLine();
            String [] flds = H.split(";");
            String sql = "CREATE TABLE " + TABLE_NAME + " (\n";
            for (String fld: flds) {
                sql = sql + ""+fld+ " TEXT,\n";
            }
            sql = sql + "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT);\n";
            Log.w("DB", sql);
            db.execSQL(sql);
            fillDatabase(db);
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillDatabase(SQLiteDatabase db) throws IOException {
        InputStream is = context.getAssets().open("comuni.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String H = br.readLine();
        String[] fldNames = H.split(";");
        while((H = br.readLine()) != null) {
            String [] flds = H.split(";");
            ContentValues cv = new ContentValues();
            try {
                for (int i = 0; i < flds.length; i++) {
                    cv.put(fldNames[i], flds[i]);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            db.insert(TABLE_NAME, null, cv);
        }
        br.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
