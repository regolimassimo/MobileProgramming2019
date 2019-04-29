package com.massimoregoli.mp2019.data;

import android.content.ContentValues;
import android.database.Cursor;

public class Comune {
    private static final String SEP = ";";
    private String nome;
    private String provincia;
    private String codice;
    private int _id;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Comune(String line) {
        String [] flds = line.split(SEP);
        if(flds.length != 3)
            return;
        nome = flds[0];
        provincia = flds[1];
        codice = flds[2];

        _id = -1;
    }


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
