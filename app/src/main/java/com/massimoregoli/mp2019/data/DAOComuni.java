package com.massimoregoli.mp2019.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DAOComuni {
    SQLiteDatabase db;
    DBHelper dbh;
    public DAOComuni(Context context) {
        dbh = new DBHelper(context);
        db = dbh.getWritableDatabase();
    }

    public Cursor getCursor() {
        Cursor ret = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME, null);
        return ret;
    }
}
