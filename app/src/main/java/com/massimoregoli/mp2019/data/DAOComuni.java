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

    public Cursor getAll() {
        Cursor ret = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " order by comune", null);
        return ret;
    }

    public Cursor getComuneByName(String comune) {
        Cursor ret = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE comune LIKE ? order by comune", new String[] {comune + "%"});
        return ret;
    }

    public Comune getComuneById(int _id) {
        Comune ret;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE _id = ?", new String[] {"" + _id});
        if(cursor.getCount() != 1)
            return null;
        return new Comune(cursor);
    }
}
