package com.massimoregoli.mp2019.threads;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.data.Drink;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CocktailTask extends AsyncTask<String, Void, Drink> {
    View view;
    public CocktailTask(LinearLayout mIngredients) {
        view = mIngredients;
    }

    @Override
    protected Drink doInBackground(String... strings) {
        Drink.Drinks ingredients;
        boolean retry = true;
        String ret = "";

        URL url = null;
        try {
            url = new URL("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i="
                    + strings[0]);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            ret = readStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder.create();

        ingredients = gson.fromJson(ret, Drink.Drinks.class);
        if(ingredients.getDrinks().size() > 0)
            return ingredients.getDrinks().get(0);
        else
            return null;
    }

    String readStream(InputStream in) {
        byte [] buffer = new byte[65536];
        String ret = "";
        int br;
        try {
            while ((br = in.read(buffer)) > 0) {
                ret = ret + new String(java.util.Arrays.copyOfRange(buffer, 0, br));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.w("AT", ret);
        return ret;
    }

    @Override
    protected void onPostExecute(Drink ingredients) {
        Log.w("CT", "END");
        if(ingredients != null) {
            ((TextView) view.findViewById(R.id.tvInstructions)).setText(ingredients.getStrInstructions());
            ((TextView) view.findViewById(R.id.tvCategory)).setText(ingredients.getStrCategory());
            ((TextView) view.findViewById(R.id.tvGlass)).setText(ingredients.getStrGlass());
            List<String> lstIngredients = ingredients.getAllIngredients();
            Log.w("CT", "ing: "+lstIngredients.size());
//            ...
        }
    }
}
