package com.massimoregoli.mp2019.threads;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;

import com.massimoregoli.mp2019.myactivities.AsyncActivity;

public class SimpleASyncTask
        extends AsyncTask<Integer, Integer, Void> {
    AsyncActivity.ViewHolder viewHolder;

    public SimpleASyncTask(AsyncActivity.ViewHolder vh) {
        viewHolder = vh;
    }

    @Override
    protected void onPreExecute() {
        viewHolder.preExecute();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        viewHolder.postExecute();
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_CALL_SIGNAL_ISDN_PING_RING,300);

        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        viewHolder.progressUpdate(values);
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        Integer te = 0;
        while(te < integers[0]) {
            ++te;
            publishProgress(te);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
