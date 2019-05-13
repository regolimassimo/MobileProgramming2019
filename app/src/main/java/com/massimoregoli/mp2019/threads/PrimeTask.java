package com.massimoregoli.mp2019.threads;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.massimoregoli.mp2019.R;

import java.lang.ref.WeakReference;

public class PrimeTask extends AsyncTask<Object, Long, Long> {
    Long ZERO = new Long(0);
    private final WeakReference<Activity> activity;

    public PrimeTask(Activity a) {
        this.activity = new WeakReference<>(a);
    }

    boolean isPrime(Long l) {
        Long i = new Long(2);
        Long e;
        double fEnd;

        e = new Long((int)Math.sqrt(l))+1;

        while(i < e) {
            if(l % i == ZERO) {
                return false;
            }
            ++i;
        }
        return true;
    }

    @Override
    protected Long doInBackground(Object... obj) {
        Long count = ZERO;

        String cmd = (String)obj[1];
        if(cmd.compareTo("#") == 0)
            count = computeNumberofPrimesBeforeN((Long)obj[0]);
        if(cmd.compareTo("isPrime") == 0) {
            if (isPrime((Long) obj[0]))
                count = new Long(1);
            else
                count = new Long(0);
        }
        return count;
    }

    Long computeNumberofPrimesBeforeN(Long longs) {
        Long start = new Long(1);
        Long count = ZERO;
        Long ten = new Long(longs / 10);
        while(start <= longs) {
            if(isCancelled())
                break;
            if(isPrime(start))
                ++count;
            if(ten != 0 && start % ten == 0) {
                publishProgress(start / ten);
            }
            ++start;
        }
        return count;
    }

    @Override
    protected void onPreExecute() {
        Log.w("PN", "Start");
        activity.get().findViewById(R.id.btnNoOfPrime).setEnabled(false);
        activity.get().findViewById(R.id.btnCancel).setEnabled(true);
        activity.get().findViewById(R.id.etInput).setEnabled(false);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Long aLong) {
        Log.w("PN", "Risultato: " + aLong);
        super.onPostExecute(aLong);
        activity.get().findViewById(R.id.btnNoOfPrime).setEnabled(true);
        activity.get().findViewById(R.id.etInput).setEnabled(true);
        activity.get().findViewById(R.id.btnCancel).setEnabled(false);
        ((TextView)activity.get().findViewById(R.id.tvOutput))
                .setText("Risultato: " + aLong);
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        Log.w("PN", "Progress: " + values[0]);
        super.onProgressUpdate(values);
        ((TextView)activity.get().findViewById(R.id.tvOutput))
                .setText("Progress: " + values[0] + "%");
    }

    @Override
    protected void onCancelled(Long aLong) {
        Log.w("PN", "Cancellato: " + aLong);
        super.onCancelled(aLong);
        activity.get().findViewById(R.id.btnNoOfPrime).setEnabled(true);
        activity.get().findViewById(R.id.etInput).setEnabled(true);
        activity.get().findViewById(R.id.btnCancel).setEnabled(false);
        ((TextView)activity.get().findViewById(R.id.tvOutput))
                .setText("Cancellato: " + aLong);
    }
}
