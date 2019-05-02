package com.massimoregoli.mp2019.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.data.Comune;

import java.util.Locale;

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
        Comune comune = new Comune(cursor);
        setText(viewHolder.tv1, comune.getComune());

        setText(viewHolder.tv2, String.format(Locale.getDefault(), "Patrono: <b>%s</b>", comune.getPatrono_nome()));
        setText(viewHolder.tv3, String.format(Locale.getDefault(), "(%s)", comune.getPatrono_data()));
        setText(viewHolder.tv4, String.format(Locale.getDefault(), "CAP: <b>%s</b>", comune.getCap()));
        setText(viewHolder.tv11, String.format(Locale.getDefault(), "(<b>%s</b> - <i>%s</i>)", comune.getProv(), comune.getRegione()));

        setText(viewHolder.tv5, String.format(Locale.getDefault(), "Residenti: <b>%s</b>", comune.getResidenti()));
        setText(viewHolder.tv6, String.format(Locale.getDefault(), "Superficie: <b>%s</b> kmq", comune.getSup()));
        setText(viewHolder.tv7, String.format(Locale.getDefault(), "Lng: <i>%s</i>", comune.getLon()));
        setText(viewHolder.tv8, String.format(Locale.getDefault(), "Lat: <i>%s</i>", comune.getLat()));
        setText(viewHolder.tv9, String.format(Locale.getDefault(), "Appellativo: <i>%s</i>", comune.getAbitanti()));
        setText(viewHolder.tv10, String.format(Locale.getDefault(), "Codice Catastale: <b>%s</b> Codice ISTAT: <b>%s</b>", comune.getCodice(), comune.getIstat()));

    }

    private void setText(TextView tv, String str) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv.setText(str);
        }
    }

    @Override
    public long getItemId(int position) {
        return cursor.getLong(12);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv1, tv2, tv3, tv4, tv5,tv6, tv7, tv8, tv9, tv10, tv11;
        View view;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            view.setOnClickListener(this);
            tv1 = view.findViewById(R.id.tv1);
            tv2 = view.findViewById(R.id.tv2);
            tv3 = view.findViewById(R.id.tv3);
            tv4 = view.findViewById(R.id.tv4);
            tv5 = view.findViewById(R.id.tv5);
            tv6 = view.findViewById(R.id.tv6);
            tv7 = view.findViewById(R.id.tv7);
            tv8 = view.findViewById(R.id.tv8);
            tv9 = view.findViewById(R.id.tv9);
            tv10 = view.findViewById(R.id.tv10);
            tv11 = view.findViewById(R.id.tv11);
        }

        @Override
        public void onClick(View v) {
            cursor.moveToPosition(this.getLayoutPosition());
            Comune comune = new Comune(cursor);
            float lat, lon;
            lat = Float.parseFloat(comune.getLat());
            lon = Float.parseFloat(comune.getLon());
            String uri = String.format(Locale.getDefault(), "geo:%f,%f", lat, lon);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(intent);
            Log.w("CA", "POS " + this.getLayoutPosition());

        }
    }
}
