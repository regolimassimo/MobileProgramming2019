package com.massimoregoli.mp2019.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.massimoregoli.mp2019.R;

public class ComuniAdapters extends RecyclerView.Adapter<ComuniAdapters.MyViewHolder> {
    private Context context;
    private Cursor cursor;
    public ComuniAdapters(Context context, Cursor cursor) {
        super();
        this.context = context;
        this.cursor = cursor;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View formNameView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comuni_item_list, viewGroup, false);

        return new MyViewHolder(formNameView);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);
        int mColumnIndexName = cursor.getColumnIndex("comune");
        int mPatronoNome = cursor.getColumnIndex("patrono_nome");
        int mPatronoData = cursor.getColumnIndex("patrono_data");
        String comuneName = cursor.getString(mColumnIndexName);
        viewHolder.tv1.setText(comuneName);
        viewHolder.tv2.setText(cursor.getString(mPatronoNome));
        viewHolder.tv3.setText(cursor.getString(mPatronoData));
    }

    @Override
    public long getItemId(int position) {
        return cursor.getLong(12);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3;

        public MyViewHolder(View view) {
            super(view);
            tv1 = view.findViewById(R.id.tv1);
            tv2 = view.findViewById(R.id.tv2);
            tv3 = view.findViewById(R.id.tv3);
        }
    }
}
