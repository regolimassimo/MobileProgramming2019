package com.massimoregoli.mp2019.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.data.MyFirstSQLiteOpenHelper;

import java.sql.SQLException;

public class MyCursorAdapter extends CursorAdapter implements AdapterView.OnItemClickListener {
    MyFirstSQLiteOpenHelper db;
    public MyCursorAdapter(Context context) {
        super(context, null, true);
        db = new MyFirstSQLiteOpenHelper(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_list,parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String label = cursor.getString(cursor.getColumnIndexOrThrow("name")) +
                " (" + cursor.getString(cursor.getColumnIndexOrThrow("prov")) +
                ")";

        int pos = cursor.getPosition();
        TextView text1 = view.findViewById(R.id.text1);
        if(pos % 2 == 0) {
            text1.setBackgroundColor(Color.LTGRAY);
        }
        text1.setText(label);
    }
    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if (getFilterQueryProvider() != null) {
            return getFilterQueryProvider().runQuery(constraint);
        }

        Cursor cursor = null;
        try {
            cursor = db.fetchItemsByDesc(
                    (constraint != null ? constraint.toString() : "@@@@"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursor;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
        // Get the Item Number from this row in the database.
        String itemNumber = cursor.getString(cursor.getColumnIndexOrThrow("name"));

    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        String name = cursor
                .getString(cursor
                        .getColumnIndexOrThrow("name"));
        return name;
    }
}
