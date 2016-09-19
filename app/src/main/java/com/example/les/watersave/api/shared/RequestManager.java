package com.example.les.watersave.api.shared;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class RequestManager {

    private static Context ctx;
    private static RequestManager instance;

    private RequestQueue requestQueue;

    private RequestManager(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new RequestManager(ctx);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(ctx.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);

            // starting the volley request queue
            requestQueue.start();
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public void stopRequestsInRequestQueueByTag(String tag) {
        getRequestQueue().cancelAll(tag);
    }
}