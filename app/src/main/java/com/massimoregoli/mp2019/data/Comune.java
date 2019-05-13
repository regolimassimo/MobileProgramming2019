package com.massimoregoli.mp2019.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.NumberFormat;
import java.util.Locale;

public class Comune {

	String istat;
	String comune;
	String codice;
	String lon;
	String lat;
	String regione;
	String prov;
	String prefisso;
	String sup;
	String residenti;
	String abitanti;
	String patrono_nome;
	String patrono_data;
	String cap;
	int _id;

	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	@Override
	public String toString() {
		return this.comune;
	}

	public String getIstat() {
		return istat;
	}

	public String getComune() {
		return comune;
	}

	public String getCodice() {
		return codice;
	}

	public String getLon() {
		double lon = Double.parseDouble(this.lon);
		return String.format(Locale.US, "%6.3f", lon);
	}

	public String getLat() {
		double lat = Double.parseDouble(this.lat);
		return String.format(Locale.US, "%6.3f", lat);
	}

	public String getRegione() {
		return regione;
	}

	public String getProv() {
		return prov;
	}

	public String getPrefisso() {
		return prefisso;
	}

	public String getSup() {
		return sup;
	}

	public String getResidenti() {
		int res = Integer.parseInt(residenti);
		String ret = "NA";
		try {
			ret = NumberFormat.getIntegerInstance(Locale.getDefault()).format(res);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public String getAbitanti() {
		return abitanti;
	}

	public String getPatrono_nome() {
		return patrono_nome;
	}

	public String getPatrono_data() {
		return patrono_data;
	}

	public String getCap() {
		return cap;
	}

	public int get_id() {
		return _id;
	}



    public Comune(Cursor cursor) {
		this.istat = cursor.getString(cursor.getColumnIndexOrThrow("istat"));
		this.comune = cursor.getString(cursor.getColumnIndexOrThrow("comune"));
		this.codice = cursor.getString(cursor.getColumnIndexOrThrow("codice"));
		this.lon = cursor.getString(cursor.getColumnIndexOrThrow("lon"));
		this.lat = cursor.getString(cursor.getColumnIndexOrThrow("lat"));
		this.regione = cursor.getString(cursor.getColumnIndexOrThrow("regione"));
		this.prov = cursor.getString(cursor.getColumnIndexOrThrow("prov"));
		this.prefisso = cursor.getString(cursor.getColumnIndexOrThrow("prefisso"));
		this.sup = cursor.getString(cursor.getColumnIndexOrThrow("sup"));
		this.residenti = cursor.getString(cursor.getColumnIndexOrThrow("residenti"));
		this.abitanti = cursor.getString(cursor.getColumnIndexOrThrow("abitanti"));
		this.patrono_nome = cursor.getString(cursor.getColumnIndexOrThrow("patrono_nome"));
		this.patrono_data = cursor.getString(cursor.getColumnIndexOrThrow("patrono_data"));
		this.cap = cursor.getString(cursor.getColumnIndexOrThrow("cap"));
		this._id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
    }

    public Comune(String str) {

	}
    
}
