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





}
