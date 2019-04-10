package com.massimoregoli.mp2019.data;

import android.content.ContentValues;
import android.database.Cursor;

public class Comune {
    private String nome;
    private String provincia;
    private String codice;


    public Comune(String [] fld) {
        nome = fld[0];
        provincia = fld[1];
        codice = fld[2];
    }

    public Comune(Cursor cursor) {
        nome = cursor
                .getString(cursor
                        .getColumnIndexOrThrow(
                                DBHelper.NAME));
        provincia = cursor
                .getString(cursor
                        .getColumnIndexOrThrow(
                                DBHelper.PROV));
        codice = cursor
                .getString(cursor
                        .getColumnIndexOrThrow(
                                DBHelper.CODE));
    }


    public ContentValues toContentValue() {
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.NAME, nome);
        cv.put(DBHelper.PROV, provincia);
        cv.put(DBHelper.CODE, codice);
        return cv;
    }
}
