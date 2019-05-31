package com.massimoregoli.mp2019.myactivities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.massimoregoli.mp2019.R;

import org.json.JSONObject;

public class HTMLCodeActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmlcode);
//        first();
        second();

    }

    private void first() {
        textView = (TextView) findViewById(R.id.tvSource);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.massimoregoli.com";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void second() {
        textView = (TextView) findViewById(R.id.tvSource);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    static class ASingleton {
        private static final ASingleton instance = new ASingleton();
        private RequestQueue requestQueue;
        private ImageLoader imageLoader;
        private static Context ctx;
        public RequestQueue getRequestQueue() {
            if (requestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
            }
            return requestQueue;
        }
        public <T> void addToRequestQueue(Request<T> req) {
            getRequestQueue().add(req);
        }
        public static ASingleton getInstance(Context context){
            return instance;
        }

    }
}
