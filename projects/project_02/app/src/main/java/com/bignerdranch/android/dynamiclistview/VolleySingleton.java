package com.bignerdranch.android.dynamiclistview;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private android.util.LruCache<String, android.graphics.Bitmap> cache = new android.util.LruCache<>(20);

            @Override
            public void putBitmap(String url, android.graphics.Bitmap bitmap) {
                cache.put(url, bitmap);
            }

            @Override
            public android.graphics.Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}