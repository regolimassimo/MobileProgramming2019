package com.massimoregoli.mp2019.threads;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MusicTask extends AsyncTask<String, String, String> {

    private final Context context;

    public MusicTask(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        String ret = null;
        boolean retry = true;


        URL url = null;
        try {
            // url = new URL("https://musixmatchcom-musixmatch.p.rapidapi.com/wsr/1.1/artist.search?s_artist_rating=desc&q_artist="+strings[0]+"&page=1&page_size=5");
            url = new URL("https://musixmatchcom-musixmatch.p.rapidapi.com/wsr/1.1/track.search?q_artist="+strings[0]+"&page_size=50&page=1");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection urlConnection = null;
        while (retry) {
            retry = false;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("X-RapidAPI-Host", "musixmatchcom-musixmatch.p.rapidapi.com");
                urlConnection.setRequestProperty("X-RapidAPI-Key", "xagSw0udhCmshDYFePS17Yz9Af3Jp1ksI2KjsnB1gkjiq6grz6");
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.setUseCaches(false);
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    ret = readStream(in);
                } catch (IOException e) {
                    retry = true;
                    publishProgress("retry");
                } finally {
                    int responseCode = urlConnection.getResponseCode();
                    Log.w("AT", "RC=" + responseCode);
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
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
            onProgressUpdate("retry...");
            e.printStackTrace();
        }
        Log.w("AT", ret);
        return ret;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast.makeText(context, values[0], Toast.LENGTH_LONG).show();
        super.onProgressUpdate(values);
    }
}
