package com.bignerdranch.android.listview;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class UserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);

        // Retrieve user data from Intent
        String name = getIntent().getStringExtra("user_name");
        String phone = getIntent().getStringExtra("user_phone");
        String imageUrl = getIntent().getStringExtra("user_image");

        // Populate the UI
        TextView nameTextView = findViewById(R.id.user_name);
        TextView phoneTextView = findViewById(R.id.user_phone);
        NetworkImageView userImageView = findViewById(R.id.userImageView);

        nameTextView.setText(name);
        phoneTextView.setText(phone);

        // Load the image using Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);
            @Override
            public Bitmap getBitmap(String url) { return cache.get(url); }
            @Override
            public void putBitmap(String url, Bitmap bitmap) { cache.put(url, bitmap); }
        });

        userImageView.setImageUrl(imageUrl, imageLoader);
    }
}
