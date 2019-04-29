package com.massimoregoli.mp2019.data;

public class CodFisc {
    private int _id;
    private String fullName;
    private String codFisc;


    public CodFisc(String n, String c) {
        this.codFisc = c;
        this.fullName = n;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCodFisc() {
        return codFisc;
    }

    public void setCodFisc(String codFisc) {
        this.codFisc = codFisc;
    }
}
