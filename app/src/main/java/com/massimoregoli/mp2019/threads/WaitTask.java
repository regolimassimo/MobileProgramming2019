package com.massimoregoli.mp2019.threads;

import android.os.AsyncTask;

import com.massimoregoli.mp2019.myactivities.Async2Activity;

public class WaitTask extends AsyncTask<Integer, Integer, Void> {

    private final Async2Activity.ViewHolder viewHolder;

    public WaitTask(Async2Activity.ViewHolder vh) {
        viewHolder = vh;
    }
    @Override
    protected Void doInBackground(Integer... integers) {
        Integer elapsedTime = 0;

        while(elapsedTime < integers[0]) {
            elapsedTime++;
            try {
                Thread.sleep(1000);
                publishProgress(elapsedTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        viewHolder.pre();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        viewHolder.post();
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        viewHolder.progress(values);
        super.onProgressUpdate(values);
    }
}
